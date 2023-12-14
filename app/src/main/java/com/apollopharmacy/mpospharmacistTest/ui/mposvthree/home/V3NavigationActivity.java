package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityV3NavigationBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.OpenOrdersV3Activity;

import javax.inject.Inject;

public class V3NavigationActivity extends BaseActivity implements V3NavigationMvpView {
    public ActivityV3NavigationBinding v3NavigationBinding;
    public static V3NavigationActivity mInstance;
    private Fragment fragment = null;
    public V3NavigationActivityCallback callback;

    @Inject
    V3NavigationMvpPresenter<V3NavigationMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v3NavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_v3_navigation);
        getActivityComponent().inject(this);
        mPresenter.onAttach(V3NavigationActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        mInstance = this;
        displaySelectedScreen("Picker");

        v3NavigationBinding.icFilter.setOnClickListener(view -> {
            if (callback != null) {
                callback.onClickFilter();
            }
        });
    }

    private void displaySelectedScreen(String name) {
        switch (name) {
            case "Picker":
                fragment = new OpenOrdersV3Activity();
                break;
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    public void setWelcome(String welcomeText) {
        v3NavigationBinding.welcome.setText(welcomeText);
    }

    public void setTitle(String tittle) {
        v3NavigationBinding.title.setText(tittle);
    }

    public void setStockAvailableVisibilty(boolean isVisible) {
        if (isVisible)
            v3NavigationBinding.stockAvailableCheckbox.setVisibility(View.VISIBLE);
        else
            v3NavigationBinding.stockAvailableCheckbox.setVisibility(View.GONE);
    }

    public interface V3NavigationActivityCallback {
        void onClickFilter();

        void onItemClick();

        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

        void onClickStockAvailable(boolean isStockAvailableChecked);

        void onClicklabelSizeIcon();

        void onClickRefresh();

        void onClickUnHold();

        void onClickRefreshPickerPackerBiller();
    }
}