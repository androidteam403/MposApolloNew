package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservedSalesLine
{
    @SerializedName("LineNo")
    @Expose
    private Integer lineNo;
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
    private Integer qty;
    @SerializedName("StockQty")
    @Expose
    private Integer stockQty;
    @SerializedName("ReturnQty")
    @Expose
    private Integer returnQty;
    @SerializedName("RemainingQty")
    @Expose
    private Integer remainingQty;
    @SerializedName("MRP")
    @Expose
    private Double mrp;
    @SerializedName("Tax")
    @Expose
    private Integer tax;
    @SerializedName("Additionaltax")
    @Expose
    private Integer additionaltax;
    @SerializedName("Barcode")
    @Expose
    private String barcode;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("DiscAmount")
    @Expose
    private Integer discAmount;
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
    private Integer linedscAmount;
    @SerializedName("LineManualDiscountAmount")
    @Expose
    private Integer lineManualDiscountAmount;
    @SerializedName("LineManualDiscountPercentage")
    @Expose
    private Integer lineManualDiscountPercentage;
    @SerializedName("NetAmount")
    @Expose
    private Double netAmount;
    @SerializedName("NetAmountInclTax")
    @Expose
    private Double netAmountInclTax;
    @SerializedName("OriginalPrice")
    @Expose
    private Double originalPrice;
    @SerializedName("PeriodicDiscAmount")
    @Expose
    private Integer periodicDiscAmount;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("TaxAmount")
    @Expose
    private Double taxAmount;
    @SerializedName("BaseAmount")
    @Expose
    private Double baseAmount;
    @SerializedName("TotalDiscAmount")
    @Expose
    private Integer totalDiscAmount;
    @SerializedName("TotalDiscPct")
    @Expose
    private Integer totalDiscPct;
    @SerializedName("TotalRoundedAmount")
    @Expose
    private Integer totalRoundedAmount;
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
    @SerializedName("Total")
    @Expose
    private Double total;
    @SerializedName("ISPrescribed")
    @Expose
    private Integer iSPrescribed;
    @SerializedName("RemainderDays")
    @Expose
    private Integer remainderDays;
    @SerializedName("IsVoid")
    @Expose
    private Boolean isVoid;
    @SerializedName("IsPriceOverride")
    @Expose
    private Boolean isPriceOverride;
    @SerializedName("IsChecked")
    @Expose
    private Boolean isChecked;
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
    private Boolean dpco;
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
    private Integer offerQty;
    @SerializedName("OfferAmount")
    @Expose
    private Double offerAmount;
    @SerializedName("OfferDiscountType")
    @Expose
    private Integer offerDiscountType;
    @SerializedName("OfferDiscountValue")
    @Expose
    private Integer offerDiscountValue;
    @SerializedName("DiscountType")
    @Expose
    private String discountType;
    @SerializedName("MixMode")
    @Expose
    private Boolean mixMode;
    @SerializedName("MMGroupId")
    @Expose
    private String mMGroupId;
    @SerializedName("DiscId")
    @Expose
    private String discId;
    @SerializedName("OfferType")
    @Expose
    private Integer offerType;
    @SerializedName("LineDiscPercentage")
    @Expose
    private Double lineDiscPercentage;
    @SerializedName("ApplyDiscount")
    @Expose
    private Boolean applyDiscount;
    @SerializedName("IGSTPerc")
    @Expose
    private Double iGSTPerc;
    @SerializedName("CESSPerc")
    @Expose
    private Double cESSPerc;
    @SerializedName("CGSTPerc")
    @Expose
    private Double cGSTPerc;
    @SerializedName("SGSTPerc")
    @Expose
    private Double sGSTPerc;
    @SerializedName("TotalTax")
    @Expose
    private Integer totalTax;
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
    private Integer discountStructureType;
    @SerializedName("SubstitudeItemId")
    @Expose
    private String substitudeItemId;
    @SerializedName("CategoryReference")
    @Expose
    private String categoryReference;
    @SerializedName("OrderStatus")
    @Expose
    private Integer orderStatus;
    @SerializedName("OmsLineID")
    @Expose
    private Integer omsLineID;
    @SerializedName("IsSubsitute")
    @Expose
    private Boolean isSubsitute;
    @SerializedName("IsGeneric")
    @Expose
    private Boolean isGeneric;
    @SerializedName("OmsLineRECID")
    @Expose
    private Integer omsLineRECID;
    @SerializedName("ISReserved")
    @Expose
    private Boolean iSReserved;
    @SerializedName("ISStockAvailable")
    @Expose
    private Boolean iSStockAvailable;
    @SerializedName("ISRestricted")
    @Expose
    private Boolean iSRestricted;
    @SerializedName("PhysicalBatchID")
    @Expose
    private Object physicalBatchID;
    @SerializedName("PhysicalMRP")
    @Expose
    private Integer physicalMRP;
    @SerializedName("PhysicalExpiry")
    @Expose
    private Object physicalExpiry;

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public Integer getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Integer remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getAdditionaltax() {
        return additionaltax;
    }

    public void setAdditionaltax(Integer additionaltax) {
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

    public Integer getDiscAmount() {
        return discAmount;
    }

    public void setDiscAmount(Integer discAmount) {
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

    public Integer getLinedscAmount() {
        return linedscAmount;
    }

    public void setLinedscAmount(Integer linedscAmount) {
        this.linedscAmount = linedscAmount;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Integer getTotalDiscAmount() {
        return totalDiscAmount;
    }

    public void setTotalDiscAmount(Integer totalDiscAmount) {
        this.totalDiscAmount = totalDiscAmount;
    }

    public Integer getTotalDiscPct() {
        return totalDiscPct;
    }

    public void setTotalDiscPct(Integer totalDiscPct) {
        this.totalDiscPct = totalDiscPct;
    }

    public Integer getTotalRoundedAmount() {
        return totalRoundedAmount;
    }

    public void setTotalRoundedAmount(Integer totalRoundedAmount) {
        this.totalRoundedAmount = totalRoundedAmount;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getISPrescribed() {
        return iSPrescribed;
    }

    public void setISPrescribed(Integer iSPrescribed) {
        this.iSPrescribed = iSPrescribed;
    }

    public Integer getRemainderDays() {
        return remainderDays;
    }

    public void setRemainderDays(Integer remainderDays) {
        this.remainderDays = remainderDays;
    }

    public Boolean getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(Boolean isVoid) {
        this.isVoid = isVoid;
    }

    public Boolean getIsPriceOverride() {
        return isPriceOverride;
    }

    public void setIsPriceOverride(Boolean isPriceOverride) {
        this.isPriceOverride = isPriceOverride;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
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

    public Boolean getDpco() {
        return dpco;
    }

    public void setDpco(Boolean dpco) {
        this.dpco = dpco;
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

    public Integer getOfferQty() {
        return offerQty;
    }

    public void setOfferQty(Integer offerQty) {
        this.offerQty = offerQty;
    }

    public Double getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(Double offerAmount) {
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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Boolean getMixMode() {
        return mixMode;
    }

    public void setMixMode(Boolean mixMode) {
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

    public Integer getOfferType() {
        return offerType;
    }

    public void setOfferType(Integer offerType) {
        this.offerType = offerType;
    }

    public Double getLineDiscPercentage() {
        return lineDiscPercentage;
    }

    public void setLineDiscPercentage(Double lineDiscPercentage) {
        this.lineDiscPercentage = lineDiscPercentage;
    }

    public Boolean getApplyDiscount() {
        return applyDiscount;
    }

    public void setApplyDiscount(Boolean applyDiscount) {
        this.applyDiscount = applyDiscount;
    }

    public Double getIGSTPerc() {
        return iGSTPerc;
    }

    public void setIGSTPerc(Double iGSTPerc) {
        this.iGSTPerc = iGSTPerc;
    }

    public Double getCESSPerc() {
        return cESSPerc;
    }

    public void setCESSPerc(Double cESSPerc) {
        this.cESSPerc = cESSPerc;
    }

    public Double getCGSTPerc() {
        return cGSTPerc;
    }

    public void setCGSTPerc(Double cGSTPerc) {
        this.cGSTPerc = cGSTPerc;
    }

    public Double getSGSTPerc() {
        return sGSTPerc;
    }

    public void setSGSTPerc(Double sGSTPerc) {
        this.sGSTPerc = sGSTPerc;
    }

    public Integer getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Integer totalTax) {
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

    public Integer getDiscountStructureType() {
        return discountStructureType;
    }

    public void setDiscountStructureType(Integer discountStructureType) {
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOmsLineID() {
        return omsLineID;
    }

    public void setOmsLineID(Integer omsLineID) {
        this.omsLineID = omsLineID;
    }

    public Boolean getIsSubsitute() {
        return isSubsitute;
    }

    public void setIsSubsitute(Boolean isSubsitute) {
        this.isSubsitute = isSubsitute;
    }

    public Boolean getIsGeneric() {
        return isGeneric;
    }

    public void setIsGeneric(Boolean isGeneric) {
        this.isGeneric = isGeneric;
    }

    public Integer getOmsLineRECID() {
        return omsLineRECID;
    }

    public void setOmsLineRECID(Integer omsLineRECID) {
        this.omsLineRECID = omsLineRECID;
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

    public Boolean getISRestricted() {
        return iSRestricted;
    }

    public void setISRestricted(Boolean iSRestricted) {
        this.iSRestricted = iSRestricted;
    }

    public Object getPhysicalBatchID() {
        return physicalBatchID;
    }

    public void setPhysicalBatchID(Object physicalBatchID) {
        this.physicalBatchID = physicalBatchID;
    }

    public Integer getPhysicalMRP() {
        return physicalMRP;
    }

    public void setPhysicalMRP(Integer physicalMRP) {
        this.physicalMRP = physicalMRP;
    }

    public Object getPhysicalExpiry() {
        return physicalExpiry;
    }

    public void setPhysicalExpiry(Object physicalExpiry) {
        this.physicalExpiry = physicalExpiry;
    }


}
