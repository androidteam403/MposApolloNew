package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;

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
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogPrinterDevicesPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogTakePrintPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.adapter.PrinterDeviceListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.adapter.ReadyForPickUpAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.dialog.ScanQrCodeDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.dialog.UnTagQrCodeDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReadyForPickUpActivity extends BaseActivity implements ReadyForPickUpMvpView {

    @Inject
    ReadyForPickUpMvpPresenter<ReadyForPickUpMvpView> mPresenter;
    ActivityReadyForPickupPBinding activityReadyForPickupBinding;
    AdapterReadyForPickupPBinding adapterReadyForPickupBinding;
    //    private FullfillmentData fullfillmentData;
    private ReadyForPickUpAdapter readyForPickUpAdapter;
    //    List<FullfillmentData> fullfillmentDataList;
    public static List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderListTest;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    public IntentResult Result;
    public int pos;

    public static String RefId;
    private List<String> barcodeList;
    private List<String> list = new ArrayList<>();
    private String[] printerDeviceList = {"MLP 360", "SPP-L310_050007", "SQP-L210_054037"};


    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;


    public static Intent getStartActivity(Context context, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList) {
        Intent intent = new Intent(context, ReadyForPickUpActivity.class);
        intent.putExtra(CommonUtils.SELECTED_ORDERS_LIST, (Serializable) selectedOmsHeaderList);
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

    @Override
    protected void setUp() {
        activityReadyForPickupBinding.setCallback(mPresenter);

        if (getIntent() != null) {
            selectedOmsHeaderList = (List<TransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra(CommonUtils.SELECTED_ORDERS_LIST);
            if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
                readyForPickUpAdapter = new ReadyForPickUpAdapter(this, selectedOmsHeaderList, this);
                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                activityReadyForPickupBinding.readyForPickupRecycleView.setLayoutManager(mLayoutManager1);

                activityReadyForPickupBinding.readyForPickupRecycleView.setItemAnimator(new DefaultItemAnimator());
                activityReadyForPickupBinding.readyForPickupRecycleView.setAdapter(readyForPickUpAdapter);
            }
        }
    }

    ScanQrCodeDialog scanQrCodeDialog;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    int position;
    String s;
    String fullfillmentId;

    @Override
    public void onTagBoxClick(String fullfillmentId, int pos) {
        this.fullfillmentId = fullfillmentId;
        this.position = pos;
        this.s = s;
        this.selectedOmsHeaderListTest = selectedOmsHeaderList;
        BillerOrdersActivity.isBillerActivity = false;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
        Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();


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
                            activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
                            activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
                        } else {
                            activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
                            activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
                        }
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
            for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)
                if (!omsHeader.isTagBox())
                    isAlltagBox = false;
            if (isAlltagBox) {
                activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
                activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
            } else {
                activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
                activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
            }
        });
        unTagQrCodeDialog.setNegativeListener(v -> unTagQrCodeDialog.dismiss());
        unTagQrCodeDialog.show();
    }

    @Override
    public void onClickStartPickup() {
        boolean isAlltagBox = true;
        for (TransactionHeaderResponse.OMSHeader omsHeader : selectedOmsHeaderList)
            if (!omsHeader.isTagBox())
                isAlltagBox = false;
        if (isAlltagBox) {
            startActivity(PickupProcessActivity.getStartActivity(this, selectedOmsHeaderList));
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        } else {
            Toast.makeText(this, "Tag All boxes", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void cancel() {
        Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_NoActionBar);
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(ReadyForPickUpActivity.this), R.layout.dialog_cancel, null, false);
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogCancelBinding.dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialogCancelBinding.dialogButtonNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onClickTakePrint() {
        Dialog takePrintDialog = new Dialog(this);
        DialogTakePrintPBinding takePrintBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_take_print_p, null, false);
        takePrintDialog.setContentView(takePrintBinding.getRoot());
        takePrintDialog.setCancelable(false);
        takePrintBinding.no.setOnClickListener(view -> {
            takePrintDialog.dismiss();
        });
        takePrintBinding.yes.setOnClickListener(view -> {
            takePrintDialog.dismiss();
            Dialog printerDeviceListDialog = new Dialog(this);
            DialogPrinterDevicesPBinding printerDevicesBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_printer_devices_p, null, false);
            printerDeviceListDialog.setContentView(printerDevicesBinding.getRoot());
            printerDeviceListDialog.setCancelable(false);
            PrinterDeviceListAdapter printerDeviceListAdapter = new PrinterDeviceListAdapter(ReadyForPickUpActivity.this, printerDeviceList);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            printerDevicesBinding.printerDevicesList.setLayoutManager(mLayoutManager1);
            printerDevicesBinding.printerDevicesList.setItemAnimator(new DefaultItemAnimator());
            printerDevicesBinding.printerDevicesList.setAdapter(printerDeviceListAdapter);

            printerDevicesBinding.printerDevicesListDialogClose.setOnClickListener(view1 -> printerDeviceListDialog.dismiss());
            printerDeviceListDialog.show();
        });
        takePrintDialog.show();
    }

    @Override
    public void onClickStartPickingWithoutQrCode() {
        startActivity(PickupProcessActivity.getStartActivity(this, selectedOmsHeaderList));
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onClickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
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
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
    }
}