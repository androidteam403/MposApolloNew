package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.OmsBatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.CheckReservedQtyDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EditphysicalbatchDialog;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;

import java.util.ArrayList;

public class BatchInfoRecycleAdapter extends RecyclerView.Adapter<BatchInfoRecycleAdapter.ViewHolder> {

    private ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList;

    private EPrescriptionInfoMvpView ePrescriptionInfoMvpView;
    Context mcontext;
    boolean alertcheck = true;

    public BatchInfoRecycleAdapter(ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList, Context context) {
        this.arrBatchList = arrBatchList;
        this.ePrescriptionInfoMvpView = ePrescriptionInfoMvpView;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public BatchInfoRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OmsBatchInfoListAdapterBinding batchInfoListAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.oms_batch_info_list_adapter, parent, false);
        return new BatchInfoRecycleAdapter.ViewHolder(batchInfoListAdapterBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull BatchInfoRecycleAdapter.ViewHolder holder, int position) {
        if (arrBatchList.size() >= position) {
            GetBatchInfoRes.BatchListObj item = arrBatchList.get(position);
            holder.batchInfoListAdapterBinding.setBatchInfo(item);

           /* if(holder.batchInfoListAdapterBinding.batchWiseQtyEdit.isSelected()) {
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.requestFocus();
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(0, holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().length());
            }*/
            if (Constant.getInstance().manualSelectedPosition == position) {
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.requestFocus();
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(0, holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().length());
                InputMethodManager imm = (InputMethodManager) holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(holder.batchInfoListAdapterBinding.batchWiseQtyEdit, InputMethodManager.SHOW_IMPLICIT);
                }
            }

            holder.batchInfoListAdapterBinding.Vendormrp.setText(String.valueOf(item.getVendormrp()));
            if (item.getREQQTY() == (double) item.getREQQTY()) {
                double d = item.getREQQTY();
                int value = Integer.valueOf((int) item.getREQQTY());
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText(String.valueOf(value));
            } else {
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText(String.valueOf(item.getREQQTY()));
            }
            // alertcheck=true;
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // ePrescriptionInfoMvpView.onItemExpiryClick(position, item.getEnterReqQuantity());
                    return false;
                }
            });

            holder.itemView.setOnClickListener(v -> {
                if (ePrescriptionInfoMvpView != null) {

                    if (!item.getNearByExpiry()) {
                        // item.setPhysicalBatchID(holder.batchInfoListAdapterBinding.phisicalbatchEdit.getText().toString());
                        ePrescriptionInfoMvpView.onItemClick(position, item.getEnterReqQuantity(), item);
                        // holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setEnabled(true);
                        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.requestFocus();
                        //  holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(0, holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().length());
                        //notifyDataSetChanged();
                    }
                }
            });
            holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.batchInfoListAdapterBinding.batchWiseQtyEdit.isFocusable()) {
                        if (ePrescriptionInfoMvpView != null) {

                            if (!item.getNearByExpiry()) {
                                // item.setPhysicalBatchID(holder.batchInfoListAdapterBinding.phisicalbatchEdit.getText().toString());
                                ePrescriptionInfoMvpView.onItemClick(position, item.getEnterReqQuantity(), item);
                                // holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setEnabled(true);
                                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.requestFocus();
                                //  holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(0, holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().length());
                                //notifyDataSetChanged();
                            }
                        }
                    }
                }
            });
            holder.batchInfoListAdapterBinding.phisicalbatchEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.batchInfoListAdapterBinding.phisicalbatchEdit.isFocusable()) {
                        if (ePrescriptionInfoMvpView != null) {

                            if (!item.getNearByExpiry()) {
                                // item.setPhysicalBatchID(holder.batchInfoListAdapterBinding.phisicalbatchEdit.getText().toString());
                                ePrescriptionInfoMvpView.onItemClick(position, item.getEnterReqQuantity(), item);
                                // holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setEnabled(true);
                                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.requestFocus();
                                //  holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(0, holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().length());
                                //notifyDataSetChanged();
                            }
                        }
                    }
                }
            });
            if (item.getPhysicalbatchstatus()) {
                //item.setPhysicalbatchstatus(false);
                holder.batchInfoListAdapterBinding.batchPickupStatus.setBackgroundResource(R.drawable.icon_points_allow);
                ;
                holder.batchInfoListAdapterBinding.batchidbackground.setBackgroundResource(R.color.Light_green);

            } else {
                holder.batchInfoListAdapterBinding.batchPickupStatus.setBackgroundResource(R.drawable.icon_unchecked_checkbox);
                ;
                holder.batchInfoListAdapterBinding.batchidbackground.setBackgroundResource(R.color.white);

            }

            holder.batchInfoListAdapterBinding.batchPickupStatus.setOnClickListener(v -> {
                // holder.batchInfoListAdapterBinding.batchPickupStatus.setBackgroundResource(R.drawable.icon_points_allow);;

                holder.batchInfoListAdapterBinding.batchPickupStatus.setEnabled(false);
                if (item.getREQQTY() > 0.0) {
                    holder.batchInfoListAdapterBinding.batchPickupStatus.setEnabled(false);
                    if (item.getPhysicalbatchstatus() == true) {
                        item.setREQQTY(0);
                        item.setPhysicalbatchstatus(false);
                        item.setUpdatezeroqtystatus(true);
                        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("0");
                        Constant.getInstance().arrBatchList.get(position).setPhysicalbatchstatus(false);
                        //Constant.getInstance().arrBatchList.get(position).setUpdatezeroqtystatus(true);
                        Constant.getInstance().arrBatchList.get(position).setREQQTY(0);
                        holder.batchInfoListAdapterBinding.batchPickupStatus.setBackgroundResource(R.drawable.icon_unchecked_checkbox);
                        ;
                        holder.batchInfoListAdapterBinding.batchidbackground.setBackgroundResource(R.color.white);

                        ePrescriptionInfoMvpView.onUnpickupcheckActivity(item);
                        holder.batchInfoListAdapterBinding.batchPickupStatus.setEnabled(true);
                    } else {
                        EditphysicalbatchDialog dialogView = new EditphysicalbatchDialog(mcontext);
                        dialogView.setTitle("Enter last 3 digits of Physical Batch");
                        dialogView.setPositiveLabel("Ok");
                        dialogView.setPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String pisicalbatch_str = holder.batchInfoListAdapterBinding.phisicalbatchEdit.getText().toString();
                                if (pisicalbatch_str.length() > 2) {
                                    String substr = pisicalbatch_str.substring(pisicalbatch_str.length() - 3);
                                    //  dialogView.dismiss();
                                    if (substr.equalsIgnoreCase(dialogView.getEnterredbatch())) {
                                        item.setPhysicalbatchstatus(true);
                                        holder.batchInfoListAdapterBinding.batchPickupStatus.setBackgroundResource(R.drawable.icon_points_allow);
                                        ;
                                        holder.batchInfoListAdapterBinding.batchidbackground.setBackgroundColor(mcontext.getResources().getColor(R.color.Light_green));
                                        item.setPhysicalBatchID(pisicalbatch_str);
                                        if (Constant.getInstance().arrBatchList != null) {
                                            if (Constant.getInstance().arrBatchList.size() > 0) {
                                                Constant.getInstance().arrBatchList.get(position).setPhysicalBatchID(pisicalbatch_str);
                                            }

                                            if (Constant.getInstance().arrBatchList.size() == 1) {
                                                // ePrescriptionInfoMvpView.addsaleslineautomatically();
                                                ePrescriptionInfoMvpView.onNavigateNextActivity();
                                                Constant.getInstance().pickupstatus = false;
                                                //  Constant.getInstance().requestqty=item.getQty();
                                                // ePrescriptionInfoMvpView.onNavigateNextActivity();
                                            }
                                        }
                                    } else {
                                        //holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("0");
                                        ePrescriptionInfoMvpView.showtoastmessage("Physical Batch not matched");
                                    }

                                } else {
                                    ePrescriptionInfoMvpView.showtoastmessage("Please enter  last 3 digits of Physical Batch");

                                }
                                dialogView.dismiss();
                                notifyDataSetChanged();
                            }

                        });
                        dialogView.setNegativeLabel("Cancel");
                        dialogView.setNegativeListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                item.setPhysicalbatchstatus(false);
                                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("0");
                                dialogView.dismiss();
                            }
                        });
                        holder.batchInfoListAdapterBinding.batchPickupStatus.setEnabled(true);
                        dialogView.show();
                    }
                } else {
                    holder.batchInfoListAdapterBinding.batchPickupStatus.setEnabled(true);
                    ePrescriptionInfoMvpView.showtoastmessage("Please Enter the Req.Qty");

                }
                if (ePrescriptionInfoMvpView != null) {
                    // ePrescriptionInfoMvpView.onBatchpickupClick(position, item.getEnterReqQuantity(), item, holder.batchInfoListAdapterBinding.phisicalbatchEdit.getText().toString());
                }
            });

            holder.batchInfoListAdapterBinding.phisicalbatchEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    arrBatchList.get(position).setPhysicalBatchID(editable.toString());

                }
            });


            holder.batchInfoListAdapterBinding.batchWiseQtyEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
               /* if (editable.toString().length() > 1 && editable.toString().startsWith("0")) {
                    editable.delete(0, 1);
                }*/
                    if (editable.toString().isEmpty()) {
                        item.setREQQTY(0);
                        item.setPhysicalbatchstatus(false);

                        holder.batchInfoListAdapterBinding.batchPickupStatus.setBackgroundResource(R.drawable.icon_unchecked_checkbox);
                        // holder.batchInfoListAdapterBinding.batchidbackground.setBackgroundResource(R.color.white);
                        holder.batchInfoListAdapterBinding.batchidbackground.setBackgroundColor(mcontext.getResources().getColor(R.color.white));

                    } else if (!TextUtils.isEmpty(editable)) {
                        if (ePrescriptionInfoMvpView != null) {
                            if ((Double.parseDouble(editable.toString()) > Constant.getInstance().selectedItem.getQty()) && alertcheck) {
                                alertcheck = false;
                                CheckReservedQtyDialog dialogView = new CheckReservedQtyDialog(mcontext);
                                dialogView.setTitle("You are entering more than Req qty");
                                dialogView.setPositiveLabel("Ok");
                                dialogView.setPositiveListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        item.setREQQTY(Double.parseDouble(editable.toString()));
                                        arrBatchList.get(position).setREQQTY(Double.parseDouble(editable.toString()));
                                        dialogView.dismiss();
                                        // notifyDataSetChanged();
                                    }

                                });
                                dialogView.setNegativeLabel("Cancel");
                                dialogView.setNegativeListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        item.setPhysicalbatchstatus(false);
                                        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("0");
                                        dialogView.dismiss();
                                    }
                                });
                                dialogView.show();

                            } else {
                                item.setREQQTY(Double.parseDouble(editable.toString()));
                                if (arrBatchList.size() >= position) {
                                    arrBatchList.get(position).setREQQTY(Double.parseDouble(editable.toString()));
                                }

                            }

                        }
                    }
                    holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(editable.length());
                }
            });

            holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (ePrescriptionInfoMvpView != null) {
                            // notifyDataSetChanged();
                            ePrescriptionInfoMvpView.onNavigateNextActivity();
                        }
                        return true;
                    }
                    return false;
                }
            });
            holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (holder.batchInfoListAdapterBinding.batchWiseQtyEdit != null) {
                        if (holder.batchInfoListAdapterBinding.batchWiseQtyEdit.hasFocus()) {
                            if (holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().equalsIgnoreCase("0")) {
                                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("");
                            }
                            InputMethodManager imm = (InputMethodManager) holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.showSoftInput(holder.batchInfoListAdapterBinding.batchWiseQtyEdit, InputMethodManager.SHOW_IMPLICIT);
                            }
                        } else {
                            if (holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().isEmpty()) {
                                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("0");
                            }
                        }
                    }
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private OmsBatchInfoListAdapterBinding batchInfoListAdapterBinding;
        private LinearLayout batchitemview;

        public ViewHolder(@NonNull OmsBatchInfoListAdapterBinding batchInfoListAdapterBinding) {
            super(batchInfoListAdapterBinding.getRoot());
            this.batchInfoListAdapterBinding = batchInfoListAdapterBinding;
            this.batchitemview = batchInfoListAdapterBinding.batchidbackground;
        }
    }

    @Override
    public int getItemCount() {
        return Constant.getInstance().arrBatchList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setClickListiner(EPrescriptionInfoMvpView clickListiner) {
        this.ePrescriptionInfoMvpView = clickListiner;
    }

}

