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
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FilterItemAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FullfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;
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
    private List<FullfilmentAdapter.FullfilmentModel> fullfilmentModelList;
    private FullfilmentAdapter fullfilmentAdapter;
    public List<RackAdapter.RackBoxModel.ProductData> productDataList;

    private boolean isContinueEnable;
    //    private AppBarConfiguration mAppBarConfiguration;
    private List<FilterModel> customerTypeFilterList;
    private List<FilterModel> orderTypeFilterList;
    private List<FilterModel> orderCategoryFilterList;
    private List<FilterModel> paymentTypeFilterList;
    private List<FilterModel> orderSourceFilterList;
    private List<FilterModel> stockAvailabilityFilterList;

    FilterItemAdapter customerTypeFilterAdapter, orderTypeFilterAdapter, orderCategoryFilterAdapter, paymentTypeFilterAdapter, orderSourceFilterAdapter, stockAvailabilityFilterAdapter;


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
        if (getIntent() != null) {
            productDataList = (List<RackAdapter.RackBoxModel.ProductData>) getIntent().getSerializableExtra("productDataList");
        }


    }

    @Override
    protected void setUp() {
        openOrdersBinding.setCallback(mPresenter);
        mPresenter.onRackApiCall();


    }


    private RacksDataResponse racksDataResponse;

    @Override
    public void onSuccessRackApi(RacksDataResponse racksDataResponse) {
        this.racksDataResponse = racksDataResponse;
        if (racksDataResponse != null && racksDataResponse.getFullfillmentDetails() != null && racksDataResponse.getFullfillmentDetails().size() > 0) {
            fullfilmentModelList = new ArrayList<>();
            for (int i = 0; i < racksDataResponse.getFullfillmentDetails().size(); i++) {
                FullfilmentAdapter.FullfilmentModel fullfilmentModel = new FullfilmentAdapter.FullfilmentModel();
                fullfilmentModel.setFullfilmentId(racksDataResponse.getFullfillmentDetails().get(i).getFullfillmentId());
                fullfilmentModel.setTotalItems(racksDataResponse.getFullfillmentDetails().get(i).getTotalItems());
                fullfilmentModel.setSelected(false);
                fullfilmentModelList.add(fullfilmentModel);
            }

            openOrdersBinding.headerOrdersCount.setText("Total " + fullfilmentModelList.size() + " orders");
            fullfilmentAdapter = new FullfilmentAdapter(this, fullfilmentModelList, this, productDataList, racksDataResponse);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            openOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
            openOrdersBinding.fullfilmentRecycler.setAdapter(fullfilmentAdapter);
        }
    }

    @Override
    public void onClickFilterIcon() {
        Dialog filterDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
        DialogFilterPBinding dialogFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_filter_p, null, false);
        filterDialog.setContentView(dialogFilterBinding.getRoot());
        filterDialog.setCancelable(false);
        filtersList(dialogFilterBinding);
        dialogFilterBinding.filterCloseIcon.setOnClickListener(view -> filterDialog.dismiss());
        dialogFilterBinding.clear.setOnClickListener(view -> {
            clearFilter();
        });
        filterDialog.show();
    }

    private void clearFilter() {

        for (int i = 0; i < customerTypeFilterList.size(); i++) {
            customerTypeFilterList.get(i).setSelected(false);
        }
        customerTypeFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < orderTypeFilterList.size(); i++) {
            orderTypeFilterList.get(i).setSelected(false);
        }
        orderTypeFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < orderCategoryFilterList.size(); i++) {
            orderCategoryFilterList.get(i).setSelected(false);
        }
        orderCategoryFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < paymentTypeFilterList.size(); i++) {
            paymentTypeFilterList.get(i).setSelected(false);
        }
        paymentTypeFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < orderSourceFilterList.size(); i++) {
            orderSourceFilterList.get(i).setSelected(false);
        }
        orderSourceFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < stockAvailabilityFilterList.size(); i++) {
            stockAvailabilityFilterList.get(i).setSelected(false);
        }
        stockAvailabilityFilterAdapter.notifyDataSetChanged();
    }

    private void filtersList(DialogFilterPBinding dialogFilterBinding) {
        customerTypeFilterList = new ArrayList<>();
        FilterModel filterModel = new FilterModel();
        filterModel.setName("Normal Priority 2H-HYD");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("Normal Priority");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("Repeat User");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("OK");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("Medium Priority");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("Medium Priority 2H-HYD");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("Normal Priority 2H-BLR");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("CIRCLEPlan");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("VIP");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        filterModel = new FilterModel();
        filterModel.setName("Circle_Half Yearly, HDFC Platinum");
        filterModel.setSelected(false);
        customerTypeFilterList.add(filterModel);

        orderTypeFilterList = new ArrayList<>();
        FilterModel filterModel1 = new FilterModel();
        filterModel1.setSelected(false);
        filterModel1.setName("Priority");
        orderTypeFilterList.add(filterModel1);

        orderCategoryFilterList = new ArrayList<>();
        FilterModel filterModel2 = new FilterModel();
        filterModel2.setName("FMCG");
        filterModel2.setSelected(false);
        orderCategoryFilterList.add(filterModel2);

        filterModel2 = new FilterModel();
        filterModel2.setName("PHARMA");
        filterModel2.setSelected(false);
        orderCategoryFilterList.add(filterModel2);

        paymentTypeFilterList = new ArrayList<>();
        FilterModel filterModel3 = new FilterModel();
        filterModel3.setName("PREPAID");
        filterModel3.setSelected(false);
        paymentTypeFilterList.add(filterModel3);

        filterModel3 = new FilterModel();
        filterModel3.setName("COD");
        filterModel3.setSelected(false);
        paymentTypeFilterList.add(filterModel3);

        orderSourceFilterList = new ArrayList<>();
        FilterModel filterModel4 = new FilterModel();
        filterModel4.setName("OMSOA");
        filterModel4.setSelected(false);
        orderSourceFilterList.add(filterModel4);

        filterModel4 = new FilterModel();
        filterModel4.setName("OMSSR");
        filterModel4.setSelected(false);
        orderSourceFilterList.add(filterModel4);

        filterModel4 = new FilterModel();
        filterModel4.setName("E SHOP");
        filterModel4.setSelected(false);
        orderSourceFilterList.add(filterModel4);

        filterModel4 = new FilterModel();
        filterModel4.setName("APOLLO247");
        filterModel4.setSelected(false);
        orderSourceFilterList.add(filterModel4);

        filterModel4 = new FilterModel();
        filterModel4.setName("OMS CIRCLE");
        filterModel4.setSelected(false);
        orderSourceFilterList.add(filterModel4);

        filterModel4 = new FilterModel();
        filterModel4.setName("OMS");
        filterModel4.setSelected(false);
        orderSourceFilterList.add(filterModel4);

        stockAvailabilityFilterList = new ArrayList<>();
        FilterModel filterModel5 = new FilterModel();
        filterModel5.setName("Full");
        filterModel5.setSelected(false);
        stockAvailabilityFilterList.add(filterModel5);

        filterModel5 = new FilterModel();
        filterModel5.setName("Partial");
        filterModel5.setSelected(false);
        stockAvailabilityFilterList.add(filterModel5);

        filterModel5 = new FilterModel();
        filterModel5.setName("Not Available");
        filterModel5.setSelected(false);
        stockAvailabilityFilterList.add(filterModel5);

        customerTypeFilterAdapter = new FilterItemAdapter(this, customerTypeFilterList);
        dialogFilterBinding.customerTypeFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dialogFilterBinding.customerTypeFilter.setAdapter(customerTypeFilterAdapter);

        orderTypeFilterAdapter = new FilterItemAdapter(this, orderTypeFilterList);
        dialogFilterBinding.orderTypeFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dialogFilterBinding.orderTypeFilter.setAdapter(orderTypeFilterAdapter);

        orderCategoryFilterAdapter = new FilterItemAdapter(this, orderCategoryFilterList);
        dialogFilterBinding.orderCategoryFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dialogFilterBinding.orderCategoryFilter.setAdapter(orderCategoryFilterAdapter);

        paymentTypeFilterAdapter = new FilterItemAdapter(this, paymentTypeFilterList);
        dialogFilterBinding.paymentTypeFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dialogFilterBinding.paymentTypeFilter.setAdapter(paymentTypeFilterAdapter);

        orderSourceFilterAdapter = new FilterItemAdapter(this, orderSourceFilterList);
        dialogFilterBinding.orderSourceFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dialogFilterBinding.orderSourceFilter.setAdapter(orderSourceFilterAdapter);

        stockAvailabilityFilterAdapter = new FilterItemAdapter(this, stockAvailabilityFilterList);
        dialogFilterBinding.stockAvailableFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dialogFilterBinding.stockAvailableFilter.setAdapter(stockAvailabilityFilterAdapter);

    }

    @Override
    public void onClickScanCode() {
        BillerOrdersActivity.isBillerActivity = true;
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onClickStausIcon(int fullFillmentPos, int pos) {
        Dialog statusUpdateDialog = new Dialog(this, R.style.fadeinandoutcustomDialog);
        DialogUpdateStatusPBinding dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_update_status_p, null, false);
        statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
        statusUpdateDialog.setCancelable(false);
        dialogUpdateStatusBinding.dismissDialog.setOnClickListener(view -> statusUpdateDialog.dismiss());

        racksDataResponse.getFullfillmentDetails().get(0).getProducts().get(0).getItemStatus();

        statusUpdateDialog.show();
    }


    int pos;

    @SuppressLint("SetTextI18n")
    @Override
    public void onFullfillmentItemClick(int pos) {
        this.pos = pos;
        if (fullfilmentModelList != null && fullfilmentModelList.size() > 0) {
            int selectedCount = 0;
            for (FullfilmentAdapter.FullfilmentModel fullfilmentModel : fullfilmentModelList) {
                if (fullfilmentModel.isSelected()) {
                    selectedCount++;
                }
            }
            if (selectedCount < 5) {
                fullfilmentModelList.get(pos).setSelected(!fullfilmentModelList.get(pos).isSelected());
            } else {
                if (fullfilmentModelList.get(pos).isSelected())
                    fullfilmentModelList.get(pos).setSelected(false);
            }
            if (fullfilmentAdapter != null)
                fullfilmentAdapter.notifyDataSetChanged();
            boolean isAnyoneSelect = false;
            int selectedItemCount = 0;
            for (FullfilmentAdapter.FullfilmentModel fullfilmentModel : fullfilmentModelList)
                if (fullfilmentModel.isSelected()) {
                    isAnyoneSelect = true;
                    selectedItemCount++;
                }
            this.isContinueEnable = isAnyoneSelect;
            if (isAnyoneSelect) {
                openOrdersBinding.selectedFullfillment.setText("Selected fullfillment " + selectedItemCount + "/5.");
                openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_select_color));
                openOrdersBinding.setIsContinueSelect(true);
            } else {
                openOrdersBinding.selectedFullfillment.setText("Select fullfilment to start pichup process.");
                openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
                openOrdersBinding.setIsContinueSelect(false);
            }
            openOrdersBinding.selectedItemCount.setText(selectedItemCount + "/5");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedRacksDataResponse = new ArrayList<>();
    }

    private List<RacksDataResponse.FullfillmentDetail> selectedRacksDataResponse;

    @Override
    public void onClickContinue() {
        if (isContinueEnable) {

            for (int i = 0; i < fullfilmentModelList.size(); i++) {
                if (fullfilmentModelList.get(i).isSelected()) {
                    racksDataResponse.getFullfillmentDetails().get(i).setSelectedBoxesData(fullfilmentModelList.get(i).isSelected());
                } else {
                    racksDataResponse.getFullfillmentDetails().get(i).setSelectedBoxesData(fullfilmentModelList.get(i).isSelected());
                }
            }

            for (int i = 0; i < racksDataResponse.getFullfillmentDetails().size(); i++) {
                if (racksDataResponse.getFullfillmentDetails().get(i).isSelectedBoxesData()) {
                    selectedRacksDataResponse.add(racksDataResponse.getFullfillmentDetails().get(i));
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
        if (racksDataResponse.getFullfillmentDetails() != null && racksDataResponse.getFullfillmentDetails().size() > 0 && racksDataResponse.getFullfillmentDetails().size() > pos) {


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
    }

    int gotId;
    boolean isAnyoneSelect = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            RacksDataResponse.FullfillmentDetail fullfillmentIdNew = (RacksDataResponse.FullfillmentDetail) data.getSerializableExtra("FullfillmentID");
            boolean isSelect = (Boolean) data.getSerializableExtra("isSelect");
            if (fullfillmentIdNew != null) {
                for (int i = 0; i < fullfilmentModelList.size(); i++) {
                    if (fullfillmentIdNew.getFullfillmentId().equals(fullfilmentModelList.get(i).getFullfilmentId())) {
                        fullfilmentModelList.get(i).setSelected(isSelect);
                        fullfilmentAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                int selectedItemCount = 0;
                for (FullfilmentAdapter.FullfilmentModel fullfilmentModel : fullfilmentModelList)
                    if (fullfilmentModel.isSelected()) {
                        isAnyoneSelect = true;
                        selectedItemCount++;
                    }
                this.isContinueEnable = isAnyoneSelect;
                if (isAnyoneSelect) {
                    openOrdersBinding.selectedFullfillment.setText("Selected fullfillment " + selectedItemCount + "/5.");
                    openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_select_color));
                    openOrdersBinding.setIsContinueSelect(true);
                } else {
                    openOrdersBinding.selectedFullfillment.setText("Select fullfilment to start pichup process.");
                    openOrdersBinding.continueBtn.setBackgroundColor(getResources().getColor(R.color.continue_unselect_color));
                    openOrdersBinding.setIsContinueSelect(false);
                }
                openOrdersBinding.selectedItemCount.setText(selectedItemCount + "/5");
            }
        } else {
            IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (Result != null) {
                if (Result.getContents() == null) {
                    Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                    BillerOrdersActivity.isBillerActivity = false;
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
