package com.apollo.pharmacy.ui.adddoctor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityAddDoctorBinding;
import com.apollo.pharmacy.databinding.ActivityAddUserBinding;
import com.apollo.pharmacy.ui.adduser.AddUserMvpPresenter;
import com.apollo.pharmacy.ui.adduser.AddUserMvpView;
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

public class AddDoctorActivity extends BaseActivity implements AddDoctorMvpView {

    @Inject
    AddDoctorMvpPresenter<AddDoctorMvpView> mPresenter;
    private ActivityAddDoctorBinding addDoctorBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AddDoctorActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDoctorBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_doctor);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddDoctorActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
