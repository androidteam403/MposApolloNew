package com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.MedicineDetailsActivityBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.adapter.MedicinesDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.model.MedicineDetailsModel;
import com.apollopharmacy.mpospharmacist.ui.pay.PayActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.adapter.ProductInfoAdapter;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.model.ProductInfoPojo;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

public class MedicinesDetailsActivity extends BaseActivity implements MedicineDetailsMvpView {

    @Inject
    MedicineDetailsMvpPresenter<MedicineDetailsMvpView> mvpPresenter;
    private MedicineDetailsActivityBinding medicinesDetailsActivityBinding;
    private ArrayList<MedicineDetailsModel> medicineDetailsModelsList = null;
    private MedicinesDetailAdapter medicinesDetailAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MedicinesDetailsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        medicinesDetailsActivityBinding = DataBindingUtil.setContentView(this, R.layout.medicine_details_activity);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(MedicinesDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        medicinesDetailsActivityBinding.setCallback(mvpPresenter);
        getMedicinesInfo();
        if (medicineDetailsModelsList.size() > 0) {
            medicinesDetailAdapter = new MedicinesDetailAdapter(this, medicineDetailsModelsList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            medicinesDetailsActivityBinding.medicineRecycle.setLayoutManager(mLayoutManager);
            //medicinesDetailsActivityBinding.medicineRecycle.setItemAnimator(new DefaultItemAnimator());
            //medicinesDetailsActivityBinding.medicineRecycle.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            //medicinesDetailsActivityBinding.medicineRecycle.setItemAnimator(new DefaultItemAnimator());
            medicinesDetailsActivityBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);
        }
    }

    private void getMedicinesInfo() {
        medicineDetailsModelsList = new ArrayList<>();
        MedicineDetailsModel medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
        medicineDetailsModel = new MedicineDetailsModel("1.00", "10", "9.70",
                "Tax:1.04(12%)");
        medicineDetailsModelsList.add(medicineDetailsModel);
    }

    @Override
    public void onManualSearchClick() {

    }

    @Override
    public void onVoiceSearchClick() {

    }

    @Override
    public void onBarCodeSearchClick() {

    }

    @Override
    public void onClickBackBtn() {
        onBackPressed();
    }

    @Override
    public void onPayButtonClick() {
        startActivity(PayActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
