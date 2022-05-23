package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBillerOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.adapter.BillerFullfillmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.adapter.PickedUpOrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class BillerOrdersActivity extends BaseActivity implements BillerOrdersMvpView {

    @Inject
    BillerOrdersMvpPresenter<BillerOrdersMvpView> mPresenter;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    ActivityBillerOrdersPBinding activityBillerOrdersBinding;
    private List<BillerFullfillmentAdapter.FullfilmentModel> fullfilmentModelList;
    private BillerFullfillmentAdapter billerFullfillmentAdapter;
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    public static boolean isBillerActivity;

    public static Intent getStartIntent(Context mContext) {
        Intent i = new Intent(mContext, BillerOrdersActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBillerOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_biller_orders_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BillerOrdersActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityBillerOrdersBinding.setCallback(mPresenter);
//        mPresenter.onRackApiCall();
        mPresenter.fetchFulfilmentOrderList();
    }

    @Override
    public void onclickScanCode() {
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    @Override
    public void onRightArrowClickedContinue(int position) {
        if (racksDataResponse != null && racksDataResponse.size() > 0 && racksDataResponse.size() > position) {
            Intent i = new Intent(BillerOrdersActivity.this, OrderDetailsScreenActivity.class);
            i.putExtra("fullfillmentDetails", racksDataResponse.get(position));
//            startActivityForResult(i, 999);
            startActivity(i);
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        }
    }

    @Override
    public void onSuccessRackApi(RacksDataResponse body) {
        racksDataResponse = body.getFullfillmentDetails();


        activityBillerOrdersBinding.scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBillerActivity = true;
                new IntentIntegrator(BillerOrdersActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        });
    }

    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {
        for (int i = 0; i < omsHeader.getOMSHeader().size(); i++) {
            if (omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().get(i).getOrderPickup() && omsHeader.getOMSHeader().get(i).getOrderPacked() ) {
                omsHeaderList.add(omsHeader.getOMSHeader().get(i));
            }


            }

        billerFullfillmentAdapter = new BillerFullfillmentAdapter(this, omsHeaderList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityBillerOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        activityBillerOrdersBinding.fullfilmentRecycler.setAdapter(billerFullfillmentAdapter);

    }

    @Override
    public void onClickFilterIcon() {
        Dialog filterDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
        DialogFilterPBinding dialogFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_filter_p, null, false);
        filterDialog.setContentView(dialogFilterBinding.getRoot());
        filterDialog.setCancelable(false);
        dialogFilterBinding.filterCloseIcon.setOnClickListener(view -> filterDialog.dismiss());
        filterDialog.show();
    }

//    private void filterOrdersLists() {
//        if (omsHeaderList != null && omsHeaderList.size() > 0) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//
//                boolean isCustomerTypeContain = false;
//                boolean isOrderTypeContain = false;
//                boolean isOrderCategoryContain = false;
//                boolean isPaymentTypeContain = false;
//                boolean isOrderSourceContain = false;
//                boolean isStockAvailabilityContain = false;
//                boolean isPickUpStatusContain = false;
//
//
//                // customer type filter list.
//                FilterModel filterModel = new FilterModel();
//
//                filterModel = new FilterModel();
//                filterModel.setName(omsHeaderList.get(i).getCustomerType());
//                filterModel.setSelected(false);
//                for (int j = 0; j < customerTypeFilterList.size(); j++) {
//                    if (customerTypeFilterList.get(j).getName().equalsIgnoreCase(filterModel.getName())) {
//                        isCustomerTypeContain = true;
//                    }
//                }
//                if (!isCustomerTypeContain) {
//                    customerTypeFilterList.add(filterModel);
//                }
////                filterModel.setName(omsHeaderList.get(i).getCustomerType());
////                filterModel.setSelected(false);
////                for (int j = 0; j < customerTypeFilterList.size(); j++) {
////                    if (customerTypeFilterList.get(j).getName().equals(filterModel.getName())) {
////                        isCustomerTypeContain = true;
////                    }
////                }
////                if (!isCustomerTypeContain) {
////                    customerTypeFilterList.add(filterModel);
////                }
//
//                // order type filter list.
//                filterModel = new FilterModel();
//                filterModel.setName(omsHeaderList.get(i).getOrderType());
//                filterModel.setSelected(false);
//                for (int j = 0; j < orderTypeFilterList.size(); j++) {
//                    if (orderTypeFilterList.get(j).getName().equals(filterModel.getName())) {
//                        isOrderTypeContain = true;
//                    }
//                }
//                if (!isOrderTypeContain) {
//                    orderTypeFilterList.add(filterModel);
//                }
//
//                // order category filter list.
//                filterModel = new FilterModel();
//                filterModel.setName(omsHeaderList.get(i).getCategoryType());
//                filterModel.setSelected(false);
//                for (int j = 0; j < orderCategoryFilterList.size(); j++) {
//                    if (orderCategoryFilterList.get(j).getName().equals(filterModel.getName())) {
//                        isOrderCategoryContain = true;
//                    }
//                }
//                if (!isOrderCategoryContain) {
//                    orderCategoryFilterList.add(filterModel);
//                }
//
//                // payment type filter list.
//                filterModel = new FilterModel();
//                filterModel.setName(omsHeaderList.get(i).getPaymentSource());
//                filterModel.setSelected(false);
//                for (int j = 0; j < paymentTypeFilterList.size(); j++) {
//                    if (paymentTypeFilterList.get(j).getName().equals(filterModel.getName())) {
//                        isPaymentTypeContain = true;
//                    }
//                }
//                if (!isPaymentTypeContain) {
//                    paymentTypeFilterList.add(filterModel);
//                }
//
//                // order source filter list.
//                filterModel = new FilterModel();
//                filterModel.setName(omsHeaderList.get(i).getOrderSource());
//                filterModel.setSelected(false);
//                for (int j = 0; j < orderSourceFilterList.size(); j++) {
//                    if (orderSourceFilterList.get(j).getName().equals(filterModel.getName())) {
//                        isOrderSourceContain = true;
//                    }
//                }
//                if (!isOrderSourceContain) {
//                    orderSourceFilterList.add(filterModel);
//                }
////pickupstatusfilter
////
//
//
//                // stock availability filter list.
//                filterModel = new FilterModel();
//                filterModel.setName(omsHeaderList.get(i).getStockStatus());
//
//                filterModel.setSelected(false);
//                for (int j = 0; j < stockAvailabilityFilterList.size(); j++) {
//                    if (stockAvailabilityFilterList.get(j).getName().equals(filterModel.getName())) {
//                        isStockAvailabilityContain = true;
//                    }
//                }
//                if (!isStockAvailabilityContain) {
//                    stockAvailabilityFilterList.add(filterModel);
//                }
//            }
//        }
//    }
//    private void applyOrderFilters() {
//        omsHeaderList.clear();
//
//        // Customer type filter list.
//        List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
//        for (FilterModel customerTypeFilter : customerTypeFilterList) {
//            for (int i = 0; i < customerTypeOMSHeaderFilter.size(); i++) {
//                if (!customerTypeFilter.isSelected() && (customerTypeFilter.getName().equals(customerTypeOMSHeaderFilter.get(i).getCustomerType()))) {
//                    customerTypeOMSHeaderFilter.remove(i);
//                    i--;
//                }
//            }
//        }
//        omsHeaderList.addAll(customerTypeOMSHeaderFilter);
//
//
//        // Order type filter list.
//        List<TransactionHeaderResponse.OMSHeader> orderTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
//        for (FilterModel orderTypeFilter : orderTypeFilterList) {
//            for (int i = 0; i < orderTypeOMSHeaderFilter.size(); i++) {
//                if (!orderTypeFilter.isSelected() && (orderTypeFilter.getName().equals(orderTypeOMSHeaderFilter.get(i).getOrderType()))) {
//                    orderTypeOMSHeaderFilter.remove(i);
//                    i--;
//                }
//            }
//        }
//        for (TransactionHeaderResponse.OMSHeader omsHeader : orderTypeOMSHeaderFilter) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                    omsHeaderList.remove(i);
//                    i--;
//                }
//            }
//        }
//        omsHeaderList.addAll(orderTypeOMSHeaderFilter);
//
//        // Order category filter list.
//        List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
//        for (FilterModel orderCategoryFilter : orderCategoryFilterList) {
//            for (int i = 0; i < orderCategoryOMSHeaderFilter.size(); i++) {
//                if (!orderCategoryFilter.isSelected() && (orderCategoryFilter.getName().equals(orderCategoryOMSHeaderFilter.get(i).getCategoryType()))) {
//                    orderCategoryOMSHeaderFilter.remove(i);
//                    i--;
//                }
//            }
//        }
//        for (TransactionHeaderResponse.OMSHeader omsHeader : orderCategoryOMSHeaderFilter) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                    omsHeaderList.remove(i);
//                    i--;
//                }
//            }
//        }
//        omsHeaderList.addAll(orderCategoryOMSHeaderFilter);
//
//        // Payment type filter list.
//        List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
//        for (FilterModel paymentTypeFilter : paymentTypeFilterList) {
//            for (int i = 0; i < paymentTypeOMSHeaderFilter.size(); i++) {
//                if (!paymentTypeFilter.isSelected() && (paymentTypeFilter.getName().equals(paymentTypeOMSHeaderFilter.get(i).getPaymentSource()))) {
//                    paymentTypeOMSHeaderFilter.remove(i);
//                    i--;
//                }
//            }
//        }
//        for (TransactionHeaderResponse.OMSHeader omsHeader : paymentTypeOMSHeaderFilter) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                    omsHeaderList.remove(i);
//                    i--;
//                }
//            }
//        }
//        omsHeaderList.addAll(paymentTypeOMSHeaderFilter);
//
//        // Order source filter list.
//        List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
//        for (FilterModel orderSourceFilter : orderSourceFilterList) {
//            for (int i = 0; i < orderSourceOMSHeaderFilter.size(); i++) {
//                if (!orderSourceFilter.isSelected() && (orderSourceFilter.getName().equals(orderSourceOMSHeaderFilter.get(i).getOrderSource()))) {
//                    orderSourceOMSHeaderFilter.remove(i);
//                    i--;
//                }
//            }
//        }
//        for (TransactionHeaderResponse.OMSHeader omsHeader : orderSourceOMSHeaderFilter) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                    omsHeaderList.remove(i);
//                    i--;
//                }
//            }
//        }
//        omsHeaderList.addAll(orderSourceOMSHeaderFilter);
//
//        // Stock availability filter list.
//        List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
//        for (FilterModel stockAvailabilityFilter : stockAvailabilityFilterList) {
//            for (int i = 0; i < stockAvailabilityOMSHeaderFilter.size(); i++) {
//                if (!stockAvailabilityFilter.isSelected() && (stockAvailabilityFilter.getName().equals(stockAvailabilityOMSHeaderFilter.get(i).getStockStatus()))) {
//                    stockAvailabilityOMSHeaderFilter.remove(i);
//                    i--;
//                }
//            }
//        }
//        for (TransactionHeaderResponse.OMSHeader omsHeader : stockAvailabilityOMSHeaderFilter) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                    omsHeaderList.remove(i);
//                    i--;
//                }
//            }
//        }
//        omsHeaderList.addAll(stockAvailabilityOMSHeaderFilter);
//        //pickUpStatusFilter
//
//        if (omsHeaderList != null && omsHeaderList.size() == 0) {
//            omsHeaderList = mvpPresenter.getTotalOmsHeaderList();
//        }
//
//        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
//            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
//                for (int j = 0; j < Objects.requireNonNull(omsHeaderList).size(); j++) {
//                    if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(j).getRefno())) {
//                        omsHeaderList.get(j).setSelected(selectedOmsHeaderList.get(i).isSelected());
//                    }
//                }
//            }
//        }
//        activityPickedUpOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
//        pickedUpOrdersAdapter = new PickedUpOrdersAdapter(this, omsHeaderList, this);
//
//        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
//        activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
//        activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}