package com.sdr.patrollib.ui.history;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.sdr.lib.util.CommonUtil;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseSimpleActivity;
import com.sdr.patrollib.base.adapter.PatrolBaseFragmentPagerAdapter;
import com.sdr.patrollib.base.fragment.PatrolBaseSimpleFragment;

import java.util.ArrayList;
import java.util.List;

public class PatrolHistoryActivity extends PatrolBaseSimpleActivity {
    private ViewPager viewPager;

    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_history);
        initToolbar();
        initView();
        // 工程巡检
        // 设备巡检
        initData();
    }

    private void initToolbar() {
        // 添加tablayout
        tab = new TabLayout(getContext());
        tab.setSelectedTabIndicatorHeight(CommonUtil.dip2px(getContext(), 2));
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setBackgroundColor(Color.TRANSPARENT);
        tab.setSelectedTabIndicatorColor(Color.WHITE);
        tab.setTabTextColors(Color.parseColor("#99FFFFFF"), Color.WHITE);
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        toolBar.addView(tab, layoutParams);

        setDisplayHomeAsUpEnabled();
    }


    private void initView() {
        viewPager = findViewById(R.id.patrol_history_view_pager);
    }

    private void initData() {
        List<PatrolBaseSimpleFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PatrolHistoryProjectFragment());
        fragmentList.add(new PatrolHistoryDeviceFragment());
        PatrolBaseFragmentPagerAdapter pagerAdapter = new PatrolBaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(viewPager, true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }
}
