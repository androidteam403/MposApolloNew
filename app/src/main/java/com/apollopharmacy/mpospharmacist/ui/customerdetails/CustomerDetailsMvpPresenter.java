package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface CustomerDetailsMvpPresenter<V extends CustomerDetailsMvpView> extends MvpPresenter<V> {
    void onAddCustomerClick();

    void onActionBarBackPressed();

    void onCustomerSearchClick();

    void onVoiceSearchClick();

    void onClickSelectBtn(GetCustomerResponse.CustomerEntity customerEntity);

    void onClickEditBtn(GetCustomerResponse.CustomerEntity customerEntity);

    void getCustomerApi(GetCustomerResponse body);

    String getStoreName();

    String getStoreId();

    String getTerminalId();
    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);
}
