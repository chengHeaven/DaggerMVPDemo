package com.heavencheng.daggermvpdemo.util;

import android.util.Log;

import com.heavencheng.daggermvpdemo.BuildConfig;
import com.heavencheng.daggermvpdemo.constants.Configs;


/**
 * @author Heaven
 */
public class LogUtils {

    public static void v(String s) {
        if (BuildConfig.DEBUG) {
            v(Configs.TAG, s);
        }
    }

    public static void d(String s) {
        if (BuildConfig.DEBUG) {
            d(Configs.TAG, s);
        }
    }

    public static void i(String s) {
        if (BuildConfig.DEBUG) {
            i(Configs.TAG, s);
        }
    }

    public static void w(String s) {
        if (BuildConfig.DEBUG) {
            w(Configs.TAG, s);
        }
    }

    public static void e(String s) {
        if (BuildConfig.DEBUG) {
            e(Configs.TAG, s);
        }
    }

    public static void v(String tag, String s) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, s == null ? "" : s);
        }
    }

    public static void d(String tag, String s) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, s == null ? "" : s);
        }
    }

    public static void i(String tag, String s) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, s == null ? "" : s);
        }
    }

    public static void w(String tag, String s) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, s == null ? "" : s);
        }
    }

    public static void e(String tag, String s) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, s == null ? "" : s);
        }
    }
}
