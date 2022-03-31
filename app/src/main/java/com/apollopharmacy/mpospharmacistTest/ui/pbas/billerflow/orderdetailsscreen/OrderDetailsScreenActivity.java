package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOrderDetailsScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBillerSelectActionPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter.OrderDetailsScreenAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import javax.inject.Inject;

public class OrderDetailsScreenActivity extends BaseActivity implements OrderDetailsScreenMvpView {

    @Inject
    OrderDetailsScreenMvpPresenter<OrderDetailsScreenMvpView> mPresenter;
    ActivityOrderDetailsScreenPBinding activityOrderDetailsScreenBinding;
    private RacksDataResponse.FullfillmentDetail racksDataResponse;
    OrderDetailsScreenAdapter orderDetailsScreenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_details_screen);
        activityOrderDetailsScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_details_screen_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OrderDetailsScreenActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityOrderDetailsScreenBinding.setCallback(mPresenter);

        if (getIntent() != null) {
            racksDataResponse = (RacksDataResponse.FullfillmentDetail) getIntent().getSerializableExtra("fullfillmentDetails");
        }

        activityOrderDetailsScreenBinding.fullfillmentId.setText(racksDataResponse.getFullfillmentId());
        activityOrderDetailsScreenBinding.boxId.setText(racksDataResponse.getBoxId());


        orderDetailsScreenAdapter = new OrderDetailsScreenAdapter(this, racksDataResponse.getProducts());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityOrderDetailsScreenBinding.orderDetailsRecycler.setLayoutManager(mLayoutManager);
        activityOrderDetailsScreenBinding.orderDetailsRecycler.setAdapter(orderDetailsScreenAdapter);


    }


    @Override
    public void onMinusCustomerDetails() {
        activityOrderDetailsScreenBinding.customerDetailsPlusSymbol.setVisibility(View.VISIBLE);
        activityOrderDetailsScreenBinding.customerDetailsMinusSymbol.setVisibility(View.GONE);
        activityOrderDetailsScreenBinding.customerDetailsExapansion.setVisibility(View.GONE);
    }

    @Override
    public void onPlusCustomerDetails() {
        activityOrderDetailsScreenBinding.customerDetailsPlusSymbol.setVisibility(View.GONE);
        activityOrderDetailsScreenBinding.customerDetailsMinusSymbol.setVisibility(View.VISIBLE);
        activityOrderDetailsScreenBinding.customerDetailsExapansion.setVisibility(View.VISIBLE);
    }

    @Override
    public void onminusOrderDetails() {
        activityOrderDetailsScreenBinding.orderDetailsPlusSymbol.setVisibility(View.VISIBLE);
        activityOrderDetailsScreenBinding.orderDetailsMinusSymbol.setVisibility(View.GONE);
        activityOrderDetailsScreenBinding.orderDetailsRecycler.setVisibility(View.GONE);
        activityOrderDetailsScreenBinding.quantityStatus.setVisibility(View.GONE);
    }

    @Override
    public void onplusOrderDetails() {
        activityOrderDetailsScreenBinding.orderDetailsPlusSymbol.setVisibility(View.GONE);
        activityOrderDetailsScreenBinding.orderDetailsMinusSymbol.setVisibility(View.VISIBLE);
        activityOrderDetailsScreenBinding.orderDetailsRecycler.setVisibility(View.VISIBLE);
        activityOrderDetailsScreenBinding.quantityStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void onminusVendorDetails() {
        activityOrderDetailsScreenBinding.vendorDetailsPlusSymbol.setVisibility(View.VISIBLE);
        activityOrderDetailsScreenBinding.vendorDetailsMinusSymbol.setVisibility(View.GONE);
        activityOrderDetailsScreenBinding.vendorDetailsExpansion.setVisibility(View.GONE);
    }

    @Override
    public void onPlusVendorDetails() {
        activityOrderDetailsScreenBinding.vendorDetailsPlusSymbol.setVisibility(View.GONE);
        activityOrderDetailsScreenBinding.vendorDetailsMinusSymbol.setVisibility(View.VISIBLE);
        activityOrderDetailsScreenBinding.vendorDetailsExpansion.setVisibility(View.VISIBLE);
    }

    DialogBillerSelectActionPBinding selectActionLayoutBinding;
    int i =1;

    @Override
    public void onActionsContinue() {

        Dialog dialog = new Dialog(this);
        selectActionLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.dialog_biller_select_action_p, null, false);
        dialog.setContentView(selectActionLayoutBinding.getRoot());
        selectActionLayoutBinding.setCallback(mPresenter);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        dialog.show();
        selectActionLayoutBinding.crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onGenerateBill() {
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrintLabel() {
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrintShippingLabel() {
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSendBacktoPackerLabel() {
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.GONE);
    }
}