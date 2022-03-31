package com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOnlineCorporateListApiRequest {
    @SerializedName("StoreId")
    @Expose
    private String storeId;
    @SerializedName("TerminalId")
    @Expose
    private String terminalId;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("ISPharmaReturn")
    @Expose
    private Boolean iSPharmaReturn;
    @SerializedName("ISFMCGReturn")
    @Expose
    private Boolean iSFMCGReturn;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getISPharmaReturn() {
        return iSPharmaReturn;
    }

    public void setISPharmaReturn(Boolean iSPharmaReturn) {
        this.iSPharmaReturn = iSPharmaReturn;
    }

    public Boolean getISFMCGReturn() {
        return iSFMCGReturn;
    }

    public void setISFMCGReturn(Boolean iSFMCGReturn) {
        this.iSFMCGReturn = iSFMCGReturn;
    }

}
