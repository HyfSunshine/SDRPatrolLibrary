package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.project_history.PatrolHistoryProject;

import java.util.List;

/**
 * Created by HyFun on 2018/12/13.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolHistoryProjectContract {
    interface View extends AbstractView {
        void loadHistoryProjcetListSuccess(List<PatrolHistoryProject> patrolHistoryProjectList);

        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View> {
        void getHistoryProject(long startTime, long endTime);
    }
}
