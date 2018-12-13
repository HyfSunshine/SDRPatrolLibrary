package com.sdr.patrollib.presenter;

import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolHistoryDeviceContract;
import com.sdr.patrollib.data.device_history.PatrolHistoryDevice;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

import java.util.List;

/**
 * Created by HyFun on 2018/12/13.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryDevicePresenter extends BasePresenter<PatrolHistoryDeviceContract.View> implements PatrolHistoryDeviceContract.Presenter {
    @Override
    public void getHistoryDeviceList(long startTime, long endTime) {
        addSubscription(
                PatrolHttp.getService().getHistoryDeviceList(startTime, endTime)
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<List<PatrolHistoryDevice>>(view) {

                            @Override
                            public void onNext(List<PatrolHistoryDevice> patrolHistoryDeviceList) {
                                view.loadHistoryDeviceListSuccess(patrolHistoryDeviceList);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
