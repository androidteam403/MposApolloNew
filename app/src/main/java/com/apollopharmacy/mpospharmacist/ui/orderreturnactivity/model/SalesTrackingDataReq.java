package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesTrackingDataReq implements Serializable {
    @SerializedName("Remainingamount")
    @Expose
    private int remainingamount;
    @SerializedName("ISOnlineOrder")
    @Expose
    private boolean iSOnlineOrder;
    @SerializedName("VendorCode")
    @Expose
    private String vendorCode;
    @SerializedName("ISReserved")
    @Expose
    private boolean iSReserved;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("OrderType")
    @Expose
    private String orderType;
    @SerializedName("OrderSource")
    @Expose
    private String orderSource;
    @SerializedName("ShippingMethod")
    @Expose
    private String shippingMethod;
    @SerializedName("ShippingMethodDesc")
    @Expose
    private String shippingMethodDesc;
    @SerializedName("BillingCity")
    @Expose
    private String billingCity;
    @SerializedName("VendorId")
    @Expose
    private String vendorId;
    @SerializedName("PaymentSource")
    @Expose
    private String paymentSource;
    @SerializedName("ISPrescibeDiscount")
    @Expose
    private boolean iSPrescibeDiscount;
    @SerializedName("CancelReasonCode")
    @Expose
    private String cancelReasonCode;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("RegionCode")
    @Expose
    private String regionCode;
    @SerializedName("CustomerID")
    @Expose
    private String customerID;
    @SerializedName("CorpCode")
    @Expose
    private String corpCode;
    @SerializedName("MobileNO")
    @Expose
    private String mobileNO;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustAddress")
    @Expose
    private String custAddress;
    @SerializedName("CustomerState")
    @Expose
    private String customerState;
    @SerializedName("Gender")
    @Expose
    private int gender;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("DoctorCode")
    @Expose
    private String doctorCode;
    @SerializedName("SalesOrigin")
    @Expose
    private String salesOrigin;
    @SerializedName("TrackingRef")
    @Expose
    private String trackingRef;
    @SerializedName("REFNO")
    @Expose
    private String rEFNO;
    @SerializedName("IPNO")
    @Expose
    private String iPNO;
    @SerializedName("IPSerialNO")
    @Expose
    private String iPSerialNO;
    @SerializedName("ReciptId")
    @Expose
    private String reciptId;
    @SerializedName("BatchTerminalid")
    @Expose
    private String batchTerminalid;
    @SerializedName("BusinessDate")
    @Expose
    private String businessDate;
    @SerializedName("Channel")
    @Expose
    private String channel;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("CreatedonPosTerminal")
    @Expose
    private String createdonPosTerminal;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("CustAccount")
    @Expose
    private String custAccount;
    @SerializedName("CustDiscamount")
    @Expose
    private int custDiscamount;
    @SerializedName("DiscAmount")
    @Expose
    private int discAmount;
    @SerializedName("EntryStatus")
    @Expose
    private int entryStatus;
    @SerializedName("GrossAmount")
    @Expose
    private int grossAmount;
    @SerializedName("NetAmount")
    @Expose
    private float netAmount;
    @SerializedName("NetAmountInclTax")
    @Expose
    private int netAmountInclTax;
    @SerializedName("NumberofItemLines")
    @Expose
    private int numberofItemLines;
    @SerializedName("NumberofItems")
    @Expose
    private int numberofItems;
    @SerializedName("RoundedAmount")
    @Expose
    private int roundedAmount;
    @SerializedName("Staff")
    @Expose
    private String staff;
    @SerializedName("Store")
    @Expose
    private String store;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Terminal")
    @Expose
    private String terminal;
    @SerializedName("ReturnStore")
    @Expose
    private String returnStore;
    @SerializedName("ReturnTerminal")
    @Expose
    private String returnTerminal;
    @SerializedName("ReturnTransactionId")
    @Expose
    private String returnTransactionId;
    @SerializedName("ReturnReceiptId")
    @Expose
    private String returnReceiptId;
    @SerializedName("TimewhenTransClosed")
    @Expose
    private int timewhenTransClosed;
    @SerializedName("TotalDiscAmount")
    @Expose
    private int totalDiscAmount;
    @SerializedName("TotalManualDiscountAmount")
    @Expose
    private int totalManualDiscountAmount;
    @SerializedName("TotalManualDiscountPercentage")
    @Expose
    private int totalManualDiscountPercentage;
    @SerializedName("TotalMRP")
    @Expose
    private int totalMRP;
    @SerializedName("TotalTaxAmount")
    @Expose
    private int totalTaxAmount;
    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("TransDate")
    @Expose
    private String transDate;
    @SerializedName("Type")
    @Expose
    private int type;
    @SerializedName("DataAreaId")
    @Expose
    private String dataAreaId;
    @SerializedName("IsVoid")
    @Expose
    private boolean isVoid;
    @SerializedName("IsReturn")
    @Expose
    private boolean isReturn;
    @SerializedName("ISBatchModifiedAllowed")
    @Expose
    private boolean iSBatchModifiedAllowed;
    @SerializedName("ISReturnAllowed")
    @Expose
    private boolean iSReturnAllowed;
    @SerializedName("IsManualBill")
    @Expose
    private boolean isManualBill;
    @SerializedName("ReturnType")
    @Expose
    private int returnType;
    @SerializedName("CurrentSalesLine")
    @Expose
    private int currentSalesLine;
    @SerializedName("RequestStatus")
    @Expose
    private int requestStatus;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("PosEvent")
    @Expose
    private int posEvent;
    @SerializedName("TransType")
    @Expose
    private int transType;
    @SerializedName("IsStockCheck")
    @Expose
    private boolean isStockCheck;
    @SerializedName("ISPosted")
    @Expose
    private boolean iSPosted;
    @SerializedName("SEZ")
    @Expose
    private int sEZ;
    @SerializedName("CouponCode")
    @Expose
    private String couponCode;
    @SerializedName("ISAdvancePayment")
    @Expose
    private boolean iSAdvancePayment;
    @SerializedName("AmounttoAccount")
    @Expose
    private int amounttoAccount;
    @SerializedName("ReminderDays")
    @Expose
    private int reminderDays;
    @SerializedName("ISOMSOrder")
    @Expose
    private boolean iSOMSOrder;
    @SerializedName("ISHBPStore")
    @Expose
    private boolean iSHBPStore;
    @SerializedName("PatientID")
    @Expose
    private String patientID;
    @SerializedName("ApprovedID")
    @Expose
    private String approvedID;
    @SerializedName("DiscountRef")
    @Expose
    private String discountRef;
    @SerializedName("AWBNo")
    @Expose
    private String aWBNo;
    @SerializedName("DSPCode")
    @Expose
    private String dSPCode;
    @SerializedName("ISHyperLocalDelivery")
    @Expose
    private boolean iSHyperLocalDelivery;
    @SerializedName("ISHyperDelivered")
    @Expose
    private boolean iSHyperDelivered;
    @SerializedName("CreatedDateTime")
    @Expose
    private String createdDateTime;
    @SerializedName("OMSCreditAmount")
    @Expose
    private int oMSCreditAmount;
    @SerializedName("ISOMSValidate")
    @Expose
    private boolean iSOMSValidate;
    @SerializedName("AllowedTenderType")
    @Expose
    private String allowedTenderType;
    @SerializedName("SalesLine")
    @Expose
    private List<SalesLine> salesLine = null;
    @SerializedName("TenderLine")
    @Expose
    private List<TenderLine> tenderLine = null;
    @SerializedName("OrderPrescriptionURL")
    @Expose
    private String orderPrescriptionURL;

    public int getRemainingamount() {
        return remainingamount;
    }

    public void setRemainingamount(int remainingamount) {
        this.remainingamount = remainingamount;
    }

    public boolean isISOnlineOrder() {
        return iSOnlineOrder;
    }

    public void setISOnlineOrder(boolean iSOnlineOrder) {
        this.iSOnlineOrder = iSOnlineOrder;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public boolean isISReserved() {
        return iSReserved;
    }

    public void setISReserved(boolean iSReserved) {
        this.iSReserved = iSReserved;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingMethodDesc() {
        return shippingMethodDesc;
    }

    public void setShippingMethodDesc(String shippingMethodDesc) {
        this.shippingMethodDesc = shippingMethodDesc;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
    }

    public boolean isISPrescibeDiscount() {
        return iSPrescibeDiscount;
    }

    public void setISPrescibeDiscount(boolean iSPrescibeDiscount) {
        this.iSPrescibeDiscount = iSPrescibeDiscount;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getSalesOrigin() {
        return salesOrigin;
    }

    public void setSalesOrigin(String salesOrigin) {
        this.salesOrigin = salesOrigin;
    }

    public String getTrackingRef() {
        return trackingRef;
    }

    public void setTrackingRef(String trackingRef) {
        this.trackingRef = trackingRef;
    }

    public String getREFNO() {
        return rEFNO;
    }

    public void setREFNO(String rEFNO) {
        this.rEFNO = rEFNO;
    }

    public String getIPNO() {
        return iPNO;
    }

    public void setIPNO(String iPNO) {
        this.iPNO = iPNO;
    }

    public String getIPSerialNO() {
        return iPSerialNO;
    }

    public void setIPSerialNO(String iPSerialNO) {
        this.iPSerialNO = iPSerialNO;
    }

    public String getReciptId() {
        return reciptId;
    }

    public void setReciptId(String reciptId) {
        this.reciptId = reciptId;
    }

    public String getBatchTerminalid() {
        return batchTerminalid;
    }

    public void setBatchTerminalid(String batchTerminalid) {
        this.batchTerminalid = batchTerminalid;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedonPosTerminal() {
        return createdonPosTerminal;
    }

    public void setCreatedonPosTerminal(String createdonPosTerminal) {
        this.createdonPosTerminal = createdonPosTerminal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustAccount() {
        return custAccount;
    }

    public void setCustAccount(String custAccount) {
        this.custAccount = custAccount;
    }

    public int getCustDiscamount() {
        return custDiscamount;
    }

    public void setCustDiscamount(int custDiscamount) {
        this.custDiscamount = custDiscamount;
    }

    public int getDiscAmount() {
        return discAmount;
    }

    public void setDiscAmount(int discAmount) {
        this.discAmount = discAmount;
    }

    public int getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(int entryStatus) {
        this.entryStatus = entryStatus;
    }

    public int getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(int grossAmount) {
        this.grossAmount = grossAmount;
    }

    public float getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(float netAmount) {
        this.netAmount = netAmount;
    }

    public int getNetAmountInclTax() {
        return netAmountInclTax;
    }

    public void setNetAmountInclTax(int netAmountInclTax) {
        this.netAmountInclTax = netAmountInclTax;
    }

    public int getNumberofItemLines() {
        return numberofItemLines;
    }

    public void setNumberofItemLines(int numberofItemLines) {
        this.numberofItemLines = numberofItemLines;
    }

    public int getNumberofItems() {
        return numberofItems;
    }

    public void setNumberofItems(int numberofItems) {
        this.numberofItems = numberofItems;
    }

    public int getRoundedAmount() {
        return roundedAmount;
    }

    public void setRoundedAmount(int roundedAmount) {
        this.roundedAmount = roundedAmount;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getReturnStore() {
        return returnStore;
    }

    public void setReturnStore(String returnStore) {
        this.returnStore = returnStore;
    }

    public String getReturnTerminal() {
        return returnTerminal;
    }

    public void setReturnTerminal(String returnTerminal) {
        this.returnTerminal = returnTerminal;
    }

    public String getReturnTransactionId() {
        return returnTransactionId;
    }

    public void setReturnTransactionId(String returnTransactionId) {
        this.returnTransactionId = returnTransactionId;
    }

    public String getReturnReceiptId() {
        return returnReceiptId;
    }

    public void setReturnReceiptId(String returnReceiptId) {
        this.returnReceiptId = returnReceiptId;
    }

    public int getTimewhenTransClosed() {
        return timewhenTransClosed;
    }

    public void setTimewhenTransClosed(int timewhenTransClosed) {
        this.timewhenTransClosed = timewhenTransClosed;
    }

    public int getTotalDiscAmount() {
        return totalDiscAmount;
    }

    public void setTotalDiscAmount(int totalDiscAmount) {
        this.totalDiscAmount = totalDiscAmount;
    }

    public int getTotalManualDiscountAmount() {
        return totalManualDiscountAmount;
    }

    public void setTotalManualDiscountAmount(int totalManualDiscountAmount) {
        this.totalManualDiscountAmount = totalManualDiscountAmount;
    }

    public int getTotalManualDiscountPercentage() {
        return totalManualDiscountPercentage;
    }

    public void setTotalManualDiscountPercentage(int totalManualDiscountPercentage) {
        this.totalManualDiscountPercentage = totalManualDiscountPercentage;
    }

    public int getTotalMRP() {
        return totalMRP;
    }

    public void setTotalMRP(int totalMRP) {
        this.totalMRP = totalMRP;
    }

    public int getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(int totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDataAreaId() {
        return dataAreaId;
    }

    public void setDataAreaId(String dataAreaId) {
        this.dataAreaId = dataAreaId;
    }

    public boolean isIsVoid() {
        return isVoid;
    }

    public void setIsVoid(boolean isVoid) {
        this.isVoid = isVoid;
    }

    public boolean isIsReturn() {
        return isReturn;
    }

    public void setIsReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

    public boolean isISBatchModifiedAllowed() {
        return iSBatchModifiedAllowed;
    }

    public void setISBatchModifiedAllowed(boolean iSBatchModifiedAllowed) {
        this.iSBatchModifiedAllowed = iSBatchModifiedAllowed;
    }

    public boolean isISReturnAllowed() {
        return iSReturnAllowed;
    }

    public void setISReturnAllowed(boolean iSReturnAllowed) {
        this.iSReturnAllowed = iSReturnAllowed;
    }

    public boolean isIsManualBill() {
        return isManualBill;
    }

    public void setIsManualBill(boolean isManualBill) {
        this.isManualBill = isManualBill;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public int getCurrentSalesLine() {
        return currentSalesLine;
    }

    public void setCurrentSalesLine(int currentSalesLine) {
        this.currentSalesLine = currentSalesLine;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public int getPosEvent() {
        return posEvent;
    }

    public void setPosEvent(int posEvent) {
        this.posEvent = posEvent;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public boolean isIsStockCheck() {
        return isStockCheck;
    }

    public void setIsStockCheck(boolean isStockCheck) {
        this.isStockCheck = isStockCheck;
    }

    public boolean isISPosted() {
        return iSPosted;
    }

    public void setISPosted(boolean iSPosted) {
        this.iSPosted = iSPosted;
    }

    public int getSEZ() {
        return sEZ;
    }

    public void setSEZ(int sEZ) {
        this.sEZ = sEZ;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public boolean isISAdvancePayment() {
        return iSAdvancePayment;
    }

    public void setISAdvancePayment(boolean iSAdvancePayment) {
        this.iSAdvancePayment = iSAdvancePayment;
    }

    public int getAmounttoAccount() {
        return amounttoAccount;
    }

    public void setAmounttoAccount(int amounttoAccount) {
        this.amounttoAccount = amounttoAccount;
    }

    public int getReminderDays() {
        return reminderDays;
    }

    public void setReminderDays(int reminderDays) {
        this.reminderDays = reminderDays;
    }

    public boolean isISOMSOrder() {
        return iSOMSOrder;
    }

    public void setISOMSOrder(boolean iSOMSOrder) {
        this.iSOMSOrder = iSOMSOrder;
    }

    public boolean isISHBPStore() {
        return iSHBPStore;
    }

    public void setISHBPStore(boolean iSHBPStore) {
        this.iSHBPStore = iSHBPStore;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getApprovedID() {
        return approvedID;
    }

    public void setApprovedID(String approvedID) {
        this.approvedID = approvedID;
    }

    public String getDiscountRef() {
        return discountRef;
    }

    public void setDiscountRef(String discountRef) {
        this.discountRef = discountRef;
    }

    public String getAWBNo() {
        return aWBNo;
    }

    public void setAWBNo(String aWBNo) {
        this.aWBNo = aWBNo;
    }

    public String getDSPCode() {
        return dSPCode;
    }

    public void setDSPCode(String dSPCode) {
        this.dSPCode = dSPCode;
    }

    public boolean isISHyperLocalDelivery() {
        return iSHyperLocalDelivery;
    }

    public void setISHyperLocalDelivery(boolean iSHyperLocalDelivery) {
        this.iSHyperLocalDelivery = iSHyperLocalDelivery;
    }

    public boolean isISHyperDelivered() {
        return iSHyperDelivered;
    }

    public void setISHyperDelivered(boolean iSHyperDelivered) {
        this.iSHyperDelivered = iSHyperDelivered;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public int getOMSCreditAmount() {
        return oMSCreditAmount;
    }

    public void setOMSCreditAmount(int oMSCreditAmount) {
        this.oMSCreditAmount = oMSCreditAmount;
    }

    public boolean isISOMSValidate() {
        return iSOMSValidate;
    }

    public void setISOMSValidate(boolean iSOMSValidate) {
        this.iSOMSValidate = iSOMSValidate;
    }

    public String getAllowedTenderType() {
        return allowedTenderType;
    }

    public void setAllowedTenderType(String allowedTenderType) {
        this.allowedTenderType = allowedTenderType;
    }

    public List<SalesLine> getSalesLine() {
        return salesLine;
    }

    public void setSalesLine(List<SalesLine> salesLine) {
        this.salesLine = salesLine;
    }

    public List<TenderLine> getTenderLine() {
        return tenderLine;
    }

    public void setTenderLine(List<TenderLine> tenderLine) {
        this.tenderLine = tenderLine;
    }

    public String getOrderPrescriptionURL() {
        return orderPrescriptionURL;
    }

    public void setOrderPrescriptionURL(String orderPrescriptionURL) {
        this.orderPrescriptionURL = orderPrescriptionURL;
    }
    public static class SalesLine {

        @SerializedName("LineNo")
        @Expose
        private int lineNo;
        @SerializedName("ItemId")
        @Expose
        private String itemId;
        @SerializedName("ItemName")
        @Expose
        private String itemName;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("CategoryCode")
        @Expose
        private String categoryCode;
        @SerializedName("SubCategory")
        @Expose
        private String subCategory;
        @SerializedName("SubCategoryCode")
        @Expose
        private String subCategoryCode;
        @SerializedName("ScheduleCategory")
        @Expose
        private String scheduleCategory;
        @SerializedName("ScheduleCategoryCode")
        @Expose
        private String scheduleCategoryCode;
        @SerializedName("ManufacturerCode")
        @Expose
        private String manufacturerCode;
        @SerializedName("ManufacturerName")
        @Expose
        private String manufacturerName;
        @SerializedName("Expiry")
        @Expose
        private String expiry;
        @SerializedName("Qty")
        @Expose
        private int qty;
        @SerializedName("StockQty")
        @Expose
        private int stockQty;
        @SerializedName("ReturnQty")
        @Expose
        private int returnQty;
        @SerializedName("RemainingQty")
        @Expose
        private int remainingQty;
        @SerializedName("MRP")
        @Expose
        private float mRP;
        @SerializedName("Tax")
        @Expose
        private int tax;
        @SerializedName("Additionaltax")
        @Expose
        private int additionaltax;
        @SerializedName("Barcode")
        @Expose
        private String barcode;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("DiscAmount")
        @Expose
        private int discAmount;
        @SerializedName("DiscOfferId")
        @Expose
        private String discOfferId;
        @SerializedName("Hsncode_In")
        @Expose
        private String hsncodeIn;
        @SerializedName("InventBatchId")
        @Expose
        private String inventBatchId;
        @SerializedName("PreviewText")
        @Expose
        private String previewText;
        @SerializedName("LinedscAmount")
        @Expose
        private int linedscAmount;
        @SerializedName("LineManualDiscountAmount")
        @Expose
        private int lineManualDiscountAmount;
        @SerializedName("LineManualDiscountPercentage")
        @Expose
        private int lineManualDiscountPercentage;
        @SerializedName("NetAmount")
        @Expose
        private float netAmount;
        @SerializedName("NetAmountInclTax")
        @Expose
        private float netAmountInclTax;
        @SerializedName("OriginalPrice")
        @Expose
        private float originalPrice;
        @SerializedName("PeriodicDiscAmount")
        @Expose
        private int periodicDiscAmount;
        @SerializedName("Price")
        @Expose
        private float price;
        @SerializedName("TaxAmount")
        @Expose
        private float taxAmount;
        @SerializedName("BaseAmount")
        @Expose
        private int baseAmount;
        @SerializedName("TotalDiscAmount")
        @Expose
        private int totalDiscAmount;
        @SerializedName("TotalDiscPct")
        @Expose
        private int totalDiscPct;
        @SerializedName("TotalRoundedAmount")
        @Expose
        private int totalRoundedAmount;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("UnitPrice")
        @Expose
        private float unitPrice;
        @SerializedName("UnitQty")
        @Expose
        private int unitQty;
        @SerializedName("VariantId")
        @Expose
        private String variantId;
        @SerializedName("Total")
        @Expose
        private int total;
        @SerializedName("ISPrescribed")
        @Expose
        private int iSPrescribed;
        @SerializedName("RemainderDays")
        @Expose
        private int remainderDays;
        @SerializedName("IsVoid")
        @Expose
        private boolean isVoid;
        @SerializedName("IsPriceOverride")
        @Expose
        private boolean isPriceOverride;
        @SerializedName("IsChecked")
        @Expose
        private boolean isChecked;
        @SerializedName("RetailCategoryRecID")
        @Expose
        private String retailCategoryRecID;
        @SerializedName("RetailSubCategoryRecID")
        @Expose
        private String retailSubCategoryRecID;
        @SerializedName("RetailMainCategoryRecID")
        @Expose
        private String retailMainCategoryRecID;
        @SerializedName("DPCO")
        @Expose
        private boolean dPCO;
        @SerializedName("ProductRecID")
        @Expose
        private String productRecID;
        @SerializedName("ModifyBatchId")
        @Expose
        private String modifyBatchId;
        @SerializedName("DiseaseType")
        @Expose
        private String diseaseType;
        @SerializedName("SubClassification")
        @Expose
        private String subClassification;
        @SerializedName("OfferQty")
        @Expose
        private int offerQty;
        @SerializedName("OfferAmount")
        @Expose
        private int offerAmount;
        @SerializedName("OfferDiscountType")
        @Expose
        private int offerDiscountType;
        @SerializedName("OfferDiscountValue")
        @Expose
        private int offerDiscountValue;
        @SerializedName("DiscountType")
        @Expose
        private String discountType;
        @SerializedName("MixMode")
        @Expose
        private boolean mixMode;
        @SerializedName("MMGroupId")
        @Expose
        private String mMGroupId;
        @SerializedName("DiscId")
        @Expose
        private String discId;
        @SerializedName("OfferType")
        @Expose
        private int offerType;
        @SerializedName("LineDiscPercentage")
        @Expose
        private int lineDiscPercentage;
        @SerializedName("ApplyDiscount")
        @Expose
        private boolean applyDiscount;
        @SerializedName("IGSTPerc")
        @Expose
        private float iGSTPerc;
        @SerializedName("CESSPerc")
        @Expose
        private float cESSPerc;
        @SerializedName("CGSTPerc")
        @Expose
        private float cGSTPerc;
        @SerializedName("SGSTPerc")
        @Expose
        private float sGSTPerc;
        @SerializedName("TotalTax")
        @Expose
        private int totalTax;
        @SerializedName("IGSTTaxCode")
        @Expose
        private String iGSTTaxCode;
        @SerializedName("CESSTaxCode")
        @Expose
        private String cESSTaxCode;
        @SerializedName("CGSTTaxCode")
        @Expose
        private String cGSTTaxCode;
        @SerializedName("SGSTTaxCode")
        @Expose
        private String sGSTTaxCode;
        @SerializedName("DiscountStructureType")
        @Expose
        private int discountStructureType;
        @SerializedName("SubstitudeItemId")
        @Expose
        private String substitudeItemId;
        @SerializedName("CategoryReference")
        @Expose
        private String categoryReference;
        @SerializedName("OrderStatus")
        @Expose
        private int orderStatus;
        @SerializedName("OmsLineID")
        @Expose
        private int omsLineID;
        @SerializedName("IsSubsitute")
        @Expose
        private boolean isSubsitute;
        @SerializedName("IsGeneric")
        @Expose
        private boolean isGeneric;
        @SerializedName("OmsLineRECID")
        @Expose
        private int omsLineRECID;
        @SerializedName("ISReserved")
        @Expose
        private boolean iSReserved;
        @SerializedName("ISStockAvailable")
        @Expose
        private boolean iSStockAvailable;

        public int getLineNo() {
            return lineNo;
        }

        public void setLineNo(int lineNo) {
            this.lineNo = lineNo;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getSubCategoryCode() {
            return subCategoryCode;
        }

        public void setSubCategoryCode(String subCategoryCode) {
            this.subCategoryCode = subCategoryCode;
        }

        public String getScheduleCategory() {
            return scheduleCategory;
        }

        public void setScheduleCategory(String scheduleCategory) {
            this.scheduleCategory = scheduleCategory;
        }

        public String getScheduleCategoryCode() {
            return scheduleCategoryCode;
        }

        public void setScheduleCategoryCode(String scheduleCategoryCode) {
            this.scheduleCategoryCode = scheduleCategoryCode;
        }

        public String getManufacturerCode() {
            return manufacturerCode;
        }

        public void setManufacturerCode(String manufacturerCode) {
            this.manufacturerCode = manufacturerCode;
        }

        public String getManufacturerName() {
            return manufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getStockQty() {
            return stockQty;
        }

        public void setStockQty(int stockQty) {
            this.stockQty = stockQty;
        }

        public int getReturnQty() {
            return returnQty;
        }

        public void setReturnQty(int returnQty) {
            this.returnQty = returnQty;
        }

        public int getRemainingQty() {
            return remainingQty;
        }

        public void setRemainingQty(int remainingQty) {
            this.remainingQty = remainingQty;
        }

        public float getMRP() {
            return mRP;
        }

        public void setMRP(float mRP) {
            this.mRP = mRP;
        }

        public int getTax() {
            return tax;
        }

        public void setTax(int tax) {
            this.tax = tax;
        }

        public int getAdditionaltax() {
            return additionaltax;
        }

        public void setAdditionaltax(int additionaltax) {
            this.additionaltax = additionaltax;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getDiscAmount() {
            return discAmount;
        }

        public void setDiscAmount(int discAmount) {
            this.discAmount = discAmount;
        }

        public String getDiscOfferId() {
            return discOfferId;
        }

        public void setDiscOfferId(String discOfferId) {
            this.discOfferId = discOfferId;
        }

        public String getHsncodeIn() {
            return hsncodeIn;
        }

        public void setHsncodeIn(String hsncodeIn) {
            this.hsncodeIn = hsncodeIn;
        }

        public String getInventBatchId() {
            return inventBatchId;
        }

        public void setInventBatchId(String inventBatchId) {
            this.inventBatchId = inventBatchId;
        }

        public String getPreviewText() {
            return previewText;
        }

        public void setPreviewText(String previewText) {
            this.previewText = previewText;
        }

        public int getLinedscAmount() {
            return linedscAmount;
        }

        public void setLinedscAmount(int linedscAmount) {
            this.linedscAmount = linedscAmount;
        }

        public int getLineManualDiscountAmount() {
            return lineManualDiscountAmount;
        }

        public void setLineManualDiscountAmount(int lineManualDiscountAmount) {
            this.lineManualDiscountAmount = lineManualDiscountAmount;
        }

        public int getLineManualDiscountPercentage() {
            return lineManualDiscountPercentage;
        }

        public void setLineManualDiscountPercentage(int lineManualDiscountPercentage) {
            this.lineManualDiscountPercentage = lineManualDiscountPercentage;
        }

        public float getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(float netAmount) {
            this.netAmount = netAmount;
        }

        public float getNetAmountInclTax() {
            return netAmountInclTax;
        }

        public void setNetAmountInclTax(float netAmountInclTax) {
            this.netAmountInclTax = netAmountInclTax;
        }

        public float getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(float originalPrice) {
            this.originalPrice = originalPrice;
        }

        public int getPeriodicDiscAmount() {
            return periodicDiscAmount;
        }

        public void setPeriodicDiscAmount(int periodicDiscAmount) {
            this.periodicDiscAmount = periodicDiscAmount;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(float taxAmount) {
            this.taxAmount = taxAmount;
        }

        public int getBaseAmount() {
            return baseAmount;
        }

        public void setBaseAmount(int baseAmount) {
            this.baseAmount = baseAmount;
        }

        public int getTotalDiscAmount() {
            return totalDiscAmount;
        }

        public void setTotalDiscAmount(int totalDiscAmount) {
            this.totalDiscAmount = totalDiscAmount;
        }

        public int getTotalDiscPct() {
            return totalDiscPct;
        }

        public void setTotalDiscPct(int totalDiscPct) {
            this.totalDiscPct = totalDiscPct;
        }

        public int getTotalRoundedAmount() {
            return totalRoundedAmount;
        }

        public void setTotalRoundedAmount(int totalRoundedAmount) {
            this.totalRoundedAmount = totalRoundedAmount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public float getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(float unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getUnitQty() {
            return unitQty;
        }

        public void setUnitQty(int unitQty) {
            this.unitQty = unitQty;
        }

        public String getVariantId() {
            return variantId;
        }

        public void setVariantId(String variantId) {
            this.variantId = variantId;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getISPrescribed() {
            return iSPrescribed;
        }

        public void setISPrescribed(int iSPrescribed) {
            this.iSPrescribed = iSPrescribed;
        }

        public int getRemainderDays() {
            return remainderDays;
        }

        public void setRemainderDays(int remainderDays) {
            this.remainderDays = remainderDays;
        }

        public boolean isIsVoid() {
            return isVoid;
        }

        public void setIsVoid(boolean isVoid) {
            this.isVoid = isVoid;
        }

        public boolean isIsPriceOverride() {
            return isPriceOverride;
        }

        public void setIsPriceOverride(boolean isPriceOverride) {
            this.isPriceOverride = isPriceOverride;
        }

        public boolean isIsChecked() {
            return isChecked;
        }

        public void setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

        public String getRetailCategoryRecID() {
            return retailCategoryRecID;
        }

        public void setRetailCategoryRecID(String retailCategoryRecID) {
            this.retailCategoryRecID = retailCategoryRecID;
        }

        public String getRetailSubCategoryRecID() {
            return retailSubCategoryRecID;
        }

        public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
            this.retailSubCategoryRecID = retailSubCategoryRecID;
        }

        public String getRetailMainCategoryRecID() {
            return retailMainCategoryRecID;
        }

        public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
            this.retailMainCategoryRecID = retailMainCategoryRecID;
        }

        public boolean isDPCO() {
            return dPCO;
        }

        public void setDPCO(boolean dPCO) {
            this.dPCO = dPCO;
        }

        public String getProductRecID() {
            return productRecID;
        }

        public void setProductRecID(String productRecID) {
            this.productRecID = productRecID;
        }

        public String getModifyBatchId() {
            return modifyBatchId;
        }

        public void setModifyBatchId(String modifyBatchId) {
            this.modifyBatchId = modifyBatchId;
        }

        public String getDiseaseType() {
            return diseaseType;
        }

        public void setDiseaseType(String diseaseType) {
            this.diseaseType = diseaseType;
        }

        public String getSubClassification() {
            return subClassification;
        }

        public void setSubClassification(String subClassification) {
            this.subClassification = subClassification;
        }

        public int getOfferQty() {
            return offerQty;
        }

        public void setOfferQty(int offerQty) {
            this.offerQty = offerQty;
        }

        public int getOfferAmount() {
            return offerAmount;
        }

        public void setOfferAmount(int offerAmount) {
            this.offerAmount = offerAmount;
        }

        public int getOfferDiscountType() {
            return offerDiscountType;
        }

        public void setOfferDiscountType(int offerDiscountType) {
            this.offerDiscountType = offerDiscountType;
        }

        public int getOfferDiscountValue() {
            return offerDiscountValue;
        }

        public void setOfferDiscountValue(int offerDiscountValue) {
            this.offerDiscountValue = offerDiscountValue;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public boolean isMixMode() {
            return mixMode;
        }

        public void setMixMode(boolean mixMode) {
            this.mixMode = mixMode;
        }

        public String getMMGroupId() {
            return mMGroupId;
        }

        public void setMMGroupId(String mMGroupId) {
            this.mMGroupId = mMGroupId;
        }

        public String getDiscId() {
            return discId;
        }

        public void setDiscId(String discId) {
            this.discId = discId;
        }

        public int getOfferType() {
            return offerType;
        }

        public void setOfferType(int offerType) {
            this.offerType = offerType;
        }

        public int getLineDiscPercentage() {
            return lineDiscPercentage;
        }

        public void setLineDiscPercentage(int lineDiscPercentage) {
            this.lineDiscPercentage = lineDiscPercentage;
        }

        public boolean isApplyDiscount() {
            return applyDiscount;
        }

        public void setApplyDiscount(boolean applyDiscount) {
            this.applyDiscount = applyDiscount;
        }

        public float getIGSTPerc() {
            return iGSTPerc;
        }

        public void setIGSTPerc(float iGSTPerc) {
            this.iGSTPerc = iGSTPerc;
        }

        public float getCESSPerc() {
            return cESSPerc;
        }

        public void setCESSPerc(float cESSPerc) {
            this.cESSPerc = cESSPerc;
        }

        public float getCGSTPerc() {
            return cGSTPerc;
        }

        public void setCGSTPerc(float cGSTPerc) {
            this.cGSTPerc = cGSTPerc;
        }

        public float getSGSTPerc() {
            return sGSTPerc;
        }

        public void setSGSTPerc(float sGSTPerc) {
            this.sGSTPerc = sGSTPerc;
        }

        public int getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(int totalTax) {
            this.totalTax = totalTax;
        }

        public String getIGSTTaxCode() {
            return iGSTTaxCode;
        }

        public void setIGSTTaxCode(String iGSTTaxCode) {
            this.iGSTTaxCode = iGSTTaxCode;
        }

        public String getCESSTaxCode() {
            return cESSTaxCode;
        }

        public void setCESSTaxCode(String cESSTaxCode) {
            this.cESSTaxCode = cESSTaxCode;
        }

        public String getCGSTTaxCode() {
            return cGSTTaxCode;
        }

        public void setCGSTTaxCode(String cGSTTaxCode) {
            this.cGSTTaxCode = cGSTTaxCode;
        }

        public String getSGSTTaxCode() {
            return sGSTTaxCode;
        }

        public void setSGSTTaxCode(String sGSTTaxCode) {
            this.sGSTTaxCode = sGSTTaxCode;
        }

        public int getDiscountStructureType() {
            return discountStructureType;
        }

        public void setDiscountStructureType(int discountStructureType) {
            this.discountStructureType = discountStructureType;
        }

        public String getSubstitudeItemId() {
            return substitudeItemId;
        }

        public void setSubstitudeItemId(String substitudeItemId) {
            this.substitudeItemId = substitudeItemId;
        }

        public String getCategoryReference() {
            return categoryReference;
        }

        public void setCategoryReference(String categoryReference) {
            this.categoryReference = categoryReference;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getOmsLineID() {
            return omsLineID;
        }

        public void setOmsLineID(int omsLineID) {
            this.omsLineID = omsLineID;
        }

        public boolean isIsSubsitute() {
            return isSubsitute;
        }

        public void setIsSubsitute(boolean isSubsitute) {
            this.isSubsitute = isSubsitute;
        }

        public boolean isIsGeneric() {
            return isGeneric;
        }

        public void setIsGeneric(boolean isGeneric) {
            this.isGeneric = isGeneric;
        }

        public int getOmsLineRECID() {
            return omsLineRECID;
        }

        public void setOmsLineRECID(int omsLineRECID) {
            this.omsLineRECID = omsLineRECID;
        }

        public boolean isISReserved() {
            return iSReserved;
        }

        public void setISReserved(boolean iSReserved) {
            this.iSReserved = iSReserved;
        }

        public boolean isISStockAvailable() {
            return iSStockAvailable;
        }

        public void setISStockAvailable(boolean iSStockAvailable) {
            this.iSStockAvailable = iSStockAvailable;
        }

    }
    public static class TenderLine {

        @SerializedName("LineNo")
        @Expose
        private int lineNo;
        @SerializedName("TenderId")
        @Expose
        private String tenderId;
        @SerializedName("TenderName")
        @Expose
        private String tenderName;
        @SerializedName("TenderType")
        @Expose
        private int tenderType;
        @SerializedName("ExchRate")
        @Expose
        private int exchRate;
        @SerializedName("ExchRateMst")
        @Expose
        private int exchRateMst;
        @SerializedName("AmountTendered")
        @Expose
        private int amountTendered;
        @SerializedName("AmountCur")
        @Expose
        private int amountCur;
        @SerializedName("AmountMst")
        @Expose
        private int amountMst;
        @SerializedName("MobileNo")
        @Expose
        private String mobileNo;
        @SerializedName("WalletType")
        @Expose
        private int walletType;
        @SerializedName("WalletOrderId")
        @Expose
        private String walletOrderId;
        @SerializedName("WalletTransactionID")
        @Expose
        private String walletTransactionID;
        @SerializedName("RewardsPoint")
        @Expose
        private int rewardsPoint;
        @SerializedName("PreviewText")
        @Expose
        private String previewText;
        @SerializedName("BarCode")
        @Expose
        private String barCode;
        @SerializedName("IsVoid")
        @Expose
        private boolean isVoid;

        public int getLineNo() {
            return lineNo;
        }

        public void setLineNo(int lineNo) {
            this.lineNo = lineNo;
        }

        public String getTenderId() {
            return tenderId;
        }

        public void setTenderId(String tenderId) {
            this.tenderId = tenderId;
        }

        public String getTenderName() {
            return tenderName;
        }

        public void setTenderName(String tenderName) {
            this.tenderName = tenderName;
        }

        public int getTenderType() {
            return tenderType;
        }

        public void setTenderType(int tenderType) {
            this.tenderType = tenderType;
        }

        public int getExchRate() {
            return exchRate;
        }

        public void setExchRate(int exchRate) {
            this.exchRate = exchRate;
        }

        public int getExchRateMst() {
            return exchRateMst;
        }

        public void setExchRateMst(int exchRateMst) {
            this.exchRateMst = exchRateMst;
        }

        public int getAmountTendered() {
            return amountTendered;
        }

        public void setAmountTendered(int amountTendered) {
            this.amountTendered = amountTendered;
        }

        public int getAmountCur() {
            return amountCur;
        }

        public void setAmountCur(int amountCur) {
            this.amountCur = amountCur;
        }

        public int getAmountMst() {
            return amountMst;
        }

        public void setAmountMst(int amountMst) {
            this.amountMst = amountMst;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public int getWalletType() {
            return walletType;
        }

        public void setWalletType(int walletType) {
            this.walletType = walletType;
        }

        public String getWalletOrderId() {
            return walletOrderId;
        }

        public void setWalletOrderId(String walletOrderId) {
            this.walletOrderId = walletOrderId;
        }

        public String getWalletTransactionID() {
            return walletTransactionID;
        }

        public void setWalletTransactionID(String walletTransactionID) {
            this.walletTransactionID = walletTransactionID;
        }

        public int getRewardsPoint() {
            return rewardsPoint;
        }

        public void setRewardsPoint(int rewardsPoint) {
            this.rewardsPoint = rewardsPoint;
        }

        public String getPreviewText() {
            return previewText;
        }

        public void setPreviewText(String previewText) {
            this.previewText = previewText;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public boolean isIsVoid() {
            return isVoid;
        }

        public void setIsVoid(boolean isVoid) {
            this.isVoid = isVoid;
        }

    }
}