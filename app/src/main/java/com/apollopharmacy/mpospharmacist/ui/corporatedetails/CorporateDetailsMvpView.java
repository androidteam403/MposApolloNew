package com.apollopharmacy.mpospharmacist.ui.corporatedetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;

public interface CorporateDetailsMvpView extends MvpView {

    void onClickBackPressed();

    void getCorporateList(CorporateModel corporateModel);

    void showNotFoundCorporate();
}
