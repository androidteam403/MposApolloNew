package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.OrderReturnActiivtyBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.OrdersFragment;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.adapter.MedicinesDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.model.MedicineDetailsModel;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter.OrderReturnAdapter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.OrderReturnModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class OrderReturnActivity extends BaseActivity implements OrederReturnMvpView {

    @Inject
    OrderReturnMvpPresenter<OrederReturnMvpView> mvpPresenter;
    OrderReturnActiivtyBinding orderReturnActiivtyBinding;
    private ArrayList<OrderReturnModel> orderReturnModelList = null;
    private OrderReturnAdapter orderReturnAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        orderReturnActiivtyBinding = DataBindingUtil.setContentView(this, R.layout.order_return_actiivty);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(OrderReturnActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        getOrderReturnInfo();
        if (orderReturnModelList.size() > 0) {
            orderReturnAdapter = new OrderReturnAdapter(this, orderReturnModelList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            orderReturnActiivtyBinding.orderreturnrecycle.setLayoutManager(mLayoutManager);
            // medicinesDetailsActivityBinding.medicineRecycle.setItemAnimator(new DefaultItemAnimator());
            //medicinesDetailsActivityBinding.medicineRecycle.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            // medicinesDetailsActivityBinding.medicineRecycle.setItemAnimator(new DefaultItemAnimator());
            orderReturnActiivtyBinding.orderreturnrecycle.setAdapter(orderReturnAdapter);
        }
    }

    private void getOrderReturnInfo() {
        orderReturnModelList = new ArrayList<>();
        OrderReturnModel orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);
        orderReturnModel = new OrderReturnModel("1.00","10","9.70",
                "Tax:1.04(12%)");
        orderReturnModelList.add(orderReturnModel);

    }
}
