package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.danger.PatrolDanger;

import java.util.List;

/**
 * Created by HyFun on 2018/12/17.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolDangerListContract {
    interface View extends AbstractView {
        void loadDangerListSuccess(List<PatrolDanger> dangerList);

        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View> {
        void getDangerList(int pageNum);
    }
}
