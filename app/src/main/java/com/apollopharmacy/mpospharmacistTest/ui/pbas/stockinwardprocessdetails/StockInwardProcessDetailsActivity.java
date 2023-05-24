package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityStockInwardProcessDetailsBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.adapter.InwardProcessLineItemsAdapter;

import javax.inject.Inject;

public class StockInwardProcessDetailsActivity extends BaseActivity implements StockInwardProcessDetailsMvpView {

    @Inject
    StockInwardProcessDetailsMvpPresenter<StockInwardProcessDetailsMvpView> mPresenter;
    private ActivityStockInwardProcessDetailsBinding stockInwardProcessDetailsBinding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, StockInwardProcessDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockInwardProcessDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_inward_process_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(StockInwardProcessDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        stockInwardProcessDetailsBinding.setCallback(mPresenter);
        setAdapter();
    }

    private void setAdapter() {
        InwardProcessLineItemsAdapter inwardProcessLineItemsAdapter = new InwardProcessLineItemsAdapter();
        LinearLayoutManager mLinearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setLayoutManager(mLinearManager);
        stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setAdapter(inwardProcessLineItemsAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }
}
