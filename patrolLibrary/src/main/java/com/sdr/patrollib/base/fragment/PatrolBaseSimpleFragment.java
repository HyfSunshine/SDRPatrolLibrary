package com.sdr.patrollib.base.fragment;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.sdr.lib.base.BaseFragment;
import com.sdr.lib.mvp.AbstractView;
import com.sdr.lib.ui.dialog.SDRLoadingDialog;
import com.sdr.lib.util.ToastTopUtil;
import com.sdr.lib.util.ToastUtil;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public abstract class PatrolBaseSimpleFragment extends BaseFragment implements AbstractView {
    @Override
    protected Drawable onHeaderBarDrawable() {
        return PatrolLibrary.getInstance().getHeaderBarDrawable();
    }

    @Override
    protected int onHeaderBarToolbarRes() {
        return 0;
    }

    /**
     * 获取空数据的
     *
     * @return
     */
    protected View getEmptyView() {
        return getLayoutInflater().inflate(R.layout.sdr_layout_public_empty_view, null);
    }

    @Override
    public String getFragmentTitle() {
        return "";
    }
    //—————————————————————ABS VIEW—————————————————————————

    private SDRLoadingDialog progressDialog;

    @Override
    public void showLoadingDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new SDRLoadingDialog.Builder(getContext())
                    .content(msg)
                    .build();
        }
        progressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showSuccessMsg(String s) {
        ToastTopUtil.showCorrectTopToast(s);
    }

    @Override
    public void showErrorMsg(String s) {
        ToastTopUtil.showErrorTopToast(s);
    }

    @Override
    public void showNormalMsg(String s) {
        ToastTopUtil.showNormalTopToast(s);
    }

    @Override
    public void showSuccessToast(String s) {
        ToastUtil.showCorrectMsg(s);
    }

    @Override
    public void showErrorToast(String s) {
        ToastUtil.showErrorMsg(s);
    }

    @Override
    public void showNormalToast(String s) {
        ToastUtil.showNormalMsg(s);
    }
}
