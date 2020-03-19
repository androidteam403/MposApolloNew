package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityOrderSummaryBinding;
import com.apollopharmacy.mpospharmacist.databinding.ViewMedicineInfoBinding;
import com.apollopharmacy.mpospharmacist.databinding.ViewPaymentInfoBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.utils.Singletone;

import java.util.ArrayList;

import javax.inject.Inject;

public class OrderSummaryActivity extends BaseActivity implements OrderSummaryMvpView {

    @Inject
    OrderSummaryMvpPresenter<OrderSummaryMvpView> mPresenter;
    ActivityOrderSummaryBinding orderSummaryBinding;
    private ArrayList<SaveRetailsTransactionRes.SalesLineEntity> medicineArrList = new ArrayList<>();
    private ArrayList<SaveRetailsTransactionRes.TenderLineEntity> paidAmountArr = new ArrayList<>();
    private CorporateModel.DropdownValueBean corporateEntity;

    public static Intent getStartIntent(Context context, SaveRetailsTransactionRes saveRetailsTransactionRes, CorporateModel.DropdownValueBean corporateEntity) {
        Intent intent = new Intent(context, OrderSummaryActivity.class);
        intent.putExtra("transaction_details", saveRetailsTransactionRes);
        intent.putExtra("corporate_info", corporateEntity);
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
        if (getIntent() != null) {
            corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
            if (corporateEntity != null) {
                orderSummaryBinding.setCorporate(corporateEntity);
            }
        }

        medicineArrList.addAll(transactionRes.getSalesLine());
        orderSummaryBinding.setItemCount(medicineArrList.size());
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

        orderSummaryBinding.placeNewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNewOrder();
            }
        });
    }

    @Override
    public void onBackPressed() {
        onClickNewOrder();
    }

    private void onClickNewOrder() {
        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().isPlaceNewOrder = true;
        Navigation.findNavController(orderSummaryBinding.placeNewOrderBtn).navigate(R.id.nav_billing);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackOrderPressed() {
        onClickNewOrder();
    }
}
