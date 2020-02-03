package com.apollo.pharmacy.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

import com.apollo.pharmacy.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public class CommonUtils {
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

        public  static boolean isValidPhoneNumber (String target){
            {
                Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
                if (target.length()<0 && target.length()>10){
                    return true;
                }

                Matcher m = p.matcher(target);
                return (m.find() && m.group().equals(target));
            }

//        target="^\\+?\\(?[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?";
//            if ( target.length() < 9 || target.length() > 11) {
//                return false;
//            } else {
////                return android.util.Patterns.PHONE.matcher(target).matches();
//            }
          //  return android.util.Patterns.PHONE.matcher(target).matches();

        }
}
