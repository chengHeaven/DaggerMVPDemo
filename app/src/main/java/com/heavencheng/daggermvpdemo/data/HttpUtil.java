package com.heavencheng.daggermvpdemo.data;

import androidx.annotation.NonNull;

import com.heavencheng.daggermvpdemo.bean.Data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven
 */
public class HttpUtil {

    public static Disposable mDisposable;
    public static CompositeDisposable mCompositeDisposable;

    public static void callbackString(@NonNull Observable<Data> observable, @NonNull Callback<String> callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Data data) {
                        if (data.getCode() == 1000) {
                            callback.onSuccess(data.getMessage());
                        } else {
                            callback.onFailure(data.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("502")) {

                        } else if (e.getMessage().contains("UnknownHostException")
                                || e.getMessage().contains("Unable to resolve host")
                                || e.getMessage().contains("No address associated with hostname")
                                || e.getMessage().contains("Failed to connect to")) {
                            callback.onFailure("网络连接已断开");
                        } else if (e.getMessage().contains("403")) {
                            callback.onFailure("登录超时，请重新登录");
                        } else if (e.getMessage().contains("timed out") || e.getMessage().contains("Timedout")
                                || e.getMessage().contains("timedout") || e.getMessage().contains("Timed out")) {
                            callback.onFailure("网络连接超时");
                        } else {
                            callback.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static <T> void callbackResult(@NonNull Observable<Data<T>> observable, @NonNull Callback<T> callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Data<T> data) {
                        if (data.getCode() == 1000) {
                            if (data.getResult() instanceof List) {
                                if (data.getResult() == null || ((List) data.getResult()).size() == 0) {
                                    callback.onFailure("暂无数据");
                                } else {
                                    callback.onSuccess(data.getResult());
                                }
                            } else {
                                callback.onSuccess(data.getResult());
                            }
                        } else {
                            callback.onFailure(data.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("502")) {

                        } else if (e.getMessage().contains("UnknownHostException")
                                || e.getMessage().contains("Unable to resolve host")
                                || e.getMessage().contains("No address associated with hostname")
                                || e.getMessage().contains("Failed to connect to")) {
                            callback.onFailure("网络连接已断开");
                        } else if (e.getMessage().contains("403")) {
                            callback.onFailure("登录超时，请重新登录");
                        } else if (e.getMessage().contains("timed out")) {
                            callback.onFailure("网络连接超时");
                        } else if (e.getMessage().contains("empty String") || e.getMessage().contains("Invalid double:")) {
                            callback.onFailure("暂无数据");
                        } else {
                            callback.onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void clear() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
