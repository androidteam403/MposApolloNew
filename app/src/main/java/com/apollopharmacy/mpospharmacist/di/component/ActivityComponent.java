package com.apollopharmacy.mpospharmacist.di.component;


import com.apollopharmacy.mpospharmacist.di.PerActivity;
import com.apollopharmacy.mpospharmacist.di.module.ActivityModule;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorActivity;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacist.ui.adduser.AddUserActivity;
import com.apollopharmacy.mpospharmacist.ui.adminlogin.AdminLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.cusdocdetails.CustDocDetails;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdoctordetails.SearchCustomerDetails;
import com.apollopharmacy.mpospharmacist.ui.dashboard.DashboardActivity;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.payment.PaymentFragment;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales.SalesFragment;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.MedicinesDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginSetUp;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrderReturnActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacist.ui.searchuser.SearchUserActivity;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashActivity;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupActivity;

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

    void inject(AddItemActivity addItemActivity);

    void inject(CustDocDetails custDocDetails);

    void inject(SearchCustomerDetails searchCustomerDetails);

    void inject(ProductListActivity productListActivity);

    void inject(DoctorDetailsActivity doctorDetailsActivity);

    void inject(CustomerDetailsActivity customerDetailsActivity);

    void inject(MedicinesDetailsActivity medicinesDetailsActivity);

    void inject (OrderReturnActivity orderReturnActivity);
}