package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetItemDetailsReq {


    @Expose
    @SerializedName("CorpCode")
    private String CorpCode;
    @Expose
    @SerializedName("SearchString")
    private String SearchString;
    @Expose
    @SerializedName("StoreID")
    private String StoreID;
    @Expose
    @SerializedName("IsStockCheck")
    private boolean IsStockCheck;
    @Expose
    @SerializedName("IsGeneric")
    private boolean IsGeneric;
    @Expose
    @SerializedName("IsInitial")
    private boolean IsInitial;

    public String getCorpCode() {
        return CorpCode;
    }

    public String getSearchString() {
        return SearchString;
    }

    public String getStoreID() {
        return StoreID;
    }

    public boolean getIsStockCheck() {
        return IsStockCheck;
    }

    public boolean getIsGeneric() {
        return IsGeneric;
    }

    public boolean getIsInitial() {
        return IsInitial;
    }

    public void setCorpCode(String corpCode) {
        CorpCode = corpCode;
    }

    public void setSearchString(String searchString) {
        SearchString = searchString;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public void setStockCheck(boolean stockCheck) {
        IsStockCheck = stockCheck;
    }

    public void setGeneric(boolean generic) {
        IsGeneric = generic;
    }

    public void setInitial(boolean initial) {
        IsInitial = initial;
    }
}
