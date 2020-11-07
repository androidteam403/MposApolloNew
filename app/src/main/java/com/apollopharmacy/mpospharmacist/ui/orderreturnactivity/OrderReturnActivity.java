package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.OrderReturnActiivtyBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter.OrderReturnAdapter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter.PaidListAdapter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.OrderReturnModel;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
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
    private int rotationAngle = 180;
    private CalculatePosTransactionRes orderHistoryItem = null;
    private PaidListAdapter payActivityAdapter;
    private ArrayList<OrderReturnModel> arrPayAdapterModel = new ArrayList<>();
    private boolean isCardPayment = false;

    public static Intent getStartIntent(Context context, CalculatePosTransactionRes model) {
        Intent intent = new Intent(context, OrderReturnActivity.class);
        intent.putExtra("order_history_info", model);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
        orderReturnActiivtyBinding.setIsReturn(false);
        orderReturnModelList = new ArrayList<>();
        if (getIntent() != null) {
            orderHistoryItem = (CalculatePosTransactionRes) getIntent().getSerializableExtra("order_history_info");
            if (orderHistoryItem != null) {
                orderReturnActiivtyBinding.setItem(orderHistoryItem);
                orderReturnModelList.addAll(orderHistoryItem.getSalesLine());
                orderReturnActiivtyBinding.setProductCount(orderHistoryItem.getSalesLine().size());
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
                mvpPresenter.trackingWiseReturnAllowed(orderHistoryItem.getCorpCode());
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
        if (orderHistoryItem.getSalesLine().size() > 0) {
            orderReturnAdapter = new OrderReturnAdapter(this, orderHistoryItem.getSalesLine());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            orderReturnActiivtyBinding.orderreturnrecycle.setLayoutManager(mLayoutManager);
            orderReturnActiivtyBinding.orderreturnrecycle.setAdapter(orderReturnAdapter);
//            orderHistoryItem.setReturnType(1);
//            mvpPresenter.orderReturnAll(orderHistoryItem);
        }

        orderReturnActiivtyBinding.sentSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OrderReturnActivity.this, "smssent", Toast.LENGTH_SHORT).show();
            }
        });

        payActivityAdapter = new PaidListAdapter(this, arrPayAdapterModel);
        RecyclerView.LayoutManager mLayoutManagerOne = new LinearLayoutManager(this);
        orderReturnActiivtyBinding.payAmount.setLayoutManager(mLayoutManagerOne);
        orderReturnActiivtyBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        orderReturnActiivtyBinding.payAmount.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        orderReturnActiivtyBinding.payAmount.setAdapter(payActivityAdapter);
    }

    private void getPaymentTypes() {
        arrPayAdapterModel.add(new OrderReturnModel("Total Amount", orderHistoryItem.getGrossAmount() - orderHistoryItem.getDiscAmount()));
        for (int i = 0; i < orderHistoryItem.getTenderLine().size(); i++) {
            OrderReturnModel orderReturnModel = new OrderReturnModel(orderHistoryItem.getTenderLine().get(i).getTenderName(), orderHistoryItem.getTenderLine().get(i).getAmountTendered());
            arrPayAdapterModel.add(orderReturnModel);
            if (orderHistoryItem.getTenderLine().get(i).getTenderId().equalsIgnoreCase("2") || orderHistoryItem.getTenderLine().get(i).getTenderName().equalsIgnoreCase("card")) {
                isCardPayment = true;
            }
        }
        if (orderHistoryItem.getRemainingamount() != 0) {
            arrPayAdapterModel.add(new OrderReturnModel("Pay Back Amount", orderHistoryItem.getRemainingamount()));
        }
    }

    @Override
    public void onClickActionBarBack() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void isCorpAllowedReturn(boolean isAllowed) {

        if (isAllowed && orderHistoryItem.getTransType() == 0) {
            orderReturnActiivtyBinding.corpReturnOptionsLayout.setVisibility(View.VISIBLE);
        } else {
            orderReturnActiivtyBinding.corpReturnOptionsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTransactionId(String transactionId) {
        orderHistoryItem.setReturnStore(mvpPresenter.getGlobalConfing().getStoreID());
        orderHistoryItem.setReturnTerminal(mvpPresenter.terminalId());
        orderHistoryItem.setReturnTransactionId(orderHistoryItem.getTransactionId());
        orderHistoryItem.setReturnReceiptId(orderHistoryItem.getReciptId());
        orderHistoryItem.setTransactionId(transactionId);
    }

    @Override
    public void showInfoPopup(String title, String message, boolean isCancelOrder, boolean isReturnAll, String terminalId) {
        if (orderHistoryItem.getTerminal().equalsIgnoreCase(terminalId)) {
            ExitInfoDialog dialogView = new ExitInfoDialog(this);
            dialogView.setTitle(title);
            dialogView.setPositiveLabel("OK");
            dialogView.setNegativeLabel("Cancel");
            dialogView.setSubtitle(message);
            dialogView.setDialogDismiss();
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                    if (isCancelOrder) {
                        try {
                            if (isCardPayment) {
                                showCancelOrderSuccess("", "Card Cancellation Not Allowed!!");
                            } else {
                                if (CommonUtils.checkCancelledDateTime(orderHistoryItem.getBusinessDate()) != 0) {
                                    showCancelOrderSuccess("", "Cancellation Not Allowed!!");
                                } else if (!mvpPresenter.isAllowOrNot(orderHistoryItem)) {
                                    showCancelOrderSuccess("", "Transaction Already Cancelled!!");
                                } else {
                                    orderHistoryItem.setReturnType(0);
                                    orderHistoryItem.setReturn(true);
                                    mvpPresenter.cancelDSBilling(orderHistoryItem);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (isReturnAll) {
                        if (mvpPresenter.isAllowOrNot(orderHistoryItem)) {
                            orderHistoryItem.setReturnType(0);
                            orderHistoryItem.setReturn(true);
                            mvpPresenter.orderReturnAll(orderHistoryItem);
                        } else {
                            showCancelOrderSuccess("", "Transaction Already Return!!");
                        }
                    }
                }
            });
            dialogView.setNegativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                }
            });
            dialogView.show();
            orderReturnActiivtyBinding.setIsReturn(false);
        } else {
            Toast.makeText(this, "Cannot Process order for this Terminal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showCancelOrderSuccess(String title, String message) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle(title);
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(message);
        dialogView.setDialogDismiss();
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                Intent intent = getIntent();
                intent.putExtra("isUpdated", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        dialogView.show();
    }

    @Override
    public void partialReturnOrder(String terminalId) {
        if (orderHistoryItem.getTerminal().equalsIgnoreCase(terminalId)) {
            if (!orderReturnActiivtyBinding.getIsReturn()) {
                for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
                    orderHistoryItem.getSalesLine().get(i).setReturnClick(true);
                    orderReturnAdapter.notifyItemChanged(i);
                }
                // orderReturnAdapter.notifyDataSetChanged();
                orderReturnActiivtyBinding.setIsReturn(true);
            } else {
                if (!isItemChecked()) {
                    ExitInfoDialog dialogView = new ExitInfoDialog(this);
                    dialogView.setTitle("Alert");
                    dialogView.setPositiveLabel("OK");
                    dialogView.setDialogDismiss();
                    dialogView.setSubtitle("Please select return item(s)");
                    dialogView.setPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogView.dismiss();
                        }
                    });
                    if (!dialogView.isDisplay())
                        dialogView.show();

                } else if (!isItemQuantity()) {
                    ExitInfoDialog dialogView = new ExitInfoDialog(this);
                    dialogView.setTitle("Alert");
                    dialogView.setDialogDismiss();
                    dialogView.setPositiveLabel("OK");
                    dialogView.setSubtitle("Return Quantity should be greater than 0 !");
                    dialogView.setPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogView.dismiss();
                        }
                    });
                    if (!dialogView.isDisplay())
                        dialogView.show();
                } else {
                    orderHistoryItem.setReturnType(1);
                    mvpPresenter.orderReturnAll(orderHistoryItem);
                }
            }
        } else {
            Toast.makeText(this, "Cannot Process order for this Terminal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAlreadyItemReturnedColor() {
//        this.orderReturnAdapter.itemColorChange();
    }

    @Override
    public void onSalesTrackingClick() {
        Toast.makeText(this, "onSalesTracking", Toast.LENGTH_SHORT).show();
    }

    @Override
    public CalculatePosTransactionRes calculations() {
        return orderHistoryItem;
    }

    private boolean isItemChecked() {
        boolean isValid = false;
        for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
            if (orderHistoryItem.getSalesLine().get(i).getIsChecked()) {
                return true;
            }
        }
        return isValid;
    }

    private boolean isItemQuantity() {
        boolean isValid = false;
        for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
            if (orderHistoryItem.getSalesLine().get(i).getIsChecked()) {
                if (orderHistoryItem.getSalesLine().get(i).getReturnQty() != 0) {
                    isValid = true;
                } else {
                    isValid = false;
                    return isValid;
                }
            }
        }
        return isValid;
    }

    @Override
    public void onBackPressed() {
        onClickActionBarBack();
    }
}
