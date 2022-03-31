package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;

import java.util.List;

import okhttp3.ResponseBody;

public interface EprescriptionsListMvpPresenter<V extends EprescriptionsListMvpView> extends MvpPresenter<V> {
    void onItemClick(CalculatePosTransactionRes item);

    //void getOrdersDetails();

    // void orderServiceCall(OrderListReq orderListReq);

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();

    void fetchOMSOrderList();

    void onOrderItemClick(int position, OMSTransactionHeaderResModel.OMSHeaderObj item);

    void getTransactionID();

    void getCorporateList();

    void stockavailabilityfilter();

    void onBarCodeClick();

    void noOrderfound(int count);

    void clickOnCustomertypeFilter();

    void clickOnApplyCommonfilter();

    void clickOnCancelCommonfilter();




}
