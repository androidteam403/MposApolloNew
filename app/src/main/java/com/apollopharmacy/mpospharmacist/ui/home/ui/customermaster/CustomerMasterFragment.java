package com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentCustMasterBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;

import javax.inject.Inject;


public class CustomerMasterFragment extends BaseFragment implements CustomerMasterMvpView {

    @Inject
    CustomerMasterPresenter<CustomerMasterMvpView> mPresenter;
    FragmentCustMasterBinding fragmentCustMasterBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentCustMasterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cust_master, container, false);

        return fragmentCustMasterBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {

    }
}