package com.sdr.patrollib.util;

import android.graphics.Color;

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

}
