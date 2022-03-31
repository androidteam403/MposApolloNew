package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerDataResBean  implements Serializable
{

    @Expose
    @SerializedName("VendorId")
    private String VendorId;
    @Expose
    @SerializedName("VendorCode")
    private String VendorCode;
    @Expose
    @SerializedName("Type")
    private double Type;
    @Expose
    @SerializedName("TransactionId")
    private String TransactionId;
    @Expose
    @SerializedName("TransType")
    private double TransType;
    @Expose
    @SerializedName("TransDate")
    private String TransDate;
    @Expose
    @SerializedName("TrackingRef")
    private String TrackingRef;
    @Expose
    @SerializedName("TotalTaxAmount")
    private double TotalTaxAmount;
    @Expose
    @SerializedName("TotalManualDiscountPercentage")
    private double TotalManualDiscountPercentage;
    @Expose
    @SerializedName("TotalManualDiscountAmount")
    private double TotalManualDiscountAmount;
    @Expose
    @SerializedName("TotalMRP")
    private double TotalMRP;
    @Expose
    @SerializedName("TotalDiscAmount")
    private double TotalDiscAmount;
    @Expose
    @SerializedName("TimewhenTransClosed")
    private double TimewhenTransClosed;
    @Expose
    @SerializedName("Tier")
    private String Tier;
    @Expose
    @SerializedName("Terminal")
    private String Terminal;
    @Expose
    @SerializedName("TenderLine")
    private List<String> TenderLine;
    @Expose
    @SerializedName("StoreName")
    private String StoreName;
    @Expose
    @SerializedName("Store")
    private String Store;
    @Expose
    @SerializedName("StockStatus")
    private String StockStatus;
    @Expose
    @SerializedName("State")
    private String State;
    @Expose
    @SerializedName("Staff")
    private String Staff;
    @Expose
    @SerializedName("ShippingMethod")
    private String ShippingMethod;
    @Expose
    @SerializedName("ShippingMethodDesc")
    private String ShippingMethodDesc;

    @Expose
    @SerializedName("ShippingCharges")
    private double ShippingCharges;
    @Expose
    @SerializedName("SalesOrigin")
    private String SalesOrigin;
    @Expose
    @SerializedName("SalesLine")
    private ArrayList<SalesLineEntity> SalesLine;
    @Expose
    @SerializedName("SEZ")
    private double SEZ;
    @Expose
    @SerializedName("RoundedAmount")
    private double RoundedAmount;
    @Expose
    @SerializedName("ReturnType")
    private double ReturnType;
    @Expose
    @SerializedName("ReturnTransactionId")
    private String ReturnTransactionId;
    @Expose
    @SerializedName("ReturnTerminal")
    private String ReturnTerminal;
    @Expose
    @SerializedName("ReturnStore")
    private String ReturnStore;
    @Expose
    @SerializedName("ReturnReceiptId")
    private String ReturnReceiptId;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private double RequestStatus;
    @Expose
    @SerializedName("ReminderDays")
    private double ReminderDays;
    @Expose
    @SerializedName("Remainingamount")
    private double Remainingamount;
    @Expose
    @SerializedName("RegionCode")
    private String RegionCode;
    @Expose
    @SerializedName("ReciptId")
    private String ReciptId;
    @Expose
    @SerializedName("REFNO")
    private String REFNO;
    @Expose
    @SerializedName("PosEvent")
    private double PosEvent;
    @Expose
    @SerializedName("Pincode")
    private String Pincode;
    @Expose
    @SerializedName("PaymentSource")
    private String PaymentSource;

    @SerializedName("PickPackReservation")
    @Expose
    private List<PickPackReservation> pickPackReservation = null;
    public List<PickPackReservation> getPickPackReservation() {
        return pickPackReservation;
    }

    public void setPickPackReservation(List<PickPackReservation> pickPackReservation) {
        this.pickPackReservation = pickPackReservation;
    }


    @Expose
    @SerializedName("PatientID")
    private String PatientID;
    @Expose
    @SerializedName("OrderType")
    private String OrderType;
    @Expose
    @SerializedName("OrderSource")
    private String OrderSource;
    @Expose
    @SerializedName("OrderPrescriptionURL")
    private ArrayList<OrderPrescriptionObj> OrderPrescriptionURL;
    @Expose
    @SerializedName("OMSCreditAmount")
    private double OMSCreditAmount;
    @Expose
    @SerializedName("NumberofItems")
    private double NumberofItems;
    @Expose
    @SerializedName("NumberofItemLines")
    private double NumberofItemLines;
    @Expose
    @SerializedName("NetAmountInclTax")
    private double NetAmountInclTax;
    @Expose
    @SerializedName("NetAmount")
    private double NetAmount;
    @Expose
    @SerializedName("MobileNO")
    private String MobileNO;
    @Expose
    @SerializedName("IsVoid")
    private boolean IsVoid;
    @Expose
    @SerializedName("IsUHIDBilling")
    private boolean IsUHIDBilling;
    @Expose
    @SerializedName("IsStockCheck")
    private boolean IsStockCheck;
    @Expose
    @SerializedName("IsReturn")
    private boolean IsReturn;
    @Expose
    @SerializedName("IsRepeatBill")
    private boolean IsRepeatBill;
    @Expose
    @SerializedName("IsManualBill")
    private boolean IsManualBill;
    @Expose
    @SerializedName("ISReturnAllowed")
    private boolean ISReturnAllowed;
    @Expose
    @SerializedName("ISReserved")
    private boolean ISReserved;
    @Expose
    @SerializedName("ISPrescibeDiscount")
    private boolean ISPrescibeDiscount;
    @Expose
    @SerializedName("ISPosted")
    private boolean ISPosted;
    @Expose
    @SerializedName("ISOnlineOrder")
    private boolean ISOnlineOrder;
    @Expose
    @SerializedName("ISOMSValidate")
    private boolean ISOMSValidate;
    @Expose
    @SerializedName("ISOMSOrder")
    private boolean ISOMSOrder;
    @Expose
    @SerializedName("ISHyperLocalDelivery")
    private boolean ISHyperLocalDelivery;
    @Expose
    @SerializedName("ISHyperDelivered")
    private boolean ISHyperDelivered;
    @Expose
    @SerializedName("ISHBPStore")
    private boolean ISHBPStore;
    @Expose
    @SerializedName("ISCancelled")
    private boolean ISCancelled;
    @Expose
    @SerializedName("ISBulkBilling")
    private boolean ISBulkBilling;
    @Expose
    @SerializedName("ISBatchModifiedAllowed")
    private boolean ISBatchModifiedAllowed;
    @Expose
    @SerializedName("ISAdvancePayment")
    private boolean ISAdvancePayment;
    @Expose
    @SerializedName("IPSerialNO")
    private String IPSerialNO;
    @Expose
    @SerializedName("IPNO")
    private String IPNO;
    @Expose
    @SerializedName("HCOfferCode")
    private String HCOfferCode;
    @Expose
    @SerializedName("GrossAmount")
    private double GrossAmount;
    @Expose
    @SerializedName("Gender")
    private double Gender;
    @Expose
    @SerializedName("ExpiryDays")
    private double ExpiryDays;
    @Expose
    @SerializedName("EntryStatus")
    private double EntryStatus;
    @Expose
    @SerializedName("DoctorName")
    private String DoctorName;
    @Expose
    @SerializedName("DoctorCode")
    private String DoctorCode;
    @Expose
    @SerializedName("DiscountStatus")
    private double DiscountStatus;
    @Expose
    @SerializedName("DiscountReferenceID")
    private String DiscountReferenceID;
    @Expose
    @SerializedName("DiscountRef")
    private String DiscountRef;
    @Expose
    @SerializedName("DiscAmount")
    private double DiscAmount;
    @Expose
    @SerializedName("DeliveryDate")
    private String DeliveryDate;
    @Expose
    @SerializedName("DataAreaId")
    private String DataAreaId;
    @Expose
    @SerializedName("DSPCode")
    private String DSPCode;
    @Expose
    @SerializedName("DOB")
    private String DOB;
    @Expose
    @SerializedName("CustomerType")
    private String CustomerType;
    @Expose
    @SerializedName("CustomerState")
    private String CustomerState;
    @Expose
    @SerializedName("CustomerName")
    private String CustomerName;
    @Expose
    @SerializedName("CustomerID")
    private String CustomerID;
    @Expose
    @SerializedName("CustDiscamount")
    private double CustDiscamount;
    @Expose
    @SerializedName("CustAddress")
    private String CustAddress;
    @Expose
    @SerializedName("CustAccount")
    private String CustAccount;
    @Expose
    @SerializedName("Currency")
    private String Currency;
    @Expose
    @SerializedName("CreatedonPosTerminal")
    private String CreatedonPosTerminal;
    @Expose
    @SerializedName("CreatedDateTime")
    private String CreatedDateTime;
    @Expose
    @SerializedName("CouponCode")
    private String CouponCode;
    @Expose
    @SerializedName("CorpCode")
    private String CorpCode;
    @Expose
    @SerializedName("Comment")
    private String Comment;
    @Expose
    @SerializedName("Channel")
    private String Channel;
    @Expose
    @SerializedName("CancelReasonCode")
    private String CancelReasonCode;
    @Expose
    @SerializedName("BusinessDate")
    private String BusinessDate;
    @Expose
    @SerializedName("BillingCity")
    private String BillingCity;
    @Expose
    @SerializedName("BatchTerminalid")
    private String BatchTerminalid;
    @Expose
    @SerializedName("ApprovedID")
    private String ApprovedID;
    @Expose
    @SerializedName("AmounttoAccount")
    private double AmounttoAccount;
    @Expose
    @SerializedName("AllowedTenderType")
    private String AllowedTenderType;
    @Expose
    @SerializedName("AgeGroup")
    private String AgeGroup;
    @Expose
    @SerializedName("AWBNo")
    private String AWBNo;

    public String getVendorId() {
        return VendorId;
    }

    public String getVendorCode() {
        return VendorCode;
    }

    public double getType() {
        return Type;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public double getTransType() {
        return TransType;
    }

    public String getTransDate() {
        return TransDate;
    }

    public String getTrackingRef() {
        return TrackingRef;
    }

    public double getTotalTaxAmount()
    {
        return TotalTaxAmount;
    }

    public double getTotalManualDiscountPercentage() {
        return TotalManualDiscountPercentage;
    }

    public double getTotalManualDiscountAmount() {
        return TotalManualDiscountAmount;
    }

    public double getTotalMRP() {
        return TotalMRP;
    }

    public double getTotalDiscAmount() {
        return TotalDiscAmount;
    }

    public double getTimewhenTransClosed() {
        return TimewhenTransClosed;
    }

    public String getTier() {
        return Tier;
    }

    public String getTerminal() {
        return Terminal;
    }

    public void setTerminal(String terminal)
    {
        this.Terminal=terminal;
    }

    public List<String> getTenderLine() {
        return TenderLine;
    }

    public String getStoreName() {
        return StoreName;
    }

    public String getStore() {
        return Store;
    }

    public String getStockStatus() {
        return StockStatus;
    }

    public String getState() {
        return State;
    }

    public String getStaff() {
        return Staff;
    }

    public String getShippingMethod() {
        return ShippingMethod;
    }
    public String getShippingMethodDesc() {
        return ShippingMethodDesc;
    }

    public double getShippingCharges() {
        return ShippingCharges;
    }

    public String getSalesOrigin() {
        return SalesOrigin;
    }

    public ArrayList<SalesLineEntity> getSalesLine() {
        return SalesLine;
    }

    public void setgetSalesLine(ArrayList<SalesLineEntity> SalesLine1)
    {
        this.SalesLine=SalesLine1;
    }

    public void setSalesLine(ArrayList<SalesLineEntity> salesLine)
    {
        this.SalesLine=salesLine;
    }

    public double getSEZ() {
        return SEZ;
    }

    public double getRoundedAmount() {
        return RoundedAmount;
    }

    public double getReturnType() {
        return ReturnType;
    }

    public String getReturnTransactionId() {
        return ReturnTransactionId;
    }

    public String getReturnTerminal() {
        return ReturnTerminal;
    }

    public String getReturnStore() {
        return ReturnStore;
    }

    public String getReturnReceiptId() {
        return ReturnReceiptId;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public double getRequestStatus() {
        return RequestStatus;
    }

    public double getReminderDays() {
        return ReminderDays;
    }

    public double getRemainingamount() {
        return Remainingamount;
    }

    public String getRegionCode() {
        return RegionCode;
    }

    public String getReciptId() {
        return ReciptId;
    }

    public String getREFNO() {
        return REFNO;
    }

    public  void setREFNO(String refno)
    {
        this.REFNO=refno;
    }

    public double getPosEvent() {
        return PosEvent;
    }

    public String getPincode() {
        return Pincode;
    }

    public String getPaymentSource() {
        return PaymentSource;
    }

    public String getPatientID() {
        return PatientID;
    }

    public String getOrderType() {
        return OrderType;
    }

    public String getOrderSource() {
        return OrderSource;
    }

    public ArrayList<OrderPrescriptionObj> getOrderPrescriptionURL() {
        return OrderPrescriptionURL;
    }

    public double getOMSCreditAmount() {
        return OMSCreditAmount;
    }

    public double getNumberofItems() {
        return NumberofItems;
    }

    public double getNumberofItemLines() {
        return NumberofItemLines;
    }

    public double getNetAmountInclTax() {
        return NetAmountInclTax;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public String getMobileNO() {
        return MobileNO;
    }

    public boolean getIsVoid() {
        return IsVoid;
    }

    public boolean getIsUHIDBilling() {
        return IsUHIDBilling;
    }

    public boolean getIsStockCheck() {
        return IsStockCheck;
    }

    public boolean getIsReturn() {
        return IsReturn;
    }

    public boolean getIsRepeatBill() {
        return IsRepeatBill;
    }

    public boolean getIsManualBill() {
        return IsManualBill;
    }

    public boolean getISReturnAllowed() {
        return ISReturnAllowed;
    }

    public boolean getISReserved() {
        return ISReserved;
    }

    public boolean getISPrescibeDiscount() {
        return ISPrescibeDiscount;
    }

    public boolean getISPosted() {
        return ISPosted;
    }

    public boolean getISOnlineOrder() {
        return ISOnlineOrder;
    }

    public boolean getISOMSValidate() {
        return ISOMSValidate;
    }

    public boolean getISOMSOrder() {
        return ISOMSOrder;
    }

    public boolean getISHyperLocalDelivery() {
        return ISHyperLocalDelivery;
    }

    public boolean getISHyperDelivered() {
        return ISHyperDelivered;
    }

    public boolean getISHBPStore() {
        return ISHBPStore;
    }

    public boolean getISCancelled() {
        return ISCancelled;
    }

    public boolean getISBulkBilling() {
        return ISBulkBilling;
    }

    public boolean getISBatchModifiedAllowed() {
        return ISBatchModifiedAllowed;
    }

    public boolean getISAdvancePayment() {
        return ISAdvancePayment;
    }

    public String getIPSerialNO() {
        return IPSerialNO;
    }

    public String getIPNO() {
        return IPNO;
    }

    public String getHCOfferCode() {
        return HCOfferCode;
    }

    public double getGrossAmount() {
        return GrossAmount;
    }

    public double getGender() {
        return Gender;
    }

    public double getExpiryDays() {
        return ExpiryDays;
    }

    public double getEntryStatus() {
        return EntryStatus;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getDoctorCode() {
        return DoctorCode;
    }

    public double getDiscountStatus() {
        return DiscountStatus;
    }

    public String getDiscountReferenceID() {
        return DiscountReferenceID;
    }

    public String getDiscountRef() {
        return DiscountRef;
    }

    public double getDiscAmount() {
        return DiscAmount;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public String getDataAreaId() {
        return DataAreaId;
    }

    public String getDSPCode() {
        return DSPCode;
    }

    public String getDOB() {
        return DOB;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public String getCustomerState() {
        return CustomerState;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public double getCustDiscamount() {
        return CustDiscamount;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public String getCustAccount() {
        return CustAccount;
    }

    public String getCurrency() {
        return Currency;
    }

    public String getCreatedonPosTerminal() {
        return CreatedonPosTerminal;
    }

    public  void setCreatedonPosTerminal(String createdonPosTerminal)
    {
        this.CreatedonPosTerminal=createdonPosTerminal;
    }

    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public String getCorpCode() {
        return CorpCode;
    }

    public String getComment() {
        return Comment;
    }

    public String getChannel() {
        return Channel;
    }

    public String getCancelReasonCode() {
        return CancelReasonCode;
    }

    public String getBusinessDate() {
        return BusinessDate;
    }

    public String getBillingCity() {
        return BillingCity;
    }

    public String getBatchTerminalid() {
        return BatchTerminalid;
    }

    public String getApprovedID() {
        return ApprovedID;
    }

    public double getAmounttoAccount() {
        return AmounttoAccount;
    }

    public String getAllowedTenderType() {
        return AllowedTenderType;
    }

    public String getAgeGroup() {
        return AgeGroup;
    }

    public String getAWBNo() {
        return AWBNo;
    }

    /*public static class SalesLineEntity {
        @Expose
        @SerializedName("VariantId")
        private String VariantId;
        @Expose
        @SerializedName("UnitQty")
        private double UnitQty;
        @Expose
        @SerializedName("UnitPrice")
        private double UnitPrice;
        @Expose
        @SerializedName("Unit")
        private String Unit;
        @Expose
        @SerializedName("TotalTax")
        private double TotalTax;
        @Expose
        @SerializedName("TotalRoundedAmount")
        private double TotalRoundedAmount;
        @Expose
        @SerializedName("TotalDiscPct")
        private double TotalDiscPct;
        @Expose
        @SerializedName("TotalDiscAmount")
        private double TotalDiscAmount;
        @Expose
        @SerializedName("Total")
        private double Total;
        @Expose
        @SerializedName("TaxAmount")
        private double TaxAmount;
        @Expose
        @SerializedName("Tax")
        private double Tax;
        @Expose
        @SerializedName("SubstitudeItemId")
        private String SubstitudeItemId;
        @Expose
        @SerializedName("SubClassification")
        private String SubClassification;
        @Expose
        @SerializedName("SubCategoryCode")
        private String SubCategoryCode;
        @Expose
        @SerializedName("SubCategory")
        private String SubCategory;
        @Expose
        @SerializedName("StockQty")
        private double StockQty;
        @Expose
        @SerializedName("ScheduleCategoryCode")
        private String ScheduleCategoryCode;
        @Expose
        @SerializedName("ScheduleCategory")
        private String ScheduleCategory;
        @Expose
        @SerializedName("SGSTTaxCode")
        private String SGSTTaxCode;
        @Expose
        @SerializedName("SGSTPerc")
        private double SGSTPerc;
        @Expose
        @SerializedName("ReturnQty")
        private double ReturnQty;
        @Expose
        @SerializedName("RetailSubCategoryRecID")
        private String RetailSubCategoryRecID;
        @Expose
        @SerializedName("RetailMainCategoryRecID")
        private String RetailMainCategoryRecID;
        @Expose
        @SerializedName("RetailCategoryRecID")
        private String RetailCategoryRecID;
        @Expose
        @SerializedName("RemainingQty")
        private double RemainingQty;
        @Expose
        @SerializedName("RemainderDays")
        private double RemainderDays;
        @Expose
        @SerializedName("Qty")
        private double Qty;
        @Expose
        @SerializedName("ProductRecID")
        private String ProductRecID;
        @Expose
        @SerializedName("Price")
        private double Price;
        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;
        @Expose
        @SerializedName("PhysicalMRP")
        private double PhysicalMRP;
        @Expose
        @SerializedName("PhysicalExpiry")
        private String PhysicalExpiry;
        @Expose
        @SerializedName("PhysicalBatchID")
        private String PhysicalBatchID;
        @Expose
        @SerializedName("PeriodicDiscAmount")
        private double PeriodicDiscAmount;
        @Expose
        @SerializedName("OriginalPrice")
        private double OriginalPrice;
        @Expose
        @SerializedName("OrderStatus")
        private double OrderStatus;
        @Expose
        @SerializedName("OmsLineRECID")
        private double OmsLineRECID;
        @Expose
        @SerializedName("OmsLineID")
        private double OmsLineID;
        @Expose
        @SerializedName("OfferType")
        private double OfferType;
        @Expose
        @SerializedName("OfferQty")
        private double OfferQty;
        @Expose
        @SerializedName("OfferDiscountValue")
        private double OfferDiscountValue;
        @Expose
        @SerializedName("OfferDiscountType")
        private double OfferDiscountType;
        @Expose
        @SerializedName("OfferAmount")
        private double OfferAmount;
        @Expose
        @SerializedName("NetAmountInclTax")
        private double NetAmountInclTax;
        @Expose
        @SerializedName("NetAmount")
        private double NetAmount;
        @Expose
        @SerializedName("ModifyBatchId")
        private String ModifyBatchId;
        @Expose
        @SerializedName("MixMode")
        private boolean MixMode;
        @Expose
        @SerializedName("ManufacturerName")
        private String ManufacturerName;
        @Expose
        @SerializedName("ManufacturerCode")
        private String ManufacturerCode;
        @Expose
        @SerializedName("MRP")
        private double MRP;
        @Expose
        @SerializedName("MMGroupId")
        private String MMGroupId;
        @Expose
        @SerializedName("LinedscAmount")
        private double LinedscAmount;
        @Expose
        @SerializedName("LineNo")
        private double LineNo;
        @Expose
        @SerializedName("LineManualDiscountPercentage")
        private double LineManualDiscountPercentage;
        @Expose
        @SerializedName("LineManualDiscountAmount")
        private double LineManualDiscountAmount;
        @Expose
        @SerializedName("LineDiscPercentage")
        private double LineDiscPercentage;
        @Expose
        @SerializedName("ItemName")
        private String ItemName;
        @Expose
        @SerializedName("ItemId")
        private String ItemId;
        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;
        @Expose
        @SerializedName("IsSubsitute")
        private boolean IsSubsitute;
        @Expose
        @SerializedName("IsPriceOverride")
        private boolean IsPriceOverride;
        @Expose
        @SerializedName("IsGeneric")
        private boolean IsGeneric;
        @Expose
        @SerializedName("IsChecked")
        private boolean IsChecked;
        @Expose
        @SerializedName("InventBatchId")
        private String InventBatchId;
        @Expose
        @SerializedName("ISStockAvailable")
        private boolean ISStockAvailable;
        @Expose
        @SerializedName("ISReserved")
        private boolean ISReserved;
        @Expose
        @SerializedName("ISPrescribed")
        private double ISPrescribed;
        @Expose
        @SerializedName("IGSTTaxCode")
        private String IGSTTaxCode;
        @Expose
        @SerializedName("IGSTPerc")
        private double IGSTPerc;
        @Expose
        @SerializedName("Hsncode_In")
        private String Hsncode_In;
        @Expose
        @SerializedName("Expiry")
        private String Expiry;
        @Expose
        @SerializedName("DiseaseType")
        private String DiseaseType;
        @Expose
        @SerializedName("DiscountType")
        private String DiscountType;
        @Expose
        @SerializedName("DiscountStructureType")
        private double DiscountStructureType;
        @Expose
        @SerializedName("DiscOfferId")
        private String DiscOfferId;
        @Expose
        @SerializedName("DiscId")
        private String DiscId;
        @Expose
        @SerializedName("DiscAmount")
        private double DiscAmount;
        @Expose
        @SerializedName("DPCO")
        private boolean DPCO;
        @Expose
        @SerializedName("Comment")
        private String Comment;
        @Expose
        @SerializedName("CategoryReference")
        private String CategoryReference;
        @Expose
        @SerializedName("CategoryCode")
        private String CategoryCode;
        @Expose
        @SerializedName("Category")
        private String Category;
        @Expose
        @SerializedName("CGSTTaxCode")
        private String CGSTTaxCode;
        @Expose
        @SerializedName("CGSTPerc")
        private double CGSTPerc;
        @Expose
        @SerializedName("CESSTaxCode")
        private String CESSTaxCode;
        @Expose
        @SerializedName("CESSPerc")
        private double CESSPerc;
        @Expose
        @SerializedName("BaseAmount")
        private double BaseAmount;
        @Expose
        @SerializedName("Barcode")
        private String Barcode;
        @Expose
        @SerializedName("ApplyDiscount")
        private boolean ApplyDiscount;
        @Expose
        @SerializedName("Additionaltax")
        private double Additionaltax;

        public String getVariantId() {
            return VariantId;
        }

        public double getUnitQty() {
            return UnitQty;
        }

        public double getUnitPrice() {
            return UnitPrice;
        }

        public String getUnit() {
            return Unit;
        }

        public double getTotalTax() {
            return TotalTax;
        }

        public double getTotalRoundedAmount() {
            return TotalRoundedAmount;
        }

        public double getTotalDiscPct() {
            return TotalDiscPct;
        }

        public double getTotalDiscAmount() {
            return TotalDiscAmount;
        }

        public double getTotal() {
            return Total;
        }

        public double getTaxAmount() {
            return TaxAmount;
        }

        public double getTax() {
            return Tax;
        }

        public String getSubstitudeItemId() {
            return SubstitudeItemId;
        }

        public String getSubClassification() {
            return SubClassification;
        }

        public String getSubCategoryCode() {
            return SubCategoryCode;
        }

        public String getSubCategory() {
            return SubCategory;
        }

        public double getStockQty() {
            return StockQty;
        }

        public String getScheduleCategoryCode() {
            return ScheduleCategoryCode;
        }

        public String getScheduleCategory() {
            return ScheduleCategory;
        }

        public String getSGSTTaxCode() {
            return SGSTTaxCode;
        }

        public double getSGSTPerc() {
            return SGSTPerc;
        }

        public double getReturnQty() {
            return ReturnQty;
        }

        public String getRetailSubCategoryRecID() {
            return RetailSubCategoryRecID;
        }

        public String getRetailMainCategoryRecID() {
            return RetailMainCategoryRecID;
        }

        public String getRetailCategoryRecID() {
            return RetailCategoryRecID;
        }

        public double getRemainingQty() {
            return RemainingQty;
        }

        public double getRemainderDays() {
            return RemainderDays;
        }

        public double getQty() {
            return Qty;
        }

        public String getProductRecID() {
            return ProductRecID;
        }

        public double getPrice() {
            return Price;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public double getPhysicalMRP() {
            return PhysicalMRP;
        }

        public String getPhysicalExpiry() {
            return PhysicalExpiry;
        }

        public String getPhysicalBatchID() {
            return PhysicalBatchID;
        }

        public double getPeriodicDiscAmount() {
            return PeriodicDiscAmount;
        }

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public double getOrderStatus() {
            return OrderStatus;
        }

        public double getOmsLineRECID() {
            return OmsLineRECID;
        }

        public double getOmsLineID() {
            return OmsLineID;
        }

        public double getOfferType() {
            return OfferType;
        }

        public double getOfferQty() {
            return OfferQty;
        }

        public double getOfferDiscountValue() {
            return OfferDiscountValue;
        }

        public double getOfferDiscountType() {
            return OfferDiscountType;
        }

        public double getOfferAmount() {
            return OfferAmount;
        }

        public double getNetAmountInclTax() {
            return NetAmountInclTax;
        }

        public double getNetAmount() {
            return NetAmount;
        }

        public String getModifyBatchId() {
            return ModifyBatchId;
        }

        public boolean getMixMode() {
            return MixMode;
        }

        public String getManufacturerName() {
            return ManufacturerName;
        }

        public String getManufacturerCode() {
            return ManufacturerCode;
        }

        public double getMRP() {
            return MRP;
        }

        public String getMMGroupId() {
            return MMGroupId;
        }

        public double getLinedscAmount() {
            return LinedscAmount;
        }

        public double getLineNo() {
            return LineNo;
        }

        public double getLineManualDiscountPercentage() {
            return LineManualDiscountPercentage;
        }

        public double getLineManualDiscountAmount() {
            return LineManualDiscountAmount;
        }

        public double getLineDiscPercentage() {
            return LineDiscPercentage;
        }

        public String getItemName() {
            return ItemName;
        }

        public String getItemId() {
            return ItemId;
        }

        public boolean getIsVoid() {
            return IsVoid;
        }

        public boolean getIsSubsitute() {
            return IsSubsitute;
        }

        public boolean getIsPriceOverride() {
            return IsPriceOverride;
        }

        public boolean getIsGeneric() {
            return IsGeneric;
        }

        public boolean getIsChecked() {
            return IsChecked;
        }

        public String getInventBatchId() {
            return InventBatchId;
        }

        public boolean getISStockAvailable() {
            return ISStockAvailable;
        }

        public boolean getISReserved() {
            return ISReserved;
        }

        public double getISPrescribed() {
            return ISPrescribed;
        }

        public String getIGSTTaxCode() {
            return IGSTTaxCode;
        }

        public double getIGSTPerc() {
            return IGSTPerc;
        }

        public String getHsncode_In() {
            return Hsncode_In;
        }

        public String getExpiry() {
            return Expiry;
        }

        public String getDiseaseType() {
            return DiseaseType;
        }

        public String getDiscountType() {
            return DiscountType;
        }

        public double getDiscountStructureType() {
            return DiscountStructureType;
        }

        public String getDiscOfferId() {
            return DiscOfferId;
        }

        public String getDiscId() {
            return DiscId;
        }

        public double getDiscAmount() {
            return DiscAmount;
        }

        public boolean getDPCO() {
            return DPCO;
        }

        public String getComment() {
            return Comment;
        }

        public String getCategoryReference() {
            return CategoryReference;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public String getCategory() {
            return Category;
        }

        public String getCGSTTaxCode() {
            return CGSTTaxCode;
        }

        public double getCGSTPerc() {
            return CGSTPerc;
        }

        public String getCESSTaxCode() {
            return CESSTaxCode;
        }

        public double getCESSPerc() {
            return CESSPerc;
        }

        public double getBaseAmount() {
            return BaseAmount;
        }

        public String getBarcode() {
            return Barcode;
        }

        public boolean getApplyDiscount() {
            return ApplyDiscount;
        }

        public double getAdditionaltax() {
            return Additionaltax;
        }
    }*/

    public static class OrderPrescriptionObj{
        @Expose
        @SerializedName("CATEGORYCODE")
        private String CATEGORYCODE;
        @Expose
        @SerializedName("PERSCRIPTIONURL")
        private String PERSCRIPTIONURL;
        @Expose
        @SerializedName("PRESCRIPTIONNO")
        private String PRESCRIPTIONNO;
        @Expose
        @SerializedName("Type")
        private String Type;

        public String getCATEGORYCODE() {
            return CATEGORYCODE;
        }

        public String getPERSCRIPTIONURL() {
            return PERSCRIPTIONURL;
        }

        public String getPRESCRIPTIONNO() {
            return PRESCRIPTIONNO;
        }

        public String getType() {
            return Type;
        }
    }
}
