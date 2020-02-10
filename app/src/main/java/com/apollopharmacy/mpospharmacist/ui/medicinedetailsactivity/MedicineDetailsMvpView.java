package com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface MedicineDetailsMvpView extends MvpView {
    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickBackBtn();

    void onPayButtonClick();
}
