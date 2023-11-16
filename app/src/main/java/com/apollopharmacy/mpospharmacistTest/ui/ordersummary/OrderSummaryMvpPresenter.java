package com.apollopharmacy.mpospharmacistTest.ui.ordersummary;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;

import java.util.List;

import okhttp3.ResponseBody;

public interface OrderSummaryMvpPresenter<V extends OrderSummaryMvpView> extends MvpPresenter<V> {

    void onBackOrderPressed();

    void onNewPlaceOrderClicked();

    void onDownloadPdfButton();

    String getStoreName();

    String getStoreId();

    String getTerminalId();

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();

    void downloadPdf(String transactionId);

    void onClickBillPrint();

    GetGlobalConfingRes getGlobalConfing();

    boolean isV1Flow();

    void setTransactionId(String transactionId);
}
