package com.apollopharmacy.mpospharmacist.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.DashBoardFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling.ManualBillingMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.manualbilling.ManualBillingMvpView;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityMvpView{

    private AppBarConfiguration mAppBarConfiguration;
    private TextView userName,userStoreLocation;
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

        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_from_right)
                .setExitAnim(R.anim.slide_to_left)
                .setPopEnterAnim(R.anim.slide_from_right)
                .setPopExitAnim(R.anim.slide_to_left)
                .build();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (mAppBarConfiguration.getDrawerLayout() != null) {
                    mAppBarConfiguration.getDrawerLayout().closeDrawers();
                }
                Singletone.getInstance().isManualBilling = false;
                if (menuItem.getItemId() == R.id.nav_dash_board) {
                    navController.navigate(R.id.nav_dash_board,null,navOptions,null);
                }else if (menuItem.getItemId() == R.id.nav_doc_master) {
                    navController.navigate(R.id.nav_doc_master,null,navOptions,null);
                }else if (menuItem.getItemId() == R.id.nav_cust_master) {
                    navController.navigate(R.id.nav_cust_master,null,navOptions,null);
                }else if (menuItem.getItemId() == R.id.nav_billing) {
                    navController.navigate(R.id.nav_billing,null,navOptions,null);
                }else if (menuItem.getItemId() == R.id.nav_order) {
                    navController.navigate(R.id.nav_order,null,navOptions,null);
                }else if (menuItem.getItemId() == R.id.nav_manual_billing) {
                    Singletone.getInstance().isManualBilling = true;
                    navController.navigate(R.id.nav_manual_billing,null,navOptions,null);
                }

                return true;
            }
        });
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
        if(Singletone.getInstance().isPlaceNewOrder) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.popBackStack();
            if(Singletone.getInstance().isManualBilling)
                navController.navigate(R.id.nav_manual_billing);
            else
                navController.navigate(R.id.nav_billing);
            Singletone.getInstance().isPlaceNewOrder = false;
        }
    }
}
