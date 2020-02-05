package com.apollo.pharmacy.ui.newadminloginsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.NewAdminloginSetupBinding;
import com.apollo.pharmacy.ui.adminlogin.AdminLoginActivity;
import com.apollo.pharmacy.ui.base.BaseActivity;

import javax.inject.Inject;

public class NewAdminLoginSetUp extends BaseActivity implements NewAdminLoginMvpView{

    @Inject
    NewAdminLoginMvpPresenter<NewAdminLoginMvpView> newAdminLoginPresenter;

    NewAdminloginSetupBinding newAdminloginSetupBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        newAdminloginSetupBinding = DataBindingUtil.setContentView(this, R.layout.new_adminlogin_setup);
        getActivityComponent().inject(this);
        newAdminLoginPresenter.onAttach(NewAdminLoginSetUp.this);
        setUp();
    }

    @Override
    protected void setUp() {
    newAdminloginSetupBinding.setCallback(newAdminLoginPresenter);
    }

    @Override
    public void onLoginClick() {
        Toast.makeText(getApplicationContext(), "Login clicked", Toast.LENGTH_SHORT).show();
    }
}