package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnactivity;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

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

    void onFeedbackformCLick(CalculatePosTransactionRes posTransactionRes);

    void onReOrderClick(CalculatePosTransactionRes posTransactionRes);

    void orderReturnAll(CalculatePosTransactionRes posTransactionRes);

    boolean isAllowOrNot(CalculatePosTransactionRes posTransactionRes);

    void onSalesTrackingApiCall();

    void onMposTabApiCall();

    void feebackapicall(String OrderId, String Rating, String Comment);

    void showfeedbackformDialog(String orderid, String mobilenumber, String storeid);

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();

    boolean DigitalReceiptRequired();

    void onClickBillReprint();

    void downloadPdf(String transactionId, boolean isModifiedTransactionId);
    String getLastTransactionId();
}
