package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentShippingLabelBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;

import java.util.List;

import javax.inject.Inject;

public class ShippingLabelFragment extends BaseFragment implements ShippingLabelMvpView , PickerNavigationActivity.PickerNavigationActivityCallback{
    @Inject
    ShippingLabelMvpPresenter<ShippingLabelMvpView> mPresenter;
    private FragmentShippingLabelBinding shippingLabelBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        shippingLabelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shipping_label, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ShippingLabelFragment.this);
        return shippingLabelBinding.getRoot();

    }

    @Override
    protected void setUp(View view) {
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Shipping Label Orders");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.setWelcome("Total " + "0" + " orders");

        mPresenter.getJounalTransactionApiCall();
    }

    @Override
    public void onClickFilters() {

    }

    @Override
    public void onItemClick() {

    }

    @Override
    public void onClickStockAvailable(boolean isStockAvailableChecked) {

    }

    @Override
    public void onSuccessJounalTransactonApi(List<CalculatePosTransactionRes> calculatePosTransactionRes) {

    }
}
