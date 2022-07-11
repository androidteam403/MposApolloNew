package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBatchlistPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBatchAlertBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter.BatchListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
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
    boolean noBatchDetails;
    private boolean allowChangeQty;
    private boolean allowMultiBatch;

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

    @SuppressLint("SetTextI18n")
    @Override
    protected void setUp() {
        batchlistBinding.setCallback(mPresenter);
        Intent intent = getIntent();
        selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("selectedOmsHeaderList");
        orderAdapterPos = intent.getExtras().getInt("orderAdapterPos");
        newSelectedOrderAdapterPos = intent.getExtras().getInt("newSelectedOrderAdapterPos1");
        salesLine = (GetOMSTransactionResponse.SalesLine) intent.getSerializableExtra("salesLine");
        noBatchDetails = intent.getExtras().getBoolean("noBatchDetails");
        batchlistBinding.fullfillmentId.setText(selectedOmsHeaderList.get(orderAdapterPos).getRefno());
        for (GetGlobalConfingRes.OMSVendorWiseConfigration omsVendorWiseConfigration : mPresenter.getGlobalConfigRes().getOMSVendorWiseConfigration()) {
            if (omsVendorWiseConfigration.getCorpCode().equalsIgnoreCase(selectedOmsHeaderList.get(orderAdapterPos).getVendorId())) {
                this.allowChangeQty = omsVendorWiseConfigration.getAllowChangeQTY();
                this.allowMultiBatch = omsVendorWiseConfigration.getAllowMultiBatch();
            }
        }

        if (noBatchDetails) {
        }


        if (selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode() != null && !selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode().isEmpty()) {
            if (selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode().length() > 5)
                batchlistBinding.boxId.setText(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode().substring(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode().length() - 5));
            else
                batchlistBinding.boxId.setText(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode());
        } else {
            batchlistBinding.boxId.setText("-");

        }
        batchlistBinding.availableQuantity.setText(String.valueOf(totalBatchDetailsQuantity).contains(".") ? String.valueOf(totalBatchDetailsQuantity).substring(0, String.valueOf(totalBatchDetailsQuantity).indexOf(".")) : String.valueOf(totalBatchDetailsQuantity));
        batchlistBinding.requiredQty.setText(String.valueOf(salesLine.getQty()));
        batchlistBinding.qtyEdit.setText(String.valueOf(salesLine.getQty()));
        mPresenter.getBatchDetailsApi(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos));
        batchlistBinding.tabletName.setText(salesLine.getItemName());
        batchlistBinding.availableQty.setText("Required Quantity : " + (salesLine.getQty()));
        searchByFulfilmentId();

    }

    private void searchByFulfilmentId() {
        batchlistBinding.searchbybatchId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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

    String status;
    List<GetBatchInfoRes.BatchListObj> body;

    @Override
    public void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> bodys) {
        for (int i = 0; i < bodys.size(); i++) {
            if (bodys.get(i).isNearByExpiry()) {
                bodys.remove(i);
                i--;
            }
        }

        this.body = bodys;
        if (body != null && body.size() > 0) {
            double totalBatchDetailsQuantity = 0.0;
            for (int i = 0; i < body.size(); i++) {
                totalBatchDetailsQuantity = totalBatchDetailsQuantity + Double.parseDouble(body.get(i).getQ_O_H());
            }
            batchlistBinding.availableQuantity.setText(String.valueOf(totalBatchDetailsQuantity).contains(".") ? String.valueOf(totalBatchDetailsQuantity).substring(0, String.valueOf(totalBatchDetailsQuantity).indexOf(".")) : String.valueOf(totalBatchDetailsQuantity));
            status = "";
            if (totalBatchDetailsQuantity >= salesLine.getQty()) {
                status = "FULL";
                checkAllFalse();
                batchlistBinding.fullPickedRadio.setChecked(true);
            } else if (totalBatchDetailsQuantity > 0.0) {
                status = "PARTIAL";
                checkAllFalse();
                batchlistBinding.partiallyPickedRadio.setChecked(true);
            } else {
                status = "NOT AVAILABLE";
                checkAllFalse();
                batchlistBinding.notAvailableRadio.setChecked(true);
            }

            String finalStatus = status;
            String finalStatus1 = status;
            batchlistBinding.selectBatchesAutomatically.setOnClickListener(view1 -> {

                int requiredQty = salesLine.getQty();
                batchListObjsList = new ArrayList<>();
                for (int i = 0; i < body.size(); i++) {
                    if (Double.parseDouble(body.get(i).getQ_O_H()) >= requiredQty) {
                        body.get(i).setSelected(true);
                        body.get(i).setREQQTY(requiredQty);
                        body.get(i).setPhysicalBatchID(body.get(i).getBatchNo());
                        batchListObjsList.add(body.get(i));
                        selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setStatus(finalStatus1);
                        GetBatchInfoRes o = new GetBatchInfoRes();
                        o.setBatchList(batchListObjsList);
                        selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
                        mPresenter.checkBatchInventory(body.get(i), requiredQty, finalStatus);
                        break;
                    } else if (Double.parseDouble(body.get(i).getQ_O_H()) < requiredQty) {

                        if (i == body.size() - 1) {
                            body.get(i).setSelected(true);
                            body.get(i).setREQQTY(Double.parseDouble(body.get(i).getQ_O_H()));
                            body.get(i).setPhysicalBatchID(body.get(i).getBatchNo());
                            batchListObjsList.add(body.get(i));
                            selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setStatus(finalStatus1);
                            GetBatchInfoRes o = new GetBatchInfoRes();
                            o.setBatchList(batchListObjsList);
                            selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
                            mPresenter.checkBatchInventory(body.get(i), requiredQty, finalStatus);
                            requiredQty = (int) (requiredQty - Double.parseDouble(body.get(i).getQ_O_H()));
                        } else {
                            body.get(i).setSelected(true);
                            body.get(i).setREQQTY(Double.parseDouble(body.get(i).getQ_O_H()));
                            body.get(i).setPhysicalBatchID(body.get(i).getBatchNo());
                            batchListObjsList.add(body.get(i));
                            mPresenter.checkBatchInventory(body.get(i), requiredQty, "");
                            requiredQty = (int) (requiredQty - Double.parseDouble(body.get(i).getQ_O_H()));
                        }
                    }
                }
            });


        } else {
            checkAllFalse();
            batchlistBinding.notAvailableRadio.setChecked(true);
            batchlistBinding.noOrderFoundText.setVisibility(View.VISIBLE);
            batchlistBinding.selectBatchesAutomatically.setVisibility(View.GONE);
//            batchlistBinding.update1.setVisibility(View.VISIBLE);

            batchlistBinding.update1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    status = "NOT AVAILABLE";
                    String finalStatus3 = status;
                    selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setStatus(status);
                    Intent i = new Intent();
                    i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                    i.putExtra("finalStatus", (String) status);
                    setResult(RESULT_OK, i);
                    finish();
//
                }
            });
        }

        if (selectedOmsHeaderList != null
                && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse() != null
                && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine() != null
                && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().size() > 0
                && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes() != null
                && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList() != null
                && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().size(); i++) {
                for (int j = 0; j < body.size(); j++) {
                    if (selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i).getBatchNo().equals(body.get(j).getBatchNo())) {
                        body.set(j, selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i));
                        batchListObjsList.add(body.get(j));
                    }
                }
            }
        }

        if (body.size() > 0) {

            if (!allowChangeQty) {
                int qty = selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(position).getQty();
                for (int i = 0; i < this.body.size(); i++) {
                    if (qty <= Double.parseDouble(this.body.get(i).getQ_O_H())) {
                        this.body.get(i).setREQQTY((double) qty);
                        qty = 0;
                        break;
                    } else {
                        this.body.get(i).setREQQTY(Double.parseDouble(this.body.get(i).getQ_O_H()));
                        qty = (int) (qty - Double.parseDouble(this.body.get(i).getQ_O_H()));
                    }
                    if (!allowMultiBatch) {
                        break;
                    }
                }
            }


            batchListAdapter = new BatchListAdapter(this, this.body, this);
            batchListAdapter.setAllowChangeQty(allowChangeQty);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            batchlistBinding.batchListRecycler.setLayoutManager(mLayoutManager);
            batchlistBinding.batchListRecycler.setAdapter(batchListAdapter);
        }


    }

    private void checkAllFalse() {
        batchlistBinding.fullPickedRadio.setChecked(false);
        batchlistBinding.partiallyPickedRadio.setChecked(false);
        batchlistBinding.notAvailableRadio.setChecked(false);
    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {
        checkAllFalse();
        batchlistBinding.notAvailableRadio.setChecked(true);
        batchlistBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        batchlistBinding.selectBatchesAutomatically.setVisibility(View.GONE);
        batchlistBinding.update1.setVisibility(View.VISIBLE);

        batchlistBinding.update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "NOT AVAILABLE";
                String finalStatus3 = status;
                selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setStatus(status);
                Intent i = new Intent();
                i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                i.putExtra("finalStatus", (String) status);
                setResult(RESULT_OK, i);
                finish();
//
            }
        });
    }

    ArrayList<GetBatchInfoRes.BatchListObj> batchListObjsList = new ArrayList<>();
    int position;
    double requiredQty;
    GetBatchInfoRes.BatchListObj item;
    double reservedqty;

    @Override
    public void onCheckBoxClick(GetBatchInfoRes.BatchListObj item, int position, double reservedqty) {
        this.reservedqty = reservedqty;
        this.item = item;
        this.position = position;
        batchListObjsList.add(item);


    }

    @Override
    public void onUncheckBoxClick(GetBatchInfoRes.BatchListObj batchListModel, double reqqty) {
        batchListObjsList.remove(batchListModel);
    }


    double requireddetailsqty = 0.0;
    double totalBatchDetailsQuantity = 0.0;
    String statusBatchlist;
    boolean isLastPos;
    int k;

    @Override
    public void onAddItemsClicked() {
        double selectedBatchesQty = 0.0;
        if (!allowMultiBatch && batchListObjsList != null && batchListObjsList.size() > 1) {
            Toast.makeText(this, "Select only one batch..!", Toast.LENGTH_SHORT).show();
        } else if (batchListObjsList != null && batchListObjsList.size() > 0) {
            for (int i = 0; i < batchListObjsList.size(); i++) {
                selectedBatchesQty = selectedBatchesQty + batchListObjsList.get(i).getREQQTY();
            }

            if (selectedBatchesQty < salesLine.getQty()) {
                statusBatchlist = "PARTIAL";
                Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_NoActionBar);
                DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_batch_alert, null, false);
                dialog.setContentView(dialogBatchAlertBinding.getRoot());
                dialogBatchAlertBinding.dialogMessage.setText("You have entered less than Request Qty");
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialogBatchAlertBinding.dialogButtonNO.setText("Cancel");
                dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.VISIBLE);
                dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                    GetBatchInfoRes o = new GetBatchInfoRes();
                    if (batchListObjsList != null && batchListObjsList.size() > 0)
                        o.setBatchList(batchListObjsList);
                    selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
                    Intent i = new Intent();
                    i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                    i.putExtra("finalStatus", (String) statusBatchlist);
                    setResult(RESULT_OK, i);
                    finish();
                    dialog.dismiss();
                });
                dialogBatchAlertBinding.dialogButtonNO.setOnClickListener(v1 -> dialog.dismiss());
                dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());


//                CheckReservedQtyDialog dialogView = new CheckReservedQtyDialog(this);
//                dialogView.setTitle("You have entered less than Request Qty");
//                dialogView.setPositiveLabel("Ok");
//                dialogView.setPositiveListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        try {
//                            GetBatchInfoRes o = new GetBatchInfoRes();
//                            if (batchListObjsList != null && batchListObjsList.size() > 0)
//                                o.setBatchList(batchListObjsList);
//                            selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
//                            Intent i = new Intent();
//                            i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
//                            i.putExtra("finalStatus", (String) statusBatchlist);
//                            setResult(RESULT_OK, i);
//                            finish();
//                        } catch (Exception e) {
//                            System.out.println("===============================================" + e.getMessage());
//                        }
//                        dialogView.dismiss();
//                    }
//
//                });
//                dialogView.setNegativeLabel("Cancel");
//                dialogView.setNegativeListener(v -> dialogView.dismiss());
//                dialogView.show();
            } else if (selectedBatchesQty == salesLine.getQty()) {
                statusBatchlist = "FULL";
                try {
                    GetBatchInfoRes o = new GetBatchInfoRes();
                    if (batchListObjsList != null && batchListObjsList.size() > 0)
                        o.setBatchList(batchListObjsList);
                    selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
                    Intent i = new Intent();
                    i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                    i.putExtra("finalStatus", (String) statusBatchlist);
                    setResult(RESULT_OK, i);
                    finish();
                } catch (Exception e) {
                    System.out.println("===============================================" + e.getMessage());
                }
            } else if (selectedBatchesQty > salesLine.getQty()) {
                Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_NoActionBar);
                DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_batch_alert, null, false);
                dialog.setContentView(dialogBatchAlertBinding.getRoot());
                dialogBatchAlertBinding.dialogMessage.setText("You have entered more than required qty");
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.VISIBLE);
                dialogBatchAlertBinding.dialogButtonNO.setOnClickListener(view -> dialog.dismiss());
                dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                    statusBatchlist = "FULL";
                    GetBatchInfoRes o = new GetBatchInfoRes();
                    if (batchListObjsList != null && batchListObjsList.size() > 0)
                        o.setBatchList(batchListObjsList);
                    selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
                    Intent i = new Intent();
                    i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                    i.putExtra("finalStatus", (String) statusBatchlist);
                    setResult(RESULT_OK, i);
                    finish();
                    dialog.dismiss();
                });
                dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());


//                Toast.makeText(this, "You have entered more than required qty", Toast.LENGTH_SHORT).show();
            }

        } else {
            Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_NoActionBar);
            DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_batch_alert, null, false);
            dialog.setContentView(dialogBatchAlertBinding.getRoot());
            dialogBatchAlertBinding.dialogMessage.setText("Select batch id");
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
            dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());
//            Toast.makeText(this, "Select batch id", Toast.LENGTH_SHORT).show();
        }


//        for (int i = 0; i < batchListObjsList.size(); i++) {
//            totalBatchDetailsQuantity = totalBatchDetailsQuantity + Double.parseDouble(batchListObjsList.get(i).getQ_O_H());
//            double batchdetailsQty = batchListObjsList.get(i).getREQQTY();
//            requireddetailsqty = requireddetailsqty + batchdetailsQty;
//
//            String status = "";
//            if (totalBatchDetailsQuantity >= salesLine.getQty()) {
//                status = "FULL";
//            } else if (totalBatchDetailsQuantity > 0.0) {
//                status = "PARTIAL";
//            } else {
//                status = "NOT AVAILABLE";
//
//            }
//            statusBatchlist = status;
//            isLastPos = false;
//            if (batchListObjsList.size() - 1 == i) {
//                isLastPos = true;
//                k = i;
//            }
//        }
//
//
//        if (requireddetailsqty > salesLine.getQty()) {
//            CheckReservedQtyDialog dialogView = new CheckReservedQtyDialog(this);
//            dialogView.setTitle("You have entered more than Request Qty");
//            dialogView.setPositiveLabel("Ok");
//            dialogView.setPositiveListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    mPresenter.checkBatchInventory(batchListObjsList.get(k), isLastPos);
//                    dialogView.dismiss();
//                }
//
//
//            });
//            dialogView.setNegativeLabel("Cancel");
//            dialogView.setNegativeListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogView.dismiss();
//                }
//            });
//            dialogView.show();
//
//        } else if (requireddetailsqty < salesLine.getQty()) {
//            CheckReservedQtyDialog dialogView = new CheckReservedQtyDialog(this);
//            dialogView.setTitle("You have entered less than Request Qty");
//            dialogView.setPositiveLabel("Ok");
//            dialogView.setPositiveListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mPresenter.checkBatchInventory(batchListObjsList.get(k), isLastPos);
//                    dialogView.dismiss();
//
//                }
//
//            });
//            dialogView.setNegativeLabel("Cancel");
//            dialogView.setNegativeListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogView.dismiss();
//                }
//            });
//            dialogView.show();
//
//        } else if (requireddetailsqty == salesLine.getQty()) {
//            mPresenter.checkBatchInventory(batchListObjsList.get(k), isLastPos);
//        }
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
//        Constant.getInstance().manualSelectedPosition = position;
//        Constant.getInstance().enteredQuantity = quantity;
//        Constant.getInstance().isSelectedBatch = true;
        if (body != null && body.size() > 0) {
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
    public void onClickSelectedBatch(GetBatchInfoRes.BatchListObj batchListModel) {
        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).getBatchNo().equals(batchListModel.getBatchNo())) {
                body.set(i, batchListModel);
                if (body.get(i).isSelected()) {
                    batchListObjsList.add(body.get(i));
                    mPresenter.checkBatchInventory(body.get(i), true);
                } else {
                    batchListObjsList.remove(body.get(i));
                }
                break;
            }
        }
        if (batchListAdapter != null) {
            batchListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickFullPicked() {

    }

    @Override
    public void onClickPartialPicked() {

    }

    @Override
    public void onClickNotAvailable() {

    }

    @Override
    public void onClickSkip() {

    }

    @Override
    public void onClickAutoUpdate() {

    }

    @Override
    public void checkBatchInventorySuccess(String status, CheckBatchInventoryRes body) {
        if (body != null && !status.isEmpty())
            onAddItemsClicked();
    }

    @Override
    public void checkBatchInventoryFailed(String message) {

    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onClickNotAvailableBtn() {
        status = "NOT AVAILABLE";
        String finalStatus3 = status;
        selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setStatus(status);
        Intent i = new Intent();
        i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
        i.putExtra("finalStatus", (String) status);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void checkBatchInventorySuccess(CheckBatchInventoryRes body) {


//        try {
//            GetBatchInfoRes o = new GetBatchInfoRes();
//            if (batchListObjsList != null && batchListObjsList.size() > 0)
//                o.setBatchList(batchListObjsList);
//            selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
//            Intent i = new Intent();
//            i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
//            i.putExtra("finalStatus", (String) statusBatchlist);
//            setResult(RESULT_OK, i);
//            finish();
//        } catch (Exception e) {
//            System.out.println("===============================================" + e.getMessage());
//        }


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