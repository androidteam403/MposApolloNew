package com.apollopharmacy.mpospharmacistTest.ui.scanner;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityScannerBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.onhold.OnHoldFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders.OrdersFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.ShippingLabelFragment;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import javax.inject.Inject;

public class ScannerActivity extends BaseActivity implements ScannerMvpView, DecoratedBarcodeView.TorchListener {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private TextView textView;
    private boolean isFlashLightOn = false;
    private ActivityScannerBinding activityScannerBinding;


    @Inject
    ScannerMvpPresenter<ScannerMvpView> mPresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ScannerActivity.class);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        activityScannerBinding = DataBindingUtil.setContentView(this, R.layout.activity_scanner);


        getActivityComponent().inject(this);
        mPresenter.onAttach(ScannerActivity.this);


        //Initialize barcode scanner view
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanners);
        ImageView imageView = findViewById(R.id.squarebox);
//        textView= findViewById(R.id.fullfillmentIdscanner);

//        textView.setText("Scan QR / barCode to tagbox for\nFullfillment ID" + fullfillmentId);
        if (PickedUpOrdersActivity.isPickedUpOrdersActivity) {
            imageView.setVisibility(View.GONE);
            PickedUpOrdersActivity.isPickedUpOrdersActivity = false;
        }

        if (OpenOrdersActivity.isopenOrderActivity) {
            imageView.setVisibility(View.GONE);
            OpenOrdersActivity.isopenOrderActivity = false;
        }

        if (BillerOrdersActivity.billerActivityScanner) {
            imageView.setVisibility(View.GONE);
            BillerOrdersActivity.billerActivityScanner = false;
        }
        if (ShippingLabelFragment.isShippingLabelFragment) {
            imageView.setVisibility(View.GONE);
            ShippingLabelFragment.isShippingLabelFragment = false;
        }
        if (OnHoldFragment.isOnHoldFragment) {
            imageView.setVisibility(View.GONE);
            OnHoldFragment.isOnHoldFragment = false;
        }

        if (OrdersFragment.isOrdersPFragment) {
            imageView.setVisibility(View.GONE);
            OrdersFragment.isOrdersPFragment = false;
        }

        barcodeScannerView.setTorchListener(this);


//

        activityScannerBinding.switchFlashlight.setVisibility(View.VISIBLE);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    protected void setUp() {

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
