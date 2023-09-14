package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickedUpOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.adapter.PickedUpOrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FullfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
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

public class PickedUpOrdersActivity extends BaseFragment implements PickedUpOrdersMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {

    @Inject
    PickedUpOrdersMvpPresenter<PickedUpOrdersMvpView> mvpPresenter;
    private ActivityPickedUpOrdersPBinding activityPickedUpOrdersBinding;
    private PickedUpOrdersAdapter pickedUpOrdersAdapter;
    private FullfilmentAdapter fullfilmentAdapter;
    private static final int PICKUP_VERIFICATION_ACTIVITY = 108;
    List<RackAdapter.RackBoxModel.ProductData> productDataList;
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList;

    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = new ArrayList<>();


    public static boolean isPickedUpOrdersActivity = false;
    int getPos;
    int itemPos;

    // Main filters headers list
    private List<FilterModel> customerTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterList = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderSourceFilterList = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterList = new ArrayList<>();
    private List<FilterModel> reverificationList = new ArrayList<>();
    private List<FilterModel> shippmentTatFilterList = new ArrayList<>();// new
    private List<FilterModel> billDateTatFilterList = new ArrayList<>();// new

    // Temp filters headers list
    private List<FilterModel> customerTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterListTemp = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderSourceFilterListTemp = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterListTemp = new ArrayList<>();
    private List<FilterModel> reverificationListTemp = new ArrayList<>();
    private List<FilterModel> billDateTatFilterListTemp = new ArrayList<>();// new
    private List<FilterModel> shippmentTatFilterListTemp = new ArrayList<>();// new

    //
    private int startIndex = 0;
    private int endIndex = 100;
    int lastIndex = 0;
    private static List<TransactionHeaderResponse.OMSHeader> omsHeaderListFileredStaticList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotal = new ArrayList<>();
    //
    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter, reverificationAdapter, shippingTatFilterAdapter, billDateTatFilterAdapter;
    private boolean isShiipimentDateFiltered = false;

    public static Intent getStartActivity(Context mContext) {
        Intent intent = new Intent(mContext, PickedUpOrdersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityPickedUpOrdersBinding = DataBindingUtil.inflate(inflater, R.layout.activity_picked_up_orders_p, container, false);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(PickedUpOrdersActivity.this);
        return activityPickedUpOrdersBinding.getRoot();

    }
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityPickedUpOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_picked_up_orders_p);
//        getActivityComponent().inject(this);
//        mvpPresenter.onAttach(PickedUpOrdersActivity.this);
//        setUp();
//    }

    @Override
    protected void setUp(View view) {
        hideKeyboard();
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Picked Orders");
        PickerNavigationActivity.mInstance.setStock("");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("");
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.VISIBLE);

        activityPickedUpOrdersBinding.terminalId.setText("Terminal ID : " + mvpPresenter.getTerminalId());
//        activityPickedUpOrdersBinding.setCallback(mvpPresenter);
//        mvpPresenter.fetchFulfilmentOrderList();
        pulltoRrefresh();
        searchByFulfilmentId();
        activityPickedUpOrdersBinding.setFilter(mvpPresenter);
        activityPickedUpOrdersBinding.setCallback(mvpPresenter);

        activityPickedUpOrdersBinding.deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityPickedUpOrdersBinding.searchText.setText("");
                activityPickedUpOrdersBinding.search.setVisibility(View.VISIBLE);
                activityPickedUpOrdersBinding.deleteCancel.setVisibility(View.GONE);
//                recyclerView();

            }
        });

//        if (mvpPresenter.getFullFillmentList() != null) {
//            activityPickedUpOrdersBinding.zeropicked.setVisibility(View.GONE);
//            activityPickedUpOrdersBinding.headerOrdersCount.setText("Total " + String.valueOf(mvpPresenter.getFullFillmentList().size()) + " Orders");
//
//            pickedUpOrdersAdapter = new PickedUpOrdersAdapter(this, mvpPresenter.getFullFillmentList(), this, mvpPresenter.getListOfListFullFillmentList(), false);
//            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
//        } else {
//            activityPickedUpOrdersBinding.zeropicked.setVisibility(View.VISIBLE);
//        }


        activityPickedUpOrdersBinding.scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();

            }
        });
    }

    private void pulltoRrefresh() {
//        activityPickedUpOrdersBinding.fullfilmentRecyclerPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                refreshData(); // your code
////                pullToRefresh.setRefreshing(false);
//                mvpPresenter.fetchFulfilmentOrderList();
//            }
//        });
    }

    private void searchByFulfilmentId() {
        activityPickedUpOrdersBinding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String charString = editable.toString();
                if (charString.isEmpty()) {
//                    omsHeaderListTotal = mPresenter.getGlobalTotalOmsHeaderList();
                    omsHeaderListFileredStaticList = mvpPresenter.getGlobalTotalOmsHeaderList();

                    startIndex = 0;
                    TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
                    omsHeader.setOMSHeader(omsHeaderListFileredStaticList);
                    onSucessfullFulfilmentIdList(omsHeader);
                } else {
                    omsHeaderListFileredStaticList = mvpPresenter.getGlobalTotalOmsHeaderList();

                    List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotalFilterTemp = new ArrayList<>();
                    for (TransactionHeaderResponse.OMSHeader row : omsHeaderListFileredStaticList) {
                        if (!omsHeaderListTotalFilterTemp.contains(row) && (row.getRefno().toLowerCase().contains(charString.toLowerCase()) || row.getOverallOrderStatus().toLowerCase().contains(charString.toLowerCase()))) {
                            omsHeaderListTotalFilterTemp.add(row);
                        }
                    }
//                    List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotalFilteredTemp = new ArrayList<>();
//                    omsHeaderListTotal = omsHeaderListTotalFilterTemp;
                    startIndex = 0;
                    TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
                    omsHeader.setOMSHeader(omsHeaderListTotalFilterTemp);
                    onSucessfullFulfilmentIdList(omsHeader);
                }


//                if (editable.length() >= 2) {
//                    activityPickedUpOrdersBinding.search.setVisibility(View.GONE);
//                    activityPickedUpOrdersBinding.deleteCancel.setVisibility(View.VISIBLE);
//                    if (pickedUpOrdersAdapter != null) {
//
//                        pickedUpOrdersAdapter.getFilter().filter(editable);
//                    }
//
//                } else if (activityPickedUpOrdersBinding.searchText.getText().toString().equals("")) {
//                    if (pickedUpOrdersAdapter != null) {
//                        pickedUpOrdersAdapter.getFilter().filter("");
//                    }
//                    activityPickedUpOrdersBinding.search.setVisibility(View.VISIBLE);
//                    activityPickedUpOrdersBinding.deleteCancel.setVisibility(View.GONE);
//                } else {
//                    if (pickedUpOrdersAdapter != null) {
//                        pickedUpOrdersAdapter.getFilter().filter("");
//                    }
//                }
            }
        });
    }

    @Override
    public void startPickUp() {

    }

    private boolean removeItsStatis;
    private boolean isScannerBack;

    @Override
    public void onClickScanCode() {
//        Intent intent = new Intent(PickedUpOrdersActivity.this, ScannerActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        isScannerBack = true;
        BillerOrdersActivity.isBillerActivity = true;

        isPickedUpOrdersActivity = true;

//        Intent i = new Intent(PickedUpOrdersActivity.this, ScannerActivity.class).initiateScan();
        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
//        IntentIntegrator intentIntegrator = new IntentIntegrator(PickedUpOrdersActivity.this);
//        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
//        intentIntegrator.setBeepEnabled(false);
//        intentIntegrator.setCameraId(0);
//
//        intentIntegrator.setPrompt("Scan QR/ BarCode of the Box");
//        intentIntegrator.setBarcodeImageEnabled(false);
//        intentIntegrator.initiateScan();

    }


    @Override
    public void onItmClick(int position, TransactionHeaderResponse.OMSHeader omsHeader) {
        startActivityForResult(PickUpVerificationActivity.getStartActivity(getContext(), omsHeader), PICKUP_VERIFICATION_ACTIVITY);

        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void noOrderFound(int count) {
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListPick = mvpPresenter.getGlobalTotalOmsHeaderList();
        if (omsHeaderListPick != null) {
            List<TransactionHeaderResponse.OMSHeader> omsHeaderListPicks = omsHeaderListPick;
//                    omsHeaderListPick.stream()
//                            .filter(e -> e.getOrderPickup() && !e.getOrderPacked())
//                            .collect(Collectors.toList());
            if (count > 0) {
                activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
                activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.VISIBLE);
//            activityPickedUpOrdersBinding.fullfilmentRecyclerPullToRefresh.setVisibility(View.VISIBLE);
//            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderListFileredStaticList.size() + "/" + omsHeaderListPicks.size() + " orders");
                PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + omsHeaderListPicks.size() + " orders");

//            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            } else {
                activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
                activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
//            activityPickedUpOrdersBinding.fullfilmentRecyclerPullToRefresh.setVisibility(View.GONE);
//            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderListFileredStaticList.size() + "/" + omsHeaderListPicks.size() + " orders");
                PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + omsHeaderListPicks.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            }
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

        //
        reverificationListTemp = new ArrayList<>();
        for (FilterModel filterModel : reverificationList) {
            FilterModel stockAvailabilityModel = new FilterModel();
            stockAvailabilityModel.setSelected(filterModel.isSelected());
            stockAvailabilityModel.setName(filterModel.getName());
            reverificationListTemp.add(stockAvailabilityModel);
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
        activityPickedUpOrdersBinding.searchText.setText("");
        if (mvpPresenter.getGlobalTotalOmsHeaderList() != null && mvpPresenter.getGlobalTotalOmsHeaderList().size() > 0) {
            temFiltersHeadersList();
            Dialog filterDialog = new Dialog(getContext(), R.style.fadeinandoutcustomDialog);
            DialogFilterPBinding dialogFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_filter_p, null, false);
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
                this.reverificationList = reverificationListTemp;
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
            }
            // reverification filter list.
            boolean isReverificationContain = false;
            FilterModel filterModel = new FilterModel();
            filterModel.setName("Reverification");
            filterModel.setSelected(false);
            for (int j = 0; j < reverificationList.size(); j++) {
                if (reverificationList.get(j).getName().equals(filterModel.getName())) {
                    isReverificationContain = true;
                }
            }
            if (!isReverificationContain) {
                reverificationList.add(filterModel);
            }
            applyOrderFilters();
        } else {
            noOrderFound(0);
        }
    }

    private void applyOrderFilters() {
        omsHeaderListTotal.clear();

        // Customer type filter list.
        boolean isCustomerTypeFilter = false;
        for (FilterModel orderTypeFilter : customerTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isCustomerTypeFilter = true;
            }
        }
        if (isCustomerTypeFilter) {
            List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                customerTypeOMSHeaderFilter = omsHeaderListTotal;
            } else {
                customerTypeOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel customerTypeFilter : customerTypeFilterList) {
                for (int i = 0; i < customerTypeOMSHeaderFilter.size(); i++) {
                    if (!customerTypeFilter.isSelected() && (customerTypeFilter.getName().equals(customerTypeOMSHeaderFilter.get(i).getCustomerType()))) {
                        customerTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = customerTypeOMSHeaderFilter;
        }

        // Order type filter list.
        boolean isorderTypeFilter = false;
        for (FilterModel orderTypeFilter : orderTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isorderTypeFilter = true;
            }
        }
        if (isorderTypeFilter) {
            List<TransactionHeaderResponse.OMSHeader> orderTypeOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                orderTypeOMSHeaderFilter = omsHeaderListTotal;
            } else {
                orderTypeOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel orderTypeFilter : orderTypeFilterList) {
                for (int i = 0; i < orderTypeOMSHeaderFilter.size(); i++) {
                    if (!orderTypeFilter.isSelected() && (orderTypeFilter.getName().equals(orderTypeOMSHeaderFilter.get(i).getOrderType()))) {
                        orderTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = orderTypeOMSHeaderFilter;
        }

        // Order category filter list.
        boolean isOrderCategoryFilter = false;
        for (FilterModel orderTypeFilter : orderCategoryFilterList) {
            if (orderTypeFilter.isSelected()) {
                isOrderCategoryFilter = true;
            }
        }
        if (isOrderCategoryFilter) {
            List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                orderCategoryOMSHeaderFilter = omsHeaderListTotal;
            } else {
                orderCategoryOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel orderCategoryFilter : orderCategoryFilterList) {
                for (int i = 0; i < orderCategoryOMSHeaderFilter.size(); i++) {
                    if (!orderCategoryFilter.isSelected() && (orderCategoryFilter.getName().equals(orderCategoryOMSHeaderFilter.get(i).getCategoryType()))) {
                        orderCategoryOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = orderCategoryOMSHeaderFilter;
        }

        // Payment type filter list.
        boolean isPaymentTypeFilter = false;
        for (FilterModel orderTypeFilter : paymentTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isPaymentTypeFilter = true;
            }
        }
        if (isPaymentTypeFilter) {
            List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                paymentTypeOMSHeaderFilter = omsHeaderListTotal;
            } else {
                paymentTypeOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel paymentTypeFilter : paymentTypeFilterList) {
                for (int i = 0; i < paymentTypeOMSHeaderFilter.size(); i++) {
                    if (!paymentTypeFilter.isSelected() && (paymentTypeFilter.getName().equals(paymentTypeOMSHeaderFilter.get(i).getPaymentSource()))) {
                        paymentTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = paymentTypeOMSHeaderFilter;
        }

        // Order source filter list.
        boolean isOrderSourceFilter = false;
        for (FilterModel orderTypeFilter : orderSourceFilterList) {
            if (orderTypeFilter.isSelected()) {
                isOrderSourceFilter = true;
            }
        }
        if (isOrderSourceFilter) {
            List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                orderSourceOMSHeaderFilter = omsHeaderListTotal;
            } else {
                orderSourceOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel orderSourceFilter : orderSourceFilterList) {
                for (int i = 0; i < orderSourceOMSHeaderFilter.size(); i++) {
                    if (!orderSourceFilter.isSelected() && (orderSourceFilter.getName().equals(orderSourceOMSHeaderFilter.get(i).getOrderSource()))) {
                        orderSourceOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = orderSourceOMSHeaderFilter;
        }

        // Stock availability filter list.
        boolean isStockAvailabilityFilter = false;
        for (FilterModel orderTypeFilter : stockAvailabilityFilterList) {
            if (orderTypeFilter.isSelected()) {
                isStockAvailabilityFilter = true;
            }
        }
        if (isStockAvailabilityFilter) {
            List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                stockAvailabilityOMSHeaderFilter = omsHeaderList;
            } else {
                stockAvailabilityOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel stockAvailabilityFilter : stockAvailabilityFilterList) {
                for (int i = 0; i < stockAvailabilityOMSHeaderFilter.size(); i++) {
                    if (!stockAvailabilityFilter.isSelected() && (stockAvailabilityFilter.getName().equals(stockAvailabilityOMSHeaderFilter.get(i).getStockStatus()))) {
                        stockAvailabilityOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = stockAvailabilityOMSHeaderFilter;
        }

        // Reverification filter list.

        boolean isReverificationFilter = false;
        for (FilterModel orderTypeFilter : reverificationList) {
            if (orderTypeFilter.isSelected()) {
                isReverificationFilter = true;
            }
        }
        if (isReverificationFilter) {
            List<TransactionHeaderResponse.OMSHeader> reverificationOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                reverificationOMSHeaderFilter = omsHeaderListTotal;
            } else {
                reverificationOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel reverificationFilter : reverificationList) {
                for (int i = 0; i < reverificationOMSHeaderFilter.size(); i++) {
                    if (!reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() == 2) {
                        reverificationOMSHeaderFilter.remove(i);
                        i--;
                    } else if (reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() != 2) {
                        reverificationOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = reverificationOMSHeaderFilter;
//            for (TransactionHeaderResponse.OMSHeader omsHeader : orderTypeOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(orderTypeOMSHeaderFilter);
        }
        //Shippment tat filter list
        boolean isShippingTatFilter = false;
        for (FilterModel orderTypeFilter : shippmentTatFilterList) {
            if (orderTypeFilter.isSelected()) {
                isShippingTatFilter = true;
            }
        }
        if (isShippingTatFilter) {
            List<TransactionHeaderResponse.OMSHeader> shippingTatOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                shippingTatOMSHeaderFilter = omsHeaderListTotal;
            } else {
                shippingTatOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel shippingFilter : shippmentTatFilterList) {
                for (int i = 0; i < shippingTatOMSHeaderFilter.size(); i++) {
                    if (!shippingFilter.isSelected() && (shippingFilter.getName().equals(shippingTatOMSHeaderFilter.get(i).getShipmentTat()))) {
                        shippingTatOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = shippingTatOMSHeaderFilter;
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
            List<TransactionHeaderResponse.OMSHeader> billDateTatOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                billDateTatOMSHeaderFilter = omsHeaderListTotal;
            } else {
                billDateTatOMSHeaderFilter = mvpPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel billDateTatFilter : billDateTatFilterList) {
                for (int i = 0; i < billDateTatOMSHeaderFilter.size(); i++) {
                    if (!billDateTatFilter.isSelected() && (billDateTatFilter.getName().equals(billDateTatOMSHeaderFilter.get(i).getBillingTat()))) {
                        billDateTatOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = billDateTatOMSHeaderFilter;
        }
        if (!isStockAvailabilityFilter && !isorderTypeFilter && !isOrderCategoryFilter && !isPaymentTypeFilter && !isOrderSourceFilter && !isCustomerTypeFilter && !isReverificationFilter && !isShippingTatFilter && !isBillDateTatFilter) {
            omsHeaderListTotal = mvpPresenter.getGlobalTotalOmsHeaderList();
        }
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                for (int j = 0; j < Objects.requireNonNull(omsHeaderListTotal).size(); j++) {
                    if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderListTotal.get(j).getRefno())) {
                        omsHeaderListTotal.get(j).setSelected(selectedOmsHeaderList.get(i).isSelected());
                    }
                }
            }
        }

        if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
            List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotals = omsHeaderListTotal;
//                    omsHeaderListTotal.stream().filter(e -> e.getOrderPickup() && !e.getOrderPacked()).collect(Collectors.toList());
            omsHeaderListFileredStaticList = omsHeaderListTotals;
            startIndex = 0;
            TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
            omsHeader.setOMSHeader(omsHeaderListTotal);
            onSucessfullFulfilmentIdList(omsHeader);


//            pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
//            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
//            noOrderFound(omsHeaderList.size());
        } else {
            omsHeaderListFileredStaticList.clear();
            noOrderFound(0);
        }

//        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
//        activityPickedUpOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
    }

    private void applyOrderFiltersTemp() {
        omsHeaderList.clear();

        // Customer type filter list.
        boolean isCustomerTypeFilter = false;
        for (FilterModel orderTypeFilter : customerTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isCustomerTypeFilter = true;
            }
        }
        if (isCustomerTypeFilter) {
            List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                customerTypeOMSHeaderFilter = omsHeaderList;
            } else {
                customerTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
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
            List<TransactionHeaderResponse.OMSHeader> orderTypeOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                orderTypeOMSHeaderFilter = omsHeaderList;
            } else {
                orderTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
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
            List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                orderCategoryOMSHeaderFilter = omsHeaderList;
            } else {
                orderCategoryOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
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
            List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                paymentTypeOMSHeaderFilter = omsHeaderList;
            } else {
                paymentTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
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
            List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                orderSourceOMSHeaderFilter = omsHeaderList;
            } else {
                orderSourceOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
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
            List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                stockAvailabilityOMSHeaderFilter = omsHeaderList;
            } else {
                stockAvailabilityOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
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

        // Reverification filter list.

        boolean isReverificationFilter = false;
        for (FilterModel orderTypeFilter : reverificationList) {
            if (orderTypeFilter.isSelected()) {
                isReverificationFilter = true;
            }
        }
        if (isReverificationFilter) {
            List<TransactionHeaderResponse.OMSHeader> reverificationOMSHeaderFilter = null;
            if (omsHeaderList != null && omsHeaderList.size() > 0) {
                reverificationOMSHeaderFilter = omsHeaderList;
            } else {
                reverificationOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel reverificationFilter : reverificationList) {
                for (int i = 0; i < reverificationOMSHeaderFilter.size(); i++) {
                    if (!reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() == 2) {
                        reverificationOMSHeaderFilter.remove(i);
                        i--;
                    } else if (reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() != 2) {
                        reverificationOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = reverificationOMSHeaderFilter;
//            for (TransactionHeaderResponse.OMSHeader omsHeader : orderTypeOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(orderTypeOMSHeaderFilter);
        }

        if (!isStockAvailabilityFilter && !isorderTypeFilter && !isOrderCategoryFilter && !isPaymentTypeFilter && !isOrderSourceFilter && !isCustomerTypeFilter && !isReverificationFilter) {
            omsHeaderList = mvpPresenter.getTotalOmsHeaderList();
        }
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                for (int j = 0; j < Objects.requireNonNull(omsHeaderList).size(); j++) {
                    if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(j).getRefno())) {
                        omsHeaderList.get(j).setSelected(selectedOmsHeaderList.get(i).isSelected());
                    }
                }
            }
        }

        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
            pickedUpOrdersAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
            activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
            activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
            noOrderFound(omsHeaderList.size());
        } else {
            noOrderFound(0);
        }

//        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
//        activityPickedUpOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
    }


    private void applyOrderFilterss() {
        omsHeaderList.clear();

        // Customer type filter list.
        List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
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
        List<TransactionHeaderResponse.OMSHeader> orderTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
        for (FilterModel orderTypeFilter : orderTypeFilterList) {
            for (int i = 0; i < orderTypeOMSHeaderFilter.size(); i++) {
                if (!orderTypeFilter.isSelected() && (orderTypeFilter.getName().equals(orderTypeOMSHeaderFilter.get(i).getOrderType()))) {
                    orderTypeOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (TransactionHeaderResponse.OMSHeader omsHeader : orderTypeOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(orderTypeOMSHeaderFilter);

        // Order category filter list.
        List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
        for (FilterModel orderCategoryFilter : orderCategoryFilterList) {
            for (int i = 0; i < orderCategoryOMSHeaderFilter.size(); i++) {
                if (!orderCategoryFilter.isSelected() && (orderCategoryFilter.getName().equals(orderCategoryOMSHeaderFilter.get(i).getCategoryType()))) {
                    orderCategoryOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (TransactionHeaderResponse.OMSHeader omsHeader : orderCategoryOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(orderCategoryOMSHeaderFilter);

        // Payment type filter list.
        List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
        for (FilterModel paymentTypeFilter : paymentTypeFilterList) {
            for (int i = 0; i < paymentTypeOMSHeaderFilter.size(); i++) {
                if (!paymentTypeFilter.isSelected() && (paymentTypeFilter.getName().equals(paymentTypeOMSHeaderFilter.get(i).getPaymentSource()))) {
                    paymentTypeOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (TransactionHeaderResponse.OMSHeader omsHeader : paymentTypeOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(paymentTypeOMSHeaderFilter);

        // Order source filter list.
        List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
        for (FilterModel orderSourceFilter : orderSourceFilterList) {
            for (int i = 0; i < orderSourceOMSHeaderFilter.size(); i++) {
                if (!orderSourceFilter.isSelected() && (orderSourceFilter.getName().equals(orderSourceOMSHeaderFilter.get(i).getOrderSource()))) {
                    orderSourceOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (TransactionHeaderResponse.OMSHeader omsHeader : orderSourceOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(orderSourceOMSHeaderFilter);

        // Stock availability filter list.
        List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = mvpPresenter.getTotalOmsHeaderList();
        for (FilterModel stockAvailabilityFilter : stockAvailabilityFilterList) {
            for (int i = 0; i < stockAvailabilityOMSHeaderFilter.size(); i++) {
                if (!stockAvailabilityFilter.isSelected() && (stockAvailabilityFilter.getName().equals(stockAvailabilityOMSHeaderFilter.get(i).getStockStatus()))) {
                    stockAvailabilityOMSHeaderFilter.remove(i);
                    i--;
                }
            }
        }
        for (TransactionHeaderResponse.OMSHeader omsHeader : stockAvailabilityOMSHeaderFilter) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }
        omsHeaderList.addAll(stockAvailabilityOMSHeaderFilter);
        //pickUpStatusFilter

        if (omsHeaderList != null && omsHeaderList.size() == 0) {
            omsHeaderList = mvpPresenter.getTotalOmsHeaderList();
        }

        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                for (int j = 0; j < Objects.requireNonNull(omsHeaderList).size(); j++) {
                    if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(j).getRefno())) {
                        omsHeaderList.get(j).setSelected(selectedOmsHeaderList.get(i).isSelected());
                    }
                }
            }
        }
//        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
        activityPickedUpOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
        pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
        pickedUpOrdersAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
        activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
        activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
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
        for (int i = 0; i < reverificationList.size(); i++) {
            reverificationList.get(i).setSelected(false);
        }
        reverificationAdapter.notifyDataSetChanged();

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

        reverificationAdapter = new FilterItemAdapter(getContext(), reverificationList);
        dialogFilterBinding.reverificationRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.reverificationRecycler.setAdapter(reverificationAdapter);

        shippingTatFilterAdapter = new FilterItemAdapter(getContext(), shippmentTatFilterList);
        dialogFilterBinding.dispatchCuttoffTimeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.dispatchCuttoffTimeFilter.setAdapter(shippingTatFilterAdapter);

        billDateTatFilterAdapter = new FilterItemAdapter(getContext(), billDateTatFilterList);
        dialogFilterBinding.billDateFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.billDateFilter.setAdapter(billDateTatFilterAdapter);
    }

    @Override
    public void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isScannerBack) {
            omsHeaderList.clear();
            mvpPresenter.fetchFulfilmentOrderList();
            activityPickedUpOrdersBinding.searchText.setText("");
        } else {
            isScannerBack = false;
        }
    }

    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {

        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
            omsHeaderListTotal = omsHeader.getOMSHeader();
//            omsHeader.getOMSHeader().stream().filter(e -> e.getOrderPickup() && !e.getOrderPacked()).collect(Collectors.toList());
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() >= 5000) {
                startIndex = 0;
                endIndex = 5000;
            } else {
                endIndex = omsHeaderListTotal.size();
            }
            activityPickedUpOrdersBinding.setIsNaxtPage(endIndex != omsHeaderListTotal.size());
            activityPickedUpOrdersBinding.setIsPrevtPage(startIndex != 0);

            List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
            omsHeader.setOMSHeader(myLastPosts);
            onSucessfullFulfilmentIdListText(omsHeader);
        } else {
            noOrderFound(0);
        }


//        this.customerTypeFilterList.clear();
//        this.customerTypeFilterListTemp.clear();
//        this.orderTypeFilterList.clear();
//        this.orderTypeFilterListTemp.clear();
//        this.orderCategoryFilterList.clear();
//        this.orderCategoryFilterListTemp.clear();
//        this.paymentTypeFilterList.clear();
//        this.paymentTypeFilterListTemp.clear();
//        this.orderSourceFilterList.clear();
//        this.orderSourceFilterListTemp.clear();
//        this.stockAvailabilityFilterList.clear();
//        this.stockAvailabilityFilterListTemp.clear();
//        this.reverificationList.clear();
//        this.reverificationListTemp.clear();
//
//        if (omsHeaderList != null && omsHeaderList.size() > 0) {
//            omsHeaderList.clear();
//        }
//
//
//        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
//            for (int i = 0; i < omsHeader.getOMSHeader().size(); i++) {
//                if (omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().get(i).getOrderPickup() && !omsHeader.getOMSHeader().get(i).getOrderPacked()) {
//                    omsHeaderList.add(omsHeader.getOMSHeader().get(i));
//                }
////                if (omsHeaderList != null && omsHeaderList.size() > 0) {
////                    mvpPresenter.setTotalOmsHeaderList(omsHeaderList);
////                    PickerNavigationActivity.mInstance.setWelcome("Total" + " " + String.valueOf(omsHeaderList.size()) + " " + "Orders");
////                    activityPickedUpOrdersBinding.headerOrdersCount.setText("Total" + " " + String.valueOf(omsHeaderList.size()) + " " + "Orders");
////                    activityPickedUpOrdersBinding.zeropicked.setVisibility(View.GONE);
////
////                    pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
////                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
////
////                    activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.VISIBLE);
////                    filterOrdersLists();
////                } else {
////                    PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
////                    activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
////                }
//            }
//            mvpPresenter.setTotalOmsHeaderList(omsHeaderList);
//            filterOrdersLists();
//        } else {
//            PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
//            activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
//        }
//        hideLoading();
    }


    public void onSucessfullFulfilmentIdListText(TransactionHeaderResponse omsHeader) {

//        this.customerTypeFilterList.clear();
//        this.customerTypeFilterListTemp.clear();
//        this.orderTypeFilterList.clear();
//        this.orderTypeFilterListTemp.clear();
//        this.orderCategoryFilterList.clear();
//        this.orderCategoryFilterListTemp.clear();
//        this.paymentTypeFilterList.clear();
//        this.paymentTypeFilterListTemp.clear();
//        this.orderSourceFilterList.clear();
//        this.orderSourceFilterListTemp.clear();
//        this.stockAvailabilityFilterList.clear();
//        this.stockAvailabilityFilterListTemp.clear();
//        this.reverificationList.clear();
//        this.reverificationListTemp.clear();

//        if (omsHeaderList != null && omsHeaderList.size() > 0) {
//            omsHeaderList.clear();
//        }
        omsHeaderList = new ArrayList<>();

        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
//            for (int i = 0; i < omsHeader.getOMSHeader().size(); i++) {
//                if (omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().get(i).getOrderPickup() && !omsHeader.getOMSHeader().get(i).getOrderPacked()) {
//                    omsHeaderList.add(omsHeader.getOMSHeader().get(i));
//                }
////                if (omsHeaderList != null && omsHeaderList.size() > 0) {
////                    mvpPresenter.setTotalOmsHeaderList(omsHeaderList);
////                    PickerNavigationActivity.mInstance.setWelcome("Total" + " " + String.valueOf(omsHeaderList.size()) + " " + "Orders");
////                    activityPickedUpOrdersBinding.headerOrdersCount.setText("Total" + " " + String.valueOf(omsHeaderList.size()) + " " + "Orders");
////                    activityPickedUpOrdersBinding.zeropicked.setVisibility(View.GONE);
////
////                    pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
////                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
////
////                    activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.VISIBLE);
////                    filterOrdersLists();
////                } else {
////                    PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
////                    activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
////                    activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
////                }
//            }

            omsHeaderList = omsHeader.getOMSHeader();
//                    omsHeader.getOMSHeader().stream()
//                            .filter(e -> e.getOrderPickup() && !e.getOrderPacked())
//                            .collect(Collectors.toList());

            mvpPresenter.setTotalOmsHeaderList(omsHeaderList);
            if (omsHeaderList != null && omsHeaderList.size() > 0) {

                if (isShiipimentDateFiltered) {

                    Collections.sort(omsHeaderList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                        public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
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


                    Map<String, List<TransactionHeaderResponse.OMSHeader>> studlistGrouped = omsHeaderList.stream().collect(Collectors.groupingBy(w -> w.getShipmentTat()));
                    omsHeaderList.clear();
                    for (Map.Entry<String, List<TransactionHeaderResponse.OMSHeader>> entry : studlistGrouped.entrySet()) {
                        omsHeaderList.addAll(studlistGrouped.get(entry.getKey()));
                        System.out.println(entry.getKey());
                    }
                }


                if (activityPickedUpOrdersBinding.fullfilmentRecycler != null) {
                    activityPickedUpOrdersBinding.fullfilmentRecycler.removeAllViews();
                }
                pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
                pickedUpOrdersAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
                activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
                activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
                if (endIndex % 100 == 0) {
                    PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + (endIndex / 100));
                } else {
                    PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + ((startIndex / 100) + 1));
                }
//                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + (Integer.parseInt(Integer.toString(startIndex).substring(0, 1)) + 1));
                noOrderFound(omsHeaderList.size());
            } else {
                noOrderFound(0);
            }
//            filterOrdersLists();
        } else {
            noOrderFound(0);
//            PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
//            activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    @Override
    public void onSuccessGetOMSTransactionList(OMSTransactionResponse response) {


    }

    @Override
    public void onClickUnHold(TransactionHeaderResponse.OMSHeader omsHeader) {
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
            mvpPresenter.mposPickPackOrderReservationApiCall(omsHeader);
            dialog.dismiss();
        });
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
            mvpPresenter.fetchFulfilmentOrderList();
        }
    }

    @Override
    public void setFiltersHeaderLists(List<TransactionHeaderResponse.OMSHeader> omsHeaderListlus) {
//        if (activityPickedUpOrdersBinding.fullfilmentRecyclerPullToRefresh.isRefreshing()) {
//            activityPickedUpOrdersBinding.fullfilmentRecyclerPullToRefresh.setRefreshing(false);
//        }
        if (omsHeaderListlus != null && omsHeaderListlus.size() > 0) {
            this.customerTypeFilterList.clear();
            this.customerTypeFilterListTemp.clear();
            this.orderTypeFilterList.clear();
            this.orderTypeFilterListTemp.clear();
            this.orderCategoryFilterList.clear();
            this.orderCategoryFilterListTemp.clear();
            this.paymentTypeFilterList.clear();
            this.paymentTypeFilterListTemp.clear();
            this.orderSourceFilterList.clear();
            this.orderSourceFilterListTemp.clear();
            this.stockAvailabilityFilterList.clear();
            this.stockAvailabilityFilterListTemp.clear();
            this.reverificationList.clear();
            this.reverificationListTemp.clear();
            this.shippmentTatFilterList.clear();
            this.shippmentTatFilterListTemp.clear();
            this.billDateTatFilterList.clear();
            this.billDateTatFilterListTemp.clear();

            List<TransactionHeaderResponse.OMSHeader> omsHeaderListlu = omsHeaderListlus;
//            omsHeaderListlu = omsHeaderListlus.stream()
//                    .filter(e -> e.getOrderPickup() && !e.getOrderPacked())
//                    .collect(Collectors.toList());


            if (omsHeaderListlu != null && omsHeaderListlu.size() > 0) {
                for (int i = 0; i < omsHeaderListlu.size(); i++) {

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
                    filterModel.setName(omsHeaderListlu.get(i).getCustomerType());
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
                    filterModel.setName(omsHeaderListlu.get(i).getOrderType());
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
                    filterModel.setName(omsHeaderListlu.get(i).getCategoryType());
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
                    filterModel.setName(omsHeaderListlu.get(i).getPaymentSource());
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
                    filterModel.setName(omsHeaderListlu.get(i).getOrderSource());
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
                    filterModel.setName(omsHeaderListlu.get(i).getStockStatus());

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
                    filterModel.setName(omsHeaderListlu.get(i).getShipmentTat());

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
                    filterModel.setName(omsHeaderListlu.get(i).getBillingTat());

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
                // reverification filter list.
                boolean isReverificationContain = false;
                FilterModel filterModel = new FilterModel();
                filterModel.setName("Reverification");
                filterModel.setSelected(false);
                for (int j = 0; j < reverificationList.size(); j++) {
                    if (reverificationList.get(j).getName().equals(filterModel.getName())) {
                        isReverificationContain = true;
                    }
                }
                if (!isReverificationContain) {
                    reverificationList.add(filterModel);
                }
                omsHeaderListFileredStaticList = omsHeaderListlu;
                startIndex = 0;
                TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
                omsHeader.setOMSHeader(omsHeaderListlu);
                onSucessfullFulfilmentIdList(omsHeader);

                // applyOrderFilters();
            } else {
                omsHeaderListFileredStaticList.clear();
                noOrderFound(0);
            }
        } else {
            omsHeaderListFileredStaticList.clear();
            noOrderFound(0);
        }
    }

    @Override
    public void onClickPrevPage() {
        if (startIndex >= 100) {
            startIndex = startIndex - 100;

            if (lastIndex != 0) {
                endIndex = endIndex - lastIndex;
                lastIndex = 0;
            } else {
                endIndex = endIndex - 100;
            }
            activityPickedUpOrdersBinding.setIsNaxtPage(endIndex != omsHeaderListTotal.size());
            activityPickedUpOrdersBinding.setIsPrevtPage(startIndex != 0);
            List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
            TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
            omsHeader.setOMSHeader(myLastPosts);
            onSucessfullFulfilmentIdListText(omsHeader);

        } else {
            Toast.makeText(getContext(), "No Previous orders", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNextPage() {
        if (omsHeaderListTotal.size() - 1 > endIndex) {
            startIndex = startIndex + 100;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() >= endIndex + 100) {
                endIndex = endIndex + 100;
            } else {
                lastIndex = omsHeaderListTotal.size() - endIndex;
                endIndex = omsHeaderListTotal.size();
            }
            activityPickedUpOrdersBinding.setIsNaxtPage(endIndex != omsHeaderListTotal.size());
            activityPickedUpOrdersBinding.setIsPrevtPage(startIndex != 0);
            List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
            TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
            omsHeader.setOMSHeader(myLastPosts);
            onSucessfullFulfilmentIdListText(omsHeader);
        } else {
            Toast.makeText(getContext(), "No Next orders", Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public void onItemClick(int position, String status, List<RackAdapter.RackBoxModel.ProductData> productDataList, List<RacksDataResponse.FullfillmentDetail> fullFillModelList, RacksDataResponse.FullfillmentDetail fillModel) {
//        Gson gson = new Gson();
//        String myJson = gson.toJson(productDataList);
//
//        Gson gson1 = new Gson();
//        String myJson1 = gson1.toJson(fullFillModelList);
//
//
//       startActivityForResult(PickUpVerificationActivity.getStartActivity(PickedUpOrdersActivity.this, position, status, myJson, myJson1, fillModel), PICKUP_VERIFICATION_ACTIVITY);
//        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
//                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                activityPickedUpOrdersBinding.searchText.setText(Result.getContents());
                BillerOrdersActivity.isBillerActivity = false;
//                Intent intent = new Intent(PickedUpOrdersActivity.this, PickUpVerificationActivity.class);
//                String id=Result.getContents();
//                intent.putExtra("fulfilmentId",id);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PICKUP_VERIFICATION_ACTIVITY:
//                    if (data != null) {
//                        int position = (int) data.getIntExtra("position", 0);
//                        Gson gson = new Gson();
//                        String json = data.getStringExtra("productDataList");
//                        Type type = new TypeToken<List<RackAdapter.RackBoxModel.ProductData>>() {
//                        }.getType();
//                        if (productDataList != null) {
//                            productDataList.clear();
//                        }
//                        productDataList = gson.fromJson(json, type);
//
//                        Gson gson1 = new Gson();
//                        String json1 = data.getStringExtra("fullFillModelList");
//                        Type type1 = new TypeToken<List<RacksDataResponse.FullfillmentDetail>>() {
//                        }.getType();
//                        if (fullfillmentDetailList != null) {
//                            fullfillmentDetailList.clear();
//                        }
//                        fullfillmentDetailList = gson1.fromJson(json1, type1);
//
//                        List<RacksDataResponse.FullfillmentDetail.Product> productsList = new ArrayList<>();
//                        RacksDataResponse.FullfillmentDetail.Product product = new RacksDataResponse.FullfillmentDetail.Product();
//
//                        RacksDataResponse.FullfillmentDetail fullfillmentDetail = new RacksDataResponse.FullfillmentDetail();
//                        for (int i = 0; i < fullfillmentDetailList.size(); i++) {
//                            fullfillmentDetail.setFullfillmentId(fullfillmentDetailList.get(i).getFullfillmentId());
//                            fullfillmentDetail.setTotalItems(fullfillmentDetailList.get(i).getTotalItems());
//                            fullfillmentDetail.setStatus(fullfillmentDetailList.get(i).getStatus());
//                            fullfillmentDetail.setSelectedBoxesData(fullfillmentDetailList.get(i).isSelectedBoxesData());
//                            fullfillmentDetail.setExpandStatus(fullfillmentDetailList.get(i).getExpandStatus());
//                            fullfillmentDetail.setBoxId(fullfillmentDetailList.get(i).getBoxId());
//                            for (int j = 0; j < fullfillmentDetailList.get(i).getProducts().size(); j++) {
//                                for (int k = 0; k < productDataList.size(); k++) {
//                                    if (fullfillmentDetailList.get(i).getProducts().get(j).getProductId().equalsIgnoreCase(productDataList.get(k).getProductId())) {
//                                        fullfillmentDetailList.get(i).getProducts().get(j).setPackerStatus(productDataList.get(k).getPackerStatus());
//                                    }
//                                }
//                            }
//                        }
//
//
//                        mvpPresenter.setFullFillmentDataList(fullfillmentDetailList);
////
////                        pickedUpOrdersAdapter = new PickedUpOrdersAdapter(this, mvpPresenter.getFullFillmentList(), this, mvpPresenter.getListOfListFullFillmentList(), false);
////                        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
////                        activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
////                        activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
////                        activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
//                    }
//                    break;
//            }
//        }
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
        isShiipimentDateFiltered = false;
        mvpPresenter.fetchFulfilmentOrderList();
    }


    @Override
    public void onClickFilters() {
        onClickFilterIcon();
    }

    @Override
    public void onItemClick() {

    }


}