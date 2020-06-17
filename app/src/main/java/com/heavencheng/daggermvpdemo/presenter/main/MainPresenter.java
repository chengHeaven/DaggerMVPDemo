package com.heavencheng.daggermvpdemo.presenter.main;

import com.heavencheng.daggermvpdemo.data.DataRepository;
import com.heavencheng.daggermvpdemo.presenter.BaseAbstractPresenter;

import javax.inject.Inject;

/**
 * @author Heaven
 */
public class MainPresenter extends BaseAbstractPresenter<MainContract.View, MainContract.Presenter> implements MainContract.Presenter {

    @Inject
    protected MainPresenter(DataRepository dataRepository, MainContract.View view) {
        super(dataRepository, view);
    }
}
