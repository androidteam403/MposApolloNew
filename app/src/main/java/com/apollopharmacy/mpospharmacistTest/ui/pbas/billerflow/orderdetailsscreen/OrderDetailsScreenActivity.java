package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOrderDetailsScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBillerSelectActionPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogVerificationStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter.OrderDetailsScreenAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class OrderDetailsScreenActivity extends BaseActivity implements OrderDetailsScreenMvpView {

    @Inject
    OrderDetailsScreenMvpPresenter<OrderDetailsScreenMvpView> mPresenter;
    ActivityOrderDetailsScreenPBinding activityOrderDetailsScreenBinding;
    OrderDetailsScreenAdapter orderDetailsScreenAdapter;

    // changes made by naveen
    private OMSTransactionHeaderResModel.OMSHeaderObj orderInfoItem;
    private List<CustomerDataResBean> omsHeader = new ArrayList<>();
    private TransactionIDResModel transactionIDResModel;
    private CorporateModel corporateModel;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList = new ArrayList<>();
    private CustomerDataResBean customerDataResBean;
    private final int ACTIVITY_EPRESCRIPTIONBILLING_DETAILS_CODE = 122;
    private GetCustomerResponse.CustomerEntity customerEntity = new GetCustomerResponse.CustomerEntity();
    private String eprescription_corpcode = "0";
    private int salesLineCount = 0;
    private String boxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                orderInfoItem = (OMSTransactionHeaderResModel.OMSHeaderObj) getIntent().getSerializableExtra("fullfillmentDetails");
                mPresenter.fetchOMSCustomerInfo(orderInfoItem.getREFNO());
            }
        }
        mPresenter.getTransactionID();

        activityOrderDetailsScreenBinding.menuIcon.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        });
        if (orderInfoItem.getOverallOrderStatus() != null && orderInfoItem.getOverallOrderStatus().length() > 2) {
            this.boxId = orderInfoItem.getOverallOrderStatus().substring(2);
            activityOrderDetailsScreenBinding.boxId.setText(boxId);
//            activityOrderDetailsScreenBinding.boxId.setText(boxId.substring(boxId.length() - 5));
        } else {
            this.boxId = "-";
        }
        activityOrderDetailsScreenBinding.boxId.setText(boxId);
//        activityOrderDetailsScreenBinding.fullfillmentId.setText(orderInfoItem.getREFNO());
        if (orderInfoItem.getOverallOrderStatus().substring(0, 1).equals("0")) {
            activityOrderDetailsScreenBinding.statusIcon.setVisibility(View.GONE);
        } else if (orderInfoItem.getOverallOrderStatus().substring(0, 1).equals("1")) {
            activityOrderDetailsScreenBinding.statusIcon.setRotation(0);
            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_circle_tick));
        } else if (orderInfoItem.getOverallOrderStatus().substring(0, 1).equals("2")) {
            activityOrderDetailsScreenBinding.statusIcon.setRotation(90);
            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.partialcirculargreeenorange));
        } else if (orderInfoItem.getOverallOrderStatus().substring(0, 1).equals("3")) {
            activityOrderDetailsScreenBinding.statusIcon.setRotation(0);
            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_not_available));
        }
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
        this.transactionIDResModel = model;
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        this.corporateModel = corporateModel;
        corporateList.addAll(corporateModel.get_DropdownValue());
    }


    @Override
    public void onminusOrderDetails() {
//        activityOrderDetailsScreenBinding.orderDetailsPlusSymbol.setVisibility(View.VISIBLE);
//        activityOrderDetailsScreenBinding.orderDetailsMinusSymbol.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.orderDetailsRecycler.setVisibility(View.GONE);
//        activityOrderDetailsScreenBinding.quantityStatus.setVisibility(View.GONE);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccessGetOMSTransaction(ArrayList<CustomerDataResBean> getOMSTransactionResponses) {
        for (int i = 0; i < getOMSTransactionResponses.size(); i++) {
            if (getOMSTransactionResponses != null && getOMSTransactionResponses.get(i).getPickPackReservation() != null) {
                this.customerDataResBean = getOMSTransactionResponses.get(0);
                if (orderInfoItem.getREFNO().equalsIgnoreCase(getOMSTransactionResponses.get(i).getREFNO())) {

                    activityOrderDetailsScreenBinding.fullfilmentIdnumber.setText(customerDataResBean.getREFNO());
                    activityOrderDetailsScreenBinding.totalItems.setText(String.valueOf(customerDataResBean.getSalesLine().size()));
                    activityOrderDetailsScreenBinding.customerType.setText(customerDataResBean.getCustomerType());
                    activityOrderDetailsScreenBinding.orderSource.setText(customerDataResBean.getOrderSource());
                    activityOrderDetailsScreenBinding.orderDate.setText(customerDataResBean.getCreatedDateTime());
                    activityOrderDetailsScreenBinding.deliveryDate.setText(customerDataResBean.getDeliveryDate());
                    activityOrderDetailsScreenBinding.shippingMethodType.setText(customerDataResBean.getShippingMethod());
                    activityOrderDetailsScreenBinding.stockStatus.setText(customerDataResBean.getStockStatus());
                    activityOrderDetailsScreenBinding.paymentSource.setText(customerDataResBean.getPaymentSource());
                    activityOrderDetailsScreenBinding.orderType.setText(customerDataResBean.getOrderType());
                    activityOrderDetailsScreenBinding.customerName.setText(customerDataResBean.getCustomerName());
                    activityOrderDetailsScreenBinding.vendorId.setText(customerDataResBean.getVendorId());
                    activityOrderDetailsScreenBinding.mobileNumber.setText(customerDataResBean.getMobileNO());
                    activityOrderDetailsScreenBinding.doctorName.setText(customerDataResBean.getDoctorName());
                    activityOrderDetailsScreenBinding.statecode.setText(customerDataResBean.getState());
                    activityOrderDetailsScreenBinding.city.setText(customerDataResBean.getBillingCity());
                    activityOrderDetailsScreenBinding.address.setText(customerDataResBean.getCustAddress());
                    activityOrderDetailsScreenBinding.pincode.setText(customerDataResBean.getPincode());

                    eprescription_corpcode = customerDataResBean.getCorpCode();

                    customerEntity.setLastName(customerDataResBean.getCustomerName());
                    customerEntity.setMiddleName(customerDataResBean.getCustomerName());
                    customerEntity.setMobileNo(customerDataResBean.getMobileNO());
                    customerEntity.setCardName(customerDataResBean.getCustomerName());
                    customerEntity.setCorpId(customerDataResBean.getCorpCode());
                    customerEntity.setCustId(customerDataResBean.getCustomerID());
                    customerEntity.setGender(String.valueOf(customerDataResBean.getGender()));

                    orderDetailsScreenAdapter = new OrderDetailsScreenAdapter(this, customerDataResBean.getSalesLine(), customerDataResBean.getPickPackReservation(), selectedBatchList, customerDataResBean);
                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    activityOrderDetailsScreenBinding.productListRecycler.setLayoutManager(mLayoutManager1);
                    activityOrderDetailsScreenBinding.productListRecycler.setItemAnimator(new DefaultItemAnimator());
                    activityOrderDetailsScreenBinding.productListRecycler.setAdapter(orderDetailsScreenAdapter);

                    if (customerDataResBean.getSalesLine() != null && customerDataResBean.getSalesLine().size() > 0) {
                        showLoading();
                        mPresenter.getBatchDetailsApi(customerDataResBean.getSalesLine().get(salesLineCount).getItemId());
                    }
                }
            } else {
                hideLoading();
                Toast.makeText(this, "Pick Pack Reservation is null", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
            }
        }
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 3) {
            if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
                if (orderInfoItem != null) {
                    mPresenter.fetchOMSCustomerInfo(orderInfoItem.getREFNO());
                }
            }
        } else if (requestType == 4) {
            finish();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        } else if (requestType == 6) {
            finish();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        } else if (requestType == 7) {
            finish();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        }
    }

    @Override
    public void LoadOmsOrderSuccess(CustomerDataResBean response) {
        CorporateModel.DropdownValueBean item = new CorporateModel.DropdownValueBean();
        CustomerDataResBean customerDataResBean_pass;
        customerDataResBean_pass = response;

        eprescription_corpcode = response.getCorpCode();

        customerEntity.setLastName(response.getCustomerName());
        customerEntity.setMiddleName(response.getCustomerName());
        customerEntity.setMobileNo(response.getMobileNO());
        customerEntity.setCardName(response.getCustomerName());
        customerEntity.setCorpId(response.getCorpCode());
        customerEntity.setCustId(response.getCustomerID());
        customerEntity.setGender(String.valueOf(response.getGender()));

        DoctorSearchResModel.DropdownValueBean doctorentyty = new DoctorSearchResModel.DropdownValueBean();
        doctorentyty.setDisplayText(response.getDoctorName());
        doctorentyty.setCode("R00123");

        ArrayList<SalesLineEntity> saleslineentity = new ArrayList<>();
        for (SalesLineEntity salesLineEntity : customerDataResBean_pass.getSalesLine()) {
            if (salesLineEntity.getTotalTax() == 0) {
                salesLineEntity.setTotalTax(salesLineEntity.getTax());
            }
            saleslineentity.add(salesLineEntity);
        }
        customerDataResBean_pass.setSalesLine(saleslineentity);


        int position = 0;
        int tempposition = 0;
        if (corporateList != null) {
            for (CorporateModel.DropdownValueBean row : corporateList) {
                if (row.getCode().contains(eprescription_corpcode)) {
                    position = tempposition;
                    break;
                }
                tempposition++;
            }
            if (corporateList != null && corporateList.size() > 0)
                item = corporateList.get(position);
        }

        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().itemsArrayList.addAll(new ArrayList<>(saleslineentity));
        boolean is_omsorder = true;

        startActivityForResult(AddItemActivity.getStartIntent(getContext(), saleslineentity, customerEntity, orderInfoItem, customerDataResBean_pass, transactionIDResModel, is_omsorder, item, doctorentyty, true), ACTIVITY_EPRESCRIPTIONBILLING_DETAILS_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void LoadOmsOrderFailure(CustomerDataResBean response) {
        Toast.makeText(this, response.getReturnMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CheckBatchStockSuccess(CustomerDataResBean response) {
        if (response != null) {
            customerDataResBean = response;
            if (orderInfoItem.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                mPresenter.onLoadOmsOrder(customerDataResBean);
            } else {
                CheckBatchStockFailure(customerDataResBean);

            }


        }
    }

    @Override
    public void CheckBatchStockFailure(CustomerDataResBean response) {
        String message = response.getReturnMessage();
        message = message + "is not available! \nThe Stock status is Partially Available\n Do you still need to continue to Billing.";

        Dialog dialog = new Dialog(this);// , R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancelBinding.dialogMessage.setText(message);
        dialogCancelBinding.dialogButtonNO.setText("Cancel");
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setText("Proceed");
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            dialog.dismiss();
            customerDataResBean = response;
            mPresenter.onLoadOmsOrder(customerDataResBean);
        });
        dialog.show();
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

    private List<GetBatchInfoRes.BatchListObj> selectedBatchList = new ArrayList<>();


    @Override
    public void onSuccessBatchInfo(GetBatchInfoRes response) {
        if (customerDataResBean.getPickPackReservation() != null && customerDataResBean.getPickPackReservation().size() > 0 && response != null && response.getBatchList() != null && response.getBatchList().size() > 0) {
            for (int i = 0; i < customerDataResBean.getPickPackReservation().size(); i++) {
                for (int j = 0; j < response.getBatchList().size(); j++) {
                    if (customerDataResBean.getPickPackReservation().get(i).getPickupItemId().equals(response.getBatchList().get(j).getItemID()) && customerDataResBean.getPickPackReservation().get(i).getPickupInventBatchId().equals(response.getBatchList().get(j).getBatchNo())) {
                        selectedBatchList.add(response.getBatchList().get(j));

                    }
                }
            }
        }
        if (salesLineCount + 1 != customerDataResBean.getSalesLine().size()) {
            salesLineCount++;
            mPresenter.getBatchDetailsApi(customerDataResBean.getSalesLine().get(salesLineCount).getItemId());
        } else {
            hideLoading();
        }
        orderDetailsScreenAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {
        salesLineCount++;
        if (salesLineCount + 1 != customerDataResBean.getSalesLine().size()) {
            mPresenter.getBatchDetailsApi(customerDataResBean.getSalesLine().get(salesLineCount).getItemId());
        } else {
            hideLoading();
        }
        Toast.makeText(this, "No batch available", Toast.LENGTH_SHORT).show();
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
        selectActionLayoutBinding.crossIcon.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onGenerateBill() {
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.GONE);
//        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.GONE);
//        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrintLabel() {
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.GONE);
//        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.GONE);
//        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrintShippingLabel() {
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.GONE);
//        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.GONE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSendBacktoPackerLabel() {
        selectActionLayoutBinding.checkedGenerateBill.setVisibility(View.GONE);
        selectActionLayoutBinding.uncheckedGenerateBill.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.checkedPrintLabel.setVisibility(View.GONE);
//        selectActionLayoutBinding.uncheckedPrintLabel.setVisibility(View.VISIBLE);
//        selectActionLayoutBinding.checkedShippingLabel.setVisibility(View.GONE);
//        selectActionLayoutBinding.uncheckedShippingLabel.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.checkedSendToPacker.setVisibility(View.VISIBLE);
        selectActionLayoutBinding.uncheckedSendToPacker.setVisibility(View.GONE);
    }

    @Override
    public void onClickProceedAction() {
        if (selectActionLayoutBinding.checkedGenerateBill.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < customerDataResBean.getSalesLine().size(); i++) {
                if (customerDataResBean.getSalesLine().get(i).getInventBatchId() == null || customerDataResBean.getSalesLine().get(i).getInventBatchId().isEmpty()) {
                    boolean isItemHaveMoreThanOneBatch = false;
                    for (int j = 0; j < customerDataResBean.getPickPackReservation().size(); j++) {
                        if (customerDataResBean.getSalesLine().get(i).getItemId().equals(customerDataResBean.getPickPackReservation().get(j).getPickupItemId())) {
                            if (!isItemHaveMoreThanOneBatch) {
                                isItemHaveMoreThanOneBatch = true;
//                                customerDataResBean.getSalesLine().get(i).setCGSTPerc(selectedBatchList.get(j).getCGSTPerc());
//                                customerDataResBean.getSalesLine().get(i).setCGSTTaxCode(selectedBatchList.get(j).getCGSTTaxCode());
                                customerDataResBean.getSalesLine().get(i).setExpiry(customerDataResBean.getPickPackReservation().get(j).getExpiry());
                                customerDataResBean.getSalesLine().get(i).setInventBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
                                customerDataResBean.getSalesLine().get(i).setMRP(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                customerDataResBean.getSalesLine().get(i).setPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
//                                for (int k = 0; k < customerDataResBean.getPickPackReservation().size(); k++) {
//                                    if (customerDataResBean.getPickPackReservation().get(k).getPickupItemId().equals(selectedBatchList.get(j).getItemID()) && customerDataResBean.getPickPackReservation().get(k).getPickupInventBatchId().equals(selectedBatchList.get(j).getBatchNo())) {
//                                        selectedBatchList.get(j).setREQQTY(customerDataResBean.getPickPackReservation().get(k).getPickupQty());
//                                    }
//                                }
                                customerDataResBean.getSalesLine().get(i).setQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());
//                                customerDataResBean.getSalesLine().get(i).setSGSTPerc(selectedBatchList.get(j).getSGSTPerc());
//                                customerDataResBean.getSalesLine().get(i).setSGSTTaxCode(selectedBatchList.get(j).getSGSTTaxCode());
//                                customerDataResBean.getSalesLine().get(i).setTotalTax(selectedBatchList.get(j).getTotalTax());
                                customerDataResBean.getSalesLine().get(i).setUnitPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                            } else {
                                SalesLineEntity salesLineEntityTemp = customerDataResBean.getSalesLine().get(i);
//                                salesLineEntityTemp.setCGSTPerc(selectedBatchList.get(j).getCGSTPerc());
//                                salesLineEntityTemp.setCGSTTaxCode(selectedBatchList.get(j).getCGSTTaxCode());
                                salesLineEntityTemp.setExpiry(customerDataResBean.getPickPackReservation().get(j).getExpiry());
                                salesLineEntityTemp.setInventBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
                                salesLineEntityTemp.setMRP(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                salesLineEntityTemp.setPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
//                                for (int k = 0; k < customerDataResBean.getPickPackReservation().size(); k++) {
//                                    if (customerDataResBean.getPickPackReservation().get(k).getPickupItemId().equals(selectedBatchList.get(j).getItemID()) && customerDataResBean.getPickPackReservation().get(k).getPickupInventBatchId().equals(selectedBatchList.get(j).getBatchNo())) {
//                                        selectedBatchList.get(j).setREQQTY(customerDataResBean.getPickPackReservation().get(k).getPickupQty());
//                                    }
//                                }
                                salesLineEntityTemp.setQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());
//                                salesLineEntityTemp.setSGSTPerc(selectedBatchList.get(j).getSGSTPerc());
//                                salesLineEntityTemp.setSGSTTaxCode(selectedBatchList.get(j).getSGSTTaxCode());
//                                salesLineEntityTemp.setTotalTax(selectedBatchList.get(j).getTotalTax());
                                salesLineEntityTemp.setUnitPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                customerDataResBean.getSalesLine().add(salesLineEntityTemp);
                            }
                        }
                    }
                }
            }
            mPresenter.onCheckBatchStock(customerDataResBean);
        } else if (selectActionLayoutBinding.checkedPrintLabel.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        } else if (selectActionLayoutBinding.checkedShippingLabel.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
        } else if (selectActionLayoutBinding.checkedSendToPacker.getVisibility() == View.VISIBLE) {
            Dialog dialog = new Dialog(this);// , R.style.Theme_AppCompat_DayNight_NoActionBar
            DialogVerificationStatusPBinding dialogVerificationStatusPBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_verification_status_p, null, false);
//            dialogVerificationStatusPBinding.pickupVerificationStatusText.setText("Biller verified for");
//            dialogVerificationStatusPBinding.fullfilmentId.setText(customerDataResBean.getREFNO());
            dialogVerificationStatusPBinding.title.setText("Send back to pcker");
            dialogVerificationStatusPBinding.dialogMessage.setText("Biller verified for\n Fulfilment ID :" + customerDataResBean.getREFNO() + "\n Push to billing");
            dialog.setContentView(dialogVerificationStatusPBinding.getRoot());
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialogVerificationStatusPBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
            dialogVerificationStatusPBinding.dialogButtonOK.setOnClickListener(v -> {
                dialog.dismiss();
                unPacking();

            });
//            dialogVerificationStatusPBinding.dialogButtonNot.setOnClickListener(v -> dialog.dismiss());
        }
    }

    private void unPacking() {
        OMSOrderForwardRequest omsOrderForwardRequest = new OMSOrderForwardRequest();
        omsOrderForwardRequest.setRequestType("4");
        omsOrderForwardRequest.setFulfillmentID(customerDataResBean.getREFNO());
        List<OMSOrderForwardRequest.ReservedSalesLine> reservedSalesLineArrayList = new ArrayList<>();
        for (int i = 0; i < customerDataResBean.getSalesLine().size(); i++) {
            for (int j = 0; j < customerDataResBean.getPickPackReservation().size(); j++) {
                if (customerDataResBean.getSalesLine().get(i).getItemId().equals(customerDataResBean.getPickPackReservation().get(j).getPickupItemId())) {
                    OMSOrderForwardRequest.ReservedSalesLine reservedSalesLine = new OMSOrderForwardRequest.ReservedSalesLine();
                    reservedSalesLine.setAdditionaltax(customerDataResBean.getSalesLine().get(i).getAdditionaltax());
                    reservedSalesLine.setApplyDiscount(customerDataResBean.getSalesLine().get(i).getApplyDiscount());
                    reservedSalesLine.setBarcode(customerDataResBean.getSalesLine().get(i).getBarcode());

                    reservedSalesLine.setBaseAmount(customerDataResBean.getSalesLine().get(i).getMRP());

                    reservedSalesLine.setCESSPerc(customerDataResBean.getSalesLine().get(i).getCESSPerc());
                    reservedSalesLine.setCESSTaxCode(customerDataResBean.getSalesLine().get(i).getCESSTaxCode());
                    reservedSalesLine.setCGSTPerc(customerDataResBean.getSalesLine().get(i).getCGSTPerc());
                    reservedSalesLine.setCGSTTaxCode(customerDataResBean.getSalesLine().get(i).getCGSTTaxCode());
                    reservedSalesLine.setCategory(customerDataResBean.getSalesLine().get(i).getCategory());
                    reservedSalesLine.setCategoryCode(customerDataResBean.getSalesLine().get(i).getCategoryCode());
                    reservedSalesLine.setCategoryReference(customerDataResBean.getSalesLine().get(i).getCategoryReference());
                    reservedSalesLine.setComment(customerDataResBean.getSalesLine().get(i).getComment());
                    reservedSalesLine.setDpco(customerDataResBean.getSalesLine().get(i).getDPCO());
                    reservedSalesLine.setDiscAmount(customerDataResBean.getSalesLine().get(i).getDiscAmount());
                    reservedSalesLine.setDiscOfferId(customerDataResBean.getSalesLine().get(i).getDiscOfferId());
                    reservedSalesLine.setDiscountStructureType(customerDataResBean.getSalesLine().get(i).getDiscountStructureType());
                    reservedSalesLine.setDiscountType(customerDataResBean.getSalesLine().get(i).getDiscountType());
                    reservedSalesLine.setDiseaseType(customerDataResBean.getSalesLine().get(i).getDiseaseType());
                    reservedSalesLine.setExpiry(customerDataResBean.getPickPackReservation().get(j).getExpiry());
                    reservedSalesLine.setHsncodeIn(customerDataResBean.getSalesLine().get(i).getHsncode_In());
                    reservedSalesLine.setIGSTPerc(customerDataResBean.getSalesLine().get(i).getIGSTPerc());
                    reservedSalesLine.setIGSTTaxCode(customerDataResBean.getSalesLine().get(i).getIGSTTaxCode());
                    reservedSalesLine.setISPrescribed(customerDataResBean.getSalesLine().get(i).getISPrescribed());
                    reservedSalesLine.setISReserved(customerDataResBean.getSalesLine().get(i).getISReserved());
                    reservedSalesLine.setISStockAvailable(customerDataResBean.getSalesLine().get(i).getISStockAvailable());
                    reservedSalesLine.setInventBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
                    reservedSalesLine.setIsChecked(customerDataResBean.getSalesLine().get(i).getIsChecked());
                    reservedSalesLine.setIsGeneric(customerDataResBean.getSalesLine().get(i).getIsGeneric());
                    reservedSalesLine.setIsPriceOverride(customerDataResBean.getSalesLine().get(i).getIsPriceOverride());
                    reservedSalesLine.setIsSubsitute(customerDataResBean.getSalesLine().get(i).getIsSubsitute());
                    reservedSalesLine.setIsVoid(customerDataResBean.getSalesLine().get(i).getIsVoid());
                    reservedSalesLine.setItemId(customerDataResBean.getSalesLine().get(i).getItemId());
                    reservedSalesLine.setItemName(customerDataResBean.getSalesLine().get(i).getItemName());
                    reservedSalesLine.setLineDiscPercentage(customerDataResBean.getSalesLine().get(i).getLineDiscPercentage());
                    reservedSalesLine.setLineManualDiscountAmount(customerDataResBean.getSalesLine().get(i).getLineManualDiscountAmount());
                    reservedSalesLine.setLineManualDiscountPercentage(customerDataResBean.getSalesLine().get(i).getLineManualDiscountPercentage());
                    reservedSalesLine.setLineNo(customerDataResBean.getSalesLine().get(i).getLineNo());//selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineNo()
                    reservedSalesLine.setLinedscAmount(customerDataResBean.getSalesLine().get(i).getLinedscAmount());
                    reservedSalesLine.setMMGroupId(customerDataResBean.getSalesLine().get(i).getMMGroupId());
                    reservedSalesLine.setMrp(customerDataResBean.getPickPackReservation().get(j).getPrice());
                    reservedSalesLine.setManufacturerCode(customerDataResBean.getSalesLine().get(i).getManufacturerCode());
                    reservedSalesLine.setManufacturerName(customerDataResBean.getSalesLine().get(i).getManufacturerName());
                    reservedSalesLine.setMixMode(customerDataResBean.getSalesLine().get(i).getMixMode());
                    reservedSalesLine.setModifyBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
                    reservedSalesLine.setNetAmount(customerDataResBean.getSalesLine().get(i).getNetAmount());
                    reservedSalesLine.setNetAmountInclTax(customerDataResBean.getSalesLine().get(i).getNetAmountInclTax());
                    reservedSalesLine.setOfferAmount(customerDataResBean.getSalesLine().get(i).getOfferAmount());
                    reservedSalesLine.setDiscountType(customerDataResBean.getSalesLine().get(i).getDiscountType());
                    reservedSalesLine.setOfferDiscountValue(customerDataResBean.getSalesLine().get(i).getOfferDiscountValue());
                    reservedSalesLine.setOfferQty(customerDataResBean.getSalesLine().get(i).getOfferQty());
                    reservedSalesLine.setOfferType(customerDataResBean.getSalesLine().get(i).getOfferType());
                    reservedSalesLine.setOmsLineRECID(customerDataResBean.getSalesLine().get(i).getOmsLineRECID());
                    reservedSalesLine.setOrderStatus(customerDataResBean.getSalesLine().get(i).getOrderStatus());
                    reservedSalesLine.setOriginalPrice(customerDataResBean.getSalesLine().get(i).getOriginalPrice());
                    reservedSalesLine.setPeriodicDiscAmount(customerDataResBean.getSalesLine().get(i).getPeriodicDiscAmount());
                    reservedSalesLine.setPhysicalMRP(customerDataResBean.getSalesLine().get(i).getPhysicalMRP());
                    reservedSalesLine.setPreviewText(customerDataResBean.getSalesLine().get(i).getPreviewText());
                    reservedSalesLine.setPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                    reservedSalesLine.setProductRecID(customerDataResBean.getSalesLine().get(i).getProductRecID());

//                    for (int k = 0; k < customerDataResBean.getPickPackReservation().size(); k++) {
//                        if (customerDataResBean.getPickPackReservation().get(k).getPickupItemId().equals(selectedBatchList.get(j).getItemID()) && customerDataResBean.getPickPackReservation().get(k).getPickupInventBatchId().equals(selectedBatchList.get(j).getBatchNo())) {
//                            selectedBatchList.get(j).setREQQTY(customerDataResBean.getPickPackReservation().get(k).getPickupQty());
//                        }
//                    }

                    reservedSalesLine.setQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());

                    reservedSalesLine.setRemainderDays(customerDataResBean.getSalesLine().get(i).getRemainderDays());
                    reservedSalesLine.setRemainingQty(customerDataResBean.getSalesLine().get(i).getRemainingQty());
                    reservedSalesLine.setResqtyflag(customerDataResBean.getSalesLine().get(i).getResqtyflag());
                    reservedSalesLine.setRetailCategoryRecID(customerDataResBean.getSalesLine().get(i).getRetailCategoryRecID());
                    reservedSalesLine.setRetailMainCategoryRecID(customerDataResBean.getSalesLine().get(i).getRetailMainCategoryRecID());
                    reservedSalesLine.setRetailSubCategoryRecID(customerDataResBean.getSalesLine().get(i).getRetailSubCategoryRecID());
                    reservedSalesLine.setReturnQty(customerDataResBean.getSalesLine().get(i).getReturnQty());
                    reservedSalesLine.setSGSTPerc(customerDataResBean.getSalesLine().get(i).getSGSTPerc());
                    reservedSalesLine.setSGSTTaxCode(customerDataResBean.getSalesLine().get(i).getSGSTTaxCode());
                    reservedSalesLine.setScheduleCategory(customerDataResBean.getSalesLine().get(i).getScheduleCategory());
                    reservedSalesLine.setScheduleCategoryCode(customerDataResBean.getSalesLine().get(i).getScheduleCategoryCode());
                    reservedSalesLine.setStockQty(customerDataResBean.getSalesLine().get(i).getStockQty());
                    reservedSalesLine.setSubCategory(customerDataResBean.getSalesLine().get(i).getSubCategory());
                    reservedSalesLine.setSubCategoryCode(customerDataResBean.getSalesLine().get(i).getSubCategoryCode());
                    reservedSalesLine.setSubClassification(customerDataResBean.getSalesLine().get(i).getSubClassification());
                    reservedSalesLine.setSubstitudeItemId(customerDataResBean.getSalesLine().get(i).getSubstitudeItemId());
                    reservedSalesLine.setTax(customerDataResBean.getSalesLine().get(i).getTax());
                    reservedSalesLine.setTaxAmount(customerDataResBean.getSalesLine().get(i).getTaxAmount());


                    reservedSalesLine.setTotal(customerDataResBean.getPickPackReservation().get(j).getPrice());


                    reservedSalesLine.setTotalDiscAmount(customerDataResBean.getSalesLine().get(i).getDiscAmount());
                    reservedSalesLine.setTotalDiscPct(customerDataResBean.getSalesLine().get(i).getTotalDiscPct());
                    reservedSalesLine.setTotalRoundedAmount(customerDataResBean.getSalesLine().get(i).getTotalRoundedAmount());
                    reservedSalesLine.setTotalTax(customerDataResBean.getSalesLine().get(i).getTotalTax());
                    reservedSalesLine.setUnit(customerDataResBean.getSalesLine().get(i).getUnit());
                    reservedSalesLine.setUnitPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());


                    reservedSalesLine.setUnitQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());


                    reservedSalesLine.setVariantId(customerDataResBean.getSalesLine().get(i).getVariantId());
                    reservedSalesLine.setIsReturnClick(customerDataResBean.getSalesLine().get(i).isReturnClick());
                    reservedSalesLine.setIsSelectedReturnItem(customerDataResBean.getSalesLine().get(i).isSelectedReturnItem());

                    reservedSalesLineArrayList.add(reservedSalesLine);
                }
            }
        }

        omsOrderForwardRequest.setReservedSalesLine(reservedSalesLineArrayList);
        mPresenter.UpdateOmsOrder(omsOrderForwardRequest);
    }

    @Override
    public void OmsOrderUpdateSuccess(OMSOrderForwardResponse response, String mposOrderUpdateRequestType) {
        mPresenter.mposPickPackOrderReservationApiCall(7, customerDataResBean);
    }

    @Override
    public void OmsOrderUpdateFailure(OMSOrderForwardResponse response) {

    }

    @Override
    public void onClickSendtoPacker() {
        unPacking();
    }

    @Override
    public void onClickContinueBill() {

        for (int i = 0; i < customerDataResBean.getSalesLine().size(); i++) {
            if (customerDataResBean.getSalesLine().get(i).getInventBatchId() == null || customerDataResBean.getSalesLine().get(i).getInventBatchId().isEmpty()) {
                boolean isItemHaveMoreThanOneBatch = false;
                for (int j = 0; j < customerDataResBean.getPickPackReservation().size(); j++) {
                    if (!customerDataResBean.getPickPackReservation().get(j).isPickPackSelected()) {
                        if (customerDataResBean.getSalesLine().get(i).getItemId().equals(customerDataResBean.getPickPackReservation().get(j).getPickupItemId())) {
                            customerDataResBean.getPickPackReservation().get(j).setPickPackSelected(true);
                            if (!isItemHaveMoreThanOneBatch) {
                                isItemHaveMoreThanOneBatch = true;
                                customerDataResBean.getSalesLine().get(i).setExpiry(customerDataResBean.getPickPackReservation().get(j).getExpiry());
                                customerDataResBean.getSalesLine().get(i).setInventBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
                                customerDataResBean.getSalesLine().get(i).setMRP(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                customerDataResBean.getSalesLine().get(i).setPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                customerDataResBean.getSalesLine().get(i).setQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());
                                customerDataResBean.getSalesLine().get(i).setUnitPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                            } else {
                                customerDataResBean.getPickPackReservation().get(j).setPickPackSelected(true);
//                                customerDataResBean.getSalesLine().add(i + 1, customerDataResBean.getSalesLine().get(i));
//
//                                customerDataResBean.getSalesLine().get(i+1).setExpiry(customerDataResBean.getPickPackReservation().get(j).getExpiry());
//                                customerDataResBean.getSalesLine().get(i+1).setInventBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
//                                customerDataResBean.getSalesLine().get(i+1).setMRP(customerDataResBean.getPickPackReservation().get(j).getPrice());
//                                customerDataResBean.getSalesLine().get(i+1).setPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
//                                customerDataResBean.getSalesLine().get(i+1).setQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());
//                                customerDataResBean.getSalesLine().get(i+1).setUnitPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());


                                SalesLineEntity salesLineEntityTemp = new SalesLineEntity();
                                salesLineEntityTemp.setAdditionaltax(customerDataResBean.getSalesLine().get(i).getAdditionaltax());
                                salesLineEntityTemp.setApplyDiscount(customerDataResBean.getSalesLine().get(i).getApplyDiscount());
                                salesLineEntityTemp.setBarcode(customerDataResBean.getSalesLine().get(i).getBarcode());
                                salesLineEntityTemp.setBaseAmount(customerDataResBean.getSalesLine().get(i).getBaseAmount());
                                salesLineEntityTemp.setCESSPerc(customerDataResBean.getSalesLine().get(i).getCESSPerc());
                                salesLineEntityTemp.setCESSTaxCode(customerDataResBean.getSalesLine().get(i).getCESSTaxCode());
                                salesLineEntityTemp.setCGSTPerc(customerDataResBean.getSalesLine().get(i).getCGSTPerc());
                                salesLineEntityTemp.setCGSTTaxCode(customerDataResBean.getSalesLine().get(i).getCGSTTaxCode());
                                salesLineEntityTemp.setCategory(customerDataResBean.getSalesLine().get(i).getCategory());
                                salesLineEntityTemp.setCategoryCode(customerDataResBean.getSalesLine().get(i).getCategoryCode());
                                salesLineEntityTemp.setCategoryReference(customerDataResBean.getSalesLine().get(i).getCategoryReference());
                                salesLineEntityTemp.setComment(customerDataResBean.getSalesLine().get(i).getComment());
                                salesLineEntityTemp.setDPCO(customerDataResBean.getSalesLine().get(i).getDPCO());
                                salesLineEntityTemp.setDiscAmount(customerDataResBean.getSalesLine().get(i).getDiscAmount());
                                salesLineEntityTemp.setDiscId(customerDataResBean.getSalesLine().get(i).getDiscId());
                                salesLineEntityTemp.setDiscOfferId(customerDataResBean.getSalesLine().get(i).getDiscOfferId());
                                salesLineEntityTemp.setDiscountStructureType(customerDataResBean.getSalesLine().get(i).getDiscountStructureType());
                                salesLineEntityTemp.setDiscountType(customerDataResBean.getSalesLine().get(i).getDiscountType());
                                salesLineEntityTemp.setDiseaseType(customerDataResBean.getSalesLine().get(i).getDiseaseType());
                                salesLineEntityTemp.setExpiry(customerDataResBean.getSalesLine().get(i).getExpiry());
                                salesLineEntityTemp.setHsncode_In(customerDataResBean.getSalesLine().get(i).getHsncode_In());
                                salesLineEntityTemp.setIGSTPerc(customerDataResBean.getSalesLine().get(i).getIGSTPerc());
                                salesLineEntityTemp.setIGSTTaxCode(customerDataResBean.getSalesLine().get(i).getIGSTTaxCode());
                                salesLineEntityTemp.setISPrescribed(customerDataResBean.getSalesLine().get(i).getISPrescribed());
                                salesLineEntityTemp.setISReserved(customerDataResBean.getSalesLine().get(i).getISReserved());
                                salesLineEntityTemp.setISStockAvailable(customerDataResBean.getSalesLine().get(i).getISStockAvailable());
                                salesLineEntityTemp.setInventBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
                                salesLineEntityTemp.setChecked(customerDataResBean.getSalesLine().get(i).getIsChecked());
                                salesLineEntityTemp.setGeneric(customerDataResBean.getSalesLine().get(i).getIsGeneric());
                                salesLineEntityTemp.setPriceOverride(customerDataResBean.getSalesLine().get(i).getIsPriceOverride());
                                salesLineEntityTemp.setSubsitute(customerDataResBean.getSalesLine().get(i).getIsSubsitute());
                                salesLineEntityTemp.setVoid(customerDataResBean.getSalesLine().get(i).getIsVoid());
                                salesLineEntityTemp.setItemId(customerDataResBean.getSalesLine().get(i).getItemId());
                                salesLineEntityTemp.setItemName(customerDataResBean.getSalesLine().get(i).getItemName());
                                salesLineEntityTemp.setLineDiscPercentage(customerDataResBean.getSalesLine().get(i).getLineDiscPercentage());
                                salesLineEntityTemp.setLineManualDiscountAmount(customerDataResBean.getSalesLine().get(i).getLineManualDiscountAmount());
                                salesLineEntityTemp.setLineManualDiscountPercentage(customerDataResBean.getSalesLine().get(i).getLineManualDiscountPercentage());
                                salesLineEntityTemp.setLineNo(customerDataResBean.getSalesLine().get(i).getLineNo());
                                salesLineEntityTemp.setLinedscAmount(customerDataResBean.getSalesLine().get(i).getLinedscAmount());
                                salesLineEntityTemp.setMMGroupId(customerDataResBean.getSalesLine().get(i).getMMGroupId());
                                salesLineEntityTemp.setMRP(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                salesLineEntityTemp.setManufacturerCode(customerDataResBean.getSalesLine().get(i).getManufacturerCode());
                                salesLineEntityTemp.setManufacturerName(customerDataResBean.getSalesLine().get(i).getManufacturerName());
                                salesLineEntityTemp.setMixMode(customerDataResBean.getSalesLine().get(i).getMixMode());
                                salesLineEntityTemp.setModifyBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupPhysicalInventBatchId());
                                salesLineEntityTemp.setNetAmount(customerDataResBean.getSalesLine().get(i).getNetAmount());
                                salesLineEntityTemp.setNetAmountInclTax(customerDataResBean.getSalesLine().get(i).getNetAmountInclTax());
                                salesLineEntityTemp.setOfferAmount(customerDataResBean.getSalesLine().get(i).getOfferAmount());
                                salesLineEntityTemp.setOfferDiscountType(customerDataResBean.getSalesLine().get(i).getOfferDiscountType());
                                salesLineEntityTemp.setOfferDiscountValue(customerDataResBean.getSalesLine().get(i).getOfferDiscountValue());
                                salesLineEntityTemp.setOfferQty(customerDataResBean.getSalesLine().get(i).getOfferQty());
                                salesLineEntityTemp.setOfferType(customerDataResBean.getSalesLine().get(i).getOfferType());
                                salesLineEntityTemp.setOmsLineID(customerDataResBean.getSalesLine().get(i).getOmsLineID());
                                salesLineEntityTemp.setOmsLineRECID(customerDataResBean.getSalesLine().get(i).getOmsLineRECID());
                                salesLineEntityTemp.setOrderStatus(customerDataResBean.getSalesLine().get(i).getOrderStatus());
                                salesLineEntityTemp.setOriginalPrice(customerDataResBean.getSalesLine().get(i).getOriginalPrice());
                                salesLineEntityTemp.setPeriodicDiscAmount(customerDataResBean.getSalesLine().get(i).getPeriodicDiscAmount());
                                salesLineEntityTemp.setPhysicalMRP(customerDataResBean.getSalesLine().get(i).getPhysicalMRP());
                                salesLineEntityTemp.setPreviewText(customerDataResBean.getSalesLine().get(i).getPreviewText());
                                salesLineEntityTemp.setPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                salesLineEntityTemp.setProductRecID(customerDataResBean.getSalesLine().get(i).getProductRecID());
                                salesLineEntityTemp.setQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());
                                salesLineEntityTemp.setRackId(customerDataResBean.getSalesLine().get(i).getRackId());
                                salesLineEntityTemp.setRemainderDays(customerDataResBean.getSalesLine().get(i).getRemainderDays());
                                salesLineEntityTemp.setRemainingQty(customerDataResBean.getSalesLine().get(i).getRemainingQty());
                                salesLineEntityTemp.setResqty(customerDataResBean.getSalesLine().get(i).getResqtyflag());
                                salesLineEntityTemp.setRetailCategoryRecID(customerDataResBean.getSalesLine().get(i).getRetailCategoryRecID());
                                salesLineEntityTemp.setRetailMainCategoryRecID(customerDataResBean.getSalesLine().get(i).getRetailMainCategoryRecID());
                                salesLineEntityTemp.setRetailSubCategoryRecID(customerDataResBean.getSalesLine().get(i).getRetailSubCategoryRecID());
                                salesLineEntityTemp.setReturnQty(customerDataResBean.getSalesLine().get(i).getReturnQty());
                                salesLineEntityTemp.setSGSTPerc(customerDataResBean.getSalesLine().get(i).getSGSTPerc());
                                salesLineEntityTemp.setSGSTTaxCode(customerDataResBean.getSalesLine().get(i).getSGSTTaxCode());
                                salesLineEntityTemp.setScheduleCategory(customerDataResBean.getSalesLine().get(i).getScheduleCategory());
                                salesLineEntityTemp.setScheduleCategoryCode(customerDataResBean.getSalesLine().get(i).getScheduleCategoryCode());
                                salesLineEntityTemp.setStockQty(customerDataResBean.getSalesLine().get(i).getStockQty());
                                salesLineEntityTemp.setSubCategory(customerDataResBean.getSalesLine().get(i).getSubCategory());
                                salesLineEntityTemp.setSubCategoryCode(customerDataResBean.getSalesLine().get(i).getSubCategoryCode());
                                salesLineEntityTemp.setSubClassification(customerDataResBean.getSalesLine().get(i).getSubClassification());
                                salesLineEntityTemp.setSubstitudeItemId(customerDataResBean.getSalesLine().get(i).getSubstitudeItemId());
                                salesLineEntityTemp.setTax(customerDataResBean.getSalesLine().get(i).getTax());
                                salesLineEntityTemp.setTaxAmount(customerDataResBean.getSalesLine().get(i).getTaxAmount());
                                salesLineEntityTemp.setTotal(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                salesLineEntityTemp.setTotalDiscAmount(customerDataResBean.getSalesLine().get(i).getTotalDiscAmount());
                                salesLineEntityTemp.setTotalDiscPct(customerDataResBean.getSalesLine().get(i).getTotalDiscPct());
                                salesLineEntityTemp.setTotalDiscAmount(customerDataResBean.getSalesLine().get(i).getTotalRoundedAmount());
                                salesLineEntityTemp.setTotalTax(customerDataResBean.getSalesLine().get(i).getTotalTax());
                                salesLineEntityTemp.setUnit(customerDataResBean.getSalesLine().get(i).getUnit());
                                salesLineEntityTemp.setUnitPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                salesLineEntityTemp.setUnitQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());
                                salesLineEntityTemp.setVariantId(customerDataResBean.getSalesLine().get(i).getVariantId());
                                salesLineEntityTemp.setReturnClick(false);
                                salesLineEntityTemp.setSelectedReturnItem(false);
                                salesLineEntityTemp.setPriceVariation(customerDataResBean.getSalesLine().get(i).getPriceVariation());
                                salesLineEntityTemp.setqCDate(customerDataResBean.getSalesLine().get(i).getqCDate());
                                salesLineEntityTemp.setqCRemarks(customerDataResBean.getSalesLine().get(i).getqCRemarks());
                                salesLineEntityTemp.setqCStatus(customerDataResBean.getSalesLine().get(i).getqCStatus());

//                                salesLineEntityTemp.setExpiry(customerDataResBean.getPickPackReservation().get(j).getExpiry());
//                                salesLineEntityTemp.setInventBatchId(customerDataResBean.getPickPackReservation().get(j).getPickupInventBatchId());
//                                salesLineEntityTemp.setMRP(customerDataResBean.getPickPackReservation().get(j).getPrice());
//                                salesLineEntityTemp.setPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
//                                salesLineEntityTemp.setQty(customerDataResBean.getPickPackReservation().get(j).getPickupQty());
//                                salesLineEntityTemp.setUnitPrice(customerDataResBean.getPickPackReservation().get(j).getPrice());
                                customerDataResBean.getSalesLine().add(salesLineEntityTemp);
                            }
                        }
                    }
                }
            }
        }
        mPresenter.onCheckBatchStock(customerDataResBean);

//        for (int i = 0; i < customerDataResBean.getSalesLine().size(); i++) {
//            if (customerDataResBean.getSalesLine().get(i).getInventBatchId() == null || customerDataResBean.getSalesLine().get(i).getInventBatchId().isEmpty()) {
//                boolean isItemHaveMoreThanOneBatch = false;
//                for (int j = 0; j < selectedBatchList.size(); j++) {
//                    if (customerDataResBean.getSalesLine().get(i).getItemId().equals(selectedBatchList.get(j).getItemID())) {
//                        if (!isItemHaveMoreThanOneBatch) {
//                            isItemHaveMoreThanOneBatch = true;
//                            customerDataResBean.getSalesLine().get(i).setCGSTPerc(selectedBatchList.get(j).getCGSTPerc());
//                            customerDataResBean.getSalesLine().get(i).setCGSTTaxCode(selectedBatchList.get(j).getCGSTTaxCode());
//                            customerDataResBean.getSalesLine().get(i).setExpiry(selectedBatchList.get(j).getExpDate());
//                            customerDataResBean.getSalesLine().get(i).setInventBatchId(selectedBatchList.get(j).getBatchNo());
//                            customerDataResBean.getSalesLine().get(i).setMRP(selectedBatchList.get(j).getMRP());
//                            customerDataResBean.getSalesLine().get(i).setPrice(selectedBatchList.get(j).getPrice());
//                            for (int k = 0; k < customerDataResBean.getPickPackReservation().size(); k++) {
//                                if (customerDataResBean.getPickPackReservation().get(k).getPickupItemId().equals(selectedBatchList.get(j).getItemID()) && customerDataResBean.getPickPackReservation().get(k).getPickupInventBatchId().equals(selectedBatchList.get(j).getBatchNo())) {
//                                    selectedBatchList.get(j).setREQQTY(customerDataResBean.getPickPackReservation().get(k).getPickupQty());
//                                }
//                            }
//                            customerDataResBean.getSalesLine().get(i).setQty(selectedBatchList.get(j).getREQQTY());
//                            customerDataResBean.getSalesLine().get(i).setSGSTPerc(selectedBatchList.get(j).getSGSTPerc());
//                            customerDataResBean.getSalesLine().get(i).setSGSTTaxCode(selectedBatchList.get(j).getSGSTTaxCode());
//                            customerDataResBean.getSalesLine().get(i).setTotalTax(selectedBatchList.get(j).getTotalTax());
//                            customerDataResBean.getSalesLine().get(i).setUnitPrice(selectedBatchList.get(j).getMRP());
//                        } else {
//                            SalesLineEntity salesLineEntityTemp = customerDataResBean.getSalesLine().get(i);
//                            salesLineEntityTemp.setCGSTPerc(selectedBatchList.get(j).getCGSTPerc());
//                            salesLineEntityTemp.setCGSTTaxCode(selectedBatchList.get(j).getCGSTTaxCode());
//                            salesLineEntityTemp.setExpiry(selectedBatchList.get(j).getExpDate());
//                            salesLineEntityTemp.setInventBatchId(selectedBatchList.get(j).getBatchNo());
//                            salesLineEntityTemp.setMRP(selectedBatchList.get(j).getMRP());
//                            salesLineEntityTemp.setPrice(selectedBatchList.get(j).getPrice());
//                            for (int k = 0; k < customerDataResBean.getPickPackReservation().size(); k++) {
//                                if (customerDataResBean.getPickPackReservation().get(k).getPickupItemId().equals(selectedBatchList.get(j).getItemID()) && customerDataResBean.getPickPackReservation().get(k).getPickupInventBatchId().equals(selectedBatchList.get(j).getBatchNo())) {
//                                    selectedBatchList.get(j).setREQQTY(customerDataResBean.getPickPackReservation().get(k).getPickupQty());
//                                }
//                            }
//                            salesLineEntityTemp.setQty(selectedBatchList.get(j).getREQQTY());
//                            salesLineEntityTemp.setSGSTPerc(selectedBatchList.get(j).getSGSTPerc());
//                            salesLineEntityTemp.setSGSTTaxCode(selectedBatchList.get(j).getSGSTTaxCode());
//                            salesLineEntityTemp.setTotalTax(selectedBatchList.get(j).getTotalTax());
//                            salesLineEntityTemp.setUnitPrice(selectedBatchList.get(j).getMRP());
//                            customerDataResBean.getSalesLine().add(salesLineEntityTemp);
//                        }
//                    }
//                }
//            }
//        }
//        mPresenter.onCheckBatchStock(customerDataResBean);
    }
}