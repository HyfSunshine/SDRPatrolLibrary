package com.sdr.patrollib.support.data;

import java.util.List;

/**
 * Created by HyFun on 2018/08/06.
 * Email:775183940@qq,com
 */

public class PatrolHistoryInfoTransformer {
    private String id;
    private String target;
    private List dangerList;

    public PatrolHistoryInfoTransformer(String id, String target, List dangerList) {
        this.id = id;
        this.target = target;
        this.dangerList = dangerList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List getDangerList() {
        return dangerList;
    }

    public void setDangerList(List dangerList) {
        this.dangerList = dangerList;
    }
}
