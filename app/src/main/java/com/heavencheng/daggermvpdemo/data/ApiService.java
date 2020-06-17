package com.heavencheng.daggermvpdemo.data;

import com.heavencheng.daggermvpdemo.BuildConfig;
import com.heavencheng.daggermvpdemo.bean.Data;
import com.heavencheng.daggermvpdemo.bean.MainBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author Heaven
 */
public interface ApiService {

    String BASE_URL = BuildConfig.DEBUG ? "" : "";

    @GET
    Observable<Data<MainBean>> request(String id);

    @GET
    Observable<Data> request();
}
