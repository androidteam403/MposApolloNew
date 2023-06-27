package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityStockInwardProcessDetailsBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogDropDownInwardBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.adapter.DropDownListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.adapter.InwardProcessLineItemsAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetPrDetailsApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetUniversalDropDownBindResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;

import javax.inject.Inject;

public class StockInwardProcessDetailsActivity extends BaseActivity implements StockInwardProcessDetailsMvpView {

    @Inject
    StockInwardProcessDetailsMvpPresenter<StockInwardProcessDetailsMvpView> mPresenter;
    private ActivityStockInwardProcessDetailsBinding stockInwardProcessDetailsBinding;
    GetInventoryTransactionDetailsResponse inventoryData;
    String dropDownItem;
    //    List<GetInventoryTransactionDetailsResponse.InventoryDatum> inventoryData;
    private int position = 0;

    int positionItem = 0;
    private boolean dropDownClicked=false;

    InwardProcessLineItemsAdapter inwardProcessLineItemsAdapter;


    public static Intent getStartIntent(Context context, GetInventoryTransactionDetailsResponse inventoryData, int position) {
        Intent intent = new Intent(context, StockInwardProcessDetailsActivity.class);
        intent.putExtra("inventoryData", (Serializable) inventoryData);
        intent.putExtra("position", (Serializable) position);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockInwardProcessDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_inward_process_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(StockInwardProcessDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        if (getIntent() != null) {
            inventoryData = (GetInventoryTransactionDetailsResponse) getIntent().getSerializableExtra("inventoryData");
            position = getIntent().getIntExtra("position", position);
        }
//        Toast.makeText(this, ""+inventoryData.size(), Toast.LENGTH_SHORT).show();
        stockInwardProcessDetailsBinding.setCallback(mPresenter);
        if(inventoryData.getInventoryData().get(position).getReferenceId()!=null && !inventoryData.getInventoryData().get(position).getReferenceId().isEmpty()){
            stockInwardProcessDetailsBinding.vendorRefNo.setText(inventoryData.getInventoryData().get(position).getReferenceId());
        }else{
            stockInwardProcessDetailsBinding.vendorRefNo.setText("--");
        }
        if(inventoryData.getInventoryData().get(position).getDatePhysical()!=null && !inventoryData.getInventoryData().get(position).getDatePhysical().isEmpty()){
            stockInwardProcessDetailsBinding.challanDate.setText(inventoryData.getInventoryData().get(position).getDatePhysical());
        }else{
            stockInwardProcessDetailsBinding.challanDate.setText("--");
        }
        stockInwardProcessDetailsBinding.deliveryDate.setText("--");
        stockInwardProcessDetailsBinding.supInvNo.setText("--");
        if(inventoryData.getInventoryData().get(position).getVendGroup()!=null && !inventoryData.getInventoryData().get(position).getVendGroup().isEmpty()){
            stockInwardProcessDetailsBinding.vendorGroup.setText(inventoryData.getInventoryData().get(position).getVendGroup());
        }else{
            stockInwardProcessDetailsBinding.vendorGroup.setText("--");
        }
//        stockInwardProcessDetailsBinding.vendorGroup.setText(inventoryData.getInventoryData().get(position).getVendGroup());
        stockInwardProcessDetailsBinding.poNumber.setText("--");
        stockInwardProcessDetailsBinding.supplierName.setText("--");
        mPresenter.getUniversalDropDown();
        mPresenter.getPrDetailsApi(inventoryData.getInventoryData().get(0).getReferenceId());
        setAdapter();
    }

    private void setAdapter() {
        if (inventoryData != null && inventoryData.getInventoryData() != null && inventoryData.getInventoryData().size() > 0) {
            stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setVisibility(View.VISIBLE);
            stockInwardProcessDetailsBinding.noListFound.setVisibility(View.GONE);
            inwardProcessLineItemsAdapter = new InwardProcessLineItemsAdapter(inventoryData.getInventoryData().get(position).getInventoryDetails(), this, dropDownItem, dropDownClicked);
            LinearLayoutManager mLinearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setLayoutManager(mLinearManager);
            stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setAdapter(inwardProcessLineItemsAdapter);

        } else {
            stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setVisibility(View.GONE);
            stockInwardProcessDetailsBinding.noListFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    GetUniversalDropDownBindResponse getUniversalDropDownBindResponse;

    @Override
    public void onSuccessUniversalDropDownDetails(GetUniversalDropDownBindResponse getUniversalDropDownBindResponse) {
        this.getUniversalDropDownBindResponse = getUniversalDropDownBindResponse;
        Toast.makeText(getApplicationContext(), "" + getUniversalDropDownBindResponse.getDropDownLineList().size(), Toast.LENGTH_SHORT).show();
    }

    BottomSheetDialog decideVersionFlowDialog;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClickDropDown(int position) {
        dropDownClicked=true;
        inwardProcessLineItemsAdapter.notifyDataSetChanged();
//        inwardProcessLineItemsAdapter = new InwardProcessLineItemsAdapter(inventoryData.getInventoryData().get(position).getInventoryDetails(), this, dropDownItem, dropDownClicked);
//        LinearLayoutManager mLinearManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setLayoutManager(mLinearManager1);
//        stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setAdapter(inwardProcessLineItemsAdapter);

        decideVersionFlowDialog = new BottomSheetDialog(StockInwardProcessDetailsActivity.this);
        DialogDropDownInwardBinding dialogDropDownInwardBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_drop_down_inward, null, false);
        decideVersionFlowDialog.setContentView(dialogDropDownInwardBinding.getRoot());
        decideVersionFlowDialog.setCancelable(true);
        if (getUniversalDropDownBindResponse != null && getUniversalDropDownBindResponse.getDropDownLineList() != null && getUniversalDropDownBindResponse.getDropDownLineList().size() > 0) {
            dialogDropDownInwardBinding.dropDownListRecyclerview.setVisibility(View.VISIBLE);
            dialogDropDownInwardBinding.noListFound.setVisibility(View.GONE);
            DropDownListAdapter dropDownListAdapter = new DropDownListAdapter(getUniversalDropDownBindResponse.getDropDownLineList(), this, position);
            LinearLayoutManager mLinearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            dialogDropDownInwardBinding.dropDownListRecyclerview.setLayoutManager(mLinearManager);
            dialogDropDownInwardBinding.dropDownListRecyclerview.setAdapter(dropDownListAdapter);

        } else {
            dialogDropDownInwardBinding.dropDownListRecyclerview.setVisibility(View.GONE);
            dialogDropDownInwardBinding.noListFound.setVisibility(View.VISIBLE);
        }


        decideVersionFlowDialog.show();
    }

    @Override
    public void onClickDropDownItem(String dropDownItem, int positionItem) {
        this.dropDownItem = dropDownItem;
        this.positionItem = positionItem;
        Toast.makeText(getApplicationContext(), "" + dropDownItem + " is selected", Toast.LENGTH_SHORT).show();
        if (decideVersionFlowDialog != null) {
            decideVersionFlowDialog.dismiss();
        }

        GetUniversalDropDownBindResponse.DropDownLine dropDownLine = new GetUniversalDropDownBindResponse.DropDownLine();
        dropDownLine.setText(dropDownItem);
        inventoryData.getInventoryData().get(position).getInventoryDetails().get(positionItem).setDropDownLineList(dropDownLine);

        inwardProcessLineItemsAdapter = new InwardProcessLineItemsAdapter(inventoryData.getInventoryData().get(position).getInventoryDetails(), this, dropDownItem, dropDownClicked);
        LinearLayoutManager mLinearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setLayoutManager(mLinearManager);
        stockInwardProcessDetailsBinding.inwardProcessLisneItemsRecycler.setAdapter(inwardProcessLineItemsAdapter);

    }

    @Override
    public void onSuccessPrDetails(GetPrDetailsApiResponse getPrDetailsApiResponse) {
        if(getPrDetailsApiResponse!=null){
            Toast.makeText(getApplicationContext(), ""+getPrDetailsApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            if(getPrDetailsApiResponse.getPONumber()!=null){
                stockInwardProcessDetailsBinding.poNumber.setText(getPrDetailsApiResponse.getPONumber().toString());
            }else{
                stockInwardProcessDetailsBinding.poNumber.setText("--");
            }
        }

    }


}
