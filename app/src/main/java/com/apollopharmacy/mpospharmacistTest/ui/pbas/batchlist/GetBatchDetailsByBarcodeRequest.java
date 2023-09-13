package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetBatchDetailsByBarcodeRequest implements Serializable
    {

        @SerializedName("ArticleCode")
        @Expose
        private String articleCode;
        @SerializedName("StoreId")
        @Expose
        private String storeId;
        @SerializedName("DataAreaId")
        @Expose
        private String dataAreaId;
        @SerializedName("TerminalId")
        @Expose
        private String terminalId;
        @SerializedName("StoreState")
        @Expose
        private String storeState;
        @SerializedName("CustomerState")
        @Expose
        private String customerState;
        @SerializedName("SEZ")
        @Expose
        private Integer sez;
        @SerializedName("SearchType")
        @Expose
        private Integer searchType;
        @SerializedName("ExpiryDays")
        @Expose
        private Integer expiryDays;
        @SerializedName("Barcode")
        @Expose
        private String barcode;
        private final static long serialVersionUID = -4590435230942443014L;

        public String getArticleCode() {
            return articleCode;
        }

        public void setArticleCode(String articleCode) {
            this.articleCode = articleCode;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getDataAreaId() {
            return dataAreaId;
        }

        public void setDataAreaId(String dataAreaId) {
            this.dataAreaId = dataAreaId;
        }

        public String getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public String getStoreState() {
            return storeState;
        }

        public void setStoreState(String storeState) {
            this.storeState = storeState;
        }

        public String getCustomerState() {
            return customerState;
        }

        public void setCustomerState(String customerState) {
            this.customerState = customerState;
        }

        public Integer getSez() {
            return sez;
        }

        public void setSez(Integer sez) {
            this.sez = sez;
        }

        public Integer getSearchType() {
            return searchType;
        }

        public void setSearchType(Integer searchType) {
            this.searchType = searchType;
        }

        public Integer getExpiryDays() {
            return expiryDays;
        }

        public void setExpiryDays(Integer expiryDays) {
            this.expiryDays = expiryDays;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

    }

