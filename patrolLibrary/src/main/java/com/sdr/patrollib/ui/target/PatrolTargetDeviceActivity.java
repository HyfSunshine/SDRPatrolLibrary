package com.sdr.patrollib.ui.target;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.contract.PatrolTargetDeviceContract;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.presenter.PatrolTargetDevicePresenter;

public class PatrolTargetDeviceActivity extends PatrolBaseActivity<PatrolTargetDevicePresenter> implements PatrolTargetDeviceContract.View {
    private static final String PATROL_DEVICE = "PATROL_DEVICE";
    private static final String PATROL_DEVICE_RECORD = "PATROL_DEVICE_RECORD";
    private static final int REQUEST_CODE_OPEN_ADD_DANGER = 3;  // 打开添加隐患
    private static final int REQUEST_CODE_OPEN_METER_READING = 4;  // 打开选择  抄表项

    private RecyclerView recyclerViewTarget, recyclerViewDanger;
    private Button buttonSubmit;


    private PatrolDevice patrolDevice;
    private PatrolDeviceRecord patrolDeviceRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_target_device);
        initIntent();
        initView();
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

    // ———————————————————————VIEW———————————————————————


}
