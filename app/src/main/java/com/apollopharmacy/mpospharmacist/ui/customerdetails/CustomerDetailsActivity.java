package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCustomerDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.adduser.AddUserActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;

import javax.inject.Inject;

public class CustomerDetailsActivity extends BaseActivity implements CustomerDetailsMvpView {
    @Inject
    CustomerDetailsMvpPresenter<CustomerDetailsMvpView> mPresenter;

    ActivityCustomerDetailsBinding customerDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        customerDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach( CustomerDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        customerDetailsBinding.setCallback(mPresenter);
    }

    @Override
    public void onCustomerNew() {
        startActivity(AddUserActivity.getStartIntent(this));
    }
}
