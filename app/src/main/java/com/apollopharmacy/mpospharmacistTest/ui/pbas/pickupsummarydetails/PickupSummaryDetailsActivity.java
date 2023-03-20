package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickupSummaryDetailsPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.adapter.PickUpSummaryDetailsProductsAdapter;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;

import java.io.Serializable;

import javax.inject.Inject;

public class PickupSummaryDetailsActivity extends BaseActivity implements PickUpSummaryDetailsMvpView {

    @Inject
    PickUpSummaryDetailsMvpPresenter<PickUpSummaryDetailsMvpView> mPresenter;
    private ActivityPickupSummaryDetailsPBinding pickupSummaryDetailsBinding;
    private TransactionHeaderResponse.OMSHeader selectedOmsHeader;
    String time, stopWatch;
    long startTime;
    long countUp;
    Chronometer stopWatchs;


    public static Intent getStartActivity(Context context, TransactionHeaderResponse.OMSHeader selectedOmsHeader, String time, String stopWatch, String boxId) {
        Intent intent = new Intent(context, PickupSummaryDetailsActivity.class);
        intent.putExtra(CommonUtils.SELECTED_ORDER, (Serializable) selectedOmsHeader);
        intent.putExtra("box_id", boxId);
        intent.putExtra("time", time);
        intent.putExtra("stopWatch", stopWatch);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickupSummaryDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_pickup_summary_details_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PickupSummaryDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        pickupSummaryDetailsBinding.setCallback(mPresenter);
        if (getIntent() != null) {

            selectedOmsHeader = (TransactionHeaderResponse.OMSHeader) getIntent().getSerializableExtra(CommonUtils.SELECTED_ORDER);
            String boxId = (String) getIntent().getSerializableExtra("box_id");
            if (boxId != null && !boxId.isEmpty()) {
//                if (boxId.length() > 5)
//                    pickupSummaryDetailsBinding.boxId.setText(boxId.substring(boxId.length() - 5));
//                else
                pickupSummaryDetailsBinding.boxId.setText(boxId);
            } else {
                pickupSummaryDetailsBinding.boxId.setText("-");
            }
            time = (String) getIntent().getStringExtra("time");
            stopWatch = (String) getIntent().getStringExtra("stopWatch");


            pickupSummaryDetailsBinding.starttime.setText(time);
//            pickupSummaryDetailsBinding.duration.setText(stopWatch);
            stopWatchs = (Chronometer) findViewById(R.id.duration);


        }
        String[] sw = stopWatch.split(":");
        startTime = SystemClock.elapsedRealtime();
        stopWatchs.setBase(SystemClock.elapsedRealtime() - (Integer.parseInt(sw[0]) * 60000 + Integer.parseInt(sw[1]) * 1000));

        stopWatchs.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;

                String asText = (countUp / 60) + ":" + (countUp % 60);
//                pickupProcessBinding.timer.setText(asText);
//                 asText1 = stopWatch.getFormat();
//                int h = (int)(countUp /3600000);
//                int m = (int)(countUp - h*3600000)/60000;
//                int s= (int)(countUp - h*3600000- m*60000);
            }
        });
        stopWatchs.start();


        pickupSummaryDetailsBinding.customerType.setText(selectedOmsHeader.getCustomerType());
        pickupSummaryDetailsBinding.orderSourceId.setText(selectedOmsHeader.getVendorId());
        pickupSummaryDetailsBinding.orderDate.setText(selectedOmsHeader.getCreatedDateTime());
        pickupSummaryDetailsBinding.deliveryDate.setText(selectedOmsHeader.getDeliveryDate());
        pickupSummaryDetailsBinding.shippingMethodType.setText(selectedOmsHeader.getShippingMethod());
        pickupSummaryDetailsBinding.sourceOrderName.setText(selectedOmsHeader.getOrderSource());
        pickupSummaryDetailsBinding.paymentSource.setText(selectedOmsHeader.getPaymentSource());
        pickupSummaryDetailsBinding.orderType.setText(selectedOmsHeader.getOrderType());
        pickupSummaryDetailsBinding.customerName.setText(selectedOmsHeader.getGetOMSTransactionResponse().getCustomerName());
        pickupSummaryDetailsBinding.vendorId.setText(selectedOmsHeader.getGetOMSTransactionResponse().getVendorId());
        pickupSummaryDetailsBinding.mobileNumber.setText(selectedOmsHeader.getGetOMSTransactionResponse().getMobileNO());
        pickupSummaryDetailsBinding.orderbillvalue.setText(String.valueOf(selectedOmsHeader.getGetOMSTransactionResponse().getNetAmount()));
        pickupSummaryDetailsBinding.doctorName.setText(selectedOmsHeader.getGetOMSTransactionResponse().getDoctorName());
        pickupSummaryDetailsBinding.statecode.setText(selectedOmsHeader.getGetOMSTransactionResponse().getState());
        pickupSummaryDetailsBinding.city.setText(selectedOmsHeader.getGetOMSTransactionResponse().getBillingCity());
        pickupSummaryDetailsBinding.address.setText(selectedOmsHeader.getGetOMSTransactionResponse().getCustAddress());
        pickupSummaryDetailsBinding.pincode.setText(selectedOmsHeader.getGetOMSTransactionResponse().getPincode());

        pickupSummaryDetailsBinding.shipmentTat.setText(selectedOmsHeader.getShipmentTat());
        pickupSummaryDetailsBinding.billdateTat.setText(selectedOmsHeader.getBillingTat());

        pickupSummaryDetailsBinding.fullfilmentIdnumber.setText(selectedOmsHeader.getRefno());
        pickupSummaryDetailsBinding.totalItems.setText(String.valueOf(selectedOmsHeader.getGetOMSTransactionResponse().getSalesLine().size()));

        if (selectedOmsHeader.getItemStatus() != null && selectedOmsHeader.getItemStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            pickupSummaryDetailsBinding.statusUpdateIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_not_available));
        } else if (selectedOmsHeader.getItemStatus() != null && selectedOmsHeader.getItemStatus().equalsIgnoreCase("FULL")) {
            pickupSummaryDetailsBinding.statusUpdateIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_circle_tick));
            pickupSummaryDetailsBinding.statusUpdateIcon.setRotation(0);
        }
        if (selectedOmsHeader.getItemStatus() != null && selectedOmsHeader.getItemStatus().equalsIgnoreCase("PARTIAL")) {
            pickupSummaryDetailsBinding.statusUpdateIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.partialcirculargreeenorange));
        }
        PickUpSummaryDetailsProductsAdapter pickUpSummaryAdapter = new PickUpSummaryDetailsProductsAdapter(this, selectedOmsHeader.getGetOMSTransactionResponse().getSalesLine());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PickupSummaryDetailsActivity.this);
        pickupSummaryDetailsBinding.productListRecycler.setLayoutManager(mLayoutManager);
        pickupSummaryDetailsBinding.productListRecycler.setAdapter(pickUpSummaryAdapter);
    }


    @Override
    public void onClickBack() {
        onBackPressed();
    }
}