package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJounalOnlineOrderTransactionsRequest {

    @SerializedName("RequestType")
    @Expose
    private Integer requestType;
    @SerializedName("BulkFilterBy")
    @Expose
    private Integer bulkFilterBy;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("CustomerAccount")
    @Expose
    private Object customerAccount;
    @SerializedName("ReceiptId")
    @Expose
    private Object receiptId;
    @SerializedName("ItemID")
    @Expose
    private Object itemID;
    @SerializedName("HomeDelivery")
    @Expose
    private Boolean homeDelivery;
    @SerializedName("MobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("CustomerName")
    @Expose
    private Object customerName;
    @SerializedName("BatchNo")
    @Expose
    private Object batchNo;
    @SerializedName("ArtName")
    @Expose
    private Object artName;
    @SerializedName("IPNumber")
    @Expose
    private Object iPNumber;
    @SerializedName("CardNo")
    @Expose
    private Object cardNo;
    @SerializedName("ISHyperLocal")
    @Expose
    private Boolean iSHyperLocal;
    @SerializedName("PreviousBills")
    @Expose
    private Boolean previousBills;
    @SerializedName("PendingBills")
    @Expose
    private Boolean pendingBills;
    @SerializedName("RiderRTO")
    @Expose
    private Boolean riderRTO;
    @SerializedName("RinderHandOver")
    @Expose
    private Boolean rinderHandOver;
    @SerializedName("RinderMobileNo")
    @Expose
    private Object rinderMobileNo;
    @SerializedName("DeliveryFailRTO")
    @Expose
    private Boolean deliveryFailRTO;
    @SerializedName("DspName")
    @Expose
    private Object dspName;
    @SerializedName("VendorName")
    @Expose
    private Object vendorName;

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getBulkFilterBy() {
        return bulkFilterBy;
    }

    public void setBulkFilterBy(Integer bulkFilterBy) {
        this.bulkFilterBy = bulkFilterBy;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Object getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(Object customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Object getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Object receiptId) {
        this.receiptId = receiptId;
    }

    public Object getItemID() {
        return itemID;
    }

    public void setItemID(Object itemID) {
        this.itemID = itemID;
    }

    public Boolean getHomeDelivery() {
        return homeDelivery;
    }

    public void setHomeDelivery(Boolean homeDelivery) {
        this.homeDelivery = homeDelivery;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getCustomerName() {
        return customerName;
    }

    public void setCustomerName(Object customerName) {
        this.customerName = customerName;
    }

    public Object getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Object batchNo) {
        this.batchNo = batchNo;
    }

    public Object getArtName() {
        return artName;
    }

    public void setArtName(Object artName) {
        this.artName = artName;
    }

    public Object getIPNumber() {
        return iPNumber;
    }

    public void setIPNumber(Object iPNumber) {
        this.iPNumber = iPNumber;
    }

    public Object getCardNo() {
        return cardNo;
    }

    public void setCardNo(Object cardNo) {
        this.cardNo = cardNo;
    }

    public Boolean getISHyperLocal() {
        return iSHyperLocal;
    }

    public void setISHyperLocal(Boolean iSHyperLocal) {
        this.iSHyperLocal = iSHyperLocal;
    }

    public Boolean getPreviousBills() {
        return previousBills;
    }

    public void setPreviousBills(Boolean previousBills) {
        this.previousBills = previousBills;
    }

    public Boolean getPendingBills() {
        return pendingBills;
    }

    public void setPendingBills(Boolean pendingBills) {
        this.pendingBills = pendingBills;
    }

    public Boolean getRiderRTO() {
        return riderRTO;
    }

    public void setRiderRTO(Boolean riderRTO) {
        this.riderRTO = riderRTO;
    }

    public Boolean getRinderHandOver() {
        return rinderHandOver;
    }

    public void setRinderHandOver(Boolean rinderHandOver) {
        this.rinderHandOver = rinderHandOver;
    }

    public Object getRinderMobileNo() {
        return rinderMobileNo;
    }

    public void setRinderMobileNo(Object rinderMobileNo) {
        this.rinderMobileNo = rinderMobileNo;
    }

    public Boolean getDeliveryFailRTO() {
        return deliveryFailRTO;
    }

    public void setDeliveryFailRTO(Boolean deliveryFailRTO) {
        this.deliveryFailRTO = deliveryFailRTO;
    }

    public Object getDspName() {
        return dspName;
    }

    public void setDspName(Object dspName) {
        this.dspName = dspName;
    }

    public Object getVendorName() {
        return vendorName;
    }

    public void setVendorName(Object vendorName) {
        this.vendorName = vendorName;
    }

}
