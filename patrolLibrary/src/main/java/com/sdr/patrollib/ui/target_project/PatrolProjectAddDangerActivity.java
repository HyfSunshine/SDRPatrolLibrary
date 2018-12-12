package com.sdr.patrollib.ui.target_project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectRecord;
import com.sdr.patrollib.support.PatrolACache;
import com.sdr.patrollib.support.PatrolConstant;
import com.sdr.patrollib.support.PatrolDangerUpdateListener;
import com.sdr.patrollib.support.data.AttachmentLocal;
import com.sdr.patrollib.ui.target_project.adapter.PatrolDangerProjectListRecyclerAdapter;
import com.sdr.patrollib.util.PatrolUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by HyFun on 2018/12/12.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolProjectAddDangerActivity extends PatrolBaseSimpleActivity implements PatrolDangerUpdateListener {
    private static final String TARGET = "TARGET";
    public static final String CONTENT = "CONTENT";
    private static final String MOBILE_RECORD = "MOBILE_RECORD";
    public static final String DANGER_LIST = "DANGER_LIST";
    private static final int REQUEST_OPEN_PHOTO_VIDEO = 10;

    /**
     * 视图相关
     */
    private EditText viewEditAdd;
    private RecyclerView viewRecyclerAdd;
    private Button viewButtonSave;
    private RecyclerView viewRecyclerDangerList;
    private View viewAddImage;

    /**
     * 逻辑相关
     */
    private PatrolProject.PatrolMobileCheckItemsVo itemTarget;
    private PatrolProject.PatrolMobileCheckItemsVo.Patrol_MobileCheckItems itemContent;
    private PatrolProjectRecord patrolProjectRecord;

    private boolean isDataChanged = false;

    // adapter
    private PatrolDangerImageListRecyclerAdapter patrolDangerImageListRecyclerAdapter;
    private PatrolDangerProjectListRecyclerAdapter patrolDangerProjectListRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_device_add_danger);
        setTitle("添加工程巡检隐患");
        setDisplayHomeAsUpEnabled();
        initIntent();
        initView();
        initData();
        initListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        itemTarget = (PatrolProject.PatrolMobileCheckItemsVo) intent.getSerializableExtra(TARGET);
        itemContent = (PatrolProject.PatrolMobileCheckItemsVo.Patrol_MobileCheckItems) intent.getSerializableExtra(CONTENT);
        patrolProjectRecord = (PatrolProjectRecord) intent.getSerializableExtra(MOBILE_RECORD);
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
        // 先找出record中的
        List<PatrolProjectRecord.Patrol_MobileCheckRecordItemContents> dangerList = getExitsDangerList();
        patrolDangerProjectListRecyclerAdapter = new PatrolDangerProjectListRecyclerAdapter(R.layout.patrol_layout_item_recycler_danger_list, dangerList, this);
        viewRecyclerDangerList.setLayoutManager(new LinearLayoutManager(getContext()));
        viewRecyclerDangerList.setNestedScrollingEnabled(false);
        viewRecyclerDangerList.setAdapter(patrolDangerProjectListRecyclerAdapter);
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
                if (patrolDangerProjectListRecyclerAdapter.getData().size() >= PatrolLibrary.getInstance().getPatrolConfig().getMaxDangerCount()) {
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
                PatrolProjectRecord.Patrol_MobileCheckRecordItemContents danger = new PatrolProjectRecord.Patrol_MobileCheckRecordItemContents();
                danger.setMobileCheckRecordId(itemContent.getMobileCheckId());
                danger.setPatrolParentId(itemContent.getParentId());
                danger.setPatrolParentName(itemTarget.getName());
                danger.setPatrolIndexId(itemContent.getId());
                danger.setPatrolIndexName(itemContent.getName());
                danger.setHasError(1);
                danger.setDangerId(dangerId);
                // 经纬度在上一个activity的onactivity result中设置  设置最后一个坐标的位置
                danger.setDangerDesc(viewEditAdd.getText().toString() + "");

                // 设置attachment list中的danger id
                List<AttachmentLocal> attachmentLocalList = patrolDangerImageListRecyclerAdapter.getData();
                danger.setAttachmentLocalList(attachmentLocalList);

                for (AttachmentLocal attach : attachmentLocalList) {
                    attach.setDangerId(dangerId);
                }
                // 添加到预览
                patrolDangerProjectListRecyclerAdapter.addData(danger);
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
        String edtDanger = viewEditAdd.getText().toString().trim();
        if (!TextUtils.isEmpty(edtDanger) || !patrolDangerImageListRecyclerAdapter.getData().isEmpty()) {
            showErrorMsg("您还有隐患未添加");
            return;
        } else if (isDataChanged) {
            Intent intent = new Intent();
            intent.putExtra(CONTENT, itemContent);
            List<PatrolProjectRecord.Patrol_MobileCheckRecordItemContents> list = new ArrayList<>();
            list.addAll(patrolDangerProjectListRecyclerAdapter.getData());
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
     * 从记录中找出该内容已经存在的隐患集合
     *
     * @return
     */
    private final List<PatrolProjectRecord.Patrol_MobileCheckRecordItemContents> getExitsDangerList() {
        List<PatrolProjectRecord.Patrol_MobileCheckRecordItemContents> list = new ArrayList<>();
        for (int i = 0; i < patrolProjectRecord.getItems().size(); i++) {
            PatrolProjectRecord.Patrol_MobileCheckRecordItemContents danger = patrolProjectRecord.getItems().get(i);
            if (danger.getPatrolIndexId() == itemContent.getId() && danger.getHasError() == 1) {
                list.add(danger);
            }
        }
        return list;
    }


    /**
     *  生成一个空的danger
     * @return
     */
    private PatrolProjectRecord.Patrol_MobileCheckRecordItemContents generateNormalDanger() {
        PatrolProjectRecord.Patrol_MobileCheckRecordItemContents danger = new PatrolProjectRecord.Patrol_MobileCheckRecordItemContents();
        danger.setMobileCheckRecordId(0);
        danger.setPatrolParentId(itemTarget.getId());
        danger.setPatrolParentName(itemTarget.getName());
        danger.setPatrolIndexId(itemContent.getId());
        danger.setPatrolIndexName(itemContent.getName());
        danger.setHasError(0);
        danger.setDangerId(PatrolUtil.uuid());
        danger.setDangerDesc("");
        danger.setAttachmentLocalList(new ArrayList<AttachmentLocal>());
        return danger;
    }

    /**
     * 开启此activity
     *
     * @param activity
     * @param requestCode
     * @param itemContent
     * @param record
     */
    public static final void start(Activity activity, int requestCode, PatrolProject.PatrolMobileCheckItemsVo itemTarget, PatrolProject.PatrolMobileCheckItemsVo.Patrol_MobileCheckItems itemContent, PatrolProjectRecord record) {
        Intent intent = new Intent(activity, PatrolProjectAddDangerActivity.class);
        intent.putExtra(TARGET, itemTarget);
        intent.putExtra(CONTENT, itemContent);
        intent.putExtra(MOBILE_RECORD, record);
        activity.startActivityForResult(intent, requestCode);
    }
}
