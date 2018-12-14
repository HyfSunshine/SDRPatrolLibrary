package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.project_history.PatrolHistoryInfoProject;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolHistoryInfoProjectContract {
    interface View extends AbstractView {
        void loadHistoryProjectInfoSuccess(PatrolHistoryInfoProject patrolHistoryInfoProject);
        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View> {
        void getHistoryProjectInfo(String id);
    }
}
