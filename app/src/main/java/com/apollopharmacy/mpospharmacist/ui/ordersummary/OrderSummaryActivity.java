package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityOrderSummaryBinding;
import com.apollopharmacy.mpospharmacist.databinding.ActivityPayBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.pay.model.SaveRetailsTransactionRes;

import javax.inject.Inject;

public class OrderSummaryActivity extends BaseActivity implements OrderSummaryMvpView {

    @Inject
    OrderSummaryMvpPresenter<OrderSummaryMvpView> mPresenter;
    ActivityOrderSummaryBinding orderSummaryBinding;

    public static Intent getStartIntent(Context context, SaveRetailsTransactionRes saveRetailsTransactionRes) {
        Intent intent = new Intent(context, OrderSummaryActivity.class);
        intent.putExtra("transaction_details", saveRetailsTransactionRes);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderSummaryBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_summary);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OrderSummaryActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        SaveRetailsTransactionRes transactionRes = (SaveRetailsTransactionRes) getIntent().getSerializableExtra("transaction_details");
    }
}
