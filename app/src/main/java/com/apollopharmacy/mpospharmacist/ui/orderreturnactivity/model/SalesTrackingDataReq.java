package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesTrackingDataReq implements Serializable {
    @Expose
    @SerializedName("AllowedTenderType")
    private String AllowedTenderType;

    @Expose
    @SerializedName("AmounttoAccount")
    private int AmounttoAccount;

    @Expose
    @SerializedName("ApprovedID")
    private String ApprovedID;

    @Expose
    @SerializedName("AWBNo")
    private String AWBNo;

    @Expose
    @SerializedName("BatchTerminalid")
    private String BatchTerminalid;

    @Expose
    @SerializedName("BillingCity")
    private String BillingCity;

    @Expose
    @SerializedName("")
    private String BusinessDate;

    @Expose
    @SerializedName("CancelReasonCode")
    private String CancelReasonCode;

    @Expose
    @SerializedName("Channel")
    private String Channel;

    @Expose
    @SerializedName("Comment")
    private String Comment;

    @Expose
    @SerializedName("CorpCode")
    private String CorpCode;

    @Expose
    @SerializedName("CouponCode")
    private String CouponCode;

    @Expose
    @SerializedName("CreatedDateTime")
    private String CreatedDateTime;

    @Expose
    @SerializedName("CreatedonPosTerminal")
    private String CreatedonPosTerminal;

    @Expose
    @SerializedName("Currency")
    private String Currency;

    @Expose
    @SerializedName("CurrentSalesLine")
    private int CurrentSalesLine;

    @Expose
    @SerializedName("CustAccount")
    private String CustAccount;

    @Expose
    @SerializedName("CustAddress")
    private String CustAddress;

    @Expose
    @SerializedName("CustDiscamount")
    private int CustDiscamount;

    @Expose
    @SerializedName("CustomerID")
    private String CustomerID;

    @Expose
    @SerializedName("CustomerName")
    private String CustomerName;

    @Expose
    @SerializedName("CustomerState")
    private String CustomerState;

    @Expose
    @SerializedName("DataAreaId")
    private String DataAreaId;

    @Expose
    @SerializedName("DeliveryDate")
    private String DeliveryDate;

    @Expose
    @SerializedName("DiscAmount")
    private int DiscAmount;

    @Expose
    @SerializedName("DiscountRef")
    private String DiscountRef;

    @Expose
    @SerializedName("DOB")
    private String DOB;

    @Expose
    @SerializedName("DoctorCode")
    private String DoctorCode;

    @Expose
    @SerializedName("DoctorName")
    private String DoctorName;

    @Expose
    @SerializedName("DSPCode")
    private String DSPCode;

    @Expose
    @SerializedName("EntryStatus")
    private int EntryStatus;

    @Expose
    @SerializedName("Gender")
    private int Gender;

    @Expose
    @SerializedName("GrossAmount")
    private int GrossAmount;

    @Expose
    @SerializedName("IPNO")
    private String IPNO;

    @Expose
    @SerializedName("IPSerialNO")
    private String IPSerialNO;

    @Expose
    @SerializedName("ISAdvancePayment")
    private boolean ISAdvancePayment;

    @Expose
    @SerializedName("ISBatchModifiedAllowed")
    private boolean ISBatchModifiedAllowed;

    @Expose
    @SerializedName("ISHBPStore")
    private boolean ISHBPStore;

    @Expose
    @SerializedName("ISHyperDelivered")
    private boolean ISHyperDelivered;

    @Expose
    @SerializedName("ISHyperLocalDelivery")
    private boolean ISHyperLocalDelivery;

    @Expose
    @SerializedName("IsManualBill")
    private boolean IsManualBill;

    @Expose
    @SerializedName("ISOMSOrder")
    private boolean ISOMSOrder;

    @Expose
    @SerializedName("ISOMSValidate")
    private boolean ISOMSValidate;

    @Expose
    @SerializedName("ISOnlineOrder")
    private boolean ISOnlineOrder;

    @Expose
    @SerializedName("ISPosted")
    private boolean ISPosted;

    @Expose
    @SerializedName("ISPrescibeDiscount")
    private boolean ISPrescibeDiscount;

    @Expose
    @SerializedName("ISReserved")
    private boolean ISReserved;

    @Expose
    @SerializedName("IsReturn")
    private boolean IsReturn;

    @Expose
    @SerializedName("ISReturnAllowed")
    private boolean ISReturnAllowed;

    @Expose
    @SerializedName("IsStockCheck")
    private boolean IsStockCheck;

    @Expose
    @SerializedName("IsVoid")
    private boolean IsVoid;

    @Expose
    @SerializedName("MobileNO")
    private String MobileNO;

    @Expose
    @SerializedName("NetAmount")
    private double NetAmount;

    @Expose
    @SerializedName("NetAmountInclTax")
    private int NetAmountInclTax;

    @Expose
    @SerializedName("NumberofItemLines")
    private int NumberofItemLines;

    @Expose
    @SerializedName("NumberofItems")
    private int NumberofItems;

    @Expose
    @SerializedName("OMSCreditAmount")
    private int OMSCreditAmount;

    @Expose
    @SerializedName("OrderPrescriptionURL")
    private String OrderPrescriptionURL;

    @Expose
    @SerializedName("OrderSource")
    private String OrderSource;

    @Expose
    @SerializedName("OrderType")
    private String OrderType;

    @Expose
    @SerializedName("PatientID")
    private String PatientID;

    @Expose
    @SerializedName("PaymentSource")
    private String PaymentSource;

    @Expose
    @SerializedName("Pincode")
    private String Pincode;

    @Expose
    @SerializedName("PosEvent")
    private int PosEvent;

    @Expose
    @SerializedName("ReciptId")
    private String ReciptId;

    @Expose
    @SerializedName("REFNO")
    private String REFNO;

    @Expose
    @SerializedName("RegionCode")
    private String RegionCode;

    @Expose
    @SerializedName("Remainingamount")
    private int Remainingamount;

    @Expose
    @SerializedName("ReminderDays")
    private int ReminderDays;

    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;

    @Expose
    @SerializedName("ReturnReceiptId")
    private String ReturnReceiptId;

    @Expose
    @SerializedName("ReturnStore")
    private String ReturnStore;

    @Expose
    @SerializedName("ReturnTerminal")
    private String ReturnTerminal;

    @Expose
    @SerializedName("ReturnTransactionId")
    private String ReturnTransactionId;

    @Expose
    @SerializedName("ReturnType")
    private int ReturnType;

    @Expose
    @SerializedName("RoundedAmount")
    private int RoundedAmount;

    @Expose
    @SerializedName("SalesLine")
    private List<SalesLine> SalesLine;

    @Expose
    @SerializedName("SalesOrigin")
    private String SalesOrigin;

    @Expose
    @SerializedName("SEZ")
    private int SEZ;

    @Expose
    @SerializedName("ShippingMethod")
    private String ShippingMethod;

    @Expose
    @SerializedName("ShippingMethodDesc")
    private String ShippingMethodDesc;

    @Expose
    @SerializedName("Staff")
    private String Staff;

    @Expose
    @SerializedName("State")
    private String State;

    @Expose
    @SerializedName("Store")
    private String Store;

    @Expose
    @SerializedName("StoreName")
    private String StoreName;

    @Expose
    @SerializedName("TenderLine")
    private List<TenderLine> TenderLine;

    @Expose
    @SerializedName("Terminal")
    private String Terminal;

    @Expose
    @SerializedName("TimewhenTransClosed")
    private int TimewhenTransClosed;

    @Expose
    @SerializedName("TotalDiscAmount")
    private int TotalDiscAmount;

    @Expose
    @SerializedName("TotalManualDiscountAmount")
    private int TotalManualDiscountAmount;

    @Expose
    @SerializedName("TotalManualDiscountPercentage")
    private int TotalManualDiscountPercentage;

    @Expose
    @SerializedName("TotalMRP")
    private int TotalMRP;

    @Expose
    @SerializedName("TotalTaxAmount")
    private int TotalTaxAmount;

    @Expose
    @SerializedName("TrackingRef")
    private String TrackingRef;

    @Expose
    @SerializedName("TransactionId")
    private String TransactionId;

    @Expose
    @SerializedName("TransDate")
    private String TransDate;

    @Expose
    @SerializedName("TransType")
    private int TransType;

    @Expose
    @SerializedName("Type")
    private int Type;

    @Expose
    @SerializedName("VendorCode")
    private String VendorCode;

    @Expose
    @SerializedName("VendorId")
    private String VendorId;

    public String getAllowedTenderType() {
        return AllowedTenderType;
    }

    public void setAllowedTenderType(String allowedTenderType) {
        AllowedTenderType = allowedTenderType;
    }

    public int getAmounttoAccount() {
        return AmounttoAccount;
    }

    public void setAmounttoAccount(int amounttoAccount) {
        AmounttoAccount = amounttoAccount;
    }

    public String getApprovedID() {
        return ApprovedID;
    }

    public void setApprovedID(String approvedID) {
        ApprovedID = approvedID;
    }

    public String getAWBNo() {
        return AWBNo;
    }

    public void setAWBNo(String AWBNo) {
        this.AWBNo = AWBNo;
    }

    public String getBatchTerminalid() {
        return BatchTerminalid;
    }

    public void setBatchTerminalid(String batchTerminalid) {
        BatchTerminalid = batchTerminalid;
    }

    public String getBillingCity() {
        return BillingCity;
    }

    public void setBillingCity(String billingCity) {
        BillingCity = billingCity;
    }

    public String getBusinessDate() {
        return BusinessDate;
    }

    public void setBusinessDate(String businessDate) {
        BusinessDate = businessDate;
    }

    public String getCancelReasonCode() {
        return CancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        CancelReasonCode = cancelReasonCode;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCorpCode() {
        return CorpCode;
    }

    public void setCorpCode(String corpCode) {
        CorpCode = corpCode;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        CreatedDateTime = createdDateTime;
    }

    public String getCreatedonPosTerminal() {
        return CreatedonPosTerminal;
    }

    public void setCreatedonPosTerminal(String createdonPosTerminal) {
        CreatedonPosTerminal = createdonPosTerminal;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public int getCurrentSalesLine() {
        return CurrentSalesLine;
    }

    public void setCurrentSalesLine(int currentSalesLine) {
        CurrentSalesLine = currentSalesLine;
    }

    public String getCustAccount() {
        return CustAccount;
    }

    public void setCustAccount(String custAccount) {
        CustAccount = custAccount;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }

    public int getCustDiscamount() {
        return CustDiscamount;
    }

    public void setCustDiscamount(int custDiscamount) {
        CustDiscamount = custDiscamount;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerState() {
        return CustomerState;
    }

    public void setCustomerState(String customerState) {
        CustomerState = customerState;
    }

    public String getDataAreaId() {
        return DataAreaId;
    }

    public void setDataAreaId(String dataAreaId) {
        DataAreaId = dataAreaId;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public int getDiscAmount() {
        return DiscAmount;
    }

    public void setDiscAmount(int discAmount) {
        DiscAmount = discAmount;
    }

    public String getDiscountRef() {
        return DiscountRef;
    }

    public void setDiscountRef(String discountRef) {
        DiscountRef = discountRef;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getDoctorCode() {
        return DoctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        DoctorCode = doctorCode;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getDSPCode() {
        return DSPCode;
    }

    public void setDSPCode(String DSPCode) {
        this.DSPCode = DSPCode;
    }

    public int getEntryStatus() {
        return EntryStatus;
    }

    public void setEntryStatus(int entryStatus) {
        EntryStatus = entryStatus;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public int getGrossAmount() {
        return GrossAmount;
    }

    public void setGrossAmount(int grossAmount) {
        GrossAmount = grossAmount;
    }

    public String getIPNO() {
        return IPNO;
    }

    public void setIPNO(String IPNO) {
        this.IPNO = IPNO;
    }

    public String getIPSerialNO() {
        return IPSerialNO;
    }

    public void setIPSerialNO(String IPSerialNO) {
        this.IPSerialNO = IPSerialNO;
    }

    public boolean isISAdvancePayment() {
        return ISAdvancePayment;
    }

    public void setISAdvancePayment(boolean ISAdvancePayment) {
        this.ISAdvancePayment = ISAdvancePayment;
    }

    public boolean isISBatchModifiedAllowed() {
        return ISBatchModifiedAllowed;
    }

    public void setISBatchModifiedAllowed(boolean ISBatchModifiedAllowed) {
        this.ISBatchModifiedAllowed = ISBatchModifiedAllowed;
    }

    public boolean isISHBPStore() {
        return ISHBPStore;
    }

    public void setISHBPStore(boolean ISHBPStore) {
        this.ISHBPStore = ISHBPStore;
    }

    public boolean isISHyperDelivered() {
        return ISHyperDelivered;
    }

    public void setISHyperDelivered(boolean ISHyperDelivered) {
        this.ISHyperDelivered = ISHyperDelivered;
    }

    public boolean isISHyperLocalDelivery() {
        return ISHyperLocalDelivery;
    }

    public void setISHyperLocalDelivery(boolean ISHyperLocalDelivery) {
        this.ISHyperLocalDelivery = ISHyperLocalDelivery;
    }

    public boolean isManualBill() {
        return IsManualBill;
    }

    public void setManualBill(boolean manualBill) {
        IsManualBill = manualBill;
    }

    public boolean isISOMSOrder() {
        return ISOMSOrder;
    }

    public void setISOMSOrder(boolean ISOMSOrder) {
        this.ISOMSOrder = ISOMSOrder;
    }

    public boolean isISOMSValidate() {
        return ISOMSValidate;
    }

    public void setISOMSValidate(boolean ISOMSValidate) {
        this.ISOMSValidate = ISOMSValidate;
    }

    public boolean isISOnlineOrder() {
        return ISOnlineOrder;
    }

    public void setISOnlineOrder(boolean ISOnlineOrder) {
        this.ISOnlineOrder = ISOnlineOrder;
    }

    public boolean isISPosted() {
        return ISPosted;
    }

    public void setISPosted(boolean ISPosted) {
        this.ISPosted = ISPosted;
    }

    public boolean isISPrescibeDiscount() {
        return ISPrescibeDiscount;
    }

    public void setISPrescibeDiscount(boolean ISPrescibeDiscount) {
        this.ISPrescibeDiscount = ISPrescibeDiscount;
    }

    public boolean isISReserved() {
        return ISReserved;
    }

    public void setISReserved(boolean ISReserved) {
        this.ISReserved = ISReserved;
    }

    public boolean isReturn() {
        return IsReturn;
    }

    public void setReturn(boolean aReturn) {
        IsReturn = aReturn;
    }

    public boolean isISReturnAllowed() {
        return ISReturnAllowed;
    }

    public void setISReturnAllowed(boolean ISReturnAllowed) {
        this.ISReturnAllowed = ISReturnAllowed;
    }

    public boolean isStockCheck() {
        return IsStockCheck;
    }

    public void setStockCheck(boolean stockCheck) {
        IsStockCheck = stockCheck;
    }

    public boolean isVoid() {
        return IsVoid;
    }

    public void setVoid(boolean aVoid) {
        IsVoid = aVoid;
    }

    public String getMobileNO() {
        return MobileNO;
    }

    public void setMobileNO(String mobileNO) {
        MobileNO = mobileNO;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }

    public int getNetAmountInclTax() {
        return NetAmountInclTax;
    }

    public void setNetAmountInclTax(int netAmountInclTax) {
        NetAmountInclTax = netAmountInclTax;
    }

    public int getNumberofItemLines() {
        return NumberofItemLines;
    }

    public void setNumberofItemLines(int numberofItemLines) {
        NumberofItemLines = numberofItemLines;
    }

    public int getNumberofItems() {
        return NumberofItems;
    }

    public void setNumberofItems(int numberofItems) {
        NumberofItems = numberofItems;
    }

    public int getOMSCreditAmount() {
        return OMSCreditAmount;
    }

    public void setOMSCreditAmount(int OMSCreditAmount) {
        this.OMSCreditAmount = OMSCreditAmount;
    }

    public String getOrderPrescriptionURL() {
        return OrderPrescriptionURL;
    }

    public void setOrderPrescriptionURL(String orderPrescriptionURL) {
        OrderPrescriptionURL = orderPrescriptionURL;
    }

    public String getOrderSource() {
        return OrderSource;
    }

    public void setOrderSource(String orderSource) {
        OrderSource = orderSource;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public String getPaymentSource() {
        return PaymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        PaymentSource = paymentSource;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public int getPosEvent() {
        return PosEvent;
    }

    public void setPosEvent(int posEvent) {
        PosEvent = posEvent;
    }

    public String getReciptId() {
        return ReciptId;
    }

    public void setReciptId(String reciptId) {
        ReciptId = reciptId;
    }

    public String getREFNO() {
        return REFNO;
    }

    public void setREFNO(String REFNO) {
        this.REFNO = REFNO;
    }

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public int getRemainingamount() {
        return Remainingamount;
    }

    public void setRemainingamount(int remainingamount) {
        Remainingamount = remainingamount;
    }

    public int getReminderDays() {
        return ReminderDays;
    }

    public void setReminderDays(int reminderDays) {
        ReminderDays = reminderDays;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        ReturnMessage = returnMessage;
    }

    public String getReturnReceiptId() {
        return ReturnReceiptId;
    }

    public void setReturnReceiptId(String returnReceiptId) {
        ReturnReceiptId = returnReceiptId;
    }

    public String getReturnStore() {
        return ReturnStore;
    }

    public void setReturnStore(String returnStore) {
        ReturnStore = returnStore;
    }

    public String getReturnTerminal() {
        return ReturnTerminal;
    }

    public void setReturnTerminal(String returnTerminal) {
        ReturnTerminal = returnTerminal;
    }

    public String getReturnTransactionId() {
        return ReturnTransactionId;
    }

    public void setReturnTransactionId(String returnTransactionId) {
        ReturnTransactionId = returnTransactionId;
    }

    public int getReturnType() {
        return ReturnType;
    }

    public void setReturnType(int returnType) {
        ReturnType = returnType;
    }

    public int getRoundedAmount() {
        return RoundedAmount;
    }

    public void setRoundedAmount(int roundedAmount) {
        RoundedAmount = roundedAmount;
    }

    public List<SalesTrackingDataReq.SalesLine> getSalesLine() {
        return SalesLine;
    }

    public void setSalesLine(List<SalesTrackingDataReq.SalesLine> salesLine) {
        SalesLine = salesLine;
    }

    public String getSalesOrigin() {
        return SalesOrigin;
    }

    public void setSalesOrigin(String salesOrigin) {
        SalesOrigin = salesOrigin;
    }

    public int getSEZ() {
        return SEZ;
    }

    public void setSEZ(int SEZ) {
        this.SEZ = SEZ;
    }

    public String getShippingMethod() {
        return ShippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        ShippingMethod = shippingMethod;
    }

    public String getShippingMethodDesc() {
        return ShippingMethodDesc;
    }

    public void setShippingMethodDesc(String shippingMethodDesc) {
        ShippingMethodDesc = shippingMethodDesc;
    }

    public String getStaff() {
        return Staff;
    }

    public void setStaff(String staff) {
        Staff = staff;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public List<SalesTrackingDataReq.TenderLine> getTenderLine() {
        return TenderLine;
    }

    public void setTenderLine(List<SalesTrackingDataReq.TenderLine> tenderLine) {
        TenderLine = tenderLine;
    }

    public String getTerminal() {
        return Terminal;
    }

    public void setTerminal(String terminal) {
        Terminal = terminal;
    }

    public int getTimewhenTransClosed() {
        return TimewhenTransClosed;
    }

    public void setTimewhenTransClosed(int timewhenTransClosed) {
        TimewhenTransClosed = timewhenTransClosed;
    }

    public int getTotalDiscAmount() {
        return TotalDiscAmount;
    }

    public void setTotalDiscAmount(int totalDiscAmount) {
        TotalDiscAmount = totalDiscAmount;
    }

    public int getTotalManualDiscountAmount() {
        return TotalManualDiscountAmount;
    }

    public void setTotalManualDiscountAmount(int totalManualDiscountAmount) {
        TotalManualDiscountAmount = totalManualDiscountAmount;
    }

    public int getTotalManualDiscountPercentage() {
        return TotalManualDiscountPercentage;
    }

    public void setTotalManualDiscountPercentage(int totalManualDiscountPercentage) {
        TotalManualDiscountPercentage = totalManualDiscountPercentage;
    }

    public int getTotalMRP() {
        return TotalMRP;
    }

    public void setTotalMRP(int totalMRP) {
        TotalMRP = totalMRP;
    }

    public int getTotalTaxAmount() {
        return TotalTaxAmount;
    }

    public void setTotalTaxAmount(int totalTaxAmount) {
        TotalTaxAmount = totalTaxAmount;
    }

    public String getTrackingRef() {
        return TrackingRef;
    }

    public void setTrackingRef(String trackingRef) {
        TrackingRef = trackingRef;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public int getTransType() {
        return TransType;
    }

    public void setTransType(int transType) {
        TransType = transType;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getVendorCode() {
        return VendorCode;
    }

    public void setVendorCode(String vendorCode) {
        VendorCode = vendorCode;
    }

    public String getVendorId() {
        return VendorId;
    }

    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }

    private class SalesLine {
        @Expose
        @SerializedName("Additionaltax")
        private int Additionaltax;

        @Expose
        @SerializedName("ApplyDiscount")
        private boolean ApplyDiscount;

        @Expose
        @SerializedName("Barcode")
        private String Barcode;

        @Expose
        @SerializedName("BaseAmount")
        private int BaseAmount;

        @Expose
        @SerializedName("Category")
        private String Category;

        @Expose
        @SerializedName("CategoryCode")
        private String CategoryCode;

        @Expose
        @SerializedName("CategoryReference")
        private String CategoryReference;

        @Expose
        @SerializedName("CESSPerc")
        private int CESSPerc;

        @Expose
        @SerializedName("CESSTaxCode")
        private String CESSTaxCode;

        @Expose
        @SerializedName("CGSTPerc")
        private int CGSTPerc;

        @Expose
        @SerializedName("CGSTTaxCode")
        private String CGSTTaxCode;

        @Expose
        @SerializedName("Comment")
        private String Comment;

        @Expose
        @SerializedName("DiscAmount")
        private int DiscAmount;

        @Expose
        @SerializedName("DiscId")
        private String DiscId;

        @Expose
        @SerializedName("DiscOfferId")
        private String DiscOfferId;


        @Expose
        @SerializedName("DiscountStructureType")
        private int DiscountStructureType;

        @Expose
        @SerializedName("DiscountType")
        private String DiscountType;

        @Expose
        @SerializedName("DiseaseType")
        private String DiseaseType;

        @Expose
        @SerializedName("DPCO")
        private boolean DPCO;

        @Expose
        @SerializedName("Expiry")
        private String Expiry;

        @Expose
        @SerializedName("Hsncode_In")
        private int Hsncode_In;

        @Expose
        @SerializedName("IGSTPerc")
        private int IGSTPerc;

        @Expose
        @SerializedName("IGSTTaxCode")
        private String IGSTTaxCode;

        @Expose
        @SerializedName("InventBatchId")
        private String InventBatchId;

        @Expose
        @SerializedName("IsChecked")
        private boolean IsChecked;

        @Expose
        @SerializedName("IsGeneric")
        private boolean IsGeneric;

        @Expose
        @SerializedName("ISPrescribed")
        private int ISPrescribed;

        @Expose
        @SerializedName("IsPriceOverride")
        private boolean IsPriceOverride;

        @Expose
        @SerializedName("ISReserved")
        private boolean ISReserved;

        @Expose
        @SerializedName("ISStockAvailable")
        private boolean ISStockAvailable;

        @Expose
        @SerializedName("IsSubsitute")
        private boolean IsSubsitute;

        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;

        @Expose
        @SerializedName("ItemId")
        private String ItemId;

        @Expose
        @SerializedName("ItemName")
        private String ItemName;

        @Expose
        @SerializedName("LineDiscPercentage")
        private int LineDiscPercentage;

        @Expose
        @SerializedName("LinedscAmount")
        private int LinedscAmount;

        @Expose
        @SerializedName("LineManualDiscountAmount")
        private int LineManualDiscountAmount;

        @Expose
        @SerializedName("LineManualDiscountPercentage")
        private int LineManualDiscountPercentage;

        @Expose
        @SerializedName("LineNo")
        private int LineNo;

        @Expose
        @SerializedName("ManufacturerCode")
        private String ManufacturerCode;

        @Expose
        @SerializedName("ManufacturerName")
        private String ManufacturerName;

        @Expose
        @SerializedName("MixMode")
        private boolean MixMode;

        @Expose
        @SerializedName("MMGroupId")
        private String MMGroupId;

        @Expose
        @SerializedName("ModifyBatchId")
        private String ModifyBatchId;

        @Expose
        @SerializedName("MRP")
        private double MRP;

        @Expose
        @SerializedName("NetAmount")
        private double NetAmount;

        @Expose
        @SerializedName("NetAmountInclTax")
        private int NetAmountInclTax;

        @Expose
        @SerializedName("OfferAmount")
        private int OfferAmount;

        @Expose
        @SerializedName("OfferDiscountType")
        private int OfferDiscountType;

        @Expose
        @SerializedName("OfferDiscountValue")
        private int OfferDiscountValue;

        @Expose
        @SerializedName("OfferQty")
        private int OfferQty;

        @Expose
        @SerializedName("OfferType")
        private int OfferType;

        @Expose
        @SerializedName("OmsLineID")
        private int OmsLineID;

        @Expose
        @SerializedName("OmsLineRECID")
        private int OmsLineRECID;

        @Expose
        @SerializedName("OrderStatus")
        private int OrderStatus;

        @Expose
        @SerializedName("OriginalPrice")
        private int OriginalPrice;

        @Expose
        @SerializedName("PeriodicDiscAmount")
        private int PeriodicDiscAmount;

        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;

        @Expose
        @SerializedName("Price")
        private double Price;

        @Expose
        @SerializedName("ProductRecID")
        private int ProductRecID;

        @Expose
        @SerializedName("Qty")
        private int Qty;

        @Expose
        @SerializedName("RemainderDays")
        private int RemainderDays;

        @Expose
        @SerializedName("RemainingQty")
        private int RemainingQty;

        @Expose
        @SerializedName("RetailCategoryRecID")
        private String RetailCategoryRecID;

        @Expose
        @SerializedName("RetailMainCategoryRecID")
        private String RetailMainCategoryRecID;

        @Expose
        @SerializedName("RetailSubCategoryRecID")
        private String RetailSubCategoryRecID;

        @Expose
        @SerializedName("ReturnQty")
        private int ReturnQty;

        @Expose
        @SerializedName("ScheduleCategory")
        private String ScheduleCategory;

        @Expose
        @SerializedName("ScheduleCategoryCode")
        private String ScheduleCategoryCode;

        @Expose
        @SerializedName("SGSTPerc")
        private int SGSTPerc;

        @Expose
        @SerializedName("SGSTTaxCode")
        private String SGSTTaxCode;

        @Expose
        @SerializedName("StockQty")
        private int StockQty;

        @Expose
        @SerializedName("SubCategory")
        private String SubCategory;

        @Expose
        @SerializedName("SubCategoryCode")
        private String SubCategoryCode;

        @Expose
        @SerializedName("SubClassification")
        private String SubClassification;

        @Expose
        @SerializedName("SubstitudeItemId")
        private String SubstitudeItemId;

        @Expose
        @SerializedName("Tax")
        private int Tax;

        @Expose
        @SerializedName("TaxAmount")
        private double TaxAmount;

        @Expose
        @SerializedName("Total")
        private int Total;

        @Expose
        @SerializedName("TotalDiscAmount")
        private int TotalDiscAmount;

        @Expose
        @SerializedName("TotalDiscPct")
        private int TotalDiscPct;

        @Expose
        @SerializedName("TotalRoundedAmount")
        private int TotalRoundedAmount;

        @Expose
        @SerializedName("TotalTax")
        private int TotalTax;

        @Expose
        @SerializedName("Unit")
        private String Unit;

        @Expose
        @SerializedName("UnitPrice")
        private double UnitPrice;

        @Expose
        @SerializedName("UnitQty")
        private int UnitQty;

        @Expose
        @SerializedName("VariantId")
        private String VariantId;

        public int getAdditionaltax() {
            return Additionaltax;
        }

        public void setAdditionaltax(int additionaltax) {
            Additionaltax = additionaltax;
        }

        public boolean isApplyDiscount() {
            return ApplyDiscount;
        }

        public void setApplyDiscount(boolean applyDiscount) {
            ApplyDiscount = applyDiscount;
        }

        public String getBarcode() {
            return Barcode;
        }

        public void setBarcode(String barcode) {
            Barcode = barcode;
        }

        public int getBaseAmount() {
            return BaseAmount;
        }

        public void setBaseAmount(int baseAmount) {
            BaseAmount = baseAmount;
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            CategoryCode = categoryCode;
        }

        public String getCategoryReference() {
            return CategoryReference;
        }

        public void setCategoryReference(String categoryReference) {
            CategoryReference = categoryReference;
        }

        public int getCESSPerc() {
            return CESSPerc;
        }

        public void setCESSPerc(int CESSPerc) {
            this.CESSPerc = CESSPerc;
        }

        public String getCESSTaxCode() {
            return CESSTaxCode;
        }

        public void setCESSTaxCode(String CESSTaxCode) {
            this.CESSTaxCode = CESSTaxCode;
        }

        public int getCGSTPerc() {
            return CGSTPerc;
        }

        public void setCGSTPerc(int CGSTPerc) {
            this.CGSTPerc = CGSTPerc;
        }

        public String getCGSTTaxCode() {
            return CGSTTaxCode;
        }

        public void setCGSTTaxCode(String CGSTTaxCode) {
            this.CGSTTaxCode = CGSTTaxCode;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public int getDiscAmount() {
            return DiscAmount;
        }

        public void setDiscAmount(int discAmount) {
            DiscAmount = discAmount;
        }

        public String getDiscId() {
            return DiscId;
        }

        public void setDiscId(String discId) {
            DiscId = discId;
        }

        public String getDiscOfferId() {
            return DiscOfferId;
        }

        public void setDiscOfferId(String discOfferId) {
            DiscOfferId = discOfferId;
        }

        public int getDiscountStructureType() {
            return DiscountStructureType;
        }

        public void setDiscountStructureType(int discountStructureType) {
            DiscountStructureType = discountStructureType;
        }

        public String getDiscountType() {
            return DiscountType;
        }

        public void setDiscountType(String discountType) {
            DiscountType = discountType;
        }

        public String getDiseaseType() {
            return DiseaseType;
        }

        public void setDiseaseType(String diseaseType) {
            DiseaseType = diseaseType;
        }

        public boolean isDPCO() {
            return DPCO;
        }

        public void setDPCO(boolean DPCO) {
            this.DPCO = DPCO;
        }

        public String getExpiry() {
            return Expiry;
        }

        public void setExpiry(String expiry) {
            Expiry = expiry;
        }

        public int getHsncode_In() {
            return Hsncode_In;
        }

        public void setHsncode_In(int hsncode_In) {
            Hsncode_In = hsncode_In;
        }

        public int getIGSTPerc() {
            return IGSTPerc;
        }

        public void setIGSTPerc(int IGSTPerc) {
            this.IGSTPerc = IGSTPerc;
        }

        public String getIGSTTaxCode() {
            return IGSTTaxCode;
        }

        public void setIGSTTaxCode(String IGSTTaxCode) {
            this.IGSTTaxCode = IGSTTaxCode;
        }

        public String getInventBatchId() {
            return InventBatchId;
        }

        public void setInventBatchId(String inventBatchId) {
            InventBatchId = inventBatchId;
        }

        public boolean isChecked() {
            return IsChecked;
        }

        public void setChecked(boolean checked) {
            IsChecked = checked;
        }

        public boolean isGeneric() {
            return IsGeneric;
        }

        public void setGeneric(boolean generic) {
            IsGeneric = generic;
        }

        public int getISPrescribed() {
            return ISPrescribed;
        }

        public void setISPrescribed(int ISPrescribed) {
            this.ISPrescribed = ISPrescribed;
        }

        public boolean isPriceOverride() {
            return IsPriceOverride;
        }

        public void setPriceOverride(boolean priceOverride) {
            IsPriceOverride = priceOverride;
        }

        public boolean isISReserved() {
            return ISReserved;
        }

        public void setISReserved(boolean ISReserved) {
            this.ISReserved = ISReserved;
        }

        public boolean isISStockAvailable() {
            return ISStockAvailable;
        }

        public void setISStockAvailable(boolean ISStockAvailable) {
            this.ISStockAvailable = ISStockAvailable;
        }

        public boolean isSubsitute() {
            return IsSubsitute;
        }

        public void setSubsitute(boolean subsitute) {
            IsSubsitute = subsitute;
        }

        public boolean isVoid() {
            return IsVoid;
        }

        public void setVoid(boolean aVoid) {
            IsVoid = aVoid;
        }

        public String getItemId() {
            return ItemId;
        }

        public void setItemId(String itemId) {
            ItemId = itemId;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public int getLineDiscPercentage() {
            return LineDiscPercentage;
        }

        public void setLineDiscPercentage(int lineDiscPercentage) {
            LineDiscPercentage = lineDiscPercentage;
        }

        public int getLinedscAmount() {
            return LinedscAmount;
        }

        public void setLinedscAmount(int linedscAmount) {
            LinedscAmount = linedscAmount;
        }

        public int getLineManualDiscountAmount() {
            return LineManualDiscountAmount;
        }

        public void setLineManualDiscountAmount(int lineManualDiscountAmount) {
            LineManualDiscountAmount = lineManualDiscountAmount;
        }

        public int getLineManualDiscountPercentage() {
            return LineManualDiscountPercentage;
        }

        public void setLineManualDiscountPercentage(int lineManualDiscountPercentage) {
            LineManualDiscountPercentage = lineManualDiscountPercentage;
        }

        public int getLineNo() {
            return LineNo;
        }

        public void setLineNo(int lineNo) {
            LineNo = lineNo;
        }

        public String getManufacturerCode() {
            return ManufacturerCode;
        }

        public void setManufacturerCode(String manufacturerCode) {
            ManufacturerCode = manufacturerCode;
        }

        public String getManufacturerName() {
            return ManufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            ManufacturerName = manufacturerName;
        }

        public boolean isMixMode() {
            return MixMode;
        }

        public void setMixMode(boolean mixMode) {
            MixMode = mixMode;
        }

        public String getMMGroupId() {
            return MMGroupId;
        }

        public void setMMGroupId(String MMGroupId) {
            this.MMGroupId = MMGroupId;
        }

        public String getModifyBatchId() {
            return ModifyBatchId;
        }

        public void setModifyBatchId(String modifyBatchId) {
            ModifyBatchId = modifyBatchId;
        }

        public double getMRP() {
            return MRP;
        }

        public void setMRP(double MRP) {
            this.MRP = MRP;
        }

        public double getNetAmount() {
            return NetAmount;
        }

        public void setNetAmount(double netAmount) {
            NetAmount = netAmount;
        }

        public int getNetAmountInclTax() {
            return NetAmountInclTax;
        }

        public void setNetAmountInclTax(int netAmountInclTax) {
            NetAmountInclTax = netAmountInclTax;
        }

        public int getOfferAmount() {
            return OfferAmount;
        }

        public void setOfferAmount(int offerAmount) {
            OfferAmount = offerAmount;
        }

        public int getOfferDiscountType() {
            return OfferDiscountType;
        }

        public void setOfferDiscountType(int offerDiscountType) {
            OfferDiscountType = offerDiscountType;
        }

        public int getOfferDiscountValue() {
            return OfferDiscountValue;
        }

        public void setOfferDiscountValue(int offerDiscountValue) {
            OfferDiscountValue = offerDiscountValue;
        }

        public int getOfferQty() {
            return OfferQty;
        }

        public void setOfferQty(int offerQty) {
            OfferQty = offerQty;
        }

        public int getOfferType() {
            return OfferType;
        }

        public void setOfferType(int offerType) {
            OfferType = offerType;
        }

        public int getOmsLineID() {
            return OmsLineID;
        }

        public void setOmsLineID(int omsLineID) {
            OmsLineID = omsLineID;
        }

        public int getOmsLineRECID() {
            return OmsLineRECID;
        }

        public void setOmsLineRECID(int omsLineRECID) {
            OmsLineRECID = omsLineRECID;
        }

        public int getOrderStatus() {
            return OrderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            OrderStatus = orderStatus;
        }

        public int getOriginalPrice() {
            return OriginalPrice;
        }

        public void setOriginalPrice(int originalPrice) {
            OriginalPrice = originalPrice;
        }

        public int getPeriodicDiscAmount() {
            return PeriodicDiscAmount;
        }

        public void setPeriodicDiscAmount(int periodicDiscAmount) {
            PeriodicDiscAmount = periodicDiscAmount;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public void setPreviewText(String previewText) {
            PreviewText = previewText;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double price) {
            Price = price;
        }

        public int getProductRecID() {
            return ProductRecID;
        }

        public void setProductRecID(int productRecID) {
            ProductRecID = productRecID;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int qty) {
            Qty = qty;
        }

        public int getRemainderDays() {
            return RemainderDays;
        }

        public void setRemainderDays(int remainderDays) {
            RemainderDays = remainderDays;
        }

        public int getRemainingQty() {
            return RemainingQty;
        }

        public void setRemainingQty(int remainingQty) {
            RemainingQty = remainingQty;
        }

        public String getRetailCategoryRecID() {
            return RetailCategoryRecID;
        }

        public void setRetailCategoryRecID(String retailCategoryRecID) {
            RetailCategoryRecID = retailCategoryRecID;
        }

        public String getRetailMainCategoryRecID() {
            return RetailMainCategoryRecID;
        }

        public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
            RetailMainCategoryRecID = retailMainCategoryRecID;
        }

        public String getRetailSubCategoryRecID() {
            return RetailSubCategoryRecID;
        }

        public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
            RetailSubCategoryRecID = retailSubCategoryRecID;
        }

        public int getReturnQty() {
            return ReturnQty;
        }

        public void setReturnQty(int returnQty) {
            ReturnQty = returnQty;
        }

        public String getScheduleCategory() {
            return ScheduleCategory;
        }

        public void setScheduleCategory(String scheduleCategory) {
            ScheduleCategory = scheduleCategory;
        }

        public String getScheduleCategoryCode() {
            return ScheduleCategoryCode;
        }

        public void setScheduleCategoryCode(String scheduleCategoryCode) {
            ScheduleCategoryCode = scheduleCategoryCode;
        }

        public int getSGSTPerc() {
            return SGSTPerc;
        }

        public void setSGSTPerc(int SGSTPerc) {
            this.SGSTPerc = SGSTPerc;
        }

        public String getSGSTTaxCode() {
            return SGSTTaxCode;
        }

        public void setSGSTTaxCode(String SGSTTaxCode) {
            this.SGSTTaxCode = SGSTTaxCode;
        }

        public int getStockQty() {
            return StockQty;
        }

        public void setStockQty(int stockQty) {
            StockQty = stockQty;
        }

        public String getSubCategory() {
            return SubCategory;
        }

        public void setSubCategory(String subCategory) {
            SubCategory = subCategory;
        }

        public String getSubCategoryCode() {
            return SubCategoryCode;
        }

        public void setSubCategoryCode(String subCategoryCode) {
            SubCategoryCode = subCategoryCode;
        }

        public String getSubClassification() {
            return SubClassification;
        }

        public void setSubClassification(String subClassification) {
            SubClassification = subClassification;
        }

        public String getSubstitudeItemId() {
            return SubstitudeItemId;
        }

        public void setSubstitudeItemId(String substitudeItemId) {
            SubstitudeItemId = substitudeItemId;
        }

        public int getTax() {
            return Tax;
        }

        public void setTax(int tax) {
            Tax = tax;
        }

        public double getTaxAmount() {
            return TaxAmount;
        }

        public void setTaxAmount(double taxAmount) {
            TaxAmount = taxAmount;
        }

        public int getTotal() {
            return Total;
        }

        public void setTotal(int total) {
            Total = total;
        }

        public int getTotalDiscAmount() {
            return TotalDiscAmount;
        }

        public void setTotalDiscAmount(int totalDiscAmount) {
            TotalDiscAmount = totalDiscAmount;
        }

        public int getTotalDiscPct() {
            return TotalDiscPct;
        }

        public void setTotalDiscPct(int totalDiscPct) {
            TotalDiscPct = totalDiscPct;
        }

        public int getTotalRoundedAmount() {
            return TotalRoundedAmount;
        }

        public void setTotalRoundedAmount(int totalRoundedAmount) {
            TotalRoundedAmount = totalRoundedAmount;
        }

        public int getTotalTax() {
            return TotalTax;
        }

        public void setTotalTax(int totalTax) {
            TotalTax = totalTax;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String unit) {
            Unit = unit;
        }

        public double getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            UnitPrice = unitPrice;
        }

        public int getUnitQty() {
            return UnitQty;
        }

        public void setUnitQty(int unitQty) {
            UnitQty = unitQty;
        }

        public String getVariantId() {
            return VariantId;
        }

        public void setVariantId(String variantId) {
            VariantId = variantId;
        }
    }

    private class TenderLine {

        @Expose
        @SerializedName("AmountCur")
        private int AmountCur;

        @Expose
        @SerializedName("AmountMst")
        private int AmountMst;

        @Expose
        @SerializedName("AmountTendered")
        private int AmountTendered;

        @Expose
        @SerializedName("BarCode")
        private String BarCode;

        @Expose
        @SerializedName("ExchRate")
        private int ExchRate;

        @Expose
        @SerializedName("ExchRateMst")
        private int ExchRateMst;

        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;

        @Expose
        @SerializedName("LineNo")
        private int LineNo;

        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;

        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;

        @Expose
        @SerializedName("RewardsPoint")
        private int RewardsPoint;

        @Expose
        @SerializedName("TenderId")
        private String TenderId;

        @Expose
        @SerializedName("TenderName")
        private String TenderName;

        @Expose
        @SerializedName("TenderType")
        private int TenderType;

        @Expose
        @SerializedName("WalletOrderId")
        private String WalletOrderId;

        @Expose
        @SerializedName("WalletTransactionID")
        private String WalletTransactionID;

        @Expose
        @SerializedName("WalletType")
        private int WalletType;

        public int getAmountCur() {
            return AmountCur;
        }

        public void setAmountCur(int amountCur) {
            AmountCur = amountCur;
        }

        public int getAmountMst() {
            return AmountMst;
        }

        public void setAmountMst(int amountMst) {
            AmountMst = amountMst;
        }

        public int getAmountTendered() {
            return AmountTendered;
        }

        public void setAmountTendered(int amountTendered) {
            AmountTendered = amountTendered;
        }

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String barCode) {
            BarCode = barCode;
        }

        public int getExchRate() {
            return ExchRate;
        }

        public void setExchRate(int exchRate) {
            ExchRate = exchRate;
        }

        public int getExchRateMst() {
            return ExchRateMst;
        }

        public void setExchRateMst(int exchRateMst) {
            ExchRateMst = exchRateMst;
        }

        public boolean isVoid() {
            return IsVoid;
        }

        public void setVoid(boolean aVoid) {
            IsVoid = aVoid;
        }

        public int getLineNo() {
            return LineNo;
        }

        public void setLineNo(int lineNo) {
            LineNo = lineNo;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String mobileNo) {
            MobileNo = mobileNo;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public void setPreviewText(String previewText) {
            PreviewText = previewText;
        }

        public int getRewardsPoint() {
            return RewardsPoint;
        }

        public void setRewardsPoint(int rewardsPoint) {
            RewardsPoint = rewardsPoint;
        }

        public String getTenderId() {
            return TenderId;
        }

        public void setTenderId(String tenderId) {
            TenderId = tenderId;
        }

        public String getTenderName() {
            return TenderName;
        }

        public void setTenderName(String tenderName) {
            TenderName = tenderName;
        }

        public int getTenderType() {
            return TenderType;
        }

        public void setTenderType(int tenderType) {
            TenderType = tenderType;
        }

        public String getWalletOrderId() {
            return WalletOrderId;
        }

        public void setWalletOrderId(String walletOrderId) {
            WalletOrderId = walletOrderId;
        }

        public String getWalletTransactionID() {
            return WalletTransactionID;
        }

        public void setWalletTransactionID(String walletTransactionID) {
            WalletTransactionID = walletTransactionID;
        }

        public int getWalletType() {
            return WalletType;
        }

        public void setWalletType(int walletType) {
            WalletType = walletType;
        }
    }
}