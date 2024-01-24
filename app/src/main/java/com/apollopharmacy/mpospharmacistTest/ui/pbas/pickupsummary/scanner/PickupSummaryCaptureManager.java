package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.scanner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummmaryActivityNew;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class PickupSummaryCaptureManager {
    private static final String TAG = com.journeyapps.barcodescanner.CaptureManager.class.getSimpleName();
    private static int cameraPermissionReqCode = 250;
    private static final String SAVED_ORIENTATION_LOCK = "SAVED_ORIENTATION_LOCK";
    private Activity activity;
    private DecoratedBarcodeView barcodeView;
    private int orientationLock = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    private boolean returnBarcodeImagePath = false;
    private PickupSummaryCaptureManagerCallback mCallback;
    private boolean destroyed = false;
    Context context;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private Handler handler;
    boolean sameBarcode;
    private boolean finishWhenClosed = false;
    Context applicationContext;
    String selectedStatus;
    private int orderPos;

    public PickupSummaryCaptureManager(Activity activity, DecoratedBarcodeView barcodeView, Context applicationContext) {
        this.applicationContext = applicationContext;
        this.activity = activity;
        this.barcodeView = barcodeView;
        barcodeView.getBarcodeView().addStateListener(stateListener);
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

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
//            barcodeView.pause();
            beepManager.playBeepSoundAndVibrate();
            String scannedResult = "";
            if (result.getText().equalsIgnoreCase("PARTIALLY")) {
                scannedResult = "PARTIAL";
            } else {
                scannedResult = result.getText();
            }
            if (!selectedStatus.isEmpty()) {
                mCallback.onHeaderBarcodeScanned(selectedStatus.equalsIgnoreCase(scannedResult));
            } else {
                if (PickUpSummmaryActivityNew.omsHeaderList.get(orderPos).getItemStatus().equalsIgnoreCase(scannedResult)) {
                    PickUpSummmaryActivityNew.omsHeaderList.get(orderPos).setScanned(true);
                    boolean isAllBarcodeScanned = true;
                    if (PickUpSummmaryActivityNew.omsHeaderList != null) {
                        for (int i = 0; i < PickUpSummmaryActivityNew.omsHeaderList.size(); i++) {
                            if (!PickUpSummmaryActivityNew.omsHeaderList.get(i).isScanned()) {
                                isAllBarcodeScanned = false;
                            }
                        }
                    }
                    if (isAllBarcodeScanned) {
                        mCallback.onCompleteScan();
                    } else {
                        mCallback.onScanned();
                        mCallback.dialogShow(orderPos);
                    }
                } else {
                   mCallback.invalidBarcodeScanned();
                }
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("IS_BACK_PRESSED", true);
        activity.setResult(Activity.RESULT_OK, intent);
        closeAndFinish();
    }

    private void closeAndFinish() {
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

    private final CameraPreview.StateListener stateListener = new CameraPreview.StateListener() {
        @Override
        public void previewSized() {

        }

        @Override
        public void previewStarted() {

        }

        @Override
        public void previewStopped() {

        }

        @Override
        public void cameraError(Exception error) {
            displayFrameworkBugMessageAndExit();
        }

        @Override
        public void cameraClosed() {
            if (finishWhenClosed) {
                Log.d(TAG, "Camera closed; finishing activity");
                finish();
            }
        }
    };

    private void displayFrameworkBugMessageAndExit() {
        if (activity.isFinishing() || this.destroyed || finishWhenClosed) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(com.google.zxing.client.android.R.string.zxing_app_name));
        builder.setMessage(activity.getString(com.google.zxing.client.android.R.string.zxing_msg_camera_framework_bug));
        builder.setPositiveButton(com.google.zxing.client.android.R.string.zxing_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void setCaptureManagerCallback(PickupSummaryCaptureManagerCallback captureManagerCallback) {
        this.mCallback = captureManagerCallback;
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

    protected void returnResultTimeout() {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.putExtra(Intents.Scan.TIMEOUT, true);
        activity.setResult(Activity.RESULT_CANCELED, intent);
        closeAndFinish();
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

    public void decode() {
        barcodeView.decodeSingle(callback);
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public void setOrderPos(int orderPos) {
        this.orderPos = orderPos;
    }

    public void onResume() {
        if (Build.VERSION.SDK_INT >= 23) {
            openCameraWithPermission();
        } else {
            barcodeView.resume();
        }
        inactivityTimer.start();
    }

    private boolean askedPermission = false;
    private void openCameraWithPermission() {
        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            barcodeView.resume();
        } else if (!askedPermission) {
            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.CAMERA},
                    cameraPermissionReqCode);
            askedPermission = true;
        } else {
            // Wait for permission result
        }
    }

    public void onPause() {
        inactivityTimer.cancel();
        barcodeView.pauseAndWait();
    }

    public void onDestroy() {
        destroyed = true;
        inactivityTimer.cancel();
        handler.removeCallbacksAndMessages(null);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(SAVED_ORIENTATION_LOCK, this.orientationLock);
    }
}