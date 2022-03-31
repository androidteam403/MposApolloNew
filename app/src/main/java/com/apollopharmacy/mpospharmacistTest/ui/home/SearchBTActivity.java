package com.apollopharmacy.mpospharmacistTest.ui.home;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBluetoothSearchBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.adptor.BtSearchAdapter;
import com.apollopharmacy.mpospharmacistTest.utils.BTSearchItem;
//import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity.setBluetooth;

import java.util.ArrayList;
import java.util.List;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;
import static com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity.setBluetooth;

public class SearchBTActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityBluetoothSearchBinding btSearchBinding;
    private BtSearchAdapter mAdapter;
    private BtSearchAdapter pairedListAdapter;
    private List<BTSearchItem> devicesList = new ArrayList<>();
    private List<BTSearchItem> pairedDevicesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_bluetooth_search);

        Boolean isBTOn = setBluetooth(true);
        if (isBTOn) {
            // Register for broadcasts when a device is discovered
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
             this.registerReceiver(mReceiver, filter);

            // Register for broadcasts when discovery has finished
            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
             this.registerReceiver(mReceiver, filter);
        }


        if (getIntent() != null) {
            pairedDevicesList = (List<BTSearchItem>) getIntent().getSerializableExtra("PairedBTList");
        }

        if (pairedDevicesList.size() == 0) {
            btSearchBinding.noPairedDeviceText.setVisibility(View.VISIBLE);
        }

        pairedListAdapter = new BtSearchAdapter(pairedDevicesList, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        btSearchBinding.recyclerPaired.setLayoutManager(mLayoutManager1);
        btSearchBinding.recyclerPaired.setItemAnimator(new DefaultItemAnimator());
        btSearchBinding.recyclerPaired.setAdapter(pairedListAdapter);


        btSearchBinding.backtohome.setOnClickListener(v -> {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //  startActivityForResult(MainActivity.getStartIntent(getContext()));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();

        });
       /* mAdapter = new BtSearchAdapter(devicesList, SearchBTActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        btSearchBinding.recyclerUnPaired.setLayoutManager(mLayoutManager);
        btSearchBinding.recyclerUnPaired.setItemAnimator(new DefaultItemAnimator());
        btSearchBinding.recyclerUnPaired.setAdapter(mAdapter);*/
    }

   private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Boolean isAvailable = false;
               // findViewById(R.id.no_device_text).setVisibility(View.GONE);
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
                        Log.e("SearchBTActivity", "Device :: " + device.getName() + " - " + device.getAddress());
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Nothing To DO
            }
        }
    };


    @Override
    public void onConnectRequest(int position, BTSearchItem item) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_item", item);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();

    }

    /*public static Context getContext() {
        return context;
    }*/
}
