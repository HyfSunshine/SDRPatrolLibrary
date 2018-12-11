package com.sdr.patrollib.base.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hyf.takephotovideolib.support.TakePhotoVideoHelper;
import com.sdr.lib.util.CommonUtil;
import com.sdr.patrollib.R;
import com.sdr.patrollib.support.data.AttachmentLocal;

import java.io.File;

/**
 * Created by HyFun on 2018/12/10.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerImageListRecyclerAdapter extends BaseQuickAdapter<AttachmentLocal, BaseViewHolder> {
    private boolean isCanDelete;

    public PatrolDangerImageListRecyclerAdapter(int layoutResId, boolean isCanDelete) {
        super(layoutResId);
        this.isCanDelete = isCanDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, AttachmentLocal item) {
        final int position = helper.getLayoutPosition();

        ImageView imageView = helper.getView(R.id.patrol_add_danger_image_recycler_item_iv_image);
        ImageView ivDelete = helper.getView(R.id.patrol_add_danger_image_recycler_item_iv_delete);
        ImageView ivPlayVideo = helper.getView(R.id.patrol_add_danger_image_recycler_item_iv_play_video);
        TextView tvFileSize = helper.getView(R.id.patrol_add_danger_image_recycler_item_tv_file_size);

        Glide.with(mContext)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(10)
                                .centerCrop()
                )
                .load(item.getFilePath())
                .into(imageView);

        ivDelete.setVisibility(isCanDelete ? View.VISIBLE : View.GONE);
        ivPlayVideo.setVisibility("mp4".equals(item.getFileType()) ? View.VISIBLE : View.GONE);
        tvFileSize.setText(item.getFileSizeStr());

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext)
                        .title("提示")
                        .content("确定删除此项附件？")
                        .negativeText("取消")
                        .positiveText("删除")
                        .positiveColor(Color.RED)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                File file = new File(item.getFilePath());
                                if (file.exists()) file.delete();
                                remove(position);
                            }
                        })
                        .show();
            }
        });

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("jpg".equals(item.getFileType())) {
                    CommonUtil.viewImage(mContext, true, item.getFilePath());
                } else if ("mp4".equals(item.getFileType())) {
                    TakePhotoVideoHelper.startPlayVideo(mContext, item.getFilePath());
                }
            }
        });
    }


    /**
     *  设置adapter
     * @param recyclerView
     * @param isCanDelete
     * @return
     */
    public static final PatrolDangerImageListRecyclerAdapter setAdapter(RecyclerView recyclerView, boolean isCanDelete) {
        PatrolDangerImageListRecyclerAdapter patrolDangerImageListRecyclerAdapter = new PatrolDangerImageListRecyclerAdapter(R.layout.patrol_layout_item_recycler_danger_image, isCanDelete);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(patrolDangerImageListRecyclerAdapter);
        return patrolDangerImageListRecyclerAdapter;
    }
}
