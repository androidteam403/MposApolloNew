package com.apollopharmacy.mpospharmacist.ui.home.ui.billing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentBillingBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;


public class BillingFragment extends BaseFragment implements BillingMvpView {

    @Inject
    BillingMvpPresenter<BillingMvpView> mPresenter;
    private FragmentBillingBinding fragmentBillingBinding;
    private int CUSTOMER_SEARCH_ACTIVITY_CODE = 101;
    private int DOCTOR_SEARCH_ACTIVITY_CODE = 102;
    private int CORPORATE_SEARCH_ACTIVITY_CODE = 103;
    private GetCustomerResponse.CustomerEntity customerResult = null;
    private TransactionIDResModel transactionIdItem = null;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList = new ArrayList<>();
    private CorporateModel corporateModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBillingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_billing, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BillingFragment.this);
        return fragmentBillingBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        mPresenter.getTransactionID();
        fragmentBillingBinding.setCallbacks(mPresenter);

        fragmentBillingBinding.customerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable)){
                    fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
                    fragmentBillingBinding.continueBtn.setClickable(false);
                }else{
                    if(!TextUtils.isEmpty(fragmentBillingBinding.customerMobile.getText().toString())
                            && !TextUtils.isEmpty(fragmentBillingBinding.customerName.getText().toString())){
                        fragmentBillingBinding.continueBtn.setAlpha(1);
                        fragmentBillingBinding.continueBtn.setClickable(true);
                    }
                }

            }
        });

        fragmentBillingBinding.customerMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable)){
                    fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
                    fragmentBillingBinding.continueBtn.setClickable(false);
                }else{
                    if(!TextUtils.isEmpty(fragmentBillingBinding.customerMobile.getText().toString())
                            && !TextUtils.isEmpty(fragmentBillingBinding.customerName.getText().toString())){
                        fragmentBillingBinding.continueBtn.setAlpha(1);
                        fragmentBillingBinding.continueBtn.setClickable(true);
                    }
                }
            }
        });

        fragmentBillingBinding.prgTrackingEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(fragmentBillingBinding.prgTrackingEdit.getText().toString())) {
                    fragmentBillingBinding.getCorporate().setPrg_Tracking(editable.toString());
                }
            }
        });
    }

    @Override
    public void onCustomerSearchClick() {
        startActivityForResult(CustomerDetailsActivity.getStartIntent(getBaseActivity()), CUSTOMER_SEARCH_ACTIVITY_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onDoctorSearchClick() {
        startActivityForResult(DoctorDetailsActivity.getStartIntent(getBaseActivity()), DOCTOR_SEARCH_ACTIVITY_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCorporateSearchClick() {
        startActivityForResult(CorporateDetailsActivity.getStartIntent(getBaseActivity(), corporateModel), CORPORATE_SEARCH_ACTIVITY_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressedClick() {

    }

    @Override
    public void customerEditClick(GetCustomerResponse.CustomerEntity customerEntity) {
        startActivityForResult(CustomerDetailsActivity.getStartIntent(getBaseActivity(), customerEntity), CUSTOMER_SEARCH_ACTIVITY_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onDoctorEditClick(DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        startActivityForResult(DoctorDetailsActivity.getStartIntent(getBaseActivity(), doctorEntity, salesEntity), DOCTOR_SEARCH_ACTIVITY_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCorporateEditClick(CorporateModel.DropdownValueBean corporateEntity) {
        startActivityForResult(CorporateDetailsActivity.getStartIntent(getBaseActivity(), corporateEntity, corporateModel), CORPORATE_SEARCH_ACTIVITY_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onContinueBtnClick() {
        if(fragmentBillingBinding.getDoctor() == null  ){
           showMessage("please select Doctor");
            return;
        }else if(fragmentBillingBinding.getCorporate() == null){
            showMessage("please select Corporate");
            return;
        }
        if (fragmentBillingBinding.getCustomer() != null) {
            startActivity(AddItemActivity.getStartIntent(getBaseActivity(), fragmentBillingBinding.getCustomer(), fragmentBillingBinding.getDoctor(), fragmentBillingBinding.getCorporate(), transactionIdItem,corporateModel));
            getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }else{
            if(fragmentBillingBinding.customerMobile.getText().toString().length() < 10){
                fragmentBillingBinding.customerMobile.setError("Enter valid mobile number");
            }else{
                GetCustomerResponse.CustomerEntity entity = new GetCustomerResponse.CustomerEntity();
                entity.setCardName(fragmentBillingBinding.customerName.getText().toString());
                entity.setMobileNo(fragmentBillingBinding.customerMobile.getText().toString());
                startActivity(AddItemActivity.getStartIntent(getBaseActivity(), entity, fragmentBillingBinding.getDoctor(), fragmentBillingBinding.getCorporate(), transactionIdItem,corporateModel));
                getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        }
    }

    @Override
    public void showTransactionID(TransactionIDResModel model) {
        fragmentBillingBinding.setTransaction(model);
        transactionIdItem = model;
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        this.corporateModel = corporateModel;
        corporateList.addAll(corporateModel.get_DropdownValue());
        fragmentBillingBinding.setCorporate(corporateModel.get_DropdownValue().get(0));
    }

    @Override
    public void getDoctorSearchList(DoctorSearchResModel model) {
        fragmentBillingBinding.setDoctor(model.get_DropdownValue().get(0));
    }

    @Override
    public void getSalesOriginList(SalesOriginResModel model) {
        fragmentBillingBinding.setSales(model.getGetSalesOriginResult().get_DropdownValue().get(0));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUSTOMER_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                fragmentBillingBinding.continueBtn.setAlpha(1);
                fragmentBillingBinding.continueBtn.setClickable(true);
                customerResult = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                fragmentBillingBinding.setCustomer(customerResult);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == DOCTOR_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DoctorSearchResModel.DropdownValueBean doctorResult = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                if (doctorResult != null) {
                    fragmentBillingBinding.setDoctor(doctorResult);
                }
                SalesOriginResModel.DropdownValueBean salesResult = (SalesOriginResModel.DropdownValueBean) data.getSerializableExtra("sales_info");
                if (salesResult != null) {
                    fragmentBillingBinding.setSales(salesResult);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == CORPORATE_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                CorporateModel.DropdownValueBean result = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                fragmentBillingBinding.setCorporate(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void corporatePrgTracking(CorporateModel.DropdownValueBean result){
        if(result.getCode().equalsIgnoreCase("5")){
            fragmentBillingBinding.setPrgTracking(true);
            fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
            fragmentBillingBinding.continueBtn.setClickable(false);
            fragmentBillingBinding.prgTrackingEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(TextUtils.isEmpty(editable) ){
                        fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
                        fragmentBillingBinding.continueBtn.setClickable(false);
                    }else{
                        if(!TextUtils.isEmpty(fragmentBillingBinding.customerMobile.getText().toString())
                                && !TextUtils.isEmpty(fragmentBillingBinding.customerName.getText().toString()) && !TextUtils.isEmpty(fragmentBillingBinding.prgTrackingEdit.getText().toString())){
                            continueBtnEnable();
                            fragmentBillingBinding.getCorporate().setPrg_Tracking(editable.toString());
                        }
                    }
                }
            });
        }else{
            fragmentBillingBinding.setPrgTracking(false);
        }
    }

    private void continueBtnEnable(){
        if(fragmentBillingBinding.getCorporate().getCode().equalsIgnoreCase("5") && TextUtils.isEmpty(fragmentBillingBinding.prgTrackingEdit.getText().toString())){
            fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
            fragmentBillingBinding.continueBtn.setClickable(false);
        }else {
            fragmentBillingBinding.continueBtn.setAlpha(1);
            fragmentBillingBinding.continueBtn.setClickable(true);
        }
    }
}