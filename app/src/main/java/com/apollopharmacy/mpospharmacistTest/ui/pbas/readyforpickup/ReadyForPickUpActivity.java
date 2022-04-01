package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.apollopharmacy.mpospharmacistTest.databinding.DialogPrinterDevicesPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogTakePrintPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.adapter.PrinterDeviceListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.adapter.ReadyForPickUpAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.dialog.ScanQrCodeDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.dialog.UnTagQrCodeDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner.ScannerActivity;
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
    private FullfillmentData fullfillmentData;
    private ReadyForPickUpAdapter readyForPickUpAdapter;
    List<FullfillmentData> fullfillmentDataList;
    public static List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    private String[] printerDeviceList = {"MLP 360", "SPP-L310_050007", "SQP-L210_054037"};

    public static Intent getStartActivity(Context context, List<RacksDataResponse.FullfillmentDetail> racksDataResponse) {
        Intent intent = new Intent(context, ReadyForPickUpActivity.class);
        intent.putExtra("rackDataResponse", (Serializable) racksDataResponse);
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
            racksDataResponse = (List<RacksDataResponse.FullfillmentDetail>) getIntent().getSerializableExtra("rackDataResponse");
        }
        if (racksDataResponse != null && racksDataResponse.size() > 0) {
            fullfillmentDataList = new ArrayList<>();

            for (int i = 0; i < racksDataResponse.size(); i++) {
                fullfillmentData = new FullfillmentData();
                fullfillmentData.setFullfillmentId(racksDataResponse.get(i).getFullfillmentId());
                fullfillmentData.setTotalItems(racksDataResponse.get(i).getTotalItems());
                fullfillmentDataList.add(fullfillmentData);
            }
        }

        readyForPickUpAdapter = new ReadyForPickUpAdapter(this, fullfillmentDataList, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityReadyForPickupBinding.readyForPickupRecycleView.setLayoutManager(mLayoutManager1);
        activityReadyForPickupBinding.readyForPickupRecycleView.setItemAnimator(new DefaultItemAnimator());
        activityReadyForPickupBinding.readyForPickupRecycleView.setAdapter(readyForPickUpAdapter);
    }

    ScanQrCodeDialog scanQrCodeDialog;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    int position;

    @Override
    public void onTagBoxClick(String fullfillmentId, int pos) {
        this.position = pos;
//        scanQrCodeDialog = new ScanQrCodeDialog(ReadyForPickUpActivity.this, fullfillmentId);
//        scanQrCodeDialog.setPositiveListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scanQrCodeDialog.dismiss();
//                fullfillmentDataList.get(pos).setTagBox(true);
//                fullfillmentDataList.get(pos).setScanView(true);
//                readyForPickUpAdapter.notifyDataSetChanged();
//                boolean isAlltagBox = true;
//                for (FullfillmentData fullfillmentData : fullfillmentDataList)
//                    if (!fullfillmentData.isTagBox())
//                        isAlltagBox = false;
//                if (isAlltagBox) {
//                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
//                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
//                } else {
//                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
//                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
//                }
//            }
//        });
//        scanQrCodeDialog.setCameraClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(takePicture, 0);
//
//                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
//            }
//        });
//        scanQrCodeDialog.setNegativeListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scanQrCodeDialog.dismiss();
//            }
//        });
//        scanQrCodeDialog.show();


//        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
//        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
//        intentIntegrator.setBeepEnabled(false);
//        intentIntegrator.setCameraId(0);
//        intentIntegrator.setPrompt("SCAN");
//        intentIntegrator.setBarcodeImageEnabled(false);
//        intentIntegrator.initiateScan();
        BillerOrdersActivity.isBillerActivity = false;
        this.fullfillmentDetailList = racksDataResponse;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE) {
//
//            switch (resultCode) {
//                case RESULT_OK:
//                    if (data != null) {
//                        Bitmap bitmap = data.getParcelableExtra("data");
//                        scanQrCodeDialog.visibilyHandlings();
//                        scanQrCodeDialog.setCameraImage(bitmap);
//                    }
//                    break;
//                case RESULT_CANCELED:
//                    break;
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                if (data != null) {
                    List<String> barcodeList = (List<String>) data.getSerializableExtra("BARCODE_LIST");
                    for (int i = 0; i < fullfillmentDataList.size(); i++) {
                        fullfillmentDataList.get(i).setTagBox(true);
                        fullfillmentDataList.get(i).setScanView(true);
                    }
                    readyForPickUpAdapter.notifyDataSetChanged();
                    boolean isAlltagBox = true;
                    for (FullfillmentData fullfillmentData : fullfillmentDataList)
                        if (!fullfillmentData.isTagBox())
                            isAlltagBox = false;
                    if (isAlltagBox) {
                        activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
                        activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
                        activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onDeleteClick(int pos, String fullfillmentId) {
        UnTagQrCodeDialog unTagQrCodeDialog = new UnTagQrCodeDialog(ReadyForPickUpActivity.this, fullfillmentId);
        unTagQrCodeDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unTagQrCodeDialog.dismiss();
                fullfillmentDataList.get(pos).setTagBox(false);
                fullfillmentDataList.get(pos).setScanView(false);
                readyForPickUpAdapter.notifyDataSetChanged();
                boolean isAlltagBox = true;
                for (FullfillmentData fullfillmentData : fullfillmentDataList)
                    if (!fullfillmentData.isTagBox())
                        isAlltagBox = false;
                if (isAlltagBox) {
                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_signin_ripple_effect));
                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.black));
                } else {
                    activityReadyForPickupBinding.startPicking.setBackground(getResources().getDrawable(R.drawable.btn_ripple_effect_grey));
                    activityReadyForPickupBinding.startPicking.setTextColor(getResources().getColor(R.color.text_color_grey));
                }
            }
        });
        unTagQrCodeDialog.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unTagQrCodeDialog.dismiss();
            }
        });
        unTagQrCodeDialog.show();
    }

    @Override
    public void onClickStartPickup() {
        boolean isAlltagBox = true;
        for (FullfillmentData fullfillmentData : fullfillmentDataList)
            if (!fullfillmentData.isTagBox())
                isAlltagBox = false;
        if (isAlltagBox) {
            startActivity(PickupProcessActivity.getStartActivity(this, racksDataResponse));
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
        startActivity(PickupProcessActivity.getStartActivity(this, racksDataResponse));
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onClickScanCode() {
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    public class FullfillmentData {
        private String fullfillmentId;
        private String totalItems;
        private boolean tagBox;
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
