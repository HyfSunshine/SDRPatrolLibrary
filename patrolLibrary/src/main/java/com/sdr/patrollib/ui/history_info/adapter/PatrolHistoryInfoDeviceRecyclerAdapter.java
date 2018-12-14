package com.sdr.patrollib.ui.history_info.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.support.data.PatrolHistoryInfoTransformer;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryInfoDeviceRecyclerAdapter extends BaseQuickAdapter<PatrolHistoryInfoTransformer,BaseViewHolder> {

    public PatrolHistoryInfoDeviceRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolHistoryInfoTransformer item) {
        TextView tvTarget = helper.getView(R.id.patrol_history_detail_mobile_recycler_item_tv_target);
        RecyclerView recyclerView = helper.getView(R.id.patrol_history_detail_mobile_recycler_item_tv_recycler_view);
        tvTarget.setText(item.getTarget());

        PatrolHistoryInfoDeviceInnerRecyclerAdapter adapter = new PatrolHistoryInfoDeviceInnerRecyclerAdapter(R.layout.patrol_layout_item_recycler_history_info_device_inner, item.getDangerList());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }
}
