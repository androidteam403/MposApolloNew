package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickupSummaryDetailsPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.adapter.PickUpSummaryDetailsProductsAdapter;

import javax.inject.Inject;

public class PickupSummaryDetailsActivity extends BaseActivity implements PickUpSummaryDetailsMvpView {

    @Inject
    PickUpSummaryDetailsMvpPresenter<PickUpSummaryDetailsMvpView> mPresenter;
    private ActivityPickupSummaryDetailsPBinding pickupSummaryDetailsBinding;

    public static Intent getStartIntent(Context context, RacksDataResponse.FullfillmentDetail fullfillmentDetail) {
        Intent intent = new Intent(context, PickupSummaryDetailsActivity.class);
        intent.putExtra("FULLFILLMENT_DETAILS", fullfillmentDetail);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickupSummaryDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_pickup_summary_details_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PickupSummaryDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        pickupSummaryDetailsBinding.setCallback(mPresenter);
        if (getIntent() != null) {
            RacksDataResponse.FullfillmentDetail racksDataResponse = (RacksDataResponse.FullfillmentDetail) getIntent().getSerializableExtra("FULLFILLMENT_DETAILS");

            pickupSummaryDetailsBinding.fullfilmentIdnumber.setText(racksDataResponse.getFullfillmentId());
            pickupSummaryDetailsBinding.totalItems.setText(racksDataResponse.getTotalItems());

            PickUpSummaryDetailsProductsAdapter pickUpSummaryAdapter = new PickUpSummaryDetailsProductsAdapter(this, racksDataResponse.getProducts());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PickupSummaryDetailsActivity.this);
            pickupSummaryDetailsBinding.productListRecycler.setLayoutManager(mLayoutManager);
            pickupSummaryDetailsBinding.productListRecycler.setAdapter(pickUpSummaryAdapter);

        }
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }
}