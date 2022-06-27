package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<FilterModel> customerTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderSourceFilterList = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterList = new ArrayList<>();
    public static boolean isPickedUpOrdersActivity = false;
    int getPos;
    int itemPos;

    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter;

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
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Picked Orders");
        PickerNavigationActivity.mInstance.setStock("");

//        activityPickedUpOrdersBinding.setCallback(mvpPresenter);
//        mvpPresenter.fetchFulfilmentOrderList();
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
                if (editable.length() >= 2) {
                    activityPickedUpOrdersBinding.search.setVisibility(View.GONE);
                    activityPickedUpOrdersBinding.deleteCancel.setVisibility(View.VISIBLE);
                    if (pickedUpOrdersAdapter != null) {

                        pickedUpOrdersAdapter.getFilter().filter(editable);
                    }

                } else if (activityPickedUpOrdersBinding.searchText.getText().toString().equals("")) {
                    if (pickedUpOrdersAdapter != null) {
                        pickedUpOrdersAdapter.getFilter().filter("");
                    }
                    activityPickedUpOrdersBinding.search.setVisibility(View.VISIBLE);
                    activityPickedUpOrdersBinding.deleteCancel.setVisibility(View.GONE);
                } else {
                    if (pickedUpOrdersAdapter != null) {
                        pickedUpOrdersAdapter.getFilter().filter("");
                    }
                }
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
        if (count > 0) {
            activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
        } else {
            activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClickFilterIcon() {
        Dialog filterDialog = new Dialog(getContext(), R.style.fadeinandoutcustomDialog);
        DialogFilterPBinding dialogFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_filter_p, null, false);
        filterDialog.setContentView(dialogFilterBinding.getRoot());
        filterDialog.setCancelable(false);
        filtersList(dialogFilterBinding);
        dialogFilterBinding.filterCloseIcon.setOnClickListener(view -> {
            applyOrderFilters();
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
        });
        filterDialog.show();
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
        }
    }

    private void applyOrderFilters() {
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
        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
        activityPickedUpOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
        pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);

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

    public void recyclerView() {
        pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
        activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
        activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
    }

    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {
        if (omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
            for (int i = 0; i < omsHeader.getOMSHeader().size(); i++) {
                if (omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().get(i).getOrderPickup() && !omsHeader.getOMSHeader().get(i).getOrderPacked()) {
                    omsHeaderList.add(omsHeader.getOMSHeader().get(i));
                }
                if (omsHeaderList != null && omsHeaderList.size() > 0) {
                    mvpPresenter.setTotalOmsHeaderList(omsHeaderList);
                    PickerNavigationActivity.mInstance.setWelcome("Total" + " " + String.valueOf(omsHeaderList.size()) + " " + "Orders");
                    activityPickedUpOrdersBinding.headerOrdersCount.setText("Total" + " " + String.valueOf(omsHeaderList.size()) + " " + "Orders");
                    activityPickedUpOrdersBinding.zeropicked.setVisibility(View.GONE);

                    pickedUpOrdersAdapter = new PickedUpOrdersAdapter(getContext(), omsHeaderList, this);
                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
                    activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
                    activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);

                    activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
                    activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.VISIBLE);
                    filterOrdersLists();
                } else {
                    PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
                    activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
                    activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
                }
            }
        } else {
            PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
            activityPickedUpOrdersBinding.fullfilmentRecycler.setVisibility(View.GONE);
            activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    @Override
    public void onSuccessGetOMSTransactionList(OMSTransactionResponse response) {


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
    public void onClickFilters() {
        onClickFilterIcon();
    }

    @Override
    public void onItemClick() {

    }


}