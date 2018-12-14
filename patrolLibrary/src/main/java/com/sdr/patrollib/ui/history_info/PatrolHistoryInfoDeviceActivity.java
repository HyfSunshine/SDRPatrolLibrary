package com.sdr.patrollib.ui.history_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.sdr.lib.widget.InnerViewPagerNestedScrollView;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.contract.PatrolHistoryInfoDeviceContract;
import com.sdr.patrollib.data.device_history.PatrolHistoryInfoDevice;
import com.sdr.patrollib.presenter.PatrolHistoryInfoDevicePresenter;
import com.sdr.patrollib.support.PatrolConstant;
import com.sdr.patrollib.support.data.PatrolHistoryInfoTransformer;
import com.sdr.patrollib.ui.history_info.adapter.PatrolHistoryInfoDeviceRecyclerAdapter;
import com.sdr.patrollib.widget.PatrolListenerScrollView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatrolHistoryInfoDeviceActivity extends PatrolBaseActivity<PatrolHistoryInfoDevicePresenter> implements PatrolHistoryInfoDeviceContract.View {
    private static final String ID = "ID";

    /**
     * 视图相关
     */
    private PatrolListenerScrollView scrollView;
    private FrameLayout flTopView;
    private ImageView ivTopHeader;
    private TextView tvTopTitle, tvTopUploader, tvTopTime, tvTopDangerNum;
    private RecyclerView recyclerView;

    private String id;
    private PatrolHistoryInfoDeviceRecyclerAdapter patrolHistoryInfoDeviceRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_history_info_device);
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
        setDisplayHomeAsUpEnabled();

        initView();
        initData();
    }

    private void initView() {
        scrollView = findViewById(R.id.patrol_history_detail_device_scroll_view);
        flTopView = findViewById(R.id.patrol_history_detail_mobile_top_container);
        flTopView.setPadding(0, getHeaderBarHeight(), 0, 0);
        ivTopHeader = findViewById(R.id.patrol_history_detail_mobile_top_iv_header_image);
        tvTopTitle = findViewById(R.id.patrol_history_detail_mobile_top_tv_title);
        tvTopDangerNum = findViewById(R.id.patrol_history_detail_mobile_top_tv_danger_num);
        tvTopUploader = findViewById(R.id.patrol_history_detail_mobile_top_tv_uploader);
        tvTopTime = findViewById(R.id.patrol_history_detail_mobile_top_tv_time);

        recyclerView = findViewById(R.id.patrol_history_detail_device_recycler);
    }

    private void initData() {
        // 顶部图片
        Glide.with(getContext()).load(R.mipmap.patrol_history_info_device_header).into(ivTopHeader);
        setHeaderImage(R.mipmap.patrol_history_info_device_header);

        // recycler view adapter
        patrolHistoryInfoDeviceRecyclerAdapter = new PatrolHistoryInfoDeviceRecyclerAdapter(R.layout.patrol_layout_item_recycler_history_info);
        patrolHistoryInfoDeviceRecyclerAdapter.setEmptyView(getEmptyView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(patrolHistoryInfoDeviceRecyclerAdapter);
        // 获取数据
        showLoadingView();
        presenter.getHistoryInfoDevice(id);
    }

    @Override
    protected PatrolHistoryInfoDevicePresenter instancePresenter() {
        return new PatrolHistoryInfoDevicePresenter();
    }

    @Override
    public void onScrollChange(int scrollY, float alpha) {
        setHeaderBarTitleViewAlpha(alpha);
    }

    @Override
    protected boolean isImageHeader() {
        return true;
    }

    // ————————————————————PRIVATE——————————————————————

    private List getExitsList(String targetId, List<PatrolHistoryInfoTransformer> list) {
        for (int i = 0; i < list.size(); i++) {
            PatrolHistoryInfoTransformer patrolHistoryDetail = list.get(i);
            if (patrolHistoryDetail.getId().equals(targetId + "")) {
                return patrolHistoryDetail.getDangerList();
            }
        }
        return null;
    }

    /**
     * 开启此activity
     *
     * @param context
     * @param id
     */
    public static final void start(Context context, String id) {
        Intent intent = new Intent(context, PatrolHistoryInfoDeviceActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }


    //——————————————————————VIEW————————————————————
    @Override
    public void loadHistoryInfoDeviceSuccess(PatrolHistoryInfoDevice patrolHistoryInfoDevice) {
        // 更新页面内容
        setTitle(patrolHistoryInfoDevice.getFacilityCheckTitle());
        tvTopTitle.setText(patrolHistoryInfoDevice.getFacilityCheckTitle());
        tvTopUploader.setText("巡查人：" + patrolHistoryInfoDevice.getPatrolEmployeeName());
        tvTopDangerNum.setVisibility(patrolHistoryInfoDevice.getErrorCount() > 0 ? View.VISIBLE : View.GONE);
        tvTopDangerNum.setText(patrolHistoryInfoDevice.getErrorCount() + "隐患");
        tvTopTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(new Date(patrolHistoryInfoDevice.getPatrolTime())));
        // 测量高度
        ivTopHeader.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        setHeaderBarScrollChange(scrollView, ivTopHeader.getMeasuredHeight());
        // 数据分类
        List<PatrolHistoryInfoTransformer> list = new ArrayList<>();
        List<PatrolHistoryInfoDevice.ContentsBean> dangerList = patrolHistoryInfoDevice.getContents();
        for (int i = 0; i < dangerList.size(); i++) {
            PatrolHistoryInfoDevice.ContentsBean bean = dangerList.get(i);
            List exitsList = getExitsList(bean.getItemId(), list);
            if (exitsList == null) {
                List<PatrolHistoryInfoDevice.ContentsBean> l = new ArrayList<>();
                l.add(bean);
                list.add(new PatrolHistoryInfoTransformer(bean.getItemId() + "", bean.getItemName(), l));
            } else {
                exitsList.add(bean);
            }
        }
        patrolHistoryInfoDeviceRecyclerAdapter.setNewData(list);
    }

    @Override
    public void loadDataComplete() {
        showContentView();
    }
}
