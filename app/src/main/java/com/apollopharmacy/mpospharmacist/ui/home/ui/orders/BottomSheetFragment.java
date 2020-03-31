package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.OrdersBottomSheetBinding;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.FiltersReq;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    public BottomSheetFragment(Context mContext,FiltersReq filtersReq,OrdersMvpView ordersMvpView) {
        this.context = mContext;
        this.filtersReq = filtersReq;
        this.ordersMvpView = ordersMvpView;
    }

    private OrdersBottomSheetBinding ordersBottomSheetBinding;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int fromYear,fromMonth,fromDay;
    private Context context;
    private FiltersReq filtersReq;
    private OrdersMvpView ordersMvpView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ordersBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.orders_bottom_sheet, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return ordersBottomSheetBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ordersBottomSheetBinding.setFilters(filtersReq);
        ordersBottomSheetBinding.setCallback(ordersMvpView);
        ordersBottomSheetBinding.fromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFromDate();
            }
        });

        ordersBottomSheetBinding.toDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToDate();
            }
        });
    }


    public boolean validateFilters(){
        String mobile = ordersBottomSheetBinding.mobileNumber.getText().toString();
        String orderId = ordersBottomSheetBinding.orderId.getText().toString();
        if(!TextUtils.isEmpty(mobile)&& mobile.length() < 10){
            ordersBottomSheetBinding.mobileNumber.setError("Enter Valid Mobile Number");
            return false;
        }else{
            filtersReq.setMobile(mobile);
        }
        if(!TextUtils.isEmpty(orderId)){
            filtersReq.setOrderId(orderId);
        }else{
            filtersReq.setOrderId("");
        }
        return true;
    }

    private void showFromDate(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        c.set(filtersReq.getFrom_Year(),filtersReq.getFrom_Month(),filtersReq.getFrom_date());
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        filtersReq.setFrom_Year(year);
                        filtersReq.setFrom_Month(monthOfYear);
                        filtersReq.setFrom_date(dayOfMonth);
                        ordersBottomSheetBinding.getFilters().setFromDate(CommonUtils.convertDateFormat(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                        filtersReq.setFromDate(CommonUtils.convertDateFormat(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                    }
                }, mYear, mMonth, mDay);
        Calendar ca = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMaxDate(ca.getTime().getTime());
        datePickerDialog.show();
    }

    private void showToDate(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        ordersBottomSheetBinding.getFilters().setToDate(CommonUtils.convertDateFormat(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                        filtersReq.setToDate(CommonUtils.convertDateFormat(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                    }
                }, mYear, mMonth, mDay);
        Calendar ca = Calendar.getInstance();
        ca.set(filtersReq.getFrom_Year(),filtersReq.getFrom_Month(),filtersReq.getFrom_date());
        datePickerDialog.getDatePicker().setMinDate(ca.getTime().getTime());
        datePickerDialog.getDatePicker().setMaxDate(c.getTime().getTime());
        datePickerDialog.show();
    }
}