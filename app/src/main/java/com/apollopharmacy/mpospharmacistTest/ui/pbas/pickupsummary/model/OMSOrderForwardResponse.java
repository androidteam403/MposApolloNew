package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class OMSOrderForwardResponse implements Serializable {

    @SerializedName("FulfillmentID")
    @Expose
    private String fulfillmentID;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("RequestType")
    @Expose
    private Integer requestType;
    @SerializedName("ReservedSalesLine")
    @Expose
    private List<ReservedSalesLine> reservedSalesLine = null;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("TerminalID")
    @Expose
    private String terminalID;
    private final static long serialVersionUID = -6835121404313861103L;

    public String getFulfillmentID() {
        return fulfillmentID;
    }

    public void setFulfillmentID(String fulfillmentID) {
        this.fulfillmentID = fulfillmentID;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public List<ReservedSalesLine> getReservedSalesLine() {
        return reservedSalesLine;
    }

    public void setReservedSalesLine(List<ReservedSalesLine> reservedSalesLine) {
        this.reservedSalesLine = reservedSalesLine;
    }

    public class ReservedSalesLine implements Serializable {

        @SerializedName("Additionaltax")
        @Expose
        private Integer additionaltax;
        @SerializedName("ApplyDiscount")
        @Expose
        private Boolean applyDiscount;
        @SerializedName("Barcode")
        @Expose
        private String barcode;
        @SerializedName("BaseAmount")
        @Expose
        private double baseAmount;
        @SerializedName("CESSPerc")
        @Expose
        private Integer cESSPerc;
        @SerializedName("CESSTaxCode")
        @Expose
        private String cESSTaxCode;
        @SerializedName("CGSTPerc")
        @Expose
        private double cGSTPerc;
        @SerializedName("CGSTTaxCode")
        @Expose
        private String cGSTTaxCode;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("CategoryCode")
        @Expose
        private String categoryCode;
        @SerializedName("CategoryReference")
        @Expose
        private String categoryReference;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("DPCO")
        @Expose
        private Boolean dpco;
        @SerializedName("DiscAmount")
        @Expose
        private double discAmount;
        @SerializedName("DiscId")
        @Expose
        private String discId;
        @SerializedName("DiscOfferId")
        @Expose
        private String discOfferId;
        @SerializedName("DiscountStructureType")
        @Expose
        private Integer discountStructureType;
        @SerializedName("DiscountType")
        @Expose
        private String discountType;
        @SerializedName("DiseaseType")
        @Expose
        private String diseaseType;
        @SerializedName("Expiry")
        @Expose
        private String expiry;
        @SerializedName("Hsncode_In")
        @Expose
        private String hsncodeIn;
        @SerializedName("IGSTPerc")
        @Expose
        private Integer iGSTPerc;
        @SerializedName("IGSTTaxCode")
        @Expose
        private String iGSTTaxCode;
        @SerializedName("ISPrescribed")
        @Expose
        private Integer iSPrescribed;
        @SerializedName("ISReserved")
        @Expose
        private Boolean iSReserved;
        @SerializedName("ISStockAvailable")
        @Expose
        private Boolean iSStockAvailable;
        @SerializedName("InventBatchId")
        @Expose
        private String inventBatchId;
        @SerializedName("IsChecked")
        @Expose
        private Boolean isChecked;
        @SerializedName("IsPriceOverride")
        @Expose
        private Boolean isPriceOverride;
        @SerializedName("IsSubsitute")
        @Expose
        private Boolean isSubsitute;
        @SerializedName("IsVoid")
        @Expose
        private Boolean isVoid;
        @SerializedName("ItemId")
        @Expose
        private String itemId;
        @SerializedName("ItemName")
        @Expose
        private String itemName;
        @SerializedName("LineDiscPercentage")
        @Expose
        private Integer lineDiscPercentage;
        @SerializedName("LineManualDiscountAmount")
        @Expose
        private Integer lineManualDiscountAmount;
        @SerializedName("LineManualDiscountPercentage")
        @Expose
        private Integer lineManualDiscountPercentage;
        @SerializedName("LineNo")
        @Expose
        private Integer lineNo;
        @SerializedName("LinedscAmount")
        @Expose
        private double linedscAmount;
        @SerializedName("MMGroupId")
        @Expose
        private String mMGroupId;
        @SerializedName("MRP")
        @Expose
        private Double mrp;
        @SerializedName("ManufacturerCode")
        @Expose
        private String manufacturerCode;
        @SerializedName("ManufacturerName")
        @Expose
        private String manufacturerName;
        @SerializedName("MixMode")
        @Expose
        private Boolean mixMode;
        @SerializedName("ModifyBatchId")
        @Expose
        private String modifyBatchId;
        @SerializedName("NetAmount")
        @Expose
        private Double netAmount;
        @SerializedName("NetAmountInclTax")
        @Expose
        private Double netAmountInclTax;
        @SerializedName("OfferAmount")
        @Expose
        private Integer offerAmount;
        @SerializedName("OfferDiscountType")
        @Expose
        private Integer offerDiscountType;
        @SerializedName("OfferDiscountValue")
        @Expose
        private Integer offerDiscountValue;
        @SerializedName("OfferQty")
        @Expose
        private Integer offerQty;
        @SerializedName("OfferType")
        @Expose
        private Integer offerType;
        @SerializedName("OmsLineID")
        @Expose
        private Integer omsLineID;
        @SerializedName("OmsLineRECID")
        @Expose
        private Integer omsLineRECID;
        @SerializedName("OrderStatus")
        @Expose
        private Integer orderStatus;
        @SerializedName("OriginalPrice")
        @Expose
        private Double originalPrice;
        @SerializedName("PeriodicDiscAmount")
        @Expose
        private Integer periodicDiscAmount;
        @SerializedName("PreviewText")
        @Expose
        private String previewText;
        @SerializedName("Price")
        @Expose
        private Double price;
        @SerializedName("ProductRecID")
        @Expose
        private String productRecID;
        @SerializedName("Qty")
        @Expose
        private Integer qty;
        @SerializedName("RemainderDays")
        @Expose
        private Integer remainderDays;
        @SerializedName("RemainingQty")
        @Expose
        private Integer remainingQty;
        @SerializedName("RetailCategoryRecID")
        @Expose
        private String retailCategoryRecID;
        @SerializedName("RetailMainCategoryRecID")
        @Expose
        private String retailMainCategoryRecID;
        @SerializedName("RetailSubCategoryRecID")
        @Expose
        private String retailSubCategoryRecID;
        @SerializedName("ReturnQty")
        @Expose
        private Integer returnQty;
        @SerializedName("SGSTPerc")
        @Expose
        private double sGSTPerc;
        @SerializedName("SGSTTaxCode")
        @Expose
        private String sGSTTaxCode;
        @SerializedName("ScheduleCategory")
        @Expose
        private String scheduleCategory;
        @SerializedName("ScheduleCategoryCode")
        @Expose
        private String scheduleCategoryCode;
        @SerializedName("StockQty")
        @Expose
        private Integer stockQty;
        @SerializedName("SubCategory")
        @Expose
        private String subCategory;
        @SerializedName("SubCategoryCode")
        @Expose
        private String subCategoryCode;
        @SerializedName("SubClassification")
        @Expose
        private String subClassification;
        @SerializedName("Tax")
        @Expose
        private Integer tax;
        @SerializedName("TaxAmount")
        @Expose
        private Double taxAmount;
        @SerializedName("Total")
        @Expose
        private double total;
        @SerializedName("TotalDiscAmount")
        @Expose
        private double totalDiscAmount;
        @SerializedName("TotalDiscPct")
        @Expose
        private double totalDiscPct;
        @SerializedName("TotalRoundedAmount")
        @Expose
        private Integer totalRoundedAmount;
        @SerializedName("TotalTax")
        @Expose
        private Integer totalTax;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("UnitPrice")
        @Expose
        private Double unitPrice;
        @SerializedName("UnitQty")
        @Expose
        private Integer unitQty;
        @SerializedName("VariantId")
        @Expose
        private String variantId;
        @SerializedName("expirydays")
        @Expose
        private Integer expirydays;
        private final static long serialVersionUID = 835135942759730218L;

        public Integer getAdditionaltax() {
            return additionaltax;
        }

        public void setAdditionaltax(Integer additionaltax) {
            this.additionaltax = additionaltax;
        }

        public Boolean getApplyDiscount() {
            return applyDiscount;
        }

        public void setApplyDiscount(Boolean applyDiscount) {
            this.applyDiscount = applyDiscount;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public double getBaseAmount() {
            return baseAmount;
        }

        public void setBaseAmount(double baseAmount) {
            this.baseAmount = baseAmount;
        }

        public Integer getCESSPerc() {
            return cESSPerc;
        }

        public void setCESSPerc(Integer cESSPerc) {
            this.cESSPerc = cESSPerc;
        }

        public String getCESSTaxCode() {
            return cESSTaxCode;
        }

        public void setCESSTaxCode(String cESSTaxCode) {
            this.cESSTaxCode = cESSTaxCode;
        }

        public double getCGSTPerc() {
            return cGSTPerc;
        }

        public void setCGSTPerc(double cGSTPerc) {
            this.cGSTPerc = cGSTPerc;
        }

        public String getCGSTTaxCode() {
            return cGSTTaxCode;
        }

        public void setCGSTTaxCode(String cGSTTaxCode) {
            this.cGSTTaxCode = cGSTTaxCode;
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

        public String getCategoryReference() {
            return categoryReference;
        }

        public void setCategoryReference(String categoryReference) {
            this.categoryReference = categoryReference;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Boolean getDpco() {
            return dpco;
        }

        public void setDpco(Boolean dpco) {
            this.dpco = dpco;
        }

        public double getDiscAmount() {
            return discAmount;
        }

        public void setDiscAmount(double discAmount) {
            this.discAmount = discAmount;
        }

        public String getDiscId() {
            return discId;
        }

        public void setDiscId(String discId) {
            this.discId = discId;
        }

        public String getDiscOfferId() {
            return discOfferId;
        }

        public void setDiscOfferId(String discOfferId) {
            this.discOfferId = discOfferId;
        }

        public Integer getDiscountStructureType() {
            return discountStructureType;
        }

        public void setDiscountStructureType(Integer discountStructureType) {
            this.discountStructureType = discountStructureType;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getDiseaseType() {
            return diseaseType;
        }

        public void setDiseaseType(String diseaseType) {
            this.diseaseType = diseaseType;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getHsncodeIn() {
            return hsncodeIn;
        }

        public void setHsncodeIn(String hsncodeIn) {
            this.hsncodeIn = hsncodeIn;
        }

        public Integer getIGSTPerc() {
            return iGSTPerc;
        }

        public void setIGSTPerc(Integer iGSTPerc) {
            this.iGSTPerc = iGSTPerc;
        }

        public String getIGSTTaxCode() {
            return iGSTTaxCode;
        }

        public void setIGSTTaxCode(String iGSTTaxCode) {
            this.iGSTTaxCode = iGSTTaxCode;
        }

        public Integer getISPrescribed() {
            return iSPrescribed;
        }

        public void setISPrescribed(Integer iSPrescribed) {
            this.iSPrescribed = iSPrescribed;
        }

        public Boolean getISReserved() {
            return iSReserved;
        }

        public void setISReserved(Boolean iSReserved) {
            this.iSReserved = iSReserved;
        }

        public Boolean getISStockAvailable() {
            return iSStockAvailable;
        }

        public void setISStockAvailable(Boolean iSStockAvailable) {
            this.iSStockAvailable = iSStockAvailable;
        }

        public String getInventBatchId() {
            return inventBatchId;
        }

        public void setInventBatchId(String inventBatchId) {
            this.inventBatchId = inventBatchId;
        }

        public Boolean getIsChecked() {
            return isChecked;
        }

        public void setIsChecked(Boolean isChecked) {
            this.isChecked = isChecked;
        }

        public Boolean getIsPriceOverride() {
            return isPriceOverride;
        }

        public void setIsPriceOverride(Boolean isPriceOverride) {
            this.isPriceOverride = isPriceOverride;
        }

        public Boolean getIsSubsitute() {
            return isSubsitute;
        }

        public void setIsSubsitute(Boolean isSubsitute) {
            this.isSubsitute = isSubsitute;
        }

        public Boolean getIsVoid() {
            return isVoid;
        }

        public void setIsVoid(Boolean isVoid) {
            this.isVoid = isVoid;
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

        public Integer getLineDiscPercentage() {
            return lineDiscPercentage;
        }

        public void setLineDiscPercentage(Integer lineDiscPercentage) {
            this.lineDiscPercentage = lineDiscPercentage;
        }

        public Integer getLineManualDiscountAmount() {
            return lineManualDiscountAmount;
        }

        public void setLineManualDiscountAmount(Integer lineManualDiscountAmount) {
            this.lineManualDiscountAmount = lineManualDiscountAmount;
        }

        public Integer getLineManualDiscountPercentage() {
            return lineManualDiscountPercentage;
        }

        public void setLineManualDiscountPercentage(Integer lineManualDiscountPercentage) {
            this.lineManualDiscountPercentage = lineManualDiscountPercentage;
        }

        public Integer getLineNo() {
            return lineNo;
        }

        public void setLineNo(Integer lineNo) {
            this.lineNo = lineNo;
        }

        public double getLinedscAmount() {
            return linedscAmount;
        }

        public void setLinedscAmount(double linedscAmount) {
            this.linedscAmount = linedscAmount;
        }

        public String getMMGroupId() {
            return mMGroupId;
        }

        public void setMMGroupId(String mMGroupId) {
            this.mMGroupId = mMGroupId;
        }

        public Double getMrp() {
            return mrp;
        }

        public void setMrp(Double mrp) {
            this.mrp = mrp;
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

        public Boolean getMixMode() {
            return mixMode;
        }

        public void setMixMode(Boolean mixMode) {
            this.mixMode = mixMode;
        }

        public String getModifyBatchId() {
            return modifyBatchId;
        }

        public void setModifyBatchId(String modifyBatchId) {
            this.modifyBatchId = modifyBatchId;
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

        public Integer getOfferAmount() {
            return offerAmount;
        }

        public void setOfferAmount(Integer offerAmount) {
            this.offerAmount = offerAmount;
        }

        public Integer getOfferDiscountType() {
            return offerDiscountType;
        }

        public void setOfferDiscountType(Integer offerDiscountType) {
            this.offerDiscountType = offerDiscountType;
        }

        public Integer getOfferDiscountValue() {
            return offerDiscountValue;
        }

        public void setOfferDiscountValue(Integer offerDiscountValue) {
            this.offerDiscountValue = offerDiscountValue;
        }

        public Integer getOfferQty() {
            return offerQty;
        }

        public void setOfferQty(Integer offerQty) {
            this.offerQty = offerQty;
        }

        public Integer getOfferType() {
            return offerType;
        }

        public void setOfferType(Integer offerType) {
            this.offerType = offerType;
        }

        public Integer getOmsLineID() {
            return omsLineID;
        }

        public void setOmsLineID(Integer omsLineID) {
            this.omsLineID = omsLineID;
        }

        public Integer getOmsLineRECID() {
            return omsLineRECID;
        }

        public void setOmsLineRECID(Integer omsLineRECID) {
            this.omsLineRECID = omsLineRECID;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Integer getPeriodicDiscAmount() {
            return periodicDiscAmount;
        }

        public void setPeriodicDiscAmount(Integer periodicDiscAmount) {
            this.periodicDiscAmount = periodicDiscAmount;
        }

        public String getPreviewText() {
            return previewText;
        }

        public void setPreviewText(String previewText) {
            this.previewText = previewText;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getProductRecID() {
            return productRecID;
        }

        public void setProductRecID(String productRecID) {
            this.productRecID = productRecID;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Integer getRemainderDays() {
            return remainderDays;
        }

        public void setRemainderDays(Integer remainderDays) {
            this.remainderDays = remainderDays;
        }

        public Integer getRemainingQty() {
            return remainingQty;
        }

        public void setRemainingQty(Integer remainingQty) {
            this.remainingQty = remainingQty;
        }

        public String getRetailCategoryRecID() {
            return retailCategoryRecID;
        }

        public void setRetailCategoryRecID(String retailCategoryRecID) {
            this.retailCategoryRecID = retailCategoryRecID;
        }

        public String getRetailMainCategoryRecID() {
            return retailMainCategoryRecID;
        }

        public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
            this.retailMainCategoryRecID = retailMainCategoryRecID;
        }

        public String getRetailSubCategoryRecID() {
            return retailSubCategoryRecID;
        }

        public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
            this.retailSubCategoryRecID = retailSubCategoryRecID;
        }

        public Integer getReturnQty() {
            return returnQty;
        }

        public void setReturnQty(Integer returnQty) {
            this.returnQty = returnQty;
        }

        public double getSGSTPerc() {
            return sGSTPerc;
        }

        public void setSGSTPerc(double sGSTPerc) {
            this.sGSTPerc = sGSTPerc;
        }

        public String getSGSTTaxCode() {
            return sGSTTaxCode;
        }

        public void setSGSTTaxCode(String sGSTTaxCode) {
            this.sGSTTaxCode = sGSTTaxCode;
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

        public Integer getStockQty() {
            return stockQty;
        }

        public void setStockQty(Integer stockQty) {
            this.stockQty = stockQty;
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

        public String getSubClassification() {
            return subClassification;
        }

        public void setSubClassification(String subClassification) {
            this.subClassification = subClassification;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public Double getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(Double taxAmount) {
            this.taxAmount = taxAmount;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getTotalDiscAmount() {
            return totalDiscAmount;
        }

        public void setTotalDiscAmount(double totalDiscAmount) {
            this.totalDiscAmount = totalDiscAmount;
        }

        public double getTotalDiscPct() {
            return totalDiscPct;
        }

        public void setTotalDiscPct(double totalDiscPct) {
            this.totalDiscPct = totalDiscPct;
        }

        public Integer getTotalRoundedAmount() {
            return totalRoundedAmount;
        }

        public void setTotalRoundedAmount(Integer totalRoundedAmount) {
            this.totalRoundedAmount = totalRoundedAmount;
        }

        public Integer getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(Integer totalTax) {
            this.totalTax = totalTax;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Integer getUnitQty() {
            return unitQty;
        }

        public void setUnitQty(Integer unitQty) {
            this.unitQty = unitQty;
        }

        public String getVariantId() {
            return variantId;
        }

        public void setVariantId(String variantId) {
            this.variantId = variantId;
        }

        public Integer getExpirydays() {
            return expirydays;
        }

        public void setExpirydays(Integer expirydays) {
            this.expirydays = expirydays;
        }

    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

}
