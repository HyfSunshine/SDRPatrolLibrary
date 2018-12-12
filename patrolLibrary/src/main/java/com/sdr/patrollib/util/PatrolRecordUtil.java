package com.sdr.patrollib.util;

import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectRecord;
import com.sdr.patrollib.support.PatrolACache;
import com.sdr.patrollib.support.PatrolConstant;

/**
 * Created by HYF on 2018/7/20.
 * Email：775183940@qq.com
 */

public class PatrolRecordUtil {
    // —————————————————————————设备巡检相关———————————————————————————————

    /**
     * 保存设备巡检记录至本地
     *
     * @param deviceRecord
     */
    public static final void saveDeviceRecord(PatrolDeviceRecord deviceRecord) {
        saveDeviceRecord(null, deviceRecord);
    }

    /**
     * 保存设备 和 设备巡检的记录
     *
     * @param patrolDevice
     * @param deviceRecord
     */
    public static final void saveDeviceRecord(PatrolDevice patrolDevice, PatrolDeviceRecord deviceRecord) {
        if (patrolDevice != null)
            PatrolACache.getACache().put(PatrolConstant.PATROL_DEVICE, patrolDevice);
        PatrolACache.getACache().put(PatrolConstant.PATROL_DEVICE_RECORD, deviceRecord);
    }

    /**
     * 移除设备巡检记录
     */
    public static final void removeDeviceRecord() {
        PatrolACache.getACache().remove(PatrolConstant.PATROL_DEVICE);
        PatrolACache.getACache().remove(PatrolConstant.PATROL_DEVICE_RECORD);
    }

    /**
     * 获取设备巡检的数据
     *
     * @return
     */
    public static final PatrolDevice getDevice() {
        return (PatrolDevice) PatrolACache.getACache().getAsObject(PatrolConstant.PATROL_DEVICE);
    }

    /**
     * 获取本地设备巡检记录
     *
     * @return
     */
    public static final PatrolDeviceRecord getDeviceRecord() {
        PatrolDeviceRecord record = (PatrolDeviceRecord) PatrolACache.getACache().getAsObject(PatrolConstant.PATROL_DEVICE_RECORD);
        return record;
    }

    // —————————————————————————移动巡检相关———————————————————————————————


    /**
     * 只保存移动巡检记录数据
     *
     * @param patrolMobileRecord
     */
    public static final void saveProjectRecord(PatrolProjectRecord patrolMobileRecord) {
        saveProjectRecord(null, patrolMobileRecord);
    }

    /**
     * 保存移动巡检 和 移动巡检记录数据
     *
     * @param patrolMobile
     * @param patrolMobileRecord
     */
    public static final void saveProjectRecord(PatrolProject patrolMobile, PatrolProjectRecord patrolMobileRecord) {
        if (patrolMobile != null)
            PatrolACache.getACache().put(PatrolConstant.PATROL_PROJECT, patrolMobile);

        PatrolACache.getACache().put(PatrolConstant.PATROL_PROJECT_RECORD, patrolMobileRecord);
    }

    /**
     * 移除移动巡检数据  和  移动巡检记录数据
     */
    public static final void removeProjectRecord() {
        PatrolACache.getACache().remove(PatrolConstant.PATROL_PROJECT);
        PatrolACache.getACache().remove(PatrolConstant.PATROL_PROJECT_RECORD);
    }

    /**
     * 获取移动巡检数据
     *
     * @return
     */
    public static final PatrolProject getProject() {
        PatrolProject patrolMobile = (PatrolProject) PatrolACache.getACache().getAsObject(PatrolConstant.PATROL_PROJECT);
        return patrolMobile;
    }

    /**
     * 获取移动巡检记录数据
     *
     * @return
     */
    public static final PatrolProjectRecord getProjectRecord() {
        PatrolProjectRecord record = (PatrolProjectRecord) PatrolACache.getACache().getAsObject(PatrolConstant.PATROL_PROJECT_RECORD);
        return record;
    }
}
