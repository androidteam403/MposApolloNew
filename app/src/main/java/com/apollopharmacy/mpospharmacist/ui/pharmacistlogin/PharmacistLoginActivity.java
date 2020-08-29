package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityPharmacistLoginBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.UserModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

public class PharmacistLoginActivity extends BaseActivity implements PharmacistLoginMvpView {
    @Inject
    PharmacistLoginMvpPresenter<PharmacistLoginMvpView> mPresenter;
    private ActivityPharmacistLoginBinding pharmacistLoginBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PharmacistLoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pharmacistLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_pharmacist_login);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PharmacistLoginActivity.this);
        setUp();
    }


    @Override
    protected void setUp() {
        pharmacistLoginBinding.setCallback(mPresenter);
        pharmacistLoginBinding.password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pharmacistLoginBinding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        pharmacistLoginBinding.selectUser.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        pharmacistLoginBinding.selectCampaign.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        pharmacistLoginBinding.selectUser.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        pharmacistLoginBinding.selectCampaign.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
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
            int radioButtonID = pharmacistLoginBinding.radioGroup.getCheckedRadioButtonId();
            View radioButton = pharmacistLoginBinding.radioGroup.findViewById(radioButtonID);
            int idx = pharmacistLoginBinding.radioGroup.indexOfChild(radioButton);
            if (idx == 0) {
                mPresenter.userLoginInStoreApi();
            } else {
                mPresenter.userLoginCampaignApi();
            }
        }
    }

    @Override
    public void onClickInstore() {
        pharmacistLoginBinding.selectCampaign.setVisibility(View.GONE);
        pharmacistLoginBinding.relativeInfo.setVisibility(View.GONE);
    }

    @Override
    public void onClickCampaignClose() {
        pharmacistLoginBinding.relativeInfo.setVisibility(View.GONE);
        pharmacistLoginBinding.selectCampaign.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCampaignSelect() {

    }

    @Override
    public void getUserIds(UserModel body) {
        ArrayAdapter<UserModel._DropdownValueBean> adapter = new ArrayAdapter<UserModel._DropdownValueBean>(this,
                android.R.layout.simple_spinner_dropdown_item, body.getGetLoginUserResult().get_DropdownValue()) {

            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pharmacistLoginBinding.selectUser.setAdapter(adapter);
        pharmacistLoginBinding.selectUser.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
            pharmacistLoginBinding.selectUser.setError(null);
        });
    }

    @Override
    public void setCampaignDetails(CampaignDetailsRes campaignDetails) {
        ArrayAdapter<CampaignDetailsRes.CampDetailsEntity> adapter1 = new ArrayAdapter<CampaignDetailsRes.CampDetailsEntity>(this,
                android.R.layout.simple_spinner_dropdown_item, campaignDetails.getCampDetails()) {

            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pharmacistLoginBinding.selectCampaign.setAdapter(adapter1);

        pharmacistLoginBinding.selectCampaign.setOnItemClickListener((materialSpinner, view, i, l) -> {
            pharmacistLoginBinding.relativeInfo.setVisibility(View.VISIBLE);
            pharmacistLoginBinding.selectCampaign.setVisibility(View.GONE);
            pharmacistLoginBinding.setCampaign((CampaignDetailsRes.CampDetailsEntity) materialSpinner.getSelectedItem());
            materialSpinner.focusSearch(View.FOCUS_DOWN);
        });
        pharmacistLoginBinding.selectCampaign.setVisibility(View.VISIBLE);
    }

    @Override
    public void userLoginSuccess() {
        startActivity(MainActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void userLoginFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
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
    public String getUserPassword() {
        return Objects.requireNonNull(pharmacistLoginBinding.password.getText()).toString();
    }

}

