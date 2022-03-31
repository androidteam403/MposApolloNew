package com.apollopharmacy.mpospharmacistTest.ui.corporatedetails;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiResponse;

public interface CorporateDetailsMvpView extends MvpView {

    void onClickBackPressed();

    void getCorporateList(CorporateModel corporateModel);

    void onClickCorporateItem(CorporateModel.DropdownValueBean item);

    void onSucessPlayList();

    Context getContext();

    void SuccessOnlineorderCorporatelist(GetOnlineCorporateListApiResponse response);

    void NavigatetoMainScreen();
}
