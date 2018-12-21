package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.danger.Maintenance_DefectTrackingInfo;

import java.util.List;

/**
 * Created by HyFun on 2018/12/20.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolDangerSolveContract {
    interface View extends AbstractView {
        void loadDangerFlowListSuccess(List<Maintenance_DefectTrackingInfo> flowList);

        void handlerDangerSuccess();

        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View> {
        void getDangerFlowList(String id);

        void handleDanger(Maintenance_DefectTrackingInfo trackingInfo);
    }
}
