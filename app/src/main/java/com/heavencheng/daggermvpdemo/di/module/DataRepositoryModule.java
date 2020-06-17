package com.heavencheng.daggermvpdemo.di.module;

import android.content.Context;

import com.heavencheng.daggermvpdemo.data.main.MainDataSource;
import com.heavencheng.daggermvpdemo.data.main.MainDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven
 */
@Module
public class DataRepositoryModule {

    @Singleton
    @Provides
    MainDataSource provideMainDataSource(Context context) {
        return new MainDataSourceImpl(context);
    }
}
