package com.sdr.patrollib.ui.target_device;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyf.takephotovideolib.support.TakePhotoVideoHelper;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseSimpleActivity;
import com.sdr.patrollib.base.adapter.PatrolDangerImageListRecyclerAdapter;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.support.PatrolACache;
import com.sdr.patrollib.support.PatrolConstant;
import com.sdr.patrollib.support.PatrolDangerUpdateListener;
import com.sdr.patrollib.support.data.AttachmentLocal;
import com.sdr.patrollib.ui.target_device.adapter.PatrolDangerDeviceListRecyclerAdapter;
import com.sdr.patrollib.util.PatrolUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PatrolDeviceAddDangerActivity extends PatrolBaseSimpleActivity implements PatrolDangerUpdateListener {
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
    private View viewAddImage;

    /**
     * 逻辑相关
     */
    private PatrolDevice.PatrolFacilityCheckItemsVo target;
    private PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents itemContent;
    private PatrolDeviceRecord deviceRecord;
    private boolean isDataChanged = false;

    // adapter
    private PatrolDangerImageListRecyclerAdapter patrolDangerImageListRecyclerAdapter;
    private PatrolDangerDeviceListRecyclerAdapter patrolDangerDeviceListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_device_add_danger);
        setTitle("添加设备巡检隐患");
        setDisplayHomeAsUpEnabled();
        initIntent();
        initView();
        initData();
        initListener();
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
        // 初始化 添加隐患
        viewAddImage = getAddFooterView();
        patrolDangerImageListRecyclerAdapter = PatrolDangerImageListRecyclerAdapter.setAdapter(viewRecyclerAdd, true);
        patrolDangerImageListRecyclerAdapter.addFooterView(viewAddImage);
        patrolDangerImageListRecyclerAdapter.setFooterViewAsFlow(true);

        // 隐患列表
//        // 获取本地缓存中所有的dangers  然后再根据内容id查询出所有的隐患  最后显示在预览的recycler view中
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> recordDangerList = getExitsDangerList(itemContent.getId());
        patrolDangerDeviceListRecyclerAdapter = new PatrolDangerDeviceListRecyclerAdapter(R.layout.patrol_layout_item_recycler_danger_list, recordDangerList, this);
        viewRecyclerDangerList.setLayoutManager(new LinearLayoutManager(getContext()));
        viewRecyclerDangerList.setNestedScrollingEnabled(false);
        viewRecyclerDangerList.setAdapter(patrolDangerDeviceListRecyclerAdapter);
    }

    private void initListener() {
        viewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patrolDangerImageListRecyclerAdapter.getData().size() >= PatrolLibrary.getInstance().getPatrolConfig().getMaxMediaCount()) {
                    // 说明已经最多了
                    showErrorMsg("您最多添加" + PatrolLibrary.getInstance().getPatrolConfig().getMaxMediaCount() + "个附件");
                    return;
                }
                new RxPermissions(getActivity())
                        .request(
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    // 开启照片拍照
                                    TakePhotoVideoHelper.startTakePhotoVideo(getActivity(), REQUEST_OPEN_PHOTO_VIDEO, PatrolACache.getSavePath(), PatrolLibrary.getInstance().getPatrolConfig().getMaxVideoDuration());
                                }
                            }
                        });
            }
        });

        viewButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存到预览界面中   保存的时候判断是否能保存，如：edt 或者 图片不能为空   保存的最大数量
                if (patrolDangerDeviceListRecyclerAdapter.getData().size() >= PatrolLibrary.getInstance().getPatrolConfig().getMaxDangerCount()) {
                    // 说明超数了
                    showErrorMsg("您最多能添加" + PatrolLibrary.getInstance().getPatrolConfig().getMaxDangerCount() + "条隐患");
                    return;
                }
                String text = viewEditAdd.getText().toString().trim().replaceAll(" ", "");
                if (TextUtils.isEmpty(text) && patrolDangerImageListRecyclerAdapter.getData().isEmpty()) {
                    // 数据为空 不能添加
                    showErrorMsg("不能为空内容");
                    return;
                }
                String dangerId = PatrolUtil.uuid();
                PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents danger = new PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents();
                danger.setFacilityCheckRecordId(itemContent.getFacilityCheckItemId());
                danger.setItemId(target.getId() + "");
                danger.setItemName(target.getName());
                danger.setCheckType(itemContent.getCheckType());
                danger.setCheckContentId(itemContent.getId() + "");
                danger.setCheckContent(itemContent.getCheckContent());
                danger.setHasError(1);
                danger.setContentDesc(text);
                danger.setDangerId(dangerId);
                danger.setMeterReadingType(itemContent.getMeterReadingType());
                danger.setMeterContent("");
                danger.setMeterLowerLimit(itemContent.getMeterLowerLimit());
                danger.setMeterUpperLimit(itemContent.getMeterUpperLimit());
                List<AttachmentLocal> attachmentLocalList = patrolDangerImageListRecyclerAdapter.getData();
                for (AttachmentLocal attach : attachmentLocalList) {
                    attach.setDangerId(dangerId);
                }
                danger.setAttachmentLocalList(attachmentLocalList);
                // 添加到预览
                patrolDangerDeviceListRecyclerAdapter.addData(danger);
                // 重置输入框 和 添加的图片集合
                viewEditAdd.setText("");
                patrolDangerImageListRecyclerAdapter.setNewData(null);
                // 数据有改变
                dangerUpdate(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OPEN_PHOTO_VIDEO && resultCode == RESULT_OK) {
            String path = data.getStringExtra(TakePhotoVideoHelper.RESULT_DATA);
            File file = new File(path);
            Date date = new Date();
            AttachmentLocal attachmentLocal = new AttachmentLocal(
                    null,
                    file.getName(),
                    path,
                    PatrolUtil.getFileType(path),
                    file.length(),
                    PatrolUtil.formatFileSize(file.length()),
                    date.getTime(),
                    PatrolConstant.DATE_TIME_FORMAT.format(date),
                    PatrolLibrary.getInstance().getPatrolUser().getUserName()
            );
            patrolDangerImageListRecyclerAdapter.addData(attachmentLocal);
        }
    }


    @Override
    protected void setNavigationOnClickListener() {
        String text = viewEditAdd.getText().toString().trim().replaceAll(" ", "");
        if (!TextUtils.isEmpty(text) || !patrolDangerImageListRecyclerAdapter.getData().isEmpty()) {
            showErrorMsg("您还有未添加的隐患");
            return;
        } else if (isDataChanged) {
            Intent intent = new Intent();
            intent.putExtra(CONTENT, itemContent);
            List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> list = new ArrayList<>();
            list.addAll(patrolDangerDeviceListRecyclerAdapter.getData());
            if (list.isEmpty()) list.add(generateNormalDanger());
            intent.putExtra(DANGER_LIST, (Serializable) list);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        setNavigationOnClickListener();
    }

    @Override
    public void dangerUpdate(boolean isChange) {
        isDataChanged = isChange;
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
     * 从record中获取当前的danger记录 list
     *
     * @param contentId
     * @return
     */
    private List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> getExitsDangerList(int contentId) {
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> dangerList = new ArrayList<>();
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> list = deviceRecord.getContents();
        for (int i = 0; i < list.size(); i++) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents dangerRecord = list.get(i);
            if (dangerRecord.getCheckContentId().equals(contentId + "") && dangerRecord.getHasError() == 1) {
                dangerList.add(dangerRecord);
            }
        }
        return dangerList;
    }


    /**
     * 生成一个空白的danger
     *
     * @return
     */
    private PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents generateNormalDanger() {
        PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = new PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents();
        content.setFacilityCheckRecordId(0);
        content.setItemId(target.getId() + "");
        content.setItemName(target.getName());
        content.setCheckType(itemContent.getCheckType());
        content.setCheckContentId(itemContent.getId() + "");
        content.setCheckContent(itemContent.getCheckContent());
        content.setHasError(0);
        content.setContentDesc(""); // 自己填写的
        content.setDangerId(PatrolUtil.uuid());
        content.setMeterReadingType(itemContent.getMeterReadingType());
        content.setMeterContent("");// 自己填写的
        content.setMeterLowerLimit(itemContent.getMeterLowerLimit());
        content.setMeterUpperLimit(itemContent.getMeterUpperLimit());
        // 还有一个附件
        content.setAttachmentLocalList(new ArrayList<AttachmentLocal>());
        return content;
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
