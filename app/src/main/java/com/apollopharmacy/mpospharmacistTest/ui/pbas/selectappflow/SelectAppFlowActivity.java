package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivitySelectAppFlowPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.adapter.SelectAppFlowListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.model.SelectAppFlowModel;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SelectAppFlowActivity extends BaseActivity implements SelectAppFlowMvpView {
    @Inject
    SelectAppFlowMvpPresenter<SelectAppFlowMvpView> mPresenter;
    private ActivitySelectAppFlowPBinding selectAppFlowBinding;
    private SelectAppFlowListAdapter selectAppFlowListAdapter;
    private final List<SelectAppFlowModel> selectAppFlowModelList = new ArrayList<>();
    private String userType;

    public static Intent getStartActivity(Context mContext) {
        Intent intent = new Intent(mContext, SelectAppFlowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectAppFlowBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_app_flow_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(SelectAppFlowActivity.this);
        setUp();

    }

    @Override
    protected void setUp() {
        List<UserModel._DropdownValueBean> loginUserResult = mPresenter.getLoginUserResult();
        for (int i = 0; i < loginUserResult.size(); i++) {
            if (mPresenter.getUserId().equalsIgnoreCase(loginUserResult.get(i).getCode())) {
                userType = loginUserResult.get(i).getUserType();
            }
        }
        getSelectAppFlowModelList();
        selectAppFlowBinding.setCallback(mPresenter);
        selectAppFlowListAdapter = new SelectAppFlowListAdapter(this, selectAppFlowModelList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        selectAppFlowBinding.appFlowListRecycler.setLayoutManager(mLayoutManager);
        selectAppFlowBinding.appFlowListRecycler.setAdapter(selectAppFlowListAdapter);
    }

    private void getSelectAppFlowModelList() {
        SelectAppFlowModel selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Picker");
        selectAppFlowModel.setSelected(true);
        selectAppFlowModelList.add(selectAppFlowModel);

        selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Checker");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);

        /*selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Biller");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);*/

        selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Shipping Label");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);

        if (userType.equalsIgnoreCase("1")) {
            selectAppFlowModel = new SelectAppFlowModel();
            selectAppFlowModel.setAppFlowName("Admin");
            selectAppFlowModel.setSelected(false);
            selectAppFlowModelList.add(selectAppFlowModel);
        }

        selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Orders");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);

       /* selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Stock inward process");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);*/

        /*selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Sealer");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);*/

        /*selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Admin");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);*/
    }

    @Override
    public void onClickContinue() {
        for (int i = 0; i < selectAppFlowModelList.size(); i++) {
            if (selectAppFlowModelList.get(i).isSelected()) {
                switch (selectAppFlowModelList.get(i).getAppFlowName()) {
                    case "Picker":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "PICKER"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Checker":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "PACKER"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Biller":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "BILLER"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Shipping Label":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "SHIPPING_LABEL"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Admin":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "ADMIN"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Orders":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "ORDERS"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Stock inward process":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "STOCK_INWARD_PROCESS"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Sealer":
                        break;

                    default:
                }
                break;
            }
        }
    }

    @Override
    public void onClickSelectAppFlowItem(int pos) {
        for (int i = 0; i < selectAppFlowModelList.size(); i++) {
            selectAppFlowModelList.get(i).setSelected(i == pos);
        }
        selectAppFlowListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickLogout() {
        Intent intent = new Intent(SelectAppFlowActivity.this, PharmacistLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }
}



