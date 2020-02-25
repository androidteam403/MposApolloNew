package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityBatchInfoBinding;
import com.apollopharmacy.mpospharmacist.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.adapter.BatchInfoAdapter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.lstener.BatchAdapterListener;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.BatchInfoAdapterPojo;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.MedicinesDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import java.util.ArrayList;

import javax.inject.Inject;

public class BatchInfoActivity extends BaseActivity implements BatchInfoMvpView, BatchAdapterListener {

    @Inject
    BatchInfoMvpPresenter<BatchInfoMvpView> mPresenter;
    ActivityBatchInfoBinding batchInfoBinding;

    BatchInfoAdapter batchInfoAdapter;
    BatchInfoListAdapterBinding batchInfoListAdapterBinding;
    private ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList = new ArrayList<>();
    private int count = 0;
    private boolean isSelectedBatch = false;
    private GetItemDetailsRes.Items selectedItem;

    public static Intent getStartIntent(Context context, GetItemDetailsRes.Items items) {
        Intent intent = new Intent(context, BatchInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_item",items);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        batchInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_batch_info);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BatchInfoActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        batchInfoBinding.setCallback(mPresenter);
        selectedItem = (GetItemDetailsRes.Items) getIntent().getSerializableExtra("selected_item");
        batchInfoAdapter = new BatchInfoAdapter( arrBatchList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        batchInfoBinding.batchInfoRecycler.setLayoutManager(mLayoutManager);

//        ((SimpleItemAnimator) batchInfoBinding.batchInfoRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
//        batchInfoBinding.batchInfoRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        batchInfoBinding.batchInfoRecycler.setAdapter(batchInfoAdapter);
        GetItemDetailsRes.Items selected_item = (GetItemDetailsRes.Items) getIntent().getSerializableExtra("selected_item");
        mPresenter.getBatchDetailsApi(selected_item);
    }

    @Override
    public void onIncrementClick() {
        count++;
        String string = Integer.toString(count);
        batchInfoBinding.inputQty.setText(string);
    }

    @Override
    public void onDecrementClick() {
        if (count == 1) {
            String string = Integer.toString(count);
            batchInfoBinding.inputQty.setText(string);
        } else {
            count--;
            String string = Integer.toString(count);
            batchInfoBinding.inputQty.setText(string);
        }
    }

    @Override
    public void onNavigateNextActivity() {
        if(isSelectedBatch && !TextUtils.isEmpty(getRequiredQuantity())) {
            selectedItem.getBatchListObj().setEnterReqQuantity(Integer.parseInt(getRequiredQuantity()));
            Intent intent = new Intent();
            intent.putExtra("selected_item", selectedItem);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            if (TextUtils.isEmpty(getRequiredQuantity()))
                showMessage("enter Quantity");
            else
                showMessage("select Batch");
        }
//        startActivity(MedicinesDetailsActivity.getStartIntent(this,items));
//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//        finish();
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void onSuccessBatchInfo(GetBatchInfoRes body) {
        arrBatchList.addAll(body.getBatchList());
        batchInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onItemClick(int position) {
        isSelectedBatch = true;
        for (int i = 0; i < arrBatchList.size(); i++) {
            if (arrBatchList.get(i).isSelected()) {
                arrBatchList.get(i).setSelected(false);
                batchInfoAdapter.notifyItemChanged(i);
            }
            if (position == i) {
                arrBatchList.get(i).setSelected(true);
                batchInfoAdapter.notifyItemChanged(i);
                selectedItem.setBatchListObj(arrBatchList.get(i));
            }
        }

    }

    @Override
    public String getRequiredQuantity() {
        return batchInfoBinding.inputQty.getText().toString();
    }

}
