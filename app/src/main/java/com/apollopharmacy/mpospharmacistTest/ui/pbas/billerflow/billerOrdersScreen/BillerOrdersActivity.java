package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBillerOrdersPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFilterPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.adapter.BillerFullfillmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.OrderDetailsScreenActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import javax.inject.Inject;

public class BillerOrdersActivity extends BaseActivity implements BillerOrdersMvpView {

    @Inject
    BillerOrdersMvpPresenter<BillerOrdersMvpView> mPresenter;
    private List<RacksDataResponse.FullfillmentDetail> racksDataResponse;
    ActivityBillerOrdersPBinding activityBillerOrdersBinding;
    private List<BillerFullfillmentAdapter.FullfilmentModel> fullfilmentModelList;
    private BillerFullfillmentAdapter billerFullfillmentAdapter;
    public static boolean isBillerActivity;

    public static Intent getStartIntent(Context mContext) {
        Intent i = new Intent(mContext, BillerOrdersActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBillerOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_biller_orders_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BillerOrdersActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityBillerOrdersBinding.setCallback(mPresenter);
        mPresenter.onRackApiCall();
    }

    @Override
    public void onclickScanCode() {
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    @Override
    public void onRightArrowClickedContinue(int position) {
        if (racksDataResponse != null && racksDataResponse.size() > 0 && racksDataResponse.size() > position) {
            Intent i = new Intent(BillerOrdersActivity.this, OrderDetailsScreenActivity.class);
            i.putExtra("fullfillmentDetails", racksDataResponse.get(position));
//            startActivityForResult(i, 999);
            startActivity(i);
            overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
        }
    }

    @Override
    public void onSuccessRackApi(RacksDataResponse body) {
        racksDataResponse = body.getFullfillmentDetails();
        billerFullfillmentAdapter = new BillerFullfillmentAdapter(this, racksDataResponse, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityBillerOrdersBinding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        activityBillerOrdersBinding.fullfilmentRecycler.setAdapter(billerFullfillmentAdapter);

        activityBillerOrdersBinding.scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBillerActivity = true;
                new IntentIntegrator(BillerOrdersActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        });
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ReadyForPickUpActivity.fullfillmentDetailList.clear();
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}