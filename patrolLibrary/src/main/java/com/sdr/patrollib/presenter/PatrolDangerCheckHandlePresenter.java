package com.sdr.patrollib.presenter;

import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolDangerCheckHandleContract;
import com.sdr.patrollib.data.danger.PatrolDangerHandleType;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

import java.util.List;

/**
 * Created by HyFun on 2018/12/20.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerCheckHandlePresenter extends BasePresenter<PatrolDangerCheckHandleContract.View> implements PatrolDangerCheckHandleContract.Presenter {
    @Override
    public void getDangerCheckHandleTypeList() {
        addSubscription(
                PatrolHttp.getService().getDangerCheckHandleTypes()
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<List<PatrolDangerHandleType>>(view) {

                            @Override
                            public void onNext(List<PatrolDangerHandleType> list) {
                                view.loadDangerCheckHandleTypeListSuccess(list);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
