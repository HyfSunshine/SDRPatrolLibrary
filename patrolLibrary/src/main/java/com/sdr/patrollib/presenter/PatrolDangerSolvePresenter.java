package com.sdr.patrollib.presenter;

import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolDangerSolveContract;
import com.sdr.patrollib.data.danger.Maintenance_DefectTrackingInfo;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

import java.util.List;

/**
 * Created by HyFun on 2018/12/20.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerSolvePresenter extends BasePresenter<PatrolDangerSolveContract.View> implements PatrolDangerSolveContract.Presenter {
    @Override
    public void getDangerFlowList(String id) {
        addSubscription(
                PatrolHttp.getService().getDangerFlowList(id)
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<List<Maintenance_DefectTrackingInfo>>(view) {

                            @Override
                            public void onNext(List<Maintenance_DefectTrackingInfo> maintenance_defectTrackingInfos) {
                                view.loadDangerFlowListSuccess(maintenance_defectTrackingInfos);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
