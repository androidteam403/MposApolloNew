package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOpenOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FullfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OpenOrdersActivity extends BaseActivity implements OpenOrdersMvpView {
    @Inject
    OpenOrdersMvpPresenter<OpenOrdersMvpView> mPresenter;
    private ActivityOpenOrdersPBinding openOrdersBinding;
    //    private List<FullfilmentAdapter.FullfilmentModel> fullfilmentModelList;
    private FullfilmentAdapter fullfilmentAdapter;

    private List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList;
    private List<TransactionHeaderResponse.OMSHeader> filteredOmsHeaderList;
    //    public List<RackAdapter.RackBoxModel.ProductData> productDataList;
    public List<GetOMSTransactionResponse> getOMSTransactionResponseList;
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = new ArrayList<>();
    public static String TOTAL_ORDERS = null;

    private List<FilterModel> customerTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterList = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderSourceFilterList = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterList = new ArrayList<>();


    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter;


    public static Intent getStartActivity(Context context) {
        return new Intent(context, OpenOrdersActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_open_orders_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OpenOrdersActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        openOrdersBinding.setCallback(mPresenter);
        mPresenter.fetchFulfilmentOrderList();
        searchByFulfilmentId();
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
                if (editable.length() >= 2) {
                    if (fullfilmentAdapter != null) {
                        fullfilmentAdapter.getFilter().filter(editable);
                    }
                } else {
                    if (fullfilmentAdapter != null) {
                        fullfilmentAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }

    int getPos;

    @Override
    public void ondownArrowClicked(int position) {
        this.getPos = position;
        if (omsHeaderList.get(position).getExpandStatus() == 1) {
            omsHeaderList.get(position).setExpandStatus(0);
            if (fullfilmentAdapter != null) {
                fullfilmentAdapter.notifyDataSetChanged();
            }
            onContinueBtnEnable();
        } else {
            mPresenter.onGetOmsTransaction(omsHeaderList.get(position).getRefno(), false);
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

    @Override
    public void onClickFilterIcon() {
        Dialog filterDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
        DialogFilterPBinding dialogFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_filter_p, null, false);
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

    private void applyOrderFilters() {
        omsHeaderList.clear();

        // Customer type filter list.
        List<TransactionHeaderResponse.OMSHeader> customerTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
        List<TransactionHeaderResponse.OMSHeader> orderTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
        List<TransactionHeaderResponse.OMSHeader> orderCategoryOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
        List<TransactionHeaderResponse.OMSHeader> paymentTypeOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
        List<TransactionHeaderResponse.OMSHeader> orderSourceOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
        List<TransactionHeaderResponse.OMSHeader> stockAvailabilityOMSHeaderFilter = mPresenter.getTotalOmsHeaderList();
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
            omsHeaderList = mPresenter.getTotalOmsHeaderList();
        }
        openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
        fullfilmentAdapter = new FullfilmentAdapter(this, omsHeaderList, this, null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
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
        customerTypeFilterAdapter = new FilterItemAdapter(this, customerTypeFilterList);
        dialogFilterBinding.customerTypeFilter.setLayoutManager(new GridLayoutManager(this, 3));
        dialogFilterBinding.customerTypeFilter.setAdapter(customerTypeFilterAdapter);

        orderTypeFilterAdapter = new FilterItemAdapter(this, orderTypeFilterList);
        dialogFilterBinding.orderTypeFilter.setLayoutManager(new GridLayoutManager(this, 3));
        dialogFilterBinding.orderTypeFilter.setAdapter(orderTypeFilterAdapter);

        orderCategoryFilterAdapter = new FilterItemAdapter(this, orderCategoryFilterList);
        dialogFilterBinding.orderCategoryFilter.setLayoutManager(new GridLayoutManager(this, 3));
        dialogFilterBinding.orderCategoryFilter.setAdapter(orderCategoryFilterAdapter);

        paymentTypeFilterAdapter = new FilterItemAdapter(this, paymentTypeFilterList);
        dialogFilterBinding.paymentTypeFilter.setLayoutManager(new GridLayoutManager(this, 3));
        dialogFilterBinding.paymentTypeFilter.setAdapter(paymentTypeFilterAdapter);

        orderSourceFilterAdapter = new FilterItemAdapter(this, orderSourceFilterList);
        dialogFilterBinding.orderSourceFilter.setLayoutManager(new GridLayoutManager(this, 3));
        dialogFilterBinding.orderSourceFilter.setAdapter(orderSourceFilterAdapter);

        stockAvailabilityFilterAdapter = new FilterItemAdapter(this, stockAvailabilityFilterList);
        dialogFilterBinding.stockAvailableFilter.setLayoutManager(new GridLayoutManager(this, 3));
        dialogFilterBinding.stockAvailableFilter.setAdapter(stockAvailabilityFilterAdapter);


    }


    //    public TransactionHeaderResponse omsHeader;
    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {
        for (int i = 0; i < omsHeader.getOMSHeader().size(); i++) {
            if (!omsHeader.getOMSHeader().get(i).getOrderPickup()) {
                omsHeaderList.add(omsHeader.getOMSHeader().get(i));
            }
        }


//        omsHeaderList = omsHeader.getOMSHeader();
        mPresenter.setTotalOmsHeaderList(omsHeaderList);
        openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
        fullfilmentAdapter = new FullfilmentAdapter(this, omsHeaderList, this, null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
        TOTAL_ORDERS = String.valueOf(omsHeaderList.size());
        filterOrdersLists();
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
        }
    }

    @Override
    public void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body) {
        if (omsHeaderList.get(getPos).getExpandStatus() == 0) {
            omsHeaderList.get(getPos).setExpandStatus(1);
        } else {
            omsHeaderList.get(getPos).setExpandStatus(0);
        }
        fullfilmentAdapter = new FullfilmentAdapter(this, omsHeaderList, this, body);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
        openOrdersBinding.fullfilmentRecycler.scrollToPosition(getPos);
        onContinueBtnEnable();
    }

    @Override
    public void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList) {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            omsHeaderList.get(getPos).setSelected(!omsHeaderList.get(getPos).isSelected());
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
            onContinueBtnEnable();
        }
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            openOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
        } else {
            openOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
//        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
//        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);

        Intent intent = new Intent(OpenOrdersActivity.this, ScannerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);


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
    public void onFullfillmentItemClick(int pos, int itemPos) {
        this.getPos = pos;
        this.itemPos = itemPos;
        if (omsHeaderList.get(pos).isSelected()) {
            omsHeaderList.get(pos).setSelected(false);
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(getPos).getRefno())) {
                        selectedOmsHeaderList.remove(i);
                        break;
                    }
                }
            }
            if (fullfilmentAdapter != null) {
                fullfilmentAdapter.notifyItemChanged(itemPos);
            }
            onContinueBtnEnable();
        } else {
            if (mPresenter.getGlobalConfiguration().getMPOSMaxOrderAllowed() > selectedOmsHeaderList.size()) {
                mPresenter.onGetOmsTransaction(omsHeaderList.get(pos).getRefno(), true);
            } else {
                Toast.makeText(this, "You have selected max allowed orders", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClickContinue() {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            startActivity(ReadyForPickUpActivity.getStartActivity(this, selectedOmsHeaderList));
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        } else {
            Toast.makeText(this, "No Orders Selected.", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            RacksDataResponse.FullfillmentDetail fullfillmentIdNew = (RacksDataResponse.FullfillmentDetail) data.getSerializableExtra("FullfillmentID");
            boolean isSelect = (Boolean) data.getSerializableExtra("isSelect");
            if (fullfillmentIdNew != null) {
                for (int i = 0; i < omsHeaderList.size(); i++) {
                    if (fullfillmentIdNew.getFullfillmentId().equals(omsHeaderList.get(i).getVendorId())) {
                        omsHeaderList.get(i).setSelected(isSelect);
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
                    openOrdersBinding.selectedFullfillment.setText("Selected fullfillment " + selectedItemCount + "/" + omsHeaderList.size());
                    openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_select_color));
                    openOrdersBinding.setIsContinueSelect(true);
                } else {
                    openOrdersBinding.selectedFullfillment.setText("Select fullfilment to start pichup process.");
                    openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
                    openOrdersBinding.setIsContinueSelect(false);
                }
                openOrdersBinding.selectedItemCount.setText(selectedItemCount + "/" + omsHeaderList.size());
            }
        } else {
            IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (Result != null) {
                if (Result.getContents() == null) {
                    Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                    openOrdersBinding.searchByfulfimentid.setText(Result.getContents());
                    BillerOrdersActivity.isBillerActivity = false;
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void onContinueBtnEnable() {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            openOrdersBinding.selectedFullfillment.setText("Selected fullfillment " + selectedOmsHeaderList.size() + "/" + mPresenter.getGlobalConfiguration().getMPOSMaxOrderAllowed());
            openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_select_color));
            openOrdersBinding.setIsContinueSelect(true);
            openOrdersBinding.selectedItemCount.setText(selectedOmsHeaderList.size() + "/" + mPresenter.getGlobalConfiguration().getMPOSMaxOrderAllowed());
        } else {
            openOrdersBinding.selectedFullfillment.setText("Select fullfilment to start pichup process.");
            openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
            openOrdersBinding.setIsContinueSelect(false);
        }
    }
}
