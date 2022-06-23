package com.apollopharmacy.mpospharmacistTest.data.network;

import com.apollopharmacy.mpospharmacistTest.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.VendorValidationReq;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacistTest.ui.adddoctor.model.AddDoctorReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CircleMemebershipCashbackPlanResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CouponDiscount;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GenerateTenderLineReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetSMSPayAPIRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetSMSPayAPIResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.HdfcLinkGenerateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.HdfcLinkGenerateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OTPRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OmsAddNewItemRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OmsAddNewItemResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffAPIReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PhonepeGenerateQrCodeRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PhonepeGenerateQrCodeResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SendGlobalMessageRequest;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SendGlobalMessageResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ValidatePointsReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.expirymodel.ExpiryChangeReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.expirymodel.ExpiryChangeRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiRequest;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerRequest;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataReqBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchReqBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.GetHBPUHIDDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.GetHBPUHIDDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.model.AdminLoginReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.model.AdminLoginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.FeedBackRequest;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.FeedBackResponse;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.SalesTrackingDataReq;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.TrackingWiseReturnAllowedRes;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PayLoadRequest;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PayLoadRes;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelRequest;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.model.CalculatePosTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.model.PostTransactionEntityReq;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.CheckBatchModelRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.CheckBatchModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOmsTransactionRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.AllowedPaymentModeRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.HBPConfigResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.LoginReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.model.GetItemDetailsReq;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.ConfingReq;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.ConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.DeviceSetupReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreListResponseModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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
    Call<UserModel> getUserIds(@Path("storeId") String storeId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("LoginService.svc/GetLoginDetail")
    Call<LoginResModel> LOGIN_RES_MODEL_CALL(@Body LoginReqModel loginReqModel);

    @POST("SalesTransactionService.svc/GetCustomer")
    Call<GetCustomerResponse> GET_CUSTOMER_REQUEST_CALL(@Body GetCustomerRequest customerRequest);

    @POST("SalesTransactionService.svc/GetItemDetails")
    Call<GetItemDetailsRes> GET_ITEM_DETAILS_RES_CALL(@Body GetItemDetailsReq itemDetailsReq);

    @POST("SalesTransactionService.svc/GetTrackingRefence/{storeId}/{DataAreaId}")
    Call<CorporateModel> getCorporateList(@Path("storeId") String storeId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("SalesTransactionService.svc/GetDoctorList/{storeId}/{DataAreaId}")
    Call<DoctorSearchResModel> getDoctorsList(@Path("storeId") String storeId, @Path("DataAreaId") String dataAreaId, @Body DoctorSearchReqModel doctorSearchReqModel);

    @POST("SalesTransactionService.svc/GetSalesOrigin/{DataAreaId}")
    Call<SalesOriginResModel> getSalesOriginList(@Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("SalesTransactionService.svc/GetTransactionId")
    Call<TransactionIDResModel> GET_TRANSACTION_ID(@Body TransactionIDReqModel transactionIDReqModel);

    @POST("SalesTransactionService.svc/GetBatchDetails")
    Call<GetBatchInfoRes> GET_BATCH_INFO_RES_CALL(@Body GetBatchInfoReq getBatchInfoReq);

    @POST("SalesTransactionService.svc/CheckBatchInventory")
    Call<CheckBatchInventoryRes> CHECK_BATCH_INVENTORY_RES_CALL(@Body CheckBatchInventoryReq batchInventoryReq);

    @POST("SalesTransactionService.svc/ExpiryChange")
    Call<ExpiryChangeRes> EXPIRY_CHANGE_RES_CALL(@Body ExpiryChangeReq expiryChangeReq);

    @POST("SalesTransactionService.svc/GenerateTenderLine/{totalAmount}")
    Call<GenerateTenderLineRes> GENERATE_TENDER_LINE_RES_CALL(@Path("totalAmount") double amount, @Body GenerateTenderLineReq tenderLineReq);

    @POST("WalletService.svc/GetPharmacyStaffAPIDetails")
    Call<PharmacyStaffApiRes> PHARMACY_STAFF_API_RES_CALL(@Body PharmacyStaffAPIReq tenderLineReq);

    @POST("SalesTransactionService.svc/SaveRetailTransaction")
    Call<SaveRetailsTransactionRes> SAVE_RETAILS_TRANSACTION_RES_CALL(@Body CalculatePosTransactionRes tenderLineReq);

    @POST("SalesTransactionService.svc/GetGlobalConfigration/{storeId}/{treminalId}/{DataAreaId}")
    Call<GetGlobalConfingRes> GET_GLOBAL_CONFING_RES_CALL(@Path("storeId") String storeId, @Path("treminalId") String terminalId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("SalesTransactionService.svc/GetHBPConfigration/{storeId}/{treminalId}/{DataAreaId}")
    Call<HBPConfigResponse> GET_HBP_CONFING_RES_CALL(@Path("storeId") String storeId, @Path("treminalId") String terminalId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("SalesTransactionService.svc/GetTrackingWiseConfigration/{storeId}/{DataAreaId}")
    Call<GetTrackingWiseConfing> GET_TRACKING_WISE_CONFING_CALL(@Path("storeId") String storeId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("SalesTransactionService.svc/DoctorCreation")
    Call<AddDoctorResModel> ADD_DOCTOR_SERVICE(@Body AddDoctorReqModel addDoctorReqModel);

    @POST("SalesTransactionService.svc/CustomerCreation")
    Call<AddCustomerResModel> ADD_CUSTOMER_SERVICE(@Body AddCustomerReqModel addCustomerReqModel);

    @POST("SalesTransactionService.svc/CalculatePosTransaction")
    Call<CalculatePosTransactionRes> CALCULATE_POS_TRANSACTION_RES_CALL(@Body POSTransactionEntity posTransactionReq);

    @POST("SalesTransactionService.svc/ChangeQty/{lineNumber}/{quantity}")
    Call<CalculatePosTransactionRes> CHANGE_QUANTITY_RES_CALL(@Path("lineNumber") int lineNumber, @Path("quantity") double quantity, @Body POSTransactionEntity posTransactionReq);

    @POST("SalesTransactionService.svc/CheckProductTrackingWise")
    Call<CalculatePosTransactionRes> CHECK_PRODUCT_TRACKING_WISE_RES_CALL(@Body POSTransactionEntity posTransactionReq);

    @POST("SalesTransactionService.svc/VoidTransaction")
    Call<CalculatePosTransactionRes> VOID_TRANSACTION(@Body CalculatePosTransactionRes posTransactionReq);

    @POST("SalesTransactionService.svc/VoidProduct/{Item_line}")
    Call<CalculatePosTransactionRes> VOID_PRODUCT(@Path("Item_line") int line, @Body CalculatePosTransactionRes posTransactionReq);

    @POST("SalesTransactionService.svc/GetUnpostedTransaction/{storeId}/{terminalId}/{DataAreaId}")
    Call<CalculatePosTransactionRes> GET_UNPOSTED_TRANSACTION(@Path("storeId") String storeId, @Path("terminalId") String terminalId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("WalletService.svc/OneApolloAPITransaction")
    Call<ValidatePointsResModel> VALIDATE_ONE_APOLLO_POINTS(@Body ValidatePointsReqModel validatePointsReqModel);

    @POST("WalletService.svc/OneApolloAPITransaction")
    Call<ValidatePointsResModel> ONE_APOLLO_SEND_OTP_RES_CALL(@Body ValidatePointsReqModel oneApolloSendOtpReq);

    @POST("LoginService.svc/CheckUserConfig")
    Call<ConfingRes> CONFING_RES_CALL(@Body ConfingReq confingReq);

    @POST("SalesTransactionService.svc/GetJounalTransactions")
    Call<ArrayList<CalculatePosTransactionRes>> ORDER_LIST_RES_CALL(@Body OrderListReq orderListReq);

    @POST("SalesTransactionService.svc/GetTransactionDetails")
    Call<ArrayList<CalculatePosTransactionRes>> DETAIL_ORDER_LIST(@Body OrderListReq orderListReq);

    @POST("SalesTransactionService.svc/GetTenderType/{storeId}/{DataAreaId}")
    Call<GetTenderTypeRes> GET_TENDER_TYPE_RES_CALL(@Path("storeId") String storeId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("SalesTransactionService.svc/AllowedPaymentMode/{storeId}/{terminalId}/{DataAreaId}")
    Call<AllowedPaymentModeRes> ALLOWED_PAYMENT_MODE_RES_CALL(@Path("storeId") String storeId, @Path("terminalId") String terminalId, @Path("DataAreaId") String dataAreaId, @Body Object o);

    @POST("SalesTransactionService.svc/ApplyMannualDiscount")
    Call<ManualDiscCheckRes> MANUAL_DISC_CHECK_RES_CALL(@Body ManualDiscCheckReq manualDiscCheckReq);

    @POST
    Call<OTPRes> GENERATE_OTP_RES_CALL(@Url String url);

    @POST
    Call<VendorCheckRes> VENDOR_CHECK_RES_CALL(@Url String url, @Body VendorValidationReq requestBody);

    @POST("WalletService.svc/GetWalletDetails/{storeId}/{state}")
    Call<WalletServiceRes> WALLET_SERVICE_RES_CALL(@Path("storeId") String storeId, @Path("state") String state, @Body WalletServiceReq walletServiceReq);

    @POST("SalesTransactionService.svc/CancelPOSTransaction")
    Call<CalculatePosTransactionRes> CANCEL_POS_TRANSACTION_RES_CALL(@Body CalculatePosTransactionRes posTransactionReq);

    @POST("SalesTransactionService.svc/ApplyMannualDiscount")
    Call<CouponDiscount> COUPON_DISCOUNT_CALL(@Body CouponDiscount couponDiscount);

    @POST("SalesTransactionService.svc/TrackingWiseReturnAllowed/{corpId}")
    Call<TrackingWiseReturnAllowedRes> TRACKING_WISE_RETURN_ALLOWED_RES_CALL(@Path("corpId") String corpId);

    @POST("SalesTransactionService.svc/ReturnPOSTransaction")
    Call<CalculatePosTransactionRes> RETURN_POS_TRANSACTION_RES_CALL(@Body CalculatePosTransactionRes posTransactionReq);

   /*@POST("SalesTransactionService.svc/SalesTrackingData")
    Call<SalesTrackingDataRes> SALES_TRACKING_DATA_RES_CALL(@Body SalesTrackingDataReq salesTrackingDataReq);*/

    @POST("SalesTransactionService.svc/SalesTrackingData")
    Call<String> SALES_TRACKING_DATA_RES_CALL(@Body SalesTrackingDataReq salesTrackingDataReq);

    @POST("SalesTransactionService.svc/VoidPayment/{Item_line}")
    Call<PaymentVoidRes> PAYMENT_VOID_RES_CALL(@Path("Item_line") int line, @Body PaymentVoidReq paymentVoidReq);

    @POST("rest/V1/billgeneration/generatebill")
    Call<PayLoadRes> PAY_LOAD_RES_CALL(@Header("Authorization") String authKey, @Body PayLoadRequest payLoadRequest);

    @POST("playlist/list/mobile-getplaylist")
    Call<ADSPlayListResponse> ADS_PLAY_LIST_RESPONSE_SINGLE(@Body ADSPlayListRequest adsPlayListRequest);

    @GET
    Call<ResponseBody> doDownloadFile(@Url String fileUrl);

    //These Changes made by Gopal on 09-01-2021
    @POST("WalletService.svc/GetSMSPayAPI")
    Call<GetSMSPayAPIResponse> GetSmsPayAPI_RES_CALL(@Body GetSMSPayAPIRequest payLoadRequest);

    //These Changes made by Gopal on 06-04-2021 for feedback api call
    @POST("Apollo/InvoiceFeedBack")
    Call<FeedBackResponse> FEEDBACK_Api_RES_CALL(@Body FeedBackRequest feedBackRequest);

    //Sms Api......
    @POST("SendSmsFortemplete")
    Call<ModelMobileNumVerify> verifyMobileNumber(@Query("to") String to, @Query("message") String message);

    //Circle membership cashback  api......
    @POST("SalesTransactionService.svc/CirclePlanCashBackCalculaton")
    Call<CircleMemebershipCashbackPlanResponse> circleMembershipCashback(@Body POSTransactionEntity posTransactionReq);

    //circlememberplan Details api......
    @POST("SalesTransactionService.svc/GetCirclePlanDetails")
    Call<CircleplanDetailsResponse> circlePlandetails(@Body CircleplanDetailsRequest request);

    @POST("SalesTransactionService.svc/CheckBatchStock/0")
    Call<CalculatePosTransactionRes> checkbatchstock(@Body POSTransactionEntity request);

    @POST("SalesTransactionService.svc/CirclePlanTransaction/{line_number}/{circle_price}/{item_code}")
    Call<CalculatePosTransactionRes> circleplantransaction(@Path("line_number") int line, @Path("circle_price") String price, @Path("item_code") String itemcode, @Body CalculatePosTransactionRes request);


    //Phonepe Qrcode Payment Apis
    @POST("WalletService.svc/GetQRCodePaymentDetails")
    Call<PhonepeGenerateQrCodeResponse> PhonepeQrCodeGenerateApi(@Body PhonepeGenerateQrCodeRequest request);


    @POST("SalesTransactionService.svc/GetOMSTransactionHeader")
    Call<OMSTransactionHeaderResModel> GET_OMS_TRANSACTION_HEADER(@Body OMSTransactionHeaderReqModel omsTransactionHeaderReqModel);

    @POST("SalesTransactionService.svc/GetOMSTransactionHeader")
    Call<OMSTransactionResponse> GET_OMS_TRANSACTION_HEADER(@Body OMSTransactionRequest omsTransactionHeaderReq);

    @POST("SalesTransactionService.svc/GetOMSTransaction")
    Call<ArrayList<CustomerDataResBean>> GET_OMS_TRANSACTION(@Body CustomerDataReqBean customerDataReqBean);

    @POST("SalesTransactionService.svc/GetOMSPhysicalBatch")
    Call<MedicineBatchResBean> GET_OMS_PHYSICAL_BATCH(@Body MedicineBatchReqBean medicineBatchReqBean);

    @POST("SalesTransactionService.svc/CheckBatchStock/0")
    Call<CustomerDataResBean> omscheckbatchstock(@Body CustomerDataResBean request);

    @POST("SalesTransactionService.svc/LoadOMSOrder")
    Call<CustomerDataResBean> LOAD_OMS_ORDER(@Body CustomerDataResBean request);

    @POST("SalesTransactionService.svc/ValidateOMSOrder")
    Call<GenerateTenderLineRes> VALIDATE_OMS_ORDER(@Body GenerateTenderLineReq tenderLineReq);

    @POST("OMSSERVICE/OMSService.svc/MPOSOrderUpdate")
    Call<OMSOrderUpdateResponse> UPDATE_OMS_ORDER(@Body OMSOrderUpdateRequest request);

//    @POST("OMSService.svc/MPOSOrderUpdate")
//    Call<OMSOrderUpdateResponse> UPDATE_OMS_ORDER(@Body OMSOrderUpdateRequest request);

    /*@POST("SalesTransactionService.svc/GetOMSSubstitute")
    Call<ArrayList<CustomerDataResBean>> GET_OMS_Substute(@Body CustomerDataReqBean customerDataReqBean);

    @POST("SalesTransactionService.svc/GetReservedOMSTransactionLine/{TransactionId}/{storeId}/{DataAreaId}")
    Call<ReservedStatusResponse>GET_OMS_RESERVED_STATUS(@Path("TransactionId") String transactionId,@Path("storeId") String storeId,@Path("DataAreaId") String dataAreaId,@Body Object o );*/

    @POST("SalesTransactionService.svc/GetUniversalDropDownBind")
    Call<GetOnlineCorporateListApiResponse> GET_ONLINE_CORPORATELIST(@Body GetOnlineCorporateListApiRequest request);

    @POST("AddNewLinePOS")
    Call<OmsAddNewItemResponse> GET_OMS_ADD_New_item(@Body OmsAddNewItemRequest request);

    //These Changes made by Naveen on 09-01-2021
    @POST("WalletService.svc/HDFCTransactionProcess")
    Call<HdfcLinkGenerateResponse> HDFC_LINK_GENERATE_RESPONSE_API_CALL(@Body HdfcLinkGenerateRequest hdfcLinkGenerateRequest);

    @GET("https://jsonblob.com/api/jsonBlob/907667560661794816")
    Call<RacksDataResponse> doRackApiCall();

    @POST("http://online.apollopharmacy.org:51/EPOS/SalesTransactionService.svc/GetOMSTransaction")
    Call<List<GetOMSTransactionResponse>> getOmsApiCall(@Body GetOmsTransactionRequest omsTransactionRequest);

    @POST("http://online.apollopharmacy.org:51/EPOS/SalesTransactionService.svc/GetOMSTransactionHeader")
    Call<TransactionHeaderResponse> GET_OMS_TRANSACTION_HEADER_PICKER(@Body TransactionHeaderRequest transactionHeaderRequest);

    //UAT
    @POST("OMSSERVICE/OMSService.svc/MPOSOrderUpdate")
    Call<OMSOrderForwardResponse> UPDATE_OMS_ORDER(@Body OMSOrderForwardRequest request);

    //Production
//    @POST("OMSService.svc/MPOSOrderUpdate")
//    Call<OMSOrderForwardResponse> UPDATE_OMS_ORDER(@Body OMSOrderForwardRequest request);

    //created by naveen
    @POST("OMSSERVICE/OMSService.svc/MPOSPickPackOrderReservation")
    Call<MPOSPickPackOrderReservationResponse> OMS_PICKER_PACKER_ORDER_RESERVATION(@Body MPOSPickPackOrderReservationRequest request);

    @POST("SalesTransactionService.svc/CalculatePosTransaction")
    Call<CalculatePosTransactionResponse> CALCULATE_POS_TRANSACTION_RES(@Body PostTransactionEntityReq posTransactionReq);

    @POST("WalletService.svc/GetHBPUHIDDetails")
    Call<GetHBPUHIDDetailsResponse> HBPUHID_DETAILS_RESPONSE_CALL(@Body GetHBPUHIDDetailsRequest getHBPUHIDDetailsRequest);

    @POST("WalletService.svc/SendGlobalMessageAPI")
    Call<SendGlobalMessageResponse> SEND_GLOBAL_MESSAGE_RESPONSE_CALL(@Body SendGlobalMessageRequest sendGlobalMessageRequest);

    @POST("SalesTransactionService.svc/CheckBatchStock/0")
    Call<GetOMSTransactionResponse> omscheckstock(@Body GetOMSTransactionResponse request);

    @POST("http://online.apollopharmacy.org:51/EPOS/SalesTransactionService.svc/GetOnlineOrderTransaction")
    Call <List<EPrescriptionModelClassResponse>> GET_ONLINE_ORDER_TRANSACTION(@Body EPrescriptionModelClassRequest ePrescriptionModelClassRequest);

    @POST("http://online.apollopharmacy.org:51/EPOS/SalesTransactionService.svc/GetOnlineOrderLineTransaction")
    Call<List<EPrescriptionMedicineResponse>> GET_ONLINEORDER_LINE_TRANSACTION(@Body EPrescriptionMedicineRequest ePrescriptionMedicineRequest);

    @POST("http://online.apollopharmacy.org:51/EPOS/SalesTransactionService.svc/GetOnlineOrderSubstituteDetails/{prescriptionNo}")
    Call<EPrescriptionSubstituteModelResponse> GET_SUBSTITUTE_DETAILS(@Path("prescriptionNo") String prescriptionNo);

    @POST("http://online.apollopharmacy.org:51/EPOS/SalesTransactionService.svc/CheckBatchStock/0")
    Call<CheckBatchModelResponse> CHECK_BATCH_STOCK(@Body CustomerDataResBean customerDataResBean);

    @POST("SalesTransactionService.svc/CheckBatchStock/0")
    Call<CustomerDataResBean> omscheckbatchstocks(@Body CustomerDataResBean customerDataResBean);

    @POST("SalesTransactionService.svc/OnlineBill")
    Call<CustomerDataResBean> ONLINE_BILL_APICALL(@Body CustomerDataResBean request);

    @POST("http://online.apollopharmacy.org:51/EPOS/SalesTransactionService.svc/PrintReceipt")
    Call<PdfModelResponse> DOWNLOAD_PDF(@Body PdfModelRequest response);
}