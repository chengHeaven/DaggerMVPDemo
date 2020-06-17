package com.heavencheng.daggermvpdemo.presenter.main;

import com.heavencheng.daggermvpdemo.presenter.BasePresenter;
import com.heavencheng.daggermvpdemo.view.BaseView;

/**
 * @author Heaven
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
