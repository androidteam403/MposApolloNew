package com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentSalesBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.dashboard.adapter.SelectedPharmacyListAdapter;
import com.apollopharmacy.mpospharmacist.ui.dashboard.model.PharmaPojo;
import com.apollopharmacy.mpospharmacist.ui.searchproduct.SearchProductActivity;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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

            salesBinding.addressRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            salesBinding.addressRecyclerView.setItemAnimator(new DefaultItemAnimator());
            salesBinding.addressRecyclerView.setAdapter(pharmacyListAdapter);
        }
    }

    private void getPharmaList() {
        pharmaPojoArrayList = new ArrayList<>();
        PharmaPojo pharmaPojo = new PharmaPojo("1","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
        ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("2","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("3","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("4","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("5","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("6","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("7","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("8","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("9","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("10","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("11","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("12","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("13","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("14","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);
        pharmaPojo = new PharmaPojo("15","AP03435","5c002d","10",
                "2345","Systo","40203802","20/2/2021","","",""
                ,"1520.50");
        pharmaPojoArrayList.add(pharmaPojo);

    }

    @Override
    public void onProductSearchClick() {
        startActivity(SearchProductActivity.getStartIntent(getContext()));
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}