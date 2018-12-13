package com.sdr.patrollib.ui.target_device;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.sdr.patrollib.support.data.AttachmentLocal;
import com.sdr.patrollib.support.data.PatrolTargetView;
import com.sdr.patrollib.ui.target_device.adapter.PatrolTargetDeviceDangerRecyclerAdapter;
import com.sdr.patrollib.ui.target_device.adapter.PatrolTargetDeviceTargetRecyclerAdapter;
import com.sdr.patrollib.util.PatrolRecordUtil;
import com.sdr.patrollib.util.PatrolUtil;

import java.io.File;
import java.util.ArrayList;
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
        initListener();
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

    private void initListener() {
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查抄表项是否全部检查完了
                int unfinishnum = getUnfinishMeterReadNum(patrolDeviceRecord);
                if (unfinishnum > 0) {
                    // 说明未完成
                    showErrorMsg("您还有" + unfinishnum + "个抄表项未检查");
                    return;
                }

                new MaterialDialog.Builder(getContext())
                        .title("提交")
                        .content("是否上传本次巡查记录？")
                        .negativeText("取消")
                        .positiveText("确定")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // 设置问题数量
                                patrolDeviceRecord.setErrorCount(PatrolUtil.getPatrolDeviceDangerCount(patrolDeviceRecord));

                                // 先上传附件 再上传json
                                List<AttachmentLocal> needUploadFileList = new ArrayList<>();
                                List<AttachmentLocal> notExitsFileList = new ArrayList<>();
                                long attatchSize = 0;
                                // 初始化 文件是否存在
                                List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = patrolDeviceRecord.getContents();
                                for (int i = 0; i < contentList.size(); i++) {
                                    PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = contentList.get(i);
                                    List<AttachmentLocal> attachmentLocalList = content.getAttachmentLocalList();
                                    for (int j = 0; j < attachmentLocalList.size(); j++) {
                                        AttachmentLocal attachment = attachmentLocalList.get(j);
                                        File file = new File(attachment.getFilePath());
                                        // 如果文件不存在  将文件设置成不存在的状态
                                        if (!file.exists()) {
                                            attachment.setStatus(AttachmentLocal.NO_FILE);
                                            notExitsFileList.add(attachment);
                                        } else if (attachment.getStatus() == AttachmentLocal.NOT_UPLOADED) {
                                            needUploadFileList.add(attachment);
                                            attatchSize += attachment.getFileSize();
                                        }
                                    }
                                }

                                if (needUploadFileList.isEmpty()) {
                                    presenter.postDeviceRecord(patrolDeviceRecord);
                                } else {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("记录中的附件共" + (needUploadFileList.size() + notExitsFileList.size()) + "个\n");
                                    sb.append("需要上传" + needUploadFileList.size() + "个\n");
                                    sb.append("不存在的附件" + notExitsFileList.size() + "个\n");
                                    sb.append("预计消耗流量" + PatrolUtil.formatFileSize(attatchSize));
                                    new MaterialDialog.Builder(getContext())
                                            .title("上传附件")
                                            .content(sb.toString())
                                            .negativeText("取消")
                                            .positiveText("上传")
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    presenter.postAttatchment(needUploadFileList, patrolDeviceRecord);
                                                }
                                            })
                                            .show();
                                }
                            }
                        })
                        .show();
            }
        });
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
        if (requestCode == REQUEST_CODE_OPEN_ADD_DANGER && resultCode == RESULT_OK) {
            PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents content = (PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents) data.getSerializableExtra(PatrolDeviceAddDangerActivity.CONTENT);
            List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> dangerList = (List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents>) data.getSerializableExtra(PatrolDeviceAddDangerActivity.DANGER_LIST);
            Iterator<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> iterator = patrolDeviceRecord.getContents().iterator();
            while (iterator.hasNext()) {
                PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents next = iterator.next();
                if (next.getCheckContentId().equals(content.getId() + "")) {
                    iterator.remove();
                }
            }
            patrolDeviceRecord.getContents().addAll(dangerList);
            // 保存至本地
            PatrolRecordUtil.saveDeviceRecord(patrolDeviceRecord);
            // 更新adapter
            targetRecyclerAdapter.notifyDataSetChanged();
            dangerRecyclerAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_OPEN_METER_READING && resultCode == RESULT_OK) {
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
                PatrolDeviceAddDangerActivity.start(getActivity(), REQUEST_CODE_OPEN_ADD_DANGER, currentTarget, content, patrolDeviceRecord);
            } else if (content.getCheckType().equals(PatrolDeviceContentType.抄表项.toString())) {
                PatrolMeterReadingActivity.start(getActivity(), REQUEST_CODE_OPEN_METER_READING, content, patrolDeviceRecord);
            } else {
                showErrorMsg("数据出错，请联系管理员！");
            }
        }
    };


    // ——————————————————————PRIVATE——————————————————————————

    /**
     * 获取未完成抄表项的数量
     *
     * @param record
     * @return
     */
    private int getUnfinishMeterReadNum(PatrolDeviceRecord record) {
        int num = 0;
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = record.getContents();
        for (int i = 0; i < contentList.size(); i++) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = contentList.get(i);
            if (content.getCheckType().equals(PatrolDeviceContentType.抄表项.toString()) && TextUtils.isEmpty(content.getMeterContent())) {
                num++;
            }
        }
        return num;
    }

    /**
     * @param context
     * @param patrolDevice
     * @param patrolDeviceRecord
     */
    public static final void start(Context context, PatrolDevice patrolDevice, PatrolDeviceRecord patrolDeviceRecord) {
        Intent intent = new Intent(context, PatrolTargetDeviceActivity.class);
        intent.putExtra(PATROL_DEVICE, patrolDevice);
        intent.putExtra(PATROL_DEVICE_RECORD, patrolDeviceRecord);
        context.startActivity(intent);
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

    @Override
    public void showUploadAttatchFaileDialog(List<AttachmentLocal> attachmentLocals) {
        new MaterialDialog.Builder(getContext())
                .title("附件上传未完成")
                .content("还有" + attachmentLocals.size() + "个附件上传失败，是否继续上传？")
                .cancelable(false)
                .negativeText("跳过")
                .positiveText("继续")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        presenter.postDeviceRecord(patrolDeviceRecord);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        presenter.postAttatchment(attachmentLocals, patrolDeviceRecord);
                    }
                })
                .show();
    }

    @Override
    public void uploadDeviceRecordSuccess() {
        // 上传成功
        showSuccessToast("上传巡查记录成功");
        // 删除本地缓存
        PatrolRecordUtil.removeDeviceRecord();
        finish();
    }

}
