package com.apollopharmacy.mpospharmacistTest.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.apollopharmacy.mpospharmacistTest.R;
import com.google.android.material.snackbar.Snackbar;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class UiUtils
{

    public static void showSnackbar(Context context, ConstraintLayout layout, String name) {
        Snackbar mSnackbar = Snackbar.make(layout, name, Snackbar.LENGTH_SHORT);
        View snackbarView = mSnackbar.getView();
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.material_amber_accent_700));
        (mSnackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(25);
        snackbarView.setMinimumHeight(60);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        mSnackbar.show();
    }
    public static double displaymetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        float widthDpi = metrics.xdpi;
        float heightDpi = metrics.ydpi;

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        float widthInches = widthPixels / widthDpi;
        float heightInches = heightPixels / heightDpi;
        double diagonalInches = Math.sqrt(
                (widthInches * widthInches)
                        + (heightInches * heightInches));
        return diagonalInches;
    }
}
