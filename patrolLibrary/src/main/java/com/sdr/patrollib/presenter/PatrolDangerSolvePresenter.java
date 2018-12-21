package com.sdr.patrollib.presenter;

import com.sdr.lib.http.HttpClient;
import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolDangerSolveContract;
import com.sdr.patrollib.data.BaseData;
import com.sdr.patrollib.data.danger.Maintenance_DefectTrackingInfo;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.PatrolServerException;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by HyFun on 2018/12/20.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerSolvePresenter extends BasePresenter<PatrolDangerSolveContract.View> implements PatrolDangerSolveContract.Presenter {
    @Override
    public void getDangerFlowList(String id) {
        addSubscription(
                PatrolHttp.getService().getDangerFlowList(id)
                        .compose(PatrolRxUtils.transformer())
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<List<Maintenance_DefectTrackingInfo>>(view) {

                            @Override
                            public void onNext(List<Maintenance_DefectTrackingInfo> maintenance_defectTrackingInfos) {
                                view.loadDangerFlowListSuccess(maintenance_defectTrackingInfos);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }

    @Override
    public void handleDanger(Maintenance_DefectTrackingInfo trackingInfo) {
        addSubscription(
                PatrolHttp.getService().updateTrackInfo(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), HttpClient.gson.toJson(trackingInfo)))
                        .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                            @Override
                            public ObservableSource<Boolean> apply(ResponseBody responseBody) throws Exception {
                                String json = responseBody.string();
                                JSONObject object = new JSONObject(json);
                                int status = object.getInt("status");
                                if (status == BaseData.SUCCESS) {
                                    return PatrolRxUtils.createData(true);
                                } else {
                                    return Observable.error(new PatrolServerException(object.getString("msg"), status));
                                }
                            }
                        })
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<Boolean>(view) {

                            @Override
                            public void onNext(Boolean aBoolean) {
                                view.handlerDangerSuccess();
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
