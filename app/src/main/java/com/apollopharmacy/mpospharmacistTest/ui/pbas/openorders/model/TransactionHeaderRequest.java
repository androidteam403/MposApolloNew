package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionHeaderRequest {

        @SerializedName("DataAreaID")
        @Expose
        private String dataAreaID;
        @SerializedName("ExpiryDays")
        @Expose
        private Integer expiryDays;
        @SerializedName("RefID")
        @Expose
        private String refID;
        @SerializedName("StoreID")
        @Expose
        private String storeID;
        @SerializedName("TerminalID")
        @Expose
        private String terminalID;
        @SerializedName("TransactionID")
        @Expose
        private String transactionID;

        public String getDataAreaID() {
            return dataAreaID;
        }

        public void setDataAreaID(String dataAreaID) {
            this.dataAreaID = dataAreaID;
        }

        public TransactionHeaderRequest withDataAreaID(String dataAreaID) {
            this.dataAreaID = dataAreaID;
            return this;
        }

        public Integer getExpiryDays() {
            return expiryDays;
        }

        public void setExpiryDays(Integer expiryDays) {
            this.expiryDays = expiryDays;
        }

        public TransactionHeaderRequest withExpiryDays(Integer expiryDays) {
            this.expiryDays = expiryDays;
            return this;
        }

        public String getRefID() {
            return refID;
        }

        public void setRefID(String refID) {
            this.refID = refID;
        }

        public TransactionHeaderRequest withRefID(String refID) {
            this.refID = refID;
            return this;
        }

        public String getStoreID() {
            return storeID;
        }

        public void setStoreID(String storeID) {
            this.storeID = storeID;
        }

        public TransactionHeaderRequest withStoreID(String storeID) {
            this.storeID = storeID;
            return this;
        }

        public String getTerminalID() {
            return terminalID;
        }

        public void setTerminalID(String terminalID) {
            this.terminalID = terminalID;
        }

        public TransactionHeaderRequest withTerminalID(String terminalID) {
            this.terminalID = terminalID;
            return this;
        }

        public String getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
        }

        public TransactionHeaderRequest withTransactionID(String transactionID) {
            this.transactionID = transactionID;
            return this;
        }

    }

