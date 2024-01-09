package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.selfidscanner;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityShelfIdScannerBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBatchAlertBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.BatchlistScannerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class ShelfIdScannerActivity extends BaseActivity implements ShelfIdScannerMvpView, DecoratedBarcodeView.TorchListener {
    private static final int BATCH_LIST_SCANNER = 2004;
    private ActivityShelfIdScannerBinding shelfIdScannerBinding;
    @Inject
    ShelfIdScannerMvpPresenter<ShelfIdScannerMvpView> mPresenter;
    GetOMSTransactionResponse.SalesLine salesLine;
    private List<GetBatchInfoRes.BatchListObj> body;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    int orderAdapterPos, newSelectedOrderAdapterPos;
    private boolean allowChangeQty;
    private boolean allowMultiBatch;
    boolean noBatchDetails;
    boolean isRackIdScanned = false;
    IntentResult result;

    private DecoratedBarcodeView barcodeScannerView;

    @SuppressLint({"LongLogTag", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            Log.i("Tab inches below 7 and 7 inches-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        shelfIdScannerBinding = DataBindingUtil.setContentView(this, R.layout.activity_shelf_id_scanner);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ShelfIdScannerActivity.this);
//        String itemId = "";
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
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanners);
        barcodeScannerView.setTorchListener(this);
        barcodeScannerView.initializeFromIntent(getIntent());
        shelfIdScannerBinding.flid.setText(salesLine.getFullfillmentId());
        if (salesLine.getBarcode() != null) {
            if (!salesLine.getBarcode().isEmpty()) {
                shelfIdScannerBinding.boxNumber.setText(salesLine.getBarcode());
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
            shelfIdScannerBinding.message.setText("Scan the Shelf ID");
        }
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (!isRackIdScanned) {
                    isRackIdScanned = true;
                    if (result.getText().equalsIgnoreCase(salesLine.getRackId())) {
                        if (salesLine.getCategoryCode().equalsIgnoreCase("P")) {
                            Intent intent = new Intent(ShelfIdScannerActivity.this, BatchListActivity.class);
                            intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                            intent.putExtra("salesLine", (Serializable) salesLine);
                            intent.putExtra("orderAdapterPos", orderAdapterPos);
                            intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                            intent.putExtra("noBatchDetails", noBatchDetails);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(ShelfIdScannerActivity.this, BatchlistScannerActivity.class);
                            intent.putExtra("selectedOmsHeaderList", (Serializable) selectedOmsHeaderList);
                            intent.putExtra("salesLine", (Serializable) salesLine);
                            intent.putExtra("orderAdapterPos", orderAdapterPos);
                            intent.putExtra("newSelectedOrderAdapterPos1", newSelectedOrderAdapterPos);
                            intent.putExtra("noBatchDetails", noBatchDetails);
                            startActivityForResult(intent, BATCH_LIST_SCANNER);
                        }
                    } else {
                        isRackIdScanned = false;
                        Dialog dialog = new Dialog(ShelfIdScannerActivity.this);
                        DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ShelfIdScannerActivity.this), R.layout.dialog_batch_alert, null, false);
                        dialog.setContentView(dialogBatchAlertBinding.getRoot());
                        dialogBatchAlertBinding.dialogMessage.setText("Please enter valid rack ID");
                        dialogBatchAlertBinding.dialogButtonOK.setText("Ok");
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                        dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> dialog.dismiss());
                    }
                } else {
                    if (result.getText().equalsIgnoreCase(salesLine.getBarcode())) {
                        finish();
                    } else {
                        Dialog dialog = new Dialog(ShelfIdScannerActivity.this);
                        DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ShelfIdScannerActivity.this), R.layout.dialog_batch_alert, null, false);
                        dialog.setContentView(dialogBatchAlertBinding.getRoot());
                        dialogBatchAlertBinding.dialogMessage.setText("Tray ID does not match so Kindly Scan the Correct Tray ID");
                        dialogBatchAlertBinding.dialogButtonOK.setText("Ok");
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                        dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> dialog.dismiss());
                    }
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    @Override
    protected void setUp() {
    }

    @Override
    public void onTorchOn() {
    }

    @Override
    public void onTorchOff() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeScannerView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClickClose() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == BATCH_LIST_SCANNER && resultCode == RESULT_OK) {
            GetBatchInfoRes.BatchListObj batchListObj = (GetBatchInfoRes.BatchListObj) data.getSerializableExtra("batchlistobj");
        }
    }
}