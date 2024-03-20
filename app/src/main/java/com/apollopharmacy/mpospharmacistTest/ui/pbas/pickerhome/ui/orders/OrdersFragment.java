package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders;

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
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentOrdersBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.FiltersReq;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnbillprint.OrderReturnBillPrintActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders.adapter.OrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class OrdersFragment extends BaseFragment implements OrdersMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {

    @Inject
    OrdersMvpPresenter<OrdersMvpView> mPresenter;
    private FragmentOrdersBinding ordersBinding;
    private OrdersAdapter ordersAdapter;
    private FiltersReq filtersReq = new FiltersReq();
    private boolean isScanerBack;
    public static boolean isOrdersPFragment = false;
    public static final int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ordersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OrdersFragment.this);
        return ordersBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        ordersBinding.setCallback(mPresenter);
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("ORDERS");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
        searchByFulfilmentId();
        mPresenter.getJounalOnlineOrderTransactionApiCall();
    }

    private void searchByFulfilmentId() {
        ordersBinding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    ordersBinding.search.setVisibility(View.GONE);
                    ordersBinding.deleteCancel.setVisibility(View.VISIBLE);
                    if (ordersAdapter != null) {
                        ordersAdapter.getFilter().filter(editable);
                    }

                } else if (ordersBinding.searchText.getText().toString().equals("")) {
                    if (ordersAdapter != null) {
                        ordersAdapter.getFilter().filter("");
                    }
                    ordersBinding.deleteCancel.setVisibility(View.GONE);
                    ordersBinding.search.setVisibility(View.VISIBLE);
                } else {
                    if (ordersAdapter != null) {
                        ordersAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }

    @Override
    public void onClickFilters() {

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

    @Override
    public void onChangeModule(int id, NavOptions navOptions) {

    }

    @Override
    public void onSuccessGetJounalOnlineOrderTransactonApi(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList) {
        if (getJounalOnlineOrderTransactionsResponseList != null && getJounalOnlineOrderTransactionsResponseList.size() > 0) {
            ordersAdapter = new OrdersAdapter(getContext(), getJounalOnlineOrderTransactionsResponseList, this);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            ordersBinding.ordersListRecycler.setLayoutManager(mLayoutManager1);
            ordersBinding.ordersListRecycler.setItemAnimator(new DefaultItemAnimator());
            ordersBinding.ordersListRecycler.setAdapter(ordersAdapter);
            noOrderFound(getJounalOnlineOrderTransactionsResponseList.size());
        } else {
            noOrderFound(0);
        }
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            ordersBinding.noOrderFoundText.setVisibility(View.GONE);
            ordersBinding.ordersListRecycler.setVisibility(View.VISIBLE);
        } else {
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            ordersBinding.ordersListRecycler.setVisibility(View.GONE);
            ordersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }

    private void apiOrdersCall() {
        filtersReq.setFromDate(CommonUtils.getCurrentDate("dd-MMM-yyyy"));
        filtersReq.setToDate(CommonUtils.getCurrentDate("dd-MMM-yyyy"));
        filtersReq.setMobile("");
        filtersReq.setOrderId("");

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int dayOfWeek = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfMonth = cal.get(Calendar.MONTH);
        int dayOfYear = cal.get(Calendar.YEAR); //169

        filtersReq.setFrom_date(dayOfWeek);
        filtersReq.setFrom_Month(dayOfMonth);
        filtersReq.setFrom_Year(dayOfYear);
        filtersReq.setTo_date(dayOfWeek);
        filtersReq.setTo_month(dayOfMonth);
        filtersReq.setTo_year(dayOfYear);
    }

    @Override
    public void onClickItem(GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse) {
        OrderListReq orderListReq = new OrderListReq();
        orderListReq.setArtName("");
        orderListReq.setBatchNo("");
        orderListReq.setCardNo("");
        orderListReq.setCustomerAccount("");
        orderListReq.setCustomerName("");
        orderListReq.setFromDate(filtersReq.getFromDate()); // "13-Mar-2020"
        orderListReq.setHomeDelivery(false);
        orderListReq.setIPNumber("");
        orderListReq.setItemID("");
        orderListReq.setMobileNo(filtersReq.getMobile());
        orderListReq.setPendingBills(false);
        orderListReq.setPreviousBills(false);
        orderListReq.setReceiptId(getJounalOnlineOrderTransactionsResponse.getReturnReceiptId());
        orderListReq.setToDate(filtersReq.getToDate());

        mPresenter.detailorderServiceCall(orderListReq);
    }

    @Override
    public void onSuccessdetailOrderList(ArrayList<CalculatePosTransactionRes> orderListRes) {
        if (orderListRes.size() > 0) {
            startActivityForResult(OrderReturnBillPrintActivity.getStartIntent(getActivity(), orderListRes.get(0)), REQUEST_CODE);
            getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public void onClickSearchTextClear() {
        ordersBinding.searchText.setText("");
        ordersBinding.deleteCancel.setVisibility(View.GONE);
        ordersBinding.search.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickScanCode() {
        isScanerBack = true;
        BillerOrdersActivity.isBillerActivity = true;
        isOrdersPFragment = true;
//        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        Intent intent = new Intent(getActivity(), ScannerActivity.class);
        intent.putExtra("isOrdersFragment", isOrdersPFragment);
        startActivityForResult(intent, 999);
        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String result = data.getStringExtra("result");
            if (result != null) {
                Toast.makeText(getContext(), "Scanned -> " + result, Toast.LENGTH_SHORT).show();
                ordersBinding.searchText.setText(result);
                BillerOrdersActivity.isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
