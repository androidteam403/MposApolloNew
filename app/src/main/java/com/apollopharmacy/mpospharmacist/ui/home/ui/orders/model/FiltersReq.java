package com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class FiltersReq extends BaseObservable {

    private String mobile;
    private String orderId;
    private String fromDate;
    private String toDate;

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
