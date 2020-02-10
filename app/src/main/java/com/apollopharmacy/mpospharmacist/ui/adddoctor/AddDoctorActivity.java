package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddDoctorBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import javax.inject.Inject;

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
        addDoctorBinding.setCallback(mPresenter);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onSubmitClick() {
        if (validate()) {
            Toast.makeText(this, "You submitted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    private boolean validate() {
        if (addDoctorBinding.doctorRegNumber.getText().toString().isEmpty()) {
            addDoctorBinding.doctorRegNumber.setError("Doctor registration number should not be empty");
            addDoctorBinding.doctorRegNumber.requestFocus();
            return false;
        } else if (addDoctorBinding.doctorName.getText().toString().isEmpty()) {
            addDoctorBinding.doctorName.setError("Doctor name should not be empty");
            addDoctorBinding.doctorName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(addDoctorBinding.doctorName.getText().toString())) {
            addDoctorBinding.doctorName.setError("Invalid doctor name");
            addDoctorBinding.doctorName.requestFocus();
            return false;
        } else if (addDoctorBinding.speciality.getText().toString().isEmpty()) {
            addDoctorBinding.speciality.setError("Speciality should not be empty");
            addDoctorBinding.speciality.requestFocus();
            return false;
        } else if (addDoctorBinding.placeOfPractice.getText().toString().isEmpty()) {
            addDoctorBinding.placeOfPractice.setError("Place of Practice should not be empty");
            addDoctorBinding.placeOfPractice.requestFocus();
            return false;
        } else if (addDoctorBinding.address.getText().toString().isEmpty()) {
            addDoctorBinding.address.setError("Address should not be empty");
            addDoctorBinding.address.requestFocus();
            return false;
        } else if (addDoctorBinding.phoneNumber.getText().toString().isEmpty()) {
            addDoctorBinding.phoneNumber.setError("Phone number should not be empty");
            addDoctorBinding.phoneNumber.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(addDoctorBinding.phoneNumber.getText().toString())) {
            addDoctorBinding.phoneNumber.setError("Invalid Phone Number");
            addDoctorBinding.phoneNumber.requestFocus();
            return false;
        }
        return true;
    }
}
