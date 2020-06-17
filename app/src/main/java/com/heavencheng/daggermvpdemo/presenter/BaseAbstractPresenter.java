package com.heavencheng.daggermvpdemo.presenter;

import com.heavencheng.daggermvpdemo.data.DataRepository;
import com.heavencheng.daggermvpdemo.view.BaseView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author Heaven
 * 所有Presenter父类，必须继承
 */
public abstract class BaseAbstractPresenter<T extends BaseView<P>, P extends BasePresenter> implements BasePresenter {

    protected Disposable mDisposable;
    protected DataRepository mDataRepository;
    protected T mView;

    protected BaseAbstractPresenter(DataRepository dataRepository, T view) {
        mDataRepository = dataRepository;
        mView = view;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
//        noinspection unchecked
        mView.setPresenter((P) this);
    }

    @Override
    public void start() {

    }

    @Override
    public void clear() {
        mDataRepository.clear();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
