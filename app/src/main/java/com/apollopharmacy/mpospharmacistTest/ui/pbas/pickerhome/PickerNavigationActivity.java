package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityNavigation3PBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

public class PickerNavigationActivity extends BaseActivity implements PickerNavigationMvpView {

    public static PickerNavigationActivity mInstance;

    @Inject
    PickerNavigationMvpPresenter<PickerNavigationMvpView> mPresenter;
    public ActivityNavigation3PBinding activityNavigation3Binding;
    private AppBarConfiguration mAppBarConfiguration;
    TextView userName;
    TextView userStore;
    public PickerNavigationActivityCallback pickerNavigationActivityCallback;
    NavController navController;
    NavOptions navOptions;

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
        mInstance = this;
//        activityNavigation3Binding.setCallback(mPresenter);
        setSupportActionBar(activityNavigation3Binding.appBarMain.toolbar);


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_dashboard)
                .setDrawerLayout(activityNavigation3Binding.drawerLayout)
                .build();


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(activityNavigation3Binding.navView, navController);

        navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_from_right)
                .setExitAnim(R.anim.slide_to_left)
                .setPopEnterAnim(R.anim.slide_from_right)
                .setPopExitAnim(R.anim.slide_to_left)
                .build();

        activityNavigation3Binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (mAppBarConfiguration.getDrawerLayout() != null) {
                    mAppBarConfiguration.getDrawerLayout().closeDrawers();
                }

                System.out.println("openscren status--->" + menuItem.getItemId());
                if (menuItem.getItemId() == R.id.nav_dashboard) {
                    navController.navigate(R.id.nav_dashboard, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_picker_vtwo) {
                    navController.navigate(R.id.nav_picker_vtwo, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_packer_vtwo) {
                    navController.navigate(R.id.nav_packer_vtwo, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_biller_vtwo) {
                    navController.navigate(R.id.nav_biller_vtwo, null, navOptions, null);
                }

                return true;
            }
        });

        activityNavigation3Binding.appBarMain.icFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickerNavigationActivityCallback != null)
                    pickerNavigationActivityCallback.onClickFilters();
            }
        });
        activityNavigation3Binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });


    }

    public void navigateToOpenOrders() {
        navController.navigate(R.id.nav_picker_vtwo, null, navOptions, null);
    }

    public void setTitle(String tittle) {
        activityNavigation3Binding.appBarMain.title.setText(tittle);
    }

    public void setWelcome(String welcomeText) {
        activityNavigation3Binding.appBarMain.welcome.setText(welcomeText);
    }

    private void logoutDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(PickerNavigationActivity.this);
        dialogView.setTitle("Alert");
        dialogView.setSubtitle("Are you sure want to logout the application ?");
        dialogView.setPositiveLabel("Yes");
        dialogView.setPositiveListener(view -> {
            dialogView.dismiss();
            mPresenter.logoutUser();

        });
        dialogView.setNegativeLabel("No");
        dialogView.setNegativeListener(v -> dialogView.dismiss());

        dialogView.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_p, menu);
        userName = findViewById(R.id.login_user_name);
        userStore = findViewById(R.id.login_user_store);

//        userStore = findViewById(R.id.user_store);

        userName.setText( mPresenter.getLoginUserName());
//        userStore.setText("Terminal ID - ");
       userStore.setText( mPresenter.getLoinStoreLocation());
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void navigateLoginActivity() {
        startActivity(PharmacistLoginActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();

    }

    public interface PickerNavigationActivityCallback {
        void onClickFilters();

        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (pickerNavigationActivityCallback != null)
            pickerNavigationActivityCallback.onActivityResult(requestCode, resultCode, data);
    }
}