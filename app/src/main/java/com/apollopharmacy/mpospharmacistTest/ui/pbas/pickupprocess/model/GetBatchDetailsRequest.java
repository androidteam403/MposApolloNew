package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBatchDetailsRequest {

    @SerializedName("ArticleCode")
    @Expose
    private String articleCode;
    @SerializedName("CustomerState")
    @Expose
    private String customerState;
    @SerializedName("DataAreaId")
    @Expose
    private String dataAreaId;
    @SerializedName("ExpiryDays")
    @Expose
    private Integer expiryDays;
    @SerializedName("SEZ")
    @Expose
    private Integer sez;
    @SerializedName("SearchType")
    @Expose
    private Integer searchType;
    @SerializedName("StoreId")
    @Expose
    private String storeId;
    @SerializedName("StoreState")
    @Expose
    private String storeState;
    @SerializedName("TerminalId")
    @Expose
    private String terminalId;

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getDataAreaId() {
        return dataAreaId;
    }

    public void setDataAreaId(String dataAreaId) {
        this.dataAreaId = dataAreaId;
    }

    public Integer getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

}