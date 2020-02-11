package com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.SearchCutomerDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.customerdoctorinfo.CustomerDoctorInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListActivity;

import javax.inject.Inject;

public class SearchCustomerDoctorDetailsActivity extends BaseActivity implements SearchCustomerDoctorDetailsMvpView {
    @Inject
    SearchCustomerDoctorDetailsMvpPresenter<SearchCustomerDoctorDetailsMvpView> customerDetailsMvpPresenter;
    SearchCutomerDetailsBinding searchCutomerDetailsBinding;

    private int CUSTOMER_SEARCH_ACTIVITY_CODE = 101;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchCustomerDoctorDetailsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchCutomerDetailsBinding = DataBindingUtil.setContentView(this, R.layout.search_cutomer_details);
        getActivityComponent().inject(this);
        customerDetailsMvpPresenter.onAttach(SearchCustomerDoctorDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        searchCutomerDetailsBinding.setCallbacks(customerDetailsMvpPresenter);

        searchCutomerDetailsBinding.continueBtn.setOnClickListener(view -> {
            startActivity(AddItemActivity.getStartIntent(this,searchCutomerDetailsBinding.getCustomer()));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        });
    }

    @Override
    public void onCustomerSearchClick() {
        startActivityForResult(CustomerDetailsActivity.getStartIntent(this),CUSTOMER_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onDoctorSearchClick() {
        startActivity(DoctorDetailsActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressedClick() {
        onBackPressed();
    }

    @Override
    public void customerEditClick(GetCustomerResponse.CustomerEntity customerEntity) {
        startActivityForResult(CustomerDetailsActivity.getStartIntent(this,customerEntity),CUSTOMER_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUSTOMER_SEARCH_ACTIVITY_CODE) {
            if(resultCode == Activity.RESULT_OK){
                GetCustomerResponse.CustomerEntity result = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                searchCutomerDetailsBinding.setCustomer(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
