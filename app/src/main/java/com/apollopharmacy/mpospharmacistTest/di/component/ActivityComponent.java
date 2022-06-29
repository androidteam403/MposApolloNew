package com.apollopharmacy.mpospharmacistTest.di.component;


import com.apollopharmacy.mpospharmacistTest.di.PerActivity;
import com.apollopharmacy.mpospharmacistTest.di.module.ActivityModule;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.AddCustomerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.adddoctor.AddDoctorActivity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.BatchInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.dialog.DateChangeDialog;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.CircleMembershipCashbackPlan;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.customerdoctorinfo.CustomerDoctorInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog.AllDoctorsDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.EPrescriptionDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.StockNotVailableDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.EprescriptionOrderListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.BillingFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.CustomerMasterFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.DashBoardFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.docmaster.DoctorMasterFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionslistFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.manualbilling.ManualBillingFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.OrdersFragment;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.NewAdminLoginSetUp;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.OrderReturnActivity;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.OrderSummaryActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.EPrescriptionActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.login.LoginActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails.OrderDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.dashboard.DashboardFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummmaryActivityNew;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.PickupSummaryDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.SelectedOrderPickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.splash.SplashActivity;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.StoreSetupActivity;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog.GetStoresDialog;

import dagger.Component;

//import com.apollopharmacy.mpospharmacist.ui.home.ui.cirlcemembership.CircleMembershipFragment;
/*import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptioninfo.EprescriptionInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptioninfo.dialog.EprescriptionDialog;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionlist.EPrescriptionListActivity;*/
//import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionlist.EPrescriptionListFragment;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

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

    void inject(DateChangeDialog dateChangeDialog);

    void inject(CircleMembershipCashbackPlan circleMembershipCashbackPlan);

    void inject(EprescriptionslistFragment eprescriptionslistFragment);

    void inject(EPrescriptionInfoInfoActivity ePrescriptionInfoInfoActivity);

    void inject(EPrescriptionDialog ePrescriptionDialog);

    void inject(StockNotVailableDialog stockNotVailableDialog);

    void inject(EprescriptionOrderListActivity eprescriptionOrderListActivity);

    //picker
    void inject(LoginActivity loginActivity);

    void inject(SelectAppFlowActivity selectAppFlowActivity);

    void inject(com.apollopharmacy.mpospharmacistTest.ui.pbas.main.MainActivity mainActivity);

    void inject(DashboardFragment dashboardFragment);

    void inject(PickerNavigationActivity pickerNavigationActivity);

    void inject(OpenOrdersActivity openOrdersActivity);

    void inject(ReadyForPickUpActivity readyForPickUpActivity);

    void inject(PickupProcessActivity pickupProcessActivity);

    void inject(PickedUpOrdersActivity pickedUpOrdersActivity);

    void inject(PickUpVerificationActivity pickUpVerificationActivity);

    void inject(PickUpSummmaryActivityNew pickUpSummmaryActivityNew);

    void inject(SelectedOrderPickupProcessActivity selectedOrderPickupProcessActivity);

    void inject(OrderDetailsActivity orderDetailsActivity);

    void inject(PickupSummaryDetailsActivity pickupSummaryDetailsActivity);

    void inject(BatchListActivity batchListActivity);

    void inject(BillerOrdersActivity billerOrdersActivity);

    void inject(OrderDetailsScreenActivity orderDetailsScreenActivity);

    void inject(EPrescriptionActivity ePrescriptionActivity);

    void inject(EPrescriptionMedicineDetailsActivity ePrescriptionMedicineDetailsActivity);
}