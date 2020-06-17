package com.heavencheng.daggermvpdemo.constants;

import okhttp3.MediaType;

/**
 * @author Heaven
 */
public class Configs {

    public static final int SUCCESS_CODE = 10000;
    public static final int FAILURE_CODE = 9999;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType IMAGE_MEDIA_TYPE = MediaType.parse("image/png; charset=utf-8");
    public static final MediaType TEXT_MEDIA_TYPE = MediaType.parse("text/plain; charset=utf-8");
    public static final MediaType MULTIPART_MEDIA_TYPE = MediaType.parse("multipart/form-data; charset=utf-8");
    public static final String TYPE = "param";
    public static final String TAG = "DaggerMVPDemo";
    public static final int PAGE_SIZE = 15;
}
