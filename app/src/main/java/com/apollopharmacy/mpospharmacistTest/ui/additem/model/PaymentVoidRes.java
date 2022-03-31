package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentVoidRes implements Serializable {
    @SerializedName("AWBNo")
    @Expose
    public String aWBNo;
    @SerializedName("AllowedTenderType")
    @Expose
    public String allowedTenderType;
    @SerializedName("AmounttoAccount")
    @Expose
    public Double amounttoAccount;
    @SerializedName("ApprovedID")
    @Expose
    public String approvedID;
    @SerializedName("BatchTerminalid")
    @Expose
    public String batchTerminalid;
    @SerializedName("BillingCity")
    @Expose
    public String billingCity;
    @SerializedName("BusinessDate")
    @Expose
    public String businessDate;
    @SerializedName("CancelReasonCode")
    @Expose
    public String cancelReasonCode;
    @SerializedName("Channel")
    @Expose
    public String channel;
    @SerializedName("Comment")
    @Expose
    public String comment;
    @SerializedName("CorpCode")
    @Expose
    public String corpCode;
    @SerializedName("CouponCode")
    @Expose
    public String couponCode;
    @SerializedName("CreatedDateTime")
    @Expose
    public String createdDateTime;
    @SerializedName("CreatedonPosTerminal")
    @Expose
    public String createdonPosTerminal;
    @SerializedName("Currency")
    @Expose
    public String currency;
    @SerializedName("CustAccount")
    @Expose
    public String custAccount;
    @SerializedName("CustAddress")
    @Expose
    public String custAddress;
    @SerializedName("CustDiscamount")
    @Expose
    public Double custDiscamount;
    @SerializedName("CustomerID")
    @Expose
    public String customerID;
    @SerializedName("CustomerName")
    @Expose
    public String customerName;
    @SerializedName("CustomerState")
    @Expose
    public String customerState;
    @SerializedName("DOB")
    @Expose
    public String dOB;
    @SerializedName("DSPCode")
    @Expose
    public String dSPCode;
    @SerializedName("DataAreaId")
    @Expose
    public String dataAreaId;
    @SerializedName("DeliveryDate")
    @Expose
    public String deliveryDate;
    @SerializedName("DiscAmount")
    @Expose
    public Double discAmount;
    @SerializedName("DiscountRef")
    @Expose
    public String discountRef;
    @SerializedName("DoctorCode")
    @Expose
    public String doctorCode;
    @SerializedName("DoctorName")
    @Expose
    public String doctorName;
    @SerializedName("EntryStatus")
    @Expose
    public Integer entryStatus;
    @SerializedName("Gender")
    @Expose
    public Integer gender;
    @SerializedName("GrossAmount")
    @Expose
    public Double grossAmount;
    @SerializedName("IPNO")
    @Expose
    public String iPNO;
    @SerializedName("IPSerialNO")
    @Expose
    public String iPSerialNO;
    @SerializedName("ISAdvancePayment")
    @Expose
    public Boolean iSAdvancePayment;
    @SerializedName("ISBatchModifiedAllowed")
    @Expose
    public Boolean iSBatchModifiedAllowed;
    @SerializedName("ISHBPStore")
    @Expose
    public Boolean iSHBPStore;
    @SerializedName("ISHyperDelivered")
    @Expose
    public Boolean iSHyperDelivered;
    @SerializedName("ISHyperLocalDelivery")
    @Expose
    public Boolean iSHyperLocalDelivery;
    @SerializedName("ISOMSOrder")
    @Expose
    public Boolean iSOMSOrder;
    @SerializedName("ISOMSValidate")
    @Expose
    public Boolean iSOMSValidate;
    @SerializedName("ISOnlineOrder")
    @Expose
    public Boolean iSOnlineOrder;
    @SerializedName("ISPosted")
    @Expose
    public Boolean iSPosted;
    @SerializedName("ISPrescibeDiscount")
    @Expose
    public Boolean iSPrescibeDiscount;
    @SerializedName("ISReserved")
    @Expose
    public Boolean iSReserved;
    @SerializedName("ISReturnAllowed")
    @Expose
    public Boolean iSReturnAllowed;
    @SerializedName("IsManualBill")
    @Expose
    public Boolean isManualBill;
    @SerializedName("IsRepeatBill")
    @Expose
    public Boolean isRepeatBill;
    @SerializedName("IsReturn")
    @Expose
    public Boolean isReturn;
    @SerializedName("IsStockCheck")
    @Expose
    public Boolean isStockCheck;
    @SerializedName("IsVoid")
    @Expose
    public Boolean isVoid;
    @SerializedName("MobileNO")
    @Expose
    public String mobileNO;
    @SerializedName("NetAmount")
    @Expose
    public Double netAmount;
    @SerializedName("NetAmountInclTax")
    @Expose
    public Double netAmountInclTax;
    @SerializedName("NumberofItemLines")
    @Expose
    public Integer numberofItemLines;
    @SerializedName("NumberofItems")
    @Expose
    public Integer numberofItems;
    @SerializedName("OMSCreditAmount")
    @Expose
    public Integer oMSCreditAmount;
    @SerializedName("OrderPrescriptionURL")
    @Expose
    public List<Object> orderPrescriptionURL = null;
    @SerializedName("OrderSource")
    @Expose
    public String orderSource;
    @SerializedName("OrderType")
    @Expose
    public String orderType;
    @SerializedName("PatientID")
    @Expose
    public String patientID;
    @SerializedName("PaymentSource")
    @Expose
    public String paymentSource;
    @SerializedName("Pincode")
    @Expose
    public String pincode;
    @SerializedName("PosEvent")
    @Expose
    public Integer posEvent;
    @SerializedName("REFNO")
    @Expose
    public String rEFNO;
    @SerializedName("ReciptId")
    @Expose
    public String reciptId;
    @SerializedName("RegionCode")
    @Expose
    public String regionCode;
    @SerializedName("Remainingamount")
    @Expose
    public Double remainingamount;
    @SerializedName("ReminderDays")
    @Expose
    public Integer reminderDays;
    @SerializedName("RequestStatus")
    @Expose
    public Integer requestStatus;
    @SerializedName("ReturnMessage")
    @Expose
    public String returnMessage;
    @SerializedName("ReturnReceiptId")
    @Expose
    public String returnReceiptId;
    @SerializedName("ReturnStore")
    @Expose
    public String returnStore;
    @SerializedName("ReturnTerminal")
    @Expose
    public String returnTerminal;
    @SerializedName("ReturnTransactionId")
    @Expose
    public String returnTransactionId;
    @SerializedName("ReturnType")
    @Expose
    public Integer returnType;
    @SerializedName("RoundedAmount")
    @Expose
    public Double roundedAmount;
    @SerializedName("SEZ")
    @Expose
    public Integer sEZ;
    @SerializedName("SalesLine")
    @Expose
    public List<SalesLine> salesLine ;
    @SerializedName("SalesOrigin")
    @Expose
    public String salesOrigin;
    @SerializedName("ShippingMethod")
    @Expose
    public String shippingMethod;
    @SerializedName("Staff")
    @Expose
    public String staff;
    @SerializedName("State")
    @Expose
    public String state;
    @SerializedName("Store")
    @Expose
    public String store;
    @SerializedName("StoreName")
    @Expose
    public String storeName;
    @SerializedName("TenderLine")
    @Expose
    public List<TenderLine> tenderLine ;
    @SerializedName("Terminal")
    @Expose
    public String terminal;
    @SerializedName("TimewhenTransClosed")
    @Expose
    public Integer timewhenTransClosed;
    @SerializedName("TotalDiscAmount")
    @Expose
    public Double totalDiscAmount;
    @SerializedName("TotalMRP")
    @Expose
    public Double totalMRP;
    @SerializedName("TotalManualDiscountAmount")
    @Expose
    public Double totalManualDiscountAmount;
    @SerializedName("TotalManualDiscountPercentage")
    @Expose
    public Double totalManualDiscountPercentage;
    @SerializedName("TotalTaxAmount")
    @Expose
    public Double totalTaxAmount;
    @SerializedName("TrackingRef")
    @Expose
    public String trackingRef;
    @SerializedName("TransDate")
    @Expose
    public String transDate;
    @SerializedName("TransType")
    @Expose
    public Integer transType;
    @SerializedName("TransactionId")
    @Expose
    public String transactionId;
    @SerializedName("Type")
    @Expose
    public Integer type;
    @SerializedName("VendorCode")
    @Expose
    public String vendorCode;
    @SerializedName("VendorId")
    @Expose
    public String vendorId;

    public String getaWBNo() {
        return aWBNo;
    }

    public void setaWBNo(String aWBNo) {
        this.aWBNo = aWBNo;
    }

    public String getAllowedTenderType() {
        return allowedTenderType;
    }

    public void setAllowedTenderType(String allowedTenderType) {
        this.allowedTenderType = allowedTenderType;
    }

    public Double getAmounttoAccount() {
        return amounttoAccount;
    }

    public void setAmounttoAccount(Double amounttoAccount) {
        this.amounttoAccount = amounttoAccount;
    }

    public void setCustDiscamount(Double custDiscamount) {
        this.custDiscamount = custDiscamount;
    }

    public Double getTotalDiscAmount() {
        return totalDiscAmount;
    }

    public String getApprovedID() {
        return approvedID;
    }

    public void setApprovedID(String approvedID) {
        this.approvedID = approvedID;
    }

    public String getBatchTerminalid() {
        return batchTerminalid;
    }

    public void setBatchTerminalid(String batchTerminalid) {
        this.batchTerminalid = batchTerminalid;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
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

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
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

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getdSPCode() {
        return dSPCode;
    }

    public void setdSPCode(String dSPCode) {
        this.dSPCode = dSPCode;
    }

    public String getDataAreaId() {
        return dataAreaId;
    }

    public void setDataAreaId(String dataAreaId) {
        this.dataAreaId = dataAreaId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getCustDiscamount() {
        return custDiscamount;
    }

    public Double getDiscAmount() {
        return discAmount;
    }

    public void setDiscAmount(Double discAmount) {
        this.discAmount = discAmount;
    }

    public String getDiscountRef() {
        return discountRef;
    }

    public void setDiscountRef(String discountRef) {
        this.discountRef = discountRef;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(Integer entryStatus) {
        this.entryStatus = entryStatus;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getiPNO() {
        return iPNO;
    }

    public void setiPNO(String iPNO) {
        this.iPNO = iPNO;
    }

    public String getiPSerialNO() {
        return iPSerialNO;
    }

    public void setiPSerialNO(String iPSerialNO) {
        this.iPSerialNO = iPSerialNO;
    }

    public Boolean getiSAdvancePayment() {
        return iSAdvancePayment;
    }

    public void setiSAdvancePayment(Boolean iSAdvancePayment) {
        this.iSAdvancePayment = iSAdvancePayment;
    }

    public Boolean getiSBatchModifiedAllowed() {
        return iSBatchModifiedAllowed;
    }

    public void setiSBatchModifiedAllowed(Boolean iSBatchModifiedAllowed) {
        this.iSBatchModifiedAllowed = iSBatchModifiedAllowed;
    }

    public Boolean getiSHBPStore() {
        return iSHBPStore;
    }

    public void setiSHBPStore(Boolean iSHBPStore) {
        this.iSHBPStore = iSHBPStore;
    }

    public Boolean getiSHyperDelivered() {
        return iSHyperDelivered;
    }

    public void setiSHyperDelivered(Boolean iSHyperDelivered) {
        this.iSHyperDelivered = iSHyperDelivered;
    }

    public Boolean getiSHyperLocalDelivery() {
        return iSHyperLocalDelivery;
    }

    public void setiSHyperLocalDelivery(Boolean iSHyperLocalDelivery) {
        this.iSHyperLocalDelivery = iSHyperLocalDelivery;
    }

    public Boolean getiSOMSOrder() {
        return iSOMSOrder;
    }

    public void setiSOMSOrder(Boolean iSOMSOrder) {
        this.iSOMSOrder = iSOMSOrder;
    }

    public Boolean getiSOMSValidate() {
        return iSOMSValidate;
    }

    public void setiSOMSValidate(Boolean iSOMSValidate) {
        this.iSOMSValidate = iSOMSValidate;
    }

    public Boolean getiSOnlineOrder() {
        return iSOnlineOrder;
    }

    public void setiSOnlineOrder(Boolean iSOnlineOrder) {
        this.iSOnlineOrder = iSOnlineOrder;
    }

    public Boolean getiSPosted() {
        return iSPosted;
    }

    public void setiSPosted(Boolean iSPosted) {
        this.iSPosted = iSPosted;
    }

    public Boolean getiSPrescibeDiscount() {
        return iSPrescibeDiscount;
    }

    public void setiSPrescibeDiscount(Boolean iSPrescibeDiscount) {
        this.iSPrescibeDiscount = iSPrescibeDiscount;
    }

    public Boolean getiSReserved() {
        return iSReserved;
    }

    public void setiSReserved(Boolean iSReserved) {
        this.iSReserved = iSReserved;
    }

    public Boolean getiSReturnAllowed() {
        return iSReturnAllowed;
    }

    public void setiSReturnAllowed(Boolean iSReturnAllowed) {
        this.iSReturnAllowed = iSReturnAllowed;
    }

    public Boolean getManualBill() {
        return isManualBill;
    }

    public void setManualBill(Boolean manualBill) {
        isManualBill = manualBill;
    }

    public Boolean getRepeatBill() {
        return isRepeatBill;
    }

    public void setRepeatBill(Boolean repeatBill) {
        isRepeatBill = repeatBill;
    }

    public Boolean getReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
    }

    public Boolean getStockCheck() {
        return isStockCheck;
    }

    public void setStockCheck(Boolean stockCheck) {
        isStockCheck = stockCheck;
    }

    public Boolean getVoid() {
        return isVoid;
    }

    public void setVoid(Boolean aVoid) {
        isVoid = aVoid;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Double getNetAmountInclTax() {
        return netAmountInclTax;
    }

    public void setNetAmountInclTax(Double netAmountInclTax) {
        this.netAmountInclTax = netAmountInclTax;
    }

    public Integer getNumberofItemLines() {
        return numberofItemLines;
    }

    public void setNumberofItemLines(Integer numberofItemLines) {
        this.numberofItemLines = numberofItemLines;
    }

    public Integer getNumberofItems() {
        return numberofItems;
    }

    public void setNumberofItems(Integer numberofItems) {
        this.numberofItems = numberofItems;
    }

    public Integer getoMSCreditAmount() {
        return oMSCreditAmount;
    }

    public void setoMSCreditAmount(Integer oMSCreditAmount) {
        this.oMSCreditAmount = oMSCreditAmount;
    }

    public List<Object> getOrderPrescriptionURL() {
        return orderPrescriptionURL;
    }

    public void setOrderPrescriptionURL(List<Object> orderPrescriptionURL) {
        this.orderPrescriptionURL = orderPrescriptionURL;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getPosEvent() {
        return posEvent;
    }

    public void setPosEvent(Integer posEvent) {
        this.posEvent = posEvent;
    }

    public String getrEFNO() {
        return rEFNO;
    }

    public void setrEFNO(String rEFNO) {
        this.rEFNO = rEFNO;
    }

    public String getReciptId() {
        return reciptId;
    }

    public void setReciptId(String reciptId) {
        this.reciptId = reciptId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Double getRemainingamount() {
        return remainingamount;
    }

    public void setRemainingamount(Double remainingamount) {
        this.remainingamount = remainingamount;
    }

    public void setTotalDiscAmount(Double totalDiscAmount) {
        this.totalDiscAmount = totalDiscAmount;
    }

    public Integer getReminderDays() {
        return reminderDays;
    }

    public void setReminderDays(Integer reminderDays) {
        this.reminderDays = reminderDays;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnReceiptId() {
        return returnReceiptId;
    }

    public void setReturnReceiptId(String returnReceiptId) {
        this.returnReceiptId = returnReceiptId;
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

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Double getRoundedAmount() {
        return roundedAmount;
    }

    public void setRoundedAmount(Double roundedAmount) {
        this.roundedAmount = roundedAmount;
    }

    public void setTotalManualDiscountAmount(Double totalManualDiscountAmount) {
        this.totalManualDiscountAmount = totalManualDiscountAmount;
    }

    public void setTotalManualDiscountPercentage(Double totalManualDiscountPercentage) {
        this.totalManualDiscountPercentage = totalManualDiscountPercentage;
    }

    public Integer getsEZ() {
        return sEZ;
    }

    public void setsEZ(Integer sEZ) {
        this.sEZ = sEZ;
    }

    public List<SalesLine> getSalesLine() {
        return salesLine;
    }

    public void setSalesLine(List<SalesLine> salesLine) {
        this.salesLine = salesLine;
    }

    public String getSalesOrigin() {
        return salesOrigin;
    }

    public void setSalesOrigin(String salesOrigin) {
        this.salesOrigin = salesOrigin;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<TenderLine> getTenderLine() {
        return tenderLine;
    }

    public void setTenderLine(List<TenderLine> tenderLine) {
        this.tenderLine = tenderLine;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Integer getTimewhenTransClosed() {
        return timewhenTransClosed;
    }

    public void setTimewhenTransClosed(Integer timewhenTransClosed) {
        this.timewhenTransClosed = timewhenTransClosed;
    }

    public Double getTotalMRP() {
        return totalMRP;
    }

    public void setTotalMRP(Double totalMRP) {
        this.totalMRP = totalMRP;
    }


    public Double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(Double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getTrackingRef() {
        return trackingRef;
    }

    public void setTrackingRef(String trackingRef) {
        this.trackingRef = trackingRef;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}



