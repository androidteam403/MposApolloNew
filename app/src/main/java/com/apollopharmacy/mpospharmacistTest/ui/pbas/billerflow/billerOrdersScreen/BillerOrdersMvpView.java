package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

public interface BillerOrdersMvpView extends MvpView {


    void onclickScanCode();

    void onRightArrowClickedContinue(String refId);

    void noOrderFound(int count);

    void onSuccessRackApi(RacksDataResponse body);

    void onSucessfullFulfilmentIdList(OMSTransactionHeaderResModel omsHeader);

    void onClickFilterIcon();
}
