package com.apollo.pharmacy.ui.customerdetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityCustomerDetailsBinding;
import com.apollo.pharmacy.ui.adduser.AddUserActivity;
import com.apollo.pharmacy.ui.adduser.AddUserMvpPresenter;
import com.apollo.pharmacy.ui.adduser.AddUserMvpView;
import com.apollo.pharmacy.ui.base.BaseActivity;

import javax.inject.Inject;

public class CustomerDetailsActivity extends BaseActivity implements CustomerDetailsMvpView {
    @Inject
    CustomerDetailsMvpPresenter<CustomerDetailsMvpView> mPresenter;

    ActivityCustomerDetailsBinding customerDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
