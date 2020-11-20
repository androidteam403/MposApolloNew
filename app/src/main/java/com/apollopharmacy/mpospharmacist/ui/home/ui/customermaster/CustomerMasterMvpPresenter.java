package com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface CustomerMasterMvpPresenter<V extends CustomerMasterMvpView> extends MvpPresenter<V> {
    void onDateClick();

    void onClickSubmit();

    void onClickAnniversary();

    void onClickRegistration();

    void onActionBarBackPressed();

    void handleCustomerAddService( );

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

}
