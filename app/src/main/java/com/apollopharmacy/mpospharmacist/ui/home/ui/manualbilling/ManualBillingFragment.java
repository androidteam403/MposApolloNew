package com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterPresenter;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class ManualBillingFragment extends BaseFragment implements ManualBillingMvpView {

    @Inject
    ManualBillingMvpPresenter<ManualBillingMvpView> mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_manual_billing, container, false);
        final TextView textView = root.findViewById(R.id.text_send);

        return root;
    }

    @Override
    protected void setUp(View view) {

    }
}