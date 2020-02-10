package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityBatchInfoBinding;
import com.apollopharmacy.mpospharmacist.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.adapter.BatchInfoAdapter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.lstener.BatchAdapterListener;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.BatchInfoAdapterPojo;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.MedicinesDetailsActivity;

import java.util.ArrayList;

import javax.inject.Inject;

public class BatchInfoActivity extends BaseActivity implements BatchInfoMvpView, BatchAdapterListener {

    @Inject
    BatchInfoMvpPresenter<BatchInfoMvpView> mPresenter;
    ActivityBatchInfoBinding batchInfoBinding;

    BatchInfoAdapter batchInfoAdapter;
    BatchInfoListAdapterBinding batchInfoListAdapterBinding;
    private ArrayList<BatchInfoAdapterPojo> arrBatchList = null;
    private int count = 0;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BatchInfoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        batchInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_batch_info);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BatchInfoActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        batchInfoBinding.setCallback(mPresenter);
        getBatchInfo();
        batchInfoAdapter = new BatchInfoAdapter(this, arrBatchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        batchInfoBinding.batchInfoRecycler.setLayoutManager(mLayoutManager);
        batchInfoBinding.batchInfoRecycler.setItemAnimator(new DefaultItemAnimator());
        batchInfoBinding.batchInfoRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        batchInfoBinding.batchInfoRecycler.setItemAnimator(new DefaultItemAnimator());
        batchInfoBinding.batchInfoRecycler.setAdapter(batchInfoAdapter);
    }

    @Override
    public void onIncrementClick() {
        count++;
        String string = Integer.toString(count);
        batchInfoBinding.inputQty.setText(string);
    }

    @Override
    public void onDecrementClick() {
        if (count == 1) {
            String string = Integer.toString(count);
            batchInfoBinding.inputQty.setText(string);
        } else {
            count--;
            String string = Integer.toString(count);
            batchInfoBinding.inputQty.setText(string);
        }
    }

    @Override
    public void onNavigateNextActivity() {
        startActivity(MedicinesDetailsActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onItemClick(BatchInfoAdapterPojo batchInfoAdapterPojo) {

    }

    private void getBatchInfo() {
        arrBatchList = new ArrayList<>();
        BatchInfoAdapterPojo batchInfoAdapterPojo = new BatchInfoAdapterPojo("Docs0014", "12/12/2020", "45.50", "4.50",
                "50.00", "0");
        arrBatchList.add(batchInfoAdapterPojo);
        batchInfoAdapterPojo = new BatchInfoAdapterPojo("Docs0014", "12/12/2020", "45.50", "4.50",
                "50.00", "0");
        arrBatchList.add(batchInfoAdapterPojo);
        batchInfoAdapterPojo = new BatchInfoAdapterPojo("Docs0014", "12/12/2020", "45.50", "4.50",
                "50.00", "0");
        arrBatchList.add(batchInfoAdapterPojo);
        batchInfoAdapterPojo = new BatchInfoAdapterPojo("Docs0014", "12/12/2020", "45.50", "4.50",
                "50.00", "0");
        arrBatchList.add(batchInfoAdapterPojo);
    }
}
