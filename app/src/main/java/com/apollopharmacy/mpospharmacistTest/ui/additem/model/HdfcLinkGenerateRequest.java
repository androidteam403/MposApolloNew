package com.apollopharmacy.mpospharmacistTest.ui.additem.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HdfcLinkGenerateRequest {

    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("RequestType")
    @Expose
    private String requestType;
    @SerializedName("Cust_Name")
    @Expose
    private String custName;
    @SerializedName("Cust_EmailID")
    @Expose
    private String custEmailID;
    @SerializedName("Cust_MobileNo")
    @Expose
    private String custMobileNo;
    @SerializedName("PayAmount")
    @Expose
    private Double payAmount;
    @SerializedName("SiteID")
    @Expose
    private String siteID;
    @SerializedName("TerminalID")
    @Expose
    private String terminalID;
    @SerializedName("DocumentNo")
    @Expose
    private String documentNo;
    @SerializedName("OrderID")
    @Expose
    private String orderID;
    @SerializedName("TransactionMerchantID")
    @Expose
    private String transactionMerchantID;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmailID() {
        return custEmailID;
    }

    public void setCustEmailID(String custEmailID) {
        this.custEmailID = custEmailID;
    }

    public String getCustMobileNo() {
        return custMobileNo;
    }

    public void setCustMobileNo(String custMobileNo) {
        this.custMobileNo = custMobileNo;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getTransactionMerchantID() {
        return transactionMerchantID;
    }

    public void setTransactionMerchantID(String transactionMerchantID) {
        this.transactionMerchantID = transactionMerchantID;
    }

}