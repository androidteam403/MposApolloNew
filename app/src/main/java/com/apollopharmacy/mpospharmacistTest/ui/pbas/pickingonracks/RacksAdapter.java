package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickingonracks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterRacksLayoutBinding;

public class RacksAdapter extends RecyclerView.Adapter<RacksAdapter.ViewHolder> {
    Context context;

    public RacksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRacksLayoutBinding racksLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_racks_layout, parent, false);
        return new ViewHolder(racksLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.racksLayoutBinding.tick.setVisibility(View.VISIBLE);
            holder.racksLayoutBinding.parentLayout.setBackgroundColor(Color.parseColor("#8cc63f"));
        } else  {
            holder.racksLayoutBinding.tick.setVisibility(View.GONE);
            holder.racksLayoutBinding.parentLayout.setBackgroundColor(Color.parseColor("#cccccc"));
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterRacksLayoutBinding racksLayoutBinding;

        public ViewHolder(@NonNull AdapterRacksLayoutBinding racksLayoutBinding) {
            super(racksLayoutBinding.getRoot());
            this.racksLayoutBinding = racksLayoutBinding;
        }
    }
}
