package com.sdr.patrollib.contract;

import com.sdr.lib.mvp.AbstractPresenter;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectItem;

import java.util.List;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolMainProjectContract {
    interface View extends AbstractView {
        void loadProjectListSuccess(List<PatrolProjectItem> projectList);

        void loadProjectDetailSuccess(PatrolProject patrolProject);

        void loadDataComplete();
    }

    interface Presenter extends AbstractPresenter<View> {
        void getProjectList();

        void getPorjectDetail(int projectId);
    }
}
