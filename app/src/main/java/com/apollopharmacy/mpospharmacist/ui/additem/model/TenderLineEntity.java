package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TenderLineEntity implements Serializable {
        @Expose
        @SerializedName("WalletType")
        private double WalletType;
        @Expose
        @SerializedName("WalletTransactionID")
        private String WalletTransactionID;
        @Expose
        @SerializedName("WalletOrderId")
        private String WalletOrderId;
        @Expose
        @SerializedName("TenderType")
        private double TenderType;
        @Expose
        @SerializedName("TenderName")
        private String TenderName;
        @Expose
        @SerializedName("TenderId")
        private String TenderId;
        @Expose
        @SerializedName("RewardsPoint")
        private int RewardsPoint;
        @Expose
        @SerializedName("PreviewText")
        private String PreviewText;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("LineNo")
        private double LineNo;
        @Expose
        @SerializedName("IsVoid")
        private boolean IsVoid;
        @Expose
        @SerializedName("ExchRateMst")
        private double ExchRateMst;
        @Expose
        @SerializedName("ExchRate")
        private double ExchRate;
        @Expose
        @SerializedName("BarCode")
        private String BarCode;
        @Expose
        @SerializedName("AmountTendered")
        private double AmountTendered;
        @Expose
        @SerializedName("AmountMst")
        private double AmountMst;
        @Expose
        @SerializedName("AmountCur")
        private double AmountCur;

        private boolean amountToBeVoid;

        public boolean isAmountToBeVoid() {
                return amountToBeVoid;
        }

        public void setAmountToBeVoid(boolean amountToBeVoid) {
                this.amountToBeVoid = amountToBeVoid;
        }

        public double getWalletType() {
            return WalletType;
        }

        public String getWalletTransactionID() {
            return WalletTransactionID;
        }

        public String getWalletOrderId() {
            return WalletOrderId;
        }

        public double getTenderType() {
            return TenderType;
        }

        public String getTenderName() {
            return TenderName;
        }

        public String getTenderId() {
            return TenderId;
        }

        public double getRewardPoints() {
            return RewardsPoint;
        }

        public String getPreviewText() {
            return PreviewText;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public double getLineNo() {
            return LineNo;
        }

        public boolean getIsVoid() {
            return IsVoid;
        }

        public void setVoid(boolean aVoid) {
                IsVoid = aVoid;
        }

        public double getExchRateMst() {
            return ExchRateMst;
        }

        public double getExchRate() {
            return ExchRate;
        }

        public String getBarCode() {
            return BarCode;
        }

        public double getAmountTendered() {
            return AmountTendered;
        }

        public double getAmountMst() {
            return AmountMst;
        }

        public double getAmountCur() {
            return AmountCur;
        }

        public void setMobileNo(String mobileNo) {
                MobileNo = mobileNo;
        }

        public void setWalletType(double walletType) {
                WalletType = walletType;
        }

        public void setWalletTransactionID(String walletTransactionID) {
                WalletTransactionID = walletTransactionID;
        }

        public void setWalletOrderId(String walletOrderId) {
                WalletOrderId = walletOrderId;
        }

        public void setTenderType(double tenderType) {
                TenderType = tenderType;
        }

        public void setTenderName(String tenderName) {
                TenderName = tenderName;
        }

        public void setTenderId(String tenderId) {
                TenderId = tenderId;
        }

        public void setRewardsPoint(int rewardsPoint) {
                rewardsPoint = rewardsPoint;
        }

        public void setPreviewText(String previewText) {
                PreviewText = previewText;
        }

        public void setLineNo(double lineNo) {
                LineNo = lineNo;
        }

        public boolean isVoid() {
                return IsVoid;
        }

        public void setExchRateMst(double exchRateMst) {
                ExchRateMst = exchRateMst;
        }

        public void setExchRate(double exchRate) {
                ExchRate = exchRate;
        }

        public void setBarCode(String barCode) {
                BarCode = barCode;
        }

        public void setAmountTendered(double amountTendered) {
                AmountTendered = amountTendered;
        }

        public void setAmountMst(double amountMst) {
                AmountMst = amountMst;
        }

        public void setAmountCur(double amountCur) {
                AmountCur = amountCur;
        }
}