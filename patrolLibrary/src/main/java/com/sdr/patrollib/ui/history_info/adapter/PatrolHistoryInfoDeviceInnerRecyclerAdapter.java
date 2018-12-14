package com.sdr.patrollib.ui.history_info.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.lib.util.CommonUtil;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.adapter.PatrolDangerImageNetRecyclerAdapter;
import com.sdr.patrollib.data.device.PatrolDeviceContentType;
import com.sdr.patrollib.data.device.PatrolDeviceMeterReadingType;
import com.sdr.patrollib.data.device_history.PatrolHistoryInfoDevice;

import java.util.List;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolHistoryInfoDeviceInnerRecyclerAdapter extends BaseQuickAdapter<PatrolHistoryInfoDevice.ContentsBean, BaseViewHolder> {

    public PatrolHistoryInfoDeviceInnerRecyclerAdapter(int layoutResId, @Nullable List<PatrolHistoryInfoDevice.ContentsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolHistoryInfoDevice.ContentsBean item) {
        CardView cardView = helper.getView(R.id.patrol_history_detail_device_recycler_item_card_view);
        TextView tvCheckType = helper.getView(R.id.patrol_history_detail_device_recycler_item_tv_check_type);
        TextView tvCheckStatus = helper.getView(R.id.patrol_history_detail_device_recycler_item_tv_status);
        TextView tvCheckContent = helper.getView(R.id.patrol_history_detail_device_recycler_item_tv_check_content);
        LinearLayout llCheckContainer = helper.getView(R.id.patrol_history_detail_device_recycler_item_ll_check_container);
        TextView tvCheckContentDes = helper.getView(R.id.patrol_history_detail_device_recycler_item_tv_content_desc);
        RecyclerView recyclerView = helper.getView(R.id.patrol_history_detail_device_recycler_item_rv_attchment);

        LinearLayout llMeteReadingContainer = helper.getView(R.id.patrol_history_detail_device_recycler_item_ll_metereading_container);


        tvCheckType.setText(item.getCheckType());
        tvCheckContent.setText(item.getCheckContent());
        if (item.getCheckType().equals(PatrolDeviceContentType.检查项.toString())) {
            llCheckContainer.setVisibility(View.VISIBLE);
            llMeteReadingContainer.setVisibility(View.GONE);
            // 正常  、  异常
            tvCheckStatus.setText(item.getHasError() > 0 ? "异常" : "正常");
            tvCheckStatus.setTextColor(item.getHasError() > 0 ? mContext.getResources().getColor(R.color.patrol_content_danger_text) : mContext.getResources().getColor(R.color.patrol_content_normal_text));
            cardView.setCardBackgroundColor(item.getHasError() > 0 ? mContext.getResources().getColor(R.color.patrol_content_danger_bg) : mContext.getResources().getColor(R.color.patrol_content_normal_bg));
            tvCheckContentDes.setText(item.getContentDesc());
            // 图片
            PatrolDangerImageNetRecyclerAdapter patrolDangerImageNetRecyclerAdapter = PatrolDangerImageNetRecyclerAdapter.setAdapter(recyclerView);
            patrolDangerImageNetRecyclerAdapter.setNewData(item.getAttachementInfos());
        } else if (item.getCheckType().equals(PatrolDeviceContentType.抄表项.toString())) {
            llCheckContainer.setVisibility(View.GONE);
            llMeteReadingContainer.setVisibility(View.VISIBLE);
            tvCheckStatus.setText(item.getMeterReadingType());

            generateMeteReadingView(item.getMeterReadingType(), item.getMeterContent(), llMeteReadingContainer);
        }
    }

    private void generateMeteReadingView(String type, String content, LinearLayout linearLayout) {
        linearLayout.removeAllViews();
        int padding = CommonUtil.dip2px(mContext, 10);
        if (type.equals(PatrolDeviceMeterReadingType.单选.toString())) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(content);
            radioButton.setClickable(false);
            radioButton.setChecked(true);
            radioButton.setTextColor(Color.parseColor("#737373"));
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            radioButton.setPadding(padding, padding, padding, padding);
            linearLayout.addView(radioButton);
        } else if (type.equals(PatrolDeviceMeterReadingType.多选.toString())) {
            String[] splits = content.split(",");
            for (int i = 0; i < splits.length; i++) {
                CheckBox checkBox = new CheckBox(mContext);
                checkBox.setText(splits[i]);
                checkBox.setChecked(true);
                checkBox.setClickable(false);
                checkBox.setTextColor(Color.parseColor("#737373"));
                checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                checkBox.setPadding(padding, padding, padding, padding);
                linearLayout.addView(checkBox);
            }
        } else if (type.equals(PatrolDeviceMeterReadingType.数值.toString())) {
            TextView textView = new TextView(mContext);
            textView.setText(content);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setPadding(padding, padding, padding, padding);
            linearLayout.addView(textView);
        } else if (type.equals(PatrolDeviceMeterReadingType.文字.toString())) {
            TextView textView = new TextView(mContext);
            textView.setText(content);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setPadding(padding, padding, padding, padding);
            linearLayout.addView(textView);
        }
    }
}
