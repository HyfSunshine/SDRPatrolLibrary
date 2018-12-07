package com.sdr.patrollib.support.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/19.
 */

public class AttachmentLocal implements Serializable {
    public static final int NOT_UPLOADED = 0; // 未上传
    public static final int UPLOADED = 1; // 已上传
    public static final int NO_FILE = 2; // 文件不存在

    private String dangerId;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
    private String fileSizeStr;
    private long takeTime;
    private String takeTimeStr;
    private String userName;
    private int status = NOT_UPLOADED;

    public AttachmentLocal(String dangerId, String fileName, String filePath, String fileType, long fileSize, String fileSizeStr, long takeTime, String takeTimeStr, String userName) {
        this.dangerId = dangerId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileSizeStr = fileSizeStr;
        this.takeTime = takeTime;
        this.takeTimeStr = takeTimeStr;
        this.userName = userName;
        this.status = NOT_UPLOADED;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDangerId() {
        return dangerId;
    }

    public void setDangerId(String dangerId) {
        this.dangerId = dangerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    public long getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(long takeTime) {
        this.takeTime = takeTime;
    }

    public String getTakeTimeStr() {
        return takeTimeStr;
    }

    public void setTakeTimeStr(String takeTimeStr) {
        this.takeTimeStr = takeTimeStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
