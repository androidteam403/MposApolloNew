package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetInventoryTransactionDetailsRequest implements Serializable
    {

        @SerializedName("FromDate")
        @Expose
        private String fromDate;
        @SerializedName("ToDate")
        @Expose
        private String toDate;
        @SerializedName("RefNo")
        @Expose
        private String refNo;
        @SerializedName("Site")
        @Expose
        private String site;
        @SerializedName("ISUnposted")
        @Expose
        private Boolean iSUnposted;
        private final static long serialVersionUID = 6739899528414151894L;

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getRefNo() {
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public Boolean getISUnposted() {
            return iSUnposted;
        }

        public void setISUnposted(Boolean iSUnposted) {
            this.iSUnposted = iSUnposted;
        }

    }

