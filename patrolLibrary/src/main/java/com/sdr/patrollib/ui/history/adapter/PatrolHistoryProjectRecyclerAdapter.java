package com.sdr.patrollib.ui.history.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.project_history.PatrolHistoryProject;
import com.sdr.patrollib.support.PatrolConstant;

import java.util.Date;

/**
 * Created by HyFun on 2018/08/03.
 * Email:775183940@qq,com
 */

public class PatrolHistoryProjectRecyclerAdapter extends BaseQuickAdapter<PatrolHistoryProject, BaseViewHolder> {

    public PatrolHistoryProjectRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolHistoryProject item) {
        TextView tvTitle = helper.getView(R.id.patrol_history_mobile_recycle_item_tv_title);
        TextView tvDanger = helper.getView(R.id.patrol_history_mobile_recycle_item_tv_danger);
        TextView tvStartTime = helper.getView(R.id.patrol_history_mobile_recycle_item_tv_starttime);
        TextView tvEndTime = helper.getView(R.id.patrol_history_mobile_recycle_item_tv_endtime);
        TextView tvUploader = helper.getView(R.id.patrol_history_mobile_recycle_item_tv_uploader);

        tvTitle.setText(item.getMobileCheckName() + "-工程巡检");
        int dangerNum = item.getErrorCount();
        if (dangerNum == 0) {
            // 说明没有隐患
            tvDanger.setText("正常");
            tvDanger.setTextColor(mContext.getResources().getColor(R.color.patrol_color_success));
            tvDanger.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.patrol_shape_radius_5_outline_success_bg));
        } else {
            // 有隐患
            tvDanger.setText("隐患" + dangerNum);
            tvDanger.setTextColor(mContext.getResources().getColor(R.color.patrol_color_error));
            tvDanger.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.patrol_shape_radius_5_outline_error_bg));
        }
        // 开始时间
        tvStartTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(new Date(item.getPatrolStartTime())));
        tvEndTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(new Date(item.getPatrolEndTime())));
        tvUploader.setText("巡检人：" + item.getPatrolEmployeeName());
    }
}
