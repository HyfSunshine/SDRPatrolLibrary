package com.sdr.patrollib.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdr.patrollib.R;
import com.sdr.patrollib.base.fragment.PatrolBaseFragment;
import com.sdr.patrollib.contract.PatrolMainDeviceContract;
import com.sdr.patrollib.presenter.PatrolMainDevicePresenter;
import com.sdr.patrollib.ui.main.adapter.PatrolMianProjectRecyclerAdapter;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolMainDeviceFragment extends PatrolBaseFragment<PatrolMainDevicePresenter> implements PatrolMainDeviceContract.View {
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;


    private PatrolMianProjectRecyclerAdapter patrolMianProjectRecyclerAdapter;

    @Override
    protected PatrolMainDevicePresenter instancePresenter() {
        return new PatrolMainDevicePresenter();
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
        return "设备巡检";
    }

    @Override
    protected void onFragmentFirstVisible() {
        // 获取数据
        patrolMianProjectRecyclerAdapter = new PatrolMianProjectRecyclerAdapter(R.layout.patrol_layout_item_recycler_main);
        patrolMianProjectRecyclerAdapter.setEmptyView(getEmptyView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(patrolMianProjectRecyclerAdapter);

        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(false);
            }
        }, 1500);
    }
}
