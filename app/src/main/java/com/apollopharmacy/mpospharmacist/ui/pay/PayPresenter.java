package com.apollopharmacy.mpospharmacist.ui.pay;

import android.text.TextUtils;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.eze.api.EzeAPI;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

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
