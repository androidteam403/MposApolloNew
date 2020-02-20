package com.apollopharmacy.mpospharmacist.ui.pay.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateTenderLineRes {


    @Expose
    @SerializedName("GenerateTenderLineResult")
    private GenerateTenderLineResultEntity GenerateTenderLineResult;

    public GenerateTenderLineResultEntity getGenerateTenderLineResult() {
        return GenerateTenderLineResult;
    }

    public static class GenerateTenderLineResultEntity {
        @Expose
        @SerializedName("VendorId")
        private String VendorId;
        @Expose
        @SerializedName("Type")
        private int Type;
        @Expose
        @SerializedName("TransactionId")
        private String TransactionId;
        @Expose
        @SerializedName("TransType")
        private int TransType;
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
        private int TotalManualDiscountPercentage;
        @Expose
        @SerializedName("TotalManualDiscountAmount")
        private int TotalManualDiscountAmount;
        @Expose
        @SerializedName("TotalMRP")
        private int TotalMRP;
        @Expose
        @SerializedName("TotalDiscAmount")
        private int TotalDiscAmount;
        @Expose
        @SerializedName("TimewhenTransClosed")
        private int TimewhenTransClosed;
        @Expose
        @SerializedName("Terminal")
        private String Terminal;
        @Expose
        @SerializedName("TenderLine")
        private List<TenderLineEntity> TenderLine;
        @Expose
        @SerializedName("StoreName")
        private String StoreName;
        @Expose
        @SerializedName("Store")
        private String Store;
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
        @SerializedName("SalesOrigin")
        private String SalesOrigin;
        @Expose
        @SerializedName("SalesLine")
        private List<SalesLineEntity> SalesLine;
        @Expose
        @SerializedName("SEZ")
        private int SEZ;
        @Expose
        @SerializedName("RoundedAmount")
        private int RoundedAmount;
        @Expose
        @SerializedName("ReturnType")
        private int ReturnType;
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
        private int RequestStatus;
        @Expose
        @SerializedName("ReminderDays")
        private int ReminderDays;
        @Expose
        @SerializedName("Remainingamount")
        private int Remainingamount;
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
        private int PosEvent;
        @Expose
        @SerializedName("Pincode")
        private String Pincode;
        @Expose
        @SerializedName("PaymentSource")
        private String PaymentSource;
        @Expose
        @SerializedName("OrderType")
        private String OrderType;
        @Expose
        @SerializedName("OrderSource")
        private String OrderSource;
        @Expose
        @SerializedName("NumberofItems")
        private int NumberofItems;
        @Expose
        @SerializedName("NumberofItemLines")
        private int NumberofItemLines;
        @Expose
        @SerializedName("NetAmountInclTax")
        private int NetAmountInclTax;
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
        @SerializedName("ISOMSOrder")
        private boolean ISOMSOrder;
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
        @SerializedName("GrossAmount")
        private int GrossAmount;
        @Expose
        @SerializedName("Gender")
        private int Gender;
        @Expose
        @SerializedName("EntryStatus")
        private int EntryStatus;
        @Expose
        @SerializedName("DoctorName")
        private String DoctorName;
        @Expose
        @SerializedName("DoctorCode")
        private String DoctorCode;
        @Expose
        @SerializedName("DiscAmount")
        private int DiscAmount;
        @Expose
        @SerializedName("DeliveryDate")
        private String DeliveryDate;
        @Expose
        @SerializedName("DataAreaId")
        private String DataAreaId;
        @Expose
        @SerializedName("DOB")
        private String DOB;
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
        private int CustDiscamount;
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
        @SerializedName("AmounttoAccount")
        private int AmounttoAccount;

        public String getVendorId() {
            return VendorId;
        }

        public int getType() {
            return Type;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public int getTransType() {
            return TransType;
        }

        public String getTransDate() {
            return TransDate;
        }

        public String getTrackingRef() {
            return TrackingRef;
        }

        public double getTotalTaxAmount() {
            return TotalTaxAmount;
        }

        public int getTotalManualDiscountPercentage() {
            return TotalManualDiscountPercentage;
        }

        public int getTotalManualDiscountAmount() {
            return TotalManualDiscountAmount;
        }

        public int getTotalMRP() {
            return TotalMRP;
        }

        public int getTotalDiscAmount() {
            return TotalDiscAmount;
        }

        public int getTimewhenTransClosed() {
            return TimewhenTransClosed;
        }

        public String getTerminal() {
            return Terminal;
        }

        public List<TenderLineEntity> getTenderLine() {
            return TenderLine;
        }

        public String getStoreName() {
            return StoreName;
        }

        public String getStore() {
            return Store;
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

        public String getSalesOrigin() {
            return SalesOrigin;
        }

        public List<SalesLineEntity> getSalesLine() {
            return SalesLine;
        }

        public int getSEZ() {
            return SEZ;
        }

        public int getRoundedAmount() {
            return RoundedAmount;
        }

        public int getReturnType() {
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

        public int getRequestStatus() {
            return RequestStatus;
        }

        public int getReminderDays() {
            return ReminderDays;
        }

        public int getRemainingamount() {
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

        public int getPosEvent() {
            return PosEvent;
        }

        public String getPincode() {
            return Pincode;
        }

        public String getPaymentSource() {
            return PaymentSource;
        }

        public String getOrderType() {
            return OrderType;
        }

        public String getOrderSource() {
            return OrderSource;
        }

        public int getNumberofItems() {
            return NumberofItems;
        }

        public int getNumberofItemLines() {
            return NumberofItemLines;
        }

        public int getNetAmountInclTax() {
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

        public boolean getISOMSOrder() {
            return ISOMSOrder;
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

        public int getGrossAmount() {
            return GrossAmount;
        }

        public int getGender() {
            return Gender;
        }

        public int getEntryStatus() {
            return EntryStatus;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public String getDoctorCode() {
            return DoctorCode;
        }

        public int getDiscAmount() {
            return DiscAmount;
        }

        public String getDeliveryDate() {
            return DeliveryDate;
        }

        public String getDataAreaId() {
            return DataAreaId;
        }

        public String getDOB() {
            return DOB;
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

        public int getCustDiscamount() {
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

        public int getAmounttoAccount() {
            return AmounttoAccount;
        }
    }

    public static class TenderLineEntity {
        @Expose
        @SerializedName("WalletType")
        private int WalletType;
        @Expose
        @SerializedName("WalletTransactionID")
        private String WalletTransactionID;
        @Expose
        @SerializedName("WalletOrderId")
        private String WalletOrderId;
        @Expose
        @SerializedName("TenderType")
        private int TenderType;
        @Expose
        @SerializedName("TenderName")
        private String TenderName;
        @Expose
        @SerializedName("TenderId")
        private String TenderId;
        @Expose
        @SerializedName("RewardsPoint")
        private int RewardsPoint;
        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("LineNo")
        private int LineNo;
        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;
        @Expose
        @SerializedName("ExchRateMst")
        private int ExchRateMst;
        @Expose
        @SerializedName("ExchRate")
        private int ExchRate;
        @Expose
        @SerializedName("BarCode")
        private String BarCode;
        @Expose
        @SerializedName("AmountTendered")
        private int AmountTendered;
        @Expose
        @SerializedName("AmountMst")
        private int AmountMst;
        @Expose
        @SerializedName("AmountCur")
        private int AmountCur;

        public int getWalletType() {
            return WalletType;
        }

        public String getWalletTransactionID() {
            return WalletTransactionID;
        }

        public String getWalletOrderId() {
            return WalletOrderId;
        }

        public int getTenderType() {
            return TenderType;
        }

        public String getTenderName() {
            return TenderName;
        }

        public String getTenderId() {
            return TenderId;
        }

        public int getRewardsPoint() {
            return RewardsPoint;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public int getLineNo() {
            return LineNo;
        }

        public boolean getIsVoid() {
            return IsVoid;
        }

        public int getExchRateMst() {
            return ExchRateMst;
        }

        public int getExchRate() {
            return ExchRate;
        }

        public String getBarCode() {
            return BarCode;
        }

        public int getAmountTendered() {
            return AmountTendered;
        }

        public int getAmountMst() {
            return AmountMst;
        }

        public int getAmountCur() {
            return AmountCur;
        }
    }

    public static class SalesLineEntity {
        @Expose
        @SerializedName("VariantId")
        private String VariantId;
        @Expose
        @SerializedName("UnitQty")
        private int UnitQty;
        @Expose
        @SerializedName("UnitPrice")
        private int UnitPrice;
        @Expose
        @SerializedName("Unit")
        private String Unit;
        @Expose
        @SerializedName("TotalTax")
        private int TotalTax;
        @Expose
        @SerializedName("TotalRoundedAmount")
        private int TotalRoundedAmount;
        @Expose
        @SerializedName("TotalDiscPct")
        private int TotalDiscPct;
        @Expose
        @SerializedName("TotalDiscAmount")
        private int TotalDiscAmount;
        @Expose
        @SerializedName("Total")
        private int Total;
        @Expose
        @SerializedName("TaxAmount")
        private double TaxAmount;
        @Expose
        @SerializedName("Tax")
        private int Tax;
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
        private int StockQty;
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
        private int SGSTPerc;
        @Expose
        @SerializedName("ReturnQty")
        private int ReturnQty;
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
        private int RemainingQty;
        @Expose
        @SerializedName("RemainderDays")
        private int RemainderDays;
        @Expose
        @SerializedName("Qty")
        private int Qty;
        @Expose
        @SerializedName("ProductRecID")
        private String ProductRecID;
        @Expose
        @SerializedName("Price")
        private int Price;
        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;
        @Expose
        @SerializedName("PeriodicDiscAmount")
        private int PeriodicDiscAmount;
        @Expose
        @SerializedName("OriginalPrice")
        private int OriginalPrice;
        @Expose
        @SerializedName("OrderStatus")
        private int OrderStatus;
        @Expose
        @SerializedName("OmsLineRECID")
        private int OmsLineRECID;
        @Expose
        @SerializedName("OmsLineID")
        private int OmsLineID;
        @Expose
        @SerializedName("OfferType")
        private int OfferType;
        @Expose
        @SerializedName("OfferQty")
        private int OfferQty;
        @Expose
        @SerializedName("OfferDiscountValue")
        private int OfferDiscountValue;
        @Expose
        @SerializedName("OfferDiscountType")
        private int OfferDiscountType;
        @Expose
        @SerializedName("OfferAmount")
        private int OfferAmount;
        @Expose
        @SerializedName("NetAmountInclTax")
        private int NetAmountInclTax;
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
        private int MRP;
        @Expose
        @SerializedName("MMGroupId")
        private String MMGroupId;
        @Expose
        @SerializedName("LinedscAmount")
        private int LinedscAmount;
        @Expose
        @SerializedName("LineNo")
        private int LineNo;
        @Expose
        @SerializedName("LineManualDiscountPercentage")
        private int LineManualDiscountPercentage;
        @Expose
        @SerializedName("LineManualDiscountAmount")
        private int LineManualDiscountAmount;
        @Expose
        @SerializedName("LineDiscPercentage")
        private int LineDiscPercentage;
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
        private int ISPrescribed;
        @Expose
        @SerializedName("IGSTTaxCode")
        private String IGSTTaxCode;
        @Expose
        @SerializedName("IGSTPerc")
        private int IGSTPerc;
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
        private int DiscountStructureType;
        @Expose
        @SerializedName("DiscOfferId")
        private String DiscOfferId;
        @Expose
        @SerializedName("DiscId")
        private String DiscId;
        @Expose
        @SerializedName("DiscAmount")
        private int DiscAmount;
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
        private int CGSTPerc;
        @Expose
        @SerializedName("CESSTaxCode")
        private String CESSTaxCode;
        @Expose
        @SerializedName("CESSPerc")
        private int CESSPerc;
        @Expose
        @SerializedName("BaseAmount")
        private int BaseAmount;
        @Expose
        @SerializedName("Barcode")
        private String Barcode;
        @Expose
        @SerializedName("ApplyDiscount")
        private boolean ApplyDiscount;
        @Expose
        @SerializedName("Additionaltax")
        private int Additionaltax;

        public String getVariantId() {
            return VariantId;
        }

        public int getUnitQty() {
            return UnitQty;
        }

        public int getUnitPrice() {
            return UnitPrice;
        }

        public String getUnit() {
            return Unit;
        }

        public int getTotalTax() {
            return TotalTax;
        }

        public int getTotalRoundedAmount() {
            return TotalRoundedAmount;
        }

        public int getTotalDiscPct() {
            return TotalDiscPct;
        }

        public int getTotalDiscAmount() {
            return TotalDiscAmount;
        }

        public int getTotal() {
            return Total;
        }

        public double getTaxAmount() {
            return TaxAmount;
        }

        public int getTax() {
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

        public int getStockQty() {
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

        public int getSGSTPerc() {
            return SGSTPerc;
        }

        public int getReturnQty() {
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

        public int getRemainingQty() {
            return RemainingQty;
        }

        public int getRemainderDays() {
            return RemainderDays;
        }

        public int getQty() {
            return Qty;
        }

        public String getProductRecID() {
            return ProductRecID;
        }

        public int getPrice() {
            return Price;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public int getPeriodicDiscAmount() {
            return PeriodicDiscAmount;
        }

        public int getOriginalPrice() {
            return OriginalPrice;
        }

        public int getOrderStatus() {
            return OrderStatus;
        }

        public int getOmsLineRECID() {
            return OmsLineRECID;
        }

        public int getOmsLineID() {
            return OmsLineID;
        }

        public int getOfferType() {
            return OfferType;
        }

        public int getOfferQty() {
            return OfferQty;
        }

        public int getOfferDiscountValue() {
            return OfferDiscountValue;
        }

        public int getOfferDiscountType() {
            return OfferDiscountType;
        }

        public int getOfferAmount() {
            return OfferAmount;
        }

        public int getNetAmountInclTax() {
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

        public int getMRP() {
            return MRP;
        }

        public String getMMGroupId() {
            return MMGroupId;
        }

        public int getLinedscAmount() {
            return LinedscAmount;
        }

        public int getLineNo() {
            return LineNo;
        }

        public int getLineManualDiscountPercentage() {
            return LineManualDiscountPercentage;
        }

        public int getLineManualDiscountAmount() {
            return LineManualDiscountAmount;
        }

        public int getLineDiscPercentage() {
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

        public int getISPrescribed() {
            return ISPrescribed;
        }

        public String getIGSTTaxCode() {
            return IGSTTaxCode;
        }

        public int getIGSTPerc() {
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

        public int getDiscountStructureType() {
            return DiscountStructureType;
        }

        public String getDiscOfferId() {
            return DiscOfferId;
        }

        public String getDiscId() {
            return DiscId;
        }

        public int getDiscAmount() {
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

        public int getCGSTPerc() {
            return CGSTPerc;
        }

        public String getCESSTaxCode() {
            return CESSTaxCode;
        }

        public int getCESSPerc() {
            return CESSPerc;
        }

        public int getBaseAmount() {
            return BaseAmount;
        }

        public String getBarcode() {
            return Barcode;
        }

        public boolean getApplyDiscount() {
            return ApplyDiscount;
        }

        public int getAdditionaltax() {
            return Additionaltax;
        }
    }
}
