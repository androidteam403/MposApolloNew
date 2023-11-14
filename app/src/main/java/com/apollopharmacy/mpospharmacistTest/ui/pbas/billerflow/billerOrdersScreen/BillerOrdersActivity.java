package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBillerOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.adapter.BillerFullfillmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class BillerOrdersActivity extends BaseFragment implements BillerOrdersMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {

    @Inject
    BillerOrdersMvpPresenter<BillerOrdersMvpView> mPresenter;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    ActivityBillerOrdersPBinding activityBillerOrdersBinding;
    private BillerFullfillmentAdapter billerFullfillmentAdapter;
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> omsHeaderList = new ArrayList<>();
    public static boolean isBillerActivity;

    private List<OMSTransactionHeaderResModel.OMSHeaderObj> selectedOmsHeaderList = new ArrayList<>();


    private static final int ORDERDETAILS_SCREEN_ACTIVITY = 108;
    public static boolean billerActivityScanner = false;

    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter, shippingTatFilterAdapter, billDateTatFilterAdapter;
    private boolean isShiipimentDateFiltered = false;
    // Main filters headers
    private List<FilterModel> customerTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterList = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderSourceFilterList = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterList = new ArrayList<>();
    private List<FilterModel> shippmentTatFilterList = new ArrayList<>();// new
    private List<FilterModel> billDateTatFilterList = new ArrayList<>();// new

    // Temp filters headers list
    private List<FilterModel> customerTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterListTemp = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderSourceFilterListTemp = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterListTemp = new ArrayList<>();
    private List<FilterModel> billDateTatFilterListTemp = new ArrayList<>();// new
    private List<FilterModel> shippmentTatFilterListTemp = new ArrayList<>();// new

    public static Intent getStartIntent(Context mContext) {
        Intent i = new Intent(mContext, BillerOrdersActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return i;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityBillerOrdersBinding = DataBindingUtil.inflate(inflater, R.layout.activity_biller_orders_p, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BillerOrdersActivity.this);
        return activityBillerOrdersBinding.getRoot();

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityBillerOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_biller_orders_p);
//        getActivityComponent().inject(this);
//        mPresenter.onAttach(BillerOrdersActivity.this);
//        setUp();
//    }

    @Override
    protected void setUp(View view) {
        hideKeyboard();
        activityBillerOrdersBinding.setScan(mPresenter);
//
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Biller Orders");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("");
        activityBillerOrdersBinding.terminalId.setText("Terminal ID : " + mPresenter.getTerminalId());
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.VISIBLE);
        pulltoRrefresh();
//                mPresenter.onRackApiCall();
        searchByFulfilmentId();
        mPresenter.fetchFulfilmentOrderList();

        activityBillerOrdersBinding.deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityBillerOrdersBinding.searchText.setText("");
                activityBillerOrdersBinding.search.setVisibility(View.VISIBLE);
                activityBillerOrdersBinding.deleteCancel.setVisibility(View.GONE);


            }
        });


    }

    private void pulltoRrefresh() {
//        activityBillerOrdersBinding.fullfilmentRecyclerPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                refreshData(); // your code
////                pullToRefresh.setRefreshing(false);
//                mPresenter.fetchFulfilmentOrderList();
//            }
//        });
    }

    @Override
    public void onclickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
        billerActivityScanner = true;
        new IntentIntegrator(getActivity()).setCaptureActivity(com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity.class).initiateScan();
        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    private void searchByFulfilmentId() {
        activityBillerOrdersBinding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    activityBillerOrdersBinding.search.setVisibility(View.GONE);
                    activityBillerOrdersBinding.deleteCancel.setVisibility(View.VISIBLE);
                    if (billerFullfillmentAdapter != null) {
                        billerFullfillmentAdapter.getFilter().filter(editable);

                    }
                } else if (activityBillerOrdersBinding.searchText.getText().toString().equals("")) {
                    if (billerFullfillmentAdapter != null) {
                        billerFullfillmentAdapter.getFilter().filter("");
                    }
                    activityBillerOrdersBinding.search.setVisibility(View.VISIBLE);
                    activityBillerOrdersBinding.deleteCancel.setVisibility(View.GONE);
                } else {
                    if (billerFullfillmentAdapter != null) {
                        billerFullfillmentAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isScanerBack) {
            isScanerBack = true;
            omsHeaderList.clear();
            mPresenter.fetchFulfilmentOrderList();
            activityBillerOrdersBinding.searchText.setText("");
        }
    }

    @Override
    public void onRightArrowClickedContinue(String refId) {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            for (OMSTransactionHeaderResModel.OMSHeaderObj omsHeaderObj : omsHeaderList) {
                if (omsHeaderObj.getREFNO().equals(refId)) {
                    isScanerBack = false;
                    Intent i = new Intent(getContext(), OrderDetailsScreenActivity.class);
                    i.putExtra("fullfillmentDetails", omsHeaderObj);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                    break;
                }
            }
        }
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            activityBillerOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
            activityBillerOrdersBinding.fullfilmentRecycler.setVisibility(View.VISIBLE);
//            activityBillerOrdersBinding.fullfilmentRecyclerPullToRefresh.setVisibility(View.VISIBLE);
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
        } else {
            activityBillerOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
            activityBillerOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
//            activityBillerOrdersBinding.fullfilmentRecyclerPullToRefresh.setVisibility(View.GONE);
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
        }
    }

    private boolean isScanerBack = true;

    @Override
    public void onSuccessRackApi(RacksDataResponse body) {
        racksDataResponse = body.getFullfillmentDetails();
        activityBillerOrdersBinding.scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isScanerBack = true;
                isBillerActivity = true;
                new IntentIntegrator(getActivity()).setCaptureActivity(com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity.class).initiateScan();
                getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        });
    }

    @Override
    public void onSucessfullFulfilmentIdList(OMSTransactionHeaderResModel omsHeader) {
//        if (activityBillerOrdersBinding.fullfilmentRecyclerPullToRefresh.isRefreshing()) {
//            activityBillerOrdersBinding.fullfilmentRecyclerPullToRefresh.setRefreshing(false);
//        }
        if (omsHeaderList != null) {
            omsHeaderList.clear();
            mPresenter.setTotalOmsHeaderList(omsHeaderList);
        }

        if (omsHeader != null && omsHeader.getOMSHeaderArr() != null && omsHeader.getOMSHeaderArr().size() > 0) {
//            for (int i = 0; i < omsHeader.getOMSHeaderArr().size(); i++) {
//                if (omsHeader.getOMSHeaderArr() != null && omsHeader.getOMSHeaderArr().get(i).getOrderPickup() && omsHeader.getOMSHeaderArr().get(i).getOrderPacked()) {
//                    omsHeaderList.add(omsHeader.getOMSHeaderArr().get(i));
//                }
//                PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
//
//
//            }
            omsHeaderList = omsHeader.getOMSHeaderArr();
            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");

            mPresenter.setTotalOmsHeaderList(omsHeaderList);
//        billerFullfillmentAdapter = new BillerFullfillmentAdapter(getContext(), omsHeaderList, this);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        activityBillerOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
//        activityBillerOrdersBinding.fullfilmentRecycler.setAdapter(billerFullfillmentAdapter);
            filterOrdersLists();
//        noOrderFound(omsHeaderList.size());
        } else {
            noOrderFound(0);
        }
    }

    private void temFiltersHeadersList() {
        //
        customerTypeFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : customerTypeFilterList) {
            FilterModel customerTypeModel = new FilterModel();
            customerTypeModel.setSelected(filterModel.isSelected());
            customerTypeModel.setName(filterModel.getName());
            customerTypeFilterListTemp.add(customerTypeModel);
        }
        //
        orderTypeFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : orderTypeFilterList) {
            FilterModel orderTypeModel = new FilterModel();
            orderTypeModel.setSelected(filterModel.isSelected());
            orderTypeModel.setName(filterModel.getName());
            orderTypeFilterListTemp.add(orderTypeModel);
        }
        //
        orderCategoryFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : orderCategoryFilterList) {
            FilterModel orderCategoryModel = new FilterModel();
            orderCategoryModel.setSelected(filterModel.isSelected());
            orderCategoryModel.setName(filterModel.getName());
            orderCategoryFilterListTemp.add(orderCategoryModel);
        }
        //
        paymentTypeFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : paymentTypeFilterList) {
            FilterModel paymentTypeModel = new FilterModel();
            paymentTypeModel.setSelected(filterModel.isSelected());
            paymentTypeModel.setName(filterModel.getName());
            paymentTypeFilterListTemp.add(paymentTypeModel);
        }
        //
        orderSourceFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : orderSourceFilterList) {
            FilterModel orderSourceModel = new FilterModel();
            orderSourceModel.setSelected(filterModel.isSelected());
            orderSourceModel.setName(filterModel.getName());
            orderSourceFilterListTemp.add(orderSourceModel);
        }
        //
        stockAvailabilityFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : stockAvailabilityFilterList) {
            FilterModel stockAvailabilityModel = new FilterModel();
            stockAvailabilityModel.setSelected(filterModel.isSelected());
            stockAvailabilityModel.setName(filterModel.getName());
            stockAvailabilityFilterListTemp.add(stockAvailabilityModel);
        }
        shippmentTatFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : shippmentTatFilterList) {
            FilterModel shippingTatModel = new FilterModel();
            shippingTatModel.setSelected(filterModel.isSelected());
            shippingTatModel.setName(filterModel.getName());
            shippmentTatFilterListTemp.add(shippingTatModel);
        }

        //
        billDateTatFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : billDateTatFilterList) {
            FilterModel billDateTatModel = new FilterModel();
            billDateTatModel.setSelected(filterModel.isSelected());
            billDateTatModel.setName(filterModel.getName());
            billDateTatFilterListTemp.add(billDateTatModel);
        }
    }

    @Override
    public void onClickFilterIcon() {
        if (mPresenter.getTotalOmsHeaderList() != null && mPresenter.getTotalOmsHeaderList().size() > 0) {
            temFiltersHeadersList();
            Dialog filterDialog = new Dialog(getContext(), R.style.fadeinandoutcustomDialog);
            DialogFilterPBinding dialogFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_filter_p, null, false);
            dialogFilterBinding.reverificationView.setVisibility(View.GONE);
            dialogFilterBinding.reverificationText.setVisibility(View.GONE);
            dialogFilterBinding.reverificationRecycler.setVisibility(View.GONE);
            filterDialog.setContentView(dialogFilterBinding.getRoot());
            filterDialog.setCancelable(false);
            filtersList(dialogFilterBinding);
            dialogFilterBinding.filterCloseIcon.setOnClickListener(view -> {
//            applyOrderFilters();

                this.customerTypeFilterList = customerTypeFilterListTemp;
                this.orderTypeFilterList = orderTypeFilterListTemp;
                this.orderCategoryFilterList = orderCategoryFilterListTemp;
                this.paymentTypeFilterList = paymentTypeFilterListTemp;
                this.orderSourceFilterList = orderSourceFilterListTemp;
                this.stockAvailabilityFilterList = stockAvailabilityFilterListTemp;
                this.shippmentTatFilterList = shippmentTatFilterListTemp;
                this.billDateTatFilterList = billDateTatFilterListTemp;

                filterDialog.dismiss();
                hideLoading();
            });
            dialogFilterBinding.applyFilters.setOnClickListener(view -> {
                showLoading();
                applyOrderFilters();
                filterDialog.dismiss();
                hideLoading();
            });
            dialogFilterBinding.clear.setOnClickListener(view -> {
                clearFilter();
                filterDialog.dismiss();
                applyOrderFilters();
            });
            filterDialog.show();
        } else {
            Toast.makeText(getContext(), "No Orders are available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickUnHold(OMSTransactionHeaderResModel.OMSHeaderObj omsHeaderObj) {
        Dialog dialog = new Dialog(getContext());// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialogCancelBinding.wraningIcon.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogCancelBinding.wraningIcon.setVisibility(View.VISIBLE);
        dialogCancelBinding.dialogMessage.setText("Do you want to on hold the order?");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            mPresenter.mposPickPackOrderReservationApiCall(omsHeaderObj);
            dialog.dismiss();
        });
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
            mPresenter.fetchFulfilmentOrderList();
        }
    }

    private void filterOrdersLists() {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            for (int i = 0; i < omsHeaderList.size(); i++) {

                boolean isCustomerTypeContain = false;
                boolean isOrderTypeContain = false;
                boolean isOrderCategoryContain = false;
                boolean isPaymentTypeContain = false;
                boolean isOrderSourceContain = false;
                boolean isStockAvailabilityContain = false;
                boolean isPickUpStatusContain = false;
                boolean isShippmentTatContain = false;
                boolean isBillDateTatContain = false;


                // customer type filter list.
                FilterModel filterModel = new FilterModel();

                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getCustomerType());
                filterModel.setSelected(false);
                for (int j = 0; j < customerTypeFilterList.size(); j++) {
                    if (customerTypeFilterList.get(j).getName().equalsIgnoreCase(filterModel.getName())) {
                        isCustomerTypeContain = true;
                    }
                }
                if (!isCustomerTypeContain) {
                    customerTypeFilterList.add(filterModel);
                }
//                filterModel.setName(omsHeaderList.get(i).getCustomerType());
//                filterModel.setSelected(false);
//                for (int j = 0; j < customerTypeFilterList.size(); j++) {
//                    if (customerTypeFilterList.get(j).getName().equals(filterModel.getName())) {
//                        isCustomerTypeContain = true;
//                    }
//                }
//                if (!isCustomerTypeContain) {
//                    customerTypeFilterList.add(filterModel);
//                }

                // order type filter list.
                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getOrderType());
                filterModel.setSelected(false);
                for (int j = 0; j < orderTypeFilterList.size(); j++) {
                    if (orderTypeFilterList.get(j).getName().equals(filterModel.getName())) {
                        isOrderTypeContain = true;
                    }
                }
                if (!isOrderTypeContain) {
                    orderTypeFilterList.add(filterModel);
                }

                // order category filter list.
                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getCategoryType());
                filterModel.setSelected(false);
                for (int j = 0; j < orderCategoryFilterList.size(); j++) {
                    if (orderCategoryFilterList.get(j).getName().equals(filterModel.getName())) {
                        isOrderCategoryContain = true;
                    }
                }
                if (!isOrderCategoryContain) {
                    orderCategoryFilterList.add(filterModel);
                }

                // payment type filter list.
                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getPaymentSource());
                filterModel.setSelected(false);
                for (int j = 0; j < paymentTypeFilterList.size(); j++) {
                    if (paymentTypeFilterList.get(j).getName().equals(filterModel.getName())) {
                        isPaymentTypeContain = true;
                    }
                }
                if (!isPaymentTypeContain) {
                    paymentTypeFilterList.add(filterModel);
                }

                // order source filter list.
                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getOrderSource());
                filterModel.setSelected(false);
                for (int j = 0; j < orderSourceFilterList.size(); j++) {
                    if (orderSourceFilterList.get(j).getName().equals(filterModel.getName())) {
                        isOrderSourceContain = true;
                    }
                }
                if (!isOrderSourceContain) {
                    orderSourceFilterList.add(filterModel);
                }
//pickupstatusfilter
//


                // stock availability filter list.
                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getStockStatus());

                filterModel.setSelected(false);
                for (int j = 0; j < stockAvailabilityFilterList.size(); j++) {
                    if (stockAvailabilityFilterList.get(j).getName().equals(filterModel.getName())) {
                        isStockAvailabilityContain = true;
                    }
                }
                if (!isStockAvailabilityContain) {
                    stockAvailabilityFilterList.add(filterModel);
                }

                // shipping tat filter list.
                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getShipmentTat());

                filterModel.setSelected(false);
                for (int j = 0; j < shippmentTatFilterList.size(); j++) {
                    if (shippmentTatFilterList.get(j).getName().equals(filterModel.getName())) {
                        isShippmentTatContain = true;
                    }
                }
                if (!isShippmentTatContain) {
                    shippmentTatFilterList.add(filterModel);
                }

                // Bill Date tat filter list.
                filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getBillingTat());

                filterModel.setSelected(false);
                for (int j = 0; j < billDateTatFilterList.size(); j++) {
                    if (billDateTatFilterList.get(j).getName().equals(filterModel.getName())) {
                        isBillDateTatContain = true;
                    }
                }
                if (!isBillDateTatContain) {
                    billDateTatFilterList.add(filterModel);
                }


            }
            applyOrderFilters();
        } else {
            noOrderFound(0);
        }
    }

    private void applyOrderFilters() {
        omsHeaderList.clear();

        // Customer type filter list.
        boolean isCustomerTypeFilter = false;
        for (FilterModel orderTypeFilter : customerTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isCustomerTypeFilter = true;
            }
        }
        if (isCustomerTypeFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> customerTypeOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                customerTypeOMSHeaderFilter = omsHeaderList;
            } else {
                customerTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel customerTypeFilter : customerTypeFilterList) {
                for (int i = 0; i < customerTypeOMSHeaderFilter.size(); i++) {
                    if (!customerTypeFilter.isSelected() && (customerTypeFilter.getName().equals(customerTypeOMSHeaderFilter.get(i).getCustomerType()))) {
                        customerTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = customerTypeOMSHeaderFilter;
        }

        // Order type filter list.
        boolean isorderTypeFilter = false;
        for (FilterModel orderTypeFilter : orderTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isorderTypeFilter = true;
            }
        }
        if (isorderTypeFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> orderTypeOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                orderTypeOMSHeaderFilter = omsHeaderList;
            } else {
                orderTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel orderTypeFilter : orderTypeFilterList) {
                for (int i = 0; i < orderTypeOMSHeaderFilter.size(); i++) {
                    if (!orderTypeFilter.isSelected() && (orderTypeFilter.getName().equals(orderTypeOMSHeaderFilter.get(i).getOrderType()))) {
                        orderTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = orderTypeOMSHeaderFilter;
        }

        // Order category filter list.
        boolean isOrderCategoryFilter = false;
        for (FilterModel orderTypeFilter : orderCategoryFilterList) {
            if (orderTypeFilter.isSelected()) {
                isOrderCategoryFilter = true;
            }
        }
        if (isOrderCategoryFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> orderCategoryOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                orderCategoryOMSHeaderFilter = omsHeaderList;
            } else {
                orderCategoryOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel orderCategoryFilter : orderCategoryFilterList) {
                for (int i = 0; i < orderCategoryOMSHeaderFilter.size(); i++) {
                    if (!orderCategoryFilter.isSelected() && (orderCategoryFilter.getName().equals(orderCategoryOMSHeaderFilter.get(i).getCategoryType()))) {
                        orderCategoryOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = orderCategoryOMSHeaderFilter;
        }

        // Payment type filter list.
        boolean isPaymentTypeFilter = false;
        for (FilterModel orderTypeFilter : paymentTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isPaymentTypeFilter = true;
            }
        }
        if (isPaymentTypeFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> paymentTypeOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                paymentTypeOMSHeaderFilter = omsHeaderList;
            } else {
                paymentTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel paymentTypeFilter : paymentTypeFilterList) {
                for (int i = 0; i < paymentTypeOMSHeaderFilter.size(); i++) {
                    if (!paymentTypeFilter.isSelected() && (paymentTypeFilter.getName().equals(paymentTypeOMSHeaderFilter.get(i).getPaymentSource()))) {
                        paymentTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = paymentTypeOMSHeaderFilter;
        }

        // Order source filter list.
        boolean isOrderSourceFilter = false;
        for (FilterModel orderTypeFilter : orderSourceFilterList) {
            if (orderTypeFilter.isSelected()) {
                isOrderSourceFilter = true;
            }
        }
        if (isOrderSourceFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> orderSourceOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                orderSourceOMSHeaderFilter = omsHeaderList;
            } else {
                orderSourceOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel orderSourceFilter : orderSourceFilterList) {
                for (int i = 0; i < orderSourceOMSHeaderFilter.size(); i++) {
                    if (!orderSourceFilter.isSelected() && (orderSourceFilter.getName().equals(orderSourceOMSHeaderFilter.get(i).getOrderSource()))) {
                        orderSourceOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = orderSourceOMSHeaderFilter;
        }

        // Stock availability filter list.
        boolean isStockAvailabilityFilter = false;
        for (FilterModel orderTypeFilter : stockAvailabilityFilterList) {
            if (orderTypeFilter.isSelected()) {
                isStockAvailabilityFilter = true;
            }
        }
        if (isStockAvailabilityFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> stockAvailabilityOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                stockAvailabilityOMSHeaderFilter = omsHeaderList;
            } else {
                stockAvailabilityOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel stockAvailabilityFilter : stockAvailabilityFilterList) {
                for (int i = 0; i < stockAvailabilityOMSHeaderFilter.size(); i++) {
                    if (!stockAvailabilityFilter.isSelected() && (stockAvailabilityFilter.getName().equals(stockAvailabilityOMSHeaderFilter.get(i).getStockStatus()))) {
                        stockAvailabilityOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = stockAvailabilityOMSHeaderFilter;
        }

        //Shippment tat filter list
        boolean isShippingTatFilter = false;
        for (FilterModel orderTypeFilter : shippmentTatFilterList) {
            if (orderTypeFilter.isSelected()) {
                isShippingTatFilter = true;
            }
        }
        if (isShippingTatFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> shippingTatOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                shippingTatOMSHeaderFilter = omsHeaderList;
            } else {
                shippingTatOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel shippingFilter : shippmentTatFilterList) {
                for (int i = 0; i < shippingTatOMSHeaderFilter.size(); i++) {
                    if (!shippingFilter.isSelected() && (shippingFilter.getName().equals(shippingTatOMSHeaderFilter.get(i).getShipmentTat()))) {
                        shippingTatOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = shippingTatOMSHeaderFilter;
//            for (TransactionHeaderResponse.OMSHeader omsHeader : stockAvailabilityOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(stockAvailabilityOMSHeaderFilter);
        }

        if (isShippingTatFilter) {
            isShiipimentDateFiltered = true;
        } else {
            isShiipimentDateFiltered = false;
        }
        boolean isBillDateTatFilter = false;
        for (FilterModel billDateTatFilter : billDateTatFilterList) {
            if (billDateTatFilter.isSelected()) {
                isBillDateTatFilter = true;
            }
        }
        if (isBillDateTatFilter) {
            List<OMSTransactionHeaderResModel.OMSHeaderObj> billDateTatOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                billDateTatOMSHeaderFilter = omsHeaderList;
            } else {
                billDateTatOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel billDateTatFilter : billDateTatFilterList) {
                for (int i = 0; i < billDateTatOMSHeaderFilter.size(); i++) {
                    if (!billDateTatFilter.isSelected() && (billDateTatFilter.getName().equals(billDateTatOMSHeaderFilter.get(i).getBillingTat()))) {
                        billDateTatOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = billDateTatOMSHeaderFilter;
        }


        if (!isStockAvailabilityFilter && !isorderTypeFilter && !isOrderCategoryFilter && !isPaymentTypeFilter && !isOrderSourceFilter && !isCustomerTypeFilter && !isShippingTatFilter && !isBillDateTatFilter) {
            omsHeaderList = mPresenter.getTotalOmsHeaderList();
        }
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                for (int j = 0; j < Objects.requireNonNull(omsHeaderList).size(); j++) {
                    if (selectedOmsHeaderList.get(i).getREFNO().equals(omsHeaderList.get(j).getREFNO())) {
                        omsHeaderList.get(j).setSelected(selectedOmsHeaderList.get(i).isSelected());
                    }
                }
            }
        }

        if (omsHeaderList != null && omsHeaderList.size() > 0) {


            if (isShiipimentDateFiltered) {

                Collections.sort(omsHeaderList, new Comparator<OMSTransactionHeaderResModel.OMSHeaderObj>() {
                    public int compare(OMSTransactionHeaderResModel.OMSHeaderObj o1, OMSTransactionHeaderResModel.OMSHeaderObj o2) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = dateFormat.parse(o1.getBillingTat());
                            date2 = dateFormat.parse(o2.getBillingTat());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        return date1.compareTo(date2);
                    }
                });


                Map<String, List<OMSTransactionHeaderResModel.OMSHeaderObj>> studlistGrouped = omsHeaderList.stream().collect(Collectors.groupingBy(w -> w.getShipmentTat()));
                omsHeaderList.clear();
                for (Map.Entry<String, List<OMSTransactionHeaderResModel.OMSHeaderObj>> entry : studlistGrouped.entrySet()) {
                    omsHeaderList.addAll(studlistGrouped.get(entry.getKey()));
                    System.out.println(entry.getKey());
                }
            }


            billerFullfillmentAdapter = new BillerFullfillmentAdapter(getContext(), omsHeaderList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            activityBillerOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            billerFullfillmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            activityBillerOrdersBinding.fullfilmentRecycler.setAdapter(billerFullfillmentAdapter);
            noOrderFound(omsHeaderList.size());
        } else {
            noOrderFound(0);
        }
        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
    }

    private void applyOrderFilterss() {
        omsHeaderList.clear();

        // Customer type filter list.
        List<OMSTransactionHeaderResModel.OMSHeaderObj> customerTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        for (FilterModel customerTypeFilter : customerTypeFilterList) {
            for (int i = 0; i < customerTypeOMSHeaderFilter.size(); i++) {
                if (!customerTypeFilter.isSelected() && (customerTypeFilter.getName().equals(customerTypeOMSHeaderFilter.get(i).getCustomerType()))) {
                    customerTypeOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(customerTypeOMSHeaderFilter);


        // Order type filter list.
        List<OMSTransactionHeaderResModel.OMSHeaderObj> orderTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        for (FilterModel orderTypeFilter : orderTypeFilterList) {
            for (int i = 0; i < orderTypeOMSHeaderFilter.size(); i++) {
                if (!orderTypeFilter.isSelected() && (orderTypeFilter.getName().equals(orderTypeOMSHeaderFilter.get(i).getOrderType()))) {
                    orderTypeOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (OMSTransactionHeaderResModel.OMSHeaderObj omsHeader : orderTypeOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getREFNO().equals(omsHeader.getREFNO())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(orderTypeOMSHeaderFilter);

        // Order category filter list.
        List<OMSTransactionHeaderResModel.OMSHeaderObj> orderCategoryOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        for (FilterModel orderCategoryFilter : orderCategoryFilterList) {
            for (int i = 0; i < orderCategoryOMSHeaderFilter.size(); i++) {
                if (!orderCategoryFilter.isSelected() && (orderCategoryFilter.getName().equals(orderCategoryOMSHeaderFilter.get(i).getCategoryType()))) {
                    orderCategoryOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (OMSTransactionHeaderResModel.OMSHeaderObj omsHeader : orderCategoryOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getREFNO().equals(omsHeader.getREFNO())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(orderCategoryOMSHeaderFilter);

        // Payment type filter list.
        List<OMSTransactionHeaderResModel.OMSHeaderObj> paymentTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        for (FilterModel paymentTypeFilter : paymentTypeFilterList) {
            for (int i = 0; i < paymentTypeOMSHeaderFilter.size(); i++) {
                if (!paymentTypeFilter.isSelected() && (paymentTypeFilter.getName().equals(paymentTypeOMSHeaderFilter.get(i).getPaymentSource()))) {
                    paymentTypeOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (OMSTransactionHeaderResModel.OMSHeaderObj omsHeader : paymentTypeOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getREFNO().equals(omsHeader.getREFNO())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(paymentTypeOMSHeaderFilter);

        // Order source filter list.
        List<OMSTransactionHeaderResModel.OMSHeaderObj> orderSourceOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        for (FilterModel orderSourceFilter : orderSourceFilterList) {
            for (int i = 0; i < orderSourceOMSHeaderFilter.size(); i++) {
                if (!orderSourceFilter.isSelected() && (orderSourceFilter.getName().equals(orderSourceOMSHeaderFilter.get(i).getOrderSource()))) {
                    orderSourceOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (OMSTransactionHeaderResModel.OMSHeaderObj omsHeader : orderSourceOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getREFNO().equals(omsHeader.getREFNO())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(orderSourceOMSHeaderFilter);

        // Stock availability filter list.
        List<OMSTransactionHeaderResModel.OMSHeaderObj> stockAvailabilityOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        for (FilterModel stockAvailabilityFilter : stockAvailabilityFilterList) {
            for (int i = 0; i < stockAvailabilityOMSHeaderFilter.size(); i++) {
                if (!stockAvailabilityFilter.isSelected() && (stockAvailabilityFilter.getName().equals(stockAvailabilityOMSHeaderFilter.get(i).getStockStatus()))) {
                    stockAvailabilityOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (OMSTransactionHeaderResModel.OMSHeaderObj omsHeader : stockAvailabilityOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getREFNO().equals(omsHeader.getREFNO())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(stockAvailabilityOMSHeaderFilter);
        //pickUpStatusFilter

        if (omsHeaderList != null && omsHeaderList.size() == 0) {
            omsHeaderList = mPresenter.getTotalOmsHeaderList();
        }

        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                for (int j = 0; j < Objects.requireNonNull(omsHeaderList).size(); j++) {
                    if (selectedOmsHeaderList.get(i).getREFNO().equals(omsHeaderList.get(j).getREFNO())) {
                        omsHeaderList.get(j).setSelected(selectedOmsHeaderList.get(i).isSelected());
                    }
                }
            }
        }
        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
        billerFullfillmentAdapter = new BillerFullfillmentAdapter(getContext(), omsHeaderList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        activityBillerOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        billerFullfillmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
        activityBillerOrdersBinding.fullfilmentRecycler.setAdapter(billerFullfillmentAdapter);
    }

    private void clearFilter() {

        for (int i = 0; i < customerTypeFilterList.size(); i++) {
            customerTypeFilterList.get(i).setSelected(false);
        }
        customerTypeFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < orderTypeFilterList.size(); i++) {
            orderTypeFilterList.get(i).setSelected(false);
        }
        orderTypeFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < orderCategoryFilterList.size(); i++) {
            orderCategoryFilterList.get(i).setSelected(false);
        }
        orderCategoryFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < paymentTypeFilterList.size(); i++) {
            paymentTypeFilterList.get(i).setSelected(false);
        }
        paymentTypeFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < orderSourceFilterList.size(); i++) {
            orderSourceFilterList.get(i).setSelected(false);
        }
        orderSourceFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < stockAvailabilityFilterList.size(); i++) {
            stockAvailabilityFilterList.get(i).setSelected(false);
        }
        stockAvailabilityFilterAdapter.notifyDataSetChanged();

        for (int i = 0; i < shippmentTatFilterList.size(); i++) {
            shippmentTatFilterList.get(i).setSelected(false);
        }
        shippingTatFilterAdapter.notifyDataSetChanged();
        isShiipimentDateFiltered = true;


        for (int i = 0; i < billDateTatFilterList.size(); i++) {
            billDateTatFilterList.get(i).setSelected(false);
        }
        billDateTatFilterAdapter.notifyDataSetChanged();
    }

    private void filtersList(DialogFilterPBinding dialogFilterBinding) {
//        dialogFilterBinding.dispatchCuttoffText.setVisibility(View.GONE);
//        dialogFilterBinding.dispatchCuttoffTimeFilter.setVisibility(View.GONE);
//        dialogFilterBinding.dispatchCuttoffView.setVisibility(View.GONE);
//
//        dialogFilterBinding.billdateTatText.setVisibility(View.GONE);
//        dialogFilterBinding.billDateFilter.setVisibility(View.GONE);


        customerTypeFilterAdapter = new FilterItemAdapter(getContext(), customerTypeFilterList);
        dialogFilterBinding.customerTypeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.customerTypeFilter.setAdapter(customerTypeFilterAdapter);

        orderTypeFilterAdapter = new FilterItemAdapter(getContext(), orderTypeFilterList);
        dialogFilterBinding.orderTypeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.orderTypeFilter.setAdapter(orderTypeFilterAdapter);

        orderCategoryFilterAdapter = new FilterItemAdapter(getContext(), orderCategoryFilterList);
        dialogFilterBinding.orderCategoryFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.orderCategoryFilter.setAdapter(orderCategoryFilterAdapter);

        paymentTypeFilterAdapter = new FilterItemAdapter(getContext(), paymentTypeFilterList);
        dialogFilterBinding.paymentTypeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.paymentTypeFilter.setAdapter(paymentTypeFilterAdapter);

        orderSourceFilterAdapter = new FilterItemAdapter(getContext(), orderSourceFilterList);
        dialogFilterBinding.orderSourceFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.orderSourceFilter.setAdapter(orderSourceFilterAdapter);

        stockAvailabilityFilterAdapter = new FilterItemAdapter(getContext(), stockAvailabilityFilterList);
        dialogFilterBinding.stockAvailableFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.stockAvailableFilter.setAdapter(stockAvailabilityFilterAdapter);

        shippingTatFilterAdapter = new FilterItemAdapter(getContext(), shippmentTatFilterList);
        dialogFilterBinding.dispatchCuttoffTimeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.dispatchCuttoffTimeFilter.setAdapter(shippingTatFilterAdapter);

        billDateTatFilterAdapter = new FilterItemAdapter(getContext(), billDateTatFilterList);
        dialogFilterBinding.billDateFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.billDateFilter.setAdapter(billDateTatFilterAdapter);

    }

    @Override
    public void onClickFilters() {
        onClickFilterIcon();
    }

    @Override
    public void onItemClick() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
//                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                activityBillerOrdersBinding.searchText.setText(Result.getContents());
                BillerOrdersActivity.isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClickStockAvailable(boolean isStockAvailableChecked) {

    }

    @Override
    public void onClicklabelSizeIcon() {

    }

    @Override
    public void onClickRefresh() {

    }

    @Override
    public void onClickUnHold() {

    }

    @Override
    public void onClickRefreshPickerPackerBiller() {
        mPresenter.fetchFulfilmentOrderList();
    }
}