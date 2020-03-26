package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.adapter.OrdersAdapter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrderReturnActivity;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;


public class OrdersFragment extends BaseFragment implements OrdersMvpView {

    @Inject
    OrdersMvpPresenter<OrdersMvpView> mPresenter;
    private FragmentOrderBinding fragmentOrderBinding;
    private ArrayList<OrderListRes> ordersModelArrayList = new ArrayList<>();
    private OrdersAdapter ordersAdapter;

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

        ordersAdapter = new OrdersAdapter(getActivity(), ordersModelArrayList, mPresenter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        fragmentOrderBinding.orderrecycle.setLayoutManager(mLayoutManager);
        fragmentOrderBinding.orderrecycle.setItemAnimator(new DefaultItemAnimator());
        fragmentOrderBinding.orderrecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));
        fragmentOrderBinding.orderrecycle.setItemAnimator(new DefaultItemAnimator());
        fragmentOrderBinding.orderrecycle.setAdapter(ordersAdapter);

        mPresenter.getOrdersDetails();
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
    public void onItemClick(OrderListRes item) {
        startActivity(OrderReturnActivity.getStartIntent(getActivity(), item));
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public String getSearchMobileNumber() {
        return fragmentOrderBinding.searchEditMobile.getText().toString();
    }

    @Override
    public void setErrorMessageEditText(String message) {
        fragmentOrderBinding.searchEditMobile.setError(message);
    }

    @Override
    public void onSuccessOrderList(ArrayList<OrderListRes> orderListRes) {
        hideKeyboard();
        if (orderListRes.size() > 0) {
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
}