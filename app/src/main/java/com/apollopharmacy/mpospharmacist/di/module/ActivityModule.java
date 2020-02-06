package com.apollopharmacy.mpospharmacist.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.apollopharmacy.mpospharmacist.di.ActivityContext;
import com.apollopharmacy.mpospharmacist.di.PerActivity;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorMvpView;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorPresenter;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemMvpView;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemPresenter;
import com.apollopharmacy.mpospharmacist.ui.adduser.AddUserMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.adduser.AddUserMvpView;
import com.apollopharmacy.mpospharmacist.ui.adduser.AddUserPresenter;
import com.apollopharmacy.mpospharmacist.ui.adminlogin.AdminLoginMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.adminlogin.AdminLoginMvpView;
import com.apollopharmacy.mpospharmacist.ui.adminlogin.AdminLoginPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoMvpView;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsPresenter;
import com.apollopharmacy.mpospharmacist.ui.dashboard.DashboardMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.dashboard.DashboardMvpView;
import com.apollopharmacy.mpospharmacist.ui.dashboard.DashboardPresenter;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.payment.PaymentMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.payment.PaymentMvpView;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.payment.PaymentPresenter;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales.SalesMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales.SalesMvpView;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales.SalesPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsPresenter;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginMvpView;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginPresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginMvpView;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchuser.SearchUserMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchuser.SearchUserMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchuser.SearchUserPresenter;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashMvpView;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashPresenter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupMvpView;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupPresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.AppSchedulerProvider;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AdminLoginMvpPresenter<AdminLoginMvpView> provideAdminLoginPresenter(AdminLoginPresenter<AdminLoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    SalesMvpPresenter<SalesMvpView> provideSalesFragment(SalesPresenter<SalesMvpView> presenter) {
        return presenter;
    }

    @Provides
    PaymentMvpPresenter<PaymentMvpView> providePaymentFragment(PaymentPresenter<PaymentMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DashboardMvpPresenter<DashboardMvpView> provideDashboardActivityPresenter(DashboardPresenter<DashboardMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SearchProductMvpPresenter<SearchProductMvpView> provideSearchProductActivity(SearchProductPresenter<SearchProductMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SearchUserMvpPresenter<SearchUserMvpView> provideSearchUserActivity(SearchUserPresenter<SearchUserMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AddUserMvpPresenter<AddUserMvpView> provideAddUserActivity(AddUserPresenter<AddUserMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PharmacistLoginMvpPresenter<PharmacistLoginMvpView> providePharmacistLoginActivity(PharmacistLoginPresenter<PharmacistLoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AddDoctorMvpPresenter<AddDoctorMvpView> provideAddDoctorActivity(AddDoctorPresenter<AddDoctorMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    StoreSetupMvpPresenter<StoreSetupMvpView> StoreSetupActivity(StoreSetupPresenter<StoreSetupMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    NewAdminLoginMvpPresenter<NewAdminLoginMvpView> provideNewAdminLoginActivity(NewAdminLoginPresenter<NewAdminLoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AddItemMvpPresenter<AddItemMvpView> provideAddItemActivity(AddItemPresenter<AddItemMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SearchCustomerDoctorDetailsMvpPresenter<SearchCustomerDoctorDetailsMvpView> provideSearchCustomerDetailsActivity(SearchCustomerDoctorDetailsPresenter<SearchCustomerDoctorDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CustomerDoctorInfoMvpPresenter<CustomerDoctorInfoMvpView> provideCustomerDocDetailsActivity(CustomerDoctorInfoPresenter<CustomerDoctorInfoMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DoctorDetailsMvpPresenter<DoctorDetailsMvpView> provideDoctorDetailsActivity(DoctorDetailsPresenter<DoctorDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CustomerDetailsMvpPresenter<CustomerDetailsMvpView> provideCustomerDetailsActivity(CustomerDetailsPresenter<CustomerDetailsMvpView> presenter) {
        return presenter;
    }
}