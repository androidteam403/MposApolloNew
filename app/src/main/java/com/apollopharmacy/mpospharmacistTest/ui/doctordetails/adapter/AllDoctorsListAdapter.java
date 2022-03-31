package com.apollopharmacy.mpospharmacistTest.ui.doctordetails.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewDoctorSearchItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog.AllDoctorsDialogMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;

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
                if (!charString.isEmpty()) {
                    ArrayList<DoctorSearchResModel.DropdownValueBean> filteredList = new ArrayList<>();
                    for (DoctorSearchResModel.DropdownValueBean row : doctorArrayList) {

                        if (charString.toUpperCase().contains("%")) {
                            String[] splitKey = charString.toUpperCase().split("%");
                            if (splitKey.length > 1) {
                                for (int i = 0; i < splitKey.length; i++) {
                                    if (i != 0) {
                                        if (!filteredList.contains(row) && (row.getCode().contains(splitKey[i]) || row.getDisplayText().contains(splitKey[i]))) {
                                            filteredList.add(row);
                                        }
                                    }
                                }
                            } else {
                                if (!filteredList.contains(row) && (row.getCode().contains(charString.toUpperCase().replace("%", "")) || row.getDisplayText().contains(charString.toUpperCase().replace("%", "")))) {
                                    filteredList.add(row);
                                }
                            }
                        } else {
                            if (!filteredList.contains(row) && (row.getCode().contains(charString.toUpperCase()) || row.getDisplayText().contains(charString.toUpperCase()))) {
                                filteredList.add(row);
                            }
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
            }
        };
    }

    public void clearDate() {
        doctorFilteredArrayList.clear();
        doctorArrayList.clear();
        notifyDataSetChanged();
    }

    public void add(ArrayList<DoctorSearchResModel.DropdownValueBean> productListFiltered) {
        this.doctorFilteredArrayList = productListFiltered;
        this.doctorArrayList = productListFiltered;
    }
}
