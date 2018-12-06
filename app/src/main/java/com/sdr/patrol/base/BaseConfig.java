package com.sdr.patrol.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.sdr.patrol.R;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class BaseConfig {
    public static final int onHeaderBarRes() {
        return R.layout.layout_public_toolbar_white;
    }

    public static final Drawable onHeaderBarDrawable(Context context) {
        return new ColorDrawable(context.getResources().getColor(R.color.colorPrimary));
    }
}
