package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;

import java.util.ArrayList;

public interface EPrescriptionInfoMvpView extends MvpView {

    void onClickBackPressed();

    void onSuccessGetOMSTransaction(ArrayList<CustomerDataResBean> response);

    void onSuccessGetOMSPhysicalBatch(MedicineBatchResBean response);

    void onClickOrderStockFulfilment();

    void onClickCancelOnlineOrder();

    void onClickChangeSite();

    void onClickClose();

    void onClickContinueBilling();

    void CheckBatchStockSuccess(CustomerDataResBean response);

    void CheckBatchStockFailure(CustomerDataResBean response);

    void LoadOmsOrderSuccess(CustomerDataResBean response);

    void LoadOmsOrderFailure(CustomerDataResBean response);

    void OmsOrderUpdateSuccess(OMSOrderUpdateResponse response);

    void OmsOrderUpdateFailure(OMSOrderUpdateResponse response);

    void onClickProductItem(int position);

    void onaddItemevent(int position);

    void onSuccessBatchInfo(GetBatchInfoRes body, double mrp);

    void onSuccessBatchInfoEshopChecking(GetBatchInfoRes body, double mrp);

    void onSuccessBatchInfo_pickpack(GetBatchInfoRes body, PickPackReservation pickPackReservation, Double mrp);

    void onFailedBatchInfo(GetBatchInfoRes body);

    void onItemClick(int position, int quantity, GetBatchInfoRes.BatchListObj batchListObj);

    void showtoastmessage(String message);

    void onNavigateNextActivity();

    void checkeshopshippingcharges();

    void onUnpickupcheckActivity(GetBatchInfoRes.BatchListObj item);

    void checkBatchInventorySuccess(boolean isAlertDialog);

    void checkBatchInventoryFailed(String returnMessage);

    // void onItemExpiryClick(int position, int quantity);

    void UpdateOmsOrder_Pickingconfirmation();

    void UpdateOmsOrder_unPickingconfirmation();

    void UpdateOmsOrder_Packingconfirmation();

    void UpdateOmsOrder_unPackingconirmation();

    void barcodereprint();

    void addsaleslineautomatically();

    //  void getrequestedqty();


}
