package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import java.util.List;

import javax.inject.Inject;

public class PickupSummaryDetailsActivity extends BaseActivity implements PickUpSummaryDetailsMvpView {

    @Inject
    PickUpSummaryDetailsMvpPresenter<PickUpSummaryDetailsMvpView> mPresenter;
    private ActivityPickupSummaryDetailsPBinding pickupSummaryDetailsBinding;
    private TransactionHeaderResponse.OMSHeader selectedOmsHeader;


    public static Intent getStartIntent(Context context, TransactionHeaderResponse.OMSHeader selectedOmsHeader) {
        Intent intent = new Intent(context, PickupSummaryDetailsActivity.class);
        intent.putExtra(CommonUtils.SELECTED_ORDER, (Serializable) selectedOmsHeader);
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
            pickupSummaryDetailsBinding.customerType.setText(selectedOmsHeader.getCustomerType());
            pickupSummaryDetailsBinding.orderSource.setText(selectedOmsHeader.getOrderSource());
            pickupSummaryDetailsBinding.orderDate.setText(selectedOmsHeader.getCreatedDateTime());
            pickupSummaryDetailsBinding.deliveryDate.setText(selectedOmsHeader.getDeliveryDate());
            pickupSummaryDetailsBinding.shippingMethodType.setText(selectedOmsHeader.getShippingMethod());
            pickupSummaryDetailsBinding.stockStatus.setText(selectedOmsHeader.getStockStatus());
            pickupSummaryDetailsBinding.paymentSource.setText(selectedOmsHeader.getPaymentSource());
            pickupSummaryDetailsBinding.orderType.setText(selectedOmsHeader.getOrderType());
            pickupSummaryDetailsBinding.customerName.setText(selectedOmsHeader.getGetOMSTransactionResponse().getCustomerName());
            pickupSummaryDetailsBinding.vendorId.setText(selectedOmsHeader.getGetOMSTransactionResponse().getVendorId());
            pickupSummaryDetailsBinding.mobileNumber.setText(selectedOmsHeader.getGetOMSTransactionResponse().getMobileNO());
//       holder.orderBinding.orderbillvalue.setText(omsHeader.getGetOMSTransactionResponse().getRoundedAmount());
            pickupSummaryDetailsBinding.doctorName.setText(selectedOmsHeader.getGetOMSTransactionResponse().getDoctorName());
            pickupSummaryDetailsBinding.statecode.setText(selectedOmsHeader.getGetOMSTransactionResponse().getState());
            pickupSummaryDetailsBinding.city.setText(selectedOmsHeader.getGetOMSTransactionResponse().getBillingCity());
            pickupSummaryDetailsBinding.address.setText(selectedOmsHeader.getGetOMSTransactionResponse().getCustAddress());
            pickupSummaryDetailsBinding.pincode.setText(selectedOmsHeader.getGetOMSTransactionResponse().getPincode());

            pickupSummaryDetailsBinding.fullfilmentIdnumber.setText(selectedOmsHeader.getRefno());
            pickupSummaryDetailsBinding.totalItems.setText(String.valueOf(selectedOmsHeader.getGetOMSTransactionResponse().getSalesLine().size()));

            if(selectedOmsHeader.getItemStatus()!=null && selectedOmsHeader.getItemStatus().equalsIgnoreCase("NOT AVAILABLE")){
                pickupSummaryDetailsBinding.statusUpdateIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_not_available));
            }else if(selectedOmsHeader.getItemStatus()!=null && selectedOmsHeader.getItemStatus().equalsIgnoreCase("FULL")) {
                pickupSummaryDetailsBinding.statusUpdateIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_circle_tick));
                pickupSummaryDetailsBinding.statusUpdateIcon.setRotation(0);
            }  if(selectedOmsHeader.getItemStatus()!=null && selectedOmsHeader.getItemStatus().equalsIgnoreCase("PARTIAL")) {
                pickupSummaryDetailsBinding.statusUpdateIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            }
            PickUpSummaryDetailsProductsAdapter pickUpSummaryAdapter = new PickUpSummaryDetailsProductsAdapter(this, selectedOmsHeader.getGetOMSTransactionResponse().getSalesLine());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PickupSummaryDetailsActivity.this);
            pickupSummaryDetailsBinding.productListRecycler.setLayoutManager(mLayoutManager);
            pickupSummaryDetailsBinding.productListRecycler.setAdapter(pickUpSummaryAdapter);
        }
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }
}