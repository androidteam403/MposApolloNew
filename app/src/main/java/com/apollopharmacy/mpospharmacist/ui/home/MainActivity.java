package com.apollopharmacy.mpospharmacist.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacist.utils.DownloadController;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

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
        toolbar.setTitleTextAppearance(this,R.style.RobotoBoldTextAppearance);
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
                    navController.navigate(R.id.nav_dash_board, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_doc_master) {
                    navController.navigate(R.id.nav_doc_master, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_cust_master) {
                    navController.navigate(R.id.nav_cust_master, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_billing) {
                    navController.navigate(R.id.nav_billing, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_order) {
                    navController.navigate(R.id.nav_order, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_manual_billing) {
                    Singletone.getInstance().isManualBilling = true;
                    navController.navigate(R.id.nav_manual_billing, null, navOptions, null);
                }

                return true;
            }
        });

        TextView logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(view1 -> {
            if (mAppBarConfiguration.getDrawerLayout() != null) {
                mAppBarConfiguration.getDrawerLayout().closeDrawers();
            }
            logoutDialog();
        });
        setUp();
    }

    @Override
    protected void setUp() {
        userName.setText(mvpPresenter.getLoginUserName());
        userStoreLocation.setText(mvpPresenter.getLoinStoreLocation());

     //   mvpPresenter.onCheckBuildDetails();
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
            if (Singletone.getInstance().isManualBilling)
                navController.navigate(R.id.nav_manual_billing);
            else
                navController.navigate(R.id.nav_billing);
            Singletone.getInstance().isPlaceNewOrder = false;
        } else if (Singletone.getInstance().isOrderCompleted) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_dash_board);
            Singletone.getInstance().isOrderCompleted = false;
        }
    }

    @Override
    public void navigateLoginActivity() {
        startActivity(PharmacistLoginActivity.getStartIntent(this));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private DownloadController downloadController;
    @Override
    public void displayAppInfoDialog(String title, String subTitle, String positiveBtn, String negativeBtn) {
        ExitInfoDialog dialogView = new ExitInfoDialog(MainActivity.this);
        dialogView.setTitle(title);
        dialogView.setSubtitle(subTitle);
        if (!TextUtils.isEmpty(positiveBtn)) {
            dialogView.setPositiveLabel(positiveBtn);
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
//                    UpdateApp atualizaApp = new UpdateApp();
//                    atualizaApp.setContext(getApplicationContext());
//                    atualizaApp.execute("http://serverurl/appfile.apk");
                    startDownload();

                }
            });
        }
        if (!TextUtils.isEmpty(negativeBtn)) {
            dialogView.setNegativeLabel(negativeBtn);
            dialogView.setNegativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogView.dismiss();
                }
            });
        } else {
            dialogView.setDialogDismiss();
        }

        dialogView.show();
    }


    private static final int PERMISSION_REQUEST_CODE = 1;

    private void startDownload() {
        if (checkPermission()) {
            // start downloading
            downloadController.enqueueDownload();
        } else {
            requestPermission(); // Code for permission
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    private void logoutDialog(){
        ExitInfoDialog dialogView = new ExitInfoDialog(MainActivity.this);
        dialogView.setTitle("Alert");
        dialogView.setSubtitle("Are you sure want to logout the application ?");
        dialogView.setPositiveLabel("Yes");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                mvpPresenter.logoutUser();

            }
        });
        dialogView.setNegativeLabel("No");
        dialogView.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });

        dialogView.show();
    }
}
