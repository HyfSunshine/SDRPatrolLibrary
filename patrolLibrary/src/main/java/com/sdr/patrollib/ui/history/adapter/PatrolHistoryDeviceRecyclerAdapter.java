package com.sdr.patrollib.ui.history.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.device_history.PatrolHistoryDevice;
import com.sdr.patrollib.support.PatrolConstant;

import java.util.Date;

/**
 * Created by HYF on 2018/8/4.
 * Email：775183940@qq.com
 */

public class PatrolHistoryDeviceRecyclerAdapter extends BaseQuickAdapter<PatrolHistoryDevice, BaseViewHolder> {

    public PatrolHistoryDeviceRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolHistoryDevice item) {
        TextView tvTitle = helper.getView(R.id.patrol_history_device_recycle_item_tv_title);
        TextView tvDanger = helper.getView(R.id.patrol_history_device_recycle_item_tv_danger);
        TextView tvUploader = helper.getView(R.id.patrol_history_device_recycle_item_tv_uploader);
        TextView tvTime = helper.getView(R.id.patrol_history_device_recycle_item_tv_time);

        tvTitle.setText(item.getFacilityCheckTitle());
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
        tvUploader.setText("巡检人：" + item.getPatrolEmployeeName());
        tvTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(new Date(item.getPatrolTime())));
    }
}
