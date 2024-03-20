package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityStockAvailableOrdersBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogActivePickersBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders.adapter.AssignedOrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders.adapter.UnAssignedOrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

public class StockAvailableOrdersActivity extends BaseActivity implements StockAvailableOrdersMvpView {
    private ActivityStockAvailableOrdersBinding stockAvailableOrdersBinding;
    @Inject
    StockAvailableOrdersMvpPresenter<StockAvailableOrdersMvpView> mPresenter;
    List<GetOMSTransactionHeaderResponse.OMSHeader> stockAvailableOrders;
    private AssignedOrderAdapter assignedOrderAdapter;
    private UnAssignedOrderAdapter unAssignedOrderAdapter;
    private List<String> activePickers;
    int selectedPickerPosition;
    boolean isAssignedTab = true;
    List<GetOMSTransactionHeaderResponse.OMSHeader> assignedOrders = new ArrayList<>();
    List<GetOMSTransactionHeaderResponse.OMSHeader> unAssignedOrders = new ArrayList<>();
    private GetOMSTransactionHeaderResponse.OMSHeader unAssignedOrder;

    public static Intent getStartIntent(Context context, List<GetOMSTransactionHeaderResponse.OMSHeader> stockAvailableOrders) {
        Intent intent = new Intent(context, StockAvailableOrdersActivity.class);
        intent.putExtra("STOCK_AVAILABLE_ORDERS", (Serializable) stockAvailableOrders);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockAvailableOrdersBinding = DataBindingUtil.setContentView(StockAvailableOrdersActivity.this, R.layout.activity_stock_available_orders);
        getActivityComponent().inject(this);
        mPresenter.onAttach(StockAvailableOrdersActivity.this);
        stockAvailableOrdersBinding.setCallback(mPresenter);
        setUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void setUp() {
        stockAvailableOrdersBinding.setSelectedStatus(1);
        List<UserModel._DropdownValueBean> loginUserDetails = mPresenter.getLoginUserDetails();
        activePickers = loginUserDetails.stream().map(UserModel._DropdownValueBean::getCode).collect(Collectors.toList());
        if (getIntent() != null) {
            stockAvailableOrders = (List<GetOMSTransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("STOCK_AVAILABLE_ORDERS");
        }
        if (stockAvailableOrders != null && stockAvailableOrders.size() > 0) {
            stockAvailableOrdersBinding.stockAvailableOrdersCount.setText("(" + stockAvailableOrders.size() + ")");
        } else {
            stockAvailableOrdersBinding.stockAvailableOrdersCount.setText("(-)");
        }

        unAssignedOrders = stockAvailableOrders.stream()
                .filter(omsHeader -> omsHeader.getPickPackUser() != null && omsHeader.getPickPackUser().isEmpty())
                .collect(Collectors.toList());
        assignedOrders = stockAvailableOrders.stream()
                .filter(omsHeader -> omsHeader.getPickPackUser() != null && !omsHeader.getPickPackUser().isEmpty())
                .collect(Collectors.toList());

        if (assignedOrders.size() > 0) {
            stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.VISIBLE);
            stockAvailableOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
            assignedOrderAdapter = new AssignedOrderAdapter(StockAvailableOrdersActivity.this, assignedOrders, this);
            stockAvailableOrdersBinding.assignedOrdersRcv.setAdapter(assignedOrderAdapter);
            stockAvailableOrdersBinding.assignedOrdersRcv.setLayoutManager(new LinearLayoutManager(StockAvailableOrdersActivity.this));
        } else {
            stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.GONE);
            stockAvailableOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }

        unAssignedOrderAdapter = new UnAssignedOrderAdapter(StockAvailableOrdersActivity.this, unAssignedOrders, this);
        stockAvailableOrdersBinding.unAssignedOrdersRcv.setAdapter(unAssignedOrderAdapter);
        stockAvailableOrdersBinding.unAssignedOrdersRcv.setLayoutManager(new LinearLayoutManager(StockAvailableOrdersActivity.this));


        stockAvailableOrdersBinding.assigned.setOnClickListener(view -> onClickAssigned());
        stockAvailableOrdersBinding.unAssigned.setOnClickListener(view -> onClickUnAssigned());

        stockAvailableOrdersBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchedText = s.toString();
                List<GetOMSTransactionHeaderResponse.OMSHeader> filteredList = new ArrayList<>();
                if (isAssignedTab) {
                    if (searchedText.isEmpty()) {
                        hideKeyboard();
                        filteredList = assignedOrders;
                    } else {
                        for (int i = 0; i < assignedOrders.size(); i++) {
                            if (assignedOrders.get(i).getRefno().toLowerCase().contains(searchedText.toLowerCase()) || assignedOrders.get(i).getPickPackUser().toLowerCase().contains(searchedText.toLowerCase())) {
                                filteredList.add(assignedOrders.get(i));
                            }
                        }
                    }
                    if (assignedOrderAdapter != null) {
                        assignedOrderAdapter.setFilteredList(filteredList);
                    }
                    if (filteredList.size() > 0) {
                        stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.VISIBLE);
                        stockAvailableOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
                    } else {
                        stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.GONE);
                        stockAvailableOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (searchedText.isEmpty()) {
                        hideKeyboard();
                        filteredList = unAssignedOrders;
                    } else {
                        for (int i = 0; i < unAssignedOrders.size(); i++) {
                            if (unAssignedOrders.get(i).getRefno().toLowerCase().contains(searchedText.toLowerCase())) {
                                filteredList.add(unAssignedOrders.get(i));
                            }
                        }
                    }
                    if (unAssignedOrderAdapter != null) {
                        unAssignedOrderAdapter.setFilteredList(filteredList);
                    }
                    if (filteredList.size() > 0) {
                        stockAvailableOrdersBinding.unAssignedOrdersRcv.setVisibility(View.VISIBLE);
                        stockAvailableOrdersBinding.noOrderFound.setVisibility(View.GONE);
                    } else {
                        stockAvailableOrdersBinding.unAssignedOrdersRcv.setVisibility(View.GONE);
                        stockAvailableOrdersBinding.noOrderFound.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void onClickAssigned() {
        isAssignedTab = true;
        stockAvailableOrdersBinding.setSelectedStatus(1);
//        stockAvailableOrdersBinding.assigned.setBackgroundColor(ContextCompat.getColor(StockAvailableOrdersActivity.this, R.color.green));
//        stockAvailableOrdersBinding.unAssigned.setBackgroundColor(ContextCompat.getColor(StockAvailableOrdersActivity.this, R.color.grey));
//        stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.VISIBLE);
//        stockAvailableOrdersBinding.unAssignedOrdersRcv.setVisibility(View.GONE);
        stockAvailableOrdersBinding.assignedTab.setVisibility(View.VISIBLE);
        stockAvailableOrdersBinding.unassignedTab.setVisibility(View.GONE);
        stockAvailableOrdersBinding.search.setHint("Search by Fulfillment ID/User ID");
        stockAvailableOrdersBinding.search.setText("");
        hideKeyboard();
    }

    private void onClickUnAssigned() {
        isAssignedTab = false;
        stockAvailableOrdersBinding.setSelectedStatus(2);
//        stockAvailableOrdersBinding.unAssigned.setBackgroundColor(ContextCompat.getColor(StockAvailableOrdersActivity.this, R.color.green));
//        stockAvailableOrdersBinding.assigned.setBackgroundColor(ContextCompat.getColor(StockAvailableOrdersActivity.this, R.color.grey));
//        stockAvailableOrdersBinding.unAssignedOrdersRcv.setVisibility(View.VISIBLE);
//        stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.GONE);
        stockAvailableOrdersBinding.unassignedTab.setVisibility(View.VISIBLE);
        stockAvailableOrdersBinding.assignedTab.setVisibility(View.GONE);
        stockAvailableOrdersBinding.search.setHint("Search by Fulfillment ID");
        stockAvailableOrdersBinding.search.setText("");
        hideKeyboard();
    }

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    public void onClickUnAssign(GetOMSTransactionHeaderResponse.OMSHeader omsHeader, String userId) {
        unAssignedOrder = omsHeader;
        List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
        omsHeaderList.add(omsHeader);
        mPresenter.mposPickPackOrderReservationApiCall(omsHeaderList, 2, userId);
    }

    private void refresh() {
        assignedOrders.clear();
        unAssignedOrders.clear();
        mPresenter.getOmsTransactionHeader();
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 1) {
            if (mposPickPackOrderReservationResponse != null) {
                if (mposPickPackOrderReservationResponse.getOrderList() != null && mposPickPackOrderReservationResponse.getOrderList().size() > 0) {
                    if (stockAvailableOrders != null && stockAvailableOrders.size() > 0) {
                        for (int i = 0; i < stockAvailableOrders.size(); i++) {
                            for (int j = 0; j < mposPickPackOrderReservationResponse.getOrderList().size(); j++) {
                                if (stockAvailableOrders.get(i).getRefno().equalsIgnoreCase(mposPickPackOrderReservationResponse.getOrderList().get(j).getTransactionID())) {
                                    stockAvailableOrders.get(i).setPickupReserved(true);
                                }
                            }
                        }
                        refresh();
                    }
                }
            }
        } else {
            if (mposPickPackOrderReservationResponse != null) {
                if (mposPickPackOrderReservationResponse.getOrderList() != null && mposPickPackOrderReservationResponse.getOrderList().size() > 0) {
                    if (stockAvailableOrders != null && stockAvailableOrders.size() > 0) {
                        for (int i = 0; i < stockAvailableOrders.size(); i++) {
                            for (int j = 0; j < mposPickPackOrderReservationResponse.getOrderList().size(); j++) {
                                if (stockAvailableOrders.get(i).getRefno().equalsIgnoreCase(mposPickPackOrderReservationResponse.getOrderList().get(j).getTransactionID())) {
                                    stockAvailableOrders.get(i).setPickupReserved(false);
                                }
                            }
                        }
                        refresh();
                    }
                }
            }
        }
    }

    @Override
    public void onClickAssign(GetOMSTransactionHeaderResponse.OMSHeader omsHeader) {
        Dialog dialog = new Dialog(StockAvailableOrdersActivity.this);
        DialogActivePickersBinding dialogActivePickersBinding = DataBindingUtil.inflate(LayoutInflater.from(StockAvailableOrdersActivity.this), R.layout.dialog_active_pickers, null, false);
        dialog.setContentView(dialogActivePickersBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogActivePickersBinding.close.setOnClickListener(view -> dialog.dismiss());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(StockAvailableOrdersActivity.this, android.R.layout.simple_spinner_dropdown_item, activePickers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogActivePickersBinding.selectPicker.setAdapter(arrayAdapter);
        dialogActivePickersBinding.selectPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPickerPosition = position;
                TextView textView = (TextView) view;
                textView.setText(activePickers.get(selectedPickerPosition));
                textView.setTextSize(12);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialogActivePickersBinding.ok.setOnClickListener(view -> {
            dialog.dismiss();
            List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
            omsHeaderList.add(omsHeader);
            mPresenter.mposPickPackOrderReservationApiCall(omsHeaderList, 1, activePickers.get(selectedPickerPosition));
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccessGetOmsTransactionHeader(List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList) {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            // remove un-assigned order manually
            /*if (unAssignedOrder != null) {
                omsHeaderList.removeIf(omsHeader -> omsHeader.getRefno().equalsIgnoreCase(unAssignedOrder.getRefno()));
            }*/
            assignedOrders = omsHeaderList.stream()
                    .filter(omsHeader -> omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE"))
                    .filter(omsHeader -> omsHeader.getPickPackUser() != null && !omsHeader.getPickPackUser().isEmpty())
                    .collect(Collectors.toList());
            unAssignedOrders = omsHeaderList.stream()
                    .filter(omsHeader -> omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE"))
                    .filter(omsHeader -> omsHeader.getPickPackUser() != null && omsHeader.getPickPackUser().isEmpty())
                    .collect(Collectors.toList());

            if (assignedOrders.size() > 0) {
                stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.VISIBLE);
                stockAvailableOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
                assignedOrderAdapter = new AssignedOrderAdapter(StockAvailableOrdersActivity.this, assignedOrders, this);
                stockAvailableOrdersBinding.assignedOrdersRcv.setAdapter(assignedOrderAdapter);
                stockAvailableOrdersBinding.assignedOrdersRcv.setLayoutManager(new LinearLayoutManager(StockAvailableOrdersActivity.this));
            } else {
                stockAvailableOrdersBinding.assignedOrdersRcv.setVisibility(View.GONE);
                stockAvailableOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
            }

            if (unAssignedOrders.size() > 0) {
                stockAvailableOrdersBinding.unAssignedOrdersRcv.setVisibility(View.VISIBLE);
                stockAvailableOrdersBinding.noOrderFound.setVisibility(View.GONE);
                unAssignedOrderAdapter = new UnAssignedOrderAdapter(StockAvailableOrdersActivity.this, unAssignedOrders, this);
                stockAvailableOrdersBinding.unAssignedOrdersRcv.setAdapter(unAssignedOrderAdapter);
                stockAvailableOrdersBinding.unAssignedOrdersRcv.setLayoutManager(new LinearLayoutManager(StockAvailableOrdersActivity.this));
            } else {
                stockAvailableOrdersBinding.unAssignedOrdersRcv.setVisibility(View.GONE);
                stockAvailableOrdersBinding.noOrderFound.setVisibility(View.VISIBLE);
            }
        }
    }
}