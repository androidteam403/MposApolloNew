package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.customPackage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;

import java.util.List;

public class SubstituteDropDownAdapter extends BaseAdapter {

    Context activity;
   List<EPrescriptionSubstituteModelResponse.Substitute> substituteList;
    LayoutInflater inflter;


    public SubstituteDropDownAdapter(Context activity, List<EPrescriptionSubstituteModelResponse.Substitute> substituteList) {
        this.activity = activity;
        this.substituteList = substituteList;
        inflter = (LayoutInflater.from(activity));
    }


    @Override
    public int getCount() {
        return substituteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflter.inflate(R.layout.substitutedropdown, null);
        TextView names = view.findViewById(R.id.reason_id);
        names.setText(substituteList.get(position).getSubstituteArtCode());

        return view;
    }
}
