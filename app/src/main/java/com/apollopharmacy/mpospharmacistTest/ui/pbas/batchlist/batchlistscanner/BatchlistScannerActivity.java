package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBatchlistScannerBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogOnHoldBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogReferToAdminBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogSkipOrderBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner.model.ReasonListResponse;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class BatchlistScannerActivity extends BaseActivity implements BatchlistScannerMvpView, DecoratedBarcodeView.TorchListener {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private boolean isFlashLightOn = false;
    private ActivityBatchlistScannerBinding activityBatchlistScannerBinding;
    private ScannedBatchListAdapter scannedBatchListAdapter;
    private List<GetBatchInfoRes.BatchListObj> batchList = new ArrayList<>();
    DialogSkipOrderBinding dialogSkipOrderBinding;


    @Inject
    BatchlistScannerMvpPresenter<BatchlistScannerMvpView> mPresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BatchlistScannerActivity.class);
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
        activityBatchlistScannerBinding = DataBindingUtil.setContentView(this, R.layout.activity_batchlist_scanner);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BatchlistScannerActivity.this);
        activityBatchlistScannerBinding.setCallback(mPresenter);

        String itemId = "";
        if (getIntent() != null) {
            itemId = getIntent().getStringExtra("ITEM_ID");
        }
        //Initialize barcode scanner view
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanners);
        ImageView imageView = findViewById(R.id.squarebox);
        barcodeScannerView.setTorchListener(this);

        barcodeScannerView.initializeFromIntent(getIntent());
        String finalItemId = itemId;
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                mPresenter.getBatchDetailsByBarCode(result.getText(), finalItemId);
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

//

//        activityBatchlistScannerBinding.switchFlashlight.setVisibility(View.VISIBLE);
//        capture = new CaptureManager(this, barcodeScannerView);
//        capture.initializeFromIntent(getIntent(), savedInstanceState);
//        capture.decode();

        ArrayList<GetBatchInfoRes.BatchListObj> filteredList = new ArrayList<>();
        activityBatchlistScannerBinding.search.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        activityBatchlistScannerBinding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchedText = textView.getText().toString().trim();
                    if (searchedText.isEmpty()) {
                        filteredList.clear();
                        filteredList.addAll(batchList);
                        scannedBatchListAdapter.filter(filteredList);
                        activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
                        activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
                        activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.VISIBLE);
                        CommonUtils.hideKeyboard(BatchlistScannerActivity.this);
                        return true;
                    } else {
                        filteredList.clear();
                        for (int i = 0; i < batchList.size(); i++) {
                            if (batchList.get(i).getBatchNo().contains(searchedText)) {
                                filteredList.add(batchList.get(i));
                            }
                        }
                        if (filteredList.size() > 0) {
                            scannedBatchListAdapter.filter(filteredList);
                            activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
                            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
                            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.VISIBLE);
                            CommonUtils.hideKeyboard(BatchlistScannerActivity.this);
                            return true;
                        } else {
                            activityBatchlistScannerBinding.noListFound.setVisibility(View.VISIBLE);
                            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.GONE);
                            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.GONE);
                            CommonUtils.hideKeyboard(BatchlistScannerActivity.this);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onTorchOn() {
//        activityBatchlistScannerBinding.switchFlashlight.setText(R.string.turn_off_flashlight);
    }

    @Override
    public void onTorchOff() {
//        activityBatchlistScannerBinding.switchFlashlight.setText(R.string.turn_on_flashlight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeScannerView.resume();
//        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeScannerView.pause();
//        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        capture.onSaveInstanceState(outState);
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

    @Override
    public void onClickClose() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClickSkip() {
        mPresenter.getReasonList();
    }

    @Override
    public void onSuccessGetBatchDetailsBarcode(GetBatchInfoRes getBatchDetailsByBarcodeResponse) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (320 * scale + 0.5f);
        activityBatchlistScannerBinding.scannerLayout.getLayoutParams().height = pixels;
        activityBatchlistScannerBinding.btnLayout.setVisibility(View.GONE);
        if (getBatchDetailsByBarcodeResponse != null && getBatchDetailsByBarcodeResponse.getBatchList() != null && getBatchDetailsByBarcodeResponse.getBatchList().size() > 0) {
            this.batchList = getBatchDetailsByBarcodeResponse.getBatchList();
            activityBatchlistScannerBinding.noListFound.setVisibility(View.GONE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.VISIBLE);
            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.VISIBLE);
            scannedBatchListAdapter = new ScannedBatchListAdapter(this, getBatchDetailsByBarcodeResponse.getBatchList(), this);
            activityBatchlistScannerBinding.batchListRcv.setAdapter(scannedBatchListAdapter);
            activityBatchlistScannerBinding.batchListRcv.setLayoutManager(new LinearLayoutManager(this));
        } else {
            activityBatchlistScannerBinding.noListFound.setVisibility(View.VISIBLE);
            activityBatchlistScannerBinding.batchListRcv.setVisibility(View.GONE);
            activityBatchlistScannerBinding.batchDetailsHeader.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClickReferToAdmin() {
        Dialog dialog = new Dialog(this);
        DialogReferToAdminBinding dialogReferToAdminBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_refer_to_admin, null, false);
        dialog.setContentView(dialogReferToAdminBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogReferToAdminBinding.close.setOnClickListener(v -> dialog.dismiss());
        dialogReferToAdminBinding.okButton.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onClickOnHold() {
        Dialog dialog = new Dialog(this);
        DialogOnHoldBinding dialogOnHoldBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_on_hold, null, false);
        dialog.setContentView(dialogOnHoldBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOnHoldBinding.close.setOnClickListener(v -> dialog.dismiss());
        dialogOnHoldBinding.saveButton.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(false);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccessGetResonListApiCall(ReasonListResponse reasonListResponse) {
        List<String> reasons = reasonListResponse.getData().stream()
                .map(ReasonListResponse.Datum::getReasonDesc).collect(Collectors.toList());
        if (reasonListResponse != null && reasonListResponse.getData().size() > 0) {
            Dialog dialog = new Dialog(this);
            dialogSkipOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_skip_order, null, false);
            dialog.setContentView(dialogSkipOrderBinding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogSkipOrderBinding.close.setOnClickListener(v1 -> dialog.dismiss());
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, reasons);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dialogSkipOrderBinding.selectReaon.setAdapter(arrayAdapter);
            dialogSkipOrderBinding.selectReaon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextSize(12);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dialog.setCancelable(false);
            dialog.show();
        }
    }
}
