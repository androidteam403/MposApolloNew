package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MPOSPickPackOrderReservationRequest {

    @SerializedName("RequestType")
    @Expose
    private Integer requestType;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("OrderList")
    @Expose
    private List<Order> orderList = null;

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public static class Order {

        @SerializedName("TransactionID")
        @Expose
        private String transactionID;
        @SerializedName("StoreID")
        @Expose
        private String storeID;
        @SerializedName("TerminalID")
        @Expose
        private String terminalID;
        @SerializedName("DataAreaID")
        @Expose
        private String dataAreaID;
        @SerializedName("RefID")
        @Expose
        private String RefID;

        public String getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
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

        public String getDataAreaID() {
            return dataAreaID;
        }

        public void setDataAreaID(String dataAreaID) {
            this.dataAreaID = dataAreaID;
        }

        public String getRefID() {
            return RefID;
        }

        public void setRefID(String refID) {
            RefID = refID;
        }
    }
}

