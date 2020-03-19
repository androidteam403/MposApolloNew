package com.apollopharmacy.mpospharmacist.di.component;


import com.apollopharmacy.mpospharmacist.di.PerActivity;
import com.apollopharmacy.mpospharmacist.di.module.ActivityModule;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerActivity;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorActivity;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialog;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.DashBoardFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster.DoctorMasterFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling.ManualBillingFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.OrdersFragment;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginSetUp;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrderReturnActivity;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.OrderSummaryActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacist.ui.searchuser.SearchUserActivity;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashActivity;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupActivity;
import com.apollopharmacy.mpospharmacist.ui.storesetup.dialog.GetStoresDialog;

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

    void inject(SearchProductActivity searchProductActivity);

    void inject(SearchUserActivity searchUserActivity);

    void inject(AddCustomerActivity addCustomerActivity);

    void inject(PharmacistLoginActivity pharmacistLoginActivity);

    void inject(AddDoctorActivity addDoctorActivity);

    void inject(StoreSetupActivity storeSetupActivity);

    void inject(NewAdminLoginSetUp newAdminLoginSetUp);

    void inject(AddItemActivity addItemActivity);

    void inject(CustomerDoctorInfoActivity customerDoctorInfoActivity);

    void inject(SearchCustomerDoctorDetailsActivity searchCustomerDoctorDetailsActivity);

    void inject(ProductListActivity productListActivity);

    void inject(DoctorDetailsActivity doctorDetailsActivity);

    void inject(CustomerDetailsActivity customerDetailsActivity);

    void inject(DashBoardFragment dashBoardFragment);

    void inject(BillingFragment billingFragment);

    void inject(CustomerMasterFragment customerMasterFragment);

    void inject(DoctorMasterFragment doctorMasterFragment);

    void inject(ManualBillingFragment manualBillingFragment);

    void inject(OrdersFragment ordersFragment);

    void inject(MainActivity mainActivity);

    void inject(OrderReturnActivity orderReturnActivity);

    void inject(BatchInfoActivity batchInfoActivity);

    void inject(CorporateDetailsActivity corporateDetailsActivity);

    void inject(AllDoctorsDialog allDoctorsDialog);

    void inject(GetStoresDialog getStoresDialog);

    void inject(ScannerActivity scannerActivity);

    void inject(OrderSummaryActivity orderSummaryActivity);
}