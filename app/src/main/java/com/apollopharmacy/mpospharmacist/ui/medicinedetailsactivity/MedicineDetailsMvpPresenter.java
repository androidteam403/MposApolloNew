package com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface MedicineDetailsMvpPresenter<V extends MedicineDetailsMvpView> extends MvpPresenter<V> {
    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onBackPressClick();
}
