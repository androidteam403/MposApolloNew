package com.apollo.pharmacy.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityDashboardBinding;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.dashboard.adapter.SelectedPharmacyListAdapter;
import com.apollo.pharmacy.ui.dashboard.model.PharmaPojo;
import com.apollo.pharmacy.ui.dashboard.tabview.ViewPagerAdapter;
import com.apollo.pharmacy.ui.searchproduct.SearchProductActivity;
import com.apollo.pharmacy.ui.searchuser.SearchUserActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import javax.inject.Inject;

public class DashboardActivity extends BaseActivity implements DashboardMvpView {

    @Inject
    DashboardMvpPresenter<DashboardMvpView> mPresenter;

    private ActivityDashboardBinding dashboardBinding;
    private ArrayList<PharmaPojo> pharmaPojoArrayList = null;
    private SelectedPharmacyListAdapter pharmacyListAdapter;
    private ArrayList<PharmaPojo> pojoArrayList;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public static Intent getStartIntent(Context context) {
        return new Intent(context, DashboardActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DashboardActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        dashboardBinding.setCallback(mPresenter);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        dashboardBinding.viewPager.setAdapter(viewPagerAdapter);
        dashboardBinding.tabLayout.setupWithViewPager(dashboardBinding.viewPager);
//        getPharmaList();
//        if (pharmaPojoArrayList.size() > 0) {
//            pharmacyListAdapter = new SelectedPharmacyListAdapter(this, pharmaPojoArrayList);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//            dashboardBinding.addressRecyclerView.setLayoutManager(mLayoutManager);
//            dashboardBinding.addressRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            dashboardBinding.addressRecyclerView.setAdapter(pharmacyListAdapter);
//        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

//    private void getPharmaList() {
//        pharmaPojoArrayList = new ArrayList<>();
//        PharmaPojo pharmaPojo = new PharmaPojo("AP-534005", "APC0111", "1234", "Pharma", "12/12/1020");
//        pharmaPojoArrayList.add(pharmaPojo);
//        pharmaPojo = new PharmaPojo("AP03435", "5C002d", "3456", "FMCG", "20/20/1010");
//        pharmaPojoArrayList.add(pharmaPojo);
//        pharmaPojo = new PharmaPojo("CN01234", "Ac444", "8784", "H&B", "20/12/2012");
//        pharmaPojoArrayList.add(pharmaPojo);
//        pharmaPojo = new PharmaPojo("An0106", "Pa1997", "6567", "FMCG", "12/20/2016");
//        pharmaPojoArrayList.add(pharmaPojo);
//
//    }

    @Override
    public void onSearchUserClick() {
        startActivity(SearchUserActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


}
