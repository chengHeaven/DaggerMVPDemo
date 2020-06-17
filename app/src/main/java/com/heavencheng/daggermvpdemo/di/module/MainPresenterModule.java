package com.heavencheng.daggermvpdemo.di.module;

import com.heavencheng.daggermvpdemo.presenter.main.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven
 */
@Module
public class MainPresenterModule {

    private final MainContract.View mView;

    public MainPresenterModule(MainContract.View view) {
        this.mView = view;
    }

    @Provides
    MainContract.View provideMainContractView() {
        return mView;
    }
}
