package com.sdr.patrollib.presenter;

import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolHistoryInfoDeviceContract;
import com.sdr.patrollib.data.device_history.PatrolHistoryInfoDevice;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryInfoDevicePresenter extends BasePresenter<PatrolHistoryInfoDeviceContract.View> implements PatrolHistoryInfoDeviceContract.Presenter {

    @Override
    public void getHistoryInfoDevice(String id) {
        addSubscription(
                PatrolHttp.getService().getHistoryDeviceInfo(id)
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<PatrolHistoryInfoDevice>(view) {

                            @Override
                            public void onNext(PatrolHistoryInfoDevice patrolHistoryInfoDevice) {
                                view.loadHistoryInfoDeviceSuccess(patrolHistoryInfoDevice);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
