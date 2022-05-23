package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

public interface BillerOrdersMvpView extends MvpView {


    void onclickScanCode();

    void onRightArrowClickedContinue(int position);

    void onSuccessRackApi(RacksDataResponse body);
    void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader);
    void onClickFilterIcon();
}
