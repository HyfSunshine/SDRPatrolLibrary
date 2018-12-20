package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.danger.PatrolDangerHandleType;

import java.util.List;

/**
 * Created by HyFun on 2018/12/20.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolDangerCheckHandleContract {
    interface View extends AbstractView {
        void loadDangerCheckHandleTypeListSuccess(List<PatrolDangerHandleType> typeList);

        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View> {
        void getDangerCheckHandleTypeList();
    }
}
