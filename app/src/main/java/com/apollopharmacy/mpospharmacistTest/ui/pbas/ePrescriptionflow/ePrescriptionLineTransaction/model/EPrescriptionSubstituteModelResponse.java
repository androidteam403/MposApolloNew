package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EPrescriptionSubstituteModelResponse implements Serializable
    {

        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;
        @SerializedName("SubstituteList")
        @Expose
        private List<Substitute> substituteList = null;
        private final static long serialVersionUID = -4694515360178489855L;

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

        public List<Substitute> getSubstituteList() {
            return substituteList;
        }

        public void setSubstituteList(List<Substitute> substituteList) {
            this.substituteList = substituteList;
        }

        public static class Substitute implements Serializable
        {

            @SerializedName("ArtCode")
            @Expose
            private String artCode;
            @SerializedName("MRP")
            @Expose
            private Double mrp;
            @SerializedName("ON_HAND")
            @Expose
            private String onHand;
            @SerializedName("PrescriptionNo")
            @Expose
            private String prescriptionNo;
            @SerializedName("SubstituteArt")
            @Expose
            private String substituteArt;
            @SerializedName("SubstituteArtCode")
            @Expose
            private String substituteArtCode;
            private final static long serialVersionUID = -4210481372860165900L;

            public String getArtCode() {
                return artCode;
            }

            public void setArtCode(String artCode) {
                this.artCode = artCode;
            }

            public Double getMrp() {
                return mrp;
            }

            public void setMrp(Double mrp) {
                this.mrp = mrp;
            }

            public String getOnHand() {
                return onHand;
            }

            public void setOnHand(String onHand) {
                this.onHand = onHand;
            }

            public String getPrescriptionNo() {
                return prescriptionNo;
            }

            public void setPrescriptionNo(String prescriptionNo) {
                this.prescriptionNo = prescriptionNo;
            }

            public String getSubstituteArt() {
                return substituteArt;
            }

            public void setSubstituteArt(String substituteArt) {
                this.substituteArt = substituteArt;
            }

            public String getSubstituteArtCode() {
                return substituteArtCode;
            }

            public void setSubstituteArtCode(String substituteArtCode) {
                this.substituteArtCode = substituteArtCode;
            }

        }


    }



