package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.project.PatrolProjectRecord;
import com.sdr.patrollib.support.data.AttachmentLocal;

import java.util.List;

/**
 * Created by HyFun on 2018/12/12.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolTargetProjectContract {
    interface View extends AbstractView {
        void showUploadAttachFaileDialog(List<AttachmentLocal> attachmentLocals);

        void uploadProjectRecordSuccess();
    }

    interface Presenter extends AbstractPresenter<View> {
        void postAttachment(List<AttachmentLocal> attachmentLocals, PatrolProjectRecord patrolProjectRecord);

        void postProjectRecord(PatrolProjectRecord patrolProjectRecord);
    }
}
