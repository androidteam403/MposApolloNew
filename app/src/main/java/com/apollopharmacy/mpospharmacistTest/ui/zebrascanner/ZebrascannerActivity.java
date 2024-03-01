package com.apollopharmacy.mpospharmacistTest.ui.zebrascanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityZebrascannerBoxtagingBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogConnectPrinterBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogScanContinueBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.google.zxing.client.android.Intents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class ZebrascannerActivity extends BaseActivity implements ZebrascannerMvpView {

    @Inject
    ZebrascannerMvpPresenter<ZebrascannerMvpView> mPresenter;
    ActivityZebrascannerBoxtagingBinding activityZebrascannerBoxtagingBinding;
    boolean sameBarcode;
    int position;
    private List<String> barcodeList = new ArrayList<>();
    String finalResult;
    private int orderPos;
    List<String> barcodeList1;
    TextView fulfilmentId;
    TextView scannedText, barcodeCount;
    private int pos = 0;
    private Activity activity;
    public static final int ZEBRA_ACTIVITY_REQUEST_CODE = 0;


    public static Intent getStartActivity(Context context) {
        return new Intent(context, ZebrascannerActivity.class);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityZebrascannerBoxtagingBinding = DataBindingUtil.setContentView(this, R.layout.activity_zebrascanner_boxtaging);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ZebrascannerActivity.this);

        setUp();

    }

    @Override
    protected void setUp() {


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView = findViewById(R.id.close_btn_w);
        imageView.setOnClickListener(v -> onBackPressed());

        ImageView cameraimageview=findViewById(R.id.camera_btn_w);
        cameraimageview.setOnClickListener(v->{
            ReadyForPickUpActivity.camera_zebrascanner=true;

         /*Intent intent = new Intent(this, ScannerActivity.class);
            startActivityForResult(intent, ZEBRA_ACTIVITY_REQUEST_CODE);
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);*/

          /*new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            //onBackPressed();*/
            finish();
        });
        scannedText = (TextView) findViewById(R.id.scanned_text);
        barcodeCount = (TextView) findViewById(R.id.barcode_count);
        fulfilmentId = (TextView) findViewById(R.id.fulfilment_id_num);
        activityZebrascannerBoxtagingBinding.scanboxid.requestFocus();

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
        if (ReadyForPickUpActivity.selectedOmsHeaderListTest!=null){
            barcodeCount.setText(scannedOrdersCount + "/" + ReadyForPickUpActivity.selectedOmsHeaderListTest.size());

        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        //InputMethodManager inputmethodmgr= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputmethodmgr.hideSoftInputFromWindow(activityZebrascannerBoxtagingBinding.scanboxid.getWindowToken(), 0);
      //  this.racksDataResponse = ReadyForPickUpActivity.selectedOmsHeaderListTest;
        //activityZebrascannerBoxtagingBinding.scanboxid.setKeyListener(null);

       // barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);

        //set torch listener
       // barcodeScannerView.setTorchListener(this);

        //switch flashlight button
       // switchFlashlightButton = (Button) findViewById(R.id.switch_flashlight);
       // switchFlashlightButton.setVisibility(View.GONE);
        if (!BillerOrdersActivity.isBillerActivity) {
            pos = ReadyForPickUpActivity.scannedItemPos;
            fulfilmentId.setText(ReadyForPickUpActivity.selectedOmsHeaderListTest.get(pos).getRefno());
        }
        //start capture
        setOrderPos(pos);

       /*activityZebrascannerBoxtagingBinding.scanboxid.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // do something..
                }
                closeKeyborad();
                return true;
            }

        });*/

        activityZebrascannerBoxtagingBinding.scanboxid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    mPresenter.scanqrcode(String.valueOf(s));
                    // productListMvpPresenter.getProductDetails();
                    //productListActivityBinding.pdialog.setVisibility(View.VISIBLE);
                    // isListFiltered = false;
                } else {
                    //  productListActivityBinding.setProductCount(0);
                    // itemsArrayList.clear();
                    // productListAdapter.clearDate();
                }
            }
        });
        activityZebrascannerBoxtagingBinding.scanboxid.setOnTouchListener(otl);
    }
    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch (View v, MotionEvent event) {
            return true; // the listener has consumed the event
        }
    };

    private void closeKeyborad() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

    @Override
    public void scanqrcode(String s) {
        finalResult = s;
        activityZebrascannerBoxtagingBinding.scanboxid.setText("");
        if (ReadyForPickUpActivity.selectedOmsHeaderListTest != null && ReadyForPickUpActivity.selectedOmsHeaderListTest.size() > 0) {
            sameBarcode = false;
            for (int o = 0; o < ReadyForPickUpActivity.selectedOmsHeaderListTest.size(); o++) {
                if (s.toString().equalsIgnoreCase(ReadyForPickUpActivity.selectedOmsHeaderListTest.get(o).getScannedBarcode())) {
                    sameBarcode = true;
                    position = o;
                }
            }
        }

        if (!sameBarcode) {
            mPresenter.mposPickPackOrderReservationApiCall(1, ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos), ReadyForPickUpActivity.userName, ReadyForPickUpActivity.storeId, ReadyForPickUpActivity.terminalId, ReadyForPickUpActivity.eposUrl, s.toString(), ReadyForPickUpActivity.dataAreaId);


            //new CaptureManagerController(activity, CaptureManager.this).mposPickPackOrderReservationApiCall(1, ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos), ReadyForPickUpActivity.userName, ReadyForPickUpActivity.storeId, ReadyForPickUpActivity.terminalId, ReadyForPickUpActivity.eposUrl, s.toString(), ReadyForPickUpActivity.dataAreaId);

//                    barcodeList.add(result.toString());
//                    ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos).setScannedBarcode(result.toString());
//                    boolean isAllBarcodeScanned = true;
//                    if (ReadyForPickUpActivity.selectedOmsHeaderListTest != null) {
//                        for (int i = 0; i < ReadyForPickUpActivity.selectedOmsHeaderListTest.size(); i++) {
//                            if (ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode() == null || ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode().isEmpty()) {
//                                isAllBarcodeScanned = false;
//                            }
//                        }
//                    }
//                    if (isAllBarcodeScanned) {
//                        returnResult(result, barcodeList);
//                    } else {
//                        if (!BillerOrdersActivity.isBillerActivity) {
//                            barcodeView.resume();
//                            mCallback.scannedListener(barcodeList);
//                        } else {
//                            returnResult(result, barcodeList);
//                        }
//                        mCallback.dialogShow(orderPos);
//                    }


        } else {
            mPresenter.onClickScanCode(s.toString(), ReadyForPickUpActivity.selectedOmsHeaderListTest.get(position).getRefno());
        }

    }

    @Override
    public void dialogShow(int orderPos) {
        Dialog dialog = new Dialog(this);
        DialogScanContinueBinding dialogScanContinueBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_scan_continue, null, false);
        dialogScanContinueBinding.message.setText(" FLid: " + ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos-1).getRefno() + "" + " tagged to Tray Id: " + ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos-1).getScannedBarcode());
        dialog.setContentView(dialogScanContinueBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new Handler().postDelayed(() -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                //  initiateScanner();
            }
        }, 3000);
        dialog.show();
    }

    public void setOrderPos(int orderPos) {
        this.orderPos = orderPos;
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
            barcodeList.add(finalResult.toString());
            ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos).setScannedBarcode(finalResult.toString());
            ReadyForPickUpActivity.selectedOmsHeaderListTest.get(orderPos).setPickupReserved(true);
            boolean isAllBarcodeScanned = true;
            if (ReadyForPickUpActivity.selectedOmsHeaderListTest != null) {
                for (int i = 0; i < ReadyForPickUpActivity.selectedOmsHeaderListTest.size(); i++) {
                    if (ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode() == null || ReadyForPickUpActivity.selectedOmsHeaderListTest.get(i).getScannedBarcode().isEmpty()) {
                        isAllBarcodeScanned = false;
                    }
                }
            }
            if(isAllBarcodeScanned) {
                returnResult(finalResult, barcodeList);
            } else {
                if (!BillerOrdersActivity.isBillerActivity) {
                    //barcodeView.resume();
                    mPresenter.scannedListener(barcodeList);
                } else {
                    returnResult(finalResult, barcodeList);
                }
                mPresenter.dialogShow(orderPos);
            }
        } else if (mposPickPackOrderReservationResponse.getRequestStatus() == 1) {

            Dialog dialog = new Dialog(this);//R.style.Theme_AppCompat_DayNight_NoActionBar
            DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_cancel, null, false);
            dialogCancelBinding.dialogMessage.setText(mposPickPackOrderReservationResponse.getReturnMessage() + "\nplease choose another box id's.");
            dialog.setContentView(dialogCancelBinding.getRoot());
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialogCancelBinding.dialogButtonNO.setVisibility(View.GONE);
            dialogCancelBinding.dialogButtonOK.setText("Ok");
            dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
                dialog.dismiss();
                // mCallback.isoxIdAlreadyAvailable();
            });
            dialog.show();
        }


    }

    private String getBarcodeImagePath(String rawResult) {
        String barcodeImagePath = null;
//        if (returnBarcodeImagePath) {
//            Bitmap bmp = rawResult.getBitmap();
//            try {
//                File bitmapFile = File.createTempFile("barcodeimage", ".jpg", activity.getCacheDir());
//                FileOutputStream outputStream = new FileOutputStream(bitmapFile);
//                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                outputStream.close();
//                barcodeImagePath = bitmapFile.getAbsolutePath();
//            } catch (IOException e) {
//                Log.w(TAG, "Unable to create temporary file and store bitmap! " + e);
//            }
//        }
        return barcodeImagePath;
    }

    public static Intent resultIntent(String rawResult, String barcodeImagePath, List<String> barcodeList) {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intents.Scan.RESULT, rawResult.toString());
        intent.putExtra(Intents.Scan.RESULT_FORMAT, rawResult);
        intent.putExtra("BARCODE_LIST", (Serializable) barcodeList);

        /*byte[] rawBytes = rawResult.getRawBytes();
        if (rawBytes != null && rawBytes.length > 0) {
            intent.putExtra(Intents.Scan.RESULT_BYTES, rawBytes);
        }
        Map<ResultMetadataType, ?> metadata = rawResult.getResultMetadata();
        if (metadata != null) {
            if (metadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
                intent.putExtra(Intents.Scan.RESULT_UPC_EAN_EXTENSION,
                        metadata.get(ResultMetadataType.UPC_EAN_EXTENSION).toString());
            }
            Number orientation = (Number) metadata.get(ResultMetadataType.ORIENTATION);
            if (orientation != null) {
                intent.putExtra(Intents.Scan.RESULT_ORIENTATION, orientation.intValue());
            }
            String ecLevel = (String) metadata.get(ResultMetadataType.ERROR_CORRECTION_LEVEL);
            if (ecLevel != null) {
                intent.putExtra(Intents.Scan.RESULT_ERROR_CORRECTION_LEVEL, ecLevel);
            }
            @SuppressWarnings("unchecked")
            Iterable<byte[]> byteSegments = (Iterable<byte[]>) metadata.get(ResultMetadataType.BYTE_SEGMENTS);
            if (byteSegments != null) {
                int i = 0;
                for (byte[] byteSegment : byteSegments) {
                    intent.putExtra(Intents.Scan.RESULT_BYTE_SEGMENTS_PREFIX + i, byteSegment);
                    i++;
                }
            }
        }
        if (barcodeImagePath != null) {
            intent.putExtra(Intents.Scan.RESULT_BARCODE_IMAGE_PATH, barcodeImagePath);
        }*/
        return intent;
    }

    protected void returnResult(String rawResult, List<String> barcodeList) {
        Intent intent=new Intent();
         //Intent intent = resultIntent(rawResult, getBarcodeImagePath(rawResult), barcodeList);
        intent.putExtra("IS_BACK_PRESSED", true);
        intent.putExtra("BARCODE_LIST",(Serializable) barcodeList);
        setResult(RESULT_OK, intent);
       // activity.startActivityForResult(intent, Activity.RESULT_OK);
        closeAndFinish();
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("IS_BACK_PRESSED", true);
        setResult(Activity.RESULT_OK, intent);
        closeAndFinish();
    }

    protected void closeAndFinish() {
        finish();
       /* if (barcodeView.getBarcodeView().isCameraClosed()) {
            finish();
        } else {
            finishWhenClosed = true;
        }

        barcodeView.pause();
        inactivityTimer.cancel();*/
    }


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

        setOrderPos(pos);
//        capture = new CaptureManager(this, barcodeScannerView, getApplicationContext());
//        capture.setOrderPos(pos);
//        capture.setCaptureManagerCallback(this);
//        capture.setBarcodeList(barcodeList);
//        capture.initializeFromIntent(getIntent(), savedInstanceState);
//        capture.decode();


//        Toast.makeText(this, "naveen", Toast.LENGTH_SHORT).show();
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
            //  initiateScanner();

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

}
