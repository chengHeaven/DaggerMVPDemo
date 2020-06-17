package com.heavencheng.daggermvpdemo.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
