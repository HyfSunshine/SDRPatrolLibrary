package com.sdr.patrollib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.sdr.lib.util.ToastUtil;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.data.project.PatrolProjectRecord;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolUtil {
    private PatrolUtil() {
    }

    /**
     * 获取60%的颜色值
     *
     * @param color
     * @return
     */
    public static final int getColor60(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        return Color.argb(153, red, green, blue);
    }


    /**
     * 获取大写的uuid
     *
     * @return
     */
    public static final String uuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return uuid;
    }


    /**
     * 获取本地文件的类型
     *
     * @param path
     * @return
     */
    public static final String getFileType(String path) {
        File file = new File(path);
        if (file.isDirectory())
            return "directory";
        String fileName = file.getName().toLowerCase();
        if (fileName.contains(".mp4")) {
            return "mp4";
        } else if (fileName.contains("jpg") || fileName.contains("jpeg") || fileName.contains("png")) {
            return "jpg";
        }
        return fileName;
    }


    /**
     * 字节大小换算
     *
     * @param size
     * @return
     */
    @NonNull
    public static String formatFileSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    /**
     * 检查gps定位是否开启
     *
     * @param activity
     * @return
     */
    public static final boolean checkOpenGPS(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Activity.LOCATION_SERVICE);
        boolean ok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!ok) {
            ToastUtil.showErrorMsg("请打开您的gps定位");
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(intent);
        }
        return ok;
    }


    /**
     * 获取设备巡检记录的缺陷总数
     *
     * @param record
     * @return
     */
    public static final int getPatrolDeviceDangerCount(PatrolDeviceRecord record) {
        int count = 0;
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = record.getContents();
        for (int i = 0; i < contentList.size(); i++) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = contentList.get(i);
            if (content.getHasError() == 1) {
                // 说明有缺陷
                count++;
            }
        }
        return count;
    }

    /**
     * 获取工程巡检隐患数量
     *
     * @param record
     * @return
     */
    public static final int getPatrolMobileDangerCount(PatrolProjectRecord record) {
        int num = 0;
        List<PatrolProjectRecord.Patrol_MobileCheckRecordItemContents> dangerList = record.getItems();
        for (int i = 0; i < dangerList.size(); i++) {
            PatrolProjectRecord.Patrol_MobileCheckRecordItemContents patrol_mobileCheckRecordItemContents = dangerList.get(i);
            if (patrol_mobileCheckRecordItemContents.getHasError() == 1) {
                num++;
            }
        }
        return num;
    }



    /**
     * 坐标集合转换 string
     *
     * @param list
     * @return x1 y1,x2 y2,x3 y3,x4 y4........
     */
    public static final String latlngListToString(List<LatLng> list) {
        if (list == null || list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            LatLng latLng = list.get(i);
            sb.append(latLng.latitude + "");
            sb.append(" ");
            sb.append(latLng.longitude);
            if (i != list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 字符串转换为 坐标集合
     *
     * @param coors x1 y1,x2 y2,x3 y3,x4 y4........
     * @return
     */
    public static final LinkedList<LatLng> strToLatlngList(String coors) {
        LinkedList<LatLng> list = new LinkedList<>();
        if (TextUtils.isEmpty(coors)) return list;
        String[] strings = coors.split(",");
        for (int i = 0; i < strings.length; i++) {
            String[] lat = strings[i].split(" ");
            LatLng latLng = new LatLng(Double.parseDouble(lat[0]), Double.parseDouble(lat[1]));
            list.add(latLng);
        }
        return list;
    }


    /**
     * 画线
     *
     * @param context
     * @param latLngList
     * @return
     */
    public static final PolylineOptions createMapLine(Context context, List<LatLng> latLngList) {
//        PathSmoothTool mpathSmoothTool = new PathSmoothTool();
//        //设置平滑处理的等级
//        mpathSmoothTool.setIntensity(4);
//        List<LatLng> pathoptimizeList = mpathSmoothTool.pathOptimize(latLngList);
        PolylineOptions options = new PolylineOptions();
        options
                .addAll(latLngList)
                .width(10)
                .setUseTexture(true)
                .setDottedLine(false)
                .geodesic(true)
                .zIndex(0)
                .color(Color.parseColor("#FFF2AF06"));
        return options;
    }

}
