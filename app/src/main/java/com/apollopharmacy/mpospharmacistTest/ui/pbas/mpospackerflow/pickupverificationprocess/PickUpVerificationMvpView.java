package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;

import java.util.ArrayList;

public interface PickUpVerificationMvpView extends MvpView {
    void onItemClick(int position, RackAdapter.RackBoxModel.ProductData pickPackProductsData);

    void onPartialWarningYesClick();

    void onPartialWarningNoClick();

    void onClickReVerificatio();

    void onClickVerification();
    void onSuccessGetOMSTransaction(ArrayList<CustomerDataResBean> response);
    void onSuccessGetOMSPhysicalBatch(MedicineBatchResBean response);

    boolean recyclerItemClickableStatus();
}
