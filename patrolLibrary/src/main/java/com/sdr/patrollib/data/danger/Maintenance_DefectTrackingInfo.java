package com.sdr.patrollib.data.danger;

import java.io.Serializable;

/**
 * 维修养护记录跟踪信息表
 * Created by wan on 2018/7/19.
 */
public class Maintenance_DefectTrackingInfo implements Serializable {
    /**
     * 主键(自增)
     */
    private int id;

    /**
     * 隐患ID，外键，参照：Maintenance_DefectInfo
     */
    private int defectId;

    /**
     * 维养处理步骤：参照：Maintenance_DefectProcessingStepEnum
     * varchar(50)
     */
    private String processingStep;

    /**
     * 处理人id，参照：员工信息表，员工ID
     * varchar(50)
     */
    private String stepEmployeeId;

    /**
     * 处理人姓名，参照：员工信息表
     * varchar(50)
     */
    private String stepEmployeeName;

    /**
     * 处理时间
     */
    private long processingTime;

    /**
     * 处理方式／审核状态
     * 如：处理方式：通过日常维修养护解决
     * 处理方式审核：通过
     * varchar(200)
     */
    private String processingMethod;

    /**
     * 处理意见
     * 如：指导意见：立即组织人员进行维修
     * 审核意见：同意以上方案，注意操作安全
     * varchar(1000)
     */
    private String handlingOpinions;

    /**
     * 维养任务ID（UUID），参照：Maintenance_TaskInfo
     * varchar(50)
     */
    private String maintenanceTaskId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDefectId() {
        return defectId;
    }

    public void setDefectId(int defectId) {
        this.defectId = defectId;
    }

    public String getProcessingStep() {
        return processingStep;
    }

    public void setProcessingStep(String processingStep) {
        this.processingStep = processingStep;
    }

    public String getStepEmployeeId() {
        return stepEmployeeId;
    }

    public void setStepEmployeeId(String stepEmployeeId) {
        this.stepEmployeeId = stepEmployeeId;
    }

    public String getStepEmployeeName() {
        return stepEmployeeName;
    }

    public void setStepEmployeeName(String stepEmployeeName) {
        this.stepEmployeeName = stepEmployeeName;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }

    public void setProcessingMethod(String processingMethod) {
        this.processingMethod = processingMethod;
    }

    public String getHandlingOpinions() {
        return handlingOpinions;
    }

    public void setHandlingOpinions(String handlingOpinions) {
        this.handlingOpinions = handlingOpinions;
    }

    public String getMaintenanceTaskId() {
        return maintenanceTaskId;
    }

    public void setMaintenanceTaskId(String maintenanceTaskId) {
        this.maintenanceTaskId = maintenanceTaskId;
    }
}