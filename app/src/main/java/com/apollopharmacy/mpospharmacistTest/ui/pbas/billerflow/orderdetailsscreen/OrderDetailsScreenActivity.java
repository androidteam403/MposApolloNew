package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOrderDetailsScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBillerSelectActionPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter.OrderDetailsScreenAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderDetailsScreenActivity extends BaseActivity implements OrderDetailsScreenMvpView {

    @Inject
    OrderDetailsScreenMvpPresenter<OrderDetailsScreenMvpView> mPresenter;
    ActivityOrderDetailsScreenPBinding activityOrderDetailsScreenBinding;
    private TransactionHeaderResponse.OMSHeader racksDataResponse;
    OrderDetailsScreenAdapter orderDetailsScreenAdapter;
    private TransactionIDResModel transactionIdItem = null;

    List<GetOMSTransactionResponse> omsHeader = new ArrayList<>();
    int pos;


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
            if (getIntent() != null) {
                racksDataResponse = (TransactionHeaderResponse.OMSHeader) getIntent().getSerializableExtra("fullfillmentDetails");
            }
        }

        mPresenter.fetchOMSCustomerInfo(racksDataResponse.getRefno());
mPresenter.getCorporateList();
mPresenter.getTransactionID();


        activityOrderDetailsScreenBinding.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        });
        activityOrderDetailsScreenBinding.fullfillmentId.setText(racksDataResponse.getRefno());
        if (racksDataResponse.getOverallOrderStatus().equals("0")){

            activityOrderDetailsScreenBinding.statusIcon.setVisibility(View.GONE);
        }
        else  if (racksDataResponse.getOverallOrderStatus().equals("1")){
            activityOrderDetailsScreenBinding.statusIcon.setRotation(0);

            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_circle_tick));

        }
        else if (racksDataResponse.getOverallOrderStatus().equals("2")){
            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.partialcirculargreeenorange));


        }
        else if (racksDataResponse.getOverallOrderStatus().equals("3")){
            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_not_available));


        }


//        orderDetailsScreenAdapter = new OrderDetailsScreenAdapter(this, racksDataResponse.getProducts());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        activityOrderDetailsScreenBinding.orderDetailsRecycler.setLayoutManager(mLayoutManager);
//        activityOrderDetailsScreenBinding.orderDetailsRecycler.setAdapter(orderDetailsScreenAdapter);


    }


    @Override
    public void onMinusCustomerDetails() {
//        activityOrderDetailsScreenBinding.customerDetailsPlusSymbol.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.customerDetailsMinusSymbol.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.customerDetailsExapansion.setVisibility(View.GONE);
    }

    @Override
    public void onPlusCustomerDetails() {
//        activityOrderDetailsScreenBinding.customerDetailsPlusSymbol.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.customerDetailsMinusSymbol.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.customerDetailsExapansion.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTransactionID(TransactionIDResModel model) {
        for (int i=0;i< omsHeader.size();i++ ){
            if (racksDataResponse.getRefno().equals(omsHeader.get(i).getRefno()) && omsHeader.get(i).getCorporateList()!=null){
                omsHeader.get(i).getTransactionIDResModelList().add(model);
            }
        }



    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        for (int i=0;i< omsHeader.size();i++ ){
           if (racksDataResponse.getRefno().equals(omsHeader.get(i).getRefno()) && omsHeader.get(i).getCorporateList()!=null){
               omsHeader.get(i).setCorporateList((List<CorporateModel>) corporateModel);
           }
        }


    }


    @Override
    public void onminusOrderDetails() {
//        activityOrderDetailsScreenBinding.orderDetailsPlusSymbol.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.orderDetailsMinusSymbol.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.orderDetailsRecycler.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.quantityStatus.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessGetOMSTransaction(List<GetOMSTransactionResponse> getOMSTransactionResponses) {




        for (int i = 0; i < getOMSTransactionResponses.size(); i++) {
            if (getOMSTransactionResponses != null ) {
//                && getOMSTransactionResponses.get(i).getPickPackReservation() != null
                if (racksDataResponse.getRefno().equalsIgnoreCase(getOMSTransactionResponses.get(i).getRefno())) {
                    activityOrderDetailsScreenBinding.fullfilmentIdnumber.setText(getOMSTransactionResponses.get(i).getRefno());
                    activityOrderDetailsScreenBinding.totalItems.setText(String.valueOf(getOMSTransactionResponses.get(i).getSalesLine().size()));
                    activityOrderDetailsScreenBinding.customerType.setText(getOMSTransactionResponses.get(i).getCustomerType());
                    activityOrderDetailsScreenBinding.orderSource.setText(getOMSTransactionResponses.get(i).getOrderSource());
                    activityOrderDetailsScreenBinding.orderDate.setText(getOMSTransactionResponses.get(i).getCreatedDateTime());
                    activityOrderDetailsScreenBinding.deliveryDate.setText(getOMSTransactionResponses.get(i).getDeliveryDate());
                    activityOrderDetailsScreenBinding.shippingMethodType.setText(getOMSTransactionResponses.get(i).getShippingMethod());
                    activityOrderDetailsScreenBinding.stockStatus.setText(getOMSTransactionResponses.get(i).getStockStatus());
                    activityOrderDetailsScreenBinding.paymentSource.setText(getOMSTransactionResponses.get(i).getPaymentSource());
                    activityOrderDetailsScreenBinding.orderType.setText(getOMSTransactionResponses.get(i).getOrderType());
                    activityOrderDetailsScreenBinding.customerName.setText(getOMSTransactionResponses.get(i).getCustomerName());
                    activityOrderDetailsScreenBinding.vendorId.setText(getOMSTransactionResponses.get(i).getVendorId());
                    activityOrderDetailsScreenBinding.mobileNumber.setText(getOMSTransactionResponses.get(i).getMobileNO());
                    activityOrderDetailsScreenBinding.doctorName.setText(getOMSTransactionResponses.get(i).getDoctorName());
                    activityOrderDetailsScreenBinding.statecode.setText(getOMSTransactionResponses.get(i).getState());
                    activityOrderDetailsScreenBinding.city.setText(getOMSTransactionResponses.get(i).getBillingCity());
                    activityOrderDetailsScreenBinding.address.setText(getOMSTransactionResponses.get(i).getCustAddress());
                    activityOrderDetailsScreenBinding.pincode.setText(getOMSTransactionResponses.get(i).getPincode());
omsHeader.add(getOMSTransactionResponses.get(i));

                    orderDetailsScreenAdapter = new OrderDetailsScreenAdapter(this, omsHeader.get(i).getSalesLine(), omsHeader.get(i).getPickPackReservation());
                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    activityOrderDetailsScreenBinding.productListRecycler.setLayoutManager(mLayoutManager1);
                    activityOrderDetailsScreenBinding.productListRecycler.setItemAnimator(new DefaultItemAnimator());
                    activityOrderDetailsScreenBinding.productListRecycler.setAdapter(orderDetailsScreenAdapter);
                }
            }
            else {
                Toast.makeText(this, "Pick Pack Reservation is null", Toast.LENGTH_SHORT).show();
                racksDataResponse.setGetOMSTransactionResponse(getOMSTransactionResponses.get(0));
                mPresenter.mposPickPackOrderReservationApiCall(4, racksDataResponse);
            }
        }

    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 3) {
            if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
                if (racksDataResponse != null) {
                    mPresenter.fetchOMSCustomerInfo(racksDataResponse.getRefno());
                }
            }
        } else if (requestType == 4) {
            finish();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        } else if (requestType == 6) {
            finish();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        }
    }


    @Override
    public void onplusOrderDetails() {
//        activityOrderDetailsScreenBinding.orderDetailsPlusSymbol.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.orderDetailsMinusSymbol.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.orderDetailsRecycler.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.quantityStatus.setVisibility(View.VISIBLE);
//    }
    }

    @Override
    public void onminusVendorDetails() {
//        activityOrderDetailsScreenBinding.vendorDetailsPlusSymbol.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.vendorDetailsMinusSymbol.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.vendorDetailsExpansion.setVisibility(View.GONE);
    }

    @Override
    public void onPlusVendorDetails() {
//        activityOrderDetailsScreenBinding.vendorDetailsPlusSymbol.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.vendorDetailsMinusSymbol.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.vendorDetailsExpansion.setVisibility(View.VISIBLE);
    }

    DialogBillerSelectActionPBinding selectActionLayoutBinding;


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