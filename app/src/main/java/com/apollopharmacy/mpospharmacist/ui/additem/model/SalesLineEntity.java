package com.apollopharmacy.mpospharmacist.ui.additem.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.apollopharmacy.mpospharmacist.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public  class SalesLineEntity extends BaseObservable implements Serializable {
        @Expose
        @SerializedName("VariantId")
        private String VariantId;
        @Expose
        @SerializedName("UnitQty")
        private double UnitQty;
        @Expose
        @SerializedName("UnitPrice")
        private double UnitPrice;
        @Expose
        @SerializedName("Unit")
        private String Unit;
        @Expose
        @SerializedName("TotalTax")
        private double TotalTax;
        @Expose
        @SerializedName("TotalRoundedAmount")
        private double TotalRoundedAmount;
        @Expose
        @SerializedName("TotalDiscPct")
        private double TotalDiscPct;
        @Expose
        @SerializedName("TotalDiscAmount")
        private double TotalDiscAmount;
        @Expose
        @SerializedName("Total")
        private double Total;
        @Expose
        @SerializedName("TaxAmount")
        private double TaxAmount;
        @Expose
        @SerializedName("Tax")
        private double Tax;
        @Expose
        @SerializedName("SubstitudeItemId")
        private String SubstitudeItemId;
        @Expose
        @SerializedName("SubClassification")
        private String SubClassification;
        @Expose
        @SerializedName("SubCategoryCode")
        private String SubCategoryCode;
        @Expose
        @SerializedName("SubCategory")
        private String SubCategory;
        @Expose
        @SerializedName("StockQty")
        private double StockQty;
        @Expose
        @SerializedName("ScheduleCategoryCode")
        private String ScheduleCategoryCode;
        @Expose
        @SerializedName("ScheduleCategory")
        private String ScheduleCategory;
        @Expose
        @SerializedName("SGSTTaxCode")
        private String SGSTTaxCode;
        @Expose
        @SerializedName("SGSTPerc")
        private double SGSTPerc;
        @Expose
        @SerializedName("ReturnQty")
        private double ReturnQty;
        @Expose
        @SerializedName("RetailSubCategoryRecID")
        private String RetailSubCategoryRecID;
        @Expose
        @SerializedName("RetailMainCategoryRecID")
        private String RetailMainCategoryRecID;
        @Expose
        @SerializedName("RetailCategoryRecID")
        private String RetailCategoryRecID;
        @Expose
        @SerializedName("RemainingQty")
        private double RemainingQty;
        @Expose
        @SerializedName("RemainderDays")
        private double RemainderDays;
        @Expose
        @SerializedName("Qty")
        private int Qty;
        @Expose
        @SerializedName("ProductRecID")
        private String ProductRecID;
        @Expose
        @SerializedName("Price")
        private double Price;
        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;
        @Expose
        @SerializedName("PeriodicDiscAmount")
        private double PeriodicDiscAmount;
        @Expose
        @SerializedName("OriginalPrice")
        private double OriginalPrice;
        @Expose
        @SerializedName("OrderStatus")
        private double OrderStatus;
        @Expose
        @SerializedName("OmsLineRECID")
        private double OmsLineRECID;
        @Expose
        @SerializedName("OmsLineID")
        private double OmsLineID;
        @Expose
        @SerializedName("OfferType")
        private double OfferType;
        @Expose
        @SerializedName("OfferQty")
        private double OfferQty;
        @Expose
        @SerializedName("OfferDiscountValue")
        private double OfferDiscountValue;
        @Expose
        @SerializedName("OfferDiscountType")
        private double OfferDiscountType;
        @Expose
        @SerializedName("OfferAmount")
        private double OfferAmount;
        @Expose
        @SerializedName("NetAmountInclTax")
        private double NetAmountInclTax;
        @Expose
        @SerializedName("NetAmount")
        private double NetAmount;
        @Expose
        @SerializedName("ModifyBatchId")
        private String ModifyBatchId;
        @Expose
        @SerializedName("MixMode")
        private boolean MixMode;
        @Expose
        @SerializedName("ManufacturerName")
        private String ManufacturerName;
        @Expose
        @SerializedName("ManufacturerCode")
        private String ManufacturerCode;
        @Expose
        @SerializedName("MRP")
        private double MRP;
        @Expose
        @SerializedName("MMGroupId")
        private String MMGroupId;
        @Expose
        @SerializedName("LinedscAmount")
        private double LinedscAmount;
        @Expose
        @SerializedName("LineNo")
        private int LineNo;
        @Expose
        @SerializedName("LineManualDiscountPercentage")
        private double LineManualDiscountPercentage;
        @Expose
        @SerializedName("LineManualDiscountAmount")
        private double LineManualDiscountAmount;
        @Expose
        @SerializedName("LineDiscPercentage")
        private double LineDiscPercentage;
        @Expose
        @SerializedName("ItemName")
        private String ItemName;
        @Expose
        @SerializedName("ItemId")
        private String ItemId;
        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;
        @Expose
        @SerializedName("IsSubsitute")
        private boolean IsSubsitute;
        @Expose
        @SerializedName("IsPriceOverride")
        private boolean IsPriceOverride;
        @Expose
        @SerializedName("IsChecked")
        private boolean IsChecked;
        @Expose
        @SerializedName("InventBatchId")
        private String InventBatchId;
        @Expose
        @SerializedName("ISStockAvailable")
        private boolean ISStockAvailable;
        @Expose
        @SerializedName("ISReserved")
        private boolean ISReserved;
        @Expose
        @SerializedName("ISPrescribed")
        private double ISPrescribed;
        @Expose
        @SerializedName("IGSTTaxCode")
        private String IGSTTaxCode;
        @Expose
        @SerializedName("IGSTPerc")
        private double IGSTPerc;
        @Expose
        @SerializedName("Hsncode_In")
        private String Hsncode_In;
        @Expose
        @SerializedName("Expiry")
        private String Expiry;
        @Expose
        @SerializedName("DiseaseType")
        private String DiseaseType;
        @Expose
        @SerializedName("DiscountType")
        private String DiscountType;
        @Expose
        @SerializedName("DiscountStructureType")
        private double DiscountStructureType;
        @Expose
        @SerializedName("DiscOfferId")
        private String DiscOfferId;
        @Expose
        @SerializedName("DiscId")
        private String DiscId;
        @Expose
        @SerializedName("DiscAmount")
        private double DiscAmount;
        @Expose
        @SerializedName("DPCO")
        private boolean DPCO;
        @Expose
        @SerializedName("Comment")
        private String Comment;
        @Expose
        @SerializedName("CategoryReference")
        private String CategoryReference;
        @Expose
        @SerializedName("CategoryCode")
        private String CategoryCode;
        @Expose
        @SerializedName("Category")
        private String Category;
        @Expose
        @SerializedName("CGSTTaxCode")
        private String CGSTTaxCode;
        @Expose
        @SerializedName("CGSTPerc")
        private double CGSTPerc;
        @Expose
        @SerializedName("CESSTaxCode")
        private String CESSTaxCode;
        @Expose
        @SerializedName("CESSPerc")
        private double CESSPerc;
        @Expose
        @SerializedName("BaseAmount")
        private double BaseAmount;
        @Expose
        @SerializedName("Barcode")
        private String Barcode;
        @Expose
        @SerializedName("ApplyDiscount")
        private boolean ApplyDiscount;
        @Expose
        @SerializedName("Additionaltax")
        private double Additionaltax;

        public String getVariantId() {
            return VariantId;
        }

        public double getUnitQty() {
            return UnitQty;
        }

        public double getUnitPrice() {
            return UnitPrice;
        }

        public String getUnit() {
            return Unit;
        }

        public double getTotalTax() {
            return TotalTax;
        }

        public double getTotalRoundedAmount() {
            return TotalRoundedAmount;
        }

        public double getTotalDiscPct() {
            return TotalDiscPct;
        }

        public double getTotalDiscAmount() {
            return TotalDiscAmount;
        }

        public double getTotal() {
            return Total;
        }

        public double getTaxAmount() {
            return TaxAmount;
        }

        public double getTax() {
            return Tax;
        }

        public String getSubstitudeItemId() {
            return SubstitudeItemId;
        }

        public String getSubClassification() {
            return SubClassification;
        }

        public String getSubCategoryCode() {
            return SubCategoryCode;
        }

        public String getSubCategory() {
            return SubCategory;
        }

        public double getStockQty() {
            return StockQty;
        }

        public String getScheduleCategoryCode() {
            return ScheduleCategoryCode;
        }

        public String getScheduleCategory() {
            return ScheduleCategory;
        }

        public String getSGSTTaxCode() {
            return SGSTTaxCode;
        }

        public double getSGSTPerc() {
            return SGSTPerc;
        }

        public double getReturnQty() {
            return ReturnQty;
        }



        public String getRetailSubCategoryRecID() {
            return RetailSubCategoryRecID;
        }

        public String getRetailMainCategoryRecID() {
            return RetailMainCategoryRecID;
        }

        public String getRetailCategoryRecID() {
            return RetailCategoryRecID;
        }

        public double getRemainingQty() {
            return RemainingQty;
        }

        public double getRemainderDays() {
            return RemainderDays;
        }

        public int getQty() {
            return Qty;
        }

        public String getProductRecID() {
            return ProductRecID;
        }

        public double getPrice() {
            return Price;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public double getPeriodicDiscAmount() {
            return PeriodicDiscAmount;
        }

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public double getOrderStatus() {
            return OrderStatus;
        }

        public double getOmsLineRECID() {
            return OmsLineRECID;
        }

        public double getOmsLineID() {
            return OmsLineID;
        }

        public double getOfferType() {
            return OfferType;
        }

        public double getOfferQty() {
            return OfferQty;
        }

        public double getOfferDiscountValue() {
            return OfferDiscountValue;
        }

        public double getOfferDiscountType() {
            return OfferDiscountType;
        }

        public double getOfferAmount() {
            return OfferAmount;
        }

        public double getNetAmountInclTax() {
            return NetAmountInclTax;
        }

        public double getNetAmount() {
            return NetAmount;
        }

        public String getModifyBatchId() {
            return ModifyBatchId;
        }

        public boolean getMixMode() {
            return MixMode;
        }

        public String getManufacturerName() {
            return ManufacturerName;
        }

        public String getManufacturerCode() {
            return ManufacturerCode;
        }

        public double getMRP() {
            return MRP;
        }

        public String getMMGroupId() {
            return MMGroupId;
        }

        public double getLinedscAmount() {
            return LinedscAmount;
        }

        public int getLineNo() {
            return LineNo;
        }

        public double getLineManualDiscountPercentage() {
            return LineManualDiscountPercentage;
        }

        public double getLineManualDiscountAmount() {
            return LineManualDiscountAmount;
        }

        public double getLineDiscPercentage() {
            return LineDiscPercentage;
        }

        public String getItemName() {
            return ItemName;
        }

        public String getItemId() {
            return ItemId;
        }

        public boolean getIsSubsitute() {
            return IsSubsitute;
        }

        public boolean getIsPriceOverride() {
            return IsPriceOverride;
        }



        public String getInventBatchId() {
            return InventBatchId;
        }

        public boolean getISStockAvailable() {
            return ISStockAvailable;
        }

        public boolean getISReserved() {
            return ISReserved;
        }

        public double getISPrescribed() {
            return ISPrescribed;
        }

        public String getIGSTTaxCode() {
            return IGSTTaxCode;
        }

        public double getIGSTPerc() {
            return IGSTPerc;
        }

        public String getHsncode_In() {
            return Hsncode_In;
        }

        public String getExpiry() {
            return Expiry;
        }

        public String getDiseaseType() {
            return DiseaseType;
        }

        public String getDiscountType() {
            return DiscountType;
        }

        public double getDiscountStructureType() {
            return DiscountStructureType;
        }

        public String getDiscOfferId() {
            return DiscOfferId;
        }

        public String getDiscId() {
            return DiscId;
        }

        public double getDiscAmount() {
            return DiscAmount;
        }

        public boolean getDPCO() {
            return DPCO;
        }

        public String getComment() {
            return Comment;
        }

        public String getCategoryReference() {
            return CategoryReference;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public String getCategory() {
            return Category;
        }

        public String getCGSTTaxCode() {
            return CGSTTaxCode;
        }

        public double getCGSTPerc() {
            return CGSTPerc;
        }

        public String getCESSTaxCode() {
            return CESSTaxCode;
        }

        public double getCESSPerc() {
            return CESSPerc;
        }

        public double getBaseAmount() {
            return BaseAmount;
        }

        public String getBarcode() {
            return Barcode;
        }

        public boolean getApplyDiscount() {
            return ApplyDiscount;
        }

        public double getAdditionaltax() {
            return Additionaltax;
        }


        public boolean isMixMode() {
                return MixMode;
        }

        @Bindable
        public boolean getIsVoid() {
                return IsVoid;
        }

//        @Bindable
//        public boolean isVoid() {
//                return IsVoid;
//        }

        public boolean isSubsitute() {
                return IsSubsitute;
        }

        public boolean isPriceOverride() {
                return IsPriceOverride;
        }

        public boolean isChecked() {
                return IsChecked;
        }

        public boolean isISStockAvailable() {
                return ISStockAvailable;
        }

        public boolean isISReserved() {
                return ISReserved;
        }

        public boolean isDPCO() {
                return DPCO;
        }

        public boolean isApplyDiscount() {
                return ApplyDiscount;
        }

        public void setVariantId(String variantId) {
                VariantId = variantId;
        }

        public void setUnitQty(double unitQty) {
                UnitQty = unitQty;
        }

        public void setUnitPrice(double unitPrice) {
                UnitPrice = unitPrice;
        }

        public void setUnit(String unit) {
                Unit = unit;
        }

        public void setTotalTax(double totalTax) {
                TotalTax = totalTax;
        }

        public void setTotalRoundedAmount(double totalRoundedAmount) {
                TotalRoundedAmount = totalRoundedAmount;
        }

        public void setTotalDiscPct(double totalDiscPct) {
                TotalDiscPct = totalDiscPct;
        }

        public void setTotalDiscAmount(double totalDiscAmount) {
                TotalDiscAmount = totalDiscAmount;
        }

        public void setTotal(double total) {
                Total = total;
        }

        public void setTaxAmount(double taxAmount) {
                TaxAmount = taxAmount;
        }

        public void setTax(double tax) {
                Tax = tax;
        }

        public void setSubstitudeItemId(String substitudeItemId) {
                SubstitudeItemId = substitudeItemId;
        }

        public void setSubClassification(String subClassification) {
                SubClassification = subClassification;
        }

        public void setSubCategoryCode(String subCategoryCode) {
                SubCategoryCode = subCategoryCode;
        }

        public void setSubCategory(String subCategory) {
                SubCategory = subCategory;
        }

        public void setStockQty(double stockQty) {
                StockQty = stockQty;
        }

        public void setScheduleCategoryCode(String scheduleCategoryCode) {
                ScheduleCategoryCode = scheduleCategoryCode;
        }

        public void setScheduleCategory(String scheduleCategory) {
                ScheduleCategory = scheduleCategory;
        }

        public void setSGSTTaxCode(String SGSTTaxCode) {
                this.SGSTTaxCode = SGSTTaxCode;
        }

        public void setSGSTPerc(double SGSTPerc) {
                this.SGSTPerc = SGSTPerc;
        }

        public void setReturnQty(double returnQty) {
                ReturnQty = returnQty;
        }

        public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
                RetailSubCategoryRecID = retailSubCategoryRecID;
        }

        public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
                RetailMainCategoryRecID = retailMainCategoryRecID;
        }

        public void setRetailCategoryRecID(String retailCategoryRecID) {
                RetailCategoryRecID = retailCategoryRecID;
        }

        public void setRemainingQty(double remainingQty) {
                RemainingQty = remainingQty;
        }

        public void setRemainderDays(double remainderDays) {
                RemainderDays = remainderDays;
        }

        public void setQty(int qty) {
                Qty = qty;
        }

        public void setProductRecID(String productRecID) {
                ProductRecID = productRecID;
        }

        public void setPrice(double price) {
                Price = price;
        }

        public void setPreviewText(String previewText) {
                PreviewText = previewText;
        }

        public void setPeriodicDiscAmount(double periodicDiscAmount) {
                PeriodicDiscAmount = periodicDiscAmount;
        }

        public void setOriginalPrice(double originalPrice) {
                OriginalPrice = originalPrice;
        }

        public void setOrderStatus(double orderStatus) {
                OrderStatus = orderStatus;
        }

        public void setOmsLineRECID(double omsLineRECID) {
                OmsLineRECID = omsLineRECID;
        }

        public void setOmsLineID(double omsLineID) {
                OmsLineID = omsLineID;
        }

        public void setOfferType(double offerType) {
                OfferType = offerType;
        }

        public void setOfferQty(double offerQty) {
                OfferQty = offerQty;
        }

        public void setOfferDiscountValue(double offerDiscountValue) {
                OfferDiscountValue = offerDiscountValue;
        }

        public void setOfferDiscountType(double offerDiscountType) {
                OfferDiscountType = offerDiscountType;
        }

        public void setOfferAmount(double offerAmount) {
                OfferAmount = offerAmount;
        }

        public void setNetAmountInclTax(double netAmountInclTax) {
                NetAmountInclTax = netAmountInclTax;
        }

        public void setNetAmount(double netAmount) {
                NetAmount = netAmount;
        }

        public void setModifyBatchId(String modifyBatchId) {
                ModifyBatchId = modifyBatchId;
        }

        public void setMixMode(boolean mixMode) {
                MixMode = mixMode;
        }

        public void setManufacturerName(String manufacturerName) {
                ManufacturerName = manufacturerName;
        }

        public void setManufacturerCode(String manufacturerCode) {
                ManufacturerCode = manufacturerCode;
        }

        public void setMRP(double MRP) {
                this.MRP = MRP;
        }

        public void setMMGroupId(String MMGroupId) {
                this.MMGroupId = MMGroupId;
        }

        public void setLinedscAmount(double linedscAmount) {
                LinedscAmount = linedscAmount;
        }

        public void setLineNo(int lineNo) {
                LineNo = lineNo;
        }

        public void setLineManualDiscountPercentage(double lineManualDiscountPercentage) {
                LineManualDiscountPercentage = lineManualDiscountPercentage;
        }

        public void setLineManualDiscountAmount(double lineManualDiscountAmount) {
                LineManualDiscountAmount = lineManualDiscountAmount;
        }

        public void setLineDiscPercentage(double lineDiscPercentage) {
                LineDiscPercentage = lineDiscPercentage;
        }

        public void setItemName(String itemName) {
                ItemName = itemName;
        }

        public void setItemId(String itemId) {
                ItemId = itemId;
        }

        public void setVoid(boolean itemDelete) {
                IsVoid = itemDelete;
                notifyPropertyChanged(BR.itemDelete);
        }

        public void setSubsitute(boolean subsitute) {
                IsSubsitute = subsitute;
        }

        public void setPriceOverride(boolean priceOverride) {
                IsPriceOverride = priceOverride;
        }

        public void setChecked(boolean checked) {
                IsChecked = checked;
        }

        public void setInventBatchId(String inventBatchId) {
                InventBatchId = inventBatchId;
        }

        public void setISStockAvailable(boolean ISStockAvailable) {
                this.ISStockAvailable = ISStockAvailable;
        }

        public void setISReserved(boolean ISReserved) {
                this.ISReserved = ISReserved;
        }

        public void setISPrescribed(double ISPrescribed) {
                this.ISPrescribed = ISPrescribed;
        }

        public void setIGSTTaxCode(String IGSTTaxCode) {
                this.IGSTTaxCode = IGSTTaxCode;
        }

        public void setIGSTPerc(double IGSTPerc) {
                this.IGSTPerc = IGSTPerc;
        }

        public void setHsncode_In(String hsncode_In) {
                Hsncode_In = hsncode_In;
        }

        public void setExpiry(String expiry) {
                Expiry = expiry;
        }

        public void setDiseaseType(String diseaseType) {
                DiseaseType = diseaseType;
        }

        public void setDiscountType(String discountType) {
                DiscountType = discountType;
        }

        public void setDiscountStructureType(double discountStructureType) {
                DiscountStructureType = discountStructureType;
        }

        public void setDiscOfferId(String discOfferId) {
                DiscOfferId = discOfferId;
        }

        public void setDiscId(String discId) {
                DiscId = discId;
        }

        public void setDiscAmount(double discAmount) {
                DiscAmount = discAmount;
        }

        public void setDPCO(boolean DPCO) {
                this.DPCO = DPCO;
        }

        public void setComment(String comment) {
                Comment = comment;
        }

        public void setCategoryReference(String categoryReference) {
                CategoryReference = categoryReference;
        }

        public void setCategoryCode(String categoryCode) {
                CategoryCode = categoryCode;
        }

        public void setCategory(String category) {
                Category = category;
        }

        public void setCGSTTaxCode(String CGSTTaxCode) {
                this.CGSTTaxCode = CGSTTaxCode;
        }

        public void setCGSTPerc(double CGSTPerc) {
                this.CGSTPerc = CGSTPerc;
        }

        public void setCESSTaxCode(String CESSTaxCode) {
                this.CESSTaxCode = CESSTaxCode;
        }

        public void setCESSPerc(double CESSPerc) {
                this.CESSPerc = CESSPerc;
        }

        public void setBaseAmount(double baseAmount) {
                BaseAmount = baseAmount;
        }

        public void setBarcode(String barcode) {
                Barcode = barcode;
        }

        public void setApplyDiscount(boolean applyDiscount) {
                ApplyDiscount = applyDiscount;
        }

        public void setAdditionaltax(double additionaltax) {
                Additionaltax = additionaltax;
        }

        private boolean isReturnClick = false;

        @Bindable
        public boolean isReturnClick() {
                return isReturnClick;
        }

        public void setReturnClick(boolean returnClick) {
                isReturnClick = returnClick;
                notifyPropertyChanged(BR.returnClick);
        }

        public boolean getIsChecked() {
                return IsChecked;
        }
        private boolean isSelectedReturnItem = false;

        public boolean isSelectedReturnItem() {
                return isSelectedReturnItem;
        }

        public void setSelectedReturnItem(boolean selectedReturnItem) {
                isSelectedReturnItem = selectedReturnItem;
        }
}