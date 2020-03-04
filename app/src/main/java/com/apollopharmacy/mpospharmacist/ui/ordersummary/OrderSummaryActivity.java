package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityOrderSummaryBinding;
import com.apollopharmacy.mpospharmacist.databinding.ViewMedicineInfoBinding;
import com.apollopharmacy.mpospharmacist.databinding.ViewPaymentInfoBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.model.PaidAmountBean;
import com.apollopharmacy.mpospharmacist.ui.pay.model.SaveRetailsTransactionRes;

import java.util.ArrayList;

import javax.inject.Inject;

public class OrderSummaryActivity extends BaseActivity implements OrderSummaryMvpView {

    @Inject
    OrderSummaryMvpPresenter<OrderSummaryMvpView> mPresenter;
    ActivityOrderSummaryBinding orderSummaryBinding;
    private ArrayList<SaveRetailsTransactionRes.SalesLineEntity> medicineArrList = new ArrayList<>();
    private ArrayList<SaveRetailsTransactionRes.TenderLineEntity> paidAmountArr = new ArrayList<>();

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
        if (transactionRes != null) {
            orderSummaryBinding.setOrderDetails(transactionRes);
        }
        medicineArrList.addAll(transactionRes.getSalesLine());
        for (int i = 0; i < medicineArrList.size(); i++) {
            ViewMedicineInfoBinding childView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_medicine_info, orderSummaryBinding.medicineListLayout, false);
            childView.setMedicinebean(medicineArrList.get(i));
            orderSummaryBinding.medicineListLayout.addView(childView.getRoot());
        }
        paidAmountArr.addAll(transactionRes.getTenderLine());
        for (int i = 0; i < paidAmountArr.size(); i++) {
            ViewPaymentInfoBinding childView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_payment_info, orderSummaryBinding.paidAmountLayout, false);
            childView.setPaidbean(paidAmountArr.get(i));
            orderSummaryBinding.paidAmountLayout.addView(childView.getRoot());
        }
    }
}
