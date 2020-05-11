package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllowedPaymentModeRes {

    @Expose
    @SerializedName("_PaymentMethodList")
    private List<_PaymentMethodListEntity> _PaymentMethodList;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    public List<_PaymentMethodListEntity> get_PaymentMethodList() {
        return _PaymentMethodList;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public static class _PaymentMethodListEntity {
        @Expose
        @SerializedName("PaymentMode")
        private String PaymentMode;
        @Expose
        @SerializedName("CombinationCode")
        private String CombinationCode;

        public String getPaymentMode() {
            return PaymentMode;
        }

        public String getCombinationCode() {
            return CombinationCode;
        }
    }
}
