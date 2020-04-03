package com.apollopharmacy.mpospharmacist.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.apollopharmacy.mpospharmacist.di.ActivityContext;
import com.apollopharmacy.mpospharmacist.di.PerActivity;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerMvpView;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerPresenter;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorMvpView;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorPresenter;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemMvpView;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemPresenter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoMvpView;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoPresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoMvpView;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialogMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialogMvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialogPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivityMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivityMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivityPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.DashBoardMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.DashBoardMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.DashBoardPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster.DoctorMasterMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster.DoctorMasterMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster.DoctorMasterPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling.ManualBillingMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling.ManualBillingMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling.ManualBillingPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.OrdersMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.OrdersMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.OrdersPresenter;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginMvpView;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.NewAdminLoginPresenter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrderReturnMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrderReturnPresenter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrederReturnMvpView;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.OrderSummaryMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.OrderSummaryMvpView;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.OrderSummaryPresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginMvpView;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginPresenter;
import com.apollopharmacy.mpospharmacist.ui.scanner.ScannerMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.scanner.ScannerMvpView;
import com.apollopharmacy.mpospharmacist.ui.scanner.ScannerPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListPresenter;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashMvpView;
import com.apollopharmacy.mpospharmacist.ui.splash.SplashPresenter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupMvpView;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupPresenter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.dialog.GetStoresDialogMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.dialog.GetStoresDialogMvpView;
import com.apollopharmacy.mpospharmacist.ui.storesetup.dialog.GetStoresDialogPresenter;
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
    AddCustomerMvpPresenter<AddCustomerMvpView> provideAddUserActivity(AddCustomerPresenter<AddCustomerMvpView> presenter) {
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

    @Provides
    @PerActivity
    ProductListMvpPresenter<ProductListMvpView> provideProductListActivity(ProductListPresenter<ProductListMvpView> presenter) {
        return presenter;
    }

    @Provides
    BillingMvpPresenter<BillingMvpView> provideBillingMvpPresenter(BillingPresenter<BillingMvpView> presenter) {
        return presenter;
    }

    @Provides
    CustomerMasterMvpPresenter<CustomerMasterMvpView> provideCustomerMasterMvpPresenter(CustomerMasterPresenter<CustomerMasterMvpView> presenter) {
        return presenter;
    }

    @Provides
    DashBoardMvpPresenter<DashBoardMvpView> provideDashBoardMvpPresenter(DashBoardPresenter<DashBoardMvpView> presenter) {
        return presenter;
    }

    @Provides
    DoctorMasterMvpPresenter<DoctorMasterMvpView> provideDoctorMasterMvpPresenter(DoctorMasterPresenter<DoctorMasterMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManualBillingMvpPresenter<ManualBillingMvpView> provideManualBillingMvpPresenter(ManualBillingPresenter<ManualBillingMvpView> presenter) {
        return presenter;
    }

    @Provides
    OrdersMvpPresenter<OrdersMvpView> provideOrdersMvpPresenter(OrdersPresenter<OrdersMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainActivityMvpPresenter<MainActivityMvpView> provideMainActivityMvpPresenter(MainActivityPresenter<MainActivityMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BatchInfoMvpPresenter<BatchInfoMvpView> provideBatchInfoActivity(BatchInfoPresenter<BatchInfoMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OrderReturnMvpPresenter<OrederReturnMvpView> provideOrderReturnActivity(OrderReturnPresenter<OrederReturnMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CorporateDetailsMvpPresenter<CorporateDetailsMvpView> provideCorporateDetailsActivity(CorporateDetailsPresenter<CorporateDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    AllDoctorsDialogMvpPresenter<AllDoctorsDialogMvpView> provideAllDoctorsDialog(AllDoctorsDialogPresenter<AllDoctorsDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    GetStoresDialogMvpPresenter<GetStoresDialogMvpView> provideStoresListDialog(GetStoresDialogPresenter<GetStoresDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    ScannerMvpPresenter<ScannerMvpView> provideScannerActivityPresenter(ScannerPresenter<ScannerMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OrderSummaryMvpPresenter<OrderSummaryMvpView> providesOrderSummaryActivityProvides(OrderSummaryPresenter<OrderSummaryMvpView> presenter) {
        return presenter;
    }
}