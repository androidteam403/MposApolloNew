package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetPrDetailsApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetUniversalDropDownBindResponse;

public interface StockInwardProcessDetailsMvpView extends MvpView {
    void onClickBack();

    void onSuccessUniversalDropDownDetails(GetUniversalDropDownBindResponse body);

    void onClickDropDown(int position);

    void onClickDropDownItem(String text, int positionItem);

    void onSuccessPrDetails(GetPrDetailsApiResponse body);
}
