package com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCustomerDoctorInfoBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;

import javax.inject.Inject;

public class CustomerDoctorInfoActivity extends BaseActivity implements CustomerDoctorInfoMvpView {
    @Inject
    CustomerDoctorInfoMvpPresenter<CustomerDoctorInfoMvpView> customerDoctorInfoMvpPresenter;
    ActivityCustomerDoctorInfoBinding customerDoctorInfoBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CustomerDoctorInfoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        customerDoctorInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_doctor_info);
        getActivityComponent().inject(this);
        customerDoctorInfoMvpPresenter.onAttach(CustomerDoctorInfoActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        customerDoctorInfoBinding.continueBtn.setOnClickListener(view -> {
            startActivity(AddItemActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        });
    }
}
