package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.utils;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public interface StickyRecyclerHeadersAdapter<VH extends RecyclerView.ViewHolder> {
    public long getHeaderId(int position);

    public VH onCreateHeaderViewHolder(ViewGroup parent);

    public void onBindHeaderViewHolder(VH holder, int position);

    public int getItemCount();
}
