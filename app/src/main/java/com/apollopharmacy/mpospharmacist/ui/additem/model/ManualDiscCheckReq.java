package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class ManualDiscCheckReq {

    @Expose
    @SerializedName("AvailDiscountList")
    private List<ManualDiscCheckRes.AvailableDiscList> availableDiscList;
    @Expose
    @SerializedName("DiscList")
    private List<ManualDiscCheckRes.DiscList> discList;
    @Expose
    @SerializedName("DisplayList")
    private List<ManualDiscCheckRes.DisplayList> displayList;
    @Expose
    @SerializedName("PosSalesTransaction")
    private POSTransactionEntity PosSalesTransaction;
    @Expose
    @SerializedName("EprescriptionMaxDicountValue")
    private int EprescriptionMaxDicountValue;
    @Expose
    @SerializedName("EprescriptionDiscountPer")
    private int EprescriptionDiscountPer;
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
    @Expose
    @SerializedName("FixedDiscountCode")
    private String FixedDiscountCode;

    public void setFixedDiscountCode(String fixedDiscountCode) {
        FixedDiscountCode = fixedDiscountCode;
    }

    public void setPosSalesTransaction(POSTransactionEntity posSalesTransaction) {
        PosSalesTransaction = posSalesTransaction;
    }

    public void setEprescriptionMaxDicountValue(int eprescriptionMaxDicountValue) {
        EprescriptionMaxDicountValue = eprescriptionMaxDicountValue;
    }

    public void setEprescriptionDiscountPer(int eprescriptionDiscountPer) {
        EprescriptionDiscountPer = eprescriptionDiscountPer;
    }

    public void setHealingCardThresholdAmout(int healingCardThresholdAmout) {
        HealingCardThresholdAmout = healingCardThresholdAmout;
    }

    public void setCreditAmount(int creditAmount) {
        CreditAmount = creditAmount;
    }

    public void setISDiscountCodeRequired(int ISDiscountCodeRequired) {
        this.ISDiscountCodeRequired = ISDiscountCodeRequired;
    }

    public void setClearAllDiscount(boolean clearAllDiscount) {
        IsClearAllDiscount = clearAllDiscount;
    }

    public void setAutoDiscount(boolean autoDiscount) {
        IsAutoDiscount = autoDiscount;
    }

    public void setNormalSale(boolean normalSale) {
        IsNormalSale = normalSale;
    }

    public void setOPTValidate(boolean OPTValidate) {
        this.OPTValidate = OPTValidate;
    }

    public void setIsOTPRequired(int isOTPRequired) {
        IsOTPRequired = isOTPRequired;
    }

    public void setRequestStatus(int requestStatus) {
        RequestStatus = requestStatus;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public void setAvailableDiscList(List<ManualDiscCheckRes.AvailableDiscList> availableDiscList) {
        this.availableDiscList = availableDiscList;
    }

    public void setDiscList(List<ManualDiscCheckRes.DiscList> discList) {
        this.discList = discList;
    }

    public void setDisplayList(List<ManualDiscCheckRes.DisplayList> displayList) {
        this.displayList = displayList;
    }
}
