package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPostOnlineOrderApiRequest {

    @SerializedName("OnlineOfferID")
    @Expose
    private String onlineOfferID;
    @SerializedName("ISPreEnqiryRequired")
    @Expose
    private Boolean iSPreEnqiryRequired;
    @SerializedName("ISAddItem")
    @Expose
    private Boolean iSAddItem;
    @SerializedName("ISDecreaseQty")
    @Expose
    private Boolean iSDecreaseQty;
    @SerializedName("ISRemoveLine")
    @Expose
    private Boolean iSRemoveLine;
    @SerializedName("IsSTKFulFillment")
    @Expose
    private Boolean isSTKFulFillment;
    @SerializedName("CorpCode")
    @Expose
    private String corpCode;
    @SerializedName("RequestData")
    @Expose
    private RequestData requestData;

    public String getOnlineOfferID() {
        return onlineOfferID;
    }

    public void setOnlineOfferID(String onlineOfferID) {
        this.onlineOfferID = onlineOfferID;
    }

    public Boolean getISPreEnqiryRequired() {
        return iSPreEnqiryRequired;
    }

    public void setISPreEnqiryRequired(Boolean iSPreEnqiryRequired) {
        this.iSPreEnqiryRequired = iSPreEnqiryRequired;
    }

    public Boolean getISAddItem() {
        return iSAddItem;
    }

    public void setISAddItem(Boolean iSAddItem) {
        this.iSAddItem = iSAddItem;
    }

    public Boolean getISDecreaseQty() {
        return iSDecreaseQty;
    }

    public void setISDecreaseQty(Boolean iSDecreaseQty) {
        this.iSDecreaseQty = iSDecreaseQty;
    }

    public Boolean getISRemoveLine() {
        return iSRemoveLine;
    }

    public void setISRemoveLine(Boolean iSRemoveLine) {
        this.iSRemoveLine = iSRemoveLine;
    }

    public Boolean getIsSTKFulFillment() {
        return isSTKFulFillment;
    }

    public void setIsSTKFulFillment(Boolean isSTKFulFillment) {
        this.isSTKFulFillment = isSTKFulFillment;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public static class BillDetails {

        @SerializedName("EmpID")
        @Expose
        private String empID;
        @SerializedName("BillDateTime")
        @Expose
        private String billDateTime;
        @SerializedName("BillNumber")
        @Expose
        private String billNumber;
        @SerializedName("BilledBy")
        @Expose
        private String billedBy;
        @SerializedName("InvoiceValue")
        @Expose
        private Double invoiceValue;
        @SerializedName("CashValue")
        @Expose
        private Double cashValue;
        @SerializedName("CreditValue")
        @Expose
        private Integer creditValue;
        @SerializedName("CodValue")
        @Expose
        private Integer codValue;

        public String getEmpID() {
            return empID;
        }

        public void setEmpID(String empID) {
            this.empID = empID;
        }

        public String getBillDateTime() {
            return billDateTime;
        }

        public void setBillDateTime(String billDateTime) {
            this.billDateTime = billDateTime;
        }

        public String getBillNumber() {
            return billNumber;
        }

        public void setBillNumber(String billNumber) {
            this.billNumber = billNumber;
        }

        public String getBilledBy() {
            return billedBy;
        }

        public void setBilledBy(String billedBy) {
            this.billedBy = billedBy;
        }

        public Double getInvoiceValue() {
            return invoiceValue;
        }

        public void setInvoiceValue(Double invoiceValue) {
            this.invoiceValue = invoiceValue;
        }

        public Double getCashValue() {
            return cashValue;
        }

        public void setCashValue(Double cashValue) {
            this.cashValue = cashValue;
        }

        public Integer getCreditValue() {
            return creditValue;
        }

        public void setCreditValue(Integer creditValue) {
            this.creditValue = creditValue;
        }

        public Integer getCodValue() {
            return codValue;
        }

        public void setCodValue(Integer codValue) {
            this.codValue = codValue;
        }

    }
    public static class ItemDetail {

        @SerializedName("PosItemId")
        @Expose
        private String posItemId;
        @SerializedName("ItemId")
        @Expose
        private String itemId;
        @SerializedName("ItemName")
        @Expose
        private String itemName;
        @SerializedName("BatchId")
        @Expose
        private String batchId;
        @SerializedName("ReqQty")
        @Expose
        private Integer reqQty;
        @SerializedName("IssuedQty")
        @Expose
        private Integer issuedQty;
        @SerializedName("MRP")
        @Expose
        private Double mrp;
        @SerializedName("DiscountMRP")
        @Expose
        private Double discountMRP;
        @SerializedName("LineAmount")
        @Expose
        private Integer lineAmount;
        @SerializedName("LineAmountWithDiscount")
        @Expose
        private Double lineAmountWithDiscount;
        @SerializedName("Status")
        @Expose
        private Boolean status;
        @SerializedName("IsSubstitute")
        @Expose
        private Boolean isSubstitute;
        @SerializedName("Substitute")
        @Expose
        private List<Substitute> substitute = null;

        public String getPosItemId() {
            return posItemId;
        }

        public void setPosItemId(String posItemId) {
            this.posItemId = posItemId;
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

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public Integer getReqQty() {
            return reqQty;
        }

        public void setReqQty(Integer reqQty) {
            this.reqQty = reqQty;
        }

        public Integer getIssuedQty() {
            return issuedQty;
        }

        public void setIssuedQty(Integer issuedQty) {
            this.issuedQty = issuedQty;
        }

        public Double getMrp() {
            return mrp;
        }

        public void setMrp(Double mrp) {
            this.mrp = mrp;
        }

        public Double getDiscountMRP() {
            return discountMRP;
        }

        public void setDiscountMRP(Double discountMRP) {
            this.discountMRP = discountMRP;
        }

        public Integer getLineAmount() {
            return lineAmount;
        }

        public void setLineAmount(Integer lineAmount) {
            this.lineAmount = lineAmount;
        }

        public Double getLineAmountWithDiscount() {
            return lineAmountWithDiscount;
        }

        public void setLineAmountWithDiscount(Double lineAmountWithDiscount) {
            this.lineAmountWithDiscount = lineAmountWithDiscount;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Boolean getIsSubstitute() {
            return isSubstitute;
        }

        public void setIsSubstitute(Boolean isSubstitute) {
            this.isSubstitute = isSubstitute;
        }

        public List<Substitute> getSubstitute() {
            return substitute;
        }

        public void setSubstitute(List<Substitute> substitute) {
            this.substitute = substitute;
        }

    }
    public static class RequestData {

        @SerializedName("CorpCode")
        @Expose
        private String corpCode;
        @SerializedName("TrackingRef")
        @Expose
        private String trackingRef;
        @SerializedName("Url")
        @Expose
        private String url;
        @SerializedName("OrderNo")
        @Expose
        private String orderNo;
        @SerializedName("SiteId")
        @Expose
        private String siteId;
        @SerializedName("VendorOrderno")
        @Expose
        private String vendorOrderno;
        @SerializedName("PaymentMode")
        @Expose
        private String paymentMode;
        @SerializedName("AtmNO")
        @Expose
        private String atmNO;
        @SerializedName("Docnum")
        @Expose
        private String docnum;
        @SerializedName("Remarks")
        @Expose
        private String remarks;
        @SerializedName("RequestType")
        @Expose
        private String requestType;
        @SerializedName("OTP")
        @Expose
        private String otp;
        @SerializedName("VendorName")
        @Expose
        private String vendorName;
        @SerializedName("DoctorName")
        @Expose
        private String doctorName;
        @SerializedName("BillDetails")
        @Expose
        private BillDetails billDetails;
        @SerializedName("ItemDetails")
        @Expose
        private List<ItemDetail> itemDetails = null;

        public String getCorpCode() {
            return corpCode;
        }

        public void setCorpCode(String corpCode) {
            this.corpCode = corpCode;
        }

        public String getTrackingRef() {
            return trackingRef;
        }

        public void setTrackingRef(String trackingRef) {
            this.trackingRef = trackingRef;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getVendorOrderno() {
            return vendorOrderno;
        }

        public void setVendorOrderno(String vendorOrderno) {
            this.vendorOrderno = vendorOrderno;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getAtmNO() {
            return atmNO;
        }

        public void setAtmNO(String atmNO) {
            this.atmNO = atmNO;
        }

        public String getDocnum() {
            return docnum;
        }

        public void setDocnum(String docnum) {
            this.docnum = docnum;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getRequestType() {
            return requestType;
        }

        public void setRequestType(String requestType) {
            this.requestType = requestType;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getVendorName() {
            return vendorName;
        }

        public void setVendorName(String vendorName) {
            this.vendorName = vendorName;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public BillDetails getBillDetails() {
            return billDetails;
        }

        public void setBillDetails(BillDetails billDetails) {
            this.billDetails = billDetails;
        }

        public List<ItemDetail> getItemDetails() {
            return itemDetails;
        }

        public void setItemDetails(List<ItemDetail> itemDetails) {
            this.itemDetails = itemDetails;
        }

    }
    public static class Substitute {

        @SerializedName("SubstituteItemID")
        @Expose
        private String substituteItemID;
        @SerializedName("SubstituteItemName")
        @Expose
        private String substituteItemName;
        @SerializedName("SubstituteBatchId")
        @Expose
        private String substituteBatchId;
        @SerializedName("IssuedQty")
        @Expose
        private Integer issuedQty;

        public String getSubstituteItemID() {
            return substituteItemID;
        }

        public void setSubstituteItemID(String substituteItemID) {
            this.substituteItemID = substituteItemID;
        }

        public String getSubstituteItemName() {
            return substituteItemName;
        }

        public void setSubstituteItemName(String substituteItemName) {
            this.substituteItemName = substituteItemName;
        }

        public String getSubstituteBatchId() {
            return substituteBatchId;
        }

        public void setSubstituteBatchId(String substituteBatchId) {
            this.substituteBatchId = substituteBatchId;
        }

        public Integer getIssuedQty() {
            return issuedQty;
        }

        public void setIssuedQty(Integer issuedQty) {
            this.issuedQty = issuedQty;
        }

    }
}