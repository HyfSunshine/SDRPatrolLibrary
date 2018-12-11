package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.support.data.AttachmentLocal;

import java.util.List;

/**
 * Created by HyFun on 2018/12/07.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolTargetDeviceContract {
    interface View extends AbstractView {
        void showUploadAttatchFaileDialog(List<AttachmentLocal> attachmentLocals);

        void uploadDeviceRecordSuccess();
    }

    interface Presenter extends AbstractPresenter<View> {
        void postAttatchment(List<AttachmentLocal> attachmentLocals, PatrolDeviceRecord patrolDeviceRecord);

        void postDeviceRecord(PatrolDeviceRecord patrolDeviceRecord);
    }
}
