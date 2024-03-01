package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetOMSTransactionHeaderResponse implements Serializable {
    @SerializedName("OMSHeader")
    @Expose
    private List<OMSHeader> oMSHeader;
    @SerializedName("PickPackRequestId")
    @Expose
    private Object pickPackRequestId;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public List<OMSHeader> getOMSHeader() {
        return oMSHeader;
    }

    public void setOMSHeader(List<OMSHeader> oMSHeader) {
        this.oMSHeader = oMSHeader;
    }

    public Object getPickPackRequestId() {
        return pickPackRequestId;
    }

    public void setPickPackRequestId(Object pickPackRequestId) {
        this.pickPackRequestId = pickPackRequestId;
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

    public class OMSHeader implements Serializable {
        @SerializedName("BillingCity")
        @Expose
        private String billingCity;
        @SerializedName("BillingTAT")
        @Expose
        private String billingTAT;
        @SerializedName("BusinessDate")
        @Expose
        private String businessDate;
        @SerializedName("CategoryType")
        @Expose
        private String categoryType;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("CreatedDateTime")
        @Expose
        private String createdDateTime;
        @SerializedName("CustAddress")
        @Expose
        private String custAddress;
        @SerializedName("CustomerState")
        @Expose
        private String customerState;
        @SerializedName("CustomerType")
        @Expose
        private String customerType;
        @SerializedName("DeliveryDate")
        @Expose
        private String deliveryDate;
        @SerializedName("ExpiryDays")
        @Expose
        private Integer expiryDays;
        @SerializedName("NetAmount")
        @Expose
        private Float netAmount;
        @SerializedName("NumberofItemLines")
        @Expose
        private Integer numberofItemLines;
        @SerializedName("OrderPacked")
        @Expose
        private Boolean orderPacked;
        @SerializedName("OrderPickup")
        @Expose
        private Boolean orderPickup;
        @SerializedName("OrderSource")
        @Expose
        private String orderSource;
        @SerializedName("OrderType")
        @Expose
        private String orderType;
        @SerializedName("OverallOrderStatus")
        @Expose
        private String overallOrderStatus;
        @SerializedName("PackUserId")
        @Expose
        private Object packUserId;
        @SerializedName("PaymentSource")
        @Expose
        private String paymentSource;
        @SerializedName("PickPackDatetime")
        @Expose
        private String pickPackDatetime;
        @SerializedName("PickPackRequestId")
        @Expose
        private Object pickPackRequestId;
        @SerializedName("PickPackStatus")
        @Expose
        private Integer pickPackStatus;
        @SerializedName("PickPackStatusDesc")
        @Expose
        private Object pickPackStatusDesc;
        @SerializedName("PickPackUser")
        @Expose
        private String pickPackUser;
        @SerializedName("PickUserId")
        @Expose
        private Object pickUserId;
        @SerializedName("Pincode")
        @Expose
        private String pincode;
        @SerializedName("PriceVarition")
        @Expose
        private Boolean priceVarition;
        @SerializedName("REFNO")
        @Expose
        private String refno;
        @SerializedName("ReVerification")
        @Expose
        private Integer reVerification;
        @SerializedName("ReciptId")
        @Expose
        private String reciptId;
        @SerializedName("ShippingMethod")
        @Expose
        private String shippingMethod;
        @SerializedName("ShippingTAT")
        @Expose
        private String shippingTAT;
        @SerializedName("StockStatus")
        @Expose
        private String stockStatus;
        @SerializedName("Terminal")
        @Expose
        private Object terminal;
        @SerializedName("TimeSlot")
        @Expose
        private String timeSlot;
        @SerializedName("TransDate")
        @Expose
        private Object transDate;
        @SerializedName("TransactionId")
        @Expose
        private Object transactionId;
        @SerializedName("VendorCode")
        @Expose
        private Object vendorCode;
        @SerializedName("VendorId")
        @Expose
        private String vendorId;

        public String getBillingCity() {
            return billingCity;
        }

        public void setBillingCity(String billingCity) {
            this.billingCity = billingCity;
        }

        public String getBillingTAT() {
            return billingTAT;
        }

        public void setBillingTAT(String billingTAT) {
            this.billingTAT = billingTAT;
        }

        public String getBusinessDate() {
            return businessDate;
        }

        public void setBusinessDate(String businessDate) {
            this.businessDate = businessDate;
        }

        public String getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(String categoryType) {
            this.categoryType = categoryType;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreatedDateTime() {
            return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
            this.createdDateTime = createdDateTime;
        }

        public String getCustAddress() {
            return custAddress;
        }

        public void setCustAddress(String custAddress) {
            this.custAddress = custAddress;
        }

        public String getCustomerState() {
            return customerState;
        }

        public void setCustomerState(String customerState) {
            this.customerState = customerState;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Integer getExpiryDays() {
            return expiryDays;
        }

        public void setExpiryDays(Integer expiryDays) {
            this.expiryDays = expiryDays;
        }

        public Float getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(Float netAmount) {
            this.netAmount = netAmount;
        }

        public Integer getNumberofItemLines() {
            return numberofItemLines;
        }

        public void setNumberofItemLines(Integer numberofItemLines) {
            this.numberofItemLines = numberofItemLines;
        }

        public Boolean getOrderPacked() {
            return orderPacked;
        }

        public void setOrderPacked(Boolean orderPacked) {
            this.orderPacked = orderPacked;
        }

        public Boolean getOrderPickup() {
            return orderPickup;
        }

        public void setOrderPickup(Boolean orderPickup) {
            this.orderPickup = orderPickup;
        }

        public String getOrderSource() {
            return orderSource;
        }

        public void setOrderSource(String orderSource) {
            this.orderSource = orderSource;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOverallOrderStatus() {
            return overallOrderStatus;
        }

        public void setOverallOrderStatus(String overallOrderStatus) {
            this.overallOrderStatus = overallOrderStatus;
        }

        public Object getPackUserId() {
            return packUserId;
        }

        public void setPackUserId(Object packUserId) {
            this.packUserId = packUserId;
        }

        public String getPaymentSource() {
            return paymentSource;
        }

        public void setPaymentSource(String paymentSource) {
            this.paymentSource = paymentSource;
        }

        public String getPickPackDatetime() {
            return pickPackDatetime;
        }

        public void setPickPackDatetime(String pickPackDatetime) {
            this.pickPackDatetime = pickPackDatetime;
        }

        public Object getPickPackRequestId() {
            return pickPackRequestId;
        }

        public void setPickPackRequestId(Object pickPackRequestId) {
            this.pickPackRequestId = pickPackRequestId;
        }

        public Integer getPickPackStatus() {
            return pickPackStatus;
        }

        public void setPickPackStatus(Integer pickPackStatus) {
            this.pickPackStatus = pickPackStatus;
        }

        public Object getPickPackStatusDesc() {
            return pickPackStatusDesc;
        }

        public void setPickPackStatusDesc(Object pickPackStatusDesc) {
            this.pickPackStatusDesc = pickPackStatusDesc;
        }

        public String getPickPackUser() {
            return pickPackUser;
        }

        public void setPickPackUser(String pickPackUser) {
            this.pickPackUser = pickPackUser;
        }

        public Object getPickUserId() {
            return pickUserId;
        }

        public void setPickUserId(Object pickUserId) {
            this.pickUserId = pickUserId;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public Boolean getPriceVarition() {
            return priceVarition;
        }

        public void setPriceVarition(Boolean priceVarition) {
            this.priceVarition = priceVarition;
        }

        public String getRefno() {
            return refno;
        }

        public void setRefno(String refno) {
            this.refno = refno;
        }

        public Integer getReVerification() {
            return reVerification;
        }

        public void setReVerification(Integer reVerification) {
            this.reVerification = reVerification;
        }

        public String getReciptId() {
            return reciptId;
        }

        public void setReciptId(String reciptId) {
            this.reciptId = reciptId;
        }

        public String getShippingMethod() {
            return shippingMethod;
        }

        public void setShippingMethod(String shippingMethod) {
            this.shippingMethod = shippingMethod;
        }

        public String getShippingTAT() {
            return shippingTAT;
        }

        public void setShippingTAT(String shippingTAT) {
            this.shippingTAT = shippingTAT;
        }

        public String getStockStatus() {
            return stockStatus;
        }

        public void setStockStatus(String stockStatus) {
            this.stockStatus = stockStatus;
        }

        public Object getTerminal() {
            return terminal;
        }

        public void setTerminal(Object terminal) {
            this.terminal = terminal;
        }

        public String getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(String timeSlot) {
            this.timeSlot = timeSlot;
        }

        public Object getTransDate() {
            return transDate;
        }

        public void setTransDate(Object transDate) {
            this.transDate = transDate;
        }

        public Object getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Object transactionId) {
            this.transactionId = transactionId;
        }

        public Object getVendorCode() {
            return vendorCode;
        }

        public void setVendorCode(Object vendorCode) {
            this.vendorCode = vendorCode;
        }

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

    }
}
