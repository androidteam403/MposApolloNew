package com.apollopharmacy.mpospharmacist.ui.home.ui.billing;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface BillingMvpPresenter<V extends BillingMvpView> extends MvpPresenter<V> {

    void onCustomerSearchClick();

    void onDoctorSearchClick();

    void onCorporateSearchClick();

    void onActionBarBackPress();

    void onClickCustomerEdit(GetCustomerResponse.CustomerEntity customerEntity);

    void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity);

    void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity);

    void onContinueBtnClick();

    void getTransactionID();

    void getCorporateList();

    void getDoctorsList();

    void getSalesOrigin();

    String getStoreName();

    String getStoreId();

    String getTerminalId();

    void getPharmacyStaffApiDetails(String action);

    void onUploadApiCall();

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

}
