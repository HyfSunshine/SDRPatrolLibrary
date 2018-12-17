package com.sdr.patrollib.data.danger;

import com.sdr.patrollib.data.AttachementInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by  HYF on 2018/7/25.
 * Email：775183940@qq.com
 */

public class PatrolDanger implements Serializable {
    /**
     * 主键(自增)
     */
    private int id;

    //region 隐患基础信息
    /**
     * 隐患来源，参照：Maintenance_DefectSourceEnum
     * varchar(20)
     */
    private String defectSource;

    /**
     * 来源记录表ID，外键，通过此键可追溯数据来源
     */
    private int sourceRecordId;

    /**
     * 上报时间
     */
    private long reportTime;

    /**
     * 上报人ID，参照：员工信息表
     * varchar(50)
     */
    private String reportEmployeeId;

    /**
     * 上报人姓名，参照：员工信息表
     * varchar(50)
     */
    private String reportEmployeeName;

    /**
     * 隐患名称(组成：场所（具体位置、问题分类等）)
     * varchar(200)
     */
    private String subject;

    /**
     * 隐患描述
     * varchar(1000)
     */
    private String contentDesc;

    /**
     * 缺陷UUID，参照：Sys_AttachenentInfo表
     * varchar(50)
     */
    private String dangerId;
    //endregion

    //region 隐患处理信息

    /**
     * 隐患处理状态，参照：Maintenance_ProcessingStateEnum， 默认：待处理
     */
    private String processingState;

    /**
     * 隐患当前处理步骤，参照：Maintenance_DefectProcessingStepEnum，
     * 默认：检查处理，即：当前状态需要检查处理
     */
    private String processStep;

    private String processingMethod;

    private List<AttachementInfo> list;

    public List<AttachementInfo> getList() {
        return list;
    }

    public void setList(List<AttachementInfo> list) {
        this.list = list;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }

    public void setProcessingMethod(String processingMethod) {
        this.processingMethod = processingMethod;
    }

    //endregion

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefectSource() {
        return defectSource;
    }

    public void setDefectSource(String defectSource) {
        this.defectSource = defectSource;
    }

    public int getSourceRecordId() {
        return sourceRecordId;
    }

    public void setSourceRecordId(int sourceRecordId) {
        this.sourceRecordId = sourceRecordId;
    }

    public long getReportTime() {
        return reportTime;
    }

    public void setReportTime(long reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportEmployeeId() {
        return reportEmployeeId;
    }

    public void setReportEmployeeId(String reportEmployeeId) {
        this.reportEmployeeId = reportEmployeeId;
    }

    public String getReportEmployeeName() {
        return reportEmployeeName;
    }

    public void setReportEmployeeName(String reportEmployeeName) {
        this.reportEmployeeName = reportEmployeeName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getProcessingState() {
        return processingState;
    }

    public void setProcessingState(String processingState) {
        this.processingState = processingState;
    }

    public String getProcessStep() {
        return processStep;
    }

    public void setProcessStep(String processStep) {
        this.processStep = processStep;
    }
}
