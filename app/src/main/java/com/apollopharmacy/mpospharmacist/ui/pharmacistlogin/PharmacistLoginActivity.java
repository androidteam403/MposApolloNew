package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityPharmacistLoginBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.LoginResModel;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;

import java.util.Objects;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;

public class PharmacistLoginActivity extends BaseActivity implements PharmacistLoginMvpView {
    @Inject
    PharmacistLoginMvpPresenter<PharmacistLoginMvpView> mPresenter;
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


//        ArrayAdapter<SelectCampaignModel> adapter1 = new ArrayAdapter<SelectCampaignModel>(this,
//                android.R.layout.simple_spinner_dropdown_item, getSelectCampaignModelTypes());
//        strFont = this.getString(R.font.roboto_regular);
//        Typeface tt1 = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
//        pharmacistLoginBinding.selectCampaign.setTypeface(tt1);
//        pharmacistLoginBinding.selectCampaign.setAdapter(adapter1);
//
//        pharmacistLoginBinding.selectCampaign.setOnItemClickListener(new MaterialSpinner.OnItemClickListener() {
//            @Override
//            public void onItemClick(@NotNull MaterialSpinner materialSpinner, @Nullable View view, int i, long l) {
//                materialSpinner.focusSearch(View.FOCUS_DOWN);
//            }
//        });

        mPresenter.getUserId();
    }

    private boolean validations() {
        String password = Objects.requireNonNull(pharmacistLoginBinding.password.getText()).toString();
        if (pharmacistLoginBinding.selectUser.getSelectedItem() == null) {
            pharmacistLoginBinding.selectUser.setError("Please Select User");
            pharmacistLoginBinding.selectUser.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;
        } else if (password.isEmpty()) {
            pharmacistLoginBinding.password.setError("Password should not empty");
            pharmacistLoginBinding.password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }


    @Override
    public void onClickLogin() {
        if (validations()) {
            mPresenter.userLoginApi();
        }
    }

    @Override
    public void onClickInstore() {
        pharmacistLoginBinding.selectCampaign.setVisibility(View.GONE);
        pharmacistLoginBinding.relativeInfo.setVisibility(View.GONE);
    }

    @Override
    public void onCampaignSelect() {
        pharmacistLoginBinding.selectCampaign.setVisibility(View.VISIBLE);
        pharmacistLoginBinding.relativeInfo.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ResourceType")
    @Override
    public void getUserIds(UserModel body) {
        ArrayAdapter<UserModel._DropdownValueBean> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, body.getGetLoginUserResult().get_DropdownValue());
        strFont = this.getString(R.font.roboto_regular);
        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
        pharmacistLoginBinding.selectUser.setTypeface(tt);
        pharmacistLoginBinding.selectUser.setAdapter(adapter);
        pharmacistLoginBinding.selectUser.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
            pharmacistLoginBinding.selectUser.setError(null);
        });
    }

    @Override
    public void userLoginSuccess(LoginResModel loginResModel) {
        startActivity(MainActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void userLoginFailed() {

    }

    @Override
    public String getUserId() {
        return pharmacistLoginBinding.selectUser.getSelectedItem() != null ? pharmacistLoginBinding.selectUser.getSelectedItem().toString() : null;
    }

    @Override
    public String getCampaignId() {
        return null;
    }

    @Override
    public String getStoreId() {
        return "16001";
    }

    @Override
    public String getTerminalId() {
        return "001";
    }

    @Override
    public String getUserPassword() {
        return Objects.requireNonNull(pharmacistLoginBinding.password.getText()).toString();
    }

}

