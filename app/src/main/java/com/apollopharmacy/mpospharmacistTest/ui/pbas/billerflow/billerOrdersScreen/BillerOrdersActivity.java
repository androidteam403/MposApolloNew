package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBillerOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.adapter.BillerFullfillmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.adapter.PickedUpOrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class BillerOrdersActivity extends BaseFragment implements BillerOrdersMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {

    @Inject
    BillerOrdersMvpPresenter<BillerOrdersMvpView> mPresenter;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    ActivityBillerOrdersPBinding activityBillerOrdersBinding;
    private List<BillerFullfillmentAdapter.FullfilmentModel> fullfilmentModelList;
    private BillerFullfillmentAdapter billerFullfillmentAdapter;
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    public static boolean isBillerActivity;
    private List<FilterModel> customerTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderCategoryFilterList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = new ArrayList<>();
    private List<FilterModel> paymentTypeFilterList = new ArrayList<>();
    private List<FilterModel> orderSourceFilterList = new ArrayList<>();
    private List<FilterModel> stockAvailabilityFilterList = new ArrayList<>();
    private static final int ORDERDETAILS_SCREEN_ACTIVITY = 108;

    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter;

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
        activityBillerOrdersBinding.setScan(mPresenter);
//
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Biller Orders");
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

        if (activityBillerOrdersBinding.searchText.getText().toString().equals("")) {
            activityBillerOrdersBinding.search.setVisibility(View.VISIBLE);
            activityBillerOrdersBinding.deleteCancel.setVisibility(View.GONE);
        }
    }





    @Override
    public void onclickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
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
                } else {
                    if (billerFullfillmentAdapter != null) {
                        billerFullfillmentAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }

    @Override
    public void onRightArrowClickedContinue(int position) {
        if (omsHeaderList != null && omsHeaderList.size() > 0 && omsHeaderList.size() > position) {
            Intent i = new Intent(getContext(), OrderDetailsScreenActivity.class);
            i.putExtra("fullfillmentDetails", omsHeaderList.get(position));
//            startActivityForResult(i, 999);
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        }
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            activityBillerOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
        } else {
            activityBillerOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccessRackApi(RacksDataResponse body) {
        racksDataResponse = body.getFullfillmentDetails();


        activityBillerOrdersBinding.scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBillerActivity = true;
                new IntentIntegrator(getActivity()).setCaptureActivity(com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity.class).initiateScan();
                getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        });
    }

    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {
        for (int i = 0; i < omsHeader.getOMSHeader().size(); i++) {
            if (omsHeader.getOMSHeader() != null && omsHeader.getOMSHeader().get(i).getOrderPickup() && omsHeader.getOMSHeader().get(i).getOrderPacked()) {
                omsHeaderList.add(omsHeader.getOMSHeader().get(i));
            }
            PickerNavigationActivity.mInstance.setWelcome("Total " + omsHeaderList.size() + " orders");

        }
        mPresenter.setTotalOmsHeaderList(omsHeaderList);
        billerFullfillmentAdapter = new BillerFullfillmentAdapter(getContext(), omsHeaderList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        activityBillerOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        activityBillerOrdersBinding.fullfilmentRecycler.setAdapter(billerFullfillmentAdapter);
        filterOrdersLists();
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
        billerFullfillmentAdapter = new BillerFullfillmentAdapter(getContext(), omsHeaderList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        activityBillerOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
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
    public void onClickFilters() {
        onClickFilterIcon();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                activityBillerOrdersBinding.searchText.setText(Result.getContents());
                BillerOrdersActivity.isBillerActivity = false;            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}