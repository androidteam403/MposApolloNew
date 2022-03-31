package com.apollopharmacy.mpospharmacistTest.ui.home.adptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.ui.home.OnItemClickListener;
import com.apollopharmacy.mpospharmacistTest.utils.BTSearchItem;

import java.util.List;

public class BtSearchAdapter extends RecyclerView.Adapter<BtSearchAdapter.MyViewHolder>
{
    private List<BTSearchItem> devicesList;
    private static final String TAG = "BtSearchAdapter";
    private OnItemClickListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt, addressTxt;
        Button pairButton;

        private MyViewHolder(View view) {
            super(view);
            nameTxt = view.findViewById(R.id.nameTxt);
            addressTxt = view.findViewById(R.id.addressTxt);
            pairButton = view.findViewById(R.id.pairButton);
        }
    }

    public BtSearchAdapter(List<BTSearchItem> moviesList, OnItemClickListener listener) {
        this.devicesList = moviesList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BTSearchItem movie = devicesList.get(position);
        holder.nameTxt.setText(movie.getName());
        holder.addressTxt.setText(movie.getAddress());
        if (movie.getPaired() == null) {
            return;
        }
        holder.pairButton.setTag(position);

        holder.pairButton.setOnClickListener(view -> {
            Log.e(TAG, "position:: " + view.getTag());
            if (listener != null) {
                int clickedPosition = (int) view.getTag();
                listener.onConnectRequest(clickedPosition, devicesList.get(clickedPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }
}
