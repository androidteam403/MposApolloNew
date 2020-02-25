package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ProductListActivityBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.adapter.ProductListAdapter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

public class ProductListActivity extends BaseActivity implements ProductListMvpView {
    @Inject
    ProductListMvpPresenter<ProductListMvpView> productListMvpPresenter;
    ProductListActivityBinding productListActivityBinding;
    private ProductListAdapter productListAdapter;
    private ArrayList<GetItemDetailsRes.Items> itemsArrayList = new ArrayList<>();
    private boolean isLoadApi = false;
    private int ACTIVITY_RESULT_FOR_BATCH_INFO = 103;
    public static Intent getStartIntent(Context context, CorporateModel.DropdownValueBean corporateModule) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("corporate_info", corporateModule);
        return intent;
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
        productListAdapter = new ProductListAdapter(this, itemsArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        productListActivityBinding.productRecycler.setLayoutManager(mLayoutManager);
        productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
        productListActivityBinding.productRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
        productListAdapter.setClickListiner(this);
        productListActivityBinding.productRecycler.setAdapter(productListAdapter);
        productListActivityBinding.searchProductEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 3 && isLoadApi){
                    productListMvpPresenter.getProductDetails();
                }else if(s.length() >= 3){
                    productListAdapter.getFilter().filter(s);
                }else{
                    if(s.length() == 0){
                        updateProductsCount(0);
                        itemsArrayList.clear();
                        productListAdapter.notifyDataSetChanged();
                    }
                    isLoadApi = true;
                }
            }
        });
    }


    @Override
    public void onClickBackBtn() {
        onBackPressed();
    }

    @Override
    public void onClickProductItem(GetItemDetailsRes.Items item) {
        startActivityForResult(BatchInfoActivity.getStartIntent(this,item),ACTIVITY_RESULT_FOR_BATCH_INFO);
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
    public CorporateModel.DropdownValueBean getCorporateValue() {
        return (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
    }

    @Override
    public void setEmptyErrorOnSearch(String message) {
        productListActivityBinding.inputLayoutSearch.setError(message);
    }

    @Override
    public void onSuccessGetItems(GetItemDetailsRes itemDetailsRes) {
        isLoadApi = false;
        updateProductsCount(itemDetailsRes.getItemList().size());
        itemsArrayList.addAll(itemDetailsRes.getItemList()) ;
        productListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedGetItems(GetItemDetailsRes itemDetailsRes) {

    }

    @Override
    public void updateProductsCount(int count) {
        productListActivityBinding.setProductCount(count);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == ACTIVITY_RESULT_FOR_BATCH_INFO){
                if(data != null) {
                    GetItemDetailsRes.Items items = (GetItemDetailsRes.Items) data.getSerializableExtra("selected_item");
                    Intent intent = new Intent();
                    intent.putExtra("selected_item", items);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }
}
