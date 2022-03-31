package com.apollopharmacy.mpospharmacistTest.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;

public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    public BluetoothSocket bluetoothSocket = null;

    public boolean isConnected = false;
    public String CONNECTED_BLUETOOTH_DEVICE_NAME = "";
    public String EXTRA_DEVICE_ADDRESS = "";
    public BluetoothAdapter mBluetoothAdapter = null;

    private Singleton() {
    }
}
