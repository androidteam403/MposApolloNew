package com.apollopharmacy.mpospharmacistTest.data.network;

import com.apollopharmacy.mpospharmacistTest.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    /********
     * URLS
     *******/

    private static final String ROOT_URL_2 = "http://lms.apollopharmacy.org:8033/APK/";

    private static final String ROOT_URL_3 = "http://online.apollopharmacy.org:51/OMSSERVICE/";

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance(String data) throws IllegalArgumentException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder()
                .baseUrl(data)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }


    private static Retrofit getRetrofitInstance2() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL_2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }


    private static Retrofit getRetrofitInstance3() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL_3)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiInterface getApiService(String url) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        try {
            if (httpUrl == null) {
                throw new IllegalArgumentException("");
            }
            return getRetrofitInstance(url).create(ApiInterface.class);
        } catch (IllegalArgumentException i) {

        }
        return null;
    }


    public static ApiInterface getApiService2() {
        return getRetrofitInstance2().create(ApiInterface.class);
    }

    public static ApiInterface getApiService3() {
        return getRetrofitInstance3().create(ApiInterface.class);
    }


    private static final String ROOT_URL = "https://signage.apollopharmacy.app/zc-v3.1-user-svc/2.0/ads/api/";


    private static Retrofit getRetrofitInstanceAds() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiInterface getApiServiceAds() {
        return getRetrofitInstanceAds().create(ApiInterface.class);
    }

    //send feed back api url calll....
    private static final String FEEDBACK_ROOT_URL = "http://lms.apollopharmacy.org:8033/SMSPAY/";

    private static Retrofit getRetrofitInstanceFeedback() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder()
                .baseUrl(FEEDBACK_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiInterface getApiServiceFeedback() {
        return getRetrofitInstanceFeedback().create(ApiInterface.class);
    }


    //private static final String ROOT_URL = "https://signage.apollopharmacy.app/zc-v3.1-user-svc/2.0/ads/api/";
    private static Retrofit getRetrofitInstanceOTP(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiInterface getApiServiceOTp(String url) {
        return getRetrofitInstanceOTP(url).create(ApiInterface.class);
    }

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}
