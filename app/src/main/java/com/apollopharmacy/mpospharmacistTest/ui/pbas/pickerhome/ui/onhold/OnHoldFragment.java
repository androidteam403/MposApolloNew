package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.onhold;

import android.app.Dialog;
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
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentOnHoldBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.onhold.adapter.OnHoldAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class OnHoldFragment extends BaseFragment implements OnHoldMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {

    @Inject
    OnHoldMvpPresenter<OnHoldMvpView> mPresenter;

    private FragmentOnHoldBinding onHoldBinding;
    private OnHoldAdapter onHoldAdapter;
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    public static boolean isOnHoldFragment = false;

    // Main filters headers list
    private List<FilterModel> customerTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterList = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderSourceFilterList = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterList = new ArrayList<>();
    private List<FilterModel> reverificationList = new ArrayList<>();

    // Temp filters headers list
    private List<FilterModel> customerTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterListTemp = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterListTemp = new ArrayList<>();
    private List<FilterModel> orderSourceFilterListTemp = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterListTemp = new ArrayList<>();
    private List<FilterModel> reverificationListTemp = new ArrayList<>();

    //filter item adapters
    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter, reverificationAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onHoldBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_hold, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OnHoldFragment.this);
        return onHoldBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        onHoldBinding.setCallback(mPresenter);
        actionbarSetUp();
        searchByFulfilmentId();
        mPresenter.getOMSTransactionHeaderApiCall();
//        mPresenter.sampleApiList();
    }

    private void actionbarSetUp() {
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("ADMIN");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
    }

    private void searchByFulfilmentId() {
        onHoldBinding.searchByfulfimentid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    onHoldBinding.searchIcon.setVisibility(View.GONE);
                    onHoldBinding.deleteCancel.setVisibility(View.VISIBLE);
                    if (onHoldAdapter != null) {
                        onHoldAdapter.getFilter().filter(editable);
                    }
                } else if (onHoldBinding.searchByfulfimentid.getText().toString().equals("")) {
                    if (onHoldAdapter != null) {
                        onHoldAdapter.getFilter().filter("");
                    }
                    onHoldBinding.searchIcon.setVisibility(View.VISIBLE);
                    onHoldBinding.deleteCancel.setVisibility(View.GONE);
                } else {
                    if (onHoldAdapter != null) {
                        onHoldAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }

    @Override
    public void onClickFilters() {
        onClickFilterIcon();
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
        Dialog dialog = new Dialog(getContext());// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialogCancelBinding.wraningIcon.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogCancelBinding.wraningIcon.setVisibility(View.VISIBLE);
        dialogCancelBinding.dialogMessage.setText("Do you want to Unhold all the orders?");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            mPresenter.mposPickPackOrderReservationApiCall(omsHeaderList);
            dialog.dismiss();
        });
    }

    @Override
    public void onClickRefreshPickerPackerBiller() {

    }

    @Override
    public void onSuccessGetOMSHeaderTransactionApi(TransactionHeaderResponse omsHeader) {
        mPresenter.setTotalOmsHeaderList(omsHeader.getOMSHeader());
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
            this.omsHeaderList = omsHeader.getOMSHeader();
            if (omsHeaderList != null && omsHeaderList.size() > 0) {

                mPresenter.setTotalOmsHeaderList(omsHeaderList);

                onHoldAdapter = new OnHoldAdapter(getContext(), omsHeaderList, this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                onHoldBinding.onHoldListRecycler.setLayoutManager(mLayoutManager);
                onHoldBinding.onHoldListRecycler.setAdapter(onHoldAdapter);
                noOrderFound(omsHeaderList.size());
                filterOrdersLists();
            } else {
                noOrderFound(0);
            }
        } else {
            noOrderFound(0);
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
        applyOrderFilters();
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            onHoldBinding.noOrderFoundText.setVisibility(View.GONE);
            onHoldBinding.onHoldListRecycler.setVisibility(View.VISIBLE);
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setBackgroundResource(R.drawable.bg_onhold_enable_btn);
            PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setEnabled(true);
        } else {
            onHoldBinding.onHoldListRecycler.setVisibility(View.GONE);
            onHoldBinding.noOrderFoundText.setVisibility(View.VISIBLE);
            PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
            PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setBackgroundResource(R.drawable.bg_onhold_disable_btn);
            PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setEnabled(false);
        }
    }

    @Override
    public void onClickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
        isOnHoldFragment = true;
        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onClickSearchClear() {
        onHoldBinding.searchByfulfimentid.setText("");
        onHoldBinding.deleteCancel.setVisibility(View.GONE);
        onHoldBinding.searchIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickUnHold(TransactionHeaderResponse.OMSHeader omsHeader) {
        List<TransactionHeaderResponse.OMSHeader> omsHeaders = new ArrayList<>();
        omsHeaders.add(omsHeader);
        Dialog dialog = new Dialog(getContext());// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialogCancelBinding.wraningIcon.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogCancelBinding.wraningIcon.setVisibility(View.VISIBLE);
        dialogCancelBinding.dialogMessage.setText("Do you want to Unhold the order?");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            mPresenter.mposPickPackOrderReservationApiCall(omsHeaders);
            dialog.dismiss();
        });

    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
            mPresenter.getOMSTransactionHeaderApiCall();
        }
    }

    @Override
    public void onClickFilterIcon() {
        if (mPresenter.getTotalOmsHeaderList() != null && mPresenter.getTotalOmsHeaderList().size() > 0) {


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

    }

    private void filtersList(DialogFilterPBinding dialogFilterBinding) {
        dialogFilterBinding.dispatchCuttoffText.setVisibility(View.GONE);
        dialogFilterBinding.dispatchCuttoffTimeFilter.setVisibility(View.GONE);
        dialogFilterBinding.dispatchCuttoffView.setVisibility(View.GONE);

        dialogFilterBinding.billdateTatText.setVisibility(View.GONE);
        dialogFilterBinding.billDateFilter.setVisibility(View.GONE);



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
//        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
//            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
//                for (int j = 0; j < Objects.requireNonNull(omsHeaderList).size(); j++) {
//                    if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(j).getRefno())) {
//                        omsHeaderList.get(j).setSelected(selectedOmsHeaderList.get(i).isSelected());
//                    }
//                }
//            }
//        }

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

//        if (isStockAvailableChecked) {
//            for (int i = 0; i < omsHeaderList.size(); i++) {
//                if (!omsHeaderList.get(i).getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//                    omsHeaderList.remove(i);
//                    i--;
//                }
//            }
//        }

        if (omsHeaderList != null && omsHeaderList.size() > 0) {

            onHoldAdapter = new OnHoldAdapter(getContext(), omsHeaderList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            onHoldBinding.onHoldListRecycler.setLayoutManager(mLayoutManager);
            onHoldBinding.onHoldListRecycler.setAdapter(onHoldAdapter);
            noOrderFound(omsHeaderList.size());
        } else {
            noOrderFound(0);
        }

//        PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() != null) {
                Toast.makeText(getContext(), "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                onHoldBinding.searchByfulfimentid.setText(Result.getContents());
                BillerOrdersActivity.isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    @Override
    public void setStoresList(TransactionHeaderResponse omsHeader) {
        mPresenter.setTotalOmsHeaderList(omsHeader.getOMSHeader());
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            omsHeaderList.clear();
        }

        if (omsHeader != null && omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().size() > 0) {
            this.omsHeaderList = omsHeader.getOMSHeader();
            if (omsHeaderList != null && omsHeaderList.size() > 0) {

                mPresenter.setTotalOmsHeaderList(omsHeaderList);

                onHoldAdapter = new OnHoldAdapter(getContext(), omsHeaderList, this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                onHoldBinding.onHoldListRecycler.setLayoutManager(mLayoutManager);
                onHoldBinding.onHoldListRecycler.setAdapter(onHoldAdapter);
                noOrderFound(omsHeaderList.size());
                filterOrdersLists();
            } else {
                noOrderFound(0);
            }
        } else {
            noOrderFound(0);
        }
    }
}
