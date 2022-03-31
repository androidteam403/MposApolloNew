package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPrinterDevicesListPBinding;


public class PrinterDeviceListAdapter extends RecyclerView.Adapter<PrinterDeviceListAdapter.ViewHolder> {
    private Context mContext;
    private String[] printerDeviceList;

    public PrinterDeviceListAdapter(Context mContext, String[] printerDeviceList) {
        this.mContext = mContext;
        this.printerDeviceList = printerDeviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPrinterDevicesListPBinding printerDevicesListBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_printer_devices_list_p, parent, false);
        return new ViewHolder(printerDevicesListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = printerDeviceList[position];
        holder.printerDevicesListBinding.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return printerDeviceList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPrinterDevicesListPBinding printerDevicesListBinding;

        public ViewHolder(@NonNull AdapterPrinterDevicesListPBinding printerDevicesListBinding) {
            super(printerDevicesListBinding.getRoot());
            this.printerDevicesListBinding = printerDevicesListBinding;
        }
    }
}
