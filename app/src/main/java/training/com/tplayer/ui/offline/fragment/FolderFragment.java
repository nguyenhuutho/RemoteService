package training.com.tplayer.ui.offline.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.remote.communication.MediaEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.database.SourceTableMedia;
import training.com.tplayer.ui.adapter.offline.FolderAdapter;
import training.com.tplayer.ui.entity.offline.FolderEntity;
import training.com.tplayer.utils.LogUtils;

import static android.R.attr.path;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class FolderFragment extends BaseFragment implements FolderAdapter.FolderAdapterListener {

    @BindView(R.id.fragment_offline_rv_folder)
    RecyclerView mRvFolder;

    private FolderAdapter mAdapter;

    public static FolderFragment newInstance() {
        Bundle args = new Bundle();
        FolderFragment fragment = new FolderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_offline_folder;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        mAdapter = new FolderAdapter(mContext, this);
        mRvFolder.setLayoutManager(new LinearLayoutManager(mContext));
        mRvFolder.setAdapter(mAdapter);


        new AsyncTask<Void, Void, List<FolderEntity>>() {
            @Override
            protected List<FolderEntity> doInBackground(Void... params) {
                return SourceTableMedia.getInstance(mContext).getListFolder();
            }

            @Override
            protected void onPostExecute(List<FolderEntity> folderEntities) {
                super.onPostExecute(folderEntities);
                LogUtils.printLog(folderEntities.toString());
                mAdapter.setDatas(folderEntities);
            }
        }.execute();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_folder_play:
                break;
            case R.id.action_folder_play_shuffle:
                break;
            case R.id.action_folder_add_now_playing:
                break;
            case R.id.action_folder_add_playlist:
                break;
            case R.id.action_folder_hide:
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onRecyclerViewItemClick(View view, FolderEntity folderEntity, int position) {
        FragmentManager fm = getFragmentManager();
        LogUtils.printLog(folderEntity.mPath);
        FragmentTransaction ft = fm.beginTransaction();
        List<MediaEntity> entityList = SourceTableMedia.getInstance(mContext)
                .getList( new String[]{path + "%", path + "%/%"} ,  MediaStore.Audio.Media.DATA + " LIKE ? AND " + MediaStore.Audio.Media.DATA + " NOT LIKE ?");

        LogUtils.printLog(entityList.toString());
        ft.add(R.id.root, SongsFragment.newInstance(SongsFragment.BUNDLE_FROM_ALBUM, entityList));
        ft.addToBackStack(null);
        ft.commit();
    }
}
