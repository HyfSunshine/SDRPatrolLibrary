package com.sdr.patrollib.data.danger;

/**
 * 维修养护：处理步骤枚举定义
 * Created by wan on 2018/7/16.
 */
public enum Maintenance_DefectProcessingStepEnum {

    工程检查,

    /**
     * 当前状态需要：检查处理
     */
    检查处理,

    检查审核,

    任务分发,

    维修养护,

    成果审核,

    隐患已处理;
}
