package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class OMSOrderForwardRequest implements Serializable {

    @SerializedName("FulfillmentID")
    @Expose
    private String fulfillmentID;
    @SerializedName("RequestType")
    @Expose
    private String requestType;
    @SerializedName("ReservedSalesLine")
    @Expose
    private List<ReservedSalesLine> reservedSalesLine = null;
    @SerializedName("TerminalID")
    @Expose
    private String terminalID;
    private final static long serialVersionUID = -2456334217242180916L;

    public String getFulfillmentID() {
        return fulfillmentID;
    }

    public void setFulfillmentID(String fulfillmentID) {
        this.fulfillmentID = fulfillmentID;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public List<ReservedSalesLine> getReservedSalesLine() {
        return reservedSalesLine;
    }

    public void setReservedSalesLine(List<ReservedSalesLine> reservedSalesLine) {
        this.reservedSalesLine = reservedSalesLine;
    }

    public static class ReservedSalesLine implements Serializable {

        @SerializedName("Additionaltax")
        @Expose
        private double additionaltax;
        @SerializedName("ApplyDiscount")
        @Expose
        private Boolean applyDiscount;
        @SerializedName("Barcode")
        @Expose
        private String barcode;
        @SerializedName("BaseAmount")
        @Expose
        private Double baseAmount;
        @SerializedName("CESSPerc")
        @Expose
        private double cESSPerc;
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
        private double discountStructureType;
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
        private double iGSTPerc;
        @SerializedName("IGSTTaxCode")
        @Expose
        private String iGSTTaxCode;
        @SerializedName("ISPrescribed")
        @Expose
        private double iSPrescribed;
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
        @SerializedName("IsGeneric")
        @Expose
        private Boolean isGeneric;
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
        private double lineDiscPercentage;
        @SerializedName("LineManualDiscountAmount")
        @Expose
        private double lineManualDiscountAmount;
        @SerializedName("LineManualDiscountPercentage")
        @Expose
        private double lineManualDiscountPercentage;
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
        private double offerAmount;
        @SerializedName("OfferDiscountType")
        @Expose
        private Integer offerDiscountType;
        @SerializedName("OfferDiscountValue")
        @Expose
        private double offerDiscountValue;
        @SerializedName("OfferQty")
        @Expose
        private double offerQty;
        @SerializedName("OfferType")
        @Expose
        private double offerType;
        @SerializedName("OmsLineID")
        @Expose
        private Integer omsLineID;
        @SerializedName("OmsLineRECID")
        @Expose
        private double omsLineRECID;
        @SerializedName("OrderStatus")
        @Expose
        private double orderStatus;
        @SerializedName("OriginalPrice")
        @Expose
        private Double originalPrice;
        @SerializedName("PeriodicDiscAmount")
        @Expose
        private double periodicDiscAmount;
        @SerializedName("PhysicalMRP")
        @Expose
        private double physicalMRP;
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
        private double qty;
        @SerializedName("RemainderDays")
        @Expose
        private double remainderDays;
        @SerializedName("RemainingQty")
        @Expose
        private double remainingQty;
        @SerializedName("Resqtyflag")
        @Expose
        private Boolean resqtyflag;
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
        private double returnQty;
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
        private double stockQty;
        @SerializedName("SubCategory")
        @Expose
        private String subCategory;
        @SerializedName("SubCategoryCode")
        @Expose
        private String subCategoryCode;
        @SerializedName("SubClassification")
        @Expose
        private String subClassification;
        @SerializedName("SubstitudeItemId")
        @Expose
        private String substitudeItemId;
        @SerializedName("Tax")
        @Expose
        private double tax;
        @SerializedName("TaxAmount")
        @Expose
        private double taxAmount;
        @SerializedName("Total")
        @Expose
        private Double total;
        @SerializedName("TotalDiscAmount")
        @Expose
        private double totalDiscAmount;
        @SerializedName("TotalDiscPct")
        @Expose
        private double totalDiscPct;
        @SerializedName("TotalRoundedAmount")
        @Expose
        private double totalRoundedAmount;
        @SerializedName("TotalTax")
        @Expose
        private double totalTax;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("UnitPrice")
        @Expose
        private Double unitPrice;
        @SerializedName("UnitQty")
        @Expose
        private double unitQty;
        @SerializedName("VariantId")
        @Expose
        private String variantId;
        @SerializedName("isReturnClick")
        @Expose
        private Boolean isReturnClick;
        @SerializedName("isSelectedReturnItem")
        @Expose
        private Boolean isSelectedReturnItem;
        private final static long serialVersionUID = 3611062889511995115L;

        public double getAdditionaltax() {
            return additionaltax;
        }

        public void setAdditionaltax(double additionaltax) {
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

        public Double getBaseAmount() {
            return baseAmount;
        }

        public void setBaseAmount(Double baseAmount) {
            this.baseAmount = baseAmount;
        }

        public double getCESSPerc() {
            return cESSPerc;
        }

        public void setCESSPerc(double cESSPerc) {
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

        public double getDiscountStructureType() {
            return discountStructureType;
        }

        public void setDiscountStructureType(double discountStructureType) {
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

        public double getIGSTPerc() {
            return iGSTPerc;
        }

        public void setIGSTPerc(double iGSTPerc) {
            this.iGSTPerc = iGSTPerc;
        }

        public String getIGSTTaxCode() {
            return iGSTTaxCode;
        }

        public void setIGSTTaxCode(String iGSTTaxCode) {
            this.iGSTTaxCode = iGSTTaxCode;
        }

        public double getISPrescribed() {
            return iSPrescribed;
        }

        public void setISPrescribed(double iSPrescribed) {
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

        public Boolean getIsGeneric() {
            return isGeneric;
        }

        public void setIsGeneric(Boolean isGeneric) {
            this.isGeneric = isGeneric;
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

        public double getLineDiscPercentage() {
            return lineDiscPercentage;
        }

        public void setLineDiscPercentage(double lineDiscPercentage) {
            this.lineDiscPercentage = lineDiscPercentage;
        }

        public double getLineManualDiscountAmount() {
            return lineManualDiscountAmount;
        }

        public void setLineManualDiscountAmount(double lineManualDiscountAmount) {
            this.lineManualDiscountAmount = lineManualDiscountAmount;
        }

        public double getLineManualDiscountPercentage() {
            return lineManualDiscountPercentage;
        }

        public void setLineManualDiscountPercentage(double lineManualDiscountPercentage) {
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

        public double getOfferAmount() {
            return offerAmount;
        }

        public void setOfferAmount(double offerAmount) {
            this.offerAmount = offerAmount;
        }

        public Integer getOfferDiscountType() {
            return offerDiscountType;
        }

        public void setOfferDiscountType(Integer offerDiscountType) {
            this.offerDiscountType = offerDiscountType;
        }

        public double getOfferDiscountValue() {
            return offerDiscountValue;
        }

        public void setOfferDiscountValue(double offerDiscountValue) {
            this.offerDiscountValue = offerDiscountValue;
        }

        public double getOfferQty() {
            return offerQty;
        }

        public void setOfferQty(double offerQty) {
            this.offerQty = offerQty;
        }

        public double getOfferType() {
            return offerType;
        }

        public void setOfferType(double offerType) {
            this.offerType = offerType;
        }

        public Integer getOmsLineID() {
            return omsLineID;
        }

        public void setOmsLineID(Integer omsLineID) {
            this.omsLineID = omsLineID;
        }

        public double getOmsLineRECID() {
            return omsLineRECID;
        }

        public void setOmsLineRECID(double omsLineRECID) {
            this.omsLineRECID = omsLineRECID;
        }

        public double getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(double orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getPeriodicDiscAmount() {
            return periodicDiscAmount;
        }

        public void setPeriodicDiscAmount(double periodicDiscAmount) {
            this.periodicDiscAmount = periodicDiscAmount;
        }

        public double getPhysicalMRP() {
            return physicalMRP;
        }

        public void setPhysicalMRP(double physicalMRP) {
            this.physicalMRP = physicalMRP;
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

        public double getQty() {
            return qty;
        }

        public void setQty(double qty) {
            this.qty = qty;
        }

        public double getRemainderDays() {
            return remainderDays;
        }

        public void setRemainderDays(double remainderDays) {
            this.remainderDays = remainderDays;
        }

        public double getRemainingQty() {
            return remainingQty;
        }

        public void setRemainingQty(double remainingQty) {
            this.remainingQty = remainingQty;
        }

        public Boolean getResqtyflag() {
            return resqtyflag;
        }

        public void setResqtyflag(Boolean resqtyflag) {
            this.resqtyflag = resqtyflag;
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

        public double getReturnQty() {
            return returnQty;
        }

        public void setReturnQty(double returnQty) {
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

        public double getStockQty() {
            return stockQty;
        }

        public void setStockQty(double stockQty) {
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

        public String getSubstitudeItemId() {
            return substitudeItemId;
        }

        public void setSubstitudeItemId(String substitudeItemId) {
            this.substitudeItemId = substitudeItemId;
        }

        public double getTax() {
            return tax;
        }

        public void setTax(double tax) {
            this.tax = tax;
        }

        public double getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
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

        public double getTotalRoundedAmount() {
            return totalRoundedAmount;
        }

        public void setTotalRoundedAmount(double totalRoundedAmount) {
            this.totalRoundedAmount = totalRoundedAmount;
        }

        public double getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(double totalTax) {
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

        public double getUnitQty() {
            return unitQty;
        }

        public void setUnitQty(double unitQty) {
            this.unitQty = unitQty;
        }

        public String getVariantId() {
            return variantId;
        }

        public void setVariantId(String variantId) {
            this.variantId = variantId;
        }

        public Boolean getIsReturnClick() {
            return isReturnClick;
        }

        public void setIsReturnClick(Boolean isReturnClick) {
            this.isReturnClick = isReturnClick;
        }

        public Boolean getIsSelectedReturnItem() {
            return isSelectedReturnItem;
        }

        public void setIsSelectedReturnItem(Boolean isSelectedReturnItem) {
            this.isSelectedReturnItem = isSelectedReturnItem;
        }

    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

}

