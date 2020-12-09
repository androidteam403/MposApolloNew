package com.apollopharmacy.mpospharmacist.ui.home;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacist.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.home.dialog.KioskExitClickListener;
import com.apollopharmacy.mpospharmacist.ui.home.dialog.KioskExitDialog;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.DownloadController;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;
import com.apollopharmacy.mpospharmacist.utils.MyAdmin;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityMvpView {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView userName, userStoreLocation;
    private CorporateModel corporateModel;
    private NavigationView navigationView;
    private Stack<Fragment> fragmentStack = new Stack<>();
    private ImageView imageView;
    private DrawerLayout drawer;

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
        toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(MainActivity.this);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imageView = findViewById(R.id.image_view);
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
                if (menuItem.isChecked()) return false;
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
//                    Singletone.getInstance().isManualBilling = true;
//                    navController.navigate(R.id.nav_manual_billing, null, navOptions, null);
                } else if (menuItem.getItemId() == R.id.nav_exit_kiosk) {
                    KioskExitDialog adminPwdDialog = new KioskExitDialog(MainActivity.this, mvpPresenter);
                    adminPwdDialog.setPositiveClickListener(new KioskExitClickListener() {
                        @Override
                        public void onClickPositiveBtn() {
                            if (isAppInLockTaskMode()) {
                                stopLockTask();
                            } else {
                                startLockTask();
                            }
                            updateMenuKiosk();
                        }

                        @Override
                        public void onClickNegitiveBtn() {

                        }
                    });
                    adminPwdDialog.show();
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

//    @Override
//    public void onBackPressed() {
//
//    }

    @Override
    protected void setUp() {
//        mvpPresenter.onMposTabApiCall();
//        turnOnScreen();
//        idealScreen();
        userName.setText(mvpPresenter.getLoginUserName());
        userStoreLocation.setText(mvpPresenter.getLoinStoreLocation());
        mvpPresenter.getCorporateList();
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
        updateMenuKiosk();
        onPause = false;

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

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        this.corporateModel = corporateModel;
    }

    @Override
    public void onSuccessGetUnPostedPOSTransaction(CalculatePosTransactionRes body) {
        GetCustomerResponse.CustomerEntity entity = new GetCustomerResponse.CustomerEntity();
        // entity.setCardNo(body.getCustomerID());
        entity.setCustId(body.getCustomerID());
        entity.setPostalAddress(body.getCustAddress());
        entity.setState(body.getCustomerState());
        entity.setCardName(body.getCustomerName());
        entity.setMobileNo(body.getMobileNO());
        entity.setSearchId(body.getMobileNO());
        entity.setCardNo(body.getTrackingRef());
        DoctorSearchResModel.DropdownValueBean doctorModule = new DoctorSearchResModel.DropdownValueBean();
        doctorModule.setCode(body.getDoctorCode());
        doctorModule.setDisplayText(body.getDoctorName());
        CorporateModel.DropdownValueBean corporateModule = new CorporateModel.DropdownValueBean();
        if (corporateModel != null) {
            for (CorporateModel.DropdownValueBean valueBean : corporateModel.get_DropdownValue()) {
                if (body.getCorpCode().equalsIgnoreCase(valueBean.getCode())) {
                    corporateModule.setCode(body.getCorpCode());
                    corporateModule.setDescription(valueBean.getDescription());
                    corporateModule.setPayMode(valueBean.getPayMode());
                    break;
                }
            }
        }
        TransactionIDResModel transactionIdModel = new TransactionIDResModel();
        transactionIdModel.setTransactionID(body.getTransactionId());
        Singletone.getInstance().itemsArrayList.addAll(body.getSalesLine());

        // setCartItems(body.getSalesLine());
        startActivity(AddItemActivity.getStartIntent(this, entity, doctorModule, corporateModule, transactionIdModel, corporateModel, body));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        hideLoading();
    }

    private void setCartItems(List<SalesLineEntity> salesLine) {
        for (SalesLineEntity lineEntity : salesLine) {
            GetItemDetailsRes.Items cartItems = new GetItemDetailsRes.Items();
            cartItems.setArtCode(lineEntity.getItemId());
            cartItems.setCategory(lineEntity.getCategory());
            cartItems.setCategoryCode(lineEntity.getCategoryCode());
            cartItems.setDescription(lineEntity.getItemName());
            cartItems.setDiseaseType(lineEntity.getDiseaseType());
            cartItems.setDPCO(lineEntity.isDPCO());
            //  cartItems.setGenericName(lineEntity.getGenericName());
            cartItems.setHsncode_In(lineEntity.getHsncode_In());
            cartItems.setManufacture(lineEntity.getManufacturerName());
            cartItems.setManufactureCode(lineEntity.getManufacturerCode());
            cartItems.setProductRecID(lineEntity.getProductRecID());
            //  cartItems.setRackId(lineEntity.getRackId());
            cartItems.setRetailCategoryRecID(lineEntity.getRetailCategoryRecID());
            cartItems.setRetailMainCategoryRecID(lineEntity.getRetailMainCategoryRecID());
            cartItems.setRetailSubCategoryRecID(lineEntity.getRetailSubCategoryRecID());
            cartItems.setSch_Catg(lineEntity.getScheduleCategory());
            cartItems.setSch_Catg_Code(lineEntity.getScheduleCategoryCode());
            //   cartItems.setSI_NO(lineEntity.getS());
            cartItems.setSubCategory(lineEntity.getSubCategory());
            cartItems.setSubClassification(lineEntity.getSubClassification());
            cartItems.setItemDelete(lineEntity.getIsVoid());
            GetBatchInfoRes.BatchListObj batchList = new GetBatchInfoRes.BatchListObj();
            batchList.setTotalTax(lineEntity.getTotalTax());
            //    batchList.setSNO(lineEntity.getSNO());
            batchList.setSGSTTaxCode(lineEntity.getSGSTTaxCode());
            batchList.setSGSTPerc(lineEntity.getSGSTPerc());
            batchList.setREQQTY((int) lineEntity.getQty());
            //   batchList.setQ_O_H(lineEntity.getQ_O_H());
            batchList.setPrice(lineEntity.getPrice());
            //   batchList.setNearByExpiry(lineEntity.isNearByExpiry());
            batchList.setMRP(lineEntity.getMRP());
            batchList.setItemID(lineEntity.getItemId());
            //   batchList.setISMRPChange(lineEntity.isISMRPChange());
            batchList.setIGSTTaxCode(lineEntity.getIGSTTaxCode());
            batchList.setIGSTPerc(lineEntity.getIGSTPerc());
            batchList.setExpDate(lineEntity.getExpiry());
            batchList.setCGSTTaxCode(lineEntity.getCGSTTaxCode());
            batchList.setCGSTPerc(lineEntity.getCGSTPerc());
            batchList.setCESSPerc(lineEntity.getCESSPerc());
            batchList.setCESSTaxCode(lineEntity.getCESSTaxCode());
            batchList.setBatchNo(lineEntity.getInventBatchId());
            batchList.setEnterReqQuantity((int) lineEntity.getQty());
            cartItems.setBatchListObj(batchList);
            //  Singletone.getInstance().itemsArrayList.add(cartItems);
        }

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

    private void logoutDialog() {
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

    private void setKioskMode() {
        // get policy manager
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // get this app package name
        ComponentName mDPM = new ComponentName(this, MyAdmin.class);
        //startLockTask();
        if (myDevicePolicyManager != null) {
            if (myDevicePolicyManager.isDeviceOwnerApp(this.getPackageName())) {
                // get this app package name
                String[] packages = {this.getPackageName()};
                // mDPM is the admin package, and allow the specified packages to lock task
                myDevicePolicyManager.setLockTaskPackages(mDPM, packages);
            } else {
                //startLockTask();
            }
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.nav_exit_kiosk);
            if (myDevicePolicyManager.isLockTaskPermitted(this.getPackageName())) {
                if (mvpPresenter.isKisokMode()) {
                    stopLockTask();
                    mvpPresenter.setKioskMode(false);
                    menuItem.setTitle("Enter Kiosk");
                } else {
                    startLockTask();
                    mvpPresenter.setKioskMode(true);
                    menuItem.setTitle("Exit Kiosk");
                }
            } else {
                showMessage("Permission not granted");
            }
        }
    }

    private void updateMenuKiosk() {
        Menu menu = navigationView.getMenu();
        MenuItem tools = menu.findItem(R.id.nav_exit_kiosk);
        if (!isAppInLockTaskMode()) {
            tools.setTitle("Enter Kiosk");
        } else {
            tools.setTitle("Exit Kiosk");
        }
    }

    protected UserIneractionListener userIneractionListener;

    public interface UserIneractionListener {
        void userInteraction();
    }

    public void setOnUserIneractionListener(UserIneractionListener userIneractionListener) {
        this.userIneractionListener = userIneractionListener;
    }

    protected OnBackPressedListener onBackPressedListener;

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIneractionListener.userInteraction();
//        stopHandler();
//        startHandler();
    }

    private List<RowsEntity> rowsEntitiesList;

    @Override
    public void onSucessPlayList() {
        rowsEntitiesList = mvpPresenter.getDataListEntity();
        for (int i = 0; i < rowsEntitiesList.size(); i++) {
            String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
            File file = new File(path);
            if (!file.exists()) {
                mvpPresenter.onDownloadApiCall(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
                        rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), i);
                break;
            }
        }
    }

    private boolean stopLooping;

    public void handelPlayList() {
        if (rowsEntitiesList.size() > 0) {
            if (!stopLooping) {
                boolean isAllFilesExist = false;
                for (int i = 0; i < rowsEntitiesList.size(); i++) {
                    if (!rowsEntitiesList.get(i).isPlayed()) {
                        String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                        File file = new File(path);
                        if (file.exists()) {
                            playListData(path, i);
                            isAllFilesExist = true;
                            break;
                        }
                    }
                }
                if (!isAllFilesExist) {
                    for (int i = 0; i < rowsEntitiesList.size(); i++) {
                        rowsEntitiesList.get(i).setPlayed(false);
                    }
                    handelPlayList();
                }
            }
        }

    }

    public void playListData(String filePath, int pos) {
        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(myBitmap);
        imageView.setVisibility(View.VISIBLE);
        imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsEntitiesList.get(pos).setPlayed(true);
                if (pos == rowsEntitiesList.size() - 1) {
                    for (RowsEntity rowsEntity : rowsEntitiesList) {
                        rowsEntity.setPlayed(false);
                    }
                }
                handelPlayList();
            }
        }, 2000);
    }

    private boolean onPause;

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }


    private Handler handler;
    private Runnable r;

    public void idealScreen() {
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                if (onPause) {
                    stopLooping = true;
                } else {
                    stopLooping = false;
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    getSupportActionBar().hide();
                    imageView.setVisibility(View.VISIBLE);
                }
                handelPlayList();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        imageView.setVisibility(View.GONE);
//                        getSupportActionBar().show();
                        stopLooping = true;
                    }
                });

            }
        };
        startHandler();
    }

    public void stopHandler() {
//        handler.removeCallbacks(r);
    }

    public void startHandler() {
//        handler.postDelayed(r, 180 * 1000);
    }

    public void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }


    @Nullable
    @Override
    public Context getContext() {
        return this;
    }


    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }


}
