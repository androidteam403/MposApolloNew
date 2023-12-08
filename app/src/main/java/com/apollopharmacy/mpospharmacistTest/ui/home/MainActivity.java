package com.apollopharmacy.mpospharmacistTest.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.BTSearchItem;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.DialogManager;
import com.apollopharmacy.mpospharmacistTest.utils.DownloadController;
import com.apollopharmacy.mpospharmacistTest.utils.EnglishNumberToWords;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.qrcode.QRGContents;
import com.apollopharmacy.mpospharmacistTest.utils.qrcode.QRGEncoder;
import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.log.LogService;
import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    public static final String cambria = "src/main/res/font/cambriab.ttf";
    private final static int ITEXT_FONT_SIZE_EIGHT = 10;
    private final static int ITEXT_FONT_SIZE_TEN = 12;
    private final static int ITEXT_FONT_SIZE_SIX = 8;

    private final static int ITEXT_FONT_SIZE_SIX_6 = 6;

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
        /*if (mvpPresenter.getGlobalConfing().isISHBPStore()) {
            rePrint.setVisibility(View.VISIBLE);
        } else {
            rePrint.setVisibility(View.GONE);
        }*/
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
                } else if (menuItem.getItemId() == R.id.nav_ePrescription_vOne) {
                    navController.navigate(R.id.nav_ePrescription_vOne, null, navOptions, null);
                    Constant.getInstance().Orders_type = "E-Prescription";
                } else if (menuItem.getItemId() == R.id.nav_reprint) {
                    onClickrePrint();
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

    private void onClickrePrint() {
        if (mvpPresenter.getLastTransactionId() != null && !mvpPresenter.getLastTransactionId().isEmpty()) {
            mvpPresenter.downloadPdf(mvpPresenter.getLastTransactionId());
        } else {
            Toast.makeText(this, "No Transaction ID Available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideItem(GetGlobalConfingRes getGlobalConfingRes) {
        Menu nav_Menu = navigationView.getMenu();
        if (getGlobalConfingRes != null) {
            if (getGlobalConfingRes.isISHBPStore()) {
                nav_Menu.findItem(R.id.nav_eprescription).setVisible(false);
                nav_Menu.findItem(R.id.nav_Picking).setVisible(false);
                nav_Menu.findItem(R.id.nav_Packing).setVisible(false);
                nav_Menu.findItem(R.id.nav_Invoice).setVisible(false);
                nav_Menu.findItem(R.id.nav_ePrescription_vOne).setVisible(true);
                nav_Menu.findItem(R.id.nav_reprint).setVisible(true);

            } else {
//                nav_Menu.findItem(R.id.nav_eprescription).setVisible(true);
//                nav_Menu.findItem(R.id.nav_Picking).setVisible(true);
//                nav_Menu.findItem(R.id.nav_Packing).setVisible(true);
//                nav_Menu.findItem(R.id.nav_Invoice).setVisible(true);
                nav_Menu.findItem(R.id.nav_eprescription).setVisible(false);
                nav_Menu.findItem(R.id.nav_Picking).setVisible(false);
                nav_Menu.findItem(R.id.nav_Packing).setVisible(false);
                nav_Menu.findItem(R.id.nav_Invoice).setVisible(false);
                nav_Menu.findItem(R.id.nav_ePrescription_vOne).setVisible(false);
                nav_Menu.findItem(R.id.nav_reprint).setVisible(false);
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

        // bixolonprinterinitilisation();
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

    @Override
    public void onSuccessPdfResponse(PdfModelResponse pdfModelResponse) {
        if (pdfModelResponse != null) {
            try {
                createPdf(pdfModelResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int pageBreakCount = 0;
    private int shippingChargePackingCount = 0;

    private void createPdf(PdfModelResponse pdfModelResponse) throws IOException {
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

//        File file = new File(pdfPath, "pdfdoc.pdf");
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
        String fileName = mvpPresenter.getLastTransactionId() + ".pdf";
        FileUtil.createFilePath(fileName, getContext(), "mposprintbill");
        PdfWriter writer = new PdfWriter(FileUtil.getFilePath(fileName, getContext(), "mposprintbill"));

//        OutputStream outputStream = new FileOutputStream(file);
//        PdfWriter writer = new PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new PdfDocument(writer);
        com.itextpdf.layout.Document document = new Document(pdfDocument, PageSize.A4);
        document.setMargins(15, 15, 15, 15);
        pageBreakCount = 0;
        shippingChargePackingCount = 0;
        //After testing remove loop
       /* for (int i = 0; i < 8; i++) {
            PdfModelResponse.SalesLine salesLine = pdfModelResponse.getSalesLine().get(0);
            pdfModelResponse.getSalesLine().add(salesLine);
        }*/
        createPdfPageWise(pdfModelResponse, pdfDocument, document, false);
        document.close();
        if (isStoragePermissionGranted()) {
            openPdf();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public static final String REGULAR =
            "res/font/gothic_regular.TTF";
    public static final String BOLD =
            "res/font/gothic_bold.TTF";

    private void createPdfPageWise(PdfModelResponse pdfModelResponse, PdfDocument pdfDocument, Document document, boolean isDuplicate) throws IOException {
        // declaring variables for loading the fonts from asset
        byte[] fontByte, boldByte;
        AssetManager am;
        am = this.getAssets();
//the file name should be same as in your assets folder
//        try (InputStream inStream = am.open("font/cambria.ttf")) {
//            fontByte = IOUtils.toByteArray(inStream);
//        }
        FontProgram fontProgram =
                FontProgramFactory.createFont(REGULAR);
        FontProgram fontProgramBold =
                FontProgramFactory.createFont(BOLD);
//        try (InputStream inStream = am.open("font/cambriab.ttf")) {
//            boldByte = IOUtils.toByteArray(inStream);
//
//        }
//        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
        PdfFont bold = PdfFontFactory.createFont(fontProgramBold, PdfEncodings.WINANSI, true);
//        PdfFont bold = PdfFontFactory.createFont(boldByte, PdfEncodings.WINANSI, true);
//        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
////        PdfFont cam = PdfFontFactory.createFont(font_end, true);
//      PdfFont cam = PdfFontFactory.createFont("src\\main\\res\\font\\cambriab.ttf", true);
        float[] columnWidth1 = {98, 150, 150, 167};//580 - 30
//        float columnWidth1[] = {65, 165, 140, 190};
        Table table1 = new Table(columnWidth1);
        table1.setWidth(UnitValue.createPercentValue(100));
        table1.setFixedLayout();

        //table1.....row1.....
        Drawable apolloLogoDrawable = getDrawable(R.drawable.new_apollo_21);
        Bitmap apolloLogoBitMap = ((BitmapDrawable) apolloLogoDrawable).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        apolloLogoBitMap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitMapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitMapData1);
        Image image1 = new Image(imageData1);
        image1.scaleToFit(65, 65);
        image1.setHeight(65);


        Border border1 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
        table1.setBorder(border1);
        table1.addCell(new Cell(4, 1).add(image1).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Apollo Pharmacy - ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getBranch()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).add(new Paragraph(new Text(pdfModelResponse.getSalesHeader().get(0).getAddressOne()).setFontSize(ITEXT_FONT_SIZE_SIX))).add(new Paragraph(new Text(pdfModelResponse.getSalesHeader().get(0).getAddressTwo()).setFontSize(ITEXT_FONT_SIZE_SIX))).add(new Paragraph(new Text("PHONE: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getTelNo()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("FSSAI NO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getFssaino()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("D.L.NO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getDlno()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("GST NO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getGstin()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER).add(new Paragraph(new Text("C.GSTIN NO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCgstin()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));


        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Registered Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("No.19 Bishop Gerden, Raja Annamalaipuram, Chennai-600028").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("Admin Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("(For all correspondence) All towers, Floor No 55, Greams Road, Chennai-600006").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("CIN : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("U52500TN2016PLC111328").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        String duplicate = isDuplicate ? "Duplicate Copy of Invoice" : "";
        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text(duplicate).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(10)).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text("INVOICE").setFontSize(ITEXT_FONT_SIZE_EIGHT).setFont(bold)).setMarginLeft(10)).setBorder(Border.NO_BORDER));

        float[] columnWidth2 = {170, 120, 120, 170};// 580
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table2 = new Table(columnWidth2);
        Border border2 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
        table2.setBorder(border2);
//        table2.setBorder(new SolidBorder(1));
        table2.addCell(new Cell().add(new Paragraph(new Text("Name : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCustName()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(new Text("Mobile No. : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCustMobile()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(new Text("Bill No. : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getReceiptId()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(new Text("Corp: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCorporate()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));

        float[] columnWidth3 = {190, 133, 102, 155};//580
//        float columnWidth3[] = {180, 130, 100, 150};
        Table table3 = new Table(columnWidth3);
        Border border3 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
        table3.setBorder(border3);
//        table3.setBorder(new SolidBorder(1));
        if (pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")) {
            table3.addCell(new Cell().add(new Paragraph(new Text("Doctor : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("--").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        } else {
            table3.addCell(new Cell().add(new Paragraph(new Text("Doctor : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getDoctorName()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        }

        table3.addCell(new Cell().add(new Paragraph(new Text("Ref. NO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getRefNo()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().add(new Paragraph(new Text("TID : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getTerminalId()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().add(new Paragraph(new Text("Bill Date : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getTransDate()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));

        float[] columnWidth4 = {40, 30, 150, 20, 50, 35, 55, 55, 55, 50, 40};//580
//        float columnWidth4[] = {41, 31, 136, 21, 51, 36, 51, 51, 51, 51, 41};
        Table table4 = new Table(columnWidth4);
        Border border4 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table4.setBorder(border4);
        table4.addCell(new Cell().add(new Paragraph(new Text("Rack").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("QTY").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Product Name").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Sch").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("HSN Code").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Mfg").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Batch").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Expiry").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("MRP").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Amount").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("GST%").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        for (int i = pageBreakCount; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 1 || pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 2) {
                shippingChargePackingCount++;
            } else {
                PdfModelResponse.SalesLine salesLine = pdfModelResponse.getSalesLine().get(i);
                pageBreakCount++;
                if (pdfModelResponse.getSalesLine().get(i).getRackId() != null && pdfModelResponse.getSalesLine().get(i).getRackId().length() > 7) {
                    table4.addCell(new Cell().add(new Paragraph(new Text(pdfModelResponse.getSalesLine().get(i).getRackId().substring(0, 5) + "..").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                } else {
                    table4.addCell(new Cell().add(new Paragraph(new Text(pdfModelResponse.getSalesLine().get(i).getRackId()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                }
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getQty()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                String itemName = salesLine.getItemName().replace(" ", "\u00A0");
                table4.addCell(new Cell().add(new Paragraph(new Text(itemName).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getSch()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getHSNCode()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getManufacturer()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                String batchNo = "";
                if (salesLine.getBatchNo().length() > 12) {
                    batchNo = salesLine.getBatchNo().substring(0, 11);
                    batchNo = batchNo + "\n" + salesLine.getBatchNo().substring(12);
                } else {
                    batchNo = salesLine.getBatchNo();
                }
                table4.addCell(new Cell().add(new Paragraph(new Text(batchNo).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                if (salesLine.getExpDate() != null && salesLine.getExpDate().length() > 5) {
                    String expDate[] = salesLine.getExpDate().substring(2, 7).split("-");
                    table4.addCell(new Cell().add(new Paragraph(new Text(expDate[1] + "-" + expDate[0]).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                } else {
                    table4.addCell(new Cell().add(new Paragraph(new Text("--").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                }
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getMrp()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getLineTotAmount()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                Double gst = Double.parseDouble(salesLine.getSGSTPer()) + Double.parseDouble(salesLine.getCGSTPer());
                table4.addCell(new Cell().add(new Paragraph(new Text(String.format("%.02f", gst)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                if (pageBreakCount % 5 == 0) {
                    break;
                }
            }
        }
        float[] columnWidthEShippingPacking = {580};//580
        Table tableEShippingPacking = new Table(columnWidthEShippingPacking);
        Border borderEShippingPacking = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        tableEShippingPacking.setBorder(borderEShippingPacking);
        String eShippingPacking = "";
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 1) {
                if (eShippingPacking.isEmpty()) {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                } else {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = eShippingPacking + " | " + pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                }
            } else if (pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 2) {
                if (eShippingPacking.isEmpty()) {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                } else {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = eShippingPacking + " | " + pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                }
            }
        }
        tableEShippingPacking.addCell(new Cell(1, 1).add(new Paragraph(new Text(eShippingPacking).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setTextAlignment(TextAlignment.CENTER)).setBorder(border4));


        float[] columnWidth5 = {144, 170, 122, 144};//580
//        float columnWidth5[] = {140, 160, 120, 140};
        Table table5 = new Table(columnWidth5);
        Border border5 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table5.setBorder(border5);
//        table5.setBorder(new SolidBorder(1));
        double taxbleValue = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()) {
                taxbleValue = taxbleValue + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable());

            }
        }
        table5.addCell(new Cell().add(new Paragraph(new Text("Taxable Value : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", taxbleValue)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        double cgstAmount = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()

                    && pdfModelResponse.getSalesLine().get(i).getCGSTPer() != null && !pdfModelResponse.getSalesLine().get(i).getCGSTPer().isEmpty()) {
//                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);
                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);

            }
        }
        table5.addCell(new Cell().add(new Paragraph(new Text("CGST Amount: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", cgstAmount)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        double sgstAmount = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty() && pdfModelResponse.getSalesLine().get(i).getSGSTPer() != null && !pdfModelResponse.getSalesLine().get(i).getSGSTPer().isEmpty()) {
                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);
                //                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);

            }
        }
        table5.addCell(new Cell().add(new Paragraph(new Text("SGST Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", sgstAmount)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        double gross = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getTotal());
        table5.addCell(new Cell().add(new Paragraph(new Text("Gross Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", gross)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));

        float[] columnWidth6 = {144, 170, 122, 144};//580
//        float columnWidth6[] = {140, 160, 120, 140};
        Table table6 = new Table(columnWidth6);
        Border border6 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table6.setBorder(border6);
//        table6.setBorder(new SolidBorder(1));
        table6.addCell(new Cell().add(new Paragraph(new Text("Donation: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("0.00").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        table6.addCell(new Cell().add(new Paragraph(new Text("*1 HC equal to 1 Rupee").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER));
        table6.addCell(new Cell(1, 2).add(new Paragraph(new Text("* You Saved Rs. " + pdfModelResponse.getSalesHeader().get(0).getDiscount() + "& Earned 50.35 HC's ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(new DashedBorder(1)));


        float[] columnWidth7 = {290, 290};//580
//        float columnWidth7[] = {280, 280};
        Table table7 = new Table(columnWidth7);
        Border border7 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table7.setBorder(border7);
//        table7.setBorder(new SolidBorder(1));
        double netAmout = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal());
        String rupeesInput = "\"Rupees\"";
        String onlyInput = "\"only\"";
        String rupees = rupeesInput.substring(0, rupeesInput.length() - 1);
        String only = onlyInput.substring(1);
//        System.out.println("Input: " + input);
//        System.out.println("Result: " + result);
        table7.addCell(new Cell().add(new Paragraph(new Text((rupees + " " + EnglishNumberToWords.convert(Math.round(Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal()))) + " " + only)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));
        table7.addCell(new Cell().add(new Paragraph(new Text("Paid Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", netAmout)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(150)).setBorder(Border.NO_BORDER));


        float[] columnWidth8 = {167, 123, 123, 167};//580
//        float columnWidth8[] = {160, 120, 120, 160};
        Table table8 = new Table(columnWidth8);
        Border border8 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table8.setBorder(border8);
//        table8.setBorder(new SolidBorder(1));

        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//        try {
//            encodeAsBitmap(pdfModelResponse).compress(Bitmap.CompressFormat.PNG, 100, stream2);
        QrCodeGeneration(pdfModelResponse, this).compress(Bitmap.CompressFormat.PNG, 100, stream2);
//        }
//        catch (WriterException e) {
//            e.printStackTrace();
//        }
        byte[] bitMapData2 = stream2.toByteArray();

        ImageData imageData2 = ImageDataFactory.create(bitMapData2);
        Image image2 = new Image(imageData2);
        image2.scaleToFit(70, 70);


        table8.addCell(new Cell().add(new Paragraph(new Text("Wishing You Speedy Recovery").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell(1, 1).add(new Paragraph(new Text("Scan QR Code For\nRefill/Reorder").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell(4, 1).add(image2.setMargins(0, 0, 0, 0).setPadding(0)).setPadding(0).setMargin(0).setBorder(Border.NO_BORDER));
        table8.addCell(new Cell().add(new Paragraph(new Text("For ").setFontSize(8).setFont(font)).add(new Text("APOLLO PHARMACY\n").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("Registered pharmacist").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));

        table8.addCell(new Cell().add(new Paragraph(new Text("\"QR Code was digitally displayed for the Customer at the time of the transaction\"").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setFont(font).setFont(font));


        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell(2, 1).add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(8).setFont(font)).add(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));

        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setFont(font).setFont(font));

        table8.addCell(new Cell(1, 4).add(new Paragraph(new Text("E & O.E Goods Once Sold Cannot Be Taken Back or Exchanges | INSULINS AND VACCINES WILL NOT BE TAKEN BACK | EMERGENCY CALL:1066 | Tollfree No: 1860-500-0101").setFontSize(ITEXT_FONT_SIZE_SIX_6))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));


        document.add(table1);
        document.add(table2);
        document.add(table3);
        document.add(table4);
        if ((pageBreakCount + shippingChargePackingCount) == pdfModelResponse.getSalesLine().size()) {
            if (!eShippingPacking.isEmpty()) {
                document.add(tableEShippingPacking);
            }
            document.add(table5);
            document.add(table6);
            document.add(table7);
            document.add(table8);
        }


        if ((pageBreakCount + shippingChargePackingCount) != pdfModelResponse.getSalesLine().size()) {
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            createPdfPageWise(pdfModelResponse, pdfDocument, document, isDuplicate);
        }
        /*else {
            if (!isDuplicate && duplicateCheckboxChecked) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                pageBreakCount = 0;
                shippingChargePackingCount = 0;
                createPdfPageWise(pdfModelResponse,pdfDocument, document, true);
            }
        }*/
    }

    private Bitmap QrCodeGeneration(PdfModelResponse pdfModelResponse, Context context) {
        String qrCodeData = "CUSTOMERNAME: " + pdfModelResponse.getSalesHeader().get(0).getCustName() + "\nPHONE: " + pdfModelResponse.getSalesHeader().get(0).getCustMobile() + "\nBILL NO: " + pdfModelResponse.getSalesHeader().get(0).getReceiptId();
        for (PdfModelResponse.SalesLine salesLine : pdfModelResponse.getSalesLine()) {
            qrCodeData = qrCodeData + "\nITEMID: " + "- " + "QTY: " + salesLine.getQty();
        }
        Bitmap bitmap1 = null;
// below line is for getting
        // the windowmanager service.
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        QRGEncoder qrgEncoder = new QRGEncoder((String) qrCodeData, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            if (qrCodeData != null) {
//                 bitmapImg.setImageBitmap(bitmap);
                bitmap1 = bitmap;
            }
        } catch (com.google.zxing.WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }
        return bitmap1;
    }

    private void openPdf() {

//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
        String fileName = mvpPresenter.getLastTransactionId() + ".pdf";
        File file = FileUtil.getFilePath(fileName, this, "mposprintbill");

        if (file.exists()) {
            //Button To start print

            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);

            PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
            String jobName = this.getString(R.string.app_name) + " Document";

            printManager.print(jobName, pda, builder.build());

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
////            Uri uri = Uri.fromFile(file);
//            intent.setDataAndType(photoURI, "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//
//            try {
//                startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(this, "No Application for pdf view", Toast.LENGTH_SHORT).show();
//            }
        } else {
//            Toast.makeText(this, "File not exist", Toast.LENGTH_SHORT).show();
        }
    }

    PrintDocumentAdapter pda = new PrintDocumentAdapter() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
            InputStream input = null;
            OutputStream output = null;
            try {
//                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
                String fileName = mvpPresenter.getLastTransactionId() + ".pdf";
                File file = FileUtil.getFilePath(fileName, MainActivity.this, "mposprintbill");

                input = new FileInputStream(file);//"/storage/emulated/0/Documents/my-document-1656940186153.pdf"
                output = new FileOutputStream(destination.getFileDescriptor());
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf)) > 0) {
                    output.write(buf, 0, bytesRead);
                }
            } catch (Exception e) {

            } finally {
                try {
                    if (input != null) {
                        input.close();
                    } else {
                        Toast.makeText(MainActivity.this, "FileInputStream getting null", Toast.LENGTH_SHORT).show();
                    }

                    if (output != null) {
                        output.close();
                    } else {
                        Toast.makeText(MainActivity.this, "FileOutStream getting null", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }
            //int pages = computePageCount(newAttributes);
            PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(mvpPresenter.getLastTransactionId() + ".pdf").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
            callback.onLayoutFinished(pdi, true);
        }

    };

    @Override
    public void onFailurePdfResponse(PdfModelResponse body) {

    }


    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }
}
