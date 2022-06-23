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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SelectAppFlowActivity extends BaseActivity implements SelectAppFlowMvpView {
    @Inject
    SelectAppFlowMvpPresenter<SelectAppFlowMvpView> mPresenter;
    private ActivitySelectAppFlowPBinding selectAppFlowBinding;
    private SelectAppFlowListAdapter selectAppFlowListAdapter;
    private final List<SelectAppFlowModel> selectAppFlowModelList = new ArrayList<>();

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
        selectAppFlowModel.setAppFlowName("Packer");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);

        selectAppFlowModel = new SelectAppFlowModel();
        selectAppFlowModel.setAppFlowName("Biller");
        selectAppFlowModel.setSelected(false);
        selectAppFlowModelList.add(selectAppFlowModel);

//        selectAppFlowModel = new SelectAppFlowModel();
//        selectAppFlowModel.setAppFlowName("Sealer");
//        selectAppFlowModel.setSelected(false);
//        selectAppFlowModelList.add(selectAppFlowModel);
//
//        selectAppFlowModel = new SelectAppFlowModel();
//        selectAppFlowModel.setAppFlowName("Admin");
//        selectAppFlowModel.setSelected(false);
//        selectAppFlowModelList.add(selectAppFlowModel);
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
                    case "Packer":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "PACKER"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Biller":
                        startActivity(PickerNavigationActivity.getStartIntent(SelectAppFlowActivity.this, "BILLER"));
                        overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
                        break;
                    case "Sealer":
                        break;
                    case "Admin":
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
}



