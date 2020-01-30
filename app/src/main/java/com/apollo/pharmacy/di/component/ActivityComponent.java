package com.apollo.pharmacy.di.component;


import com.apollo.pharmacy.di.PerActivity;
import com.apollo.pharmacy.di.module.ActivityModule;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;
import com.apollo.pharmacy.ui.searchpharmacy.SearchPharmacyActivity;
import com.apollo.pharmacy.ui.searchproduct.SearchProductActivity;
import com.apollo.pharmacy.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(AdminLoginActivity adminLoginActivity);

    void inject(SearchPharmacyActivity searchPharmacyActivity);

    void inject(DashboardActivity dashboardActivity);

    void inject(SearchProductActivity searchProductActivity);
}