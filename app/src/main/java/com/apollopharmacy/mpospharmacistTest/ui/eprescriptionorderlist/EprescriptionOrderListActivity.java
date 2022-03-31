package com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityEprescriptionorderlistBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.adaptor.EprescriptionListRecycleAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.model.OMSTransactionHeaderResModel;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class EprescriptionOrderListActivity extends BaseActivity implements EprescriptionOrderListMvpView
{
    @Inject
    EprescriptionOrderListMvpPresenter<EprescriptionOrderListMvpView> mPresenter;
    private ActivityEprescriptionorderlistBinding activityEprescriptionorderlistBinding;
    private EprescriptionListRecycleAdapter orderListRecycleAdapter;
    private static final String TAG = EprescriptionOrderListActivity.class.getSimpleName();
    private ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> ordersArrayList = new ArrayList<>();

    private ArrayList<CorporateModel.DropdownValueBean> corporateList;
    private GetCustomerResponse.CustomerEntity customerEntity;
    CorporateModel corporateModel;

    private final int ACTIVITY_EPRESCRIPTIONLIST_DETAILS_CODE = 121;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, EprescriptionOrderListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, GetCustomerResponse.CustomerEntity customerEntity, CorporateModel corporateModel) {
        Intent intent = new Intent(context, EprescriptionOrderListActivity.class);
        intent.putExtra("customer_model", customerEntity);
        intent.putExtra("corporate_model", corporateModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        activityEprescriptionorderlistBinding= DataBindingUtil.setContentView(this, R.layout.activity_eprescriptionorderlist);
        getActivityComponent().inject(this);
        mPresenter.onAttach(EprescriptionOrderListActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityEprescriptionorderlistBinding.setCallback(mPresenter);

        corporateModel = (CorporateModel) getIntent().getSerializableExtra("corporate_model");
        customerEntity=(GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_model");


        orderListRecycleAdapter = new EprescriptionListRecycleAdapter(this, ordersArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        activityEprescriptionorderlistBinding.eprescriptionorderRecycle.setLayoutManager(mLayoutManager);
        activityEprescriptionorderlistBinding.eprescriptionorderRecycle.setItemAnimator(new DefaultItemAnimator());
        activityEprescriptionorderlistBinding.eprescriptionorderRecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));
        activityEprescriptionorderlistBinding.eprescriptionorderRecycle.setItemAnimator(new DefaultItemAnimator());
        activityEprescriptionorderlistBinding.eprescriptionorderRecycle.setAdapter(orderListRecycleAdapter);

        mPresenter.fetchOMSOrderList();

        activityEprescriptionorderlistBinding.searchOrderNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    orderListRecycleAdapter.getFilter().filter(editable);
                } else {
                    orderListRecycleAdapter.getFilter().filter("");
                    orderListRecycleAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public  void PickingOrderPressed()
    {
        mPresenter.fetchOMSOrderList();
    }

    @Override
    public  void  PackingOrderPressed()
    {
        mPresenter.fetchOMSOrderList();
    }

    @Override
    public  void InvoiceOrderPressed()
    {
        mPresenter.fetchOMSOrderList();
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    public void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }


    @Override
    public void onOrderItemClick(int position, OMSTransactionHeaderResModel.OMSHeaderObj item) {
         //finish();
      //  startActivityForResult(EPrescriptionInfoInfoActivity.getStartIntent(this, item,corporateModel,customerEntity),ACTIVITY_EPRESCRIPTIONLIST_DETAILS_CODE);
      //  overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    @Override
    public void onSuccessGetOMSTransactionList(OMSTransactionHeaderResModel response) {
        if (response.getOMSHeaderArr().size() > 0) {
            ordersArrayList.addAll(response.getOMSHeaderArr());
            orderListRecycleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_EPRESCRIPTIONLIST_DETAILS_CODE) {
            if (resultCode == RESULT_OK) {
                if(data != null)
                {
                    System.out.println("Activity result--> eorderliat"+(ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item"));
                    ArrayList <SalesLineEntity> salesentity=new ArrayList<>();
                    CorporateModel.DropdownValueBean item = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                    customerEntity=(GetCustomerResponse.CustomerEntity)data.getSerializableExtra("customer_info");
                    salesentity = (ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item");
                    OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem=(OMSTransactionHeaderResModel.OMSHeaderObj)data.getSerializableExtra("fullfillment_id");
                    CustomerDataResBean customerDataResBean=(CustomerDataResBean)data.getSerializableExtra("customer_bean");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("corporate_info", item);
                    bundle.putSerializable("selected_item", salesentity);
                    bundle.putSerializable("customer_info", customerEntity);
                    bundle.putSerializable("fullfillment_id", orderinfoitem);
                    bundle.putSerializable("customer_bean", customerDataResBean);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        }
    }

}
