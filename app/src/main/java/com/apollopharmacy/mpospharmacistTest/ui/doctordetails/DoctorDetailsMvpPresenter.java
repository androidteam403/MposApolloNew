package com.apollopharmacy.mpospharmacistTest.ui.doctordetails;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface DoctorDetailsMvpPresenter<V extends DoctorDetailsMvpView> extends MvpPresenter<V> {

    void onBackPressedClick();

    void onAllDoctorsClick();

    void getDoctorsList();

    void getSalesOrigin();

    void getAllDoctorsList();

    void onSubmitClick();

    void onCustomDoctorLayoutClick();

    String getStoreName();

    String getStoreId();

    String getTerminalId();

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();
}
