package com.sdr.patrollib.ui.history_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.base.adapter.PatrolBaseFragmentPagerAdapter;
import com.sdr.patrollib.base.fragment.PatrolBaseSimpleFragment;
import com.sdr.patrollib.contract.PatrolHistoryInfoProjectContract;
import com.sdr.patrollib.data.project_history.PatrolHistoryInfoProject;
import com.sdr.patrollib.presenter.PatrolHistoryInfoProjectPresenter;
import com.sdr.patrollib.support.PatrolConstant;
import com.sdr.patrollib.util.PatrolUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatrolHistoryInfoProjectActivity extends PatrolBaseActivity<PatrolHistoryInfoProjectPresenter> implements PatrolHistoryInfoProjectContract.View {

    private static final String ID = "ID";
    /**
     * 视图相关
     */
    private FrameLayout flTopView;
    private ImageView ivTopHeader;
    private TextView tvTopTitle, tvTopUploader, tvTopTime, tvTopDangerNum;
    private TabLayout tab;
    private ViewPager viewPager;


    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_history_info_project);
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
        setDisplayHomeAsUpEnabled();
        initView();
        initData();
    }

    private void initView() {
        flTopView = findViewById(R.id.patrol_history_detail_mobile_top_container);
        flTopView.setPadding(0, getHeaderBarHeight(), 0, 0);
        ivTopHeader = findViewById(R.id.patrol_history_detail_mobile_top_iv_header_image);
        tvTopTitle = findViewById(R.id.patrol_history_detail_mobile_top_tv_title);
        tvTopDangerNum = findViewById(R.id.patrol_history_detail_mobile_top_tv_danger_num);
        tvTopUploader = findViewById(R.id.patrol_history_detail_mobile_top_tv_uploader);
        tvTopTime = findViewById(R.id.patrol_history_detail_mobile_top_tv_time);

        tab = findViewById(R.id.patrol_history_detail_mobile_tab);
        viewPager = findViewById(R.id.patrol_history_detail_mobile_viewpager);
    }

    private void initData() {
        // 顶部图片
        Glide.with(getContext()).load(R.mipmap.patrol_history_info_project_header).into(ivTopHeader);
        // 初始化tab
        int normalColor = PatrolUtil.getColor60(getResources().getColor(R.color.colorPrimary));
        tab.setTabTextColors(normalColor, getResources().getColor(R.color.colorPrimary));
        tab.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        // 获取数据
        showLoadingView();
        presenter.getHistoryProjectInfo(id);
    }

    @Override
    protected PatrolHistoryInfoProjectPresenter instancePresenter() {
        return new PatrolHistoryInfoProjectPresenter();
    }

    @Override
    protected boolean isImageHeader() {
        return true;
    }


    // —————————————————————————VIEW—————————————————————————————

    /**
     * 开启详情activity
     *
     * @param context
     * @param id
     */
    public static final void start(Context context, String id) {
        Intent intent = new Intent(context, PatrolHistoryInfoProjectActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    // ——————————————————————VIEW————————————————————————
    @Override
    public void loadHistoryProjectInfoSuccess(PatrolHistoryInfoProject patrolHistoryInfoProject) {
        // 更新页面内容
        tvTopTitle.setText(patrolHistoryInfoProject.getMobileCheckName());
        tvTopUploader.setText("巡查人：" + patrolHistoryInfoProject.getPatrolEmployeeName());
        tvTopDangerNum.setVisibility(patrolHistoryInfoProject.getErrorCount() > 0 ? View.VISIBLE : View.GONE);
        tvTopDangerNum.setText(patrolHistoryInfoProject.getErrorCount() + "隐患");
        tvTopTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(new Date(patrolHistoryInfoProject.getPatrolStartTime()))
                + "至" +
                PatrolConstant.DATE_TIME_FORMAT.format(new Date(patrolHistoryInfoProject.getPatrolEndTime())));


        List<PatrolBaseSimpleFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PatrolHistoryInfoProjectSheetFragment(patrolHistoryInfoProject));
        fragmentList.add(new PatrolHistoryInfoProjectMapFragment(patrolHistoryInfoProject));

        PatrolBaseFragmentPagerAdapter patrolBaseFragmentPagerAdapter = new PatrolBaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(patrolBaseFragmentPagerAdapter);
        tab.setupWithViewPager(viewPager);
    }

    @Override
    public void loadDataComplete() {
        showContentView();
    }
}
