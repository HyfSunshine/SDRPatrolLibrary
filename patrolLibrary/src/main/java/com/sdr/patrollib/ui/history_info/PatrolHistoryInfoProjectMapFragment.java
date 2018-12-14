package com.sdr.patrollib.ui.history_info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.fragment.PatrolBaseSimpleFragment;
import com.sdr.patrollib.data.project_history.PatrolHistoryInfoProject;
import com.sdr.patrollib.util.PatrolUtil;

import java.util.LinkedList;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

@SuppressLint("ValidFragment")
public class PatrolHistoryInfoProjectMapFragment extends PatrolBaseSimpleFragment {
    private PatrolHistoryInfoProject patrolHistoryInfoProject;

    public PatrolHistoryInfoProjectMapFragment(PatrolHistoryInfoProject patrolHistoryInfoProject) {
        this.patrolHistoryInfoProject = patrolHistoryInfoProject;
    }


    private TextureMapView mapView;
    private TextView tvTime, tvLength;

    private AMap aMap;

    @Nullable
    @Override
    public View onCreateFragmentView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.patrol_fragment_history_info_project_map, viewGroup, false);
        initView(view);
        mapView.onCreate(bundle);
        return view;
    }

    @Override
    public String getFragmentTitle() {
        return "巡查路线";
    }

    private void initView(View view) {
        mapView = view.findViewById(R.id.patrol_fragment_history_mobile_detail_mapview);
        tvTime = view.findViewById(R.id.patrol_fragment_history_mobile_detail_tv_time);
        tvLength = view.findViewById(R.id.patrol_fragment_history_mobile_detail_tv_length);
    }

    @Override
    protected void onFragmentFirstVisible() {
        aMap = mapView.getMap();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(false);

        // 时间
        tvTime.setText(secondToStr(patrolHistoryInfoProject.getPatrolTime()));
        // 距离
        tvLength.setText(patrolHistoryInfoProject.getPatrolLength() + "m");

        // 地图划线
        // str 转 坐标
        LinkedList<LatLng> latLngs = PatrolUtil.strToLatlngList(patrolHistoryInfoProject.getPatrolCoors());
        // 绘制起始markers
        if (latLngs.size() == 1) {
            aMap.addMarker(createStartMarker(latLngs.get(0), R.mipmap.patrol_map_marker_start));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLngs.get(0)));
        } else if (latLngs.size() > 1) {
            aMap.addMarker(createStartMarker(latLngs.get(0), R.mipmap.patrol_map_marker_start));
            aMap.addMarker(createStartMarker(latLngs.get(latLngs.size() - 1), R.mipmap.patrol_map_marker_end));
            aMap.addPolyline(PatrolUtil.createMapLine(getContext(), latLngs));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLngs.get(0)));
        }
    }


    // ———————————————————————————生命周期方法———————————————————————————————

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null)
            mapView.onSaveInstanceState(outState);
    }

    // —————————————————————————————私有方法———————————————————————————————

    /**
     * 秒数转换成 时分秒格式
     *
     * @param seconds
     * @return
     */
    private String secondToStr(int seconds) {
        // 处理时间 格式  时分秒
        int startHours = (int) (seconds / 3600);
        int startMinutes = (int) ((seconds % 3600) / 60);
        int startSeconds = (int) ((seconds % 3600) % 60);
        String timeStr = String.format("%02d", startHours) + "时" + String.format("%02d", startMinutes) + "分" + String.format("%02d", startSeconds) + "秒";
        return timeStr;
    }

    /**
     * 创建一个marker
     */

    private MarkerOptions createStartMarker(LatLng latLng, int iconRes) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(iconRes))
                .zIndex(1)
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                .setFlat(false);//设置marker平贴地图效果
        return options;
    }
}
