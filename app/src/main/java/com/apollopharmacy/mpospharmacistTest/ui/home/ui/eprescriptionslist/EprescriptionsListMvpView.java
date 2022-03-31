package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

public interface EprescriptionsListMvpView extends MvpView {

    //  void onItemClick(CalculatePosTransactionRes item);

    //void onSuccessOrderList(ArrayList<CalculatePosTransactionRes> orderListRes);

    void noDataFound();

    //void onClickApplyFilters();

    void onSucessPlayList();

    Context getContext();

    void onSuccessGetOMSTransactionList(OMSTransactionHeaderResModel response);

    void onOrderItemClick(int position, OMSTransactionHeaderResModel.OMSHeaderObj item);

    void showTransactionID(TransactionIDResModel model);

    void getCorporateList(CorporateModel corporateModel);



    void stockavailability_filter();

    void onBarCodeClick();

    void updateProductsCount(int count);

    void customertypefilter();

    void clickOnApplyCommonfilter();

    void clickOnCancelCommonfilter();


}
