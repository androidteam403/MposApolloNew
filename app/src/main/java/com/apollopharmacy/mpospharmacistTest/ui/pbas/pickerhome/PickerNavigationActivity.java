package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityNavigation3PBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;

import javax.inject.Inject;

public class PickerNavigationActivity extends BaseActivity implements PickerNavigationMvpView {

    @Inject
    PickerNavigationMvpPresenter<PickerNavigationMvpView> mPresenter;
    ActivityNavigation3PBinding activityNavigation3Binding;
    private AppBarConfiguration mAppBarConfiguration;
    TextView userName;
    TextView userStore;

    public static Intent getStartIntent(Context mContext) {
        Intent intent = new Intent(mContext, PickerNavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNavigation3Binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation3_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PickerNavigationActivity.this);
        setUp();


    }

    @Override
    protected void setUp() {
//        activityNavigation3Binding.setCallback(mPresenter);
        setSupportActionBar(activityNavigation3Binding.appBarMain.toolbar);


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_dashboard)
                .setDrawerLayout(activityNavigation3Binding.drawerLayout)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(activityNavigation3Binding.navView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_p, menu);
        userName = findViewById(R.id.user_name);
//        userStore = findViewById(R.id.user_store);

        userName.setText(mPresenter.getLoginUserName());
//        userStore.setText(mPresenter.getLoinStoreLocation());
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}