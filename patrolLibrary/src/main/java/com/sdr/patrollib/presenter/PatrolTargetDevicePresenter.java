package com.sdr.patrollib.presenter;

import com.sdr.lib.http.HttpClient;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.base.BasePresenter;
import com.sdr.patrollib.contract.PatrolTargetDeviceContract;
import com.sdr.patrollib.data.BaseData;
import com.sdr.patrollib.data.device.PatrolDeviceRecord;
import com.sdr.patrollib.http.PatrolHttp;
import com.sdr.patrollib.http.PatrolServerException;
import com.sdr.patrollib.http.rx.PatrolRxBaseObserver;
import com.sdr.patrollib.http.rx.PatrolRxUtils;
import com.sdr.patrollib.support.data.AttachmentLocal;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by HyFun on 2018/12/07.
 * Email: 775183940@qq.com
 * Description:
 */

public class PatrolTargetDevicePresenter extends BasePresenter<PatrolTargetDeviceContract.View> implements PatrolTargetDeviceContract.Presenter {

    private List<AttachmentLocal> needUploadList = new ArrayList<>();
    private List<AttachmentLocal> notUploadList = new ArrayList<>();
    private PatrolDeviceRecord patrolDeviceRecord;

    private int currentIndex = 0;

    @Override
    public void postAttatchment(List<AttachmentLocal> attachmentLocals, PatrolDeviceRecord patrolDeviceRecord) {
        needUploadList.clear();
        needUploadList.addAll(attachmentLocals);
        notUploadList.clear();
        currentIndex = 0;
        this.patrolDeviceRecord = patrolDeviceRecord;
        postFile();
    }

    @Override
    public void postDeviceRecord(PatrolDeviceRecord patrolDeviceRecord) {
        view.showLoadingDialog("上传记录中...");
        addSubscription(
                PatrolHttp.getService().postDeviceRecordJson(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), HttpClient.gson.toJson(patrolDeviceRecord)))
                        .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                            @Override
                            public ObservableSource<Boolean> apply(ResponseBody responseBody) throws Exception {
                                String json = responseBody.string();
                                JSONObject object = new JSONObject(json);
                                int status = object.getInt("status");
                                if (status == BaseData.SUCCESS) {
                                    return observer -> {
                                        observer.onNext(true);
                                        observer.onComplete();
                                    };
                                } else {
                                    return Observable.error(new PatrolServerException("上传设备巡查记录失败", status));
                                }
                            }
                        })
                        .compose(PatrolRxUtils.io_main())
                        .subscribeWith(new PatrolRxBaseObserver<Boolean>(view) {

                            @Override
                            public void onNext(Boolean aBoolean) {
                                view.uploadDeviceRecordSuccess();
                            }

                            @Override
                            public void onComplete() {
                                view.hideLoadingDialog();
                            }
                        })
        );
    }


    /**
     * 递归
     *
     * @return
     */
    private void postFile() {
        view.showLoadingDialog("上传附件中(" + (currentIndex + 1) + "/" + needUploadList.size() + ")");
        AttachmentLocal attach = needUploadList.get(currentIndex);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dangerId", attach.getDangerId())
                .addFormDataPart("employeeName", PatrolLibrary.getInstance().getPatrolUser().getUserName())
                .addFormDataPart("file", attach.getFileName(), RequestBody.create(MediaType.parse("multipart/octet-stream"), new File(attach.getFilePath())));

        Disposable disposable = PatrolHttp.getService().postFile(builder.build())
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(ResponseBody responseBody) throws Exception {
                        String json = responseBody.string();
                        JSONObject object = new JSONObject(json);
                        int status = object.getInt("status");
                        if (status == BaseData.SUCCESS) {
                            return observer -> {
                                observer.onNext(true);
                                observer.onComplete();
                            };
                        } else {
                            // 添加到上传失败的集合中
                            return Observable.error(new PatrolServerException("附件上传失败", status));
                        }
                    }
                })
                .compose(PatrolRxUtils.io_main())
                .subscribeWith(new PatrolRxBaseObserver<Boolean>(view) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        attach.setStatus(AttachmentLocal.UPLOADED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        notUploadList.add(attach);
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                        currentIndex++;
                        if (currentIndex >= needUploadList.size()) {
                            // 说明上传完成
                            view.hideLoadingDialog();
                            if (!notUploadList.isEmpty()) {
                                // 说明还有未完成的
                                view.showUploadAttatchFaileDialog(notUploadList);
                            } else {
                                // 已经完成  上传记录的json
                                postDeviceRecord(patrolDeviceRecord);
                            }
                        } else {
                            postFile();
                        }
                    }
                });

        addSubscription(disposable);
    }

}
