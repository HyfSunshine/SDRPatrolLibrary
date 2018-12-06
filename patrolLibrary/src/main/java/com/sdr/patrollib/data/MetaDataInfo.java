package com.sdr.patrollib.data;

/**
 * Created by wan on 2018/7/16.
 */

import java.io.Serializable;

/**
 * 元数据信息信息
 * Created by wan on 2018/6/26.
 * 20180720修改user为employee
 */
public class MetaDataInfo implements Serializable {

    private String createEmployeeId;

    private String createEmployeeName;

    private long createDate;

    private String editEmployeeId;

    private String editEmployeeName;

    private long editDate;

    private String deleteEmployeeId;

    private String deleteEmployeeName;

    private long deleteDate;

    private int deleteFlag;

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

    public long getEditDate() {
        return editDate;
    }

    public void setEditDate(long editDate) {
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

    public long getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(long deleteDate) {
        this.deleteDate = deleteDate;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
