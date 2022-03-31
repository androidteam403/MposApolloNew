package com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface CustomerMasterMvpPresenter<V extends CustomerMasterMvpView> extends MvpPresenter<V> {
    void onDateClick();

    void onClickSubmit();

   // void onClickAnniversary();

    void onActionBarBackPressed();

    void handleCustomerAddService( );

    //void showOTPPopUp();

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();

    void sendSmsservice(String mobilenumber);

}
