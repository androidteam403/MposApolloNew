package com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.SwipeController;
import com.apollopharmacy.mpospharmacist.utils.SwipeControllerActions;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

public class MedicinesDetailsActivity extends BaseActivity implements MedicineDetailsMvpView {

    @Inject
    MedicineDetailsMvpPresenter<MedicineDetailsMvpView> mvpPresenter;
    private MedicineDetailsActivityBinding medicinesDetailsActivityBinding;
    private ArrayList<GetItemDetailsRes.Items> medicineDetailsModelsList = new ArrayList<>();
    private MedicinesDetailAdapter medicinesDetailAdapter;

    public static Intent getStartIntent(Context context, GetItemDetailsRes.Items items) {
        Intent intent = new Intent(context, MedicinesDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_item",items);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicinesDetailsActivityBinding = DataBindingUtil.setContentView(this, R.layout.medicine_details_activity);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(MedicinesDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        medicinesDetailsActivityBinding.setCallback(mvpPresenter);
        GetItemDetailsRes.Items items = (GetItemDetailsRes.Items) getIntent().getSerializableExtra("selected_item");
        medicineDetailsModelsList.add(items);
        if (medicineDetailsModelsList.size() > 0) {
            medicinesDetailAdapter = new MedicinesDetailAdapter(this, medicineDetailsModelsList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            medicinesDetailsActivityBinding.medicineRecycle.setLayoutManager(mLayoutManager);
            medicinesDetailsActivityBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);

            SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {
                 //   medicinesDetailAdapter.remove(position);
                    medicinesDetailAdapter.notifyItemRemoved(position);
                    medicinesDetailAdapter.notifyItemRangeChanged(position, medicinesDetailAdapter.getItemCount());
                }
            });

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
            itemTouchhelper.attachToRecyclerView(medicinesDetailsActivityBinding.medicineRecycle);

            medicinesDetailsActivityBinding.medicineRecycle.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    swipeController.onDraw(c);
                }
            });
        }
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
