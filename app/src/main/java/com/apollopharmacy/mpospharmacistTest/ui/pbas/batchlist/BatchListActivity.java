package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBatchlistPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter.BatchListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BatchListActivity extends BaseActivity implements BatchListMvpView {
    @Inject
    BatchListMvpPresenter<BatchListMvpView> mPresenter;
    private ActivityBatchlistPBinding batchlistBinding;
    private BatchListAdapter batchListAdapter;
    private List<BatchListModel> batchListModelList;

    public static Intent getStartIntent(Context mContext) {
        return new Intent(mContext, BatchListActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        batchlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_batchlist_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BatchListActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        this.batchListModelList = getBatchList();
        batchListAdapter = new BatchListAdapter(this, batchListModelList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        batchlistBinding.batchListRecycler.setLayoutManager(mLayoutManager);
        batchlistBinding.batchListRecycler.setAdapter(batchListAdapter);
    }

    private List<BatchListModel> getBatchList() {
        List<BatchListModel> batchListModelList = new ArrayList<>();
        BatchListModel batchListModel = new BatchListModel();
        batchListModel.setBatchId("95374664110");
        batchListModel.setBatchidSelect(false);
        batchListModelList.add(batchListModel);

        batchListModel = new BatchListModel();
        batchListModel.setBatchId("77537464110");
        batchListModel.setBatchidSelect(false);
        batchListModelList.add(batchListModel);

        batchListModel = new BatchListModel();
        batchListModel.setBatchId("95357764110");
        batchListModel.setBatchidSelect(false);
        batchListModelList.add(batchListModel);

        batchListModel = new BatchListModel();
        batchListModel.setBatchId("95374667548");
        batchListModel.setBatchidSelect(false);
        batchListModelList.add(batchListModel);

        batchListModel = new BatchListModel();
        batchListModel.setBatchId("95376645345");
        batchListModel.setBatchidSelect(false);
        batchListModelList.add(batchListModel);

        return batchListModelList;
    }

    @Override
    public void onClickItem(int pos) {
        if (batchListModelList.get(pos).isBatchidSelect())
            batchListModelList.get(pos).setBatchidSelect(false);
        else
            batchListModelList.get(pos).setBatchidSelect(true);
        batchListAdapter.notifyDataSetChanged();
    }

    public class BatchListModel {
        private String batchId;
        private boolean isBatchidSelect;

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public boolean isBatchidSelect() {
            return isBatchidSelect;
        }

        public void setBatchidSelect(boolean batchidSelect) {
            isBatchidSelect = batchidSelect;
        }
    }
}