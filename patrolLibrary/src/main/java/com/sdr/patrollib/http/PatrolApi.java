package com.sdr.patrollib.http;

import com.sdr.patrollib.data.BaseData;
import com.sdr.patrollib.data.device.PatrolDevice;
import com.sdr.patrollib.data.project.PatrolProject;
import com.sdr.patrollib.data.project.PatrolProjectItem;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public interface PatrolApi {
    //获取工程巡检列表
    @GET("app/app_patrol_mobile_check")
    Observable<BaseData<List<PatrolProjectItem>>> getProjectList(@Query("userId") String userId);

    // 获取工程巡检详情
    @GET("app/app_patrol_mobile_check/{projectId}")
    Observable<BaseData<PatrolProject>> getProjectDetail(@Path("projectId") int projectId, @Query("userId") String userId);

    // 获取设备巡检的详情
    @GET("app/app_patrol_facility_check")
    Observable<BaseData<PatrolDevice>> getDeviceInfo(@Query("userId") String userId, @Query("markCode") String code);

    // 设备巡检附件上传
    @POST("app/app_patrol_facility_check_records/app_upload")
    Observable<ResponseBody> postFile(@Body RequestBody Body);

    // 设备巡检记录上传
    @POST("app/app_patrol_facility_check_records")
    Observable<ResponseBody> postDeviceRecordJson(@Body RequestBody requestBody);

}
