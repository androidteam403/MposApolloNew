package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface ProductListMvpPresenter<V extends ProductListMvpView> extends MvpPresenter<V> {

    void onClickBackPress();

    void getProductDetails();

    void onVoiceSearchClick();

    void onBarCodeClick();

    String getStoreName();

    String getStoreId();

    String getTerminalId();

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);
}
