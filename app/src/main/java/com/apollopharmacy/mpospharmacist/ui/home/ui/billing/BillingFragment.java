package com.apollopharmacy.mpospharmacist.ui.home.ui.billing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentBillingBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacist.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


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
    private CorporateModel.DropdownValueBean corporateEntity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBillingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_billing, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BillingFragment.this);
        return fragmentBillingBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        fragmentBillingBinding.siteName.setText(mPresenter.getStoreName());
        fragmentBillingBinding.siteId.setText(mPresenter.getStoreId());
        fragmentBillingBinding.terminalId.setText(mPresenter.getTerminalId());
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
                if (TextUtils.isEmpty(editable)) {
                    fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
                    fragmentBillingBinding.continueBtn.setClickable(false);
                } else {
                    if (!TextUtils.isEmpty(fragmentBillingBinding.customerMobile.getText().toString())
                            && !TextUtils.isEmpty(fragmentBillingBinding.customerName.getText().toString())) {
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
                if (TextUtils.isEmpty(editable)) {
                    fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
                    fragmentBillingBinding.continueBtn.setClickable(false);
                } else {
                    if (!TextUtils.isEmpty(fragmentBillingBinding.customerMobile.getText().toString())
                            && !TextUtils.isEmpty(fragmentBillingBinding.customerName.getText().toString())) {
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
        genderSpnr();
    }

    private void genderSpnr() {
        fragmentBillingBinding.gender.getEditText().setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        fragmentBillingBinding.gender.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SpinnerPojo> genderSpinnerPojo = new ArrayAdapter<SpinnerPojo>(getContext(), android.R.layout.simple_spinner_item, getGender()) {
            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        genderSpinnerPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentBillingBinding.gender.setAdapter(genderSpinnerPojo);
        fragmentBillingBinding.gender.setSelection(0);
    }

    private ArrayList<SpinnerPojo> getGender() {
        ArrayList<SpinnerPojo> arrGenderSpinner = new ArrayList<>();
        SpinnerPojo genderSpinnerPojo = new SpinnerPojo();
        genderSpinnerPojo.setGender("Male");
        arrGenderSpinner.add(genderSpinnerPojo);
        genderSpinnerPojo = new SpinnerPojo();
        genderSpinnerPojo.setGender("Female");
        arrGenderSpinner.add(genderSpinnerPojo);
        return arrGenderSpinner;
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
    public void onUploadApiCall() {
        mPresenter.getPharmacyStaffApiDetails("ENQUIRY");
    }

    private PharmacyStaffApiRes newstaffApiRes;


    @Override
    public void onSucessStaffListData(PharmacyStaffApiRes staffApiRes) {
        newstaffApiRes = new PharmacyStaffApiRes();
        newstaffApiRes = staffApiRes;
        double availableAmount = Double.parseDouble(staffApiRes.getTotalBalance()) - Double.parseDouble(staffApiRes.getUsedBalance());
        fragmentBillingBinding.availablePoints.setText(String.valueOf(availableAmount));

    }

    @Override
    public CorporateModel.DropdownValueBean getCorporateModule() {
        return corporateEntity;
    }

    @Override
    public void onFaliureStaffListData() {
        if (customerResult != null) {
            String availableData = customerResult.getAvailablePoints();
            if (customerResult.getAvailablePoints().equalsIgnoreCase("")) {
                fragmentBillingBinding.availablePoints.setText("--");
            } else {
                fragmentBillingBinding.availablePoints.setText(availableData);
            }
        } else {
            fragmentBillingBinding.availablePoints.setText("--");
        }
    }

    @Override
    public void onContinueBtnClick() {

        if (fragmentBillingBinding.getDoctor() == null) {
            showMessage("please select Doctor");
            return;
        } else if (fragmentBillingBinding.getCorporate() == null) {
            showMessage("please select Corporate");
            return;
        }
        if (fragmentBillingBinding.getCustomer() != null) {
//            StringTokenizer split = new StringTokenizer(fragmentBillingBinding.salesOrigin.getSelectedItem().toString(), "-");
            String codeDes = fragmentBillingBinding.salesOrigin.getSelectedItem().toString();
            String[] parts = codeDes.split("-");
            String salesCode = parts[0];

            startActivity(AddItemActivity.getStartIntent(getBaseActivity(), true, fragmentBillingBinding.getCustomer(), fragmentBillingBinding.getDoctor(), fragmentBillingBinding.getCorporate(), transactionIdItem, corporateModel, newstaffApiRes, fragmentBillingBinding.availablePoints.getText().toString(), fragmentBillingBinding.prgTrackingEdit.getText().toString(), salesCode));
            getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else {
            if (fragmentBillingBinding.customerName.getText().toString().isEmpty()) {
                fragmentBillingBinding.customerName.setError("Enter Customer Name");
            } else if (fragmentBillingBinding.customerMobile.getText().toString().length() < 10) {
                fragmentBillingBinding.customerMobile.setError("Enter valid mobile number");
            } else {
                String codeDes = fragmentBillingBinding.salesOrigin.getSelectedItem().toString();
                String[] parts = codeDes.split("-");
                String salesCode1 = parts[0];
                GetCustomerResponse.CustomerEntity entity = new GetCustomerResponse.CustomerEntity();
                entity.setCardName(fragmentBillingBinding.customerName.getText().toString());
                entity.setMobileNo(fragmentBillingBinding.customerMobile.getText().toString());
                startActivity(AddItemActivity.getStartIntent(getBaseActivity(), true, entity, fragmentBillingBinding.getDoctor(), fragmentBillingBinding.getCorporate(), transactionIdItem, corporateModel, newstaffApiRes, fragmentBillingBinding.prgTrackingEdit.getText().toString(), salesCode1));
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
    public void getSalesListDropDown(SalesOriginResModel model) {
        fragmentBillingBinding.salesOrigin.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < model.getGetSalesOriginResult().get_DropdownValue().size(); i++) {
            String getSalesCode = model.getGetSalesOriginResult().get_DropdownValue().get(i).getCode();
            String getSalesDescription = model.getGetSalesOriginResult().get_DropdownValue().get(i).getDisplayText();
            data.add(getSalesCode + "-" + getSalesDescription);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, data) {

            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentBillingBinding.salesOrigin.setAdapter(adapter);
        fragmentBillingBinding.salesOrigin.setSelection(0);
        fragmentBillingBinding.salesOrigin.getEditText().setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        fragmentBillingBinding.salesOrigin.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        fragmentBillingBinding.salesOrigin.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
        });
    }

    @Override
    public String getPrgTracing() {
        return fragmentBillingBinding.prgTrackingEdit.getText().toString();
    }

    @Override
    public void partialPaymentDialog(String title, String description) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this.getContext());
        dialogView.setTitle(title);
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(description);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUSTOMER_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                fragmentBillingBinding.continueBtn.setAlpha(1);
                fragmentBillingBinding.continueBtn.setClickable(true);
                customerResult = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                if (customerResult != null) {
                    fragmentBillingBinding.setCustomer(customerResult);
                    fragmentBillingBinding.prgTrackingEdit.setText(customerResult.getCardNo());
                    fragmentBillingBinding.tier.setText(customerResult.getTier());
                    fragmentBillingBinding.availablePoints.setText(customerResult.getAvailablePoints());
                    if (customerResult.getCardNo() == null || customerResult.getCardNo().equalsIgnoreCase("")) {
                        fragmentBillingBinding.prgTrackingEdit.setText("--");
                    } else {
                        fragmentBillingBinding.prgTrackingEdit.setText(customerResult.getCardNo());
                    }
                    if (customerResult.getTier() == null || customerResult.getTier().equalsIgnoreCase("")) {
                        fragmentBillingBinding.tier.setText("--");
                    } else {
                        fragmentBillingBinding.tier.setText(customerResult.getTier());
                    }
                    if (customerResult.getTier() == null || customerResult.getAvailablePoints().equalsIgnoreCase("")) {
                        fragmentBillingBinding.availablePoints.setText("--");
                    } else {
                        fragmentBillingBinding.availablePoints.setText(customerResult.getAvailablePoints());
                    }
                    if (customerResult.getCorpId() != null) {
                        for (CorporateModel.DropdownValueBean corporateModel : corporateModel.get_DropdownValue())
                            if (corporateModel.getCode().equalsIgnoreCase(customerResult.getCorpId())) {
                                fragmentBillingBinding.setCorporate(corporateModel);
                            }

                    } else {
                        fragmentBillingBinding.setCorporate(corporateModel.get_DropdownValue().get(0));
//                        fragmentBillingBinding.prgTrackingEdit.setText("--");
//                        fragmentBillingBinding.tier.setText("--");
//                        fragmentBillingBinding.availablePoints.setText("--");
                    }
                }
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
//                SalesOriginResModel.DropdownValueBean salesResult = (SalesOriginResModel.DropdownValueBean) data.getSerializableExtra("sales_info");
//                if (salesResult != null) {
//                    fragmentBillingBinding.setSales(salesResult);
//                }
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

    private void corporatePrgTracking(CorporateModel.DropdownValueBean result) {
        if (result.getCode().equalsIgnoreCase("5")) {
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
                    if (TextUtils.isEmpty(editable)) {
                        fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
                        fragmentBillingBinding.continueBtn.setClickable(false);
                    } else {
                        if (!TextUtils.isEmpty(fragmentBillingBinding.customerMobile.getText().toString())
                                && !TextUtils.isEmpty(fragmentBillingBinding.customerName.getText().toString()) && !TextUtils.isEmpty(fragmentBillingBinding.prgTrackingEdit.getText().toString())) {
                            continueBtnEnable();
                            fragmentBillingBinding.getCorporate().setPrg_Tracking(editable.toString());
                        }
                    }
                }
            });
        } else {
            fragmentBillingBinding.setPrgTracking(false);
        }
    }

    private void continueBtnEnable() {
        if (fragmentBillingBinding.getCorporate().getCode().equalsIgnoreCase("5") && TextUtils.isEmpty(fragmentBillingBinding.prgTrackingEdit.getText().toString())) {
            fragmentBillingBinding.continueBtn.setAlpha((float) 0.3);
            fragmentBillingBinding.continueBtn.setClickable(false);
        } else {
            fragmentBillingBinding.continueBtn.setAlpha(1);
            fragmentBillingBinding.continueBtn.setClickable(true);
        }
    }
}