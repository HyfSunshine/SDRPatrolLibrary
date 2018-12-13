package com.sdr.patrollib.support;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectRecord;
import com.sdr.patrollib.support.data.PatrolTaskLocal;
import com.sdr.patrollib.util.PatrolUtil;

import java.util.List;

/**
 * Created by HYF on 2018/7/21.
 * Email：775183940@qq.com
 * <p>
 * 还有未完成任务的dialog
 */

public class PatrolUnFinishDialog {
    public interface OnClickOptionListener {
        void onClickGoon(int position, int patrolType, PatrolTaskLocal patrolTaskLocal, BaseQuickAdapter adapter);

        void onClickDrop(int position, int patrolType, PatrolTaskLocal patrolTaskLocal, BaseQuickAdapter adapter);
    }


    private Context context;
    private List<PatrolTaskLocal> patrolTaskLocalList;

    private MaterialDialog dialog;

    public PatrolUnFinishDialog(Context context, List<PatrolTaskLocal> patrolTaskLocalList) {
        this.context = context;
        this.patrolTaskLocalList = patrolTaskLocalList;
    }

    private OnClickOptionListener onClickOptionListener;

    public PatrolUnFinishDialog setOnClickOptionListener(OnClickOptionListener onClickOptionListener) {
        this.onClickOptionListener = onClickOptionListener;
        return this;
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.patrol_layout_dialog_unfinish_list, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.patrol_dialog_unfinish_recycler_item_rv_patrol_list);
        PatrolDialogUnfinishRecyclerAdapter adapter = new PatrolDialogUnfinishRecyclerAdapter(R.layout.patrol_layout_dialog_unfinish_list_item, patrolTaskLocalList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        dialog = new MaterialDialog.Builder(context)
                .title("您还有未完成的巡查任务")
                .customView(view, false)
                .cancelable(false)
                .positiveText("了解")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private class PatrolDialogUnfinishRecyclerAdapter extends BaseQuickAdapter<PatrolTaskLocal, BaseViewHolder> {

        public PatrolDialogUnfinishRecyclerAdapter(int layoutResId, @Nullable List<PatrolTaskLocal> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final PatrolTaskLocal item) {
            final int position = helper.getLayoutPosition();
            final int patrolType = item.getPatrolType();
            TextView tvDot = helper.getView(R.id.patrol_dialog_unfinish_recycler_item_tv_dot);
            TextView tvTitle = helper.getView(R.id.patrol_dialog_unfinish_recycler_item_tv_title);
            TextView tvTime = helper.getView(R.id.patrol_dialog_unfinish_recycler_item_tv_time);
            TextView tvDangerNum = helper.getView(R.id.patrol_dialog_unfinish_recycler_item_tv_danger_num);
            LinearLayout llGoon = helper.getView(R.id.patrol_dialog_unfinish_recycler_item_ll_goon);
            LinearLayout llCancel = helper.getView(R.id.patrol_dialog_unfinish_recycler_item_ll_cancel);

            tvDot.setVisibility(item.getOrigin() == null ? View.GONE : View.VISIBLE);
            if (patrolType == PatrolTaskLocal.PATROL_TYPE_DEVICE) {
                PatrolDevice device = (PatrolDevice) item.getOrigin();
                PatrolDeviceRecord deviceRecord = (PatrolDeviceRecord) item.getRecord();
                tvTitle.setText(deviceRecord.getFacilityCheckTitle());
                tvTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(deviceRecord.getPatrolTime()));
                tvDangerNum.setText("记录" + PatrolUtil.getPatrolDeviceDangerCount(deviceRecord));
            } else if (patrolType == PatrolTaskLocal.PATROL_TYPE_MOBILE) {
                PatrolProject mobile = (PatrolProject) item.getOrigin();
                PatrolProjectRecord mobileRecord = (PatrolProjectRecord) item.getRecord();
                tvTitle.setText(mobileRecord.getMobileCheckName());
                tvTime.setText(PatrolConstant.DATE_TIME_FORMAT.format(mobileRecord.getPatrolStartTime()));
                tvDangerNum.setText("缺陷" + PatrolUtil.getPatrolMobileDangerCount(mobileRecord));
            }


            llGoon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null)
                        dialog.dismiss();
                    if (onClickOptionListener != null)
                        onClickOptionListener.onClickGoon(position, patrolType, item, PatrolDialogUnfinishRecyclerAdapter.this);
                }
            });

            llCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null)
                        dialog.dismiss();
                    if (onClickOptionListener != null)
                        onClickOptionListener.onClickDrop(position, patrolType, item, PatrolDialogUnfinishRecyclerAdapter.this);
                }
            });
        }
    }
}
