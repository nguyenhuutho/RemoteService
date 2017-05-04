package training.com.tplayer.ui.offline.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseActivity;
import training.com.tplayer.ui.adapter.offline.OfflinePagerAdapter;
import training.com.tplayer.utils.LogUtils;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class TabOfflineActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    public static final String KEY_TAB = "key.tab";
    public static final int TAB_SONG = 0;
    public static final int TAB_ALBUM = 1;
    public static final int TAB_ARTIST = 2;
    public static final int TAB_PLAYLIST = 3;
    public static final int TAB_FOLDER = 4;
    public static final int TAB_DOWNLOAD = 5;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.act_offline_tablayout)
    SmartTabLayout mTabLayout;

    @BindView(R.id.act_offline_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.act_offline_toolbar)
    Toolbar mToolbar;

    private OfflinePagerAdapter mAdapter;

    private int mCurrentTab = TAB_SONG;

    @Override
    public int setLayoutId() {
        return R.layout.activity_tab_offline;
    }

    @Override
    public void onBindView() {
        ButterKnife.bind(this);

        collapsingToolbarLayout.setTitleEnabled(false);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdapter = new OfflinePagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void getDataBundle(Bundle savedInstanceState) {
        super.getDataBundle(savedInstanceState);
        if (getIntent() != null) {
            mCurrentTab = getIntent().getIntExtra(KEY_TAB, TAB_SONG);
            mViewPager.setCurrentItem(mCurrentTab);
            mToolbar.setTitle(mAdapter.getPageTitle(mCurrentTab));
        }

    }


    @Override
    public void onActivityCreated() {
        bindTPlayerService();
    }

    @Override
    protected void createPresenterImpl() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mToolbar.setTitle(mAdapter.getPageTitle(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_offline_tab_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.app_bar_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fm = getSupportFragmentManager();
        LogUtils.printLog("Count : " + fm.getBackStackEntryCount());
    }
}
