package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model;

import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TransactionHeaderResponse implements Serializable {


    @SerializedName("OMSHeader")
    @Expose
    private List<OMSHeader> oMSHeader = null;
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

        @SerializedName("BusinessDate")
        @Expose
        private String businessDate;
        @SerializedName("CategoryType")
        @Expose
        private String categoryType;
        @SerializedName("CreatedDateTime")
        @Expose
        private String createdDateTime;
        @SerializedName("CustomerType")
        @Expose
        private String customerType;
        @SerializedName("DeliveryDate")
        @Expose
        private String deliveryDate;
        @SerializedName("ExpiryDays")
        @Expose
        private Integer expiryDays;
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
        @SerializedName("PaymentSource")
        @Expose
        private String paymentSource;
        @SerializedName("REFNO")
        @Expose
        private String refno;
        @SerializedName("ReciptId")
        @Expose
        private String reciptId;
        @SerializedName("ShippingMethod")
        @Expose
        private String shippingMethod;
        @SerializedName("StockStatus")
        @Expose
        private String stockStatus;
        @SerializedName("Terminal")
        @Expose
        private Object terminal;
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
        private boolean isTagBox;
        private boolean isSelected;
        private boolean scanView;
        private GetOMSTransactionResponse getOMSTransactionResponse;


        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        private int expandStatus = 0;

        public int getExpandStatus() {
            return expandStatus;
        }

        public void setExpandStatus(int expandStatus) {
            this.expandStatus = expandStatus;
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

        public String getCreatedDateTime() {
            return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
            this.createdDateTime = createdDateTime;
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

        public String getPaymentSource() {
            return paymentSource;
        }

        public void setPaymentSource(String paymentSource) {
            this.paymentSource = paymentSource;
        }

        public String getRefno() {
            return refno;
        }

        public void setRefno(String refno) {
            this.refno = refno;
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

        public GetOMSTransactionResponse getGetOMSTransactionResponse() {
            return getOMSTransactionResponse;
        }

        public void setGetOMSTransactionResponse(GetOMSTransactionResponse getOMSTransactionResponse) {
            this.getOMSTransactionResponse = getOMSTransactionResponse;
        }

        public boolean isTagBox() {
            return isTagBox;
        }

        public void setTagBox(boolean tagBox) {
            isTagBox = tagBox;
        }

        public boolean isScanView() {
            return scanView;
        }

        public void setScanView(boolean scanView) {
            this.scanView = scanView;
        }
    }
}


