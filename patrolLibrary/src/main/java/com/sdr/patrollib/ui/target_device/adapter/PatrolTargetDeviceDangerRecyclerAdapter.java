package com.sdr.patrollib.ui.target_device.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceContentType;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;

import java.util.List;

/**
 * Created by HyFun on 2018/12/10.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolTargetDeviceDangerRecyclerAdapter extends BaseQuickAdapter<PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents, BaseViewHolder> {
    private PatrolDeviceRecord patrolDeviceRecord;

    public PatrolTargetDeviceDangerRecyclerAdapter(int layoutResId, @Nullable List<PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents> data, PatrolDeviceRecord patrolDeviceRecord) {
        super(layoutResId, data);
        this.patrolDeviceRecord = patrolDeviceRecord;
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents item) {
        CardView cardView = helper.getView(R.id.patrol_content_recycler_list_item_cardview_container);
        TextView tvType = helper.getView(R.id.patrol_content_recycler_list_item_tv_type);
        TextView tvTitle = helper.getView(R.id.patrol_content_recycler_list_item_tv_title);
        TextView tvStatus = helper.getView(R.id.patrol_content_recycler_list_item_tv_status);

        tvTitle.setText(item.getCheckContent());


        if (item.getCheckType().equals(PatrolDeviceContentType.检查项.toString())) {
            // 检查类别
            tvType.setText(item.getCheckType());
            int num = getContentNum(item.getId());
            if (num == 0) {
                // 说明正常
                //cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.patrol_content_normal_bg));
                tvStatus.setText("正常");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.patrol_content_normal_text));
            } else {
                //cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.patrol_content_danger_bg));
                tvStatus.setText(num + "隐患");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.patrol_content_danger_text));
            }
        } else if (item.getCheckType().equals(PatrolDeviceContentType.抄表项.toString())) {
            // 检查类别
            tvType.setText(item.getCheckType() + "-" + item.getMeterReadingType());

            String meterContent = getDangerByContentId(item.getId()).getMeterContent();
            if (TextUtils.isEmpty(meterContent)) {
                tvStatus.setText("未记录");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.patrol_content_danger_text));
            } else {
                tvStatus.setText("已记录");
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.patrol_content_normal_text));
            }
        }
    }


    /**
     * 获取检查内容的数量
     *
     * @param contentId
     * @return
     */
    private int getContentNum(int contentId) {
        int sum = 0;
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = patrolDeviceRecord.getContents();
        for (int i = 0; i < contentList.size(); i++) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = contentList.get(i);
            if (content.getHasError() == 1 && content.getCheckContentId().equals(contentId + "")) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * 通过内容id  找到记录中的danger id
     *
     * @param contentId
     * @return
     */
    private PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents getDangerByContentId(int contentId) {
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = patrolDeviceRecord.getContents();
        for (int i = 0; i < contentList.size(); i++) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = contentList.get(i);
            if (content.getCheckContentId().equals(contentId + ""))
                return content;
        }
        return null;
    }
}
