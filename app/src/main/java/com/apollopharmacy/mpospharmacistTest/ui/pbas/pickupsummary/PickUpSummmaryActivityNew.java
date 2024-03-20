
package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickUpSummaryPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogConnectPrinterBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogDeleteBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogDroppingBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFarwardtoPackerAlertBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFarwardtoPackerPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.OrderAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.adapter.SummaryFullfillmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.scanner.PickupSummaryScannerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.scanner.PickupSummaryZebrascannerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.utils.BluetoothActivity;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.printf.manager.BluetoothManager;
import com.printf.manager.PrintfTSPLManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class PickUpSummmaryActivityNew extends BaseActivity implements PickUpSummaryMvpView {
    @Inject
    PickUpSummaryMvpPresenter<PickUpSummaryMvpView> mPresenter;
    ActivityPickUpSummaryPBinding activityPickUpSummaryBinding;
    long startTime;
    long countUp;
    public static int position;
    Chronometer stopWatchs;
    private SummaryFullfillmentAdapter summaryFullfillmentAdapter;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    List<List<RackAdapter.RackBoxModel.ProductData>> rackListOfListFiltered;
    ArrayList<SalesLineEntity> salesentity = new ArrayList<>();
    TransactionHeaderResponse.OMSHeader omsHeader;
    GetOMSTransactionResponse.SalesLine salesLineEntit;
    List<List<OrderAdapter.RackBoxModel.ProductData>> fullfillmentListOfListFiltered;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    String time, stopWatch;
    int orderAdapterPos, newSelectedOrderAdapterPos;
    ArrayList<GetOMSTransactionResponse.SalesLine> salesEntity = new ArrayList<>();
    private final int ACTIVITY_BARCODESCANNER_DETAILS_CODE = 151;

    int fullOrdersCount = 0;
    int partialOrdersCount = 0;
    int notAvailableOrdersCount = 0;
    int updateOmsOrderCount = 0;
    public static List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    String selectedStatus = "";

    public  static  boolean cameraenabled=false;
    public static Intent getStartActivity(Context context, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, String time, String stopWatch, TransactionHeaderResponse.OMSHeader omsHeader, GetOMSTransactionResponse.SalesLine salesLine, int orderAdapterPos, int position) {
        Intent intent = new Intent(context, PickUpSummmaryActivityNew.class);
        intent.putExtra(CommonUtils.SELECTED_ORDERS_LIST, (Serializable) selectedOmsHeaderList);
        intent.putExtra("time", time);
        intent.putExtra("stopWatch", stopWatch);
        intent.putExtra("omsHeader", (Serializable) omsHeader);
        intent.putExtra("salesLine", (Serializable) salesLine);
        intent.putExtra("orderAdapterPos", orderAdapterPos);
        intent.putExtra("newSelectedOrderAdapterPos1", position);
        return intent;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickUpSummaryBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick_up_summary_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PickUpSummmaryActivityNew.this);
        setUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void setUp() {

        activityPickUpSummaryBinding.setCallback(mPresenter);
        if (getIntent() != null) {
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra(CommonUtils.SELECTED_ORDERS_LIST);
            for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                if (selectedOmsHeaderList.get(i).isOnHold()) {
                    selectedOmsHeaderList.remove(i);
                    i--;
                }
            }
            omsHeader = (TransactionHeaderResponse.OMSHeader) getIntent().getSerializableExtra("omsHeader");
            salesLineEntit = (GetOMSTransactionResponse.SalesLine) getIntent().getSerializableExtra("salesLine");
            if (selectedOmsHeaderList != null) {
                activityPickUpSummaryBinding.headerOrdersCount.setText("Total " + selectedOmsHeaderList.size() + " Orders");
                boolean isAllTagBox = true;
                for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList) {
                    if (!omsHeader.isTagBox()) {
                        isAllTagBox = false;
                        break;
                    }
                }
                takePrintEnableHandled(isAllTagBox);

            }


//            Gson gson = new Gson();
//            String json = getIntent().getStringExtra("rackListOfListFiltered");
//            Type type = new TypeToken<List<List<RackAdapter.RackBoxModel.ProductData>>>() {
//            }.getType();
//            if (rackListOfListFiltered != null) {
//                rackListOfListFiltered.clear();
//            }
//            rackListOfListFiltered = gson.fromJson(json, type);
//
//            Gson gson1 = new Gson();
//            String json1 = getIntent().getStringExtra("fullListOfListFiltered");
//            Type type1 = new TypeToken<List<List<OrderAdapter.RackBoxModel.ProductData>>>() {
//            }.getType();
//            if (fullfillmentListOfListFiltered != null) {
//                fullfillmentListOfListFiltered.clear();
//            }
//            fullfillmentListOfListFiltered = gson1.fromJson(json1, type1);

            time = (String) getIntent().getStringExtra("time");
            stopWatch = (String) getIntent().getStringExtra("stopWatch");

            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                long partialCount = selectedOmsHeaderList.stream().filter(item -> item.getItemStatus() != null && item.getItemStatus().equalsIgnoreCase("PARTIAL")).count();
                long fullCount = selectedOmsHeaderList.stream().filter(item -> item.getItemStatus() != null && item.getItemStatus().equalsIgnoreCase("FULL")).count();
                long notAvailableCount = selectedOmsHeaderList.stream().filter(item -> item.getItemStatus() != null && item.getItemStatus().equalsIgnoreCase("NOT AVAILABLE")).count();
                activityPickUpSummaryBinding.full.setText(String.valueOf(fullCount));
                activityPickUpSummaryBinding.notAvailable.setText(String.valueOf(notAvailableCount));
                activityPickUpSummaryBinding.partially.setText(String.valueOf(partialCount));
            }
            activityPickUpSummaryBinding.time.setText(time);
            activityPickUpSummaryBinding.timer.setText(stopWatch);
            stopWatchs = (Chronometer) findViewById(R.id.chrono);
            startTime = SystemClock.elapsedRealtime();

            String[] sw = stopWatch.split(":");
            stopWatchs.setBase(SystemClock.elapsedRealtime() - (Integer.parseInt(sw[0]) * 60000 + Integer.parseInt(sw[1]) * 1000));

            stopWatchs.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
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
            stopWatchs.start();
        }
//        selectedOmsHeaderList.get(orderAdapterPos).setItemStatus(omsHeader.getItemStatus());

//        if (rackListOfListFiltered != null)
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            Collections.sort(selectedOmsHeaderList, (o1, o2) -> {
                String[] status = {"FULL", "PARTIAL", "NOT AVAILABLE"};
                int index1 = getIndex(status, o1.getItemStatus());
                int index2 = getIndex(status, o2.getItemStatus());
                return Integer.compare(index1, index2);
            });
            summaryFullfillmentAdapter = new SummaryFullfillmentAdapter(PickUpSummmaryActivityNew.this, selectedOmsHeaderList, PickUpSummmaryActivityNew.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PickUpSummmaryActivityNew.this);
            activityPickUpSummaryBinding.rackRecycler.setLayoutManager(mLayoutManager);
            activityPickUpSummaryBinding.rackRecycler.setAdapter(summaryFullfillmentAdapter);
        }

        activityPickUpSummaryBinding.headerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private int getIndex(String[] status, String itemStatus) {
        for (int i = 0; i < status.length; i++) {
            if (status[i].equals(itemStatus)) {
                return i;
            }
        }
        return status.length;
    }

    private void takePrintEnableHandled(boolean isTakePrintEnable) {
        if (!isTakePrintEnable) {
            activityPickUpSummaryBinding.printFlidBtn.setVisibility(View.VISIBLE);
//            activityReadyForPickupBinding.takePrint.setBackground(getResources().getDrawable(R.drawable.rounde_corner_takeprint_bg));
//            activityReadyForPickupBinding.takePrint.setTextColor(getResources().getColor(R.color.white));
//            activityReadyForPickupBinding.takePrint.setEnabled(true);

        } else {
            activityPickUpSummaryBinding.printFlidBtn.setVisibility(View.GONE);
//            activityReadyForPickupBinding.takePrint.setBackground(getResources().getDrawable(R.drawable.rounded_corner_takeprint_bg_disabled));
//            activityReadyForPickupBinding.takePrint.setTextColor(getResources().getColor(R.color.text_color_grey));
//            activityReadyForPickupBinding.takePrint.setEnabled(false);
        }

    }

    @Override
    public List<List<OrderAdapter.RackBoxModel.ProductData>> fullfilListOfList() {
        return fullfillmentListOfListFiltered;
    }

    @Override
    public List<List<RackAdapter.RackBoxModel.ProductData>> productList() {
        return rackListOfListFiltered;
    }

    String fullCount, partialCount, notCount;

    @Override
    public String fullCount(String fullCount) {
        this.fullCount = fullCount;
        return fullCount;
    }

    @Override
    public void onClickPrint() {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Dialog dialogView = new Dialog(this);// , R.style.Theme_AppCompat_DayNight_NoActionBar
            DialogConnectPrinterBinding connectPrinterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_connect_printer, null, false);
            dialogView.setContentView(connectPrinterBinding.getRoot());
            dialogView.setCancelable(false);
            dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            connectPrinterBinding.dialogButtonOK.setOnClickListener(view -> {
                dialogView.dismiss();
                startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            });
            connectPrinterBinding.dialogButtonNO.setOnClickListener(view -> dialogView.dismiss());
//            connectPrinterBinding.dialogButtonNot.setOnClickListener(view -> dialogView.dismiss());
            dialogView.show();
        } else {
            for (TransactionHeaderResponse.OMSHeader omsHeaders : selectedOmsHeaderList) {
                if (!omsHeaders.isTagBox())
                    generatecode(omsHeaders.getRefno());
            }
        }
    }

    int count = 0;

    @Override
    public void OmsOrderUpdateSuccess(OMSOrderForwardResponse response) {
        count++;
        if (count == (fullOrdersCount + partialOrdersCount)) {//omsOrderForwardRequests.size()
//            generatebarcode(selectedOmsHeaderList);
            mPresenter.mposPickPackOrderReservationApiCall(2, selectedOmsHeaderList);
        }
    }

    @Override
    public void OmsOrderUpdateFailure(OMSOrderForwardResponse response) {

    }


    @Override
    public String partialCount(String partialCount) {
        this.partialCount = partialCount;
        return partialCount;
    }

    @Override
    public void Forward_To_Pickerconfirmation() {
//        request = new ForwardToPickerRequest();
//        request.setRequestType("3");
//        request.setFulfillmentID("FL20211217113000001");

    }

    @Override
    public String notAvailable(String notAvailableCount) {
        this.notCount = notAvailableCount;
        return notAvailableCount;
    }

    @Override
    public void OmsOrderUpdateSuccess(OMSOrderUpdateResponse response) {
        Toast.makeText(getApplicationContext(), "Success!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void OmsOrderUpdateFailure(OMSOrderUpdateResponse response) {

    }

   /* @Override
    public void onClickItem(int pos, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, String selectedStatus, String itemStatus) {

        position=pos;
        omsHeaderList.clear();
        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
            if (selectedOmsHeaderList.get(i).isEnabled() && selectedOmsHeaderList.get(i).getItemStatus().equalsIgnoreCase(itemStatus)) {
                omsHeaderList.add(selectedOmsHeaderList.get(i));
            }
        }
        this.selectedStatus = selectedStatus;

//        new IntentIntegrator(this).setCaptureActivity(com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity.class).initiateScan();
        Intent intent = new Intent(PickUpSummmaryActivityNew.this, PickupSummaryScannerActivity.class);
        intent.putExtra("selectedStatus", selectedStatus);
        startActivityForResult(intent, 222);
        this.overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        //        startActivity(PickupSummaryDetailsActivity.getStartActivity(this, selectedOmsHeaderList.get(pos), activityPickUpSummaryBinding.time.getText().toString(), activityPickUpSummaryBinding.chrono.getText().toString(), selectedOmsHeaderList.get(pos).getScannedBarcode()));
//        startActivity(PickupSummaryDetailsActivity.getStartIntent(this, selectedOmsHeaderList.get(pos)));
    }*/

    @Override
    public void onClickItem(int pos, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, String selectedStatus, String itemStatus) {

        position=pos;
        String boxid="";
        omsHeaderList.clear();
        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
            if (selectedOmsHeaderList.get(i).isEnabled() && selectedOmsHeaderList.get(i).getItemStatus().equalsIgnoreCase(itemStatus)) {
                omsHeaderList.add(selectedOmsHeaderList.get(i));
                boxid=selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode();
            }
        }
        this.selectedStatus = selectedStatus;

//        new IntentIntegrator(this).setCaptureActivity(com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity.class).initiateScan();
       /* Intent intent = new Intent(PickUpSummmaryActivityNew.this, PickupSummaryScannerActivity.class);
        intent.putExtra("selectedStatus", selectedStatus);
        startActivityForResult(intent, 222);
        this.overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);*/

        Intent intent = new Intent(PickUpSummmaryActivityNew.this, PickupSummaryZebrascannerActivity.class);
        intent.putExtra("selectedStatus", selectedStatus);
        intent.putExtra("boxid",boxid);
        startActivityForResult(intent, 222);
        this.overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);

        //        startActivity(PickupSummaryDetailsActivity.getStartActivity(this, selectedOmsHeaderList.get(pos), activityPickUpSummaryBinding.time.getText().toString(), activityPickUpSummaryBinding.chrono.getText().toString(), selectedOmsHeaderList.get(pos).getScannedBarcode()));
//        startActivity(PickupSummaryDetailsActivity.getStartIntent(this, selectedOmsHeaderList.get(pos)));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void ondeleteItem(int pos, List<TransactionHeaderResponse.OMSHeader> omsHeaderList) {

        Dialog dialog = new Dialog(this);
        DialogDeleteBinding dialogDeleteBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.dialog_delete, null, false);

        dialog.setContentView(dialogDeleteBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogDeleteBinding.dialogButtonNO.setOnClickListener(view -> dialog.dismiss());
        dialogDeleteBinding.dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOmsHeaderList.get(pos).setScanned(false);
                summaryFullfillmentAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialogDeleteBinding.close.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onClickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    List<OMSOrderForwardRequest.ReservedSalesLine> pick_pack_list = new ArrayList<>();
    List<OMSOrderForwardRequest.ReservedSalesLine> salesLines = new ArrayList<>();
    OMSOrderForwardRequest omsOrderForwardRequest;
    List<OMSOrderForwardRequest> omsOrderForwardRequests = new ArrayList<>();
    OMSOrderForwardRequest.ReservedSalesLine reservedSalesLine;

    int p;

    @Override
    public void onClickUpdateOMSOrder_pickingconfirmation() {


//    @Override
//    public void forwardtoPacker() {
//        OMSOrderUpdateRequest omsOrderUpdateRequest = new OMSOrderUpdateRequest();
//
//        for(int i =0; i<selectedOmsHeaderList.size();i++){
//            omsOrderUpdateRequest.setRequestType("1");
//            omsOrderUpdateRequest.setFulfillmentID(selectedOmsHeaderList.get(i).getRefno());
//        }
//
//        mPresenter.UpdateOmsOrder(omsOrderUpdateRequest);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (selectedOmsHeaderList.size()==selectedOmsHeaderList.stream().filter(TransactionHeaderResponse.OMSHeader::isScanned).collect(Collectors.toList()).size()){
                Dialog dialog = new Dialog(this);
                DialogFarwardtoPackerAlertBinding updateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                        R.layout.dialog_farwardto_packer_alert, null, false);
                dialog.setContentView(updateStatusBinding.getRoot());
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                int full = 0;
                int partial = 0;
                int notAvailable = 0;
                if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                    for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList) {
                        if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equals("FULL")) {
                            full = full + 1;
                        }
                        if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equals("PARTIAL")) {
                            partial = partial + 1;
                        }
                        if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equals("NOT AVAILABLE")) {
                            notAvailable = notAvailable + 1;
                        }
                    }
                }
                updateStatusBinding.fullCount.setText(String.valueOf(full));
                updateStatusBinding.partialCount.setText(String.valueOf(partial));
                updateStatusBinding.notAvailableCount.setText(String.valueOf(notAvailable));
                this.fullOrdersCount = full;
                this.partialOrdersCount = partial;
                this.notAvailableOrdersCount = notAvailable;

//        if (fullCount != null)
//            updateStatusBinding.fullCount.setText(fullCount);
//        else
//            updateStatusBinding.fullCount.setText("0");
//        if (partialCount != null)
//            updateStatusBinding.partialCount.setText(partialCount);
//        else
//            updateStatusBinding.partialCount.setText("0");
//        if (notCount != null)
//            updateStatusBinding.notAvailableCount.setText(notCount);
//        else
//            updateStatusBinding.notAvailableCount.setText("0");

                updateStatusBinding.no.setOnClickListener(v -> {
                    dialog.dismiss();
                });
                updateStatusBinding.yes.setOnClickListener(v -> {
//            if (!BluetoothManager.getInstance(this).isConnect()) {
//                Dialog dialogView = new Dialog(this, R.style.Theme_AppCompat_DayNight_NoActionBar);
//                DialogConnectPrinterBinding connectPrinterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_connect_printer, null, false);
//                dialogView.setContentView(connectPrinterBinding.getRoot());
//                dialogView.setCancelable(false);
//                connectPrinterBinding.dialogButtonOK.setOnClickListener(view -> {
//                    dialogView.dismiss();
//                    startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//
//                });
//                connectPrinterBinding.dialogButtonNO.setOnClickListener(view -> dialogView.dismiss());
//                connectPrinterBinding.dialogButtonNot.setOnClickListener(view -> dialogView.dismiss());
//                dialogView.show();
//            } else {
                    omsOrderForwardRequests = new ArrayList<>();

                    int count = 1;
                    for (int j = 0; j < selectedOmsHeaderList.size(); j++) {
                        omsOrderForwardRequest = new OMSOrderForwardRequest();
                        omsOrderForwardRequest.setRequestType("1");
                        omsOrderForwardRequest.setFulfillmentID(selectedOmsHeaderList.get(j).getRefno());
                        List<OMSOrderForwardRequest.ReservedSalesLine> reservedSalesLineArrayList = new ArrayList<>();
                        if (omsOrderForwardRequest.getFulfillmentID().equalsIgnoreCase(selectedOmsHeaderList.get(j).getRefno())) {
                            for (int k = 0; k < selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().size(); k++) {
                                reservedSalesLine = new OMSOrderForwardRequest.ReservedSalesLine();
                                Integer requiredQty = selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getQty();
                                Integer pickedQty = Integer.parseInt(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPickedQty());
                                reservedSalesLine.setQCFailedQty(requiredQty - pickedQty);
                                reservedSalesLine.setQCRemarks(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getReason());
                                if (selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getStatus().equalsIgnoreCase("PARTIAL")) {
                                    reservedSalesLine.setQCStatus(1);
                                } else {
                                    reservedSalesLine.setQCStatus(0);
                                }
                                if (selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes() != null && selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList() != null) {
                                    for (int l = 0; l < selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().size(); l++) {
//                                        reservedSalesLine = new OMSOrderForwardRequest.ReservedSalesLine();
//                                        Integer requiredQty = selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getQty();
//                                        Integer pickedQty = Integer.parseInt(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getPickedQty());
//                                        reservedSalesLine.setQCFailedQty(requiredQty - pickedQty);
//                                        reservedSalesLine.setQCRemarks(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getReason());
//                                        if (selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getStatus().equalsIgnoreCase("PARTIAL")) {
//                                            reservedSalesLine.setQCStatus(1);
//                                        } else {
//                                            reservedSalesLine.setQCStatus(0);
//                                        }
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

                                        GetGlobalConfingRes getGlobalConfingRes = mPresenter.getGlobalConfigRes();
                                        boolean isoMSOrderDeliveryItem = false;
                                        for (int m = 0; m < getGlobalConfingRes.getoMSOrderDeliveryItemId().size(); m++) {
                                            if (getGlobalConfingRes.getoMSOrderDeliveryItemId().get(m).equalsIgnoreCase(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getItemId())) {
                                                isoMSOrderDeliveryItem = true;
                                                break;
                                            }
                                        }
                                        if (isoMSOrderDeliveryItem) {
                                            reservedSalesLine.setMrp(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMrp());
                                        } else {
                                            reservedSalesLine.setMrp(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getMRP());
                                        }
//                               if (selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getItemId().equalsIgnoreCase("ESH0002")
//                               || selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getItemId().equalsIgnoreCase("PAC0237")){
//                                   reservedSalesLine.setMrp(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getMrp());
//                               }else {
//                                   reservedSalesLine.setMrp(selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getMRP());
//                               }
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
                    boolean isAnyOrderAvailable = false;
                    for (p = 0; p < omsOrderForwardRequests.size(); p++) {
                        if (omsOrderForwardRequests.get(p).getReservedSalesLine() != null
                                && omsOrderForwardRequests.get(p).getReservedSalesLine().size() > 0) {
                            isAnyOrderAvailable = true;
                            mPresenter.UpdateOmsOrder(omsOrderForwardRequests.get(p));
                        }
                    }
                    if (!isAnyOrderAvailable) {
                        mPresenter.mposPickPackOrderReservationApiCall(2, selectedOmsHeaderList);
                    }
                    dialog.dismiss();
                    dialog.cancel();

//            mPresenter.ForwardToPickerRequest(request);


                });
                dialog.show();

                updateStatusBinding.cancelIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 2) {
            if (partialOrdersCount != 0 || fullOrdersCount != 0) {
                mPresenter.mposPickPackOrderReservationApiCall(5, selectedOmsHeaderList);
            } else {
                gotoOpenOrder("FAILED");
            }
        } else if (requestType == 5) {
            if (mposPickPackOrderReservationResponse != null)
                gotoOpenOrder("SUCCESS");
        } else if (requestType == 10) {
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0 && mposPickPackOrderReservationResponse != null) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    if (selectedOmsHeaderList.get(i).getRefno().equalsIgnoreCase(mposPickPackOrderReservationResponse.getOrderList().get(0).getTransactionID())) {
                        selectedOmsHeaderList.remove(i);
                        i--;
                    }
                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                selectedOmsHeaderList.removeIf(omsHeader -> omsHeader.getRefno().equalsIgnoreCase(mposPickPackOrderReservationResponse.getOrderList().get(0).getTransactionID()));
                if (selectedOmsHeaderList.size() > 0) {
                    summaryFullfillmentAdapter.notifyDataSetChanged();
                } else {
                    summaryFullfillmentAdapter.notifyDataSetChanged();
                    gotoOpenOrder("FAILED");
                }

//                }
            }
        }
    }

    @Override
    public void onClickOnHold(TransactionHeaderResponse.OMSHeader omsHeader) {
        List<TransactionHeaderResponse.OMSHeader> omsHeadersListOnHold = new ArrayList<>();
        omsHeadersListOnHold.add(omsHeader);
        Dialog dialog = new Dialog(this);// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_cancel, null, false);
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
    public void onClickEnableBtn(List<TransactionHeaderResponse.OMSHeader> omsHeaderList) {
        for (TransactionHeaderResponse.OMSHeader omsHeader : omsHeaderList) {
            omsHeader.setEnabled(true);
        }
        summaryFullfillmentAdapter.notifyDataSetChanged();
    }

    private void gotoOpenOrder(String status) {
        Dialog dialog = new Dialog(this);
        DialogFarwardtoPackerPBinding updateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.dialog_farwardto_packer_p, null, false);
        if (status.equalsIgnoreCase("SUCCESS")) {
            updateStatusBinding.message.setText("Forwarded to Checker");
            updateStatusBinding.statusImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark));
        } else {
            updateStatusBinding.message.setText("Orders Not Available");
            updateStatusBinding.statusImage.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.red)));
            updateStatusBinding.statusImage.setImageDrawable(getResources().getDrawable(R.drawable.delete_white_icon));
        }
        dialog.setContentView(updateStatusBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        updateStatusBinding.gotoOpenOrders.setOnClickListener(v -> {
            mPresenter.setFullfillmentData(racksDataResponse);
            mPresenter.setListOfListFullfillmentData(rackListOfListFiltered);
            Intent intent = new Intent(PickUpSummmaryActivityNew.this, PickerNavigationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("FRAGMENT_NAME", "PICKER");
            intent.putExtra("EXIT", true);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
            dialog.dismiss();
        });
        dialog.show();
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mPresenter.setFullfillmentData(racksDataResponse);
                mPresenter.setListOfListFullfillmentData(rackListOfListFiltered);
                Intent intent = new Intent(PickUpSummmaryActivityNew.this, PickerNavigationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("FRAGMENT_NAME", "PICKER");
                intent.putExtra("EXIT", true);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
                dialog.dismiss();
            }
        }.start();
    }


    public void generatecode(String refnumber) {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Your printer is disconnected. Please connect to Printer by clicking on Reprint Barcode", Toast.LENGTH_LONG).show();
        } else {
            PrintfTSPLManager instance = PrintfTSPLManager.getInstance(PickUpSummmaryActivityNew.this);
            instance.clearCanvas();
            instance.initCanvas(90, 23);
            instance.setDirection(0);
            //打印条形码
            //Print barcode
            instance.printBarCode(20, 10, "128", 130, 2, 2, 0, refnumber);
            instance.beginPrintf(1);
        }
    }


    public static class SummaryProductsData {
        private String product;
        private String qty;
        private int productStatus;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public int getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(int productStatus) {
            this.productStatus = productStatus;
        }
    }


    public static class SummaryFullfillmentData {
        private String fullfilmentId;
        private int totalItems;
        private int boxId;
        private int orderStatus;

        public int getBoxId() {
            return boxId;
        }

        public void setBoxId(int boxId) {
            this.boxId = boxId;
        }

        public String getFullfilmentId() {
            return fullfilmentId;
        }

        public void setFullfilmentId(String fullfilmentId) {
            this.fullfilmentId = fullfilmentId;
        }

        public int getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(int totalItems) {
            this.totalItems = totalItems;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
//        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (data != null) {
            boolean isBarcodeMatched = data.getBooleanExtra("isBarcodeMatched", false);
            boolean isBackPressed = data.getBooleanExtra("IS_BACK_PRESSED", false);
            boolean isAllCompleted = data.getBooleanExtra("allCompleted", false);
//            String result = data.getStringExtra("result");
            if (isAllCompleted || isBackPressed) {
                summaryFullfillmentAdapter.notifyDataSetChanged();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (selectedOmsHeaderList.size() == selectedOmsHeaderList.stream().filter(TransactionHeaderResponse.OMSHeader::isScanned).collect(Collectors.toList()).size()) {
                        activityPickUpSummaryBinding.forwardToPacker.setBackgroundColor(Color.parseColor("#fdb813"));
                    }
                }
                /*if (selectedOmsHeaderList.get(position).getItemStatus().equalsIgnoreCase(result)) {
                    selectedOmsHeaderList.get(position).setScanned(true);
                    summaryFullfillmentAdapter.notifyDataSetChanged();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (selectedOmsHeaderList.size() == selectedOmsHeaderList.stream().filter(TransactionHeaderResponse.OMSHeader::isScanned).collect(Collectors.toList()).size()) {
                            activityPickUpSummaryBinding.forwardToPacker.setBackgroundColor(Color.parseColor("#fdb813"));
                        }
                    }
//                   Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();

                }*/
                /*else {
                    Dialog dialog = new Dialog(this);
                    DialogDroppingBinding dialogDroppingBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                            R.layout.dialog_dropping, null, false);

                    dialog.setContentView(dialogDroppingBinding.getRoot());
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialogDroppingBinding.ok.setOnClickListener(view -> dialog.dismiss());

                    dialogDroppingBinding.close.setOnClickListener(view -> dialog.dismiss());
                    dialog.show();
                }*/

                BillerOrdersActivity.isBillerActivity = false;
//                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else if (isBarcodeMatched) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    if (selectedOmsHeaderList.get(i).getItemStatus().equalsIgnoreCase(selectedStatus)) {
                        selectedOmsHeaderList.get(i).setEnabled(true);
                    }
                }
                summaryFullfillmentAdapter.notifyDataSetChanged();
            } else {
                Dialog dialog = new Dialog(this);
                DialogDroppingBinding dialogDroppingBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                        R.layout.dialog_dropping, null, false);

                dialog.setContentView(dialogDroppingBinding.getRoot());
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogDroppingBinding.ok.setOnClickListener(view -> dialog.dismiss());

                dialogDroppingBinding.close.setOnClickListener(view -> dialog.dismiss());
                dialog.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void generatebarcode(List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList) {
        if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
            for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList) {
                if (!BluetoothManager.getInstance(getContext()).isConnect()) {
                    Toast.makeText(getContext(), "Your printer is disconnected. Please connect to Printer by clicking on Reprint Barcode", Toast.LENGTH_LONG).show();
                } else {
                    PrintfTSPLManager instance = PrintfTSPLManager.getInstance(PickUpSummmaryActivityNew.this);
                    instance.clearCanvas();
                    instance.initCanvas(90, 23);
                    instance.setDirection(0);
                    //打印条形码
                    //Print barcode
                    instance.printBarCode(20, 10, "128", 130, 2, 2, 0, omsHeader.getRefno());
                    instance.beginPrintf(1);
                }
            }
        }
    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        if(cameraenabled == true)
        {
            cameraenabled=false;
            Intent intent = new Intent(PickUpSummmaryActivityNew.this, PickupSummaryScannerActivity.class);
            intent.putExtra("selectedStatus", selectedStatus);
            startActivityForResult(intent, 222);
            this.overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        }

    }
}
