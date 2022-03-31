package com.apollopharmacy.mpospharmacistTest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.adapter.RecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PhotoPopupWindow extends PopupWindow {
    View view;
    Context mContext;
    PhotoView photoView;
    ProgressBar loading;
    ViewGroup parent;
    private static PhotoPopupWindow instance = null;

    String imageurl = "";
    private RecyclerViewAdapter prescriptionAdapter;
    private RecyclerView prescriptionsRecyclerView;

    public PhotoPopupWindow(Context ctx, int layout, View v, String imageUrl, Bitmap bitmap) {
        super(((LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_image_fullview, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        setElevation(5.0f);
        this.mContext = ctx;
        this.view = getContentView();
        this.imageurl = imageUrl;
        ImageButton closeButton = this.view.findViewById(R.id.ib_close);
        setOutsideTouchable(true);

        setFocusable(true);
        closeButton.setOnClickListener(view -> dismiss());
        //---------Begin customising this popup--------------------

        photoView = view.findViewById(R.id.image);
        loading = view.findViewById(R.id.loading);
        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();

      /*  prescriptionsRecyclerView=view.findViewById(R.id.prescriptions_RecyclerView);
          prescriptionAdapter = new RecyclerViewAdapter(PhotoPopupWindow.this, images, this);
        prescriptionsRecyclerView.setAdapter(prescriptionAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        prescriptionsRecyclerView.setLayoutManager(manager);*/

        if (bitmap != null) {
            loading.setVisibility(View.GONE);
            photoView.setImageBitmap(bitmap);
        } else {
            // String imgPath = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.prescription_image).toString();
            String imgPath = Uri.parse(imageUrl).toString();
            loading.setIndeterminate(true);
            loading.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(imgPath)
                    .apply(new RequestOptions().placeholder(R.drawable.thumbnail_image).error(R.drawable.thumbnail_image))
                    .into(photoView);
            showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }
}