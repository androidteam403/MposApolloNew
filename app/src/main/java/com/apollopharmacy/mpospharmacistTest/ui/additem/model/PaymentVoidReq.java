package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentVoidReq {

    @SerializedName("POSTransaction")
    private POSTransaction posTransaction;

    @SerializedName("Wallet")
    private Wallet wallet;

    public void setPosTransaction(POSTransaction posTransaction) {
        this.posTransaction = posTransaction;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public POSTransaction getPosTransaction() {
        return posTransaction;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public static class Wallet {

        @Expose
        @SerializedName("ReturnMessage")
        private String ReturnMessage;
        @Expose
        @SerializedName("RequestStatus")
        private int RequestStatus;
        @Expose
        @SerializedName("RewardsPoint")
        private int RewardsPoint;
        @Expose
        @SerializedName("WalletTransactionID")
        private String WalletTransactionID;
        @Expose
        @SerializedName("WalletOrderID")
        private String WalletOrderID;
        @Expose
        @SerializedName("WalletRefundId")
        private String WalletRefundId;
        @Expose
        @SerializedName("WalletAmount")
        private double WalletAmount;
        @Expose
        @SerializedName("POSTerminal")
        private String POSTerminal;
        @Expose
        @SerializedName("POSTransactionID")
        private String POSTransactionID;
        @Expose
        @SerializedName("Response")
        private String Response;
        @Expose
        @SerializedName("RequestURL")
        private String RequestURL;
        @Expose
        @SerializedName("OTPTransactionId")
        private String OTPTransactionId;
        @Expose
        @SerializedName("OTP")
        private String OTP;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("WalletURL")
        private String WalletURL;
        @Expose
        @SerializedName("WalletRequestType")
        private int WalletRequestType;
        @Expose
        @SerializedName("WalletType")
        private int WalletType;
        @Expose
        @SerializedName("UHID")
        private String UHID;

        public void setUHID(String UHID) {
            this.UHID = UHID;
        }

        public void setReturnMessage(String ReturnMessage) {
            this.ReturnMessage = ReturnMessage;
        }

        public void setRequestStatus(int RequestStatus) {
            this.RequestStatus = RequestStatus;
        }

        public void setRewardsPoint(int RewardsPoint) {
            this.RewardsPoint = RewardsPoint;
        }

        public void setWalletTransactionID(String WalletTransactionID) {
            this.WalletTransactionID = WalletTransactionID;
        }

        public void setWalletOrderID(String WalletOrderID) {
            this.WalletOrderID = WalletOrderID;
        }

        public void setWalletRefundId(String WalletRefundId) {
            this.WalletRefundId = WalletRefundId;
        }

        public void setWalletAmount(double WalletAmount) {
            this.WalletAmount = WalletAmount;
        }

        public void setPOSTerminal(String POSTerminal) {
            this.POSTerminal = POSTerminal;
        }

        public void setPOSTransactionID(String POSTransactionID) {
            this.POSTransactionID = POSTransactionID;
        }

        public void setResponse(String Response) {
            this.Response = Response;
        }

        public void setRequestURL(String RequestURL) {
            this.RequestURL = RequestURL;
        }

        public void setOTPTransactionId(String OTPTransactionId) {
            this.OTPTransactionId = OTPTransactionId;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public void setWalletURL(String WalletURL) {
            this.WalletURL = WalletURL;
        }

        public void setWalletRequestType(int WalletRequestType) {
            this.WalletRequestType = WalletRequestType;
        }

        public void setWalletType(int WalletType) {
            this.WalletType = WalletType;
        }
    }


    public static class POSTransaction implements Serializable {
        @SerializedName("AllowedTenderType")
        private String AllowedTenderType;
        @SerializedName("AmounttoAccount")
        private int AmounttoAccount;
        @SerializedName("ApprovedID")
        private String ApprovedID;
        @SerializedName("AWBNo")
        private String AWBNo;
        @SerializedName("BatchTerminalid")
        private String BatchTerminalid;
        @SerializedName("BillingCity")
        private String BillingCity;
        @SerializedName("BusinessDate")
        private String BusinessDate;
        @SerializedName("CancelReasonCode")
        private String CancelReasonCode;
        @SerializedName("Channel")
        private String Channel;
        @SerializedName("Comment")
        private String Comment;
        @SerializedName("CorpCode")
        private String CorpCode;
        @SerializedName("CouponCode")
        private String CouponCode;
        @SerializedName("CreatedDateTime")
        private String CreatedDateTime;
        @SerializedName("CreatedonPosTerminal")
        private String CreatedonPosTerminal;
        @SerializedName("Currency")
        private String Currency;
        @SerializedName("CurrentSalesLine")
        private int CurrentSalesLine;
        @SerializedName("CustAccount")
        private String CustAccount;
        @SerializedName("CustAddress")
        private String CustAddress;
        @SerializedName("CustDiscamount")
        private int CustDiscamount;
        @SerializedName("CustomerID")
        private String CustomerID;
        @SerializedName("CustomerName")
        private String CustomerName;
        @SerializedName("CustomerState")
        private String CustomerState;
        @SerializedName("DataAreaId")
        private String DataAreaId;
        @SerializedName("DeliveryDate")
        private String DeliveryDate;
        @SerializedName("DiscAmount")
        private int DiscAmount;
        @SerializedName("DiscountRef")
        private String DiscountRef;
        @SerializedName("DOB")
        private String DOB;
        @SerializedName("DoctorCode")
        private String DoctorCode;
        @SerializedName("DoctorName")
        private String DoctorName;
        @SerializedName("DSPCode")
        private String DSPCode;
        @SerializedName("EntryStatus")
        private int EntryStatus;
        @SerializedName("ExpiryDays")
        private int ExpiryDays;
        @SerializedName("Gender")
        private int Gender;
        @SerializedName("GrossAmount")
        private double GrossAmount;
        @SerializedName("IPNO")
        private String IPNO;
        @SerializedName("IPSerialNO")
        private String IPSerialNO;
        @SerializedName("ISAdvancePayment")
        private boolean ISAdvancePayment;
        @SerializedName("ISBatchModifiedAllowed")
        private boolean ISBatchModifiedAllowed;
        @SerializedName("ISHBPStore")
        private boolean ISHBPStore;
        @SerializedName("ISHyperDelivered")
        private boolean ISHyperDelivered;
        @SerializedName("ISHyperLocalDelivery")
        private boolean ISHyperLocalDelivery;
        @SerializedName("IsManualBill")
        private boolean IsManualBill;
        @SerializedName("ISOMSOrder")
        private boolean ISOMSOrder;
        @SerializedName("ISOMSValidate")
        private boolean ISOMSValidate;
        @SerializedName("ISOnlineOrder")
        private boolean ISOnlineOrder;
        @SerializedName("ISPosted")
        private boolean ISPosted;
        @SerializedName("ISPrescibeDiscount")
        private boolean ISPrescibeDiscount;
        @SerializedName("ISReserved")
        private boolean ISReserved;
        @SerializedName("IsReturn")
        private boolean IsReturn;
        @SerializedName("ISReturnAllowed")
        private boolean ISReturnAllowed;
        @SerializedName("IsStockCheck")
        private boolean IsStockCheck;
        @SerializedName("IsVoid")
        private boolean IsVoid;
        @SerializedName("MobileNO")
        private String MobileNO;
        @SerializedName("NetAmount")
        private double NetAmount;
        @SerializedName("NetAmountInclTax")
        private double NetAmountInclTax;
        @SerializedName("NumberofItemLines")
        private int NumberofItemLines;
        @SerializedName("NumberofItems")
        private int NumberofItems;
        @SerializedName("OMSCreditAmount")
        private int OMSCreditAmount;
        @SerializedName("OrderPrescriptionURL")
        private String OrderPrescriptionURL;
        @SerializedName("OrderSource")
        private String OrderSource;
        @SerializedName("OrderType")
        private String OrderType;
        @SerializedName("PatientID")
        private String PatientID;
        @SerializedName("PaymentSource")
        private String PaymentSource;
        @SerializedName("Pincode")
        private String Pincode;
        @SerializedName("PosEvent")
        private int PosEvent;
        @SerializedName("ReciptId")
        private String ReciptId;
        @SerializedName("REFNO")
        private String REFNO;
        @SerializedName("RegionCode")
        private String RegionCode;
        @SerializedName("Remainingamount")
        private double Remainingamount;
        @SerializedName("ReminderDays")
        private int ReminderDays;
        @SerializedName("RequestStatus")
        private int RequestStatus;
        @SerializedName("ReturnMessage")
        private String ReturnMessage;
        @SerializedName("ReturnReceiptId")
        private String ReturnReceiptId;
        @SerializedName("ReturnStore")
        private String ReturnStore;
        @SerializedName("ReturnTerminal")
        private String ReturnTerminal;
        @SerializedName("ReturnTransactionId")
        private String ReturnTransactionId;
        @SerializedName("ReturnType")
        private int ReturnType;
        @SerializedName("RoundedAmount")
        private int RoundedAmount;
        @SerializedName("SalesLine")
        private List<SalesLine> salesLines;

        public List<SalesLine> getSalesLines() {
            return salesLines;
        }

        public List<TenderLine> getTenderLines() {
            return tenderLines;
        }

        public static class SalesLine {
            @SerializedName("Additionaltax")
            private int Additionaltax;
            @SerializedName("ApplyDiscount")
            private boolean ApplyDiscount;
            @SerializedName("Barcode")
            private String Barcode;
            @SerializedName("BaseAmount")
            private int BaseAmount;
            @SerializedName("Category")
            private String Category;
            @SerializedName("CategoryCode")
            private String CategoryCode;
            @SerializedName("CategoryReference")
            private String CategoryReference;
            @SerializedName("CESSPerc")
            private int CESSPerc;
            @SerializedName("CESSTaxCode")
            private String CESSTaxCode;
            @SerializedName("CGSTPerc")
            private int CGSTPerc;
            @SerializedName("CGSTTaxCode")
            private String CGSTTaxCode;
            @SerializedName("Comment")
            private String Comment;
            @SerializedName("DiscAmount")
            private int DiscAmount;
            @SerializedName("DiscId")
            private String DiscId;
            @SerializedName("DiscOfferId")
            private String DiscOfferId;
            @SerializedName("DiscountStructureType")
            private int DiscountStructureType;
            @SerializedName("DiscountType")
            private String DiscountType;
            @SerializedName("DiseaseType")
            private String DiseaseType;
            @SerializedName("DPCO")
            private boolean DPCO;
            @SerializedName("Expiry")
            private String Expiry;
            @SerializedName("Hsncode_In")
            private String Hsncode_In;
            @SerializedName("IGSTPerc")
            private int IGSTPerc;
            @SerializedName("IGSTTaxCode")
            private String IGSTTaxCode;
            @SerializedName("InventBatchId")
            private String InventBatchId;
            @SerializedName("IsChecked")
            private boolean IsChecked;
            @SerializedName("IsGeneric")
            private boolean IsGeneric;
            @SerializedName("ISPrescribed")
            private int ISPrescribed;
            @SerializedName("IsPriceOverride")
            private boolean IsPriceOverride;
            @SerializedName("ISReserved")
            private boolean ISReserved;
            @SerializedName("ISStockAvailable")
            private boolean ISStockAvailable;
            @SerializedName("IsSubsitute")
            private boolean IsSubsitute;
            @SerializedName("IsVoid")
            private boolean IsVoid;
            @SerializedName("ItemId")
            private String ItemId;
            @SerializedName("ItemName")
            private String ItemName;
            @SerializedName("LineDiscPercentage")
            private int LineDiscPercentage;
            @SerializedName("LinedscAmount")
            private int LinedscAmount;
            @SerializedName("LineManualDiscountAmount")
            private int LineManualDiscountAmount;
            @SerializedName("LineManualDiscountPercentage")
            private int LineManualDiscountPercentage;
            @SerializedName("LineNo")
            private int LineNo;
            @SerializedName("ManufacturerCode")
            private String ManufacturerCode;
            @SerializedName("ManufacturerName")
            private String ManufacturerName;
            @SerializedName("MixMode")
            private boolean MixMode;
            @SerializedName("MMGroupId")
            private String MMGroupId;
            @SerializedName("ModifyBatchId")
            private String ModifyBatchId;
            @SerializedName("MRP")
            private int MRP;
            @SerializedName("NetAmount")
            private double NetAmount;
            @SerializedName("NetAmountInclTax")
            private int NetAmountInclTax;
            @SerializedName("OfferAmount")
            private int OfferAmount;
            @SerializedName("OfferDiscountType")
            private int OfferDiscountType;
            @SerializedName("OfferDiscountValue")
            private int OfferDiscountValue;
            @SerializedName("OfferQty")
            private int OfferQty;
            @SerializedName("OfferType")
            private int OfferType;
            @SerializedName("OmsLineID")
            private int OmsLineID;
            @SerializedName("OmsLineRECID")
            private int OmsLineRECID;
            @SerializedName("OrderStatus")
            private int OrderStatus;
            @SerializedName("OriginalPrice")
            private int OriginalPrice;
            @SerializedName("PeriodicDiscAmount")
            private int PeriodicDiscAmount;
            @SerializedName("PhysicalBatchID")
            private String PhysicalBatchID;
            @SerializedName("PhysicalExpiry")
            private String PhysicalExpiry;
            @SerializedName("PhysicalMRP")
            private int PhysicalMRP;
            @SerializedName("PreviewText")
            private String PreviewText;
            @SerializedName("Price")
            private int Price;
            @SerializedName("ProductRecID")
            private String ProductRecID;
            @SerializedName("Qty")
            private double Qty;
            @SerializedName("RemainderDays")
            private int RemainderDays;
            @SerializedName("RemainingQty")
            private int RemainingQty;
            @SerializedName("RetailCategoryRecID")
            private String RetailCategoryRecID;
            @SerializedName("RetailMainCategoryRecID")
            private String RetailMainCategoryRecID;
            @SerializedName("RetailSubCategoryRecID")
            private String RetailSubCategoryRecID;
            @SerializedName("ReturnQty")
            private int ReturnQty;
            @SerializedName("ScheduleCategory")
            private String ScheduleCategory;
            @SerializedName("ScheduleCategoryCode")
            private String ScheduleCategoryCode;
            @SerializedName("SGSTPerc")
            private int SGSTPerc;
            @SerializedName("SGSTTaxCode")
            private String SGSTTaxCode;
            @SerializedName("StockQty")
            private int StockQty;
            @SerializedName("SubCategory")
            private String SubCategory;
            @SerializedName("SubCategoryCode")
            private String SubCategoryCode;
            @SerializedName("SubClassification")
            private String SubClassification;
            @SerializedName("SubstitudeItemId")
            private String SubstitudeItemId;
            @SerializedName("Tax")
            private int Tax;
            @SerializedName("TaxAmount")
            private double TaxAmount;
            @SerializedName("Total")
            private int Total;
            @SerializedName("TotalDiscAmount")
            private int TotalDiscAmount;
            @SerializedName("TotalDiscPct")
            private int TotalDiscPct;
            @SerializedName("TotalRoundedAmount")
            private int TotalRoundedAmount;
            @SerializedName("TotalTax")
            private int TotalTax;
            @SerializedName("Unit")
            private String Unit;
            @SerializedName("UnitPrice")
            private int UnitPrice;
            @SerializedName("UnitQty")
            private int UnitQty;
            @SerializedName("VariantId")
            private String VariantId;

            public void setAdditionaltax(int additionaltax) {
                Additionaltax = additionaltax;
            }

            public void setApplyDiscount(boolean applyDiscount) {
                ApplyDiscount = applyDiscount;
            }

            public void setBarcode(String barcode) {
                Barcode = barcode;
            }

            public void setBaseAmount(int baseAmount) {
                BaseAmount = baseAmount;
            }

            public void setCategory(String category) {
                Category = category;
            }

            public void setCategoryCode(String categoryCode) {
                CategoryCode = categoryCode;
            }

            public void setCategoryReference(String categoryReference) {
                CategoryReference = categoryReference;
            }

            public void setCESSPerc(int CESSPerc) {
                this.CESSPerc = CESSPerc;
            }

            public void setCESSTaxCode(String CESSTaxCode) {
                this.CESSTaxCode = CESSTaxCode;
            }

            public void setCGSTPerc(int CGSTPerc) {
                this.CGSTPerc = CGSTPerc;
            }

            public void setCGSTTaxCode(String CGSTTaxCode) {
                this.CGSTTaxCode = CGSTTaxCode;
            }

            public void setComment(String comment) {
                Comment = comment;
            }

            public void setDiscAmount(int discAmount) {
                DiscAmount = discAmount;
            }

            public void setDiscId(String discId) {
                DiscId = discId;
            }

            public void setDiscOfferId(String discOfferId) {
                DiscOfferId = discOfferId;
            }

            public void setDiscountStructureType(int discountStructureType) {
                DiscountStructureType = discountStructureType;
            }

            public void setDiscountType(String discountType) {
                DiscountType = discountType;
            }

            public void setDiseaseType(String diseaseType) {
                DiseaseType = diseaseType;
            }

            public void setDPCO(boolean DPCO) {
                this.DPCO = DPCO;
            }

            public void setExpiry(String expiry) {
                Expiry = expiry;
            }

            public void setHsncode_In(String hsncode_In) {
                Hsncode_In = hsncode_In;
            }

            public void setIGSTPerc(int IGSTPerc) {
                this.IGSTPerc = IGSTPerc;
            }

            public void setIGSTTaxCode(String IGSTTaxCode) {
                this.IGSTTaxCode = IGSTTaxCode;
            }

            public void setInventBatchId(String inventBatchId) {
                InventBatchId = inventBatchId;
            }

            public void setChecked(boolean checked) {
                IsChecked = checked;
            }

            public void setGeneric(boolean generic) {
                IsGeneric = generic;
            }

            public void setISPrescribed(int ISPrescribed) {
                this.ISPrescribed = ISPrescribed;
            }

            public void setPriceOverride(boolean priceOverride) {
                IsPriceOverride = priceOverride;
            }

            public void setISReserved(boolean ISReserved) {
                this.ISReserved = ISReserved;
            }

            public void setISStockAvailable(boolean ISStockAvailable) {
                this.ISStockAvailable = ISStockAvailable;
            }

            public void setSubsitute(boolean subsitute) {
                IsSubsitute = subsitute;
            }

            public void setVoid(boolean aVoid) {
                IsVoid = aVoid;
            }

            public void setItemId(String itemId) {
                ItemId = itemId;
            }

            public void setItemName(String itemName) {
                ItemName = itemName;
            }

            public void setLineDiscPercentage(int lineDiscPercentage) {
                LineDiscPercentage = lineDiscPercentage;
            }

            public void setLinedscAmount(int linedscAmount) {
                LinedscAmount = linedscAmount;
            }

            public void setLineManualDiscountAmount(int lineManualDiscountAmount) {
                LineManualDiscountAmount = lineManualDiscountAmount;
            }

            public void setLineManualDiscountPercentage(int lineManualDiscountPercentage) {
                LineManualDiscountPercentage = lineManualDiscountPercentage;
            }

            public void setLineNo(int lineNo) {
                LineNo = lineNo;
            }

            public void setManufacturerCode(String manufacturerCode) {
                ManufacturerCode = manufacturerCode;
            }

            public void setManufacturerName(String manufacturerName) {
                ManufacturerName = manufacturerName;
            }

            public void setMixMode(boolean mixMode) {
                MixMode = mixMode;
            }

            public void setMMGroupId(String MMGroupId) {
                this.MMGroupId = MMGroupId;
            }

            public void setModifyBatchId(String modifyBatchId) {
                ModifyBatchId = modifyBatchId;
            }

            public void setMRP(int MRP) {
                this.MRP = MRP;
            }

            public void setNetAmount(double netAmount) {
                NetAmount = netAmount;
            }

            public void setNetAmountInclTax(int netAmountInclTax) {
                NetAmountInclTax = netAmountInclTax;
            }

            public void setOfferAmount(int offerAmount) {
                OfferAmount = offerAmount;
            }

            public void setOfferDiscountType(int offerDiscountType) {
                OfferDiscountType = offerDiscountType;
            }

            public void setOfferDiscountValue(int offerDiscountValue) {
                OfferDiscountValue = offerDiscountValue;
            }

            public void setOfferQty(int offerQty) {
                OfferQty = offerQty;
            }

            public void setOfferType(int offerType) {
                OfferType = offerType;
            }

            public void setOmsLineID(int omsLineID) {
                OmsLineID = omsLineID;
            }

            public void setOmsLineRECID(int omsLineRECID) {
                OmsLineRECID = omsLineRECID;
            }

            public void setOrderStatus(int orderStatus) {
                OrderStatus = orderStatus;
            }

            public void setOriginalPrice(int originalPrice) {
                OriginalPrice = originalPrice;
            }

            public void setPeriodicDiscAmount(int periodicDiscAmount) {
                PeriodicDiscAmount = periodicDiscAmount;
            }

            public void setPhysicalBatchID(String physicalBatchID) {
                PhysicalBatchID = physicalBatchID;
            }

            public void setPhysicalExpiry(String physicalExpiry) {
                PhysicalExpiry = physicalExpiry;
            }

            public void setPhysicalMRP(int physicalMRP) {
                PhysicalMRP = physicalMRP;
            }

            public void setPreviewText(String previewText) {
                PreviewText = previewText;
            }

            public void setPrice(int price) {
                Price = price;
            }

            public void setProductRecID(String productRecID) {
                ProductRecID = productRecID;
            }

            public void setQty(double qty) {
                Qty = qty;
            }

            public void setRemainderDays(int remainderDays) {
                RemainderDays = remainderDays;
            }

            public void setRemainingQty(int remainingQty) {
                RemainingQty = remainingQty;
            }

            public void setRetailCategoryRecID(String retailCategoryRecID) {
                RetailCategoryRecID = retailCategoryRecID;
            }

            public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
                RetailMainCategoryRecID = retailMainCategoryRecID;
            }

            public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
                RetailSubCategoryRecID = retailSubCategoryRecID;
            }

            public void setReturnQty(int returnQty) {
                ReturnQty = returnQty;
            }

            public void setScheduleCategory(String scheduleCategory) {
                ScheduleCategory = scheduleCategory;
            }

            public void setScheduleCategoryCode(String scheduleCategoryCode) {
                ScheduleCategoryCode = scheduleCategoryCode;
            }

            public void setSGSTPerc(int SGSTPerc) {
                this.SGSTPerc = SGSTPerc;
            }

            public void setSGSTTaxCode(String SGSTTaxCode) {
                this.SGSTTaxCode = SGSTTaxCode;
            }

            public void setStockQty(int stockQty) {
                StockQty = stockQty;
            }

            public void setSubCategory(String subCategory) {
                SubCategory = subCategory;
            }

            public void setSubCategoryCode(String subCategoryCode) {
                SubCategoryCode = subCategoryCode;
            }

            public void setSubClassification(String subClassification) {
                SubClassification = subClassification;
            }

            public void setSubstitudeItemId(String substitudeItemId) {
                SubstitudeItemId = substitudeItemId;
            }

            public void setTax(int tax) {
                Tax = tax;
            }

            public void setTaxAmount(double taxAmount) {
                TaxAmount = taxAmount;
            }

            public void setTotal(int total) {
                Total = total;
            }

            public void setTotalDiscAmount(int totalDiscAmount) {
                TotalDiscAmount = totalDiscAmount;
            }

            public void setTotalDiscPct(int totalDiscPct) {
                TotalDiscPct = totalDiscPct;
            }

            public void setTotalRoundedAmount(int totalRoundedAmount) {
                TotalRoundedAmount = totalRoundedAmount;
            }

            public void setTotalTax(int totalTax) {
                TotalTax = totalTax;
            }

            public void setUnit(String unit) {
                Unit = unit;
            }

            public void setUnitPrice(int unitPrice) {
                UnitPrice = unitPrice;
            }

            public void setUnitQty(int unitQty) {
                UnitQty = unitQty;
            }

            public void setVariantId(String variantId) {
                VariantId = variantId;
            }
        }

        @SerializedName("SalesOrigin")
        private String SalesOrigin;
        @SerializedName("SEZ")
        private int SEZ;
        @SerializedName("ShippingMethod")
        private String ShippingMethod;
        @SerializedName("ShippingMethodDesc")
        private String ShippingMethodDesc;
        @SerializedName("Staff")
        private String Staff;
        @SerializedName("State")
        private String State;
        @SerializedName("Store")
        private String Store;
        @SerializedName("StoreName")
        private String StoreName;
        @SerializedName("TenderLine")
        private List<TenderLine> tenderLines;

        public static class TenderLine {
            @SerializedName("AmountCur")
            private int AmountCur;
            @SerializedName("AmountMst")
            private int AmountMst;
            @SerializedName("AmountTendered")
            private int AmountTendered;
            @SerializedName("BarCode")
            private String BarCode;
            @SerializedName("ExchRate")
            private int ExchRate;
            @SerializedName("ExchRateMst")
            private int ExchRateMst;
            @SerializedName("IsVoid")
            private boolean IsVoid;
            @SerializedName("LineNo")
            private int LineNo;
            @SerializedName("MobileNo")
            private String MobileNo;
            @SerializedName("PreviewText")
            private String PreviewText;
            @SerializedName("RewardsPoint")
            private int RewardsPoint;
            @SerializedName("TenderId")
            private String TenderId;
            @SerializedName("TenderName")
            private String TenderName;
            @SerializedName("TenderType")
            private int TenderType;
            @SerializedName("WalletOrderId")
            private String WalletOrderId;
            @SerializedName("WalletTransactionID")
            private String WalletTransactionID;
            @SerializedName("WalletType")
            private int WalletType;

            public void setAmountCur(int amountCur) {
                AmountCur = amountCur;
            }

            public void setAmountMst(int amountMst) {
                AmountMst = amountMst;
            }

            public void setAmountTendered(int amountTendered) {
                AmountTendered = amountTendered;
            }

            public void setBarCode(String barCode) {
                BarCode = barCode;
            }

            public void setExchRate(int exchRate) {
                ExchRate = exchRate;
            }

            public void setExchRateMst(int exchRateMst) {
                ExchRateMst = exchRateMst;
            }

            public void setVoid(boolean aVoid) {
                IsVoid = aVoid;
            }

            public void setLineNo(int lineNo) {
                LineNo = lineNo;
            }

            public void setMobileNo(String mobileNo) {
                MobileNo = mobileNo;
            }

            public void setPreviewText(String previewText) {
                PreviewText = previewText;
            }

            public void setRewardsPoint(int rewardsPoint) {
                RewardsPoint = rewardsPoint;
            }

            public void setTenderId(String tenderId) {
                TenderId = tenderId;
            }

            public void setTenderName(String tenderName) {
                TenderName = tenderName;
            }

            public void setTenderType(int tenderType) {
                TenderType = tenderType;
            }

            public void setWalletOrderId(String walletOrderId) {
                WalletOrderId = walletOrderId;
            }

            public void setWalletTransactionID(String walletTransactionID) {
                WalletTransactionID = walletTransactionID;
            }

            public void setWalletType(int walletType) {
                WalletType = walletType;
            }
        }

        @SerializedName("Terminal")
        private String Terminal;
        @SerializedName("TimewhenTransClosed")
        private int TimewhenTransClosed;
        @SerializedName("TotalDiscAmount")
        private int TotalDiscAmount;
        @SerializedName("TotalManualDiscountAmount")
        private int TotalManualDiscountAmount;
        @SerializedName("TotalManualDiscountPercentage")
        private int TotalManualDiscountPercentage;
        @SerializedName("TotalMRP")
        private double TotalMRP;
        @SerializedName("TotalTaxAmount")
        private double TotalTaxAmount;
        @SerializedName("TrackingRef")
        private String TrackingRef;
        @SerializedName("TransactionId")
        private String TransactionId;
        @SerializedName("TransDate")
        private String TransDate;
        @SerializedName("TransType")
        private int TransType;
        @SerializedName("Type")
        private int Type;
        @SerializedName("VendorCode")
        private String VendorCode;
        @SerializedName("VendorId")
        private String VendorId;

        public void setAllowedTenderType(String allowedTenderType) {
            AllowedTenderType = allowedTenderType;
        }

        public void setAmounttoAccount(int amounttoAccount) {
            AmounttoAccount = amounttoAccount;
        }

        public void setApprovedID(String approvedID) {
            ApprovedID = approvedID;
        }

        public void setAWBNo(String AWBNo) {
            this.AWBNo = AWBNo;
        }

        public void setBatchTerminalid(String batchTerminalid) {
            BatchTerminalid = batchTerminalid;
        }

        public void setBillingCity(String billingCity) {
            BillingCity = billingCity;
        }

        public void setBusinessDate(String businessDate) {
            BusinessDate = businessDate;
        }

        public void setCancelReasonCode(String cancelReasonCode) {
            CancelReasonCode = cancelReasonCode;
        }

        public void setChannel(String channel) {
            Channel = channel;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public void setCorpCode(String corpCode) {
            CorpCode = corpCode;
        }

        public void setCouponCode(String couponCode) {
            CouponCode = couponCode;
        }

        public void setCreatedDateTime(String createdDateTime) {
            CreatedDateTime = createdDateTime;
        }

        public void setCreatedonPosTerminal(String createdonPosTerminal) {
            CreatedonPosTerminal = createdonPosTerminal;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public void setCurrentSalesLine(int currentSalesLine) {
            CurrentSalesLine = currentSalesLine;
        }

        public void setCustAccount(String custAccount) {
            CustAccount = custAccount;
        }

        public void setCustAddress(String custAddress) {
            CustAddress = custAddress;
        }

        public void setCustDiscamount(int custDiscamount) {
            CustDiscamount = custDiscamount;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public void setCustomerState(String customerState) {
            CustomerState = customerState;
        }

        public void setDataAreaId(String dataAreaId) {
            DataAreaId = dataAreaId;
        }

        public void setDeliveryDate(String deliveryDate) {
            DeliveryDate = deliveryDate;
        }

        public void setDiscAmount(int discAmount) {
            DiscAmount = discAmount;
        }

        public void setDiscountRef(String discountRef) {
            DiscountRef = discountRef;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public void setDoctorCode(String doctorCode) {
            DoctorCode = doctorCode;
        }

        public void setDoctorName(String doctorName) {
            DoctorName = doctorName;
        }

        public void setDSPCode(String DSPCode) {
            this.DSPCode = DSPCode;
        }

        public void setEntryStatus(int entryStatus) {
            EntryStatus = entryStatus;
        }

        public void setExpiryDays(int expiryDays) {
            ExpiryDays = expiryDays;
        }

        public void setGender(int gender) {
            Gender = gender;
        }

        public void setGrossAmount(double grossAmount) {
            GrossAmount = grossAmount;
        }

        public void setIPNO(String IPNO) {
            this.IPNO = IPNO;
        }

        public void setIPSerialNO(String IPSerialNO) {
            this.IPSerialNO = IPSerialNO;
        }

        public void setISAdvancePayment(boolean ISAdvancePayment) {
            this.ISAdvancePayment = ISAdvancePayment;
        }

        public void setISBatchModifiedAllowed(boolean ISBatchModifiedAllowed) {
            this.ISBatchModifiedAllowed = ISBatchModifiedAllowed;
        }

        public void setISHBPStore(boolean ISHBPStore) {
            this.ISHBPStore = ISHBPStore;
        }

        public void setISHyperDelivered(boolean ISHyperDelivered) {
            this.ISHyperDelivered = ISHyperDelivered;
        }

        public void setISHyperLocalDelivery(boolean ISHyperLocalDelivery) {
            this.ISHyperLocalDelivery = ISHyperLocalDelivery;
        }

        public void setManualBill(boolean manualBill) {
            IsManualBill = manualBill;
        }

        public void setISOMSOrder(boolean ISOMSOrder) {
            this.ISOMSOrder = ISOMSOrder;
        }

        public void setISOMSValidate(boolean ISOMSValidate) {
            this.ISOMSValidate = ISOMSValidate;
        }

        public void setISOnlineOrder(boolean ISOnlineOrder) {
            this.ISOnlineOrder = ISOnlineOrder;
        }

        public void setISPosted(boolean ISPosted) {
            this.ISPosted = ISPosted;
        }

        public void setISPrescibeDiscount(boolean ISPrescibeDiscount) {
            this.ISPrescibeDiscount = ISPrescibeDiscount;
        }

        public void setISReserved(boolean ISReserved) {
            this.ISReserved = ISReserved;
        }

        public void setReturn(boolean aReturn) {
            IsReturn = aReturn;
        }

        public void setISReturnAllowed(boolean ISReturnAllowed) {
            this.ISReturnAllowed = ISReturnAllowed;
        }

        public void setStockCheck(boolean stockCheck) {
            IsStockCheck = stockCheck;
        }

        public void setVoid(boolean aVoid) {
            IsVoid = aVoid;
        }

        public void setMobileNO(String mobileNO) {
            MobileNO = mobileNO;
        }

        public void setNetAmount(double netAmount) {
            NetAmount = netAmount;
        }

        public void setNetAmountInclTax(double netAmountInclTax) {
            NetAmountInclTax = netAmountInclTax;
        }

        public void setNumberofItemLines(int numberofItemLines) {
            NumberofItemLines = numberofItemLines;
        }

        public void setNumberofItems(int numberofItems) {
            NumberofItems = numberofItems;
        }

        public void setOMSCreditAmount(int OMSCreditAmount) {
            this.OMSCreditAmount = OMSCreditAmount;
        }

        public void setOrderPrescriptionURL(String orderPrescriptionURL) {
            OrderPrescriptionURL = orderPrescriptionURL;
        }

        public void setOrderSource(String orderSource) {
            OrderSource = orderSource;
        }

        public void setOrderType(String orderType) {
            OrderType = orderType;
        }

        public void setPatientID(String patientID) {
            PatientID = patientID;
        }

        public void setPaymentSource(String paymentSource) {
            PaymentSource = paymentSource;
        }

        public void setPincode(String pincode) {
            Pincode = pincode;
        }

        public void setPosEvent(int posEvent) {
            PosEvent = posEvent;
        }

        public void setReciptId(String reciptId) {
            ReciptId = reciptId;
        }

        public void setREFNO(String REFNO) {
            this.REFNO = REFNO;
        }

        public void setRegionCode(String regionCode) {
            RegionCode = regionCode;
        }

        public void setRemainingamount(double remainingamount) {
            Remainingamount = remainingamount;
        }

        public void setReminderDays(int reminderDays) {
            ReminderDays = reminderDays;
        }

        public void setRequestStatus(int requestStatus) {
            RequestStatus = requestStatus;
        }

        public void setReturnMessage(String returnMessage) {
            ReturnMessage = returnMessage;
        }

        public void setReturnReceiptId(String returnReceiptId) {
            ReturnReceiptId = returnReceiptId;
        }

        public void setReturnStore(String returnStore) {
            ReturnStore = returnStore;
        }

        public void setReturnTerminal(String returnTerminal) {
            ReturnTerminal = returnTerminal;
        }

        public void setReturnTransactionId(String returnTransactionId) {
            ReturnTransactionId = returnTransactionId;
        }

        public void setReturnType(int returnType) {
            ReturnType = returnType;
        }

        public void setRoundedAmount(int roundedAmount) {
            RoundedAmount = roundedAmount;
        }

        public void setSalesLines(List<SalesLine> salesLines) {
            this.salesLines = salesLines;
        }

        public void setSalesOrigin(String salesOrigin) {
            SalesOrigin = salesOrigin;
        }

        public void setSEZ(int SEZ) {
            this.SEZ = SEZ;
        }

        public void setShippingMethod(String shippingMethod) {
            ShippingMethod = shippingMethod;
        }

        public void setShippingMethodDesc(String shippingMethodDesc) {
            ShippingMethodDesc = shippingMethodDesc;
        }

        public void setStaff(String staff) {
            Staff = staff;
        }

        public void setState(String state) {
            State = state;
        }

        public void setStore(String store) {
            Store = store;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }

        public void setTenderLines(List<TenderLine> tenderLines) {
            this.tenderLines = tenderLines;
        }

        public void setTerminal(String terminal) {
            Terminal = terminal;
        }

        public void setTimewhenTransClosed(int timewhenTransClosed) {
            TimewhenTransClosed = timewhenTransClosed;
        }

        public void setTotalDiscAmount(int totalDiscAmount) {
            TotalDiscAmount = totalDiscAmount;
        }

        public void setTotalManualDiscountAmount(int totalManualDiscountAmount) {
            TotalManualDiscountAmount = totalManualDiscountAmount;
        }

        public void setTotalManualDiscountPercentage(int totalManualDiscountPercentage) {
            TotalManualDiscountPercentage = totalManualDiscountPercentage;
        }

        public void setTotalMRP(double totalMRP) {
            TotalMRP = totalMRP;
        }

        public void setTotalTaxAmount(double totalTaxAmount) {
            TotalTaxAmount = totalTaxAmount;
        }

        public void setTrackingRef(String trackingRef) {
            TrackingRef = trackingRef;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public void setTransDate(String transDate) {
            TransDate = transDate;
        }

        public void setTransType(int transType) {
            TransType = transType;
        }

        public void setType(int type) {
            Type = type;
        }

        public void setVendorCode(String vendorCode) {
            VendorCode = vendorCode;
        }

        public void setVendorId(String vendorId) {
            VendorId = vendorId;
        }
    }
}