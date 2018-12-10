package com.sdr.patrollib.ui.target_device;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.contract.PatrolTargetDeviceContract;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceContentType;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.presenter.PatrolTargetDevicePresenter;
import com.sdr.patrollib.support.data.PatrolTargetView;
import com.sdr.patrollib.ui.target_device.adapter.PatrolTargetDeviceDangerRecyclerAdapter;
import com.sdr.patrollib.ui.target_device.adapter.PatrolTargetDeviceTargetRecyclerAdapter;
import com.sdr.patrollib.util.PatrolRecordUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PatrolTargetDeviceActivity extends PatrolBaseActivity<PatrolTargetDevicePresenter> implements PatrolTargetDeviceContract.View {
    private static final String PATROL_DEVICE = "PATROL_DEVICE";
    private static final String PATROL_DEVICE_RECORD = "PATROL_DEVICE_RECORD";
    private static final int REQUEST_CODE_OPEN_ADD_DANGER = 3;  // 打开添加隐患
    private static final int REQUEST_CODE_OPEN_METER_READING = 4;  // 打开选择  抄表项

    private RecyclerView recyclerViewTarget, recyclerViewDanger;
    private Button buttonSubmit;


    private PatrolDevice patrolDevice;
    private PatrolDeviceRecord patrolDeviceRecord;
    private LinkedList<PatrolTargetView> views = new LinkedList<>();

    // adapter
    private PatrolTargetDeviceTargetRecyclerAdapter targetRecyclerAdapter;
    private PatrolDevice.PatrolFacilityCheckItemsVo currentTarget;

    private PatrolTargetDeviceDangerRecyclerAdapter dangerRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_target_device);
        initIntent();
        initView();
        initData();
    }

    @Override
    protected PatrolTargetDevicePresenter instancePresenter() {
        return new PatrolTargetDevicePresenter();
    }

    private void initIntent() {
        Intent intent = getIntent();
        patrolDevice = (PatrolDevice) intent.getSerializableExtra(PATROL_DEVICE);
        patrolDeviceRecord = (PatrolDeviceRecord) intent.getSerializableExtra(PATROL_DEVICE_RECORD);
        setDisplayHomeAsUpEnabled();
        setTitle(patrolDeviceRecord.getFacilityCheckTitle());
    }

    private void initView() {
        recyclerViewTarget = findViewById(R.id.patrol_target_rv_target);
        recyclerViewDanger = findViewById(R.id.patrol_target_rv_danger);
        buttonSubmit = findViewById(R.id.patrol_target_device_btn_submit);
    }

    private void initData() {
        addView(new PatrolTargetView(patrolDeviceRecord.getFacilityCheckTitle(), recyclerViewTarget));
        // recyclerViewTarget的显示
        targetRecyclerAdapter = new PatrolTargetDeviceTargetRecyclerAdapter(R.layout.patrol_layout_item_recycler_target, patrolDevice.getPatrolFacilityCheckItemsVos(), patrolDeviceRecord);
        targetRecyclerAdapter.setEmptyView(getEmptyView());
        targetRecyclerAdapter.setOnItemClickListener(targetItemClickListener);
        recyclerViewTarget.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewTarget.setAdapter(targetRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        setNavigationOnClickListener();
    }

    @Override
    protected void setNavigationOnClickListener() {
        if (views.size() >= 2) {
            removeView();
        } else {
            // 询问是否放弃此次巡检
            new MaterialDialog.Builder(getContext())
                    .title("提示")
                    .content("是否放弃此次设备巡查，并删除巡查记录？")
                    .negativeText("继续")
                    .positiveText("放弃")
                    .positiveColor(Color.RED)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            PatrolRecordUtil.removeDeviceRecord();
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_METER_READING && resultCode == RESULT_OK) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents dangerRecord = (PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents) data.getSerializableExtra(PatrolMeterReadingActivity.DANGER);
            Iterator<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> iterator = patrolDeviceRecord.getContents().iterator();
            while (iterator.hasNext()) {
                PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = iterator.next();
                if (content.getDangerId().equals(dangerRecord.getDangerId())) {
                    iterator.remove();
                }
            }
            patrolDeviceRecord.getContents().add(dangerRecord);
            // 保存至本地
            PatrolRecordUtil.saveDeviceRecord(patrolDeviceRecord);
            // 更新adapter
            targetRecyclerAdapter.notifyDataSetChanged();
            dangerRecyclerAdapter.notifyDataSetChanged();
        }
    }

    // ———————————————————————LISTENER—————————————————————————
    private BaseQuickAdapter.OnItemClickListener targetItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            currentTarget = targetRecyclerAdapter.getItem(position);
            List<PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents> contents = currentTarget.getPatrolFacilityCheckItemContents();
            addView(new PatrolTargetView(currentTarget.getName(), recyclerViewDanger));
            dangerRecyclerAdapter = new PatrolTargetDeviceDangerRecyclerAdapter(R.layout.patrol_layout_item_recycler_danger, contents, patrolDeviceRecord);
            dangerRecyclerAdapter.setEmptyView(getEmptyView());
            dangerRecyclerAdapter.setOnItemClickListener(dangerItemClickListener);
            recyclerViewDanger.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewDanger.setAdapter(dangerRecyclerAdapter);
        }
    };

    private BaseQuickAdapter.OnItemClickListener dangerItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents content = (PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents) adapter.getData().get(position);
            // 判断检查项类别  打开不同的activity
            if (content.getCheckType().equals(PatrolDeviceContentType.检查项.toString())) {
                //PatrolDeviceAddDangerActivity.start(getActivity(), REQUEST_CODE_OPEN_ADD_DANGER, currentTarget, content, patrolDeviceRecord);
            } else if (content.getCheckType().equals(PatrolDeviceContentType.抄表项.toString())) {
                PatrolMeterReadingActivity.start(getActivity(), REQUEST_CODE_OPEN_METER_READING, content, patrolDeviceRecord);
            } else {
                showErrorMsg("数据出错，请联系管理员！");
            }
        }
    };


    // ——————————————————————PRIVATE——————————————————————————

    /**
     * 开启进入activity
     *
     * @param activity
     * @param requestCode
     * @param patrolDevice
     * @param patrolDeviceRecord
     */
    public static final void start(Activity activity, int requestCode, PatrolDevice patrolDevice, PatrolDeviceRecord patrolDeviceRecord) {
        Intent intent = new Intent(activity, PatrolTargetDeviceActivity.class);
        intent.putExtra(PATROL_DEVICE, patrolDevice);
        intent.putExtra(PATROL_DEVICE_RECORD, patrolDeviceRecord);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 添加显示的视图
     *
     * @param targetView
     */
    private void addView(PatrolTargetView targetView) {
        setTitle(targetView.getTitle());
        for (PatrolTargetView target : views) {
            target.getView().setVisibility(View.GONE);
        }
        targetView.getView().setVisibility(View.VISIBLE);
        views.add(targetView);
    }

    /**
     * 移除最后一个视图
     */
    private void removeView() {
        if (views.isEmpty()) return;
        // 移除最后一个
        PatrolTargetView targetView = views.removeLast();
        targetView.getView().setVisibility(View.GONE);
        PatrolTargetView last = views.getLast();
        setTitle(last.getTitle());
        last.getView().setVisibility(View.VISIBLE);
    }


    // ———————————————————————VIEW———————————————————————


}