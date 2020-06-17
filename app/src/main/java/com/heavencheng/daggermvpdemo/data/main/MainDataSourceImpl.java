package com.heavencheng.daggermvpdemo.data.main;

import android.content.Context;

import com.heavencheng.daggermvpdemo.bean.Data;
import com.heavencheng.daggermvpdemo.bean.MainBean;
import com.heavencheng.daggermvpdemo.data.BaseDataSourceImpl;
import com.heavencheng.daggermvpdemo.data.Callback;
import com.heavencheng.daggermvpdemo.data.HttpUtil;
import com.heavencheng.daggermvpdemo.data.RetrofitFactory;

import io.reactivex.Observable;

/**
 * @author Heaven
 */
public class MainDataSourceImpl extends BaseDataSourceImpl implements MainDataSource {

    public MainDataSourceImpl(Context context) {
        super(context);
    }

    @Override
    public void request(Callback<String> callback) {
        Observable<Data> observable = RetrofitFactory.getInstance().request();
        HttpUtil.callbackString(observable, callback);
    }

    @Override
    public void request(String id, Callback<MainBean> callback) {
        Observable<Data<MainBean>> observable = RetrofitFactory.getInstance().request(id);
        HttpUtil.callbackResult(observable, callback);
    }
}
