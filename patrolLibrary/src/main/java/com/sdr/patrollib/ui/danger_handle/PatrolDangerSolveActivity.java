package com.sdr.patrollib.ui.danger_handle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseSimpleActivity;
import com.sdr.patrollib.base.adapter.PatrolDangerImageNetRecyclerAdapter;
import com.sdr.patrollib.data.danger.PatrolDanger;

public class PatrolDangerSolveActivity extends PatrolBaseSimpleActivity {
    private static final String PATROL_DANGER = "PATROL_DANGER";

    /**
     * 视图相关
     */
    // 隐患描述
    private TextView viewTextDangerProblem;
    private TextView viewTextDangerDes;
    private RecyclerView viewRecyclerDangerAttatchments;


    private PatrolDanger patrolDanger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_danger_solve);
        setTitle("隐患处理");
        setDisplayHomeAsUpEnabled();
        patrolDanger = (PatrolDanger) getIntent().getSerializableExtra(PATROL_DANGER);
        initView();
        initData();
    }

    private void initView() {
        viewTextDangerProblem = findViewById(R.id.patrol_danger_solve_tv_problem);
        viewTextDangerDes = findViewById(R.id.patrol_danger_solve_tv_description);
        viewRecyclerDangerAttatchments = findViewById(R.id.patrol_danger_solve_rv_image_list);

    }

    private void initData() {
        // 隐患描述
        viewTextDangerProblem.setText(patrolDanger.getSubject().replaceAll(",", "-") + "");
        String contentDes = patrolDanger.getContentDesc();
        viewTextDangerDes.setVisibility(TextUtils.isEmpty(contentDes) ? View.GONE : View.VISIBLE);
        viewTextDangerDes.setText(contentDes + "");
        PatrolDangerImageNetRecyclerAdapter patrolDangerImageNetRecyclerAdapter = PatrolDangerImageNetRecyclerAdapter.setAdapter(viewRecyclerDangerAttatchments);
        patrolDangerImageNetRecyclerAdapter.setNewData(patrolDanger.getList());
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


}
