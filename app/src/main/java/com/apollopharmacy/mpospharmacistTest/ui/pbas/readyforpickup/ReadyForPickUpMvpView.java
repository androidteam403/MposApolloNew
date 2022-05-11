package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface ReadyForPickUpMvpView extends MvpView {
    void onTagBoxClick(String fullfillmentId, int position);

    void onDeleteClick(int position, String fullfillmentId, String s);

    void onClickStartPickup();

    void onClickBack();
    void cancel();
//


    void onClickTakePrint();

    void onClickStartPickingWithoutQrCode();

    void onClickScanCode();
}
