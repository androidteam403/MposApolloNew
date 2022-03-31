package com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class FiltersReq extends BaseObservable {

    private String mobile;
    private String orderId;
    private String fromDate;
    private String toDate;
    private int from_Year;
    private int from_Month;
    private int from_date;
    private int to_year;
    private int to_month;
    private int to_date;

    public int getFrom_Year() {
        return from_Year;
    }

    public void setFrom_Year(int from_Year) {
        this.from_Year = from_Year;
    }

    public int getFrom_Month() {
        return from_Month;
    }

    public void setFrom_Month(int from_Month) {
        this.from_Month = from_Month;
    }

    public int getFrom_date() {
        return from_date;
    }

    public void setFrom_date(int from_date) {
        this.from_date = from_date;
    }

    public int getTo_year() {
        return to_year;
    }

    public void setTo_year(int to_year) {
        this.to_year = to_year;
    }

    public int getTo_month() {
        return to_month;
    }

    public void setTo_month(int to_month) {
        this.to_month = to_month;
    }

    public int getTo_date() {
        return to_date;
    }

    public void setTo_date(int to_date) {
        this.to_date = to_date;
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
        notifyPropertyChanged(BR.orderId);
    }

    @Bindable
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
        notifyPropertyChanged(BR.fromDate);
    }

    @Bindable
    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
        notifyPropertyChanged(BR.toDate);
    }
}
