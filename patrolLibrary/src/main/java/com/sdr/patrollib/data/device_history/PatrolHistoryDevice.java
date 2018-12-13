package com.sdr.patrollib.data.device_history;

/**
 * Created by HYF on 2018/8/4.
 * Email：775183940@qq.com
 */

public class PatrolHistoryDevice {

    /**
     * id : 9
     * facilityCheckId : 7
     * facilityCheckTitle : [大江东综合管廊][防火分区1][电力舱001]配电
     * patrolTime : 1533112477000
     * patrolEmployeeId :
     * patrolEmployeeName :
     * errorCount : 1
     * hasReport : 0
     */

    private int id;
    private int facilityCheckId;
    private String facilityCheckTitle;
    private long patrolTime;
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
}
