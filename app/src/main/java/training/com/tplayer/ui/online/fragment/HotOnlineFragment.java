package training.com.tplayer.ui.online.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.remote.communication.MediaEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseEntity;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.custom.GravitySnapHelper;
import training.com.tplayer.custom.TextViewRoboto;
import training.com.tplayer.network.html.GetDataCodeTask;
import training.com.tplayer.network.html.HotAlbumTask;
import training.com.tplayer.network.html.HotHightLightTask;
import training.com.tplayer.network.html.HotNewTask;
import training.com.tplayer.network.html.base.IOnLoadSuccess;
import training.com.tplayer.network.service.LoadListDataCodeService;
import training.com.tplayer.ui.adapter.online.HotAlbumAdapter;
import training.com.tplayer.ui.adapter.online.HotHightLightAdapter;
import training.com.tplayer.ui.adapter.online.HotNewAdapter;
import training.com.tplayer.ui.entity.AlbumBasicEntity;
import training.com.tplayer.ui.entity.DataCodeEntity;
import training.com.tplayer.ui.entity.HotNewEntity;
import training.com.tplayer.ui.entity.HotSongOnlEntity;
import training.com.tplayer.ui.player.PlayerActivity;
import training.com.tplayer.utils.FileUtils;
import training.com.tplayer.utils.LogUtils;
import training.com.tplayer.utils.NetworkUtils;
import training.com.tplayer.utils.ViewUtils;

/**
 * Created by ThoNH on 13/04/2017.
 */

public class HotOnlineFragment extends BaseFragment implements HotAlbumAdapter.HotAlbumAdapterListener,
        HotNewAdapter.HotTopicAdapterListener, HotHightLightAdapter.HotHightLightAdapterListener,
        IOnLoadSuccess, View.OnClickListener {

    private final String FILE_NAME_CACHE_HOT_ALBUM = "cache_album_hot";
    private final String FILE_NAME_CACHE_NEW_SONG = "cache_new_song";
    private final String FILE_NAME_CACHE_HIGHT_LIGHT = "cache_hight_light";

    @BindView(R.id.fragment_online_hot_rv_album)
    RecyclerView mRvAlbum;

    @BindView(R.id.fragment_online_hot_rv_new)
    RecyclerView mRvNew;

    @BindView(R.id.fragment_online_hot_rv_hightlight)
    RecyclerView mRvHightLight;

    @BindView(R.id.fragment_online_hot_txt_title_album)
    TextViewRoboto mTitleAlbum;

    @BindView(R.id.fragment_charts_item_txt_title)
    TextViewRoboto mTitleNew;

    @BindView(R.id.fragment_online_hot_txt_title_hightlight)
    TextViewRoboto mTitleHightlight;

    @BindView(R.id.layout_new_play_all)
    RelativeLayout mLayoutNew;

    @BindView(R.id.layout_hightlight_play_all)
    RelativeLayout mLayoutHightLight;

    @BindView(R.id.loading)
    ProgressBar mLoading;

    @BindView(R.id.fragment_onl_hot_reload)
    SwipeRefreshLayout mReloadLayout;


    private HotAlbumAdapter mAlbumAdapter;
    private HotNewAdapter mNewAdapter;
    private HotHightLightAdapter mHightLightAdapter;


    public static HotOnlineFragment newInstance() {
        Bundle args = new Bundle();
        HotOnlineFragment fragment = new HotOnlineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerLoadSongComplete();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_online_hot;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());

        view.findViewById(R.id.fragment_onl_hot_layout_hightlight_play_all).setOnClickListener(this);
        view.findViewById(R.id.fragment_charts_item_layout_play_all).setOnClickListener(this);

        initAlbum();
        initNew();
        initHightLight();

        mReloadLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mReloadLayout.setRefreshing(true);
                if (NetworkUtils.isDeviceOnline(mContext)) {
                    new HotAlbumTask(HotOnlineFragment.this).execute();
                    new HotNewTask(HotOnlineFragment.this).execute();
                    new HotHightLightTask(HotOnlineFragment.this).execute();
                } else {
                    mReloadLayout.setRefreshing(false);
                    ViewUtils.toastShowLong(mContext,getResources().getString(R.string.network_not_available));
                }
            }
        });
    }

    private void initAlbum() {
        mTitleAlbum.setText(R.string.fragment_title_hot_music_album);
        mTitleAlbum.setVisibility(View.GONE);
        mAlbumAdapter = new HotAlbumAdapter(mContext, this);
        setUpRecyclerView(mRvAlbum, mAlbumAdapter, (new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)));

        if (FileUtils.CacheFile.isFileExist(mContext, FILE_NAME_CACHE_HOT_ALBUM)) {
            List<AlbumBasicEntity> listAlbum = new FileUtils.CacheFile<AlbumBasicEntity>()
                    .readCacheFile(mContext, FILE_NAME_CACHE_HOT_ALBUM, AlbumBasicEntity[].class);
            if (listAlbum != null && listAlbum.size() != 0) {
                mAlbumAdapter.setDatas(listAlbum);
                mLoading.setVisibility(View.GONE);
            } else {
                new HotAlbumTask(this).execute();
            }
        } else {
            new HotAlbumTask(this).execute();
        }
    }

    private void initNew() {
        mTitleNew.setText(R.string.fragment_title_hot_music_new);
        mTitleNew.setVisibility(View.GONE);
        mNewAdapter = new HotNewAdapter(mContext, this);
        setUpRecyclerView(mRvNew, mNewAdapter, (new GridLayoutManager(mContext, 2, LinearLayoutManager.HORIZONTAL, false)));

        if (FileUtils.CacheFile.isFileExist(mContext, FILE_NAME_CACHE_NEW_SONG)) {
            List<HotNewEntity> listNew = new FileUtils.CacheFile<HotNewEntity>()
                    .readCacheFile(mContext, FILE_NAME_CACHE_NEW_SONG, HotNewEntity[].class);
            if (listNew != null && listNew.size() != 0) {
                mNewAdapter.setDatas(listNew);
                mLayoutNew.setVisibility(View.VISIBLE);
            } else {
                new HotNewTask(this).execute();
            }
        } else {
            new HotNewTask(this).execute();
        }
    }

    private void initHightLight() {

        mTitleHightlight.setText(R.string.fragment_title_hot_music_hightlight);
        mTitleHightlight.setVisibility(View.GONE);
        mHightLightAdapter = new HotHightLightAdapter(mContext, this);
        setUpRecyclerView(mRvHightLight, mHightLightAdapter,
                (new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)));

        if (FileUtils.CacheFile.isFileExist(mContext, FILE_NAME_CACHE_HIGHT_LIGHT)) {
            List<HotSongOnlEntity> listHightLight = new FileUtils.CacheFile<HotSongOnlEntity>()
                    .readCacheFile(mContext, FILE_NAME_CACHE_HIGHT_LIGHT, HotSongOnlEntity[].class);
            if (listHightLight != null && listHightLight.size() != 0) {
                mHightLightAdapter.setDatas(listHightLight);
                mLayoutHightLight.setVisibility(View.VISIBLE);
            } else {
                new HotHightLightTask(this).execute();
            }
        } else {
            new HotHightLightTask(this).execute();
        }
    }

    private void setUpRecyclerView(RecyclerView recyclerView, BaseRecyclerViewAdapter adapter,
                                   RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        GravitySnapHelper gravitySnapHelper = new GravitySnapHelper(Gravity.START);
        gravitySnapHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onRecyclerViewItemClick(View view, BaseEntity baseEntity, int position) {
        String link = "";
        if (baseEntity instanceof AlbumBasicEntity) link = ((AlbumBasicEntity) baseEntity).link;
        if (baseEntity instanceof HotSongOnlEntity) link = ((HotSongOnlEntity) baseEntity).link;
        if (baseEntity instanceof HotNewEntity) link = ((HotNewEntity) baseEntity).link;

        GetDataCodeTask getDataCodeTask = new GetDataCodeTask(link, new IOnLoadSuccess() {
            @Override
            public void onResponse(List entity, String TAG) {
                startLoadSongZingMp3(entity);
            }
        }, "getDataCodeTask");

        getDataCodeTask.execute();

    }


    @Override
    public void onResponse(List entity, String TAG) {


        if (entity.size() != 0) {
            LogUtils.printLog("onResponse true");
            mLoading.setVisibility(View.GONE);
            mReloadLayout.setEnabled(false);

            if (HotNewTask.TAG.equals(TAG)) {
                mNewAdapter.setDatas((ArrayList<HotNewEntity>) entity);

                mTitleNew.setVisibility(View.VISIBLE);
                mLayoutNew.setVisibility(View.VISIBLE);

                // save cache new song online
                new FileUtils.CacheFile<HotNewEntity>()
                        .writeCacheFile(mContext, FILE_NAME_CACHE_NEW_SONG, (ArrayList<HotNewEntity>) entity);

                return;
            }

            if (HotHightLightTask.TAG.equals(TAG)) {
                mHightLightAdapter.setDatas((ArrayList<HotSongOnlEntity>) entity);
                mTitleHightlight.setVisibility(View.VISIBLE);

                // save cache hight light song online
                new FileUtils.CacheFile<HotSongOnlEntity>()
                        .writeCacheFile(mContext, FILE_NAME_CACHE_HIGHT_LIGHT, (ArrayList<HotSongOnlEntity>) entity);

                return;
            }

            if (HotAlbumTask.TAG.equals(TAG)) {
                mAlbumAdapter.setDatas((ArrayList<AlbumBasicEntity>) entity);
                mTitleAlbum.setVisibility(View.VISIBLE);

                // save cache albums hot online
                new FileUtils.CacheFile<AlbumBasicEntity>()
                        .writeCacheFile(mContext, FILE_NAME_CACHE_HOT_ALBUM, (ArrayList<AlbumBasicEntity>) entity);

            }
        } else {
            LogUtils.printLog("onResponse false");
            mLoading.setVisibility(View.GONE);
        }
    }

    //playall click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_onl_hot_layout_hightlight_play_all:

                List<DataCodeEntity> entitiesHightLight = new ArrayList<>();
                for (int i = 0; i < mHightLightAdapter.getSize(); i++) {
                    DataCodeEntity dataCodeEntity = new DataCodeEntity(mHightLightAdapter.getDataItem(i).data_code);
                    entitiesHightLight.add(dataCodeEntity);
                }
                startLoadSongZingMp3(entitiesHightLight);

                break;
            case R.id.fragment_charts_item_layout_play_all:

                List<DataCodeEntity> entitiesNew = new ArrayList<>();
                for (int i = 0; i < mNewAdapter.getSize(); i++) {
                    DataCodeEntity dataCodeEntity = new DataCodeEntity(mNewAdapter.getDataItem(i).data_code);
                    entitiesNew.add(dataCodeEntity);
                }
                startLoadSongZingMp3(entitiesNew);
                break;
        }
    }

    private void startLoadSongZingMp3(List entity) {
        Intent intent = new Intent(mContext, LoadListDataCodeService.class);
        intent.putParcelableArrayListExtra(LoadListDataCodeService.EXTRA_DATA_CODE,
                (ArrayList<? extends Parcelable>) entity);
        mContext.startService(intent);
    }

    private void registerLoadSongComplete() {
        IntentFilter filterZing = new IntentFilter();
        filterZing.addAction(LoadListDataCodeService.ACTION_CALL_BACK_SONG);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<MediaEntity> songs =
                        intent.getParcelableArrayListExtra(LoadListDataCodeService.EXTRA_CALL_BACK);
                if (isAdded())
                startActivity(songs);

            }
        }, filterZing);
    }

    private void startActivity(List<MediaEntity> songs) {
        Intent intent = new Intent(mContext, PlayerActivity.class);
        intent.putParcelableArrayListExtra(PlayerActivity.EXTRA_DATA_PLAYER, (ArrayList<? extends Parcelable>) songs);
        startActivity(intent);
    }
}
