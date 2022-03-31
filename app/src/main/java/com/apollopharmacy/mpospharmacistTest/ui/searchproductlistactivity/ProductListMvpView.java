package com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.model.GetItemDetailsRes;

public interface ProductListMvpView extends MvpView {

    void onClickBackBtn();

    void onClickProductItem(GetItemDetailsRes.Items item);

    String getSearchProductKey();

    CorporateModel.DropdownValueBean getCorporateValue();

    void setEmptyErrorOnSearch(String message);

    void onSuccessGetItems(GetItemDetailsRes itemDetailsRes);

    void onFailedGetItems(GetItemDetailsRes itemDetailsRes);

    void updateProductsCount(int count);

    void onVoiceSearchClick();

    void onBarCodeClick();

    void onSucessPlayList();

    Context getContext();
}
