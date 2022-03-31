package com.apollopharmacy.mpospharmacistTest.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.apollopharmacy.mpospharmacistTest.di.ActivityContext;
import com.apollopharmacy.mpospharmacistTest.di.PerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.AddCustomerMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.AddCustomerMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.AddCustomerPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.adddoctor.AddDoctorMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.adddoctor.AddDoctorMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.adddoctor.AddDoctorPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.BatchInfoMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.BatchInfoMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.BatchInfoPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.dialog.DateChangeMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.dialog.DateChangeMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.dialog.DateChangePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.CorporateDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.CorporateDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.CorporateDetailsPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.CustomerDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.CustomerDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.CustomerDetailsPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.customerdoctorinfo.CustomerDoctorInfoMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.customerdoctorinfo.CustomerDoctorInfoMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.customerdoctorinfo.CustomerDoctorInfoPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.DoctorDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.DoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.DoctorDetailsPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog.AllDoctorsDialogMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog.AllDoctorsDialogMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog.AllDoctorsDialogPresenter;


import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.EPrescriptionDialogMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.EPrescriptionDialogMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.EPrescriptionDialogPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.StocknotAvailableDialogMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.StocknotAvailableDialogMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.StocknotAvailableDialogPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.EprescriptionOrderListMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.EprescriptionOrderListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.EprescriptionOrderListPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivityMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivityMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivityPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.BillingMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.BillingMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.BillingPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.CustomerMasterMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.CustomerMasterMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.CustomerMasterPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.DashBoardMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.DashBoardMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.DashBoardPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.docmaster.DoctorMasterMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.docmaster.DoctorMasterMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.docmaster.DoctorMasterPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionslistPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.manualbilling.ManualBillingMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.manualbilling.ManualBillingMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.manualbilling.ManualBillingPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.OrdersMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.OrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.OrdersPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.NewAdminLoginMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.NewAdminLoginMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.NewAdminLoginPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.OrderReturnMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.OrderReturnPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.OrederReturnMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.OrderSummaryMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.OrderSummaryMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.OrderSummaryPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.login.LoginMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.login.LoginMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.login.LoginPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.main.MainMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.main.MainMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.main.MainPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.main.RssAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails.OrderDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails.OrderDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails.OrderDetailsPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.dashboard.DashboardMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.dashboard.DashboardMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.dashboard.DashboardPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummaryMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummaryMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummaryPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.PickUpSummaryDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.PickUpSummaryDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.PickUpSummaryDetailsPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.SelectedOrderPickupProcessMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.SelectedOrderPickupProcessMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.SelectedOrderPickupProcessPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.ProductListMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.ProductListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.ProductListPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.splash.SplashMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.splash.SplashMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.splash.SplashPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.StoreSetupMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.StoreSetupMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.StoreSetupPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog.GetStoresDialogMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog.GetStoresDialogMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog.GetStoresDialogPresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.AppSchedulerProvider;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import java.util.ArrayList;

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
    EprescriptionsListMvpPresenter<EprescriptionsListMvpView> provideEprescriptionslistMvpPresenter(EprescriptionslistPresenter<EprescriptionsListMvpView> presenter) {
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
    EPrescriptionInfoMvpPresenter<EPrescriptionInfoMvpView> providerEPrescriptionActivity(EPrescriptionInfoPresenter<EPrescriptionInfoMvpView> presenter) {
        return presenter;
    }

    @Provides
    EPrescriptionDialogMvpPresenter<EPrescriptionDialogMvpView> providerEPrescriptionDialog(EPrescriptionDialogPresenter<EPrescriptionDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    StocknotAvailableDialogMvpPresenter<StocknotAvailableDialogMvpView> StockNotVailableDialog(StocknotAvailableDialogPresenter<StocknotAvailableDialogMvpView> presenter) {
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

    @Provides
    @PerActivity
    DateChangeMvpPresenter<DateChangeMvpView> dateChaneActivityProvides(DateChangePresenter<DateChangeMvpView> presenter) {
        return presenter;
    }

    @Provides
    EprescriptionOrderListMvpPresenter<EprescriptionOrderListMvpView> eprescriptionOrderListActivityProvides(EprescriptionOrderListPresenter<EprescriptionOrderListMvpView> presenter) {
        return presenter;
    }

   /*@Provides
    EPrescriptionListMvpPresenter<EPrescriptionListMvpView> ProvideseprescriptionListMvpPresenter(EPrescriptionListMvpPresenter<EPrescriptionListMvpView> presenter){
        return  presenter;
    }*/

    // picker
    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DashboardMvpPresenter<DashboardMvpView> provideDashboardPresenter(DashboardPresenter<DashboardMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OpenOrdersMvpPresenter<OpenOrdersMvpView> provideOpenOrdersPresenter(OpenOrdersPresenter<OpenOrdersMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OrderDetailsMvpPresenter<OrderDetailsMvpView> OrderDetailsPresenter(OrderDetailsPresenter<OrderDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ReadyForPickUpMvpPresenter<ReadyForPickUpMvpView> readyForPickUpPresenter(ReadyForPickUpPresenter<ReadyForPickUpMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BillerOrdersMvpPresenter<BillerOrdersMvpView> billerOrdersPickupPresenter(BillerOrdersPresenter<BillerOrdersMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PickedUpOrdersMvpPresenter<PickedUpOrdersMvpView> pickedUpOrdersMvpPresenter(PickedUpOrdersPresenter<PickedUpOrdersMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    PickUpVerificationMvpPresenter<PickUpVerificationMvpView> pick(PickUpVerificationPresenter<PickUpVerificationMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PickUpSummaryMvpPresenter<PickUpSummaryMvpView> pickUpSummary(PickUpSummaryPresenter<PickUpSummaryMvpView> presenter) {
        return presenter;
    }

    @Provides
    RssAdapter provideRssAdapter() {
        return new RssAdapter(new ArrayList<>());
    }

    @Provides
    @PerActivity
    PickupProcessMvpPresenter<PickupProcessMvpView> providePickupProcessPresenter(PickupProcessPresenter<PickupProcessMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SelectedOrderPickupProcessMvpPresenter<SelectedOrderPickupProcessMvpView> provideSelectedOrderPickupProcessPresenter(SelectedOrderPickupProcessPresenter<SelectedOrderPickupProcessMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PickUpSummaryDetailsMvpPresenter<PickUpSummaryDetailsMvpView> providePickUpSummaryDetailsPresenter(PickUpSummaryDetailsPresenter<PickUpSummaryDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BatchListMvpPresenter<BatchListMvpView> provideBatchListPresenter(BatchListPresenter<BatchListMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PickerNavigationMvpPresenter<PickerNavigationMvpView> providePickerNavigationPresenter(PickerNavigationPresenter<PickerNavigationMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SelectAppFlowMvpPresenter<SelectAppFlowMvpView> provideSelectAppFlowPresenter(SelectAppFlowPresenter<SelectAppFlowMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OrderDetailsScreenMvpPresenter<OrderDetailsScreenMvpView> orderDetailsScreenPresenter(OrderDetailsScreenPresenter<OrderDetailsScreenMvpView> presenter) {
        return presenter;
    }
}