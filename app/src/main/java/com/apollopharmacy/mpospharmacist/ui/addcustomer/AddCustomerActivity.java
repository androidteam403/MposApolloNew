package com.apollopharmacy.mpospharmacist.ui.addcustomer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddCustomerBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.tiper.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

public class AddCustomerActivity extends BaseActivity implements AddCustomerMvpView {

    @Inject
    AddCustomerMvpPresenter<AddCustomerMvpView> mPresenter;
    private ActivityAddCustomerBinding addCustomerBinding;

    private String requiredDOBFormat = "";
    private GetCustomerResponse.CustomerEntity userInputNumber;

    public static Intent getStartIntent(Context context, boolean isEdit,GetCustomerResponse.CustomerEntity inputNumber) {
        Intent intent = new Intent(context, AddCustomerActivity.class);
        intent.putExtra("is_edit_mode",isEdit);
        intent.putExtra("customer_number", inputNumber);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addCustomerBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddCustomerActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        addCustomerBinding.setCallback(mPresenter);

        if (getIntent() != null) {
             userInputNumber = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_number");
            addCustomerBinding.setCustomerDetails(userInputNumber);

//            if (userInputNumber != null) {
//                addCustomerBinding.mobile.setText(userInputNumber.getMobileNo());
//                addCustomerBinding.mobile.setSelection(userInputNumber.getMobileNo().length());
//            }
        }
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//        addCustomerBinding.dateOfRegistration.setText(df.format(c));
        addCustomerBinding.gender.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SpinnerPojo> genderSpinnerPojo = new ArrayAdapter<SpinnerPojo>(getContext(), android.R.layout.simple_spinner_item, getGender()){
            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView,@NotNull  ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        genderSpinnerPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCustomerBinding.gender.setAdapter(genderSpinnerPojo);

//        ArrayAdapter<SpinnerPojo.City> citySpinnerPojo = new ArrayAdapter<SpinnerPojo.City>(getContext(), android.R.layout.simple_spinner_dropdown_item, getCity());
//        addCustomerBinding.citySpinner.setAdapter(citySpinnerPojo);
//        addCustomerBinding.citySpinner.setFocusableInTouchMode(false);
//
//        ArrayAdapter<SpinnerPojo.State> stateSpinnerPojo = new ArrayAdapter<SpinnerPojo.State>(getContext(), android.R.layout.simple_spinner_dropdown_item, getState());
//        addCustomerBinding.stateSpinner.setAdapter(stateSpinnerPojo);
//        addCustomerBinding.stateSpinner.setFocusableInTouchMode(false);
//
//        ArrayAdapter<SpinnerPojo.District> districtSpinnerPojo = new ArrayAdapter<SpinnerPojo.District>(getContext(), android.R.layout.simple_spinner_dropdown_item, getDistrict());
//        addCustomerBinding.districtSpinner.setAdapter(districtSpinnerPojo);
//        addCustomerBinding.districtSpinner.setFocusableInTouchMode(false);
        addCustomerBinding.maritalStatusSpinner.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SpinnerPojo.MaritalStatus> maritalStatusPojo = new ArrayAdapter<SpinnerPojo.MaritalStatus>(getApplicationContext(), android.R.layout.simple_spinner_item, getMarital()){

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView,@NotNull  ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        maritalStatusPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCustomerBinding.maritalStatusSpinner.setAdapter(maritalStatusPojo);
        addCustomerBinding.maritalStatusSpinner.setFocusableInTouchMode(false);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onDateClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(addCustomerBinding.dateOfBirth.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addCustomerBinding.dateOfBirth.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            requiredDOBFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
            addCustomerBinding.dateOfBirth.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 365.25 * 18)));
        dialog.show();
    }

    @Override
    public void onSubmitClick() {
        if (validate()) {
            boolean isEditMode = getIntent().getBooleanExtra("is_edit_mode",false);
            if(isEditMode)
                submitEditMode();
            else
                mPresenter.handleCustomerAddService();
        }
    }

    private void submitEditMode(){
        GetCustomerResponse.CustomerEntity customerEntity = new GetCustomerResponse.CustomerEntity();
        customerEntity.setSearchId(userInputNumber.getCustId());
        customerEntity.setCardName(getFirstName());
        customerEntity.setMiddleName(getMiddleName());
        customerEntity.setLastName(getLastName());
        customerEntity.setCardNo(userInputNumber.getCardNo());
        customerEntity.setEmail(getEmail());
        customerEntity.setMobileNo(getMobile());
        customerEntity.setTelephoneNo(getTelephone());
        customerEntity.setAge(addCustomerBinding.age.getText().toString());
        customerEntity.setGender(getGenderOption());
        customerEntity.setMaritalStatus(getMaritalStatus());
        customerEntity.setDob(getDOB());
        customerEntity.setDistrict(getDistrictOption());
        customerEntity.setCity(getCityOption());
        customerEntity.setState(getStateOption());
        customerEntity.setZipCode(getZipCode());
        customerEntity.setPostalAddress(getPostalAddress());
        customerEntity.setAnniversary(getAnniversary());
        customerEntity.setNumberOfDependents(addCustomerBinding.numberOfDependents.getText().toString());
        customerEntity.setDateOfRegistration(getDateOfReg());
        customerEntity.setCorpId(userInputNumber.getCorpId());

        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer_info", customerEntity);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    @Override
    public void onAnniversaryClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(addCustomerBinding.anniversary.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addCustomerBinding.anniversary.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            addCustomerBinding.anniversary.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onRegistrationClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(addCustomerBinding.dateOfRegistration.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addCustomerBinding.dateOfRegistration.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            addCustomerBinding.dateOfRegistration.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
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

        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer_info", customerEntity);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

        Toast.makeText(this, addCustomerResModel.getReturnMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCustomerFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getFirstName() {
        return (Objects.requireNonNull(addCustomerBinding.firstName.getText())).toString();
    }

    @Override
    public String getMiddleName() {
        return (Objects.requireNonNull(addCustomerBinding.middleName.getText())).toString();
    }

    @Override
    public String getLastName() {
        return (Objects.requireNonNull(addCustomerBinding.lastName.getText())).toString();
    }

    @Override
    public int getAge() {
        if (Objects.requireNonNull(addCustomerBinding.age.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(addCustomerBinding.age.getText().toString());
        }
    }

    @Override
    public String getGenderOption() {
        if (addCustomerBinding.gender.getSelectedItem() == null) {
            return "";
        } else {
            return addCustomerBinding.gender.getSelectedItem().toString();
        }
    }

    @Override
    public String getMobile() {
        return (Objects.requireNonNull(addCustomerBinding.mobile.getText())).toString();
    }

    @Override
    public String getAnniversary() {
        return (Objects.requireNonNull(addCustomerBinding.anniversary.getText())).toString();
    }

    @Override
    public String getMaritalStatus() {
        if (addCustomerBinding.maritalStatusSpinner.getSelectedItem() == null) {
            return "";
        } else {
            return addCustomerBinding.maritalStatusSpinner.getSelectedItem().toString();
        }
    }

    @Override
    public int getNumberOfDependants() {
        if (Objects.requireNonNull(addCustomerBinding.numberOfDependents.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(addCustomerBinding.numberOfDependents.getText().toString());
        }
    }

    @Override
    public String getCardNumber() {
        return (Objects.requireNonNull(addCustomerBinding.cardNumber.getText())).toString();
    }

    @Override
    public String getDateOfReg() {
        return Objects.requireNonNull(addCustomerBinding.dateOfRegistration.getText()).toString();
    }

    @Override
    public String getDOB() {
        if(TextUtils.isEmpty(requiredDOBFormat)){
            return CommonUtils.getCurrentDate("dd-MMM-yyyy");
        }else {
            return requiredDOBFormat;
        }
    }

    @Override
    public String getPostalAddress() {
        return Objects.requireNonNull(addCustomerBinding.postalAddress.getText()).toString();
    }

    @Override
    public String getCityOption() {
        return Objects.requireNonNull(addCustomerBinding.cityEdittext.getText()).toString();
    }

    @Override
    public String getStateOption() {
        return Objects.requireNonNull(addCustomerBinding.stateEdittext.getText()).toString();
    }

    @Override
    public String getDistrictOption() {
        return Objects.requireNonNull(addCustomerBinding.districtEditText.getText()).toString();
    }

    @Override
    public String getZipCode() {
        return Objects.requireNonNull(addCustomerBinding.zipCode.getText()).toString();
    }

    @Override
    public String getEmail() {
        return Objects.requireNonNull(addCustomerBinding.email.getText()).toString();
    }

    @Override
    public String getTelephone() {
        return Objects.requireNonNull(addCustomerBinding.telephone.getText()).toString();
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
        String firstName = Objects.requireNonNull(addCustomerBinding.firstName.getText()).toString();
        String mobile = Objects.requireNonNull(addCustomerBinding.mobile.getText()).toString();
        String cardNumber = Objects.requireNonNull(addCustomerBinding.cardNumber.getText()).toString();
        String email = Objects.requireNonNull(addCustomerBinding.email.getText()).toString();
        String zipCode = Objects.requireNonNull(addCustomerBinding.zipCode.getText()).toString();

        if (firstName.isEmpty()) {
            addCustomerBinding.firstName.setError("First Name should not be empty");
            addCustomerBinding.firstName.requestFocus();
            return false;
        } else if (cardNumber.isEmpty()) {
            addCustomerBinding.cardNumber.setError("Card Number should not be empty");
            addCustomerBinding.cardNumber.requestFocus();
            return false;
        } else if (cardNumber.length() < 10) {
            addCustomerBinding.cardNumber.setError("Card Number Minimum 10 characters");
            addCustomerBinding.cardNumber.requestFocus();
            return false;
        }else if(!email.isEmpty() && !CommonUtils.isValidEmail(email)){
            addCustomerBinding.email.setError("Enter Valid Email");
            addCustomerBinding.email.requestFocus();
            return false;
        } else if (mobile.isEmpty()) {
            addCustomerBinding.mobile.setError("Mobile Number should not be empty");
            addCustomerBinding.mobile.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(mobile)) {
            addCustomerBinding.mobile.setError("Invalid Mobile Number");
            addCustomerBinding.mobile.requestFocus();
            return false;
        }else if(!zipCode.isEmpty() && zipCode.length() < 6){
            addCustomerBinding.zipCode.setError("Enter Valid ZipCode");
            addCustomerBinding.zipCode.requestFocus();
            return false;
        }

        return true;
    }
}
