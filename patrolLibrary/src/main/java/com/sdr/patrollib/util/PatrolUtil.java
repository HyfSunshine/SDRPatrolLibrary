package com.sdr.patrollib.util;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.sdr.patrollib.data.device.PatrolDeviceRecord;

import java.io.File;
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
}
