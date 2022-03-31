package com.apollopharmacy.mpospharmacistTest.ui.customerdoctorinfo;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface CustomerDoctorInfoMvpPresenter<V extends CustomerDoctorInfoMvpView> extends MvpPresenter<V> {

    void onClickBackPress();

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();

}
