package com.apollo.pharmacy.ui.doctordetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityDoctorDetailsBinding;
import com.apollo.pharmacy.ui.adddoctor.AddDoctorActivity;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.customerdetails.CustomerDetailsActivity;

import javax.inject.Inject;

public class DoctorDetailsActivity extends BaseActivity implements DoctorDetailsMvpView {
    @Inject
    DoctorDetailsMvpPresenter<DoctorDetailsMvpView> mPresenter;

    ActivityDoctorDetailsBinding doctorDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctorDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach( DoctorDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        doctorDetailsBinding.setCallback(mPresenter);
    }

    @Override
    public void onDoctorNew() {
        startActivity(AddDoctorActivity.getStartIntent(this));
    }
}
