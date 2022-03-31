package com.apollopharmacy.mpospharmacistTest.ui.home;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBluetoothSearchBinding;
import com.apollopharmacy.mpospharmacistTest.ui.home.adptor.BtSearchAdapter;
import com.apollopharmacy.mpospharmacistTest.utils.BTSearchItem;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.log.LogService;
import com.bixolon.labelprinter.BixolonLabelPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Singleton;

public class BTSearchActivity
{
    /*private ActivityBluetoothSearchBinding btSearchBinding;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private BtSearchAdapter mAdapter;
    private BtSearchAdapter pairedListAdapter;
    private List<BTSearchItem> devicesList = new ArrayList<>();
    private List<BTSearchItem> pairedDevicesList = new ArrayList<>();
    private BluetoothAdapter mBtAdapter;
    private UUID myUUID;
    private Set<BluetoothDevice> pairedDevices;
    private MenuItem refreshItem;
    private String TAG = BTSearchActivity.class.getSimpleName();
    // Debugging
    private static final boolean D = true;
    private ProgressBar pairedProgressBar = null;
    private ProgressBar progressBar = null;
    private ProgressDialog progress = null;
    private String selectedDeviceName = "";
    private String selectedDeviceAddress = "";
    private final String UUID_STRING_WELL_KNOWN_SPP = "0001101-0000-1000-8000-00805F9B34FB";
    private BluetoothSocket mmSocket;
    private BluetoothConvService conversation;
    Connection connection;
    private BTSearchItem selectedDevice = null;
    private int retryCnt = 0;

    public ArrayList<BluetoothDevice> m_LeDevices;
    static BixolonLabelPrinter mBixolonLabelPrinter;
    private boolean mIsConnected;
    private String mConnectedDeviceName = "";
    public Handler m_hHandler = null;
    public BluetoothAdapter m_BluetoothAdapter = null;
    public BluetoothLeScanner mLEScanner = null;
    private ScanCallback mScanCallback;
    public List<ScanFilter> filters;
    public ScanSettings settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_bluetooth_search);

        mBixolonLabelPrinter = new BixolonLabelPrinter(this, mHandler, Looper.getMainLooper());
        LogService.InitDebugLog(true, false, BXLCommonConst._LOG_LEVEL_HIGH);

        final int ANDROID_NOUGAT = 24;
        if(Build.VERSION.SDK_INT >= ANDROID_NOUGAT)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        m_hHandler = new Handler();
//        m_LeDevices = new ArrayList<BluetoothDevice>();
//        m_LeDevices.clear();
//        scanLeDevice(true);
//
//        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        m_BluetoothAdapter = bluetoothManager.getAdapter();
//
//        // Checks if Bluetooth is supported on the device.
//        if (m_BluetoothAdapter == null) {
//            Toast.makeText(this, "Error Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
//        }
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            mLEScanner = m_BluetoothAdapter.getBluetoothLeScanner();
//            settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build();
////            settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_OPPORTUNISTIC).build();
//            filters = new ArrayList<ScanFilter>();
//        }

        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(BTSearchActivity.this, R.layout.view_device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(BTSearchActivity.this, R.layout.view_device_name);

        mAdapter = new BtSearchAdapter(devicesList, BTSearchActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        btSearchBinding.recyclerUnPaired.setLayoutManager(mLayoutManager);
        btSearchBinding.recyclerUnPaired.setItemAnimator(new DefaultItemAnimator());
        btSearchBinding.recyclerUnPaired.setAdapter(mAdapter);

        // Find and set up the ListView for paired devices
        btSearchBinding.pairedDevices.setAdapter(mPairedDevicesArrayAdapter);
        btSearchBinding.pairedDevices.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        btSearchBinding.newDevices.setAdapter(mNewDevicesArrayAdapter);
        btSearchBinding.newDevices.setOnItemClickListener(mDeviceClickListener);

        Boolean isBTOn = setBluetooth(true);
        if (isBTOn) {
            // Register for broadcasts when a device is discovered
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            this.registerReceiver(mReceiver, filter);

            // Register for broadcasts when discovery has finished
            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            this.registerReceiver(mReceiver, filter);

            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            pairedDevices = mBtAdapter.getBondedDevices();
            pairedDevicesList.clear();
            if (pairedDevices.size() > 0) {
                btSearchBinding.pairedProgressBar.setVisibility(View.VISIBLE);
                findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
                findViewById(R.id.no_paired_device_text).setVisibility(View.GONE);
                pairedListAdapter = new BtSearchAdapter(pairedDevicesList, this);
                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                btSearchBinding.recyclerPaired.setLayoutManager(mLayoutManager1);
                btSearchBinding.recyclerPaired.setItemAnimator(new DefaultItemAnimator());
                btSearchBinding.recyclerPaired.setAdapter(pairedListAdapter);
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName() != null) {
//                        scanLeDevice(false);
                        BTSearchItem item = new BTSearchItem();
                        item.setName(device.getName());
                        item.setAddress(device.getAddress());
                        item.setPaired(false);
                        pairedDevicesList.add(item);
                        pairedListAdapter.notifyDataSetChanged();
                    }
                }
                btSearchBinding.pairedProgressBar.setVisibility(View.GONE);
                // mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            } else {
                btSearchBinding.pairedProgressBar.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshItem != null) {
                            refreshItem.setVisible(true);
                        }
                    }
                });
//                pairedDevicesList.clear();
//                pairedListAdapter.notifyDataSetChanged();
                findViewById(R.id.no_paired_device_text).setVisibility(View.VISIBLE);
                String noDevices = getResources().getText(R.string.none_paired).toString();
                // mPairedDevicesArrayAdapter.add(noDevices);
            }
            doDiscovery();
        } else {
            displayAlert("Bluetooth Failed", "Problem occured while connecting the bluetooth");
        }
    }


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BixolonLabelPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BixolonLabelPrinter.STATE_CONNECTED:
                            //setStatus(R.string.title_connected_to);
//							mListView.setEnabled(true);
                            mIsConnected = true;
                            invalidateOptionsMenu();
                            break;

                        case BixolonLabelPrinter.STATE_CONNECTING:
//                            setStatus(R.string.title_connecting);
                            break;

                        case BixolonLabelPrinter.STATE_NONE:
                            Log.e("NONE", msg.toString());
//                            setStatus(R.string.title_not_connected);
//							mListView.setEnabled(false);
                            mIsConnected = false;
                            invalidateOptionsMenu();
                            break;
                    }
                    break;
                case BixolonLabelPrinter.MESSAGE_READ:
                    BTSearchActivity.this.dispatchMessage(msg);
                    break;
                case BixolonLabelPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(BixolonLabelPrinter.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    break;
                case BixolonLabelPrinter.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.TOAST), Toast.LENGTH_SHORT).show();
                    break;
                case BixolonLabelPrinter.MESSAGE_LOG:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.LOG), Toast.LENGTH_SHORT).show();
                    break;
                case BixolonLabelPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No paired device", Toast.LENGTH_SHORT).show();
                    } else {

                        //Show list of Paired devices to User
//                        DialogManager.showBluetoothDialog(MainActivity.this, (Set<BluetoothDevice>) msg.obj);
                    }
                    break;
                case BixolonLabelPrinter.MESSAGE_OUTPUT_COMPLETE:
                    Toast.makeText(getApplicationContext(), "Transaction Print complete", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    private final void setStatus(int resId) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(resId);
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

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public void scanLeDevice(final boolean bEnable) {
        if (bEnable) {
            if (Build.VERSION.SDK_INT >= 21) {
                try {
                    setScanCallback();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            // Stops scanning after a pre-defined scan period.
            m_hHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT < 21) {
                        m_BluetoothAdapter.stopLeScan(m_LeScanCallback);
                    } else {
                        mLEScanner.stopScan(mScanCallback);
                    }
                }
            }, 10000);

            if (Build.VERSION.SDK_INT < 21) {
                m_BluetoothAdapter.startLeScan(m_LeScanCallback);
            } else {
                mLEScanner.startScan(filters, settings, mScanCallback);
            }
        } else {
            if (Build.VERSION.SDK_INT < 21) {
                m_BluetoothAdapter.stopLeScan(m_LeScanCallback);
            } else {
                mLEScanner.stopScan(mScanCallback);
            }
        }
    }

    private void setScanCallback() throws NoSuchMethodException {
        mScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                Log.i("callbackType", String.valueOf(callbackType));
                Log.i("result", result.toString());
                BluetoothDevice btDevice = result.getDevice();

                if (!m_LeDevices.contains(btDevice)) {
                    m_LeDevices.add(btDevice);
                    BTSearchItem btSearchItem = new BTSearchItem();
                    btSearchItem.setName(btDevice.getName());
                    btSearchItem.setAddress(btDevice.getAddress());
//                    adapter.add(btDevice.getName() + "\n" + btDevice.getAddress());
                } else {
                    return;
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);

                for (ScanResult sr : results) {
                    Log.i("ScanResult - Results", sr.toString());
                }
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);

                Log.e("Scan Failed", "Error Code: " + errorCode);
            }
        };
    }

    public BluetoothAdapter.LeScanCallback m_LeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!m_LeDevices.contains(device)) {
                        m_LeDevices.add(device);
                        BTSearchItem btSearchItem = new BTSearchItem();
                        btSearchItem.setName(device.getName());
                        btSearchItem.setAddress(device.getAddress());
//                        adapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
            });
        }
    };

    @Override
    public void onConnectRequest(int position, BTSearchItem item) {
        mConnectedDeviceName = item.getName();
        selectedDevice = item;
      //  Singleton.getInstance().bluetoothSocket = null;
         Singletone.getInstance().bluetoothSocket = null;
        showLoading(selectedDevice.getName());
        mBixolonLabelPrinter.connect(item.getAddress(), 1);
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            if (mBtAdapter.isDiscovering())
                mBtAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            String infoArr[] = info.split("\n");
            Log.e("DeviceListAct", "Name :: " + infoArr[0]);
            selectedDeviceName = infoArr[0].toString();
            selectedDeviceAddress = address;
            showLoading(selectedDeviceName);
            myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);
            connection = new Connection();
            connection.start();
        }
    };

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

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Log.e(TAG, "Bind::" + device.getBondState() + " :: " + device.getName());
                Boolean isAvailable = false;
                findViewById(R.id.no_device_text).setVisibility(View.GONE);

                for (BTSearchItem item : devicesList) {
                    if (item.getAddress().equalsIgnoreCase(device.getAddress())) {
                        isAvailable = true;
                    }
                }

                if (!isAvailable) {
                    if (device.getName() != null) {
                        BTSearchItem item = new BTSearchItem();
                        item.setName(device.getName());
                        item.setAddress(device.getAddress());
                        item.setPaired(false);
                        devicesList.add(item);
                    }
                }
                mAdapter.notifyDataSetChanged();
                if (refreshItem != null) {
                    refreshItem.setVisible(true);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (mAdapter.getItemCount() == 0) {
                    progressBar.setVisibility(View.GONE);
                    devicesList.clear();
                    mAdapter.notifyDataSetChanged();
                    findViewById(R.id.no_device_text).setVisibility(View.VISIBLE);
                    if (refreshItem != null) {
                        refreshItem.setVisible(true);
                    }
                }
            }
        }
    };

    private void displayAlert(String title, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(BTSearchActivity.this);
                    builder1.setTitle(title);
                    builder1.setMessage(message);
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } catch (Exception e) {

                }
            }
        });
    }

    private void showLoading(final String name) {
        if (progress == null) {
            try {
                progress = new ProgressDialog(BTSearchActivity.this);
                // Set progress dialog style spinner
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // Set the progress dialog title and message
                progress.setTitle("Connecting to : " + name); // Connecting to
                progress.setMessage("Please wait..");
                // Set the progress dialog background color
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                progress.setIndeterminate(false);
                // Finally, show the progress dialog
                progress.show();
            } catch (Exception e) {

            }
        }
    }

    private void closeLoading() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        refreshItem = menu.findItem(R.id.refresh);
        refreshItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    *//**
     * Start device discover with the BluetoothAdapter
     *//*
    private void doDiscovery() {
        btSearchBinding.progressBar.setVisibility(View.VISIBLE);
        if (D)
            Log.d(TAG, "doDiscovery()");

        findViewById(R.id.no_device_text).setVisibility(View.GONE);
        if (refreshItem != null) {
            refreshItem.setVisible(false);
        }
        Log.e(TAG, "discovery");
        if (mBtAdapter == null) {
            return;
        }
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        Log.e(TAG, "Searching devices");
        mBtAdapter.startDiscovery();
        getSystemService(Context.BLUETOOTH_SERVICE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBtAdapter != null) {
                    if (mBtAdapter.isDiscovering()) {
                        progressBar.setVisibility(View.GONE);
                        mBtAdapter.cancelDiscovery();
                    }
                }
            }
        }, 15 * 1000);
    }

    private class Connection extends Thread {
        Connection() {
            String deviceAddress = selectedDevice.getAddress();
            BluetoothDevice device = mBtAdapter.getRemoteDevice(deviceAddress);
            try {
                mmSocket = null;
                mmSocket = device.createRfcommSocketToServiceRecord(myUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                if (mmSocket != null) {
                    mmSocket.connect();
                    conversation = new BluetoothConvService(mmSocket);
                 //   Singleton.get.bluetoothSocket = mmSocket;
                    Singletone.getInstance().bluetoothSocket=mmSocket;
                   // class.getInterfaces()

//                    Toast.makeText(BluetoothSearchActivity.this, "Connected", Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeLoading();
                         //   Singleton.getInstance().CONNECTED_BLUETOOTH_DEVICE_NAME = selectedDevice.getName();
                              Singletone.getInstance().CONNECTED_BLUETOOTH_DEVICE_NAME = selectedDevice.getName();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("isConnected", true);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshItem != null) {
                            refreshItem.setVisible(true);
                        }
                        if (retryCnt >= 1) {
                            Log.e(TAG, "is retry ending ====== ");
                            closeLoading();

                            showAlert(getResources().getString(R.string.label_bluetooth_conn_header_err), getResources().getString(R.string.label_bluetooth_conn_err));
                        } else {
                            retryCnt++;
                            Log.e(TAG, "is retry cnt Calling : " + retryCnt);
                            //Toast.makeText(getApplicationContext(), "Retrying BT Connection", Toast.LENGTH_SHORT).show();
                            connectBluetooth();
                        }
//                        closeLoading();
//                        showAlert(getResources().getString(R.string.label_bluetooth_conn_header_err), getResources().getString(R.string.label_bluetooth_conn_err));
                        // Toast.makeText(BluetoothSearchActivity.this, "Problem occured while connecting.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            super.run();
        }
    }

    private void connectBluetooth() {
        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);
        connection = new Connection();
        connection.start();
    }

    private void showAlert(String title, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BTSearchActivity.this);
                    builder.setTitle(title);
                    builder.setCancelable(false);
                    builder.setMessage(message);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

}
