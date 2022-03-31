package com.apollopharmacy.mpospharmacistTest.utils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacistTest.utils.adaptor.BluetoothRecyclerViewAdapter;
import com.printf.manager.BluetoothManager;
import com.printf.model.BluetoothModel;
import com.printf.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印机设备，蓝牙搜索页面
 */

/**
 * Printer device, Bluetooth search page
 */
public class BluetoothActivity extends Activity {

    RecyclerView rvBluetoothShowList;

    Context context;

    private List<BluetoothModel> bluetoothModels = new ArrayList<>();

    private BluetoothRecyclerViewAdapter recyclerShowAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BluetoothActivity.class);
    }

    /**
     * 扫描到蓝牙的回调（添加蓝牙设备到列表）
     */
    /**
     * Scan to Bluetooth callback (add Bluetooth device to the list)
     */
    BluetoothManager.ScanBlueCallBack scanBlueCallBack = new BluetoothManager.ScanBlueCallBack() {
        @Override
        public void scanDevice(BluetoothModel bluetoothModel) {
            bluetoothModels.add(bluetoothModel);
            recyclerShowAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 连接蓝牙结果的回调
     * Callback of Bluetooth connection result
     */
    BluetoothManager.ConnectResultCallBack connectResultCallBack = new BluetoothManager.ConnectResultCallBack() {
        @Override
        public void success(BluetoothDevice device) {
            Toast.makeText(context, "Bluetooth connection is successful", Toast.LENGTH_SHORT).show();
            BluetoothActivity.this.finish();
        }

        @Override
        public void close(BluetoothDevice device) {
            Toast.makeText(context, "Bluetooth off", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(BluetoothDevice device) {
            Toast.makeText(context, "Bluetooth connection failed", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        context = this;
        rvBluetoothShowList = findViewById(R.id.rv_bluetooth_show_list);
        recyclerShowAdapter = new BluetoothRecyclerViewAdapter(this, bluetoothModels);
        rvBluetoothShowList.setAdapter(recyclerShowAdapter);
        rvBluetoothShowList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //检查是否开启了定位权限（安卓6.0以上，搜索蓝牙需要定位权限）
        //Check whether the location permission is enabled (Android 6.0 or higher, searching for Bluetooth requires location permission)
        if (PermissionUtil.checkLocationPermission(this)) {
            //添加扫描到蓝牙回调
            //Add scan to Bluetooth callback
            BluetoothManager.getInstance(this).addScanBlueCallBack(scanBlueCallBack);
            //添加连接蓝牙结果回调
            //Add callback for Bluetooth connection result
            BluetoothManager.getInstance(this).addConnectResultCallBack(connectResultCallBack);
            //开始搜索蓝牙
            //Start searching for Bluetooth
            int i = BluetoothManager.getInstance(this).beginSearch();
            //蓝牙适配器未打开
            //Bluetooth adapter is not turned on
            if (i == 2) {
                BluetoothManager.getInstance(this)
                        .openBluetoothAdapter(BluetoothActivity.this, 101);
            }
        }
        //点击蓝牙列表中的item
        //Click on the item in the Bluetooth list
        recyclerShowAdapter.setOnClickItemLister(new BluetoothRecyclerViewAdapter.OnClickItemLister() {
            @Override
            public void onClick(View view, int position) {
                String bluetoothMac = bluetoothModels.get(position).getBluetoothMac();
                //配对连接蓝牙（打印机设备默认配对码：0000）
                //Pair and connect to Bluetooth (default pairing code for printer devices: 0000)
                BluetoothManager.getInstance(BluetoothActivity.this).pairBluetooth(bluetoothMac);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //定位权限申请结果回调
        //Callback of location permission application result
        if (requestCode == PermissionUtil.MY_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //添加扫描到蓝牙回调
                //Add scan to Bluetooth callback
                BluetoothManager.getInstance(this).addScanBlueCallBack(scanBlueCallBack);
                //添加连接蓝牙结果回调
                //Add a callback to the Bluetooth connection result
                BluetoothManager.getInstance(this).addConnectResultCallBack(connectResultCallBack);
                int i = BluetoothManager.getInstance(this).beginSearch();
                if (i == 2) {
                    BluetoothManager.getInstance(this)
                            .openBluetoothAdapter(BluetoothActivity.this, 101);
                }
                //连接上一次的蓝牙
                //Connect to the last Bluetooth
                BluetoothManager.getInstance(this).connectLastBluetooth();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开蓝牙适配器结果回调
        //Open the Bluetooth adapter result callback
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            BluetoothManager.getInstance(this).beginSearch();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothManager.getInstance(BluetoothActivity.this)
                .removeScanBlueCallBack(scanBlueCallBack);
        BluetoothManager.getInstance(BluetoothActivity.this)
                .removeConnectResultCallBack(connectResultCallBack);
        BluetoothManager.getInstance(BluetoothActivity.this)
                .stopSearch();
    }
}
