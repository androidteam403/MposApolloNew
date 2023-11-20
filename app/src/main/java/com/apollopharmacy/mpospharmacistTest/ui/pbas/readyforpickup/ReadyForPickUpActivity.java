package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityReadyForPickupPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterReadyForPickupPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBoxidAlertBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogConnectPrinterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.adapter.ReadyForPickUpAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.dialog.ScanQrCodeDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.dialog.UnTagQrCodeDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.utils.BluetoothActivity;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.printf.manager.BluetoothManager;
import com.printf.manager.PrintfTSPLManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class ReadyForPickUpActivity extends BaseActivity implements ReadyForPickUpMvpView {

    @Inject
    ReadyForPickUpMvpPresenter<ReadyForPickUpMvpView> mPresenter;
    ActivityReadyForPickupPBinding activityReadyForPickupBinding;
    AdapterReadyForPickupPBinding adapterReadyForPickupBinding;
    //    private FullfillmentData fullfillmentData;
    private ReadyForPickUpAdapter readyForPickUpAdapter;
    //    List<FullfillmentData> fullfillmentDataList;
    public static List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderListTest;

    public static String userName;
    public static String storeId;
    public static String terminalId;
    public static String eposUrl;
    public static String dataAreaId;

    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    public IntentResult Result;
    public int pos;

    public static String RefId;
    private List<String> barcodeList;
    private List<String> list = new ArrayList<>();
    private String[] printerDeviceList = {"MLP 360", "SPP-L310_050007", "SQP-L210_054037"};
    private final int ACTIVITY_BARCODESCANNER_DETAILS_CODE = 151;

    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    private List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList;

    int taggedCount = 0;


    public static Intent getStartActivity(Context context, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, List<TransactionHeaderResponse.OMSHeader> omsHeaderList) {
        Intent intent = new Intent(context, ReadyForPickUpActivity.class);
        intent.putExtra(CommonUtils.SELECTED_ORDERS_LIST, (Serializable) selectedOmsHeaderList);
        intent.putExtra(CommonUtils.ALL_ORDERS, (Serializable) omsHeaderList);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReadyForPickupBinding = DataBindingUtil.setContentView(this, R.layout.activity_ready_for_pickup_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ReadyForPickUpActivity.this);
        setUp();
    }

    private void takePrintEnableHandled(boolean isTakePrintEnable) {
        if (!isTakePrintEnable) {
            activityReadyForPickupBinding.takePrint.setVisibility(View.VISIBLE);
//            activityReadyForPickupBinding.takePrint.setBackground(getResources().getDrawable(R.drawable.rounde_corner_takeprint_bg));
//            activityReadyForPickupBinding.takePrint.setTextColor(getResources().getColor(R.color.white));
//            activityReadyForPickupBinding.takePrint.setEnabled(true);

        } else {
            activityReadyForPickupBinding.takePrint.setVisibility(View.GONE);
//            activityReadyForPickupBinding.takePrint.setBackground(getResources().getDrawable(R.drawable.rounded_corner_takeprint_bg_disabled));
//            activityReadyForPickupBinding.takePrint.setTextColor(getResources().getColor(R.color.text_color_grey));
//            activityReadyForPickupBinding.takePrint.setEnabled(false);
        }

    }

    @Override
    protected void setUp() {
        activityReadyForPickupBinding.setCallback(mPresenter);

        userName = mPresenter.userName();
        storeId = mPresenter.storeId();
        terminalId = mPresenter.terminalId();
        eposUrl = mPresenter.eposUrl();
        dataAreaId = mPresenter.dataAreaId();

        if (getIntent() != null) {
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra(CommonUtils.SELECTED_ORDERS_LIST);
            totalOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra(CommonUtils.ALL_ORDERS);
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < totalOmsHeaderList.size(); i++) {
                    if (!totalOmsHeaderList.get(i).isSelected()) {
                        totalOmsHeaderList.get(i).setPickupReserved(false);
                    }
                }
            }
            if (selectedOmsHeaderList != null) {
                activityReadyForPickupBinding.headerOrdersCount.setText("Total " + selectedOmsHeaderList.size() + " Orders");
                activityReadyForPickupBinding.baskets.setText(Integer.toString(selectedOmsHeaderList.size()));
            }
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    selectedOmsHeaderList.get(i).setExpandStatus(0);
                }
                readyForPickUpAdapter = new ReadyForPickUpAdapter(this, selectedOmsHeaderList, this);
                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                activityReadyForPickupBinding.readyForPickupRecycleView.setLayoutManager(mLayoutManager1);

                activityReadyForPickupBinding.readyForPickupRecycleView.setItemAnimator(new DefaultItemAnimator());
                activityReadyForPickupBinding.readyForPickupRecycleView.setAdapter(readyForPickUpAdapter);

                activityReadyForPickupBinding.pending.setText(String.valueOf(selectedOmsHeaderList.size()));
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    if (selectedOmsHeaderList.get(i).isTagBox()) {
                        taggedCount++;
                        activityReadyForPickupBinding.tagged.setText(String.valueOf(taggedCount));
                        activityReadyForPickupBinding.pending.setText(String.valueOf(selectedOmsHeaderList.size() - taggedCount));
                    }
                }

                boolean isAlltagBox = true;
                for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)

                    if (!omsHeader.isTagBox())
                        isAlltagBox = false;
                if (isAlltagBox) {
//                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));

                    activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.yellow));
                    activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.black));

//                    activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                    activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.text_color_grey));

                    activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.text_color_grey));

                } else {
//                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));

                    activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.text_color_grey));

//                    activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                    activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.black));

                    activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.green));
                    activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.white));
                }
                takePrintEnableHandled(isAlltagBox);

            }
        }
    }


    ScanQrCodeDialog scanQrCodeDialog;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    int position;
    String s;
    String fullfillmentId;
    public static int scannedItemPos = 0;

    @Override
    public void onTagBoxClick(String fullfillmentId, int pos) {
        this.fullfillmentId = fullfillmentId;
        this.position = pos;
        this.s = s;
        scannedItemPos = pos;
        this.selectedOmsHeaderListTest = selectedOmsHeaderList;
        BillerOrdersActivity.isBillerActivity = false;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
        Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (data != null && data.getBooleanExtra("IS_BACK_PRESSED", false)) {
            if (!BillerOrdersActivity.isBillerActivity) {
                if (data != null) {
                    List<String> barcodeList = (List<String>) data.getSerializableExtra("BARCODE_LIST");

//                        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
//                            if (i < barcodeList.size()) {
//                                selectedOmsHeaderList.get(i).setScannedBarcode(barcodeList.get(i));
//                            }
//                            selectedOmsHeaderList.get(i).setTagBox(true);
//                            selectedOmsHeaderList.get(i).setScanView(true);
//                        }

                    this.selectedOmsHeaderList = ReadyForPickUpActivity.selectedOmsHeaderListTest;
                    for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                        if (selectedOmsHeaderList.get(i).getScannedBarcode() != null && !selectedOmsHeaderList.get(i).getScannedBarcode().isEmpty()) {
                            if (!selectedOmsHeaderList.get(i).isTagBox()) {
                                taggedCount++;
                                activityReadyForPickupBinding.tagged.setText(String.valueOf(taggedCount));
                                activityReadyForPickupBinding.pending.setText(String.valueOf(selectedOmsHeaderList.size() - taggedCount));
                            }
                            selectedOmsHeaderList.get(i).setTagBox(true);
                            selectedOmsHeaderList.get(i).setScanView(true);
                        } else {
                            selectedOmsHeaderList.get(i).setTagBox(false);
                            selectedOmsHeaderList.get(i).setScanView(false);
                        }
                    }

                    readyForPickUpAdapter.notifyDataSetChanged();
                    boolean isAlltagBox = true;
                    for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)

                        if (!omsHeader.isTagBox())
                            isAlltagBox = false;
                    if (isAlltagBox) {
//                        activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                        activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));

                        activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.yellow));
                        activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.black));

//                        activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                        activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.text_color_grey));

                        activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.light_gray));
                        activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.text_color_grey));

                    } else {
//                        activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                        activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));

                        activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.light_gray));
                        activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.text_color_grey));

//                        activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                        activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.black));

                        activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.green));
                        activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.white));
                    }
                    takePrintEnableHandled(isAlltagBox);
                }
            } else {
                BillerOrdersActivity.isBillerActivity = false;
            }
//            Toast.makeText(this, "scanner back pressed", Toast.LENGTH_SHORT).show();
        }
        if (Result != null && !data.getBooleanExtra("IS_BACK_PRESSED", false)) {
            if (Result.getContents() == null) {

                Toast.makeText(this, "Tagging Failed", Toast.LENGTH_SHORT).show();
            } else {
//                for(int j =0; j<selectedOmsHeaderList.size();j++){
//                    for(int k=0; k<selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().size();k++){
//                        Toast.makeText(this, "FLid:" + selectedOmsHeaderList.get(j).getRefno() + "" + "tagged to Box Number:" + selectedOmsHeaderList.get(j).getGetOMSTransactionResponse().getSalesLine().get(k).getRackId() , Toast.LENGTH_SHORT).show();
//                    }
//
//                }

                if (!BillerOrdersActivity.isBillerActivity) {
                    if (data != null) {
                        List<String> barcodeList = (List<String>) data.getSerializableExtra("BARCODE_LIST");

//                        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
//                            if (i < barcodeList.size()) {
//                                selectedOmsHeaderList.get(i).setScannedBarcode(barcodeList.get(i));
//                            }
//                            selectedOmsHeaderList.get(i).setTagBox(true);
//                             selectedOmsHeaderList.get(i).setScanView(true);
//                        }

                        this.selectedOmsHeaderList = ReadyForPickUpActivity.selectedOmsHeaderListTest;
                        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                            if (selectedOmsHeaderList.get(i).getScannedBarcode() != null && !selectedOmsHeaderList.get(i).getScannedBarcode().isEmpty()) {
                                if (!selectedOmsHeaderList.get(i).isTagBox()) {
                                    taggedCount++;
                                    activityReadyForPickupBinding.tagged.setText(String.valueOf(taggedCount));
                                    activityReadyForPickupBinding.pending.setText(String.valueOf(selectedOmsHeaderList.size() - taggedCount));
                                }
                                selectedOmsHeaderList.get(i).setTagBox(true);
                                selectedOmsHeaderList.get(i).setScanView(true);
                            } else {
                                selectedOmsHeaderList.get(i).setTagBox(false);
                                selectedOmsHeaderList.get(i).setScanView(false);
                            }
                        }

                        readyForPickUpAdapter.notifyDataSetChanged();
                        boolean isAlltagBox = true;
                        for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)

                            if (!omsHeader.isTagBox())
                                isAlltagBox = false;
                        if (isAlltagBox) {
//                            activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                            activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));

                            activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.yellow));
                            activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.black));

//                            activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                            activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.text_color_grey));

                            activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.light_gray));
                            activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.text_color_grey));
                        } else {
//                            activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                            activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));

                            activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.light_gray));
                            activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.text_color_grey));

//                            activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                            activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.black));

                            activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.green));
                            activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.white));
                        }
                        takePrintEnableHandled(isAlltagBox);
                    }
                } else {
                    BillerOrdersActivity.isBillerActivity = false;
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDeleteClick(int pos, String fullfillmentId, String s) {
        UnTagQrCodeDialog unTagQrCodeDialog = new UnTagQrCodeDialog(ReadyForPickUpActivity.this, fullfillmentId);
        unTagQrCodeDialog.setPositiveListener(v -> {
            unTagQrCodeDialog.dismiss();
            selectedOmsHeaderList.get(pos).setTagBox(false);
            selectedOmsHeaderList.get(pos).setScanView(false);
            selectedOmsHeaderList.get(pos).setScannedBarcode("");
            readyForPickUpAdapter.notifyDataSetChanged();
            boolean isAlltagBox = true;
            taggedCount--;
            activityReadyForPickupBinding.tagged.setText(String.valueOf(taggedCount));
            activityReadyForPickupBinding.pending.setText(String.valueOf(selectedOmsHeaderList.size() - taggedCount));
            for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)
                if (!omsHeader.isTagBox())
                    isAlltagBox = false;
            if (isAlltagBox) {
//                activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
//
//                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.text_color_grey));

                activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.yellow));
                activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.black));

                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.light_gray));
                activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.text_color_grey));


            } else {
//                activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
//
//                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.black));

                activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.light_gray));
                activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.text_color_grey));

                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.green));
                activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.white));

            }
            takePrintEnableHandled(isAlltagBox);
        });
        unTagQrCodeDialog.setNegativeListener(v -> unTagQrCodeDialog.dismiss());
        unTagQrCodeDialog.show();
    }

    @Override
    public void onClickStartPickup() {
//        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
//            Dialog dialogView = new Dialog(this, R.style.Theme_AppCompat_DayNight_NoActionBar);
//            DialogConnectPrinterBinding connectPrinterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_connect_printer, null, false);
//            dialogView.setContentView(connectPrinterBinding.getRoot());
//            dialogView.setCancelable(false);
//            connectPrinterBinding.dialogButtonOK.setOnClickListener(view -> {
//                dialogView.dismiss();
//                startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
//                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//
//            });
//            connectPrinterBinding.dialogButtonNO.setOnClickListener(view -> dialogView.dismiss());
//            connectPrinterBinding.dialogButtonNot.setOnClickListener(view -> dialogView.dismiss());
//            dialogView.show();
//
//            //Toast.makeText(getContext(), "Please connect Bluetooth first", Toast.LENGTH_SHORT).show();
//            // startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
//            // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            // return;
//        } else {
        boolean isAlltagBox = true;
        for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)
            if (!omsHeader.isTagBox())
                isAlltagBox = false;
        if (isAlltagBox) {
            mPresenter.mposPickPackOrderReservationApiCall(1, selectedOmsHeaderList);
////            startActivity(PickupProcessActivity.getStartActivity(this, selectedOmsHeaderList));
////            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        } else {
//            Toast.makeText(this, "Tag All boxes", Toast.LENGTH_SHORT).show();
        }
//        }
//    }
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void cancel() {
        onBackPressed();

    }


    @Override
    public void onClickTakePrint(TransactionHeaderResponse.OMSHeader omsHeader) {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Dialog dialogView = new Dialog(this);// R.style.Theme_AppCompat_DayNight_NoActionBar
            DialogConnectPrinterBinding connectPrinterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_connect_printer, null, false);
            dialogView.setContentView(connectPrinterBinding.getRoot());
            dialogView.setCancelable(false);
            dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            connectPrinterBinding.dialogButtonOK.setOnClickListener(view -> {
                dialogView.dismiss();
                startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            });
            connectPrinterBinding.dialogButtonNO.setOnClickListener(view -> dialogView.dismiss());
//            connectPrinterBinding.dialogButtonNot.setOnClickListener(view -> dialogView.dismiss());
            dialogView.show();

            //Toast.makeText(getContext(), "Please connect Bluetooth first", Toast.LENGTH_SHORT).show();
            // startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
            // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            // return;
        } else {
            if (!omsHeader.isTagBox())
                generatebarcode(omsHeader.getRefno());
        }
//        Dialog takePrintDialog = new Dialog(this);
//        DialogTakePrintPBinding takePrintBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_take_print_p, null, false);
//        takePrintDialog.setContentView(takePrintBinding.getRoot());
//        takePrintDialog.setCancelable(false);
//        takePrintBinding.no.setOnClickListener(view -> {
//            takePrintDialog.dismiss();
//        });
//        takePrintBinding.yes.setOnClickListener(view -> {
//            takePrintDialog.dismiss();
//            Dialog printerDeviceListDialog = new Dialog(this);
//            DialogPrinterDevicesPBinding printerDevicesBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_printer_devices_p, null, false);
//            printerDeviceListDialog.setContentView(printerDevicesBinding.getRoot());
//            printerDeviceListDialog.setCancelable(false);
//            PrinterDeviceListAdapter printerDeviceListAdapter = new PrinterDeviceListAdapter(ReadyForPickUpActivity.this, printerDeviceList);
//            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//            printerDevicesBinding.printerDevicesList.setLayoutManager(mLayoutManager1);
//            printerDevicesBinding.printerDevicesList.setItemAnimator(new DefaultItemAnimator());
//            printerDevicesBinding.printerDevicesList.setAdapter(printerDeviceListAdapter);
//
//            printerDevicesBinding.printerDevicesListDialogClose.setOnClickListener(view1 -> printerDeviceListDialog.dismiss());
//            printerDeviceListDialog.show();
//        });
//        takePrintDialog.show();
    }

    @Override
    public void onClickStartPickingWithoutQrCode() {
////        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
////            Dialog dialogView = new Dialog(this, R.style.Theme_AppCompat_DayNight_NoActionBar);
////            DialogConnectPrinterBinding connectPrinterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_connect_printer, null, false);
////            dialogView.setContentView(connectPrinterBinding.getRoot());
////            dialogView.setCancelable(false);
////            connectPrinterBinding.dialogButtonOK.setOnClickListener(view -> {
////                dialogView.dismiss();
////                startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
////                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
////
////            });
////            connectPrinterBinding.dialogButtonNO.setOnClickListener(view -> dialogView.dismiss());
////            connectPrinterBinding.dialogButtonNot.setOnClickListener(view -> dialogView.dismiss());
////            dialogView.show();
//
//            //Toast.makeText(getContext(), "Please connect Bluetooth first", Toast.LENGTH_SHORT).show();
////             startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
////             overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            // return;
////        } else {


        boolean isAlltagBox = true;
        for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)
            if (!omsHeader.isTagBox())
                isAlltagBox = false;
        if (!isAlltagBox) {
            mPresenter.mposPickPackOrderReservationApiCall(1, selectedOmsHeaderList);
        }


//       startActivity(PickupProcessActivity.getStartActivity(this, selectedOmsHeaderList));
////        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
////        }
    }

    @Override
    public void onClickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onClickPrint() {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Dialog dialogView = new Dialog(this);
            DialogConnectPrinterBinding connectPrinterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_connect_printer, null, false);
            dialogView.setContentView(connectPrinterBinding.getRoot());
            dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogView.setCancelable(false);
            connectPrinterBinding.dialogButtonOK.setOnClickListener(view -> {
                dialogView.dismiss();
                startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            });
            connectPrinterBinding.dialogButtonNO.setOnClickListener(view -> dialogView.dismiss());
            dialogView.show();
        } else {
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                    if (!selectedOmsHeaderList.get(i).isTagBox())
                        generatebarcode(selectedOmsHeaderList.get(i).getRefno());
                }
            }

        }

    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 1 && mposPickPackOrderReservationResponse.getReturnMessage() != null && !mposPickPackOrderReservationResponse.getReturnMessage().equalsIgnoreCase("")) {
            Dialog dialog = new Dialog(this);// R.style.Theme_AppCompat_DayNight_NoActionBar
            DialogBoxidAlertBinding dialogBoxidAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(ReadyForPickUpActivity.this), R.layout.dialog_boxid_alert, null, false);
            dialog.setContentView(dialogBoxidAlertBinding.getRoot());
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogBoxidAlertBinding.dialogButtonNO.setVisibility(View.GONE);
            dialogBoxidAlertBinding.dialogMessage.setText(mposPickPackOrderReservationResponse.getReturnMessage() + "\nplease choose another box id's.");
            dialogBoxidAlertBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
            dialogBoxidAlertBinding.dialogButtonOK.setText("OK");
            dialogBoxidAlertBinding.dialogButtonOK.setOnClickListener(v -> {
                String returnMessage = mposPickPackOrderReservationResponse.getReturnMessage();
                String[] messageList = returnMessage.split(",");
                for (int k = 0; k < messageList.length; k++) {
                    for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                        if (selectedOmsHeaderList.get(i).getScannedBarcode().contains(messageList[k])) {
                            int positionRemove = i;
                            selectedOmsHeaderList.get(positionRemove).setTagBox(false);
                            selectedOmsHeaderList.get(positionRemove).setScanView(false);
                            selectedOmsHeaderList.get(positionRemove).setScannedBarcode("");
                            readyForPickUpAdapter.notifyDataSetChanged();
                            boolean isAlltagBox = true;
                            for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)
                                if (!omsHeader.isTagBox())
                                    isAlltagBox = false;
                            if (isAlltagBox) {
//                                activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                                activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
//
//                                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                                activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.text_color_grey));

                                activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.yellow));
                                activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.black));

                                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.light_gray));
                                activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.text_color_grey));
                            } else {
//                                activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                                activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
//
//                                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                                activityReadyForPickupBinding.startPickingWithoutQrCode.setTextColor(getResources().getColor(R.color.black));

                                activityReadyForPickupBinding.startPicking.setBackgroundColor(getResources().getColor(R.color.light_gray));
                                activityReadyForPickupBinding.startPickingText.setTextColor(getResources().getColor(R.color.text_color_grey));

                                activityReadyForPickupBinding.startPickingWithoutQrCode.setBackgroundColor(getResources().getColor(R.color.green));
                                activityReadyForPickupBinding.startPickingWithoutQrCodeText.setTextColor(getResources().getColor(R.color.white));
                            }
                            takePrintEnableHandled(isAlltagBox);
                        }

                    }

                    dialog.dismiss();
                }
            });
//            dialogBoxidAlertBinding.dialogButtonNot.setOnClickListener(v -> dialog.dismiss());
            dialog.show();

//            ExitInfoDialog dialogView = new ExitInfoDialog(this);
//            dialogView.setTitle("");
//            dialogView.setPositiveLabel("OK");
//            dialogView.setSubtitle(mposPickPackOrderReservationResponse.getReturnMessage() + "\nplease choose another box id's.");
//            dialogView.setPositiveListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String returnMessage = mposPickPackOrderReservationResponse.getReturnMessage();
//                    String[] messageList = returnMessage.split(",");
//                    for (int k = 0; k < messageList.length; k++) {
//                        for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
//                            if (selectedOmsHeaderList.get(i).getScannedBarcode().contains(messageList[k])) {
//                                int positionRemove = i;
//                                selectedOmsHeaderList.get(positionRemove).setTagBox(false);
//                                selectedOmsHeaderList.get(positionRemove).setScanView(false);
//                                selectedOmsHeaderList.get(positionRemove).setScannedBarcode("");
//                                readyForPickUpAdapter.notifyDataSetChanged();
//                                boolean isAlltagBox = true;
//                                for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)
//                                    if (!omsHeader.isTagBox())
//                                        isAlltagBox = false;
//                                if (isAlltagBox) {
//                                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
//                                } else {
//                                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
//                                }
//takePrintEnableHandled(isAlltagBox);
//                            }
//
//                        }
//
//                        dialogView.dismiss();
//                    }
//
//                }
//            });
//            dialogView.show();
        } else if (requestType == 1 && mposPickPackOrderReservationResponse.getReturnMessage().equalsIgnoreCase("")) {
            if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
                if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                    for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList) {
//                        generatebarcode(omsHeader.getRefno());
                    }
                    for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
                        selectedOmsHeaderList.get(i).setPickupReserved(true);
                    }
                    startActivity(PickupProcessActivity.getStartActivity(this, selectedOmsHeaderList));
                    overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                }
            }
        } else if (requestType == 2) {
            doBackPressed();
        }
    }

    public class FullfillmentData {
        private String fullfillmentId;
        private String totalItems;
        private boolean tagBox;
        private String id;
        private boolean scanView;


        public boolean isScanView() {
            return scanView;
        }

        public void setScanView(boolean scanView) {
            this.scanView = scanView;
        }

        public String getFullfillmentId() {
            return fullfillmentId;
        }

        public void setFullfillmentId(String fullfillmentId) {
            this.fullfillmentId = fullfillmentId;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        public boolean isTagBox() {
            return tagBox;
        }

        public void setTagBox(boolean tagBox) {
            this.tagBox = tagBox;
        }

    }

    @Override
    public void onBackPressed() {
//        doBackPressed();
        Dialog dialog = new Dialog(this);//R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(ReadyForPickUpActivity.this), R.layout.dialog_cancel, null, false);
        dialogCancelBinding.dialogMessage.setText("The Changes made will be discarded and you'll be directed to Open Orders Page.\n Do you still want to Continue?");
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialogCancelBinding.wraningIcon.setImageDrawable(getResources().getDrawable(R.drawable.warning_icon));
        dialogCancelBinding.wraningIcon.setVisibility(View.VISIBLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            mPresenter.mposPickPackOrderReservationApiCall(2, selectedOmsHeaderList);
            dialog.dismiss();
        });
    }

    private void doBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
    }

    public void generatebarcode(String refnumber) {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Your printer is disconnected. Please connect to Printer by clicking on Reprint Barcode", Toast.LENGTH_LONG).show();
        } else {
            PrintfTSPLManager instance = PrintfTSPLManager.getInstance(ReadyForPickUpActivity.this);
            instance.clearCanvas();
            instance.initCanvas(90, 23);
            instance.setDirection(0);
            //打印条形码
            //Print barcode
            instance.printBarCode(20, 10, "128", 130, 2, 2, 0, refnumber);
            instance.beginPrintf(1);
        }
        /*MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(refnumber, BarcodeFormat.CODE_128, 242, 71);
            Bitmap bitmap = Bitmap.createBitmap(242, 71, Bitmap.Config.RGB_565);
            for (int i = 0; i < 242; i++) {
                for (int j = 0; j < 71; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }

            String encodedImage = BitMapToString(bitmap);
            Constant.getInstance().global_barcode_str = encodedImage;
            // activityEPrescriptionInfoBinding.imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }*/

    }
}