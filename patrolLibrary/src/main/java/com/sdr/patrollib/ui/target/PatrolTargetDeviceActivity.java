package com.sdr.patrollib.ui.target;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.contract.PatrolTargetDeviceContract;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.presenter.PatrolTargetDevicePresenter;
import com.sdr.patrollib.support.data.PatrolTargetView;

import java.util.LinkedList;

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

    }

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
