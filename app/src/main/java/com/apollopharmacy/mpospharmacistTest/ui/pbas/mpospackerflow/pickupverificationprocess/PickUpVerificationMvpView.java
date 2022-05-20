package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;

import java.util.List;

public interface PickUpVerificationMvpView extends MvpView {
    void onItemClick(int position, RackAdapter.RackBoxModel.ProductData pickPackProductsData);

    void onPartialWarningYesClick();

    void onPartialWarningNoClick();

    void onClickReVerificatio();

    void onClickUpdate(int pos, String refNo);

    void onClickVerification();

    void onSuccessGetOMSTransaction(List<GetOMSTransactionResponse> response);

    void onSuccessGetOMSPhysicalBatch(MedicineBatchResBean response);

    boolean recyclerItemClickableStatus();

    // created by naveen
    void onClickItemUpdate(GetOMSTransactionResponse.SalesLine salesLine, int pos);

    void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse);


}
