package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityAllOrdersBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.AdminMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.AdminMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders.adapter.OrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.notallocatedorders.NotAllocatedOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.orderdetails.OrderDetailsActivity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class AllOrdersActivity extends BaseActivity implements AllOrdersMvpView {
    private ActivityAllOrdersBinding allOrdersBinding;
    private OrdersAdapter ordersAdapter;
    List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList;
    @Inject
    AllOrdersMvpPresenter<AllOrdersMvpView> mPresenter;

    public static Intent getStartIntent(Context context, List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList) {
        Intent intent = new Intent(context, AllOrdersActivity.class);
        intent.putExtra("omsHeaderList", (Serializable) omsHeaderList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_orders);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AllOrdersActivity.this);
        allOrdersBinding.setCallback(mPresenter);
        setUp();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setUp() {
        if (getIntent() != null) {
            omsHeaderList = (List<GetOMSTransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("omsHeaderList");
        }
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            allOrdersBinding.allOrdersCount.setText("("+omsHeaderList.size()+")");
            ordersAdapter = new OrdersAdapter(this, this, omsHeaderList);
            allOrdersBinding.ordersRecycler.setAdapter(ordersAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            allOrdersBinding.ordersRecycler.setLayoutManager(layoutManager);
        } else {
            allOrdersBinding.ordersRecycler.setVisibility(View.GONE);
            allOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
            allOrdersBinding.allOrdersCount.setText("(-)");
        }
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onClickOrder(int position) {
        if (position == 0) {
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("status", "inprogress");
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(this, NotAllocatedOrdersActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("status", "request");
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}