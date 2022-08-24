package com.apollopharmacy.mpospharmacistTest.ui.ordersummary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPdfBinding;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.OrderSummaryActivity;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;

import java.util.ArrayList;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {

    Context context;

    PdfModelResponse salesLine;
    private List<PdfModelResponse.SalesLine> salesLineList = new ArrayList<>();


    public PdfAdapter(PdfModelResponse salesLine, Context applicationContext, OrderSummaryActivity orderSummaryActivity) {
        this.salesLine = salesLine;
        this.context = applicationContext;
        this.salesLineList = salesLine.getSalesLine();
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
        holder.adapterPdfBinding.productNamepdf.setText(salesLine.getItemName());
        holder.adapterPdfBinding.batch.setText(salesLine.getBatchNo());
        if (salesLine.getExpDate() != null && salesLine.getExpDate().length() > 5) {
            holder.adapterPdfBinding.expiry.setText(salesLine.getExpDate().substring(5));
        }else {
            holder.adapterPdfBinding.expiry.setText("-");
        }
        holder.adapterPdfBinding.gst.setText(String.valueOf(Double.parseDouble(salesLine.getSGSTPer()) + Double.parseDouble(salesLine.getCGSTPer())));
        holder.adapterPdfBinding.hsncode.setText(salesLine.getHSNCode());
        holder.adapterPdfBinding.mfg.setText(salesLine.getManufacturer());
        holder.adapterPdfBinding.qty.setText(salesLine.getQty());
        holder.adapterPdfBinding.rate.setText(salesLine.getMrp());
        holder.adapterPdfBinding.sch.setText(salesLine.getSch());
        holder.adapterPdfBinding.amount.setText(salesLine.getLineTotAmount());
    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPdfBinding adapterPdfBinding;

        public ViewHolder(@NonNull AdapterPdfBinding adapterPdfBinding) {
            super(adapterPdfBinding.getRoot());
            this.adapterPdfBinding = adapterPdfBinding;
        }
    }
}
