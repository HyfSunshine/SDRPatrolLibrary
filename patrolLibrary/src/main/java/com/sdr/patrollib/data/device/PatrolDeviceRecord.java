package com.sdr.patrollib.data.device;

import com.sdr.patrollib.support.data.AttachmentLocal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HYF on 2018/7/20.
 * Email：775183940@qq.com
 */

public class PatrolDeviceRecord implements Serializable {
    /**
     * 主键(自增)
     */
    private int id;

    /**
     * 外键，参考：主表 Patrol_FacilityCheck
     */
    private int facilityCheckId;

    /**
     * 外键，参考：主表 Patrol_FacilityCheck
     */
    private String facilityCheckTitle;

    /**
     * 巡检时间
     */
    private long patrolTime;

    /**
     * 巡检人ID，参照：员工信息表
     */
    private String patrolEmployeeId;

    /**
     * 巡检人姓名，参照：员工信息表
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

    /**
     * 巡检内容
     */
    private List<Patrol_FacilityCheckRecordItemContents> contents;

    public List<Patrol_FacilityCheckRecordItemContents> getContents() {
        return contents;
    }

    public void setContents(List<Patrol_FacilityCheckRecordItemContents> contents) {
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacilityCheckId() {
        return facilityCheckId;
    }

    public void setFacilityCheckId(int facilityCheckId) {
        this.facilityCheckId = facilityCheckId;
    }

    public String getFacilityCheckTitle() {
        return facilityCheckTitle;
    }

    public void setFacilityCheckTitle(String facilityCheckTitle) {
        this.facilityCheckTitle = facilityCheckTitle;
    }

    public long getPatrolTime() {
        return patrolTime;
    }

    public void setPatrolTime(long patrolTime) {
        this.patrolTime = patrolTime;
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

    public static class Patrol_FacilityCheckRecordItemContents implements Serializable {
        /**
         * 主键(自增)
         */
        private int id;

        /**
         * 外键，参考：主表 Patrol_FacilityCheckRecords
         */
        private int facilityCheckRecordId;

        //region 巡检指标项
        /**
         * 巡检指标项ID，外键：参照：Patrol_FacilityCheckItems
         */
        private String itemId;

        /**
         * 巡检指标名称
         */
        private String itemName;
        //endregion

        /**
         * 检查项类别（检查项、抄表项），参照：Patrol_CheckTypeEnum
         */
        private String checkType;

        /**
         * 检查内容ID，外键：参照：Patrol_FacilityCheckItemContents
         */
        private String checkContentId;

        /**
         * 检查内容
         */
        private String checkContent;

        //region 检查项巡检结果
        /**
         * 检查结果：是否发现问题及隐患（0：未发现；1：发现）
         */
        private int hasError;

        /**
         * 抄表项：隐患描述
         * varchar(1000)
         */
        private String contentDesc;

        /**
         * 缺陷UUID，参照：Sys_AttachenentInfo表
         * varchar(255)
         */
        private String dangerId;
        //endregion

        //region 抄表项巡检结果

        /**
         * 抄表项：输入数据类型，如：radio、checkbox、number、String，参照：Patrol_MeterReadingTypeEnum
         */
        private String meterReadingType;

        /**
         * 抄表项内容
         * varchar(500)
         */
        private String meterContent;

        /**
         * 正常值范围下限值
         */
        private double meterLowerLimit;

        /**
         * 正常值范围上限值
         */
        private double meterUpperLimit;
        //endregion
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

        public int getFacilityCheckRecordId() {
            return facilityCheckRecordId;
        }

        public void setFacilityCheckRecordId(int facilityCheckRecordId) {
            this.facilityCheckRecordId = facilityCheckRecordId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getCheckType() {
            return checkType;
        }

        public void setCheckType(String checkType) {
            this.checkType = checkType;
        }

        public String getCheckContentId() {
            return checkContentId;
        }

        public void setCheckContentId(String checkContentId) {
            this.checkContentId = checkContentId;
        }

        public String getCheckContent() {
            return checkContent;
        }

        public void setCheckContent(String checkContent) {
            this.checkContent = checkContent;
        }

        public int getHasError() {
            return hasError;
        }

        public void setHasError(int hasError) {
            this.hasError = hasError;
        }

        public String getContentDesc() {
            return contentDesc;
        }

        public void setContentDesc(String contentDesc) {
            this.contentDesc = contentDesc;
        }

        public String getDangerId() {
            return dangerId;
        }

        public void setDangerId(String dangerId) {
            this.dangerId = dangerId;
        }

        public String getMeterReadingType() {
            return meterReadingType;
        }

        public void setMeterReadingType(String meterReadingType) {
            this.meterReadingType = meterReadingType;
        }

        public String getMeterContent() {
            return meterContent;
        }

        public void setMeterContent(String meterContent) {
            this.meterContent = meterContent;
        }

        public double getMeterLowerLimit() {
            return meterLowerLimit;
        }

        public void setMeterLowerLimit(double meterLowerLimit) {
            this.meterLowerLimit = meterLowerLimit;
        }

        public double getMeterUpperLimit() {
            return meterUpperLimit;
        }

        public void setMeterUpperLimit(double meterUpperLimit) {
            this.meterUpperLimit = meterUpperLimit;
        }
    }
}
