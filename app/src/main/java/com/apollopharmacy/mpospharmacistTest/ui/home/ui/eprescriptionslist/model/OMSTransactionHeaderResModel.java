package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model;

import androidx.databinding.BaseObservable;

import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OMSTransactionHeaderResModel {
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("OMSHeader")
    private List<OMSHeaderObj> OMSHeaderArr;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public List<OMSHeaderObj> getOMSHeaderArr() {
        return OMSHeaderArr;
    }

    public void setOMSHeaderArr(List<OMSHeaderObj> oMSHeader) {
        this.OMSHeaderArr = oMSHeader;
    }

    public static class OMSHeaderObj extends BaseObservable implements Serializable {
        @Expose
        @SerializedName("BusinessDate")
        private String BusinessDate;
        @Expose
        @SerializedName("CreatedDateTime")
        private String CreatedDateTime;
        @Expose
        @SerializedName("CustomerType")
        private String CustomerType;
        @Expose
        @SerializedName("DeliveryDate")
        private String DeliveryDate;

        public void setREFNO(String REFNO) {
            this.REFNO = REFNO;
        }

        @Expose
        @SerializedName("ExpiryDays")
        private int ExpiryDays;
        @Expose
        @SerializedName("OrderSource")
        private String OrderSource;
        @Expose
        @SerializedName("OrderType")
        private String OrderType;
        @SerializedName("OverallOrderStatus")
        @Expose
        private String OverallOrderStatus;
        @Expose
        @SerializedName("PaymentSource")
        private String PaymentSource;

        @SerializedName("PickPackDatetime")
        @Expose
        private String PickPackDatetime;
        @SerializedName("PickPackStatus")
        @Expose
        private String PickPackStatus;
        @SerializedName("PickPackUser")
        @Expose
        private String PickPackUser;

        @Expose
        @SerializedName("REFNO")
        private String REFNO;
        @Expose
        @SerializedName("ReciptId")
        private String ReciptId;
        @SerializedName("NumberofItemLines")
        @Expose
        private int NumberofItemLines;
        @Expose
        @SerializedName("ShippingMethod")
        private String ShippingMethod;
        @Expose
        @SerializedName("StockStatus")
        private String StockStatus;
        @Expose
        @SerializedName("Terminal")
        private String Terminal;
        @Expose
        @SerializedName("TransDate")
        private String TransDate;
        @Expose
        @SerializedName("TransactionId")
        private String TransactionId;
        @Expose
        @SerializedName("VendorCode")
        private String VendorCode;
        @Expose
        @SerializedName("VendorId")
        private String VendorId;

        @SerializedName("ShippingTAT")
        @Expose
        private String shipmentTat;
        @SerializedName("BillingTAT")
        @Expose
        private String billingTat;
        @Expose
        @SerializedName("OrderPacked")
        private boolean OrderPacked;

        @Expose
        @SerializedName("OrderPickup")
        private boolean OrderPickup;

        @Expose
        @SerializedName("CategoryType")
        private String CategoryType;

        private boolean isSelected;

        public String getCategoryType() {
            return CategoryType;
        }

        public void setCategoryType(String CategoryType) {
            this.CategoryType = CategoryType;
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

        public boolean getOrderPacked() {
            return OrderPacked;
        }

        public boolean getOrderPickup() {
            return OrderPickup;
        }

        public String getBusinessDate() {
            return BusinessDate;
        }

        public String getCreatedDateTime() {
            return CreatedDateTime;
        }

        public String getCustomerType() {
            return CustomerType;
        }

        public String getDeliveryDate() {
            return DeliveryDate;
        }

        public int getExpiryDays() {
            return ExpiryDays;
        }

        public String getOrderSource() {
            return OrderSource;
        }

        public String getOrderType() {
            return OrderType;
        }

        public String getOverallOrderStatus() {
            return OverallOrderStatus;
        }

        public void setOverallOrderStatus(String overallOrderStatus) {
            OverallOrderStatus = overallOrderStatus;
        }

        public String getPaymentSource() {
            return PaymentSource;
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

        public String getREFNO() {
            return REFNO;
        }

        public String getReciptId() {
            return ReciptId;
        }

        public int getNumberofItemLines() {
            return NumberofItemLines;
        }

        public void setNumberofItemLines(int numberofItemLines) {
            NumberofItemLines = numberofItemLines;
        }

        public String getShippingMethod() {
            return ShippingMethod;
        }

        public String getStockStatus() {
            return StockStatus;
        }

        public String getTerminal() {
            return Terminal;
        }

        public String getTransDate() {
            return TransDate;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public String getVendorCode() {
            return VendorCode;
        }

        public String getVendorId() {
            return VendorId;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
