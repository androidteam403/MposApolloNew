package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.OrderReturnActiivtyBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter.OrderReturnAdapter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter.PaidListAdapter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.OrderReturnModel;
import com.apollopharmacy.mpospharmacist.utils.ViewAnimationUtils;

import java.util.ArrayList;

import javax.inject.Inject;

public class OrderReturnActivity extends BaseActivity implements OrederReturnMvpView {

    @Inject
    OrderReturnMvpPresenter<OrederReturnMvpView> mvpPresenter;
    OrderReturnActiivtyBinding orderReturnActiivtyBinding;
    private ArrayList<SalesLineEntity> orderReturnModelList = null;
    private OrderReturnAdapter orderReturnAdapter;
    private boolean isExpand = false;
    private int rotationAngle = 0;
    private CalculatePosTransactionRes orderHistoryItem = null;
    private PaidListAdapter payActivityAdapter;
    private ArrayList<OrderReturnModel> arrPayAdapterModel = new ArrayList<>();

    public static Intent getStartIntent(Context context, CalculatePosTransactionRes model) {
        Intent intent = new Intent(context, OrderReturnActivity.class);
        intent.putExtra("order_history_info", model);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderReturnActiivtyBinding = DataBindingUtil.setContentView(this, R.layout.order_return_actiivty);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(OrderReturnActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        orderReturnActiivtyBinding.setCallback(mvpPresenter);
        orderReturnModelList = new ArrayList<>();
        if (getIntent() != null) {
            orderHistoryItem = (CalculatePosTransactionRes) getIntent().getSerializableExtra("order_history_info");
            if (orderHistoryItem != null) {
                orderReturnActiivtyBinding.setItem(orderHistoryItem);
                orderReturnModelList.addAll(orderHistoryItem.getSalesLine());
                orderReturnActiivtyBinding.setProductCount(orderReturnModelList.size());
                if (orderHistoryItem.getSalesLine().size() > 0) {
                    orderHistoryItem.setPharmaTotalAmount(0);
                    orderHistoryItem.setFmcgTotalAmount(0);
                    orderHistoryItem.setPlTotalAmount(0);
                    orderHistoryItem.setTaxableTotalAmount(0);
                    orderHistoryItem.setOrderTotalAmount(orderHistoryItem.getAmounttoAccount());
                    orderHistoryItem.setOrderSavingsAmount(orderHistoryItem.getDiscAmount() / orderHistoryItem.getGrossAmount() * 100);
                    for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
                        if (orderHistoryItem.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("P")) {
                            orderHistoryItem.setPharmaTotalAmount(orderHistoryItem.getPharmaTotalAmount() + orderHistoryItem.getSalesLine().get(i).getNetAmount());
                            orderHistoryItem.setTaxableTotalAmount(orderHistoryItem.getTaxableTotalAmount() + orderHistoryItem.getSalesLine().get(i).getTaxAmount());
                        } else if (orderHistoryItem.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("F")) {
                            orderHistoryItem.setFmcgTotalAmount(orderHistoryItem.getFmcgTotalAmount() + orderHistoryItem.getSalesLine().get(i).getNetAmount());
                            orderHistoryItem.setTaxableTotalAmount(orderHistoryItem.getTaxableTotalAmount() + orderHistoryItem.getSalesLine().get(i).getTaxAmount());
                        } else if (orderHistoryItem.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("A")) {
                            orderHistoryItem.setPlTotalAmount(orderHistoryItem.getPlTotalAmount() + orderHistoryItem.getSalesLine().get(i).getNetAmount());
                            orderHistoryItem.setTaxableTotalAmount(orderHistoryItem.getTaxableTotalAmount() + orderHistoryItem.getSalesLine().get(i).getTaxAmount());
                        }
                    }
                    getPaymentTypes();
                }
            }
        }
        orderReturnActiivtyBinding.detailsLayout.detailsExpanCollapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    isExpand = false;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(orderReturnActiivtyBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.collapse(orderReturnActiivtyBinding.detailsLayout.customerDoctorLayout);
                } else {
                    isExpand = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(orderReturnActiivtyBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.expand(orderReturnActiivtyBinding.detailsLayout.customerDoctorLayout);
                }
            }
        });
        if (orderReturnModelList.size() > 0) {
            orderReturnAdapter = new OrderReturnAdapter(this, orderReturnModelList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            orderReturnActiivtyBinding.orderreturnrecycle.setLayoutManager(mLayoutManager);
            orderReturnActiivtyBinding.orderreturnrecycle.setAdapter(orderReturnAdapter);
        }

        payActivityAdapter = new PaidListAdapter(this, arrPayAdapterModel);
        RecyclerView.LayoutManager mLayoutManagerOne = new LinearLayoutManager(this);
        orderReturnActiivtyBinding.payAmount.setLayoutManager(mLayoutManagerOne);
        orderReturnActiivtyBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        orderReturnActiivtyBinding.payAmount.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        orderReturnActiivtyBinding.payAmount.setAdapter(payActivityAdapter);
    }

    private void getPaymentTypes() {
        for (int i = 0; i < orderHistoryItem.getTenderLine().size(); i++) {
            OrderReturnModel orderReturnModel = new OrderReturnModel(orderHistoryItem.getTenderLine().get(i).getTenderName(), "₹ " + orderHistoryItem.getTenderLine().get(i).getAmountTendered());
            arrPayAdapterModel.add(orderReturnModel);
        }
    }

    @Override
    public void onClickActionBarBack() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        onClickActionBarBack();
    }
}
