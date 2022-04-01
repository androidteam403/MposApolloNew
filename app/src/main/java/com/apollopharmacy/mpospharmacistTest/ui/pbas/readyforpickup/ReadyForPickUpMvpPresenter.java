package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

public interface ReadyForPickUpMvpPresenter<V extends ReadyForPickUpMvpView> extends MvpPresenter<V> {
    void onClickStartPickup();

    void onClickBack();

    void onClickTakePrint();

    void onClickStartPickingWithoutQrCode();

    void onClickScanCode();
}
