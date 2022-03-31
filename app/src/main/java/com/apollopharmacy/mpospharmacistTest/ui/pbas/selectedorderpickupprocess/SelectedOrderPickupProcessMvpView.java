package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

public interface SelectedOrderPickupProcessMvpView extends MvpView {
    void onClickBack();

    void onRackApiCall();

    void onSuccessRackApi(RacksDataResponse racksDataResponse);

    void onClickStausIcon();

    void onClickFullPicked();

    void onClickPartialPicked();

    void onClickNotAvailable();

    void onClickSkip();

    void onClickBatchDetails();

    void statusSpinnerCallback(int status);

    void onClickItemStatusDropdown();
}
