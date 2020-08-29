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
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchCustomerDoctorDetailsActivity extends BaseActivity implements SearchCustomerDoctorDetailsMvpView {
    @Inject
    SearchCustomerDoctorDetailsMvpPresenter<SearchCustomerDoctorDetailsMvpView> customerDetailsMvpPresenter;
    SearchCutomerDetailsBinding searchCutomerDetailsBinding;

    private int CUSTOMER_SEARCH_ACTIVITY_CODE = 101;
    private int DOCTOR_SEARCH_ACTIVITY_CODE = 102;
    private int CORPORATE_SEARCH_ACTIVITY_CODE = 103;
    private GetCustomerResponse.CustomerEntity customerResult = null;
    private TransactionIDResModel transactionIdItem = null;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList = new ArrayList<>();
    private CorporateModel corporateModel;

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
        customerDetailsMvpPresenter.getTransactionID();
        searchCutomerDetailsBinding.setCallbacks(customerDetailsMvpPresenter);
       // customerDetailsMvpPresenter.getCorporateList();
    }

    @Override
    public void onCustomerSearchClick() {
        startActivityForResult(CustomerDetailsActivity.getStartIntent(this), CUSTOMER_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onDoctorSearchClick() {
        startActivityForResult(DoctorDetailsActivity.getStartIntent(this), DOCTOR_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCorporateSearchClick() {
        startActivityForResult(CorporateDetailsActivity.getStartIntent(this,corporateModel), CORPORATE_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressedClick() {
        onBackPressed();
    }

    @Override
    public void customerEditClick(GetCustomerResponse.CustomerEntity customerEntity) {
        startActivityForResult(CustomerDetailsActivity.getStartIntent(this, customerEntity), CUSTOMER_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        startActivityForResult(DoctorDetailsActivity.getStartIntent(this, doctorEntity, salesEntity), DOCTOR_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity) {
        startActivityForResult(CorporateDetailsActivity.getStartIntent(this, corporateEntity,corporateModel), CORPORATE_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onContinueBtnClick() {
        if(searchCutomerDetailsBinding.getCustomer() != null) {
            startActivity(AddItemActivity.getStartIntent(this,true, searchCutomerDetailsBinding.getCustomer(), searchCutomerDetailsBinding.getDoctor(), searchCutomerDetailsBinding.getCorporate(), transactionIdItem,corporateModel));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public void showTransactionID(TransactionIDResModel model) {
        searchCutomerDetailsBinding.setTransaction(model);
        transactionIdItem = model;
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        this.corporateModel = corporateModel;
        corporateList.addAll(corporateModel.get_DropdownValue());
        searchCutomerDetailsBinding.setCorporate(corporateModel.get_DropdownValue().get(0));
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
            if (resultCode == Activity.RESULT_OK) {
                searchCutomerDetailsBinding.continueBtn.setAlpha(1);
                searchCutomerDetailsBinding.continueBtn.setClickable(true);
                customerResult = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                searchCutomerDetailsBinding.setCustomer(customerResult);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == DOCTOR_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DoctorSearchResModel.DropdownValueBean doctorResult = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                if (doctorResult != null) {
                    searchCutomerDetailsBinding.setDoctor(doctorResult);
                }
                SalesOriginResModel.DropdownValueBean salesResult = (SalesOriginResModel.DropdownValueBean) data.getSerializableExtra("sales_info");
                if (salesResult != null) {
                    searchCutomerDetailsBinding.setSales(salesResult);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == CORPORATE_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                CorporateModel.DropdownValueBean result = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                searchCutomerDetailsBinding.setCorporate(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
