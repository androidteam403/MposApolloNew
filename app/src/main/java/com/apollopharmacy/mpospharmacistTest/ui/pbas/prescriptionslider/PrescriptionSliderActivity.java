package com.apollopharmacy.mpospharmacistTest.ui.pbas.prescriptionslider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPrescriptionSliderBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.prescriptionslider.adapter.PresctioptionViewPager;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

public class PrescriptionSliderActivity extends BaseActivity implements PrescriptionSliderMvpView, ViewPager.OnPageChangeListener {
    @Inject
    PrescriptionSliderMvpPresenter<PrescriptionSliderMvpView> mPresenter;
    private ActivityPrescriptionSliderBinding prescriptionSliderBinding;
    public static String PRESCRIPTIONS_LIST = "PRESCRIPTIONS_LIST";
    public static String PRESCRIPTION_POSITION = "PRESCRIPTION_POSITION";
    private List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList;
    private int position;

    public static Intent getStartActivity(Context mContext, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList, int position) {
        Intent intent = new Intent(mContext, PrescriptionSliderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(PRESCRIPTIONS_LIST, (Serializable) prescriptionsList);
        intent.putExtra(PRESCRIPTION_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prescriptionSliderBinding = DataBindingUtil.setContentView(this, R.layout.activity_prescription_slider);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PrescriptionSliderActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        if (getIntent() != null) {
            prescriptionsList = (List<GetOMSTransactionResponse.OrderPrescriptionURL>) getIntent().getSerializableExtra(PRESCRIPTIONS_LIST);
            position = (int) getIntent().getSerializableExtra(PRESCRIPTION_POSITION);
        }
        PresctioptionViewPager presctioptionViewPager = new PresctioptionViewPager(this, this, prescriptionsList);
        prescriptionSliderBinding.prescriptionViewPager.addOnPageChangeListener(this);
        prescriptionSliderBinding.prescriptionViewPager.setAdapter(presctioptionViewPager);
        prescriptionSliderBinding.prescriptionViewPager.setCurrentItem(position, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClickBackIcon() {
        onBackPressed();
    }
}
