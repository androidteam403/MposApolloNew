package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetJounalOnlineOrderTransactionsResponse {

    @SerializedName("AWBNo")
    @Expose
    private String aWBNo;
    @SerializedName("AgeGroup")
    @Expose
    private String ageGroup;
    @SerializedName("AllowedTenderType")
    @Expose
    private String allowedTenderType;
    @SerializedName("AmounttoAccount")
    @Expose
    private Double amounttoAccount;
    @SerializedName("ApprovedID")
    @Expose
    private String approvedID;
    @SerializedName("BatchTerminalid")
    @Expose
    private String batchTerminalid;
    @SerializedName("BillingCity")
    @Expose
    private String billingCity;
    @SerializedName("BusinessDate")
    @Expose
    private String businessDate;

    @SerializedName("BoxId")
    @Expose
    private String boxId;

    @SerializedName("CancelReasonCode")
    @Expose
    private String cancelReasonCode;
    @SerializedName("Channel")
    @Expose
    private String channel;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("CorpCode")
    @Expose
    private String corpCode;
    @SerializedName("CouponCode")
    @Expose
    private String couponCode;
    @SerializedName("CreatedDateTime")
    @Expose
    private String createdDateTime;
    @SerializedName("CreatedonPosTerminal")
    @Expose
    private String createdonPosTerminal;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("CustAccount")
    @Expose
    private String custAccount;
    @SerializedName("CustAddress")
    @Expose
    private String custAddress;
    @SerializedName("CustDiscamount")
    @Expose
    private Double custDiscamount;
    @SerializedName("CustomerID")
    @Expose
    private String customerID;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerState")
    @Expose
    private String customerState;
    @SerializedName("CustomerType")
    @Expose
    private String customerType;
    @SerializedName("DOB")
    @Expose
    private String dob;
    @SerializedName("DSPCode")
    @Expose
    private String dSPCode;
    @SerializedName("DataAreaId")
    @Expose
    private String dataAreaId;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("DiscAmount")
    @Expose
    private Double discAmount;
    @SerializedName("DiscountRef")
    @Expose
    private String discountRef;
    @SerializedName("DiscountReferenceID")
    @Expose
    private String discountReferenceID;
    @SerializedName("DiscountStatus")
    @Expose
    private Double discountStatus;
    @SerializedName("DoctorCode")
    @Expose
    private String doctorCode;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("DonationAmount")
    @Expose
    private Double donationAmount;
    @SerializedName("DspName")
    @Expose
    private String dspName;
    @SerializedName("EntryStatus")
    @Expose
    private Integer entryStatus;
    @SerializedName("ExpiryDays")
    @Expose
    private Integer expiryDays;
    @SerializedName("FwdReturnOtp")
    @Expose
    private String fwdReturnOtp;
    @SerializedName("Gender")
    @Expose
    private Integer gender;
    @SerializedName("GrossAmount")
    @Expose
    private Double grossAmount;
    @SerializedName("HCOfferCode")
    @Expose
    private String hCOfferCode;
    @SerializedName("IPNO")
    @Expose
    private String ipno;
    @SerializedName("IPSerialNO")
    @Expose
    private String iPSerialNO;
    @SerializedName("ISAdvancePayment")
    @Expose
    private Boolean iSAdvancePayment;
    @SerializedName("ISBatchModifiedAllowed")
    @Expose
    private Boolean iSBatchModifiedAllowed;
    @SerializedName("ISBulkBilling")
    @Expose
    private Boolean iSBulkBilling;
    @SerializedName("ISCancelled")
    @Expose
    private Boolean iSCancelled;
    @SerializedName("ISHBPStore")
    @Expose
    private Boolean iSHBPStore;
    @SerializedName("ISHyperDelivered")
    @Expose
    private Boolean iSHyperDelivered;
    @SerializedName("ISHyperLocalDelivery")
    @Expose
    private Boolean iSHyperLocalDelivery;
    @SerializedName("ISOMSOrder")
    @Expose
    private Boolean iSOMSOrder;
    @SerializedName("ISOMSReturn")
    @Expose
    private Boolean iSOMSReturn;
    @SerializedName("ISOMSValidate")
    @Expose
    private Boolean iSOMSValidate;
    @SerializedName("ISOnlineOrder")
    @Expose
    private Boolean iSOnlineOrder;
    @SerializedName("ISPosted")
    @Expose
    private Boolean iSPosted;
    @SerializedName("ISPrescibeDiscount")
    @Expose
    private Boolean iSPrescibeDiscount;
    @SerializedName("ISReserved")
    @Expose
    private Boolean iSReserved;
    @SerializedName("ISReturnAllowed")
    @Expose
    private Boolean iSReturnAllowed;
    @SerializedName("IsBulkDiscount")
    @Expose
    private Boolean isBulkDiscount;
    @SerializedName("IsHDOrder")
    @Expose
    private Boolean isHDOrder;
    @SerializedName("IsMPOSBill")
    @Expose
    private Integer isMPOSBill;
    @SerializedName("IsManualBill")
    @Expose
    private Boolean isManualBill;
    @SerializedName("IsOMSJurnalsScreen")
    @Expose
    private Boolean isOMSJurnalsScreen;
    @SerializedName("IsRepeatBill")
    @Expose
    private Boolean isRepeatBill;
    @SerializedName("IsReturn")
    @Expose
    private Boolean isReturn;
    @SerializedName("IsStockCheck")
    @Expose
    private Boolean isStockCheck;
    @SerializedName("IsTPASeller")
    @Expose
    private Boolean isTPASeller;
    @SerializedName("IsUHIDBilling")
    @Expose
    private Boolean isUHIDBilling;
    @SerializedName("IsVoid")
    @Expose
    private Boolean isVoid;
    @SerializedName("MobileNO")
    @Expose
    private String mobileNO;
    @SerializedName("NetAmount")
    @Expose
    private Double netAmount;
    @SerializedName("NetAmountInclTax")
    @Expose
    private Double netAmountInclTax;
    @SerializedName("NumberofItemLines")
    @Expose
    private Integer numberofItemLines;
    @SerializedName("NumberofItems")
    @Expose
    private Integer numberofItems;
    @SerializedName("OMSCreditAmount")
    @Expose
    private Double oMSCreditAmount;
    @SerializedName("OrderPrescriptionURL")
    @Expose
    private Object orderPrescriptionURL;
    @SerializedName("OrderSource")
    @Expose
    private String orderSource;
    @SerializedName("OrderType")
    @Expose
    private String orderType;
    @SerializedName("PatientID")
    @Expose
    private String patientID;
    @SerializedName("PaymentSource")
    @Expose
    private String paymentSource;
    @SerializedName("PickPackReservation")
    @Expose
    private Object pickPackReservation;
    @SerializedName("PickupOtp")
    @Expose
    private String pickupOtp;
    @SerializedName("PickupStatus")
    @Expose
    private Boolean pickupStatus;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("PosEvent")
    @Expose
    private Integer posEvent;
    @SerializedName("REFNO")
    @Expose
    private String refno;
    @SerializedName("RTOStatus")
    @Expose
    private Boolean rTOStatus;
    @SerializedName("ReciptId")
    @Expose
    private String reciptId;
    @SerializedName("RegionCode")
    @Expose
    private String regionCode;
    @SerializedName("Remainingamount")
    @Expose
    private Double remainingamount;
    @SerializedName("ReminderDays")
    @Expose
    private Integer reminderDays;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("ReturnReceiptId")
    @Expose
    private String returnReceiptId;
    @SerializedName("ReturnRequestId")
    @Expose
    private String returnRequestId;
    @SerializedName("ReturnStore")
    @Expose
    private String returnStore;
    @SerializedName("ReturnTerminal")
    @Expose
    private String returnTerminal;
    @SerializedName("ReturnTransactionId")
    @Expose
    private String returnTransactionId;
    @SerializedName("ReturnType")
    @Expose
    private Integer returnType;
    @SerializedName("RevReturnOtp")
    @Expose
    private String revReturnOtp;
    @SerializedName("RiderCode")
    @Expose
    private String riderCode;
    @SerializedName("RiderMobile")
    @Expose
    private String riderMobile;
    @SerializedName("RiderName")
    @Expose
    private String riderName;
    @SerializedName("RoundedAmount")
    @Expose
    private Double roundedAmount;
    @SerializedName("SEZ")
    @Expose
    private Integer sez;
    @SerializedName("SalesLine")
    @Expose
    private List<Object> salesLine = null;
    @SerializedName("SalesOrigin")
    @Expose
    private String salesOrigin;
    @SerializedName("ShippingCharges")
    @Expose
    private Double shippingCharges;
    @SerializedName("ShippingMethod")
    @Expose
    private String shippingMethod;
    @SerializedName("Staff")
    @Expose
    private String staff;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("StockStatus")
    @Expose
    private String stockStatus;
    @SerializedName("Store")
    @Expose
    private String store;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("TenderLine")
    @Expose
    private List<Object> tenderLine = null;
    @SerializedName("Terminal")
    @Expose
    private String terminal;
    @SerializedName("Tier")
    @Expose
    private String tier;
    @SerializedName("TimewhenTransClosed")
    @Expose
    private Integer timewhenTransClosed;
    @SerializedName("TotalDiscAmount")
    @Expose
    private Double totalDiscAmount;
    @SerializedName("TotalMRP")
    @Expose
    private Double totalMRP;
    @SerializedName("TotalManualDiscountAmount")
    @Expose
    private Double totalManualDiscountAmount;
    @SerializedName("TotalManualDiscountPercentage")
    @Expose
    private Double totalManualDiscountPercentage;
    @SerializedName("TotalTaxAmount")
    @Expose
    private Double totalTaxAmount;
    @SerializedName("TrackingRef")
    @Expose
    private String trackingRef;
    @SerializedName("TransDate")
    @Expose
    private String transDate;
    @SerializedName("TransType")
    @Expose
    private Integer transType;
    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("VendorCode")
    @Expose
    private String vendorCode;
    @SerializedName("VendorId")
    @Expose
    private String vendorId;

    public String getAWBNo() {
        return aWBNo;
    }

    public void setAWBNo(String aWBNo) {
        this.aWBNo = aWBNo;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
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

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
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

    public Double getCustDiscamount() {
        return custDiscamount;
    }

    public void setCustDiscamount(Double custDiscamount) {
        this.custDiscamount = custDiscamount;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDSPCode() {
        return dSPCode;
    }

    public void setDSPCode(String dSPCode) {
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

    public String getDiscountReferenceID() {
        return discountReferenceID;
    }

    public void setDiscountReferenceID(String discountReferenceID) {
        this.discountReferenceID = discountReferenceID;
    }

    public Double getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(Double discountStatus) {
        this.discountStatus = discountStatus;
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

    public Double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getDspName() {
        return dspName;
    }

    public void setDspName(String dspName) {
        this.dspName = dspName;
    }

    public Integer getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(Integer entryStatus) {
        this.entryStatus = entryStatus;
    }

    public Integer getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
    }

    public String getFwdReturnOtp() {
        return fwdReturnOtp;
    }

    public void setFwdReturnOtp(String fwdReturnOtp) {
        this.fwdReturnOtp = fwdReturnOtp;
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

    public String getHCOfferCode() {
        return hCOfferCode;
    }

    public void setHCOfferCode(String hCOfferCode) {
        this.hCOfferCode = hCOfferCode;
    }

    public String getIpno() {
        return ipno;
    }

    public void setIpno(String ipno) {
        this.ipno = ipno;
    }

    public String getIPSerialNO() {
        return iPSerialNO;
    }

    public void setIPSerialNO(String iPSerialNO) {
        this.iPSerialNO = iPSerialNO;
    }

    public Boolean getISAdvancePayment() {
        return iSAdvancePayment;
    }

    public void setISAdvancePayment(Boolean iSAdvancePayment) {
        this.iSAdvancePayment = iSAdvancePayment;
    }

    public Boolean getISBatchModifiedAllowed() {
        return iSBatchModifiedAllowed;
    }

    public void setISBatchModifiedAllowed(Boolean iSBatchModifiedAllowed) {
        this.iSBatchModifiedAllowed = iSBatchModifiedAllowed;
    }

    public Boolean getISBulkBilling() {
        return iSBulkBilling;
    }

    public void setISBulkBilling(Boolean iSBulkBilling) {
        this.iSBulkBilling = iSBulkBilling;
    }

    public Boolean getISCancelled() {
        return iSCancelled;
    }

    public void setISCancelled(Boolean iSCancelled) {
        this.iSCancelled = iSCancelled;
    }

    public Boolean getISHBPStore() {
        return iSHBPStore;
    }

    public void setISHBPStore(Boolean iSHBPStore) {
        this.iSHBPStore = iSHBPStore;
    }

    public Boolean getISHyperDelivered() {
        return iSHyperDelivered;
    }

    public void setISHyperDelivered(Boolean iSHyperDelivered) {
        this.iSHyperDelivered = iSHyperDelivered;
    }

    public Boolean getISHyperLocalDelivery() {
        return iSHyperLocalDelivery;
    }

    public void setISHyperLocalDelivery(Boolean iSHyperLocalDelivery) {
        this.iSHyperLocalDelivery = iSHyperLocalDelivery;
    }

    public Boolean getISOMSOrder() {
        return iSOMSOrder;
    }

    public void setISOMSOrder(Boolean iSOMSOrder) {
        this.iSOMSOrder = iSOMSOrder;
    }

    public Boolean getISOMSReturn() {
        return iSOMSReturn;
    }

    public void setISOMSReturn(Boolean iSOMSReturn) {
        this.iSOMSReturn = iSOMSReturn;
    }

    public Boolean getISOMSValidate() {
        return iSOMSValidate;
    }

    public void setISOMSValidate(Boolean iSOMSValidate) {
        this.iSOMSValidate = iSOMSValidate;
    }

    public Boolean getISOnlineOrder() {
        return iSOnlineOrder;
    }

    public void setISOnlineOrder(Boolean iSOnlineOrder) {
        this.iSOnlineOrder = iSOnlineOrder;
    }

    public Boolean getISPosted() {
        return iSPosted;
    }

    public void setISPosted(Boolean iSPosted) {
        this.iSPosted = iSPosted;
    }

    public Boolean getISPrescibeDiscount() {
        return iSPrescibeDiscount;
    }

    public void setISPrescibeDiscount(Boolean iSPrescibeDiscount) {
        this.iSPrescibeDiscount = iSPrescibeDiscount;
    }

    public Boolean getISReserved() {
        return iSReserved;
    }

    public void setISReserved(Boolean iSReserved) {
        this.iSReserved = iSReserved;
    }

    public Boolean getISReturnAllowed() {
        return iSReturnAllowed;
    }

    public void setISReturnAllowed(Boolean iSReturnAllowed) {
        this.iSReturnAllowed = iSReturnAllowed;
    }

    public Boolean getIsBulkDiscount() {
        return isBulkDiscount;
    }

    public void setIsBulkDiscount(Boolean isBulkDiscount) {
        this.isBulkDiscount = isBulkDiscount;
    }

    public Boolean getIsHDOrder() {
        return isHDOrder;
    }

    public void setIsHDOrder(Boolean isHDOrder) {
        this.isHDOrder = isHDOrder;
    }

    public Integer getIsMPOSBill() {
        return isMPOSBill;
    }

    public void setIsMPOSBill(Integer isMPOSBill) {
        this.isMPOSBill = isMPOSBill;
    }

    public Boolean getIsManualBill() {
        return isManualBill;
    }

    public void setIsManualBill(Boolean isManualBill) {
        this.isManualBill = isManualBill;
    }

    public Boolean getIsOMSJurnalsScreen() {
        return isOMSJurnalsScreen;
    }

    public void setIsOMSJurnalsScreen(Boolean isOMSJurnalsScreen) {
        this.isOMSJurnalsScreen = isOMSJurnalsScreen;
    }

    public Boolean getIsRepeatBill() {
        return isRepeatBill;
    }

    public void setIsRepeatBill(Boolean isRepeatBill) {
        this.isRepeatBill = isRepeatBill;
    }

    public Boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Boolean isReturn) {
        this.isReturn = isReturn;
    }

    public Boolean getIsStockCheck() {
        return isStockCheck;
    }

    public void setIsStockCheck(Boolean isStockCheck) {
        this.isStockCheck = isStockCheck;
    }

    public Boolean getIsTPASeller() {
        return isTPASeller;
    }

    public void setIsTPASeller(Boolean isTPASeller) {
        this.isTPASeller = isTPASeller;
    }

    public Boolean getIsUHIDBilling() {
        return isUHIDBilling;
    }

    public void setIsUHIDBilling(Boolean isUHIDBilling) {
        this.isUHIDBilling = isUHIDBilling;
    }

    public Boolean getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(Boolean isVoid) {
        this.isVoid = isVoid;
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

    public Double getOMSCreditAmount() {
        return oMSCreditAmount;
    }

    public void setOMSCreditAmount(Double oMSCreditAmount) {
        this.oMSCreditAmount = oMSCreditAmount;
    }

    public Object getOrderPrescriptionURL() {
        return orderPrescriptionURL;
    }

    public void setOrderPrescriptionURL(Object orderPrescriptionURL) {
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

    public Object getPickPackReservation() {
        return pickPackReservation;
    }

    public void setPickPackReservation(Object pickPackReservation) {
        this.pickPackReservation = pickPackReservation;
    }

    public String getPickupOtp() {
        return pickupOtp;
    }

    public void setPickupOtp(String pickupOtp) {
        this.pickupOtp = pickupOtp;
    }

    public Boolean getPickupStatus() {
        return pickupStatus;
    }

    public void setPickupStatus(Boolean pickupStatus) {
        this.pickupStatus = pickupStatus;
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

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public Boolean getRTOStatus() {
        return rTOStatus;
    }

    public void setRTOStatus(Boolean rTOStatus) {
        this.rTOStatus = rTOStatus;
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

    public String getReturnRequestId() {
        return returnRequestId;
    }

    public void setReturnRequestId(String returnRequestId) {
        this.returnRequestId = returnRequestId;
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

    public String getRevReturnOtp() {
        return revReturnOtp;
    }

    public void setRevReturnOtp(String revReturnOtp) {
        this.revReturnOtp = revReturnOtp;
    }

    public String getRiderCode() {
        return riderCode;
    }

    public void setRiderCode(String riderCode) {
        this.riderCode = riderCode;
    }

    public String getRiderMobile() {
        return riderMobile;
    }

    public void setRiderMobile(String riderMobile) {
        this.riderMobile = riderMobile;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public Double getRoundedAmount() {
        return roundedAmount;
    }

    public void setRoundedAmount(Double roundedAmount) {
        this.roundedAmount = roundedAmount;
    }

    public Integer getSez() {
        return sez;
    }

    public void setSez(Integer sez) {
        this.sez = sez;
    }

    public List<Object> getSalesLine() {
        return salesLine;
    }

    public void setSalesLine(List<Object> salesLine) {
        this.salesLine = salesLine;
    }

    public String getSalesOrigin() {
        return salesOrigin;
    }

    public void setSalesOrigin(String salesOrigin) {
        this.salesOrigin = salesOrigin;
    }

    public Double getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(Double shippingCharges) {
        this.shippingCharges = shippingCharges;
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

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
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

    public List<Object> getTenderLine() {
        return tenderLine;
    }

    public void setTenderLine(List<Object> tenderLine) {
        this.tenderLine = tenderLine;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Integer getTimewhenTransClosed() {
        return timewhenTransClosed;
    }

    public void setTimewhenTransClosed(Integer timewhenTransClosed) {
        this.timewhenTransClosed = timewhenTransClosed;
    }

    public Double getTotalDiscAmount() {
        return totalDiscAmount;
    }

    public void setTotalDiscAmount(Double totalDiscAmount) {
        this.totalDiscAmount = totalDiscAmount;
    }

    public Double getTotalMRP() {
        return totalMRP;
    }

    public void setTotalMRP(Double totalMRP) {
        this.totalMRP = totalMRP;
    }

    public Double getTotalManualDiscountAmount() {
        return totalManualDiscountAmount;
    }

    public void setTotalManualDiscountAmount(Double totalManualDiscountAmount) {
        this.totalManualDiscountAmount = totalManualDiscountAmount;
    }

    public Double getTotalManualDiscountPercentage() {
        return totalManualDiscountPercentage;
    }

    public void setTotalManualDiscountPercentage(Double totalManualDiscountPercentage) {
        this.totalManualDiscountPercentage = totalManualDiscountPercentage;
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
