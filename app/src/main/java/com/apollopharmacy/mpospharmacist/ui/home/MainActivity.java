package com.apollopharmacy.mpospharmacist.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityMvpView {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView userName, userStoreLocation;
    @Inject
    MainActivityMvpPresenter<MainActivityMvpView> mvpPresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(MainActivity.this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        userName = view.findViewById(R.id.login_user_name);
        userStoreLocation = view.findViewById(R.id.login_user_store);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dash_board, R.id.nav_doc_master, R.id.nav_cust_master,
                R.id.nav_billing, R.id.nav_order, R.id.nav_manual_billing)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setUp();
    }

    @Override
    protected void setUp() {
        userName.setText(mvpPresenter.getLoginUserName());
        userStoreLocation.setText(mvpPresenter.getLoinStoreLocation());
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Singletone.getInstance().isPlaceNewOrder) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.popBackStack();
            navController.navigate(R.id.nav_billing);
            Singletone.getInstance().isPlaceNewOrder = false;
        } else if (Singletone.getInstance().isOrderCompleted) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_dash_board);
            Singletone.getInstance().isOrderCompleted = false;
        }
    }
}
