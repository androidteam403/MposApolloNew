package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickupVerificationPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogConnectPrinterBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineInfoEntity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.adapter.PickUpVerificationAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.dialog.VerificationStatusDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model.OMSOrderForwardResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model.MPOSPickPackOrderReservationResponse;
import com.apollopharmacy.mpospharmacistTest.utils.BluetoothActivity;
import com.printf.manager.BluetoothManager;
import com.printf.manager.PrintfTSPLManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class PickUpVerificationActivity extends BaseActivity implements PickUpVerificationMvpView {

    @Inject
    PickUpVerificationMvpPresenter<PickUpVerificationMvpView> mpresenter;
    private ActivityPickupVerificationPBinding activityPickupVerificationBinding;
    private PickUpVerificationAdapter pickUpVerificationAdapter;
    private boolean updateVerified;
    boolean reverification;
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;
    List<RackAdapter.RackBoxModel.ProductData> productDataList;
    private String status;
    private int position;
    CustomerDataResBean customerDataResBean;
    private GetCustomerResponse.CustomerEntity customerEntity;
    String eprescription_corpcode = "0";
    List<PickPackReservation> globalpickupreservation = new ArrayList<>();

    List<RacksDataResponse.FullfillmentDetail> fullFillModelList;
    List<GetOMSTransactionResponse> customerDataList = new ArrayList<>();

    ArrayList<MedicineInfoEntity> itemsArrayList = new ArrayList<>();
    ArrayList<SalesLineEntity> itemsList = new ArrayList<>();
    RacksDataResponse.FullfillmentDetail fillModel;
    String fId;
    TransactionHeaderResponse.OMSHeader omsHeader;
    private final int ACTIVITY_BARCODESCANNER_DETAILS_CODE = 151;
    private String boxId;
    private int salesLineCount = 0;

    private int isPackingVerified = 0;

    public static Intent getStartActivity(Context context, TransactionHeaderResponse.OMSHeader omsHeader) {
        Intent intent = new Intent(context, PickUpVerificationActivity.class);
        intent.putExtra("OMS_HEADER", (Serializable) omsHeader);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickupVerificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_pickup_verification_p);
        getActivityComponent().inject(this);
        mpresenter.onAttach(PickUpVerificationActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {

        activityPickupVerificationBinding.setCallback(mpresenter);

        if (getIntent() != null) {
            omsHeader = (TransactionHeaderResponse.OMSHeader) getIntent().getSerializableExtra("OMS_HEADER");

            if (omsHeader != null) {
                if (omsHeader.getPickPackUser() != null && !omsHeader.getPickPackUser().isEmpty()) {
                    activityPickupVerificationBinding.pickPackUser.setText(omsHeader.getPickPackUser());
                } else {
                    activityPickupVerificationBinding.pickPackUser.setText("-");
                }
                if (omsHeader.getOverallOrderStatus() != null && omsHeader.getOverallOrderStatus().length() > 2) {
                    this.boxId = omsHeader.getOverallOrderStatus().substring(2);
                    activityPickupVerificationBinding.boxId.setText(boxId);
//                    activityPickupVerificationBinding.boxId.setText(boxId.substring(boxId.length() - 5));
                } else {
                    this.boxId = "-";
                }
                assert omsHeader.getOverallOrderStatus() != null;
                if (omsHeader.getOverallOrderStatus().substring(0, 1).equalsIgnoreCase("1")) {
                    activityPickupVerificationBinding.statusText.setText("FULL");
                    activityPickupVerificationBinding.fullStatusColor.setRotation(0);
                    activityPickupVerificationBinding.fullStatusColor.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_tick));
                } else if (omsHeader.getOverallOrderStatus().substring(0, 1).equalsIgnoreCase("2")) {
                    activityPickupVerificationBinding.statusText.setText("PARTIAL");
                    activityPickupVerificationBinding.fullStatusColor.setRotation(90);
                    activityPickupVerificationBinding.fullStatusColor.setImageDrawable(getResources().getDrawable(R.drawable.partialcirculargreeenorange));
                } else if (omsHeader.getOverallOrderStatus().substring(0, 1).equalsIgnoreCase("3")) {
                    activityPickupVerificationBinding.statusText.setText("NOT AVAILABLE");
                    activityPickupVerificationBinding.fullStatusColor.setRotation(0);
                    activityPickupVerificationBinding.fullStatusColor.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_available));
                }
                mpresenter.mposPickPackOrderReservationApiCall(3, omsHeader);
//                mpresenter.fetchOMSCustomerInfo(omsHeader.getRefno());
            }
        }
//        Toast.makeText(getApplicationContext(), omsHeader.getOverallOrderStatus(), Toast.LENGTH_SHORT).show();
//        omsHeader.setOverallOrderStatus("3");

        if (omsHeader.getOverallOrderStatus().equalsIgnoreCase("2")) {

//                activityPickupVerificationBinding.parent.setBackgroundColor(Color.TRANSPARENT);
            activityPickupVerificationBinding.parent.setClickable(false);
            activityPickupVerificationBinding.parent.setBackgroundColor(getResources().getColor(R.color.white));
            activityPickupVerificationBinding.parent.setAlpha((float) 0.4);
            activityPickupVerificationBinding.buttonParent.setAlpha((float) 0.4);
            activityPickupVerificationBinding.warningText.setVisibility(View.VISIBLE);
        } else if (omsHeader.getOverallOrderStatus().equalsIgnoreCase("3")) {
            activityPickupVerificationBinding.parent.setClickable(false);
            activityPickupVerificationBinding.parent.setBackgroundColor(getResources().getColor(R.color.white));
            activityPickupVerificationBinding.parent.setAlpha((float) 0.4);
            activityPickupVerificationBinding.buttonParent.setAlpha((float) 0.4);
            activityPickupVerificationBinding.warningTextNotAvailable.setVisibility(View.VISIBLE);
        }


        activityPickupVerificationBinding.backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    int flag;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemClick(int position, RackAdapter.RackBoxModel.ProductData pickPackProductsData) {
//        Dialog dialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
//
//        DialogCustomUpdateStatusPBinding dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
//                R.layout.dialog_custom_update_status_p, null, false);
//        dialog.setContentView(dialogUpdateStatusBinding.getRoot());
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        if (pickPackProductsData != null) {
//            dialogUpdateStatusBinding.title.setText(pickPackProductsData.getProductName());
//            dialogUpdateStatusBinding.qty.setText(pickPackProductsData.getCapturedQuantity());
    }
//        dialogUpdateStatusBinding.id.setText(fillModel.getFullfillmentId());
//        dialogUpdateStatusBinding.boxId.setText(fillModel.getBoxId());
//        dialogUpdateStatusBinding.fullStatusColorLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!dialogUpdateStatusBinding.fullStatusColor.isChecked()) {
//                    dialogUpdateStatusBinding.fullStatusColor.setChecked(true);
//                    dialogUpdateStatusBinding.fullStatusColor.setButtonTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.black)));
//                    dialogUpdateStatusBinding.partialStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(false);
//                    pickPackProductsData.setPackerStatus("Full");
//                }
//            }
//        });

//        dialogUpdateStatusBinding.fullStatusColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogUpdateStatusBinding.fullStatusColor.isChecked()) {
//                    dialogUpdateStatusBinding.fullStatusColor.setChecked(true);
//                    dialogUpdateStatusBinding.fullStatusColor.setButtonTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.black)));
//                    dialogUpdateStatusBinding.partialStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(false);
//                    pickPackProductsData.setPackerStatus("Full");
//                }
//            }
//        });

//        dialogUpdateStatusBinding.partialStatusColorLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!dialogUpdateStatusBinding.partialStatusColor.isChecked()) {
//                    dialogUpdateStatusBinding.fullStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.partialStatusColor.setChecked(true);
//                    dialogUpdateStatusBinding.partialStatusColor.setButtonTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.black)));
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(false);
//                    pickPackProductsData.setPackerStatus("Partial");
//                }
//            }
//        });

//        dialogUpdateStatusBinding.partialStatusColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogUpdateStatusBinding.partialStatusColor.isChecked()) {
//                    dialogUpdateStatusBinding.fullStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.partialStatusColor.setChecked(true);
//                    dialogUpdateStatusBinding.partialStatusColor.setButtonTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.black)));
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(false);
//                    pickPackProductsData.setPackerStatus("Partial");
//                }
//            }
//        });
//        dialogUpdateStatusBinding.notAvailableStatusColorLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!dialogUpdateStatusBinding.notAvailableStatusColor.isChecked()) {
//                    dialogUpdateStatusBinding.fullStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.partialStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(true);
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setButtonTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.black)));
//                    pickPackProductsData.setPackerStatus("Not Available");
//                }
//            }
//        });

//        dialogUpdateStatusBinding.notAvailableStatusColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogUpdateStatusBinding.notAvailableStatusColor.isChecked()) {
//                    dialogUpdateStatusBinding.fullStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.partialStatusColor.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(true);
//                    dialogUpdateStatusBinding.notAvailableStatusColor.setButtonTintList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.black)));
//                    pickPackProductsData.setPackerStatus("Not Available");
//                }
//            }
//        });

//        dialogUpdateStatusBinding.dialogButtonOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogUpdateStatusBinding.fullStatusColor.isChecked() || dialogUpdateStatusBinding.partialStatusColor.isChecked() || dialogUpdateStatusBinding.notAvailableStatusColor.isChecked()) {
//                    activityPickupVerificationBinding.sendReVer.setBackgroundResource(R.color.red);
//                    activityPickupVerificationBinding.sendReVer.setTextColor(getResources().getColor(R.color.white));
//                    activityPickupVerificationBinding.pickVerified.setBackgroundResource(R.color.yellow);
//                    activityPickupVerificationBinding.pickVerified.setTextColor(getResources().getColor(R.color.black));
//                    updateVerified = true;
////                pickUpVerificationAdapter.notifyDataSetChanged();
//                    productDataList.get(position).setPackerStatus(pickPackProductsData.getPackerStatus());
//                    mpresenter.fetchOMSCustomerInfo(omsList.get(position).getREFNO());
//                    pickUpVerificationAdapter = new PickUpVerificationAdapter(PickUpVerificationActivity.this, customerDataList.get(0).getSalesLine(), PickUpVerificationActivity.this);
//                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(PickUpVerificationActivity.this, LinearLayoutManager.VERTICAL, false);
//                    activityPickupVerificationBinding.recyclerView.setLayoutManager(mLayoutManager1);
//                    activityPickupVerificationBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    activityPickupVerificationBinding.recyclerView.setAdapter(pickUpVerificationAdapter);
//                    dialog.dismiss();
//                } else {
//                    Toast.makeText(PickUpVerificationActivity.this, "Please Update status", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        dialogUpdateStatusBinding.dialogButtonNO.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

    @SuppressLint("Range")
    @Override
    public void onPartialWarningYesClick() {
//        partialConfirmationClickable = true;
//        activityPickupVerificationBinding.partialStatusColor.setVisibility(View.VISIBLE);
        activityPickupVerificationBinding.warningText.setVisibility(View.GONE);
        activityPickupVerificationBinding.warningTextNotAvailable.setVisibility(View.GONE);
        activityPickupVerificationBinding.parent.setBackgroundColor(0);
        activityPickupVerificationBinding.buttonParent.setBackgroundColor(0);
        activityPickupVerificationBinding.parent.setAlpha(4);
        activityPickupVerificationBinding.buttonParent.setAlpha(4);
        activityPickupVerificationBinding.parent.setClickable(true);
    }

    @Override
    public void onPartialWarningNoClick() {
        finish();
        overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClickReVerificatio() {
        VerificationStatusDialog verificationStatusDialog = new VerificationStatusDialog(PickUpVerificationActivity.this, true, omsHeader.getRefno());
        verificationStatusDialog.setPositiveListener(v -> {
            verificationStatusDialog.dismiss();
            mposOrderUpdate("2");
        });
        verificationStatusDialog.setNegativeListener(v -> verificationStatusDialog.dismiss());

        verificationStatusDialog.show();

    }

    @Override
    public void onClickUpdate(int pos, String refNo) {
        int full = 0;
        int partial = 0;
        int notavailable = 0;

//        for (int i = 0; i < customerDataList.size(); i++) {
//            if (customerDataList.get(i).getSalesLine().get(i).getPackerStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//                full++;
//            } else if (customerDataList.get(i).getSalesLine().get(i).getPackerStatus().equalsIgnoreCase("Partial AVAILABLE")) {
//                partial++;
//            } else if (customerDataList.get(i).getSalesLine().get(i).getPackerStatus().equalsIgnoreCase("Not AVAILABLE")) {
//                notavailable++;
//            }
//        }
//        if (full > partial && full > notavailable) {
//            activityPickupVerificationBinding.fullStatusColor.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_circle_tick));
//
//        } else if (partial > full && partial > notavailable) {
//            activityPickupVerificationBinding.fullStatusColor.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_partial));
//
//        } else if (notavailable > full && notavailable > partial) {
//            activityPickupVerificationBinding.fullStatusColor.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_not_available));
//
//        }

    }


//       Dialog statusUpdateDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
//        dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_update_status_p, null, false);
//        statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
//        statusUpdateDialog.setCancelable(false);
//        dialogUpdateStatusBinding.fullfillmentId.setText(customerDataList.get(pos).getREFNO());
//        dialogUpdateStatusBinding.productName.setText(customerDataList.get(pos).getSalesLine().get(pos).getItemName());
//
//dialogUpdateStatusBinding.fullPickedRadio.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
//        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//    }
//});
//
//dialogUpdateStatusBinding.partiallyPickedRadio.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
//        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//    }
//});
//
//dialogUpdateStatusBinding.notAvailableRadio.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//        dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);
//    }
//});
//dialogUpdateStatusBinding.update.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        statusUpdateDialog.dismiss();
//
//    }
//});
//
//        dialogUpdateStatusBinding.dismissDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                statusUpdateDialog.dismiss();
//            }
//        });
//        statusUpdateDialog.show();

//        dialogUpdateStatusBinding.fullPickedRadio.setClickable(true);
//        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//        dialogUpdateStatusBinding.notAvailableRadio.setClickable(false);
//        dialogUpdateStatusBinding.partiallyPickedRadio.setClickable(false);


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClickVerification() {
        VerificationStatusDialog verificationStatusDialog = new VerificationStatusDialog(PickUpVerificationActivity.this, false, omsHeader.getRefno());
        verificationStatusDialog.setPositiveListener(v -> {
            verificationStatusDialog.dismiss();
            mposOrderUpdate("3");
        });
        verificationStatusDialog.setNegativeListener(v -> verificationStatusDialog.dismiss());
        verificationStatusDialog.show();
    }

    private void mposOrderUpdate(String requestType) {
        OMSOrderForwardRequest omsOrderForwardRequest = new OMSOrderForwardRequest();
        omsOrderForwardRequest.setRequestType(requestType);
        omsOrderForwardRequest.setFulfillmentID(omsHeader.getRefno());
        List<OMSOrderForwardRequest.ReservedSalesLine> reservedSalesLineArrayList = new ArrayList<>();


        if (omsOrderForwardRequest.getFulfillmentID().equalsIgnoreCase(omsHeader.getRefno())) {
            for (int k = 0; k < omsHeader.getGetOMSTransactionResponse().getSalesLine().size(); k++) {
                for (int m = 0; m < omsHeader.getGetOMSTransactionResponse().getPickPackReservation().size(); m++) {
                    if (omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getItemId().equals(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupItemId())) {
                        OMSOrderForwardRequest.ReservedSalesLine reservedSalesLine = new OMSOrderForwardRequest.ReservedSalesLine();
                        reservedSalesLine.setAdditionaltax(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getAdditionaltax());
                        reservedSalesLine.setApplyDiscount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getApplyDiscount());
                        reservedSalesLine.setBarcode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getBarcode());
                        reservedSalesLine.setBaseAmount(Double.valueOf(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getBaseAmount()));
                        reservedSalesLine.setCESSPerc(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getCESSPerc());
                        reservedSalesLine.setCESSTaxCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getCESSTaxCode());
                        reservedSalesLine.setCGSTPerc(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getCGSTPerc());
                        reservedSalesLine.setCGSTTaxCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getCGSTTaxCode());
                        reservedSalesLine.setCategory(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getCategory());
                        reservedSalesLine.setCategoryCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryCode());
                        reservedSalesLine.setCategoryReference(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getCategoryReference());
                        reservedSalesLine.setComment(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getComment());
                        reservedSalesLine.setDpco(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDpco());
                        reservedSalesLine.setDiscAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
                        reservedSalesLine.setDiscOfferId(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscOfferId());
                        reservedSalesLine.setDiscountStructureType(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountStructureType());
                        reservedSalesLine.setDiscountType(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
                        reservedSalesLine.setDiseaseType(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDiseaseType());
                        reservedSalesLine.setExpiry(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getExpiry());
                        reservedSalesLine.setHsncodeIn(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getHsncodeIn());
                        reservedSalesLine.setIGSTPerc(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTPerc());
                        reservedSalesLine.setIGSTTaxCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getIGSTTaxCode());
                        reservedSalesLine.setISPrescribed(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getISPrescribed());
                        reservedSalesLine.setISReserved(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getISReserved());
                        reservedSalesLine.setISStockAvailable(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getISStockAvailable());
                        reservedSalesLine.setInventBatchId(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupInventBatchId());
                        reservedSalesLine.setIsChecked(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getIsChecked());
                        reservedSalesLine.setIsGeneric(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getIsGeneric());
                        reservedSalesLine.setIsPriceOverride(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getIsPriceOverride());
                        reservedSalesLine.setIsSubsitute(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getIsSubsitute());
                        reservedSalesLine.setIsVoid(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getIsVoid());
                        reservedSalesLine.setItemId(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getItemId());
                        reservedSalesLine.setItemName(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getItemName());
                        reservedSalesLine.setLineDiscPercentage(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
                        reservedSalesLine.setLineDiscPercentage(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getLineDiscPercentage());
                        reservedSalesLine.setLineManualDiscountAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountAmount());
                        reservedSalesLine.setLineManualDiscountPercentage(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getLineManualDiscountPercentage());
                        reservedSalesLine.setLineNo(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getLineNo());
                        reservedSalesLine.setLinedscAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getLinedscAmount());
                        reservedSalesLine.setMMGroupId(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getMMGroupId());
                        reservedSalesLine.setMrp(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPrice());
                        reservedSalesLine.setManufacturerCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerCode());
                        reservedSalesLine.setManufacturerName(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getManufacturerName());
                        reservedSalesLine.setMixMode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getMixMode());
                        reservedSalesLine.setModifyBatchId(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getModifyBatchId());
                        reservedSalesLine.setNetAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmount());
                        reservedSalesLine.setNetAmountInclTax(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getNetAmountInclTax());
                        reservedSalesLine.setOfferAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferAmount());
                        reservedSalesLine.setDiscountType(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscountType());
                        reservedSalesLine.setOfferDiscountValue(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferDiscountValue());
                        reservedSalesLine.setOfferQty(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferQty());
                        reservedSalesLine.setOfferType(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getOfferType());
                        reservedSalesLine.setOmsLineRECID(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getOmsLineRECID());
                        reservedSalesLine.setOrderStatus(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getOrderStatus());
                        reservedSalesLine.setOriginalPrice(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getOriginalPrice());
                        reservedSalesLine.setPeriodicDiscAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getPeriodicDiscAmount());
                        reservedSalesLine.setPhysicalMRP(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getPhysicalMRP());
                        reservedSalesLine.setPreviewText(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getPreviewText());
                        reservedSalesLine.setPrice(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPrice());
                        reservedSalesLine.setProductRecID(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getProductRecID());
//                    String reqQtyDoubleDataFormat = String.valueOf(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getGetBatchInfoRes().getBatchList().get(l).getREQQTY());
//                    int reqQty = 0;
//                    if (reqQtyDoubleDataFormat.contains(".")) {
//                        reqQty = Integer.parseInt(reqQtyDoubleDataFormat.substring(0, reqQtyDoubleDataFormat.indexOf(".")));
//                    }
                        reservedSalesLine.setQty(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupQty());
                        reservedSalesLine.setRemainderDays(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getRemainderDays());
                        reservedSalesLine.setRemainingQty(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getRemainingQty());
                        reservedSalesLine.setResqtyflag(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getResqtyflag());
                        reservedSalesLine.setRetailCategoryRecID(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getRetailCategoryRecID());
                        reservedSalesLine.setRetailMainCategoryRecID(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getRetailMainCategoryRecID());
                        reservedSalesLine.setRetailSubCategoryRecID(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getRetailSubCategoryRecID());
                        reservedSalesLine.setReturnQty(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getReturnQty());
                        reservedSalesLine.setSGSTPerc(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getSGSTPerc());
                        reservedSalesLine.setSGSTTaxCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getSGSTTaxCode());
                        reservedSalesLine.setScheduleCategory(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategory());
                        reservedSalesLine.setScheduleCategoryCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getScheduleCategoryCode());
                        reservedSalesLine.setStockQty(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getStockQty());
                        reservedSalesLine.setSubCategory(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategory());
                        reservedSalesLine.setSubCategoryCode(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getSubCategoryCode());
                        reservedSalesLine.setSubClassification(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getSubClassification());
                        reservedSalesLine.setSubClassification(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getSubClassification());
                        reservedSalesLine.setSubstitudeItemId(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getSubstitudeItemId());
                        reservedSalesLine.setTax(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getTax());
                        reservedSalesLine.setTaxAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getTaxAmount());
                        reservedSalesLine.setTotal(Double.valueOf(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getTotal()));
                        reservedSalesLine.setTotalDiscAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getDiscAmount());
                        reservedSalesLine.setTotalDiscPct(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getTotalDiscPct());
                        reservedSalesLine.setTotalRoundedAmount(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getTotalRoundedAmount());
                        reservedSalesLine.setTotalTax(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getTotalTax());
                        reservedSalesLine.setUnit(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getUnit());
                        reservedSalesLine.setUnitPrice(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPrice());
                        reservedSalesLine.setUnitQty(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(m).getPickupQty());
                        reservedSalesLine.setVariantId(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).getVariantId());
                        reservedSalesLine.setIsReturnClick(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).isReturnClick());
                        reservedSalesLine.setIsSelectedReturnItem(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(k).isSelectedReturnItem());
                        reservedSalesLineArrayList.add(reservedSalesLine);
                    }
                }
            }
        }
        omsOrderForwardRequest.setReservedSalesLine(reservedSalesLineArrayList);
        mpresenter.UpdateOmsOrder(omsOrderForwardRequest);
    }

    @Override
    public void onSuccessGetOMSTransaction(List<GetOMSTransactionResponse> getOMSTransactionResponses) {

        if (getOMSTransactionResponses != null && getOMSTransactionResponses.get(0) != null && getOMSTransactionResponses.get(0).getPickPackReservation() != null && getOMSTransactionResponses.get(0).getPickPackReservation().size() > 0) {


            if (getOMSTransactionResponses != null && getOMSTransactionResponses.size() > 0) {

                omsHeader.setGetOMSTransactionResponse(getOMSTransactionResponses.get(0));
                if (omsHeader.getGetOMSTransactionResponse().getPickPackReservation() != null)
                    for (int i = 0; i < omsHeader.getGetOMSTransactionResponse().getPickPackReservation().size(); i++) {
                        for (int j = 0; j < omsHeader.getGetOMSTransactionResponse().getSalesLine().size(); j++) {
                            if (omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(i).getPickupItemId().equals(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(j).getItemId())) {
                                if (omsHeader.getGetOMSTransactionResponse().getSalesLine().get(j).getPickedQty() != null)
                                    omsHeader.getGetOMSTransactionResponse().getSalesLine().get(j).setPickedQty(String.valueOf(Integer.parseInt(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(j).getPickedQty()) + omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(i).getPickupQty()));
                                else
                                    omsHeader.getGetOMSTransactionResponse().getSalesLine().get(j).setPickedQty(String.valueOf(omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(i).getPickupQty()));
                            }
                        }
                    }

                for (int i = 0; i < omsHeader.getGetOMSTransactionResponse().getSalesLine().size(); i++) {
                    int pickedQty = 0, qty = 0;
                    if (omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPickedQty() != null && !omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPickedQty().isEmpty()) {
                        if (Integer.parseInt(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPickedQty()) >= omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getQty()) {
                            omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).setPickerStatus("FULL");
                        } else if (Integer.parseInt(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPickedQty()) == 0) {
                            omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).setPickerStatus("NOT AVAILABLE");
                        } else if (Integer.parseInt(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPickedQty()) < omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getQty()) {
                            omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).setPickerStatus("PARTIAL");
                        }
                    } else {
                        omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).setPickerStatus("NOT AVAILABLE");
                    }
                }


                activityPickupVerificationBinding.fullfilmentId.setText(getOMSTransactionResponses.get(0).getRefno());
                activityPickupVerificationBinding.orderId.setText(getOMSTransactionResponses.get(0).getReciptId());
                activityPickupVerificationBinding.date.setText(getOMSTransactionResponses.get(0).getDeliveryDate());
                if (omsHeader.getGetOMSTransactionResponse().getSalesLine() != null && omsHeader.getGetOMSTransactionResponse().getSalesLine().size() > 0) {
                    for (int i = 0; i < omsHeader.getGetOMSTransactionResponse().getSalesLine().size(); i++) {
                        if (omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getItemId().equals("ESH0002")) {
                            int qty = 0;
                            for (int j = 0; j < omsHeader.getGetOMSTransactionResponse().getPickPackReservation().size(); j++) {
                                if (omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(j).getPickupItemId().equals("ESH0002")) {
                                    qty = qty + omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(j).getPickupQty();
                                }
                            }
                            if (qty >= omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getQty()) {
                                omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).setPackerStatus("FULL");
                            } else if (qty < omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getQty()) {
                                omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).setPackerStatus("PARTIAL");
                            } else if (qty <= 0) {
                                omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).setPackerStatus("NOT AVAILABLE");
                            }

                        }
                    }
                }
                reSendVerPickVernEableChecked();
                pickUpVerificationAdapter = new PickUpVerificationAdapter(this, omsHeader.getGetOMSTransactionResponse().getSalesLine(), this, selectedBatchList, omsHeader.getGetOMSTransactionResponse());
                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                activityPickupVerificationBinding.recyclerView.setLayoutManager(mLayoutManager1);
                activityPickupVerificationBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                activityPickupVerificationBinding.recyclerView.setAdapter(pickUpVerificationAdapter);

//                if (omsHeader.getGetOMSTransactionResponse().getSalesLine() != null && omsHeader.getGetOMSTransactionResponse().getSalesLine().size() > 0) {
//                    showLoading();
//                    mpresenter.getBatchDetailsApi(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(salesLineCount).getItemId());
//                }
            }
        } else {
            Toast.makeText(this, "Pick Pack Reservation is null", Toast.LENGTH_SHORT).show();
            omsHeader.setGetOMSTransactionResponse(getOMSTransactionResponses.get(0));
            mpresenter.mposPickPackOrderReservationApiCall(4, omsHeader);
        }
//        customerDataList = getOMSTransactionResponses;
//
//
//        for (int i = 0; i < customerDataList.size(); i++) {
//            if (omsList.get(position).getRefno().equals(customerDataList.get(i).getRefno())) {
//
//                activityPickupVerificationBinding.fullfilmentId.setText(customerDataList.get(i).getRefno());
//                activityPickupVerificationBinding.orderId.setText(customerDataList.get(i).getReciptId());
//                activityPickupVerificationBinding.date.setText(customerDataList.get(i).getDeliveryDate());
//                pickUpVerificationAdapter = new PickUpVerificationAdapter(this, PickUpVerificationActivity.this, customerDataList, this);
//                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//                activityPickupVerificationBinding.recyclerView.setLayoutManager(mLayoutManager1);
//                activityPickupVerificationBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//                activityPickupVerificationBinding.recyclerView.setAdapter(pickUpVerificationAdapter);
//
//            } else if (fId.equals(customerDataList.get(i).getRefno())) {
//                activityPickupVerificationBinding.fullfilmentId.setText(customerDataList.get(i).getRefno());
//                activityPickupVerificationBinding.orderId.setText(customerDataList.get(i).getReciptId());
//                activityPickupVerificationBinding.date.setText(customerDataList.get(i).getDeliveryDate());
//                pickUpVerificationAdapter = new PickUpVerificationAdapter(this, PickUpVerificationActivity.this, customerDataList, this);
//                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//                activityPickupVerificationBinding.recyclerView.setLayoutManager(mLayoutManager1);
//                activityPickupVerificationBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//                activityPickupVerificationBinding.recyclerView.setAdapter(pickUpVerificationAdapter);
//            }
//        }
//    }

//        if (response != null && response.size() > 0) {
//            if (response.get(0) != null && response.size() > 0) {
//                itemsList = response.get(position).getSalesLine();
//                customerDataResBean = response.get(0);
//                List<PickPackReservation> pickPackReservations = response.get(0).getPickPackReservation();
//                if (pickPackReservations != null) {
//                    for (PickPackReservation pickPackReservation : pickPackReservations) {
//                        pickPackReservation.setisBatchupdated(false);
//                        globalpickupreservation.add(pickPackReservation);
//                    }
//                }
//
//                eprescription_corpcode = response.get(0).getCorpCode();
//                salesentity = response.get(0).getSalesLine();
//                tempsalesentity = response.get(0).getSalesLine();
//
//
//                customerEntity.setLastName(response.get(0).getCustomerName());
//                customerEntity.setMiddleName(response.get(0).getCustomerName());
//                customerEntity.setMobileNo(response.get(0).getMobileNO());
//                customerEntity.setCardName(response.get(0).getCustomerName());
//                customerEntity.setCorpId(response.get(0).getCorpCode());
//                customerEntity.setCustId(response.get(0).getCustomerID());
//                customerEntity.setGender(String.valueOf(response.get(0).getGender()));
//
//
//                if (response.get(0).getSalesLine().size() > 0) {
//                    for (SalesLineEntity entity : response.get(0).getSalesLine()) {
//                        MedicineInfoEntity medicineInfo = new MedicineInfoEntity();
//
//                        medicineInfo.setCategoryCode(entity.getCategoryCode());
//                        medicineInfo.setItemName(entity.getItemName());
//                        medicineInfo.setQty(entity.getQty());
//                        medicineInfo.setStockQty(entity.getStockQty());
//                        medicineInfo.setMRP(entity.getPrice());
//                        medicineInfo.setScheduleCategory(entity.getScheduleCategory());
//                        medicineInfo.setCategory(entity.getCategory());
//                        medicineInfo.setComment("");
//
//                        medicineInfo.setItemId(entity.getItemId());
//                        medicineInfo.setSubstitudeItemId("");
//                        medicineInfo.setRackId(entity.getRackId());
//
//                        List<PickPackReservation> pickupreservation = new ArrayList<>();
//                        pickupreservation = response.get(0).getPickPackReservation();
//
//                        double pickupqty = 0;
//                        if (pickupreservation != null) {
//                            for (PickPackReservation item : pickupreservation) {
//
//                                if (entity.getItemId().equalsIgnoreCase(item.getPickupItemId())) {
////                                    pickreserved_status = true;
//                                    Constant.getInstance().isSelectedBatch = true;
//                                    Constant.getInstance().selectedItem.setItemId(entity.getItemId());
//
//                                    Constant.getInstance().batchServiceCall = 0;
//                                    Constant.getInstance().batchInfoProducts.clear();
//                                    Constant.getInstance().arrBatchList.clear();
//
//                                    pickupqty = pickupqty + item.getPickupQty();
//                                }
//
//                            }
//                        }
//
//                        medicineInfo.setReqQty(pickupqty);
//                        System.out.println("Customer Name-->1" + entity.getItemName());
//                        itemsArrayList.add(medicineInfo);
//
//                        double total = entity.getPrice() * pickupqty;
////                        totalamount = totalamount + total;
//                    }
//                    String Rupeestring = "\u20B9";
//                    byte[] utf8 = null;
//                    try {
//                        utf8 = Rupeestring.getBytes("UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Rupeestring = new String(utf8, "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//
////                    String total_amt = String.format("%.2f", totalamount);
////                    activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);
//
//                }
//
////                activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());
////                medicinesDetailAdapter.notifyDataSetChanged();
//                if (response.get(0).getOrderPrescriptionURL().size() > 0) {
////                    imageUri = response.get(0).getOrderPrescriptionURL().get(0).getPERSCRIPTIONURL();
//                }
//            }
//
////            if (globalpickupreservation.size() > 0) {
////                additemstosalesline(globalpickupreservation);
////            } else {
////                mPresenter.checkeshopshippingcharges();
////            }
//        } else {
//            showMessage("Order Details Not Available");
//        }
    }

    @Override
    public void onSuccessGetOMSPhysicalBatch(MedicineBatchResBean response) {

    }


    private boolean partialConfirmationClickable;

    @Override
    public boolean recyclerItemClickableStatus() {
        return partialConfirmationClickable;
    }

    @Override
    public void onClickItemUpdate(GetOMSTransactionResponse.SalesLine salesLine, int pos) {
        Dialog updateStatusdialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        DialogUpdateStatusPBinding dialogUpdateStatusPBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_update_status_p, null, false);
        dialogUpdateStatusPBinding.fullPickedRadio.setText(R.string.label_fully_packed);
        dialogUpdateStatusPBinding.partiallyPickedRadio.setText(R.string.label_partially_packed);
        dialogUpdateStatusPBinding.notAvailableRadio.setText(R.string.label_not_available);
        updateStatusdialog.setContentView(dialogUpdateStatusPBinding.getRoot());
        if (updateStatusdialog.getWindow() != null)
            updateStatusdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateStatusdialog.setCancelable(false);
        dialogUpdateStatusPBinding.fullfillmentId.setText(omsHeader.getGetOMSTransactionResponse().getRefno());
        dialogUpdateStatusPBinding.boxId.setText(boxId);
//        dialogUpdateStatusPBinding.boxId.setText(boxId.length() >= 5 ? boxId.substring(boxId.length() - 5) : boxId);
        dialogUpdateStatusPBinding.productName.setText(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(pos).getItemName());
        dialogUpdateStatusPBinding.qty.setText(String.valueOf(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(pos).getQty()));
        dialogUpdateStatusPBinding.update.setText("Save Status");
        if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("FULL")) {
            dialogUpdateStatusPBinding.fullPickedRadio.setChecked(true);
        } else if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("PARTIAL")) {
            dialogUpdateStatusPBinding.partiallyPickedRadio.setChecked(true);
        } else if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("NOT AVAILABLE")) {
            dialogUpdateStatusPBinding.notAvailableRadio.setChecked(true);
        } else {
            dialogUpdateStatusPBinding.notAvailableRadio.setChecked(true);
        }
        dialogUpdateStatusPBinding.fullPickedRadioOne.setOnClickListener(view -> {
            dialogUpdateStatusPBinding.fullPickedRadio.setChecked(true);
            dialogUpdateStatusPBinding.partiallyPickedRadio.setChecked(false);
            dialogUpdateStatusPBinding.notAvailableRadio.setChecked(false);
        });
        dialogUpdateStatusPBinding.partiallyPickedRadioTwo.setOnClickListener(view -> {
            dialogUpdateStatusPBinding.fullPickedRadio.setChecked(false);
            dialogUpdateStatusPBinding.partiallyPickedRadio.setChecked(true);
            dialogUpdateStatusPBinding.notAvailableRadio.setChecked(false);
        });
        dialogUpdateStatusPBinding.notAvailableRadioThree.setOnClickListener(view -> {
            dialogUpdateStatusPBinding.fullPickedRadio.setChecked(false);
            dialogUpdateStatusPBinding.partiallyPickedRadio.setChecked(false);
            dialogUpdateStatusPBinding.notAvailableRadio.setChecked(true);
        });
        dialogUpdateStatusPBinding.update.setOnClickListener(view -> {
            if (dialogUpdateStatusPBinding.fullPickedRadio.isChecked()) {
                omsHeader.getGetOMSTransactionResponse().getSalesLine().get(pos).setPackerStatus("FULL");
            } else if (dialogUpdateStatusPBinding.partiallyPickedRadio.isChecked()) {
                omsHeader.getGetOMSTransactionResponse().getSalesLine().get(pos).setPackerStatus("PARTIAL");
            } else if (dialogUpdateStatusPBinding.notAvailableRadio.isChecked()) {
                omsHeader.getGetOMSTransactionResponse().getSalesLine().get(pos).setPackerStatus("NOT AVAILABLE");
            }
            if (pickUpVerificationAdapter != null) {
                pickUpVerificationAdapter.notifyDataSetChanged();
            }
            updateStatusdialog.dismiss();
            reSendVerPickVernEableChecked();
        });
        dialogUpdateStatusPBinding.dismissDialog.setOnClickListener(view -> updateStatusdialog.dismiss());
        updateStatusdialog.show();
    }

    @Override
    public void onSuccessMposPickPackOrderReservationApiCall(int requestType, MPOSPickPackOrderReservationResponse mposPickPackOrderReservationResponse) {
        if (requestType == 3) {
            if (mposPickPackOrderReservationResponse != null && mposPickPackOrderReservationResponse.getRequestStatus() == 0) {
                if (omsHeader != null) {
                    mpresenter.fetchOMSCustomerInfo(omsHeader.getRefno());
                }
            }
        } else if (requestType == 4) {
            finish();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        } else if (requestType == 6) {
            finish();
            overridePendingTransition(R.anim.slide_from_left_p, R.anim.slide_to_right_p);
        }
    }

    @Override
    public void OmsOrderUpdateSuccess(OMSOrderForwardResponse response, String mposOrderUpdateRequestType) {
        if (mposOrderUpdateRequestType.equals("3")) {
            mpresenter.mposPickPackOrderReservationApiCall(6, omsHeader);
        } else if (mposOrderUpdateRequestType.equals("2")) {
            mpresenter.mposPickPackOrderReservationApiCall(4, omsHeader);
        }
    }

    @Override
    public void OmsOrderUpdateFailure(OMSOrderForwardResponse response) {

    }

    @Override
    public void onClickTakePrint() {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Dialog dialogView = new Dialog(this);// , R.style.Theme_AppCompat_DayNight_NoActionBar
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
            generatebarcode(omsHeader.getRefno());
        }
    }

    private List<GetBatchInfoRes.BatchListObj> selectedBatchList = new ArrayList<>();


    @Override
    public void onSuccessBatchInfo(GetBatchInfoRes response) {
        if (omsHeader.getGetOMSTransactionResponse().getPickPackReservation() != null && omsHeader.getGetOMSTransactionResponse().getPickPackReservation().size() > 0 && response != null && response.getBatchList() != null && response.getBatchList().size() > 0) {
            for (int i = 0; i < omsHeader.getGetOMSTransactionResponse().getPickPackReservation().size(); i++) {
                for (int j = 0; j < response.getBatchList().size(); j++) {
                    if (omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(i).getPickupItemId().equals(response.getBatchList().get(j).getItemID()) && omsHeader.getGetOMSTransactionResponse().getPickPackReservation().get(i).getPickupInventBatchId().equals(response.getBatchList().get(j).getBatchNo())) {
                        selectedBatchList.add(response.getBatchList().get(j));

                    }
                }
            }
        }
        if (salesLineCount + 1 != omsHeader.getGetOMSTransactionResponse().getSalesLine().size()) {
            salesLineCount++;
            mpresenter.getBatchDetailsApi(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(salesLineCount).getItemId());
        } else {
            hideLoading();
        }
        pickUpVerificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {
        salesLineCount++;
        if (salesLineCount + 1 != omsHeader.getGetOMSTransactionResponse().getSalesLine().size()) {
            mpresenter.getBatchDetailsApi(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(salesLineCount).getItemId());
        } else {
            hideLoading();
        }
        Toast.makeText(this, "No batch available", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClickPackerStatusUpdate() {
        if (isPackingVerified == 1) {
            VerificationStatusDialog verificationStatusDialog = new VerificationStatusDialog(PickUpVerificationActivity.this, false, omsHeader.getRefno());
            verificationStatusDialog.setPositiveListener(v -> {
                verificationStatusDialog.dismiss();
                mposOrderUpdate("3");
            });
            verificationStatusDialog.setNegativeListener(v -> verificationStatusDialog.dismiss());
            verificationStatusDialog.show();

            new Handler().postDelayed(() -> {
                if (verificationStatusDialog != null && verificationStatusDialog.isShowing()) {
                    verificationStatusDialog.dismiss();
                    mposOrderUpdate("3");
                }
            }, 3000);

        } else if (isPackingVerified == 2) {
            VerificationStatusDialog verificationStatusDialog = new VerificationStatusDialog(PickUpVerificationActivity.this, true, omsHeader.getRefno());
            verificationStatusDialog.setPositiveListener(v -> {
                verificationStatusDialog.dismiss();
                mposOrderUpdate("2");
            });
            verificationStatusDialog.setNegativeListener(v -> verificationStatusDialog.dismiss());
            verificationStatusDialog.show();

            new Handler().postDelayed(() -> {
                if (verificationStatusDialog != null && verificationStatusDialog.isShowing()) {
                    verificationStatusDialog.dismiss();
                    mposOrderUpdate("2");
                }
            }, 3000);
        } else {
            Toast.makeText(this, "Update the packer status", Toast.LENGTH_SHORT).show();
        }
    }

    public void generatebarcode(String refnumber) {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Your printer is disconnected. Please connect to Printer by clicking on Reprint Barcode", Toast.LENGTH_LONG).show();
        } else {
            PrintfTSPLManager instance = PrintfTSPLManager.getInstance(PickUpVerificationActivity.this);
            instance.clearCanvas();
            instance.initCanvas(90, 23);
            instance.setDirection(0);
            //打印条形码
            //Print barcode
            instance.printBarCode(20, 10, "128", 130, 2, 2, 0, refnumber);
            instance.beginPrintf(1);
        }
    }

    private void reSendVerPickVernEableChecked() {
        if (omsHeader != null && omsHeader.getGetOMSTransactionResponse() != null && omsHeader.getGetOMSTransactionResponse().getSalesLine() != null && omsHeader.getGetOMSTransactionResponse().getSalesLine().size() > 0) {
            boolean isPickerPackerStatusMatched = true;
            boolean isAllUpdated = true;
            for (int i = 0; i < omsHeader.getGetOMSTransactionResponse().getSalesLine().size(); i++) {
                if (omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPickerStatus() != null && omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPackerStatus() != null) {
                    if (!omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPickerStatus().equals(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(i).getPackerStatus())) {
                        isPickerPackerStatusMatched = false;
                    }
                } else {
                    isAllUpdated = false;
                }
            }
            if (isAllUpdated) {
                if (isPickerPackerStatusMatched) {
                    this.isPackingVerified = 1;

                    activityPickupVerificationBinding.sendReVer.setEnabled(false);
                    activityPickupVerificationBinding.sendReVer.setTextColor(getResources().getColor(R.color.unselect_text_color));
                    activityPickupVerificationBinding.sendReVer.setBackgroundColor(getResources().getColor(R.color.light_grey));

                    activityPickupVerificationBinding.pickVerified.setEnabled(true);
                    activityPickupVerificationBinding.pickVerified.setTextColor(getResources().getColor(R.color.black));
                    activityPickupVerificationBinding.pickVerified.setBackgroundColor(getResources().getColor(R.color.yellow));
                } else {
                    this.isPackingVerified = 2;

                    activityPickupVerificationBinding.sendReVer.setEnabled(true);
                    activityPickupVerificationBinding.sendReVer.setTextColor(getResources().getColor(R.color.white));
                    activityPickupVerificationBinding.sendReVer.setBackgroundColor(getResources().getColor(R.color.red));

                    activityPickupVerificationBinding.pickVerified.setEnabled(false);
                    activityPickupVerificationBinding.pickVerified.setTextColor(getResources().getColor(R.color.unselect_text_color));
                    activityPickupVerificationBinding.pickVerified.setBackgroundColor(getResources().getColor(R.color.light_grey));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_cancel, null, false);
        dialogCancelBinding.dialogMessage.setText("The Changes made will be discarded and you'll be directed to Picked orders Page.\n Do you still want to Continue?");
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            mpresenter.mposPickPackOrderReservationApiCall(4, omsHeader);
            dialog.dismiss();
        });
//        dialogCancelBinding.dialogButtonNot.setOnClickListener(v -> dialog.dismiss());
    }
}