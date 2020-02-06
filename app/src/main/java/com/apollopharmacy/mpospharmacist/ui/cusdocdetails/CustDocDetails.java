package com.apollopharmacy.mpospharmacist.ui.cusdocdetails;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.CustDocDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;

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
