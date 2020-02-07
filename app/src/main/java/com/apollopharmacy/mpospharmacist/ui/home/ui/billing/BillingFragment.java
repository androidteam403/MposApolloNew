package com.apollopharmacy.mpospharmacist.ui.home.ui.billing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentBillingBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;


public class BillingFragment extends BaseFragment implements BillingMvpView {

    @Inject
    BillingMvpPresenter<BillingMvpView> mPresenter;
    FragmentBillingBinding fragmentBillingBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBillingBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_billing,container,false);

        return fragmentBillingBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {

    }
}