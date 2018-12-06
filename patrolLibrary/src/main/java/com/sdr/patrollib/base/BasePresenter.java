package com.sdr.patrollib.base;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by HyFun on 2018/10/12.
 * Email:775183940@qq.com
 */

public class BasePresenter<V extends AbstractView> implements AbstractPresenter<V> {

    protected V view;
    private CompositeDisposable compositeDisposable;

    public int pageNo = 1;
    public final int pageSize = 20;


    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void addSubscription(Disposable... disposables) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.addAll(disposables);
    }
}
