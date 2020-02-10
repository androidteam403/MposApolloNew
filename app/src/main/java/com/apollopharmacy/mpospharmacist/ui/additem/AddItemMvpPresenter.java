package com.apollopharmacy.mpospharmacist.ui.additem;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface AddItemMvpPresenter<V extends AddItemMvpView> extends MvpPresenter<V> {

    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickBackPressed();
}
