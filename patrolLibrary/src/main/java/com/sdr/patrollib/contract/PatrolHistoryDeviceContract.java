package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.device_history.PatrolHistoryDevice;

import java.util.List;

/**
 * Created by HyFun on 2018/12/13.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolHistoryDeviceContract {
    interface View extends AbstractView {
        void loadHistoryDeviceListSuccess(List<PatrolHistoryDevice> patrolHistoryDeviceList);

        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View> {
        void getHistoryDeviceList(long startTime, long endTime);
    }
}
