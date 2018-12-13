package com.sdr.patrollib.data.project_history;


import com.sdr.patrollib.data.MetaDataInfo;

/**
 * Created by HyFun on 2018/08/03.
 * Email:775183940@qq,com
 */

public class PatrolHistoryProject extends MetaDataInfo {

    /**
     * id : 17
     * mobileCheckId : 9
     * mobileCheckName : 大江东管廊
     * patrolCoors : 31.276529 120.538747,31.276775 120.538867,31.276584 120.538759,31.276795 120.538797,31.276563 120.538718
     * patrolLength : 75
     * patrolStartTime : 1533185165000
     * patrolTaskId :
     * patrolTime : 1086
     * patrolEndTime : 1533186215000
     * patrolSubmitTime : 1533186215000
     * patrolEmployeeId : 1
     * patrolEmployeeName : 张三丰
     * errorCount : 1
     * hasReport : 0
     */

    private int id;
    private int mobileCheckId;
    private String mobileCheckName;
    private String patrolCoors;
    private double patrolLength;
    private long patrolStartTime;
    private String patrolTaskId;
    private int patrolTime;
    private long patrolEndTime;
    private long patrolSubmitTime;
    private String patrolEmployeeId;
    private String patrolEmployeeName;
    private int errorCount;
    private int hasReport;

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
}
