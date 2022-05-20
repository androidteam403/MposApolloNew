package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPickedUpOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.adapter.PickedUpOrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PickedUpOrdersActivity extends BaseActivity implements PickedUpOrdersMvpView {

    @Inject
    PickedUpOrdersMvpPresenter<PickedUpOrdersMvpView> mvpPresenter;
    private ActivityPickedUpOrdersPBinding activityPickedUpOrdersBinding;
    private PickedUpOrdersAdapter pickedUpOrdersAdapter;
    private static final int PICKUP_VERIFICATION_ACTIVITY = 108;
    List<RackAdapter.RackBoxModel.ProductData> productDataList;
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList;

    public static Intent getStartActivity(Context mContext) {
        Intent intent = new Intent(mContext, PickedUpOrdersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickedUpOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_picked_up_orders_p);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(PickedUpOrdersActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
//        activityPickedUpOrdersBinding.setCallback(mvpPresenter);
        mvpPresenter.fetchOMSOrderList();
        searchByFulfilmentId();
//        if (mvpPresenter.getFullFillmentList() != null) {
//            activityPickedUpOrdersBinding.zeropicked.setVisibility(View.GONE);
//            activityPickedUpOrdersBinding.headerOrdersCount.setText("Total " + String.valueOf(mvpPresenter.getFullFillmentList().size()) + " Orders");
//
//            pickedUpOrdersAdapter = new PickedUpOrdersAdapter(this, mvpPresenter.getFullFillmentList(), this, mvpPresenter.getListOfListFullFillmentList(), false);
//            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
//            activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
//        } else {
//            activityPickedUpOrdersBinding.zeropicked.setVisibility(View.VISIBLE);
//        }



        activityPickedUpOrdersBinding.scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(PickedUpOrdersActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();

            }
        });
    }


    private void searchByFulfilmentId() {
        activityPickedUpOrdersBinding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    if (pickedUpOrdersAdapter != null) {
                        pickedUpOrdersAdapter.getFilter().filter(editable);

                    }
                } else {
                    if (pickedUpOrdersAdapter != null) {
                        pickedUpOrdersAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }
    @Override
    public void startPickUp() {

    }

    private boolean removeItsStatis;



    @Override
    public void onClickScanCode() {
//        Intent intent = new Intent(PickedUpOrdersActivity.this, ScannerActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);


        }


    @Override
    public void onItmClick(int position,List<TransactionHeaderResponse.OMSHeader> omsHeaderObjList) {
        startActivityForResult(PickUpVerificationActivity.getStartActivity(PickedUpOrdersActivity.this, position, omsHeaderObjList), PICKUP_VERIFICATION_ACTIVITY);

        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
        activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.GONE);
        } else {
         activityPickedUpOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList) {

    }

    @Override
    public void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader) {
        for (int i=0;i<omsHeader.getOMSHeader().size();i++){
            if (omsHeader.getOMSHeader() != null  && omsHeader.getOMSHeader() .get(i).getOrderPickup()==true && omsHeader.getOMSHeader() .get(i).getOrderPacked()==false) {
                omsHeaderList.add(omsHeader.getOMSHeader() .get(i));


            }
            activityPickedUpOrdersBinding.headerOrdersCount.setText("Total"+ " " +String.valueOf(omsHeaderList.size())+" "+"Orders");
            activityPickedUpOrdersBinding.zeropicked.setVisibility(View.GONE);
            pickedUpOrdersAdapter = new PickedUpOrdersAdapter(this, omsHeaderList, this);

            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
            activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
            activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);

        }
    }

    @Override
    public void onSuccessGetOMSTransactionList(OMSTransactionResponse response) {



        }


//    @Override
//    public void onItemClick(int position, String status, List<RackAdapter.RackBoxModel.ProductData> productDataList, List<RacksDataResponse.FullfillmentDetail> fullFillModelList, RacksDataResponse.FullfillmentDetail fillModel) {
//        Gson gson = new Gson();
//        String myJson = gson.toJson(productDataList);
//
//        Gson gson1 = new Gson();
//        String myJson1 = gson1.toJson(fullFillModelList);
//
//
//       startActivityForResult(PickUpVerificationActivity.getStartActivity(PickedUpOrdersActivity.this, position, status, myJson, myJson1, fillModel), PICKUP_VERIFICATION_ACTIVITY);
//        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PickedUpOrdersActivity.this, PickUpVerificationActivity.class);
              String id=Result.getContents();
                intent.putExtra("fulfilmentId",id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
   }
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PICKUP_VERIFICATION_ACTIVITY:
//                    if (data != null) {
//                        int position = (int) data.getIntExtra("position", 0);
//                        Gson gson = new Gson();
//                        String json = data.getStringExtra("productDataList");
//                        Type type = new TypeToken<List<RackAdapter.RackBoxModel.ProductData>>() {
//                        }.getType();
//                        if (productDataList != null) {
//                            productDataList.clear();
//                        }
//                        productDataList = gson.fromJson(json, type);
//
//                        Gson gson1 = new Gson();
//                        String json1 = data.getStringExtra("fullFillModelList");
//                        Type type1 = new TypeToken<List<RacksDataResponse.FullfillmentDetail>>() {
//                        }.getType();
//                        if (fullfillmentDetailList != null) {
//                            fullfillmentDetailList.clear();
//                        }
//                        fullfillmentDetailList = gson1.fromJson(json1, type1);
//
//                        List<RacksDataResponse.FullfillmentDetail.Product> productsList = new ArrayList<>();
//                        RacksDataResponse.FullfillmentDetail.Product product = new RacksDataResponse.FullfillmentDetail.Product();
//
//                        RacksDataResponse.FullfillmentDetail fullfillmentDetail = new RacksDataResponse.FullfillmentDetail();
//                        for (int i = 0; i < fullfillmentDetailList.size(); i++) {
//                            fullfillmentDetail.setFullfillmentId(fullfillmentDetailList.get(i).getFullfillmentId());
//                            fullfillmentDetail.setTotalItems(fullfillmentDetailList.get(i).getTotalItems());
//                            fullfillmentDetail.setStatus(fullfillmentDetailList.get(i).getStatus());
//                            fullfillmentDetail.setSelectedBoxesData(fullfillmentDetailList.get(i).isSelectedBoxesData());
//                            fullfillmentDetail.setExpandStatus(fullfillmentDetailList.get(i).getExpandStatus());
//                            fullfillmentDetail.setBoxId(fullfillmentDetailList.get(i).getBoxId());
//                            for (int j = 0; j < fullfillmentDetailList.get(i).getProducts().size(); j++) {
//                                for (int k = 0; k < productDataList.size(); k++) {
//                                    if (fullfillmentDetailList.get(i).getProducts().get(j).getProductId().equalsIgnoreCase(productDataList.get(k).getProductId())) {
//                                        fullfillmentDetailList.get(i).getProducts().get(j).setPackerStatus(productDataList.get(k).getPackerStatus());
//                                    }
//                                }
//                            }
//                        }
//
//
//                        mvpPresenter.setFullFillmentDataList(fullfillmentDetailList);
////
////                        pickedUpOrdersAdapter = new PickedUpOrdersAdapter(this, mvpPresenter.getFullFillmentList(), this, mvpPresenter.getListOfListFullFillmentList(), false);
////                        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
////                        activityPickedUpOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager1);
////                        activityPickedUpOrdersBinding.fullfilmentRecycler.setItemAnimator(new DefaultItemAnimator());
////                        activityPickedUpOrdersBinding.fullfilmentRecycler.setAdapter(pickedUpOrdersAdapter);
//                    }
//                    break;
//            }
//        }
    }


}
