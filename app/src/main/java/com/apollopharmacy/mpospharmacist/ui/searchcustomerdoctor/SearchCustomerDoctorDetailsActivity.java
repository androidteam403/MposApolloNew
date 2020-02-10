package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.SearchCutomerDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;

import javax.inject.Inject;

public class SearchCustomerDoctorDetailsActivity extends BaseActivity implements SearchCustomerDoctorDetailsMvpView {
    @Inject
    SearchCustomerDoctorDetailsMvpPresenter<SearchCustomerDoctorDetailsMvpView> customerDetailsMvpPresenter;
    SearchCutomerDetailsBinding searchCutomerDetailsBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchCustomerDoctorDetailsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        searchCutomerDetailsBinding = DataBindingUtil.setContentView(this, R.layout.search_cutomer_details);
        getActivityComponent().inject(this);
        customerDetailsMvpPresenter.onAttach(SearchCustomerDoctorDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        searchCutomerDetailsBinding.setCallbacks(customerDetailsMvpPresenter);

        searchCutomerDetailsBinding.continueBtn.setOnClickListener(view -> {
            startActivity(CustomerDoctorInfoActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        });
    }

    @Override
    public void onCustomerSearchClick() {
        startActivity(CustomerDetailsActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onDoctorSearchClick() {
        startActivity(DoctorDetailsActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCorporateSearchClick() {
        startActivity(CorporateDetailsActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressedClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
