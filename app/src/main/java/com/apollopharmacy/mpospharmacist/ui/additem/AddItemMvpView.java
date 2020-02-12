package com.apollopharmacy.mpospharmacist.ui.additem;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface AddItemMvpView extends MvpView {

    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickActionBarBack();

    void onClearAll();

    void onPayButtonClick();
}
