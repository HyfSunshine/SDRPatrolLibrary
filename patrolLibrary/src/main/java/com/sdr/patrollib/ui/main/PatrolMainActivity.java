package com.sdr.patrollib.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.base.adapter.BaseFragmentPagerAdapter;
import com.sdr.patrollib.base.fragment.PatrolBaseSimpleFragment;
import com.sdr.patrollib.contract.PatrolMainContract;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectRecord;
import com.sdr.patrollib.presenter.PatrolMainPresenter;
import com.sdr.patrollib.support.PatrolNumNotifyDialog;
import com.sdr.patrollib.support.PatrolUnFinishDialog;
import com.sdr.patrollib.support.data.AttachmentLocal;
import com.sdr.patrollib.support.data.PatrolTaskLocal;
import com.sdr.patrollib.ui.target_device.PatrolTargetDeviceActivity;
import com.sdr.patrollib.ui.target_project.PatrolTargetProjectActivity;
import com.sdr.patrollib.util.PatrolRecordUtil;
import com.sdr.patrollib.util.PatrolUtil;
import com.sdr.qrcodelib.QRScanActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PatrolMainActivity extends PatrolBaseActivity<PatrolMainPresenter> implements PatrolMainContract.View {

    private static final int REQUEST_CODE_QRCODE = 100;

    private ImageView viewHeaderImage;
    private View viewHeaderContainer;
    private TextView viewHeaderTvUserName;
    private TextView viewHeaderTvUserPhone;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_main);
        setDisplayHomeAsUpEnabled();
        initView();
        initData();
    }

    @Override
    protected PatrolMainPresenter instancePresenter() {
        return new PatrolMainPresenter();
    }

    private void initView() {
        viewHeaderImage = findViewById(R.id.patrol_main_header_iv_background);
        viewHeaderContainer = findViewById(R.id.patrol_main_header_container);
        viewHeaderTvUserName = findViewById(R.id.patrol_main_header_tv_username);
        viewHeaderTvUserPhone = findViewById(R.id.patrol_main_header_tv_userphone);
        tabLayout = findViewById(R.id.patrol_main_tab);
        viewPager = findViewById(R.id.patrol_main_viewpager);
    }

    private void initData() {
        // toolbar
        {
            toolBar.inflateMenu(R.menu.patrol_menu_main);
            toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.patrol_menu_id_qrcode) {
                        // 打开扫码 需要申请相机权限
                        new RxPermissions(getActivity())
                                .request(Manifest.permission.CAMERA)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        if (aBoolean) {
                                            QRScanActivity.start(getActivity(), REQUEST_CODE_QRCODE);
                                        }
                                    }
                                });
                    }
                    return true;
                }
            });
        }

        // header
        {
            Glide.with(getContext()).load(R.mipmap.patrol_main_header_image).into(viewHeaderImage);
            viewHeaderContainer.setPadding(0, getHeaderBarHeight(), 0, 0);
            viewHeaderTvUserName.setText(PatrolLibrary.getInstance().getPatrolUser().getUserName());
            viewHeaderTvUserPhone.setText(PatrolLibrary.getInstance().getPatrolUser().getPhoneNum());
        }

        // container
        {
            int selectColor = getResources().getColor(R.color.colorPrimary);
            int normalColor = PatrolUtil.getColor60(selectColor);
            tabLayout.setTabTextColors(normalColor, selectColor);

            List<PatrolBaseSimpleFragment> fragmentList = new ArrayList<>();
            fragmentList.add(new PatrolMainProjectFragment());
            fragmentList.add(new PatrolMainDeviceFragment());
            BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
            viewPager.setAdapter(baseFragmentPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

        }

        initUnFinishData();
    }

    private void initUnFinishData() {
        List<PatrolTaskLocal> patrolTaskLocals = new ArrayList<>();
        // 设备巡检
        PatrolDevice device = PatrolRecordUtil.getDevice();
        PatrolDeviceRecord deviceRecord = PatrolRecordUtil.getDeviceRecord();
        if (device != null && deviceRecord != null && !PatrolUtil.isRecordTimeOut(deviceRecord.getPatrolTime())) {
            patrolTaskLocals.add(new PatrolTaskLocal(PatrolTaskLocal.PATROL_TYPE_DEVICE, device, deviceRecord));
        }
        // 工程巡检
        PatrolProject mobile = PatrolRecordUtil.getProject();
        PatrolProjectRecord mobileRecord = PatrolRecordUtil.getProjectRecord();
        if (mobile != null && mobileRecord != null && !PatrolUtil.isRecordTimeOut(mobileRecord.getPatrolStartTime())) {
            patrolTaskLocals.add(new PatrolTaskLocal(PatrolTaskLocal.PATROL_TYPE_MOBILE, mobile, mobileRecord));
        }

        if (patrolTaskLocals.isEmpty()) return;
        PatrolUnFinishDialog patrolUnFinishDialog = new PatrolUnFinishDialog(getContext(), patrolTaskLocals)
                .setOnClickOptionListener(onClickUnfinishDialogOptionListener);
        patrolUnFinishDialog.show();
    }


    @Override
    protected boolean isImageHeader() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String string = bundle.getString(CodeUtils.RESULT_STRING);
            showLoadingDialog("正在获取中...");
            presenter.getDeviceInfoFromQRcode(string);
        }
    }

    // —————————————————————————监听事件—————————————————————————————————
    // 点击未完成任务dialog的   继续  放弃    按钮时的监听事件
    private PatrolUnFinishDialog.OnClickOptionListener onClickUnfinishDialogOptionListener = new PatrolUnFinishDialog.OnClickOptionListener() {
        @Override
        public void onClickGoon(int position, int patrolType, PatrolTaskLocal patrolTaskLocal, BaseQuickAdapter adapter) {
            if (patrolType == PatrolTaskLocal.PATROL_TYPE_DEVICE) {
                // 跳转到巡查界面
                PatrolDevice device = (PatrolDevice) patrolTaskLocal.getOrigin();
                PatrolDeviceRecord record = (PatrolDeviceRecord) patrolTaskLocal.getRecord();
                PatrolTargetDeviceActivity.start(getContext(), device, record);
            } else if (patrolType == PatrolTaskLocal.PATROL_TYPE_MOBILE) {
                PatrolProject mobile = (PatrolProject) patrolTaskLocal.getOrigin();
                PatrolProjectRecord mobileRecord = (PatrolProjectRecord) patrolTaskLocal.getRecord();
                PatrolTargetProjectActivity.start(getContext(), mobile, mobileRecord);
            }
        }

        @Override
        public void onClickDrop(int position, int patrolType, PatrolTaskLocal patrolTaskLocal, BaseQuickAdapter adapter) {
            if (patrolType == PatrolTaskLocal.PATROL_TYPE_DEVICE) {
                // 删除本地的数据
                PatrolRecordUtil.removeDeviceRecord();
            } else if (patrolType == PatrolTaskLocal.PATROL_TYPE_MOBILE) {
                PatrolRecordUtil.removeProjectRecord();
            }
            adapter.remove(position);
        }
    };

    // —————————————————————VIEW———————————————————————
    @Override
    public void loadDeviceInfoFromCodeSuccess(PatrolDevice patrolDevice) {
        String big = patrolDevice.getLargeClass();
        big = TextUtils.isEmpty(big) ? "" : ("[" + big + "]");
        String middle = patrolDevice.getMiddleClass();
        middle = TextUtils.isEmpty(middle) ? "" : ("[" + middle + "]");
        String small = patrolDevice.getSmallClass();
        small = TextUtils.isEmpty(small) ? "" : ("[" + small + "]");

        final String title = big + middle + small + patrolDevice.getName();

        new PatrolNumNotifyDialog(getContext())
                .setPatrolType(PatrolNumNotifyDialog.PATROL_DEVICE)
                .setTitle(title)
                .setTargetNum(patrolDevice.getPatrolFacilityCheckItemsVos().size())
                .setPositiveListener(new PatrolNumNotifyDialog.OnclickTargetNumConfirmListener() {
                    @Override
                    public void onClick(AlertDialog dialog) {
                        // 点击开始按钮生成默认的record  保存至本地缓存  开启target activity
                        PatrolDeviceRecord record = new PatrolDeviceRecord();
                        record.setFacilityCheckId(patrolDevice.getId());
                        record.setFacilityCheckTitle(title);
                        record.setPatrolTime(new Date().getTime());
                        record.setPatrolEmployeeId(PatrolLibrary.getInstance().getPatrolUser().getUserId());
                        record.setPatrolEmployeeName(PatrolLibrary.getInstance().getPatrolUser().getUserName());
                        record.setHasReport(0);
                        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = new ArrayList<>();
                        List<PatrolDevice.PatrolFacilityCheckItemsVo> targetList = patrolDevice.getPatrolFacilityCheckItemsVos();
                        for (int i = 0; i < targetList.size(); i++) {
                            PatrolDevice.PatrolFacilityCheckItemsVo target = targetList.get(i);
                            List<PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents> pointList = target.getPatrolFacilityCheckItemContents();
                            for (int j = 0; j < pointList.size(); j++) {
                                PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents point = pointList.get(j);
                                // 初始化所有的contents
                                PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = new PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents();
                                content.setFacilityCheckRecordId(patrolDevice.getId());
                                content.setItemId(target.getId() + "");
                                content.setItemName(target.getName());
                                content.setCheckType(point.getCheckType());
                                content.setCheckContentId(point.getId() + "");
                                content.setCheckContent(point.getCheckContent());
                                content.setHasError(0);
                                content.setContentDesc(""); // 自己填写的
                                content.setDangerId(PatrolUtil.uuid());
                                content.setMeterReadingType(point.getMeterReadingType());
                                content.setMeterContent("");// 自己填写的
                                content.setMeterLowerLimit(point.getMeterLowerLimit());
                                content.setMeterUpperLimit(point.getMeterUpperLimit());
                                // 还有一个附件
                                content.setAttachmentLocalList(new ArrayList<AttachmentLocal>());
                                contentList.add(content);
                            }
                        }
                        record.setContents(contentList);
                        // 保存至本地
                        PatrolRecordUtil.saveDeviceRecord(patrolDevice, record);
                        // 跳转到巡查界面
                        PatrolTargetDeviceActivity.start(getActivity(), patrolDevice, record);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void loadDataComplete() {
        hideLoadingDialog();
    }
}
