package com.heavencheng.daggermvpdemo.data;

import android.annotation.SuppressLint;

import com.heavencheng.daggermvpdemo.customer.WebViewCookie;
import com.heavencheng.daggermvpdemo.util.SharedPreferencesUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Heaven
 */
public class RetrofitFactory {

    private static String mToken = "";
    private static ApiService sInstance;

    public static void init() {
        if ("".equals(mToken)) {
            mToken = SharedPreferencesUtil.getInstance("NonTaxPosToken").getString("token");
        }
    }

    public static ApiService getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (sInstance == null) {
                    sInstance = new Retrofit.Builder()
                            .baseUrl(ApiService.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClient())
                            .build()
                            .create(ApiService.class);
                }
            }
        }
        return sInstance;
    }

    public static void updateToken(String token) {
        mToken = token;
    }

    private static OkHttpClient getOkHttpClient() {
        init();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("X-AUTH-TOKEN", mToken)
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(logging)
//                .addInterceptor(new ChuckInterceptor(App.getAppContext()))
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .cookieJar(new WebViewCookie())
                .sslSocketFactory(getSslSocketFactory())
                .hostnameVerifier(getHostnameVerifier())
                .build();
    }

    private static SSLSocketFactory getSslSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    @SuppressLint("TrustAllX509TrustManager")
    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};
    }

    private static HostnameVerifier getHostnameVerifier() {
        return (hostname, session) -> true;
    }

    public static String getToken() {
        return mToken;
    }
}
