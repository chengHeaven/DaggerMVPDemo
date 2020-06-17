package com.heavencheng.daggermvpdemo.application;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.heavencheng.daggermvpdemo.di.component.DaggerDataRepositoryComponent;
import com.heavencheng.daggermvpdemo.di.component.DataRepositoryComponent;
import com.heavencheng.daggermvpdemo.di.module.ApplicationModule;
import com.heavencheng.daggermvpdemo.util.LogUtils;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author Heaven
 */
public class App extends MultiDexApplication {

    private DataRepositoryComponent mDataRepositoryComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataRepositoryComponent = DaggerDataRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        setRxJavaErrorHandler();
    }

    public DataRepositoryComponent getDataRepositoryComponent() {
        return mDataRepositoryComponent;
    }

    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> LogUtils.e("error"));
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }
}
