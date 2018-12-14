package com.sdr.patrollib.ui.history_info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdr.patrollib.R;
import com.sdr.patrollib.base.fragment.PatrolBaseSimpleFragment;
import com.sdr.patrollib.data.project_history.PatrolHistoryInfoProject;
import com.sdr.patrollib.support.data.PatrolHistoryInfoTransformer;
import com.sdr.patrollib.ui.history_info.adapter.PatrolHistoryInfoProjectRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyFun on 2018/12/14.
 * Email: 775183940@qq.com
 * Description:
 */

@SuppressLint("ValidFragment")
public class PatrolHistoryInfoProjectSheetFragment extends PatrolBaseSimpleFragment {
    private RecyclerView recyclerView;

    private PatrolHistoryInfoProject patrolHistoryInfoProject;

    public PatrolHistoryInfoProjectSheetFragment(PatrolHistoryInfoProject patrolHistoryInfoProject) {
        this.patrolHistoryInfoProject = patrolHistoryInfoProject;
    }

    @Override
    public String getFragmentTitle() {
        return "巡查单";
    }

    @Nullable
    @Override
    public View onCreateFragmentView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    protected void onFragmentFirstVisible() {
        // 数据分类
        List<PatrolHistoryInfoTransformer> list = new ArrayList<>();
        List<PatrolHistoryInfoProject.ContentsBean> dangerList = patrolHistoryInfoProject.getContents();
        for (int i = 0; i < dangerList.size(); i++) {
            PatrolHistoryInfoProject.ContentsBean bean = dangerList.get(i);
            List exitsList = getExitsList(bean.getPatrolParentId(), list);
            if (exitsList == null) {
                List<PatrolHistoryInfoProject.ContentsBean> l = new ArrayList<>();
                l.add(bean);
                list.add(new PatrolHistoryInfoTransformer(bean.getPatrolParentId() + "", bean.getPatrolParentName(), l));
            } else {
                exitsList.add(bean);
            }
        }


        PatrolHistoryInfoProjectRecyclerAdapter  patrolHistoryInfoProjectRecyclerAdapter= new PatrolHistoryInfoProjectRecyclerAdapter(R.layout.patrol_layout_item_recycler_history_info, list);
        patrolHistoryInfoProjectRecyclerAdapter.setEmptyView(getEmptyView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(patrolHistoryInfoProjectRecyclerAdapter);
    }


    // ———————————————————————PRIVATE———————————————————————
    private List getExitsList(int targetId, List<PatrolHistoryInfoTransformer> list) {
        for (int i = 0; i < list.size(); i++) {
            PatrolHistoryInfoTransformer patrolHistoryInfoTransformer = list.get(i);
            if (patrolHistoryInfoTransformer.getId().equals(targetId + "")) {
                return patrolHistoryInfoTransformer.getDangerList();
            }
        }
        return null;
    }
}
