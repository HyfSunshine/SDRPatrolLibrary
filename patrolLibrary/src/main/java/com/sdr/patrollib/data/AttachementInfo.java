package com.sdr.patrollib.data;

import java.io.Serializable;

public class AttachementInfo implements Serializable {
    /**
     * id : 60
     * attachId : AC7A5A474A314EAC9E8E690F776765FB
     * attchType :
     * attchPath : /app_defect_attachment/3590bf694d0e44a8a93ad88aeaf3a362.jpg
     * uploadTime : 1533186248000
     * uploadEmployeeName : 张三丰
     */

    private int id;
    private String attachId;
    private String attchType;
    private String attchPath;
    private long uploadTime;
    private String uploadEmployeeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getAttchType() {
        return attchType;
    }

    public void setAttchType(String attchType) {
        this.attchType = attchType;
    }

    public String getAttchPath() {
        return attchPath;
    }

    public void setAttchPath(String attchPath) {
        this.attchPath = attchPath;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadEmployeeName() {
        return uploadEmployeeName;
    }

    public void setUploadEmployeeName(String uploadEmployeeName) {
        this.uploadEmployeeName = uploadEmployeeName;
    }
}