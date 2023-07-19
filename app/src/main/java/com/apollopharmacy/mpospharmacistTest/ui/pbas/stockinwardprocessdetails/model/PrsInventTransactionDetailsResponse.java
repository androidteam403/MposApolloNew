package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


    public class PrsInventTransactionDetailsResponse implements Serializable
    {

        @SerializedName("ChallanDate")
        @Expose
        private String challanDate;
        @SerializedName("DataAriaId")
        @Expose
        private String dataAriaId;
        @SerializedName("DelivaryDate")
        @Expose
        private String delivaryDate;
        @SerializedName("OperationType")
        @Expose
        private String operationType;
        @SerializedName("PONO")
        @Expose
        private String pono;
        @SerializedName("Remarks")
        @Expose
        private String remarks;
        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;
        @SerializedName("Store")
        @Expose
        private String store;
        @SerializedName("SuppInvNo")
        @Expose
        private String suppInvNo;
        @SerializedName("SupplierName")
        @Expose
        private String supplierName;
        @SerializedName("TerminalId")
        @Expose
        private String terminalId;
        @SerializedName("TicketId")
        @Expose
        private Object ticketId;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("VendarRefNo")
        @Expose
        private String vendarRefNo;
        @SerializedName("_TransLine")
        @Expose
        private Object transLine;
        private final static long serialVersionUID = -7656807209343063696L;

        public String getChallanDate() {
            return challanDate;
        }

        public void setChallanDate(String challanDate) {
            this.challanDate = challanDate;
        }

        public String getDataAriaId() {
            return dataAriaId;
        }

        public void setDataAriaId(String dataAriaId) {
            this.dataAriaId = dataAriaId;
        }

        public String getDelivaryDate() {
            return delivaryDate;
        }

        public void setDelivaryDate(String delivaryDate) {
            this.delivaryDate = delivaryDate;
        }

        public String getOperationType() {
            return operationType;
        }

        public void setOperationType(String operationType) {
            this.operationType = operationType;
        }

        public String getPono() {
            return pono;
        }

        public void setPono(String pono) {
            this.pono = pono;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getSuppInvNo() {
            return suppInvNo;
        }

        public void setSuppInvNo(String suppInvNo) {
            this.suppInvNo = suppInvNo;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public Object getTicketId() {
            return ticketId;
        }

        public void setTicketId(Object ticketId) {
            this.ticketId = ticketId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getVendarRefNo() {
            return vendarRefNo;
        }

        public void setVendarRefNo(String vendarRefNo) {
            this.vendarRefNo = vendarRefNo;
        }

        public Object getTransLine() {
            return transLine;
        }

        public void setTransLine(Object transLine) {
            this.transLine = transLine;
        }

    }

