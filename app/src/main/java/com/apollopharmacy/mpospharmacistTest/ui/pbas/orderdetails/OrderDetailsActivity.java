package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOrderDetailssPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails.adapter.OrderDetailsAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;


import java.io.Serializable;

import javax.inject.Inject;

public class OrderDetailsActivity extends BaseActivity implements OrderDetailsMvpView {

    @Inject
    OrderDetailsMvpPresenter<OrderDetailsMvpView> mPresenter;
    ActivityOrderDetailssPBinding activityOrderDetailssBinding;
    private RacksDataResponse.FullfillmentDetail racksDataResponse;
    OrderDetailsAdapter orderDetailsAdapter;

    public static Intent getStartActivity(Context context) {
        return new Intent(context, OrderDetailsActivity.class);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderDetailssBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detailss_p);
        getActivityComponent().inject(OrderDetailsActivity.this);
        mPresenter.onAttach(OrderDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityOrderDetailssBinding.setCallback(mPresenter);
        if (getIntent() != null) {
            racksDataResponse = (RacksDataResponse.FullfillmentDetail) getIntent().getSerializableExtra("fullfillmentDetails");
        }
        activityOrderDetailssBinding.headerFullfillmentId.setText("Fullfillment ID: " + racksDataResponse.getFullfillmentId());
        orderDetailsAdapter = new OrderDetailsAdapter(racksDataResponse.getProducts(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityOrderDetailssBinding.orderDetailsRecycler.setLayoutManager(mLayoutManager);
        activityOrderDetailssBinding.orderDetailsRecycler.setAdapter(orderDetailsAdapter);


    }

    @Override
    public void onSelectContinue() {
        Intent i = new Intent();
        i.putExtra("FullfillmentID", (Serializable) racksDataResponse);
        i.putExtra("isSelect", true);
        setResult(RESULT_OK, i);
        finish();
        overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
    }

    @Override
    public void onClickBackIcon() {
        onBackPressed();
    }
}