package com.apollo.pharmacy.di.component;


import com.apollo.pharmacy.di.PerActivity;
import com.apollo.pharmacy.di.module.ActivityModule;
import com.apollo.pharmacy.ui.adddoctor.AddDoctorActivity;
import com.apollo.pharmacy.ui.adduser.AddUserActivity;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginActivity;
import com.apollo.pharmacy.ui.customerdetails.CustomerDetailsActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;
import com.apollo.pharmacy.ui.dashboard.fragments.payment.PaymentFragment;
import com.apollo.pharmacy.ui.dashboard.fragments.sales.SalesFragment;
import com.apollo.pharmacy.ui.doctordetails.DoctorDetailsActivity;
import com.apollo.pharmacy.ui.newadminloginsetup.NewAdminLoginSetUp;
import com.apollo.pharmacy.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollo.pharmacy.ui.searchproduct.SearchProductActivity;
import com.apollo.pharmacy.ui.searchuser.SearchUserActivity;
import com.apollo.pharmacy.ui.splash.SplashActivity;
import com.apollo.pharmacy.ui.storesetup.StoreSetupActivity;

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

    void inject(DashboardActivity dashboardActivity);

    void inject(SalesFragment salesFragment);

    void inject(PaymentFragment paymentFragment);

    void inject(SearchProductActivity searchProductActivity);

    void inject(SearchUserActivity searchUserActivity);

    void inject(AddUserActivity addUserActivity);

    void inject(PharmacistLoginActivity pharmacistLoginActivity);

    void inject(AddDoctorActivity addDoctorActivity);

    void inject(StoreSetupActivity storeSetupActivity);

    void inject(NewAdminLoginSetUp newAdminLoginSetUp);

    void inject(CustomerDetailsActivity customerDetailsActivity);

    void inject(DoctorDetailsActivity doctorDetailsActivity);



}