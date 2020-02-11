package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ProductListActivityBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.adapter.ProductListAdapter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.ProductList;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

public class ProductListActivity extends BaseActivity implements ProductListMvpView {
    @Inject
    ProductListMvpPresenter<ProductListMvpView> productListMvpPresenter;
    ProductListActivityBinding productListActivityBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProductListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productListActivityBinding = DataBindingUtil.setContentView(this, R.layout.product_list_activity);
        getActivityComponent().inject(this);
        productListMvpPresenter.onAttach(ProductListActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        productListActivityBinding.setCallback(productListMvpPresenter);


    }


    @Override
    public void onClickBackBtn() {
        onBackPressed();
    }

    @Override
    public void onClickProductItem(GetItemDetailsRes.Items item) {
        startActivity(BatchInfoActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public String getSearchProductKey() {
        if (TextUtils.isEmpty(Objects.requireNonNull(productListActivityBinding.searchProductEditText.getText()).toString())) {
            setEmptyErrorOnSearch("Enter product Name");
            return null;
        } else {
            productListActivityBinding.inputLayoutSearch.setError(null);
        }
        return productListActivityBinding.searchProductEditText.getText().toString();
    }

    @Override
    public void setEmptyErrorOnSearch(String message) {
        productListActivityBinding.inputLayoutSearch.setError(message);
    }

    @Override
    public void onSuccessGetItems(GetItemDetailsRes itemDetailsRes) {
        productListActivityBinding.setProductCount(itemDetailsRes.getItemList().size());
        ProductListAdapter productListAdapter = new ProductListAdapter(this, itemDetailsRes.getItemList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        productListActivityBinding.productRecycler.setLayoutManager(mLayoutManager);
        productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
        productListActivityBinding.productRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
        productListAdapter.setClickListiner(this);
        productListActivityBinding.productRecycler.setAdapter(productListAdapter);
    }

    @Override
    public void onFailedGetItems(GetItemDetailsRes itemDetailsRes) {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
