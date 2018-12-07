package com.sdr.patrollib.ui.main.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.project.PatrolProjectItem;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolMianProjectRecyclerAdapter extends BaseQuickAdapter<PatrolProjectItem, BaseViewHolder> {
    public PatrolMianProjectRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolProjectItem item) {
        int position = helper.getLayoutPosition();
        TextView tvNo = helper.getView(R.id.patrol_fragment_main_recycler_item_tv_no);
        TextView tvTitle = helper.getView(R.id.patrol_fragment_main_recycler_item_tv_title);
        tvNo.setText((position + 1) + "");
        tvTitle.setText(item.getProjectName() + "");
    }
}
