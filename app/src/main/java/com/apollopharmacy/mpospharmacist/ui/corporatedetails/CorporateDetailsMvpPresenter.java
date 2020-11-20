package com.apollopharmacy.mpospharmacist.ui.corporatedetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface CorporateDetailsMvpPresenter<V extends CorporateDetailsMvpView> extends MvpPresenter<V> {

    void getCorporateList();

    void onActionBarBackPressed();

    String getStoreName();

    String getStoreId();

    String getTerminalId();
    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);
}
