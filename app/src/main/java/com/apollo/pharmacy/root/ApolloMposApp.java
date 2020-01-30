package com.apollo.pharmacy.root;


import android.app.Application;
import android.content.Context;

import com.apollo.pharmacy.data.db.realm.MyMigration;
import com.apollo.pharmacy.di.component.ApplicationComponent;
import com.apollo.pharmacy.di.component.DaggerApplicationComponent;
import com.apollo.pharmacy.di.module.ApplicationModule;
import com.apollo.pharmacy.utils.CustomFontFamily;
import com.orhanobut.hawk.Hawk;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApolloMposApp extends Application {

    private ApplicationComponent mApplicationComponent;
    private static Context context;
    CustomFontFamily customFontFamily;
    private static Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        ApolloMposApp.context = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);
        Hawk.init(getApplicationContext()).build();

        customFontFamily = CustomFontFamily.getInstance();
        // add your custom fonts here with your own custom name.
        customFontFamily.addFont("robotoLight", "roboto_light.ttf");
        customFontFamily.addFont("robotoRegular", "roboto_regular.ttf");
        customFontFamily.addFont("robotoMedium", "roboto_medium.ttf");
        customFontFamily.addFont("robotoBold", "roboto_bold.ttf");

        initRealmConfiguration();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static Context getContext() {
        return context;
    }

    private void initRealmConfiguration() {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("apollo_m_pass.realm")
                .schemaVersion(0)
                .migration(new MyMigration())
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public static Realm getRealm() {
        return realm;
    }
}
