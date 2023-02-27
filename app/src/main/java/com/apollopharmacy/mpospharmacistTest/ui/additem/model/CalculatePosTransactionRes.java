package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalculatePosTransactionRes implements Serializable {
    @Expose
    @SerializedName("VendorId")
    private String VendorId;
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
    @SerializedName("Terminal")
    private String Terminal;
    @Expose
    @SerializedName("TenderLine")
    private List<TenderLineEntity> TenderLine = new ArrayList<>();
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
    @SerializedName("ShippingMethodDesc")
    private String ShippingMethodDesc;

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
    private int ReturnType;

    public void setReturnType(int returnType) {
        ReturnType = returnType;
    }

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

    public void setRemainingamount(double remainingamount) {
        Remainingamount = remainingamount;
    }

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
    @Expose
    @SerializedName("OrderType")
    private String OrderType;
    @Expose
    @SerializedName("OrderSource")
    private String OrderSource;
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
    @SerializedName("IsStockCheck")
    private boolean IsStockCheck;
    @Expose
    @SerializedName("IsReturn")
    private boolean IsReturn;
    @Expose
    @SerializedName("IsRepeatBill")
    private boolean IsRepeatBill;

    @Expose
    @SerializedName("IsMPOSBill")
    private int IsMPOSBill;

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
    private double GrossAmount;
    @Expose
    @SerializedName("Gender")
    private double Gender;
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
    @SerializedName("DiscAmount")
    private double DiscAmount;
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
    private double CustDiscamount;
    @Expose
    @SerializedName("CustAddress")
    private String CustAddress;
    @Expose
    @SerializedName("CustomerType")
    private String CustomerType;
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
    private double AmounttoAccount;

    @Expose
    @SerializedName("CurrentSalesLine")
    private double CurrentSalesLine;


    private double paymentDoneAmount = 0.0;

    public double getPaymentDoneAmount() {
        return paymentDoneAmount;
    }

    public void setPaymentDoneAmount(double paymentDoneAmount) {
        this.paymentDoneAmount = paymentDoneAmount;
    }

    public String getVendorId() {
        return VendorId;
    }

    public double getType() {
        return Type;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
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

    public void setTrackingRef(String trackingRef) {
        TrackingRef = trackingRef;
    }

    public double getTotalTaxAmount() {
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

    public String getShippingMethodDesc() {
        return ShippingMethodDesc;
    }

    public String getSalesOrigin() {
        return SalesOrigin;
    }

    public ArrayList<SalesLineEntity> getSalesLine() {
        return SalesLine;
    }

    public void setSalesLine(ArrayList<SalesLineEntity> salesLine) {
        SalesLine = salesLine;
    }

    public double getSEZ() {
        return SEZ;
    }

    public double getRoundedAmount() {
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

    public void setReturnTerminal(String returnTerminal) {
        ReturnTerminal = returnTerminal;
    }

    public void setReturnStore(String returnStore) {
        ReturnStore = returnStore;
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

    public double getPosEvent() {
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

    public double getGrossAmount() {
        return GrossAmount;
    }

    public double getGender() {
        return Gender;
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

    public double getDiscAmount() {
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

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
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

    public double getAmounttoAccount() {
        return AmounttoAccount;
    }

    public double getCurrentSalesLine() {
        return CurrentSalesLine;
    }
    public void setReturn(boolean aReturn) {
        IsReturn = aReturn;
    }
    public String getCustomerType() {
        return CustomerType;
    }

    public double PharmaTotalAmount;
    public double FmcgTotalAmount;
    public double PlTotalAmount;
    public double totalRoundedAmount;
    public double MrpTotalAmount;
    public double TaxableTotalAmount;
    public double DiscTotalAmount;
    public double OrderTotalAmount;
    public double OrderSavingsAmount;
    public double OrderSavingsPercentage;


    public double getPharmaTotalAmount() {
        return PharmaTotalAmount;
    }

    public void setPharmaTotalAmount(double pharmaTotalAmount) {
        PharmaTotalAmount = pharmaTotalAmount;
    }

    public double getFmcgTotalAmount() {
        return FmcgTotalAmount;
    }

    public void setFmcgTotalAmount(double fmcgTotalAmount) {
        FmcgTotalAmount = fmcgTotalAmount;
    }

    public double getPlTotalAmount() {
        return PlTotalAmount;
    }

    public void setPlTotalAmount(double plTotalAmount) {
        PlTotalAmount = plTotalAmount;
    }

    public double getTotalRoundedAmount() {
        return totalRoundedAmount;
    }

    public void setTotalRoundedAmount(double totalRoundedAmount) {
        this.totalRoundedAmount = totalRoundedAmount;
    }

    public double getMrpTotalAmount() {
        return MrpTotalAmount;
    }

    public void setMrpTotalAmount(double mrpTotalAmount) {
        MrpTotalAmount = mrpTotalAmount;
    }

    public double getTaxableTotalAmount() {
        return TaxableTotalAmount;
    }

    public void setTaxableTotalAmount(double taxableTotalAmount) {
        TaxableTotalAmount = taxableTotalAmount;
    }

    public double getDiscTotalAmount() {
        return DiscTotalAmount;
    }

    public void setDiscTotalAmount(double discTotalAmount) {
        DiscTotalAmount = discTotalAmount;
    }

    public double getOrderTotalAmount() {
        return OrderTotalAmount;
    }

    public void setOrderTotalAmount(double orderTotalAmount) {
        OrderTotalAmount = orderTotalAmount;
    }

    public double getOrderSavingsAmount() {
        return OrderSavingsAmount;
    }

    public void setOrderSavingsAmount(double orderSavingsAmount) {
        OrderSavingsAmount = orderSavingsAmount;
    }

    public double getOrderSavingsPercentage() {
        return OrderSavingsPercentage;
    }

    public void setOrderSavingsPercentage(double orderSavingsPercentage) {
        OrderSavingsPercentage = orderSavingsPercentage;
    }

    public void setGender(double gender) {
        Gender = gender;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public void setDoctorCode(String doctorCode) {
        DoctorCode = doctorCode;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setCustomerState(String customerState) {
        CustomerState = customerState;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }


    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }

    public void setType(double type) {
        Type = type;
    }

    public void setTransType(double transType) {
        TransType = transType;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        TotalTaxAmount = totalTaxAmount;
    }

    public void setTotalManualDiscountPercentage(double totalManualDiscountPercentage) {
        TotalManualDiscountPercentage = totalManualDiscountPercentage;
    }

    public void setTotalManualDiscountAmount(double totalManualDiscountAmount) {
        TotalManualDiscountAmount = totalManualDiscountAmount;
    }

    public void setTotalMRP(double totalMRP) {
        TotalMRP = totalMRP;
    }

    public void setTotalDiscAmount(double totalDiscAmount) {
        TotalDiscAmount = totalDiscAmount;
    }

    public void setTimewhenTransClosed(double timewhenTransClosed) {
        TimewhenTransClosed = timewhenTransClosed;
    }

    public void setTerminal(String terminal) {
        Terminal = terminal;
    }

    public void setTenderLine(List<TenderLineEntity> tenderLine) {
        TenderLine = tenderLine;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public void setStore(String store) {
        Store = store;
    }

    public void setState(String state) {
        State = state;
    }

    public void setStaff(String staff) {
        Staff = staff;
    }

    public void setShippingMethod(String shippingMethod) {
        ShippingMethod = shippingMethod;
    }

    public void setShippingMethodDesc(String shippingMethodDesc) {
        ShippingMethodDesc = shippingMethodDesc;
    }

    public void setSalesOrigin(String salesOrigin) {
        SalesOrigin = salesOrigin;
    }

    public void setSEZ(double SEZ) {
        this.SEZ = SEZ;
    }

    public void setRoundedAmount(double roundedAmount) {
        RoundedAmount = roundedAmount;
    }

    public void setReturnTransactionId(String returnTransactionId) {
        ReturnTransactionId = returnTransactionId;
    }

    public void setReturnReceiptId(String returnReceiptId) {
        ReturnReceiptId = returnReceiptId;
    }

    public void setReturnMessage(String returnMessage) {
        ReturnMessage = returnMessage;
    }

    public void setRequestStatus(double requestStatus) {
        RequestStatus = requestStatus;
    }

    public void setReminderDays(double reminderDays) {
        ReminderDays = reminderDays;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public void setReciptId(String reciptId) {
        ReciptId = reciptId;
    }

    public void setREFNO(String REFNO) {
        this.REFNO = REFNO;
    }

    public void setPosEvent(double posEvent) {
        PosEvent = posEvent;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public void setPaymentSource(String paymentSource) {
        PaymentSource = paymentSource;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public void setOrderSource(String orderSource) {
        OrderSource = orderSource;
    }

    public void setNumberofItems(double numberofItems) {
        NumberofItems = numberofItems;
    }

    public void setNumberofItemLines(double numberofItemLines) {
        NumberofItemLines = numberofItemLines;
    }

    public void setNetAmountInclTax(double netAmountInclTax) {
        NetAmountInclTax = netAmountInclTax;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }

    public void setMobileNO(String mobileNO) {
        MobileNO = mobileNO;
    }

    public void setVoid(boolean aVoid) {
        IsVoid = aVoid;
    }

    public void setStockCheck(boolean stockCheck) {
        IsStockCheck = stockCheck;
    }

    public void setRepeatBill(boolean repeatBill) {
        IsRepeatBill = repeatBill;
    }

    public void setManualBill(boolean manualBill) {
        IsManualBill = manualBill;
    }

    public int getIsMPOSBill() {
        return IsMPOSBill;
    }

    public void setIsMPOSBill(int isMPOSBill) {
        IsMPOSBill = isMPOSBill;
    }

    public void setISReturnAllowed(boolean ISReturnAllowed) {
        this.ISReturnAllowed = ISReturnAllowed;
    }

    public void setISReserved(boolean ISReserved) {
        this.ISReserved = ISReserved;
    }

    public void setISPrescibeDiscount(boolean ISPrescibeDiscount) {
        this.ISPrescibeDiscount = ISPrescibeDiscount;
    }

    public void setISPosted(boolean ISPosted) {
        this.ISPosted = ISPosted;
    }

    public void setISOMSOrder(boolean ISOMSOrder) {
        this.ISOMSOrder = ISOMSOrder;
    }

    public void setISBatchModifiedAllowed(boolean ISBatchModifiedAllowed) {
        this.ISBatchModifiedAllowed = ISBatchModifiedAllowed;
    }

    public void setISAdvancePayment(boolean ISAdvancePayment) {
        this.ISAdvancePayment = ISAdvancePayment;
    }

    public void setIPSerialNO(String IPSerialNO) {
        this.IPSerialNO = IPSerialNO;
    }

    public void setIPNO(String IPNO) {
        this.IPNO = IPNO;
    }

    public void setGrossAmount(double grossAmount) {
        GrossAmount = grossAmount;
    }

    public void setEntryStatus(double entryStatus) {
        EntryStatus = entryStatus;
    }

    public void setDiscAmount(double discAmount) {
        DiscAmount = discAmount;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public void setDataAreaId(String dataAreaId) {
        DataAreaId = dataAreaId;
    }

    public void setCustDiscamount(double custDiscamount) {
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

    public void setCorpCode(String corpCode) {
        CorpCode = corpCode;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        CancelReasonCode = cancelReasonCode;
    }

    public void setBusinessDate(String businessDate) {
        BusinessDate = businessDate;
    }

    public void setBillingCity(String billingCity) {
        BillingCity = billingCity;
    }

    public void setBatchTerminalid(String batchTerminalid) {
        BatchTerminalid = batchTerminalid;
    }

    public void setAmounttoAccount(double amounttoAccount)
    {
        AmounttoAccount = amounttoAccount;
    }

    public void setCurrentSalesLine(double currentSalesLine)
    {
        CurrentSalesLine = currentSalesLine;
    }

    public  void setCustomerType(String customerType1)
    {
        CustomerType=customerType1;
    }

    @Expose
    @SerializedName("Tier")
    private String Tier;
    public  String getTier()
    {
        return  Tier;
    }
    public  void setTier(String tier)
    {
        Tier=tier;
    }

    @Expose
    @SerializedName("StockStatus")
    private String StockStatus;
    public  String getStockStatus()
    {
        return  StockStatus;
    }
    public  void  setStockStatus(String stockStatus)
    {
        StockStatus=stockStatus;
    }

    @Expose
    @SerializedName("ShippingCharges")
    private double ShippingCharges;

    public  double getShippingCharges()
    {
        return  ShippingCharges;
    }
    public  void setShippingCharges(double shippingCharges)
    {
        ShippingCharges=shippingCharges;
    }

    @Expose
    @SerializedName("OrderPrescriptionURL")
    private ArrayList<CustomerDataResBean.OrderPrescriptionObj> OrderPrescriptionURL;

    public ArrayList<CustomerDataResBean.OrderPrescriptionObj> getOrderPrescriptionURL() {
        return OrderPrescriptionURL;
    }

    public void setOrderPrescriptionURL(ArrayList<CustomerDataResBean.OrderPrescriptionObj> orderPrescriptionURL)
    {
        OrderPrescriptionURL=orderPrescriptionURL;
    }

    @Expose
    @SerializedName("OMSCreditAmount")
    private double OMSCreditAmount;

    public double getOMSCreditAmount()
    {
        return  OMSCreditAmount;
    }

    public  void setOMSCreditAmount(double omsCreditAmount)
    {
        OMSCreditAmount=omsCreditAmount;
    }

    @Expose
    @SerializedName("IsUHIDBilling")
    private boolean IsUHIDBilling;
    public Boolean getIsUHIDBilling() {
        return IsUHIDBilling;
    }

    public void setIsUHIDBilling(Boolean isUHIDBilling) {
        this.IsUHIDBilling = isUHIDBilling;
    }

    @Expose
    @SerializedName("ISOnlineOrder")
    private boolean ISOnlineOrder;

    public Boolean getISOnlineOrder() {
        return ISOnlineOrder;
    }

    public void setISOnlineOrder(Boolean iSOnlineOrder) {
        this.ISOnlineOrder = iSOnlineOrder;
    }

    @Expose
    @SerializedName("ISOMSValidate")
    private boolean ISOMSValidate;

    public Boolean getISOMSValidate() {
        return ISOMSValidate;
    }

    public void setISOMSValidate(Boolean iSOMSValidate) {
        this.ISOMSValidate = iSOMSValidate;
    }


    @Expose
    @SerializedName("ISHyperLocalDelivery")
    private boolean ISHyperLocalDelivery;
    public Boolean getISHyperLocalDelivery() {
        return ISHyperLocalDelivery;
    }

    public void setISHyperLocalDelivery(Boolean iSHyperLocalDelivery) {
        this.ISHyperLocalDelivery = iSHyperLocalDelivery;
    }




    @Expose
    @SerializedName("ISHyperDelivered")
    private boolean ISHyperDelivered;
    public Boolean getISHyperDelivered() {
        return ISHyperDelivered;
    }

    public void setISHyperDelivered(Boolean iSHyperDelivered) {
        this.ISHyperDelivered = iSHyperDelivered;
    }


    @Expose
    @SerializedName("ISHBPStore")
    private boolean ISHBPStore;

    public Boolean getISHBPStore() {
        return ISHBPStore;
    }

    public void setISHBPStore(Boolean iSHBPStore) {
        this.ISHBPStore = iSHBPStore;
    }
    @Expose
    @SerializedName("ISCancelled")
    private boolean ISCancelled;
    public Boolean getISCancelled() {
        return ISCancelled;
    }

    public void setISCancelled(Boolean iSCancelled) {
        this.ISCancelled = iSCancelled;
    }
    @Expose
    @SerializedName("ISBulkBilling")
    private boolean ISBulkBilling;

    public Boolean getISBulkBilling() {
        return ISBulkBilling;
    }

    public void setISBulkBilling(Boolean iSBulkBilling) {
        this.ISBulkBilling = iSBulkBilling;
    }
    @Expose
    @SerializedName("HCOfferCode")
    private String HCOfferCode;
    public String getHCOfferCode() {
        return HCOfferCode;
    }

    public void setHCOfferCode(String hCOfferCode) {
        this.HCOfferCode = hCOfferCode;
    }

    @Expose
    @SerializedName("ExpiryDays")
    private double ExpiryDays;
    public double getExpiryDays() {
        return ExpiryDays;
    }

    public void setExpiryDays(double expiryDays) {
        this.ExpiryDays = expiryDays;
    }

    @Expose
    @SerializedName("DiscountStatus")
    private double DiscountStatus;
    public double getDiscountStatus() {
        return DiscountStatus;
    }

    public void setDiscountStatus(double discountStatus) {
        this.DiscountStatus = discountStatus;
    }
    @Expose
    @SerializedName("DiscountReferenceID")
    private String DiscountReferenceID;
    public String getDiscountReferenceID() {
        return DiscountReferenceID;
    }

    public void setDiscountReferenceID(String discountReferenceID) {
        this.DiscountReferenceID = discountReferenceID;
    }

    @Expose
    @SerializedName("DiscountRef")
    private String DiscountRef;
    public String getDiscountRef() {
        return DiscountRef;
    }

    public void setDiscountRef(String discountRef) {
        this.DiscountRef = discountRef;
    }

    @Expose
    @SerializedName("DSPCode")
    private String DSPCode;
    public String getDSPCode() {
        return DSPCode;
    }

    public void setDSPCode(String dSPCode) {
        this.DSPCode = dSPCode;
    }

    @Expose
    @SerializedName("CreatedDateTime")
    private String CreatedDateTime;
    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.CreatedDateTime = createdDateTime;
    }
    @Expose
    @SerializedName("ApprovedID")
    private String ApprovedID;
    public String getApprovedID() {
        return ApprovedID;
    }

    public void setApprovedID(String approvedID) {
        this.ApprovedID = approvedID;
    }

    @Expose
    @SerializedName("AllowedTenderType")
    private String AllowedTenderType;
    public String getAllowedTenderType() {
        return AllowedTenderType;
    }

    public void setAllowedTenderType(String allowedTenderType) {
        this.AllowedTenderType = allowedTenderType;
    }
    @Expose
    @SerializedName("AgeGroup")
    private String AgeGroup;
    public String getAgeGroup() {
        return AgeGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.AgeGroup = ageGroup;
    }
    @Expose
    @SerializedName("AWBNo")
    private String AWBNo;
    public String getAWBNo() {
        return AWBNo;
    }

    public void setAWBNo(String aWBNo) {
        this.AWBNo = aWBNo;
    }

    @Expose
    @SerializedName("VendorCode")
    private String VendorCode;
    public String getVendorCode()
    {
        return VendorCode;
    }

    public  void setVendorCode(String vendorCode)
    {
        this.VendorCode=vendorCode;
    }


    @Expose
    @SerializedName("PatientID")
    private String PatientID;
    public String getPatientID()
    {
        return  PatientID;
    }

    public  void setPatientID(String patientID)
    {
        this.PatientID=patientID;
    }


}
