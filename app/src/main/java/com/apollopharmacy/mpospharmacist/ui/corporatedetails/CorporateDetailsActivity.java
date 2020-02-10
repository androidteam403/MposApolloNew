package com.apollopharmacy.mpospharmacist.ui.corporatedetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCorporateDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.adapter.CorporateDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.adapter.MedicinesDetailAdapter;
import com.apollopharmacy.mpospharmacist.utils.SwipeController;
import com.apollopharmacy.mpospharmacist.utils.SwipeControllerActions;

import java.util.ArrayList;

import javax.inject.Inject;

public class CorporateDetailsActivity extends BaseActivity implements CorporateDetailsMvpView {
    @Inject
    CorporateDetailsMvpPresenter<CorporateDetailsMvpView> mPresenter;
    ActivityCorporateDetailsBinding corporateDetailsBinding;

    private CorporateDetailAdapter corporateDetailAdapter;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList;
    private ArrayList<CorporateModel.DropdownValueBean> tempCorporateList = new ArrayList<>();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CorporateDetailsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        corporateDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_corporate_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(CorporateDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        corporateDetailsBinding.setCallback(mPresenter);
        mPresenter.getCorporateList();
//        corporateDetailsBinding.corporateNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (count >= 3) {
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        corporateList = new ArrayList<>();
        tempCorporateList.clear();
        corporateList.addAll(corporateModel.get_DropdownValue());
        tempCorporateList.addAll(corporateModel.get_DropdownValue());
        corporateDetailAdapter = new CorporateDetailAdapter(this, corporateList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        corporateDetailsBinding.corporateRecyclerView.setLayoutManager(mLayoutManager);
        corporateDetailsBinding.corporateRecyclerView.setAdapter(corporateDetailAdapter);
    }

    @Override
    public void showNotFoundCorporate() {
        corporateDetailsBinding.corporateRecyclerView.setVisibility(View.GONE);
        corporateDetailsBinding.noCorporateFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
