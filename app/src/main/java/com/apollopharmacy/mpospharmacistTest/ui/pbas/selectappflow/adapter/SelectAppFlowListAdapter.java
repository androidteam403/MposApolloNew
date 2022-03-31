package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectAppFlowListPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.model.SelectAppFlowModel;

import java.util.List;

public class SelectAppFlowListAdapter extends RecyclerView.Adapter<SelectAppFlowListAdapter.VoiewHolder> {
    private Context mContext;
    private List<SelectAppFlowModel> selectAppFlowModelList;
    private SelectAppFlowMvpView selectAppFlowMvpView;

    public SelectAppFlowListAdapter(Context mContext, List<SelectAppFlowModel> selectAppFlowModelList, SelectAppFlowMvpView selectAppFlowMvpView) {
        this.mContext = mContext;
        this.selectAppFlowModelList = selectAppFlowModelList;
        this.selectAppFlowMvpView = selectAppFlowMvpView;
    }

    @NonNull
    @Override
    public VoiewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectAppFlowListPBinding selectAppFlowListBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_select_app_flow_list_p, parent, false);
        return new SelectAppFlowListAdapter.VoiewHolder(selectAppFlowListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VoiewHolder holder, int position) {
        SelectAppFlowModel selectAppFlowModel = selectAppFlowModelList.get(position);
        holder.selectAppFlowListBinding.selectAppFlowName.setText(selectAppFlowModel.getAppFlowName());
        if (selectAppFlowModel.isSelected()) {
            holder.selectAppFlowListBinding.icSelectUnselect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.selectAppFlowListBinding.parentLayout.setBackground(mContext.getResources().getDrawable(R.drawable.bg_for_login));
            holder.selectAppFlowListBinding.selectAppFlowName.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            holder.selectAppFlowListBinding.icSelectUnselect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.black_circle_login));
            holder.selectAppFlowListBinding.parentLayout.setBackground(mContext.getResources().getDrawable(R.drawable.bg_for_login_unselected));
            holder.selectAppFlowListBinding.selectAppFlowName.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        holder.itemView.setOnClickListener(view -> {
            if (selectAppFlowMvpView != null)
                selectAppFlowMvpView.onClickSelectAppFlowItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return selectAppFlowModelList.size();
    }

    public class VoiewHolder extends RecyclerView.ViewHolder {
        AdapterSelectAppFlowListPBinding selectAppFlowListBinding;

        public VoiewHolder(@NonNull AdapterSelectAppFlowListPBinding selectAppFlowListBinding) {
            super(selectAppFlowListBinding.getRoot());
            this.selectAppFlowListBinding = selectAppFlowListBinding;
        }
    }
}
