package com.sdr.patrollib.support.upload;

import com.sdr.patrollib.contract.PatrolTargetDeviceContract;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;

/**
 * Created by HyFun on 2018/12/11.
 * Email: 775183940@qq.com
 * Description: 设备巡检上传
 */

public class PatrolDeviceUploadManager {
    private PatrolDeviceRecord patrolDeviceRecord;
    private PatrolTargetDeviceContract.View view;

    public PatrolDeviceUploadManager(PatrolDeviceRecord patrolDeviceRecord, PatrolTargetDeviceContract.View view) {
        this.patrolDeviceRecord = patrolDeviceRecord;
        this.view = view;
    }



}
