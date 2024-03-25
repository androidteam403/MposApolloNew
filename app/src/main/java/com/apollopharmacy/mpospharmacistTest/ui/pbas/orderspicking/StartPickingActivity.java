package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderspicking;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityStartPickingBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.forwardtopacker.ForwardToPackerActivity;

public class StartPickingActivity extends BaseActivity {
    ActivityStartPickingBinding activityStartPickingBinding;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStartPickingBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_picking);
        setUp();
    }

    @Override
    protected void setUp() {
        ordersAdapter = new OrdersAdapter(this);
        activityStartPickingBinding.ordersRcv.setAdapter(ordersAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activityStartPickingBinding.ordersRcv.setLayoutManager(layoutManager);

        activityStartPickingBinding.continueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForwardToPackerActivity.class);
            startActivity(intent);
        });
    }
}