package com.apollopharmacy.mpospharmacist.ui.doctordetails.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ViewDoctorSearchItemBinding;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialogMvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;

import java.util.ArrayList;

public class AllDoctorsListAdapter extends RecyclerView.Adapter<AllDoctorsListAdapter.ViewHolder> implements Filterable {
    private Activity activity;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> doctorArrayList;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> doctorFilteredArrayList;
    private AllDoctorsDialogMvpView doctorsMvpView;

    public AllDoctorsListAdapter(Activity activity, ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayList) {
        this.activity = activity;
        this.doctorArrayList = doctorSearchArrayList;
        this.doctorFilteredArrayList = doctorSearchArrayList;
    }

    @NonNull
    @Override
    public AllDoctorsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDoctorSearchItemBinding doctorSearchItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_doctor_search_item, parent, false);
        return new AllDoctorsListAdapter.ViewHolder(doctorSearchItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDoctorsListAdapter.ViewHolder holder, int position) {
        DoctorSearchResModel.DropdownValueBean item = doctorFilteredArrayList.get(position);
        holder.viewDoctorSearchItemBinding.setModel(item);
        holder.itemView.setOnClickListener(v -> {
            if (doctorsMvpView != null) {
                doctorsMvpView.onClickListener(item);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewDoctorSearchItemBinding viewDoctorSearchItemBinding;

        public ViewHolder(@NonNull ViewDoctorSearchItemBinding viewDoctorSearchItemBinding) {
            super(viewDoctorSearchItemBinding.getRoot());
            this.viewDoctorSearchItemBinding = viewDoctorSearchItemBinding;
        }
    }

    @Override
    public int getItemCount() {
        return doctorFilteredArrayList.size();
    }

    public void onClickListener(AllDoctorsDialogMvpView mvpView) {
        this.doctorsMvpView = mvpView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    doctorFilteredArrayList = doctorArrayList;
                } else {
                    ArrayList<DoctorSearchResModel.DropdownValueBean> filteredList = new ArrayList<>();
                    for (DoctorSearchResModel.DropdownValueBean row : doctorArrayList) {
                        if (row.getDisplayText().contains(charString.toUpperCase()) || row.getCode().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }
                    doctorFilteredArrayList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = doctorFilteredArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                doctorFilteredArrayList = (ArrayList<DoctorSearchResModel.DropdownValueBean>) filterResults.values;
                notifyDataSetChanged();
                doctorsMvpView.updateNoDoctorView(doctorFilteredArrayList.size());
            }
        };
    }
}
