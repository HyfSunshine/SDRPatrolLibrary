package com.sdr.patrollib.http;

import com.sdr.patrollib.data.BaseData;
import com.sdr.patrollib.data.main.PatrolProject;
import com.sdr.patrollib.data.main.PatrolProjectItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
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

    @GET("app/app_patrol_mobile_check/{projectId}")
    Observable<BaseData<PatrolProject>> getProjectDetail(@Path("projectId") int projectId, @Query("userId") String userId);
}
