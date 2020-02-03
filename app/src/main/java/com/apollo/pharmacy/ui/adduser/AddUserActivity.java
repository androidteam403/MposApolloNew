package com.apollo.pharmacy.ui.adduser;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityAddUserBinding;
import com.apollo.pharmacy.ui.adduser.model.SpinnerPojo;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.searchuser.adapter.SearchCustomerAdapter;
import com.apollo.pharmacy.ui.searchuser.model.SearchCustomerAdapterModel;
import com.apollo.pharmacy.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import static com.apollo.pharmacy.root.ApolloMposApp.getContext;

public class AddUserActivity extends BaseActivity implements AddUserMvpView {

    @Inject
    AddUserMvpPresenter<AddUserMvpView> mPresenter;
    private ActivityAddUserBinding addUserBinding;
    private ArrayList<SpinnerPojo> arrGenderSpinner;
    private ArrayList<SpinnerPojo.City> arrCitySpinner;
    private ArrayList<SpinnerPojo.State> arrStateSpinner;
    private ArrayList<SpinnerPojo.District> arrDistrictSpinner;
    private ArrayList<SpinnerPojo.MaritalStatus> arrMaritalSpinner;

    private ArrayList<SearchCustomerAdapterModel> arrSearchCustomerAdapterModel = null;
    private SearchCustomerAdapter searchCustomerAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AddUserActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_user);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddUserActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        addUserBinding.setCallback(mPresenter);
        ArrayAdapter<SpinnerPojo> genderSpinnerPojo = new ArrayAdapter<SpinnerPojo>(getContext(), android.R.layout.simple_spinner_dropdown_item, getGender());
//        strFont = this.getString(R.font.roboto_regular);
//        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
//       Typeface tt = Typeface.createFromAsset(getAssets(),"strFont");
//        newCustomerBinding.spinner.setTypeface(tt);
        addUserBinding.gender.setAdapter(genderSpinnerPojo);

        ArrayAdapter<SpinnerPojo.City> citySpinnerPojo = new ArrayAdapter<SpinnerPojo.City>(getContext(), android.R.layout.simple_spinner_dropdown_item, getCity());
        addUserBinding.citySpinner.setAdapter(citySpinnerPojo);
        addUserBinding.citySpinner.setFocusableInTouchMode(false);

        ArrayAdapter<SpinnerPojo.State> stateSpinnerPojo = new ArrayAdapter<SpinnerPojo.State>(getContext(), android.R.layout.simple_spinner_dropdown_item, getState());
        addUserBinding.stateSpinner.setAdapter(stateSpinnerPojo);
        addUserBinding.stateSpinner.setFocusableInTouchMode(false);

        ArrayAdapter<SpinnerPojo.District> districtSpinnerPojo = new ArrayAdapter<SpinnerPojo.District>(getContext(), android.R.layout.simple_spinner_dropdown_item, getDistrict());
        addUserBinding.districtSpinner.setAdapter(districtSpinnerPojo);
        addUserBinding.districtSpinner.setFocusableInTouchMode(false);

        ArrayAdapter<SpinnerPojo.MaritalStatus> maritalStatusPojo = new ArrayAdapter<SpinnerPojo.MaritalStatus>(getContext(), android.R.layout.simple_spinner_dropdown_item, getMarital());
        addUserBinding.maritalStatusSpinner.setAdapter(maritalStatusPojo);
        addUserBinding.maritalStatusSpinner.setFocusableInTouchMode(false);
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
        if (addUserBinding.dateOfBirth.getText().toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addUserBinding.dateOfBirth.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate = "";
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            addUserBinding.dateOfBirth.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    @Override
    public void onSubmitClick() {
        if (validate()) {
            Toast.makeText(this, "You Submitted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAnniversaryClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;
        if (addUserBinding.anniversary.getText().toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addUserBinding.anniversary.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate = "";
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            addUserBinding.anniversary.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onRegistrationClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;
        if (addUserBinding.dateOfRegistration.getText().toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addUserBinding.dateOfRegistration.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate = "";
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            addUserBinding.dateOfRegistration.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
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
        String firstName = addUserBinding.firstName.getText().toString();
        String mobile = addUserBinding.mobile.getText().toString();
        String cardNumber = addUserBinding.cardNumber.getText().toString();
        if (firstName.isEmpty()) {
            addUserBinding.firstName.setError("First Name should not be empty");
            addUserBinding.firstName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(addUserBinding.firstName.getText().toString())) {
            addUserBinding.firstName.setError("Invalid First Name");
            addUserBinding.firstName.requestFocus();
            return false;
        } else if (mobile.isEmpty()) {
            addUserBinding.mobile.setError("Mobile Number should not be empty");
            addUserBinding.mobile.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(addUserBinding.mobile.getText().toString())) {
            addUserBinding.mobile.setError("Invalid Mobile Number");
            addUserBinding.mobile.requestFocus();
            return false;
        } else if (cardNumber.isEmpty()) {
            addUserBinding.cardNumber.setError("Card Number should not be empty");
            addUserBinding.cardNumber.requestFocus();
            return false;
        }
        return true;
    }
}
