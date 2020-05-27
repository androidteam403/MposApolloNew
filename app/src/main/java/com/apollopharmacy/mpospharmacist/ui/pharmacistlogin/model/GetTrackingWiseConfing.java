package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTrackingWiseConfing {


    @Expose
    @SerializedName("_TrackingConfigration")
    private List<_TrackingConfigrationEntity> _TrackingConfigration;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    public List<_TrackingConfigrationEntity> get_TrackingConfigration() {
        return _TrackingConfigration;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public static class _TrackingConfigrationEntity {
        @Expose
        @SerializedName("SubCategory")
        private String SubCategory;
        @Expose
        @SerializedName("OTPTenderType")
        private String OTPTenderType;
        @Expose
        @SerializedName("MandatoryCreditPercentage")
        private int MandatoryCreditPercentage;
        @Expose
        @SerializedName("ISEMPBilling")
        private int ISEMPBilling;
        @Expose
        @SerializedName("ISCouponNature")
        private int ISCouponNature;
        @Expose
        @SerializedName("ISCouponBilling")
        private int ISCouponBilling;
        @Expose
        @SerializedName("CorpCode")
        private String CorpCode;
        @Expose
        @SerializedName("Category")
        private String Category;

        public String getSubCategory() {
            return SubCategory;
        }

        public String getOTPTenderType() {
            return OTPTenderType;
        }

        public int getMandatoryCreditPercentage() {
            return MandatoryCreditPercentage;
        }

        public int getISEMPBilling() {
            return ISEMPBilling;
        }

        public int getISCouponNature() {
            return ISCouponNature;
        }

        public int getISCouponBilling() {
            return ISCouponBilling;
        }

        public String getCorpCode() {
            return CorpCode;
        }

        public String getCategory() {
            return Category;
        }
    }
}
