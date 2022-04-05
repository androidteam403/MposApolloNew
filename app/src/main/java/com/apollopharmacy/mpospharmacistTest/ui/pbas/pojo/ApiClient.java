package com.apollopharmacy.mpospharmacistTest.ui.pbas.pojo;

import com.apollopharmacy.mpospharmacistTest.BuildConfig;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;

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



    private static final String ROOT_URL = "http://online.apollopharmacy.org:51/EPOS/";

    /**
     * Get Retrofit Instance
     */



    public static Retrofit getRetrofitInstance() throws IllegalArgumentException {
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




    /**
     * Get API Service
     *
     * @return API Service
     */






}
