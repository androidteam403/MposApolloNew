package com.apollo.pharmacy.ui.adduser;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityAddUserBinding;
import com.apollo.pharmacy.ui.adduser.model.GenderSpinnerPojo;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.searchuser.adapter.SearchCustomerAdapter;
import com.apollo.pharmacy.ui.searchuser.model.SearchCustomerAdapterModel;

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
    private ArrayList<GenderSpinnerPojo> arrGenderSpinner;

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


        ArrayAdapter<GenderSpinnerPojo> genderSpinnerPojo = new ArrayAdapter<GenderSpinnerPojo>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, getGender());

//        strFont = this.getString(R.font.roboto_regular);
//        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
//       Typeface tt = Typeface.createFromAsset(getAssets(),"strFont");
//        newCustomerBinding.spinner.setTypeface(tt);
        addUserBinding.spinner.setAdapter(genderSpinnerPojo);
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

    private ArrayList<GenderSpinnerPojo> getGender() {
        arrGenderSpinner = new ArrayList<>();
        GenderSpinnerPojo genderSpinnerPojo = new GenderSpinnerPojo();
        genderSpinnerPojo.setGender("Male");
        arrGenderSpinner.add(genderSpinnerPojo);

        genderSpinnerPojo = new GenderSpinnerPojo();
        genderSpinnerPojo.setGender("Female");
        arrGenderSpinner.add(genderSpinnerPojo);

        return arrGenderSpinner;
    }
}
