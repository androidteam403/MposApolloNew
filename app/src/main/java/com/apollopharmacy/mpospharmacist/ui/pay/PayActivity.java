package com.apollopharmacy.mpospharmacist.ui.pay;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityPayBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.BatchInfoAdapterPojo;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayActivityAdapter;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayAdapterListener;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayAdapterModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class PayActivity extends BaseActivity implements PayMvpView , PayAdapterListener {

    @Inject
    PayMvpPresenter<PayMvpView> mPresenter;
    ActivityPayBinding activityPayBinding;
    PayActivityAdapter payActivityAdapter;
    private ArrayList<PayAdapterModel> arrPayAdapterModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        activityPayBinding = DataBindingUtil.setContentView(this, R.layout.activity_pay);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PayActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityPayBinding.setCallback(mPresenter);
        getAmount();
        payActivityAdapter = new PayActivityAdapter(this, arrPayAdapterModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityPayBinding.payAmount.setLayoutManager(mLayoutManager);
        activityPayBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        activityPayBinding.payAmount.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        activityPayBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        activityPayBinding.payAmount.setAdapter(payActivityAdapter);
    }
    private void getAmount() {
        arrPayAdapterModel = new ArrayList<>();
        PayAdapterModel payAdapterModel = new PayAdapterModel("100.00");
        arrPayAdapterModel.add(payAdapterModel);
    }
}