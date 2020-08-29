package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityOrderSummaryBinding;
import com.apollopharmacy.mpospharmacist.databinding.ViewMedicineInfoBinding;
import com.apollopharmacy.mpospharmacist.databinding.ViewPaymentInfoBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.utils.Singletone;

import java.util.ArrayList;

import javax.inject.Inject;

public class OrderSummaryActivity extends BaseActivity implements OrderSummaryMvpView {

    @Inject
    OrderSummaryMvpPresenter<OrderSummaryMvpView> mPresenter;
    ActivityOrderSummaryBinding orderSummaryBinding;
    private ArrayList<SaveRetailsTransactionRes.SalesLineEntity> medicineArrList = new ArrayList<>();
    private ArrayList<SaveRetailsTransactionRes.TenderLineEntity> paidAmountArr = new ArrayList<>();
    private CorporateModel.DropdownValueBean corporateEntity;
    ViewPaymentInfoBinding childView;

    public static Intent getStartIntent(Context context, SaveRetailsTransactionRes saveRetailsTransactionRes, CorporateModel.DropdownValueBean corporateEntity, OrderPriceInfoModel orderPriceInfoModel) {
        Intent intent = new Intent(context, OrderSummaryActivity.class);
        intent.putExtra("transaction_details", saveRetailsTransactionRes);
        intent.putExtra("corporate_info", corporateEntity);
        intent.putExtra("order_data", orderPriceInfoModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderSummaryBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_summary);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OrderSummaryActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        orderSummaryBinding.siteName.setText(mPresenter.getStoreName());
        orderSummaryBinding.siteId.setText(mPresenter.getStoreId());
        orderSummaryBinding.terminalId.setText(mPresenter.getTerminalId());
        orderSummaryBinding.setCallback(mPresenter);
//        OrderPriceInfoModel orderPriceInfoModel=(OrderPriceInfoModel) getIntent().getSerializableExtra("order_data");
//        if (orderPriceInfoModel!=null){
//            childView.setOrderAmount(orderPriceInfoModel);
//        }
        SaveRetailsTransactionRes transactionRes = (SaveRetailsTransactionRes) getIntent().getSerializableExtra("transaction_details");
        if (transactionRes != null) {
            orderSummaryBinding.setOrderDetails(transactionRes);
        }
        if (getIntent() != null) {
            corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
            if (corporateEntity != null) {
                orderSummaryBinding.setCorporate(corporateEntity);
            }
        }

        medicineArrList.addAll(transactionRes.getSalesLine());
        orderSummaryBinding.setItemCount(medicineArrList.size());
        for (int i = 0; i < medicineArrList.size(); i++) {
            ViewMedicineInfoBinding childView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_medicine_info, orderSummaryBinding.medicineListLayout, false);
            childView.setMedicinebean(medicineArrList.get(i));
            orderSummaryBinding.medicineListLayout.addView(childView.getRoot());
        }
        SaveRetailsTransactionRes.TenderLineEntity lineEntity = new SaveRetailsTransactionRes.TenderLineEntity();
        lineEntity.setTenderName("Total Amount");
        lineEntity.setAmountTendered((transactionRes.getGrossAmount() - transactionRes.getDiscAmount()));
        paidAmountArr.add(lineEntity);
        paidAmountArr.addAll(transactionRes.getTenderLine());
        if (transactionRes.getRemainingamount() != 0) {
            SaveRetailsTransactionRes.TenderLineEntity remainAmountLineEntity = new SaveRetailsTransactionRes.TenderLineEntity();
            remainAmountLineEntity.setAmountTendered(transactionRes.getRemainingamount());
            remainAmountLineEntity.setTenderName("Pay Back Amount");
            paidAmountArr.add(remainAmountLineEntity);
        }
        for (int i = 0; i < paidAmountArr.size(); i++) {
            childView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_payment_info, orderSummaryBinding.paidAmountLayout, false);
            childView.setPaidbean(paidAmountArr.get(i));
            orderSummaryBinding.paidAmountLayout.addView(childView.getRoot());
        }

//        orderSummaryBinding.placeNewOrderBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickNewOrder();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        onClickNewOrder();
    }

    @Override
    public void onBackOrderPressed() {
        onClickNewOrder();
    }

    private void onClickNewOrder() {
        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().isPlaceNewOrder = false;
        Singletone.getInstance().isOrderCompleted = true;
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onNewPlaceOrderClicked() {
        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().isPlaceNewOrder = true;
        Singletone.getInstance().isOrderCompleted = false;
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
