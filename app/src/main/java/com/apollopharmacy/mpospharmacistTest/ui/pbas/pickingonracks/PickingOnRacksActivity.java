package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickingonracks;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickingOnRacksBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;

public class PickingOnRacksActivity extends BaseActivity {
    ActivityPickingOnRacksBinding activityPickingOnRacksBinding;
    private RacksAdapter racksAdapter;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickingOnRacksBinding = DataBindingUtil.setContentView(this, R.layout.activity_picking_on_racks);
        setUp();
    }

    @Override
    protected void setUp() {
        racksAdapter = new RacksAdapter(this);
        activityPickingOnRacksBinding.racksRcv.setAdapter(racksAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        activityPickingOnRacksBinding.racksRcv.setLayoutManager(layoutManager);

        productsAdapter = new ProductsAdapter(this);
        activityPickingOnRacksBinding.productsRcv.setAdapter(productsAdapter);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        activityPickingOnRacksBinding.productsRcv.setLayoutManager(layoutManager1);
    }
}