package com.apollopharmacy.mpospharmacistTest.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.apollopharmacy.mpospharmacistTest.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static String getCurrentDate(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date());
    }


    public static String convertDecimalFormat(double value){
        return new DecimalFormat("##.##").format(value);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static char[] generatorOTP(int length)
    {
        System.out.print("Your OTP is : ");
        //Creating object of Random class
        Random obj = new Random();
        char[] otp = new char[length];
        for (int i=0; i<length; i++)
        {
            otp[i]= (char)(obj.nextInt(10)+48);
        }
        return otp;
    }

    public static String convertDateFormat(String date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        SimpleDateFormat dateFormatFull = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());

        try {
            Date d = dateFormat.parse(date);
            if (d != null) {
                return dateFormatFull.format(d);
            }
        }
        catch(Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep"+e);
        }
        return null;
    }

    public static String convertTimeFormat(String time){
        if(!TextUtils.isEmpty(time)) {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date = null;
            try {
                date = sdf.parse(time);
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                if (date != null) {
                    return format.format(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static int checkCancelledDateTime(String canceledDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        Date endDateTime = null;
        Date currentDate = null;
        try {
            endDateTime = dateFormat.parse(canceledDate);
            currentDate = dateFormat.parse(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int startEndCatalogDate = 0;
        if (endDateTime != null) {
            if (endDateTime.before(currentDate)) {
                startEndCatalogDate = 1;
            } else if (endDateTime.after(currentDate)) {
                startEndCatalogDate = -1;
            }
        }
        return startEndCatalogDate;
    }
}
