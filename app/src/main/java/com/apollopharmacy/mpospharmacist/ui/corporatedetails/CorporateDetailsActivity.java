package com.apollopharmacy.mpospharmacist.ui.corporatedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCorporateDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.adapter.CorporateDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.SwipeController;
import com.apollopharmacy.mpospharmacist.utils.SwipeControllerActions;

import java.util.ArrayList;

import javax.inject.Inject;

public class CorporateDetailsActivity extends BaseActivity implements CorporateDetailsMvpView {
    @Inject
    CorporateDetailsMvpPresenter<CorporateDetailsMvpView> mPresenter;
    ActivityCorporateDetailsBinding corporateDetailsBinding;

    private CorporateDetailAdapter corporateDetailAdapter;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList;
    private ArrayList<CorporateModel.DropdownValueBean> tempCorporateList = new ArrayList<>();

    public static Intent getStartIntent(Context context,CorporateModel corporateModel) {
        Intent intent = new Intent(context, CorporateDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("corporate_list", corporateModel);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, CorporateModel.DropdownValueBean corporateEntity,CorporateModel corporateModel) {
        Intent intent = new Intent(context, CorporateDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("corporate_list", corporateModel);
        bundle.putSerializable("corporate_info", corporateEntity);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        corporateDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_corporate_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(CorporateDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        corporateDetailsBinding.setCallback(mPresenter);
        CorporateModel corporateModel  = (CorporateModel )getIntent().getSerializableExtra("corporate_list");
        if(corporateModel != null) {
            getCorporateList(corporateModel);
        }
        corporateDetailsBinding.corporateNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (corporateDetailAdapter != null) {
                    corporateDetailAdapter.getFilter().filter(s);
                }
            }
        });

        corporateDetailsBinding.corporateParentView.setOnTouchListener((v, event) -> {
            CommonUtils.hideKeyboard(CorporateDetailsActivity.this);
            return false;
        });
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        if(corporateModel.get_DropdownValue()!= null && corporateModel.get_DropdownValue().size() > 0) {
            corporateList = new ArrayList<>();
            tempCorporateList.clear();
            corporateList.addAll(corporateModel.get_DropdownValue());
            tempCorporateList.addAll(corporateModel.get_DropdownValue());
            corporateDetailAdapter = new CorporateDetailAdapter(this, corporateList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            corporateDetailsBinding.corporateRecyclerView.setLayoutManager(mLayoutManager);
            corporateDetailAdapter.setClickListener(this);
            corporateDetailsBinding.corporateRecyclerView.setAdapter(corporateDetailAdapter);
        }
    }

    @Override
    public void showNotFoundCorporate() {
        corporateDetailsBinding.corporateRecyclerView.setVisibility(View.GONE);
        corporateDetailsBinding.noCorporateFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickCorporateItem(CorporateModel.DropdownValueBean item) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("corporate_info", item);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
