package com.apollopharmacy.mpospharmacistTest.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.dialog.KioskExitClickListener;
import com.apollopharmacy.mpospharmacistTest.ui.home.dialog.KioskExitDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.BTSearchItem;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.DialogManager;
import com.apollopharmacy.mpospharmacistTest.utils.DownloadController;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.log.LogService;
import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityMvpView {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView userName, userStoreLocation;
    private CorporateModel corporateModel;
    private NavigationView navigationView;
    private ImageView imageView;
    private DrawerLayout drawer;


    //Bixolon Printer functionalities........

    private static final String TAG = "MainActivity";
    public static BixolonLabelPrinter mBixolonLabelPrinter;

    private List<BTSearchItem> pairedDevicesList = new ArrayList<>();
    private Set<BluetoothDevice> pairedDevices;
    private BluetoothAdapter mBtAdapter;

    private static final int BT_SEARCH_REQUEST_CODE = 5;
    private String mConnectedDeviceName = null;
    private boolean mIsConnected;
    private BTSearchItem selectedDevice = null;
    private ProgressDialog progress = null;

    private int mBarcodeSelection = BixolonLabelPrinter.BARCODE_CODE128;
    private int mHri = BixolonLabelPrinter.HRI_ABOVE_FONT_SIZE_2;
    NavController navController;
    NavOptions navOptions;

    @Inject
    MainActivityMvpPresenter<MainActivityMvpView> mvpPresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
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
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dash_board, R.id.nav_doc_master, R.id.nav_cust_master)
                .setDrawerLayout(drawer)
                .build();
        Constant.getInstance().isomsorder_check = false;

        // Constant.getInstance().Orders_type = "Packing";
        //navController = Navigation.findNavController(this, R.id.nav_eprescription);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_from_right)
                .setExitAnim(R.anim.slide_to_left)
                .setPopEnterAnim(R.anim.slide_from_right)
                .setPopExitAnim(R.anim.slide_to_left)
                .build();

        // navController.navigate(R.id.nav_Packing, null, navOptions, null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (mAppBarConfiguration.getDrawerLayout() != null) {
                    mAppBarConfiguration.getDrawerLayout().closeDrawers();
                }

                if (menuItem.getItemId() == R.id.nav_digitalsignage_screen_exit) {
                    System.out.println("openscren status--->" + mvpPresenter.isOpenScreens());
                    if (!mvpPresenter.isOpenScreens()) {
                        mvpPresenter.enableopenscreens();
                    } else {
                        mvpPresenter.disablescreens();
                    }
                    updateOpenScreens();
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

                    });
                    adminPwdDialog.show();
                } else {
                    if (menuItem.isChecked()) return false;
                }

                Singletone.getInstance().isManualBilling = false;
                System.out.println("openscren status--->" + menuItem.getItemId());
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
                } /*else if (menuItem.getItemId() == R.id.nav_manual_billing) {
                }*/ else if (menuItem.getItemId() == R.id.nav_eprescription) {
                    // navigateprinterscreen();
                    navController.navigate(R.id.nav_eprescription, null, navOptions, null);
                    Constant.getInstance().Orders_type = "EPrescription";
                } else if (menuItem.getItemId() == R.id.nav_Picking) {
                    navController.navigate(R.id.nav_Picking, null, navOptions, null);
                    Constant.getInstance().Orders_type = "Picking";
                } else if (menuItem.getItemId() == R.id.nav_Packing) {
                    navController.navigate(R.id.nav_Packing, null, navOptions, null);
                    Constant.getInstance().Orders_type = "Packing";
                } else if (menuItem.getItemId() == R.id.nav_Invoice) {
                    navigateprinterscreen();
                    // navController.navigate(R.id.nav_Invoice, null, navOptions, null);
                    // Constant.getInstance().Orders_type = "Invoice";
                }else if(menuItem.getItemId() == R.id.nav_ePrescription_vOne){
                    navController.navigate(R.id.nav_ePrescription_vOne, null, navOptions, null);
                    Constant.getInstance().Orders_type = "E-Prescription";
                }
                /*else if (menuItem.getItemId() == R.id.nav_exit_kiosk) {
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

                    });
                    adminPwdDialog.show();
                } else if (menuItem.getItemId() == R.id.nav_digitalsignage_screen_exit) {
                    System.out.println("openscren status--->" + mvpPresenter.isOpenScreens());
                    if (!mvpPresenter.isOpenScreens()) {
                        mvpPresenter.enableopenscreens();
                    } else {
                        mvpPresenter.disablescreens();
                    }
                    updateOpenScreens();
                }*/

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

        Constant.getInstance().ordersource = "";
        setUp();
    }

    private void hideItem(GetGlobalConfingRes getGlobalConfingRes) {
        Menu nav_Menu = navigationView.getMenu();
        if (getGlobalConfingRes != null) {
            if (getGlobalConfingRes.isISHBPStore()) {
                nav_Menu.findItem(R.id.nav_eprescription).setVisible(false);
                nav_Menu.findItem(R.id.nav_Picking).setVisible(false);
                nav_Menu.findItem(R.id.nav_Packing).setVisible(false);
                nav_Menu.findItem(R.id.nav_Invoice).setVisible(false);
            } else {
                nav_Menu.findItem(R.id.nav_eprescription).setVisible(true);
                nav_Menu.findItem(R.id.nav_Picking).setVisible(true);
                nav_Menu.findItem(R.id.nav_Packing).setVisible(true);
                nav_Menu.findItem(R.id.nav_Invoice).setVisible(true);
            }
        }
    }

    public void navigateprinterscreen() {
        //SearchBTDialog searchBTDialog;
        // searchBTDialog = new SearchBTDialog(this);

        Intent intent = new Intent(this, SearchBTActivity.class);
        intent.putExtra("PairedBTList", (Serializable) pairedDevicesList);
        startActivityForResult(intent, BT_SEARCH_REQUEST_CODE);


    }

    @Override
    protected void setUp() {
        mvpPresenter.getGlobalConfig();
        userName.setText(mvpPresenter.getLoginUserName());
        userStoreLocation.setText(mvpPresenter.getLoinStoreLocation());
        mvpPresenter.getCorporateList();

        bixolonprinterinitilisation();
    }

    public void bixolonprinterinitilisation() {
        mBixolonLabelPrinter = new BixolonLabelPrinter(this, mHandler, Looper.getMainLooper());
        LogService.InitDebugLog(true, false, BXLCommonConst._LOG_LEVEL_HIGH);

        final int ANDROID_NOUGAT = 24;
        if (Build.VERSION.SDK_INT >= ANDROID_NOUGAT) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Boolean isBTOn = setBluetooth(true);
        if (isBTOn) {
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            pairedDevices = mBtAdapter.getBondedDevices();
            pairedDevicesList.clear();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName() != null) {
                        BTSearchItem item = new BTSearchItem();
                        item.setName(device.getName());
                        item.setAddress(device.getAddress());
                        item.setPaired(false);
                        pairedDevicesList.add(item);
                        Log.e(TAG, "Main Search Device :: " + device.getName() + "\n" + device.getAddress());
                    }
                }
            }
        }
    }


    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return false;
        }
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BixolonLabelPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BixolonLabelPrinter.STATE_CONNECTED:
                            //activityMainBinding.printLayout.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Bluetooth Connected", Toast.LENGTH_SHORT).show();
//							mListView.setEnabled(true);
                            mIsConnected = true;
                            closeLoading();
//                            invalidateOptionsMenu();
                            break;

                        case BixolonLabelPrinter.STATE_CONNECTING:
                            break;

                        case BixolonLabelPrinter.STATE_NONE:
                            // activityMainBinding.connectBtDevice.setVisibility(View.VISIBLE);
                            Log.e("NONE", msg.toString());
                            Toast.makeText(MainActivity.this, "Bluetooth Connection Failed", Toast.LENGTH_SHORT).show();
//							mListView.setEnabled(false);
                            closeLoading();
                            mIsConnected = false;
//                            invalidateOptionsMenu();
                            break;
                    }
                    break;
                case BixolonLabelPrinter.MESSAGE_READ:
                    MainActivity.this.dispatchMessage(msg);
                    break;

                case BixolonLabelPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(BixolonLabelPrinter.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_TOAST:
//                    mListView.setEnabled(false);
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.TOAST), Toast.LENGTH_SHORT).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_LOG:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.LOG), Toast.LENGTH_SHORT).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No paired device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showBluetoothDialog(MainActivity.this, (Set<BluetoothDevice>) msg.obj);
                    }
                    break;
                case BixolonLabelPrinter.MESSAGE_OUTPUT_COMPLETE:
                    Toast.makeText(getApplicationContext(), "Transaction Print complete", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    private void closeLoading() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    @SuppressLint("HandlerLeak")
    private void dispatchMessage(Message msg) {
        switch (msg.arg1) {
            case BixolonLabelPrinter.PROCESS_GET_STATUS:
                byte[] report = (byte[]) msg.obj;
                StringBuffer buffer = new StringBuffer();
                if ((report[0] & BixolonLabelPrinter.STATUS_1ST_BYTE_PAPER_EMPTY) == BixolonLabelPrinter.STATUS_1ST_BYTE_PAPER_EMPTY) {
                    buffer.append("Paper Empty.\n");
                }
                if ((report[0] & BixolonLabelPrinter.STATUS_1ST_BYTE_COVER_OPEN) == BixolonLabelPrinter.STATUS_1ST_BYTE_COVER_OPEN) {
                    buffer.append("Cover open.\n");
                }
                if ((report[0] & BixolonLabelPrinter.STATUS_1ST_BYTE_CUTTER_JAMMED) == BixolonLabelPrinter.STATUS_1ST_BYTE_CUTTER_JAMMED) {
                    buffer.append("Cutter jammed.\n");
                }
                if ((report[0] & BixolonLabelPrinter.STATUS_1ST_BYTE_TPH_OVERHEAT) == BixolonLabelPrinter.STATUS_1ST_BYTE_TPH_OVERHEAT) {
                    buffer.append("TPH(thermal head) overheat.\n");
                }
                if ((report[0] & BixolonLabelPrinter.STATUS_1ST_BYTE_AUTO_SENSING_FAILURE) == BixolonLabelPrinter.STATUS_1ST_BYTE_AUTO_SENSING_FAILURE) {
                    buffer.append("Gap detection error. (Auto-sensing failure)\n");
                }
                if ((report[0] & BixolonLabelPrinter.STATUS_1ST_BYTE_RIBBON_END_ERROR) == BixolonLabelPrinter.STATUS_1ST_BYTE_RIBBON_END_ERROR) {
                    buffer.append("Ribbon end error.\n");
                }

                if (report.length == 2) {
                    if ((report[1] & BixolonLabelPrinter.STATUS_2ND_BYTE_BUILDING_IN_IMAGE_BUFFER) == BixolonLabelPrinter.STATUS_2ND_BYTE_BUILDING_IN_IMAGE_BUFFER) {
                        buffer.append("On building label to be printed in image buffer.\n");
                    }
                    if ((report[1] & BixolonLabelPrinter.STATUS_2ND_BYTE_PRINTING_IN_IMAGE_BUFFER) == BixolonLabelPrinter.STATUS_2ND_BYTE_PRINTING_IN_IMAGE_BUFFER) {
                        buffer.append("On printing label in image buffer.\n");
                    }
                    if ((report[1] & BixolonLabelPrinter.STATUS_2ND_BYTE_PAUSED_IN_PEELER_UNIT) == BixolonLabelPrinter.STATUS_2ND_BYTE_PAUSED_IN_PEELER_UNIT) {
                        buffer.append("Issued label is paused in peeler unit.\n");
                    }
                }
                if (buffer.length() == 0) {
                    buffer.append("No error");
                }
                Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_SHORT).show();
                break;
            case BixolonLabelPrinter.PROCESS_GET_INFORMATION_MODEL_NAME:
            case BixolonLabelPrinter.PROCESS_GET_INFORMATION_FIRMWARE_VERSION:
            case BixolonLabelPrinter.PROCESS_EXECUTE_DIRECT_IO:
                Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                Constant.getInstance().BarcodeStr = "";
                String searchedProductName = result.getContents();

              /*Bundle bundle = new Bundle();
                String myMessage = searchedProductName;
                bundle.putString("BarcodeStr", myMessage );
                EprescriptionslistFragment fragInfo = new EprescriptionslistFragment();
                fragInfo.setArguments(bundle);*/
                Log.i("Activity result-->", searchedProductName);

                Constant.getInstance().BarcodeStr = searchedProductName;
                // Constant.getInstance().BarcodeStr = "FL20211224301000489";


            }
        } else if (requestCode == BT_SEARCH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // activityMainBinding.connectBtDevice.setVisibility(View.GONE);
                selectedDevice = (BTSearchItem) data.getSerializableExtra("selected_item");
                showLoading(selectedDevice.getName());
                MainActivity.mBixolonLabelPrinter.connect(selectedDevice.getAddress());
                // mBixolonLabelPrinter.connect(selectedDevice.getAddress(), 1);
                // navController.navigate(R.id.nav_eprescription, null, navOptions, null);
                // Constant.getInstance().Orders_type = "EPrescription";


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showLoading(final String name) {
        if (progress == null) {
            try {
                progress = new ProgressDialog(MainActivity.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setTitle("Connecting to : " + name); // Connecting to
                progress.setMessage("Please wait..");
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                progress.setIndeterminate(false);
                progress.show();
            } catch (Exception e) {

            }
        }
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
            navController.popBackStack();
            Singletone.getInstance().isOrderCompleted = false;
            if (Constant.getInstance().isomsorder_check) {
                Constant.getInstance().Orders_type = "Picking";
                navController.navigate(R.id.nav_Picking);
            } else {
                navController.navigate(R.id.nav_dash_board);
            }

        }

        updateMenuKiosk();
        updateOpenScreens();
    }

    public void frompickuptodashboard() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.popBackStack();
        navController.navigate(R.id.nav_dash_board);
    }

    @Override
    public void navigateLoginActivity() {
        startActivity(PharmacistLoginActivity.getStartIntent(this));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private DownloadController downloadController;

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        this.corporateModel = corporateModel;
    }

    @Override
    public void onSuccessGetUnPostedPOSTransaction(CalculatePosTransactionRes body) {
        GetCustomerResponse.CustomerEntity entity = new GetCustomerResponse.CustomerEntity();
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

        startActivity(AddItemActivity.getStartIntent(this, entity, doctorModule, corporateModule, transactionIdModel, corporateModel, body));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        hideLoading();
    }


    private static final int PERMISSION_REQUEST_CODE = 1;

    private void startDownload() {
        if (checkPermission()) {
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

    private void updateMenuKiosk() {
        Menu menu = navigationView.getMenu();
        MenuItem tools = menu.findItem(R.id.nav_exit_kiosk);
        if (!isAppInLockTaskMode()) {
            tools.setTitle("Enter Kiosk");
        } else {
            tools.setTitle("Exit Kiosk");
        }
    }

    private void updateOpenScreens() {
        Menu menu = navigationView.getMenu();
        MenuItem tools = menu.findItem(R.id.nav_digitalsignage_screen_exit);
        if (mvpPresenter.isOpenScreens()) {
            tools.setTitle("Close Screens");
        } else {
            tools.setTitle("Open Screens");
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
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getGlobalConfig(GetGlobalConfingRes getGlobalConfingRes) {
        hideItem(getGlobalConfingRes);
    }


    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }


}
