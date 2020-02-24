package com.apollopharmacy.mpospharmacist.ui.batchonfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import io.realm.RealmObject;

public class GetBatchInfoRes {


    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("BatchList")
    private List<BatchListObj> BatchList;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public List<BatchListObj> getBatchList() {
        return BatchList;
    }

    public static class BatchListObj  implements Serializable {


        @Expose
        @SerializedName("TotalTax")
        private double TotalTax;
        @Expose
        @SerializedName("SNO")
        private String SNO;
        @Expose
        @SerializedName("SGSTTaxCode")
        private String SGSTTaxCode;
        @Expose
        @SerializedName("SGSTPerc")
        private double SGSTPerc;
        @Expose
        @SerializedName("REQQTY")
        private int REQQTY;
        @Expose
        @SerializedName("Q_O_H")
        private String Q_O_H;
        @Expose
        @SerializedName("Price")
        private double Price;
        @Expose
        @SerializedName("NearByExpiry")
        private boolean NearByExpiry;
        @Expose
        @SerializedName("MRP")
        private double MRP;
        @Expose
        @SerializedName("ItemID")
        private String ItemID;
        @Expose
        @SerializedName("ISMRPChange")
        private boolean ISMRPChange;
        @Expose
        @SerializedName("IGSTTaxCode")
        private String IGSTTaxCode;
        @Expose
        @SerializedName("IGSTPerc")
        private double IGSTPerc;
        @Expose
        @SerializedName("ExpDate")
        private String ExpDate;
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
        @SerializedName("BatchNo")
        private String BatchNo;

        public double getTotalTax() {
            return TotalTax;
        }

        public String getSNO() {
            return SNO;
        }

        public String getSGSTTaxCode() {
            return SGSTTaxCode;
        }

        public double getSGSTPerc() {
            return SGSTPerc;
        }

        public int getREQQTY() {
            return REQQTY;
        }

        public String getQ_O_H() {
            return Q_O_H;
        }

        public double getPrice() {
            return Price;
        }

        public boolean getNearByExpiry() {
            return NearByExpiry;
        }

        public double getMRP() {
            return MRP;
        }

        public String getItemID() {
            return ItemID;
        }

        public boolean getISMRPChange() {
            return ISMRPChange;
        }

        public String getIGSTTaxCode() {
            return IGSTTaxCode;
        }

        public double getIGSTPerc() {
            return IGSTPerc;
        }

        public String getExpDate() {
            return ExpDate;
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

        public String getBatchNo() {
            return BatchNo;
        }

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public void setTotalTax(double totalTax) {
            TotalTax = totalTax;
        }

        public void setSNO(String SNO) {
            this.SNO = SNO;
        }

        public void setSGSTTaxCode(String SGSTTaxCode) {
            this.SGSTTaxCode = SGSTTaxCode;
        }

        public void setSGSTPerc(double SGSTPerc) {
            this.SGSTPerc = SGSTPerc;
        }

        public void setREQQTY(int REQQTY) {
            this.REQQTY = REQQTY;
        }

        public void setQ_O_H(String q_O_H) {
            Q_O_H = q_O_H;
        }

        public void setPrice(double price) {
            Price = price;
        }

        public void setNearByExpiry(boolean nearByExpiry) {
            NearByExpiry = nearByExpiry;
        }

        public void setMRP(double MRP) {
            this.MRP = MRP;
        }

        public void setItemID(String itemID) {
            ItemID = itemID;
        }

        public void setISMRPChange(boolean ISMRPChange) {
            this.ISMRPChange = ISMRPChange;
        }

        public void setIGSTTaxCode(String IGSTTaxCode) {
            this.IGSTTaxCode = IGSTTaxCode;
        }

        public void setIGSTPerc(double IGSTPerc) {
            this.IGSTPerc = IGSTPerc;
        }

        public void setExpDate(String expDate) {
            ExpDate = expDate;
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

        public void setBatchNo(String batchNo) {
            BatchNo = batchNo;
        }

        private int enterReqQuantity;

        public int getEnterReqQuantity() {
            return enterReqQuantity;
        }

        public void setEnterReqQuantity(int enterReqQuantity) {
            this.enterReqQuantity = enterReqQuantity;
        }

        private String calculatedTotalPrice;


        public String getCalculatedTotalPrice() {
            return calculatedTotalPrice = new DecimalFormat("##.##").format(getMRP()*getEnterReqQuantity()) ;
        }


    }
}
