package com.apollo.pharmacy.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.apollo.pharmacy.di.ActivityContext;
import com.apollo.pharmacy.di.PerActivity;
import com.apollo.pharmacy.ui.adddoctor.AddDoctorMvpPresenter;
import com.apollo.pharmacy.ui.adddoctor.AddDoctorMvpView;
import com.apollo.pharmacy.ui.adddoctor.AddDoctorPresenter;
import com.apollo.pharmacy.ui.adduser.AddUserMvpPresenter;
import com.apollo.pharmacy.ui.adduser.AddUserMvpView;
import com.apollo.pharmacy.ui.adduser.AddUserPresenter;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginMvpPresenter;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginMvpView;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginPresenter;
import com.apollo.pharmacy.ui.dashboard.DashboardMvpPresenter;
import com.apollo.pharmacy.ui.dashboard.DashboardMvpView;
import com.apollo.pharmacy.ui.dashboard.DashboardPresenter;
import com.apollo.pharmacy.ui.dashboard.fragments.payment.PaymentMvpPresenter;
import com.apollo.pharmacy.ui.dashboard.fragments.payment.PaymentMvpView;
import com.apollo.pharmacy.ui.dashboard.fragments.payment.PaymentPresenter;
import com.apollo.pharmacy.ui.dashboard.fragments.sales.SalesMvpPresenter;
import com.apollo.pharmacy.ui.dashboard.fragments.sales.SalesMvpView;
import com.apollo.pharmacy.ui.dashboard.fragments.sales.SalesPresenter;
import com.apollo.pharmacy.ui.pharmacistlogin.PharmacistLoginMvpPresenter;
import com.apollo.pharmacy.ui.pharmacistlogin.PharmacistLoginMvpView;
import com.apollo.pharmacy.ui.pharmacistlogin.PharmacistLoginPresenter;
import com.apollo.pharmacy.ui.searchproduct.SearchProductMvpPresenter;
import com.apollo.pharmacy.ui.searchproduct.SearchProductMvpView;
import com.apollo.pharmacy.ui.searchproduct.SearchProductPresenter;
import com.apollo.pharmacy.ui.searchuser.SearchUserMvpPresenter;
import com.apollo.pharmacy.ui.searchuser.SearchUserMvpView;
import com.apollo.pharmacy.ui.searchuser.SearchUserPresenter;
import com.apollo.pharmacy.ui.splash.SplashMvpPresenter;
import com.apollo.pharmacy.ui.splash.SplashMvpView;
import com.apollo.pharmacy.ui.splash.SplashPresenter;
import com.apollo.pharmacy.utils.rx.AppSchedulerProvider;
import com.apollo.pharmacy.utils.rx.SchedulerProvider;

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
}