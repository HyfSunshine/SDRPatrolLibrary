package com.sdr.patrollib.ui.danger_handle.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.danger.Maintenance_DefectTrackingInfo;
import com.sdr.patrollib.support.PatrolConstant;

import java.util.Date;

/**
 * Created by HyFun on 2018/12/20.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerSolveFlowRecyclerAdapter extends BaseQuickAdapter<Maintenance_DefectTrackingInfo, BaseViewHolder> {

    public PatrolDangerSolveFlowRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Maintenance_DefectTrackingInfo item) {
        TextView tvTime = helper.getView(R.id.patrol_handle_danger_flow_recycler_item_tv_time);
        TextView tvUser = helper.getView(R.id.patrol_handle_danger_flow_recycler_item_tv_user);
        TextView tvContent = helper.getView(R.id.patrol_handle_danger_flow_recycler_item_tv_content);

        tvTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(new Date(item.getProcessingTime())));
        tvUser.setText(item.getStepEmployeeName());

        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(item.getProcessingStep())) {
            sb.append("[处理步骤]" + item.getProcessingStep());
            sb.append("\n");
        }

        if (!TextUtils.isEmpty(item.getProcessingMethod())) {
            sb.append("[处理方式]" + item.getProcessingMethod());
            sb.append("\n");
        }
        if (!TextUtils.isEmpty(item.getHandlingOpinions())) {
            sb.append("[处理意见]" + item.getHandlingOpinions());
            sb.append("\n");
        }
        tvContent.setText(sb.toString());
    }
}
