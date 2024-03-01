package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.zebraselfidscanner;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityZebraSelfscannerBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogRackAlertBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogShelfScanSuccessBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.ZebraBatchlistScanner;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ZebraSelfscanner extends BaseActivity implements  ZebraSelfscannerMvpView
{

    @Inject
    ZebraSelfscannerMvpPresenter<ZebraSelfscannerMvpView> mpresenter;

    private static final int BATCH_LIST_SCANNER = 2004;
    private static final int BATCH_LIST_ACTIVITY = 2005;
    private ActivityZebraSelfscannerBinding shelfIdScannerBinding;

    GetOMSTransactionResponse.SalesLine salesLine;
    private List<GetBatchInfoRes.BatchListObj> body;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    int orderAdapterPos, newSelectedOrderAdapterPos;
    private boolean allowChangeQty;
    private boolean allowMultiBatch;
    boolean noBatchDetails;
   public static boolean isRackIdScanned = false;
    IntentResult result;

    private DecoratedBarcodeView barcodeScannerView;

    List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderListResult = new ArrayList<>();
    String statusBatchlist;
    boolean isReferToAdmin;
    boolean isBatchHold;
    boolean isBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shelfIdScannerBinding=DataBindingUtil.setContentView(this,R.layout.activity_zebra_selfscanner);
        getActivityComponent().inject(this);
        mpresenter.onAttach(ZebraSelfscanner.this);

        String boxId = "";
        if (getIntent() != null) {
//            itemId = getIntent().getStringExtra("ITEM_ID");
            salesLine = (GetOMSTransactionResponse.SalesLine) getIntent().getSerializableExtra("salesLine");
//            body = (List<GetBatchInfoRes.BatchListObj>) getIntent().getSerializableExtra("BATCH_LIST");
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("selectedOmsHeaderList");
            orderAdapterPos = (int) getIntent().getSerializableExtra("orderAdapterPos");
            newSelectedOrderAdapterPos = (int) getIntent().getSerializableExtra("newSelectedOrderAdapterPos1");
            noBatchDetails = getIntent().getExtras().getBoolean("noBatchDetails");
            boxId = getIntent().getStringExtra("box_id");
//            allowChangeQty = (boolean) getIntent().getSerializableExtra("ALLOW_CHANGE_QTY");
//            allowMultiBatch = (boolean) getIntent().getSerializableExtra("ALLOW_MULTI_BATCH");
        }
        boolean isRackIdScanAllowed = true; // mPresenter.getGlobalConfiguration().isRackidScanAllowed();
        if (!isRackIdScanAllowed) {
            if (salesLine.getCategoryCode().equalsIgnoreCase("P")) {
                Intent intent = new Intent(ZebraSelfscanner.this, BatchListActivity.class);
                intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                intent.putExtra("salesLine", (Serializable) salesLine);
                intent.putExtra("orderAdapterPos", orderAdapterPos);
                intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                intent.putExtra("noBatchDetails", noBatchDetails);
                startActivityForResult(intent, BATCH_LIST_ACTIVITY);
            } else {
                Intent intent = new Intent(ZebraSelfscanner.this, ZebraBatchlistScanner.class);
                //Intent intent = new Intent(ZebraSelfscanner.this, BatchlistScannerActivity.class);
                intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                intent.putExtra("salesLine", (Serializable) salesLine);
                intent.putExtra("orderAdapterPos", orderAdapterPos);
                intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                intent.putExtra("noBatchDetails", noBatchDetails);
                startActivityForResult(intent, BATCH_LIST_SCANNER);
            }
        }

        shelfIdScannerBinding.scanshelfid.requestFocus();
      //  barcodeScannerView = findViewById(R.id.zxing_barcode_scanners);
     //   barcodeScannerView.setTorchListener(this);
       // barcodeScannerView.initializeFromIntent(getIntent());

        shelfIdScannerBinding.flid.setText(salesLine.getFullfillmentId());
        if (selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode() != null) {
            if (!selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode().isEmpty()) {
                shelfIdScannerBinding.boxNumber.setText(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode());
            } else {
                shelfIdScannerBinding.boxNumber.setText("-");
            }
        } else {
            shelfIdScannerBinding.boxNumber.setText("-");
        }
        if (salesLine.getPickedQty() != null) {
            shelfIdScannerBinding.pickedQty.setText(salesLine.getPickedQty());
        } else {
            shelfIdScannerBinding.pickedQty.setText("0");
        }
        shelfIdScannerBinding.reqQty.setText(Integer.toString(salesLine.getQty()));
        shelfIdScannerBinding.rackId.setText(salesLine.getRackId());
        if (isRackIdScanned) {
            shelfIdScannerBinding.message.setText("Scan the Box ID");
        } else {
            shelfIdScannerBinding.message.setText("Scan the Rack ID");
        }

        shelfIdScannerBinding.scanshelfid.addTextChangedListener(new TextWatcher() {
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
                    scanshefid(String.valueOf(s));
                }

            }
        });

       // shelfIdScannerBinding.scanshelfid

        shelfIdScannerBinding.scanshelfid.setOnTouchListener(otl);
        /* barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                BeepManager beepManager = new BeepManager(ZebraSelfscanner.this);
                beepManager.playBeepSoundAndVibrate();
                if (!isRackIdScanned) {
                    isRackIdScanned = true;
                    if (result.getText().equalsIgnoreCase(salesLine.getRackId())) {
                        barcodeScannerView.pause();
                        Dialog dialog = new Dialog(ZebraSelfscanner.this);
                        DialogShelfScanSuccessBinding shelfScanSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this),R.layout.dialog_shelf_scan_success, null, false);
                        dialog.setContentView(shelfScanSuccessBinding.getRoot());
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        new Handler().postDelayed(() -> {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                                if (salesLine.getCategoryCode().equalsIgnoreCase("P")) {
                                    Intent intent = new Intent(ZebraSelfscanner.this, BatchListActivity.class);
                                    intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                                    intent.putExtra("salesLine", (Serializable) salesLine);
                                    intent.putExtra("orderAdapterPos", orderAdapterPos);
                                    intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                                    intent.putExtra("noBatchDetails", noBatchDetails);
                                    startActivityForResult(intent, BATCH_LIST_ACTIVITY);
                                } else {
                                    Intent intent = new Intent(ZebraSelfscanner.this, BatchlistScannerActivity.class);
                                    intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                                    intent.putExtra("salesLine", (Serializable) salesLine);
                                    intent.putExtra("orderAdapterPos", orderAdapterPos);
                                    intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                                    intent.putExtra("noBatchDetails", noBatchDetails);
                                    startActivityForResult(intent, BATCH_LIST_SCANNER);
                                    //finish();
                                }
                            }
                        }, 1000);
                        dialog.show();
                    } else {
                        barcodeScannerView.pause();
                        isRackIdScanned = false;
                        Dialog dialog = new Dialog(ZebraSelfscanner.this);
                        DialogRackAlertBinding dialogRackAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this), R.layout.dialog_rack_alert, null, false);
                        dialog.setContentView(dialogRackAlertBinding.getRoot());
                        dialogRackAlertBinding.message.setText("Please enter valid rack ID");
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialogRackAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                            barcodeScannerView.resume();
                            dialog.dismiss();
                        });
                    }
                } else {
                    if (result.getText().equalsIgnoreCase(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode())) {
                        barcodeScannerView.pause();
                        Dialog dialog = new Dialog(ZebraSelfscanner.this);
                        DialogShelfScanSuccessBinding shelfScanSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this),R.layout.dialog_shelf_scan_success, null, false);
                        dialog.setContentView(shelfScanSuccessBinding.getRoot());
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        shelfScanSuccessBinding.message.setText("Box Scanned Successfully");
                        new Handler().postDelayed(() -> {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                                Intent i = new Intent();
                                i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                                i.putExtra("finalStatus", (String) statusBatchlist);
                                setResult(RESULT_OK, i);
                                finish();
                            }
                        }, 1000);
                        dialog.show();
                    } else {
                        barcodeScannerView.pause();
                        Dialog dialog = new Dialog(ZebraSelfscanner.this);
                        DialogRackAlertBinding dialogRackAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this), R.layout.dialog_rack_alert, null, false);
                        dialog.setContentView(dialogRackAlertBinding.getRoot());
                        dialogRackAlertBinding.message.setText("Tray ID does not match so Kindly Scan the Correct Tray ID");
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialogRackAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                            barcodeScannerView.resume();
                            dialog.dismiss();
                        });
                    }
                }
            }

           @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

           }
        });*/

        shelfIdScannerBinding.close.setOnClickListener(view -> {
                    onClickClose();
                });

        shelfIdScannerBinding.cameraBtnW.setOnClickListener(view->{
            PickupProcessActivity.cameraenabled=true;
            finish();
        });
        setUp();
    }

    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch (View v, MotionEvent event) {
            return true; // the listener has consumed the event
        }
    };
    public  void scanshefid(String s)
    {
        if (!isRackIdScanned) {
            isRackIdScanned = true;
            shelfIdScannerBinding.scanshelfid.setText("");
            if (s.equalsIgnoreCase(salesLine.getRackId())) {
               // barcodeScannerView.pause();
                Dialog dialog = new Dialog(ZebraSelfscanner.this);
                DialogShelfScanSuccessBinding shelfScanSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this),R.layout.dialog_shelf_scan_success, null, false);
                dialog.setContentView(shelfScanSuccessBinding.getRoot());
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                new Handler().postDelayed(() -> {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        if (salesLine.getCategoryCode().equalsIgnoreCase("P")) {
                            Intent intent = new Intent(ZebraSelfscanner.this, BatchListActivity.class);
                            intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                            intent.putExtra("salesLine", (Serializable) salesLine);
                            intent.putExtra("orderAdapterPos", orderAdapterPos);
                            intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                            intent.putExtra("noBatchDetails", noBatchDetails);
                            startActivityForResult(intent, BATCH_LIST_ACTIVITY);
                        } else {
                             Intent intent = new Intent(ZebraSelfscanner.this, ZebraBatchlistScanner.class);
                             //Intent intent = new Intent(ZebraSelfscanner.this, BatchlistScannerActivity.class);
                            intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                            intent.putExtra("salesLine", (Serializable) salesLine);
                            intent.putExtra("orderAdapterPos", orderAdapterPos);
                            intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                            intent.putExtra("noBatchDetails", noBatchDetails);
                            startActivityForResult(intent, BATCH_LIST_SCANNER);
                            //finish();
                        }
                    }
                }, 1000);
                dialog.show();
            } else {
               // barcodeScannerView.pause();
                isRackIdScanned = false;
                Dialog dialog = new Dialog(ZebraSelfscanner.this);
                DialogRackAlertBinding dialogRackAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this), R.layout.dialog_rack_alert, null, false);
                dialog.setContentView(dialogRackAlertBinding.getRoot());
                dialogRackAlertBinding.message.setText("Please enter valid rack ID");
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialogRackAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                   // barcodeScannerView.resume();
                    dialog.dismiss();
                });
            }
        } else {
            if (s.equalsIgnoreCase(selectedOmsHeaderList.get(orderAdapterPos).getScannedBarcode())) {
                isRackIdScanned = false;
               // barcodeScannerView.pause();
                Dialog dialog = new Dialog(ZebraSelfscanner.this);
                DialogShelfScanSuccessBinding shelfScanSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this), R.layout.dialog_shelf_scan_success, null, false);
                dialog.setContentView(shelfScanSuccessBinding.getRoot());
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                shelfScanSuccessBinding.message.setText("Tray Scanned Successfully");
                new Handler().postDelayed(() -> {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        Intent i = new Intent();
                        i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                        i.putExtra("finalStatus", (String) statusBatchlist);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }, 1000);
                dialog.show();
            } else {
                //barcodeScannerView.pause();
                Dialog dialog = new Dialog(ZebraSelfscanner.this);
                DialogShelfScanSuccessBinding shelfScanSuccessBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this), R.layout.dialog_shelf_scan_success, null, false);
                dialog.setContentView(shelfScanSuccessBinding.getRoot());
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                shelfScanSuccessBinding.message.setText("Tray ID does not match so Kindly Scan the Correct Tray ID");
                new Handler().postDelayed(() -> {
                    dialog.dismiss();
                    shelfIdScannerBinding.scanshelfid.setText("");
                    shelfIdScannerBinding.scanshelfid.requestFocus();
                }, 1500);
                dialog.show();


                /*Dialog dialog = new Dialog(ZebraSelfscanner.this);
                DialogRackAlertBinding dialogRackAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ZebraSelfscanner.this), R.layout.dialog_rack_alert, null, false);
                dialog.setContentView(dialogRackAlertBinding.getRoot());
                dialogRackAlertBinding.message.setText("Tray ID does not match so Kindly Scan the Correct Tray ID");
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialogRackAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                    // barcodeScannerView.resume();
                    dialog.dismiss();
                    shelfIdScannerBinding.scanshelfid.setText("");
                    shelfIdScannerBinding.scanshelfid.requestFocus();

                });*/
            }
        }

    }


    @Override
    protected void setUp() {
    }

    @Override
    protected void onResume() {
        super.onResume();
       // barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  barcodeScannerView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClickClose() {
        super.onBackPressed();
        isRackIdScanned=false;
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == BATCH_LIST_SCANNER && resultCode == RESULT_OK) {
            shelfIdScannerBinding.message.setText("Scan the Box ID");
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) data.getSerializableExtra("selectedOmsHeaderList");
            statusBatchlist = data.getStringExtra("finalStatus");
            isBatchHold = data.getBooleanExtra("IS_BATCH_HOLD", false);
            boolean isBarCodeProblem = data.getBooleanExtra("isBarCodeProblem", false);
            if (selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getPickedQty() != null) {
                if (!selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getPickedQty().isEmpty()) {
                    shelfIdScannerBinding.pickedQty.setText(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getPickedQty());
                }
            } else {
                shelfIdScannerBinding.pickedQty.setText("0");
            }
            if (!isBarCodeProblem) {
                Intent i = new Intent();
                i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                i.putExtra("finalStatus", (String) statusBatchlist);
                i.putExtra("IS_BATCH_HOLD", isBatchHold);
                setResult(RESULT_OK, i);
                finish();
            }
        } else if (requestCode == BATCH_LIST_ACTIVITY && resultCode == RESULT_OK) {
//            shelfIdScannerBinding.completeBox.setVisibility(View.VISIBLE);
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) data.getSerializableExtra("selectedOmsHeaderList");
            statusBatchlist = data.getStringExtra("finalStatus");
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() != 0) {
                if (selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getPickedQty() != null) {
                    if (!selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getPickedQty().isEmpty()) {
                        shelfIdScannerBinding.pickedQty.setText(selectedOmsHeaderList.get(orderAdapterPos).getGetOMSTransactionResponse().getSalesLine().get(newSelectedOrderAdapterPos).getPickedQty());
                    } else {
                        shelfIdScannerBinding.pickedQty.setText("0");
                    }
                } else {
                    shelfIdScannerBinding.pickedQty.setText("0");
                }
            }
            isReferToAdmin = data.getBooleanExtra("isReferToAdmin", false);
            boolean isBatchHold = data.getBooleanExtra("IS_BATCH_HOLD", false);
            isBackPressed = data.getBooleanExtra("isBackPressed", false);
            if (isReferToAdmin) {
                Intent i = new Intent();
                i.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                i.putExtra("IS_BATCH_HOLD", isBatchHold);
                i.putExtra("finalStatus", statusBatchlist);
                setResult(RESULT_OK, i);
                finish();
            } else if (isBackPressed) {
                isRackIdScanned = false;
            } else {
                shelfIdScannerBinding.message.setText("Scan the Box ID");
            }
        }
    }
}