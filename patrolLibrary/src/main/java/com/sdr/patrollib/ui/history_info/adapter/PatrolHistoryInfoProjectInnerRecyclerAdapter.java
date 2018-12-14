package com.sdr.patrollib.ui.history_info.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.adapter.PatrolDangerImageNetRecyclerAdapter;
import com.sdr.patrollib.data.project_history.PatrolHistoryInfoProject;

import java.util.List;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryInfoProjectInnerRecyclerAdapter extends BaseQuickAdapter<PatrolHistoryInfoProject.ContentsBean, BaseViewHolder> {

    public PatrolHistoryInfoProjectInnerRecyclerAdapter(int layoutResId, @Nullable List<PatrolHistoryInfoProject.ContentsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolHistoryInfoProject.ContentsBean item) {
        CardView cardView = helper.getView(R.id.patrol_history_detail_list_recycler_item_item_cv_container);
        TextView tvContentName = helper.getView(R.id.patrol_history_detail_list_recycler_item_item_tv_content_name);
        TextView tvNormal = helper.getView(R.id.patrol_history_detail_list_recycler_item_item_tv_normal);
        LinearLayout llDangerView = helper.getView(R.id.patrol_history_detail_list_recycler_item_item_ll_danger_container);
        TextView tvDangerDesc = helper.getView(R.id.patrol_history_detail_list_recycler_item_item_tv_danger_desc);
        RecyclerView recyclerView = helper.getView(R.id.patrol_history_detail_list_recycler_item_item_rv_photo_list);
        // 变换颜色
        tvContentName.setText(item.getPatrolIndexName());
        if (item.getHasError() == 0) {
            // 没有问题
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.patrol_content_normal_bg));
            tvNormal.setVisibility(View.VISIBLE);
            llDangerView.setVisibility(View.GONE);
        } else {
            cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.patrol_content_danger_bg));
            tvNormal.setVisibility(View.GONE);
            llDangerView.setVisibility(View.VISIBLE);
            // 问题描述
            String desc = item.getDangerDesc();
            tvDangerDesc.setVisibility(TextUtils.isEmpty(desc) ? View.GONE : View.VISIBLE);
            tvDangerDesc.setText(desc + "");
            // 图片
            PatrolDangerImageNetRecyclerAdapter patrolDangerImageNetRecyclerAdapter = PatrolDangerImageNetRecyclerAdapter.setAdapter(recyclerView);
            patrolDangerImageNetRecyclerAdapter.setNewData(item.getAttachementInfos());
        }
    }
}
