package com.sdr.patrollib.data.project;


import com.sdr.patrollib.data.MetaDataInfo;
import com.sdr.patrollib.support.data.AttachmentLocal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by  HYF on 2018/7/23.
 * Email：775183940@qq.com
 */

public class PatrolProjectRecord extends MetaDataInfo {
    /**
     * 主键(自增)
     */
    private int id;

    /**
     * 外键，参照：主表 Patrol_MobileCheck
     */
    private int mobileCheckId;

    /**
     * 巡检项目（工程）名称
     * varchar(50)
     */
    private String mobileCheckName;

    /**
     * 巡检路线（坐标点组：x,y x1,y1 x2,y2）
     */
    private String patrolCoors;

    /**
     * 巡检长度（单位：米）
     */
    private double patrolLength;

    /**
     * 巡查开始时间
     */
    private long patrolStartTime;

    /**
     * 巡查任务ID，外键，参考：巡查任务计划表
     */
    private String patrolTaskId;

    /**
     * 巡查时长  单位：秒
     */
    private int patrolTime;

    /**
     * 巡检结束时间
     */
    private long patrolEndTime;

    /**
     * 巡检提交时间
     */
    private long patrolSubmitTime;

    /**
     * 巡检人ID, 非userId，参照：员工信息表
     */
    private String patrolEmployeeId;

    /**
     * 巡检人姓名，参照：员工信息表
     * varchar(50)
     */
    private String patrolEmployeeName;

    /**
     * 问题及隐患数量
     */
    private int errorCount;

    /**
     * 是否已上报（默认值：0， 0：未上报， 1：已上报）
     */
    private int hasReport;

    private List<Patrol_MobileCheckRecordItemContents> items;

    public List<Patrol_MobileCheckRecordItemContents> getItems() {
        return items;
    }

    public void setItems(List<Patrol_MobileCheckRecordItemContents> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMobileCheckId() {
        return mobileCheckId;
    }

    public void setMobileCheckId(int mobileCheckId) {
        this.mobileCheckId = mobileCheckId;
    }

    public String getMobileCheckName() {
        return mobileCheckName;
    }

    public void setMobileCheckName(String mobileCheckName) {
        this.mobileCheckName = mobileCheckName;
    }

    public String getPatrolCoors() {
        return patrolCoors;
    }

    public void setPatrolCoors(String patrolCoors) {
        this.patrolCoors = patrolCoors;
    }

    public double getPatrolLength() {
        return patrolLength;
    }

    public void setPatrolLength(double patrolLength) {
        this.patrolLength = patrolLength;
    }

    public long getPatrolStartTime() {
        return patrolStartTime;
    }

    public void setPatrolStartTime(long patrolStartTime) {
        this.patrolStartTime = patrolStartTime;
    }

    public String getPatrolTaskId() {
        return patrolTaskId;
    }

    public void setPatrolTaskId(String patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public int getPatrolTime() {
        return patrolTime;
    }

    public void setPatrolTime(int patrolTime) {
        this.patrolTime = patrolTime;
    }

    public long getPatrolEndTime() {
        return patrolEndTime;
    }

    public void setPatrolEndTime(long patrolEndTime) {
        this.patrolEndTime = patrolEndTime;
    }

    public long getPatrolSubmitTime() {
        return patrolSubmitTime;
    }

    public void setPatrolSubmitTime(long patrolSubmitTime) {
        this.patrolSubmitTime = patrolSubmitTime;
    }

    public String getPatrolEmployeeId() {
        return patrolEmployeeId;
    }

    public void setPatrolEmployeeId(String patrolEmployeeId) {
        this.patrolEmployeeId = patrolEmployeeId;
    }

    public String getPatrolEmployeeName() {
        return patrolEmployeeName;
    }

    public void setPatrolEmployeeName(String patrolEmployeeName) {
        this.patrolEmployeeName = patrolEmployeeName;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getHasReport() {
        return hasReport;
    }

    public void setHasReport(int hasReport) {
        this.hasReport = hasReport;
    }

    public static class Patrol_MobileCheckRecordItemContents implements Serializable {
        /**
         * 主键(自增)
         */
        private int id;

        /**
         * 外键，参照：主表 Patrol_MobileCheck
         */
        private int mobileCheckRecordId;

        /**
         * 父指标项ID
         */
        private int patrolParentId;

        /**
         * 父指标项名称
         */
        private String patrolParentName;

        /**
         * 子指标项ID
         */
        private int patrolIndexId;

        /**
         * 子指标项名称
         */
        private String patrolIndexName;

        //region 检查项巡检结果
        /**
         * 检查结果：是否发现问题及隐患（0：未发现；1：发现）
         */
        private int hasError;

        /**
         * 缺陷UUID，参照：Sys_AttachenentInfo表
         * varchar(255)
         */
        private String dangerId;

        /**
         * 缺陷位置：经度
         */
        private double dangerLng;

        /**
         * 缺陷位置：纬度
         */
        private double dangerLat;

        /**
         * 隐患描述
         * varchar(1000)
         */
        private String dangerDesc;

        private List<AttachmentLocal> mAttachmentLocalList;

        public List<AttachmentLocal> getAttachmentLocalList() {
            return mAttachmentLocalList;
        }

        public void setAttachmentLocalList(List<AttachmentLocal> attachmentLocalList) {
            mAttachmentLocalList = attachmentLocalList;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMobileCheckRecordId() {
            return mobileCheckRecordId;
        }

        public void setMobileCheckRecordId(int mobileCheckRecordId) {
            this.mobileCheckRecordId = mobileCheckRecordId;
        }

        public int getPatrolParentId() {
            return patrolParentId;
        }

        public void setPatrolParentId(int patrolParentId) {
            this.patrolParentId = patrolParentId;
        }

        public String getPatrolParentName() {
            return patrolParentName;
        }

        public void setPatrolParentName(String patrolParentName) {
            this.patrolParentName = patrolParentName;
        }

        public int getPatrolIndexId() {
            return patrolIndexId;
        }

        public void setPatrolIndexId(int patrolIndexId) {
            this.patrolIndexId = patrolIndexId;
        }

        public String getPatrolIndexName() {
            return patrolIndexName;
        }

        public void setPatrolIndexName(String patrolIndexName) {
            this.patrolIndexName = patrolIndexName;
        }

        public int getHasError() {
            return hasError;
        }

        public void setHasError(int hasError) {
            this.hasError = hasError;
        }

        public String getDangerId() {
            return dangerId;
        }

        public void setDangerId(String dangerId) {
            this.dangerId = dangerId;
        }

        public double getDangerLng() {
            return dangerLng;
        }

        public void setDangerLng(double dangerLng) {
            this.dangerLng = dangerLng;
        }

        public double getDangerLat() {
            return dangerLat;
        }

        public void setDangerLat(double dangerLat) {
            this.dangerLat = dangerLat;
        }

        public String getDangerDesc() {
            return dangerDesc;
        }

        public void setDangerDesc(String dangerDesc) {
            this.dangerDesc = dangerDesc;
        }
    }
}
