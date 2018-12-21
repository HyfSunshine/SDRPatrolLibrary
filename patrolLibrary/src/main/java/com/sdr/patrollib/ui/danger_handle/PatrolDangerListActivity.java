package com.sdr.patrollib.ui.danger_handle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.contract.PatrolDangerListContract;
import com.sdr.patrollib.data.danger.PatrolDanger;
import com.sdr.patrollib.presenter.PatrolDangerListPresenter;
import com.sdr.patrollib.ui.danger_handle.adapter.PatrolDangerListRecyclerAdapter;

import java.util.List;

public class PatrolDangerListActivity extends PatrolBaseActivity<PatrolDangerListPresenter> implements PatrolDangerListContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    private static final int REQUEST_CODE_OPEN_SOLVE_ACTIVITY = 2;


    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private PatrolDangerListRecyclerAdapter patrolDangerListRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_danger_list);
        initView();
        initData();
    }

    private void initView() {
        setDisplayHomeAsUpEnabled();
        setTitle("隐患列表");
        swipe = findViewById(R.id.patrol_danger_list_swipe);
        recyclerView = findViewById(R.id.patrol_danger_list_recycler);
    }

    private void initData() {
        patrolDangerListRecyclerAdapter = new PatrolDangerListRecyclerAdapter(R.layout.patrol_layout_item_danger_list);
        patrolDangerListRecyclerAdapter.setEmptyView(getEmptyView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(patrolDangerListRecyclerAdapter);

        // 获取数据
        swipe.setRefreshing(true);
        presenter.getDangerList(presenter.pageNo);

        // 监听
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(this);
        patrolDangerListRecyclerAdapter.setOnLoadMoreListener(this, recyclerView);
        patrolDangerListRecyclerAdapter.setOnItemClickListener(this);

    }

    @Override
    protected PatrolDangerListPresenter instancePresenter() {
        return new PatrolDangerListPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OPEN_SOLVE_ACTIVITY && resultCode == RESULT_OK) {
            // 刷新
            swipe.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PatrolDanger item = patrolDangerListRecyclerAdapter.getItem(position);
        PatrolDangerSolveActivity.start(getActivity(), REQUEST_CODE_OPEN_SOLVE_ACTIVITY, item);
    }

    @Override
    public void onRefresh() {
        presenter.pageNo = 1;
        presenter.getDangerList(presenter.pageNo);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getDangerList(presenter.pageNo);
    }

    // ———————————————————————VIEW———————————————————————

    @Override
    public void loadDangerListSuccess(List<PatrolDanger> dangerList) {
        if (presenter.pageNo == 1) {
            patrolDangerListRecyclerAdapter.setNewData(dangerList);
            patrolDangerListRecyclerAdapter.setEnableLoadMore(true);
        } else {
            patrolDangerListRecyclerAdapter.addData(dangerList);
        }

        if (dangerList.size() == presenter.pageSize) {
            presenter.pageNo++;
            patrolDangerListRecyclerAdapter.loadMoreComplete();
        } else {
            patrolDangerListRecyclerAdapter.loadMoreEnd();
        }
    }

    @Override
    public void loadDataComplete() {
        swipe.setRefreshing(false);
    }

}
