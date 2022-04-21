package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import android.content.Intent;
import android.os.Bundle;
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
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter.BatchListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.GetBatchDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;

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
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    int orderAdapterPos, newSelectedOrderAdapterPos;
    GetOMSTransactionResponse.SalesLine salesLine;

//    private List<BatchListModel> batchListModelList;
//private  List<GetBatchInfoRes.BatchListObj> batchListModelListl;
int i=0;

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
        selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("selectedOmsHeaderList");
        orderAdapterPos = intent.getExtras().getInt("orderAdapterPos");
        salesLine = (GetOMSTransactionResponse.SalesLine) intent.getExtras().getSerializable("salesLine");
         requiredqty=salesLine.getQty();
         batchlistBinding.tabletName.setText(salesLine.getItemName());
        newSelectedOrderAdapterPos=intent.getExtras().getInt("newSelectedOrderAdapterPos1");
         mPresenter.getBatchDetailsApi(salesLine);





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


    List<GetBatchInfoRes.BatchListObj> body;
    @Override
    public void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> body) {
        this.body=body;
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

    ArrayList< GetBatchInfoRes.BatchListObj> batchListObjsList = new ArrayList<>();
    int position;
    @Override
    public void onCheckBoxClick(GetBatchInfoRes.BatchListObj item, int position, double reqqty) {
        this.position=position;
        batchListObjsList.add(item);


    }

    @Override
    public void onUncheckBoxClick(GetBatchInfoRes.BatchListObj batchListModel, double reqqty, int position) {
        batchListObjsList.remove(batchListModel);
    }


    @Override
    public void onAddItemsClicked() {

        for (int i = 0; i < batchListObjsList.size(); i++){
            mPresenter.checkBatchInventory(batchListObjsList.get(i));
        }
    }

    @Override
    public void checkBatchInventorySuccess(CheckBatchInventoryRes body) {
        GetBatchInfoRes o = new GetBatchInfoRes();
        o.setBatchList(batchListObjsList);
        selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
        Intent i = new Intent();
        i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public void checkBatchInventoryFailed(CheckBatchInventoryRes body) {

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