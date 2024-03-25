package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.orderdetails;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOrderDetailsBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogActionsBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.orderdetails.adapter.OrderDetailsAdapter;

public class OrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsBinding activityOrderDetailsBinding;
    private OrderDetailsAdapter orderDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        setUp();
    }

    @Override
    protected void setUp() {
        String status = "";
        if (getIntent() != null) {
            status = getIntent().getStringExtra("status");
        }
        if (status.equalsIgnoreCase("request")) {
            activityOrderDetailsBinding.status.setText("Request");
            activityOrderDetailsBinding.status.setTextColor(Color.parseColor("#e39c45"));
            activityOrderDetailsBinding.leftStrip.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
            activityOrderDetailsBinding.actionButton.setVisibility(View.GONE);
            activityOrderDetailsBinding.warning.setVisibility(View.VISIBLE);
        } else {
            activityOrderDetailsBinding.status.setText("Inprogress");
            activityOrderDetailsBinding.status.setTextColor(Color.parseColor("#8bbdae"));
            activityOrderDetailsBinding.leftStrip.setBackgroundColor(Color.parseColor("#70ba9f"));
            activityOrderDetailsBinding.actionButton.setVisibility(View.VISIBLE);
            activityOrderDetailsBinding.warning.setVisibility(View.GONE);
        }
        orderDetailsAdapter = new OrderDetailsAdapter(this, status);
        activityOrderDetailsBinding.orderDetailsRecycler.setAdapter(orderDetailsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activityOrderDetailsBinding.orderDetailsRecycler.setLayoutManager(layoutManager);

        activityOrderDetailsBinding.actionButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            DialogActionsBinding dialogActionsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_actions, null, false);
            dialog.setContentView(dialogActionsBinding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogActionsBinding.closeButton.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            dialogActionsBinding.updateButton.setOnClickListener(v2 -> {
                activityOrderDetailsBinding.actionButton.setVisibility(View.GONE);
                activityOrderDetailsBinding.actionUpdatedSuccessfully.setVisibility(View.VISIBLE);

                dialog.dismiss();
            });
            dialog.setCancelable(false);
            dialog.show();
        });
    }
}