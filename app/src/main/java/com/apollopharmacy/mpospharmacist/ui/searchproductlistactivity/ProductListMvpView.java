package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.ProductList;

public interface ProductListMvpView extends MvpView {

    void onClickBackBtn();

    void onClickProductItem(GetItemDetailsRes.Items item);

    String getSearchProductKey();

    CorporateModel.DropdownValueBean getCorporateValue();

    void setEmptyErrorOnSearch(String message);

    void onSuccessGetItems(GetItemDetailsRes itemDetailsRes);

    void onFailedGetItems(GetItemDetailsRes itemDetailsRes);

    void updateProductsCount(int count);
}
