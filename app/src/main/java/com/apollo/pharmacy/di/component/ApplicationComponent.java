package com.apollo.pharmacy.di.component;

import android.app.Application;
import android.content.Context;

import com.apollo.pharmacy.root.ApolloMposApp;
import com.apollo.pharmacy.data.DataManager;
import com.apollo.pharmacy.di.ApplicationContext;
import com.apollo.pharmacy.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ApolloMposApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
//    @Component.Builder
//    interface Builder {
//        ApplicationComponent build();
//
//        Builder applicationModule(ApplicationModule applicationModule);
//
//        DataManager getDataManager();
//    }


}
