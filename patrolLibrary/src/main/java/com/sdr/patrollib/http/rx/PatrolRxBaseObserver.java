package com.sdr.patrollib.http.rx;

import com.orhanobut.logger.Logger;
import com.sdr.lib.http.HttpClient;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.lib.rx.CommonException;
import com.sdr.lib.rx.HandleException;

import io.reactivex.observers.ResourceObserver;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public abstract class PatrolRxBaseObserver<T> extends ResourceObserver<T> {
    private AbstractView view;

    public PatrolRxBaseObserver(AbstractView view) {
        this.view = view;
    }

    @Override
    public void onError(Throwable e) {
        PatrolRxUtils.handleException(new HandleException(e) {
            @Override
            public void commonException(CommonException e) {
                view.showErrorToast(e.getMessage());
            }
        });
        Logger.t(HttpClient.TAG).e(e, e.getMessage());
        onComplete();
    }
}
