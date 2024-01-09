package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CaptureManager implements CallbackCaptureManager {
    int orientationLock = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    private static final String SAVED_ORIENTATION_LOCK = "SAVED_ORIENTATION_LOCK";
    private static final String TAG = com.journeyapps.barcodescanner.CaptureManager.class.getSimpleName();
    private BeepManager beepManager;
    private Handler handler;
    private boolean finishWhenClosed = false;
    private InactivityTimer inactivityTimer;
    private boolean returnBarcodeImagePath = false;
    CaptureManagerCallback mCallback;
    double scannedQuantity = 0.0;
    GetOMSTransactionResponse.SalesLine salesLine;
    Context applicationContext;
    Activity activity;
    DecoratedBarcodeView barcodeView;

    public CaptureManager(Activity activity, DecoratedBarcodeView barcodeView, Context applicationContext) {
        this.activity = activity;
        this.barcodeView = barcodeView;
        this.applicationContext = applicationContext;
        handler = new Handler();

        inactivityTimer = new InactivityTimer(activity, new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Finishing due to inactivity");
                finish();
            }
        });

        beepManager = new BeepManager(activity);
    }

    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            new CaptureManagerController(activity, CaptureManager.this).getBatchDetailsByBarCode(result.getText(), salesLine.getItemId(), BatchlistScannerActivity.eposUrl, BatchlistScannerActivity.storeId, BatchlistScannerActivity.dataAreaId, BatchlistScannerActivity.terminalId, BatchlistScannerActivity.stateCode);
            /*if (salesLine.getItemId().equalsIgnoreCase(result.getText())) {
                scannedQuantity++;
                mCallback.dialogShow(scannedQuantity);
                if (scannedQuantity == salesLine.getQty()) {
                    mCallback.onCompleteScan();
                }
            }*/
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    public void decode() {
        barcodeView.decodeSingle(barcodeCallback);
    }

    public void initializeFromIntent(Intent intent, Bundle savedInstanceState) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (savedInstanceState != null) {
            // If the screen was locked and unlocked again, we may start in a different orientation
            // (even one not allowed by the manifest). In this case we restore the orientation we were
            // previously locked to.
            this.orientationLock = savedInstanceState.getInt(SAVED_ORIENTATION_LOCK, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }

        if (intent != null) {
            // Only lock the orientation if it's not locked to something else yet
            boolean orientationLocked = intent.getBooleanExtra(Intents.Scan.ORIENTATION_LOCKED, true);
            if (orientationLocked) {
                lockOrientation();
            }

            if (Intents.Scan.ACTION.equals(intent.getAction())) {
                barcodeView.initializeFromIntent(intent);
            }

            if (!intent.getBooleanExtra(Intents.Scan.BEEP_ENABLED, true)) {
                beepManager.setBeepEnabled(false);
            }

            if (intent.hasExtra(Intents.Scan.TIMEOUT)) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        returnResultTimeout();
                    }
                };
                handler.postDelayed(runnable, intent.getLongExtra(Intents.Scan.TIMEOUT, 0L));
            }

            if (intent.getBooleanExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, false)) {
                returnBarcodeImagePath = true;
            }
        }
    }

    protected void lockOrientation() {
        // Only get the orientation if it's not locked to one yet.
        if (this.orientationLock == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            // Adapted from http://stackoverflow.com/a/14565436
            Display display = activity.getWindowManager().getDefaultDisplay();
            int rotation = display.getRotation();
            int baseOrientation = activity.getResources().getConfiguration().orientation;
            int orientation = 0;
            if (baseOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                } else {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                }
            } else if (baseOrientation == Configuration.ORIENTATION_PORTRAIT) {
                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270) {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                } else {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                }
            }

            this.orientationLock = orientation;
        }
        //noinspection ResourceType
        activity.setRequestedOrientation(this.orientationLock);
    }

    protected void returnResultTimeout() {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.putExtra(Intents.Scan.TIMEOUT, true);
        activity.setResult(Activity.RESULT_CANCELED, intent);
        closeAndFinish();
    }

    protected void closeAndFinish() {
        if (barcodeView.getBarcodeView().isCameraClosed()) {
            finish();
        } else {
            finishWhenClosed = true;
        }

        barcodeView.pause();
        inactivityTimer.cancel();
    }

    private void finish() {
        activity.finish();
    }

    public void setCaptureManagerCallback(CaptureManagerCallback callback) {
        this.mCallback = callback;
    }

    public void setSalesLine(GetOMSTransactionResponse.SalesLine salesLine) {
        this.salesLine = salesLine;
    }

    List<GetBatchInfoRes.BatchListObj> batchList;
    GetBatchInfoRes.BatchListObj batchListObj;
    List<GetBatchInfoRes.BatchListObj> salesLineBatchList = new ArrayList<>();
    @Override
    public void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse) {
        batchList = getBatchDetailsByBarcodeResponse.getBatchList();
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
        if (batchListObj == null) {
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
        for (int i = 0; i < salesLineBatchList.size(); i++) {
            scannedQuantity = scannedQuantity + salesLineBatchList.get(i).getREQQTY();
        }
        if (scannedQuantity == salesLine.getQty()) {
            mCallback.dialogShow(Double.toString(scannedQuantity));
            returnResult(salesLineBatchList);
        } else {
            mCallback.dialogShow(Double.toString(scannedQuantity));
        }
    }

    private void returnResult(List<GetBatchInfoRes.BatchListObj> salesLineBatchList) {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra("salesLineBatchList", (Serializable) salesLineBatchList);
        activity.setResult(Activity.RESULT_OK, intent);
        closeAndFinish();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
    }
}
