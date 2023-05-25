package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HdfcLinkGenerateResponse {

    @SerializedName("Cust_EmailID")
    @Expose
    private String custEmailID;
    @SerializedName("Cust_MobileNo")
    @Expose
    private String custMobileNo;
    @SerializedName("Cust_Name")
    @Expose
    private String custName;
    @SerializedName("DocumentNo")
    @Expose
    private String documentNo;
    @SerializedName("ErrorCode")
    @Expose
    private String errorCode;
    @SerializedName("ErrorDesc")
    @Expose
    private String errorDesc;
    @SerializedName("InvoiceID")
    @Expose
    private String invoiceID;
    @SerializedName("Invoice_Status")
    @Expose
    private String invoiceStatus;
    @SerializedName("OrderID")
    @Expose
    private String orderID;
    @SerializedName("Paid_RefNo")
    @Expose
    private Object paidRefNo;
    @SerializedName("PayAmount")
    @Expose
    private Double payAmount;
    @SerializedName("Paymented_Status")
    @Expose
    private Object paymentedStatus;
    @SerializedName("QR_Code")
    @Expose
    private String qRCode;
    @SerializedName("RequestID")
    @Expose
    private String requestID;
    @SerializedName("SiteID")
    @Expose
    private String siteID;
    @SerializedName("SuccessMsg")
    @Expose
    private String successMsg;
    @SerializedName("TerminalID")
    @Expose
    private String terminalID;
    @SerializedName("Tiny_URL")
    @Expose
    private String tinyURL;
    @SerializedName("TransactionMerchantID")
    @Expose
    private String transactionMerchantID;
    @SerializedName("paymented_Status_Desc")
    @Expose
    private Object paymentedStatusDesc;

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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Object getPaidRefNo() {
        return paidRefNo;
    }

    public void setPaidRefNo(Object paidRefNo) {
        this.paidRefNo = paidRefNo;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Object getPaymentedStatus() {
        return paymentedStatus;
    }

    public void setPaymentedStatus(Object paymentedStatus) {
        this.paymentedStatus = paymentedStatus;
    }

    public String getQRCode() {
        return qRCode;
    }

    public void setQRCode(String qRCode) {
        this.qRCode = qRCode;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getTinyURL() {
        return tinyURL;
    }

    public void setTinyURL(String tinyURL) {
        this.tinyURL = tinyURL;
    }

    public String getTransactionMerchantID() {
        return transactionMerchantID;
    }

    public void setTransactionMerchantID(String transactionMerchantID) {
        this.transactionMerchantID = transactionMerchantID;
    }

    public Object getPaymentedStatusDesc() {
        return paymentedStatusDesc;
    }

    public void setPaymentedStatusDesc(Object paymentedStatusDesc) {
        this.paymentedStatusDesc = paymentedStatusDesc;
    }

}