package com.heavencheng.daggermvpdemo.data;

/**
 * @author Heaven
 */
public interface Callback<T> {

    /**
     * request success
     *
     * @param t response {@link T}
     */
    void onSuccess(T t);

    /**
     * request failure
     *
     * @param message failure message
     */
    void onFailure(String message);
}
