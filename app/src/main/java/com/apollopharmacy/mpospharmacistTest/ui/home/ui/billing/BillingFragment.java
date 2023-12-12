package com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentBillingBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.GetHBPUHIDDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.PatientMasterResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;


public class BillingFragment extends BaseFragment implements BillingMvpView, MainActivity.UserIneractionListener, MainActivity.OnBackPressedListener {

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
    private List<PatientMasterResponse.DropdownValue> patientMasterList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBillingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_billing, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BillingFragment.this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnUserIneractionListener(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnBackPressedListener(this);
        return fragmentBillingBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        mPresenter.getPatientMasterApiCall();
    }

    private void setUp() {
        fragmentBillingBinding.siteName.setText(mPresenter.getStoreName());
        fragmentBillingBinding.siteId.setText(mPresenter.getStoreId());
        fragmentBillingBinding.terminalId.setText(mPresenter.getTerminalId());
        getPatientMasterDropdown(patientMasterList);
        mPresenter.getTransactionID();
        fragmentBillingBinding.setCallbacks(mPresenter);
        if (mPresenter.getGlobalCDonfiguration() != null
                && mPresenter.getGlobalCDonfiguration().isISHBPStore()
                && mPresenter.getHBPConfing() != null
                && mPresenter.getHBPConfing().getUHIDBilling()) {
            fragmentBillingBinding.uhidCheckBox.setVisibility(View.VISIBLE);
        } else
            fragmentBillingBinding.uhidCheckBox.setVisibility(View.GONE);
        if (mPresenter.getGlobalCDonfiguration().isISHBPStore()) {
            fragmentBillingBinding.patientType.setVisibility(View.VISIBLE);
        } else {
            fragmentBillingBinding.patientType.setVisibility(View.GONE);
        }
        fragmentBillingBinding.uhidCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fragmentBillingBinding.customerSearchBox.setVisibility(View.GONE);
                    fragmentBillingBinding.customerName.setText(null);
                    fragmentBillingBinding.customerMobile.setText(null);
                    fragmentBillingBinding.customerName.setEnabled(true);
                    fragmentBillingBinding.prgTrackingEdit.setEnabled(true);
                    fragmentBillingBinding.prgTrackingEdit.setText(null);
                    BillingFragment.this.customerResult = null;
                    fragmentBillingBinding.setCustomer(customerResult);
                } else {
                    fragmentBillingBinding.customerSearchBox.setVisibility(View.VISIBLE);
                    fragmentBillingBinding.customerName.setText("");
                    fragmentBillingBinding.customerMobile.setText("");
                    fragmentBillingBinding.prgTrackingEdit.setText("");
                    fragmentBillingBinding.customerName.setEnabled(true);
                    fragmentBillingBinding.prgTrackingEdit.setEnabled(true);
                    fragmentBillingBinding.gender.setSelection(0);
                    BillingFragment.this.customerResult = null;
                    fragmentBillingBinding.setCustomer(customerResult);
                }
            }
        });

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
                    if (editable != null && editable.toString() != null && editable.toString().length() == 15) {
                        fragmentBillingBinding.getCorporate().setPrg_Tracking(editable.toString());
                        if (fragmentBillingBinding.uhidCheckBox.isChecked()) {
                            fragmentBillingBinding.uploadApi.setVisibility(View.GONE);
                            mPresenter.getUHIDDetails(editable.toString());
                        }
                    }
                } else {
                    fragmentBillingBinding.uploadApi.setVisibility(View.VISIBLE);
                }
            }
        });
        genderSpnr();
        if (mPresenter.enablescreens()) {
            mPresenter.onMposTabApiCall();
            turnOnScreen();
        }
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

    boolean isCorporatePayment = false;

    @Override
    public void onSucessStaffListData(PharmacyStaffApiRes staffApiRes) {
        newstaffApiRes = new PharmacyStaffApiRes();
        newstaffApiRes = staffApiRes;
        double availableAmount = Double.parseDouble(staffApiRes.getTotalBalance()) - Double.parseDouble(staffApiRes.getUsedBalance());
        fragmentBillingBinding.availablePoints.setText(String.valueOf(availableAmount));
        fragmentBillingBinding.getCorporate().setPrg_Tracking(fragmentBillingBinding.prgTrackingEdit.getText().toString());
        fragmentBillingBinding.customerDetailsArrow.setEnabled(false);
//        fragmentBillingBinding.doctorDetailsArrow.setEnabled(false);
        fragmentBillingBinding.corporateDetailsArrow.setEnabled(false);
        fragmentBillingBinding.prgTrackingEdit.setEnabled(false);
        isCorporatePayment = true;
        if (customerResult != null) {
            customerResult.setCardName(staffApiRes.getEmpName());
            customerResult.setMobileNo(staffApiRes.getRegMobileNo());
            fragmentBillingBinding.setCustomer(customerResult);
            fragmentBillingBinding.continueBtn.setAlpha(1);
            fragmentBillingBinding.continueBtn.setClickable(true);
        } else {
            customerResult = new GetCustomerResponse.CustomerEntity();
            customerResult.setCardName(staffApiRes.getEmpName());
            customerResult.setMobileNo(staffApiRes.getRegMobileNo());
            fragmentBillingBinding.setCustomer(customerResult);
            fragmentBillingBinding.continueBtn.setAlpha(1);
            fragmentBillingBinding.continueBtn.setClickable(true);
        }

    }


    @Override
    public void onFaliureStaffListData(PharmacyStaffApiRes pharmacyStaffApiRes) {
        if (customerResult != null) {
            String availableData = customerResult.getAvailablePoints();
            if (customerResult.getAvailablePoints() != null && customerResult.getAvailablePoints().equalsIgnoreCase("")) {
                isCorporatePayment = false;
                fragmentBillingBinding.availablePoints.setText("--");
                if (pharmacyStaffApiRes != null && pharmacyStaffApiRes.getMessage() != null) {
                    Toast.makeText(getContext(), pharmacyStaffApiRes.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                fragmentBillingBinding.availablePoints.setText(availableData);
            }
        } else {
            isCorporatePayment = false;
            fragmentBillingBinding.availablePoints.setText("--");
            if (pharmacyStaffApiRes != null && pharmacyStaffApiRes.getMessage() != null) {
                Toast.makeText(getContext(), pharmacyStaffApiRes.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
        if (fragmentBillingBinding.uhidCheckBox != null && fragmentBillingBinding.uhidCheckBox.isChecked() && TextUtils.isEmpty(fragmentBillingBinding.prgTrackingEdit.getText().toString())) {
            showMessage("Enter UHID number");
            return;
        }
        if (fragmentBillingBinding.getCustomer() != null) {
            String codeDes = fragmentBillingBinding.salesOrigin.getSelectedItem().toString();
            String[] parts = codeDes.split("-");
            String salesCode = parts[0];

            fragmentBillingBinding.getCustomer().setCardNo(fragmentBillingBinding.prgTrackingEdit.getText().toString().trim());

            boolean isUhidSelected = false;
            if (fragmentBillingBinding.uhidCheckBox.isChecked()) {
                isUhidSelected = true;
            }

            String selectedPatientCodeDes = fragmentBillingBinding.patientType.getSelectedItem().toString();
            startActivity(AddItemActivity.getStartIntent(getBaseActivity(), true, fragmentBillingBinding.getCustomer(), fragmentBillingBinding.getDoctor(), fragmentBillingBinding.getCorporate(), transactionIdItem, corporateModel, newstaffApiRes, fragmentBillingBinding.availablePoints.getText().toString(), fragmentBillingBinding.prgTrackingEdit.getText().toString(), salesCode, isUhidSelected, isCorporatePayment, selectedPatientCodeDes));
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

                boolean isUhidSelected = false;
                if (fragmentBillingBinding.uhidCheckBox.isChecked()) {
                    isUhidSelected = true;
                }

                String selectedPatientCodeDes = fragmentBillingBinding.patientType.getSelectedItem().toString();
                startActivity(AddItemActivity.getStartIntent(getBaseActivity(), true, entity, fragmentBillingBinding.getDoctor(), fragmentBillingBinding.getCorporate(), transactionIdItem, corporateModel, newstaffApiRes, fragmentBillingBinding.prgTrackingEdit.getText().toString(), salesCode1, isUhidSelected, isCorporatePayment, selectedPatientCodeDes));
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
                        fragmentBillingBinding.prgTrackingEdit.setText("");
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
                    Log.d("Get Corporate_id", new Gson().toJson(customerResult));
                    if (customerResult.getCorpId() != null) {
                        for (CorporateModel.DropdownValueBean corporateModel : corporateModel.get_DropdownValue())
                            if (corporateModel.getCode().equalsIgnoreCase(customerResult.getCorpId())) {
                                fragmentBillingBinding.setCorporate(corporateModel);
                            }

                    } else {
                        fragmentBillingBinding.setCorporate(corporateModel.get_DropdownValue().get(0));
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == DOCTOR_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DoctorSearchResModel.DropdownValueBean doctorResult = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                if (doctorResult != null) {
                    fragmentBillingBinding.setDoctor(doctorResult);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == CORPORATE_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                CorporateModel.DropdownValueBean result = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                result.setPrg_Tracking(fragmentBillingBinding.prgTrackingEdit.getText().toString());
                fragmentBillingBinding.setCorporate(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
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


    private List<RowsEntity> rowsEntitiesList;

    @Override
    public void onSucessPlayList() {
        rowsEntitiesList = mPresenter.getDataListEntity();
        for (int i = 0; i < rowsEntitiesList.size(); i++) {
            if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                    rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                File file = new File(path);
                if (!file.exists()) {
                    mPresenter.onDownloadApiCall(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
                            rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), i);
                    break;
                }
            }
        }
    }

    @Override
    public void updateUHIDDetails(GetHBPUHIDDetailsResponse getHBPUHIDDetailsResponse) {
        if (getHBPUHIDDetailsResponse != null) {
            if (getHBPUHIDDetailsResponse.isStatus()) {
                fragmentBillingBinding.customerName.setText(getHBPUHIDDetailsResponse.getUhid_details().getPatientname());
                fragmentBillingBinding.customerMobile.setText(getHBPUHIDDetailsResponse.getUhid_details().getContact());
                fragmentBillingBinding.customerName.setEnabled(false);
                fragmentBillingBinding.prgTrackingEdit.setEnabled(false);
                if (getHBPUHIDDetailsResponse.getUhid_details().getGender().equalsIgnoreCase("71"))
                    fragmentBillingBinding.gender.setSelection(1);
                else if (getHBPUHIDDetailsResponse.getUhid_details().getGender().equalsIgnoreCase("72"))
                    fragmentBillingBinding.gender.setSelection(0);
            } else {
                ExitInfoDialog dialogView = new ExitInfoDialog(getContext());
                dialogView.setDialogDismiss();
                dialogView.setTitle("");
                dialogView.setPositiveLabel("OK");
                dialogView.setSubtitle(getHBPUHIDDetailsResponse.getMessage() + "!!");
                dialogView.setPositiveListener(view -> {
                    fragmentBillingBinding.customerName.setText(null);
                    fragmentBillingBinding.customerMobile.setText(null);
                    fragmentBillingBinding.customerName.setEnabled(true);
                    fragmentBillingBinding.prgTrackingEdit.setEnabled(true);
                    fragmentBillingBinding.prgTrackingEdit.setText(null);
                    dialogView.dismiss();
                });
                dialogView.show();
            }
        }
    }

    @Override
    public CorporateModel.DropdownValueBean getCorporateModule() {
        return fragmentBillingBinding.getCorporate();
    }

    @Override
    public void onSuccessPatientMaster(PatientMasterResponse patientMasterResponse) {
        this.patientMasterList = patientMasterResponse.getDropdownValue();
        setUp();
    }

    private boolean stopLooping;

    public void handelPlayList() {
        if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
            if (!onPause) {
                if (!stopLooping) {
                    boolean isAllFilesExist = false;
                    for (int i = 0; i < rowsEntitiesList.size(); i++) {
                        if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                                rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                            if (!rowsEntitiesList.get(i).isPlayed()) {
                                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                                File file = new File(path);
                                if (file.exists()) {
                                    playListData(path, i);
                                    isAllFilesExist = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!isAllFilesExist) {
                        for (int i = 0; i < rowsEntitiesList.size(); i++) {
                            rowsEntitiesList.get(i).setPlayed(false);
                        }
                        handelPlayList();
                    }
                }
            }
        }
    }

    public void playListData(String filePath, int pos) {
        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        fragmentBillingBinding.imageView.setImageBitmap(myBitmap);
        fragmentBillingBinding.imageView.setVisibility(View.VISIBLE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        ((MainActivity) getActivity()).closeDrawer();
        // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentBillingBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsEntitiesList.get(pos).setPlayed(true);
                if (pos == rowsEntitiesList.size() - 1) {
                    for (RowsEntity rowsEntity : rowsEntitiesList) {
                        rowsEntity.setPlayed(false);
                    }
                }
                handelPlayList();
            }
        }, 5000);
    }

    private boolean onPause;

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        onPause = false;
        idealScreen();
    }


    private Handler handler;
    private Runnable r;

    public void idealScreen() {
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                if (onPause) {
                    stopLooping = true;
                } else {
                    stopLooping = false;
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
                        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    }
                }
                handelPlayList();
                fragmentBillingBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        fragmentBillingBinding.imageView.setVisibility(View.GONE);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                        stopLooping = true;
                    }
                });

            }
        };
        startHandler();
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 180 * 1000);
    }

    @Override
    public void userInteraction() {
        stopHandler();
        startHandler();
    }

    public void turnOnScreen() {
        final Window win = getActivity().getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @Override
    public void doBack() {
//        onBackPressedClick();
        Dialog dialog = new Dialog(getContext());//R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_cancel, null, false);
        dialogCancelBinding.title.setText("Alert!");
        dialogCancelBinding.dialogMessage.setText("Do you want to exit?");
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            getActivity().finish();
            stopLooping = true;
        });
        dialog.show();
    }

    public void getPatientMasterDropdown(List<PatientMasterResponse.DropdownValue> patientMasterDropdown) {
        fragmentBillingBinding.patientType.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < patientMasterDropdown.size(); i++) {
            String getPatientMasterCode = patientMasterDropdown.get(i).getCode();
            String getPatientMasterDescription = patientMasterDropdown.get(i).getDisplayText();
            data.add(getPatientMasterCode + "-" + getPatientMasterDescription);
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
        fragmentBillingBinding.patientType.setAdapter(adapter);
        fragmentBillingBinding.patientType.setSelection(0);
        fragmentBillingBinding.patientType.getEditText().setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        fragmentBillingBinding.patientType.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        fragmentBillingBinding.patientType.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
        });
    }
}