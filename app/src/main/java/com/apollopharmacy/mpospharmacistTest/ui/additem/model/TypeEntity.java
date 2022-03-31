package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class TypeEntity {
        @Expose
        @SerializedName("TenderLimit")
        private double TenderLimit;
        @Expose
        @SerializedName("TenderCombinationType")
        private int TenderCombinationType;
        @Expose
        @SerializedName("RoundingMethod")
        private int RoundingMethod;
        @Expose
        @SerializedName("PosOpereration")
        private int PosOpereration;
        @Expose
        @SerializedName("TenderURL")
        private String TenderURL;
        @Expose
        @SerializedName("Tender")
        private String Tender;
        @Expose
        @SerializedName("TenderTypeId")
        private String TenderTypeId;

        public double getTenderLimit() {
            return TenderLimit;
        }

        public int getTenderCombinationType() {
            return TenderCombinationType;
        }

        public int getRoundingMethod() {
            return RoundingMethod;
        }

        public int getPosOpereration() {
            return PosOpereration;
        }

        public String getTenderURL() {
            return TenderURL;
        }

        public String getTender() {
            return Tender;
        }

        public String getTenderTypeId() {
            return TenderTypeId;
        }

        public void setTenderLimit(double tenderLimit) {
            TenderLimit = tenderLimit;
        }

        public void setTenderCombinationType(int tenderCombinationType) {
            TenderCombinationType = tenderCombinationType;
        }

        public void setRoundingMethod(int roundingMethod) {
            RoundingMethod = roundingMethod;
        }

        public void setPosOpereration(int posOpereration) {
            PosOpereration = posOpereration;
        }

        public void setTenderURL(String tenderURL) {
            TenderURL = tenderURL;
        }

        public void setTender(String tender) {
            Tender = tender;
        }

        public void setTenderTypeId(String tenderTypeId) {
            TenderTypeId = tenderTypeId;
        }
    }