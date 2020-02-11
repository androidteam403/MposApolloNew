package com.apollopharmacy.mpospharmacist.ui.customerdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetCustomerResponse {



    @Expose
    @SerializedName("_Customer")
    private List<CustomerEntity> _Customer;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    public List<CustomerEntity> get_Customer() {
        return _Customer;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public static class CustomerEntity implements Serializable {
        @Expose
        @SerializedName("Tier")
        private String Tier;
        @Expose
        @SerializedName("TelephoneNo")
        private String TelephoneNo;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("EmpNo")
        private String EmpNo;
        @Expose
        @SerializedName("CustId")
        private String CustId;
        @Expose
        @SerializedName("CustActiveStatus")
        private String CustActiveStatus;
        @Expose
        @SerializedName("CorpId")
        private String CorpId;
        @Expose
        @SerializedName("CardNo")
        private String CardNo;
        @Expose
        @SerializedName("CardName")
        private String CardName;
        @Expose
        @SerializedName("AvailablePoints")
        private String AvailablePoints;

        public String getTier() {
            return Tier;
        }

        public String getTelephoneNo() {
            return TelephoneNo;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public String getEmpNo() {
            return EmpNo;
        }

        public String getCustId() {
            return CustId;
        }

        public String getCustActiveStatus() {
            return CustActiveStatus;
        }

        public String getCorpId() {
            return CorpId;
        }

        public String getCardNo() {
            return CardNo;
        }

        public String getCardName() {
            return CardName;
        }

        public String getAvailablePoints() {
            return AvailablePoints;
        }

        private String searchId;

        public String getSearchId() {
            return searchId;
        }

        public void setSearchId(String searchId) {
            this.searchId = searchId;
        }
    }
}
