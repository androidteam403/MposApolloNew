package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface OrderSummaryMvpPresenter<V extends OrderSummaryMvpView> extends MvpPresenter<V> {

    void onBackOrderPressed();

    void onNewPlaceOrderClicked();

    String getStoreName();

    String getStoreId();

    String getTerminalId();

    void payLoadDataApi();
    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);
}
