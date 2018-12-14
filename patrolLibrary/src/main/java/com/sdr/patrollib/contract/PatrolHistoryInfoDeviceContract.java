package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.device_history.PatrolHistoryInfoDevice;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolHistoryInfoDeviceContract {
    interface View extends AbstractView{
        void loadHistoryInfoDeviceSuccess(PatrolHistoryInfoDevice patrolHistoryInfoDevice);
        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View>{
        void getHistoryInfoDevice(String id);
    }
}
