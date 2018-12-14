package com.sdr.patrollib.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;
import com.sdr.lib.base.OnScrollListener;
import com.sdr.lib.base.OnScrollListenerView;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolListenerScrollView extends NestedScrollView implements OnScrollListenerView {

    private OnScrollListener listener;

    public PatrolListenerScrollView(@NonNull Context context) {
        super(context);
    }

    public PatrolListenerScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScrollChange(t);
        }
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.listener = onScrollListener;
    }
}
