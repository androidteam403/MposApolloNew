package com.apollo.pharmacy.ui.dashboard.fragments.sales;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.FragmentSalesBinding;
import com.apollo.pharmacy.ui.base.BaseFragment;
import com.apollo.pharmacy.ui.dashboard.adapter.SelectedPharmacyListAdapter;
import com.apollo.pharmacy.ui.dashboard.model.PharmaPojo;
import com.apollo.pharmacy.ui.searchproduct.SearchProductActivity;
import com.apollo.pharmacy.ui.searchuser.SearchUserActivity;

import java.util.ArrayList;

import javax.inject.Inject;

public class SalesFragment extends BaseFragment implements SalesMvpView {
    @Inject
    SalesMvpPresenter<SalesMvpView> mPresenter;
    private ArrayList<PharmaPojo> pharmaPojoArrayList = null;
    private SelectedPharmacyListAdapter pharmacyListAdapter;

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
            salesBinding.setCallback(mPresenter);
        getPharmaList();

        if (pharmaPojoArrayList.size() > 0) {
            pharmacyListAdapter = new SelectedPharmacyListAdapter(this, pharmaPojoArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            salesBinding.addressRecyclerView.setLayoutManager(mLayoutManager);
            salesBinding.addressRecyclerView.setItemAnimator(new DefaultItemAnimator());
            salesBinding.addressRecyclerView.setAdapter(pharmacyListAdapter);
        }
    }

    private void getPharmaList() {
        pharmaPojoArrayList = new ArrayList<>();
        PharmaPojo pharmaPojo = new PharmaPojo("AP-534005", "APC0111", "1234", "Pharma", "12/12/1020");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("AP03435", "5C002d", "3456", "FMCG", "20/20/1010");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("CN01234", "Ac444", "8784", "H&B", "20/12/2012");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("An0106", "Pa1997", "6567", "FMCG", "12/20/2016");
        pharmaPojoArrayList.add(pharmaPojo);

    }

    @Override
    public void onProductSearchClick() {
        startActivity(SearchProductActivity.getStartIntent(getContext()));
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }
}