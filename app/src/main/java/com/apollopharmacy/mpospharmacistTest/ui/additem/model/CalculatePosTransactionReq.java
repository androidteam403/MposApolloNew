package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalculatePosTransactionReq {


    @Expose
    @SerializedName("TenderLine")
    private List<TenderLineEntity> TenderLine;
    @Expose
    @SerializedName("SalesLine")
    private List<SalesLineEntity> SalesLine;
    @Expose
    @SerializedName("ISOMSOrder")
    private boolean ISOMSOrder;
    @Expose
    @SerializedName("ReminderDays")
    private int ReminderDays;
    @Expose
    @SerializedName("AmounttoAccount")
    private int AmounttoAccount;
    @Expose
    @SerializedName("ISAdvancePayment")
    private boolean ISAdvancePayment;
    @Expose
    @SerializedName("CouponCode")
    private String CouponCode;
    @Expose
    @SerializedName("SEZ")
    private int SEZ;
    @Expose
    @SerializedName("ISPosted")
    private boolean ISPosted;
    @Expose
    @SerializedName("IsStockCheck")
    private boolean IsStockCheck;
    @Expose
    @SerializedName("TransType")
    private int TransType;
    @Expose
    @SerializedName("PosEvent")
    private int PosEvent;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("CurrentSalesLine")
    private int CurrentSalesLine;
    @Expose
    @SerializedName("ReturnType")
    private int ReturnType;
    @Expose
    @SerializedName("IsManualBill")
    private boolean IsManualBill;
    @Expose
    @SerializedName("ISReturnAllowed")
    private boolean ISReturnAllowed;
    @Expose
    @SerializedName("ISBatchModifiedAllowed")
    private boolean ISBatchModifiedAllowed;
    @Expose
    @SerializedName("IsReturn")
    private boolean IsReturn;
    @Expose
    @SerializedName("IsVoid")
    private boolean IsVoid;
    @Expose
    @SerializedName("DataAreaId")
    private String DataAreaId;
    @Expose
    @SerializedName("Type")
    private int Type;
    @Expose
    @SerializedName("TransDate")
    private String TransDate;
    @Expose
    @SerializedName("TransactionId")
    private String TransactionId;
    @Expose
    @SerializedName("TotalTaxAmount")
    private int TotalTaxAmount;
    @Expose
    @SerializedName("TotalMRP")
    private int TotalMRP;
    @Expose
    @SerializedName("TotalManualDiscountPercentage")
    private int TotalManualDiscountPercentage;
    @Expose
    @SerializedName("TotalManualDiscountAmount")
    private int TotalManualDiscountAmount;
    @Expose
    @SerializedName("TotalDiscAmount")
    private int TotalDiscAmount;
    @Expose
    @SerializedName("TimewhenTransClosed")
    private int TimewhenTransClosed;
    @Expose
    @SerializedName("ReturnReceiptId")
    private String ReturnReceiptId;
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
    @SerializedName("Terminal")
    private String Terminal;
    @Expose
    @SerializedName("State")
    private String State;
    @Expose
    @SerializedName("Store")
    private String Store;
    @Expose
    @SerializedName("Staff")
    private String Staff;
    @Expose
    @SerializedName("RoundedAmount")
    private int RoundedAmount;
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
    private int NetAmount;
    @Expose
    @SerializedName("GrossAmount")
    private int GrossAmount;
    @Expose
    @SerializedName("EntryStatus")
    private int EntryStatus;
    @Expose
    @SerializedName("DiscAmount")
    private int DiscAmount;
    @Expose
    @SerializedName("CustDiscamount")
    private int CustDiscamount;
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
    @SerializedName("Comment")
    private String Comment;
    @Expose
    @SerializedName("Channel")
    private String Channel;
    @Expose
    @SerializedName("BusinessDate")
    private String BusinessDate;
    @Expose
    @SerializedName("BatchTerminalid")
    private String BatchTerminalid;
    @Expose
    @SerializedName("ReciptId")
    private String ReciptId;
    @Expose
    @SerializedName("IPSerialNO")
    private String IPSerialNO;
    @Expose
    @SerializedName("IPNO")
    private String IPNO;
    @Expose
    @SerializedName("REFNO")
    private String REFNO;
    @Expose
    @SerializedName("TrackingRef")
    private String TrackingRef;
    @Expose
    @SerializedName("SalesOrigin")
    private String SalesOrigin;
    @Expose
    @SerializedName("DoctorCode")
    private String DoctorCode;
    @Expose
    @SerializedName("DoctorName")
    private String DoctorName;
    @Expose
    @SerializedName("Pincode")
    private String Pincode;
    @Expose
    @SerializedName("Gender")
    private int Gender;
    @Expose
    @SerializedName("CustomerState")
    private String CustomerState;
    @Expose
    @SerializedName("CustAddress")
    private String CustAddress;
    @Expose
    @SerializedName("CustomerName")
    private String CustomerName;
    @Expose
    @SerializedName("DOB")
    private String DOB;
    @Expose
    @SerializedName("MobileNO")
    private String MobileNO;
    @Expose
    @SerializedName("CorpCode")
    private String CorpCode;
    @Expose
    @SerializedName("CustomerID")
    private String CustomerID;
    @Expose
    @SerializedName("RegionCode")
    private String RegionCode;
    @Expose
    @SerializedName("StoreName")
    private String StoreName;
    @Expose
    @SerializedName("CancelReasonCode")
    private String CancelReasonCode;
    @Expose
    @SerializedName("ISPrescibeDiscount")
    private boolean ISPrescibeDiscount;
    @Expose
    @SerializedName("PaymentSource")
    private String PaymentSource;
    @Expose
    @SerializedName("VendorId")
    private String VendorId;
    @Expose
    @SerializedName("BillingCity")
    private String BillingCity;
    @Expose
    @SerializedName("ShippingMethod")
    private String ShippingMethod;
    @Expose
    @SerializedName("OrderSource")
    private String OrderSource;
    @Expose
    @SerializedName("OrderType")
    private String OrderType;
    @Expose
    @SerializedName("DeliveryDate")
    private String DeliveryDate;
    @Expose
    @SerializedName("ISReserved")
    private boolean ISReserved;
    @Expose
    @SerializedName("Remainingamount")
    private int Remainingamount;

    public void setTenderLine(List<TenderLineEntity> TenderLine) {
        this.TenderLine = TenderLine;
    }

    public void setSalesLine(List<SalesLineEntity> SalesLine) {
        this.SalesLine = SalesLine;
    }

    public void setISOMSOrder(boolean ISOMSOrder) {
        this.ISOMSOrder = ISOMSOrder;
    }

    public void setReminderDays(int ReminderDays) {
        this.ReminderDays = ReminderDays;
    }

    public void setAmounttoAccount(int AmounttoAccount) {
        this.AmounttoAccount = AmounttoAccount;
    }

    public void setISAdvancePayment(boolean ISAdvancePayment) {
        this.ISAdvancePayment = ISAdvancePayment;
    }

    public void setCouponCode(String CouponCode) {
        this.CouponCode = CouponCode;
    }

    public void setSEZ(int SEZ) {
        this.SEZ = SEZ;
    }

    public void setISPosted(boolean ISPosted) {
        this.ISPosted = ISPosted;
    }

    public void setIsStockCheck(boolean IsStockCheck) {
        this.IsStockCheck = IsStockCheck;
    }

    public void setTransType(int TransType) {
        this.TransType = TransType;
    }

    public void setPosEvent(int PosEvent) {
        this.PosEvent = PosEvent;
    }

    public void setReturnMessage(String ReturnMessage) {
        this.ReturnMessage = ReturnMessage;
    }

    public void setRequestStatus(int RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public void setCurrentSalesLine(int CurrentSalesLine) {
        this.CurrentSalesLine = CurrentSalesLine;
    }

    public void setReturnType(int ReturnType) {
        this.ReturnType = ReturnType;
    }

    public void setIsManualBill(boolean IsManualBill) {
        this.IsManualBill = IsManualBill;
    }

    public void setISReturnAllowed(boolean ISReturnAllowed) {
        this.ISReturnAllowed = ISReturnAllowed;
    }

    public void setISBatchModifiedAllowed(boolean ISBatchModifiedAllowed) {
        this.ISBatchModifiedAllowed = ISBatchModifiedAllowed;
    }

    public void setIsReturn(boolean IsReturn) {
        this.IsReturn = IsReturn;
    }

    public void setIsVoid(boolean IsVoid) {
        this.IsVoid = IsVoid;
    }

    public void setDataAreaId(String DataAreaId) {
        this.DataAreaId = DataAreaId;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public void setTransDate(String TransDate) {
        this.TransDate = TransDate;
    }

    public void setTransactionId(String TransactionId) {
        this.TransactionId = TransactionId;
    }

    public void setTotalTaxAmount(int TotalTaxAmount) {
        this.TotalTaxAmount = TotalTaxAmount;
    }

    public void setTotalMRP(int TotalMRP) {
        this.TotalMRP = TotalMRP;
    }

    public void setTotalManualDiscountPercentage(int TotalManualDiscountPercentage) {
        this.TotalManualDiscountPercentage = TotalManualDiscountPercentage;
    }

    public void setTotalManualDiscountAmount(int TotalManualDiscountAmount) {
        this.TotalManualDiscountAmount = TotalManualDiscountAmount;
    }

    public void setTotalDiscAmount(int TotalDiscAmount) {
        this.TotalDiscAmount = TotalDiscAmount;
    }

    public void setTimewhenTransClosed(int TimewhenTransClosed) {
        this.TimewhenTransClosed = TimewhenTransClosed;
    }

    public void setReturnReceiptId(String ReturnReceiptId) {
        this.ReturnReceiptId = ReturnReceiptId;
    }

    public void setReturnTransactionId(String ReturnTransactionId) {
        this.ReturnTransactionId = ReturnTransactionId;
    }

    public void setReturnTerminal(String ReturnTerminal) {
        this.ReturnTerminal = ReturnTerminal;
    }

    public void setReturnStore(String ReturnStore) {
        this.ReturnStore = ReturnStore;
    }

    public void setTerminal(String Terminal) {
        this.Terminal = Terminal;
    }

    public void setState(String State) {
        this.State = State;
    }

    public void setStore(String Store) {
        this.Store = Store;
    }

    public void setStaff(String Staff) {
        this.Staff = Staff;
    }

    public void setRoundedAmount(int RoundedAmount) {
        this.RoundedAmount = RoundedAmount;
    }

    public void setNumberofItems(int NumberofItems) {
        this.NumberofItems = NumberofItems;
    }

    public void setNumberofItemLines(int NumberofItemLines) {
        this.NumberofItemLines = NumberofItemLines;
    }

    public void setNetAmountInclTax(int NetAmountInclTax) {
        this.NetAmountInclTax = NetAmountInclTax;
    }

    public void setNetAmount(int NetAmount) {
        this.NetAmount = NetAmount;
    }

    public void setGrossAmount(int GrossAmount) {
        this.GrossAmount = GrossAmount;
    }

    public void setEntryStatus(int EntryStatus) {
        this.EntryStatus = EntryStatus;
    }

    public void setDiscAmount(int DiscAmount) {
        this.DiscAmount = DiscAmount;
    }

    public void setCustDiscamount(int CustDiscamount) {
        this.CustDiscamount = CustDiscamount;
    }

    public void setCustAccount(String CustAccount) {
        this.CustAccount = CustAccount;
    }

    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    public void setCreatedonPosTerminal(String CreatedonPosTerminal) {
        this.CreatedonPosTerminal = CreatedonPosTerminal;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public void setChannel(String Channel) {
        this.Channel = Channel;
    }

    public void setBusinessDate(String BusinessDate) {
        this.BusinessDate = BusinessDate;
    }

    public void setBatchTerminalid(String BatchTerminalid) {
        this.BatchTerminalid = BatchTerminalid;
    }

    public void setReciptId(String ReciptId) {
        this.ReciptId = ReciptId;
    }

    public void setIPSerialNO(String IPSerialNO) {
        this.IPSerialNO = IPSerialNO;
    }

    public void setIPNO(String IPNO) {
        this.IPNO = IPNO;
    }

    public void setREFNO(String REFNO) {
        this.REFNO = REFNO;
    }

    public void setTrackingRef(String TrackingRef) {
        this.TrackingRef = TrackingRef;
    }

    public void setSalesOrigin(String SalesOrigin) {
        this.SalesOrigin = SalesOrigin;
    }

    public void setDoctorCode(String DoctorCode) {
        this.DoctorCode = DoctorCode;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public void setPincode(String Pincode) {
        this.Pincode = Pincode;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public void setCustomerState(String CustomerState) {
        this.CustomerState = CustomerState;
    }

    public void setCustAddress(String CustAddress) {
        this.CustAddress = CustAddress;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setMobileNO(String MobileNO) {
        this.MobileNO = MobileNO;
    }

    public void setCorpCode(String CorpCode) {
        this.CorpCode = CorpCode;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public void setRegionCode(String RegionCode) {
        this.RegionCode = RegionCode;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public void setCancelReasonCode(String CancelReasonCode) {
        this.CancelReasonCode = CancelReasonCode;
    }

    public void setISPrescibeDiscount(boolean ISPrescibeDiscount) {
        this.ISPrescibeDiscount = ISPrescibeDiscount;
    }

    public void setPaymentSource(String PaymentSource) {
        this.PaymentSource = PaymentSource;
    }

    public void setVendorId(String VendorId) {
        this.VendorId = VendorId;
    }

    public void setBillingCity(String BillingCity) {
        this.BillingCity = BillingCity;
    }

    public void setShippingMethod(String ShippingMethod) {
        this.ShippingMethod = ShippingMethod;
    }

    public void setOrderSource(String OrderSource) {
        this.OrderSource = OrderSource;
    }

    public void setOrderType(String OrderType) {
        this.OrderType = OrderType;
    }

    public void setDeliveryDate(String DeliveryDate) {
        this.DeliveryDate = DeliveryDate;
    }

    public void setISReserved(boolean ISReserved) {
        this.ISReserved = ISReserved;
    }

    public void setRemainingamount(int Remainingamount) {
        this.Remainingamount = Remainingamount;
    }

    public static class TenderLineEntity {
        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;
        @Expose
        @SerializedName("BarCode")
        private String BarCode;
        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;
        @Expose
        @SerializedName("RewardsPoint")
        private int RewardsPoint;
        @Expose
        @SerializedName("WalletTransactionID")
        private String WalletTransactionID;
        @Expose
        @SerializedName("WalletOrderId")
        private String WalletOrderId;
        @Expose
        @SerializedName("WalletType")
        private int WalletType;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("AmountMst")
        private int AmountMst;
        @Expose
        @SerializedName("AmountCur")
        private int AmountCur;
        @Expose
        @SerializedName("AmountTendered")
        private int AmountTendered;
        @Expose
        @SerializedName("ExchRateMst")
        private int ExchRateMst;
        @Expose
        @SerializedName("ExchRate")
        private int ExchRate;
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
        @SerializedName("LineNo")
        private int LineNo;

        public void setIsVoid(boolean IsVoid) {
            this.IsVoid = IsVoid;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }

        public void setPreviewText(String PreviewText) {
            this.PreviewText = PreviewText;
        }

        public void setRewardsPoint(int RewardsPoint) {
            this.RewardsPoint = RewardsPoint;
        }

        public void setWalletTransactionID(String WalletTransactionID) {
            this.WalletTransactionID = WalletTransactionID;
        }

        public void setWalletOrderId(String WalletOrderId) {
            this.WalletOrderId = WalletOrderId;
        }

        public void setWalletType(int WalletType) {
            this.WalletType = WalletType;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public void setAmountMst(int AmountMst) {
            this.AmountMst = AmountMst;
        }

        public void setAmountCur(int AmountCur) {
            this.AmountCur = AmountCur;
        }

        public void setAmountTendered(int AmountTendered) {
            this.AmountTendered = AmountTendered;
        }

        public void setExchRateMst(int ExchRateMst) {
            this.ExchRateMst = ExchRateMst;
        }

        public void setExchRate(int ExchRate) {
            this.ExchRate = ExchRate;
        }

        public void setTenderType(int TenderType) {
            this.TenderType = TenderType;
        }

        public void setTenderName(String TenderName) {
            this.TenderName = TenderName;
        }

        public void setTenderId(String TenderId) {
            this.TenderId = TenderId;
        }

        public void setLineNo(int LineNo) {
            this.LineNo = LineNo;
        }
    }

    public static class SalesLineEntity {
        @Expose
        @SerializedName("ISStockAvailable")
        private boolean ISStockAvailable;
        @Expose
        @SerializedName("ISReserved")
        private boolean ISReserved;
        @Expose
        @SerializedName("OmsLineRECID")
        private int OmsLineRECID;
        @Expose
        @SerializedName("IsSubsitute")
        private boolean IsSubsitute;
        @Expose
        @SerializedName("OmsLineID")
        private int OmsLineID;
        @Expose
        @SerializedName("OrderStatus")
        private int OrderStatus;
        @Expose
        @SerializedName("CategoryReference")
        private String CategoryReference;
        @Expose
        @SerializedName("SubstitudeItemId")
        private String SubstitudeItemId;
        @Expose
        @SerializedName("DiscountStructureType")
        private int DiscountStructureType;
        @Expose
        @SerializedName("SGSTTaxCode")
        private String SGSTTaxCode;
        @Expose
        @SerializedName("CGSTTaxCode")
        private String CGSTTaxCode;
        @Expose
        @SerializedName("CESSTaxCode")
        private String CESSTaxCode;
        @Expose
        @SerializedName("IGSTTaxCode")
        private String IGSTTaxCode;
        @Expose
        @SerializedName("TotalTax")
        private int TotalTax;
        @Expose
        @SerializedName("SGSTPerc")
        private int SGSTPerc;
        @Expose
        @SerializedName("CGSTPerc")
        private int CGSTPerc;
        @Expose
        @SerializedName("CESSPerc")
        private int CESSPerc;
        @Expose
        @SerializedName("IGSTPerc")
        private int IGSTPerc;
        @Expose
        @SerializedName("ApplyDiscount")
        private boolean ApplyDiscount;
        @Expose
        @SerializedName("LineDiscPercentage")
        private int LineDiscPercentage;
        @Expose
        @SerializedName("OfferType")
        private int OfferType;
        @Expose
        @SerializedName("DiscId")
        private String DiscId;
        @Expose
        @SerializedName("MMGroupId")
        private String MMGroupId;
        @Expose
        @SerializedName("MixMode")
        private boolean MixMode;
        @Expose
        @SerializedName("DiscountType")
        private String DiscountType;
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
        @SerializedName("OfferQty")
        private int OfferQty;
        @Expose
        @SerializedName("SubClassification")
        private String SubClassification;
        @Expose
        @SerializedName("DiseaseType")
        private String DiseaseType;
        @Expose
        @SerializedName("ModifyBatchId")
        private String ModifyBatchId;
        @Expose
        @SerializedName("ProductRecID")
        private String ProductRecID;
        @Expose
        @SerializedName("DPCO")
        private boolean DPCO;
        @Expose
        @SerializedName("RetailMainCategoryRecID")
        private String RetailMainCategoryRecID;
        @Expose
        @SerializedName("RetailSubCategoryRecID")
        private String RetailSubCategoryRecID;
        @Expose
        @SerializedName("RetailCategoryRecID")
        private String RetailCategoryRecID;
        @Expose
        @SerializedName("IsChecked")
        private boolean IsChecked;
        @Expose
        @SerializedName("IsPriceOverride")
        private boolean IsPriceOverride;
        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;
        @Expose
        @SerializedName("RemainderDays")
        private int RemainderDays;
        @Expose
        @SerializedName("ISPrescribed")
        private int ISPrescribed;
        @Expose
        @SerializedName("Total")
        private int Total;
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
        @SerializedName("TotalRoundedAmount")
        private int TotalRoundedAmount;
        @Expose
        @SerializedName("TotalDiscPct")
        private int TotalDiscPct;
        @Expose
        @SerializedName("TotalDiscAmount")
        private int TotalDiscAmount;
        @Expose
        @SerializedName("BaseAmount")
        private int BaseAmount;
        @Expose
        @SerializedName("TaxAmount")
        private double TaxAmount;
        @Expose
        @SerializedName("Price")
        private int Price;
        @Expose
        @SerializedName("PeriodicDiscAmount")
        private int PeriodicDiscAmount;
        @Expose
        @SerializedName("OriginalPrice")
        private int OriginalPrice;
        @Expose
        @SerializedName("NetAmountInclTax")
        private int NetAmountInclTax;
        @Expose
        @SerializedName("NetAmount")
        private double NetAmount;
        @Expose
        @SerializedName("LineManualDiscountPercentage")
        private int LineManualDiscountPercentage;
        @Expose
        @SerializedName("LineManualDiscountAmount")
        private int LineManualDiscountAmount;
        @Expose
        @SerializedName("LinedscAmount")
        private int LinedscAmount;
        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;
        @Expose
        @SerializedName("InventBatchId")
        private String InventBatchId;
        @Expose
        @SerializedName("Hsncode_In")
        private String Hsncode_In;
        @Expose
        @SerializedName("DiscOfferId")
        private String DiscOfferId;
        @Expose
        @SerializedName("DiscAmount")
        private int DiscAmount;
        @Expose
        @SerializedName("Comment")
        private String Comment;
        @Expose
        @SerializedName("Barcode")
        private String Barcode;
        @Expose
        @SerializedName("Additionaltax")
        private int Additionaltax;
        @Expose
        @SerializedName("Tax")
        private int Tax;
        @Expose
        @SerializedName("MRP")
        private int MRP;
        @Expose
        @SerializedName("RemainingQty")
        private int RemainingQty;
        @Expose
        @SerializedName("ReturnQty")
        private int ReturnQty;
        @Expose
        @SerializedName("StockQty")
        private int StockQty;
        @Expose
        @SerializedName("Qty")
        private int Qty;
        @Expose
        @SerializedName("Expiry")
        private String Expiry;
        @Expose
        @SerializedName("ManufacturerName")
        private String ManufacturerName;
        @Expose
        @SerializedName("ManufacturerCode")
        private String ManufacturerCode;
        @Expose
        @SerializedName("ScheduleCategoryCode")
        private String ScheduleCategoryCode;
        @Expose
        @SerializedName("ScheduleCategory")
        private String ScheduleCategory;
        @Expose
        @SerializedName("SubCategoryCode")
        private String SubCategoryCode;
        @Expose
        @SerializedName("SubCategory")
        private String SubCategory;
        @Expose
        @SerializedName("CategoryCode")
        private String CategoryCode;
        @Expose
        @SerializedName("Category")
        private String Category;
        @Expose
        @SerializedName("ItemName")
        private String ItemName;
        @Expose
        @SerializedName("ItemId")
        private String ItemId;
        @Expose
        @SerializedName("LineNo")
        private int LineNo;

        public void setISStockAvailable(boolean ISStockAvailable) {
            this.ISStockAvailable = ISStockAvailable;
        }

        public void setISReserved(boolean ISReserved) {
            this.ISReserved = ISReserved;
        }

        public void setOmsLineRECID(int OmsLineRECID) {
            this.OmsLineRECID = OmsLineRECID;
        }

        public void setIsSubsitute(boolean IsSubsitute) {
            this.IsSubsitute = IsSubsitute;
        }

        public void setOmsLineID(int OmsLineID) {
            this.OmsLineID = OmsLineID;
        }

        public void setOrderStatus(int OrderStatus) {
            this.OrderStatus = OrderStatus;
        }

        public void setCategoryReference(String CategoryReference) {
            this.CategoryReference = CategoryReference;
        }

        public void setSubstitudeItemId(String SubstitudeItemId) {
            this.SubstitudeItemId = SubstitudeItemId;
        }

        public void setDiscountStructureType(int DiscountStructureType) {
            this.DiscountStructureType = DiscountStructureType;
        }

        public void setSGSTTaxCode(String SGSTTaxCode) {
            this.SGSTTaxCode = SGSTTaxCode;
        }

        public void setCGSTTaxCode(String CGSTTaxCode) {
            this.CGSTTaxCode = CGSTTaxCode;
        }

        public void setCESSTaxCode(String CESSTaxCode) {
            this.CESSTaxCode = CESSTaxCode;
        }

        public void setIGSTTaxCode(String IGSTTaxCode) {
            this.IGSTTaxCode = IGSTTaxCode;
        }

        public void setTotalTax(int TotalTax) {
            this.TotalTax = TotalTax;
        }

        public void setSGSTPerc(int SGSTPerc) {
            this.SGSTPerc = SGSTPerc;
        }

        public void setCGSTPerc(int CGSTPerc) {
            this.CGSTPerc = CGSTPerc;
        }

        public void setCESSPerc(int CESSPerc) {
            this.CESSPerc = CESSPerc;
        }

        public void setIGSTPerc(int IGSTPerc) {
            this.IGSTPerc = IGSTPerc;
        }

        public void setApplyDiscount(boolean ApplyDiscount) {
            this.ApplyDiscount = ApplyDiscount;
        }

        public void setLineDiscPercentage(int LineDiscPercentage) {
            this.LineDiscPercentage = LineDiscPercentage;
        }

        public void setOfferType(int OfferType) {
            this.OfferType = OfferType;
        }

        public void setDiscId(String DiscId) {
            this.DiscId = DiscId;
        }

        public void setMMGroupId(String MMGroupId) {
            this.MMGroupId = MMGroupId;
        }

        public void setMixMode(boolean MixMode) {
            this.MixMode = MixMode;
        }

        public void setDiscountType(String DiscountType) {
            this.DiscountType = DiscountType;
        }

        public void setOfferDiscountValue(int OfferDiscountValue) {
            this.OfferDiscountValue = OfferDiscountValue;
        }

        public void setOfferDiscountType(int OfferDiscountType) {
            this.OfferDiscountType = OfferDiscountType;
        }

        public void setOfferAmount(int OfferAmount) {
            this.OfferAmount = OfferAmount;
        }

        public void setOfferQty(int OfferQty) {
            this.OfferQty = OfferQty;
        }

        public void setSubClassification(String SubClassification) {
            this.SubClassification = SubClassification;
        }

        public void setDiseaseType(String DiseaseType) {
            this.DiseaseType = DiseaseType;
        }

        public void setModifyBatchId(String ModifyBatchId) {
            this.ModifyBatchId = ModifyBatchId;
        }

        public void setProductRecID(String ProductRecID) {
            this.ProductRecID = ProductRecID;
        }

        public void setDPCO(boolean DPCO) {
            this.DPCO = DPCO;
        }

        public void setRetailMainCategoryRecID(String RetailMainCategoryRecID) {
            this.RetailMainCategoryRecID = RetailMainCategoryRecID;
        }

        public void setRetailSubCategoryRecID(String RetailSubCategoryRecID) {
            this.RetailSubCategoryRecID = RetailSubCategoryRecID;
        }

        public void setRetailCategoryRecID(String RetailCategoryRecID) {
            this.RetailCategoryRecID = RetailCategoryRecID;
        }

        public void setIsChecked(boolean IsChecked) {
            this.IsChecked = IsChecked;
        }

        public void setIsPriceOverride(boolean IsPriceOverride) {
            this.IsPriceOverride = IsPriceOverride;
        }

        public void setIsVoid(boolean IsVoid) {
            this.IsVoid = IsVoid;
        }

        public void setRemainderDays(int RemainderDays) {
            this.RemainderDays = RemainderDays;
        }

        public void setISPrescribed(int ISPrescribed) {
            this.ISPrescribed = ISPrescribed;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public void setVariantId(String VariantId) {
            this.VariantId = VariantId;
        }

        public void setUnitQty(int UnitQty) {
            this.UnitQty = UnitQty;
        }

        public void setUnitPrice(int UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public void setTotalRoundedAmount(int TotalRoundedAmount) {
            this.TotalRoundedAmount = TotalRoundedAmount;
        }

        public void setTotalDiscPct(int TotalDiscPct) {
            this.TotalDiscPct = TotalDiscPct;
        }

        public void setTotalDiscAmount(int TotalDiscAmount) {
            this.TotalDiscAmount = TotalDiscAmount;
        }

        public void setBaseAmount(int BaseAmount) {
            this.BaseAmount = BaseAmount;
        }

        public void setTaxAmount(double TaxAmount) {
            this.TaxAmount = TaxAmount;
        }

        public void setPrice(int Price) {
            this.Price = Price;
        }

        public void setPeriodicDiscAmount(int PeriodicDiscAmount) {
            this.PeriodicDiscAmount = PeriodicDiscAmount;
        }

        public void setOriginalPrice(int OriginalPrice) {
            this.OriginalPrice = OriginalPrice;
        }

        public void setNetAmountInclTax(int NetAmountInclTax) {
            this.NetAmountInclTax = NetAmountInclTax;
        }

        public void setNetAmount(double NetAmount) {
            this.NetAmount = NetAmount;
        }

        public void setLineManualDiscountPercentage(int LineManualDiscountPercentage) {
            this.LineManualDiscountPercentage = LineManualDiscountPercentage;
        }

        public void setLineManualDiscountAmount(int LineManualDiscountAmount) {
            this.LineManualDiscountAmount = LineManualDiscountAmount;
        }

        public void setLinedscAmount(int LinedscAmount) {
            this.LinedscAmount = LinedscAmount;
        }

        public void setPreviewText(String PreviewText) {
            this.PreviewText = PreviewText;
        }

        public void setInventBatchId(String InventBatchId) {
            this.InventBatchId = InventBatchId;
        }

        public void setHsncode_In(String Hsncode_In) {
            this.Hsncode_In = Hsncode_In;
        }

        public void setDiscOfferId(String DiscOfferId) {
            this.DiscOfferId = DiscOfferId;
        }

        public void setDiscAmount(int DiscAmount) {
            this.DiscAmount = DiscAmount;
        }

        public void setComment(String Comment) {
            this.Comment = Comment;
        }

        public void setBarcode(String Barcode) {
            this.Barcode = Barcode;
        }

        public void setAdditionaltax(int Additionaltax) {
            this.Additionaltax = Additionaltax;
        }

        public void setTax(int Tax) {
            this.Tax = Tax;
        }

        public void setMRP(int MRP) {
            this.MRP = MRP;
        }

        public void setRemainingQty(int RemainingQty) {
            this.RemainingQty = RemainingQty;
        }

        public void setReturnQty(int ReturnQty) {
            this.ReturnQty = ReturnQty;
        }

        public void setStockQty(int StockQty) {
            this.StockQty = StockQty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public void setExpiry(String Expiry) {
            this.Expiry = Expiry;
        }

        public void setManufacturerName(String ManufacturerName) {
            this.ManufacturerName = ManufacturerName;
        }

        public void setManufacturerCode(String ManufacturerCode) {
            this.ManufacturerCode = ManufacturerCode;
        }

        public void setScheduleCategoryCode(String ScheduleCategoryCode) {
            this.ScheduleCategoryCode = ScheduleCategoryCode;
        }

        public void setScheduleCategory(String ScheduleCategory) {
            this.ScheduleCategory = ScheduleCategory;
        }

        public void setSubCategoryCode(String SubCategoryCode) {
            this.SubCategoryCode = SubCategoryCode;
        }

        public void setSubCategory(String SubCategory) {
            this.SubCategory = SubCategory;
        }

        public void setCategoryCode(String CategoryCode) {
            this.CategoryCode = CategoryCode;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public void setItemId(String ItemId) {
            this.ItemId = ItemId;
        }

        public void setLineNo(int LineNo) {
            this.LineNo = LineNo;
        }
    }
}
