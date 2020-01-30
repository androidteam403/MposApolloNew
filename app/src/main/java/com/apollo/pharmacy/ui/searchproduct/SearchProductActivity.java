package com.apollo.pharmacy.ui.searchproduct;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.data.db.model.StoreDetails;
import com.apollo.pharmacy.data.db.realm.RealmController;
import com.apollo.pharmacy.databinding.ActivitySearchPharmacyBinding;
import com.apollo.pharmacy.databinding.ActivitySearchProductBinding;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;
import com.apollo.pharmacy.ui.searchpharmacy.SearchPharmacyMvpPresenter;
import com.apollo.pharmacy.ui.searchpharmacy.SearchPharmacyMvpView;
import com.apollo.pharmacy.ui.searchpharmacy.adapter.SearchPharmacyListAdapter;
import com.apollo.pharmacy.ui.searchpharmacy.model.Pharmacy;
import com.apollo.pharmacy.ui.searchproduct.adapter.BatchInfoAdapter;
import com.apollo.pharmacy.ui.searchproduct.adapter.ProductInfoAdapter;
import com.apollo.pharmacy.ui.searchproduct.listener.SearchProductListener;
import com.apollo.pharmacy.ui.searchproduct.model.ProductBatchPojo;
import com.apollo.pharmacy.ui.searchproduct.model.ProductInfoPojo;
import com.apollo.pharmacy.utils.bottomsheet.PharmacyBottomSheetFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;

import static com.apollo.pharmacy.root.ApolloMposApp.getContext;

public class SearchProductActivity extends BaseActivity implements SearchProductMvpView, SearchProductListener {

    @Inject
    SearchProductMvpPresenter<SearchProductMvpView> mPresenter;
    private ActivitySearchProductBinding searchProductBinding;

    private ArrayList<ProductInfoPojo> productInfoList = null;
    private ArrayList<ProductBatchPojo> productBatchList = null;
    private ProductInfoAdapter productInfoAdapter;
    private BatchInfoAdapter batchInfoAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchProductActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_product);
        getActivityComponent().inject(this);
        mPresenter.onAttach(SearchProductActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        getProductInfo();
        getBatchInfo();
        getEditTextInfo();
        if (productInfoList.size() > 0) {
            productInfoAdapter = new ProductInfoAdapter(this, productInfoList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            searchProductBinding.productInfoRecycler.setLayoutManager(mLayoutManager);
            searchProductBinding.productInfoRecycler.setItemAnimator(new DefaultItemAnimator());
            searchProductBinding.productInfoRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            searchProductBinding.productInfoRecycler.setItemAnimator(new DefaultItemAnimator());
            searchProductBinding.productInfoRecycler.setAdapter(productInfoAdapter);
        }
        batchInfoAdapter = new BatchInfoAdapter(this, productBatchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        searchProductBinding.batchInfoRecycler.setLayoutManager(mLayoutManager);
        searchProductBinding.batchInfoRecycler.setItemAnimator(new DefaultItemAnimator());
        searchProductBinding.batchInfoRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        searchProductBinding.batchInfoRecycler.setItemAnimator(new DefaultItemAnimator());
        searchProductBinding.batchInfoRecycler.setAdapter(batchInfoAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getProductInfo() {
        productInfoList = new ArrayList<>();
        ProductInfoPojo productInfoPojo = new ProductInfoPojo("ap908098", "DOLO-650", "FMCG", "Pharma", "12/12/1020",
                "fever", "21323");
        productInfoList.add(productInfoPojo);
        productInfoPojo = new ProductInfoPojo("hfh887", "Paracetimol-98", "FMCG", "Pharma", "09/01/2020",
                "cold", "6786");
        productInfoList.add(productInfoPojo);

        productInfoPojo = new ProductInfoPojo("ap908098", "DOLO-650", "FMCG", "Pharma", "12/12/1020",
                "fever", "21323");
        productInfoList.add(productInfoPojo);
        productInfoPojo = new ProductInfoPojo("hfh887", "Paracetimol-98", "FMCG", "Pharma", "09/01/2020",
                "cold", "6786");
        productInfoList.add(productInfoPojo);

        productInfoPojo = new ProductInfoPojo("ap908098", "DOLO-650", "FMCG", "Pharma", "12/12/1020",
                "fever", "21323");
        productInfoList.add(productInfoPojo);
        productInfoPojo = new ProductInfoPojo("hfh887", "Paracetimol-98", "FMCG", "Pharma", "09/01/2020",
                "cold", "6786");
        productInfoList.add(productInfoPojo);

        productInfoPojo = new ProductInfoPojo("ap908098", "DOLO-650", "FMCG", "Pharma", "12/12/1020",
                "fever", "21323");
        productInfoList.add(productInfoPojo);
        productInfoPojo = new ProductInfoPojo("hfh887", "Paracetimol-98", "FMCG", "Pharma", "09/01/2020",
                "cold", "6786");
        productInfoList.add(productInfoPojo);
        productInfoPojo = new ProductInfoPojo("ap908098", "DOLO-650", "FMCG", "Pharma", "12/12/1020",
                "fever", "21323");
        productInfoList.add(productInfoPojo);

    }

    private void getBatchInfo() {
        productBatchList = new ArrayList<>();
        ProductBatchPojo productBatchPojo = new ProductBatchPojo("122434", "20/20/2020", "78", "10", "2");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("7867678", "20/14/2019", "58", "12", "1");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("122434", "20/20/2020", "78", "10", "2");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("7867678", "20/14/2019", "58", "12", "1");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("122434", "20/20/2020", "78", "10", "2");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("7867678", "20/14/2019", "58", "12", "1");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("122434", "20/20/2020", "78", "10", "2");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("7867678", "20/14/2019", "58", "12", "1");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("122434", "20/20/2020", "78", "10", "2");
        productBatchList.add(productBatchPojo);
        productBatchPojo = new ProductBatchPojo("7867678", "20/14/2019", "58", "12", "1");
        productBatchList.add(productBatchPojo);
    }

    private void getEditTextInfo() {
        searchProductBinding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchProductBinding.productInfoRecycler.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchProductBinding.editSearch.length() >= 3) {
                    searchProductBinding.productInfoRecycler.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(ProductInfoPojo productInfoPojo) {
        searchProductBinding.batchInfoRecycler.setVisibility(View.VISIBLE);
    }
}
