package com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentCustMasterBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;


public class CustomerMasterFragment extends BaseFragment implements CustomerMasterMvpView {

    @Inject
    CustomerMasterPresenter<CustomerMasterMvpView> mPresenter;
    private FragmentCustMasterBinding fragmentCustMasterBinding;
    private String requiredDOBFormat = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCustMasterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cust_master, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(CustomerMasterFragment.this);
        return fragmentCustMasterBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        fragmentCustMasterBinding.setCallback(mPresenter);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fragmentCustMasterBinding.dateOfRegistration.setText(df.format(c));

        ArrayAdapter<SpinnerPojo> genderSpinnerPojo = new ArrayAdapter<>(getBaseActivity(), android.R.layout.simple_spinner_dropdown_item, getGender());
        fragmentCustMasterBinding.gender.setAdapter(genderSpinnerPojo);
        ArrayAdapter<SpinnerPojo.MaritalStatus> maritalStatusPojo = new ArrayAdapter<>(getBaseActivity(), android.R.layout.simple_spinner_dropdown_item, getMarital());
        fragmentCustMasterBinding.maritalStatusSpinner.setAdapter(maritalStatusPojo);
        fragmentCustMasterBinding.maritalStatusSpinner.setFocusableInTouchMode(false);
    }

    @Override
    public void onDateClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(fragmentCustMasterBinding.dateOfBirth.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = fragmentCustMasterBinding.dateOfBirth.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getBaseActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            requiredDOBFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
            fragmentCustMasterBinding.dateOfBirth.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 365.25 * 18)));
        dialog.show();
    }

    @Override
    public void onSubmitClick() {
        if (validate()) {
            mPresenter.handleCustomerAddService();
        }
    }

    @Override
    public void onAnniversaryClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(fragmentCustMasterBinding.anniversary.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = fragmentCustMasterBinding.anniversary.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getBaseActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            fragmentCustMasterBinding.anniversary.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onRegistrationClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(fragmentCustMasterBinding.dateOfRegistration.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = fragmentCustMasterBinding.dateOfRegistration.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getBaseActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            fragmentCustMasterBinding.dateOfRegistration.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onClickBackPressed() {

    }

    @Override
    public void addCustomerSuccess(AddCustomerResModel addCustomerResModel) {
        GetCustomerResponse.CustomerEntity customerEntity = new GetCustomerResponse.CustomerEntity();
        customerEntity.setSearchId(addCustomerResModel.getCustId());
        customerEntity.setCardName(addCustomerResModel.getFirstName());
        customerEntity.setCardNo(addCustomerResModel.getCardNumber());
        customerEntity.setCorpId(addCustomerResModel.getCorpId());
        customerEntity.setMobileNo(addCustomerResModel.getMobile());
        customerEntity.setTelephoneNo(addCustomerResModel.getTelephone());

//        Intent returnIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("customer_info", customerEntity);
//        returnIntent.putExtras(bundle);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
        showMessage(addCustomerResModel.getReturnMessage());
    }

    @Override
    public void addCustomerFailed(String errMsg) {
        showMessage(errMsg);
    }

    @Override
    public String getFirstName() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.firstName.getText())).toString();
    }

    @Override
    public String getMiddleName() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.middleName.getText())).toString();
    }

    @Override
    public String getLastName() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.lastName.getText())).toString();
    }

    @Override
    public int getAge() {
        if (Objects.requireNonNull(fragmentCustMasterBinding.age.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(fragmentCustMasterBinding.age.getText().toString());
        }
    }

    @Override
    public String getGenderOption() {
        if (fragmentCustMasterBinding.gender.getSelectedItem() == null) {
            return "";
        } else {
            return fragmentCustMasterBinding.gender.getSelectedItem().toString();
        }
    }

    @Override
    public String getMobile() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.mobile.getText())).toString();
    }

    @Override
    public String getAnniversary() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.anniversary.getText())).toString();
    }

    @Override
    public String getMaritalStatus() {
        if (fragmentCustMasterBinding.maritalStatusSpinner.getSelectedItem() == null) {
            return "";
        } else {
            return fragmentCustMasterBinding.maritalStatusSpinner.getSelectedItem().toString();
        }
    }

    @Override
    public int getNumberOfDependants() {
        if (Objects.requireNonNull(fragmentCustMasterBinding.numberOfDependents.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(fragmentCustMasterBinding.numberOfDependents.getText().toString());
        }
    }

    @Override
    public String getCardNumber() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.cardNumber.getText())).toString();
    }

    @Override
    public String getDateOfReg() {
        return Objects.requireNonNull(fragmentCustMasterBinding.dateOfRegistration.getText()).toString();
    }

    @Override
    public String getDOB() {
        return requiredDOBFormat;
    }

    @Override
    public String getPostalAddress() {
        return Objects.requireNonNull(fragmentCustMasterBinding.postalAddress.getText()).toString();
    }

    @Override
    public String getCityOption() {
        return Objects.requireNonNull(fragmentCustMasterBinding.cityEdittext.getText()).toString();
    }

    @Override
    public String getStateOption() {
        return Objects.requireNonNull(fragmentCustMasterBinding.stateEdittext.getText()).toString();
    }

    @Override
    public String getDistrictOption() {
        return Objects.requireNonNull(fragmentCustMasterBinding.districtEditText.getText()).toString();
    }

    @Override
    public String getZipCode() {
        return Objects.requireNonNull(fragmentCustMasterBinding.zipCode.getText()).toString();
    }

    @Override
    public String getEmail() {
        return Objects.requireNonNull(fragmentCustMasterBinding.email.getText()).toString();
    }

    @Override
    public String getTelephone() {
        return Objects.requireNonNull(fragmentCustMasterBinding.telephone.getText()).toString();
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

    private ArrayList<SpinnerPojo.MaritalStatus> getMarital() {
        ArrayList<SpinnerPojo.MaritalStatus> arrMaritalSpinner = new ArrayList<>();
        SpinnerPojo.MaritalStatus maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Married");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("UnMarried");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Single");
        arrMaritalSpinner.add(maritalStatus);
        return arrMaritalSpinner;
    }


    private boolean validate() {
        String firstName = Objects.requireNonNull(fragmentCustMasterBinding.firstName.getText()).toString();
        String mobile = Objects.requireNonNull(fragmentCustMasterBinding.mobile.getText()).toString();
        String cardNumber = Objects.requireNonNull(fragmentCustMasterBinding.cardNumber.getText()).toString();
        String dob = Objects.requireNonNull(fragmentCustMasterBinding.dateOfBirth.getText()).toString();

        if (firstName.isEmpty()) {
            fragmentCustMasterBinding.firstName.setError("First Name should not be empty");
            fragmentCustMasterBinding.firstName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(firstName)) {
            fragmentCustMasterBinding.firstName.setError("Invalid First Name");
            fragmentCustMasterBinding.firstName.requestFocus();
            return false;
        } else if (dob.isEmpty()) {
            fragmentCustMasterBinding.dateOfBirth.setError("Select any Date");
            fragmentCustMasterBinding.dateOfBirth.requestFocus();
            return false;
        } else if (mobile.isEmpty()) {
            fragmentCustMasterBinding.mobile.setError("Mobile Number should not be empty");
            fragmentCustMasterBinding.mobile.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(mobile)) {
            fragmentCustMasterBinding.mobile.setError("Invalid Mobile Number");
            fragmentCustMasterBinding.mobile.requestFocus();
            return false;
        } else if (cardNumber.isEmpty()) {
            fragmentCustMasterBinding.cardNumber.setError("Card Number should not be empty");
            fragmentCustMasterBinding.cardNumber.requestFocus();
            return false;
        }
        return true;
    }
}