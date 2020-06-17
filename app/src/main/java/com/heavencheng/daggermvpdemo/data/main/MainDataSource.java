package com.heavencheng.daggermvpdemo.data.main;

import com.heavencheng.daggermvpdemo.bean.MainBean;
import com.heavencheng.daggermvpdemo.data.BaseDataSource;
import com.heavencheng.daggermvpdemo.data.Callback;

/**
 * @author Heaven
 */
public interface MainDataSource extends BaseDataSource {

    void request(Callback<String> callback);

    void request(String id, Callback<MainBean> callback);
}
