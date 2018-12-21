package com.sdr.patrollib.ui.danger_handle.handle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseSimpleActivity;
import com.sdr.patrollib.data.danger.Maintenance_DefectProcessingStepEnum;
import com.sdr.patrollib.data.danger.Maintenance_DefectTrackingInfo;
import com.sdr.patrollib.data.danger.PatrolDanger;
import com.sdr.patrollib.ui.danger_handle.PatrolDangerSolveActivity;

import java.util.Date;

public class PatrolDangerCheckAuditActivity extends PatrolBaseSimpleActivity {
    private static final String PATROL_DANGER = "PATROL_DANGER";

    private RadioGroup radioGroup;
    private EditText editText;
    private Button buttonConfirm;


    private PatrolDanger patrolDanger;

    private Boolean tongyi = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_danger_check_audit);
        setTitle("检查审核");
        setDisplayHomeAsUpEnabled();
        patrolDanger = (PatrolDanger) getIntent().getSerializableExtra(PATROL_DANGER);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        radioGroup = findViewById(R.id.patrol_danger_solve_check_audit_radio_group);
        editText = findViewById(R.id.patrol_danger_solve_check_audit_edt_content);
        buttonConfirm = findViewById(R.id.patrol_danger_solve_check_audit_btn_confirm);
    }

    private void initData() {

    }

    private void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.patrol_danger_solve_check_audit_rb_tongyi) {
                    tongyi = true;
                } else if (checkedId == R.id.patrol_danger_solve_check_audit_rb_butongyi) {
                    tongyi = false;
                }
            }
        });


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tongyi == null || TextUtils.isEmpty(editText.getText().toString().trim())) {
                    showErrorToast("不能为空");
                    return;
                }
                String handleMethod = tongyi ? "通过" : "不通过";
                String content = "处理类型：" + handleMethod + "\n" + "处理意见：" + editText.getText().toString().trim();
                new MaterialDialog.Builder(getContext())
                        .title("提示")
                        .content(content)
                        .negativeText("取消")
                        .positiveText("确定")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // 生成 7 个
                                Maintenance_DefectTrackingInfo trackingInfo = new Maintenance_DefectTrackingInfo();
                                trackingInfo.setDefectId(patrolDanger.getId());
                                trackingInfo.setStepEmployeeId(PatrolLibrary.getInstance().getPatrolUser().getUserId());
                                trackingInfo.setStepEmployeeName(PatrolLibrary.getInstance().getPatrolUser().getUserName());
                                trackingInfo.setProcessingStep(tongyi ? Maintenance_DefectProcessingStepEnum.检查审核.toString() : Maintenance_DefectProcessingStepEnum.工程检查.toString());
                                trackingInfo.setProcessingMethod(handleMethod);
                                trackingInfo.setHandlingOpinions(editText.getText().toString().trim());
                                trackingInfo.setProcessingTime(new Date().getTime());

                                Intent intent = new Intent();
                                intent.putExtra(PatrolDangerSolveActivity.TRACK_INFO, trackingInfo);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    // ———————————————————————PRIVATE———————————————————————

    /**
     * 开启此activity
     *
     * @param activity
     * @param requestCode
     * @param patrolDanger
     */
    public static final void start(Activity activity, int requestCode, PatrolDanger patrolDanger) {
        Intent intent = new Intent(activity, PatrolDangerCheckAuditActivity.class);
        intent.putExtra(PATROL_DANGER, patrolDanger);
        activity.startActivityForResult(intent, requestCode);
    }
}
