package com.apollopharmacy.mpospharmacist.data.network;

import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerRequest;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchReqModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginReqModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsReq;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("apollompos/Self/CampaignDetails/16001")
    Call<CampaignDetailsRes> CAMPAIGN_DETAILS_RES_CALL();

    @POST("LoginService.svc/GetLoginUser/16001/AHEL")
    Call<UserModel> getUserIds(@Body Object o);

    @POST("LoginService.svc/GetLoginDetail")
    Call<LoginResModel> LOGIN_RES_MODEL_CALL(@Body LoginReqModel loginReqModel);

    @POST("SalesTransactionService.svc/GetCustomer")
    Call<GetCustomerResponse> GET_CUSTOMER_REQUEST_CALL(@Body GetCustomerRequest customerRequest);

    @POST("SalesTransactionService.svc/GetItemDetails")
    Call<GetItemDetailsRes> GET_ITEM_DETAILS_RES_CALL (@Body GetItemDetailsReq itemDetailsReq);

    @POST("SalesTransactionService.svc/GetTrackingRefence/16001/AHEL")
    Call<CorporateModel> getCorporateList(@Body Object o);

    @POST("SalesTransactionService.svc/GetDoctorList/16001/AHEL")
    Call<DoctorSearchResModel> getDoctorsList(@Body DoctorSearchReqModel doctorSearchReqModel);

    @POST("SalesTransactionService.svc/GetSalesOrigin/AHEL")
    Call<SalesOriginResModel> getSalesOriginList(@Body Object o);

    @POST("SalesTransactionService.svc/GetTransactionId")
    Call<TransactionIDResModel> GET_TRANSACTION_ID(@Body TransactionIDReqModel transactionIDReqModel);
}
