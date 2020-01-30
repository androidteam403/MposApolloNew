package com.apollo.pharmacy.ui.searchpharmacy;

import com.apollo.pharmacy.ui.base.MvpPresenter;
import com.apollo.pharmacy.ui.searchpharmacy.model.Pharmacy;

public interface SearchPharmacyMvpPresenter<V extends SearchPharmacyMvpView> extends MvpPresenter<V> {

    void finishActivity();

    void getPharmacyList();

    void onPharmacyItemClick(Pharmacy.StoreListObj model);

    void onSetUpClick();

    void insertAdminLoginDetails();
}
