package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface AddDoctorMvpPresenter<V extends AddDoctorMvpView> extends MvpPresenter<V> {

    void onSubmitBtnClick();

    void onActionBarBackPressed();

    void handleAddDoctorService();
    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);
}
