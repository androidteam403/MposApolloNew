package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ProductListActivityBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.adapter.ProductListAdapter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.ProductList;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

public class ProductListActivity extends BaseActivity implements ProductListMvpView {
    @Inject
    ProductListMvpPresenter<ProductListMvpView> productListMvpPresenter;
    ProductListActivityBinding productListActivityBinding;

    private ArrayList<ProductList> productLists = null;
    private ProductListAdapter productListAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProductListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
              getSupportActionBar().hide();
        }
        productListActivityBinding = DataBindingUtil.setContentView(this, R.layout.product_list_activity);
        getActivityComponent().inject(this);
        productListMvpPresenter.onAttach(ProductListActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        productListActivityBinding.setCallback(productListMvpPresenter);
        getProductInfo();
        if (productLists.size() > 0) {
            productListAdapter = new ProductListAdapter(this, productLists);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            productListActivityBinding.productRecycler.setLayoutManager(mLayoutManager);
            productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
            productListActivityBinding.productRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
            productListActivityBinding.productRecycler.setAdapter(productListAdapter);
        }
    }

    private void getProductInfo() {
        productLists = new ArrayList<>();
        ProductList productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);
        productListPojo = new ProductList("Art Code\nDOL001", "Description\nDoloBank Tab",
                "Category\nPharma", "Sub Category\nTablet", "Manufacture\nMicro Labs Pharma..",
                "Generic Name\n-----");
        productLists.add(productListPojo);


    }

    @Override
    public void onClickBackBtn() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
