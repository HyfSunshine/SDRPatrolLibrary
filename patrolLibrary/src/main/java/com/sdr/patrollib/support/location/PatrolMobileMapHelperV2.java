package com.sdr.patrollib.support.location;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.orhanobut.logger.Logger;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.R;
import com.sdr.patrollib.data.project.PatrolProjectRecord;
import com.sdr.patrollib.util.PatrolUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HyFun on 2018/10/26.
 * Email:775183940@qq.com
 */

public class PatrolMobileMapHelperV2 {
    private static final int MSG_CODE_UPDATE_TIME = 1;


    private PatrolMapHandler handler = new PatrolMapHandler();
    // 定位
    private AMapLocationClient mainLocationClient;// 主要的定位client
    private AMapLocationClient subLocationClient;// 辅助定位的client
    private AMapLocationClientOption.AMapLocationMode mainLocationMode = AMapLocationClientOption.AMapLocationMode.Device_Sensors; // 当前的定位类型  默认使用gps定位
    private int locationFailNum = 0; // 主定位失败次数
    private boolean needBackLocation = true;


    // 地图
    private LinkedList<LatLng> positionList = new LinkedList<>(); // 巡检路线集合
    private int patrolTime; // 巡查时长
    private double patrolLength; // 巡查长度 （距离）  m
    private MediaPlayer mediaPlayer;

    private Context context;
    private PatrolProjectRecord mobileRecord;
    private PatrolMobileMapChangeListener listener;

    public PatrolMobileMapHelperV2(Context context, PatrolProjectRecord mobileRecord, PatrolMobileMapChangeListener listener) {
        this.context = context;
        this.mobileRecord = mobileRecord;
        this.listener = listener;

        handler.sendEmptyMessage(MSG_CODE_UPDATE_TIME);
        //初始化位置集合
        positionList.addAll(PatrolUtil.strToLatlngList(mobileRecord.getPatrolCoors()));
        // 计算长度
        patrolLength = calculateListDistance(positionList);
        if (listener != null) {
            listener.onPatrolLengthChange(false, patrolLength, positionList);
        }
        // 开启定位
        startMainLocation(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        startSubLocation();

        play();
    }

    // 前台activity可见  locationClient.disableBackgroundLocation(true);
    public void onResume() {
        mainLocationClient.disableBackgroundLocation(true);
        subLocationClient.disableBackgroundLocation(true);
    }

    // 应用后台执行了  locationClient.enableBackgroundLocation(2001, buildNotification());
    public void onPause() {
        if (!needBackLocation) return;
        mainLocationClient.enableBackgroundLocation(2001, buildNotification());
        subLocationClient.enableBackgroundLocation(2001, buildNotification());
    }

    public void onDestory() {
        if (mainLocationClient != null) {
            mainLocationClient.stopLocation();
            mainLocationClient.onDestroy();
        }

        if (subLocationClient != null) {
            subLocationClient.stopLocation();
            subLocationClient.onDestroy();
        }

        // 移除handler的信息
        handler.removeMessage();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    public LinkedList<LatLng> getPositionList() {
        return positionList;
    }

    public void setNeedBackLocation(boolean needBackLocation) {
        this.needBackLocation = needBackLocation;
    }

    // —————————————————handler—————————————————
    private class PatrolMapHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_CODE_UPDATE_TIME) {
                // s
                patrolTime = (int) ((new Date().getTime() - mobileRecord.getPatrolStartTime()) / 1000);
                String timeStr = secondToStr(patrolTime);
                if (listener != null)
                    listener.onPatrolTimeChange(patrolTime, timeStr);
                sendEmptyMessageDelayed(MSG_CODE_UPDATE_TIME, 1000);
            }
        }

        public void removeMessage() {
            removeMessages(MSG_CODE_UPDATE_TIME);
        }
    }

    /**
     * 主定位回调
     */
    private AMapLocationListener mainLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    locationFailNum = 0;
                    patrolLength = calculateListDistance(positionList);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    if (positionList.isEmpty()) {
                        positionList.add(latLng);
                        sb.append("【第一次添加集合内容】\n");
                        if (listener != null) {
                            listener.onPatrolLengthChange(true, patrolLength, positionList);
                        }
                    } else {
                        // 计算两点的距离
                        //              >=100添加
                        //              <100 不添加到集合中
                        // 获取到最后一个
                        LatLng lastLatlng = positionList.getLast();
                        // LatLng(double latitude, double longitude)
                        // 计算距离是否符合   单位 m
                        float distance = AMapUtils.calculateLineDistance(lastLatlng, latLng);
                        if (distance >= PatrolLibrary.getInstance().getPatrolConfig().getMinDistance()) {
                            positionList.add(latLng);
                            sb.append("【距离达到要求，已添加到集合中，距离为" + distance + "】\n");
                            // 回调
                            if (listener != null) {
                                listener.onPatrolLengthChange(true, patrolLength, positionList);
                            }
                        } else {
                            sb.append("【距离不够，没有添加到集合中，距离为" + distance + "】\n");
                        }
                    }

                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("开始定位时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(location.getTime())) + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                    locationFailNum++;
                    if (mainLocationMode == AMapLocationClientOption.AMapLocationMode.Device_Sensors && locationFailNum < 3) {
                        startMainLocation(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
                    }
                    if (mainLocationMode == AMapLocationClientOption.AMapLocationMode.Device_Sensors && locationFailNum >= 3) {
                        // 如果定位失败3次  那么就切换定位方式
                        Logger.w("gps 3次定位失败，切换为网络定位......");
                        startMainLocation(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    }
                    Logger.d("定位失败次数：" + locationFailNum);
                }
                //定位之后的回调时间
                sb.append("回调时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "\n");

                //解析定位结果，
                String result = sb.toString();
                listener.onLocationChanged(location, result);
                Logger.d(result);
            } else {
                Logger.e("定位失败，loc is null");
            }
        }
    };

    /**
     * 辅助定位回调
     */
    private AMapLocationListener subLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (location == null) return;
            if (location.getErrorCode() == 0) {
                // 定位成功 且当前不是gps定位
                if (mainLocationMode != AMapLocationClientOption.AMapLocationMode.Device_Sensors) {
                    startMainLocation(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
                    Logger.w("辅助GPS定位成功，正在切换回GPS定位......");
                }
            } else {
                // 定位不成功  重新启动辅助定位 保证辅助定位不会被中断
                startSubLocation();
            }
        }
    };
    // —————————————————私有方法—————————————————

    /**
     * 主定位开始定位
     *
     * @param mode
     */
    private void startMainLocation(AMapLocationClientOption.AMapLocationMode mode) {
        mainLocationMode = mode;
        if (mainLocationClient == null) {
            mainLocationClient = new AMapLocationClient(context);
        }
        mainLocationClient.stopLocation();
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        // 设置定位级别  设置使用gps定位
        mLocationOption.setLocationMode(mode);
        // 设置单次定位
        //mLocationOption.setOnceLocation(true);
        // 定位间隔 5s
        mLocationOption.setInterval(5000);
        mainLocationClient.setLocationOption(mLocationOption);
        mainLocationClient.setLocationListener(mainLocationListener);
        mainLocationClient.startLocation();
    }

    /**
     * 辅助定位开始定位
     */
    private void startSubLocation() {
        if (subLocationClient == null) {
            subLocationClient = new AMapLocationClient(context);
        }
        subLocationClient.stopLocation();
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        // 设置定位级别  设置使用gps定位
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        // 设置单次定位
        //mLocationOption.setOnceLocation(true);
        // 定位间隔 5s
        mLocationOption.setInterval(5000);
        subLocationClient.setLocationOption(mLocationOption);
        subLocationClient.setLocationListener(subLocationListener);
        subLocationClient.startLocation();
    }


    /**
     * 秒数转换成 时分秒格式
     *
     * @param seconds
     * @return
     */
    private String secondToStr(int seconds) {
        // 处理时间 格式  时分秒
        int startHours = (int) (seconds / 3600);
        int startMinutes = (int) ((seconds % 3600) / 60);
        int startSeconds = (int) ((seconds % 3600) % 60);
        String timeStr = String.format("%02d", startHours) + "时" + String.format("%02d", startMinutes) + "分" + String.format("%02d", startSeconds) + "秒";
        return timeStr;
    }

    /**
     * 计算集合中的距离
     *
     * @param latLngPoints
     * @return
     */
    private int calculateListDistance(List<LatLng> latLngPoints) {
        if (latLngPoints == null || latLngPoints.size() <= 1) return 0;
        int distance = 0;
        for (int i = 0; i < latLngPoints.size() - 1; i++) {
            LatLng currentLatLng = latLngPoints.get(i);
            LatLng nextLatLng = latLngPoints.get(i + 1);
            // 用当前的点和下一个点进行对比
            // LatLng(double latitude, double longitude)
            distance += AMapUtils.calculateLineDistance(currentLatLng, nextLatLng);
        }
        return distance;
    }

    private void play() {
        // debug播放国歌  release播放静音
        mediaPlayer = MediaPlayer.create(context, PatrolLibrary.getInstance().isDebug() ? R.raw.jingyin : R.raw.jingyin);
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(context, PatrolLibrary.getInstance().isDebug() ? R.raw.jingyin : R.raw.jingyin);
            }

            mediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        //mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//设置重复播放
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
    }

    // ———————————————————定位的通知栏———————————————————

    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    @SuppressLint("NewApi")
    private Notification buildNotification() {

        Notification.Builder builder = null;
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = context.getPackageName();
            if (!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(context.getResources().getColor(R.color.colorPrimary)); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(context, channelId);
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setSmallIcon(R.drawable.patrol_ic_notification)
                .setContentTitle("工程巡查定位")
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }
        return notification;
    }
}
