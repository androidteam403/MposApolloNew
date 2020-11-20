package com.apollopharmacy.mpospharmacist.ui.home;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface MainActivityMvpPresenter<V extends MainActivityMvpView> extends MvpPresenter<V> {
    String getLoginUserName();

    String getLoinStoreLocation();

    void logoutUser();

    void onCheckBuildDetails();

    void getCorporateList();

    void getUnpostedTransaction();

    boolean isKisokMode();

    void setKioskMode(boolean isKioskMode);

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);
}
