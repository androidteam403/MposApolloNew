package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponDiscount {


    @Expose
    @SerializedName("CouponEnquiryDetailsResult")
    private CouponEnquiryDetailsResultEntity CouponEnquiryDetailsResult;
    @Expose
    @SerializedName("CouponEnquiryRequest")
    private CouponEnquiryRequestEntity CouponEnquiryRequest;
    @Expose
    @SerializedName("PosSalesTransaction")
    private CalculatePosTransactionRes PosSalesTransaction;
    @Expose
    @SerializedName("EprescriptionMaxDicountValue")
    private int EprescriptionMaxDicountValue;
    @Expose
    @SerializedName("EprescriptionDiscountPer")
    private int EprescriptionDiscountPer;
    @Expose
    @SerializedName("CouponDiscountURLApolloServer")
    private String CouponDiscountURLApolloServer;
    @Expose
    @SerializedName("CouponCode")
    private String CouponCode;
    @Expose
    @SerializedName("HealingCardThresholdAmout")
    private int HealingCardThresholdAmout;
    @Expose
    @SerializedName("CreditAmount")
    private int CreditAmount;
    @Expose
    @SerializedName("ISDiscountCodeRequired")
    private int ISDiscountCodeRequired;
    @Expose
    @SerializedName("IsClearAllDiscount")
    private boolean IsClearAllDiscount;
    @Expose
    @SerializedName("IsAutoDiscount")
    private boolean IsAutoDiscount;
    @Expose
    @SerializedName("IsNormalSale")
    private boolean IsNormalSale;
    @Expose
    @SerializedName("OPTValidate")
    private boolean OPTValidate;
    @Expose
    @SerializedName("IsOTPRequired")
    private int IsOTPRequired;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("RequestType")
    private String RequestType;

    public CouponEnquiryDetailsResultEntity getCouponEnquiryDetailsResult() {
        return CouponEnquiryDetailsResult;
    }

    public void setCouponEnquiryDetailsResult(CouponEnquiryDetailsResultEntity CouponEnquiryDetailsResult) {
        this.CouponEnquiryDetailsResult = CouponEnquiryDetailsResult;
    }

    public CouponEnquiryRequestEntity getCouponEnquiryRequest() {
        return CouponEnquiryRequest;
    }

    public void setCouponEnquiryRequest(CouponEnquiryRequestEntity CouponEnquiryRequest) {
        this.CouponEnquiryRequest = CouponEnquiryRequest;
    }

    public CalculatePosTransactionRes getPosSalesTransaction() {
        return PosSalesTransaction;
    }

    public void setPosSalesTransaction(CalculatePosTransactionRes PosSalesTransaction) {
        this.PosSalesTransaction = PosSalesTransaction;
    }

    public int getEprescriptionMaxDicountValue() {
        return EprescriptionMaxDicountValue;
    }

    public void setEprescriptionMaxDicountValue(int EprescriptionMaxDicountValue) {
        this.EprescriptionMaxDicountValue = EprescriptionMaxDicountValue;
    }

    public int getEprescriptionDiscountPer() {
        return EprescriptionDiscountPer;
    }

    public void setEprescriptionDiscountPer(int EprescriptionDiscountPer) {
        this.EprescriptionDiscountPer = EprescriptionDiscountPer;
    }

    public String getCouponDiscountURLApolloServer() {
        return CouponDiscountURLApolloServer;
    }

    public void setCouponDiscountURLApolloServer(String CouponDiscountURLApolloServer) {
        this.CouponDiscountURLApolloServer = CouponDiscountURLApolloServer;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String CouponCode) {
        this.CouponCode = CouponCode;
    }

    public int getHealingCardThresholdAmout() {
        return HealingCardThresholdAmout;
    }

    public void setHealingCardThresholdAmout(int HealingCardThresholdAmout) {
        this.HealingCardThresholdAmout = HealingCardThresholdAmout;
    }

    public int getCreditAmount() {
        return CreditAmount;
    }

    public void setCreditAmount(int CreditAmount) {
        this.CreditAmount = CreditAmount;
    }

    public int getISDiscountCodeRequired() {
        return ISDiscountCodeRequired;
    }

    public void setISDiscountCodeRequired(int ISDiscountCodeRequired) {
        this.ISDiscountCodeRequired = ISDiscountCodeRequired;
    }

    public boolean getIsClearAllDiscount() {
        return IsClearAllDiscount;
    }

    public void setIsClearAllDiscount(boolean IsClearAllDiscount) {
        this.IsClearAllDiscount = IsClearAllDiscount;
    }

    public boolean getIsAutoDiscount() {
        return IsAutoDiscount;
    }

    public void setIsAutoDiscount(boolean IsAutoDiscount) {
        this.IsAutoDiscount = IsAutoDiscount;
    }

    public boolean getIsNormalSale() {
        return IsNormalSale;
    }

    public void setIsNormalSale(boolean IsNormalSale) {
        this.IsNormalSale = IsNormalSale;
    }

    public boolean getOPTValidate() {
        return OPTValidate;
    }

    public void setOPTValidate(boolean OPTValidate) {
        this.OPTValidate = OPTValidate;
    }

    public int getIsOTPRequired() {
        return IsOTPRequired;
    }

    public void setIsOTPRequired(int IsOTPRequired) {
        this.IsOTPRequired = IsOTPRequired;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(int RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String RequestType) {
        this.RequestType = RequestType;
    }

    public static class CouponEnquiryDetailsResultEntity {


        @Expose
        @SerializedName("CoupenDetailsResult")
        private CoupenDetailsResultEntity CoupenDetailsResult;

        public CoupenDetailsResultEntity getCoupenDetailsResult() {
            return CoupenDetailsResult;
        }

        public void setCoupenDetailsResult(CoupenDetailsResultEntity CoupenDetailsResult) {
            this.CoupenDetailsResult = CoupenDetailsResult;
        }


    }
    public static class CoupenDetailsResultEntity {
        @Expose
        @SerializedName("ValidFrom")
        private String ValidFrom;
        @Expose
        @SerializedName("TenderType")
        private String TenderType;
        @Expose
        @SerializedName("StoreId")
        private String StoreId;
        @Expose
        @SerializedName("SingleUse")
        private String SingleUse;
        @Expose
        @SerializedName("RequestStatus")
        private boolean RequestStatus;
        @Expose
        @SerializedName("RequestMessage")
        private String RequestMessage;
        @Expose
        @SerializedName("ItemCode")
        private String ItemCode;
        @Expose
        @SerializedName("DueDate")
        private String DueDate;
        @Expose
        @SerializedName("DiscountType")
        private String DiscountType;
        @Expose
        @SerializedName("DiscountCode")
        private String DiscountCode;
        @Expose
        @SerializedName("CreditAmount")
        private String CreditAmount;
        @Expose
        @SerializedName("CouponNo")
        private String CouponNo;
        @Expose
        @SerializedName("CorporateCode")
        private String CorporateCode;

        public String getValidFrom() {
            return ValidFrom;
        }

        public void setValidFrom(String ValidFrom) {
            this.ValidFrom = ValidFrom;
        }

        public String getTenderType() {
            return TenderType;
        }

        public void setTenderType(String TenderType) {
            this.TenderType = TenderType;
        }

        public String getStoreId() {
            return StoreId;
        }

        public void setStoreId(String StoreId) {
            this.StoreId = StoreId;
        }

        public String getSingleUse() {
            return SingleUse;
        }

        public void setSingleUse(String SingleUse) {
            this.SingleUse = SingleUse;
        }

        public boolean getRequestStatus() {
            return RequestStatus;
        }

        public void setRequestStatus(boolean RequestStatus) {
            this.RequestStatus = RequestStatus;
        }

        public String getRequestMessage() {
            return RequestMessage;
        }

        public void setRequestMessage(String RequestMessage) {
            this.RequestMessage = RequestMessage;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public String getDueDate() {
            return DueDate;
        }

        public void setDueDate(String DueDate) {
            this.DueDate = DueDate;
        }

        public String getDiscountType() {
            return DiscountType;
        }

        public void setDiscountType(String DiscountType) {
            this.DiscountType = DiscountType;
        }

        public String getDiscountCode() {
            return DiscountCode;
        }

        public void setDiscountCode(String DiscountCode) {
            this.DiscountCode = DiscountCode;
        }

        public String getCreditAmount() {
            return CreditAmount;
        }

        public void setCreditAmount(String CreditAmount) {
            this.CreditAmount = CreditAmount;
        }

        public String getCouponNo() {
            return CouponNo;
        }

        public void setCouponNo(String CouponNo) {
            this.CouponNo = CouponNo;
        }

        public String getCorporateCode() {
            return CorporateCode;
        }

        public void setCorporateCode(String CorporateCode) {
            this.CorporateCode = CorporateCode;
        }
    }

    public static class CouponEnquiryRequestEntity {
        @Expose
        @SerializedName("SiteId")
        private String SiteId;
        @Expose
        @SerializedName("CategoryAmount")
        private String CategoryAmount;
        @Expose
        @SerializedName("InvoiceAmount")
        private String InvoiceAmount;
        @Expose
        @SerializedName("TransactionId")
        private String TransactionId;
        @Expose
        @SerializedName("RequestType")
        private String RequestType;
        @Expose
        @SerializedName("CorporateCode")
        private String CorporateCode;
        @Expose
        @SerializedName("CouponCode")
        private String CouponCode;

        public String getSiteId() {
            return SiteId;
        }

        public void setSiteId(String SiteId) {
            this.SiteId = SiteId;
        }

        public String getCategoryAmount() {
            return CategoryAmount;
        }

        public void setCategoryAmount(String CategoryAmount) {
            this.CategoryAmount = CategoryAmount;
        }

        public String getInvoiceAmount() {
            return InvoiceAmount;
        }

        public void setInvoiceAmount(String InvoiceAmount) {
            this.InvoiceAmount = InvoiceAmount;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String TransactionId) {
            this.TransactionId = TransactionId;
        }

        public String getRequestType() {
            return RequestType;
        }

        public void setRequestType(String RequestType) {
            this.RequestType = RequestType;
        }

        public String getCorporateCode() {
            return CorporateCode;
        }

        public void setCorporateCode(String CorporateCode) {
            this.CorporateCode = CorporateCode;
        }

        public String getCouponCode() {
            return CouponCode;
        }

        public void setCouponCode(String CouponCode) {
            this.CouponCode = CouponCode;
        }
    }


}
