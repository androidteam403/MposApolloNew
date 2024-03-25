package com.apollopharmacy.mpospharmacistTest.ui.pbas.forwardtopacker;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityForwardToPackerBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickingonracks.PickingOnRacksActivity;

public class ForwardToPackerActivity extends BaseActivity {
    ActivityForwardToPackerBinding activityForwardToPackerBinding;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForwardToPackerBinding = DataBindingUtil.setContentView(this, R.layout.activity_forward_to_packer);
        setUp();
    }

    @Override
    protected void setUp() {
        itemAdapter = new ItemAdapter(this);
        activityForwardToPackerBinding.itemsRcv.setAdapter(itemAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activityForwardToPackerBinding.itemsRcv.setLayoutManager(layoutManager);

        activityForwardToPackerBinding.forwardToPacker.setOnClickListener(v -> {
            Intent intent = new Intent(this, PickingOnRacksActivity.class);
            startActivity(intent);
        });
    }
}