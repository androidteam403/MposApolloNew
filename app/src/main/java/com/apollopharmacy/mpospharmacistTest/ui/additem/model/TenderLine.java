package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TenderLine {

        @SerializedName("AmountCur")
        @Expose
        public Integer amountCur;
        @SerializedName("AmountMst")
        @Expose
        public Integer amountMst;
        @SerializedName("AmountTendered")
        @Expose
        public Integer amountTendered;
        @SerializedName("BarCode")
        @Expose
        public String barCode;
        @SerializedName("ExchRate")
        @Expose
        public Integer exchRate;
        @SerializedName("ExchRateMst")
        @Expose
        public Integer exchRateMst;
        @SerializedName("IsVoid")
        @Expose
        public Boolean isVoid;
        @SerializedName("LineNo")
        @Expose
        public Integer lineNo;
        @SerializedName("MobileNo")
        @Expose
        public String mobileNo;
        @SerializedName("PreviewText")
        @Expose
        public String previewText;
        @SerializedName("RewardsPoint")
        @Expose
        public Integer rewardsPoint;
        @SerializedName("TenderId")
        @Expose
        public String tenderId;
        @SerializedName("TenderName")
        @Expose
        public String tenderName;
        @SerializedName("TenderType")
        @Expose
        public Integer tenderType;
        @SerializedName("WalletOrderId")
        @Expose
        public String walletOrderId;
        @SerializedName("WalletTransactionID")
        @Expose
        public String walletTransactionID;
        @SerializedName("WalletType")
        @Expose
        public Integer walletType;

    public Integer getAmountCur() {
        return amountCur;
    }

    public void setAmountCur(Integer amountCur) {
        this.amountCur = amountCur;
    }

    public Integer getAmountMst() {
        return amountMst;
    }

    public void setAmountMst(Integer amountMst) {
        this.amountMst = amountMst;
    }

    public Integer getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(Integer amountTendered) {
        this.amountTendered = amountTendered;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getExchRate() {
        return exchRate;
    }

    public void setExchRate(Integer exchRate) {
        this.exchRate = exchRate;
    }

    public Integer getExchRateMst() {
        return exchRateMst;
    }

    public void setExchRateMst(Integer exchRateMst) {
        this.exchRateMst = exchRateMst;
    }

    public Boolean getVoid() {
        return isVoid;
    }

    public void setVoid(Boolean aVoid) {
        isVoid = aVoid;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public Integer getRewardsPoint() {
        return rewardsPoint;
    }

    public void setRewardsPoint(Integer rewardsPoint) {
        this.rewardsPoint = rewardsPoint;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getTenderName() {
        return tenderName;
    }

    public void setTenderName(String tenderName) {
        this.tenderName = tenderName;
    }

    public Integer getTenderType() {
        return tenderType;
    }

    public void setTenderType(Integer tenderType) {
        this.tenderType = tenderType;
    }

    public String getWalletOrderId() {
        return walletOrderId;
    }

    public void setWalletOrderId(String walletOrderId) {
        this.walletOrderId = walletOrderId;
    }

    public String getWalletTransactionID() {
        return walletTransactionID;
    }

    public void setWalletTransactionID(String walletTransactionID) {
        this.walletTransactionID = walletTransactionID;
    }

    public Integer getWalletType() {
        return walletType;
    }

    public void setWalletType(Integer walletType) {
        this.walletType = walletType;
    }
}
