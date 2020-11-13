package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface DashBoardMvpPresenter<V extends DashBoardMvpView> extends MvpPresenter<V> {

    void onClickNewOrder();

    void onPlayListApiCall();

    void onDownloadApiCall(String filePath, String fileName,int pos);

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile,int pos);

    void showLoader();

    List<RowsEntity> getDataListEntity();

}
