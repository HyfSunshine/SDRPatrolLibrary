package com.sdr.patrollib.data.project_history;

import com.sdr.patrollib.data.AttachementInfo;

import java.util.List;

/**
 * Created by HyFun on 2018/08/06.
 * Email:775183940@qq,com
 */

public class PatrolHistoryInfoProject {

    /**
     * createEmployeeId : 1
     * createEmployeeName : 张三丰
     * createDate : 1533185165000
     * editEmployeeId :
     * editEmployeeName :
     * editDate : 0
     * deleteEmployeeId :
     * deleteEmployeeName :
     * deleteDate : 0
     * deleteFlag : 0
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
     * contents : [{"id":24,"mobileCheckRecordId":17,"patrolParentId":27,"patrolParentName":"配电启动设备","patrolIndexId":34,"patrolIndexName":"启动器","hasError":1,"dangerId":"AC7A5A474A314EAC9E8E690F776765FB","dangerLng":120.538718,"dangerLat":31.276563,"dangerDesc":"大佬检查内容啦  10项检查内容","attachementInfos":[{"id":60,"attachId":"AC7A5A474A314EAC9E8E690F776765FB","attchType":"","attchPath":"/app_defect_attachment/3590bf694d0e44a8a93ad88aeaf3a362.jpg","uploadTime":1533186248000,"uploadEmployeeName":"张三丰"}]},{"id":15,"mobileCheckRecordId":17,"patrolParentId":27,"patrolParentName":"配电启动设备","patrolIndexId":35,"patrolIndexName":"配电装置","hasError":0,"dangerId":"5639840F502245C2B049C506D86FE8A8","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":16,"mobileCheckRecordId":17,"patrolParentId":27,"patrolParentName":"配电启动设备","patrolIndexId":36,"patrolIndexName":"开关、接头","hasError":0,"dangerId":"5CA9ECEDC51744EE8BE8C4F542C7A9D3","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":17,"mobileCheckRecordId":17,"patrolParentId":28,"patrolParentName":"变压器","patrolIndexId":37,"patrolIndexName":"变压器","hasError":0,"dangerId":"E57D579A5BDB491F99A6F9FBA81C10C1","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":18,"mobileCheckRecordId":17,"patrolParentId":29,"patrolParentName":"电动机","patrolIndexId":38,"patrolIndexName":"电动机","hasError":0,"dangerId":"427634FE9DE949228A22E5035C246679","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":19,"mobileCheckRecordId":17,"patrolParentId":30,"patrolParentName":"水泵","patrolIndexId":39,"patrolIndexName":"水泵室内","hasError":0,"dangerId":"EDEB4CE6305A461F8F61D0C7764182A8","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":20,"mobileCheckRecordId":17,"patrolParentId":32,"patrolParentName":"泵房","patrolIndexId":40,"patrolIndexName":"通气孔","hasError":0,"dangerId":"CB3B059A7BB1497BB54AC4F216C9C80F","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":21,"mobileCheckRecordId":17,"patrolParentId":32,"patrolParentName":"泵房","patrolIndexId":41,"patrolIndexName":"房门","hasError":0,"dangerId":"446914FD3AAD4D789DF14B00FC647307","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":22,"mobileCheckRecordId":17,"patrolParentId":33,"patrolParentName":"其他","patrolIndexId":42,"patrolIndexName":"墙壁","hasError":0,"dangerId":"07FF53B29A69476D942BF7E691D31046","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]},{"id":23,"mobileCheckRecordId":17,"patrolParentId":31,"patrolParentName":"其他附属设施","patrolIndexId":43,"patrolIndexName":"配电启动设备","hasError":0,"dangerId":"2890B5CDD0A943179034E4B52A636698","dangerLng":0,"dangerLat":0,"dangerDesc":"","attachementInfos":[]}]
     */

    private String createEmployeeId;
    private String createEmployeeName;
    private long createDate;
    private String editEmployeeId;
    private String editEmployeeName;
    private int editDate;
    private String deleteEmployeeId;
    private String deleteEmployeeName;
    private int deleteDate;
    private int deleteFlag;
    private int id;
    private int mobileCheckId;
    private String mobileCheckName;
    private String patrolCoors;
    private int patrolLength;
    private long patrolStartTime;
    private String patrolTaskId;
    private int patrolTime;
    private long patrolEndTime;
    private long patrolSubmitTime;
    private String patrolEmployeeId;
    private String patrolEmployeeName;
    private int errorCount;
    private int hasReport;
    private List<ContentsBean> contents;

    public String getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(String createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateEmployeeName() {
        return createEmployeeName;
    }

    public void setCreateEmployeeName(String createEmployeeName) {
        this.createEmployeeName = createEmployeeName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getEditEmployeeId() {
        return editEmployeeId;
    }

    public void setEditEmployeeId(String editEmployeeId) {
        this.editEmployeeId = editEmployeeId;
    }

    public String getEditEmployeeName() {
        return editEmployeeName;
    }

    public void setEditEmployeeName(String editEmployeeName) {
        this.editEmployeeName = editEmployeeName;
    }

    public int getEditDate() {
        return editDate;
    }

    public void setEditDate(int editDate) {
        this.editDate = editDate;
    }

    public String getDeleteEmployeeId() {
        return deleteEmployeeId;
    }

    public void setDeleteEmployeeId(String deleteEmployeeId) {
        this.deleteEmployeeId = deleteEmployeeId;
    }

    public String getDeleteEmployeeName() {
        return deleteEmployeeName;
    }

    public void setDeleteEmployeeName(String deleteEmployeeName) {
        this.deleteEmployeeName = deleteEmployeeName;
    }

    public int getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(int deleteDate) {
        this.deleteDate = deleteDate;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public int getPatrolLength() {
        return patrolLength;
    }

    public void setPatrolLength(int patrolLength) {
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

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class ContentsBean {
        /**
         * id : 24
         * mobileCheckRecordId : 17
         * patrolParentId : 27
         * patrolParentName : 配电启动设备
         * patrolIndexId : 34
         * patrolIndexName : 启动器
         * hasError : 1
         * dangerId : AC7A5A474A314EAC9E8E690F776765FB
         * dangerLng : 120.538718
         * dangerLat : 31.276563
         * dangerDesc : 大佬检查内容啦  10项检查内容
         * attachementInfos : [{"id":60,"attachId":"AC7A5A474A314EAC9E8E690F776765FB","attchType":"","attchPath":"/app_defect_attachment/3590bf694d0e44a8a93ad88aeaf3a362.jpg","uploadTime":1533186248000,"uploadEmployeeName":"张三丰"}]
         */

        private int id;
        private int mobileCheckRecordId;
        private int patrolParentId;
        private String patrolParentName;
        private int patrolIndexId;
        private String patrolIndexName;
        private int hasError;
        private String dangerId;
        private double dangerLng;
        private double dangerLat;
        private String dangerDesc;
        private List<AttachementInfo> attachementInfos;

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

        public List<AttachementInfo> getAttachementInfos() {
            return attachementInfos;
        }

        public void setAttachementInfos(List<AttachementInfo> attachementInfos) {
            this.attachementInfos = attachementInfos;
        }
    }
}
