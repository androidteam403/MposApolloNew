package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneratePdfbyFlidResponse {

    @SerializedName("STATUS")
    @Expose
    private Boolean status;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("URL")
    @Expose
    private String url;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
