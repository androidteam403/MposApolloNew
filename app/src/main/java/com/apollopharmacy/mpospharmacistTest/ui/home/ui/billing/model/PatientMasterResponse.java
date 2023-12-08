package com.apollopharmacy.mpospharmacistTest.ui.home.ui.billing.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientMasterResponse {
    @SerializedName("RequestStatus")
    private int requestStatus;
    @SerializedName("ReturnMessage")
    private String returnMessage;
    @SerializedName("_DropdownValue")
    private List<DropdownValue> dropdownValue;

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public List<DropdownValue> getDropdownValue() {
        return dropdownValue;
    }

    public void setDropdownValue(List<DropdownValue> dropdownValue) {
        this.dropdownValue = dropdownValue;
    }

    public class DropdownValue {
        @SerializedName("code")
        private String code;
        @SerializedName("displayText")
        private String displayText;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDisplayText() {
            return displayText;
        }

        public void setDisplayText(String displayText) {
            this.displayText = displayText;
        }
    }

}
