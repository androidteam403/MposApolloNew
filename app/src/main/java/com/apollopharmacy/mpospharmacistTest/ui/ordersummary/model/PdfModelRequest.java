package com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



    public class PdfModelRequest implements Serializable
    {

        @SerializedName("StoreCode")
        @Expose
        private String storeCode;
        @SerializedName("TerminalID")
        @Expose
        private String terminalID;
        @SerializedName("DataAreaID")
        @Expose
        private String dataAreaID;
        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;
        @SerializedName("TransactionId")
        @Expose
        private String transactionId;
        @SerializedName("BillingType")
        @Expose
        private Integer billingType;
        @SerializedName("DigitalReceiptRequired")
        @Expose
        private Boolean digitalReceiptRequired;
        private final static long serialVersionUID = 8671079243531974481L;

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getTerminalID() {
            return terminalID;
        }

        public void setTerminalID(String terminalID) {
            this.terminalID = terminalID;
        }

        public String getDataAreaID() {
            return dataAreaID;
        }

        public void setDataAreaID(String dataAreaID) {
            this.dataAreaID = dataAreaID;
        }

        public Integer getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(Integer requestStatus) {
            this.requestStatus = requestStatus;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public Integer getBillingType() {
            return billingType;
        }

        public void setBillingType(Integer billingType) {
            this.billingType = billingType;
        }

        public Boolean getDigitalReceiptRequired() {
            return digitalReceiptRequired;
        }

        public void setDigitalReceiptRequired(Boolean digitalReceiptRequired) {
            this.digitalReceiptRequired = digitalReceiptRequired;
        }

    }

