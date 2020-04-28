package com.apollopharmacy.mpospharmacist.data.network;

import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorValidationReq;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerReqModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.OTPRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsReqModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.CheckBatchInventoryRes;
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
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GenerateTenderLineReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SaveRetailsTransactionRes;
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
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.ConfingReq;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.ConfingRes;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupReqModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreListResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("apollompos/Self/LOGIN")
    Call<AdminLoginResModel> LOGIN_SERVICE_CALL(@Body AdminLoginReqModel adminLoginReqModel);

    @GET("apollompos/Self/STORELIST")
    Call<StoreListResponseModel> GET_STORES_LIST();

    @POST("apollompos/Self/Registration")
    Call<DeviceSetupResModel> STORE_SETUP_CALL(@Body DeviceSetupReqModel setupReqModel);

    @GET("apollompos/Self/CampaignDetails/{storeId}")
    Call<CampaignDetailsRes> CAMPAIGN_DETAILS_RES_CALL(@Path("storeId") String storeId);

    @POST("LoginService.svc/GetLoginUser/{storeId}/{DataAreaId}")
    Call<UserModel> getUserIds(@Path("storeId") String storeId,@Path("DataAreaId") String dataAreaId,@Body Object o);

    @POST("LoginService.svc/GetLoginDetail")
    Call<LoginResModel> LOGIN_RES_MODEL_CALL(@Body LoginReqModel loginReqModel);

    @POST("SalesTransactionService.svc/GetCustomer")
    Call<GetCustomerResponse> GET_CUSTOMER_REQUEST_CALL(@Body GetCustomerRequest customerRequest);

    @POST("SalesTransactionService.svc/GetItemDetails")
    Call<GetItemDetailsRes> GET_ITEM_DETAILS_RES_CALL(@Body GetItemDetailsReq itemDetailsReq);

    @POST("SalesTransactionService.svc/GetTrackingRefence/{storeId}/{DataAreaId}")
    Call<CorporateModel> getCorporateList(@Path("storeId") String storeId,@Path("DataAreaId") String dataAreaId,@Body Object o);

    @POST("SalesTransactionService.svc/GetDoctorList/{storeId}/{DataAreaId}")
    Call<DoctorSearchResModel> getDoctorsList(@Path("storeId") String storeId,@Path("DataAreaId") String dataAreaId,@Body DoctorSearchReqModel doctorSearchReqModel);

    @POST("SalesTransactionService.svc/GetSalesOrigin/{DataAreaId}")
    Call<SalesOriginResModel> getSalesOriginList(@Path("DataAreaId") String dataAreaId,@Body Object o);

    @POST("SalesTransactionService.svc/GetTransactionId")
    Call<TransactionIDResModel> GET_TRANSACTION_ID(@Body TransactionIDReqModel transactionIDReqModel);

    @POST("SalesTransactionService.svc/GetBatchDetails")
    Call<GetBatchInfoRes> GET_BATCH_INFO_RES_CALL (@Body GetBatchInfoReq getBatchInfoReq);

    @POST("SalesTransactionService.svc/CheckBatchInventory")
    Call<CheckBatchInventoryRes> CHECK_BATCH_INVENTORY_RES_CALL (@Body CheckBatchInventoryReq batchInventoryReq);

    @POST("SalesTransactionService.svc/GenerateTenderLine/{totalAmount}")
    Call<GenerateTenderLineRes> GENERATE_TENDER_LINE_RES_CALL (@Path("totalAmount") double amount,@Body GenerateTenderLineReq tenderLineReq);

    @POST("SalesTransactionService.svc/SaveRetailTransaction")
    Call<SaveRetailsTransactionRes> SAVE_RETAILS_TRANSACTION_RES_CALL (@Body CalculatePosTransactionRes tenderLineReq);

    @POST("SalesTransactionService.svc/GetGlobalConfigration/{storeId}/{DataAreaId}")
    Call<GetGlobalConfingRes> GET_GLOBAL_CONFING_RES_CALL (@Path("storeId") String storeId,@Path("DataAreaId") String dataAreaId,@Body Object o);

    @POST("SalesTransactionService.svc/DoctorCreation")
    Call<AddDoctorResModel> ADD_DOCTOR_SERVICE(@Body AddDoctorReqModel addDoctorReqModel);

    @POST("SalesTransactionService.svc/CustomerCreation")
    Call<AddCustomerResModel> ADD_CUSTOMER_SERVICE(@Body AddCustomerReqModel addCustomerReqModel);

    @POST("SalesTransactionService.svc/CalculatePosTransaction")
    Call<CalculatePosTransactionRes> CALCULATE_POS_TRANSACTION_RES_CALL (@Body POSTransactionEntity posTransactionReq);

    @POST("SalesTransactionService.svc/VoidTransaction")
    Call<CalculatePosTransactionRes> VOID_TRANSACTION (@Body CalculatePosTransactionRes posTransactionReq);

    @POST("SalesTransactionService.svc/VoidProduct/{Item_line}")
    Call<CalculatePosTransactionRes> VOID_PRODUCT (@Path("Item_line") int line,@Body CalculatePosTransactionRes posTransactionReq);

    @POST("SalesTransactionService.svc/GetUnpostedTransaction/{storeId}/{terminalId}/{DataAreaId}")
    Call<CalculatePosTransactionRes> GET_UNPOSTED_TRANSACTION (@Path("storeId") String storeId,@Path("terminalId") String terminalId, @Path("DataAreaId") String dataAreaId,@Body  Object o );

    @POST("WalletService.svc/OneApolloAPITransaction")
    Call<ValidatePointsResModel> VALIDATE_ONE_APOLLO_POINTS(@Body ValidatePointsReqModel validatePointsReqModel);

    @POST("WalletService.svc/OneApolloAPITransaction")
    Call<ValidatePointsResModel> ONE_APOLLO_SEND_OTP_RES_CALL (@Body ValidatePointsReqModel oneApolloSendOtpReq);

    @POST("LoginService.svc/CheckUserConfig")
    Call<ConfingRes> CONFING_RES_CALL (@Body ConfingReq confingReq);

    @POST("SalesTransactionService.svc/GetTransactionDetails")
    Call<ArrayList<CalculatePosTransactionRes>> ORDER_LIST_RES_CALL (@Body OrderListReq orderListReq);

    @POST("SalesTransactionService.svc/GetTenderType/{storeId}/{DataAreaId}")
    Call<GetTenderTypeRes>  GET_TENDER_TYPE_RES_CALL (@Path("storeId") String storeId,@Path("DataAreaId") String dataAreaId,@Body  Object o);

    @POST("SalesTransactionService.svc/ApplyMannualDiscount")
    Call<ManualDiscCheckRes> MANUAL_DISC_CHECK_RES_CALL (@Body ManualDiscCheckReq manualDiscCheckReq);

    @POST
    Call<OTPRes> GENERATE_OTP_RES_CALL (@Url String url);

    @POST
    Call<VendorCheckRes> VENDOR_CHECK_RES_CALL(@Url String url, @Body VendorValidationReq requestBody);

}
