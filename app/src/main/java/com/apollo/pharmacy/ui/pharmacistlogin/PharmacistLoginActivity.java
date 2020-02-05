package com.apollo.pharmacy.ui.pharmacistlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityPharmacistLoginBinding;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginActivity;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;
import com.apollo.pharmacy.ui.pharmacistlogin.model.SelectCampaignModel;
import com.apollo.pharmacy.ui.pharmacistlogin.model.UserModel;
import com.tiper.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class PharmacistLoginActivity extends BaseActivity implements PharmacistLoginMvpView {
    @Inject
    PharmacistLoginMvpPresenter<PharmacistLoginMvpView> mPresenter;
    private ArrayList<UserModel> userTypeArr;
    private ArrayList<SelectCampaignModel> selectCampaignModelArr;
    String strFont = null;
    private ActivityPharmacistLoginBinding pharmacistLoginBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PharmacistLoginActivity.class);
    }

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

    @SuppressLint("ResourceType")
    @Override
    protected void setUp() {
        pharmacistLoginBinding.setCallback(mPresenter);

        ArrayAdapter<UserModel> adapter = new ArrayAdapter<UserModel>(this,
                android.R.layout.simple_spinner_dropdown_item, getUserTypes());
        strFont = this.getString(R.font.roboto_regular);
        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
        pharmacistLoginBinding.selectUser.setTypeface(tt);
        pharmacistLoginBinding.selectUser.setAdapter(adapter);
        pharmacistLoginBinding.selectUser.setOnItemClickListener(new MaterialSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull MaterialSpinner materialSpinner, @Nullable View view, int i, long l) {
                materialSpinner.focusSearch(View.FOCUS_DOWN);
                pharmacistLoginBinding.selectUser.setError(null);
            }
        });
        ArrayAdapter<SelectCampaignModel> adapter1 = new ArrayAdapter<SelectCampaignModel>(this,
                android.R.layout.simple_spinner_dropdown_item, getSelectCampaignModelTypes());
        strFont = this.getString(R.font.roboto_regular);
        Typeface tt1 = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
        pharmacistLoginBinding.loginOption.setTypeface(tt1);
        pharmacistLoginBinding.loginOption.setAdapter(adapter1);

        pharmacistLoginBinding.loginOption.setOnItemClickListener(new MaterialSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull MaterialSpinner materialSpinner, @Nullable View view, int i, long l) {
                materialSpinner.focusSearch(View.FOCUS_DOWN);
            }
        });
    }

    private boolean validations() {
        String password = pharmacistLoginBinding.password.getText().toString();
        if (pharmacistLoginBinding.selectUser.getSelectedItem() == null) {
            pharmacistLoginBinding.selectUser.setError("Please Select User");
            pharmacistLoginBinding.selectUser.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;
        } else if (password.isEmpty()) {
            pharmacistLoginBinding.password.setError("Password should not empty");
            pharmacistLoginBinding.password.requestFocus();
            return false;
        }return true;
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
    public void onSuccessLogin() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickLogin() {
        if (validations()) {
            // Intent i = new Intent(getApplicationContext(), AdminLoginActivity.class);
            startActivity(AdminLoginActivity.getStartIntent(this));
        }
    }

    @Override
    public void onClickInstore() {
        pharmacistLoginBinding.loginOption.setVisibility(View.GONE);
        pharmacistLoginBinding.relativeInfo.setVisibility(View.GONE);
    }

    @Override
    public void onCampaignSelect() {
        pharmacistLoginBinding.loginOption.setVisibility(View.VISIBLE);
        pharmacistLoginBinding.relativeInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onIntentCall() {
//        if (validations()) {
//            startActivity(DashboardActivity.getStartIntent(this));
//            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            finish();
//          //  CustomerLoginDialog.newInstance().show(getSupportFragmentManager(), "CustomerLogin");
//        }
    }

    //    private boolean validations() {
//        String name = pharmacistLoginBinding.callNumber.getText().toString();
//        String password = pharmacistLoginBinding.password.getText().toString();
//        if (name.isEmpty()) {
//            pharmacistLoginBinding.callNumber.setError("Name should not empty");
//            pharmacistLoginBinding.callNumber.requestFocus();
//            return false;
//        } else if (password.isEmpty()) {
//            pharmacistLoginBinding.password.setError("password should not empty");
//            pharmacistLoginBinding.password.requestFocus();
//            return false;
//        }
//        return true;
//    }
    private ArrayList<UserModel> getUserTypes() {
        userTypeArr = new ArrayList<>();
        UserModel item = new UserModel();
        item.setUserType("pharmacist");
        userTypeArr.add(item);

        item = new UserModel();
        item.setUserType("Doctor");
        userTypeArr.add(item);

        item = new UserModel();
        item.setUserType("Customer");
        userTypeArr.add(item);
        return userTypeArr;
    }

    private ArrayList<SelectCampaignModel> getSelectCampaignModelTypes() {
        selectCampaignModelArr = new ArrayList<>();
        SelectCampaignModel item = new SelectCampaignModel();
        item.setLoginType("User");
        selectCampaignModelArr.add(item);

        item = new SelectCampaignModel();
        item.setLoginType("Admin");
        selectCampaignModelArr.add(item);

        item = new SelectCampaignModel();
        item.setLoginType("Pharma");
        selectCampaignModelArr.add(item);
        return selectCampaignModelArr;
    }
}

