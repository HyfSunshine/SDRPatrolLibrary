package com.sdr.patrollib.presenter;

import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolMainContract;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolMainPresenter extends BasePresenter<PatrolMainContract.View> implements PatrolMainContract.Presenter {

    @Override
    public void getDeviceInfoFromQRcode(String code) {
        addSubscription(
                PatrolHttp.getService().getDeviceInfo(PatrolLibrary.getInstance().getPatrolUser().getUserId(), code)
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<PatrolDevice>(view) {

                            @Override
                            public void onNext(PatrolDevice patrolDevice) {
                                view.loadDeviceInfoFromCodeSuccess(patrolDevice);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
