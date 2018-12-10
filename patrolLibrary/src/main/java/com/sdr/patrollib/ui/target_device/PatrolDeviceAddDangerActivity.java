package com.sdr.patrollib.ui.target_device;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseSimpleActivity;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;

public class PatrolDeviceAddDangerActivity extends PatrolBaseSimpleActivity {
    private static final String TARGET = "TARGET";
    public static final String CONTENT = "CONTENT";
    private static final String DEVICE_RECORD = "DEVICE_RECORD";
    public static final String DANGER_LIST = "DANGER_LIST";
    private static final int REQUEST_OPEN_PHOTO_VIDEO = 10;
    // 视图相关
    private EditText viewEditAdd;
    private RecyclerView viewRecyclerAdd;
    private Button viewButtonSave;
    private RecyclerView viewRecyclerDangerList;


    /**
     * 逻辑相关
     */
    private PatrolDevice.PatrolFacilityCheckItemsVo target;
    private PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents itemContent;
    private PatrolDeviceRecord deviceRecord;
    private boolean isDataChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_device_add_danger);
        setTitle("添加设备巡检隐患");
        setDisplayHomeAsUpEnabled();
        initIntent();
        initView();
        initData();
    }

    private void initIntent() {
        Intent intent = getIntent();
        target = (PatrolDevice.PatrolFacilityCheckItemsVo) intent.getSerializableExtra(TARGET);
        itemContent = (PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents) intent.getSerializableExtra(CONTENT);
        deviceRecord = (PatrolDeviceRecord) intent.getSerializableExtra(DEVICE_RECORD);
    }

    private void initView() {
        viewEditAdd = findViewById(R.id.patrol_device_add_danger_edt_danger);
        viewRecyclerAdd = findViewById(R.id.patrol_device_add_danger_rv_add_image);
        viewButtonSave = findViewById(R.id.patrol_device_add_danger_btn_save_danger);
        viewRecyclerDangerList = findViewById(R.id.patrol_device_add_danger_rv_danger_list);
    }

    private void initData() {

    }


    //————————————————————————PRIVATE————————————————————————

    /**
     * 获取添加的foot view
     *
     * @return
     */
    private final View getAddFooterView() {
        return getLayoutInflater().inflate(R.layout.patrol_layout_item_recycler_danger_image_add, null);
    }


    /**
     * 开启activity
     *
     * @param activity
     * @param requestCode
     * @param itemContent
     */
    public static final void start(Activity activity, int requestCode, PatrolDevice.PatrolFacilityCheckItemsVo target, PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents itemContent, PatrolDeviceRecord deviceRecord) {
        Intent intent = new Intent(activity, PatrolDeviceAddDangerActivity.class);
        intent.putExtra(TARGET, target);
        intent.putExtra(CONTENT, itemContent);
        intent.putExtra(DEVICE_RECORD, deviceRecord);
        activity.startActivityForResult(intent, requestCode);
    }


}
