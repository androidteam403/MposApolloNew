package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MPOSPickPackOrderReservationResponse {

    @SerializedName("OrderList")
    @Expose
    private List<Order> orderList = null;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("RequestType")
    @Expose
    private Integer requestType;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("UserName")
    @Expose
    private String userName;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public class Order {

        @SerializedName("DataAreaID")
        @Expose
        private String dataAreaID;
        @SerializedName("RefID")
        @Expose
        private Object refID;
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

        public Object getRefID() {
            return refID;
        }

        public void setRefID(Object refID) {
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
}
