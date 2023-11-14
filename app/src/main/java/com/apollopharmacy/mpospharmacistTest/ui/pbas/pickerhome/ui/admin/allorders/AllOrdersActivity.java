package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityAllOrdersBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders.adapter.OrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.notallocatedorders.NotAllocatedOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.orderdetails.OrderDetailsActivity;

public class AllOrdersActivity extends BaseActivity implements AllOrdersMvpView {
    private ActivityAllOrdersBinding allOrdersBinding;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_orders);
        setUp();
    }

    @Override
    protected void setUp() {
        ordersAdapter = new OrdersAdapter(this, this);
        allOrdersBinding.ordersRecycler.setAdapter(ordersAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        allOrdersBinding.ordersRecycler.setLayoutManager(layoutManager);
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
}