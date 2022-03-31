package com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DropDownLine {
    @SerializedName("ReferenceField")
    @Expose
    private String referenceField;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Values")
    @Expose
    private String values;

    public String getReferenceField() {
        return referenceField;
    }

    public void setReferenceField(String referenceField) {
        this.referenceField = referenceField;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
