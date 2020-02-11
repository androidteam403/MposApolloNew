package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import java.io.Serializable;
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

    public static Intent getStartIntent(Context context, DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        Intent intent = new Intent(context, DoctorDetailsActivity.class);
        intent.putExtra("doctor_info", doctorEntity);
        intent.putExtra("sales_info", salesEntity);
        return intent;
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
        allDoctorsArrayList = new ArrayList<>();
        mPresenter.getDoctorsList();
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

        if (getIntent() != null) {
            DoctorSearchResModel.DropdownValueBean doctorEntity = (DoctorSearchResModel.DropdownValueBean) getIntent().getSerializableExtra("doctor_info");
            if (doctorEntity != null) {
                for (int i = 0; i < model.get_DropdownValue().size(); i++) {
                    if (model.get_DropdownValue().get(i).getCode().equals(doctorEntity.getCode())) {
                        doctorDetailsBinding.selectDoctor.setSelection(i);
                    }
                }
            }
        }
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
        if (getIntent() != null) {
            SalesOriginResModel.DropdownValueBean salesEntity = (SalesOriginResModel.DropdownValueBean) getIntent().getSerializableExtra("sales_info");
            if (salesEntity != null) {
                for (int i = 0; i < model.getGetSalesOriginResult().get_DropdownValue().size(); i++) {
                    if (model.getGetSalesOriginResult().get_DropdownValue().get(i).getCode().equals(salesEntity.getCode())) {
                        doctorDetailsBinding.selectSalesOrigin.setSelection(i);
                    }
                }
            }
        }
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
            dialog.setDoctorDetailsMvpView(this);
            dialog.setDoctorsArray(allDoctorsArrayList);
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    @Override
    public void onSubmitClick() {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("doctor_info", (Serializable) doctorDetailsBinding.selectDoctor.getSelectedItem());
        bundle.putSerializable("sales_info", (Serializable) doctorDetailsBinding.selectSalesOrigin.getSelectedItem());
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onSelectDoctor(DoctorSearchResModel.DropdownValueBean dropdownValueBean) {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
