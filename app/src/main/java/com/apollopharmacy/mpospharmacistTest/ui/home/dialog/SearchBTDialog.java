package com.apollopharmacy.mpospharmacistTest.ui.home.dialog;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBluetoothSearchBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogSmspayPaymentBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.OnItemClickListener;
import com.apollopharmacy.mpospharmacistTest.ui.home.SearchBTActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.adptor.BtSearchAdapter;
import com.apollopharmacy.mpospharmacistTest.utils.BTSearchItem;
import com.apollopharmacy.mpospharmacistTest.utils.DecimalDigitsInputFilter;

import java.util.ArrayList;
import java.util.List;

import static com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity.setBluetooth;

public class SearchBTDialog  extends AppCompatActivity implements OnItemClickListener
{
    private Dialog dialog;
    private Context context;
    private ActivityBluetoothSearchBinding btSearchBinding;

    private BtSearchAdapter mAdapter;
    public BtSearchAdapter pairedListAdapter;
    private List<BTSearchItem> devicesList = new ArrayList<>();
    private List<BTSearchItem> pairedDevicesList = new ArrayList<>();

    public SearchBTDialog(Context context) {

        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        btSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.activity_bluetooth_search, null, false);
        dialog.setContentView(btSearchBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUp();
        //  setDialogDismiss();

    }

    private void setUp() {
        Boolean isBTOn = setBluetooth(true);
        if (isBTOn) {
            // Register for broadcasts when a device is discovered
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
           // this.registerReceiver(mReceiver, filter);

            // Register for broadcasts when discovery has finished
            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
          //  this.registerReceiver(mReceiver, filter);
        }

        /*if (getIntent() != null) {
            pairedDevicesList = (List<BTSearchItem>) getIntent().getSerializableExtra("PairedBTList");
        }*/

        pairedListAdapter = new BtSearchAdapter(pairedDevicesList, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        btSearchBinding.recyclerPaired.setLayoutManager(mLayoutManager1);
        btSearchBinding.recyclerPaired.setItemAnimator(new DefaultItemAnimator());
        btSearchBinding.recyclerPaired.setAdapter(pairedListAdapter);

      /*  mAdapter = new BtSearchAdapter(devicesList, SearchBTDialog.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        btSearchBinding.recyclerUnPaired.setLayoutManager(mLayoutManager);
        btSearchBinding.recyclerUnPaired.setItemAnimator(new DefaultItemAnimator());
        btSearchBinding.recyclerUnPaired.setAdapter(mAdapter);*/
    }

   /* private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
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
                        Log.e("SearchBTActivity", "Device :: " + device.getName() + " - " + device.getAddress());
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Nothing To DO
            }
        }
    };*/

    @Override
    public void onConnectRequest(int position, BTSearchItem item) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_item", item);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

   /* public void setCalculatedPosTransaction(CalculatePosTransactionRes posTransaction) {
        smspayPaymentBinding.setOrder(posTransaction);
        smspayPaymentBinding.walletMobileNumber.setSelection(smspayPaymentBinding.walletMobileNumber.getText().length());
        smspayPaymentBinding.walletAmountEdit.setSelection(smspayPaymentBinding.walletAmountEdit.getText().length());
    }


    public String getWalletMobileNumber() {
        return smspayPaymentBinding.walletMobileNumber.getText().toString();
    }

    public String getWalletAmount() {
        return String.valueOf(Double.parseDouble(smspayPaymentBinding.walletAmountEdit.getText().toString()));
    }

    public String getTransactionId() {
        return smspayPaymentBinding.transactionid.getText().toString();
    }

    public void setPaymentMethod(PaymentMethodModel paymentMethod) {
        smspayPaymentBinding.setPayment(paymentMethod);
    }

    public void setCloseListener(View.OnClickListener okListener) {
        smspayPaymentBinding.dialogCloseBtn.setOnClickListener(okListener);
    }
    public void setGenerateLinkListener(View.OnClickListener otpListener) {
        smspayPaymentBinding.dialogGenerateBtn.setOnClickListener(otpListener);
    }
    public void setValidateLinkListener(View.OnClickListener otpListener) {
        smspayPaymentBinding.dialogValidateBtn.setOnClickListener(otpListener);
    }

    public void setCanceltransactionListener(View.OnClickListener otpListener) {
        smspayPaymentBinding.dialogCancelBtn.setOnClickListener(otpListener);
    }


    public boolean isValidateAmount() {
        if (TextUtils.isEmpty(smspayPaymentBinding.walletMobileNumber.getText().toString())) {
            smspayPaymentBinding.walletMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (smspayPaymentBinding.walletMobileNumber.getText().toString().length() < 10) {
            smspayPaymentBinding.walletMobileNumber.setError("Enter valid Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(smspayPaymentBinding.walletAmountEdit.getText().toString())) {
            smspayPaymentBinding.walletAmountEdit.setError("Enter Amount");
            return false;
        }

        return true;
    }*/

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }
}
