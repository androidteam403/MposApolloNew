package com.apollopharmacy.mpospharmacist.ui.doctordetails.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ViewDoctorSearchItemBinding;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;

import java.util.ArrayList;

public class DoctorDetailAdapter extends RecyclerView.Adapter<DoctorDetailAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayList;

    public DoctorDetailAdapter(Activity activity, ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayList) {
        this.activity = activity;
        this.doctorSearchArrayList = doctorSearchArrayList;
    }

    @NonNull
    @Override
    public DoctorDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDoctorSearchItemBinding doctorSearchItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_doctor_search_item, parent, false);
        return new DoctorDetailAdapter.ViewHolder(doctorSearchItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorDetailAdapter.ViewHolder holder, int position) {
        DoctorSearchResModel.DropdownValueBean item = doctorSearchArrayList.get(position);
        holder.viewDoctorSearchItemBinding.setModel(item);

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
        return doctorSearchArrayList.size();
    }
}
