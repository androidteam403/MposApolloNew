package com.apollopharmacy.mpospharmacistTest.di.module;

import android.app.Application;
import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.BuildConfig;
import com.apollopharmacy.mpospharmacistTest.data.BaseDataManager;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.NetworkService;
import com.apollopharmacy.mpospharmacistTest.data.network.RestApiHelper;
import com.apollopharmacy.mpospharmacistTest.data.network.RestApiManager;
import com.apollopharmacy.mpospharmacistTest.data.network.WrapperConverterFactory;
import com.apollopharmacy.mpospharmacistTest.data.prefs.PreferencesHelper;
import com.apollopharmacy.mpospharmacistTest.data.prefs.PreferencesManager;
import com.apollopharmacy.mpospharmacistTest.di.AdminPreferenceInfo;
import com.apollopharmacy.mpospharmacistTest.di.ApiInfo;
import com.apollopharmacy.mpospharmacistTest.di.ApplicationContext;
import com.apollopharmacy.mpospharmacistTest.di.DatabaseInfo;
import com.apollopharmacy.mpospharmacistTest.di.PreferenceInfo;
import com.apollopharmacy.mpospharmacistTest.root.AppConstant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }


    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstant.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstant.USER_PREF_NAME;
    }

    @Provides
    @AdminPreferenceInfo
    String provideAdminPreferenceName(){
        return AppConstant.ADMIN_PREF_NAME;
    }
    @Provides
    @Singleton
    DataManager provideDataManager(BaseDataManager mDataManager) {
        return mDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(PreferencesManager preferencesManager) {
        return preferencesManager;
    }

    @Provides
    @Singleton
    RestApiHelper provideRestApiHelper(RestApiManager restApiManager) {
        return restApiManager;
    }


    /**
     * @return HTTTP Client
     */
    @Provides
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(chain -> {
            Request request = chain.request();
            return chain.proceed(request);
        }).build();
    }

    /**
     * provide Retrofit instances
     *
     * @param baseURL base url for api calling
     * @param client  OkHttp client
     * @return Retrofit instances
     */

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new WrapperConverterFactory(GsonConverterFactory.create()))
                .build();
    }

    /**
     * Provide Api service
     *
     * @return ApiService instances
     */
    @Provides
    public NetworkService provideApiService() {
        return provideRetrofit(BuildConfig.BASE_URL, provideClient()).create(NetworkService.class);
    }
}
