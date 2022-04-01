package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

public interface OpenOrdersMvpView extends MvpView {
    void onFullfillmentItemClick(int pos);

    void onRightArrowClickedContinue(int pos);

    void onClickContinue();

    void onSuccessRackApi(RacksDataResponse body);

    void onClickFilterIcon();

    void onClickScanCode();

    void onClickStausIcon(int fullFillmentPos, int pos);
}
