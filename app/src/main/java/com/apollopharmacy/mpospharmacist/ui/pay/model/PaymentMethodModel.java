package com.apollopharmacy.mpospharmacist.ui.pay.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class PaymentMethodModel extends BaseObservable {

    private boolean cashMode = true;
    private boolean cardMode = false;
    private boolean hellingCardMode = false;

    @Bindable
    public boolean isCashMode() {
        return cashMode;
    }

    public void setCashMode(boolean cashMode) {
        this.cashMode = cashMode;
        notifyPropertyChanged(BR.cashMode);
    }
    @Bindable
    public boolean isCardMode() {
        return cardMode;
    }

    public void setCardMode(boolean cardMode) {
        this.cardMode = cardMode;
        notifyPropertyChanged(BR.cardMode);
    }
    @Bindable
    public boolean isHellingCardMode() {
        return hellingCardMode;
    }

    public void setHellingCardMode(boolean hellingCardMode) {
        this.hellingCardMode = hellingCardMode;
        notifyPropertyChanged(BR.hellingCardMode);
    }
}
