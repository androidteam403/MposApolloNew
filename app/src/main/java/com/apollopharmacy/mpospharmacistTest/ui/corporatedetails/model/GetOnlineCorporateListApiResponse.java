package com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOnlineCorporateListApiResponse {
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("_DropDownLineList")
    @Expose
    private List<DropDownLine> dropDownLineList = null;

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

    public List<DropDownLine> getDropDownLineList() {
        return dropDownLineList;
    }

    public void setDropDownLineList(List<DropDownLine> dropDownLineList) {
        this.dropDownLineList = dropDownLineList;
    }

}
