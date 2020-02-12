package com.apollopharmacy.mpospharmacist.ui.customerdetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCustomerDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;

import java.util.Objects;

import javax.inject.Inject;

public class CustomerDetailsActivity extends BaseActivity implements CustomerDetailsMvpView {
    @Inject
    CustomerDetailsMvpPresenter<CustomerDetailsMvpView> mPresenter;
    ActivityCustomerDetailsBinding customerDetailsBinding;

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
            }
        }

    }

    @Override
    public void onAddCustomerClick() {
        startActivity(AddCustomerActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
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
        if (body.get_Customer().size() > 0) {
            GetCustomerResponse.CustomerEntity customerEntity = body.get_Customer().get(0);
            customerEntity.setSearchId(getCustomerNumber());
            customerDetailsBinding.setCustomer(customerEntity);
            customerDetailsBinding.setNoUser(false);
        } else {
            customerDetailsBinding.setNoUser(true);
        }
    }

    @Override
    public void onFailedCustomerSearch() {
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
}
