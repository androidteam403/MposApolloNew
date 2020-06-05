package com.apollopharmacy.mpospharmacist.ui.storesetup.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.DialogAllDoctorsBinding;
import com.apollopharmacy.mpospharmacist.databinding.DialogStoreListBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseDialog;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.adapter.AllDoctorsListAdapter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialogMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialogMvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupMvpView;
import com.apollopharmacy.mpospharmacist.ui.storesetup.adapter.GetStoresListAdapter;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreListResponseModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class GetStoresDialog extends BaseDialog implements GetStoresDialogMvpView {
    private DialogStoreListBinding storeListBinding;
    private ArrayList<StoreListResponseModel.StoreListObj> storesArrList = new ArrayList<>();
    private StoreSetupMvpView storeSetupMvpView;

    @Inject
    GetStoresDialogMvpPresenter<GetStoresDialogMvpView> mPresenter;

    public static GetStoresDialog newInstance() {
        GetStoresDialog dialog = new GetStoresDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }


    public void setStoreListArray(ArrayList<StoreListResponseModel.StoreListObj> storesArrList) {
        this.storesArrList = storesArrList;
    }

    public void setStoreDetailsMvpView(StoreSetupMvpView detailsMvpView) {
        this.storeSetupMvpView = detailsMvpView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        storeListBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_store_list, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(GetStoresDialog.this);
        return storeListBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        storeListBinding.setCallback(mPresenter);
        GetStoresListAdapter storesListAdapter = new GetStoresListAdapter(getActivity(), storesArrList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        storeListBinding.storesRecyclerView.setLayoutManager(mLayoutManager);
        storesListAdapter.onClickListener(this);
        storeListBinding.storesRecyclerView.setAdapter(storesListAdapter);
        storeListBinding.doctorNameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                storesListAdapter.getFilter().filter(s);
            }
        });
    }

    @Override
    public void openLoginActivity() {

    }

    @Override
    public void dismissDialog() {
        dismissDialog("");
        storeSetupMvpView.dialogCloseListiner();
    }

    @Override
    public void onClickListener(StoreListResponseModel.StoreListObj item) {
        storeSetupMvpView.onSelectStore(item);
        dismissDialog("");
        storeSetupMvpView.dialogCloseListiner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        storeSetupMvpView.dialogCloseListiner();
    }
}
