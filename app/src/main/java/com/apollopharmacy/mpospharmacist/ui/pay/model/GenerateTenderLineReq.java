package com.apollopharmacy.mpospharmacist.ui.pay.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerateTenderLineReq {


    @Expose
    @SerializedName("POSTransaction")
    private POSTransactionEntity POSTransaction;
    @Expose
    @SerializedName("Type")
    private TypeEntity Type;
    @Expose
    @SerializedName("Wallet")
    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public POSTransactionEntity getPOSTransaction() {
        return POSTransaction;
    }

    public TypeEntity getType() {
        return Type;
    }

    public void setPOSTransaction(POSTransactionEntity POSTransaction) {
        this.POSTransaction = POSTransaction;
    }

    public void setType(TypeEntity type) {
        Type = type;
    }

    public static class POSTransactionEntity {
        @Expose
        @SerializedName("TenderLine")
        private List<String> TenderLine;
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
        private double TotalTaxAmount;
        @Expose
        @SerializedName("TotalMRP")
        private double TotalMRP;
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
        private double NetAmountInclTax;
        @Expose
        @SerializedName("NetAmount")
        private double NetAmount;
        @Expose
        @SerializedName("GrossAmount")
        private double GrossAmount;
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
        private double Remainingamount;

        public void setTenderLine(List<String> tenderLine) {
            TenderLine = tenderLine;
        }

        public void setSalesLine(List<SalesLineEntity> salesLine) {
            SalesLine = salesLine;
        }

        public void setISOMSOrder(boolean ISOMSOrder) {
            this.ISOMSOrder = ISOMSOrder;
        }

        public void setReminderDays(int reminderDays) {
            ReminderDays = reminderDays;
        }

        public void setAmounttoAccount(int amounttoAccount) {
            AmounttoAccount = amounttoAccount;
        }

        public void setISAdvancePayment(boolean ISAdvancePayment) {
            this.ISAdvancePayment = ISAdvancePayment;
        }

        public void setCouponCode(String couponCode) {
            CouponCode = couponCode;
        }

        public void setSEZ(int SEZ) {
            this.SEZ = SEZ;
        }

        public void setISPosted(boolean ISPosted) {
            this.ISPosted = ISPosted;
        }

        public void setStockCheck(boolean stockCheck) {
            IsStockCheck = stockCheck;
        }

        public void setTransType(int transType) {
            TransType = transType;
        }

        public void setPosEvent(int posEvent) {
            PosEvent = posEvent;
        }

        public void setReturnMessage(String returnMessage) {
            ReturnMessage = returnMessage;
        }

        public void setRequestStatus(int requestStatus) {
            RequestStatus = requestStatus;
        }

        public void setCurrentSalesLine(int currentSalesLine) {
            CurrentSalesLine = currentSalesLine;
        }

        public void setReturnType(int returnType) {
            ReturnType = returnType;
        }

        public void setManualBill(boolean manualBill) {
            IsManualBill = manualBill;
        }

        public void setISReturnAllowed(boolean ISReturnAllowed) {
            this.ISReturnAllowed = ISReturnAllowed;
        }

        public void setISBatchModifiedAllowed(boolean ISBatchModifiedAllowed) {
            this.ISBatchModifiedAllowed = ISBatchModifiedAllowed;
        }

        public void setReturn(boolean aReturn) {
            IsReturn = aReturn;
        }

        public void setVoid(boolean aVoid) {
            IsVoid = aVoid;
        }

        public void setDataAreaId(String dataAreaId) {
            DataAreaId = dataAreaId;
        }

        public void setType(int type) {
            Type = type;
        }

        public void setTransDate(String transDate) {
            TransDate = transDate;
        }

        public void setTransactionId(String transactionId) {
            TransactionId = transactionId;
        }

        public void setTotalTaxAmount(double totalTaxAmount) {
            TotalTaxAmount = totalTaxAmount;
        }

        public void setTotalMRP(double totalMRP) {
            TotalMRP = totalMRP;
        }

        public void setTotalManualDiscountPercentage(int totalManualDiscountPercentage) {
            TotalManualDiscountPercentage = totalManualDiscountPercentage;
        }

        public void setTotalManualDiscountAmount(int totalManualDiscountAmount) {
            TotalManualDiscountAmount = totalManualDiscountAmount;
        }

        public void setTotalDiscAmount(int totalDiscAmount) {
            TotalDiscAmount = totalDiscAmount;
        }

        public void setTimewhenTransClosed(int timewhenTransClosed) {
            TimewhenTransClosed = timewhenTransClosed;
        }

        public void setReturnReceiptId(String returnReceiptId) {
            ReturnReceiptId = returnReceiptId;
        }

        public void setReturnTransactionId(String returnTransactionId) {
            ReturnTransactionId = returnTransactionId;
        }

        public void setReturnTerminal(String returnTerminal) {
            ReturnTerminal = returnTerminal;
        }

        public void setReturnStore(String returnStore) {
            ReturnStore = returnStore;
        }

        public void setTerminal(String terminal) {
            Terminal = terminal;
        }

        public void setState(String state) {
            State = state;
        }

        public void setStore(String store) {
            Store = store;
        }

        public void setStaff(String staff) {
            Staff = staff;
        }

        public void setRoundedAmount(int roundedAmount) {
            RoundedAmount = roundedAmount;
        }

        public void setNumberofItems(int numberofItems) {
            NumberofItems = numberofItems;
        }

        public void setNumberofItemLines(int numberofItemLines) {
            NumberofItemLines = numberofItemLines;
        }

        public void setNetAmountInclTax(double netAmountInclTax) {
            NetAmountInclTax = netAmountInclTax;
        }

        public void setNetAmount(double netAmount) {
            NetAmount = netAmount;
        }

        public void setGrossAmount(double grossAmount) {
            GrossAmount = grossAmount;
        }

        public void setEntryStatus(int entryStatus) {
            EntryStatus = entryStatus;
        }

        public void setDiscAmount(int discAmount) {
            DiscAmount = discAmount;
        }

        public void setCustDiscamount(int custDiscamount) {
            CustDiscamount = custDiscamount;
        }

        public void setCustAccount(String custAccount) {
            CustAccount = custAccount;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public void setCreatedonPosTerminal(String createdonPosTerminal) {
            CreatedonPosTerminal = createdonPosTerminal;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public void setChannel(String channel) {
            Channel = channel;
        }

        public void setBusinessDate(String businessDate) {
            BusinessDate = businessDate;
        }

        public void setBatchTerminalid(String batchTerminalid) {
            BatchTerminalid = batchTerminalid;
        }

        public void setReciptId(String reciptId) {
            ReciptId = reciptId;
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

        public void setTrackingRef(String trackingRef) {
            TrackingRef = trackingRef;
        }

        public void setSalesOrigin(String salesOrigin) {
            SalesOrigin = salesOrigin;
        }

        public void setDoctorCode(String doctorCode) {
            DoctorCode = doctorCode;
        }

        public void setDoctorName(String doctorName) {
            DoctorName = doctorName;
        }

        public void setPincode(String pincode) {
            Pincode = pincode;
        }

        public void setGender(int gender) {
            Gender = gender;
        }

        public void setCustomerState(String customerState) {
            CustomerState = customerState;
        }

        public void setCustAddress(String custAddress) {
            CustAddress = custAddress;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public void setMobileNO(String mobileNO) {
            MobileNO = mobileNO;
        }

        public void setCorpCode(String corpCode) {
            CorpCode = corpCode;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public void setRegionCode(String regionCode) {
            RegionCode = regionCode;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }

        public void setCancelReasonCode(String cancelReasonCode) {
            CancelReasonCode = cancelReasonCode;
        }

        public void setISPrescibeDiscount(boolean ISPrescibeDiscount) {
            this.ISPrescibeDiscount = ISPrescibeDiscount;
        }

        public void setPaymentSource(String paymentSource) {
            PaymentSource = paymentSource;
        }

        public void setVendorId(String vendorId) {
            VendorId = vendorId;
        }

        public void setBillingCity(String billingCity) {
            BillingCity = billingCity;
        }

        public void setShippingMethod(String shippingMethod) {
            ShippingMethod = shippingMethod;
        }

        public void setOrderSource(String orderSource) {
            OrderSource = orderSource;
        }

        public void setOrderType(String orderType) {
            OrderType = orderType;
        }

        public void setDeliveryDate(String deliveryDate) {
            DeliveryDate = deliveryDate;
        }

        public void setISReserved(boolean ISReserved) {
            this.ISReserved = ISReserved;
        }

        public void setRemainingamount(double remainingamount) {
            Remainingamount = remainingamount;
        }

        public List<String> getTenderLine() {
            return TenderLine;
        }

        public List<SalesLineEntity> getSalesLine() {
            return SalesLine;
        }

        public boolean getISOMSOrder() {
            return ISOMSOrder;
        }

        public int getReminderDays() {
            return ReminderDays;
        }

        public int getAmounttoAccount() {
            return AmounttoAccount;
        }

        public boolean getISAdvancePayment() {
            return ISAdvancePayment;
        }

        public String getCouponCode() {
            return CouponCode;
        }

        public int getSEZ() {
            return SEZ;
        }

        public boolean getISPosted() {
            return ISPosted;
        }

        public boolean getIsStockCheck() {
            return IsStockCheck;
        }

        public int getTransType() {
            return TransType;
        }

        public int getPosEvent() {
            return PosEvent;
        }

        public String getReturnMessage() {
            return ReturnMessage;
        }

        public int getRequestStatus() {
            return RequestStatus;
        }

        public int getCurrentSalesLine() {
            return CurrentSalesLine;
        }

        public int getReturnType() {
            return ReturnType;
        }

        public boolean getIsManualBill() {
            return IsManualBill;
        }

        public boolean getISReturnAllowed() {
            return ISReturnAllowed;
        }

        public boolean getISBatchModifiedAllowed() {
            return ISBatchModifiedAllowed;
        }

        public boolean getIsReturn() {
            return IsReturn;
        }

        public boolean getIsVoid() {
            return IsVoid;
        }

        public String getDataAreaId() {
            return DataAreaId;
        }

        public int getType() {
            return Type;
        }

        public String getTransDate() {
            return TransDate;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public double getTotalTaxAmount() {
            return TotalTaxAmount;
        }

        public double getTotalMRP() {
            return TotalMRP;
        }

        public int getTotalManualDiscountPercentage() {
            return TotalManualDiscountPercentage;
        }

        public int getTotalManualDiscountAmount() {
            return TotalManualDiscountAmount;
        }

        public int getTotalDiscAmount() {
            return TotalDiscAmount;
        }

        public int getTimewhenTransClosed() {
            return TimewhenTransClosed;
        }

        public String getReturnReceiptId() {
            return ReturnReceiptId;
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

        public String getTerminal() {
            return Terminal;
        }

        public String getState() {
            return State;
        }

        public String getStore() {
            return Store;
        }

        public String getStaff() {
            return Staff;
        }

        public int getRoundedAmount() {
            return RoundedAmount;
        }

        public int getNumberofItems() {
            return NumberofItems;
        }

        public int getNumberofItemLines() {
            return NumberofItemLines;
        }

        public double getNetAmountInclTax() {
            return NetAmountInclTax;
        }

        public double getNetAmount() {
            return NetAmount;
        }

        public double getGrossAmount() {
            return GrossAmount;
        }

        public int getEntryStatus() {
            return EntryStatus;
        }

        public int getDiscAmount() {
            return DiscAmount;
        }

        public int getCustDiscamount() {
            return CustDiscamount;
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

        public String getComment() {
            return Comment;
        }

        public String getChannel() {
            return Channel;
        }

        public String getBusinessDate() {
            return BusinessDate;
        }

        public String getBatchTerminalid() {
            return BatchTerminalid;
        }

        public String getReciptId() {
            return ReciptId;
        }

        public String getIPSerialNO() {
            return IPSerialNO;
        }

        public String getIPNO() {
            return IPNO;
        }

        public String getREFNO() {
            return REFNO;
        }

        public String getTrackingRef() {
            return TrackingRef;
        }

        public String getSalesOrigin() {
            return SalesOrigin;
        }

        public String getDoctorCode() {
            return DoctorCode;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public String getPincode() {
            return Pincode;
        }

        public int getGender() {
            return Gender;
        }

        public String getCustomerState() {
            return CustomerState;
        }

        public String getCustAddress() {
            return CustAddress;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public String getDOB() {
            return DOB;
        }

        public String getMobileNO() {
            return MobileNO;
        }

        public String getCorpCode() {
            return CorpCode;
        }

        public String getCustomerID() {
            return CustomerID;
        }

        public String getRegionCode() {
            return RegionCode;
        }

        public String getStoreName() {
            return StoreName;
        }

        public String getCancelReasonCode() {
            return CancelReasonCode;
        }

        public boolean getISPrescibeDiscount() {
            return ISPrescibeDiscount;
        }

        public String getPaymentSource() {
            return PaymentSource;
        }

        public String getVendorId() {
            return VendorId;
        }

        public String getBillingCity() {
            return BillingCity;
        }

        public String getShippingMethod() {
            return ShippingMethod;
        }

        public String getOrderSource() {
            return OrderSource;
        }

        public String getOrderType() {
            return OrderType;
        }

        public String getDeliveryDate() {
            return DeliveryDate;
        }

        public boolean getISReserved() {
            return ISReserved;
        }

        public double getRemainingamount() {
            return Remainingamount;
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
        private double TotalTax;
        @Expose
        @SerializedName("SGSTPerc")
        private double SGSTPerc;
        @Expose
        @SerializedName("CGSTPerc")
        private double CGSTPerc;
        @Expose
        @SerializedName("CESSPerc")
        private int CESSPerc;
        @Expose
        @SerializedName("IGSTPerc")
        private double IGSTPerc;
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
        private double Total;
        @Expose
        @SerializedName("VariantId")
        private String VariantId;
        @Expose
        @SerializedName("UnitQty")
        private int UnitQty;
        @Expose
        @SerializedName("UnitPrice")
        private double UnitPrice;
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
        private double BaseAmount;
        @Expose
        @SerializedName("TaxAmount")
        private double TaxAmount;
        @Expose
        @SerializedName("Price")
        private double Price;
        @Expose
        @SerializedName("PeriodicDiscAmount")
        private int PeriodicDiscAmount;
        @Expose
        @SerializedName("OriginalPrice")
        private double OriginalPrice;
        @Expose
        @SerializedName("NetAmountInclTax")
        private double NetAmountInclTax;
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
        private double Tax;
        @Expose
        @SerializedName("MRP")
        private double MRP;
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

        public void setOmsLineRECID(int omsLineRECID) {
            OmsLineRECID = omsLineRECID;
        }

        public void setSubsitute(boolean subsitute) {
            IsSubsitute = subsitute;
        }

        public void setOmsLineID(int omsLineID) {
            OmsLineID = omsLineID;
        }

        public void setOrderStatus(int orderStatus) {
            OrderStatus = orderStatus;
        }

        public void setCategoryReference(String categoryReference) {
            CategoryReference = categoryReference;
        }

        public void setSubstitudeItemId(String substitudeItemId) {
            SubstitudeItemId = substitudeItemId;
        }

        public void setDiscountStructureType(int discountStructureType) {
            DiscountStructureType = discountStructureType;
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

        public void setTotalTax(double totalTax) {
            TotalTax = totalTax;
        }

        public void setSGSTPerc(double SGSTPerc) {
            this.SGSTPerc = SGSTPerc;
        }

        public void setCGSTPerc(double CGSTPerc) {
            this.CGSTPerc = CGSTPerc;
        }

        public void setCESSPerc(int CESSPerc) {
            this.CESSPerc = CESSPerc;
        }

        public void setIGSTPerc(double IGSTPerc) {
            this.IGSTPerc = IGSTPerc;
        }

        public void setApplyDiscount(boolean applyDiscount) {
            ApplyDiscount = applyDiscount;
        }

        public void setLineDiscPercentage(int lineDiscPercentage) {
            LineDiscPercentage = lineDiscPercentage;
        }

        public void setOfferType(int offerType) {
            OfferType = offerType;
        }

        public void setDiscId(String discId) {
            DiscId = discId;
        }

        public void setMMGroupId(String MMGroupId) {
            this.MMGroupId = MMGroupId;
        }

        public void setMixMode(boolean mixMode) {
            MixMode = mixMode;
        }

        public void setDiscountType(String discountType) {
            DiscountType = discountType;
        }

        public void setOfferDiscountValue(int offerDiscountValue) {
            OfferDiscountValue = offerDiscountValue;
        }

        public void setOfferDiscountType(int offerDiscountType) {
            OfferDiscountType = offerDiscountType;
        }

        public void setOfferAmount(int offerAmount) {
            OfferAmount = offerAmount;
        }

        public void setOfferQty(int offerQty) {
            OfferQty = offerQty;
        }

        public void setSubClassification(String subClassification) {
            SubClassification = subClassification;
        }

        public void setDiseaseType(String diseaseType) {
            DiseaseType = diseaseType;
        }

        public void setModifyBatchId(String modifyBatchId) {
            ModifyBatchId = modifyBatchId;
        }

        public void setProductRecID(String productRecID) {
            ProductRecID = productRecID;
        }

        public void setDPCO(boolean DPCO) {
            this.DPCO = DPCO;
        }

        public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
            RetailMainCategoryRecID = retailMainCategoryRecID;
        }

        public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
            RetailSubCategoryRecID = retailSubCategoryRecID;
        }

        public void setRetailCategoryRecID(String retailCategoryRecID) {
            RetailCategoryRecID = retailCategoryRecID;
        }

        public void setChecked(boolean checked) {
            IsChecked = checked;
        }

        public void setPriceOverride(boolean priceOverride) {
            IsPriceOverride = priceOverride;
        }

        public void setVoid(boolean aVoid) {
            IsVoid = aVoid;
        }

        public void setRemainderDays(int remainderDays) {
            RemainderDays = remainderDays;
        }

        public void setISPrescribed(int ISPrescribed) {
            this.ISPrescribed = ISPrescribed;
        }

        public void setTotal(double total) {
            Total = total;
        }

        public void setVariantId(String variantId) {
            VariantId = variantId;
        }

        public void setUnitQty(int unitQty) {
            UnitQty = unitQty;
        }

        public void setUnitPrice(double unitPrice) {
            UnitPrice = unitPrice;
        }

        public void setUnit(String unit) {
            Unit = unit;
        }

        public void setTotalRoundedAmount(int totalRoundedAmount) {
            TotalRoundedAmount = totalRoundedAmount;
        }

        public void setTotalDiscPct(int totalDiscPct) {
            TotalDiscPct = totalDiscPct;
        }

        public void setTotalDiscAmount(int totalDiscAmount) {
            TotalDiscAmount = totalDiscAmount;
        }

        public void setBaseAmount(double baseAmount) {
            BaseAmount = baseAmount;
        }

        public void setTaxAmount(double taxAmount) {
            TaxAmount = taxAmount;
        }

        public void setPrice(double price) {
            Price = price;
        }

        public void setPeriodicDiscAmount(int periodicDiscAmount) {
            PeriodicDiscAmount = periodicDiscAmount;
        }

        public void setOriginalPrice(double originalPrice) {
            OriginalPrice = originalPrice;
        }

        public void setNetAmountInclTax(double netAmountInclTax) {
            NetAmountInclTax = netAmountInclTax;
        }

        public void setNetAmount(double netAmount) {
            NetAmount = netAmount;
        }

        public void setLineManualDiscountPercentage(int lineManualDiscountPercentage) {
            LineManualDiscountPercentage = lineManualDiscountPercentage;
        }

        public void setLineManualDiscountAmount(int lineManualDiscountAmount) {
            LineManualDiscountAmount = lineManualDiscountAmount;
        }

        public void setLinedscAmount(int linedscAmount) {
            LinedscAmount = linedscAmount;
        }

        public void setPreviewText(String previewText) {
            PreviewText = previewText;
        }

        public void setInventBatchId(String inventBatchId) {
            InventBatchId = inventBatchId;
        }

        public void setHsncode_In(String hsncode_In) {
            Hsncode_In = hsncode_In;
        }

        public void setDiscOfferId(String discOfferId) {
            DiscOfferId = discOfferId;
        }

        public void setDiscAmount(int discAmount) {
            DiscAmount = discAmount;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public void setBarcode(String barcode) {
            Barcode = barcode;
        }

        public void setAdditionaltax(int additionaltax) {
            Additionaltax = additionaltax;
        }

        public void setTax(double tax) {
            Tax = tax;
        }

        public void setMRP(double MRP) {
            this.MRP = MRP;
        }

        public void setRemainingQty(int remainingQty) {
            RemainingQty = remainingQty;
        }

        public void setReturnQty(int returnQty) {
            ReturnQty = returnQty;
        }

        public void setStockQty(int stockQty) {
            StockQty = stockQty;
        }

        public void setQty(int qty) {
            Qty = qty;
        }

        public void setExpiry(String expiry) {
            Expiry = expiry;
        }

        public void setManufacturerName(String manufacturerName) {
            ManufacturerName = manufacturerName;
        }

        public void setManufacturerCode(String manufacturerCode) {
            ManufacturerCode = manufacturerCode;
        }

        public void setScheduleCategoryCode(String scheduleCategoryCode) {
            ScheduleCategoryCode = scheduleCategoryCode;
        }

        public void setScheduleCategory(String scheduleCategory) {
            ScheduleCategory = scheduleCategory;
        }

        public void setSubCategoryCode(String subCategoryCode) {
            SubCategoryCode = subCategoryCode;
        }

        public void setSubCategory(String subCategory) {
            SubCategory = subCategory;
        }

        public void setCategoryCode(String categoryCode) {
            CategoryCode = categoryCode;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public void setItemId(String itemId) {
            ItemId = itemId;
        }

        public void setLineNo(int lineNo) {
            LineNo = lineNo;
        }

        public boolean getISStockAvailable() {
            return ISStockAvailable;
        }

        public boolean getISReserved() {
            return ISReserved;
        }

        public int getOmsLineRECID() {
            return OmsLineRECID;
        }

        public boolean getIsSubsitute() {
            return IsSubsitute;
        }

        public int getOmsLineID() {
            return OmsLineID;
        }

        public int getOrderStatus() {
            return OrderStatus;
        }

        public String getCategoryReference() {
            return CategoryReference;
        }

        public String getSubstitudeItemId() {
            return SubstitudeItemId;
        }

        public int getDiscountStructureType() {
            return DiscountStructureType;
        }

        public String getSGSTTaxCode() {
            return SGSTTaxCode;
        }

        public String getCGSTTaxCode() {
            return CGSTTaxCode;
        }

        public String getCESSTaxCode() {
            return CESSTaxCode;
        }

        public String getIGSTTaxCode() {
            return IGSTTaxCode;
        }

        public double getTotalTax() {
            return TotalTax;
        }

        public double getSGSTPerc() {
            return SGSTPerc;
        }

        public double getCGSTPerc() {
            return CGSTPerc;
        }

        public int getCESSPerc() {
            return CESSPerc;
        }

        public double getIGSTPerc() {
            return IGSTPerc;
        }

        public boolean getApplyDiscount() {
            return ApplyDiscount;
        }

        public int getLineDiscPercentage() {
            return LineDiscPercentage;
        }

        public int getOfferType() {
            return OfferType;
        }

        public String getDiscId() {
            return DiscId;
        }

        public String getMMGroupId() {
            return MMGroupId;
        }

        public boolean getMixMode() {
            return MixMode;
        }

        public String getDiscountType() {
            return DiscountType;
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

        public int getOfferQty() {
            return OfferQty;
        }

        public String getSubClassification() {
            return SubClassification;
        }

        public String getDiseaseType() {
            return DiseaseType;
        }

        public String getModifyBatchId() {
            return ModifyBatchId;
        }

        public String getProductRecID() {
            return ProductRecID;
        }

        public boolean getDPCO() {
            return DPCO;
        }

        public String getRetailMainCategoryRecID() {
            return RetailMainCategoryRecID;
        }

        public String getRetailSubCategoryRecID() {
            return RetailSubCategoryRecID;
        }

        public String getRetailCategoryRecID() {
            return RetailCategoryRecID;
        }

        public boolean getIsChecked() {
            return IsChecked;
        }

        public boolean getIsPriceOverride() {
            return IsPriceOverride;
        }

        public boolean getIsVoid() {
            return IsVoid;
        }

        public int getRemainderDays() {
            return RemainderDays;
        }

        public int getISPrescribed() {
            return ISPrescribed;
        }

        public double getTotal() {
            return Total;
        }

        public String getVariantId() {
            return VariantId;
        }

        public int getUnitQty() {
            return UnitQty;
        }

        public double getUnitPrice() {
            return UnitPrice;
        }

        public String getUnit() {
            return Unit;
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

        public double getBaseAmount() {
            return BaseAmount;
        }

        public double getTaxAmount() {
            return TaxAmount;
        }

        public double getPrice() {
            return Price;
        }

        public int getPeriodicDiscAmount() {
            return PeriodicDiscAmount;
        }

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public double getNetAmountInclTax() {
            return NetAmountInclTax;
        }

        public double getNetAmount() {
            return NetAmount;
        }

        public int getLineManualDiscountPercentage() {
            return LineManualDiscountPercentage;
        }

        public int getLineManualDiscountAmount() {
            return LineManualDiscountAmount;
        }

        public int getLinedscAmount() {
            return LinedscAmount;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public String getInventBatchId() {
            return InventBatchId;
        }

        public String getHsncode_In() {
            return Hsncode_In;
        }

        public String getDiscOfferId() {
            return DiscOfferId;
        }

        public int getDiscAmount() {
            return DiscAmount;
        }

        public String getComment() {
            return Comment;
        }

        public String getBarcode() {
            return Barcode;
        }

        public int getAdditionaltax() {
            return Additionaltax;
        }

        public double getTax() {
            return Tax;
        }

        public double getMRP() {
            return MRP;
        }

        public int getRemainingQty() {
            return RemainingQty;
        }

        public int getReturnQty() {
            return ReturnQty;
        }

        public int getStockQty() {
            return StockQty;
        }

        public int getQty() {
            return Qty;
        }

        public String getExpiry() {
            return Expiry;
        }

        public String getManufacturerName() {
            return ManufacturerName;
        }

        public String getManufacturerCode() {
            return ManufacturerCode;
        }

        public String getScheduleCategoryCode() {
            return ScheduleCategoryCode;
        }

        public String getScheduleCategory() {
            return ScheduleCategory;
        }

        public String getSubCategoryCode() {
            return SubCategoryCode;
        }

        public String getSubCategory() {
            return SubCategory;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public String getCategory() {
            return Category;
        }

        public String getItemName() {
            return ItemName;
        }

        public String getItemId() {
            return ItemId;
        }

        public int getLineNo() {
            return LineNo;
        }
    }

    public static class TypeEntity {
        @Expose
        @SerializedName("TenderLimit")
        private int TenderLimit;
        @Expose
        @SerializedName("TenderCombinationType")
        private int TenderCombinationType;
        @Expose
        @SerializedName("RoundingMethod")
        private int RoundingMethod;
        @Expose
        @SerializedName("PosOpereration")
        private int PosOpereration;
        @Expose
        @SerializedName("TenderURL")
        private String TenderURL;
        @Expose
        @SerializedName("Tender")
        private String Tender;
        @Expose
        @SerializedName("TenderTypeId")
        private String TenderTypeId;

        public int getTenderLimit() {
            return TenderLimit;
        }

        public int getTenderCombinationType() {
            return TenderCombinationType;
        }

        public int getRoundingMethod() {
            return RoundingMethod;
        }

        public int getPosOpereration() {
            return PosOpereration;
        }

        public String getTenderURL() {
            return TenderURL;
        }

        public String getTender() {
            return Tender;
        }

        public String getTenderTypeId() {
            return TenderTypeId;
        }

        public void setTenderLimit(int tenderLimit) {
            TenderLimit = tenderLimit;
        }

        public void setTenderCombinationType(int tenderCombinationType) {
            TenderCombinationType = tenderCombinationType;
        }

        public void setRoundingMethod(int roundingMethod) {
            RoundingMethod = roundingMethod;
        }

        public void setPosOpereration(int posOpereration) {
            PosOpereration = posOpereration;
        }

        public void setTenderURL(String tenderURL) {
            TenderURL = tenderURL;
        }

        public void setTender(String tender) {
            Tender = tender;
        }

        public void setTenderTypeId(String tenderTypeId) {
            TenderTypeId = tenderTypeId;
        }
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
        private int WalletAmount;
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

        public void setWalletAmount(int WalletAmount) {
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
}
