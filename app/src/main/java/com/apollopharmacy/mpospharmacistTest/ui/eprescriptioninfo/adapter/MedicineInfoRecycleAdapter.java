package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewEprescMedicineItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineInfoEntity;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedicineInfoRecycleAdapter extends RecyclerView.Adapter<MedicineInfoRecycleAdapter.ItemBaseViewHolder> {

    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    private List<MedicineInfoEntity> mDatas;
    private List<MedicineInfoEntity> tempmDatas;
    private Context mContext;
    private EPrescriptionInfoMvpView ePrescriptionInfoMvpView;
    private BatchInfoRecycleAdapter batchInfoRecycleAdapter;
    int selected_position = -1;
    int previous_selectedposition = -1;
    boolean isItemselected = false;

    public MedicineInfoRecycleAdapter(Context context, ArrayList<MedicineInfoEntity> medicineInforArrList) {
        mDatas = medicineInforArrList;
        mContext = context;
        tempmDatas = new ArrayList<>();
    }

    @NotNull
    @Override
    public MedicineInfoRecycleAdapter.ItemBaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ViewEprescMedicineItemBinding itemMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_epresc_medicine_item, parent, false);
        return new ItemBaseViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final ItemBaseViewHolder holder, int position) {
        MedicineInfoEntity item = mDatas.get(position);
        holder.listItemMainBinding.setProduct(item);
        //tempmDatas.add(item);
        System.out.println("check item quantity-->" + item.getReqQty());
        Constant.getInstance().requestqty = item.getQty();

        holder.listItemMainBinding.requestqtyText.setText(String.valueOf(item.getReqQty()));

        if (Double.parseDouble(holder.listItemMainBinding.requestqtyText.getText().toString()) > 0) {
            holder.listItemMainBinding.itemViewLiner.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CCFF99")));
        } else {
            holder.listItemMainBinding.itemViewLiner.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        }

        //below steps for Check Boxes....
        if (Constant.getInstance().arrBatchList.size() > 0) {
            double QOH_Qty = 0;
            for (int i = 0; i < Constant.getInstance().arrBatchList.size(); i++) {
                if (Double.parseDouble(Constant.getInstance().arrBatchList.get(i).getQ_O_H()) > 0 && Constant.getInstance().arrBatchList != null) {
                    QOH_Qty = QOH_Qty + Double.parseDouble(Constant.getInstance().arrBatchList.get(i).getQ_O_H());
                }


            }
            holder.Qohtextview.setText(String.valueOf(QOH_Qty));
            //holder.listItemMainBinding.Qohcounttext.setText(String.valueOf(QOH_Qty));
        }


        holder.batchinfolist.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

//        ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList = new ArrayList<>();
//        if (Constant.getInstance().arrBatchList != null && Constant.getInstance().arrBatchList.size() > 0) {
//            for (int i = 0; i < Constant.getInstance().arrBatchList.size(); i++) {
//                arrBatchList.add(Constant.getInstance().arrBatchList.get(i));
//                if (i > 4) {
//                    break;
//                }
//            }
//        }
//        if (Double.parseDouble(holder.listItemMainBinding.requestqtyText.getText().toString()) <= 0) {
//            if (Constant.getInstance().arrBatchList != null && Constant.getInstance().arrBatchList.size() > 0) {
//                for (int i = 0; i < Constant.getInstance().arrBatchList.size(); i++) {
//                    Constant.getInstance().arrBatchList.get(i).setPhysicalbatchstatus(false);
//                }
//            }
//        }
        batchInfoRecycleAdapter = new BatchInfoRecycleAdapter(Constant.getInstance().arrBatchList, mContext);
        batchInfoRecycleAdapter.setMedicineItemQty(item.getQty());
        holder.batchinfolist.setAdapter(batchInfoRecycleAdapter);
        batchInfoRecycleAdapter.setClickListiner(ePrescriptionInfoMvpView);

        if (selected_position == position && isItemselected == true) {
            if (Constant.getInstance().arrBatchList != null) {
                if (Constant.getInstance().arrBatchList.size() > 0) {
                    //isItemselected=false;
                    for (int i = 0; i < Constant.getInstance().arrBatchList.size(); i++) {
                        if (Constant.getInstance().arrBatchList.get(i).isSelected() && Constant.getInstance().arrBatchList.get(i).getREQQTY() == 0) {
                            Constant.getInstance().arrBatchList.get(i).setSelected(false);
                            holder.batchinfolist.getAdapter().notifyItemChanged(i);

                        } else if (Constant.getInstance().arrBatchList.get(i).getREQQTY() > 0) {
                            Constant.getInstance().arrBatchList.get(i).setSelected(true);
                            holder.batchinfolist.getAdapter().notifyItemChanged(i);
                        } else {

                        }
                    }
                    Constant.getInstance().arrBatchList.get(Constant.getInstance().manualSelectedPosition).setSelected(true);
                    //Constant.getInstance().arrBatchList=arrBatchList;
                    holder.batchinfolist.getAdapter().notifyItemChanged(Constant.getInstance().manualSelectedPosition);
                    holder.batchinfo_layout.setVisibility(View.VISIBLE);

                    if (Constant.getInstance().isdoneBatchSelect == true) {
                        holder.batchinfo_layout.setVisibility(View.GONE);
                    }
                }
            }
        } else {
            holder.batchinfo_layout.setVisibility(View.GONE);
            //notifyDataSetChanged();
        }

        holder.addItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.getInstance().pickupstatus = false;
                Constant.getInstance().requestqty = item.getQty();
                ePrescriptionInfoMvpView.onNavigateNextActivity();
                //ePrescriptionInfoMvpView.onClickProductItem(position);
            }
        });
        holder.listItemMainBinding.addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double totalBatchSelectedQty = 0.0;
                for (int i = 0; i < Constant.getInstance().arrBatchList.size(); i++) {
                    totalBatchSelectedQty = totalBatchSelectedQty + Constant.getInstance().arrBatchList.get(i).getREQQTY();
                }
                if (totalBatchSelectedQty > item.getQty()) {
//                    Toast.makeText(mContext, "You have enter more than request Qty", Toast.LENGTH_SHORT).show();
                    ePrescriptionInfoMvpView.showtoastmessage("You have enter more than request Qty");
                } else {
                    Constant.getInstance().pickupstatus = false;
                    Constant.getInstance().requestqty = item.getQty();
                    ePrescriptionInfoMvpView.onNavigateNextActivity();
                }
            }
        });
        holder.listItemMainBinding.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ePrescriptionInfoMvpView != null) {
                    //   holder.listItemMainBinding.itemView.removeAllViews();
//                    ePrescriptionInfoMvpView.onClickProductItem(position);
                    if (isItemselected == false && previous_selectedposition != position) {
                        isItemselected = true;
                        selected_position = position;
                        ePrescriptionInfoMvpView.onClickProductItem(position);
                    } else if (previous_selectedposition == position && isItemselected == true) {
                        ePrescriptionInfoMvpView.onClickProductItem(position);
                        isItemselected = false;
                        selected_position = position;
                        notifyDataSetChanged();
                    } else if (previous_selectedposition != position && isItemselected == true) {
                        isItemselected = true;
                        selected_position = position;
                        ePrescriptionInfoMvpView.onClickProductItem(position);
                    } else if (previous_selectedposition == position && isItemselected == false) {
                        isItemselected = true;
                        selected_position = position;
                        ePrescriptionInfoMvpView.onClickProductItem(position);
                    }

                    previous_selectedposition = position;


                }
            }
        });
    }


  /*  public void getrequestedqty()
    {
        Constant.getInstance().pickupstatus=false;
        Constant.getInstance().requestqty=item.getQty();
        ePrescriptionInfoMvpView.onNavigateNextActivity();
    }*/


    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setClickListiner(EPrescriptionInfoMvpView clickListiner) {
        this.ePrescriptionInfoMvpView = clickListiner;
    }


    static class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        public ViewEprescMedicineItemBinding listItemMainBinding;
        RecyclerView batchinfolist;
        LinearLayout batchinfo_layout;
        TextView addItemText;
        TextView Qohtextview;

        public ItemBaseViewHolder(ViewEprescMedicineItemBinding item) {
            super(item.getRoot());
            batchinfolist = item.batchInfoRecycler;
            this.listItemMainBinding = item;
            batchinfo_layout = item.batchInfoLayout;
            addItemText = item.addItems;
            Qohtextview = item.Qohcounttext;
        }
    }
}
