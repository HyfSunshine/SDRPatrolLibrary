package com.sdr.patrollib.ui.danger_handle.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.AttachementInfo;
import com.sdr.patrollib.data.danger.Maintenance_DefectProcessingStepEnum;
import com.sdr.patrollib.data.danger.PatrolDanger;
import com.sdr.patrollib.support.PatrolConstant;
import com.sdr.patrollib.util.PatrolUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by HyFun on 2018/12/17.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerListRecyclerAdapter extends BaseQuickAdapter<PatrolDanger, BaseViewHolder> {

    public PatrolDangerListRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolDanger item) {
        TextView tvType = helper.getView(R.id.patrol_danger_list_recycler_item_tv_type);
        TextView tvStatus = helper.getView(R.id.patrol_danger_list_recycler_item_tv_status);
        TextView tvTitle = helper.getView(R.id.patrol_danger_list_recycler_item_tv_title);
        TextView tvContent = helper.getView(R.id.patrol_danger_list_recycler_item_tv_content);
        ImageView imageView = helper.getView(R.id.patrol_danger_list_recycler_item_iv_danger);
        TextView tvUser = helper.getView(R.id.patrol_danger_list_recycler_item_tv_user);
        TextView tvTime = helper.getView(R.id.patrol_danger_list_recycler_item_tv_time);

        tvType.setText(item.getDefectSource());
        tvStatus.setText(item.getProcessStep());
        tvStatus.setTextColor(getStatusColor(item.getProcessStep()));
        tvTitle.setText(item.getSubject().replaceAll(",", "-"));

        String content = item.getContentDesc();
        tvContent.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        tvContent.setText(content + "");

        List<AttachementInfo> list = item.getList();
        imageView.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
        if (!list.isEmpty()) {
            AttachementInfo attachment = item.getList().get(0);
            // 确保是jpg 、MP4文件
            if ("jpg".equals(PatrolUtil.getFileType(attachment.getAttchPath())) || "mp4".equals(attachment.getAttchPath())) {
                final String fileUrl = PatrolLibrary.getInstance().getUrl() + attachment.getAttchPath();
                Glide.with(mContext)
                        .setDefaultRequestOptions(
                                new RequestOptions()
                                        .frame(10)
                                        .centerCrop()
                        )
                        .load(fileUrl)
                        .into(imageView);
            }
        }

        tvUser.setText("上报人：" + item.getReportEmployeeName() + "");
        tvTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(new Date(item.getReportTime())));

    }

    private int getStatusColor(String status) {
        if (status.equals(Maintenance_DefectProcessingStepEnum.工程检查.toString()) || status.equals(Maintenance_DefectProcessingStepEnum.检查处理.toString())) {
            return mContext.getResources().getColor(R.color.patrol_color_error);
        } else if (status.equals(Maintenance_DefectProcessingStepEnum.隐患已处理.toString())) {
            return mContext.getResources().getColor(R.color.patrol_color_gray);
        } else {
            return mContext.getResources().getColor(R.color.patrol_color_orange);
        }
    }
}
