package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.scanner;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogDroppingBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogScanContinueBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummmaryActivityNew;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class PickupSummaryZebrascannerActivity extends AppCompatActivity {
    private PickupSummaryCaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private Button switchFlashlightButton;
    private boolean isFlashLightOn = false;
    Bundle savedInstanceState;
    TextView scannedText, barcodeCount, fulfilmentId,boxid;
    public String selectedStatus = "";
    private int pos = 0;
    Dialog dialog;

    EditText Scanqrcodetext;
    ImageView Cameraview;
    private int orderPos;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_summary_zebrascanner);
        if (getIntent() != null) {
            selectedStatus = getIntent().getStringExtra("selectedStatus");
        }
        ImageView closeBtn = findViewById(R.id.close_btn_w);
        closeBtn.setOnClickListener(view -> onBackPressed());
        scannedText = findViewById(R.id.scanned_text);
        barcodeCount = findViewById(R.id.barcode_count);
        fulfilmentId = findViewById(R.id.fulfilment_id_num);
        boxid= findViewById(R.id.boxid_zebra);

        Scanqrcodetext = findViewById(R.id.zebra_scanboxid);
        Cameraview=findViewById(R.id.camera_btn_w);
        int scannedOrdersCount = 0;
        if(PickUpSummmaryActivityNew.omsHeaderList != null && PickUpSummmaryActivityNew.omsHeaderList.size() > 0) {
            for (int i = 0; i < PickUpSummmaryActivityNew.omsHeaderList.size(); i++) {
                if (!PickUpSummmaryActivityNew.omsHeaderList.get(i).isScanned()) {
                    fulfilmentId.setText(PickUpSummmaryActivityNew.omsHeaderList.get(i).getRefno());
                    boxid.setText(PickUpSummmaryActivityNew.omsHeaderList.get(i).getScannedBarcode());
                    pos = i;
                    break;
                }
            }
            for (int i = 0; i < PickUpSummmaryActivityNew.omsHeaderList.size(); i++) {
                if (PickUpSummmaryActivityNew.omsHeaderList.get(i).isScanned()) {
                    scannedOrdersCount++;
                }
            }
        }
        if (scannedOrdersCount == 0) {
            scannedText.setTextColor(getResources().getColor(R.color.text_color_grey));
        } else {
            scannedText.setTextColor(getResources().getColor(R.color.white));
        }
        if (PickUpSummmaryActivityNew.omsHeaderList != null) {
            barcodeCount.setText(scannedOrdersCount + "/" + PickUpSummmaryActivityNew.omsHeaderList.size());
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
         //barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
         //barcodeScannerView.setTorchListener(this);
         //switchFlashlightButton = findViewById(R.id.switch_flashlight);
         //switchFlashlightButton.setVisibility(View.GONE);

        if(!BillerOrdersActivity.isBillerActivity) {
            if (PickUpSummmaryActivityNew.omsHeaderList != null && PickUpSummmaryActivityNew.omsHeaderList.size() > 1) {
                pos = PickUpSummmaryActivityNew.position;
            }
            if (PickUpSummmaryActivityNew.omsHeaderList != null && PickUpSummmaryActivityNew.omsHeaderList.size() > 0) {
                fulfilmentId.setText(PickUpSummmaryActivityNew.omsHeaderList.get(pos).getRefno());
            }
        }

        Scanqrcodetext.requestFocus();
        Scanqrcodetext.addTextChangedListener(new TextWatcher() {
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
                    zebrascaneerfunction(String.valueOf(s));
                }
            }
        });

        Scanqrcodetext.setOnTouchListener(otl);
        Cameraview.setOnClickListener(view->{
            PickUpSummmaryActivityNew.cameraenabled=true;
            finish();
        });

      /*capture = new PickupSummaryCaptureManager(this, barcodeScannerView, getApplicationContext());
        capture.setSelectedStatus(selectedStatus);
        capture.setOrderPos(pos);
        capture.setCaptureManagerCallback(this);
        this.savedInstanceState = savedInstanceState;
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();*/

        this.orderPos=pos;

    }

    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch (View v, MotionEvent event) {
            return true; // the listener has consumed the event
        }
    };

    public void setOrderPos(int orderPos) {
        this.orderPos = orderPos;
    }
    public  void zebrascaneerfunction(String s)
    {
        String scannedResult = "";
        if (s.equalsIgnoreCase("PARTIALLY")) {
            scannedResult = "PARTIAL";
        } else {
            scannedResult = s;
        }
        if (!selectedStatus.isEmpty()) {
           onHeaderBarcodeScanned(selectedStatus.equalsIgnoreCase(scannedResult));
        } else {
            if (PickUpSummmaryActivityNew.omsHeaderList.get(orderPos).getScannedBarcode().equalsIgnoreCase(scannedResult)) {
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
                    onCompleteScan();
                } else {
                    onScanned();
                    dialogShow(orderPos);
                }
            } else {
                invalidBarcodeScanned();
            }
        }
    }



//    @Override
//    public void onTorchOn() {
//
//    }
//
//    @Override
//    public void onTorchOff() {
//
//    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
       // capture.onBackPressed();
        finish();
    }


    public void onHeaderBarcodeScanned(boolean isMatched) {
        Intent intent = new Intent();
        intent.putExtra("isBarcodeMatched", isMatched);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void onScanned() {
        int scannedOrdersCount = 0;
        if (PickUpSummmaryActivityNew.omsHeaderList != null && PickUpSummmaryActivityNew.omsHeaderList.size() > 0) {
            for (int i = 0; i < PickUpSummmaryActivityNew.omsHeaderList.size(); i++) {
                if (!PickUpSummmaryActivityNew.omsHeaderList.get(i).isScanned()) {
                    fulfilmentId.setText(PickUpSummmaryActivityNew.omsHeaderList.get(i).getRefno());
                    pos = i;
                    break;
                }
            }
            for (int i = 0; i < PickUpSummmaryActivityNew.omsHeaderList.size(); i++) {
                if (PickUpSummmaryActivityNew.omsHeaderList.get(i).isScanned()) {
                    scannedOrdersCount++;
                }
            }
        }
        TextView barcodeCount = findViewById(R.id.barcode_count);
        if (scannedOrdersCount == 0) {
            scannedText.setTextColor(getResources().getColor(R.color.text_color_grey));
        } else {
            scannedText.setTextColor(getResources().getColor(R.color.white));
        }
        barcodeCount.setText(scannedOrdersCount + "/" + PickUpSummmaryActivityNew.omsHeaderList.size());
        fulfilmentId.setText(PickUpSummmaryActivityNew.omsHeaderList.get(pos).getRefno());
    }


    public void dialogShow(int orderPos) {
        dialog = new Dialog(this);
        DialogScanContinueBinding dialogScanContinueBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_scan_continue, null, false);
        dialogScanContinueBinding.message.setText(" FLid: " + PickUpSummmaryActivityNew.omsHeaderList.get(orderPos).getRefno() + " Scanned");
        dialog.setContentView(dialogScanContinueBinding.getRoot());
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new Handler().postDelayed(() -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                initiateScanner();
            }
        }, 2000);
        dialog.show();
    }


    public void onCompleteScan() {
        Intent intent = new Intent();
        intent.putExtra("allCompleted", true);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void invalidBarcodeScanned() {
        Dialog dialog = new Dialog(PickupSummaryZebrascannerActivity.this);
        DialogDroppingBinding dialogDroppingBinding = DataBindingUtil.inflate(LayoutInflater.from(PickupSummaryZebrascannerActivity.this),
                R.layout.dialog_dropping, null, false);
        dialog.setContentView(dialogDroppingBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogDroppingBinding.message.setText("You are Scanning the Incorrect \nBox ID Kindly Check again.");
        dialogDroppingBinding.message1.setVisibility(View.GONE);
        dialogDroppingBinding.ok.setOnClickListener(view -> {
            dialog.dismiss();
            initiateScanner();
        });

        dialogDroppingBinding.close.setOnClickListener(view -> {
            dialog.dismiss();
            initiateScanner();
        });
        dialog.show();
    }

    private void initiateScanner() {

       /* new IntentIntegrator(this).setCaptureActivity(PickupSummaryScannerActivity.class).initiateScan();
        capture.setOrderPos(pos);
        capture.setCaptureManagerCallback(this);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();*/

        this.orderPos=pos;
        Scanqrcodetext.setText("");
        Scanqrcodetext.requestFocus();



    }

    @Override
    protected void onResume() {
        super.onResume();
      //  capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
        //capture.onDestroy();
    }

    public void setSavedInstanceState(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
      //  capture.onSaveInstanceState(savedInstanceState);
    }

  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }*/

}