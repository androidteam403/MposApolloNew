package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBatchlistPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter.BatchListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.SelectedOrderPickupProcessActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BatchListActivity extends BaseActivity implements BatchListMvpView {
    @Inject
    BatchListMvpPresenter<BatchListMvpView> mPresenter;
    private ActivityBatchlistPBinding batchlistBinding;
    private BatchListAdapter batchListAdapter;
    double requiredqty;
    private ArrayList<GetBatchInfoRes.BatchListObj> batchListModelListl = new ArrayList<>();
//    private List<BatchListModel> batchListModelList;
//private  List<GetBatchInfoRes.BatchListObj> batchListModelListl;


//    public static Intent getStartIntent(Context mContext, String itemId, String itemName1, double reqqty) {
//       Intent i = new Intent(mContext, BatchListActivity.class);
//        i.putExtra("itemId", (Serializable) itemId);
//        i.putExtra("itemName", (Serializable)itemName1);
//        i.putExtra("reqqty", (Serializable) reqqty);
//        return i;
//    }

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
        batchlistBinding.setCallback(mPresenter);
        Intent intent = getIntent();
        String itemId =intent.getExtras().getString("itemId");
        String itemName1 = intent.getExtras().getString("itemName");
         requiredqty=intent.getExtras().getDouble("reqqty");

        batchlistBinding.tabletName.setText(itemName1);
//        this.batchListModelList = getBatchList();
        mPresenter.getBatchDetailsApi(itemId);




    }

//    private List<BatchListModel> getBatchList() {

//        batchListModel.setBatchId("95374664110");
//        batchListModel.setBatchidSelect(false);
//        batchListModelList.add(batchListModel);
//
//        batchListModel = new BatchListModel();
//        batchListModel.setBatchId("77537464110");
//        batchListModel.setBatchidSelect(false);
//        batchListModelList.add(batchListModel);
//
//        batchListModel = new BatchListModel();
//        batchListModel.setBatchId("95357764110");
//        batchListModel.setBatchidSelect(false);
//        batchListModelList.add(batchListModel);
//
//        batchListModel = new BatchListModel();
//        batchListModel.setBatchId("95374667548");
//        batchListModel.setBatchidSelect(false);
//        batchListModelList.add(batchListModel);
//
//        batchListModel = new BatchListModel();
//        batchListModel.setBatchId("95376645345");
//        batchListModel.setBatchidSelect(false);
//        batchListModelList.add(batchListModel);
//
//        return batchListModelList;
//    }


    GetBatchInfoRes body;
    @Override
    public void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> body) {

        if (body.size() > 0) {
            batchListAdapter = new BatchListAdapter(this, body,requiredqty, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            batchlistBinding.batchListRecycler.setLayoutManager(mLayoutManager);
            batchlistBinding.batchListRecycler.setAdapter(batchListAdapter);
        }
    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {

    }
    boolean batchSelected;
    int batchSelectedPosition;
    double reqqty;
    String batchNo;
    String itemID;
    @Override
    public void onCheckBoxClick(int position, boolean batchSelected, double reqqty, String batchNo, String itemID) {
        this.batchSelected=batchSelected;
        this.batchSelectedPosition=position;
        this.reqqty=reqqty;
        this.batchNo=batchNo;
        this.itemID=itemID;

    }

    @Override
    public void checkBatchInventorySuccess(CheckBatchInventoryRes body) {
        Toast.makeText(getApplicationContext(), "BatchList is added", Toast.LENGTH_LONG).show();

    }

    @Override
    public void checkBatchInventoryFailed(CheckBatchInventoryRes body) {

    }

    @Override
    public void onAddItemsClicked() {

        mPresenter.checkBatchInventory(reqqty, batchNo, itemID);
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