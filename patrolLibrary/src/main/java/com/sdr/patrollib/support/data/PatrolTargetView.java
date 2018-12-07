package com.sdr.patrollib.support.data;

import android.view.View;

/**
 * Created by  HYF on 2018/7/23.
 * Email：775183940@qq.com
 */

public class PatrolTargetView {
    private String title;
    private View mView;

    public PatrolTargetView(String title, View view) {
        this.title = title;
        mView = view;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }
}
