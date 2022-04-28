package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBatchlistPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.CheckReservedQtyDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter.BatchListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BatchListActivity extends BaseActivity implements BatchListMvpView {
    @Inject
    BatchListMvpPresenter<BatchListMvpView> mPresenter;
    private ActivityBatchlistPBinding batchlistBinding;
    private BatchListAdapter batchListAdapter;

    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    int orderAdapterPos, newSelectedOrderAdapterPos;
    GetOMSTransactionResponse.SalesLine salesLine;
    String itemName;

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

    @SuppressLint("SetTextI18n")
    @Override
    protected void setUp() {
        batchlistBinding.setCallback(mPresenter);
        Intent intent = getIntent();
        selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("selectedOmsHeaderList");
        orderAdapterPos = intent.getExtras().getInt("orderAdapterPos");
        newSelectedOrderAdapterPos=intent.getExtras().getInt("newSelectedOrderAdapterPos1");
        salesLine= (GetOMSTransactionResponse.SalesLine) intent.getSerializableExtra("salesLine");
        mPresenter.getBatchDetailsApi(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos));
      batchlistBinding.tabletName.setText(salesLine.getItemName());
      batchlistBinding.availableQty.setText("Required Quantity : " +(salesLine.getQty()));
        searchByBatchId();
    }

    public void searchByBatchId(){
        batchlistBinding.searchbybatchId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    if (batchListAdapter != null) {
                        batchListAdapter.getFilter().filter(editable);
                    }
                } else {
                    if (batchListAdapter != null) {
                        batchListAdapter.getFilter().filter("");
                    }
                }
            }
        });
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
            batchListAdapter = new BatchListAdapter(this, body, this);
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
    double requiredQty;
    GetBatchInfoRes.BatchListObj item;
    @Override
    public void onCheckBoxClick(GetBatchInfoRes.BatchListObj item, int position) {
        this.item=item;
        this.position=position;
        batchListObjsList.add(item);


    }

    @Override
    public void onUncheckBoxClick(GetBatchInfoRes.BatchListObj batchListModel, double reqqty) {
        batchListObjsList.remove(batchListModel);
    }
    double requireddetailsqty=0.0;
    double totalBatchDetailsQuantity = 0.0;
    String statusBatchlist;
    @Override
    public void onAddItemsClicked() {

        for (int i = 0; i < batchListObjsList.size(); i++){


                totalBatchDetailsQuantity = totalBatchDetailsQuantity + Double.parseDouble(batchListObjsList.get(i).getQ_O_H());
                double batchdetailsQty= batchListObjsList.get(i).getREQQTY();
            requireddetailsqty=requireddetailsqty+ batchdetailsQty;

                String status = "";
            if (totalBatchDetailsQuantity >=requireddetailsqty) {
                status = "FULL";
            } else if (totalBatchDetailsQuantity > 0.0) {
                status = "PARTIAL";
            } else {
                status = "NOT AVAILABLE";

            }
            statusBatchlist = status;
            mPresenter.checkBatchInventory(batchListObjsList.get(i));
        }




    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            batchlistBinding.noOrderFoundText.setVisibility(View.GONE);
        } else {
            batchlistBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNavigateNextActivity() {
        double Reservedqty = 0;
        int count = 0;
        if (body != null && body.size() > 0) {
            for (GetBatchInfoRes.BatchListObj obj : body) {
                if (obj.isSelected() && obj.getPhysicalbatchstatus()) {
                    Reservedqty = Reservedqty + obj.getREQQTY();
                }
                count++;
                if (body.size() == count) {
//                    checkreservedqty(Reservedqty);
                }
            }

        }
    }




    @Override
    public void onItemClick(int position, int quantity, GetBatchInfoRes.BatchListObj batchListObj) {
        Constant.getInstance().manualSelectedPosition = position;
        Constant.getInstance().enteredQuantity = quantity;
        Constant.getInstance().isSelectedBatch = true;
        if (body!= null && body.size() > 0) {
            for (int i = 0; i < body.size(); i++) {
                if (!body.get(i).getPhysicalbatchstatus()) {
                   body.get(i).setREQQTY(0);
                   body.get(i).setSelected(i == position);
                }
            }
        }
        batchListAdapter.notifyItemChanged(Constant.getInstance().selected_position);
    }

    @Override
    public void checkBatchInventorySuccess(CheckBatchInventoryRes body) {
        GetBatchInfoRes o = new GetBatchInfoRes();
        o.setBatchList(batchListObjsList);
        selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
        Intent i = new Intent();
        i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
        i.putExtra("finalStatus", (String) statusBatchlist);
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