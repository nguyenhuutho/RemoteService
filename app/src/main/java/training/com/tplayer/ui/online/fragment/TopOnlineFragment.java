package training.com.tplayer.ui.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.tplayer.R;
import training.com.tplayer.base.BaseFragment;
import training.com.tplayer.custom.TextViewRoboto;

/**
 * Created by ThoNH on 13/04/2017.
 */

public class TopOnlineFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.textViewRobotoVietnam)
    TextViewRoboto txtTitleVietnam;
    @BindView(R.id.textViewRobotoNational)
    TextViewRoboto txtTitleNational;

    public static TopOnlineFragment newInstance() {
        Bundle args = new Bundle();
        TopOnlineFragment fragment = new TopOnlineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_online_top;
    }

    @Override
    public void onViewCreatedFragment(View view, @Nullable Bundle savedInstanceState) {

        ButterKnife.bind(this, getView());
        txtTitleNational.setText(R.string.fragment_title_top_music_national);
        txtTitleVietnam.setText(R.string.fragment_title_top_music_vietnam);

        view.findViewById(R.id.vietnam_bolero).setOnClickListener(this);
        view.findViewById(R.id.vietnam_dance).setOnClickListener(this);
        view.findViewById(R.id.vietnam_rap).setOnClickListener(this);
        view.findViewById(R.id.vietnam_rock).setOnClickListener(this);
        view.findViewById(R.id.vietnam_trinh).setOnClickListener(this);
        view.findViewById(R.id.vietnam_young).setOnClickListener(this);

        view.findViewById(R.id.nation_japan).setOnClickListener(this);
        view.findViewById(R.id.nation_rap).setOnClickListener(this);
        view.findViewById(R.id.national_edm).setOnClickListener(this);
        view.findViewById(R.id.national_kpop).setOnClickListener(this);
        view.findViewById(R.id.national_rock).setOnClickListener(this);
        view.findViewById(R.id.national_pop).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vietnam_young:
                addFragment("http://mp3.zing.vn/top100/Nhac-Tre/IWZ9Z088.html");
                break;
            case R.id.vietnam_bolero:
                addFragment("http://mp3.zing.vn/top100/Nhac-Tru-Tinh/IWZ9Z08B.html");
                break;
            case R.id.vietnam_dance:
                addFragment("http://mp3.zing.vn/top100/Nhac-Dance/IWZ9Z0CW.html");
                break;
            case R.id.vietnam_rap:
                addFragment("http://mp3.zing.vn/top100/Rap-Viet/IWZ9Z089.html");
                break;
            case R.id.vietnam_rock:
                addFragment("http://mp3.zing.vn/top100/Rock-Viet/IWZ9Z08A.html");
                break;
            case R.id.vietnam_trinh:
                addFragment("http://mp3.zing.vn/top100/Nhac-Trinh/IWZ9Z08E.html");
                break;

            case R.id.nation_japan:
                addFragment("http://mp3.zing.vn/top100/Nhat-Ban/IWZ9Z08Z.html");
                break;
            case R.id.nation_rap:
                addFragment("http://mp3.zing.vn/top100/Rap-Hip-Hop/IWZ9Z09B.html");
                break;
            case R.id.national_edm:
                addFragment("http://mp3.zing.vn/top100/Electronic-Dance/IWZ9Z09A.html");
                break;
            case R.id.national_kpop:
                addFragment("http://mp3.zing.vn/top100/Han-Quoc/IWZ9Z08W.html");
                break;
            case R.id.national_rock:
                addFragment("http://mp3.zing.vn/top100/Rock/IWZ9Z099.html");
                break;
            case R.id.national_pop:
                addFragment("http://mp3.zing.vn/top100/Pop/IWZ9Z097.html");
                break;
        }
    }

    private void addFragment(String url) {
         ChartsItemFragment fragment = ChartsItemFragment.newInstance(url) ;
        String tag = ChartsItemFragment.class.getSimpleName();
         FragmentManager fm = getFragmentManager();
         FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right,
                R.anim.enter_from_right,
                R.anim.exit_to_left);
        ft.add(R.id.layout_main_album,fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }
}
