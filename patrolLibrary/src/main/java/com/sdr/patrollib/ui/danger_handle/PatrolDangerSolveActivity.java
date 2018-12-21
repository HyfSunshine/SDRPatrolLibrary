package com.sdr.patrollib.ui.danger_handle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseActivity;
import com.sdr.patrollib.base.adapter.PatrolDangerImageNetRecyclerAdapter;
import com.sdr.patrollib.contract.PatrolDangerSolveContract;
import com.sdr.patrollib.data.danger.Maintenance_DefectProcessingStepEnum;
import com.sdr.patrollib.data.danger.Maintenance_DefectTrackingInfo;
import com.sdr.patrollib.data.danger.PatrolDanger;
import com.sdr.patrollib.presenter.PatrolDangerSolvePresenter;
import com.sdr.patrollib.ui.danger_handle.adapter.PatrolDangerSolveFlowRecyclerAdapter;
import com.sdr.patrollib.ui.danger_handle.handle.PatrolDangerCheckAuditActivity;
import com.sdr.patrollib.ui.danger_handle.handle.PatrolDangerCheckHandleActivity;

import java.util.List;

public class PatrolDangerSolveActivity extends PatrolBaseActivity<PatrolDangerSolvePresenter> implements PatrolDangerSolveContract.View {
    private static final String PATROL_DANGER = "PATROL_DANGER";
    public static final String TRACK_INFO = "TRACK_INFO";

    private static final int REQUEST_CODE_OPEN_HANDLE_ACTIVITY = 100;

    /**
     * 视图相关
     */
    // 隐患描述
    private TextView viewTextDangerProblem;
    private TextView viewTextDangerDes;
    private RecyclerView viewRecyclerDangerAttatchments;
    private RecyclerView viewRecyclerFlow;
    private Button viewButtonSubmit;


    private PatrolDanger patrolDanger;
    private PatrolDangerSolveFlowRecyclerAdapter patrolDangerSolveFlowRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_danger_solve);
        setTitle("隐患处理");
        setDisplayHomeAsUpEnabled();
        patrolDanger = (PatrolDanger) getIntent().getSerializableExtra(PATROL_DANGER);
        initView();
        initData();
        initListener();
    }

    @Override
    protected PatrolDangerSolvePresenter instancePresenter() {
        return new PatrolDangerSolvePresenter();
    }

    private void initView() {
        viewTextDangerProblem = findViewById(R.id.patrol_danger_solve_tv_problem);
        viewTextDangerDes = findViewById(R.id.patrol_danger_solve_tv_description);
        viewRecyclerDangerAttatchments = findViewById(R.id.patrol_danger_solve_rv_image_list);
        viewRecyclerFlow = findViewById(R.id.patrol_danger_solve_rv_flow);
        viewButtonSubmit = findViewById(R.id.patrol_danger_solve_btn_submit);
    }

    private void initData() {
        // 隐患描述
        viewTextDangerProblem.setText(patrolDanger.getSubject().replaceAll(",", "-") + "");
        String contentDes = patrolDanger.getContentDesc();
        viewTextDangerDes.setVisibility(TextUtils.isEmpty(contentDes) ? View.GONE : View.VISIBLE);
        viewTextDangerDes.setText(contentDes + "");
        PatrolDangerImageNetRecyclerAdapter patrolDangerImageNetRecyclerAdapter = PatrolDangerImageNetRecyclerAdapter.setAdapter(viewRecyclerDangerAttatchments);
        patrolDangerImageNetRecyclerAdapter.setNewData(patrolDanger.getList());

        // 处理流程
        patrolDangerSolveFlowRecyclerAdapter = new PatrolDangerSolveFlowRecyclerAdapter(R.layout.patrol_layout_item_recycler_solve_danger_flow);
        viewRecyclerFlow.setLayoutManager(new LinearLayoutManager(getContext()));
        viewRecyclerFlow.setNestedScrollingEnabled(false);
        viewRecyclerFlow.setAdapter(patrolDangerSolveFlowRecyclerAdapter);

        // 获取流程数据
        showLoadingView();
        presenter.getDangerFlowList(patrolDanger.getId() + "");

        // 按钮是否可见
        boolean visible = patrolDanger.getProcessStep().equals(Maintenance_DefectProcessingStepEnum.检查处理.toString()) ||
                patrolDanger.getProcessStep().equals(Maintenance_DefectProcessingStepEnum.检查审核.toString());
        viewButtonSubmit.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void initListener() {
        viewButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 根据不同类型 打开不同activity
                if (patrolDanger.getProcessStep().equals(Maintenance_DefectProcessingStepEnum.检查处理.toString())) {
                    PatrolDangerCheckHandleActivity.start(getActivity(), REQUEST_CODE_OPEN_HANDLE_ACTIVITY, patrolDanger);
                } else if (patrolDanger.getProcessStep().equals(Maintenance_DefectProcessingStepEnum.检查审核.toString())) {
                    PatrolDangerCheckAuditActivity.start(getActivity(), REQUEST_CODE_OPEN_HANDLE_ACTIVITY, patrolDanger);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OPEN_HANDLE_ACTIVITY && resultCode == RESULT_OK) {
            Maintenance_DefectTrackingInfo trackingInfo = (Maintenance_DefectTrackingInfo) data.getSerializableExtra(TRACK_INFO);
            // 更新
            showLoadingDialog("正在更新...");
            presenter.handleDanger(trackingInfo);
        }
    }


    // —————————————————————PRIVATE—————————————————————


    /**
     * 开启
     *
     * @param activity
     * @param requestCode
     * @param patrolDanger
     */
    public static final void start(Activity activity, int requestCode, PatrolDanger patrolDanger) {
        Intent intent = new Intent(activity, PatrolDangerSolveActivity.class);
        intent.putExtra(PATROL_DANGER, patrolDanger);
        activity.startActivityForResult(intent, requestCode);
    }


    // ——————————————————————VIEW——————————————————————————

    @Override
    public void loadDangerFlowListSuccess(List<Maintenance_DefectTrackingInfo> flowList) {
        patrolDangerSolveFlowRecyclerAdapter.setNewData(flowList);
    }

    @Override
    public void handlerDangerSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void loadDataComplete() {
        showContentView();
        hideLoadingDialog();
    }
}
