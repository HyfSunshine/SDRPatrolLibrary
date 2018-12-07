package com.sdr.patrollib.support;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdr.lib.util.CommonUtil;
import com.sdr.patrollib.R;

/**
 * Created by  HYF on 2018/7/19.
 * Email：775183940@qq.com
 * <p>
 * 巡检指标数量提示的dialog
 */

public class PatrolNumNotifyDialog {
    public static final int PATROL_MOBILE = 0; // 移动巡检类型
    public static final int PATROL_DEVICE = 1; // 设备巡检类型


    private int patrolType;
    private String title;
    private int targetNum;
    private OnclickTargetNumConfirmListener positiveListener;

    private Context mContext;

    public PatrolNumNotifyDialog(Context context) {
        mContext = context;
    }

    public PatrolNumNotifyDialog setPatrolType(int patrolType) {
        this.patrolType = patrolType;
        return this;
    }

    public PatrolNumNotifyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public PatrolNumNotifyDialog setTargetNum(int targetNum) {
        this.targetNum = targetNum;
        return this;
    }

    public PatrolNumNotifyDialog setPositiveListener(OnclickTargetNumConfirmListener positiveListener) {
        this.positiveListener = positiveListener;
        return this;
    }

    public void show() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.patrol_layout_dialog_target_num_notify, null, false);
        ImageView close = view.findViewById(R.id.patrol_dialog_target_num_iv_close);
        TextView tvTitle = view.findViewById(R.id.patrol_dialog_target_num_tv_title);
        TextView tvType = view.findViewById(R.id.patrol_dialog_target_num_tv_type);
        TextView tvNum = view.findViewById(R.id.patrol_dialog_target_num_tv_target_num);
        Button btnPositive = view.findViewById(R.id.patrol_dialog_target_num_btn_confirm);

        tvTitle.setText(title);
        String typeString = "";
        if (patrolType == PATROL_MOBILE) {
            typeString = "标准化巡查";
        } else if (patrolType == PATROL_DEVICE) {
            typeString = "设备巡查";
        }
        tvType.setText(typeString);
        tvNum.setText(getTargetNumString(targetNum));
        int spaceTB = CommonUtil.dip2px(mContext, 10);
        int spaceLR = CommonUtil.dip2px(mContext, 20);
        final AlertDialog dialog = new AlertDialog.Builder(mContext, R.style.SDR_Theme_Dialog)
                .setCancelable(false)
                .setView(view, spaceLR, spaceTB, spaceLR, spaceTB)
                .show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positiveListener != null)
                    positiveListener.onClick(dialog);
            }
        });
    }


    /**
     * 指标数量文字内容
     *
     * @param num
     * @return
     */
    private SpannableString getTargetNumString(int num) {
        String text = "检查指标" + num + "项";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 4, text.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(2.0f), 4, text.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public interface OnclickTargetNumConfirmListener {
        void onClick(AlertDialog dialog);
    }

}
