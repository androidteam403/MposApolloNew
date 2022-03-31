package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateRequest;

import java.util.ArrayList;
import java.util.List;

public interface EPrescriptionInfoMvpPresenter<V extends EPrescriptionInfoMvpView> extends MvpPresenter<V> {

    void onActionBarBackPressed();

    void fetchOMSCustomerInfo(String refNumber);

    void fetchOMSMedicineInfo(String refNumber);

    void onClickOrderStockFulfilment();

    void onClickCancelOnlineOrder();

    void onClickChangeSite();

    void onClickClose();

    void onClickContinueBilling();

    void onCheckBatchStock(CustomerDataResBean customerDataResBean);

    void  onLoadOmsOrder(CustomerDataResBean customerDataResBean);


    void getBatchDetailsApi(SalesLineEntity selected_item);

    void getBatchDetailsApi_pickpack(SalesLineEntity selected_item, PickPackReservation pickPackReservation);

    //void getBatchDetails_multiplearticles(ArrayList<SalesLineEntity> salesLineEntities);

    void onNavigateNextActivity();

    void checkBatchInventory(GetBatchInfoRes.BatchListObj items, boolean isAlertDialog);

    void UpdateOmsOrder(OMSOrderUpdateRequest request);

    void onClickUpdateOMSOrder_pickingconfirmation();

    void onClickUpdateOMSOrder_unpickingconfirmation();

    void onClickUpdateOMSOrder_packingconfirmation();

    void onClickUpdateOMSOrder_unpackingconfirmation();

    void onclickbarcodereprint();

    void checkeshopshippingcharges();

}
