package com.sdr.patrollib.presenter;

import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolHistoryProjectContract;
import com.sdr.patrollib.data.project_history.PatrolHistoryProject;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

import java.util.List;

/**
 * Created by HyFun on 2018/12/13.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryProjectPresenter extends BasePresenter<PatrolHistoryProjectContract.View> implements PatrolHistoryProjectContract.Presenter {
    @Override
    public void getHistoryProject(long startTime, long endTime) {
        addSubscription(
                PatrolHttp.getService().getHistoryProjectList(startTime, endTime)
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<List<PatrolHistoryProject>>(view) {

                            @Override
                            public void onNext(List<PatrolHistoryProject> patrolHistoryProjectList) {
                                view.loadHistoryProjcetListSuccess(patrolHistoryProjectList);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
