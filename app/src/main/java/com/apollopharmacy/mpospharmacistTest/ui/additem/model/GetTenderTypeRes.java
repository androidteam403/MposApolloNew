package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class GetTenderTypeRes {


    @Expose
    @SerializedName("GetTenderTypeResult")
    private GetTenderTypeResultEntity GetTenderTypeResult;

    public GetTenderTypeResultEntity getGetTenderTypeResult() {
        return GetTenderTypeResult;
    }

    public static class GetTenderTypeResultEntity {
        @Expose
        @SerializedName("_TenderType")
        private List<_TenderTypeEntity> _TenderType;
        @Expose
        @SerializedName("ReturnMessage")
        private String ReturnMessage;
        @Expose
        @SerializedName("RequestStatus")
        private int RequestStatus;

        public List<_TenderTypeEntity> get_TenderType() {
            return _TenderType;
        }

        public String getReturnMessage() {
            return ReturnMessage;
        }

        public int getRequestStatus() {
            return RequestStatus;
        }
    }

    public static class _TenderTypeEntity {
        @Expose
        @SerializedName("TenderURL")
        private String TenderURL;
        @Expose
        @SerializedName("TenderTypeId")
        private String TenderTypeId;
        @Expose
        @SerializedName("TenderLimit")
        private double TenderLimit;
        @Expose
        @SerializedName("TenderCombinationType")
        private int TenderCombinationType;
        @Expose
        @SerializedName("Tender")
        private String Tender;
        @Expose
        @SerializedName("RoundingMethod")
        private int RoundingMethod;
        @Expose
        @SerializedName("PosOpereration")
        private int PosOpereration;

        public String getTenderURL() {
            return TenderURL;
        }

        public String getTenderTypeId() {
            return TenderTypeId;
        }

        public double getTenderLimit() {
            return TenderLimit;
        }

        public int getTenderCombinationType() {
            return TenderCombinationType;
        }

        public String getTender() {
            return Tender;
        }

        public int getRoundingMethod() {
            return RoundingMethod;
        }

        public int getPosOpereration() {
            return PosOpereration;
        }
    }
}
