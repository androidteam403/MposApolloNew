package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface DoctorDetailsMvpPresenter<V extends DoctorDetailsMvpView> extends MvpPresenter<V> {
    void onAddDoctorClick();

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
}
