package com.heavencheng.daggermvpdemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Heaven
 */
public class NetworkUtil {

    /**
     * 获取当前的网络状态 ：0：没有网络、1：WIFI网络、2：移动网络
     *
     * @param context Context
     * @return network type
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return 0;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            return 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            return 2;
        }
        return 0;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context Context
     * @return network type
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
