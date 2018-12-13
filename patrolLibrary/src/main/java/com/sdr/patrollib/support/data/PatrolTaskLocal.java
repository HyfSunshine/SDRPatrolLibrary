package com.sdr.patrollib.support.data;

/**
 * Created by HYF on 2018/7/21.
 * Email：775183940@qq.com
 */

public class PatrolTaskLocal<O, R> {
    public static final int PATROL_TYPE_DEVICE = 0;
    public static final int PATROL_TYPE_MOBILE = 1;

    private int patrolType;
    private O origin;
    private R record;

    public PatrolTaskLocal(int patrolType, O origin, R record) {
        this.patrolType = patrolType;
        this.origin = origin;
        this.record = record;
    }

    public int getPatrolType() {
        return patrolType;
    }

    public void setPatrolType(int patrolType) {
        this.patrolType = patrolType;
    }

    public O getOrigin() {
        return origin;
    }

    public void setOrigin(O origin) {
        this.origin = origin;
    }

    public R getRecord() {
        return record;
    }

    public void setRecord(R record) {
        this.record = record;
    }
}
