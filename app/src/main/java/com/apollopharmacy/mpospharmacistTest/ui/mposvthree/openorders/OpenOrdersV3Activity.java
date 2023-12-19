package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
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
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOpenOrdersV3Binding;
import com.apollopharmacy.mpospharmacistTest.databinding.PickerV3FilterDialogBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.home.V3NavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.adapter.FulfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

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

import javax.inject.Inject;

public class OpenOrdersV3Activity extends BaseFragment implements OpenOrdersV3MvpView, V3NavigationActivity.V3NavigationActivityCallback {
    public ActivityOpenOrdersV3Binding openOrdersV3Binding;
    private int startIndex = 0;
    private int endIndex = 100;
    public static String TOTAL_ORDERS = null;

    // Main filters headers list
    private List<FilterModel> customerTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterList = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderSourceFilterList = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterList = new ArrayList<>();
    private List<FilterModel> reverificationList = new ArrayList<>();
    private List<FilterModel> shippmentTatFilterList = new ArrayList<>();
    private List<FilterModel> billDateTatFilterList = new ArrayList<>();

    // Temp filters headers list
    private List<FilterModel> customerTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterListTemp = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderSourceFilterListTemp = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterListTemp = new ArrayList<>();
    private List<FilterModel> reverificationListTemp = new ArrayList<>();
    private List<FilterModel> billDateTatFilterListTemp = new ArrayList<>();
    private List<FilterModel> shippmentTatFilterListTemp = new ArrayList<>();

    private static List<TransactionHeaderResponse.OMSHeader> omsHeaderListFileredStaticList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotal = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    private ArrayList<String> filterTypeList = new ArrayList<>();
    private boolean isShiipimentDateFiltered = false;
    public FulfilmentAdapter fulfilmentAdapter;
    int maxOrdersAllowed = 0;
    int minOrdersAllowed = 0;
    private boolean isStockAvailableChecked = false;
    private boolean isBillDateFiltered = false;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = new ArrayList<>();
    private boolean isBackFromReadyforPickupScreen = false;
    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter, reverificationAdapter, shippingTatFilterAdapter, billDateTatFilterAdapter;

    @Inject
    OpenOrdersV3MvpPresenter<OpenOrdersV3MvpView> mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        openOrdersV3Binding = DataBindingUtil.inflate(inflater, R.layout.activity_open_orders_v3, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OpenOrdersV3Activity.this);
        return openOrdersV3Binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        V3NavigationActivity.mInstance.setWelcome("");
        V3NavigationActivity.mInstance.setTitle("Open Orders");
        V3NavigationActivity.mInstance.setStockAvailableVisibilty(true);
        V3NavigationActivity.mInstance.v3NavigationBinding.icFilter.setVisibility(View.VISIBLE);
        V3NavigationActivity.mInstance.v3NavigationBinding.icPaperSize.setVisibility(View.GONE);
        V3NavigationActivity.mInstance.v3NavigationBinding.refresh.setVisibility(View.GONE);
        V3NavigationActivity.mInstance.v3NavigationBinding.unHold.setVisibility(View.GONE);
        V3NavigationActivity.mInstance.v3NavigationBinding.stockAvailableCheckbox.setChecked(false);
        V3NavigationActivity.mInstance.v3NavigationBinding.pageNo.setText("");
        V3NavigationActivity.mInstance.v3NavigationBinding.refreshPickerPackerBiller.setVisibility(View.VISIBLE);
        V3NavigationActivity.mInstance.v3NavigationBinding.stockAvailableCheckbox.setVisibility(View.GONE);

        V3NavigationActivity.mInstance.callback = this;
        mPresenter.fetchFulfilmentOrderList(false);
        List<UserModel._DropdownValueBean> maxMinOrdersList = mPresenter.getMaxMinOrdersList();
        for (int i = 0; i < maxMinOrdersList.size(); i++) {
            if (mPresenter.getUserId().equalsIgnoreCase(maxMinOrdersList.get(i).getCode())) {
                maxOrdersAllowed = Integer.parseInt(maxMinOrdersList.get(i).getMaximumOrders());
                minOrdersAllowed = Integer.parseInt(maxMinOrdersList.get(i).getMinimumOrders());
            }
        }
    }

    private boolean isRefreshScreen = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void setFiltersHeaderLists(List<TransactionHeaderResponse.OMSHeader> omsHeaderList, boolean isRefresh) {
        isRefreshScreen = isRefresh;
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
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
            }
            List<TransactionHeaderResponse.OMSHeader> omsHeaderListlu;
            omsHeaderListlu = omsHeaderList.stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {
        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
            omsHeaderListTotal = omsHeader.getOMSHeader().stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
            if (omsHeaderListTotal != null && omsHeaderListTotal.size() >= 5000) {
                startIndex = 0;
                endIndex = 5000;
            } else {
                endIndex = omsHeaderListTotal.size();
            }
            openOrdersV3Binding.setIsNaxtPage(endIndex != omsHeaderListTotal.size());
            openOrdersV3Binding.setIsPrevtPage(startIndex != 0);

            List<TransactionHeaderResponse.OMSHeader> myLastPosts = omsHeaderListTotal.subList(startIndex, endIndex);
            omsHeader.setOMSHeader(myLastPosts);
            onSucessfullFulfilmentIdListText(omsHeader);
        } else {
            noOrderFound(0);
        }
    }

    List<TransactionHeaderResponse.OMSHeader> maxOrdersList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSucessfullFulfilmentIdListText(TransactionHeaderResponse omsHeader) {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            omsHeaderList.clear();
        }
        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
            omsHeaderList = omsHeader.getOMSHeader().stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
            Collections.sort(omsHeaderList, new Comparator<TransactionHeaderResponse.OMSHeader>() {
                @Override
                public int compare(TransactionHeaderResponse.OMSHeader o1, TransactionHeaderResponse.OMSHeader o2) {
                    return -o1.getStockStatus().compareTo(o2.getStockStatus());
                }
            });
            mPresenter.setTotalOmsHeaderList(omsHeaderList);
            V3NavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
//            openOrdersV3Binding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
            openOrdersV3Binding.total.setText(String.valueOf(omsHeader.getOMSHeader().size()));
            openOrdersV3Binding.assigned.setText(String.valueOf(omsHeader.getOMSHeader().size()));
            long pendingCount = omsHeader.getOMSHeader().stream().filter(e -> e.getPickPackStatus().equalsIgnoreCase("1") && mPresenter.getUserId().equalsIgnoreCase(e.getPickPackUser())).count();
            openOrdersV3Binding.pending.setText(String.valueOf(pendingCount));

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
            if (minOrdersAllowed > 0 && maxOrdersAllowed > 0) {
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
            }
            fulfilmentAdapter = new FulfilmentAdapter(getContext(), omsHeaderList, this, null, mPresenter.getUserId());
            fulfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            openOrdersV3Binding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersV3Binding.fullfilmentRecycler.setAdapter(fulfilmentAdapter);
            openOrdersV3Binding.fullfilmentRecycler.addItemDecoration(new HeaderItemDecoration(openOrdersV3Binding.fullfilmentRecycler, fulfilmentAdapter, getContext()));
            if (maxOrdersAllowed > 0) {
                maxOrdersList = omsHeaderList.stream().limit(maxOrdersAllowed).collect(Collectors.toList());
                mPresenter.mposPickPackOrderReservationApiCall(1, maxOrdersList);
            }
            if (endIndex % 100 == 0) {
                V3NavigationActivity.mInstance.v3NavigationBinding.pageNo.setText("Page No." + (endIndex / 100));
            } else {
                V3NavigationActivity.mInstance.v3NavigationBinding.pageNo.setText("Page No." + ((startIndex / 100) + 1));
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
            V3NavigationActivity.mInstance.v3NavigationBinding.stockAvailableCheckbox.setChecked(V3NavigationActivity.mInstance.v3NavigationBinding.stockAvailableCheckbox.isChecked());
        } else {
            if (!isBackFromReadyforPickupScreen) {
//                showLoading();
//                autoAssign();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        }
        // Order category filter list.
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
        }

        // Payment type filter list.
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
        }
        // Order source filter list.
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
        }
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
        }

        // BillDateTat filter list
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
        }

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
        } else {
            omsHeaderListFileredStaticList.clear();
            noOrderFound(0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void noOrderFound(int count) {
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListPick = mPresenter.getGlobalTotalOmsHeaderList();
        List<TransactionHeaderResponse.OMSHeader> omsHeaderListPicks = omsHeaderListPick.stream().filter(e -> !e.getOrderPickup()).collect(Collectors.toList());
        if (count > 0) {
            openOrdersV3Binding.noOrderFoundText.setVisibility(View.GONE);
            openOrdersV3Binding.fullfilmentRecycler.setVisibility(View.VISIBLE);
            V3NavigationActivity.mInstance.setWelcome("Total " + count + "/" + omsHeaderListPicks.size() + " orders");
        } else {
            openOrdersV3Binding.fullfilmentRecycler.setVisibility(View.GONE);
            openOrdersV3Binding.noOrderFoundText.setVisibility(View.VISIBLE);
            V3NavigationActivity.mInstance.setWelcome("Total " + count + "/" + omsHeaderListPicks.size() + " orders");
        }
    }

    @Override
    public void onClickPrescriptionPreview(GetOMSTransactionResponse.OrderPrescriptionURL orderPrescriptionURL, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList, int position) {
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 1 && mposPickPackOrderReservationResponse.getReturnMessage().equalsIgnoreCase("")) {
            if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
                if (maxOrdersList != null && maxOrdersList.size() > 0) {
                    for (int i = 0; i < maxOrdersList.size(); i++) {
                        maxOrdersList.get(i).setPickupReserved(true);
                    }
                }
            }
        }
    }

    int getPos;

    @Override
    public void ondownArrowClicked(String refId, int position) {
        for (int i = 0; i < omsHeaderList.size(); i++) {
            if (omsHeaderList.get(i).getRefno().equals(refId)) {
                this.getPos = i;
                if (omsHeaderList.get(i).getExpandStatus() == 1) {
                    omsHeaderList.get(i).setExpandStatus(0);
                    if (fulfilmentAdapter != null) {
                        fulfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
                        fulfilmentAdapter.notifyDataSetChanged();
                    }
                } else {
                    mPresenter.onGetOmsTransaction(omsHeaderList.get(i).getRefno(), false, false, false);
                }
                break;
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
                    mPresenter.onGetOmsTransaction(omsHeaderList.get(autoAssignPos).getRefno(), true, true, false);
                }
            } else {
                autoAssignPos++;
                autoAssign();
            }
        } else {
            hideLoading();
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                if (fulfilmentAdapter != null) {
                    fulfilmentAdapter.notifyDataSetChanged();
                }
                onContinueBtnEnable();
//                onClickContinue();
            }
        }
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

    int itemPos;

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            if (fulfilmentAdapter != null) {
                fulfilmentAdapter.notifyItemChanged(itemPos);
            }
            if (selectedOmsHeaderList.size() >= minOrdersAllowed) {
                onContinueBtnEnable();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccessGetOmsTransactionBulkSelection(List<GetOMSTransactionResponse> body) {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getRefno().equalsIgnoreCase(body.get(0).getRefno())) {
                    omsHeaderList.get(i).setGetOMSTransactionResponse(body.get(0));
                    selectedOmsHeaderList.add(omsHeaderList.get(i));
                }
            }
        }
        selectedOmsHeaderList = selectedOmsHeaderList.stream().distinct().collect(Collectors.toList());
        fulfilmentAdapter.notifyDataSetChanged();
        onContinueBtnEnable();
//        callGetOmsTransactionApi(availableOmsHeaderList, index + 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            fulfilmentAdapter = new FulfilmentAdapter(getContext(), omsHeaderList, this, body, mPresenter.getUserId());
            fulfilmentAdapter.setDispatchCutoffTime(isShiipimentDateFiltered);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            openOrdersV3Binding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersV3Binding.fullfilmentRecycler.setAdapter(fulfilmentAdapter);
            openOrdersV3Binding.fullfilmentRecycler.scrollToPosition(getPos);
            onContinueBtnEnable();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            if (fulfilmentAdapter != null) {
                fulfilmentAdapter.notifyItemChanged(itemPos);
            }
            onContinueBtnEnable();
        } else {
            if (minOrdersAllowed > 0 && maxOrdersAllowed > 0) {
                if (maxOrdersAllowed > selectedOmsHeaderList.size()) {
                    mPresenter.onGetOmsTransaction(omsHeaderList.get(pos).getRefno(), true, false, false);
                } else {
                    Toast.makeText(getContext(), "You have selected max allowed orders", Toast.LENGTH_SHORT).show();
                }
            } else {
                mPresenter.onGetOmsTransaction(omsHeaderList.get(pos).getRefno(), true, false, false);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void onContinueBtnEnable() {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0 && selectedOmsHeaderList.size() >= minOrdersAllowed) {
            openOrdersV3Binding.selectedFullfillment.setText("Selected fulfilment " + selectedOmsHeaderList.size() + "/" + mPresenter.getGlobalConfiguration().getMPOSMaxOrderAllowed());
            openOrdersV3Binding.continueBtn.setBackgroundColor(getContext().getResources().getColor(R.color.continue_select_color));
            openOrdersV3Binding.setIsContinueSelect(true);
            openOrdersV3Binding.selectedItemCount.setText(selectedOmsHeaderList.size() + "/" + mPresenter.getGlobalConfiguration().getMPOSMaxOrderAllowed());
        } else {
            openOrdersV3Binding.selectedFullfillment.setText("Select fulfilment to start pickup process.");
            openOrdersV3Binding.continueBtn.setBackgroundColor(getContext().getResources().getColor(R.color.continue_unselect_color));
            openOrdersV3Binding.setIsContinueSelect(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClickFilter() {
        if (mPresenter.getTotalOmsHeaderList() != null && mPresenter.getTotalOmsHeaderList().size() > 0) {
            openOrdersV3Binding.searchByfulfimentid.setText("");
            temFiltersHeadersList();
            Dialog filterDialog = new Dialog(getContext(), R.style.fadeinandoutcustomDialog);
            PickerV3FilterDialogBinding pickerV3FilterDialogBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.picker_v3_filter_dialog,
                    null,
                    false
            );
            filterDialog.setContentView(pickerV3FilterDialogBinding.getRoot());
            filterDialog.setCancelable(false);
            filtersList(pickerV3FilterDialogBinding);
            pickerV3FilterDialogBinding.filterCloseIcon.setOnClickListener(view -> {
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
            pickerV3FilterDialogBinding.applyFilters.setOnClickListener(view -> {
                showLoading();
                applyOrderFilters();
//                initFilterTypeAdapter();
                filterDialog.dismiss();
                hideLoading();
            });
            pickerV3FilterDialogBinding.clear.setOnClickListener(view -> {
//                clearFilter();
                filterDialog.dismiss();
                applyOrderFilters();
//                initFilterTypeAdapter();
            });
            filterDialog.show();
        } else {
            Toast.makeText(getContext(), "No Orders are available", Toast.LENGTH_SHORT).show();
        }
    }

    private void filtersList(PickerV3FilterDialogBinding pickerV3FilterDialogBinding) {
        stockAvailabilityFilterAdapter = new FilterItemAdapter(getContext(), stockAvailabilityFilterList);
        pickerV3FilterDialogBinding.stockAvailableFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.stockAvailableFilter.setAdapter(stockAvailabilityFilterAdapter);

        orderTypeFilterAdapter = new FilterItemAdapter(getContext(), orderTypeFilterList);
        pickerV3FilterDialogBinding.orderTypeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.orderTypeFilter.setAdapter(orderTypeFilterAdapter);

        orderCategoryFilterAdapter = new FilterItemAdapter(getContext(), orderCategoryFilterList);
        pickerV3FilterDialogBinding.orderCategoryFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.orderCategoryFilter.setAdapter(orderCategoryFilterAdapter);

        paymentTypeFilterAdapter = new FilterItemAdapter(getContext(), paymentTypeFilterList);
        pickerV3FilterDialogBinding.paymentTypeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.paymentTypeFilter.setAdapter(paymentTypeFilterAdapter);

        orderSourceFilterAdapter = new FilterItemAdapter(getContext(), orderSourceFilterList);
        pickerV3FilterDialogBinding.orderSourceFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.orderSourceFilter.setAdapter(orderSourceFilterAdapter);

        customerTypeFilterAdapter = new FilterItemAdapter(getContext(), customerTypeFilterList);
        pickerV3FilterDialogBinding.customerTypeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.customerTypeFilter.setAdapter(customerTypeFilterAdapter);

        stockAvailabilityFilterAdapter = new FilterItemAdapter(getContext(), stockAvailabilityFilterList);
        pickerV3FilterDialogBinding.stockAvailableFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.stockAvailableFilter.setAdapter(stockAvailabilityFilterAdapter);

        reverificationAdapter = new FilterItemAdapter(getContext(), reverificationList);
        pickerV3FilterDialogBinding.reverificationRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.reverificationRecycler.setAdapter(reverificationAdapter);

        shippingTatFilterAdapter = new FilterItemAdapter(getContext(), shippmentTatFilterList);
        pickerV3FilterDialogBinding.dispatchCuttoffTimeFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.dispatchCuttoffTimeFilter.setAdapter(shippingTatFilterAdapter);

        billDateTatFilterAdapter = new FilterItemAdapter(getContext(), billDateTatFilterList);
        pickerV3FilterDialogBinding.billDateFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        pickerV3FilterDialogBinding.billDateFilter.setAdapter(billDateTatFilterAdapter);
    }

    private void temFiltersHeadersList() {
        customerTypeFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : customerTypeFilterList) {
            FilterModel customerTypeModel = new FilterModel();
            customerTypeModel.setSelected(filterModel.isSelected());
            customerTypeModel.setName(filterModel.getName());
            customerTypeFilterListTemp.add(customerTypeModel);
        }

        orderTypeFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : orderTypeFilterList) {
            FilterModel orderTypeModel = new FilterModel();
            orderTypeModel.setSelected(filterModel.isSelected());
            orderTypeModel.setName(filterModel.getName());
            orderTypeFilterListTemp.add(orderTypeModel);
        }

        orderCategoryFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : orderCategoryFilterList) {
            FilterModel orderCategoryModel = new FilterModel();
            orderCategoryModel.setSelected(filterModel.isSelected());
            orderCategoryModel.setName(filterModel.getName());
            orderCategoryFilterListTemp.add(orderCategoryModel);
        }

        paymentTypeFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : paymentTypeFilterList) {
            FilterModel paymentTypeModel = new FilterModel();
            paymentTypeModel.setSelected(filterModel.isSelected());
            paymentTypeModel.setName(filterModel.getName());
            paymentTypeFilterListTemp.add(paymentTypeModel);
        }

        orderSourceFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : orderSourceFilterList) {
            FilterModel orderSourceModel = new FilterModel();
            orderSourceModel.setSelected(filterModel.isSelected());
            orderSourceModel.setName(filterModel.getName());
            orderSourceFilterListTemp.add(orderSourceModel);
        }

        stockAvailabilityFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : stockAvailabilityFilterList) {
            FilterModel stockAvailabilityModel = new FilterModel();
            stockAvailabilityModel.setSelected(filterModel.isSelected());
            stockAvailabilityModel.setName(filterModel.getName());
            stockAvailabilityFilterListTemp.add(stockAvailabilityModel);
        }

        reverificationListTemp = new ArrayList<>();
        for (FilterModel filterModel : reverificationList) {
            FilterModel reverificationModel = new FilterModel();
            reverificationModel.setSelected(filterModel.isSelected());
            reverificationModel.setName(filterModel.getName());
            reverificationListTemp.add(reverificationModel);
        }

        shippmentTatFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : shippmentTatFilterList) {
            FilterModel shippingTatModel = new FilterModel();
            shippingTatModel.setSelected(filterModel.isSelected());
            shippingTatModel.setName(filterModel.getName());
            shippmentTatFilterListTemp.add(shippingTatModel);
        }

        billDateTatFilterListTemp = new ArrayList<>();
        for (FilterModel filterModel : billDateTatFilterList) {
            FilterModel billDateTatModel = new FilterModel();
            billDateTatModel.setSelected(filterModel.isSelected());
            billDateTatModel.setName(filterModel.getName());
            billDateTatFilterListTemp.add(billDateTatModel);
        }
    }

    @Override
    public void onItemClick() {

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

    }
}