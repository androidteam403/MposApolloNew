package com.apollopharmacy.mpospharmacist.ui.customerdoctordetails;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.SearchCutomerDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;

import javax.inject.Inject;

public class SearchCustomerDetails extends BaseActivity implements SearchCustomerDetailsMvpView {

    @Inject
    SearchCustomerDetailsMvpPresenter<SearchCustomerDetailsMvpView> customerDetailsMvpPresenter;

    SearchCutomerDetailsBinding searchCutomerDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            //  getSupportActionBar().hide();
        }
        searchCutomerDetailsBinding = DataBindingUtil.setContentView(this, R.layout.search_cutomer_details);
        getActivityComponent().inject(this);
        customerDetailsMvpPresenter.onAttach(SearchCustomerDetails.this);
        setUp();
    }

    @Override
    protected void setUp() {
        searchCutomerDetailsBinding.setCallbacks(customerDetailsMvpPresenter);
    }

    @Override
    public void onPhoneClick() {
        Toast.makeText(getApplicationContext(), "phoneClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterClick() {
        Toast.makeText(getApplicationContext(), "RegisterClick", Toast.LENGTH_SHORT).show();
    }
}
