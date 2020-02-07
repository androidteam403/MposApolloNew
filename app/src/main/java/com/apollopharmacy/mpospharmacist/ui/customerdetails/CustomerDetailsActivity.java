package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCustomerDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;

import javax.inject.Inject;

public class CustomerDetailsActivity extends BaseActivity implements CustomerDetailsMvpView {
    @Inject
    CustomerDetailsMvpPresenter<CustomerDetailsMvpView> mPresenter;
    ActivityCustomerDetailsBinding customerDetailsBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CustomerDetailsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        customerDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(CustomerDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        customerDetailsBinding.setCallback(mPresenter);
    }

    @Override
    public void onAddCustomerClick() {
        startActivity(AddCustomerActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
