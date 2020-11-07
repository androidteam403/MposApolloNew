package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesLine {

        @SerializedName("Additionaltax")
        @Expose
        public Integer additionaltax;
        @SerializedName("ApplyDiscount")
        @Expose
        public Boolean applyDiscount;
        @SerializedName("Barcode")
        @Expose
        public String barcode;
        @SerializedName("BaseAmount")
        @Expose
        public Integer baseAmount;
        @SerializedName("CESSPerc")
        @Expose
        public Integer cESSPerc;
        @SerializedName("CESSTaxCode")
        @Expose
        public String cESSTaxCode;
        @SerializedName("CGSTPerc")
        @Expose
        public Integer cGSTPerc;
        @SerializedName("CGSTTaxCode")
        @Expose
        public String cGSTTaxCode;
        @SerializedName("Category")
        @Expose
        public String category;
        @SerializedName("CategoryCode")
        @Expose
        public String categoryCode;
        @SerializedName("CategoryReference")
        @Expose
        public String categoryReference;
        @SerializedName("Comment")
        @Expose
        public String comment;
        @SerializedName("DPCO")
        @Expose
        public Boolean dPCO;
        @SerializedName("DiscAmount")
        @Expose
        public Integer discAmount;
        @SerializedName("DiscId")
        @Expose
        public String discId;
        @SerializedName("DiscOfferId")
        @Expose
        public String discOfferId;
        @SerializedName("DiscountStructureType")
        @Expose
        public Integer discountStructureType;
        @SerializedName("DiscountType")
        @Expose
        public String discountType;
        @SerializedName("DiseaseType")
        @Expose
        public String diseaseType;
        @SerializedName("Expiry")
        @Expose
        public String expiry;
        @SerializedName("Hsncode_In")
        @Expose
        public String hsncodeIn;
        @SerializedName("IGSTPerc")
        @Expose
        public Integer iGSTPerc;
        @SerializedName("IGSTTaxCode")
        @Expose
        public String iGSTTaxCode;
        @SerializedName("ISPrescribed")
        @Expose
        public Integer iSPrescribed;
        @SerializedName("ISReserved")
        @Expose
        public Boolean iSReserved;
        @SerializedName("ISStockAvailable")
        @Expose
        public Boolean iSStockAvailable;
        @SerializedName("InventBatchId")
        @Expose
        public String inventBatchId;
        @SerializedName("IsChecked")
        @Expose
        public Boolean isChecked;
        @SerializedName("IsGeneric")
        @Expose
        public Boolean isGeneric;
        @SerializedName("IsPriceOverride")
        @Expose
        public Boolean isPriceOverride;
        @SerializedName("IsSubsitute")
        @Expose
        public Boolean isSubsitute;
        @SerializedName("IsVoid")
        @Expose
        public Boolean isVoid;
        @SerializedName("ItemId")
        @Expose
        public String itemId;
        @SerializedName("ItemName")
        @Expose
        public String itemName;
        @SerializedName("LineDiscPercentage")
        @Expose
        public Integer lineDiscPercentage;
        @SerializedName("LineManualDiscountAmount")
        @Expose
        public Integer lineManualDiscountAmount;
        @SerializedName("LineManualDiscountPercentage")
        @Expose
        public Integer lineManualDiscountPercentage;
        @SerializedName("LineNo")
        @Expose
        public Integer lineNo;
        @SerializedName("LinedscAmount")
        @Expose
        public Integer linedscAmount;
        @SerializedName("MMGroupId")
        @Expose
        public String mMGroupId;
        @SerializedName("MRP")
        @Expose
        public Integer mRP;
        @SerializedName("ManufacturerCode")
        @Expose
        public String manufacturerCode;
        @SerializedName("ManufacturerName")
        @Expose
        public String manufacturerName;
        @SerializedName("MixMode")
        @Expose
        public Boolean mixMode;
        @SerializedName("ModifyBatchId")
        @Expose
        public String modifyBatchId;
        @SerializedName("NetAmount")
        @Expose
        public Double netAmount;
        @SerializedName("NetAmountInclTax")
        @Expose
        public Integer netAmountInclTax;
        @SerializedName("OfferAmount")
        @Expose
        public Integer offerAmount;
        @SerializedName("OfferDiscountType")
        @Expose
        public Integer offerDiscountType;
        @SerializedName("OfferDiscountValue")
        @Expose
        public Integer offerDiscountValue;
        @SerializedName("OfferQty")
        @Expose
        public Integer offerQty;
        @SerializedName("OfferType")
        @Expose
        public Integer offerType;
        @SerializedName("OmsLineID")
        @Expose
        public Integer omsLineID;
        @SerializedName("OmsLineRECID")
        @Expose
        public Integer omsLineRECID;
        @SerializedName("OrderStatus")
        @Expose
        public Integer orderStatus;
        @SerializedName("OriginalPrice")
        @Expose
        public Integer originalPrice;
        @SerializedName("PeriodicDiscAmount")
        @Expose
        public Integer periodicDiscAmount;
        @SerializedName("PreviewText")
        @Expose
        public String previewText;
        @SerializedName("Price")
        @Expose
        public Integer price;
        @SerializedName("ProductRecID")
        @Expose
        public String productRecID;
        @SerializedName("Qty")
        @Expose
        public Integer qty;
        @SerializedName("RemainderDays")
        @Expose
        public Integer remainderDays;
        @SerializedName("RemainingQty")
        @Expose
        public Integer remainingQty;
        @SerializedName("RetailCategoryRecID")
        @Expose
        public String retailCategoryRecID;
        @SerializedName("RetailMainCategoryRecID")
        @Expose
        public String retailMainCategoryRecID;
        @SerializedName("RetailSubCategoryRecID")
        @Expose
        public String retailSubCategoryRecID;
        @SerializedName("ReturnQty")
        @Expose
        public Integer returnQty;
        @SerializedName("SGSTPerc")
        @Expose
        public Integer sGSTPerc;
        @SerializedName("SGSTTaxCode")
        @Expose
        public String sGSTTaxCode;
        @SerializedName("ScheduleCategory")
        @Expose
        public String scheduleCategory;
        @SerializedName("ScheduleCategoryCode")
        @Expose
        public String scheduleCategoryCode;
        @SerializedName("StockQty")
        @Expose
        public Integer stockQty;
        @SerializedName("SubCategory")
        @Expose
        public String subCategory;
        @SerializedName("SubCategoryCode")
        @Expose
        public String subCategoryCode;
        @SerializedName("SubClassification")
        @Expose
        public String subClassification;
        @SerializedName("SubstitudeItemId")
        @Expose
        public String substitudeItemId;
        @SerializedName("Tax")
        @Expose
        public Integer tax;
        @SerializedName("TaxAmount")
        @Expose
        public Double taxAmount;
        @SerializedName("Total")
        @Expose
        public Integer total;
        @SerializedName("TotalDiscAmount")
        @Expose
        public Integer totalDiscAmount;
        @SerializedName("TotalDiscPct")
        @Expose
        public Integer totalDiscPct;
        @SerializedName("TotalRoundedAmount")
        @Expose
        public Integer totalRoundedAmount;
        @SerializedName("TotalTax")
        @Expose
        public Integer totalTax;
        @SerializedName("Unit")
        @Expose
        public String unit;
        @SerializedName("UnitPrice")
        @Expose
        public Integer unitPrice;
        @SerializedName("UnitQty")
        @Expose
        public Integer unitQty;
        @SerializedName("VariantId")
        @Expose
        public String variantId;

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

    public Integer getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Integer baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Integer getcESSPerc() {
        return cESSPerc;
    }

    public void setcESSPerc(Integer cESSPerc) {
        this.cESSPerc = cESSPerc;
    }

    public String getcESSTaxCode() {
        return cESSTaxCode;
    }

    public void setcESSTaxCode(String cESSTaxCode) {
        this.cESSTaxCode = cESSTaxCode;
    }

    public Integer getcGSTPerc() {
        return cGSTPerc;
    }

    public void setcGSTPerc(Integer cGSTPerc) {
        this.cGSTPerc = cGSTPerc;
    }

    public String getcGSTTaxCode() {
        return cGSTTaxCode;
    }

    public void setcGSTTaxCode(String cGSTTaxCode) {
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

    public Boolean getdPCO() {
        return dPCO;
    }

    public void setdPCO(Boolean dPCO) {
        this.dPCO = dPCO;
    }

    public Integer getDiscAmount() {
        return discAmount;
    }

    public void setDiscAmount(Integer discAmount) {
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

    public Integer getiGSTPerc() {
        return iGSTPerc;
    }

    public void setiGSTPerc(Integer iGSTPerc) {
        this.iGSTPerc = iGSTPerc;
    }

    public String getiGSTTaxCode() {
        return iGSTTaxCode;
    }

    public void setiGSTTaxCode(String iGSTTaxCode) {
        this.iGSTTaxCode = iGSTTaxCode;
    }

    public Integer getiSPrescribed() {
        return iSPrescribed;
    }

    public void setiSPrescribed(Integer iSPrescribed) {
        this.iSPrescribed = iSPrescribed;
    }

    public Boolean getiSReserved() {
        return iSReserved;
    }

    public void setiSReserved(Boolean iSReserved) {
        this.iSReserved = iSReserved;
    }

    public Boolean getiSStockAvailable() {
        return iSStockAvailable;
    }

    public void setiSStockAvailable(Boolean iSStockAvailable) {
        this.iSStockAvailable = iSStockAvailable;
    }

    public String getInventBatchId() {
        return inventBatchId;
    }

    public void setInventBatchId(String inventBatchId) {
        this.inventBatchId = inventBatchId;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Boolean getGeneric() {
        return isGeneric;
    }

    public void setGeneric(Boolean generic) {
        isGeneric = generic;
    }

    public Boolean getPriceOverride() {
        return isPriceOverride;
    }

    public void setPriceOverride(Boolean priceOverride) {
        isPriceOverride = priceOverride;
    }

    public Boolean getSubsitute() {
        return isSubsitute;
    }

    public void setSubsitute(Boolean subsitute) {
        isSubsitute = subsitute;
    }

    public Boolean getVoid() {
        return isVoid;
    }

    public void setVoid(Boolean aVoid) {
        isVoid = aVoid;
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

    public Integer getLinedscAmount() {
        return linedscAmount;
    }

    public void setLinedscAmount(Integer linedscAmount) {
        this.linedscAmount = linedscAmount;
    }

    public String getmMGroupId() {
        return mMGroupId;
    }

    public void setmMGroupId(String mMGroupId) {
        this.mMGroupId = mMGroupId;
    }

    public Integer getmRP() {
        return mRP;
    }

    public void setmRP(Integer mRP) {
        this.mRP = mRP;
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

    public Integer getNetAmountInclTax() {
        return netAmountInclTax;
    }

    public void setNetAmountInclTax(Integer netAmountInclTax) {
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

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

    public Integer getsGSTPerc() {
        return sGSTPerc;
    }

    public void setsGSTPerc(Integer sGSTPerc) {
        this.sGSTPerc = sGSTPerc;
    }

    public String getsGSTTaxCode() {
        return sGSTTaxCode;
    }

    public void setsGSTTaxCode(String sGSTTaxCode) {
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

    public String getSubstitudeItemId() {
        return substitudeItemId;
    }

    public void setSubstitudeItemId(String substitudeItemId) {
        this.substitudeItemId = substitudeItemId;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
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
}
