package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.notallocatedorders;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityNotAllocatedOrdersBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.notallocatedorders.adapter.NotAllocatedOrdersAdapter;

public class NotAllocatedOrdersActivity extends BaseActivity {
    private ActivityNotAllocatedOrdersBinding activityNotAllocatedOrdersBinding;
    private NotAllocatedOrdersAdapter notAllocatedOrdersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotAllocatedOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_not_allocated_orders);
        setUp();
    }

    @Override
    protected void setUp() {
        notAllocatedOrdersAdapter = new NotAllocatedOrdersAdapter(this);
        activityNotAllocatedOrdersBinding.orderDetailsRecycler.setAdapter(notAllocatedOrdersAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activityNotAllocatedOrdersBinding.orderDetailsRecycler.setLayoutManager(layoutManager);
    }
}