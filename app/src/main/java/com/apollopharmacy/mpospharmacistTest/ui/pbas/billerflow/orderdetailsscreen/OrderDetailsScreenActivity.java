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
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.ReservedSalesLine;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter.OrderDetailsScreenAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
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
    ArrayList<SalesLineEntity> salesentity = new ArrayList<>();
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
                mPresenter.fetchOMSCustomerInfo(racksDataResponse.getRefno());
            }
        }
        mPresenter.getTransactionID();


        activityOrderDetailsScreenBinding.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        });
        activityOrderDetailsScreenBinding.fullfillmentId.setText(racksDataResponse.getRefno());
        if (racksDataResponse.getOverallOrderStatus().equals("0")) {

            activityOrderDetailsScreenBinding.statusIcon.setVisibility(View.GONE);
        } else if (racksDataResponse.getOverallOrderStatus().equals("1")) {
            activityOrderDetailsScreenBinding.statusIcon.setRotation(0);

            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_circle_tick));

        } else if (racksDataResponse.getOverallOrderStatus().equals("2")) {
            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.partialcirculargreeenorange));


        } else if (racksDataResponse.getOverallOrderStatus().equals("3")) {
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
        for (int i = 0; i < omsHeader.size(); i++) {
            if (racksDataResponse.getRefno().equals(omsHeader.get(i).getRefno()) && omsHeader.get(i).getCorporateList() != null) {
                omsHeader.get(i).getTransactionIDResModelList().add(model);
            }
        }


    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        for (int i = 0; i < omsHeader.size(); i++) {
            if (racksDataResponse.getRefno().equals(omsHeader.get(i).getRefno()) && omsHeader.get(i).getCorporateList() != null) {
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
            if (getOMSTransactionResponses != null) {
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
            } else {
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
    public void LoadOmsOrderSuccess(CustomerDataResBean response) {

    }

    @Override
    public void LoadOmsOrderFailure(CustomerDataResBean response) {

    }


    @Override
    public void CheckBatchStock(GetOMSTransactionResponse response) {

    }

    @Override
    public void onBatchStockFailure(GetOMSTransactionResponse response) {

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
    public void onSuccessBatchInfo(GetBatchInfoRes response) {

    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {

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


        OMSOrderUpdateRequest request = new OMSOrderUpdateRequest();

        request.setRequestType("4");
            request.setFulfillmentID(racksDataResponse.getRefno());
            ArrayList<SalesLineEntity> pick_pack_list = new ArrayList<>();
            for (SalesLineEntity item : salesentity) {
                if (item.getModifyBatchId().length() > 0) {
                    pick_pack_list.add(item);
                }
            }
            request.setReservedSalesLine(pick_pack_list);
            mPresenter.UpdateOmsOrder(request);
        }

    @Override
    public void OmsOrderUpdateSuccess(OMSOrderUpdateResponse response) {

    }

    @Override
    public void OmsOrderUpdateFailure(OMSOrderUpdateResponse response) {

    }


//    private void mposOrderUpdate(String requestType) {
//        OMSOrderForwardRequest omsOrderForwardRequest = new OMSOrderForwardRequest();
//        omsOrderForwardRequest.setRequestType(requestType);
//        omsOrderForwardRequest.setFulfillmentID(racksDataResponse.getRefno());
//        List<OMSOrderForwardRequest.ReservedSalesLine> reservedSalesLineArrayList = new ArrayList<>();
//
//
//        if (omsOrderForwardRequest.getFulfillmentID().equalsIgnoreCase(racksDataResponse.getRefno())) {
//            for (int k = 0; k < racksDataResponse.getGetOMSTransactionResponse().getSalesLine().size(); k++) {
//                for (int m = 0; m < racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().size(); m++) {
//                    if (racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getItemId().equals(racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupItemId())) {
//                        OMSOrderForwardRequest.ReservedSalesLine reservedSalesLine = new OMSOrderForwardRequest.ReservedSalesLine();
//                        reservedSalesLine.setAdditionaltax(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getAdditionaltax());
//                        reservedSalesLine.setApplyDiscount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getApplyDiscount());
//                        reservedSalesLine.setBarcode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getBarcode());
//                        reservedSalesLine.setBaseAmount(Double.valueOf(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getBaseAmount()));
//                        reservedSalesLine.setCESSPerc(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getCESSPerc());
//                        reservedSalesLine.setCESSTaxCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getCESSTaxCode());
//                        reservedSalesLine.setCGSTPerc(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getCGSTPerc());
//                        reservedSalesLine.setCGSTTaxCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getCGSTTaxCode());
//                        reservedSalesLine.setCategory(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getCategory());
//                        reservedSalesLine.setCategoryCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryCode());
//                        reservedSalesLine.setCategoryReference(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryReference());
//                        reservedSalesLine.setComment(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getComment());
//                        reservedSalesLine.setDpco(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDpco());
//                        reservedSalesLine.setDiscAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
//                        reservedSalesLine.setDiscOfferId(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscOfferId());
//                        reservedSalesLine.setDiscountStructureType(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountStructureType());
//                        reservedSalesLine.setDiscountType(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
//                        reservedSalesLine.setDiseaseType(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDiseaseType());
//                        reservedSalesLine.setExpiry(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getExpiry());
//                        reservedSalesLine.setHsncodeIn(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getHsncodeIn());
//                        reservedSalesLine.setIGSTPerc(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTPerc());
//                        reservedSalesLine.setIGSTTaxCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTTaxCode());
//                        reservedSalesLine.setISPrescribed(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getISPrescribed());
//                        reservedSalesLine.setISReserved(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getISReserved());
//                        reservedSalesLine.setISStockAvailable(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getISStockAvailable());
//                        reservedSalesLine.setInventBatchId(racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupInventBatchId());
//                        reservedSalesLine.setIsChecked(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getIsChecked());
//                        reservedSalesLine.setIsGeneric(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getIsGeneric());
//                        reservedSalesLine.setIsPriceOverride(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getIsPriceOverride());
//                        reservedSalesLine.setIsSubsitute(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getIsSubsitute());
//                        reservedSalesLine.setIsVoid(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getIsVoid());
//                        reservedSalesLine.setItemId(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getItemId());
//                        reservedSalesLine.setItemName(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getItemName());
//                        reservedSalesLine.setLineDiscPercentage(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
//                        reservedSalesLine.setLineDiscPercentage(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
//                        reservedSalesLine.setLineManualDiscountAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountAmount());
//                        reservedSalesLine.setLineManualDiscountPercentage(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountPercentage());
//                        reservedSalesLine.setLineNo(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getLineNo());
//                        reservedSalesLine.setLinedscAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getLinedscAmount());
//                        reservedSalesLine.setMMGroupId(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getMMGroupId());
//                        reservedSalesLine.setMrp(racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPrice());
//                        reservedSalesLine.setManufacturerCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerCode());
//                        reservedSalesLine.setManufacturerName(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerName());
//                        reservedSalesLine.setMixMode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getMixMode());
//                        reservedSalesLine.setModifyBatchId(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getModifyBatchId());
//                        reservedSalesLine.setNetAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmount());
//                        reservedSalesLine.setNetAmountInclTax(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmountInclTax());
//                        reservedSalesLine.setOfferAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferAmount());
//                        reservedSalesLine.setDiscountType(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
//                        reservedSalesLine.setOfferDiscountValue(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferDiscountValue());
//                        reservedSalesLine.setOfferQty(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferQty());
//                        reservedSalesLine.setOfferType(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferType());
//                        reservedSalesLine.setOmsLineRECID(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getOmsLineRECID());
//                        reservedSalesLine.setOrderStatus(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getOrderStatus());
//                        reservedSalesLine.setOriginalPrice(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getOriginalPrice());
//                        reservedSalesLine.setPeriodicDiscAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getPeriodicDiscAmount());
//                        reservedSalesLine.setPhysicalMRP(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getPhysicalMRP());
//                        reservedSalesLine.setPreviewText(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getPreviewText());
//                        reservedSalesLine.setPrice(racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPrice());
//                        reservedSalesLine.setProductRecID(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getProductRecID());
////                    String reqQtyDoubleDataFormat = String.valueOf(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getREQQTY());
////                    int reqQty = 0;
////                    if (reqQtyDoubleDataFormat.contains(".")) {
////                        reqQty = Integer.parseInt(reqQtyDoubleDataFormat.substring(0, reqQtyDoubleDataFormat.indexOf(".")));
////                    }
//                        reservedSalesLine.setQty(racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupQty());
//                        reservedSalesLine.setRemainderDays(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getRemainderDays());
//                        reservedSalesLine.setRemainingQty(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getRemainingQty());
//                        reservedSalesLine.setResqtyflag(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getResqtyflag());
//                        reservedSalesLine.setRetailCategoryRecID(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getRetailCategoryRecID());
//                        reservedSalesLine.setRetailMainCategoryRecID(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getRetailMainCategoryRecID());
//                        reservedSalesLine.setRetailSubCategoryRecID(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getRetailSubCategoryRecID());
//                        reservedSalesLine.setReturnQty(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getReturnQty());
//                        reservedSalesLine.setSGSTPerc(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getSGSTPerc());
//                        reservedSalesLine.setSGSTTaxCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getSGSTTaxCode());
//                        reservedSalesLine.setScheduleCategory(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategory());
//                        reservedSalesLine.setScheduleCategoryCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategoryCode());
//                        reservedSalesLine.setStockQty(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getStockQty());
//                        reservedSalesLine.setSubCategory(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategory());
//                        reservedSalesLine.setSubCategoryCode(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategoryCode());
//                        reservedSalesLine.setSubClassification(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getSubClassification());
//                        reservedSalesLine.setSubClassification(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getSubClassification());
//                        reservedSalesLine.setSubstitudeItemId(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getSubstitudeItemId());
//                        reservedSalesLine.setTax(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getTax());
//                        reservedSalesLine.setTaxAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getTaxAmount());
//                        reservedSalesLine.setTotal(Double.valueOf(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getTotal()));
//                        reservedSalesLine.setTotalDiscAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
//                        reservedSalesLine.setTotalDiscPct(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getTotalDiscPct());
//                        reservedSalesLine.setTotalRoundedAmount(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getTotalRoundedAmount());
//                        reservedSalesLine.setTotalTax(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getTotalTax());
//                        reservedSalesLine.setUnit(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getUnit());
//                        reservedSalesLine.setUnitPrice(racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPrice());
//                        reservedSalesLine.setUnitQty(racksDataResponse.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupQty());
//                        reservedSalesLine.setVariantId(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).getVariantId());
//                        reservedSalesLine.setIsReturnClick(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).isReturnClick());
//                        reservedSalesLine.setIsSelectedReturnItem(racksDataResponse.getGetOMSTransactionResponse().getSalesLine().get(k).isSelectedReturnItem());
//                        reservedSalesLineArrayList.add(reservedSalesLine);
//                    }
//                }
//            }
//        }
//        omsOrderForwardRequest.setReservedSalesLine(reservedSalesLineArrayList);
//        mPresenter.UpdateOmsOrder(omsOrderForwardRequest);
//    }


}