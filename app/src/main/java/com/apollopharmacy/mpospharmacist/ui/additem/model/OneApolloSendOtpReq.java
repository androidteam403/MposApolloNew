package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneApolloSendOtpReq {


    @Expose
    @SerializedName("RequestData")
    private RequestDataEntity RequestData;

    public void setRequestData(RequestDataEntity RequestData) {
        this.RequestData = RequestData;
    }

    public static class RequestDataEntity {
        @Expose
        @SerializedName("Url")
        private String Url;
        @Expose
        @SerializedName("CustomerID")
        private String CustomerID;
        @Expose
        @SerializedName("Type")
        private String Type;
        @Expose
        @SerializedName("Coupon")
        private String Coupon;
        @Expose
        @SerializedName("Action")
        private String Action;
        @Expose
        @SerializedName("OTP")
        private String OTP;
        @Expose
        @SerializedName("RRNO")
        private String RRNO;
        @Expose
        @SerializedName("Points")
        private String Points;
        @Expose
        @SerializedName("ReqBy")
        private String ReqBy;
        @Expose
        @SerializedName("MobileNum")
        private String MobileNum;
        @Expose
        @SerializedName("DocNum")
        private String DocNum;
        @Expose
        @SerializedName("StoreId")
        private String StoreId;

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public void setCustomerID(String CustomerID) {
            this.CustomerID = CustomerID;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public void setCoupon(String Coupon) {
            this.Coupon = Coupon;
        }

        public void setAction(String Action) {
            this.Action = Action;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }

        public void setRRNO(String RRNO) {
            this.RRNO = RRNO;
        }

        public void setPoints(String Points) {
            this.Points = Points;
        }

        public void setReqBy(String ReqBy) {
            this.ReqBy = ReqBy;
        }

        public void setMobileNum(String MobileNum) {
            this.MobileNum = MobileNum;
        }

        public void setDocNum(String DocNum) {
            this.DocNum = DocNum;
        }

        public void setStoreId(String StoreId) {
            this.StoreId = StoreId;
        }
    }
}
