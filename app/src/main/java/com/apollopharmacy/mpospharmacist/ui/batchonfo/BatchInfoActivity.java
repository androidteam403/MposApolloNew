package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

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
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public static Intent getStartIntent(Context context, GetItemDetailsRes.Items items, TransactionIDResModel transactionID) {
        Intent intent = new Intent(context, BatchInfoActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("transaction_id", transactionID);
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
        TransactionIDResModel transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
        batchInfoBinding.setTransaction(transactionIdModel);
        selectedItem = (GetItemDetailsRes.Items) getIntent().getSerializableExtra("selected_item");
        batchInfoAdapter = new BatchInfoAdapter( arrBatchList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        batchInfoBinding.batchInfoRecycler.setLayoutManager(mLayoutManager);
        batchInfoBinding.setProduct(selectedItem);
//        ((SimpleItemAnimator) batchInfoBinding.batchInfoRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
//        batchInfoBinding.batchInfoRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        batchInfoBinding.batchInfoRecycler.setAdapter(batchInfoAdapter);
        GetItemDetailsRes.Items selected_item = (GetItemDetailsRes.Items) getIntent().getSerializableExtra("selected_item");
        mPresenter.getBatchDetailsApi(selected_item);

        batchInfoBinding.inputQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(!TextUtils.isEmpty(s)){
                        quantityBaseBatchSelect(Double.parseDouble(s.toString()));
                    }


            }
        });
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
        if(body.getBatchList().size() > 0) {
            arrBatchList.addAll(body.getBatchList());
            Collections.sort(arrBatchList, new Comparator<GetBatchInfoRes.BatchListObj>() {
                @Override
                public int compare(GetBatchInfoRes.BatchListObj o1, GetBatchInfoRes.BatchListObj o2) {
                    boolean b1 = o1.getNearByExpiry();
                    boolean b2 = o2.getNearByExpiry();
                    return Boolean.compare(b1, b2);
                }
            });
            isSelectedBatch = true;
            arrBatchList.get(0).setSelected(true);
            selectedItem.setBatchListObj(arrBatchList.get(0));
            batchInfoAdapter.notifyDataSetChanged();
        }
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

    private void quantityBaseBatchSelect(double totalQuantity){
        double batchQuantity = 0.0;
        double remainingQuantity = totalQuantity;
        for (int i = 0; i < arrBatchList.size(); i++) {
            double qoh = Double.parseDouble(arrBatchList.get(i).getQ_O_H());
            if(remainingQuantity != 0.0 && batchQuantity < totalQuantity){
                batchQuantity += qoh;
                remainingQuantity = totalQuantity - batchQuantity;
                arrBatchList.get(i).setSelected(true);
                batchInfoAdapter.notifyItemChanged(i);
                selectedItem.setBatchListObj(arrBatchList.get(i));
            }else{
                break;
            }
        }
    }

    @Override
    public String getRequiredQuantity() {
        return batchInfoBinding.inputQty.getText().toString();
    }

}
