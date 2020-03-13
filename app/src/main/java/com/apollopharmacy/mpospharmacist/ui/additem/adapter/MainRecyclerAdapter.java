package com.apollopharmacy.mpospharmacist.ui.additem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ListItemMainBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.MotionEventCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ItemBaseViewHolder> {

    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    private List<GetItemDetailsRes.Items> mDatas;
    private Context mContext;
    private ItemTouchHelperExtension mItemTouchHelperExtension;
    private AddItemMvpView addItemMvpView;

    public MainRecyclerAdapter(Context context,ArrayList<GetItemDetailsRes.Items> medicineDetailsModelArrayList) {
        mDatas = medicineDetailsModelArrayList;
        mContext = context;
    }


    public void setAddItemMvpView(AddItemMvpView addItemMvpView){
        this.addItemMvpView = addItemMvpView;
    }
    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }


    @NotNull
    @Override
    public MainRecyclerAdapter.ItemBaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
      ListItemMainBinding itemMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_main, parent, false);
        return new ItemSwipeWithActionWidthViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final MainRecyclerAdapter.ItemBaseViewHolder holder, int position) {
        GetItemDetailsRes.Items item = mDatas.get(position);
        holder.listItemMainBinding.setProduct(item);

        if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
            ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
            viewHolder.mActionViewRefresh.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(mContext, "Refresh Click" + holder.getAdapterPosition()
                                    , Toast.LENGTH_SHORT).show();
                            mItemTouchHelperExtension.closeOpened();
                        }
                    }

            );
            viewHolder.mActionViewDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (item.isItemDelete()) {
                                if (addItemMvpView != null) {
                                    item.setItemDelete(false);
                                    addItemMvpView.onItemAdded();
                                }
                            } else {
                                item.setItemDelete(true);
                                if (addItemMvpView != null) {
                                    item.setItemDelete(true);
                                    addItemMvpView.onItemDeleted();
                                }
                            }
                            mItemTouchHelperExtension.closeOpened();
                        }
                    }

            );
        }
    }

    private void doDelete(int adapterPosition) {
        mDatas.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void move(int from, int to) {
        GetItemDetailsRes.Items prev = mDatas.remove(from);
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