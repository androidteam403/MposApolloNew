package com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentCustMasterBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.billing.BillingMvpView;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class CustomerMasterFragment extends BaseFragment implements CustomerMasterMvpView{

    @Inject
    CustomerMasterPresenter<CustomerMasterMvpView> mPresenter;
    FragmentCustMasterBinding  fragmentCustMasterBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentCustMasterBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_cust_master,container,false);

        return fragmentCustMasterBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {

    }
}