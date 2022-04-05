package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOpenOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FullfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OpenOrdersActivity extends BaseActivity implements OpenOrdersMvpView {
    @Inject
    OpenOrdersMvpPresenter<OpenOrdersMvpView> mPresenter;
    private ActivityOpenOrdersPBinding openOrdersBinding;
//    private List<FullfilmentAdapter.FullfilmentModel> fullfilmentModelList;
    private FullfilmentAdapter fullfilmentAdapter;
//    public List<RackAdapter.RackBoxModel.ProductData> productDataList;
    public List<GetOMSTransactionResponse> getOMSTransactionResponseList;
    public List<TransactionHeaderResponse.OMSHeader> omsHeaderList;

    private boolean isContinueEnable;
    //    private AppBarConfiguration mAppBarConfiguration;

    public static Intent getStartActivity(Context context) {
        return new Intent(context, OpenOrdersActivity.class);
    }


//    public static Intent getStartActivity(DashboardFragment dashboardFragment) {
//        return new Intent(dashboardFragment, OpenOrdersActivity.class);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_open_orders_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(OpenOrdersActivity.this);
        setUp();
//        if (getIntent() != null) {
//            productDataList = (List<RackAdapter.RackBoxModel.ProductData>) getIntent().getSerializableExtra("productDataList");
//        }


    }

    @Override
    protected void setUp() {
        openOrdersBinding.setCallback(mPresenter);
        mPresenter.fetchFulfilmentOrderList();
    }

    int getPos;

    @Override
    public void ondownArrowClicked(int position)
    {
        this.getPos=position;
        mPresenter.onGetOmsTransaction();
    }


//    private RacksDataResponse racksDataResponse;

    @Override
    public void onSuccessRackApi(RacksDataResponse racksDataResponse) {
//        this.racksDataResponse = racksDataResponse;
//        if (racksDataResponse != null && racksDataResponse.getFullfillmentDetails() != null && racksDataResponse.getFullfillmentDetails().size() > 0) {
//            fullfilmentModelList = new ArrayList<>();
//            for (int i = 0; i < racksDataResponse.getFullfillmentDetails().size(); i++) {
//                FullfilmentAdapter.FullfilmentModel fullfilmentModel = new FullfilmentAdapter.FullfilmentModel();
//                fullfilmentModel.setFullfilmentId(racksDataResponse.getFullfillmentDetails().get(i).getFullfillmentId());
//                fullfilmentModel.setTotalItems(racksDataResponse.getFullfillmentDetails().get(i).getTotalItems());
//                fullfilmentModel.setSelected(false);
//                fullfilmentModelList.add(fullfilmentModel);
//            }
//
//            openOrdersBinding.headerOrdersCount.setText("Total " + fullfilmentModelList.size() + " orders");
//            fullfilmentAdapter = new FullfilmentAdapter(this, fullfilmentModelList, this, productDataList, racksDataResponse);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
//            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
//        }

    }

    @Override
    public void onClickFilterIcon() {
        Dialog filterDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
        DialogFilterPBinding dialogFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_filter_p, null, false);
        filterDialog.setContentView(dialogFilterBinding.getRoot());
        filterDialog.setCancelable(false);
        dialogFilterBinding.filterCloseIcon.setOnClickListener(view -> filterDialog.dismiss());
        filterDialog.show();
    }


//    public TransactionHeaderResponse omsHeader;
    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {

        this.omsHeaderList = omsHeader.getOMSHeader();
        openOrdersBinding.headerOrdersCount.setText("Total " + omsHeaderList.size() + " orders");
        FullfilmentAdapter fullfilmentAdapter=new FullfilmentAdapter(this,omsHeader.getOMSHeader(), this, null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
    }


    @Override
    public void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body) {

        fullfilmentAdapter = new FullfilmentAdapter(this, omsHeaderList, this, body);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);

        if (omsHeaderList.get(getPos).getExpandStatus() == 0 && fullfilmentAdapter!=null) {
            fullfilmentAdapter.notifyDataSetChanged();
            omsHeaderList.get(getPos).setExpandStatus(1);

        }else {
            omsHeaderList.get(getPos).setExpandStatus(0);
            fullfilmentAdapter.notifyDataSetChanged();
        }


//     this.getOMSTransactionResponseList=body;

//        for (int i = 0; i <body.size(); i++){
//            getOMSTransactionResponseList.get(getPos).setCustomerType(body.get(i).getCustomerType());
//        }


    }
    @Override
    public void onClickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);



    }

    @Override
    public void onClickStausIcon(int fullFillmentPos, int pos) {
//        Dialog statusUpdateDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
//        DialogUpdateStatusPBinding dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_update_status_p, null, false);
//        statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
//        statusUpdateDialog.setCancelable(false);
//        dialogUpdateStatusBinding.dismissDialog.setOnClickListener(view -> statusUpdateDialog.dismiss());
//
//        omsHeaderList.get(pos).getFullfillmentDetails().get(0).getProducts().get(0).getItemStatus();
//
//        statusUpdateDialog.show();
    }




    int pos;

    @SuppressLint("SetTextI18n")
    @Override
    public void onFullfillmentItemClick(int pos) {
        this.pos = pos;
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            int selectedCount = 0;
            for (TransactionHeaderResponse.OMSHeader fullfilmentModel : omsHeaderList) {
                if (fullfilmentModel.isSelected()) {
                    selectedCount++;
                }
            }
            if (selectedCount < omsHeaderList.size()) {
                omsHeaderList.get(pos).setSelected(!omsHeaderList.get(pos).isSelected());
            } else {
                if (omsHeaderList.get(pos).isSelected())
                    omsHeaderList.get(pos).setSelected(false);
            }
            if (fullfilmentAdapter != null)
                fullfilmentAdapter.notifyDataSetChanged();
            boolean isAnyoneSelect = false;
            int selectedItemCount = 0;
            for (TransactionHeaderResponse.OMSHeader fullfilmentModel : omsHeaderList)
                if (fullfilmentModel.isSelected()) {
                    isAnyoneSelect = true;
                    selectedItemCount++;
                }
            this.isContinueEnable = isAnyoneSelect;
            if (isAnyoneSelect) {
                openOrdersBinding.selectedFullfillment.setText("Selected fullfillment " + selectedItemCount + "/"+ omsHeaderList.size() );
                openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_select_color));
                openOrdersBinding.setIsContinueSelect(true);
            } else {
                openOrdersBinding.selectedFullfillment.setText("Select fullfilment to start pichup process.");
                openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
                openOrdersBinding.setIsContinueSelect(false);
            }
            openOrdersBinding.selectedItemCount.setText(selectedItemCount + "/" + omsHeaderList.size());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       selectedRacksDataResponse = new ArrayList<>();
    }
    public List<TransactionHeaderResponse.OMSHeader> selectedRacksDataResponse;

    @Override
    public void onClickContinue() {
        if (isContinueEnable) {

            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).isSelected()) {
                    omsHeaderList.get(pos).setSelected(omsHeaderList.get(i).isSelected());
                } else {
                    omsHeaderList.get(i).setSelected(omsHeaderList.get(i).isSelected());
                }
            }

            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).isSelected()) {
                    selectedRacksDataResponse.add(omsHeaderList.get(i));
                }
            }

            startActivity(ReadyForPickUpActivity.getStartActivity(this, selectedRacksDataResponse));
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        } else {
            Toast.makeText(this, "Please Select Orders", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRightArrowClickedContinue(int position) {
//        if (racksDataResponse.getFullfillmentDetails() != null && racksDataResponse.getFullfillmentDetails().size() > 0 && racksDataResponse.getFullfillmentDetails().size() > pos) {


            //            Intent i = new Intent(OpenOrdersActivity.this, OrderDetailsActivity.class);
//            i.putExtra("fullfillmentDetails", racksDataResponse.getFullfillmentDetails().get(position));
//            startActivityForResult(i, 999);
//            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

//            openOrdersBinding.layoutFulfilment.setVisibility(View.VISIBLE);
//            FulfimentDetailsAdapter fulfimentDetailsAdapter=new FulfimentDetailsAdapter(productDataList,this);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//            openOrdersBinding.rackRecycler.setLayoutManager(mLayoutManager);
//            openOrdersBinding.rackRecycler.setAdapter(fulfimentDetailsAdapter);


        }
//    }

    int gotId;
    boolean isAnyoneSelect = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            RacksDataResponse.FullfillmentDetail fullfillmentIdNew = (RacksDataResponse.FullfillmentDetail) data.getSerializableExtra("FullfillmentID");
            boolean isSelect = (Boolean) data.getSerializableExtra("isSelect");
            if (fullfillmentIdNew != null) {
                for (int i = 0; i < omsHeaderList.size(); i++) {
                    if (fullfillmentIdNew.getFullfillmentId().equals(omsHeaderList.get(i).getVendorId())) {
                        omsHeaderList.get(i).setSelected(isSelect);
                        fullfilmentAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                int selectedItemCount = 0;
                for (TransactionHeaderResponse.OMSHeader fullfilmentModel : omsHeaderList)
                    if (fullfilmentModel.isSelected()) {
                        isAnyoneSelect = true;
                        selectedItemCount++;
                    }
                this.isContinueEnable = isAnyoneSelect;
                if (isAnyoneSelect) {
                    openOrdersBinding.selectedFullfillment.setText("Selected fullfillment " + selectedItemCount + "/"+ omsHeaderList.size() );
                    openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_select_color));
                    openOrdersBinding.setIsContinueSelect(true);
                } else {
                    openOrdersBinding.selectedFullfillment.setText("Select fullfilment to start pichup process.");
                    openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
                    openOrdersBinding.setIsContinueSelect(false);
                }
                openOrdersBinding.selectedItemCount.setText(selectedItemCount + "/" + omsHeaderList.size());
            }
        }else{
            IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (Result != null) {
                if (Result.getContents() == null) {
                    Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                    openOrdersBinding.searchByfulfimentid.setText(Result.getContents());
                   BillerOrdersActivity.isBillerActivity = false;
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
