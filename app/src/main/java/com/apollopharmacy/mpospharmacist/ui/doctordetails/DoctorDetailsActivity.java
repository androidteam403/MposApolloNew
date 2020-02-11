package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityDoctorDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialog;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class DoctorDetailsActivity extends BaseActivity implements DoctorDetailsMvpView {
    @Inject
    DoctorDetailsMvpPresenter<DoctorDetailsMvpView> mPresenter;
    ActivityDoctorDetailsBinding doctorDetailsBinding;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> allDoctorsArrayList;
    String strFont = null;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DoctorDetailsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctorDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DoctorDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        doctorDetailsBinding.setCallback(mPresenter);
        mPresenter.getDoctorsList();
        allDoctorsArrayList = new ArrayList<>();
        mPresenter.getAllDoctorsList();
    }

    @Override
    public void onAddDoctorClick() {
        startActivity(AddDoctorActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @SuppressLint("ResourceType")
    @Override
    public void getDoctorSearchList(DoctorSearchResModel model) {
        ArrayAdapter<DoctorSearchResModel.DropdownValueBean> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, model.get_DropdownValue());
        strFont = this.getString(R.font.roboto_regular);
        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
        doctorDetailsBinding.selectDoctor.setTypeface(tt);
        doctorDetailsBinding.selectDoctor.setAdapter(adapter);
        doctorDetailsBinding.selectDoctor.setSelection(0);
        doctorDetailsBinding.selectDoctor.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
            doctorDetailsBinding.selectDoctor.setError(null);
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void getSalesOriginList(SalesOriginResModel model) {
        ArrayAdapter<SalesOriginResModel.DropdownValueBean> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, model.getGetSalesOriginResult().get_DropdownValue());
        strFont = this.getString(R.font.roboto_regular);
        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
        doctorDetailsBinding.selectSalesOrigin.setTypeface(tt);
        doctorDetailsBinding.selectSalesOrigin.setAdapter(adapter);
        doctorDetailsBinding.selectSalesOrigin.setSelection(0);
        doctorDetailsBinding.selectSalesOrigin.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
            doctorDetailsBinding.selectSalesOrigin.setError(null);
        });
    }

    @Override
    public void getAllDoctorsSearchList(DoctorSearchResModel model) {
        allDoctorsArrayList.clear();
        allDoctorsArrayList.addAll(model.get_DropdownValue());
    }

    @Override
    public void onAllDoctorsClick() {
        if (allDoctorsArrayList.size() > 0) {
            AllDoctorsDialog dialog = AllDoctorsDialog.newInstance();
            dialog.setDoctorsArray(allDoctorsArrayList);
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
