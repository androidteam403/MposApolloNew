package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.selfidscanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityShelfIdScannerBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.BatchlistScannerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class ShelfIdScannerActivity extends BaseActivity implements ShelfIdScannerMvpView, DecoratedBarcodeView.TorchListener {
    private ActivityShelfIdScannerBinding shelfIdScannerBinding;
    @Inject
    ShelfIdScannerMvpPresenter<ShelfIdScannerMvpView> mPresenter;
    GetOMSTransactionResponse.SalesLine salesLine;
    private List<GetBatchInfoRes.BatchListObj> body;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    int orderAdapterPos, newSelectedOrderAdapterPos;
    private boolean allowChangeQty;
    private boolean allowMultiBatch;

    private DecoratedBarcodeView barcodeScannerView;

    @SuppressLint("LongLogTag")
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
        String itemId = "";
        if (getIntent() != null) {
            itemId = getIntent().getStringExtra("ITEM_ID");
            salesLine = (GetOMSTransactionResponse.SalesLine) getIntent().getSerializableExtra("SALESLINE");
            body = (List<GetBatchInfoRes.BatchListObj>) getIntent().getSerializableExtra("BATCH_LIST");
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("SELECTED_OMS_HEADER_LIST");
            orderAdapterPos = (int) getIntent().getSerializableExtra("ORDER_ADAPTER_POS");
            newSelectedOrderAdapterPos = (int) getIntent().getSerializableExtra("NEW_SELECTED_ORDER_ADAPTER_POS");
            allowChangeQty = (boolean) getIntent().getSerializableExtra("ALLOW_CHANGE_QTY");
            allowMultiBatch = (boolean) getIntent().getSerializableExtra("ALLOW_MULTI_BATCH");
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
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText().equalsIgnoreCase(salesLine.getRackId())) {
                    Intent intent = new Intent(ShelfIdScannerActivity.this, BatchlistScannerActivity.class);
                    intent.putExtra("ITEM_ID", salesLine.getItemId());
                    intent.putExtra("SALESLINE", salesLine);
                    intent.putExtra("BATCH_LIST", (Serializable) body);
                    intent.putExtra("SELECTED_OMS_HEADER_LIST", (Serializable) selectedOmsHeaderList);
                    intent.putExtra("ORDER_ADAPTER_POS", orderAdapterPos);
                    intent.putExtra("NEW_SELECTED_ORDER_ADAPTER_POS", newSelectedOrderAdapterPos);
                    intent.putExtra("ALLOW_CHANGE_QTY", allowChangeQty);
                    intent.putExtra("ALLOW_MULTI_BATCH", allowMultiBatch);
                    startActivity(intent);
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
}