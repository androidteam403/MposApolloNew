package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class GetUniversalDropDownBindResponse implements Serializable
    {

        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;
        @SerializedName("_DropDownLineList")
        @Expose
        private List<DropDownLine> dropDownLineList;


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
        public static class DropDownLine implements Serializable
        {

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

    }





