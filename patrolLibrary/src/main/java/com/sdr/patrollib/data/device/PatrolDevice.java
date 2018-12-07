package com.sdr.patrollib.data.device;

import com.sdr.patrollib.data.MetaDataInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HYF on 2018/7/20.
 * Email：775183940@qq.com
 */

public class PatrolDevice extends MetaDataInfo {
    /**
     * 主键(自增)
     */
    private int id;

    /**
     * 大分类（管廊项目中：管廊名称）
     */
    public String largeClass;

    /**
     * 中分类（管廊项目中：防火分区）
     */
    public String middleClass;

    /**
     * 小分类（管廊项目中：舱室）
     */
    public String smallClass;

    /**
     * 巡检设备名称
     */
    private String name;

    /**
     * 近场检测标志类型
     * NFC、WiFi、QRCode、等，参照：Patrol_MarkTypeEnum
     */
    private String markType;

    /**
     * 近场检测标志code
     */
    private String markCode;


    //region 通过触发器更新，在保存设备巡检结果时
    /**
     * 巡检次数
     */
    private int count;

    /**
     * 最后一次巡检时间
     */
    private long lastPatrolTime;

    /**
     * 最后一次巡检人ID，参照：员工信息表
     */
    private int lastPatrolUserId;

    /**
     * 最后一次巡检人姓名，参照：员工信息表
     */
    private String lastPatrolUserName;
    //endregion

    private List<PatrolFacilityCheckItemsVo> patrolFacilityCheckItemsVos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLargeClass() {
        return largeClass;
    }

    public void setLargeClass(String largeClass) {
        this.largeClass = largeClass;
    }

    public String getMiddleClass() {
        return middleClass;
    }

    public void setMiddleClass(String middleClass) {
        this.middleClass = middleClass;
    }

    public String getSmallClass() {
        return smallClass;
    }

    public void setSmallClass(String smallClass) {
        this.smallClass = smallClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarkType() {
        return markType;
    }

    public void setMarkType(String markType) {
        this.markType = markType;
    }

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getLastPatrolTime() {
        return lastPatrolTime;
    }

    public void setLastPatrolTime(long lastPatrolTime) {
        this.lastPatrolTime = lastPatrolTime;
    }

    public int getLastPatrolUserId() {
        return lastPatrolUserId;
    }

    public void setLastPatrolUserId(int lastPatrolUserId) {
        this.lastPatrolUserId = lastPatrolUserId;
    }

    public String getLastPatrolUserName() {
        return lastPatrolUserName;
    }

    public void setLastPatrolUserName(String lastPatrolUserName) {
        this.lastPatrolUserName = lastPatrolUserName;
    }

    public List<PatrolFacilityCheckItemsVo> getPatrolFacilityCheckItemsVos() {
        return patrolFacilityCheckItemsVos;
    }

    public void setPatrolFacilityCheckItemsVos(List<PatrolFacilityCheckItemsVo> patrolFacilityCheckItemsVos) {
        this.patrolFacilityCheckItemsVos = patrolFacilityCheckItemsVos;
    }

    public static class PatrolFacilityCheckItemsVo extends MetaDataInfo {

        /**
         * 主键(自增)
         */
        private int id;

        /**
         * 外键，参考：主表 Patrol_FacilityCheck
         */
        private int facilityCheckId;

        /**
         * 指标项名称
         */
        private String name;

        /**
         * 指标项用的图片，推荐：64Px * 64Px
         */
        private String imgUri;

        private List<Patrol_FacilityCheckItemContents> patrolFacilityCheckItemContents;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFacilityCheckId() {
            return facilityCheckId;
        }

        public void setFacilityCheckId(int facilityCheckId) {
            this.facilityCheckId = facilityCheckId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUri() {
            return imgUri;
        }

        public void setImgUri(String imgUri) {
            this.imgUri = imgUri;
        }

        public List<Patrol_FacilityCheckItemContents> getPatrolFacilityCheckItemContents() {
            return patrolFacilityCheckItemContents;
        }

        public void setPatrolFacilityCheckItemContents(List<Patrol_FacilityCheckItemContents> patrolFacilityCheckItemContents) {
            this.patrolFacilityCheckItemContents = patrolFacilityCheckItemContents;
        }

        public static class Patrol_FacilityCheckItemContents implements Serializable {

            /**
             * 主键(自增)
             */
            private int id;

            /**
             * 外键，参照：主表 Patrol_FacilityCheckItems
             */
            private int facilityCheckItemId;

            /**
             * 检查项类别（检查项、抄表项），参照：Patrol_CheckTypeEnum
             */
            private String checkType;

            /**
             * 检查／抄表项内容
             */
            private String checkContent;

            /**
             * 抄表项：输入数据类型，如：radio、checkbox、number、String，参照：Patrol_MeterReadingTypeEnum
             */
            private String meterReadingType;

            /**
             * 可选项列表（输入数据类型为单选、多选时使用）
             * 多个选项间用逗号分隔
             * varchar(500)
             */
            private String meterOptions;

            /**
             * 正常值范围下限值
             */
            private double meterLowerLimit;

            /**
             * 正常值范围上限值
             */
            private double meterUpperLimit;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getFacilityCheckItemId() {
                return facilityCheckItemId;
            }

            public void setFacilityCheckItemId(int facilityCheckItemId) {
                this.facilityCheckItemId = facilityCheckItemId;
            }

            public String getCheckType() {
                return checkType;
            }

            public void setCheckType(String checkType) {
                this.checkType = checkType;
            }

            public String getCheckContent() {
                return checkContent;
            }

            public void setCheckContent(String checkContent) {
                this.checkContent = checkContent;
            }

            public String getMeterReadingType() {
                return meterReadingType;
            }

            public void setMeterReadingType(String meterReadingType) {
                this.meterReadingType = meterReadingType;
            }

            public String getMeterOptions() {
                return meterOptions;
            }

            public void setMeterOptions(String meterOptions) {
                this.meterOptions = meterOptions;
            }

            public double getMeterLowerLimit() {
                return meterLowerLimit;
            }

            public void setMeterLowerLimit(double meterLowerLimit) {
                this.meterLowerLimit = meterLowerLimit;
            }

            public double getMeterUpperLimit() {
                return meterUpperLimit;
            }

            public void setMeterUpperLimit(double meterUpperLimit) {
                this.meterUpperLimit = meterUpperLimit;
            }
        }
    }
}
