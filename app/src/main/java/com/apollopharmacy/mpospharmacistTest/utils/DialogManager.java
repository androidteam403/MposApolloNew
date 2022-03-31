package com.apollopharmacy.mpospharmacistTest.utils;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;

import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;

import java.util.Set;

public class DialogManager
{
    public static void showBluetoothDialog(Context context, final Set<BluetoothDevice> pairedDevices) {
        final String[] items = new String[pairedDevices.size()];
        int index = 0;
        for (BluetoothDevice device : pairedDevices) {
            items[index++] = device.getName() + "\n" + device.getAddress();
        }

        new AlertDialog.Builder(context).setTitle("Paired Bluetooth printers")
                .setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        String strSelectList = items[which];
                        String temp;
                        int indexSpace = 0;
                        for (int i = 5; i < strSelectList.length(); i++) {
                            temp = strSelectList.substring(i - 5, i);
                            if ((temp.equals("00:10")) || (temp.equals("74:F0")) || (temp.equals("00:15")) || (temp.equals("DD:C5"))) {
                                indexSpace = i;
                                i = 100;
                            }
                        }
                        String strDeviceInfo = null;
                        strDeviceInfo = strSelectList.substring(indexSpace - 5, strSelectList.length());

                        MainActivity.mBixolonLabelPrinter.connect(strDeviceInfo);

                    }
                }).show();
    }
}
