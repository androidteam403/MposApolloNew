package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;

import java.util.List;

public interface AdminMvpView extends MvpView {
    void onSuccessGetOmsTransactionHeader(List<GetOMSTransactionHeaderResponse.OMSHeader> body);

    void onClickAllOrders();
}
