package com.sdr.patrollib.support.location;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;

import java.util.List;

public interface PatrolMobileMapChangeListener {
    void onLocationChanged(AMapLocation location, String message);

    void onPatrolTimeChange(int patrolTime, String timeStr);

    void onPatrolLengthChange(boolean isNewData, double patrolLength, List<LatLng> latLngList);
}