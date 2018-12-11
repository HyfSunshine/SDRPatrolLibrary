package com.sdr.patrollib.ui.target_device.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.adapter.PatrolDangerImageListRecyclerAdapter;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.support.PatrolDangerUpdateListener;
import com.sdr.patrollib.support.data.AttachmentLocal;

import java.io.File;
import java.util.List;

/**
 * Created by HyFun on 2018/12/11.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerDeviceListRecyclerAdapter extends BaseQuickAdapter<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents, BaseViewHolder> {

    private PatrolDangerUpdateListener updateListener;

    public PatrolDangerDeviceListRecyclerAdapter(int layoutResId, @Nullable List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> data, PatrolDangerUpdateListener updateListener) {
        super(layoutResId, data);
        this.updateListener = updateListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents item) {
        final int position = helper.getLayoutPosition();

        TextView tvContent = helper.getView(R.id.patrol_add_danger_content_recycler_item_tv_content);
        RecyclerView rvImages = helper.getView(R.id.patrol_add_danger_content_recycler_item_rv_images);
//        TextView tvTime = helper.getView(R.id.patrol_add_danger_content_recycler_item_tv_time);
        ImageView ivDelete = helper.getView(R.id.patrol_add_danger_content_recycler_item_iv_delete);

        tvContent.setText(item.getContentDesc());

        PatrolDangerImageListRecyclerAdapter patrolDangerImageListRecyclerAdapter = PatrolDangerImageListRecyclerAdapter.setAdapter(rvImages, false);
        patrolDangerImageListRecyclerAdapter.setNewData(item.getAttachmentLocalList());

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext)
                        .title("提示")
                        .content("确定删除此条隐患记录？")
                        .negativeText("取消")
                        .positiveText("删除")
                        .positiveColor(Color.RED)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // 删除该记录的所有文件
                                List<AttachmentLocal> attachmentLocalList = item.getAttachmentLocalList();
                                for (AttachmentLocal attach : attachmentLocalList) {
                                    File file = new File(attach.getFilePath());
                                    if (file.exists()) file.delete();
                                }
                                remove(position);
                                if (updateListener != null)
                                    updateListener.dangerUpdate(true);
                            }
                        })
                        .show();
            }
        });
    }
}
