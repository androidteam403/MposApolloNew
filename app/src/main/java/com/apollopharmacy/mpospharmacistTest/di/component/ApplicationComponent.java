package com.apollopharmacy.mpospharmacistTest.di.component;

import android.app.Application;
import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.di.ApplicationContext;
import com.apollopharmacy.mpospharmacistTest.di.module.ApplicationModule;

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
