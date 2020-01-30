package com.apollo.pharmacy.ui.searchpharmacy;

import com.apollo.pharmacy.ui.base.MvpView;
import com.apollo.pharmacy.ui.searchpharmacy.model.Pharmacy;

public interface SearchPharmacyMvpView extends MvpView {

    void activityFinish();

    void onSuccessGetPharmacy(Pharmacy response);

    void onItemClick(Pharmacy.StoreListObj model);

    void onSetUpClick();

    void onNavigateHomeScreen();
}
