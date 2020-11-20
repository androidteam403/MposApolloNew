package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;

import java.util.List;

import okhttp3.ResponseBody;

public interface OrderReturnMvpPresenter<V extends OrederReturnMvpView> extends MvpPresenter<V> {

    void onClickBackPressed();

    GetGlobalConfingRes getGlobalConfing();

    String terminalId();

    void trackingWiseReturnAllowed(String corpId);

    void getTransactionID();

    void cancelDSBilling(CalculatePosTransactionRes posTransactionRes);

    void onReturnClick(CalculatePosTransactionRes posTransactionRes);

    void onCancelCLick(CalculatePosTransactionRes posTransactionRes);

    void onReOrderClick(CalculatePosTransactionRes posTransactionRes);

    void orderReturnAll(CalculatePosTransactionRes posTransactionRes);

    boolean isAllowOrNot(CalculatePosTransactionRes posTransactionRes);

    void onSalesTrackingClick();

    void onSalesTrackingApiCall();
    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);
}
