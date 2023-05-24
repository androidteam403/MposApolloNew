package com.apollopharmacy.mpospharmacistTest.ui.pbas.prescriptionslider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewPagerAdapterPrescriptionBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.prescriptionslider.PrescriptionSliderMvpView;
import com.bumptech.glide.Glide;

import java.util.List;

public class PresctioptionViewPager extends PagerAdapter {

    private Context mContext;
    private PrescriptionSliderMvpView mvpView;
    private List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList;

    public PresctioptionViewPager(Context mContext, PrescriptionSliderMvpView mvpView, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList) {
        this.mContext = mContext;
        this.mvpView = mvpView;
        this.prescriptionsList = prescriptionsList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewPagerAdapterPrescriptionBinding viewPagerAdapterPrescriptionBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.view_pager_adapter_prescription, container, false);
        GetOMSTransactionResponse.OrderPrescriptionURL orderPrescriptionURL = prescriptionsList.get(position);
        viewPagerAdapterPrescriptionBinding.setOrderPrescriptionURL(orderPrescriptionURL);
        viewPagerAdapterPrescriptionBinding.setMCallback(mvpView);
        Glide.with(mContext)
                .load(orderPrescriptionURL.getPerscriptionurl())
                .into(viewPagerAdapterPrescriptionBinding.prescriptionPreview);
        container.addView(viewPagerAdapterPrescriptionBinding.getRoot());
        return viewPagerAdapterPrescriptionBinding.getRoot();
    }

    @Override
    public int getCount() {
        return prescriptionsList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
