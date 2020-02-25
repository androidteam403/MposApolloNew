package com.apollopharmacy.mpospharmacist.ui.searchuser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivitySearchUserBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.AddCustomerActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.searchuser.adapter.SearchCustomerAdapter;
import com.apollopharmacy.mpospharmacist.ui.searchuser.model.SearchCustomerAdapterModel;

import java.util.ArrayList;

import javax.inject.Inject;

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
        searchUserBinding.setCallBack(mPresenter);
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
        startActivity(AddCustomerActivity.getStartIntent(this, ""));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
