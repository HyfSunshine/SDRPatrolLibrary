package com.sdr.patrollib.ui.target_device;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sdr.lib.util.CommonUtil;
import com.sdr.patrollib.R;
import com.sdr.patrollib.base.activity.PatrolBaseSimpleActivity;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.device.PatrolDeviceMeterReadingType;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;

import java.util.ArrayList;
import java.util.List;

public class PatrolMeterReadingActivity extends PatrolBaseSimpleActivity {
    private static final String CONTENT = "CONTENT";
    private static final String DEVICE_RECORD = "DEVICE_RECORD";
    public static final String DANGER = "DANGER";
    // 视图
    private TextView tvCheckContent;
    // 单选
    private RadioGroup radioGroupContainer;
    // 多选
    private LinearLayout llMultiChoiceContainer;
    // 数字
    private LinearLayout llNumContainer;
    private TextView tvNumMin;
    private TextView tvNumMax;
    private EditText edtNumContent;
    // 内容
    private EditText edtMeterContent;

    private Button btnSave;

    private PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents content;
    private PatrolDeviceRecord patrolDeviceRecord;
    private PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents dangerRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_meter_reading);
        initIntent();
        initToolbar();
        initView();
        initData();
        initListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        content = (PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents) intent.getSerializableExtra(CONTENT);
        patrolDeviceRecord = (PatrolDeviceRecord) intent.getSerializableExtra(DEVICE_RECORD);
        if (content == null || patrolDeviceRecord == null)
            finish();
        dangerRecord = getDangerRecordByContentId(content.getId());
        if (dangerRecord == null)
            new NullPointerException();
    }

    private void initToolbar() {
        setTitle(getValueNotNull(content.getCheckContent()));
        setDisplayHomeAsUpEnabled();
    }

    private void initView() {
        tvCheckContent = findViewById(R.id.patrol_meter_read_tv_check_content);
        radioGroupContainer = findViewById(R.id.patrol_meter_read_rg_sigle_choice_container);
        radioGroupContainer.removeAllViews();
        llMultiChoiceContainer = findViewById(R.id.patrol_meter_read_ll_multi_choice_container);
        llMultiChoiceContainer.removeAllViews();
        llNumContainer = findViewById(R.id.patrol_meter_read_ll_num_container);
        tvNumMin = findViewById(R.id.patrol_meter_read_tv_num_min);
        tvNumMax = findViewById(R.id.patrol_meter_read_tv_num_max);
        edtNumContent = findViewById(R.id.patrol_meter_read_edt_num);
        edtMeterContent = findViewById(R.id.patrol_meter_read_edt_content);
        btnSave = findViewById(R.id.patrol_meter_read_btn_save);
    }

    private void initData() {
        // 判断抄表项类型 显示不同的视图
        // 初始化已选择的值
        tvCheckContent.setText(getValueNotNull(content.getCheckContent()));
        int padding = CommonUtil.dip2px(getContext(), 10);

        if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.单选.toString())) {
            // 逗号分隔  显示radiobutton
            String meterOptions = content.getMeterOptions();
            String[] arrays = meterOptions.split(",");
            for (int i = 0; i < arrays.length; i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(arrays[i]);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                radioButton.setPadding(padding, padding, padding, padding);
                radioButton.setChecked(dangerRecord.getMeterContent().equals(arrays[i]));
                radioGroupContainer.addView(radioButton, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            }
            radioGroupContainer.setVisibility(View.VISIBLE);
        } else if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.多选.toString())) {
            // 原始的选项
            String meterOptions = content.getMeterOptions();
            String[] originOptions = meterOptions.split(",");
            // 记录中已经选择的选项
            String[] exitsOptions = dangerRecord.getMeterContent().split(",");
            for (int i = 0; i < originOptions.length; i++) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(originOptions[i]);
                checkBox.setPadding(padding, padding, padding, padding);
                checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                checkBox.setChecked(hasValueInArray(originOptions[i], exitsOptions));
                llMultiChoiceContainer.addView(checkBox, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            llMultiChoiceContainer.setVisibility(View.VISIBLE);
        } else if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.数值.toString())) {
            tvNumMin.setText(content.getMeterLowerLimit() + ""); // 最小值
            tvNumMax.setText(content.getMeterUpperLimit() + ""); // 最大值
            edtNumContent.setText(getValueNotNull(dangerRecord.getMeterContent()));
            llNumContainer.setVisibility(View.VISIBLE);
        } else if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.文字.toString())) {
            edtMeterContent.setText(getValueNotNull(dangerRecord.getMeterContent()));
            edtMeterContent.setVisibility(View.VISIBLE);
        } else {
            showErrorMsg("数据出错，请联系管理员！");
            finish();
        }
    }

    private void initListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 保存方法
                String value = "";
                if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.单选.toString())) {
                    // 判断是否选择
                    value = getRadioGroupValue();
                    if (TextUtils.isEmpty(value)) {
                        dangerRecord.setHasError(0);
                    } else {
                        dangerRecord.setHasError(0);
                    }
                } else if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.多选.toString())) {
                    value = getMutileValue();
                    if (TextUtils.isEmpty(value)) {
                        dangerRecord.setHasError(0);
                    } else {
                        dangerRecord.setHasError(0);
                    }
                } else if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.数值.toString())) {
                    // 判断数值是否符合要求
                    String numStr = edtNumContent.getText().toString().trim();
                    if (TextUtils.isEmpty(numStr)) {
                        dangerRecord.setHasError(0);
                    } else {
                        dangerRecord.setHasError(0);

                        double numValue = 0.0;
                        try {
                            numValue = Double.parseDouble(numStr);
                        } catch (Exception e) {
                        }
                        if (numValue < content.getMeterLowerLimit() || numValue > content.getMeterUpperLimit()) {
                            // 说明超出范围
                            showErrorToast("请输入正确的数值!");
                            return;
                        }
                    }
                    value = numStr;
                } else if (content.getMeterReadingType().equals(PatrolDeviceMeterReadingType.文字.toString())) {
                    value = edtMeterContent.getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        dangerRecord.setHasError(0);
                    } else {
                        dangerRecord.setHasError(0);
                    }
                }
                dangerRecord.setMeterContent(value);
                // 返回
                Intent intent = new Intent();
                intent.putExtra(DANGER, dangerRecord);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        for (int i = 0; i < radioGroupContainer.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroupContainer.getChildAt(i);
            final int index = i;
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRadioButtonFalse(index);
                }
            });
        }
    }

    // ————————————————————————————————私有方法————————————————————————————————

    public String getValueNotNull(Object object) {
        if (object == null) return "";
        return object.toString();
    }

    /**
     * 根据 content id 查询出存储在本地的一条记录
     *
     * @param contentId
     * @return
     */
    private PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents getDangerRecordByContentId(int contentId) {
        List<PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents> contentList = patrolDeviceRecord.getContents();
        for (int i = 0; i < contentList.size(); i++) {
            PatrolDeviceRecord.Patrol_FacilityCheckRecordItemContents content = contentList.get(i);
            if (content.getCheckContentId().equals(contentId + "")) {
                return content;
            }
        }
        return null;
    }

    /**
     * 判断该值是否存在于数组之中  默认没有存在
     *
     * @param value
     * @param arrays
     * @return
     */
    private boolean hasValueInArray(String value, String[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            if (value.equals(arrays[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断单选是否选择了
     *
     * @return
     */
    private String getRadioGroupValue() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < radioGroupContainer.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroupContainer.getChildAt(i);
            if (radioButton.isChecked())
                values.add(radioButton.getText().toString());
        }
        return TextUtils.join(",", values);
    }

    /**
     * 获取多选的值
     *
     * @return
     */
    private String getMutileValue() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < llMultiChoiceContainer.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) llMultiChoiceContainer.getChildAt(i);
            if (checkBox.isChecked())
                values.add(checkBox.getText().toString());
        }
        return TextUtils.join(",", values);
    }

    private void setRadioButtonFalse(int index) {
        radioGroupContainer.clearCheck();
        for (int i = 0; i < radioGroupContainer.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroupContainer.getChildAt(i);
            radioButton.setChecked(index == i);
        }
    }


    /**
     * 开启此activity
     *
     * @param activity
     * @param requestCode
     * @param content
     * @param patrolDeviceRecord
     */
    public static final void start(Activity activity, int requestCode, PatrolDevice.PatrolFacilityCheckItemsVo.Patrol_FacilityCheckItemContents content, PatrolDeviceRecord patrolDeviceRecord) {
        Intent intent = new Intent(activity, PatrolMeterReadingActivity.class);
        intent.putExtra(CONTENT, content);
        intent.putExtra(DEVICE_RECORD, patrolDeviceRecord);
        activity.startActivityForResult(intent, requestCode);
    }
}
