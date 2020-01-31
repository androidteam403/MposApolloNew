package com.apollo.pharmacy.ui.pharmacistlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityPharmacistLoginBinding;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;

import javax.inject.Inject;

import okhttp3.ResponseBody;

/**
 * A login screen that offers login via email/password.
 */
public class PharmacistLoginActivity extends BaseActivity implements PharmacistLoginMvpView {
    @Inject
    PharmacistLoginMvpPresenter<PharmacistLoginMvpView> mPresenter;

    private ActivityPharmacistLoginBinding pharmacistLoginBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PharmacistLoginActivity.class);
    }

    // UI references.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        pharmacistLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_pharmacist_login);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PharmacistLoginActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        pharmacistLoginBinding.setCallback(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void onLoginSuccess(ResponseBody responseBody) {
        startActivity(DashboardActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }


    @Override
    public String getMobile() {
        return null;
    }

    @Override
    public String getOtp() {
        return null;
    }

    @Override
    public void showInputMobileError(String errorMessage) {

    }

    @Override
    public void showInputOtpError(String errorMessage) {

    }

    @Override
    public void onSuccessSendOtp() {

    }

    @Override
    public void onSuccessLogin() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onIntentCall() {
        if (validations()) {
            startActivity(DashboardActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();
          //  CustomerLoginDialog.newInstance().show(getSupportFragmentManager(), "CustomerLogin");
        }
    }

    private boolean validations() {
        String name = pharmacistLoginBinding.name.getText().toString();
        String password = pharmacistLoginBinding.password.getText().toString();
        if (name.isEmpty()) {
            pharmacistLoginBinding.name.setError("Name should not empty");
            pharmacistLoginBinding.name.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            pharmacistLoginBinding.password.setError("password should not empty");
            pharmacistLoginBinding.password.requestFocus();
            return false;

        }
        return true;
    }

}

