package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.util.List;

public interface PickerNavigationMvpView extends MvpView {

    void navigateLoginActivity();
    void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body);

    void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList);
}
