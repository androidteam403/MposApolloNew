package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityZebraBatchlistScannerBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogAddBatchDetailsBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBatchAlertBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogOnHoldBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogRackAlertBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogReferToAdminBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogShelfScanSuccessBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogSkipOrderBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model.ReasonListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.zebraselfidscanner.ZebraSelfscanner;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ZebraBatchlistScanner extends BaseActivity implements  BatchlistScannerMvpView
{
    private static final int BATCH_LIST_DETAILS = 888;
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private boolean isFlashLightOn = false;
    private ActivityZebraBatchlistScannerBinding activityBatchlistScannerBinding;
    private ScannedBatchListAdapter scannedBatchListAdapter;
    private List<GetBatchInfoRes.BatchListObj> batchList = new ArrayList<>();
    private List<GetBatchInfoRes.BatchListObj> scannedBatchList = new ArrayList<>();
    DialogSkipOrderBinding dialogSkipOrderBinding;
    String status;
    private List<GetBatchInfoRes.BatchListObj> body;
    GetOMSTransactionResponse.SalesLine salesLine;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    int orderAdapterPos, newSelectedOrderAdapterPos;

    ArrayList<GetBatchInfoRes.BatchListObj> batchListObjsList = new ArrayList<>();
    private boolean allowChangeQty;
    private boolean allowMultiBatch;
    public static boolean isBatchListScanner;
    String statusBatchlist;
    Bundle savedInstanceState;
    Dialog dialog;
    public static String userName;
    public static String storeId;
    public static String terminalId;
    public static String eposUrl;
    public static String dataAreaId;
    public static String stateCode;
    @Inject
    BatchlistScannerMvpPresenter<BatchlistScannerMvpView> mPresenter;
    public static boolean isBarCodeProblem;
    private String scannedQty;
    public static boolean isBoxIdScan;

    double scannedQuantity = 0.0;
   // GetOMSTransactionResponse.SalesLine salesLine;
  //  List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
  //  List<GetBatchInfoRes.BatchListObj> batchList;
   // List<GetBatchInfoRes.BatchListObj> scannedBatchList;
    GetBatchInfoRes.BatchListObj batchListObj;
    List<GetBatchInfoRes.BatchListObj> salesLineBatchList = new ArrayList<>();
    Context applicationContext;
    Activity activity;
    DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_zebra_batchlist_scanner);
        activityBatchlistScannerBinding=DataBindingUtil.setContentView(this, R.layout.activity_zebra_batchlist_scanner);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ZebraBatchlistScanner.this);
        activityBatchlistScannerBinding.setCallback(mPresenter);

        String itemId = "";
        if (getIntent() != null) {
            itemId = getIntent().getStringExtra("ITEM_ID");
            salesLine = (GetOMSTransactionResponse.SalesLine) getIntent().getSerializableExtra("salesLine");
            body = (List<GetBatchInfoRes.BatchListObj>) getIntent().getSerializableExtra("BATCH_LIST");
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("selectedOmsHeaderList");
            orderAdapterPos = (int) getIntent().getSerializableExtra("orderAdapterPos");
            newSelectedOrderAdapterPos = (int) getIntent().getSerializableExtra("newSelectedOrderAdapterPos1");
            allowChangeQty = getIntent().getBooleanExtra("ALLOW_CHANGE_QTY", false);
            allowMultiBatch = getIntent().getBooleanExtra("ALLOW_MULTI_BATCH", false);
            isBatchListScanner = getIntent().getBooleanExtra("isBatchListScanner", false);
            scannedQty = getIntent().getStringExtra("scannedQty");
            scannedBatchList = (List<GetBatchInfoRes.BatchListObj>) getIntent().getSerializableExtra("scannedBatchList");
        }
        userName = mPresenter.userName();
        storeId = mPresenter.storeId();
        terminalId = mPresenter.terminalId();
        eposUrl = mPresenter.eposUrl();
        dataAreaId = mPresenter.dataAreaId();
        stateCode = mPresenter.stateCode();

        //Initialize barcode scanner view
       // barcodeScannerView = findViewById(R.id.zxing_barcode_scanners);
       // ImageView imageView = findViewById(R.id.squarebox);
       // barcodeScannerView.setTorchListener(this);

       // barcodeScannerView.initializeFromIntent(getIntent());
        activityBatchlistScannerBinding.scanbarcode.requestFocus();





        String finalItemId = itemId;
        activityBatchlistScannerBinding.flid.setText(salesLine.getFullfillmentId());
        if (selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode() != null) {
            if (!selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode().isEmpty()) {
                activityBatchlistScannerBinding.boxNumber.setText(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode());
            } else {
                activityBatchlistScannerBinding.boxNumber.setText("-");
            }
        } else {
            activityBatchlistScannerBinding.boxNumber.setText("-");
        }
        if (salesLine.getPickedQty() != null) {
            activityBatchlistScannerBinding.pickedQty.setText(salesLine.getPickedQty());
        } else {
            activityBatchlistScannerBinding.pickedQty.setText("0");
        }
        activityBatchlistScannerBinding.reqQty.setText(Integer.toString(salesLine.getQty()));
        activityBatchlistScannerBinding.rackId.setText(salesLine.getRackId());
        if (isBatchListScanner) {
            if (scannedQty != null && !scannedQty.isEmpty()) {
                activityBatchlistScannerBinding.pickedQty.setText(scannedQty.substring(0, scannedQty.indexOf(".")));
            } else {
                activityBatchlistScannerBinding.pickedQty.setText("0");
            }
        }
        /*barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                mPresenter.getBatchDetailsByBarCode(result.getText(), finalItemId);
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });*/

//

//        activityBatchlistScannerBinding.switchFlashlight.setVisibility(View.VISIBLE);
        /*capture = new CaptureManager(this, barcodeScannerView, getApplicationContext());
        this.savedInstanceState = savedInstanceState;
        capture.setCaptureManagerCallback(this);
        capture.setSalesLine(salesLine);
        capture.setSelectedOmsHeaderList(selectedOmsHeaderList);
        if (isBatchListScanner) {
            if (scannedBatchList != null && scannedBatchList.size() != 0) {
                capture.setScannedBatchList(scannedBatchList);
            }
        }
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();*/


        activityBatchlistScannerBinding.scanbarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    Scanbarcode(String.valueOf(s));
                }
            }
        });

        activityBatchlistScannerBinding.scanbarcode.setOnTouchListener(otl);



        /*ArrayList<GetBatchInfoRes.BatchListObj> filteredList = new ArrayList<>();
        activityBatchlistScannerBinding.search.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        activityBatchlistScannerBinding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchedText = textView.getText().toString().trim();
                    if (searchedText.isEmpty()) {
                        filteredList.clear();
                        filteredList.addAll(batchList);
                        scannedBatchListAdapter.filter(filteredList);
                        activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
                        activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
//                        activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.VISIBLE);
                        CommonUtils.hideKeyboard(BatchlistScannerActivity.this);
                        return true;
                    } else {
                        filteredList.clear();
                        for (int i = 0; i < batchList.size(); i++) {
                            if (batchList.get(i).getBatchNo().contains(searchedText)) {
                                filteredList.add(batchList.get(i));
                            }
                        }
                        if (filteredList.size() > 0) {
                            scannedBatchListAdapter.filter(filteredList);
                            activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
                            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
//                            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.VISIBLE);
                            CommonUtils.hideKeyboard(BatchlistScannerActivity.this);
                            return true;
                        } else {
                            activityBatchlistScannerBinding.noListFound.setVisibility(View.VISIBLE);
                            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.GONE);
//                            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.GONE);
                            CommonUtils.hideKeyboard(BatchlistScannerActivity.this);
                            return true;
                        }
                    }
                }
                return false;
            }
        });*/
        searchByFulfilmentId();
    }

    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch (View v, MotionEvent event) {
            return true; // the listener has consumed the event
        }
    };


    private void searchByFulfilmentId() {
        activityBatchlistScannerBinding.searchbybatchId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    activityBatchlistScannerBinding.clear.setVisibility(View.VISIBLE);
                } else {
                    activityBatchlistScannerBinding.clear.setVisibility(View.GONE);
                }
                if (editable.length() >= 2) {
                    if (scannedBatchListAdapter != null) {
                        scannedBatchListAdapter.getFilter().filter(editable);
                    }
                } else {
                    if (scannedBatchListAdapter != null) {
                        scannedBatchListAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }


    public  void Scanbarcode(String s)
    {
        if (isBatchListScanner && !isBarCodeProblem) {
            onBarcodeScan(s);
        } else if (scannedQuantity == salesLine.getQty()) {

            onCompleteScan(s, salesLineBatchList);
            activityBatchlistScannerBinding.scanbarcode.setText("");
            activityBatchlistScannerBinding.scanbarcode.requestFocus();

        } else {
            mPresenter.getBatchDetailsByzebraBarCode(s, salesLine.getItemId(), eposUrl,storeId, dataAreaId, terminalId, stateCode);
           // new CaptureManagerController(activity, CaptureManager.this).getBatchDetailsByBarCode(result.getText(), salesLine.getItemId(), BatchlistScannerActivity.eposUrl, BatchlistScannerActivity.storeId, BatchlistScannerActivity.dataAreaId, BatchlistScannerActivity.terminalId, BatchlistScannerActivity.stateCode);
        }
    }


    @Override
    public void onSuccessGetBatchDetailszebraBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse) {
        if (isBatchListScanner) {
            batchList = scannedBatchList;
            isBatchListScanner = false;
        } else {
            batchList = getBatchDetailsByBarcodeResponse.getBatchList();
        }
        boolean isMatched = false;
        if (batchList != null && batchList.size() > 0) {
            for (int i = 0; i < batchList.size(); i++) {
                if (batchList.get(i).isNearByExpiry()) {
                    batchList.remove(i);
                    i--;
                }
            }
            Collections.sort(batchList, (o1, o2) -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy"); // 31-Jan-2024
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(o1.getExpDate());
                    date2 = dateFormat.parse(o2.getExpDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            });
            for (int i = 0; i < batchList.size(); i++) {
                if (salesLine.getPreferredBatch().equalsIgnoreCase(batchList.get(i).getBatchNo())) {
                    // if qoh available add batch to sales line and increase req qty of batch item
                    if (batchList.get(i).getQ_O_H() != null) {
                        isMatched = true;
                        if (salesLineBatchList.size() > 0) {
                            for (int j = 0; j < salesLineBatchList.size(); j++) {
                                if (salesLineBatchList.get(j).getBatchNo().equalsIgnoreCase(batchList.get(j).getBatchNo())) {
                                    salesLineBatchList.get(j).setREQQTY(salesLineBatchList.get(j).getREQQTY() + 1);
                                } else {
                                    batchList.get(j).setREQQTY(this.batchList.get(j).getREQQTY() + 1);
                                    salesLineBatchList.add(this.batchList.get(j));
                                }
                            }
                        } else {
                            batchList.get(i).setREQQTY(batchList.get(i).getREQQTY() + 1);
                            salesLineBatchList.add(batchList.get(i));
                        }
                    }
                    batchListObj = batchList.get(i);
                }
            }
            if (!isMatched) {
                Collections.sort(batchList, (o1, o2) -> {
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    long diff1 = 0;
                    long diff2 = 0;
                    try {
                        diff1 = Math.abs(dateFormat.parse(o1.getExpDate()).getTime() - currentDate.getTime());
                        diff2 = Math.abs(dateFormat.parse(o2.getExpDate()).getTime() - currentDate.getTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return Long.compare(diff1, diff2);
                });
                batchListObj = batchList.get(0);
                if (salesLineBatchList.size() > 0) {
                    for (int j = 0; j < salesLineBatchList.size(); j++) {
                        if (salesLineBatchList.get(j).getBatchNo().equalsIgnoreCase(batchList.get(j).getBatchNo())) {
                            salesLineBatchList.get(j).setREQQTY(salesLineBatchList.get(j).getREQQTY() + 1);
                        } else {
                            batchList.get(j).setREQQTY(this.batchList.get(j).getREQQTY() + 1);
                            salesLineBatchList.add(this.batchList.get(j));
                        }
                    }
                } else {
                    batchListObj.setREQQTY(batchListObj.getREQQTY() + 1);
                    salesLineBatchList.add(batchListObj);
                }
            }
            double totalQty = 0.0;
            for (int i = 0; i < salesLineBatchList.size(); i++) {
                totalQty = totalQty + salesLineBatchList.get(i).getREQQTY();
            }
            scannedQuantity = totalQty;
            if (scannedQuantity == salesLine.getQty()) {
                onScanned(salesLineBatchList);
                dialogShow(Double.toString(scannedQuantity));
                enableCompleteBoxBtn();
            } else {
                onScanned(salesLineBatchList);
                dialogShow(Double.toString(scannedQuantity));
            }
        } else {
            invalidBarcode();
        }
    }
    @Override
    protected void setUp() {

    }

  /*  @Override
    public void onTorchOn() {
//        activityBatchlistScannerBinding.switchFlashlight.setText(R.string.turn_off_flashlight);
    }

    @Override
    public void onTorchOff() {
//        activityBatchlistScannerBinding.switchFlashlight.setText(R.string.turn_on_flashlight);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
//        barcodeScannerView.resume();
//        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // barcodeScannerView.pause();
//        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
//        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        capture.onSaveInstanceState(outState);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ZebraSelfscanner.isRackIdScanned=false;
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ZebraSelfscanner.isRackIdScanned=false;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        final MenuItem menuCloseItem = menu.findItem(R.id.action_close);
        View actionNotificationView = MenuItemCompat.getActionView(menuCloseItem);
        actionNotificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuCloseItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close) {
            this.finish();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            return true;
        }
        ZebraSelfscanner.isRackIdScanned=false;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickClose() {
        super.onBackPressed();
        ZebraSelfscanner.isRackIdScanned=false;
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClickSkip() {
        mPresenter.getReasonList();
    }

    @Override
    public void onClickBarcodeProblem() {
//        if (batchList != null && batchList.size() > 0) {
        isBarCodeProblem = true;
        Intent intent = new Intent(ZebraBatchlistScanner.this, BatchListActivity.class);
        intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
        intent.putExtra("orderAdapterPos", orderAdapterPos);
        intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
        intent.putExtra("salesLine", salesLine);
        intent.putExtra("scannedBatchList", (Serializable) batchList);
        intent.putExtra("isBarCodeProblem", isBarCodeProblem);
        intent.putExtra("scannedQty", scannedQty);
        startActivityForResult(intent, BATCH_LIST_DETAILS);
//            finish();
//        }
    }

    @Override
    public void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchInfoRes) {
        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (320 * scale + 0.5f);
        activityBatchlistScannerBinding.scannerLayout.getLayoutParams().height = pixels;
        activityBatchlistScannerBinding.btnLayout.setVisibility(View.GONE);
//        activityBatchlistScannerBinding.completeBox.setVisibility(View.GONE);
        activityBatchlistScannerBinding.btnLayout2.setVisibility(View.VISIBLE);
        if (getBatchInfoRes != null && getBatchInfoRes.getBatchList() != null && getBatchInfoRes.getBatchList().size() > 0) {
           /* scanSearch = true;
            this.body = getBatchInfoRes.getBatchList();
            batchlistBinding.noOrderFoundText.setVisibility(View.GONE);
            batchlistBinding.batchListRecycler.setVisibility(View.VISIBLE);
            batchListAdapter.setBarcodeBatchList(this.body);
            batchListAdapter.notifyDataSetChanged();*/

            activityBatchlistScannerBinding.batchDetails.setVisibility(View.VISIBLE);
            activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
//            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.VISIBLE);


            for (int i = 0; i < batchListObjsList.size(); i++) {
                for (int j = 0; j < getBatchInfoRes.getBatchList().size(); j++) {
                    if (batchListObjsList.get(i).getBatchNo().equals(getBatchInfoRes.getBatchList().get(j).getBatchNo())
                            && batchListObjsList.get(i).getItemID().equals(getBatchInfoRes.getBatchList().get(j).getItemID())) {
                        getBatchInfoRes.getBatchList().remove(j);
                        j--;
                    }
                }
            }


/*
            for (int i = 0; i < getBatchInfoRes.getBatchList().size(); i++) {
                boolean isBatchAvailable = false;
                for (int j = 0; j < batchListObjsList.size(); j++) {
                    if (getBatchInfoRes.getBatchList().get(i).getBatchId().equals(batchListObjsList.get(j).getBatchId())
                            && getBatchInfoRes.getBatchList().get(i).getItemID().equals(batchListObjsList.get(j).getItemID())) {
                        isBatchAvailable = true;
                    }
                }
                if (isBatchAvailable) {
                    getBatchInfoRes.getBatchList().remove(i);
                    i--;
                }
            }
*/
            getBatchInfoRes.getBatchList().addAll(batchListObjsList);

            setBatchDetails(getBatchInfoRes.getBatchList(), true);
//        Toast.makeText(getApplicationContext(), ""+getBatchDetailsByBarcodeResponse.getBatchList().size(), Toast.LENGTH_SHORT).show();
//            batchListAdapter = new BatchListAdapter(this, this.body, this, salesLine, getBatchDetailsByBarcodeResponse.getBatchList(), scanSearch);
//            batchListAdapter.setAllowChangeQty(allowChangeQty);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//            batchlistBinding.batchListRecycler.setLayoutManager(mLayoutManager);
//            batchlistBinding.batchListRecycler.setAdapter(batchListAdapter);
        } else {
            Dialog dialog = new Dialog(this);
            DialogBatchAlertBinding batchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_batch_alert, null, false);
            dialog.setContentView(batchAlertBinding.getRoot());
            batchAlertBinding.dialogMessage.setText("Batch not found");
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            batchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
            batchAlertBinding.dialogButtonOK.setOnClickListener(view -> dialog.dismiss());
            activityBatchlistScannerBinding.batchDetails.setVisibility(View.GONE);
            activityBatchlistScannerBinding.noListFound.setVisibility(View.VISIBLE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.GONE);
//            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.GONE);
        }

       /* final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (320 * scale + 0.5f);
        activityBatchlistScannerBinding.scannerLayout.getLayoutParams().height = pixels;
        activityBatchlistScannerBinding.btnLayout.setVisibility(View.GONE);
        if (getBatchDetailsByBarcodeResponse != null && getBatchDetailsByBarcodeResponse.getBatchList() != null && getBatchDetailsByBarcodeResponse.getBatchList().size() > 0) {
            this.batchList = getBatchDetailsByBarcodeResponse.getBatchList();
            activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
//            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.VISIBLE);
            scannedBatchListAdapter = new ScannedBatchListAdapter(this, getBatchDetailsByBarcodeResponse.getBatchList(), this);
            activityBatchlistScannerBinding.batchListRcv.setAdapter(scannedBatchListAdapter);
            activityBatchlistScannerBinding.batchListRcv.setLayoutManager(new LinearLayoutManager(this));
        } else {
            activityBatchlistScannerBinding.noListFound.setVisibility(View.VISIBLE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.GONE);
//            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.GONE);
        }*/
    }

    public void setBatchDetails(List<GetBatchInfoRes.BatchListObj> bodys, boolean isBarcodeScan) {
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
//            batchlistBinding.availableQuantity.setText(String.valueOf(totalBatchDetailsQuantity).contains(".") ? String.valueOf(totalBatchDetailsQuantity).substring(0, String.valueOf(totalBatchDetailsQuantity).indexOf(".")) : String.valueOf(totalBatchDetailsQuantity));
            status = "";
            if (totalBatchDetailsQuantity >= salesLine.getQty()) {
                status = "FULL";
                /*checkAllFalse();
                batchlistBinding.fullPickedRadio.setChecked(true);*/
            } else if (totalBatchDetailsQuantity > 0.0) {
                status = "PARTIAL";
                /*checkAllFalse();
                batchlistBinding.partiallyPickedRadio.setChecked(true);*/
            } else {
                status = "NOT AVAILABLE";
                /*checkAllFalse();
                batchlistBinding.notAvailableRadio.setChecked(true);*/
            }

            String finalStatus = status;
            String finalStatus1 = status;


        } else {
           /* checkAllFalse();
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
            });*/
        }

        if (selectedOmsHeaderList != null && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse() != null && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine() != null && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().size() > 0 && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes() != null && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList() != null && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().size() > 0) {
            for (int i = 0; i < selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().size(); i++) {
                for (int j = 0; j < body.size(); j++) {
                    if (selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i).getBatchNo().equals(body.get(j).getBatchNo())) {
//                        body.set(j, selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i));
                        body.get(j).setREQQTY(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i).getREQQTY());
                        body.get(j).setSelected(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i).isSelected());
                        body.get(j).setPhysicalBatchID(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i).getPhysicalBatchID());
                        if (!isBarcodeScan) {
                            batchListObjsList.add(body.get(j));
                        }
                    }
                }
            }

            if (!isBarcodeScan) {
                for (int i = 0; i < selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().size(); i++) {
                    boolean isBatchAvailable = false;
                    for (int j = 0; j < body.size(); j++) {
                        if (selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i).getBatchNo().equals(body.get(j).getBatchNo())
                                && selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i).getItemID().equals(body.get(j).getItemID())) {
                            isBatchAvailable = true;
                        }
                    }
                    if (!isBatchAvailable) {
                        body.add(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getGetBatchInfoRes().getBatchList().get(i));
                    }
                }
            }
        }

        if (body.size() > 0) {
            if (!allowChangeQty || isBarcodeScan) {
                int qty = selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getQty();
                double selectedQty = 0.0;
                if (batchListObjsList != null && batchListObjsList.size() > 0) {
                    for (GetBatchInfoRes.BatchListObj batch : batchListObjsList) {
                        selectedQty = selectedQty + batch.getREQQTY();
                    }
                }
                String selectedQtyinText = String.valueOf(selectedQty).substring(0, String.valueOf(selectedQty).indexOf("."));

                qty = qty - Integer.parseInt(selectedQtyinText);
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
            /*if (!salesLine.getPreferredBatch().isEmpty()) {
                boolean isPrefferdBatchAvailable = false;
                for (int i = 0; i < body.size(); i++) {
                    if (salesLine.getPreferredBatch().equalsIgnoreCase(body.get(i).getBatchNo())) {
                        isPrefferdBatchAvailable = true;
                        if (Double.valueOf(body.get(i).getQ_O_H()) >= salesLine.getQty()) {
                            batchlistBinding.prefferedBatchStatus.setText("Preferred batch Fully available");
                            break;
                        } else {
                            batchlistBinding.prefferedBatchStatus.setText("Preferred batch Partially available");
                            break;
                        }
                    }
                }
                if (!isPrefferdBatchAvailable) {
                    batchlistBinding.prefferedBatchStatus.setText("Preferred batch not available in this store");
                }
            } else {
                batchlistBinding.prefferedBatchStatus.setText("Preferred batch is empty.");
            }*/
            scannedBatchListAdapter = new ScannedBatchListAdapter(this, this.body, this, salesLine, batchListObjsList);
            scannedBatchListAdapter.setAllowChangeQty(allowChangeQty);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            activityBatchlistScannerBinding.batchListRcv.setLayoutManager(mLayoutManager);
            activityBatchlistScannerBinding.batchListRcv.setAdapter(scannedBatchListAdapter);
            if (body.get(0).isBarcodeScannedBatch()) {
                activityBatchlistScannerBinding.batchListRcv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // At this point the layout is complete and the
                        // dimensions of recyclerView and any child views
                        // are known.
                        activityBatchlistScannerBinding.batchListRcv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        onAddItemsClicked(true);
                    }
                });
            }
        }
    }

    public void onAddItemsClicked(boolean isBarcodeScannerItem) {
        double selectedBatchesQty = 0.0;
        if (!allowMultiBatch && batchListObjsList != null && batchListObjsList.size() > 1) {
            Toast.makeText(this, "Select only one batch..!", Toast.LENGTH_SHORT).show();
        } else if (batchListObjsList != null && batchListObjsList.size() > 0) {
            for (int i = 0; i < batchListObjsList.size(); i++) {
                selectedBatchesQty = selectedBatchesQty + batchListObjsList.get(i).getREQQTY();
            }

            if (selectedBatchesQty < salesLine.getQty()) {
                if (!isBarcodeScannerItem) {
                    statusBatchlist = "PARTIAL";
                    Dialog dialog = new Dialog(this);//, R.style.Theme_AppCompat_DayNight_NoActionBar
                    DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_batch_alert, null, false);
                    dialog.setContentView(dialogBatchAlertBinding.getRoot());
                    dialogBatchAlertBinding.printImg.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
                    dialogBatchAlertBinding.printImg.setVisibility(View.VISIBLE);
                    dialogBatchAlertBinding.dialogMessage.setText("You have entered less than Request quantity\nDo you still want to continue?");
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
                        i.putExtra("IS_BATCH_SELECTED_THROUGH_BARCODE", true);
                        setResult(RESULT_OK, i);
                        finish();
                        dialog.dismiss();
                    });
                    dialogBatchAlertBinding.dialogButtonNO.setOnClickListener(v1 -> dialog.dismiss());
//                dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());


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
                }
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
                    i.putExtra("IS_BATCH_SELECTED_THROUGH_BARCODE", true);
                    setResult(RESULT_OK, i);
                    finish();
                } catch (Exception e) {
                    System.out.println("===============================================" + e.getMessage());
                }
            } else if (selectedBatchesQty > salesLine.getQty()) {
                Dialog dialog = new Dialog(this);//, R.style.Theme_AppCompat_DayNight_NoActionBar
                DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_batch_alert, null, false);
                dialog.setContentView(dialogBatchAlertBinding.getRoot());
                dialogBatchAlertBinding.printImg.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
                dialogBatchAlertBinding.printImg.setVisibility(View.VISIBLE);
                dialogBatchAlertBinding.dialogMessage.setText("You have entered more than required quantity\nPlease enter required quantity");
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                dialogBatchAlertBinding.dialogButtonNO.setOnClickListener(view -> dialog.dismiss());
                dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
//                    statusBatchlist = "FULL";
//                    GetBatchInfoRes o = new GetBatchInfoRes();
//                    if (batchListObjsList != null && batchListObjsList.size() > 0)
//                        o.setBatchList(batchListObjsList);
//                    selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
//                    Intent i = new Intent();
//                    i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
//                    i.putExtra("finalStatus", (String) statusBatchlist);
//                    setResult(RESULT_OK, i);
//                    finish();
                    dialog.dismiss();
                });
//                dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());


//                Toast.makeText(this, "You have entered more than required qty", Toast.LENGTH_SHORT).show();
            }

        } else {
            Dialog dialog = new Dialog(this);//, R.style.Theme_AppCompat_DayNight_NoActionBar
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
//            dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());
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
    public void onClickReferToAdmin() {
        Dialog dialog = new Dialog(this);
        DialogReferToAdminBinding dialogReferToAdminBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_refer_to_admin, null, false);
        dialog.setContentView(dialogReferToAdminBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogReferToAdminBinding.close.setOnClickListener(v -> dialog.dismiss());
        dialogReferToAdminBinding.okButton.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onClickOnHold() {
        onClickSkip();
    }

    int selectedReasonPos = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccessGetResonListApiCall(ReasonListResponse reasonListResponse) {
        List<String> reasons = reasonListResponse.getData().stream()
                .map(ReasonListResponse.Datum::getReasonDesc).collect(Collectors.toList());
        if (reasonListResponse != null && reasonListResponse.getData().size() > 0) {
            Dialog dialog = new Dialog(this);
            dialogSkipOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_skip_order, null, false);
            dialog.setContentView(dialogSkipOrderBinding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogSkipOrderBinding.close.setOnClickListener(v1 -> dialog.dismiss());
            dialogSkipOrderBinding.notAvailable.setOnClickListener(v -> {
                List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
                omsHeaderList.add(selectedOmsHeaderList.get(orderAdapterPos));
                mPresenter.mposPickPackOrderReservationApiCall(10, omsHeaderList, reasonListResponse.getData().get(selectedReasonPos).getReasonCode(), dialog);
            });
            selectedReasonPos = 0;
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, reasons);//reasonListResponse.getData()
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dialogSkipOrderBinding.selectReaon.setAdapter(arrayAdapter);

            dialogSkipOrderBinding.selectReaon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedReasonPos = position;
//                    ((TextView) parent.getChildAt(0)).setTextSize(12);
                    TextView textView = (TextView) view;
                    textView.setText(reasonListResponse.getData().get(selectedReasonPos).getReasonDesc());
                    textView.setTextSize(12);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dialog.show();
        }
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            activityBatchlistScannerBinding.batchDetails.setVisibility(View.VISIBLE);
            activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
        } else {
            activityBatchlistScannerBinding.batchDetails.setVisibility(View.GONE);
            activityBatchlistScannerBinding.noListFound.setVisibility(View.VISIBLE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClickSelectedBatch(GetBatchInfoRes.BatchListObj batchListModel, boolean isBarcodeScannedItem) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        hideKeyboard();
        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).getBatchNo().equals(batchListModel.getBatchNo())) {
                body.set(i, batchListModel);
                if (body.get(i).isSelected()) {
                    for (int n = 0; n < batchListObjsList.size(); n++) {
                        if (body.get(i).getBatchNo().equals(batchListObjsList.get(n).getBatchNo())
                                && body.get(i).getItemID().equals(batchListObjsList.get(n).getItemID())) {
                            batchListObjsList.remove(n);
                            n--;
                        }
                    }

                    batchListObjsList.add(body.get(i));
                    mPresenter.checkBatchInventory(body.get(i), true);
                } else {
                    batchListObjsList.remove(body.get(i));
                }
                break;
            }
        }
        if (!isBarcodeScannedItem) {
            if (scannedBatchListAdapter != null) {
                scannedBatchListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void checkBatchInventorySuccess(CheckBatchInventoryRes body) {

    }

    @Override
    public void onClickSearchTextClear() {
        activityBatchlistScannerBinding.searchbybatchId.getText().clear();
    }

    @Override
    public void onClickProductInfo() {
        Dialog dialog = new Dialog(this);
        DialogAddBatchDetailsBinding dialogAddBatchDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_add_batch_details, null, false);
        dialog.setContentView(dialogAddBatchDetailsBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAddBatchDetailsBinding.close.setOnClickListener(v -> dialog.dismiss());
        dialogAddBatchDetailsBinding.addButton.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse, Dialog dialog1) {
        ZebraSelfscanner.isRackIdScanned=false;
        if (dialog1 != null && dialog1.isShowing()) {
            dialog1.dismiss();
        }
        selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setOnHold(true);
        Dialog dialog = new Dialog(this);
        DialogOnHoldBinding dialogOnHoldBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_on_hold, null, false);
        dialog.setContentView(dialogOnHoldBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOnHoldBinding.close.setOnClickListener(v -> dialog.dismiss());
        dialogOnHoldBinding.saveButton.setOnClickListener(v -> {
            dialog.dismiss();
            Intent i = new Intent();
            i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
            i.putExtra("IS_BATCH_HOLD", true);
            i.putExtra("finalStatus", "NOT AVAILABLE");
            setResult(RESULT_OK, i);
            finish();
        });
        dialog.setCancelable(false);
        dialog.show();
    }


    public void dialogShow(String scannedQty) {
        this.scannedQty = scannedQty;
        activityBatchlistScannerBinding.pickedQty.setText(scannedQty.substring(0, scannedQty.indexOf(".")));
        selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setPickedQty(scannedQty.substring(0, scannedQty.indexOf(".")));
        Toast.makeText(this, "Requested Qty - " + salesLine.getQty() + "\n" + "Scanned Qty - " + scannedQty.substring(0, scannedQty.indexOf(".")), Toast.LENGTH_SHORT).show();
        if (Double.parseDouble(scannedQty) == salesLine.getQty()) {
            showCompleteDialog();
        } else {
            activityBatchlistScannerBinding.scanbarcode.setText("");
            activityBatchlistScannerBinding.scanbarcode.requestFocus();

            //initiateScanner();
        }
        /*dialog = new Dialog(this);
        DialogScanBinding dialogScanBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.dialog_scan,
                null,
                false
        );
        dialogScanBinding.requestedQty.setText("Requested Qty - " + salesLine.getQty());
        dialogScanBinding.scannedQty.setText("Scanned Qty - " + scannedQty.substring(0, scannedQty.indexOf(".")));
        dialog.setContentView(dialogScanBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new Handler().postDelayed(() -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                initiateScanner();
                if (Double.parseDouble(scannedQty) == salesLine.getQty()) {
                    showCompleteDialog();
                }
            }
        }, 2000);
        dialog.show();*/
    }

    @SuppressLint("SetTextI18n")
    private void showCompleteDialog() {
        isBoxIdScan = true;
        Dialog dialog = new Dialog(ZebraBatchlistScanner.this);
        DialogShelfScanSuccessBinding dialogShelfScanSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraBatchlistScanner.this), R.layout.dialog_shelf_scan_success, null, false);
        dialog.setContentView(dialogShelfScanSuccessBinding.getRoot());
        dialogShelfScanSuccessBinding.message.setText("Product Scanned Successfully");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new Handler().postDelayed(() -> {
            dialog.dismiss();
            activityBatchlistScannerBinding.scanbarcode.setText("");
            activityBatchlistScannerBinding.scanbarcode.requestFocus();
            //initiateScanner();
        }, 2000);
        dialog.show();
        /*Dialog dialog = new Dialog(BatchlistScannerActivity.this);
        DialogRackAlertBinding dialogRackAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(BatchlistScannerActivity.this), R.layout.dialog_rack_alert, null, false);
        dialog.setContentView(dialogRackAlertBinding.getRoot());
        dialogRackAlertBinding.message.setText("Product Scanning is completed! Kindly Scan the Tray ID");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogRackAlertBinding.dialogButtonOK.setOnClickListener(v -> {
            dialog.dismiss();
            initiateScanner();
        });*/
    }



    public void onCompleteScan(String tryId, List<GetBatchInfoRes.BatchListObj> salesLineBatchList) {
        BatchlistScannerActivity.isBarCodeProblem = false;
        ZebraSelfscanner.isRackIdScanned=false;
        if (tryId.equalsIgnoreCase(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode())) {
            Dialog dialog = new Dialog(ZebraBatchlistScanner.this);
            DialogShelfScanSuccessBinding shelfScanSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraBatchlistScanner.this), R.layout.dialog_shelf_scan_success, null, false);
            dialog.setContentView(shelfScanSuccessBinding.getRoot());
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            shelfScanSuccessBinding.message.setText("Box Scanned Successfully");
            new Handler().postDelayed(() -> {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    GetBatchInfoRes o = new GetBatchInfoRes();
                    o.setBatchList(salesLineBatchList);
                    selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).setGetBatchInfoRes(o);
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                    intent.putExtra("finalStatus", "FULL");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }, 1000);
            dialog.show();
        } else {

            Dialog dialog = new Dialog(ZebraBatchlistScanner.this);
            DialogRackAlertBinding dialogRackAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraBatchlistScanner.this), R.layout.dialog_rack_alert, null, false);
            dialog.setContentView(dialogRackAlertBinding.getRoot());
            dialogRackAlertBinding.message.setText("Tray ID does not match so Kindly Scan the Correct Tray ID");
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialogRackAlertBinding.dialogButtonOK.setOnClickListener(v -> {
                dialog.dismiss();
                activityBatchlistScannerBinding.scanbarcode.setText("");
                activityBatchlistScannerBinding.scanbarcode.requestFocus();
               // initiateScanner();
            });
        }
    }


    public void enableCompleteBoxBtn() {
        activityBatchlistScannerBinding.scanBoxIdText.setVisibility(View.VISIBLE);
        activityBatchlistScannerBinding.btnLayout.setVisibility(View.GONE);

        activityBatchlistScannerBinding.scanbarcode.setText("");
        activityBatchlistScannerBinding.scanbarcode.requestFocus();
        activityBatchlistScannerBinding.scanbarcode.setHint("Scan BoxId");
    }


    public void onScanned(List<GetBatchInfoRes.BatchListObj> salesLineBatchList) {
        this.batchList = salesLineBatchList;
    }


    public void invalidBarcode() {
        Dialog dialog = new Dialog(ZebraBatchlistScanner.this);
        DialogRackAlertBinding dialogRackAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraBatchlistScanner.this), R.layout.dialog_rack_alert, null, false);
        dialog.setContentView(dialogRackAlertBinding.getRoot());
        dialogRackAlertBinding.message.setText("Product Barcode Doesn’t Match with the Requested Item");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogRackAlertBinding.dialogButtonOK.setOnClickListener(v -> {
            dialog.dismiss();
            activityBatchlistScannerBinding.scanbarcode.setText("");
            activityBatchlistScannerBinding.scanbarcode.requestFocus();


           // initiateScanner();
        });
    }


    public void onBarcodeScan(String resultText) {
        Intent intent = new Intent();
        intent.putExtra("result", resultText);
        setResult(RESULT_OK, intent);
        finish();
    }

    /*public void initiateScanner() {
        new IntentIntegrator(this).setCaptureActivity(BatchlistScannerActivity.class).initiateScan();
        capture.setCaptureManagerCallback(this);
        capture.setSalesLine(salesLine);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BATCH_LIST_DETAILS && resultCode == RESULT_OK) {
            List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) data.getSerializableExtra("selectedOmsHeaderList");
            String statusBatchlist = data.getStringExtra("finalStatus");
            boolean isBackPressed = data.getBooleanExtra("isBackPressed", false);
            if (isBackPressed) {
                activityBatchlistScannerBinding.btnLayout.setVisibility(View.VISIBLE);
                activityBatchlistScannerBinding.scanBoxIdText.setVisibility(View.GONE);
            } else {
                Intent i = new Intent();
                i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                i.putExtra("finalStatus", (String) statusBatchlist);
                i.putExtra("isBarCodeProblem", isBarCodeProblem);
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }
}