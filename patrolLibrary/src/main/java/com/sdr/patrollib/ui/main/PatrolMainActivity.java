package com.sdr.patrollib.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.base.adapter.BaseFragmentPagerAdapter;
import com.sdr.patrollib.base.fragment.PatrolBaseSimpleFragment;
import com.sdr.patrollib.presenter.PatrolMainPresenter;
import com.sdr.patrollib.util.PatrolUtil;

import java.util.ArrayList;
import java.util.List;

public class PatrolMainActivity extends PatrolBaseActivity<PatrolMainPresenter> {

    private ImageView viewHeaderImage;
    private View viewHeaderContainer;
    private TextView viewHeaderTvUserName;
    private TextView viewHeaderTvUserPhone;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_main);
        setDisplayHomeAsUpEnabled();
        initView();
        initData();
    }

    @Override
    protected PatrolMainPresenter instancePresenter() {
        return new PatrolMainPresenter();
    }

    private void initView() {
        viewHeaderImage = findViewById(R.id.patrol_main_header_iv_background);
        viewHeaderContainer = findViewById(R.id.patrol_main_header_container);
        viewHeaderTvUserName = findViewById(R.id.patrol_main_header_tv_username);
        viewHeaderTvUserPhone = findViewById(R.id.patrol_main_header_tv_userphone);
        tabLayout = findViewById(R.id.patrol_main_tab);
        viewPager = findViewById(R.id.patrol_main_viewpager);
    }

    private void initData() {
        // toolbar
        {
            toolBar.inflateMenu(R.menu.patrol_menu_main);
            toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.patrol_menu_id_qrcode) {
                        // 打开扫码 需要申请相机权限

                    }
                    return true;
                }
            });
        }

        // header
        {
            Glide.with(getContext()).load(R.mipmap.patrol_main_header_image).into(viewHeaderImage);
            viewHeaderContainer.setPadding(0, getHeaderBarHeight(), 0, 0);
            viewHeaderTvUserName.setText(PatrolLibrary.getInstance().getPatrolUser().getUserName());
            viewHeaderTvUserPhone.setText(PatrolLibrary.getInstance().getPatrolUser().getPhoneNum());
        }

        // container
        {
            int selectColor = getResources().getColor(R.color.colorPrimary);
            int normalColor = PatrolUtil.getColor60(selectColor);
            tabLayout.setTabTextColors(normalColor, selectColor);

            List<PatrolBaseSimpleFragment> fragmentList = new ArrayList<>();
            fragmentList.add(new PatrolMainProjectFragment());
            fragmentList.add(new PatrolMainDeviceFragment());
            BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
            viewPager.setAdapter(baseFragmentPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

        }
    }


    @Override
    protected boolean isImageHeader() {
        return true;
    }
}
