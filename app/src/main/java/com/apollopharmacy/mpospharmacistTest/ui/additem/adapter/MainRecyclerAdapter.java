package com.apollopharmacy.mpospharmacistTest.ui.additem.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.core.view.MotionEventCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ExitInfoDialogBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.ListItemMainBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ItemBaseViewHolder> {

    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    private List<SalesLineEntity> mDatas;
    private Context mContext;
    private ItemTouchHelperExtension mItemTouchHelperExtension;
    private AddItemMvpView addItemMvpView;

    public MainRecyclerAdapter(Context context, ArrayList<SalesLineEntity> medicineDetailsModelArrayList) {
        mDatas = medicineDetailsModelArrayList;
        mContext = context;
    }

    public void setAddItemMvpView(AddItemMvpView addItemMvpView) {
        this.addItemMvpView = addItemMvpView;
    }

    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }


    @NotNull
    @Override
    public ItemBaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ListItemMainBinding itemMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_main, parent, false);
        return new ItemSwipeWithActionWidthViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final ItemBaseViewHolder holder, int position) {

        System.out.println("batch number-->" + mDatas.get(position).getInventBatchId());

        SalesLineEntity item = mDatas.get(position);
        holder.listItemMainBinding.setProduct(item);
        if (item.getIsVoid()) {
            holder.listItemMainBinding.mainContentView.remainingDays.setEnabled(false);
        } else {
            holder.listItemMainBinding.mainContentView.remainingDays.setEnabled(true);
        }
        if (item.getCategoryCode().equalsIgnoreCase("P")) {
            holder.listItemMainBinding.mainContentView.itemIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_pharma));
        } else if (item.getCategoryCode().equalsIgnoreCase("F")) {
            holder.listItemMainBinding.mainContentView.itemIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_fmcg));
        } else if (item.getCategoryCode().equalsIgnoreCase("A")) {
            holder.listItemMainBinding.mainContentView.itemIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_h_b));
        }

        holder.listItemMainBinding.mainContentView.viewListMainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
            ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
            viewHolder.mActionViewRefresh.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (addItemMvpView != null) {
                                addItemMvpView.onItemEdit(item);
                            }
                            mItemTouchHelperExtension.closeOpened();
                        }
                    }

            );
            viewHolder.mActionViewDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (item.getIsVoid()) {
                                if (addItemMvpView != null) {
                                    addItemMvpView.onItemAdded(item.getLineNo());
                                }
                            } else {
                                if (addItemMvpView != null) {
//                                    if (item.getItemId().equalsIgnoreCase("ESH0002") || item.getItemId().equalsIgnoreCase("PAC0237")) {
//                                        showMessagePopup("Cant void " + item.getItemName());
//                                    } else {
                                        addItemMvpView.onItemDeleted(item.getLineNo(), item);
//                                    }

                                }
                            }
                            mItemTouchHelperExtension.closeOpened();
                        }
                    }

            );
        }

        holder.listItemMainBinding.mainContentView.remainingDays.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (holder.listItemMainBinding.mainContentView.remainingDays.hasFocus()) {
                    if (holder.listItemMainBinding.mainContentView.remainingDays.getText().toString().equalsIgnoreCase("0.0")) {
                        holder.listItemMainBinding.mainContentView.remainingDays.setText("");
                    }
                    InputMethodManager imm = (InputMethodManager) holder.listItemMainBinding.mainContentView.remainingDays.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(holder.listItemMainBinding.mainContentView.remainingDays, InputMethodManager.SHOW_IMPLICIT);
                    }
                } else {
                    if (holder.listItemMainBinding.mainContentView.remainingDays.getText().toString().isEmpty()) {
                        holder.listItemMainBinding.mainContentView.remainingDays.setText("0.0");
                    }
                }
            }
        });
        holder.listItemMainBinding.mainContentView.remainingDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    item.setRemainderDays(0.0);
                } else {
                    if (editable.length() > 0) {
                        item.setRemainderDays(Double.parseDouble(editable.toString()));
                    }
                }
            }

        });
    }

    private void showMessagePopup(String message) {
        Dialog showMessagePopup = new Dialog(mContext);
        ExitInfoDialogBinding exitInfoDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.exit_info_dialog, null, false);
        showMessagePopup.setCancelable(false);
        showMessagePopup.setContentView(exitInfoDialogBinding.getRoot());
        if (showMessagePopup.getWindow() != null)
            showMessagePopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitInfoDialogBinding.title.setVisibility(View.VISIBLE);
        exitInfoDialogBinding.title.setText(message);

        exitInfoDialogBinding.subtitle.setVisibility(View.GONE);
        exitInfoDialogBinding.subtitle.setText(message);
        exitInfoDialogBinding.dialogButtonNO.setVisibility(View.GONE);
//        exitInfoDialogBinding.sepe
        exitInfoDialogBinding.dialogButtonOK.setText("OK");
        exitInfoDialogBinding.dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessagePopup.dismiss();
            }
        });
        showMessagePopup.show();

    }

    private void doDelete(int adapterPosition) {
        mDatas.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void move(int from, int to) {
        SalesLineEntity prev = mDatas.remove(from);
        mDatas.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        public ListItemMainBinding listItemMainBinding;
        RecyclerView batch_info_recycler;


        public ItemBaseViewHolder(ListItemMainBinding item) {
            super(item.getRoot());
            this.listItemMainBinding = item;
        }

        public void bind() {
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mItemTouchHelperExtension.startDrag(ItemBaseViewHolder.this);
                    }
                    return true;
                }
            });
        }
    }


    class ItemSwipeWithActionWidthViewHolder extends ItemBaseViewHolder implements Extension {

        View mActionViewDelete;
        View mActionViewRefresh;

        public ItemSwipeWithActionWidthViewHolder(ListItemMainBinding itemView) {
            super(itemView);
            mActionViewDelete = itemView.getRoot().findViewById(R.id.view_list_repo_action_delete);
            mActionViewRefresh = itemView.getRoot().findViewById(R.id.view_list_repo_action_update);
        }

        @Override
        public float getActionWidth() {
            return listItemMainBinding.viewListRepoActionContainer.getWidth();
        }
    }

}
