package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class PrsInvntTransactionDetailsRequest implements Serializable
    {

        @SerializedName("Remarks")
        @Expose
        private String remarks;
        @SerializedName("PONO")
        @Expose
        private String pono;
        @SerializedName("OperationType")
        @Expose
        private String operationType;
        @SerializedName("VendarRefNo")
        @Expose
        private String vendarRefNo;
        @SerializedName("SupplierName")
        @Expose
        private String supplierName;
        @SerializedName("DelivaryDate")
        @Expose
        private String delivaryDate;
        @SerializedName("ChallanDate")
        @Expose
        private String challanDate;
        @SerializedName("SuppInvNo")
        @Expose
        private String suppInvNo;
        @SerializedName("DataAriaId")
        @Expose
        private String dataAriaId;
        @SerializedName("Store")
        @Expose
        private String store;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("TerminalId")
        @Expose
        private String terminalId;
        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private Object returnMessage;
        @SerializedName("TicketId")
        @Expose
        private Object ticketId;
        @SerializedName("_TransLine")
        @Expose
        private List<Object> transLine;


        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getPono() {
            return pono;
        }

        public void setPono(String pono) {
            this.pono = pono;
        }

        public String getOperationType() {
            return operationType;
        }

        public void setOperationType(String operationType) {
            this.operationType = operationType;
        }

        public String getVendarRefNo() {
            return vendarRefNo;
        }

        public void setVendarRefNo(String vendarRefNo) {
            this.vendarRefNo = vendarRefNo;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getDelivaryDate() {
            return delivaryDate;
        }

        public void setDelivaryDate(String delivaryDate) {
            this.delivaryDate = delivaryDate;
        }

        public String getChallanDate() {
            return challanDate;
        }

        public void setChallanDate(String challanDate) {
            this.challanDate = challanDate;
        }

        public String getSuppInvNo() {
            return suppInvNo;
        }

        public void setSuppInvNo(String suppInvNo) {
            this.suppInvNo = suppInvNo;
        }

        public String getDataAriaId() {
            return dataAriaId;
        }

        public void setDataAriaId(String dataAriaId) {
            this.dataAriaId = dataAriaId;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public Integer getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(Integer requestStatus) {
            this.requestStatus = requestStatus;
        }

        public Object getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(Object returnMessage) {
            this.returnMessage = returnMessage;
        }

        public Object getTicketId() {
            return ticketId;
        }

        public void setTicketId(Object ticketId) {
            this.ticketId = ticketId;
        }

        public List<Object> getTransLine() {
            return transLine;
        }

        public void setTransLine(List<Object> transLine) {
            this.transLine = transLine;
        }

    }

