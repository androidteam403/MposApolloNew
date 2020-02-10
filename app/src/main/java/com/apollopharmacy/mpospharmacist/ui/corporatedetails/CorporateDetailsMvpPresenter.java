package com.apollopharmacy.mpospharmacist.ui.corporatedetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface CorporateDetailsMvpPresenter<V extends CorporateDetailsMvpView> extends MvpPresenter<V> {

    void getCorporateList();

    void onActionBarBackPressed();
}
