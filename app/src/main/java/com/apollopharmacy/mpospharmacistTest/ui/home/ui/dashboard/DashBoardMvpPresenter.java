package com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface DashBoardMvpPresenter<V extends DashBoardMvpView> extends MvpPresenter<V> {

    void onClickNewOrder();

    List<RowsEntity> getPosiflextDataListEntity();

    void onDownloadPosiflexCall(String filePath, String fileName, int pos);

    void createPosiFlexFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();

}
