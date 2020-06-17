package com.heavencheng.daggermvpdemo.data;

import com.heavencheng.daggermvpdemo.bean.MainBean;
import com.heavencheng.daggermvpdemo.data.main.MainDataSource;

import javax.inject.Inject;

/**
 * @author Heaven
 */
public class DataRepository implements MainDataSource {

    private final MainDataSource mMainDataSource;

    @Inject
    public DataRepository(MainDataSource mainDataSource) {
        mMainDataSource = mainDataSource;
    }

    public void clear() {
        mMainDataSource.clear();
    }

    @Override
    public void request(Callback<String> callback) {
        mMainDataSource.request(callback);
    }

    @Override
    public void request(String id, Callback<MainBean> callback) {
        mMainDataSource.request(id, callback);
    }
}
