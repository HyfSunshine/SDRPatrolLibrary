package com.sdr.patrollib.base.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hyf.takephotovideolib.support.TakePhotoVideoHelper;
import com.sdr.lib.util.CommonUtil;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.AttachementInfo;

import java.io.File;
import java.util.List;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerImageNetRecyclerAdapter extends BaseQuickAdapter<AttachementInfo, BaseViewHolder> {

    public PatrolDangerImageNetRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttachementInfo item) {
        final int position = helper.getLayoutPosition();

        ImageView imageView = helper.getView(R.id.patrol_add_danger_image_recycler_item_iv_image);
        ImageView ivDelete = helper.getView(R.id.patrol_add_danger_image_recycler_item_iv_delete);
        ImageView ivPlayVideo = helper.getView(R.id.patrol_add_danger_image_recycler_item_iv_play_video);
        TextView tvFileSize = helper.getView(R.id.patrol_add_danger_image_recycler_item_tv_file_size);

        final String fileUrl = PatrolLibrary.getInstance().getUrl() + item.getAttchPath();
        Glide.with(mContext)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(10)
                                .centerCrop()
                )
                .load(fileUrl)
                .into(imageView);
        ivDelete.setVisibility(View.GONE);
        ivPlayVideo.setVisibility(new File(item.getAttchPath()).getName().contains(".mp4") ? View.VISIBLE : View.GONE);
        tvFileSize.setVisibility(View.GONE);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("jpg".equals(getFileType(fileUrl))) {
                    CommonUtil.viewImage(mContext, false, fileUrl);
                } else if ("mp4".equals(getFileType(fileUrl))) {
                    TakePhotoVideoHelper.startPlayVideo(mContext, fileUrl);
                }
            }
        });

    }


    private String getFileType(String url) {
        String type = "";
        File file = new File(url);
        if (file.getName().contains(".jpg") || file.getName().contains(".png") || file.getName().contains(".jpeg")) {
            type = "jpg";
        } else if (file.getName().contains(".mp4")) {
            type = "mp4";
        }
        return type;
    }



    /**
     *  设置adapter
     * @param recyclerView
     * @return
     */
    public static final PatrolDangerImageNetRecyclerAdapter setAdapter(RecyclerView recyclerView) {
        PatrolDangerImageNetRecyclerAdapter patrolDangerImageListRecyclerAdapter = new PatrolDangerImageNetRecyclerAdapter(R.layout.patrol_layout_item_recycler_danger_image);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(patrolDangerImageListRecyclerAdapter);
        return patrolDangerImageListRecyclerAdapter;
    }
}
