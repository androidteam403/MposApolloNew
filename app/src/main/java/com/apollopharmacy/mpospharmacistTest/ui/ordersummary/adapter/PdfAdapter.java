package com.apollopharmacy.mpospharmacistTest.ui.ordersummary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPdfBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSummaryProductsPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.OrderSummaryActivity;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.adapter.SummaryProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder>{

    Context context;

    PdfModelResponse salesLine;
    private List<PdfModelResponse.SalesLine> salesLineList = new ArrayList<>();


    public PdfAdapter(PdfModelResponse salesLine, Context applicationContext, OrderSummaryActivity orderSummaryActivity) {
        this.salesLine=salesLine;
        this.context=applicationContext;
        this.salesLineList=salesLine.getSalesLine();
    }

    @NonNull
    @Override
    public PdfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPdfBinding adapterPdfBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_pdf, parent, false);
        return new ViewHolder(adapterPdfBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfAdapter.ViewHolder holder, int position) {
        PdfModelResponse.SalesLine salesLine = salesLineList.get(position);
        holder.adapterPdfBinding.productNamepdf.setText(salesLineList.get(position).getItemName());
        holder.adapterPdfBinding.batch.setText(salesLineList.get(position).getBatchNo());
        holder.adapterPdfBinding.expiry.setText(salesLineList.get(position).getExpDate());
        holder.adapterPdfBinding.gst.setText(salesLineList.get(position).getSGSTPer());
        holder.adapterPdfBinding.hsncode.setText(salesLineList.get(position).getHSNCode());
        holder.adapterPdfBinding.mfg.setText(salesLineList.get(position).getManufacturer());
        holder.adapterPdfBinding.qty.setText(salesLineList.get(position).getQty());
        holder.adapterPdfBinding.rate.setText(salesLineList.get(position).getLineTotAmount());
         holder.adapterPdfBinding.sch.setText(salesLineList.get(position).getSch());
         holder.adapterPdfBinding.amount.setText(salesLineList.get(position).getMrp());
    }

    @Override
    public int getItemCount()
    {
        return salesLineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPdfBinding adapterPdfBinding;
        public ViewHolder(@NonNull AdapterPdfBinding adapterPdfBinding ) {
            super(adapterPdfBinding.getRoot());
            this.adapterPdfBinding=adapterPdfBinding;
        }
    }
}
