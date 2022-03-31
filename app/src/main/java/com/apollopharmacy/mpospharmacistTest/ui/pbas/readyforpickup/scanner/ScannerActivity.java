package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;

public class ScannerActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener, CaptureManagerCallback {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private Button switchFlashlightButton;
    private boolean isFlashLightOn = false;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    Bundle savedInstanceState;
    private List<String> barcodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_p);
        TextView barcodeCount = (TextView) findViewById(R.id.barcode_count);
        if (!BillerOrdersActivity.isBillerActivity) {
            barcodeCount.setText("0/" + ReadyForPickUpActivity.fullfillmentDetailList.size());
        } else {
            barcodeCount.setVisibility(View.GONE);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        this.racksDataResponse = ReadyForPickUpActivity.fullfillmentDetailList;


        //Initialize barcode scanner view
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);

        //set torch listener
        barcodeScannerView.setTorchListener(this);

        //switch flashlight button
        switchFlashlightButton = (Button) findViewById(R.id.switch_flashlight);
        switchFlashlightButton.setVisibility(View.GONE);

        //start capture
        capture = new CaptureManager(this, barcodeScannerView);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
    }

    @Override
    public void scannedListener(List<String> barcodeList) {
        TextView barcodeCount = (TextView) findViewById(R.id.barcode_count);
        barcodeCount.setText(barcodeList.size() + "/" + ReadyForPickUpActivity.fullfillmentDetailList.size());
        capture = new CaptureManager(this, barcodeScannerView);
        capture.setCaptureManagerCallback(this);
        capture.setBarcodeList(barcodeList);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        Toast.makeText(this, "naveen", Toast.LENGTH_SHORT).show();
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