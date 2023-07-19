package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetPrDetailsApiRequest implements Serializable
    {

        @SerializedName("PONumber")
        @Expose
        private String pONumber;
        @SerializedName("DataAreaID")
        @Expose
        private String dataAreaID;
        @SerializedName("StoreID")
        @Expose
        private String storeID;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("message")
        @Expose
        private Object message;
        @SerializedName("errorMessage")
        @Expose
        private Object errorMessage;
        @SerializedName("data")
        @Expose
        private Object data;


        public String getPONumber() {
            return pONumber;
        }

        public void setPONumber(String pONumber) {
            this.pONumber = pONumber;
        }

        public String getDataAreaID() {
            return dataAreaID;
        }

        public void setDataAreaID(String dataAreaID) {
            this.dataAreaID = dataAreaID;
        }

        public String getStoreID() {
            return storeID;
        }

        public void setStoreID(String storeID) {
            this.storeID = storeID;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public Object getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(Object errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

    }

