package com.sdr.patrollib.http.rx;

import com.sdr.patrollib.data.BaseData;
import com.sdr.patrollib.http.PatrolServerException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

/**
 * 转换数据
 */
public class PatrolRxUtils extends com.sdr.lib.rx.RxUtils {
    public static <T> ObservableTransformer<BaseData<T>, T> transformer() {
        return new ObservableTransformer<BaseData<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseData<T>> observable) {
                return observable.flatMap(new Function<BaseData<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseData<T> baseData) throws Exception {
                        if (baseData.getStatus() == BaseData.SUCCESS) {
                            return createData(baseData.getData());
                        } else {
                            PatrolServerException exception = new PatrolServerException(baseData.getMsg(), baseData.getStatus());
                            return Observable.error(exception);
                        }
                    }
                });
            }
        };
    }


    public static <T> Observable<T> createData(T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}
