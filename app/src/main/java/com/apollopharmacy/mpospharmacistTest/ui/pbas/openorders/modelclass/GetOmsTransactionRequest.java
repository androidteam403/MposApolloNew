package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOmsTransactionRequest implements Serializable
    {

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
        private final static long serialVersionUID = 3395350390322077999L;

        public String getDataAreaID() {
            return dataAreaID;
        }

        public void setDataAreaID(String dataAreaID) {
            this.dataAreaID = dataAreaID;
        }

        public Integer getExpiryDays() {
            return expiryDays;
        }

        public void setExpiryDays(Integer expiryDays) {
            this.expiryDays = expiryDays;
        }

        public String getRefID() {
            return refID;
        }

        public void setRefID(String refID) {
            this.refID = refID;
        }

        public String getStoreID() {
            return storeID;
        }

        public void setStoreID(String storeID) {
            this.storeID = storeID;
        }

        public String getTerminalID() {
            return terminalID;
        }

        public void setTerminalID(String terminalID) {
            this.terminalID = terminalID;
        }

        public String getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
        }

    }

