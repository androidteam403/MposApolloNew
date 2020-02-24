package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddDoctorBinding;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Inject;

public class AddDoctorActivity extends BaseActivity implements AddDoctorMvpView {
    AddDoctorPresenter presenter;
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
    public void onSubmitBtnClick() {
        if (validate()) {
            mPresenter.handleAddDoctorService();
        }
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void addDoctorSuccess(AddDoctorResModel addDoctorResModel) {
//        DoctorSearchResModel.DropdownValueBean doctorModel = new DoctorSearchResModel.DropdownValueBean();
//        doctorModel.setCode(addDoctorResModel.getDocRegID());
//        doctorModel.setDisplayText(addDoctorResModel.getDocName());

//        Intent returnIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("doctor_info", customDoctorItem);
//        returnIntent.putExtras(bundle);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();

        //Pass this to Search Activity
        Toast.makeText(this, addDoctorResModel.getReturnMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addDoctorFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getDoctorName() {
        return (Objects.requireNonNull(addDoctorBinding.doctorName.getText())).toString();
    }

    @Override
    public String getDoctorRegNo() {
        return (Objects.requireNonNull(addDoctorBinding.doctorRegNumber.getText())).toString();
    }

    @Override
    public String getSpeciality() {
        return (Objects.requireNonNull(addDoctorBinding.speciality.getText())).toString();
    }

    @Override
    public String getPlaceOfPractice() {
        return (Objects.requireNonNull(addDoctorBinding.placeOfPractice.getText()).toString());
    }

    @Override
    public String getAddress() {
        return (Objects.requireNonNull(addDoctorBinding.address.getText()).toString());
    }

    @Override
    public String getPhoneNo() {
        return (Objects.requireNonNull(addDoctorBinding.phoneNumber.getText())).toString();
    }


    private boolean validate() {
        String doctorRegNo = Objects.requireNonNull(addDoctorBinding.doctorRegNumber.getText()).toString();
        String dctrName = Objects.requireNonNull(addDoctorBinding.doctorName.getText()).toString();
        String spclty = Objects.requireNonNull(addDoctorBinding.speciality.getText()).toString();
        String placeofPractice = Objects.requireNonNull(addDoctorBinding.placeOfPractice.getText()).toString();
        String adrs = Objects.requireNonNull(addDoctorBinding.address.getText()).toString();
        String pNumber = Objects.requireNonNull(addDoctorBinding.phoneNumber.getText()).toString();
        if (doctorRegNo.isEmpty()) {
            addDoctorBinding.doctorRegNumber.setError("Doctor registration number should not be empty");
            addDoctorBinding.doctorRegNumber.requestFocus();
            return false;
        } else if (dctrName.isEmpty()) {
            addDoctorBinding.doctorName.setError("Doctor name should not be empty");
            addDoctorBinding.doctorName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(dctrName)) {
            addDoctorBinding.doctorName.setError("Invalid doctor name");
            addDoctorBinding.doctorName.requestFocus();
            return false;
        } else if (spclty.isEmpty()) {
            addDoctorBinding.speciality.setError("Speciality should not be empty");
            addDoctorBinding.speciality.requestFocus();
            return false;
        } else if (placeofPractice.isEmpty()) {
            addDoctorBinding.placeOfPractice.setError("Place of Practice should not be empty");
            addDoctorBinding.placeOfPractice.requestFocus();
            return false;
        } else if (adrs.isEmpty()) {
            addDoctorBinding.address.setError("Address should not be empty");
            addDoctorBinding.address.requestFocus();
            return false;
        } else if (pNumber.isEmpty()) {
            addDoctorBinding.phoneNumber.setError("Phone number should not be empty");
            addDoctorBinding.phoneNumber.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(pNumber)) {
            addDoctorBinding.phoneNumber.setError("Invalid Phone Number");
            addDoctorBinding.phoneNumber.requestFocus();
            return false;
        }
        return true;
    }
}
