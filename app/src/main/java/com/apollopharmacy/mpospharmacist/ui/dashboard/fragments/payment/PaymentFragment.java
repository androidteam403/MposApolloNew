package com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentPaymentBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;

import javax.inject.Inject;

public class PaymentFragment extends BaseFragment implements PaymentMvpView {
    @Inject
    PaymentMvpPresenter<PaymentMvpView> mPresenter;

    private FragmentPaymentBinding paymentBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PaymentFragment.this);
        return paymentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void setUp(View view) {

    }
}