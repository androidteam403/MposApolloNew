package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;




    public class GetPrDetailsApiResponse implements Serializable
    {

        @SerializedName("DataAreaID")
        @Expose
        private Object dataAreaID;
        @SerializedName("PONumber")
        @Expose
        private Object pONumber;
        @SerializedName("StoreID")
        @Expose
        private Object storeID;
        @SerializedName("data")
        @Expose
        private Object data;
        @SerializedName("errorMessage")
        @Expose
        private Object errorMessage;
        @SerializedName("message")
        @Expose
        private Object message;
        @SerializedName("prsTicketStatus")
        @Expose
        private Object prsTicketStatus;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("ticketNo")
        @Expose
        private Object ticketNo;


        public Object getDataAreaID() {
            return dataAreaID;
        }

        public void setDataAreaID(Object dataAreaID) {
            this.dataAreaID = dataAreaID;
        }

        public Object getPONumber() {
            return pONumber;
        }

        public void setPONumber(Object pONumber) {
            this.pONumber = pONumber;
        }

        public Object getStoreID() {
            return storeID;
        }

        public void setStoreID(Object storeID) {
            this.storeID = storeID;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(Object errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public Object getPrsTicketStatus() {
            return prsTicketStatus;
        }

        public void setPrsTicketStatus(Object prsTicketStatus) {
            this.prsTicketStatus = prsTicketStatus;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Object getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(Object ticketNo) {
            this.ticketNo = ticketNo;
        }

    }

