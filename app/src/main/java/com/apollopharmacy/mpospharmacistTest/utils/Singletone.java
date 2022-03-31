package com.apollopharmacy.mpospharmacistTest.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;

import java.util.ArrayList;

public class Singletone {
    private static final Singletone ourInstance = new Singletone();

    public static Singletone getInstance() {
        return ourInstance;
    }

    private Singletone() {
    }

    public ArrayList<SalesLineEntity> itemsArrayList = new ArrayList<>();
    public GetTenderTypeRes.GetTenderTypeResultEntity tenderTypeResultEntity;
    public boolean isPlaceNewOrder = false;
    public boolean isOrderCompleted = false;
    public boolean isManualBilling = false;
    public boolean isReturn = false;


    public BluetoothSocket bluetoothSocket = null;

    public boolean isConnected = false;
    public String CONNECTED_BLUETOOTH_DEVICE_NAME = "";
    public String EXTRA_DEVICE_ADDRESS = "";
    public BluetoothAdapter mBluetoothAdapter = null;

}
