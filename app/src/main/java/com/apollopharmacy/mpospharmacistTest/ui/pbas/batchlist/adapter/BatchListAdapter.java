package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterBatchlistPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.CheckReservedQtyDialog;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BatchListAdapter extends RecyclerView.Adapter<BatchListAdapter.ViewHolder> {
    private Context mContext;
    private List<GetBatchInfoRes.BatchListObj> batchListModelListl = new ArrayList<>();
    private List<GetBatchInfoRes.BatchListObj> omsHeaderList = new ArrayList<>();
    private List<GetBatchInfoRes.BatchListObj> filteredList = new ArrayList<>();
    private BatchListMvpView batchListMvpView;
    boolean batchSelected=false;
    double reqqty;
    double Reservedqty;
    boolean alertcheck = true;

    public BatchListAdapter(Context mContext, List<GetBatchInfoRes.BatchListObj> batchListModelList1, BatchListMvpView mvpView) {
        this.mContext = mContext;
        this.batchListModelListl = batchListModelList1;
        this.omsHeaderList=batchListModelList1;
        this.batchListMvpView = mvpView;
        this.reqqty=reqqty;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBatchlistPBinding adapterBatchlistBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_batchlist_p, parent, false);
        return new BatchListAdapter.ViewHolder(adapterBatchlistBinding);
    }

String requiredQty;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj batchListModel = batchListModelListl.get(position);

        holder.adapterBatchlistBinding.batchno.setText(batchListModel.getBatchNo());
        holder.adapterBatchlistBinding.qohCoount.setText(batchListModel.getQ_O_H());
        holder.adapterBatchlistBinding.expiryDate.setText(batchListModel.getExpDate());
        holder.adapterBatchlistBinding.phisicalbatchEdit.setText(batchListModel.getBatchNo());
        holder.adapterBatchlistBinding.mrp.setText(String.valueOf(batchListModel.getMRP()));



//        holder.adapterBatchlistBinding.requiredQuantity.setText(reqqty);




       holder.adapterBatchlistBinding.requiredQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.adapterBatchlistBinding.requiredQuantity.isFocusable()) {
                        if (batchListMvpView != null) {

                            if (!batchListModel.getNearByExpiry()) {
                                // item.setPhysicalBatchID(holder.batchInfoListAdapterBinding.phisicalbatchEdit.getText().toString());
                                batchListMvpView.onItemClick(position, batchListModel.getEnterReqQuantity(), batchListModel);
                                // holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setEnabled(true);
                                holder.adapterBatchlistBinding.requiredQuantity.requestFocus();
                                //  holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(0, holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().length());
                                //notifyDataSetChanged();
                            }
                        }
                    }
                }
            });

        holder.adapterBatchlistBinding.requiredQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                 if (!TextUtils.isEmpty(editable)) {
                    if (batchListMvpView != null) {
                        if (Double.parseDouble(editable.toString()) > Double.parseDouble(batchListModel.getQ_O_H())) {

                            CheckReservedQtyDialog dialogView = new CheckReservedQtyDialog(mContext);
                            dialogView.setTitle("You are entering more than Req qty");
                            dialogView.setPositiveLabel("Ok");
                            dialogView.setPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   Reservedqty = 0;
                                    batchListModel.setREQQTY(Double.parseDouble(editable.toString()));
                                    batchListModelListl.get(position).setREQQTY(Double.parseDouble(editable.toString()));
                                    Reservedqty = Reservedqty + batchListModel.getREQQTY();
                                    dialogView.dismiss();
//                                     notifyDataSetChanged();
                                }

                            });
                            dialogView.setNegativeLabel("Cancel");
                            dialogView.setNegativeListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    batchListModel.setPhysicalbatchstatus(false);
                                    holder.adapterBatchlistBinding.requiredQuantity.setText("0");
                                    dialogView.dismiss();
                                }
                            });
                            dialogView.show();

                        }
//                       else {
//                            batchListModel.setREQQTY(Double.parseDouble(editable.toString()));
//                            if (batchListModelListl.size() >= position) {
//                                batchListModelListl.get(position).setREQQTY(Double.parseDouble((editable.toString())));
//                            }
//
//                        }

                    }
                }
            }
        });

              if (reqqty == (double) reqqty) {
                double d = reqqty;
                int value = Integer.valueOf((int) reqqty);
                holder.adapterBatchlistBinding.requiredQuantity.setText(String.valueOf(value));
            } else {
                holder.adapterBatchlistBinding.requiredQuantity.setText(String.valueOf(reqqty));
            }

                holder.adapterBatchlistBinding.batchPickupStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.GONE);
                holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.VISIBLE);
                holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.Light_green);
                batchSelected=true;
                batchListModel.setSelected(true);
                batchListModel.setBatchNo(batchListModelListl.get(position).getBatchNo());
                batchListModel.setREQQTY(Double.parseDouble(holder.adapterBatchlistBinding.requiredQuantity.getText().toString()));
                batchListMvpView.onCheckBoxClick(batchListModel, position, Reservedqty);
            }
        });

        holder.adapterBatchlistBinding.iconPointsAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.GONE);
                holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.VISIBLE);
                holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.white);
                batchListModel.setBatchNo(batchListModelListl.get(position).getBatchNo());
                batchListModel.setSelected(false);
                batchListModel.setREQQTY(Double.parseDouble(holder.adapterBatchlistBinding.requiredQuantity.getText().toString()));
                batchListMvpView.onUncheckBoxClick(batchListModel, position);



            }
        });


        if (batchListModelListl.get(position).isSelected()) {
            holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.GONE);
            holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.VISIBLE);
            holder.adapterBatchlistBinding.requiredQuantity.setText((String.valueOf(batchListModelListl.get(position).getREQQTY())));
            holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.Light_green);
//            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
        } else {
            holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.GONE);
            holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.VISIBLE);
            holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.white);
//            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
        }

        holder.adapterBatchlistBinding.requiredQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (batchListMvpView != null) {
                        // notifyDataSetChanged();
                        batchListMvpView.onNavigateNextActivity();
                    }
                    return true;
                }
                return false;
            }
        });

        holder.adapterBatchlistBinding.requiredQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (holder.adapterBatchlistBinding.requiredQuantity != null) {
                    if (holder.adapterBatchlistBinding.requiredQuantity.hasFocus()) {
                        if (holder.adapterBatchlistBinding.requiredQuantity.getText().toString().equalsIgnoreCase("0")) {
                            holder.adapterBatchlistBinding.requiredQuantity.setText("");
                        }
                        InputMethodManager imm = (InputMethodManager) holder.adapterBatchlistBinding.requiredQuantity.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(holder.adapterBatchlistBinding.requiredQuantity, InputMethodManager.SHOW_IMPLICIT);
                        }
                    } else {
                        if (holder.adapterBatchlistBinding.requiredQuantity.getText().toString().isEmpty()) {
                            holder.adapterBatchlistBinding.requiredQuantity.setText("0");
                        }
                    }
                }
            }
        });




//        if (batchListModel.isBatchidSelect())
//            Glide.with(mContext).load(R.drawable.ic_circle_tick).into(holder.adapterBatchlistBinding.batchIdSelectIcon);
//        else
//            Glide.with(mContext).load(R.drawable.ic_circle_stroke).into(holder.adapterBatchlistBinding.batchIdSelectIcon);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mvpView != null)
//                    mvpView.onClickItem(position);
//            }
//        });
    }


    @Override
    public int getItemCount() {

        int count = 0;
        if (batchListModelListl != null && batchListModelListl.size() > 0) {
            count = batchListModelListl.size();
        }
        return count;
    }



    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    batchListModelListl = omsHeaderList;
                } else {
                    filteredList.clear();
                    for (GetBatchInfoRes.BatchListObj row : omsHeaderList) {
                        if (!filteredList.contains(row) && (row.getBatchNo().contains(charString))) {
                            filteredList.add(row);
                        }

                    }
                    batchListModelListl = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = batchListModelListl;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (batchListModelListl != null && !batchListModelListl.isEmpty()) {
                    batchListModelListl = (List<GetBatchInfoRes.BatchListObj>) filterResults.values;
                    try {
                        batchListMvpView.noOrderFound(batchListModelListl.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("FullfilmentAdapter", e.getMessage());
                    }
                } else {
                    batchListMvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterBatchlistPBinding adapterBatchlistBinding;

        public ViewHolder(@NonNull AdapterBatchlistPBinding adapterBatchlistBinding) {
            super(adapterBatchlistBinding.getRoot());
            this.adapterBatchlistBinding = adapterBatchlistBinding;
        }
    }
}
