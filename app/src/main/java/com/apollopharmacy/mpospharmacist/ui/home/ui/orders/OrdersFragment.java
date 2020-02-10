package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentOrderBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.adapter.OrdersAdapter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrdersModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsActivity;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;


public class OrdersFragment extends BaseFragment implements OrdersMvpView {

    @Inject
    OrdersMvpPresenter<OrdersMvpView> mPresenter;
    private FragmentOrderBinding fragmentOrderBinding;
    private ArrayList<OrdersModel> ordersModelArrayList = null;
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

        getOrdersFragment();
        if (ordersModelArrayList.size() > 0) {
            ordersAdapter = new OrdersAdapter(getActivity(), ordersModelArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            fragmentOrderBinding.orderrecycle.setLayoutManager(mLayoutManager);
            fragmentOrderBinding.orderrecycle.setItemAnimator(new DefaultItemAnimator());
            fragmentOrderBinding.orderrecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));
            fragmentOrderBinding.orderrecycle.setItemAnimator(new DefaultItemAnimator());
            fragmentOrderBinding.orderrecycle.setAdapter(ordersAdapter);
        }
    }

    private void getOrdersFragment() {
        ordersModelArrayList = new ArrayList<>();
        OrdersModel ordersModel = new OrdersModel("OrderId\nJB99302", "CustomerName\nC.Nagaraj",
                "CustNumber\n6544656", "OrderDate\n20 Jan 2020", "Amount\n120.00", "OrderStatus\nDelivered");
        ordersModelArrayList.add(ordersModel);
        ordersModel = new OrdersModel("OrderId\nJB99302", "CustomerName\nC.Nagaraj",
                "CustNumber\n6544656", "OrderDate\n20 Jan 2020", "Amount\n120.00", "OrderStatus\nDelivered");
        ordersModelArrayList.add(ordersModel);
        ordersModel = new OrdersModel("OrderId\nJB99302", "CustomerName\nC.Nagaraj",
                "CustNumber\n6544656", "OrderDate\n20 Jan 2020", "Amount\n120.00", "OrderStatus\nDelivered");
        ordersModelArrayList.add(ordersModel);

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
    public void onItemClick() {

    }
}