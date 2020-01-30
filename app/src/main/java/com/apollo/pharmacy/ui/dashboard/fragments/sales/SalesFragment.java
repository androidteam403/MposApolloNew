package com.apollo.pharmacy.ui.dashboard.fragments.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.FragmentSalesBinding;
import com.apollo.pharmacy.ui.base.BaseFragment;

import javax.inject.Inject;

public class SalesFragment extends BaseFragment implements SalesMvpView {
    @Inject
    SalesMvpPresenter<SalesMvpView> mPresenter;

    private FragmentSalesBinding salesBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        salesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(SalesFragment.this);
        return salesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,  @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void setUp(View view) {

    }
}