package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCustomerDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class CustomerDetailsActivity extends BaseActivity implements CustomerDetailsMvpView {
    @Inject
    CustomerDetailsMvpPresenter<CustomerDetailsMvpView> mPresenter;
    ActivityCustomerDetailsBinding customerDetailsBinding;
    private int NEW_CUSTOMER_SEARCH_ACTIVITY_CODE = 105;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 106;
    private final int REQ_CODE_SPEECH_INPUT = 107;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CustomerDetailsActivity.class);
    }

    public static Intent getStartIntent(Context context, GetCustomerResponse.CustomerEntity customerEntity) {
        Intent intent = new Intent(context, CustomerDetailsActivity.class);
        intent.putExtra("customer_info", customerEntity);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(CustomerDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        customerDetailsBinding.setCallback(mPresenter);
        if (getIntent() != null) {
            GetCustomerResponse.CustomerEntity customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
            if (customerEntity != null) {
                customerDetailsBinding.setCustomer(customerEntity);
                customerDetailsBinding.customerNumberEdit.setText(customerEntity.getSearchId());
                customerDetailsBinding.customerNumberEdit.setSelection(customerEntity.getSearchId().length());
            }
        }

        customerDetailsBinding.customerParentView.setOnTouchListener((v, event) -> {
            CommonUtils.hideKeyboard(CustomerDetailsActivity.this);
            return false;
        });
        customerDetailsBinding.customerNumberEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //temp
//        GetCustomerResponse.CustomerEntity customerEntity = new GetCustomerResponse.CustomerEntity();
//        customerEntity.setSearchId("8056427651");
//        customerDetailsBinding.setCustomer(customerEntity);
    }

    @Override
    public void onAddCustomerClick() {
        startActivityForResult(AddCustomerActivity.getStartIntent(this, customerDetailsBinding.customerNumberEdit.getText().toString()), NEW_CUSTOMER_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void onVoiceSearchClick() {
        requestAudioPermissions();
    }

    @Override
    public String getCustomerNumber() {
        if (TextUtils.isEmpty(Objects.requireNonNull(customerDetailsBinding.customerNumberEdit.getText()).toString())) {
            setCustomerErrorMessage();
            return null;
        } else {
            customerDetailsBinding.phoneNumber.setError(null);
        }
        return customerDetailsBinding.customerNumberEdit.getText().toString();
    }

    @Override
    public void setCustomerErrorMessage() {
        customerDetailsBinding.phoneNumber.setError("Enter Customer Number");
    }

    @Override
    public void onSuccessCustomerSearch(GetCustomerResponse body) {
        CommonUtils.hideKeyboard(CustomerDetailsActivity.this);
        if (body.get_Customer().size() > 0) {
            GetCustomerResponse.CustomerEntity customerEntity = body.get_Customer().get(0);
            customerEntity.setSearchId(getCustomerNumber());
            customerDetailsBinding.setCustomer(customerEntity);
            customerDetailsBinding.setNoUser(false);
        } else {
            customerDetailsBinding.setCustomer(null);
            customerDetailsBinding.setNoUser(true);
        }
    }

    @Override
    public void onFailedCustomerSearch() {
        customerDetailsBinding.setCustomer(null);
        customerDetailsBinding.setNoUser(true);
    }

    @Override
    public void onSubmitBtnClick(GetCustomerResponse.CustomerEntity customerEntity) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer_info", customerEntity);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CUSTOMER_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                GetCustomerResponse.CustomerEntity customerEntity = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("customer_info", customerEntity);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String searchedProductName = result.get(0);
                customerDetailsBinding.customerNumberEdit.setText(searchedProductName);
                customerDetailsBinding.customerNumberEdit.setSelection(searchedProductName.length());
                mPresenter.onCustomerSearchClick();
            }
        }
    }

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            //Go ahead with recording audio now
            promptSpeechInput();
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }
}
