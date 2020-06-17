package com.heavencheng.daggermvpdemo.di.component;

import com.heavencheng.daggermvpdemo.data.DataRepository;
import com.heavencheng.daggermvpdemo.di.module.ApplicationModule;
import com.heavencheng.daggermvpdemo.di.module.DataRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Heaven
 */
@Singleton
@Component(modules = {DataRepositoryModule.class, ApplicationModule.class})
public interface DataRepositoryComponent {

    /**
     * get data repository
     *
     * @return data repository
     */
    DataRepository getRepository();
}
