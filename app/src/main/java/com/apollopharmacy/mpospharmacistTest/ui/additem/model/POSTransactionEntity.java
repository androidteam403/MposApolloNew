package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class POSTransactionEntity implements Serializable {
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


        public void setTenderLine(List<TenderLineEntity> tenderLine) {
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

        public List<TenderLineEntity> getTenderLine() {
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



        //added aditional parameters for Circle members ship....
        @SerializedName("AgeGroup")
        @Expose
        private String ageGroup;

        @SerializedName("AllowedTenderType")
        @Expose
        private String allowedTenderType;

        @SerializedName("ApprovedID")
        @Expose
        private String approvedID;

        @SerializedName("AWBNo")
        @Expose
        private String aWBNo;

        @SerializedName("CreatedDateTime")
        @Expose
        private String createdDateTime;

        @SerializedName("CustomerType")
        @Expose
        private String customerType;

        @SerializedName("DiscountRef")
        @Expose
        private String discountRef;

        @SerializedName("DiscountReferenceID")
        @Expose
        private String discountReferenceID;

        @SerializedName("DiscountStatus")
        @Expose
        private Integer discountStatus;

        @SerializedName("DSPCode")
        @Expose
        private String dSPCode;

        @SerializedName("ExpiryDays")
        @Expose
        private Integer expiryDays;

        @SerializedName("HCOfferCode")
        @Expose
        private String hCOfferCode;

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

        @SerializedName("ISOMSValidate")
        @Expose
        private Boolean iSOMSValidate;

        @SerializedName("ISOnlineOrder")
        @Expose
        private Boolean iSOnlineOrder;

        @SerializedName("IsUHIDBilling")
        @Expose
        private Boolean isUHIDBilling;

        @SerializedName("OMSCreditAmount")
        @Expose
        private double oMSCreditAmount;

        @SerializedName("OrderPrescriptionURL")
        @Expose
        private ArrayList<CustomerDataResBean.OrderPrescriptionObj> orderPrescriptionURL;

        @SerializedName("PatientID")
        @Expose
        private String patientID;

        @SerializedName("ShippingCharges")
        @Expose
        private double shippingCharges;

        @SerializedName("ShippingMethodDesc")
        @Expose
        private String shippingMethodDesc;

        @SerializedName("StockStatus")
        @Expose
        private String stockStatus;

        @SerializedName("Tier")
        @Expose
        private String tier;

        @SerializedName("VendorCode")
        @Expose
        private String vendorCode;


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

        public Boolean getIsUHIDBilling() {
                return isUHIDBilling;
        }

        public void setIsUHIDBilling(Boolean isUHIDBilling) {
                this.isUHIDBilling = isUHIDBilling;
        }


        public String getApprovedID() {
                return approvedID;
        }

        public void setApprovedID(String approvedID) {
                this.approvedID = approvedID;
        }

        public String getAWBNo() {
                return aWBNo;
        }

        public void setAWBNo(String aWBNo) {
                this.aWBNo = aWBNo;
        }

        public String getCreatedDateTime() {
                return createdDateTime;
        }

        public void setCreatedDateTime(String createdDateTime) {
                this.createdDateTime = createdDateTime;
        }

        public String getCustomerType() {
                return customerType;
        }

        public void setCustomerType(String customerType) {
                this.customerType = customerType;
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

        public Integer getDiscountStatus() {
                return discountStatus;
        }

        public void setDiscountStatus(Integer discountStatus) {
                this.discountStatus = discountStatus;
        }

        public String getDSPCode() {
                return dSPCode;
        }

        public void setDSPCode(String dSPCode) {
                this.dSPCode = dSPCode;
        }

        public Integer getExpiryDays() {
                return expiryDays;
        }

        public void setExpiryDays(Integer expiryDays) {
                this.expiryDays = expiryDays;
        }

        public String getHCOfferCode() {
                return hCOfferCode;
        }

        public void setHCOfferCode(String hCOfferCode) {
                this.hCOfferCode = hCOfferCode;
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

        public double getOMSCreditAmount() {
                return oMSCreditAmount;
        }

        public void setOMSCreditAmount(double oMSCreditAmount) {
                this.oMSCreditAmount = oMSCreditAmount;
        }

        public ArrayList<CustomerDataResBean.OrderPrescriptionObj> getOrderPrescriptionURL() {
                return orderPrescriptionURL;
        }

        public void setOrderPrescriptionURL(ArrayList<CustomerDataResBean.OrderPrescriptionObj> orderPrescriptionURL) {
                this.orderPrescriptionURL = orderPrescriptionURL;
        }

        public String getPatientID() {
                return patientID;
        }

        public void setPatientID(String patientID) {
                this.patientID = patientID;
        }

        public double getShippingCharges() {
                return shippingCharges;
        }

        public void setShippingCharges(double shippingCharges) {
                this.shippingCharges = shippingCharges;
        }

        public String getShippingMethodDesc() {
                return shippingMethodDesc;
        }

        public void setShippingMethodDesc(String shippingMethodDesc) {
                this.shippingMethodDesc = shippingMethodDesc;
        }

        public String getStockStatus() {
                return stockStatus;
        }

        public void setStockStatus(String stockStatus) {
                this.stockStatus = stockStatus;
        }

        public String getTier() {
                return tier;
        }

        public void setTier(String tier) {
                this.tier = tier;
        }

        public String getVendorCode() {
                return vendorCode;
        }

        public void setVendorCode(String vendorCode) {
                this.vendorCode = vendorCode;
        }
}