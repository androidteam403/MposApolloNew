package com.apollo.pharmacy.ui.cusdocdetails;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.CustDocDetailsBinding;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.customerdoctordetails.SearchCustomerDetails;
import com.apollo.pharmacy.ui.customerdoctordetails.SearchCustomerDetailsMvpPresenter;
import com.apollo.pharmacy.ui.customerdoctordetails.SearchCustomerDetailsMvpView;

import javax.inject.Inject;

public class CustDocDetails extends BaseActivity implements CustDocMvpView {

    @Inject
    CustDocMvpPresenter<CustDocMvpView> custDocMvpPresenter;

    CustDocDetailsBinding custDocDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            //  getSupportActionBar().hide();
        }
        custDocDetailsBinding= DataBindingUtil.setContentView(this, R.layout.cust_doc_details);
        getActivityComponent().inject(this);
        custDocMvpPresenter.onAttach(CustDocDetails.this);
        setUp();
    }

    @Override
    protected void setUp() {

    }
}
