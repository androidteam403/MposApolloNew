package com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OMSTransactionHeaderResModel {
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("OMSHeader")
    private ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> OMSHeaderArr;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> getOMSHeaderArr() {
        return OMSHeaderArr;
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
        @Expose
        @SerializedName("ExpiryDays")
        private int ExpiryDays;
        @Expose
        @SerializedName("OrderSource")
        private String OrderSource;
        @Expose
        @SerializedName("OrderType")
        private String OrderType;
        @Expose
        @SerializedName("PaymentSource")
        private String PaymentSource;
        @Expose
        @SerializedName("REFNO")
        private String REFNO;
        @Expose
        @SerializedName("ReciptId")
        private String ReciptId;
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

        public String getPaymentSource() {
            return PaymentSource;
        }

        public String getREFNO() {
            return REFNO;
        }

        public String getReciptId() {
            return ReciptId;
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
    }
}
