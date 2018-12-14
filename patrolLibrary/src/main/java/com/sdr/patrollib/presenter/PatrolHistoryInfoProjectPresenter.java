package com.sdr.patrollib.presenter;

import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolHistoryInfoProjectContract;
import com.sdr.patrollib.data.project_history.PatrolHistoryInfoProject;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryInfoProjectPresenter extends BasePresenter<PatrolHistoryInfoProjectContract.View> implements PatrolHistoryInfoProjectContract.Presenter {
    @Override
    public void getHistoryProjectInfo(String id) {
        addSubscription(
                PatrolHttp.getService().getHistoryProjectInfo(id)
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<PatrolHistoryInfoProject>(view) {

                            @Override
                            public void onNext(PatrolHistoryInfoProject patrolHistoryInfoProject) {
                                view.loadHistoryProjectInfoSuccess(patrolHistoryInfoProject);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
