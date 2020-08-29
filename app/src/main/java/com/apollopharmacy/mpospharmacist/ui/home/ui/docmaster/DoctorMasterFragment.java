package com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentDocMasterBinding;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import java.util.Objects;

import javax.inject.Inject;


public class DoctorMasterFragment extends BaseFragment implements DoctorMasterMvpView {
    @Inject
    DoctorMasterMvpPresenter<DoctorMasterMvpView> mPresenter;

    private FragmentDocMasterBinding fragmentDocMasterBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentDocMasterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_master, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DoctorMasterFragment.this);
        return fragmentDocMasterBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        fragmentDocMasterBinding.setCallback(mPresenter);
    }


    @Override
    public void onSubmitBtnClick() {
        if (validate()) {
            mPresenter.handleAddDoctorService();
        }
    }

    @Override
    public void onClickBackPressed() {

    }

    @Override
    public void addDoctorSuccess(AddDoctorResModel addDoctorResModel) {
        Toast.makeText(getContext(), ""+addDoctorResModel.getReturnMessage(), Toast.LENGTH_SHORT).show();
        DoctorSearchResModel.DropdownValueBean doctorModel = new DoctorSearchResModel.DropdownValueBean();
        doctorModel.setCode(addDoctorResModel.getDocRegID());
        doctorModel.setDisplayText(addDoctorResModel.getDocName());
        fragmentDocMasterBinding.doctorRegNumber.setText("");
        fragmentDocMasterBinding.doctorName.setText("");
        fragmentDocMasterBinding.speciality.setText("");
        fragmentDocMasterBinding.placeOfPractice.setText("");
        fragmentDocMasterBinding.address.setText("");
        fragmentDocMasterBinding.phoneNumber.setText("");
//        Intent returnIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("doctor_info", doctorModel);
//        bundle.putSerializable("sales_info", salesEntity);
//        returnIntent.putExtras(bundle);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
    }

    @Override
    public void addDoctorFailed(String errMsg) {
        showMessage(errMsg);
    }

    @Override
    public String getDoctorName() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.doctorName.getText())).toString();
    }

    @Override
    public String getDoctorRegNo() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.doctorRegNumber.getText())).toString();
    }

    @Override
    public String getSpeciality() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.speciality.getText())).toString();
    }

    @Override
    public String getPlaceOfPractice() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.placeOfPractice.getText()).toString());
    }

    @Override
    public String getAddress() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.address.getText()).toString());
    }

    @Override
    public String getPhoneNo() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.phoneNumber.getText())).toString();
    }


    private boolean validate() {
        String doctorRegNo = Objects.requireNonNull(fragmentDocMasterBinding.doctorRegNumber.getText()).toString();
        String dctrName = Objects.requireNonNull(fragmentDocMasterBinding.doctorName.getText()).toString();
        String spclty = Objects.requireNonNull(fragmentDocMasterBinding.speciality.getText()).toString();
        String placeofPractice = Objects.requireNonNull(fragmentDocMasterBinding.placeOfPractice.getText()).toString();
        String adrs = Objects.requireNonNull(fragmentDocMasterBinding.address.getText()).toString();
        String pNumber = Objects.requireNonNull(fragmentDocMasterBinding.phoneNumber.getText()).toString();
        if (doctorRegNo.isEmpty()) {
            fragmentDocMasterBinding.doctorRegNumber.setError("Doctor registration number should not be empty");
            fragmentDocMasterBinding.doctorRegNumber.requestFocus();
            return false;
        } else if (dctrName.isEmpty()) {
            fragmentDocMasterBinding.doctorName.setError("Doctor name should not be empty");
            fragmentDocMasterBinding.doctorName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(dctrName)) {
            fragmentDocMasterBinding.doctorName.setError("Invalid doctor name");
            fragmentDocMasterBinding.doctorName.requestFocus();
            return false;
        } else if (spclty.isEmpty()) {
            fragmentDocMasterBinding.speciality.setError("Speciality should not be empty");
            fragmentDocMasterBinding.speciality.requestFocus();
            return false;
        } else if (placeofPractice.isEmpty()) {
            fragmentDocMasterBinding.placeOfPractice.setError("Place of Practice should not be empty");
            fragmentDocMasterBinding.placeOfPractice.requestFocus();
            return false;
        } else if (adrs.isEmpty()) {
            fragmentDocMasterBinding.address.setError("Address should not be empty");
            fragmentDocMasterBinding.address.requestFocus();
            return false;
        } else if (pNumber.isEmpty()) {
            fragmentDocMasterBinding.phoneNumber.setError("Phone number should not be empty");
            fragmentDocMasterBinding.phoneNumber.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(pNumber)) {
            fragmentDocMasterBinding.phoneNumber.setError("Invalid Phone Number");
            fragmentDocMasterBinding.phoneNumber.requestFocus();
            return false;
        }
        return true;
    }
}