package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetUniversalDropDownBindRequest implements Serializable
    {

        @SerializedName("StoreId")
        @Expose
        private String storeId;
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("Value")
        @Expose
        private String value;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
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

    }

