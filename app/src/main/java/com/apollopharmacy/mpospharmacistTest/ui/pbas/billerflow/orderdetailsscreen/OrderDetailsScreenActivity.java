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
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.StockNotVailableDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter.OrderDetailsScreenAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
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
        activityOrderDetailsScreenBinding.fullfillmentId.setText(orderInfoItem.getREFNO());
        if (orderInfoItem.getOverallOrderStatus().equals("0")) {

            activityOrderDetailsScreenBinding.statusIcon.setVisibility(View.GONE);
        } else if (orderInfoItem.getOverallOrderStatus().equals("1")) {
            activityOrderDetailsScreenBinding.statusIcon.setRotation(0);

            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_circle_tick));

        } else if (orderInfoItem.getOverallOrderStatus().equals("2")) {
            activityOrderDetailsScreenBinding.statusIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.partialcirculargreeenorange));


        } else if (orderInfoItem.getOverallOrderStatus().equals("3")) {
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

                    orderDetailsScreenAdapter = new OrderDetailsScreenAdapter(this, customerDataResBean.getSalesLine(), customerDataResBean.getPickPackReservation());
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
            item = corporateList.get(position);
        }

        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().itemsArrayList.addAll(new ArrayList<>(saleslineentity));
        boolean is_omsorder = true;

        startActivityForResult(AddItemActivity.getStartIntent(getContext(), saleslineentity, customerEntity, orderInfoItem, customerDataResBean_pass, transactionIDResModel, is_omsorder, item, doctorentyty), ACTIVITY_EPRESCRIPTIONBILLING_DETAILS_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void LoadOmsOrderFailure(CustomerDataResBean response) {

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
        message = message + "Stock Partial Available \n Do you want Continue this bill";

        StockNotVailableDialog dialogView = new StockNotVailableDialog(this);
        dialogView.setTitle(message);
        dialogView.setPositiveLabel("Proceed");
        dialogView.setNegativeLabel("Cancel");

        dialogView.setPositiveListener(view -> {
            dialogView.dismiss();
            customerDataResBean = response;
            mPresenter.onLoadOmsOrder(customerDataResBean);
        });

        dialogView.setNegativeListener(v -> dialogView.dismiss());
        dialogView.show();
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

    @Override
    public void onClickProceedAction() {
        if (selectActionLayoutBinding.checkedGenerateBill.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < customerDataResBean.getSalesLine().size(); i++) {
                boolean isItemHaveMoreThanOneBatch = false;
                for (int j = 0; j < selectedBatchList.size(); j++) {
                    if (customerDataResBean.getSalesLine().get(i).getItemId().equals(selectedBatchList.get(j).getItemID())) {
                        if (!isItemHaveMoreThanOneBatch) {
                            isItemHaveMoreThanOneBatch = true;
                            customerDataResBean.getSalesLine().get(i).setCGSTPerc(selectedBatchList.get(j).getCGSTPerc());
                            customerDataResBean.getSalesLine().get(i).setCGSTTaxCode(selectedBatchList.get(j).getCGSTTaxCode());
                            customerDataResBean.getSalesLine().get(i).setExpiry(selectedBatchList.get(j).getExpDate());
                            customerDataResBean.getSalesLine().get(i).setInventBatchId(selectedBatchList.get(j).getBatchNo());
                            customerDataResBean.getSalesLine().get(i).setMRP(selectedBatchList.get(j).getMRP());
                            customerDataResBean.getSalesLine().get(i).setPrice(selectedBatchList.get(j).getPrice());
                            for (int k = 0; k < customerDataResBean.getPickPackReservation().size(); k++) {
                                if (customerDataResBean.getPickPackReservation().get(k).getPickupItemId().equals(selectedBatchList.get(j).getItemID()) && customerDataResBean.getPickPackReservation().get(k).getPickupInventBatchId().equals(selectedBatchList.get(j).getBatchNo())) {
                                    selectedBatchList.get(j).setREQQTY(customerDataResBean.getPickPackReservation().get(k).getPickupQty());
                                }
                            }
                            customerDataResBean.getSalesLine().get(i).setQty(selectedBatchList.get(j).getREQQTY());
                            customerDataResBean.getSalesLine().get(i).setSGSTPerc(selectedBatchList.get(j).getSGSTPerc());
                            customerDataResBean.getSalesLine().get(i).setSGSTTaxCode(selectedBatchList.get(j).getSGSTTaxCode());
                            customerDataResBean.getSalesLine().get(i).setTotalTax(selectedBatchList.get(i).getTotalTax());
                            customerDataResBean.getSalesLine().get(i).setUnitPrice(selectedBatchList.get(i).getMRP());
                        } else {
                            SalesLineEntity salesLineEntityTemp = customerDataResBean.getSalesLine().get(i);
                            salesLineEntityTemp.setCGSTPerc(selectedBatchList.get(j).getCGSTPerc());
                            salesLineEntityTemp.setCGSTTaxCode(selectedBatchList.get(j).getCGSTTaxCode());
                            salesLineEntityTemp.setExpiry(selectedBatchList.get(j).getExpDate());
                            salesLineEntityTemp.setInventBatchId(selectedBatchList.get(j).getBatchNo());
                            salesLineEntityTemp.setMRP(selectedBatchList.get(j).getMRP());
                            salesLineEntityTemp.setPrice(selectedBatchList.get(j).getPrice());
                            salesLineEntityTemp.setQty(selectedBatchList.get(j).getREQQTY());
                            salesLineEntityTemp.setSGSTPerc(selectedBatchList.get(j).getSGSTPerc());
                            salesLineEntityTemp.setSGSTTaxCode(selectedBatchList.get(j).getSGSTTaxCode());
                            salesLineEntityTemp.setTotalTax(selectedBatchList.get(i).getTotalTax());
                            salesLineEntityTemp.setUnitPrice(selectedBatchList.get(i).getMRP());
                            customerDataResBean.getSalesLine().add(i + 1, salesLineEntityTemp);
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
            Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
        }
    }
}