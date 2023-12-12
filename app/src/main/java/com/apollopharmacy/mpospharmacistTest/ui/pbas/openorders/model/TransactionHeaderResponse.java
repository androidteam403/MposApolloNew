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


    public static class OMSHeader implements Serializable {

        @SerializedName("BusinessDate")
        @Expose
        private String businessDate;

        @SerializedName("FulfilId")
        @Expose
        private String FulfilId;

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
        @SerializedName("OverallOrderStatus")
        @Expose
        private String OverallOrderStatus;


        @SerializedName("PaymentSource")
        @Expose
        private String paymentSource;
        @SerializedName("PickPackDatetime")
        @Expose
        private String PickPackDatetime;
        @SerializedName("PickPackStatus")
        @Expose
        private String PickPackStatus;
        @SerializedName("PickPackUser")
        @Expose
        private String PickPackUser;
        @SerializedName("REFNO")
        @Expose
        private String refno;

        @SerializedName("ReVerification")
        @Expose
        private int ReVerification;
        @SerializedName("ReciptId")
        @Expose
        private String reciptId;
        @SerializedName("NumberofItemLines")
        @Expose
        private int NumberofItemLines;
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
        @SerializedName("ShippingTAT")
        @Expose
        private String shipmentTat;
        @SerializedName("BillingTAT")
        @Expose
        private String billingTat;
        private boolean isTagBox;
        private boolean isSelected;
        private boolean scanView;
        private boolean isOverAllStatusfromList;
        private String itemStatus = "";
        private String scannedBarcode;
        private boolean isPickupReserved;
        private boolean isExpanded;

        private boolean isScanned;
        private boolean isShipmentTatSelected;

        public boolean isShipmentTatSelected() {
            return isShipmentTatSelected;
        }

        public void setShipmentTatSelected(boolean shipmentTatSelected) {
            isShipmentTatSelected = shipmentTatSelected;
        }

        private GetOMSTransactionResponse getOMSTransactionResponse;
        private boolean isOnHold = false;

        public String getFulfilId() {
            return FulfilId;
        }

        public String setFulfilId(String fulfilId) {
            FulfilId = fulfilId;
            return fulfilId;
        }

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

        public String getOverallOrderStatus() {
            return OverallOrderStatus;
        }

        public void setOverallOrderStatus(String overallOrderStatus) {
            OverallOrderStatus = overallOrderStatus;
        }

        public String getPaymentSource() {
            return paymentSource;
        }

        public void setPaymentSource(String paymentSource) {
            this.paymentSource = paymentSource;
        }

        public String getPickPackDatetime() {
            return PickPackDatetime;
        }

        public void setPickPackDatetime(String pickPackDatetime) {
            PickPackDatetime = pickPackDatetime;
        }

        public String getPickPackStatus() {
            return PickPackStatus;
        }

        public void setPickPackStatus(String pickPackStatus) {
            PickPackStatus = pickPackStatus;
        }

        public String getPickPackUser() {
            return PickPackUser;
        }

        public void setPickPackUser(String pickPackUser) {
            PickPackUser = pickPackUser;
        }

        public String getRefno() {
            return refno;
        }

        public void setRefno(String refno) {
            this.refno = refno;
        }

        public int getReVerification() {
            return ReVerification;
        }

        public void setReVerification(int reVerification) {
            ReVerification = reVerification;
        }

        public String getReciptId() {
            return reciptId;
        }

        public void setReciptId(String reciptId) {
            this.reciptId = reciptId;
        }

        public int getNumberofItemLines() {
            return NumberofItemLines;
        }

        public void setNumberofItemLines(int numberofItemLines) {
            NumberofItemLines = numberofItemLines;
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

        public String getShipmentTat() {
            return shipmentTat;
        }

        public void setShipmentTat(String shipmentTat) {
            this.shipmentTat = shipmentTat;
        }

        public String getBillingTat() {
            return billingTat;
        }

        public void setBillingTat(String billingTat) {
            this.billingTat = billingTat;
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

        public boolean isOverAllStatusfromList() {
            return isOverAllStatusfromList;
        }

        public void setOverAllStatusfromList(boolean overAllStatusfromList) {
            isOverAllStatusfromList = overAllStatusfromList;
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

        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public String getScannedBarcode() {
            return scannedBarcode;
        }

        public void setScannedBarcode(String scannedBarcode) {
            this.scannedBarcode = scannedBarcode;
        }

        public boolean isPickupReserved() {
            return isPickupReserved;
        }

        public void setPickupReserved(boolean pickupReserved) {
            isPickupReserved = pickupReserved;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }
        public boolean isScanned() {
            return isScanned;
        }

        public void setScanned(boolean scanned) {
            isScanned = scanned;
        }

        public boolean isOnHold() {
            return isOnHold;
        }

        public void setOnHold(boolean onHold) {
            isOnHold = onHold;
        }
    }
}


