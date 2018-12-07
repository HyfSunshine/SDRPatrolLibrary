package com.sdr.patrollib.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.fragment.PatrolBaseFragment;
import com.sdr.patrollib.contract.PatrolMainProjectContract;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectItem;
import com.sdr.patrollib.presenter.PatrolMainProjectPresenter;
import com.sdr.patrollib.ui.main.adapter.PatrolMianProjectRecyclerAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolMainProjectFragment extends PatrolBaseFragment<PatrolMainProjectPresenter> implements PatrolMainProjectContract.View,
        BaseQuickAdapter.OnItemClickListener {

    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;

    private PatrolMianProjectRecyclerAdapter patrolMianProjectRecyclerAdapter;

    @Override
    protected PatrolMainProjectPresenter instancePresenter() {
        return new PatrolMainProjectPresenter();
    }

    @Nullable
    @Override
    public View onCreateFragmentView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.patrol_fragment_main, null);
    }

    @Override
    protected void bindButterKnife(View view) {
        swipe = view.findViewById(R.id.patrol_fragment_swipe);
        recyclerView = view.findViewById(R.id.patrol_fragment_recycler_view);
    }

    @Override
    public String getFragmentTitle() {
        return "工程巡检";
    }

    @Override
    protected void onFragmentFirstVisible() {
        patrolMianProjectRecyclerAdapter = new PatrolMianProjectRecyclerAdapter(R.layout.patrol_layout_item_recycler_main);
        patrolMianProjectRecyclerAdapter.setEmptyView(getEmptyView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(patrolMianProjectRecyclerAdapter);

        // 获取数据
        swipe.setRefreshing(true);
        presenter.getProjectList();

        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(this);
        patrolMianProjectRecyclerAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        presenter.getProjectList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PatrolProjectItem item = patrolMianProjectRecyclerAdapter.getItem(position);
        // 获取定位权限
        String[] permiss = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        new RxPermissions(this)
                .request(permiss)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 获取工程详情
                            showLoadingDialog("正在加载...");
                            presenter.getPorjectDetail(item.getId());
                        } else {
                            // 没有权限
                            showErrorToast("申请权限失败");
                        }
                    }
                });
    }

    // ————————————————————————VIEW————————————————————————

    @Override
    public void loadProjectListSuccess(List<PatrolProjectItem> projectList) {
        patrolMianProjectRecyclerAdapter.setNewData(projectList);
    }

    @Override
    public void loadProjectDetailSuccess(PatrolProject patrolProject) {
        // 获取详情成功  显示数量dialog
    }

    @Override
    public void loadDataComplete() {
        swipe.setRefreshing(false);
        hideLoadingDialog();
    }

}
