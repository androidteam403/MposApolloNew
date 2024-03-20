package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;

import java.util.List;

public interface AllOrdersMvpView extends MvpView {
    void onClickBack();
    void onClickOrder(int position);

    void onSuccessGetOmsTransactionHeader(List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList);
}
