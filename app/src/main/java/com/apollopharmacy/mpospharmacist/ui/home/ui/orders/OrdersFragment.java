package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentOrderBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.adapter.OrdersAdapter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.FiltersReq;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrderReturnActivity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;


public class OrdersFragment extends BaseFragment implements OrdersMvpView {

    @Inject
    OrdersMvpPresenter<OrdersMvpView> mPresenter;
    private FragmentOrderBinding fragmentOrderBinding;
    private ArrayList<CalculatePosTransactionRes> ordersModelArrayList = new ArrayList<>();
    private OrdersAdapter ordersAdapter;
    private FiltersReq filtersReq = new FiltersReq();
    private BottomSheetFragment bottomSheetFragment;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);

        getActivityComponent().inject(this);
        mPresenter.onAttach(OrdersFragment.this);
        return fragmentOrderBinding.getRoot();

    }

    @Override
    protected void setUp(View view) {
        fragmentOrderBinding.setCallbacks(mPresenter);
        fragmentOrderBinding.setNoDataFound(false);
        setHasOptionsMenu(true);
        ordersAdapter = new OrdersAdapter(getActivity(), ordersModelArrayList, mPresenter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        fragmentOrderBinding.orderrecycle.setLayoutManager(mLayoutManager);
        fragmentOrderBinding.orderrecycle.setItemAnimator(new DefaultItemAnimator());
        fragmentOrderBinding.orderrecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));
        fragmentOrderBinding.orderrecycle.setItemAnimator(new DefaultItemAnimator());
        fragmentOrderBinding.orderrecycle.setAdapter(ordersAdapter);
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
        mPresenter.getOrdersDetails();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            bottomSheetFragment = new BottomSheetFragment(getBaseActivity(),filtersReq,this);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onReturnClick() {
        Toast.makeText(getContext(), "Return Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelCLick() {
        Toast.makeText(getContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReOrderClick() {
        Toast.makeText(getContext(), "Re-orderClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(CalculatePosTransactionRes item) {
        startActivity(OrderReturnActivity.getStartIntent(getActivity(), item));
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


    @Override
    public void onSuccessOrderList(ArrayList<CalculatePosTransactionRes> orderListRes) {
        hideKeyboard();
        if (orderListRes.size() > 0) {
            ordersModelArrayList.clear();
            ordersModelArrayList.addAll(orderListRes);
            ordersAdapter.notifyDataSetChanged();
            fragmentOrderBinding.setCount(ordersModelArrayList.size());
        }
    }

    @Override
    public void noDataFound() {
        hideKeyboard();
        ordersModelArrayList.clear();
        ordersAdapter.notifyDataSetChanged();
        fragmentOrderBinding.setCount(ordersModelArrayList.size());
        fragmentOrderBinding.setNoDataFound(true);
    }

    @Override
    public void onClickApplyFilters() {
        if(bottomSheetFragment != null){
            if(bottomSheetFragment.validateFilters()){
                bottomSheetFragment.dismiss();
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
                orderListReq.setReceiptId(filtersReq.getOrderId());
                orderListReq.setToDate(filtersReq.getToDate()); //  "13-Mar-2020"
                ordersModelArrayList.clear();
                ordersAdapter.notifyDataSetChanged();
                fragmentOrderBinding.setCount(ordersModelArrayList.size());
                mPresenter.orderServiceCall(orderListReq);
            }
        }
    }
}