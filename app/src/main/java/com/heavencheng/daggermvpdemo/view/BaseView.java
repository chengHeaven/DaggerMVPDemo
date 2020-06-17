package com.heavencheng.daggermvpdemo.view;

import androidx.annotation.StringRes;

import com.heavencheng.daggermvpdemo.presenter.BasePresenter;

/**
 * @author Heaven
 */
public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

    void showWaiting();

    void hideWaiting();

    boolean disconnectedNetwork();

    void toastMessage(String msg);

    void toastMessage(@StringRes int stringRes);

    void finishActivityForResult();

    void finishActivityForResult(int resultCode);

    void finishActivity();

    void whetherLogin(String message);
}
