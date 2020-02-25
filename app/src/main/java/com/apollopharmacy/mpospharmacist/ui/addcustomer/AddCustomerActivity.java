package com.apollopharmacy.mpospharmacist.ui.addcustomer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddCustomerBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

public class AddCustomerActivity extends BaseActivity implements AddCustomerMvpView {

    @Inject
    AddCustomerMvpPresenter<AddCustomerMvpView> mPresenter;
    private ActivityAddCustomerBinding addCustomerBinding;

    private ArrayList<SpinnerPojo> arrGenderSpinner;
    private ArrayList<SpinnerPojo.City> arrCitySpinner;
    private ArrayList<SpinnerPojo.State> arrStateSpinner;
    private ArrayList<SpinnerPojo.District> arrDistrictSpinner;
    private ArrayList<SpinnerPojo.MaritalStatus> arrMaritalSpinner;
    private String requiredDOBFormat = "";

    public static Intent getStartIntent(Context context, String inputNumber) {
        Intent intent = new Intent(context, AddCustomerActivity.class);
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
            String userInputNumber = (String) getIntent().getSerializableExtra("customer_number");
            addCustomerBinding.mobile.setText(userInputNumber);
        }
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        addCustomerBinding.dateOfRegistration.setText(df.format(c));

        ArrayAdapter<SpinnerPojo> genderSpinnerPojo = new ArrayAdapter<SpinnerPojo>(getContext(), android.R.layout.simple_spinner_dropdown_item, getGender());
        addCustomerBinding.gender.setAdapter(genderSpinnerPojo);

        ArrayAdapter<SpinnerPojo.City> citySpinnerPojo = new ArrayAdapter<SpinnerPojo.City>(getContext(), android.R.layout.simple_spinner_dropdown_item, getCity());
        addCustomerBinding.citySpinner.setAdapter(citySpinnerPojo);
        addCustomerBinding.citySpinner.setFocusableInTouchMode(false);

        ArrayAdapter<SpinnerPojo.State> stateSpinnerPojo = new ArrayAdapter<SpinnerPojo.State>(getContext(), android.R.layout.simple_spinner_dropdown_item, getState());
        addCustomerBinding.stateSpinner.setAdapter(stateSpinnerPojo);
        addCustomerBinding.stateSpinner.setFocusableInTouchMode(false);

        ArrayAdapter<SpinnerPojo.District> districtSpinnerPojo = new ArrayAdapter<SpinnerPojo.District>(getContext(), android.R.layout.simple_spinner_dropdown_item, getDistrict());
        addCustomerBinding.districtSpinner.setAdapter(districtSpinnerPojo);
        addCustomerBinding.districtSpinner.setFocusableInTouchMode(false);

        ArrayAdapter<SpinnerPojo.MaritalStatus> maritalStatusPojo = new ArrayAdapter<SpinnerPojo.MaritalStatus>(getContext(), android.R.layout.simple_spinner_dropdown_item, getMarital());
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
        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;
        if (addCustomerBinding.dateOfBirth.getText().toString().isEmpty()) {
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
            String selectedDate = "";
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
            mPresenter.handleCustomerAddService();
        }
    }

    @Override
    public void onAnniversaryClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;
        if (addCustomerBinding.anniversary.getText().toString().isEmpty()) {
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
            String selectedDate = "";
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            addCustomerBinding.anniversary.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onRegistrationClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;
        if (addCustomerBinding.dateOfRegistration.getText().toString().isEmpty()) {
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
            String selectedDate = "";
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
        if (addCustomerBinding.age.getText().toString().trim().isEmpty()) {
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
        if (addCustomerBinding.numberOfDependents.getText().toString().trim().isEmpty()) {
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
        return requiredDOBFormat;
    }

    @Override
    public String getPostalAddress() {
        return Objects.requireNonNull(addCustomerBinding.postalAddress.getText()).toString();
    }

    @Override
    public String getCityOption() {
        if (addCustomerBinding.citySpinner.getSelectedItem() == null) {
            return "";
        } else {
            return addCustomerBinding.citySpinner.getSelectedItem().toString();
        }
    }

    @Override
    public String getStateOption() {
        if (addCustomerBinding.stateSpinner.getSelectedItem() == null) {
            return "";
        } else {
            return addCustomerBinding.stateSpinner.getSelectedItem().toString();
        }
    }

    @Override
    public String getDistrictOption() {
        if (addCustomerBinding.districtSpinner.getSelectedItem() == null) {
            return "";
        } else {
            return addCustomerBinding.districtSpinner.getSelectedItem().toString();
        }
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
        arrGenderSpinner = new ArrayList<>();
        SpinnerPojo genderSpinnerPojo = new SpinnerPojo();
        genderSpinnerPojo.setGender("Male");
        arrGenderSpinner.add(genderSpinnerPojo);
        genderSpinnerPojo = new SpinnerPojo();
        genderSpinnerPojo.setGender("Female");
        arrGenderSpinner.add(genderSpinnerPojo);
        return arrGenderSpinner;
    }

    private ArrayList<SpinnerPojo.City> getCity() {
        arrCitySpinner = new ArrayList<>();
        SpinnerPojo.City cityName = new SpinnerPojo.City();
        cityName.setCity("Hyderbad");
        arrCitySpinner.add(cityName);
        cityName = new SpinnerPojo.City();
        cityName.setCity("Siddipet");
        arrCitySpinner.add(cityName);
        cityName = new SpinnerPojo.City();
        cityName.setCity("Warangal");
        arrCitySpinner.add(cityName);
        cityName = new SpinnerPojo.City();
        cityName.setCity("Karimnagar");
        arrCitySpinner.add(cityName);
        return arrCitySpinner;
    }

    private ArrayList<SpinnerPojo.State> getState() {
        arrStateSpinner = new ArrayList<>();
        SpinnerPojo.State stateName = new SpinnerPojo.State();
        stateName.setState("Telangana");
        arrStateSpinner.add(stateName);
        stateName = new SpinnerPojo.State();
        stateName.setState("Andhra Pradhesh");
        arrStateSpinner.add(stateName);
        stateName = new SpinnerPojo.State();
        stateName.setState("Tamilanadu");
        arrStateSpinner.add(stateName);
        stateName = new SpinnerPojo.State();
        stateName.setState("Karnataka");
        arrStateSpinner.add(stateName);
        return arrStateSpinner;
    }

    private ArrayList<SpinnerPojo.MaritalStatus> getMarital() {
        arrMaritalSpinner = new ArrayList<>();
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

    private ArrayList<SpinnerPojo.District> getDistrict() {
        arrDistrictSpinner = new ArrayList<>();
        SpinnerPojo.District districtName = new SpinnerPojo.District();
        districtName.setDistrict("Siddipet");
        arrDistrictSpinner.add(districtName);
        districtName = new SpinnerPojo.District();
        districtName.setDistrict("Ameerpet");
        arrDistrictSpinner.add(districtName);
        districtName = new SpinnerPojo.District();
        districtName.setDistrict("Kukatpally");
        arrDistrictSpinner.add(districtName);
        districtName = new SpinnerPojo.District();
        districtName.setDistrict("Miyapur");
        arrDistrictSpinner.add(districtName);
        return arrDistrictSpinner;
    }

    private boolean validate() {
        String firstName = addCustomerBinding.firstName.getText().toString();
        String mobile = addCustomerBinding.mobile.getText().toString();
        String cardNumber = addCustomerBinding.cardNumber.getText().toString();
        String dob = addCustomerBinding.dateOfBirth.getText().toString();

        if (firstName.isEmpty()) {
            addCustomerBinding.firstName.setError("First Name should not be empty");
            addCustomerBinding.firstName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(firstName)) {
            addCustomerBinding.firstName.setError("Invalid First Name");
            addCustomerBinding.firstName.requestFocus();
            return false;
        } else if (dob.isEmpty()) {
            addCustomerBinding.dateOfBirth.setError("Select any Date");
            addCustomerBinding.dateOfBirth.requestFocus();
            return false;
        }else if (mobile.isEmpty()) {
            addCustomerBinding.mobile.setError("Mobile Number should not be empty");
            addCustomerBinding.mobile.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(mobile)) {
            addCustomerBinding.mobile.setError("Invalid Mobile Number");
            addCustomerBinding.mobile.requestFocus();
            return false;
        } else if (cardNumber.isEmpty()) {
            addCustomerBinding.cardNumber.setError("Card Number should not be empty");
            addCustomerBinding.cardNumber.requestFocus();
            return false;
        }
        return true;
    }
}
