package com.sdr.patrollib.ui.target_device.adapter;

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
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;

import java.util.List;

/**
 * Created by HyFun on 2018/12/10.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolTargetDeviceTargetRecyclerAdapter extends BaseQuickAdapter<PatrolDevice.PatrolFacilityCheckItemsVo, BaseViewHolder> {

    private PatrolDeviceRecord patrolDeviceRecord;

    public PatrolTargetDeviceTargetRecyclerAdapter(int layoutResId, @Nullable List<PatrolDevice.PatrolFacilityCheckItemsVo> data, PatrolDeviceRecord patrolDeviceRecord) {
        super(layoutResId, data);
        this.patrolDeviceRecord = patrolDeviceRecord;
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolDevice.PatrolFacilityCheckItemsVo item) {
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
     * 获取target 的缺陷数量
     *
     * @param targetId
     * @return
     */
    private int getTargetNum(int targetId) {
        int num = 0;
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = patrolDeviceRecord.getContents();
        for (int i = 0; i < contentList.size(); i++) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = contentList.get(i);
            if (content.getHasError() == 1 && content.getItemId().equals(targetId + "")) {
                num++;
            }
        }
        return num;
    }
}
