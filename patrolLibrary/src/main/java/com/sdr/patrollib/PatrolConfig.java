package com.sdr.patrollib;

/**
 * Created by HyFun on 2018/12/11.
 * Email: 775183940@qq.com
 * Description: 巡检的配置项
 */

public class PatrolConfig {
    private int maxVideoDuration = 15000;
    private int maxMediaCount = 9;
    private int minDistance = 10;
    private int maxDangerCount = 1;


    public int getMaxVideoDuration() {
        return maxVideoDuration;
    }

    public void setMaxVideoDuration(int maxVideoDuration) {
        this.maxVideoDuration = maxVideoDuration;
    }

    public int getMaxMediaCount() {
        return maxMediaCount;
    }

    public void setMaxMediaCount(int maxMediaCount) {
        this.maxMediaCount = maxMediaCount;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    public int getMaxDangerCount() {
        return maxDangerCount;
    }

    public void setMaxDangerCount(int maxDangerCount) {
        this.maxDangerCount = maxDangerCount;
    }
}
