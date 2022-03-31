package com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBatchInfoReq {


    @Expose
    @SerializedName("SearchType")
    private int SearchType;
    @Expose
    @SerializedName("SEZ")
    private int SEZ;
    @Expose
    @SerializedName("CustomerState")
    private String CustomerState;
    @Expose
    @SerializedName("StoreState")
    private String StoreState;
    @Expose
    @SerializedName("TerminalId")
    private String TerminalId;
    @Expose
    @SerializedName("DataAreaId")
    private String DataAreaId;
    @Expose
    @SerializedName("StoreId")
    private String StoreId;
    @Expose
    @SerializedName("ArticleCode")
    private String ArticleCode;

    @Expose
    @SerializedName("ExpiryDays")
    private double ExpiryDays;

    public double getExpiryDays() {
        return ExpiryDays;
    }

    public void setExpiryDays(double ExpiryDays) {
        this.ExpiryDays = ExpiryDays;
    }

    public int getSearchType() {
        return SearchType;
    }

    public int getSEZ() {
        return SEZ;
    }

    public String getCustomerState() {
        return CustomerState;
    }

    public String getStoreState() {
        return StoreState;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public String getDataAreaId() {
        return DataAreaId;
    }

    public String getStoreId() {
        return StoreId;
    }

    public String getArticleCode() {
        return ArticleCode;
    }

    public void setSearchType(int searchType) {
        SearchType = searchType;
    }

    public void setSEZ(int SEZ) {
        this.SEZ = SEZ;
    }

    public void setCustomerState(String customerState) {
        CustomerState = customerState;
    }

    public void setStoreState(String storeState) {
        StoreState = storeState;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public void setDataAreaId(String dataAreaId) {
        DataAreaId = dataAreaId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public void setArticleCode(String articleCode) {
        ArticleCode = articleCode;
    }
}
