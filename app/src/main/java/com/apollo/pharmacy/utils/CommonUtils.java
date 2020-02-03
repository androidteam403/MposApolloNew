package com.apollo.pharmacy.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.apollo.pharmacy.R;


/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public class CommonUtils {

//    public String pattern = "[A-Za-z]";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static boolean nameVallidate(String name) {
        String pattern = "^[A-Za-z]*$";
        if (name.matches(pattern)) {
            return true;
        }
        return false;
    }
    public static boolean mobileValidate(String mobile){
        if (mobile.length() < 10){
            return false;
        }
        return true;
    }
}