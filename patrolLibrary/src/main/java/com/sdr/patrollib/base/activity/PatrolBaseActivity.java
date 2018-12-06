package com.sdr.patrollib.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sdr.lib.mvp.AbstractPresenter;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public abstract class PatrolBaseActivity<P extends AbstractPresenter> extends PatrolBaseSimpleActivity {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = instancePresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    protected abstract P instancePresenter();
}
