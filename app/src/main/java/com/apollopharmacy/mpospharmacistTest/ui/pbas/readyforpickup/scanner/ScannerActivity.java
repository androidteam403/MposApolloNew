package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityScannerBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogConnectPrinterBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogScanContinueBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;

public class ScannerActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener, CaptureManagerCallback {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private Button switchFlashlightButton;
    private boolean isFlashLightOn = false;
    private List<TransactionHeaderResponse.OMSHeader> racksDataResponse;
    Bundle savedInstanceState;
    TextView scannedText, barcodeCount;
    int position;
    private List<String> barcodeList = new ArrayList<>();
    //    String fullfillmentId;
    TextView textView;
    private int pos = 0;
    TextView fulfilmentId;
    ActivityScannerBinding activityScannerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_p);

//         activityScannerBinding = DataBindingUtil.setContentView(this, R.layout.activity_scanner_p);

        ImageView imageView = findViewById(R.id.close_btn_w);

        imageView.setOnClickListener(v -> onBackPressed());

        scannedText = (TextView) findViewById(R.id.scanned_text);
        barcodeCount = (TextView) findViewById(R.id.barcode_count);
        fulfilmentId = (TextView) findViewById(R.id.fulfilment_id_num);
        int scannedOrdersCount = 0;
        if (!BillerOrdersActivity.isBillerActivity) {
            if (ReadyForPickUpActivity.selectedOmsHeaderListTest != null && ReadyForPickUpActivity.selectedOmsHeaderListTest.size() > 0) {
                for (int i = 0; i < ReadyForPickUpActivity.selectedOmsHeaderListTest.size(); i++) {
                    if (ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode() == null || ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode().isEmpty()) {
                        fulfilmentId.setText(ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getRefno());
                        pos = i;
                        break;
                    }
                }
                for (int i = 0; i < ReadyForPickUpActivity.selectedOmsHeaderListTest.size(); i++) {
                    if (ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode() != null && !ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode().isEmpty()) {
                        scannedOrdersCount++;
                    }
                }

            }
        }
        if (scannedOrdersCount == 0) {
            scannedText.setTextColor(getResources().getColor(R.color.text_color_grey));
        } else {
            scannedText.setTextColor(getResources().getColor(R.color.white));
        }
        barcodeCount.setText(scannedOrdersCount + "/" + ReadyForPickUpActivity.selectedOmsHeaderListTest.size());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        this.racksDataResponse = ReadyForPickUpActivity.selectedOmsHeaderListTest;

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);

        //set torch listener
        barcodeScannerView.setTorchListener(this);

        //switch flashlight button
        switchFlashlightButton = (Button) findViewById(R.id.switch_flashlight);
        switchFlashlightButton.setVisibility(View.GONE);
        if (!BillerOrdersActivity.isBillerActivity) {
            pos = ReadyForPickUpActivity.scannedItemPos;
            fulfilmentId.setText(ReadyForPickUpActivity.selectedOmsHeaderListTest.get(pos).getRefno());
        }
        //start capture
        capture = new CaptureManager(this, barcodeScannerView, getApplicationContext());
        capture.setOrderPos(pos);
        capture.setCaptureManagerCallback(this);
        capture.setBarcodeList(barcodeList);
        this.savedInstanceState = savedInstanceState;
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();


    }

    /**
     * Check if the device's camera has a Flashlight.
     *
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight() {
        if (isFlashLightOn) {
            barcodeScannerView.setTorchOff();
            isFlashLightOn = false;
        } else {
            barcodeScannerView.setTorchOn();
            isFlashLightOn = true;
        }
    }

    @Override
    public void onTorchOn() {
        switchFlashlightButton.setText(R.string.turn_off_flashlight);
    }

    @Override
    public void onTorchOff() {
        switchFlashlightButton.setText(R.string.turn_on_flashlight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    List<String> barcodeList1;


    @Override
    public void scannedListener(List<String> barcodeList) {
        this.barcodeList1 = barcodeList;
        int scannedOrdersCount = 0;
        if (!BillerOrdersActivity.isBillerActivity) {
            if (ReadyForPickUpActivity.selectedOmsHeaderListTest != null && ReadyForPickUpActivity.selectedOmsHeaderListTest.size() > 0) {
                for (int i = 0; i < ReadyForPickUpActivity.selectedOmsHeaderListTest.size(); i++) {
                    if (ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode() == null || ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode().isEmpty()) {
                        fulfilmentId.setText(ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getRefno());
                        pos = i;
                        break;
                    }
                }
                for (int i = 0; i < ReadyForPickUpActivity.selectedOmsHeaderListTest.size(); i++) {
                    if (ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode() != null && !ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode().isEmpty()) {
                        scannedOrdersCount++;
                    }

                }
            }

        }

        TextView barcodeCount = (TextView) findViewById(R.id.barcode_count);
        if (scannedOrdersCount == 0) {
            scannedText.setTextColor(getResources().getColor(R.color.text_color_grey));
        } else {
            scannedText.setTextColor(getResources().getColor(R.color.white));
        }
        barcodeCount.setText(scannedOrdersCount + "/" + ReadyForPickUpActivity.selectedOmsHeaderListTest.size());
        fulfilmentId.setText(ReadyForPickUpActivity.selectedOmsHeaderListTest.get(pos).getRefno());

//        capture = new CaptureManager(this, barcodeScannerView, getApplicationContext());
//        capture.setOrderPos(pos);
//        capture.setCaptureManagerCallback(this);
//        capture.setBarcodeList(barcodeList);
//        capture.initializeFromIntent(getIntent(), savedInstanceState);
//        capture.decode();


//        Toast.makeText(this, "naveen", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dialogShow(int orderPos) {
        Dialog dialog = new Dialog(this);
        DialogScanContinueBinding dialogScanContinueBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_scan_continue, null, false);
        dialogScanContinueBinding.message.setText(" FLid: " + ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos).getRefno() + "" + " tagged to Box Number: " + ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos).getScannedBarcode());
        dialog.setContentView(dialogScanContinueBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new Handler().postDelayed(() -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                initiateScanner();
            }
        }, 3000);
        dialog.show();
    }


    @Override
    public void onClickScanCode(String s, String refno) {

        Dialog dialogView = new Dialog(this);// R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogConnectPrinterBinding connectPrinterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_connect_printer, null, false);
        connectPrinterBinding.dialogMessage.setText("Box id " + s + " already tagged to " + refno + " Please tag another box id");
        connectPrinterBinding.printImg.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogView.setContentView(connectPrinterBinding.getRoot());
        dialogView.setCancelable(false);
        dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        connectPrinterBinding.dialogButtonOK.setText("Ok");
        connectPrinterBinding.dialogButtonOK.setOnClickListener(view -> {
            dialogView.dismiss();
            initiateScanner();

        });
        connectPrinterBinding.dialogButtonNO.setVisibility(View.GONE);
        dialogView.show();


//        ExitInfoDialog dialogView = new ExitInfoDialog(this);
//        dialogView.setTitle("");
//        dialogView.setPositiveLabel("OK");
//        dialogView.setSubtitle("Barcode " + s + " already tagged to " + refno + " Please tag another barcode");
//        dialogView.setPositiveListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogView.dismiss();
//                initiateScanner();
//
//
//            }
//        });
//        dialogView.show();


    }


    @Override
    public void onBackPressed() {
        if (!BillerOrdersActivity.isBillerActivity) {
            capture.onBackPressed();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        }
//
    }

    @Override
    public void isoxIdAlreadyAvailable() {
        initiateScanner();
    }

    public void initiateScanner() {

        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        capture.setOrderPos(pos);
        capture.setCaptureManagerCallback(this);
        capture.setBarcodeList(barcodeList);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//
//        final MenuItem menuCloseItem = menu.findItem(R.id.action_close);
//        View actionNotificationView = MenuItemCompat.getActionView(menuCloseItem);
//        actionNotificationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuCloseItem);
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_close) {
//            this.finish();
//            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}