package com.apollopharmacy.mpospharmacist.ui.pay;

import android.text.TextUtils;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineReq;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.pay.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.eze.api.EzeAPI;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayPresenter<V extends PayMvpView> extends BasePresenter<V>
        implements PayMvpPresenter<V> {
    private final int REQUEST_CODE_INITIALIZE = 10001;
    @Inject
    public PayPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void NavigateToHomeScreen() {
        getMvpView().NavigateToHomeScreen();

    }

    @Override
    public void onClickCardPayment() {
        getMvpView().onClickCardPaymentBtn();
    }

    @Override
    public void onClickCashPayment() {
        getMvpView().onClickCashPaymentBtn();
    }

    @Override
    public void onClickCardPaymentPay() {
        if(TextUtils.isEmpty(getMvpView().getCardPaymentAmount())){
            getMvpView().setErrorCardPaymentAmountEditText("Enter Amount");
        }else{
            doInitializeEzeTap();
        }
    }

    @Override
    public void onClickCashPaymentPay() {
        if(TextUtils.isEmpty(getMvpView().getCashPaymentAmount())){
            getMvpView().setErrorCashPaymentAmountEditText("Enter Amount");
        }else {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                //Creating an object of our api interface
                ApiInterface api = ApiClient.getApiService();

                Call<GenerateTenderLineRes> call = api.GENERATE_TENDER_LINE_RES_CALL(generateTenderLineReq());
                call.enqueue(new Callback<GenerateTenderLineRes>() {
                    @Override
                    public void onResponse(@NotNull Call<GenerateTenderLineRes> call, @NotNull Response<GenerateTenderLineRes> response) {
                        if (response.isSuccessful()) {
                            //Dismiss Dialog
                            if ( response.body() != null )
                                saveRetailTransaction(response.body());
                            else {
                                getMvpView().onFailedGenerateTenderLine(response.body());
                                getMvpView().hideLoading();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<GenerateTenderLineRes> call, @NotNull Throwable t) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                    }
                });
            } else {
                getMvpView().onError("Internet Connection Not Available");
            }
        }
    }

    private void saveRetailTransaction(GenerateTenderLineRes body){
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService();

            Call<SaveRetailsTransactionRes> call = api.SAVE_RETAILS_TRANSACTION_RES_CALL(body.getGenerateTenderLineResult());
            call.enqueue(new Callback<SaveRetailsTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<SaveRetailsTransactionRes> call, @NotNull Response<SaveRetailsTransactionRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null )
                            getMvpView().onSuccessSaveRetailTransaction(response.body());
                        else
                            getMvpView().onFailedSaveRetailsTransaction(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SaveRetailsTransactionRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
    // Generate TenderLine Request Formation
    private GenerateTenderLineReq generateTenderLineReq(){
        GenerateTenderLineReq tenderLineReq = new GenerateTenderLineReq();
        tenderLineReq.setType(typeEntity());
        tenderLineReq.setPOSTransaction(posTransactionEntity());
        return tenderLineReq;
    }

    private GenerateTenderLineReq.TypeEntity typeEntity(){
        GenerateTenderLineReq.TypeEntity typeEntity = new GenerateTenderLineReq.TypeEntity();
        typeEntity.setPosOpereration(0);
        typeEntity.setRoundingMethod(0);
        typeEntity.setTender("Cash");
        typeEntity.setTenderCombinationType(1);
        typeEntity.setTenderLimit(200000);
        typeEntity.setTenderTypeId("1");
        typeEntity.setTenderURL("NULL");
        return typeEntity;
    }

    private GenerateTenderLineReq.POSTransactionEntity posTransactionEntity(){
        GenerateTenderLineReq.POSTransactionEntity posTransactionEntity = new GenerateTenderLineReq.POSTransactionEntity();
        posTransactionEntity.setAmounttoAccount(0);
        posTransactionEntity.setBatchTerminalid("");
        posTransactionEntity.setBillingCity("");
        posTransactionEntity.setBusinessDate(CommonUtils.getCurrentDate(CommonUtils.DATE_FORMAT_DD_MMM_YYYY));
        posTransactionEntity.setCancelReasonCode("");
        posTransactionEntity.setChannel("5637145327");
        posTransactionEntity.setComment("");
        posTransactionEntity.setCorpCode("0");
        posTransactionEntity.setCouponCode("");
        posTransactionEntity.setCreatedonPosTerminal(getMvpView().getTransactionModule().getTerminalID());// Terminal Id
        posTransactionEntity.setCurrency("INR");
        posTransactionEntity.setCurrentSalesLine(0);
        posTransactionEntity.setCustAccount("");
        posTransactionEntity.setCustAddress("");
        posTransactionEntity.setCustDiscamount(0);
        posTransactionEntity.setCustomerID(getMvpView().getCustomerModule().getCustId());// customer Id
        posTransactionEntity.setCustomerName(getMvpView().getCustomerModule().getCardName());
        posTransactionEntity.setCustomerState("");
        posTransactionEntity.setDataAreaId(getMvpView().getTransactionModule().getDataAreaID()); // DataAreId
        posTransactionEntity.setDeliveryDate("");
        posTransactionEntity.setDiscAmount(0);
        posTransactionEntity.setDOB("");
        posTransactionEntity.setDoctorCode(getMvpView().getDoctorModule().getCode());
        posTransactionEntity.setDoctorName(getMvpView().getDoctorModule().getDisplayText());
        posTransactionEntity.setEntryStatus(0);
        posTransactionEntity.setGender(0);
        posTransactionEntity.setGrossAmount(65);//gross Amount
        posTransactionEntity.setIPNO("");
        posTransactionEntity.setIPSerialNO("");
        posTransactionEntity.setISAdvancePayment(false);
        posTransactionEntity.setISBatchModifiedAllowed(false);
        posTransactionEntity.setISOMSOrder(false);
        posTransactionEntity.setISPosted(false);
        posTransactionEntity.setISPrescibeDiscount(false);
        posTransactionEntity.setISReserved(false);
        posTransactionEntity.setISReturnAllowed(false);
        posTransactionEntity.setManualBill(false);
        posTransactionEntity.setMobileNO(getMvpView().getCustomerModule().getMobileNo());// user mobile number
        posTransactionEntity.setNetAmount(70);// net amount
        posTransactionEntity.setNetAmountInclTax(75);// include tax total
        posTransactionEntity.setNumberofItemLines(getMvpView().getSelectedProducts().size());// items count
        posTransactionEntity.setNumberofItems(getMvpView().getSelectedProducts().size());// items count
        posTransactionEntity.setOrderSource("");
        posTransactionEntity.setOrderType("");
        posTransactionEntity.setPaymentSource("");
        posTransactionEntity.setPincode("");
        posTransactionEntity.setPosEvent(0);
        posTransactionEntity.setReciptId("");
        posTransactionEntity.setREFNO("");
        posTransactionEntity.setRegionCode("");
        posTransactionEntity.setRemainingamount(85);// amount to paid
        posTransactionEntity.setReminderDays(0);
        posTransactionEntity.setRequestStatus(0);
        posTransactionEntity.setReturn(false);
        posTransactionEntity.setReturnMessage("");
        posTransactionEntity.setReturnReceiptId("");
        posTransactionEntity.setReturnStore("");
        posTransactionEntity.setReturnTerminal("");
        posTransactionEntity.setReturnTransactionId("");
        posTransactionEntity.setReturnType(0);
        posTransactionEntity.setRoundedAmount(0);
        posTransactionEntity.setSalesLine( salesLineEntities());// sales line object
        posTransactionEntity.setSalesOrigin("");
        posTransactionEntity.setSEZ(0);
        posTransactionEntity.setShippingMethod("");
        posTransactionEntity.setStaff(getMvpView().getTransactionModule().getTerminalID());// terminal Id
        posTransactionEntity.setState("AP");
        posTransactionEntity.setStockCheck(true);
        posTransactionEntity.setStore(getMvpView().getTransactionModule().getStoreID());// store details
        posTransactionEntity.setStoreName("");
        posTransactionEntity.setTenderLine(new ArrayList<>());// TenderLine Object
        posTransactionEntity.setTerminal(getMvpView().getTransactionModule().getTerminalID());// teinal id
        posTransactionEntity.setTimewhenTransClosed(0);
        posTransactionEntity.setTotalDiscAmount(0);
        posTransactionEntity.setTotalManualDiscountAmount(0);
        posTransactionEntity.setTotalManualDiscountPercentage(0);
        posTransactionEntity.setTotalMRP(85);// total MRP Price
        posTransactionEntity.setTotalTaxAmount(15);// total Tax amount
        posTransactionEntity.setTrackingRef("");
        posTransactionEntity.setTransactionId(getMvpView().getTransactionModule().getTransactionID());// Trancaction id
        posTransactionEntity.setTransDate(CommonUtils.getCurrentDate("DD-MMM-YYYY hh:mm:ss"));// payment date time format `18-Feb-2020 03:01:20`
        posTransactionEntity.setTransType(0);
        posTransactionEntity.setType(2);
        posTransactionEntity.setVendorId("");
        return posTransactionEntity;
    }

    private ArrayList<GenerateTenderLineReq.SalesLineEntity> salesLineEntities() {
        ArrayList<GenerateTenderLineReq.SalesLineEntity> salesLineEntities = new ArrayList<>();
        ArrayList<GetItemDetailsRes.Items> itemsArrayList = getMvpView().getSelectedProducts();
        for (GetItemDetailsRes.Items items : itemsArrayList) {
            GenerateTenderLineReq.SalesLineEntity salesLineEntity = new GenerateTenderLineReq.SalesLineEntity();
            salesLineEntity.setAdditionaltax(0);
            salesLineEntity.setApplyDiscount(false);
            salesLineEntity.setBarcode("");
            salesLineEntity.setBaseAmount(items.getBatchListObj().getMRP());
            salesLineEntity.setCategory(items.getCategory());
            salesLineEntity.setCategoryCode(items.getCategoryCode());
            salesLineEntity.setCategoryReference("");
            salesLineEntity.setCESSPerc(0);
            salesLineEntity.setCESSTaxCode("");
            salesLineEntity.setCGSTPerc(items.getBatchListObj().getCGSTPerc());
            salesLineEntity.setCGSTTaxCode(items.getBatchListObj().getCGSTTaxCode());
            salesLineEntity.setChecked(false);
            salesLineEntity.setComment("");
            salesLineEntity.setDiscAmount(0);
            salesLineEntity.setDiscId("");
            salesLineEntity.setDiscOfferId("");
            salesLineEntity.setDiscountStructureType(0);
            salesLineEntity.setDiscountType("");
            salesLineEntity.setDiseaseType("Acute");
            salesLineEntity.setDPCO(false);
            salesLineEntity.setExpiry(items.getBatchListObj().getExpDate());
            salesLineEntity.setHsncode_In(items.getHsncode_In());
            salesLineEntity.setIGSTPerc(items.getBatchListObj().getIGSTPerc());
            salesLineEntity.setIGSTTaxCode(items.getBatchListObj().getIGSTTaxCode());
            salesLineEntity.setInventBatchId(items.getBatchListObj().getBatchNo());
            salesLineEntity.setISPrescribed(0);
            salesLineEntity.setISReserved(false);
            salesLineEntity.setISStockAvailable(false);
            salesLineEntity.setItemId(items.getArtCode());
            salesLineEntity.setItemName(items.getDescription());
            salesLineEntity.setLineDiscPercentage(0);
            salesLineEntity.setLinedscAmount(0);
            salesLineEntity.setLineManualDiscountAmount(0);
            salesLineEntity.setLineManualDiscountPercentage(0);
            salesLineEntity.setLineNo(1);
            salesLineEntity.setManufacturerCode(items.getManufactureCode());
            salesLineEntity.setManufacturerName(items.getManufacture());
            salesLineEntity.setMixMode(false);
            salesLineEntity.setMMGroupId("0");
            salesLineEntity.setModifyBatchId("");
            salesLineEntity.setMRP(items.getBatchListObj().getMRP());
            salesLineEntity.setNetAmount(items.getBatchListObj().getPrice());
            salesLineEntity.setNetAmountInclTax(items.getBatchListObj().getMRP());
            salesLineEntity.setOfferAmount(0);
            salesLineEntity.setOfferDiscountType(0);
            salesLineEntity.setOfferDiscountValue(0);
            salesLineEntity.setOfferQty(0);
            salesLineEntity.setOfferType(0);
            salesLineEntity.setOmsLineID(0);
            salesLineEntity.setOmsLineRECID(0);
            salesLineEntity.setOrderStatus(0);
            salesLineEntity.setOriginalPrice(items.getBatchListObj().getMRP());
            salesLineEntity.setPeriodicDiscAmount(0);
            salesLineEntity.setPreviewText("");
            salesLineEntity.setPrice(items.getBatchListObj().getPrice());
            salesLineEntity.setPriceOverride(false);
            salesLineEntity.setProductRecID(items.getProductRecID());
            salesLineEntity.setQty(items.getBatchListObj().getEnterReqQuantity());
            salesLineEntity.setRemainderDays(0);
            salesLineEntity.setRemainingQty(0);
            salesLineEntity.setRetailCategoryRecID(items.getRetailCategoryRecID());
            salesLineEntity.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
            salesLineEntity.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
            salesLineEntity.setReturnQty(0);
            salesLineEntity.setScheduleCategory(items.getSch_Catg());
            salesLineEntity.setScheduleCategoryCode(items.getSch_Catg_Code());
            salesLineEntity.setSGSTPerc(items.getBatchListObj().getSGSTPerc());
            salesLineEntity.setSGSTTaxCode(items.getBatchListObj().getSGSTTaxCode());
            salesLineEntity.setStockQty(0);
            salesLineEntity.setSubCategory(items.getSubCategory());
            salesLineEntity.setSubCategoryCode(items.getSubCategory());
            salesLineEntity.setSubClassification(items.getSubClassification());
            salesLineEntity.setSubsitute(false);
            salesLineEntity.setSubstitudeItemId("");
            salesLineEntity.setTax(items.getBatchListObj().getTotalTax());
            salesLineEntity.setTaxAmount(items.getBatchListObj().getTotalTax());
            salesLineEntity.setTotal(items.getBatchListObj().getMRP());
            salesLineEntity.setTotalDiscAmount(0);
            salesLineEntity.setTotalDiscPct(0);
            salesLineEntity.setTotalRoundedAmount(0);
            salesLineEntity.setTotalTax(items.getBatchListObj().getTotalTax());
            salesLineEntity.setUnit("");
            salesLineEntity.setUnitPrice(items.getBatchListObj().getMRP());
            salesLineEntity.setUnitQty(items.getBatchListObj().getREQQTY());
            salesLineEntity.setVariantId("");
            salesLineEntities.add(salesLineEntity);
        }
        return salesLineEntities;
    }
    /**
     * invoke to initialize the SDK with the merchant key and the device (card
     * reader) with bank keys
     */
    private void doInitializeEzeTap() {
        /**********************************************
         {
         "demoAppKey": "your demo app key",
         "prodAppKey": "your prod app key",
         "merchantName": "your merchant name",
         "userName": "your user name",
         "currencyCode": "INR",
         "appMode": "DEMO/PROD",
         "captureSignature": "true/false",
         "prepareDevice": "true/false"
         }
         **********************************************/
        JSONObject jsonRequest = new JSONObject();
//        if(Setting.config != null) {
//            try {
//                jsonRequest.put("demoAppKey", Setting.config.getAppKey());
//                jsonRequest.put("prodAppKey", Setting.config.getAppKey());
//                jsonRequest.put("merchantName", Setting.config.getMerchantName());
//                jsonRequest.put("userName", Setting.config.getUsername());
//                jsonRequest.put("currencyCode", "INR");
//                jsonRequest.put("appMode", Setting.config.getAppMode());
//                jsonRequest.put("captureSignature", "true");
//                jsonRequest.put("prepareDevice", "false");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        try {
            jsonRequest.put("demoAppKey", "bdb9b6b1-80ac-42a1-bc8a-3caf259c6023");
            jsonRequest.put("prodAppKey", "bdb9b6b1-80ac-42a1-bc8a-3caf259c6023");
            jsonRequest.put("merchantName", "9866666344");
            jsonRequest.put("userName","9866666344");
            jsonRequest.put("currencyCode", "INR");
            jsonRequest.put("appMode", "DEMO");
            jsonRequest.put("captureSignature", "true");
            jsonRequest.put("prepareDevice", "false");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // }
        EzeAPI.initialize(getMvpView().getContext(), REQUEST_CODE_INITIALIZE, jsonRequest);
    }

}
