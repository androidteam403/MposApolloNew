package com.apollopharmacy.mpospharmacist.ui.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityPayBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.pay.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayActivityAdapter;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayAdapterListener;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayAdapterModel;
import com.eze.api.EzeAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PayActivity extends BaseActivity implements PayMvpView, PayAdapterListener {

    @Inject
    PayMvpPresenter<PayMvpView> mPresenter;
    ActivityPayBinding activityPayBinding;
    PayActivityAdapter payActivityAdapter;
    private ArrayList<PayAdapterModel> arrPayAdapterModel;


    private final int REQUEST_CODE_INITIALIZE = 10001;
    private final int REQUEST_CODE_PREPARE = 10002;
    private final int REQUEST_CODE_WALLET_TXN = 10003;
    private final int REQUEST_CODE_CHEQUE_TXN = 10004;
    private final int REQUEST_CODE_SALE_TXN = 10006;
    private final int REQUEST_CODE_CASH_BACK_TXN = 10007;
    private final int REQUEST_CODE_CASH_AT_POS_TXN = 10008;
    private final int REQUEST_CODE_CASH_TXN = 10009;
    private final int REQUEST_CODE_SEARCH = 10010;
    private final int REQUEST_CODE_VOID = 10011;
    private final int REQUEST_CODE_ATTACH_SIGN = 10012;
    private final int REQUEST_CODE_UPDATE = 10013;
    private final int REQUEST_CODE_CLOSE = 10014;
    private final int REQUEST_CODE_GET_TXN_DETAIL = 10015;
    private final int REQUEST_CODE_GET_INCOMPLETE_TXN = 10016;
    private final int REQUEST_CODE_PAY = 10017;
    private final int REQUEST_CODE_UPI = 10018;
    private final int REQUEST_CODE_REMOTE_PAY = 10019;
    private final int REQUEST_CODE_QR_CODE_PAY = 10020;
    private final int REQUEST_CODE_NORMAL_EMI = 10021;
    private final int REQUEST_CODE_BRAND_EMI = 10022;
    private final int REQUEST_CODE_PRINT_RECEIPT = 10021;
    private final int REQUEST_CODE_PRINT_BITMAP = 10022;


    PaymentMethodModel paymentMethodModel;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PayActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPayBinding = DataBindingUtil.setContentView(this, R.layout.activity_pay);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PayActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        paymentMethodModel = new PaymentMethodModel();
        activityPayBinding.setPaymentMode(paymentMethodModel);
        activityPayBinding.setCallback(mPresenter);
        getAmount();
        payActivityAdapter = new PayActivityAdapter(this, arrPayAdapterModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityPayBinding.payAmount.setLayoutManager(mLayoutManager);
        activityPayBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        activityPayBinding.payAmount.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        activityPayBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        activityPayBinding.payAmount.setAdapter(payActivityAdapter);
    }

    private void getAmount() {
        arrPayAdapterModel = new ArrayList<>();
        PayAdapterModel payAdapterModel = new PayAdapterModel("100.00");
        arrPayAdapterModel.add(payAdapterModel);
    }

    @Override
    public void NavigateToHomeScreen() {
        startActivity(MainActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getCardPaymentAmount() {
        return activityPayBinding.cardPaymentAmountEditText.getText().toString();
    }

    @Override
    public void setErrorCardPaymentAmountEditText(String message) {
        showMessage(message);
    }

    @Override
    public void onClickCardPaymentBtn() {
        paymentMethodModel.setCashMode(false);
        paymentMethodModel.setCardMode(true);
    }

    @Override
    public void onClickCashPaymentBtn() {
        paymentMethodModel.setCashMode(true);
        paymentMethodModel.setCardMode(false);
    }


    /**
     * unique ID for a transaction in EzeTap EMI Id associated with the
     * transaction
     */
    private String strTxnId = null, emiID = null;
    String Name, amount, Invoice, LeadsId;
    public static String EMAIL;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("SampleAppLogs", "requestCode = " + requestCode + "resultCode = " + resultCode);
        try {
            if (intent != null && intent.hasExtra("response")) {
                Log.d("SampleAppLogs", intent.getStringExtra("response"));
                Toast.makeText(this, intent.getStringExtra("response"), Toast.LENGTH_LONG).show();


            }
            switch (requestCode) {
                case REQUEST_CODE_INITIALIZE:
                    if (resultCode == RESULT_OK) {
                        doPrepareDeviceEzeTap();
                    }
                    break;
                case REQUEST_CODE_UPI:
                case REQUEST_CODE_CASH_TXN:
                case REQUEST_CODE_CASH_BACK_TXN:
                case REQUEST_CODE_CASH_AT_POS_TXN:
                case REQUEST_CODE_WALLET_TXN:
                case REQUEST_CODE_SALE_TXN:

                    if (resultCode == RESULT_OK) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("result");
                        response = response.getJSONObject("txn");
                        strTxnId = response.getString("txnId");
                        emiID = response.getString("emiId");
                        if (strTxnId.equals("") || strTxnId.equals(null)) {
                            Toast.makeText(this, intent.getStringExtra("response"), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent1 = new Intent(PayActivity.this, MainActivity.class);
                            intent1.putExtra("response", intent.getStringExtra("response"));
                            intent1.putExtra("LeadsId", LeadsId);
                            startActivity(intent1);
                            finish();
                        }
//
                    } else if (resultCode == RESULT_CANCELED) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("error");
                        String errorCode = response.getString("code");
                        String errorMessage = response.getString("message");
                    }

                    break;
                case REQUEST_CODE_PREPARE:
                    if (resultCode == RESULT_OK) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("result");
                        doPaymentEztap();
                    } else if (resultCode == RESULT_CANCELED) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("error");
                        String errorCode = response.getString("code");
                        String errorMessage = response.getString("message");
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * optional mechanism to prepare a device for card transactions
     */
    private void doPrepareDeviceEzeTap() {
        EzeAPI.prepareDevice(this, REQUEST_CODE_PREPARE);
    }


    private void doPaymentEztap() {
        try {
            JSONObject jsonRequest = new JSONObject();
            JSONObject jsonOptionalParams = new JSONObject();
            JSONObject jsonReferences = new JSONObject();
            JSONObject jsonCustomer = new JSONObject();
            // Building Customer Object
            jsonCustomer.put("name", "jagadeesh");
            jsonCustomer.put("mobileNo", "9160147044");
            jsonCustomer.put("email", "jagadish.v@thresholdsoft.com");

            // Building References Object
            jsonReferences.put("reference1", "urefu7869");
            jsonReferences.put("reference2", Invoice);
            jsonReferences.put("reference3", EMAIL);

            // Passing Additional References
            JSONArray array = new JSONArray();
            array.put("addRef_xx1");
            array.put("addRef_xx2");
            jsonReferences.put("additionalReferences", array);

            // Building Optional params Object
            jsonOptionalParams.put("amountCashback", "");// Cannot
            // have
            // amount cashback in SALE transaction.
            jsonOptionalParams.put("amountTip", 0.00);
            jsonOptionalParams.put("references", jsonReferences);
            jsonOptionalParams.put("customer", jsonCustomer);

            // Service Fee
            double serviceFee = -1.0;
            String paymentBy = null;
//            if (serviceFeeEditText.getText().toString().length() > 0) {
//                serviceFee = Double.parseDouble(serviceFeeEditText.getText().toString());
//            }
//            if (paymentByEditText.getText().toString().length() > 0) {
//                paymentBy = paymentByEditText.getText().toString();
//            }
            jsonOptionalParams.put("serviceFee", serviceFee);
            jsonOptionalParams.put("paymentBy", paymentBy);

            // Pay to Account
            String accountLabel = null;
//            if (accountLabelEditTet.getText().toString().length() > 0) {
//                accountLabel = accountLabelEditTet.getText().toString();
//            }
            jsonOptionalParams.put("payToAccount", accountLabel);

//            if(REQUEST_CODE == REQUEST_CODE_BRAND_EMI || REQUEST_CODE == REQUEST_CODE_PAY)
//            {
//                JSONObject brandDetails = new JSONObject();
//                brandDetails.put("SKUCode", productCodeEditText.getText().toString().trim());
//                brandDetails.put("brand", productBrandEditText.getText().toString().trim());
//                brandDetails.put("serial", productSerialEditText.getText().toString().trim());
//                jsonOptionalParams.put("productDetails", brandDetails);
//            }

            JSONObject addlData = new JSONObject();
            addlData.put("addl1", "addl1");
            addlData.put("addl2", "addl2");
            addlData.put("addl3", "addl3");
            jsonOptionalParams.put("addlData", addlData);

            JSONObject appData = new JSONObject();
            appData.put("app1", "app1");
            appData.put("app2", "app2");
            appData.put("app3", "app3");
            jsonOptionalParams.put("appData", appData);

            // Building final request object
            jsonRequest.put("amount", getCardPaymentAmount().trim());
            jsonRequest.put("options", jsonOptionalParams);

//            InputMethodManager imm = (InputMethodManager) Online_Payment.this
//                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(emailIdEditText.getWindowToken(), 0);

            jsonRequest.put("mode", "SALE");//Card payment Mode
            doSaleTxn(jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Take credit card transactions for Visa, Mastercard and Rupay. Debit card
     * transactions for Indian banks. Ability to perform EMI option.
     */
    private void doSaleTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "amountCashback": 0,
         "amountTip": 0,
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         },
         "mode": "SALE"
         }
         ******************************************/
        EzeAPI.cardTransaction(this, REQUEST_CODE_SALE_TXN, jsonRequest);
    }
}
