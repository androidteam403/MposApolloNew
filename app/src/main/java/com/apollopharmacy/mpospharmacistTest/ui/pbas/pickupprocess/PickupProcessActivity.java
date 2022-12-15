package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickupProcessPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.OrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RackWiseSortedData;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummmaryActivityNew;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.SelectedOrderPickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class PickupProcessActivity extends BaseActivity implements PickupProcessMvpView {
    @Inject
    PickupProcessMvpPresenter<PickupProcessMvpView> mPresenter;
    private ActivityPickupProcessPBinding pickupProcessBinding;
    private OrderAdapter orderAdapter;
    private RackAdapter rackAdapter;
    public AdapterOrderPBinding orderBinding;
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;
    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    public String[] items;

    String statusBatchlist;
    private List<List<RackAdapter.RackBoxModel.ProductData>> rackListOfList = new ArrayList<>();
    private List<List<OrderAdapter.RackBoxModel.ProductData>> fullListOfList = new ArrayList<>();
    private List<SalesLineEntity> salesentity = new ArrayList<>();
    List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    private static List<RacksDataResponse.FullfillmentDetail.Product> rackIdList = new ArrayList<>();
    private ArrayList<String> boxStringList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    private List<RackWiseSortedData> rackWiseSortedDataList;
    long startTime;
    long countUp;
    Chronometer stopWatch;
    List<OMSOrderForwardRequest> omsOrderForwardRequests = new ArrayList<>();
    OMSOrderForwardRequest omsOrderForwardRequest;
    OMSOrderForwardRequest.ReservedSalesLine reservedSalesLine;
    private boolean isAllOnHold;

    public static Intent getStartActivity(Context context, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList) {
        Intent intent = new Intent(context, PickupProcessActivity.class);
        intent.putExtra(CommonUtils.SELECTED_ORDERS_LIST, (Serializable) selectedOmsHeaderList);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickupProcessBinding = DataBindingUtil.setContentView(this, R.layout.activity_pickup_process_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PickupProcessActivity.this);
        setUp();
    }

    int getOrderPos;
    String itemStatus;

    @Override
    public void onClickOrderItem(int pos, TransactionHeaderResponse.OMSHeader itemStatus) {


        this.getOrderPos = pos;
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            if (selectedOmsHeaderList.get(pos).getExpandStatus() == 1) {
                selectedOmsHeaderList.get(pos).setExpandStatus(0);
            } else {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    selectedOmsHeaderList.get(i).setExpandStatus(i == pos ? 1 : 0);
                }
            }

            boolean eShopCharge = false;
            if (selectedOmsHeaderList.get(pos).getGetOMSTransactionResponse() != null && selectedOmsHeaderList.get(pos).getGetOMSTransactionResponse().getSalesLine() != null && selectedOmsHeaderList.get(pos).getGetOMSTransactionResponse().getSalesLine().size() > 0) {

                for (GetOMSTransactionResponse.SalesLine salesLine : selectedOmsHeaderList.get(pos).getGetOMSTransactionResponse().getSalesLine()) {

                    if (salesLine.getItemId().equals("ESH0002") && (salesLine.getStatus() == null || salesLine.getStatus().equals(""))) {
                        eShopCharge = true;
                        this.salesLinessEshopCharge = salesLine;
                        this.newwAdapterposition = selectedOmsHeaderList.get(pos).getGetOMSTransactionResponse().getSalesLine().indexOf(salesLine);
                        mPresenter.getBatchDetailsApi(salesLine);
                        break;
                    }
                }
            }


            if (orderAdapter != null && !eShopCharge)
                orderAdapter.notifyDataSetChanged();
        }
    }


    int getPos;
    String status;

    @Override
    public void onClickSalesLine(int position, String status) {
//        this.getPos=position;
//        this.status=status;

//        salesLineList.get(position).setStatus(status);
//
//        if (orderAdapter != null)
//            orderAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClickItemStatusUpdate(int orderAdapterPos, int newSelectedOrderAdapterPos, String status) {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setStatus(status);
            boolean isNotAvailable = true;
            boolean isFull = true;
            boolean isNull = false;
            for (int i = 0; i < selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().size(); i++) {

                if (selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(i).getStatus() != null) {
                    if (!selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(i).getStatus().equals("NOT AVAILABLE")) {
                        isNotAvailable = false;
                    }
                    if (!selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(i).getStatus().equals("FULL")) {
                        isFull = false;
                    }
                } else {
                    isNull = true;
                }
            }

            if (!isNull) {
                if (isNotAvailable) {
                    selectedOmsHeaderList.get(orderAdapterPos).setItemStatus("NOT AVAILABLE");
                    orderAdapter.setSelectedOmsHeaderList(selectedOmsHeaderList);
                    orderAdapter.notifyItemChanged(orderAdapterPos);
                    selectedOmsHeaderList.get(orderAdapterPos).setOverallOrderStatus("3");
                } else if (isFull) {
                    selectedOmsHeaderList.get(orderAdapterPos).setItemStatus("FULL");
                    orderAdapter.setSelectedOmsHeaderList(selectedOmsHeaderList);
                    orderAdapter.notifyItemChanged(orderAdapterPos);
                    selectedOmsHeaderList.get(orderAdapterPos).setOverallOrderStatus("1");
                } else if (!isNotAvailable && !isFull) {
                    selectedOmsHeaderList.get(orderAdapterPos).setItemStatus("PARTIAL");
                    orderAdapter.setSelectedOmsHeaderList(selectedOmsHeaderList);
                    orderAdapter.notifyItemChanged(orderAdapterPos);
                    selectedOmsHeaderList.get(orderAdapterPos).setOverallOrderStatus("2");
                }
            } else {
                orderAdapter.setSelectedOmsHeaderList(selectedOmsHeaderList);
                orderAdapter.notifyItemChanged(orderAdapterPos);
            }
        }
        boolean isAllStatusUpdated = true;
        boolean isAllonHoldOrders = true;
        int selected = 0;
        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
            for (int j = 0; j < selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                if (!selectedOmsHeaderList.get(i).isOnHold()) {
                    isAllonHoldOrders = false;
                    if (selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus() == null || selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus().isEmpty()) {
                        isAllStatusUpdated = false;
                    }
                }
            }
            if (!selectedOmsHeaderList.get(i).isOnHold()) {
                selected++;
            }
        }
        if (isAllonHoldOrders) {
            pickupProcessBinding.onHold.setBackgroundResource(R.drawable.bg_onhold_disable_btn);
            pickupProcessBinding.onHold.setEnabled(false);
            isAllOnHold = true;
            pickupProcessBinding.continueOrder.setVisibility(View.GONE);
            pickupProcessBinding.continueOrders.setVisibility(View.VISIBLE);
            pickupProcessBinding.selectedItemCount.setText(selectedOmsHeaderList.size() + "/" + selectedOmsHeaderList.size());
        } else if (isAllStatusUpdated && !isAllonHoldOrders) {
            pickupProcessBinding.continueOrder.setVisibility(View.GONE);
            pickupProcessBinding.continueOrders.setVisibility(View.VISIBLE);
            pickupProcessBinding.selectedItemCount.setText(selected + "/" + selectedOmsHeaderList.size());
        }
        int rackWiseSortedDataPos = -1;
        if (rackWiseSortedDataList != null && rackWiseSortedDataList.size() > 0) {
            for (int i = 0; i < rackWiseSortedDataList.size(); i++) {
                for (int j = 0; j < rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                    if (rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getRackId().equals(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getRackId())
                            && rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getItemId().equals(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getItemId())
                            && rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getFullfillmentId().equals(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getFullfillmentId())) {
                        rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().set(j, selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos));
                        rackWiseSortedDataPos = i;
                        break;
                    }
                }
            }
        }
        if (rackWiseSortedDataPos != -1) {
            boolean isFull = true;
            boolean isNotAvailable = true;
            boolean isNull = false;
            for (int i = 0; i < rackWiseSortedDataList.get(rackWiseSortedDataPos).getGetOMSTransactionResponse().getSalesLine().size(); i++) {
                if (rackWiseSortedDataList.get(rackWiseSortedDataPos).getGetOMSTransactionResponse().getSalesLine().get(i).getStatus() != null) {
                    if (!rackWiseSortedDataList.get(rackWiseSortedDataPos).getGetOMSTransactionResponse().getSalesLine().get(i).getStatus().equals("FULL")) {
                        isFull = false;
                    }
                    if (!rackWiseSortedDataList.get(rackWiseSortedDataPos).getGetOMSTransactionResponse().getSalesLine().get(i).getStatus().equals("NOT AVAILABLE")) {
                        isNotAvailable = false;
                    }
                } else {
                    isNull = true;
                }
            }
            if (!isNull) {
                if (isFull) {
                    rackWiseSortedDataList.get(rackWiseSortedDataPos).setRackStatus("FULL");
                } else if (isNotAvailable) {
                    rackWiseSortedDataList.get(rackWiseSortedDataPos).setRackStatus("NOT AVAILABLE");
                } else if (!isFull && !isNotAvailable) {
                    rackWiseSortedDataList.get(rackWiseSortedDataPos).setRackStatus("PARTIAL");
                }
            }
        }
        if (rackAdapter != null) {
            rackAdapter.notifyDataSetChanged();
        }

        boolean isAllRackStatusUpdated = true;
        boolean isAllRackOnHoldOrders = true;

        for (int i = 0; i < rackWiseSortedDataList.size(); i++) {
            for (int j = 0; j < rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                if (!rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).isOnHold()) {
                    isAllRackOnHoldOrders = false;
                    if (rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus() == null || rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus().isEmpty()) {
                        isAllRackStatusUpdated = false;
                    }
                }
            }
        }
        if (isAllonHoldOrders) {
            pickupProcessBinding.onHold.setBackgroundResource(R.drawable.bg_onhold_disable_btn);
            pickupProcessBinding.onHold.setEnabled(false);
            isAllOnHold = true;
            pickupProcessBinding.farwarToPackerBtn.setEnabled(true);
            pickupProcessBinding.farwarToPackerBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
            pickupProcessBinding.farwarToPackerBtn.setTextColor(getResources().getColor(R.color.black));
        } else if (isAllRackStatusUpdated && !isAllRackOnHoldOrders) {
            pickupProcessBinding.farwarToPackerBtn.setEnabled(true);
            pickupProcessBinding.farwarToPackerBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
            pickupProcessBinding.farwarToPackerBtn.setTextColor(getResources().getColor(R.color.black));
        } else {
            pickupProcessBinding.farwarToPackerBtn.setEnabled(false);
            pickupProcessBinding.farwarToPackerBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
            pickupProcessBinding.farwarToPackerBtn.setTextColor(getResources().getColor(R.color.text_color_grey));
        }

        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > orderAdapterPos
                && !selectedOmsHeaderList.get(orderAdapterPos).getItemStatus().equalsIgnoreCase("NOT AVAILABLE")
                && !selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            mposOrderUpdate(orderAdapterPos, "1");
        } else if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > orderAdapterPos
                && selectedOmsHeaderList.get(orderAdapterPos).getItemStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            mposOrderUpdate(orderAdapterPos, "2");
        }
    }


    private Dialog statusUpdateDialog;
    int orderAdapterPos, position;
    GetOMSTransactionResponse.SalesLine salesLinee;
    TransactionHeaderResponse.OMSHeader omsHeaderObj;
    ArrayList<GetBatchInfoRes.BatchListObj> batchListObjsList;
    boolean noBatchDetails = true;


    @Override
    public void onSuccessGetBatchDetails(GetBatchInfoRes getBatchDetailsResponse, GetOMSTransactionResponse.SalesLine salesLine, String refNo, int orderAdapterPos, int position, TransactionHeaderResponse.OMSHeader omsHeader) {
        this.orderAdapterPos = orderAdapterPos;
        this.position = position;
        this.salesLinee = salesLine;
        this.omsHeaderObj = omsHeader;
//     if (getBatchDetailsResponse != null && getBatchDetailsResponse.getBatchList() != null && getBatchDetailsResponse.getBatchList().size() > 0) {
        if (getBatchDetailsResponse != null && getBatchDetailsResponse.getBatchList() != null && getBatchDetailsResponse.getBatchList().size() > 0) {
            for (int i = 0; i < getBatchDetailsResponse.getBatchList().size(); i++) {
                if (getBatchDetailsResponse.getBatchList().get(i).isNearByExpiry()) {
                    getBatchDetailsResponse.getBatchList().remove(i);
                    i--;
                }
            }
        }

        if (getBatchDetailsResponse != null && getBatchDetailsResponse.getBatchList() != null && getBatchDetailsResponse.getBatchList().size() > 0) {
            onClickBatchDetails(orderAdapterPos, salesLine, position);
        } else {
            onClickItemStatusUpdate(orderAdapterPos, position, "NOT AVAILABLE");
        }
//            statusUpdateDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
//            dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_update_status_p, null, false);
//            statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
//            statusUpdateDialog.setCancelable(false);
//            dialogUpdateStatusBinding.fullfillmentId.setText(refNo);
//            dialogUpdateStatusBinding.boxId.setText(salesLine.getRackId());
//            dialogUpdateStatusBinding.productName.setText(salesLine.getItemName());
//            double totalBatchDetailsQuantity = 0.0;
//            for (int i = 0; i < getBatchDetailsResponse.getBatchList().size(); i++) {
//                totalBatchDetailsQuantity = totalBatchDetailsQuantity + Double.parseDouble(getBatchDetailsResponse.getBatchList().get(i).getQ_O_H());
//            }
//            String status = "";
//            if (totalBatchDetailsQuantity >= salesLine.getQty()) {
//                status = "FULL";
//                checkAllFalse();
//                dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
//                dialogUpdateStatusBinding.availableProFull.setText(String.valueOf(totalBatchDetailsQuantity).contains(".") ? String.valueOf(totalBatchDetailsQuantity).substring(0, String.valueOf(totalBatchDetailsQuantity).indexOf(".")) : String.valueOf(totalBatchDetailsQuantity));
//                dialogUpdateStatusBinding.requiredProFull.setText(String.valueOf(salesLine.getQty()));
//                dialogUpdateStatusBinding.qtyEditFull.setText(String.valueOf(salesLine.getQty()));
//                dialogUpdateStatusBinding.fullDetails.setVisibility(View.VISIBLE);
//            } else if (totalBatchDetailsQuantity > 0.0) {
//                status = "PARTIAL";
//                checkAllFalse();
//                dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
//                dialogUpdateStatusBinding.availableProPartial.setText(String.valueOf(totalBatchDetailsQuantity).contains(".") ? String.valueOf(totalBatchDetailsQuantity).substring(0, String.valueOf(totalBatchDetailsQuantity).indexOf(".")) : String.valueOf(totalBatchDetailsQuantity));
//                dialogUpdateStatusBinding.requiredProPartial.setText(String.valueOf(salesLine.getQty()));
//                dialogUpdateStatusBinding.qtyEditPartial.setText(String.valueOf(totalBatchDetailsQuantity).contains(".") ? String.valueOf(totalBatchDetailsQuantity).substring(0, String.valueOf(totalBatchDetailsQuantity).indexOf(".")) : String.valueOf(totalBatchDetailsQuantity));
//                dialogUpdateStatusBinding.partialDetails.setVisibility(View.VISIBLE);
//            } else {
//                status = "NOT AVAILABLE";
//                checkAllFalse();
//                dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);
//            }
//            dialogUpdateStatusBinding.skip.setOnClickListener(view -> {
//                checkAllFalse();
//                dialogUpdateStatusBinding.skipRadioBtn.setChecked(true);
//            });
//            dialogUpdateStatusBinding.dismissDialog.setOnClickListener(vie -> statusUpdateDialog.dismiss());
//
//            dialogUpdateStatusBinding.batchnavigation.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickBatchDetails(orderAdapterPos, null, position);
//                }
//            });
//
//            dialogUpdateStatusBinding.batchnavigation1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickBatchDetails(orderAdapterPos, null, position);
//                }
//            });
//            String finalStatus = status;
//            String finalStatus1 = status;
//            dialogUpdateStatusBinding.update.setOnClickListener(view1 -> {
//                if (dialogUpdateStatusBinding.skipRadioBtn.isChecked()) {
//                    statusUpdateDialog.dismiss();
//                } else {
//                    int requiredQty = salesLine.getQty();
//                    batchListObjsList = new ArrayList<>();
//                    for (int i = 0; i < getBatchDetailsResponse.getBatchList().size(); i++) {
//                        if (Double.parseDouble(getBatchDetailsResponse.getBatchList().get(i).getQ_O_H()) >= requiredQty) {
//                            getBatchDetailsResponse.getBatchList().get(i).setSelected(true);
//                            getBatchDetailsResponse.getBatchList().get(i).setREQQTY(requiredQty);
//                            batchListObjsList.add(getBatchDetailsResponse.getBatchList().get(i));
//                            selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(position).setStatus(finalStatus1);
//                            GetBatchInfoRes o = new GetBatchInfoRes();
//                            o.setBatchList(batchListObjsList);
//                            selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(position).setGetBatchInfoRes(o);
//                            mPresenter.checkBatchInventory(getBatchDetailsResponse.getBatchList().get(i), requiredQty, finalStatus);
//                            break;
//                        } else if (Double.parseDouble(getBatchDetailsResponse.getBatchList().get(i).getQ_O_H()) < requiredQty) {
//
//                            if (i == getBatchDetailsResponse.getBatchList().size() - 1) {
//                                getBatchDetailsResponse.getBatchList().get(i).setSelected(true);
//                                getBatchDetailsResponse.getBatchList().get(i).setREQQTY(Double.parseDouble(getBatchDetailsResponse.getBatchList().get(i).getQ_O_H()));
//                                batchListObjsList.add(getBatchDetailsResponse.getBatchList().get(i));
//                                selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(position).setStatus(finalStatus1);
//                                GetBatchInfoRes o = new GetBatchInfoRes();
//                                o.setBatchList(batchListObjsList);
//                                selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(position).setGetBatchInfoRes(o);
//                                mPresenter.checkBatchInventory(getBatchDetailsResponse.getBatchList().get(i), requiredQty, finalStatus);
//                                requiredQty = (int) (requiredQty - Double.parseDouble(getBatchDetailsResponse.getBatchList().get(i).getQ_O_H()));
//                            } else {
//                                getBatchDetailsResponse.getBatchList().get(i).setSelected(true);
//                                getBatchDetailsResponse.getBatchList().get(i).setREQQTY(Double.parseDouble(getBatchDetailsResponse.getBatchList().get(i).getQ_O_H()));
//                                batchListObjsList.add(getBatchDetailsResponse.getBatchList().get(i));
//                                mPresenter.checkBatchInventory(getBatchDetailsResponse.getBatchList().get(i), requiredQty, "");
//                                requiredQty = (int) (requiredQty - Double.parseDouble(getBatchDetailsResponse.getBatchList().get(i).getQ_O_H()));
//                            }
//
//
//                        }
//
//                    }
//                }
//                statusUpdateDialog.dismiss();
//            });
//            statusUpdateDialog.show();
//       }
//        else {
//            onClickBatchDetails(orderAdapterPos, salesLine, position);
//            Toast.makeText(this, "No batch details available", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public void checkBatchInventoryFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void onClickOrderAdapterArrow(int pos) {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                selectedOmsHeaderList.get(i).setExpanded(i == pos);
            }
            if (orderAdapter != null) {
                orderAdapter.setSelectedOmsHeaderList(selectedOmsHeaderList);
                orderAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 2) {
            if (mposPickPackOrderReservationResponse != null)
                doBackPressed();
        } else if (requestType == 5) {

        } else if (requestType == 1) {

        } else if (requestType == 10) {
            if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
                if (mposPickPackOrderReservationResponse.getOrderList() != null && mposPickPackOrderReservationResponse.getOrderList().size() > 0) {
                    if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                            for (int j = 0; j < mposPickPackOrderReservationResponse.getOrderList().size(); j++) {
                                if (selectedOmsHeaderList.get(i).getRefno().equals(mposPickPackOrderReservationResponse.getOrderList().get(j).getTransactionID())) {
                                    selectedOmsHeaderList.get(i).setOnHold(true);
                                    selectedOmsHeaderList.get(i).setExpanded(false);
                                    selectedOmsHeaderList.get(i).setExpandStatus(0);
                                }
                            }


//                            if (selectedOmsHeaderList.get(i).getRefno().equals(mposPickPackOrderReservationResponse.getOrderList().get(0).getTransactionID())) {
//                                selectedOmsHeaderList.get(i).setOnHold(true);
//                                selectedOmsHeaderList.get(i).setExpanded(false);
//                                selectedOmsHeaderList.get(i).setExpandStatus(0);
//                                if (orderAdapter != null) {
//                                    orderAdapter.setSelectedOmsHeaderList(selectedOmsHeaderList);
//                                    orderAdapter.notifyDataSetChanged();
//                                }
//                                rackDataSet();
//                                enableContinueButton();
//                                break;
//                            }
                        }
                        if (orderAdapter != null) {
                            orderAdapter.setSelectedOmsHeaderList(selectedOmsHeaderList);
                            orderAdapter.notifyDataSetChanged();
                        }
                        rackDataSet();
                        enableContinueButton();
                    }
                }
            }
        }
    }

    private void enableContinueButton() {
        boolean isAllStatusUpdated = true;
        boolean isAllonHoldOrders = true;
        int selected = 0;
        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
            for (int j = 0; j < selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                if (!selectedOmsHeaderList.get(i).isOnHold()) {
                    isAllonHoldOrders = false;
                    if (selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus() == null || selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus().isEmpty()) {
                        isAllStatusUpdated = false;
                    }
                }
            }
            if (!selectedOmsHeaderList.get(i).isOnHold()) {
                selected++;
            }
        }

        if (isAllonHoldOrders) {
            pickupProcessBinding.onHold.setBackgroundResource(R.drawable.bg_onhold_disable_btn);
            pickupProcessBinding.onHold.setEnabled(false);
            isAllOnHold = true;
            pickupProcessBinding.continueOrder.setVisibility(View.GONE);
            pickupProcessBinding.continueOrders.setVisibility(View.VISIBLE);
            pickupProcessBinding.selectedItemCount.setText(selectedOmsHeaderList.size() + "/" + selectedOmsHeaderList.size());
        } else if (isAllStatusUpdated && !isAllonHoldOrders) {
            pickupProcessBinding.continueOrder.setVisibility(View.GONE);
            pickupProcessBinding.continueOrders.setVisibility(View.VISIBLE);
            pickupProcessBinding.selectedItemCount.setText(selected + "/" + selectedOmsHeaderList.size());
        }

        boolean isAllRackStatusUpdated = true;
        boolean isAllRackOnHoldOrders = true;

        for (int i = 0; i < rackWiseSortedDataList.size(); i++) {
            for (int j = 0; j < rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                if (!rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).isOnHold()) {
                    isAllRackOnHoldOrders = false;
                    if (rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus() == null || rackWiseSortedDataList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getStatus().isEmpty()) {
                        isAllRackStatusUpdated = false;
                    }
                }
            }
        }
        if (isAllRackOnHoldOrders) {
            pickupProcessBinding.onHold.setBackgroundResource(R.drawable.bg_onhold_disable_btn);
            pickupProcessBinding.onHold.setEnabled(false);
            isAllOnHold = true;
            pickupProcessBinding.farwarToPackerBtn.setEnabled(true);
            pickupProcessBinding.farwarToPackerBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
            pickupProcessBinding.farwarToPackerBtn.setTextColor(getResources().getColor(R.color.black));
        } else if (isAllRackStatusUpdated && !isAllRackOnHoldOrders) {
            pickupProcessBinding.farwarToPackerBtn.setEnabled(true);
            pickupProcessBinding.farwarToPackerBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
            pickupProcessBinding.farwarToPackerBtn.setTextColor(getResources().getColor(R.color.black));
        } else {
            pickupProcessBinding.farwarToPackerBtn.setEnabled(false);
            pickupProcessBinding.farwarToPackerBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
            pickupProcessBinding.farwarToPackerBtn.setTextColor(getResources().getColor(R.color.text_color_grey));
        }
    }

    @Override
    public void onClickRackAdapter(int pos) {
        for (int i = 0; i < rackWiseSortedDataList.size(); i++) {
            if (i == pos) {
                if (rackWiseSortedDataList.get(i).isExpanded()) {
                    rackWiseSortedDataList.get(i).setExpanded(false);
                } else {
                    rackWiseSortedDataList.get(i).setExpanded(true);
                }
            }
        }

        boolean racksEshopCharge = false;
        if (rackWiseSortedDataList.get(pos).getGetOMSTransactionResponse() != null && rackWiseSortedDataList.get(pos).getGetOMSTransactionResponse().getSalesLine() != null && rackWiseSortedDataList.get(pos).getGetOMSTransactionResponse().getSalesLine().size() > 0) {
            for (GetOMSTransactionResponse.SalesLine salesLine : rackWiseSortedDataList.get(pos).getGetOMSTransactionResponse().getSalesLine()) {

                if (salesLine.getItemId().equals("ESH0002") && (salesLine.getStatus() == null || salesLine.getStatus().equals(""))) {
                    if (salesLine.getStatus() == null || salesLine.getStatus().equals("")) {
                        racksEshopCharge = true;
                        this.salesLinessEshopCharge = salesLine;
                        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                            if (selectedOmsHeaderList.get(i).getRefno().equals(salesLine.getFullfillmentId())) {
                                this.getOrderPos = i;
                                for (int j = 0; j < selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                                    if (selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).equals("ESH0002")) {
                                        this.newwAdapterposition = j;
                                    }
                                }
                            }
                        }
                    }
                    mPresenter.getBatchDetailsApi(salesLine);
                    break;
                }
            }
        }
        if (rackAdapter != null && !racksEshopCharge) {
            rackAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickRackItemStart(GetOMSTransactionResponse.SalesLine salesLine) {

        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                for (int j = 0; j < selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                    if (selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getItemId().equals(salesLine.getItemId())
                            && selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getFullfillmentId().equals(salesLine.getFullfillmentId())
                            && selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getRackId().equals(salesLine.getRackId())) {
                        orderAdapterPos = i;
                        position = j;
                        break;
                    }
                }
            }

            mPresenter.getBatchDetailsApiCall(salesLine, selectedOmsHeaderList.get(orderAdapterPos).getRefno(), orderAdapterPos, position, selectedOmsHeaderList.get(orderAdapterPos));
        }
    }

    @Override
    public void onClickForwardToPacker() {
        onClickContinue();
    }

    int newwOrderPosition;
    int newwAdapterposition;
    GetOMSTransactionResponse.SalesLine salesLinessEshopCharge;

    @Override
    public void onExpansionEshopCharge(TransactionHeaderResponse.OMSHeader omsHeader, int position, int newAdapterposition, GetOMSTransactionResponse.SalesLine salesLinesss) {

//        this.newwAdapterposition=newAdapterposition;
        this.newwOrderPosition = position;
        this.salesLinessEshopCharge = salesLinesss;


        for (int i = 0; i < selectedOmsHeaderList.get(position).getGetOMSTransactionResponse().getSalesLine().size(); i++) {
            if (selectedOmsHeaderList.get(position).getGetOMSTransactionResponse().getSalesLine().get(i).getItemId().equalsIgnoreCase("ESH0002")) {
                newwAdapterposition = i;
                mPresenter.getBatchDetailsApi(selectedOmsHeaderList.get(position).getGetOMSTransactionResponse().getSalesLine().get(newwAdapterposition));
//                selectedOmsHeaderList.get(position).setExpandStatus(1);

            } else {
//                 Toast.makeText(getApplicationContext(), "not available", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void checkBatchInventorySuccess(String status, CheckBatchInventoryRes body) {
        if (body != null && !status.isEmpty()) {
            hideLoading();
            onClickItemStatusUpdate(getOrderPos, newwAdapterposition, status);
        }
    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {
        status = "NOT AVAILABLE";
        hideLoading();
        selectedOmsHeaderList.get(getOrderPos).getGetOMSTransactionResponse().getSalesLine().get(newwAdapterposition).setStatus(status);
        onClickItemStatusUpdate(getOrderPos, newwAdapterposition, status);
    }

    @Override
    public void onSuccessBatchInfo(List<GetBatchInfoRes.BatchListObj> body) {
        if (body != null && body.size() > 0) {
            double totalBatchDetailsQuantity = 0.0;
            for (int i = 0; i < body.size(); i++) {
                totalBatchDetailsQuantity = totalBatchDetailsQuantity + Double.parseDouble(body.get(i).getQ_O_H());
            }
//            batchlistBinding.availableQuantity.setText(String.valueOf(totalBatchDetailsQuantity).contains(".") ? String.valueOf(totalBatchDetailsQuantity).substring(0, String.valueOf(totalBatchDetailsQuantity).indexOf(".")) : String.valueOf(totalBatchDetailsQuantity));
            status = "";
            if (totalBatchDetailsQuantity >= salesLinessEshopCharge.getQty()) {
                status = "FULL";
//                checkAllFalse();
//                batchlistBinding.fullPickedRadio.setChecked(true);
            } else if (totalBatchDetailsQuantity > 0.0) {
                status = "PARTIAL";
//                checkAllFalse();
//                batchlistBinding.partiallyPickedRadio.setChecked(true);
            } else {
                status = "NOT AVAILABLE";
//                checkAllFalse();
//                batchlistBinding.notAvailableRadio.setChecked(true);
            }

            String finalStatus = status;
            String finalStatus1 = status;
//            batchlistBinding.selectBatchesAutomatically.setOnClickListener(view1 -> {

            int requiredQty = salesLinessEshopCharge.getQty();
            batchListObjsList = new ArrayList<>();
            for (int i = 0; i < body.size(); i++) {
                if (Double.parseDouble(body.get(i).getQ_O_H()) >= requiredQty) {
                    body.get(i).setSelected(true);
                    body.get(i).setREQQTY(requiredQty);
                    body.get(i).setPhysicalBatchID(body.get(i).getBatchNo());
                    batchListObjsList.add(body.get(i));
                    selectedOmsHeaderList.get(getOrderPos).getGetOMSTransactionResponse().getSalesLine().get(newwAdapterposition).setStatus(finalStatus1);
                    GetBatchInfoRes o = new GetBatchInfoRes();
                    o.setBatchList(batchListObjsList);
                    selectedOmsHeaderList.get(getOrderPos).getGetOMSTransactionResponse().getSalesLine().get(newwAdapterposition).setGetBatchInfoRes(o);
                    mPresenter.checkBatchInventory(body.get(i), requiredQty, finalStatus);
                    break;
                } else if (Double.parseDouble(body.get(i).getQ_O_H()) < requiredQty) {

                    if (i == body.size() - 1) {
                        body.get(i).setSelected(true);
                        body.get(i).setREQQTY(Double.parseDouble(body.get(i).getQ_O_H()));
                        body.get(i).setPhysicalBatchID(body.get(i).getBatchNo());
                        batchListObjsList.add(body.get(i));
                        selectedOmsHeaderList.get(getOrderPos).getGetOMSTransactionResponse().getSalesLine().get(newwAdapterposition).setStatus(finalStatus1);
                        GetBatchInfoRes o = new GetBatchInfoRes();
                        o.setBatchList(batchListObjsList);
                        selectedOmsHeaderList.get(getOrderPos).getGetOMSTransactionResponse().getSalesLine().get(newwAdapterposition).setGetBatchInfoRes(o);
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
//            });


        }
    }

    @Override
    public void onClickOnHold(TransactionHeaderResponse.OMSHeader omsHeader) {
        List<TransactionHeaderResponse.OMSHeader> omsHeadersListOnHold = new ArrayList<>();
        omsHeadersListOnHold.add(omsHeader);
        Dialog dialog = new Dialog(this);// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(PickupProcessActivity.this), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialogCancelBinding.wraningIcon.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogCancelBinding.wraningIcon.setVisibility(View.VISIBLE);
        dialogCancelBinding.dialogMessage.setText("Do you want to on hold the order?");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            mPresenter.mposPickPackOrderReservationApiCall(10, omsHeadersListOnHold);
            dialog.dismiss();
        });
    }

    @Override
    public void onClickOnHoldAll() {
        Dialog dialog = new Dialog(this);// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(PickupProcessActivity.this), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialogCancelBinding.wraningIcon.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogCancelBinding.wraningIcon.setVisibility(View.VISIBLE);
        dialogCancelBinding.dialogMessage.setText("Do you want to on hold all the orders?");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            mPresenter.mposPickPackOrderReservationApiCall(10, selectedOmsHeaderList);
            dialog.dismiss();
        });
    }

//    private void checkAllFalse() {
//        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//        dialogUpdateStatusBinding.skipRadioBtn.setChecked(false);
//
//        dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
//        dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);

//    }

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void setUp() {
        pickupProcessBinding.setCallback(mPresenter);
        if (getIntent() != null) {
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra(CommonUtils.SELECTED_ORDERS_LIST);
            pickupProcessBinding.headerOrdersCount.setText("Total " + selectedOmsHeaderList.size() + " Orders");
            pickupProcessBinding.selectedFullfillment.setText("Selected Fulfilment: " + selectedOmsHeaderList.size() + "/" + OpenOrdersActivity.TOTAL_ORDERS);
            pickupProcessBinding.selectedItemCount.setText(selectedOmsHeaderList.size() + "/" + selectedOmsHeaderList.size());
            pickupProcessBinding.continueOrder.setVisibility(View.VISIBLE);
            pickupProcessBinding.continueOrders.setVisibility(View.GONE);

            orderAdapter = new OrderAdapter(PickupProcessActivity.this, selectedOmsHeaderList, PickupProcessActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PickupProcessActivity.this);
            pickupProcessBinding.orderRecycler.setLayoutManager(mLayoutManager);
            pickupProcessBinding.orderRecycler.setAdapter(orderAdapter);
            rackDataSet();
            pickupProcessBinding.continueOrder.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Please select all the orders", Toast.LENGTH_LONG).show());

        }
//        mPresenter.onRackApiCall();
        rackOrderCheckedListener();
//        Glide.with(this).load("https://apis.v35.dev.zeroco.de/zc-v3.1-fs-svc/2.0/apollo_rider/get/41B8F83052E720DA0FC28401C9BFAA90396DCB4FD14F508D641DBC42F5808C634160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8FA633B599A9A34C9C97BCF338231C7AA91F16F94D257D61803FBC97DE5FEEACF62933C5F49DFFBE9EBADD5C68A6A9245EE277F7369BEBB4A75B56F81CDA296FE0F45824C81F0E7A9C29BA1E691D49C48BCB3E2586250A732BC0C95D8C9A1E1154C38FC1DFED04C09C36722BD70B9D0E10952C6B12C3EABEF551397B781F83118196C4F5899C1A7EBB728DE8B78537C55B735B4BEAE021E0391CB1ACE72296B00A8869B3AA7F4BF1674AC2BF9952BF39A67ABCA6DC6BF69C69CCC9C5766F79B2F9").circleCrop().into(pickupProcessBinding.pickerImg);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm a");
        String strDate = mdformat.format(calendar.getTime());
        pickupProcessBinding.time.setText(strDate);

        stopWatch = (Chronometer) findViewById(R.id.chrono);
        startTime = SystemClock.elapsedRealtime();

        stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;

                String asText = (countUp / 60) + ":" + (countUp % 60);
//                pickupProcessBinding.timer.setText(asText);
//                 asText1 = stopWatch.getFormat();
//                int h = (int)(countUp /3600000);
//                int m = (int)(countUp - h*3600000)/60000;
//                int s= (int)(countUp - h*3600000- m*60000);
            }
        });
        stopWatch.start();
    }

    @Override
    public void onSuccessRackApi(RacksDataResponse racksDataResponse) {
//        for (int i = 0; i < racksDataResponse.getFullfillmentDetails().size(); i++) {
//            for (int j = 0; j < racksDataResponse.getFullfillmentDetails().get(i).getProducts().size(); j++) {
//                rackIdList.add(racksDataResponse.getFullfillmentDetails().get(i).getProducts().get(j));
//            }
//        }
//        for (int i = 0; i < rackIdList.size(); i++) {
//            for (int j = 0; j < rackIdList.size(); j++) {
//                if (i != j && rackIdList.get(i).getRackId().equals(rackIdList.get(j).getRackId())) {
//                    rackIdList.remove(j);
//                }
//            }
//        }
//
////        rackIdList.get(0).setExpandStatus(1);
////        racksDataResponse.getFullfillmentDetails().get(0).setExpandStatus(1);
//
//        rackAdapter = new RackAdapter(this, rackIdList, racksDataResponse, this, rackListOfList, false);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        pickupProcessBinding.rackRecycler.setLayoutManager(mLayoutManager);
//        pickupProcessBinding.rackRecycler.setAdapter(rackAdapter);
    }

    private void rackOrderCheckedListener() {
        pickupProcessBinding.rackOrderToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                pickupProcessBinding.rackRecycler.setVisibility(View.VISIBLE);
                pickupProcessBinding.orderRecycler.setVisibility(View.GONE);

                pickupProcessBinding.continueButtonLayout.setVisibility(View.GONE);
                pickupProcessBinding.farwarToPackerBtn.setVisibility(View.VISIBLE);
            } else {
                pickupProcessBinding.rackRecycler.setVisibility(View.GONE);
                pickupProcessBinding.orderRecycler.setVisibility(View.VISIBLE);

                pickupProcessBinding.farwarToPackerBtn.setVisibility(View.GONE);
                pickupProcessBinding.continueButtonLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void rackDataSet() {
        rackWiseSortedDataList = new ArrayList<>();
        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
            for (int j = 0; j < selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).setFullfillmentId(selectedOmsHeaderList.get(i).getRefno());
                selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).setOrderItemNo("B" + String.valueOf(i + 1));
                selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).setOnHold(selectedOmsHeaderList.get(i).isOnHold());
                if (rackWiseSortedDataList.size() > 0) {
                    int rackWiseSortedDataListPos = -1;
                    for (int k = 0; k < rackWiseSortedDataList.size(); k++) {
                        if (rackWiseSortedDataList.get(k).getRackId().equals(selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getRackId())) {
                            rackWiseSortedDataListPos = k;
                        }
                    }
                    if (rackWiseSortedDataListPos == -1) {
                        RackWiseSortedData rackWiseSortedData = new RackWiseSortedData();

                        rackWiseSortedData.setRackId(selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getRackId());

                        List<RackWiseSortedData.BoxIdModel> boxIdModelList = new ArrayList<>();
                        RackWiseSortedData.BoxIdModel boxIdModel = new RackWiseSortedData.BoxIdModel();
                        boxIdModel.setBoxId(selectedOmsHeaderList.get(i).getScannedBarcode());
                        boxIdModel.setOrderItemNo("B" + String.valueOf(i + 1));
                        boxIdModelList.add(boxIdModel);
                        rackWiseSortedData.setBoxIdList(boxIdModelList);

                        GetOMSTransactionResponse getOMSTransactionResponse = new GetOMSTransactionResponse();
                        List<GetOMSTransactionResponse.SalesLine> salesLineList = new ArrayList<>();
                        salesLineList.add(selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j));
                        getOMSTransactionResponse.setSalesLine(salesLineList);

                        rackWiseSortedData.setGetOMSTransactionResponse(getOMSTransactionResponse);
                        rackWiseSortedDataList.add(rackWiseSortedData);
                    } else {
                        RackWiseSortedData.BoxIdModel boxIdModel = new RackWiseSortedData.BoxIdModel();
                        boxIdModel.setBoxId(selectedOmsHeaderList.get(i).getScannedBarcode());
                        boxIdModel.setOrderItemNo("B" + String.valueOf(i + 1));
                        rackWiseSortedDataList.get(rackWiseSortedDataListPos).getBoxIdList().add(boxIdModel);

                        rackWiseSortedDataList.get(rackWiseSortedDataListPos).getGetOMSTransactionResponse().getSalesLine().add(selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j));
                    }
                } else {
                    selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).setFullfillmentId(selectedOmsHeaderList.get(i).getRefno());
                    RackWiseSortedData rackWiseSortedData = new RackWiseSortedData();
                    rackWiseSortedData.setRackId(selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j).getRackId());

                    List<RackWiseSortedData.BoxIdModel> boxIdModelList = new ArrayList<>();
                    RackWiseSortedData.BoxIdModel boxIdModel = new RackWiseSortedData.BoxIdModel();
                    boxIdModel.setBoxId(selectedOmsHeaderList.get(i).getScannedBarcode());
                    boxIdModel.setOrderItemNo("B" + String.valueOf(i + 1));
                    boxIdModelList.add(boxIdModel);
                    rackWiseSortedData.setBoxIdList(boxIdModelList);

                    GetOMSTransactionResponse getOMSTransactionResponse = new GetOMSTransactionResponse();
                    List<GetOMSTransactionResponse.SalesLine> salesLineList = new ArrayList<>();
                    salesLineList.add(selectedOmsHeaderList.get(i).getGetOMSTransactionResponse().getSalesLine().get(j));
                    getOMSTransactionResponse.setSalesLine(salesLineList);

                    rackWiseSortedData.setGetOMSTransactionResponse(getOMSTransactionResponse);
                    rackWiseSortedDataList.add(rackWiseSortedData);
                }
            }
        }


        rackAdapter = new RackAdapter(this, rackWiseSortedDataList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        pickupProcessBinding.rackRecycler.setLayoutManager(mLayoutManager);
        pickupProcessBinding.rackRecycler.setAdapter(rackAdapter);
    }

    List<List<RackAdapter.RackBoxModel.ProductData>> rackListOfListFiltered;

    @Override
    public void onRackStatusUpdate(List<List<RackAdapter.RackBoxModel.ProductData>> listOfList) {
        this.rackListOfListFiltered = listOfList;

//followed by code Just only to change  the continue btn color
        int statusCount = 0;
        int overallProductCount = 0;
        if (rackListOfList != null && rackListOfList.size() > 0) {

            for (int i = 0; i < racksDataResponse.size(); i++) {
                for (int j = 0; j < racksDataResponse.get(i).getProducts().size(); j++) {
                    for (int k = 0; k < rackListOfList.size(); k++) {
                        for (int l = 0; l < rackListOfList.get(k).size(); l++) {
                            if (racksDataResponse.get(i).getProducts().get(j).getProductId().equalsIgnoreCase(rackListOfList.get(k).get(l).getProductId())) {
                                racksDataResponse.get(i).getProducts().get(j).setFinalStatusUpdate(rackListOfList.get(k).get(l).isFinalStatusUpdate());
                            }
                        }
                    }
                }
            }
            if (rackListOfList != null && rackListOfList.size() > 0) {
                for (int i = 0; i < racksDataResponse.size(); i++) {
                    for (int j = 0; j < racksDataResponse.get(i).getProducts().size(); j++) {
                        overallProductCount = overallProductCount + 1;
                        if (racksDataResponse.get(i).getProducts().get(j).isFinalStatusUpdate()) {
                            statusCount = statusCount + 1;
                        }
                    }
                }
                if (statusCount == overallProductCount) {
                    pickupProcessBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_select_color));
//                    pickupProcessBinding.continueBtn.setTextColor(getResources().getColor(R.color.black));
                }
            }
        }

    }


    @Override
    public List<List<RackAdapter.RackBoxModel.ProductData>> productList() {
        return rackListOfListFiltered;
    }

    List<List<OrderAdapter.RackBoxModel.ProductData>> fullfillmentListOfListFiltered;

    @Override
    public void onFullfillmentOrderStatusUpdate(List<List<OrderAdapter.RackBoxModel.ProductData>> listOfList) {
        this.fullfillmentListOfListFiltered = listOfList;
    }

    @Override
    public List<List<OrderAdapter.RackBoxModel.ProductData>> fullfilListOfList() {
        return fullfillmentListOfListFiltered;
    }

    List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn;

    @Override
    public List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn(List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn) {
        this.productsNextPosReturn = productsNextPosReturn;
        return productsNextPosReturn;
    }

    @Override
    public List<RackAdapter.RackBoxModel.ProductData> productsNextPosReturn() {
        return productsNextPosReturn;
    }

    @Override
    public void onClickRightArrow(RacksDataResponse.FullfillmentDetail fullfillmentDetail) {
        startActivity(SelectedOrderPickupProcessActivity.getStartIntent(this, fullfillmentDetail));
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    @Override
    public void onClickBack() {
        onBackPressed();
    }


//        int statusCount = 0;
//        int overallProductCount = 0;
//        if (rackListOfList != null && rackListOfList.size() > 0) {
//
//            for (int i = 0; i < racksDataResponse.size(); i++) {
//                for (int j = 0; j < racksDataResponse.get(i).getProducts().size(); j++) {
//                    for (int k = 0; k < rackListOfList.size(); k++) {
//                        for (int l = 0; l < rackListOfList.get(k).size(); l++) {
//                            if (racksDataResponse.get(i).getProducts().get(j).getProductId().equalsIgnoreCase(rackListOfList.get(k).get(l).getProductId())) {
//                                racksDataResponse.get(i).getProducts().get(j).setFinalStatusUpdate(rackListOfList.get(k).get(l).isFinalStatusUpdate());
//                            }
//                        }
//                    }
//                }
//            }
//            if (rackListOfList != null && rackListOfList.size() > 0) {
//                for (int i = 0; i < racksDataResponse.size(); i++) {
//                    for (int j = 0; j < racksDataResponse.get(i).getProducts().size(); j++) {
//                        overallProductCount = overallProductCount + 1;
//                        if (racksDataResponse.get(i).getProducts().get(j).isFinalStatusUpdate()) {
//                            statusCount = statusCount + 1;
//                        }
//                    }
//                }
//                if (statusCount == overallProductCount) {
//                    stopWatch.stop();
//                    Gson gson = new Gson();
//                    String myJson = gson.toJson(rackListOfListFiltered);
//                    String myFullFillJson = gson.toJson(fullfillmentListOfListFiltered);
//                    startActivity(PickUpSummmaryActivityNew.getStartActivity(this, racksDataResponse, myJson, myFullFillJson, pickupProcessBinding.time.getText().toString(), pickupProcessBinding.timer.getText().toString()));
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                } else {
//                    Toast.makeText(this, "Collect Every Product Required Quantity", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } else {
//            Toast.makeText(this, "Collect Every Product Required Quantity", Toast.LENGTH_SHORT).show();
//        }


    boolean isNotAvailable = true;
    boolean isFull = true;
    boolean isNull = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && resultCode == RESULT_OK) {
//            statusUpdateDialog.dismiss();
            if (data != null) {
                selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) data.getSerializableExtra("selectedOmsHeaderList");
                statusBatchlist = (String) data.getStringExtra("finalStatus");
                onClickItemStatusUpdate(orderAdapterPos, position, statusBatchlist);
            }
        }

    }

    private int j = -1;

    public void mposOrderUpdate(int j, String requestType) {
        this.j = j;
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() >= j) {
            int count = 1;
//        for (int j = 0; j < selectedOmsHeaderList.size(); j++) {
            omsOrderForwardRequest = new OMSOrderForwardRequest();
            omsOrderForwardRequest.setRequestType(requestType);
            omsOrderForwardRequest.setFulfillmentID(selectedOmsHeaderList.get(j).getRefno());
            List<OMSOrderForwardRequest.ReservedSalesLine> reservedSalesLineArrayList = new ArrayList<>();

            if (omsOrderForwardRequest.getFulfillmentID().equalsIgnoreCase(selectedOmsHeaderList.get(j).getRefno())) {
                for (int k = 0; k < selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().size(); k++) {
                    if (selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes() != null && selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList() != null) {
                        for (int l = 0; l < selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().size(); l++) {
                            reservedSalesLine = new OMSOrderForwardRequest.ReservedSalesLine();
                            reservedSalesLine.setAdditionaltax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getAdditionaltax());
                            reservedSalesLine.setApplyDiscount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getApplyDiscount());
                            reservedSalesLine.setBarcode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getBarcode());
                            reservedSalesLine.setBaseAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMrp());
                            reservedSalesLine.setCESSPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCESSPerc());
                            reservedSalesLine.setCESSTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCESSTaxCode());
                            reservedSalesLine.setCGSTPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getCGSTPerc());
                            reservedSalesLine.setCGSTTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getCGSTTaxCode());
                            reservedSalesLine.setCategory(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCategory());
                            reservedSalesLine.setCategoryCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryCode());
                            reservedSalesLine.setCategoryReference(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryReference());
                            reservedSalesLine.setComment(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getComment());
                            reservedSalesLine.setDpco(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDpco());
                            reservedSalesLine.setDiscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
                            reservedSalesLine.setDiscOfferId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscOfferId());
                            reservedSalesLine.setDiscountStructureType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountStructureType());
                            reservedSalesLine.setDiscountType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
                            reservedSalesLine.setDiseaseType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiseaseType());
                            reservedSalesLine.setExpiry(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getExpDate());
                            reservedSalesLine.setHsncodeIn(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getHsncodeIn());
                            reservedSalesLine.setIGSTPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTPerc());
                            reservedSalesLine.setIGSTTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTTaxCode());
                            reservedSalesLine.setISPrescribed(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getISPrescribed());
                            reservedSalesLine.setISReserved(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getISReserved());
                            reservedSalesLine.setISStockAvailable(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getISStockAvailable());
                            reservedSalesLine.setInventBatchId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getBatchNo());
                            reservedSalesLine.setIsChecked(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsChecked());
                            reservedSalesLine.setIsGeneric(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsGeneric());
                            reservedSalesLine.setIsPriceOverride(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsPriceOverride());
                            reservedSalesLine.setIsSubsitute(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsSubsitute());
                            reservedSalesLine.setIsVoid(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsVoid());
                            reservedSalesLine.setItemId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getItemId());
                            reservedSalesLine.setItemName(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getItemName());
                            reservedSalesLine.setLineDiscPercentage(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
                            reservedSalesLine.setLineDiscPercentage(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
                            reservedSalesLine.setLineManualDiscountAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountAmount());
                            reservedSalesLine.setLineManualDiscountPercentage(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountPercentage());
                            reservedSalesLine.setLineNo(count);//selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineNo()
                            count++;
                            reservedSalesLine.setLinedscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLinedscAmount());
                            reservedSalesLine.setMMGroupId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMMGroupId());
                            reservedSalesLine.setMrp(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getMRP());
                            reservedSalesLine.setManufacturerCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerCode());
                            reservedSalesLine.setManufacturerName(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerName());
                            reservedSalesLine.setMixMode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMixMode());
                            reservedSalesLine.setModifyBatchId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getPhysicalBatchID());
                            reservedSalesLine.setNetAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmount());
                            reservedSalesLine.setNetAmountInclTax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmountInclTax());
                            reservedSalesLine.setOfferAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferAmount());
                            reservedSalesLine.setDiscountType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
                            reservedSalesLine.setOfferDiscountValue(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferDiscountValue());
                            reservedSalesLine.setOfferQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferQty());
                            reservedSalesLine.setOfferType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferType());
                            reservedSalesLine.setOmsLineRECID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOmsLineRECID());
                            reservedSalesLine.setOrderStatus(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOrderStatus());
                            reservedSalesLine.setOriginalPrice(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOriginalPrice());
                            reservedSalesLine.setPeriodicDiscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPeriodicDiscAmount());
                            reservedSalesLine.setPhysicalMRP(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPhysicalMRP());
                            reservedSalesLine.setPreviewText(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPreviewText());
                            reservedSalesLine.setPrice(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getPrice());
                            reservedSalesLine.setProductRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getProductRecID());
                            String reqQtyDoubleDataFormat = String.valueOf(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getREQQTY());
                            int reqQty = 0;
                            if (reqQtyDoubleDataFormat.contains(".")) {
                                reqQty = Integer.parseInt(reqQtyDoubleDataFormat.substring(0, reqQtyDoubleDataFormat.indexOf(".")));
                            }
                            reservedSalesLine.setQty(reqQty);
                            reservedSalesLine.setRemainderDays(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRemainderDays());
                            reservedSalesLine.setRemainingQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRemainingQty());
                            reservedSalesLine.setResqtyflag(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getResqtyflag());
                            reservedSalesLine.setRetailCategoryRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRetailCategoryRecID());
                            reservedSalesLine.setRetailMainCategoryRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRetailMainCategoryRecID());
                            reservedSalesLine.setRetailSubCategoryRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRetailSubCategoryRecID());
                            reservedSalesLine.setReturnQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getReturnQty());
                            reservedSalesLine.setSGSTPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getSGSTPerc());
                            reservedSalesLine.setSGSTTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getSGSTTaxCode());
                            reservedSalesLine.setScheduleCategory(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategory());
                            reservedSalesLine.setScheduleCategoryCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategoryCode());
                            reservedSalesLine.setStockQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getStockQty());
                            reservedSalesLine.setSubCategory(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategory());
                            reservedSalesLine.setSubCategoryCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategoryCode());
                            reservedSalesLine.setSubClassification(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubClassification());
                            reservedSalesLine.setSubstitudeItemId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubstitudeItemId());
                            reservedSalesLine.setTax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTax());
                            reservedSalesLine.setTaxAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTaxAmount());
                            reservedSalesLine.setTotal(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMrp());
                            reservedSalesLine.setTotalDiscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
                            reservedSalesLine.setTotalDiscPct(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTotalDiscPct());
                            reservedSalesLine.setTotalRoundedAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTotalRoundedAmount());
                            reservedSalesLine.setTotalTax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getTotalTax());
                            reservedSalesLine.setUnit(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getUnit());
                            reservedSalesLine.setUnitPrice(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getMRP());
                            reservedSalesLine.setUnitQty(reqQty);
                            reservedSalesLine.setVariantId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getVariantId());
                            reservedSalesLine.setIsReturnClick(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).isReturnClick());
                            reservedSalesLine.setIsSelectedReturnItem(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).isSelectedReturnItem());

                            reservedSalesLineArrayList.add(reservedSalesLine);
                        }
                    }
                }

            }
            omsOrderForwardRequest.setReservedSalesLine(reservedSalesLineArrayList);
//            omsOrderForwardRequests.add(omsOrderForwardRequest);
//        }

//        for (int p = 0; p < omsOrderForwardRequests.size(); p++) {
//            OMSOrderForwardResponse o = new OMSOrderForwardResponse();
//            OmsOrderUpdateSuccess(o);
//            Toast.makeText(this, "oms update", Toast.LENGTH_SHORT).show();
            mPresenter.UpdateOmsOrder(omsOrderForwardRequest, requestType);
//        }
        }

    }


    @Override
    public void getBatchDetailsApiCall(GetOMSTransactionResponse.SalesLine salesLine, String refNo, int orderAdapterPos, int position, TransactionHeaderResponse.OMSHeader omsHeader) {
        mPresenter.getBatchDetailsApiCall(salesLine, refNo, orderAdapterPos, position, omsHeader);

    }

    @Override
    public void onClickBatchDetails(int orderAdapterPos, GetOMSTransactionResponse.SalesLine salesLine, int adapterPosition) {
        Intent i = new Intent(PickupProcessActivity.this, BatchListActivity.class);
        i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
        i.putExtra("omsHeader", (Serializable) omsHeaderObj);
        i.putExtra("salesLine", (Serializable) salesLinee);
        i.putExtra("orderAdapterPos", orderAdapterPos);
        i.putExtra("newSelectedOrderAdapterPos1", adapterPosition);
        i.putExtra("noBatchDetails", this.noBatchDetails);
        startActivityForResult(i, 777);
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    @Override
    public void onClickFullPicked() {

    }

    @Override
    public void onClickStausIcon() {

    }


    @Override
    public void onClickContinue() {
        if (isAllOnHold) {
            Intent intent = PickerNavigationActivity.getStartIntent(this, "PICKER");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        } else {
            startActivity(PickUpSummmaryActivityNew.getStartActivity(this, selectedOmsHeaderList, pickupProcessBinding.time.getText().toString(), pickupProcessBinding.chrono.getText().toString(), omsHeaderObj, salesLinee, orderAdapterPos, position));
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        }
    }

    @Override
    public void onClickStart(int position) {

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

    int count;

    @Override
    public void OmsOrderUpdateSuccess(OMSOrderForwardResponse response, String requestType) {
        if (requestType.equalsIgnoreCase("1")) {




//            mPresenter.mposPickPackOrderReservationApiCalls(5, selectedOmsHeaderList.get(j));
        } else if (requestType.equalsIgnoreCase("2")) {
            mPresenter.mposPickPackOrderReservationApiCalls(1, selectedOmsHeaderList.get(j));
        }

    }

    @Override
    public void OmsOrderUpdateFailure(OMSOrderForwardResponse response) {
        Toast.makeText(getApplicationContext(), "OrderUpdate is not Successfull", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickDropDown(Spinner spinner) {
        String[] items = new String[]{"Partially Filled", "Fully Filled", "Not Available"};
        ArrayAdapter<String> genderSpinnerPojo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
//                    Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
//                    ((TextView) v).setTypeface(externalFont);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
//                    Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
//                    ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        genderSpinnerPojo.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(genderSpinnerPojo);
        spinner.setSelection(0);
//if (spinner.equals("Partially Filled")){
//    orderBinding.prioritys.setVisibility(View.VISIBLE);
//    orderBinding.partiallyFilled.setVisibility(View.VISIBLE);
//    orderBinding.fullPrority.setVisibility(View.GONE);
//}
//else  if (spinner.equals("Fully Filled")){
//    orderBinding.prioritys.setVisibility(View.VISIBLE);
//    orderBinding.fullPrority.setVisibility(View.VISIBLE);
//    orderBinding.notAvailable.setVisibility(View.GONE);
//    orderBinding.partiallyFilled.setVisibility(View.GONE);
//
//}else if (spinner.equals("Not Available")){
//    orderBinding.prioritys.setVisibility(View.VISIBLE);
//    orderBinding.fullPrority.setVisibility(View.GONE);
//    orderBinding.notAvailable.setVisibility(View.VISIBLE);
//    orderBinding.partiallyFilled.setVisibility(View.GONE);
//
//}


    }

//    String[] items=new String[]{
//                "Partially Filled","Fully Filled","Not Available"
//        };
//        ArrayAdapter<String> genderSpinnerPojo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
//            @NotNull
//            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
//                View v = super.getView(position, convertView, parent);
////                    Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
////                    ((TextView) v).setTypeface(externalFont);
//                return v;
//            }
//
//            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
//                View v = super.getDropDownView(position, convertView, parent);
////                    Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
////                    ((TextView) v).setTypeface(externalFont);
//                return v;
//            }
//        };
//        genderSpinnerPojo.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        spinner.setAdapter(genderSpinnerPojo);
//        spinner.setSelection(0);
//
//

    @SuppressLint("SetTextI18n")
    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(PickupProcessActivity.this), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialogCancelBinding.wraningIcon.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogCancelBinding.wraningIcon.setVisibility(View.VISIBLE);
        dialogCancelBinding.dialogMessage.setText("The Changes made will be discarded and you'll be directed to Ready for Pickup Page.\n Do you still want to Continue?");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
//            mPresenter.mposPickPackOrderReservationApiCall(2, selectedOmsHeaderList);
            unPickTheOrders();
            dialog.dismiss();
        });
//        dialogCancelBinding.dialogButtonNot.setOnClickListener(v -> dialog.dismiss());
//        doBackPressed();
    }

    private void doBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
    }


    private void unPickTheOrders() {
        omsOrderForwardRequests = new ArrayList<>();
        int count = 1;
        for (int j = 0; j < selectedOmsHeaderList.size(); j++) {
            omsOrderForwardRequest = new OMSOrderForwardRequest();
            omsOrderForwardRequest.setRequestType("2");
            omsOrderForwardRequest.setFulfillmentID(selectedOmsHeaderList.get(j).getRefno());
            List<OMSOrderForwardRequest.ReservedSalesLine> reservedSalesLineArrayList = new ArrayList<>();

            if (omsOrderForwardRequest.getFulfillmentID().equalsIgnoreCase(selectedOmsHeaderList.get(j).getRefno())) {
                for (int k = 0; k < selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().size(); k++) {
                    if (selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes() != null && selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList() != null) {
                        for (int l = 0; l < selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().size(); l++) {
                            reservedSalesLine = new OMSOrderForwardRequest.ReservedSalesLine();
                            reservedSalesLine.setAdditionaltax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getAdditionaltax());
                            reservedSalesLine.setApplyDiscount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getApplyDiscount());
                            reservedSalesLine.setBarcode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getBarcode());
                            reservedSalesLine.setBaseAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMrp());
                            reservedSalesLine.setCESSPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCESSPerc());
                            reservedSalesLine.setCESSTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCESSTaxCode());
                            reservedSalesLine.setCGSTPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getCGSTPerc());
                            reservedSalesLine.setCGSTTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getCGSTTaxCode());
                            reservedSalesLine.setCategory(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCategory());
                            reservedSalesLine.setCategoryCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryCode());
                            reservedSalesLine.setCategoryReference(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryReference());
                            reservedSalesLine.setComment(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getComment());
                            reservedSalesLine.setDpco(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDpco());
                            reservedSalesLine.setDiscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
                            reservedSalesLine.setDiscOfferId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscOfferId());
                            reservedSalesLine.setDiscountStructureType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountStructureType());
                            reservedSalesLine.setDiscountType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
                            reservedSalesLine.setDiseaseType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiseaseType());
                            reservedSalesLine.setExpiry(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getExpDate());
                            reservedSalesLine.setHsncodeIn(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getHsncodeIn());
                            reservedSalesLine.setIGSTPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTPerc());
                            reservedSalesLine.setIGSTTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTTaxCode());
                            reservedSalesLine.setISPrescribed(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getISPrescribed());
                            reservedSalesLine.setISReserved(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getISReserved());
                            reservedSalesLine.setISStockAvailable(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getISStockAvailable());
                            reservedSalesLine.setInventBatchId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getBatchNo());
                            reservedSalesLine.setIsChecked(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsChecked());
                            reservedSalesLine.setIsGeneric(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsGeneric());
                            reservedSalesLine.setIsPriceOverride(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsPriceOverride());
                            reservedSalesLine.setIsSubsitute(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsSubsitute());
                            reservedSalesLine.setIsVoid(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getIsVoid());
                            reservedSalesLine.setItemId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getItemId());
                            reservedSalesLine.setItemName(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getItemName());
                            reservedSalesLine.setLineDiscPercentage(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
                            reservedSalesLine.setLineDiscPercentage(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
                            reservedSalesLine.setLineManualDiscountAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountAmount());
                            reservedSalesLine.setLineManualDiscountPercentage(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountPercentage());
                            reservedSalesLine.setLineNo(count);//selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLineNo()
                            count++;
                            reservedSalesLine.setLinedscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getLinedscAmount());
                            reservedSalesLine.setMMGroupId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMMGroupId());
                            reservedSalesLine.setMrp(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getMRP());
                            reservedSalesLine.setManufacturerCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerCode());
                            reservedSalesLine.setManufacturerName(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerName());
                            reservedSalesLine.setMixMode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMixMode());
                            reservedSalesLine.setModifyBatchId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getPhysicalBatchID());
                            reservedSalesLine.setNetAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmount());
                            reservedSalesLine.setNetAmountInclTax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmountInclTax());
                            reservedSalesLine.setOfferAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferAmount());
                            reservedSalesLine.setDiscountType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
                            reservedSalesLine.setOfferDiscountValue(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferDiscountValue());
                            reservedSalesLine.setOfferQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferQty());
                            reservedSalesLine.setOfferType(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOfferType());
                            reservedSalesLine.setOmsLineRECID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOmsLineRECID());
                            reservedSalesLine.setOrderStatus(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOrderStatus());
                            reservedSalesLine.setOriginalPrice(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getOriginalPrice());
                            reservedSalesLine.setPeriodicDiscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPeriodicDiscAmount());
                            reservedSalesLine.setPhysicalMRP(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPhysicalMRP());
                            reservedSalesLine.setPreviewText(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPreviewText());
                            reservedSalesLine.setPrice(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getPrice());
                            reservedSalesLine.setProductRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getProductRecID());
                            String reqQtyDoubleDataFormat = String.valueOf(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getREQQTY());
                            int reqQty = 0;
                            if (reqQtyDoubleDataFormat.contains(".")) {
                                reqQty = Integer.parseInt(reqQtyDoubleDataFormat.substring(0, reqQtyDoubleDataFormat.indexOf(".")));
                            }
                            reservedSalesLine.setQty(reqQty);
                            reservedSalesLine.setRemainderDays(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRemainderDays());
                            reservedSalesLine.setRemainingQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRemainingQty());
                            reservedSalesLine.setResqtyflag(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getResqtyflag());
                            reservedSalesLine.setRetailCategoryRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRetailCategoryRecID());
                            reservedSalesLine.setRetailMainCategoryRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRetailMainCategoryRecID());
                            reservedSalesLine.setRetailSubCategoryRecID(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRetailSubCategoryRecID());
                            reservedSalesLine.setReturnQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getReturnQty());
                            reservedSalesLine.setSGSTPerc(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getSGSTPerc());
                            reservedSalesLine.setSGSTTaxCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getSGSTTaxCode());
                            reservedSalesLine.setScheduleCategory(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategory());
                            reservedSalesLine.setScheduleCategoryCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategoryCode());
                            reservedSalesLine.setStockQty(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getStockQty());
                            reservedSalesLine.setSubCategory(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategory());
                            reservedSalesLine.setSubCategoryCode(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategoryCode());
                            reservedSalesLine.setSubClassification(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubClassification());
                            reservedSalesLine.setSubstitudeItemId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getSubstitudeItemId());
                            reservedSalesLine.setTax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTax());
                            reservedSalesLine.setTaxAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTaxAmount());
                            reservedSalesLine.setTotal(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMrp());
                            reservedSalesLine.setTotalDiscAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
                            reservedSalesLine.setTotalDiscPct(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTotalDiscPct());
                            reservedSalesLine.setTotalRoundedAmount(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getTotalRoundedAmount());
                            reservedSalesLine.setTotalTax(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getTotalTax());
                            reservedSalesLine.setUnit(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getUnit());
                            reservedSalesLine.setUnitPrice(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getMRP());
                            reservedSalesLine.setUnitQty(reqQty);
                            reservedSalesLine.setVariantId(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getVariantId());
                            reservedSalesLine.setIsReturnClick(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).isReturnClick());
                            reservedSalesLine.setIsSelectedReturnItem(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).isSelectedReturnItem());

                            reservedSalesLineArrayList.add(reservedSalesLine);
                        }
                    }
                }

            }
            omsOrderForwardRequest.setReservedSalesLine(reservedSalesLineArrayList);
            omsOrderForwardRequests.add(omsOrderForwardRequest);
        }

        if (omsOrderForwardRequests != null && omsOrderForwardRequests.size() > 0) {
            for (int i = 0; i < omsOrderForwardRequests.size(); i++) {
                mPresenter.unPickUpdateOmsOrder(omsOrderForwardRequests.get(i), i == omsOrderForwardRequests.size() - 1, selectedOmsHeaderList);
            }
        }
    }
}
