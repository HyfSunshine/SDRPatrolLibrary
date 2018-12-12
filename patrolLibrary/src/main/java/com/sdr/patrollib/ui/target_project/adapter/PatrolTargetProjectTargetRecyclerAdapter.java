package com.sdr.patrollib.ui.target_project.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectRecord;

import java.util.List;

/**
 * Created by HyFun on 2018/12/12.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolTargetProjectTargetRecyclerAdapter extends BaseQuickAdapter<PatrolProject.PatrolMobileCheckItemsVo, BaseViewHolder> {

    private PatrolProjectRecord record;

    public PatrolTargetProjectTargetRecyclerAdapter(int layoutResId, @Nullable List<PatrolProject.PatrolMobileCheckItemsVo> data, PatrolProjectRecord record) {
        super(layoutResId, data);
        this.record = record;
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolProject.PatrolMobileCheckItemsVo item) {
        ImageView imageView = helper.getView(R.id.patrol_target_target_item_iv_icon);
        TextView tvTitle = helper.getView(R.id.patrol_target_target_item_tv_title);
        TextView tvNum = helper.getView(R.id.patrol_target_target_item_tv_num);

        RequestOptions options = new RequestOptions();
        Glide.with(mContext)
                .load(PatrolLibrary.getInstance().getUrl() + item.getImgUri())
                .apply(options)
                .into(imageView);
        tvTitle.setText(item.getName());

        int targetNum = getTargetNum(item.getId());
        tvNum.setVisibility(targetNum == 0 ? View.GONE : View.VISIBLE);
        tvNum.setText(targetNum + "");
    }

    /**
     * 根据target id获取记录中的隐患数量
     *
     * @param targetId
     * @return
     */
    private int getTargetNum(int targetId) {
        int num = 0;
        List<PatrolProjectRecord.Patrol_MobileCheckRecordItemContents> contentList = record.getItems();
        for (int i = 0; i < contentList.size(); i++) {
            PatrolProjectRecord.Patrol_MobileCheckRecordItemContents content = contentList.get(i);
            if (content.getPatrolParentId() == targetId && content.getHasError() == 1) {
                num++;
            }
        }
        return num;
    }
}
