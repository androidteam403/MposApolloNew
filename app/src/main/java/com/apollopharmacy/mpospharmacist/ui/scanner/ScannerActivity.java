package com.apollopharmacy.mpospharmacist.ui.scanner;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityScannerBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import javax.inject.Inject;

public class ScannerActivity extends BaseActivity implements ScannerMvpView, DecoratedBarcodeView.TorchListener {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private boolean isFlashLightOn = false;
    private ActivityScannerBinding activityScannerBinding;

    @Inject
    ScannerMvpPresenter<ScannerMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityScannerBinding = DataBindingUtil.setContentView(this, R.layout.activity_scanner);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ScannerActivity.this);

        //Initialize barcode scanner view
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);

        //set torch listener
        barcodeScannerView.setTorchListener(this);

        //switch flashlight button
        activityScannerBinding.switchFlashlight.setVisibility(View.GONE);

        //start capture
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    protected void setUp() {

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
        activityScannerBinding.switchFlashlight.setText(R.string.turn_off_flashlight);
    }

    @Override
    public void onTorchOff() {
        activityScannerBinding.switchFlashlight.setText(R.string.turn_on_flashlight);
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
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        return super.onOptionsItemSelected(item);
    }
}
