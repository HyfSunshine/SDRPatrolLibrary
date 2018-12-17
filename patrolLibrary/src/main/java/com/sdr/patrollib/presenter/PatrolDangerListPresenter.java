package com.sdr.patrollib.presenter;

import com.google.gson.reflect.TypeToken;
import com.sdr.lib.http.HttpClient;
import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolDangerListContract;
import com.sdr.patrollib.data.BaseData;
import com.sdr.patrollib.data.danger.PatrolDanger;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.PatrolServerException;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by HyFun on 2018/12/17.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolDangerListPresenter extends BasePresenter<PatrolDangerListContract.View> implements PatrolDangerListContract.Presenter {
    @Override
    public void getDangerList(int pageNo) {
        addSubscription(
                PatrolHttp.getService().getDangerList(pageNo, pageSize, "")
                        .flatMap(new Function<ResponseBody, ObservableSource<List<PatrolDanger>>>() {
                            @Override
                            public ObservableSource<List<PatrolDanger>> apply(ResponseBody responseBody) throws Exception {
                                String json = responseBody.string();
                                JSONObject object = new JSONObject(json);
                                int status = object.getInt("status");
                                if (status == BaseData.SUCCESS) {
                                    JSONObject dataObj = object.getJSONObject("data");
                                    List<PatrolDanger> dangerList = HttpClient.gson.fromJson(dataObj.getString("list"), new TypeToken<List<PatrolDanger>>() {
                                    }.getType());
                                    return observer -> {
                                        observer.onNext(dangerList);
                                        observer.onComplete();
                                    };
                                } else {
                                    return Observable.error(new PatrolServerException(object.getString("msg"), status));
                                }
                            }
                        })
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<List<PatrolDanger>>(view) {

                            @Override
                            public void onNext(List<PatrolDanger> dangerList) {
                                view.loadDangerListSuccess(dangerList);
                            }

                            @Override
                            public void onComplete() {
                                view.loadDataComplete();
                            }
                        })
        );
    }
}
