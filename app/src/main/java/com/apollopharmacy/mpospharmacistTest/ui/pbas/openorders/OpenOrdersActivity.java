package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOpenOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterTypeAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FullfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.orderspicking.StartPickingActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.prescriptionslider.PrescriptionSliderActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;
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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.inject.Inject;

public class OpenOrdersActivity extends BaseFragment implements OpenOrdersMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {


    @Inject
    OpenOrdersMvpPresenter<OpenOrdersMvpView> mPresenter;
    private ActivityOpenOrdersPBinding openOrdersBinding;
    //    private List<FullfilmentAdapter.FullfilmentModel> fullfilmentModelList;
    private FullfilmentAdapter fullfilmentAdapter;
    public static boolean isopenOrderActivity = false;
    public static final int READYFORPICKUP = 110;

    private List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList;
    private List<TransactionHeaderResponse.OMSHeader> filteredOmsHeaderList;
    //    public List<RackAdapter.RackBoxModel.ProductData> productDataList;
    public List<GetOMSTransactionResponse> getOMSTransactionResponseList;

    private List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotal = new ArrayList<>();

    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    private static List<TransactionHeaderResponse.OMSHeader> omsHeaderListFileredStaticList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = new ArrayList<>();
    public static String TOTAL_ORDERS = null;

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


    private boolean isStockAvailableChecked = false;
    private int startIndex = 0;
    private int endIndex = 100;

    private boolean isStockAvailable = false;
    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter, reverificationAdapter, shippingTatFilterAdapter, billDateTatFilterAdapter;

    private boolean isShiipimentDateFiltered = false;
    private boolean isBillDateFiltered = false;

    private ArrayList<String> filterTypeList = new ArrayList<>();
    private boolean isBackFromReadyforPickupScreen = false;

    int maxOrdersAllowed = 10;
    int minOrdersAllowed = 3;

    public static Intent getStartActivity(Context context) {
        return new Intent(context, OpenOrdersActivity.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        openOrdersBinding = DataBindingUtil.inflate(inflater, R.layout.activity_open_orders_p, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OpenOrdersActivity.this);
        return openOrdersBinding.getRoot();

    }
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        openOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_open_orders_p);
//        getActivityComponent().inject(this);
//        mPresenter.onAttach(OpenOrdersActivity.this);
//        setUp();
//    }

//    @Override
//    protected void setUp(View view) {
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void setUp(View view) {
        hideKeyboard();
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.setTitle("Open Orders");
        PickerNavigationActivity.mInstance.setStock("Stock Available");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(true);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.stockAvailableCheckbox.setChecked(false);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.VISIBLE);


        prevNextListeners();
        pulltoRrefresh();
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        openOrdersBinding.setCallback(mPresenter);
        openOrdersBinding.terminalId.setText("Terminal ID : " + mPresenter.getTerminalId());
        mPresenter.fetchFulfilmentOrderList(false);
        searchByFulfilmentId();

        openOrdersBinding.deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrdersBinding.searchByfulfimentid.setText("");
                openOrdersBinding.searchIcon.setVisibility(View.VISIBLE);
                openOrdersBinding.deleteCancel.setVisibility(View.GONE);
                recyclerView();

            }
        });

        // start picking activity for demo
        openOrdersBinding.assignedLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StartPickingActivity.class);
            startActivity(intent);
        });

        List<UserModel._DropdownValueBean> maxMinOrdersList = mPresenter.getMaxMinOrdersList();
        for (int i = 0; i < maxMinOrdersList.size(); i++) {
            if (mPresenter.getUserId().equalsIgnoreCase(maxMinOrdersList.get(i).getCode())) {
//                maxOrdersAllowed = Integer.parseInt(maxMinOrdersList.get(i).getMaximumOrders());
//                minOrdersAllowed = Integer.parseInt(maxMinOrdersList.get(i).getMinimumOrders());
            }
        }

    }

    private void pulltoRrefresh() {
//        openOrdersBinding.fullfilmentRecyclerPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                refreshData(); // your code
////                pullToRefresh.setRefreshing(false);
//                mPresenter.fetchFulfilmentOrderList();
//            }
//        });
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
            FilterModel reverificationModel = new FilterModel();
            reverificationModel.setSelected(filterModel.isSelected());
            reverificationModel.setName(filterModel.getName());
            reverificationListTemp.add(reverificationModel);
        }
        //
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

    private void searchByFulfilmentId() {
        openOrdersBinding.searchByfulfimentid.addTextChangedListener(new TextWatcher() {
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
                    startIndex = 0;
                    TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
                    omsHeader.setOMSHeader(omsHeaderListFileredStaticList);
                    onSucessfullFulfilmentIdList(omsHeader);
                } else {
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
//                    openOrdersBinding.searchIcon.setVisibility(View.GONE);
//                    openOrdersBinding.deleteCancel.setVisibility(View.VISIBLE);
//                    if (fullfilmentAdapter != null) {
//                        fullfilmentAdapter.getFilter().filter(editable);
//                    }
//                } else if (openOrdersBinding.searchByfulfimentid.getText().toString().equals("")) {
//                    if (fullfilmentAdapter != null) {
//                        fullfilmentAdapter.getFilter().filter("");
//                    }
//                    openOrdersBinding.searchIcon.setVisibility(View.VISIBLE);
//                    openOrdersBinding.deleteCancel.setVisibility(View.GONE);
//                } else {
//                    if (fullfilmentAdapter != null) {
//                        fullfilmentAdapter.getFilter().filter("");
//                    }
//                }
            }
        });
    }


    int getPos;

    @Override
    public void ondownArrowClicked(String refId, int position) {
        for (int i = 0; i < omsHeaderList.size(); i++) {
            if (omsHeaderList.get(i).getRefno().equals(refId)) {
                this.getPos = i;
                if (omsHeaderList.get(i).getExpandStatus() == 1) {
                    omsHeaderList.get(i).setExpandStatus(0);
                    if (fullfilmentAdapter != null) {
                        fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
                        fullfilmentAdapter.notifyDataSetChanged();
                    }
                } else {
                    mPresenter.onGetOmsTransaction(omsHeaderList.get(i).getRefno(), false, false);
                }
                break;
            }
        }
    }

    @Override
    public void onSuccessRackApi(RacksDataResponse racksDataResponse) {
//        this.racksDataResponse = racksDataResponse;
//        if (racksDataResponse != null && racksDataResponse.getFullfillmentDetails() != null && racksDataResponse.getFullfillmentDetails().size() > 0) {
//            fullfilmentModelList = new ArrayList<>();
//            for (int i = 0; i < racksDataResponse.getFullfillmentDetails().size(); i++) {
//                FullfilmentAdapter.FullfilmentModel fullfilmentModel = new FullfilmentAdapter.FullfilmentModel();
//                fullfilmentModel.setFullfilmentId(racksDataResponse.getFullfillmentDetails().get(i).getFullfillmentId());
//                fullfilmentModel.setTotalItems(racksDataResponse.getFullfillmentDetails().get(i).getTotalItems());
//                fullfilmentModel.setSelected(false);
//                fullfilmentModelList.add(fullfilmentModel);
//            }
//
//            openOrdersBinding.headerOrdersCount.setText("Total " + fullfilmentModelList.size() + " orders");
//            fullfilmentAdapter = new FullfilmentAdapter(this, fullfilmentModelList, this, productDataList, racksDataResponse);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
//            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
//        }
    }

    public void recyclerView() {

//        List<TransactionHeaderResponse.OMSHeader> newOmsHeaderList = new ArrayList<>();
//        for (int j = 0; j < omsHeaderList.size(); j++) {
//            if ( omsHeaderList.get(j).getStockStatus().equalsIgnoreCase("NOT AVAILABLE")&&omsHeaderList.get(j).getStockStatus().equalsIgnoreCase("PARTIAL AVAILABLE")) {
//                newOmsHeaderList.remove(omsHeaderList.get(j));
//            }
//        }

        Collections.sort(omsHeaderList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
            public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(o1.getDeliveryDate());
                    date2 = dateFormat.parse(o2.getDeliveryDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date1.compareTo(date2);
            }
        });
//        Collections.reverse(omsHeaderList);

        fullfilmentAdapter = new FullfilmentAdapter(getContext(), omsHeaderList, this, null, mPresenter.getUserId());
        fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
    }

    @Override
    public void onClickFilterIcon() {
        if (mPresenter.getTotalOmsHeaderList() != null && mPresenter.getTotalOmsHeaderList().size() > 0) {

            openOrdersBinding.searchByfulfimentid.setText("");
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
                initFilterTypeAdapter();
                filterDialog.dismiss();
                hideLoading();
            });
            dialogFilterBinding.clear.setOnClickListener(view -> {
                clearFilter();
                filterDialog.dismiss();
                applyOrderFilters();
                initFilterTypeAdapter();
            });
            filterDialog.show();
        } else {
            Toast.makeText(getContext(), "No Orders are available", Toast.LENGTH_SHORT).show();
        }
    }

    private void initFilterTypeAdapter() {
        FilterTypeAdapter filterTypeAdapter = new FilterTypeAdapter(getContext(), filterTypeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        openOrdersBinding.filterTypeRcv.setAdapter(filterTypeAdapter);
        openOrdersBinding.filterTypeRcv.setLayoutManager(layoutManager);
    }

    private void applyOrderFilters() {
        omsHeaderListTotal.clear();
        filterTypeList.clear();

        // Customer type filter list.
        boolean isCustomerTypeFilter = false;
        for (FilterModel orderTypeFilter : customerTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isCustomerTypeFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isCustomerTypeFilter) {
            List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                customerTypeOMSHeaderFilter = omsHeaderListTotal;
            } else {
                customerTypeOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
            }
//            List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            for (FilterModel customerTypeFilter : customerTypeFilterList) {
                for (int i = 0; i < customerTypeOMSHeaderFilter.size(); i++) {
                    if (!customerTypeFilter.isSelected() && (customerTypeFilter.getName().equals(customerTypeOMSHeaderFilter.get(i).getCustomerType()))) {
                        customerTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderListTotal = customerTypeOMSHeaderFilter;
//            omsHeaderList.addAll(customerTypeOMSHeaderFilter);
        }

        // Order type filter list.

        boolean isorderTypeFilter = false;
        for (FilterModel orderTypeFilter : orderTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isorderTypeFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isorderTypeFilter) {
            List<TransactionHeaderResponse.OMSHeader> orderTypeOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                orderTypeOMSHeaderFilter = omsHeaderListTotal;
            } else {
                orderTypeOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
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

        // Order category filter list.

//        List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        boolean isOrderCategoryFilter = false;
        for (FilterModel orderTypeFilter : orderCategoryFilterList) {
            if (orderTypeFilter.isSelected()) {
                isOrderCategoryFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isOrderCategoryFilter) {
            List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                orderCategoryOMSHeaderFilter = omsHeaderListTotal;
            } else {
                orderCategoryOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
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
//            for (TransactionHeaderResponse.OMSHeader omsHeader : orderCategoryOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(orderCategoryOMSHeaderFilter);
        }

        // Payment type filter list.

//        List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        boolean isPaymentTypeFilter = false;
        for (FilterModel orderTypeFilter : paymentTypeFilterList) {
            if (orderTypeFilter.isSelected()) {
                isPaymentTypeFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isPaymentTypeFilter) {
            List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                paymentTypeOMSHeaderFilter = omsHeaderListTotal;
            } else {
                paymentTypeOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
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
//            for (TransactionHeaderResponse.OMSHeader omsHeader : paymentTypeOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(paymentTypeOMSHeaderFilter);
        }
        // Order source filter list.

//        List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        boolean isOrderSourceFilter = false;
        for (FilterModel orderTypeFilter : orderSourceFilterList) {
            if (orderTypeFilter.isSelected()) {
                isOrderSourceFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isOrderSourceFilter) {
            List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                orderSourceOMSHeaderFilter = omsHeaderListTotal;
            } else {
                orderSourceOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
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
//            for (TransactionHeaderResponse.OMSHeader omsHeader : orderSourceOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(orderSourceOMSHeaderFilter);
        }
        // Stock availability filter list.
//        boolean isStockAvailableSelected = false;
//        for (int i = 0; i < stockAvailabilityFilterList.size(); i++) {
//            if (stockAvailabilityFilterList.get(i).isSelected() && stockAvailabilityFilterList.get(i).getName().equalsIgnoreCase("STOCK AVAILABLE")) {
//                isStockAvailableSelected = true;
//            }
//        }

//        List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
        boolean isStockAvailabilityFilter = false;
        for (FilterModel orderTypeFilter : stockAvailabilityFilterList) {
            if (orderTypeFilter.isSelected()) {
                isStockAvailabilityFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isStockAvailabilityFilter) {
            List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                stockAvailabilityOMSHeaderFilter = omsHeaderListTotal;
            } else {
                stockAvailabilityOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
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

        //Shippment tat filter list
        boolean isShippingTatFilter = false;
        for (FilterModel orderTypeFilter : shippmentTatFilterList) {
            if (orderTypeFilter.isSelected()) {
                isShippingTatFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isShippingTatFilter) {
            List<TransactionHeaderResponse.OMSHeader> shippingTatOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                shippingTatOMSHeaderFilter = omsHeaderListTotal;
            } else {
                shippingTatOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
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


        boolean isBillDateTatFilter = false;
        for (FilterModel billDateTatFilter : billDateTatFilterList) {
            if (billDateTatFilter.isSelected()) {
                isBillDateTatFilter = true;
                filterTypeList.add(billDateTatFilter.getName());
            }
        }
        if (isBillDateTatFilter) {
            List<TransactionHeaderResponse.OMSHeader> billDateTatOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                billDateTatOMSHeaderFilter = omsHeaderListTotal;
            } else {
                billDateTatOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
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


        // Reverification filter list.

        boolean isReverificationFilter = false;
        for (FilterModel orderTypeFilter : reverificationList) {
            if (orderTypeFilter.isSelected()) {
                isReverificationFilter = true;
                filterTypeList.add(orderTypeFilter.getName());
            }
        }
        if (isReverificationFilter) {
            List<TransactionHeaderResponse.OMSHeader> reverificationOMSHeaderFilter = null;
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
                reverificationOMSHeaderFilter = omsHeaderListTotal;
            } else {
                reverificationOMSHeaderFilter = mPresenter.getGlobalTotalOmsHeaderList();
            }
            for (FilterModel reverificationFilter : reverificationList) {
                for (int i = 0; i < reverificationOMSHeaderFilter.size(); i++) {
                    if (!reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() == 1) {
                        reverificationOMSHeaderFilter.remove(i);
                        i--;
                    } else if (reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() != 1) {
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


        //pickUpStatusFilter

//        if (omsHeaderList != null && omsHeaderList.size() == 0) {
//            omsHeaderList = mPresenter.getTotalOmsHeaderList();
//        }


        if (isShippingTatFilter) {
            isShiipimentDateFiltered = true;
        } else {
            isShiipimentDateFiltered = false;
        }
        if (isBillDateTatFilter) {
            isBillDateFiltered = true;
        } else {
            isBillDateFiltered = false;
        }
        if (!isStockAvailabilityFilter && !isorderTypeFilter && !isOrderCategoryFilter && !isPaymentTypeFilter && !isOrderSourceFilter && !isCustomerTypeFilter && !isReverificationFilter && !isShippingTatFilter && !isBillDateTatFilter) {
            omsHeaderListTotal = mPresenter.getGlobalTotalOmsHeaderList();

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

//        PickerNavigationActivity.mInstance.setStock("Stock available");
//        if (isStockAvailable) {
//            if (omsHeaderList != null && omsHeaderList.size() > 0) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (!omsHeaderList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//        } else {
//            if (omsHeaderList != null && omsHeaderList.size() > 0) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//        }

        if (isStockAvailableChecked) {
            for (int i = 0; i < omsHeaderListTotal.size(); i++) {
                if (!omsHeaderListTotal.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                    omsHeaderListTotal.remove(i);
                    i--;
                }
            }
        }

        if (omsHeaderListTotal != null && omsHeaderListTotal.size() > 0) {
            List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotals = omsHeaderListTotal.stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
            omsHeaderListFileredStaticList = omsHeaderListTotals;
            startIndex = 0;
            TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
            omsHeader.setOMSHeader(omsHeaderListTotal);
            onSucessfullFulfilmentIdList(omsHeader);

//            fullfilmentAdapter = new FullfilmentAdapter(getContext(), omsHeaderList, this, null, mPresenter.getUserId());
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
//            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
//            noOrderFound(omsHeaderList.size());
        } else {
            omsHeaderListFileredStaticList.clear();
            noOrderFound(0);
        }

//        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
//        openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
//        PickerNavigationActivity.mInstance.setStockAvailableBoxCheck(isStockAvailableSelected);
    }


    private void applyOrderFiltersComment() {
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
                customerTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
//            List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            for (FilterModel customerTypeFilter : customerTypeFilterList) {
                for (int i = 0; i < customerTypeOMSHeaderFilter.size(); i++) {
                    if (!customerTypeFilter.isSelected() && (customerTypeFilter.getName().equals(customerTypeOMSHeaderFilter.get(i).getCustomerType()))) {
                        customerTypeOMSHeaderFilter.remove(i);
                        i--;
                    }
                }
            }
            omsHeaderList = customerTypeOMSHeaderFilter;
//            omsHeaderList.addAll(customerTypeOMSHeaderFilter);
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

        // Order category filter list.

//        List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
//            for (TransactionHeaderResponse.OMSHeader omsHeader : orderCategoryOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(orderCategoryOMSHeaderFilter);
        }

        // Payment type filter list.

//        List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
//            for (TransactionHeaderResponse.OMSHeader omsHeader : paymentTypeOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(paymentTypeOMSHeaderFilter);
        }
        // Order source filter list.

//        List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
//            for (TransactionHeaderResponse.OMSHeader omsHeader : orderSourceOMSHeaderFilter) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//            omsHeaderList.addAll(orderSourceOMSHeaderFilter);
        }
        // Stock availability filter list.
//        boolean isStockAvailableSelected = false;
//        for (int i = 0; i < stockAvailabilityFilterList.size(); i++) {
//            if (stockAvailabilityFilterList.get(i).isSelected() && stockAvailabilityFilterList.get(i).getName().equalsIgnoreCase("STOCK AVAILABLE")) {
//                isStockAvailableSelected = true;
//            }
//        }

//        List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
                reverificationOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
            }
            for (FilterModel reverificationFilter : reverificationList) {
                for (int i = 0; i < reverificationOMSHeaderFilter.size(); i++) {
                    if (!reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() == 1) {
                        reverificationOMSHeaderFilter.remove(i);
                        i--;
                    } else if (reverificationFilter.isSelected() && reverificationOMSHeaderFilter.get(i).getReVerification() != 1) {
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


        //pickUpStatusFilter

//        if (omsHeaderList != null && omsHeaderList.size() == 0) {
//            omsHeaderList = mPresenter.getTotalOmsHeaderList();
//        }
        if (!isStockAvailabilityFilter && !isorderTypeFilter && !isOrderCategoryFilter && !isPaymentTypeFilter && !isOrderSourceFilter && !isCustomerTypeFilter && !isReverificationFilter) {
            omsHeaderList = mPresenter.getTotalOmsHeaderList();

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

//        PickerNavigationActivity.mInstance.setStock("Stock available");
//        if (isStockAvailable) {
//            if (omsHeaderList != null && omsHeaderList.size() > 0) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (!omsHeaderList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//        } else {
//            if (omsHeaderList != null && omsHeaderList.size() > 0) {
//                for (int i = 0; i < omsHeaderList.size(); i++) {
//                    if (omsHeaderList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//                        omsHeaderList.remove(i);
//                        i--;
//                    }
//                }
//            }
//        }

        if (isStockAvailableChecked) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (!omsHeaderList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                    omsHeaderList.remove(i);
                    i--;
                }
            }
        }

        if (omsHeaderList != null && omsHeaderList.size() > 0) {

            Collections.sort(omsHeaderList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = dateFormat.parse(o1.getDeliveryDate());
                        date2 = dateFormat.parse(o2.getDeliveryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date1.compareTo(date2);
                }
            });
//            Collections.reverse(omsHeaderList);
//
            fullfilmentAdapter = new FullfilmentAdapter(getContext(), omsHeaderList, this, null, mPresenter.getUserId());
            fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
            noOrderFound(omsHeaderList.size());
        } else {
            noOrderFound(0);
        }

        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
        openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
//        PickerNavigationActivity.mInstance.setStockAvailableBoxCheck(isStockAvailableSelected);
    }

    private void applyFiltersTemp() {
        omsHeaderList.clear();
        List<TransactionHeaderResponse.OMSHeader> omsHeaderMainList = mPresenter.getTotalOmsHeaderList();

        for (TransactionHeaderResponse.OMSHeader omsHeader : omsHeaderMainList) {
            boolean isValidOrder = true;
            for (FilterModel filterModel : stockAvailabilityFilterList) {
                if (filterModel.isSelected() && filterModel.getName().equalsIgnoreCase(omsHeader.getStockStatus())) {
                    omsHeaderList.add(omsHeader);
                }
            }
            for (FilterModel filterModel : orderTypeFilterList) {
                if (filterModel.isSelected() && filterModel.getName().equalsIgnoreCase(omsHeader.getOrderType())) {
                    if (!omsHeaderList.contains(omsHeader)) omsHeaderList.add(omsHeader);
                }
            }
        }
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
        stockAvailabilityFilterAdapter = new FilterItemAdapter(getContext(), stockAvailabilityFilterList);
        dialogFilterBinding.stockAvailableFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.stockAvailableFilter.setAdapter(stockAvailabilityFilterAdapter);


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

        customerTypeFilterAdapter = new FilterItemAdapter(getContext(), customerTypeFilterList);
        dialogFilterBinding.customerTypeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dialogFilterBinding.customerTypeFilter.setAdapter(customerTypeFilterAdapter);


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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {
        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
            omsHeaderListTotal = omsHeader.getOMSHeader().stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() >= 5000) {
                startIndex = 0;
                endIndex = 5000;
            } else {
                endIndex = omsHeaderListTotal.size();
            }
            openOrdersBinding.setIsNaxtPage(endIndex != omsHeaderListTotal.size());
            openOrdersBinding.setIsPrevtPage(startIndex != 0);

            List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
            omsHeader.setOMSHeader(myLastPosts);
            onSucessfullFulfilmentIdListText(omsHeader);
        } else {
            noOrderFound(0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            omsHeaderList.clear();
        }
        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
//            for (int i = 0; i < omsHeader.getOMSHeader().size(); i++) {
//                if (!omsHeader.getOMSHeader().get(i).getOrderPickup()) {
////                    if (omsHeader.getOMSHeader().get(i).getOverallOrderStatus().length() > 3 && omsHeader.getOMSHeader().get(i).getReVerification() != 0) {
//                    omsHeaderList.add(omsHeader.getOMSHeader().get(i));
////                    } else if (omsHeader.getOMSHeader().get(i).getOverallOrderStatus().length() < 3 && omsHeader.getOMSHeader().get(i).getReVerification() == 0) {
////                        omsHeaderList.add(omsHeader.getOMSHeader().get(i));
////                    }
//
//                }
//            }

            omsHeaderList = omsHeader.getOMSHeader().stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
            Collections.sort(omsHeaderList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                @Override
                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                    return -o1.getStockStatus().compareTo(o2.getStockStatus());
                }
            });

            mPresenter.setTotalOmsHeaderList(omsHeaderList);
            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
            openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
            openOrdersBinding.total.setText(String.valueOf(omsHeader.getOMSHeader().size()));
            openOrdersBinding.assigned.setText(String.valueOf(omsHeader.getOMSHeader().size()));
            long pendingCount = omsHeader.getOMSHeader().stream().filter(e -> e.getPickPackStatus().equalsIgnoreCase("1") && mPresenter.getUserId().equalsIgnoreCase(e.getPickPackUser())).count();
            openOrdersBinding.pending.setText(String.valueOf(pendingCount));

            Collections.sort(omsHeaderList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = dateFormat.parse(o1.getDeliveryDate());
                        date2 = dateFormat.parse(o2.getDeliveryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date1.compareTo(date2);
                }
            });
//            Collections.reverse(omsHeaderList);
            Collections.sort(omsHeaderList, ((o1, o2) -> {
                boolean pickPackUser1 = o1.getPickPackUser().equalsIgnoreCase(mPresenter.getUserId());
                boolean pickPackUser2 = o2.getPickPackUser().equalsIgnoreCase(mPresenter.getUserId());
                if (pickPackUser1 && !pickPackUser2) {
                    return -1;
                } else if (!pickPackUser1 && pickPackUser2) {
                    return 1;
                } else {
                    return 0;
                }
            }));
            Collections.sort(omsHeaderList, ((o1, o2) -> {
                boolean isStockAvailable1 = o1.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE");
                boolean isStockAvailable2 = o2.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE");
                if (isStockAvailable1 && !isStockAvailable2) {
                    return -1;
                } else if (!isStockAvailable1 && isStockAvailable2) {
                    return 1;
                } else {
                    return 0;
                }
            }));
            Collections.sort(omsHeaderList, (o1, o2) -> {
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                long diff1 = 0;
                long diff2 = 0;
                try {
                    diff1 = Math.abs(dateFormat.parse(o1.getShipmentTat()).getTime() - currentDate.getTime());
                    diff2 = Math.abs(dateFormat.parse(o2.getShipmentTat()).getTime() - currentDate.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Long.compare(diff1, diff2);
            });

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


//            for (int i = 0; i <= studlistGrouped.size(); i++){
//                omsHeaderList.addAll(studlistGrouped.get(i))
//            }
            Map<String, List<TransactionHeaderResponse.OMSHeader>> omsHeaderListGroup = omsHeaderList.stream()
                    .filter(o -> o.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE"))
                    .collect(Collectors.groupingBy(TransactionHeaderResponse.OMSHeader::getShipmentTat));
            omsHeaderList.clear();
            for (Map.Entry<String, List<TransactionHeaderResponse.OMSHeader>> entry : omsHeaderListGroup.entrySet()) {
                omsHeaderList.addAll(omsHeaderListGroup.get(entry.getKey()));
            }
            if (omsHeaderList.size() > 0 && omsHeaderList.size() > maxOrdersAllowed) {
                omsHeaderList = omsHeaderList.stream().limit(maxOrdersAllowed).collect(Collectors.toList());
            }


            fullfilmentAdapter = new FullfilmentAdapter(getContext(), omsHeaderList, this, null, mPresenter.getUserId());
            fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
            if (endIndex % 100 == 0) {
                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + (endIndex / 100));
            } else {
                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + ((startIndex / 100) + 1));
            }
//            PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + (Integer.parseInt(Integer.toString(startIndex).substring(0, 1)) + 1));
            TOTAL_ORDERS = String.valueOf(omsHeaderList.size());
            noOrderFound(omsHeaderList.size());
//            filterOrdersLists();
//            filterOrdersListsComment();


        } else {
            noOrderFound(0);
        }

        if (isRefreshScreen) {
            isRefreshScreen = false;
            applyOrderFilters();
            PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.stockAvailableCheckbox.setChecked(PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.stockAvailableCheckbox.isChecked());
        } else {
            if (!isBackFromReadyforPickupScreen) {
//                showLoading();
//                autoAssign();
            }
        }
    }

    private int autoAssignPos = 0;

    private void autoAssign() {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() < minOrdersAllowed && omsHeaderList.size() > autoAssignPos) {
            if (!omsHeaderList.get(autoAssignPos).getStockStatus().equals("NOT AVAILABLE")) {
                if ((omsHeaderList.get(autoAssignPos).getPickPackStatus().equalsIgnoreCase("1") && !mPresenter.getUserId().equalsIgnoreCase(omsHeaderList.get(autoAssignPos).getPickPackUser()))) {
                    hideLoading();
                    onContinueBtnEnable();
                } else {
                    mPresenter.onGetOmsTransaction(omsHeaderList.get(autoAssignPos).getRefno(), true, true);
                }
            } else {
                autoAssignPos++;
                autoAssign();
            }
        } else {
            hideLoading();
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                if (fullfilmentAdapter != null) {
                    fullfilmentAdapter.notifyDataSetChanged();
                }
                onContinueBtnEnable();
//                onClickContinue();
            }
        }
        /*for (TransactionHeaderResponse.OMSHeader omsHeader1 : omsHeaderList) {
            if (!omsHeader1.getStockStatus().equals("NOT AVAILABLE")) {
                if ((omsHeader1.getPickPackStatus().equalsIgnoreCase("1") && !mPresenter.getUserId().equalsIgnoreCase(omsHeader1.getPickPackUser()))) {

                }else {

                }
            }
        }*/

        /*if (!omsHeader.getStockStatus().equals("NOT AVAILABLE")) {
            if ((omsHeader.getPickPackStatus().equalsIgnoreCase("1") && !userId.equalsIgnoreCase(omsHeader.getPickPackUser()))) {
                Toast.makeText(context, "Order is in progress", Toast.LENGTH_SHORT).show();
            } else {
                if (mvpView != null)
                    for (int i = 0; i < omsHeaderList.size(); i++) {
                        if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                            mvpView.onFullfillmentItemClick(i, position, omsHeader);
                            break;
                        }
                    }

            }
        } else {
            Toast.makeText(context, omsHeader.getStockStatus(), Toast.LENGTH_SHORT).show();
        }*/
    }

    private void filterOrdersListsComment() {
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListlu = mPresenter.getGlobalTotalOmsHeaderList();
        if (omsHeaderListlu != null && omsHeaderListlu.size() > 0) {
            for (int i = 0; i < omsHeaderListlu.size(); i++) {

                boolean isCustomerTypeContain = false;
                boolean isOrderTypeContain = false;
                boolean isOrderCategoryContain = false;
                boolean isPaymentTypeContain = false;
                boolean isOrderSourceContain = false;
                boolean isStockAvailabilityContain = false;
                boolean isPickUpStatusContain = false;
                boolean isReverification = false;


                // customer type filter list.
                FilterModel filterModel = new FilterModel();
                filterModel.setName(omsHeaderListlu.get(i).getCustomerType());
                filterModel.setSelected(false);
                for (int j = 0; j < customerTypeFilterList.size(); j++) {
                    if (customerTypeFilterList.get(j).getName().equals(filterModel.getName())) {
                        isCustomerTypeContain = true;
                    }
                }
                if (!isCustomerTypeContain) {
                    customerTypeFilterList.add(filterModel);
                }

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
            }
            boolean isReverificationContain = false;
            // reverification filter list.
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

        }

//        if (omsHeaderListlu != null && omsHeaderListlu.size() > 0) {
//            for (int i = 0; i < omsHeaderListlu.size(); i++) {
//
//                if (!customerTypeFilterList.contains(new FilterModel(omsHeaderListlu.get(i).getCustomerType(), false))) {
//                    customerTypeFilterList.add(new FilterModel(omsHeaderListlu.get(i).getCustomerType(), false));
//                }
//                if (!orderTypeFilterList.contains(new FilterModel(omsHeaderListlu.get(i).getOrderType(), false))) {
//                    orderTypeFilterList.add(new FilterModel(omsHeaderListlu.get(i).getOrderType(), false));
//                }
//                if (!orderCategoryFilterList.contains(new FilterModel(omsHeaderListlu.get(i).getCategoryType(), false))) {
//                    orderCategoryFilterList.add(new FilterModel(omsHeaderListlu.get(i).getCategoryType(), false));
//                }
//                if (!paymentTypeFilterList.contains(new FilterModel(omsHeaderListlu.get(i).getPaymentSource(), false))) {
//                    paymentTypeFilterList.add(new FilterModel(omsHeaderListlu.get(i).getPaymentSource(), false));
//                }
//                if (!orderSourceFilterList.contains(new FilterModel(omsHeaderListlu.get(i).getOrderSource(), false))) {
//                    orderSourceFilterList.add(new FilterModel(omsHeaderListlu.get(i).getOrderSource(), false));
//                }
//                if (!stockAvailabilityFilterList.contains(new FilterModel(omsHeaderListlu.get(i).getStockStatus(), false))) {
//                    stockAvailabilityFilterList.add(new FilterModel(omsHeaderListlu.get(i).getStockStatus(), false));
//                }
//                if (!reverificationList.contains(new FilterModel("Reverification", false))) {
//                    reverificationList.add(new FilterModel("Reverification", false));
//                }
//
//
////                boolean isCustomerTypeContain = false;
////                boolean isOrderTypeContain = false;
////                boolean isOrderCategoryContain = false;
////                boolean isPaymentTypeContain = false;
////                boolean isOrderSourceContain = false;
////                boolean isStockAvailabilityContain = false;
////                boolean isPickUpStatusContain = false;
////                boolean isReverification = false;
////
////
////                //1. customer type filter list.
////                FilterModel filterModel = new FilterModel();
////                filterModel.setName(mPresenter.getGlobalTotalOmsHeaderList().get(i).getCustomerType());
////                filterModel.setSelected(false);
////
////                FilterModel finalFilterModel = filterModel;
////                isCustomerTypeContain = customerTypeFilterList.stream().anyMatch(o -> o.getName().equals(finalFilterModel.getName()));
////
//////                for (int j = 0; j < customerTypeFilterList.size(); j++) {
//////                    if (customerTypeFilterList.get(j).getName().equals(filterModel.getName())) {
//////                        isCustomerTypeContain = true;
//////                    }
//////                }
////                if (!isCustomerTypeContain) {
////                    customerTypeFilterList.add(filterModel);
////                }
////
////                //2. order type filter list.
////                filterModel = new FilterModel();
////                filterModel.setName(mPresenter.getGlobalTotalOmsHeaderList().get(i).getOrderType());
////                filterModel.setSelected(false);
////
////                FilterModel finalFilterModel1 = filterModel;
////                isOrderTypeContain = orderTypeFilterList.stream().anyMatch(o -> o.getName().equals(finalFilterModel1.getName()));
////
//////                for (int j = 0; j < orderTypeFilterList.size(); j++) {
//////                    if (orderTypeFilterList.get(j).getName().equals(filterModel.getName())) {
//////                        isOrderTypeContain = true;
//////                    }
//////                }
////                if (!isOrderTypeContain) {
////                    orderTypeFilterList.add(filterModel);
////                }
////
////                //3. order category filter list.
////                filterModel = new FilterModel();
////                filterModel.setName(mPresenter.getGlobalTotalOmsHeaderList().get(i).getCategoryType());
////                filterModel.setSelected(false);
////                FilterModel finalFilterModel2 = filterModel;
////                isOrderCategoryContain = orderCategoryFilterList.stream().anyMatch(o -> o.getName().equals(finalFilterModel2.getName()));
//////                for (int j = 0; j < orderCategoryFilterList.size(); j++) {
//////                    if (orderCategoryFilterList.get(j).getName().equals(filterModel.getName())) {
//////                        isOrderCategoryContain = true;
//////                    }
//////                }
////                if (!isOrderCategoryContain) {
////                    orderCategoryFilterList.add(filterModel);
////                }
////
////                //4. payment type filter list.
////                filterModel = new FilterModel();
////                filterModel.setName(mPresenter.getGlobalTotalOmsHeaderList().get(i).getPaymentSource());
////                filterModel.setSelected(false);
////                FilterModel finalFilterModel3 = filterModel;
////                isPaymentTypeContain = paymentTypeFilterList.stream().anyMatch(o -> o.getName().equals(finalFilterModel3.getName()));
//////                for (int j = 0; j < paymentTypeFilterList.size(); j++) {
//////                    if (paymentTypeFilterList.get(j).getName().equals(filterModel.getName())) {
//////                        isPaymentTypeContain = true;
//////                    }
//////                }
////                if (!isPaymentTypeContain) {
////                    paymentTypeFilterList.add(filterModel);
////                }
////
////                //5. order source filter list.
////                filterModel = new FilterModel();
////                filterModel.setName(mPresenter.getGlobalTotalOmsHeaderList().get(i).getOrderSource());
////                filterModel.setSelected(false);
////
////                FilterModel finalFilterModel4 = filterModel;
////                isOrderSourceContain = orderSourceFilterList.stream().anyMatch(o -> o.getName().equals(finalFilterModel4.getName()));
//////                for (int j = 0; j < orderSourceFilterList.size(); j++) {
//////                    if (orderSourceFilterList.get(j).getName().equals(filterModel.getName())) {
//////                        isOrderSourceContain = true;
//////                    }
//////                }
////                if (!isOrderSourceContain) {
////                    orderSourceFilterList.add(filterModel);
////                }
//////pickupstatusfilter
//////
////
////
////                //6. stock availability filter list.
////                filterModel = new FilterModel();
////                filterModel.setName(mPresenter.getGlobalTotalOmsHeaderList().get(i).getStockStatus());
////
////                filterModel.setSelected(false);
////                FilterModel finalFilterModel5 = filterModel;
////                isStockAvailabilityContain = stockAvailabilityFilterList.stream().anyMatch(o -> o.getName().equals(finalFilterModel5.getName()));
//////                for (int j = 0; j < stockAvailabilityFilterList.size(); j++) {
//////                    if (stockAvailabilityFilterList.get(j).getName().equals(filterModel.getName())) {
//////                        isStockAvailabilityContain = true;
//////                    }
//////                }
////                if (!isStockAvailabilityContain) {
////                    stockAvailabilityFilterList.add(filterModel);
////                }
////            }
////            boolean isReverificationContain = false;
////            //7. reverification filter list.
////            FilterModel filterModel = new FilterModel();
////            filterModel.setName("Reverification");
////            filterModel.setSelected(false);
////            FilterModel finalFilterModel6 = filterModel;
////            isReverificationContain = reverificationList.stream().anyMatch(o -> o.getName().equals(finalFilterModel6.getName()));
////
////
//////            for (int j = 0; j < reverificationList.size(); j++) {
//////                if (reverificationList.get(j).getName().equals(filterModel.getName())) {
//////                    isReverificationContain = true;
//////                }
//////            }
////            if (!isReverificationContain) {
////                reverificationList.add(filterModel);
////            }
//
//            }
//        }
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
                boolean isReverification = false;


                // customer type filter list.
                FilterModel filterModel = new FilterModel();
                filterModel.setName(omsHeaderList.get(i).getCustomerType());
                filterModel.setSelected(false);
                for (int j = 0; j < customerTypeFilterList.size(); j++) {
                    if (customerTypeFilterList.get(j).getName().equals(filterModel.getName())) {
                        isCustomerTypeContain = true;
                    }
                }
                if (!isCustomerTypeContain) {
                    customerTypeFilterList.add(filterModel);
                }

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
            boolean isReverificationContain = false;
            // reverification filter list.
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

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!isScanerBack) {
//            omsHeaderList.clear();
//            mPresenter.fetchFulfilmentOrderList();
//            openOrdersBinding.searchByfulfimentid.setText("");
//        } else {
//            isScanerBack = false;
//        }
    }

    @Override
    public void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body) {
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListForSelectionList = mPresenter.getGlobalTotalOmsHeaderList();
        if (body != null && body.size() > 0) {

            int index = IntStream.range(0, omsHeaderListForSelectionList.size()).filter(i -> omsHeaderListForSelectionList.get(i).getRefno().equals(omsHeaderList.get(getPos).getRefno())).findFirst().orElse(-1);


            if (omsHeaderList.get(getPos).getExpandStatus() == 0) {
                omsHeaderList.get(getPos).setExpandStatus(1);
            } else {
                omsHeaderList.get(getPos).setExpandStatus(0);
            }
            omsHeaderList.get(getPos).setGetOMSTransactionResponse(body.get(0));

            if (index != -1) {
                omsHeaderListForSelectionList.set(index, omsHeaderList.get(getPos));
                mPresenter.setGlobalTotalOmsHeaderList(omsHeaderListForSelectionList);
            }

            /*Collections.sort(omsHeaderList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = dateFormat.parse(o1.getDeliveryDate());
                        date2 = dateFormat.parse(o2.getDeliveryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date1.compareTo(date2);
                }
            });*/
//            Collections.reverse(omsHeaderList);
            fullfilmentAdapter = new FullfilmentAdapter(getContext(), omsHeaderList, this, body, mPresenter.getUserId());
            fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
            openOrdersBinding.fullfilmentRecycler.scrollToPosition(getPos);
            onContinueBtnEnable();
        }
    }

    @Override
    public void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList) {
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListForSelectionList = mPresenter.getGlobalTotalOmsHeaderList();
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            omsHeaderList.get(getPos).setSelected(!omsHeaderList.get(getPos).isSelected());

            int index = IntStream.range(0, omsHeaderListForSelectionList.size()).filter(i -> omsHeaderListForSelectionList.get(i).getRefno().equals(omsHeaderList.get(getPos).getRefno())).findFirst().orElse(-1);
            if (index != -1) {
                omsHeaderListForSelectionList.get(index).setSelected(omsHeaderList.get(getPos).isSelected());
                mPresenter.setGlobalTotalOmsHeaderList(omsHeaderListForSelectionList);
            }


            if (omsHeaderList.get(getPos).isSelected()) {

                omsHeaderList.get(getPos).setGetOMSTransactionResponse(getOMSTransactionResponseList.get(0));
                selectedOmsHeaderList.add(omsHeaderList.get(getPos));
            } else {
                if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                    for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                        if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(getPos).getRefno())) {
                            selectedOmsHeaderList.remove(i);
                            break;
                        }
                    }
                }
            }
            if (fullfilmentAdapter != null) {
                fullfilmentAdapter.notifyItemChanged(itemPos);
            }
            if (selectedOmsHeaderList.size() >= minOrdersAllowed) {
                onContinueBtnEnable();
            }
        }
    }

    @Override
    public void noOrderFound(int count) {
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListPick = mPresenter.getGlobalTotalOmsHeaderList();
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListPicks = omsHeaderListPick.stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
        if (count > 0) {
            openOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
            openOrdersBinding.fullfilmentRecycler.setVisibility(View.VISIBLE);
//            openOrdersBinding.fullfilmentRecyclerPullToRefresh.setVisibility(View.VISIBLE);

//            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderListFileredStaticList.size() + "/" + omsHeaderListPicks.size() + " orders");
//            openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderListFileredStaticList.size() + "/" + omsHeaderListPicks.size() + " orders");
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + omsHeaderListPicks.size() + " orders");

        } else {
//            openOrdersBinding.fullfilmentRecyclerPullToRefresh.setVisibility(View.GONE);
            openOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
            openOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);

//            PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
//            openOrdersBinding.headerOrdersCount.setText("Total 0 orders");
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + omsHeaderListPicks.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderListFileredStaticList.size() + "/" + omsHeaderListPicks.size() + " orders");
//            openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderListFileredStaticList.size() + "/" + omsHeaderListPicks.size() + " orders");
        }
    }

    private boolean isRefreshScreen = false;

    @Override
    public void setFiltersHeaderLists(List<TransactionHeaderResponse.OMSHeader> omsHeaderListlus, boolean isRefresh) {
//        if (openOrdersBinding.fullfilmentRecyclerPullToRefresh.isRefreshing()) {
//            openOrdersBinding.fullfilmentRecyclerPullToRefresh.setRefreshing(false);
//        }
        isRefreshScreen = isRefresh;
        if (omsHeaderListlus != null && omsHeaderListlus.size() > 0) {
            startIndex = 0;
            if (!isRefresh) {
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
                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.stockAvailableCheckbox.setChecked(false);
            }

            List<TransactionHeaderResponse.OMSHeader> omsHeaderListlu;
            omsHeaderListlu = omsHeaderListlus.stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
            if (omsHeaderListlu != null && omsHeaderListlu.size() > 0) {
                for (int i = 0; i < omsHeaderListlu.size(); i++) {

                    boolean isCustomerTypeContain = false;
                    boolean isOrderTypeContain = false;
                    boolean isOrderCategoryContain = false;
                    boolean isPaymentTypeContain = false;
                    boolean isOrderSourceContain = false;
                    boolean isStockAvailabilityContain = false;
                    boolean isPickUpStatusContain = false;
                    boolean isReverification = false;
                    boolean isShippmentTatContain = false;
                    boolean isBillDateTatContain = false;


                    // customer type filter list.
                    FilterModel filterModel = new FilterModel();
                    filterModel.setName(omsHeaderListlu.get(i).getCustomerType());
                    filterModel.setSelected(false);
                    for (int j = 0; j < customerTypeFilterList.size(); j++) {
                        if (customerTypeFilterList.get(j).getName().equals(filterModel.getName())) {
                            isCustomerTypeContain = true;
                        }
                    }
                    if (!isCustomerTypeContain) {
                        customerTypeFilterList.add(filterModel);
                    }

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
                boolean isReverificationContain = false;
                // reverification filter list.
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
            openOrdersBinding.setIsNaxtPage(endIndex != omsHeaderListTotal.size());
            openOrdersBinding.setIsPrevtPage(startIndex != 0);
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
            openOrdersBinding.setIsNaxtPage(endIndex != omsHeaderListTotal.size());
            openOrdersBinding.setIsPrevtPage(startIndex != 0);
            List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
            TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
            omsHeader.setOMSHeader(myLastPosts);
            onSucessfullFulfilmentIdListText(omsHeader);
        } else {
            Toast.makeText(getContext(), "No Next orders", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickPrescriptionPreview(GetOMSTransactionResponse.OrderPrescriptionURL orderPrescriptionURL, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList, int position) {
        startActivity(PrescriptionSliderActivity.getStartActivity(getContext(), prescriptionsList, position));
        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);

//        Dialog prescriptionPreviewDialog = new Dialog(getContext(), R.style.fadeinandoutcustomDialog);
//        DialogPrescriptionPreviewBinding dialogPrescriptionPreviewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_prescription_preview, null, false);
//        prescriptionPreviewDialog.setContentView(dialogPrescriptionPreviewBinding.getRoot());
//        Glide.with(getContext()).load(orderPrescriptionURL.getPerscriptionurl()).into(dialogPrescriptionPreviewBinding.prescriptionPreview);
//        dialogPrescriptionPreviewBinding.setOrderPrescriptionURL(orderPrescriptionURL);
//        dialogPrescriptionPreviewBinding.backArrow.setOnClickListener(v -> prescriptionPreviewDialog.dismiss());
//        prescriptionPreviewDialog.show();
    }

    @Override
    public void onSuccessGetOmsTransactionAutoAssign(List<GetOMSTransactionResponse> getOMSTransactionResponseList) {
        omsHeaderList.get(autoAssignPos).setSelected(true);
        if (omsHeaderList.get(autoAssignPos).isSelected()) {
            omsHeaderList.get(autoAssignPos).setGetOMSTransactionResponse(getOMSTransactionResponseList.get(0));
            selectedOmsHeaderList.add(omsHeaderList.get(autoAssignPos));
            autoAssignPos++;
            autoAssign();
        }
    }

    boolean isScanerBack;

    @Override
    public void onClickScanCode() {
        isScanerBack = true;
        BillerOrdersActivity.isBillerActivity = true;
        isopenOrderActivity = true;
        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);

//        Intent intent = new Intent(OpenOrdersActivity.this, ScannerActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);


    }

    @Override
    public void onStockAvailability() {

        List<TransactionHeaderResponse.OMSHeader> filterList = new ArrayList<>();
        for (int i = 0; i < mPresenter.getTotalOmsHeaderList().size(); i++) {

            if (!mPresenter.getTotalOmsHeaderList().get(i).getOrderPickup() && mPresenter.getTotalOmsHeaderList().get(i).getStockStatus().equalsIgnoreCase("stock available")) {
                filterList.add(mPresenter.getTotalOmsHeaderList().get(i));
            }

            Collections.sort(filterList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = dateFormat.parse(o1.getDeliveryDate());
                        date2 = dateFormat.parse(o2.getDeliveryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date1.compareTo(date2);
                }
            });
//            Collections.reverse(filterList);
            fullfilmentAdapter = new FullfilmentAdapter(getContext(), filterList, this, null, mPresenter.getUserId());
            fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);


        }


    }

    @Override
    public void onClickItem() {
        onFullfillmentItemClick(getPos, itemPos, null);
    }

    @Override
    public void onClickStausIcon(int fullFillmentPos, int pos) {
//        Dialog statusUpdateDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
//        DialogUpdateStatusPBinding dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_update_status_p, null, false);
//        statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
//        statusUpdateDialog.setCancelable(false);
//        dialogUpdateStatusBinding.dismissDialog.setOnClickListener(view -> statusUpdateDialog.dismiss());
//
//        omsHeaderList.get(pos).getFullfillmentDetails().get(0).getProducts().get(0).getItemStatus();
//
//        statusUpdateDialog.show();
    }


    int itemPos;

    @SuppressLint("SetTextI18n")
    @Override
    public void onFullfillmentItemClick(int pos, int itemPos, TransactionHeaderResponse.OMSHeader omsHeader) {
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListForSelectionList = mPresenter.getGlobalTotalOmsHeaderList();


        this.getPos = pos;
        this.itemPos = itemPos;
        if (omsHeaderList.get(pos).isSelected()) {
            omsHeaderList.get(pos).setSelected(false);
            int index = IntStream.range(0, omsHeaderListForSelectionList.size()).filter(i -> omsHeaderListForSelectionList.get(i).getRefno().equals(omsHeaderList.get(pos).getRefno())).findFirst().orElse(-1);
            if (index != -1) {
                omsHeaderListForSelectionList.get(index).setSelected(false);
                mPresenter.setGlobalTotalOmsHeaderList(omsHeaderListForSelectionList);
            }
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(getPos).getRefno())) {
                        selectedOmsHeaderList.remove(i);
                    }
                }
            }
            if (fullfilmentAdapter != null) {
                fullfilmentAdapter.notifyItemChanged(itemPos);
            }
            onContinueBtnEnable();
        } else {
//            maxOrdersAllowed = 5;
            if (maxOrdersAllowed > selectedOmsHeaderList.size()) {
                mPresenter.onGetOmsTransaction(omsHeaderList.get(pos).getRefno(), true, false);
            } else {
                Toast.makeText(getContext(), "You have selected max allowed orders", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//    }

    @Override
    public void onClickContinue() {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            startActivityForResult(ReadyForPickUpActivity.getStartActivity(getContext(), selectedOmsHeaderList), READYFORPICKUP);
            getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        } else {
            Toast.makeText(getContext(), "No Orders Selected.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRightArrowClickedContinue(int position) {
//        if (racksDataResponse.getFullfillmentDetails() != null && racksDataResponse.getFullfillmentDetails().size() > 0 && racksDataResponse.getFullfillmentDetails().size() > pos) {
//
//            Intent i = new Intent(OpenOrdersActivity.this, OrderDetailsActivity.class);
//            i.putExtra("fullfillmentDetails", racksDataResponse.getFullfillmentDetails().get(position));
//            startActivityForResult(i, 999);
//            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//
//            openOrdersBinding.layoutFulfilment.setVisibility(View.VISIBLE);
//            FulfimentDetailsAdapter fulfimentDetailsAdapter = new FulfimentDetailsAdapter(productDataList, this);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//            openOrdersBinding.rackRecycler.setLayoutManager(mLayoutManager);
//            openOrdersBinding.rackRecycler.setAdapter(fulfimentDetailsAdapter);
//        }
    }

    int gotId;
    boolean isAnyoneSelect = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 999 && resultCode == getActivity().RESULT_OK) {
            RacksDataResponse.FullfillmentDetail fullfillmentIdNew = (RacksDataResponse.FullfillmentDetail) data.getSerializableExtra("FullfillmentID");
            boolean isSelect = (Boolean) data.getSerializableExtra("isSelect");
            if (fullfillmentIdNew != null) {
                for (int i = 0; i < omsHeaderList.size(); i++) {
                    if (fullfillmentIdNew.getFullfillmentId().equals(omsHeaderList.get(i).getVendorId())) {
                        omsHeaderList.get(i).setSelected(isSelect);
                        fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
                        fullfilmentAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                int selectedItemCount = 0;
                for (TransactionHeaderResponse.OMSHeader fullfilmentModel : omsHeaderList)
                    if (fullfilmentModel.isSelected()) {
                        isAnyoneSelect = true;
                        selectedItemCount++;
                    }

                if (isAnyoneSelect) {
                    openOrdersBinding.selectedFullfillment.setText("Selected fulfilment " + selectedItemCount + "/" + omsHeaderList.size());
                    openOrdersBinding.continueBtn.setBackgroundColor(getContext().getResources().getColor(R.color.continue_select_color));
                    openOrdersBinding.setIsContinueSelect(true);
                } else {
                    openOrdersBinding.selectedFullfillment.setText("Select fulfilment to start pichup process.");
                    openOrdersBinding.continueBtn.setBackgroundColor(getContext().getResources().getColor(R.color.continue_unselect_color));
                    openOrdersBinding.setIsContinueSelect(false);
                }
                openOrdersBinding.selectedItemCount.setText(selectedItemCount + "/" + omsHeaderList.size());
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == READYFORPICKUP) {
            isBackFromReadyforPickupScreen = true;
            openOrdersBinding.searchByfulfimentid.setText("");
            selectedOmsHeaderList.clear();
            omsHeaderList.clear();
            if (fullfilmentAdapter != null) {
                fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
                fullfilmentAdapter.notifyDataSetChanged();
            }
            onContinueBtnEnable();
            mPresenter.fetchFulfilmentOrderList(false);

        } else {
            IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (Result != null) {
                if (Result.getContents() == null) {
//                    Toast.makeText(getContext(), "cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                    openOrdersBinding.searchByfulfimentid.setText(Result.getContents());
                    BillerOrdersActivity.isBillerActivity = false;
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onClickStockAvailable(boolean isStockAvailableChecked) {
        openOrdersBinding.searchByfulfimentid.setText("");
        List<TransactionHeaderResponse.OMSHeader> totalGlobalList = omsHeaderListTotal;
//        omsHeaderList = mPresenter.getTotalOmsHeaderList();
        this.isStockAvailableChecked = isStockAvailableChecked;
        if (isStockAvailableChecked) {
//            if (stockAvailabilityFilterList != null && stockAvailabilityFilterList.size()>0){
//                for (int i = 0; i< stockAvailabilityFilterList.size(); i++){
//                    if (stockAvailabilityFilterList.get(i).getName().equalsIgnoreCase("STOCK AVAILABLE")){
//                        stockAvailabilityFilterList.get(i).set
//                    }
//                }
//            }
            for (int i = 0; i < totalGlobalList.size(); i++) {
                if (!totalGlobalList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                    totalGlobalList.remove(i);
                    i--;
                }
            }
            if (totalGlobalList != null && totalGlobalList.size() > 0) {

                omsHeaderListFileredStaticList = totalGlobalList;
                startIndex = 0;
                TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
                omsHeader.setOMSHeader(totalGlobalList);
                onSucessfullFulfilmentIdList(omsHeader);

//                fullfilmentAdapter = new FullfilmentAdapter(getContext(), omsHeaderList, this, null, mPresenter.getUserId());
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
//                openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
//                noOrderFound(omsHeaderList.size());
            } else {
                omsHeaderListFileredStaticList.clear();
                noOrderFound(0);
            }
//            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
//            openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
        } else {
            applyOrderFilters();
        }
////        omsHeaderList = mPresenter.getTotalOmsHeaderList();
//        this.isStockAvailableChecked = isStockAvailableChecked;
//        if (isStockAvailableChecked) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//                if (!omsHeaderList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//                    omsHeaderList.remove(i);
//                    i--;
//                }
//            }
//            if (omsHeaderList != null && omsHeaderList.size() > 0) {
//
//                fullfilmentAdapter = new FullfilmentAdapter(getContext(), omsHeaderList, this, null, mPresenter.getUserId());
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
//                openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
//                noOrderFound(omsHeaderList.size());
//            } else {
//                noOrderFound(0);
//            }
//            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
//            openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
//        } else {
//            applyOrderFilters();
//        }


//        this.isStockAvailable = isStockAvailableChecked;
//
//        if (stockAvailabilityFilterList != null && stockAvailabilityFilterList.size() > 0) {
//            if (isStockAvailableChecked) {
//                for (int i = 0; i < stockAvailabilityFilterList.size(); i++) {
//                    if (stockAvailabilityFilterList.get(i).getName().equalsIgnoreCase("STOCK AVAILABLE")) {
//                        stockAvailabilityFilterList.get(i).setSelected(true);
//                    } else {
//                        stockAvailabilityFilterList.get(i).setSelected(false);
//                    }
//                }
//            } else {
//                for (int i = 0; i < stockAvailabilityFilterList.size(); i++) {
//                    if (stockAvailabilityFilterList.get(i).getName().equalsIgnoreCase("STOCK AVAILABLE")) {
//                        stockAvailabilityFilterList.get(i).setSelected(false);
//                    }
//                }
//            }
//        }
//        applyOrderFilters();


//        if (stockAvailabilityFilterList != null && stockAvailabilityFilterList.size() > 0) {
//            boolean isStockAvailable = false;
//            for (int i = 0; i < stockAvailabilityFilterList.size(); i++) {
//                if (stockAvailabilityFilterList.get(i).getName().equalsIgnoreCase("STOCK AVAILABLE")) {
//                    isStockAvailable = true;
//                    if (isStockAvailableChecked) {
//                        stockAvailabilityFilterList.get(i).setSelected(true);
//
//                    } else {
//                        stockAvailabilityFilterList.get(i).setSelected(false);
//                    }
//                    applyOrderFilters();
//                    break;
//                }
//            }
//            if (!isStockAvailable) {
//                applyOrderFilters();
//            }
//        }
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

    private boolean isRefresh = false;

    @Override
    public void onClickRefreshPickerPackerBiller() {
        selectedOmsHeaderList.clear();
        isShiipimentDateFiltered = false;
        openOrdersBinding.searchByfulfimentid.setText("");
        omsHeaderList.clear();
        onContinueBtnEnable();
        mPresenter.fetchFulfilmentOrderList(true);
    }


    private void onContinueBtnEnable() {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0 && selectedOmsHeaderList.size() >= minOrdersAllowed) {
            openOrdersBinding.selectedFullfillment.setText("Selected fulfilment " + selectedOmsHeaderList.size() + "/" + mPresenter.getGlobalConfiguration().getMPOSMaxOrderAllowed());
            openOrdersBinding.continueBtn.setBackgroundColor(getContext().getResources().getColor(R.color.continue_select_color));
            openOrdersBinding.setIsContinueSelect(true);
            openOrdersBinding.selectedItemCount.setText(selectedOmsHeaderList.size() + "/" + mPresenter.getGlobalConfiguration().getMPOSMaxOrderAllowed());
        } else {
            openOrdersBinding.selectedFullfillment.setText("Select fulfilment to start pickup process.");
            openOrdersBinding.continueBtn.setBackgroundColor(getContext().getResources().getColor(R.color.continue_unselect_color));
            openOrdersBinding.setIsContinueSelect(false);
        }
    }

    @Override
    public void onClickFilters() {
        onClickFilterIcon();


    }

    @Override
    public void onItemClick() {
        List<TransactionHeaderResponse.OMSHeader> filterList = new ArrayList<>();
        for (int i = 0; i < mPresenter.getTotalOmsHeaderList().size(); i++) {

            if (!mPresenter.getTotalOmsHeaderList().get(i).getOrderPickup() && mPresenter.getTotalOmsHeaderList().get(i).getStockStatus().equalsIgnoreCase("stock available")) {
                filterList.add(mPresenter.getTotalOmsHeaderList().get(i));
            }

            Collections.sort(filterList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = dateFormat.parse(o1.getDeliveryDate());
                        date2 = dateFormat.parse(o2.getDeliveryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date1.compareTo(date2);
                }
            });
//            Collections.reverse(filterList);
            fullfilmentAdapter = new FullfilmentAdapter(getContext(), filterList, this, null, mPresenter.getUserId());
            fullfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);


        }
    }

    int lastIndex = 0;

    private void prevNextListeners() {
        openOrdersBinding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotalLu = mPresenter.getGlobalTotalOmsHeaderList();
                if (startIndex >= 100) {
                    startIndex = startIndex - 100;

                    if (lastIndex != 0) {
                        endIndex = endIndex - lastIndex;
                        lastIndex = 0;
                    } else {
                        endIndex = endIndex - 100;
                    }

                    List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
                    TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
                    omsHeader.setOMSHeader(myLastPosts);
                    onSucessfullFulfilmentIdListText(omsHeader);

                } else {
                    Toast.makeText(getContext(), "No Previous orders", Toast.LENGTH_SHORT).show();
                }


            }
        });
        openOrdersBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotalLu = mPresenter.getGlobalTotalOmsHeaderList();


                if (omsHeaderListTotal.size() - 1 > endIndex) {
                    startIndex = startIndex + 100;
                    if (omsHeaderListTotal != null && omsHeaderListTotal.size() >= endIndex + 100) {
                        endIndex = endIndex + 100;
                    } else {
                        lastIndex = omsHeaderListTotal.size() - endIndex;
                        endIndex = omsHeaderListTotal.size();
                    }

                    List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
                    TransactionHeaderResponse omsHeader = new TransactionHeaderResponse();
                    omsHeader.setOMSHeader(myLastPosts);
                    onSucessfullFulfilmentIdListText(omsHeader);
                } else {
                    Toast.makeText(getContext(), "No Next orders", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
