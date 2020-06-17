package com.heavencheng.daggermvpdemo.data;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Heaven
 */
public abstract class BaseDataSourceImpl implements BaseDataSource {

    protected Context mContext;
    protected Disposable mDisposable;
    protected CompositeDisposable mCompositeDisposable;

    public BaseDataSourceImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void clear() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
