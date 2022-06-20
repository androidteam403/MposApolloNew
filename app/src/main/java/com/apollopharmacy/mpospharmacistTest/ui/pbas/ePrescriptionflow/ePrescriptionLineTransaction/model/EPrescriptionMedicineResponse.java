package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

    public class EPrescriptionMedicineResponse implements Serializable
    {

        @SerializedName("ActName")
        @Expose
        private String actName;
        @SerializedName("ArtCode")
        @Expose
        private String artCode;
        @SerializedName("ArtId")
        @Expose
        private String artId;
        @SerializedName("ArtName")
        @Expose
        private String artName;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("LastCol")
        @Expose
        private String lastCol;
        @SerializedName("MRP")
        @Expose
        private String mrp;
        @SerializedName("ON_Hand")
        @Expose
        private String oNHand;
        @SerializedName("PackMrp")
        @Expose
        private Double packMrp;
        @SerializedName("PackSize")
        @Expose
        private String packSize;
        @SerializedName("PrescriptionNo")
        @Expose
        private String prescriptionNo;
        @SerializedName("Qty")
        @Expose
        private String qty;
        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;
        @SerializedName("SLNNo")
        @Expose
        private String sLNNo;
        @SerializedName("Schedule")
        @Expose
        private String schedule;
        @SerializedName("ShopId")
        @Expose
        private String shopId;
        @SerializedName("Status")
        @Expose
        private Boolean status;
        @SerializedName("SubstituteArtCode")
        @Expose
        private String substituteArtCode;
        @SerializedName("SubstituteMRP")
        @Expose
        private String substituteMRP;
        @SerializedName("WayOrder")
        @Expose
        private String wayOrder;

        private EPrescriptionSubstituteModelResponse.Substitute substitute;
        private final static long serialVersionUID = 6983229842062456972L;

        public String getActName() {
            return actName;
        }

        public void setActName(String actName) {
            this.actName = actName;
        }

        public String getArtCode() {
            return artCode;
        }

        public void setArtCode(String artCode) {
            this.artCode = artCode;
        }

        public String getArtId() {
            return artId;
        }

        public void setArtId(String artId) {
            this.artId = artId;
        }

        public String getArtName() {
            return artName;
        }

        public void setArtName(String artName) {
            this.artName = artName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLastCol() {
            return lastCol;
        }

        public void setLastCol(String lastCol) {
            this.lastCol = lastCol;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getONHand() {
            return oNHand;
        }

        public void setONHand(String oNHand) {
            this.oNHand = oNHand;
        }

        public Double getPackMrp() {
            return packMrp;
        }

        public void setPackMrp(Double packMrp) {
            this.packMrp = packMrp;
        }

        public String getPackSize() {
            return packSize;
        }

        public void setPackSize(String packSize) {
            this.packSize = packSize;
        }

        public String getPrescriptionNo() {
            return prescriptionNo;
        }

        public void setPrescriptionNo(String prescriptionNo) {
            this.prescriptionNo = prescriptionNo;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public Integer getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(Integer requestStatus) {
            this.requestStatus = requestStatus;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public String getSLNNo() {
            return sLNNo;
        }

        public void setSLNNo(String sLNNo) {
            this.sLNNo = sLNNo;
        }

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getSubstituteArtCode() {
            return substituteArtCode;
        }

        public void setSubstituteArtCode(String substituteArtCode) {
            this.substituteArtCode = substituteArtCode;
        }

        public String getSubstituteMRP() {
            return substituteMRP;
        }

        public void setSubstituteMRP(String substituteMRP) {
            this.substituteMRP = substituteMRP;
        }

        public String getWayOrder() {
            return wayOrder;
        }

        public void setWayOrder(String wayOrder) {
            this.wayOrder = wayOrder;
        }

        public EPrescriptionSubstituteModelResponse.Substitute getSubstitute() {
            return substitute;
        }

        public void setSubstitute(EPrescriptionSubstituteModelResponse.Substitute substitute) {
            this.substitute = substitute;
        }
    }

