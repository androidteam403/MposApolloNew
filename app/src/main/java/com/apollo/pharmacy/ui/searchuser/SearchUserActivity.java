package com.apollo.pharmacy.ui.searchuser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivitySearchProductBinding;
import com.apollo.pharmacy.databinding.ActivitySearchUserBinding;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.searchproduct.SearchProductMvpPresenter;
import com.apollo.pharmacy.ui.searchproduct.SearchProductMvpView;
import com.apollo.pharmacy.ui.searchproduct.adapter.BatchInfoAdapter;
import com.apollo.pharmacy.ui.searchproduct.adapter.ProductInfoAdapter;
import com.apollo.pharmacy.ui.searchproduct.listener.SearchProductListener;
import com.apollo.pharmacy.ui.searchproduct.model.ProductBatchPojo;
import com.apollo.pharmacy.ui.searchproduct.model.ProductInfoPojo;
import com.apollo.pharmacy.ui.searchuser.adapter.SearchCustomerAdapter;
import com.apollo.pharmacy.ui.searchuser.model.SearchCustomerAdapterModel;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.apollo.pharmacy.root.ApolloMposApp.getContext;

public class SearchUserActivity extends BaseActivity implements SearchUserMvpView {

    @Inject
    SearchUserMvpPresenter<SearchUserMvpView> mPresenter;
    private ActivitySearchUserBinding searchUserBinding;

    private ArrayList<SearchCustomerAdapterModel> arrSearchCustomerAdapterModel = null;
    private SearchCustomerAdapter searchCustomerAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchUserActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_user);
        getActivityComponent().inject(this);
        mPresenter.onAttach(SearchUserActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        getAddressList();
        if (arrSearchCustomerAdapterModel.size() > 0) {
            searchCustomerAdapter = new SearchCustomerAdapter(this, arrSearchCustomerAdapterModel, mPresenter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            searchUserBinding.recyclerView.setLayoutManager(mLayoutManager);
            searchUserBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            searchUserBinding.recyclerView.setAdapter(searchCustomerAdapter);
        } else {
            searchUserBinding.addCustomer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClickAdd() {

    }

    private void getAddressList() {
        arrSearchCustomerAdapterModel = new ArrayList<>();
//        SearchCustomerAdapterModel searchCustomerAdapterModel = new SearchCustomerAdapterModel("naveen", "male","9177551736", "25 sep 1996");
//        arrSearchCustomerAdapterModel.add(searchCustomerAdapterModel);
//        searchCustomerAdapterModel = new SearchCustomerAdapterModel("abc", "female","9478738378", "25 jan 1999");
//        arrSearchCustomerAdapterModel.add(searchCustomerAdapterModel);
//        searchCustomerAdapterModel = new SearchCustomerAdapterModel("def", "male","9479858378", "25 feb 1899");
//        arrSearchCustomerAdapterModel.add(searchCustomerAdapterModel);
//        searchCustomerAdapterModel = new SearchCustomerAdapterModel("ghi", "female","9167455894", "25 dec 2002");
//        arrSearchCustomerAdapterModel.add(searchCustomerAdapterModel);
    }
}
