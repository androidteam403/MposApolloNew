package com.apollopharmacy.mpospharmacist.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.apollopharmacy.mpospharmacist.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public class CommonUtils {
    public static final String DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-YYYY";
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

    public static boolean mobileValidate(String mobile) {
        if (mobile.length() < 10) {
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(String target) {
        {
            Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
            if (target.length() < 0 && target.length() > 10) {
                return true;
            }

            Matcher m = p.matcher(target);
            return (m.find() && m.group().equals(target));
        }
    }


    public static String getCurrentDate(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date());
    }
}
