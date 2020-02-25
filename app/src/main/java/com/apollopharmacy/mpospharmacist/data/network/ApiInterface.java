package com.apollopharmacy.mpospharmacist.data.network;

import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerReqModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorReqModel;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerRequest;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineReq;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.pay.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model.AdminLoginReqModel;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model.AdminLoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginReqModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsReq;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupReqModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreListResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("apollompos/Self/LOGIN")
    Call<AdminLoginResModel> LOGIN_SERVICE_CALL(@Body AdminLoginReqModel adminLoginReqModel);

    @GET("apollompos/Self/STORELIST")
    Call<StoreListResponseModel> GET_STORES_LIST();

    @POST("apollompos/Self/Registration")
    Call<DeviceSetupResModel> STORE_SETUP_CALL(@Body DeviceSetupReqModel setupReqModel);

    @GET("apollompos/Self/CampaignDetails/{storeId}")
    Call<CampaignDetailsRes> CAMPAIGN_DETAILS_RES_CALL(@Path("storeId") String storeId);

    @POST("LoginService.svc/GetLoginUser/16001/AHEL")
    Call<UserModel> getUserIds(@Body Object o);

    @POST("LoginService.svc/GetLoginDetail")
    Call<LoginResModel> LOGIN_RES_MODEL_CALL(@Body LoginReqModel loginReqModel);

    @POST("SalesTransactionService.svc/GetCustomer")
    Call<GetCustomerResponse> GET_CUSTOMER_REQUEST_CALL(@Body GetCustomerRequest customerRequest);

    @POST("SalesTransactionService.svc/GetItemDetails")
    Call<GetItemDetailsRes> GET_ITEM_DETAILS_RES_CALL(@Body GetItemDetailsReq itemDetailsReq);

    @POST("SalesTransactionService.svc/GetTrackingRefence/16001/AHEL")
    Call<CorporateModel> getCorporateList(@Body Object o);

    @POST("SalesTransactionService.svc/GetDoctorList/16001/AHEL")
    Call<DoctorSearchResModel> getDoctorsList(@Body DoctorSearchReqModel doctorSearchReqModel);

    @POST("SalesTransactionService.svc/GetSalesOrigin/AHEL")
    Call<SalesOriginResModel> getSalesOriginList(@Body Object o);

    @POST("SalesTransactionService.svc/GetTransactionId")
    Call<TransactionIDResModel> GET_TRANSACTION_ID(@Body TransactionIDReqModel transactionIDReqModel);

    @POST("SalesTransactionService.svc/GetBatchDetails")
    Call<GetBatchInfoRes> GET_BATCH_INFO_RES_CALL (@Body GetBatchInfoReq getBatchInfoReq);

    @POST("SalesTransactionService.svc/GenerateTenderLine/69")
    Call<GenerateTenderLineRes> GENERATE_TENDER_LINE_RES_CALL (@Body GenerateTenderLineReq tenderLineReq);

    @POST("SalesTransactionService.svc/SaveRetailTransaction")
    Call<SaveRetailsTransactionRes> SAVE_RETAILS_TRANSACTION_RES_CALL (@Body GenerateTenderLineRes.GenerateTenderLineResultEntity tenderLineReq);

    @POST("SalesTransactionService.svc/GetGlobalConfigration/16001/ahel")
    Call<GetGlobalConfingRes> GET_GLOBAL_CONFING_RES_CALL (@Body Object o);

    @POST("SalesTransactionService.svc/DoctorCreation")
    Call<AddDoctorResModel> ADD_DOCTOR_SERVICE(@Body AddDoctorReqModel addDoctorReqModel);

    @POST("SalesTransactionService.svc/CustomerCreation")
    Call<AddCustomerResModel> ADD_CUSTOMER_SERVICE(@Body AddCustomerReqModel addCustomerReqModel);
}
