package com.sdr.patrollib.ui.target_project.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectRecord;

import java.util.List;

/**
 * Created by HyFun on 2018/12/12.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolTargetProjectDangerRecyclerAdapter extends BaseQuickAdapter<PatrolProject.PatrolMobileCheckItemsVo.Patrol_MobileCheckItems,BaseViewHolder> {

    private PatrolProjectRecord record;

    public PatrolTargetProjectDangerRecyclerAdapter(int layoutResId, @Nullable List<PatrolProject.PatrolMobileCheckItemsVo.Patrol_MobileCheckItems> data, PatrolProjectRecord record) {
        super(layoutResId, data);
        this.record = record;
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolProject.PatrolMobileCheckItemsVo.Patrol_MobileCheckItems item) {
        CardView cardView = helper.getView(R.id.patrol_content_recycler_list_item_cardview_container);
        TextView tvType = helper.getView(R.id.patrol_content_recycler_list_item_tv_type);
        TextView tvTitle = helper.getView(R.id.patrol_content_recycler_list_item_tv_title);
        TextView tvStatus = helper.getView(R.id.patrol_content_recycler_list_item_tv_status);
        tvType.setText("巡查项");
        tvTitle.setText(item.getName() + "");
        int num = getContentDanger(item.getId());
        if (num == 0){
            // 说明正常
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.patrol_content_normal_bg));
            tvStatus.setText("正常");
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.patrol_content_normal_text));
        }else {
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.patrol_content_danger_bg));
            tvStatus.setText(num + "隐患");
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.patrol_content_danger_text));
        }
    }

    private int getContentDanger(int contentId) {
        int num = 0;
        for (int i = 0; i < record.getItems().size(); i++) {
            PatrolProjectRecord.Patrol_MobileCheckRecordItemContents danger = record.getItems().get(i);
            if (danger.getPatrolIndexId() == contentId && danger.getHasError() == 1) {
                num++;
            }
        }
        return num;
    }
}
