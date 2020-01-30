package com.apollo.pharmacy.ui.searchpharmacy.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pharmacy {

    @SerializedName("store_list")
    private ArrayList<StoreListObj> storeIdArrList;

    public ArrayList<StoreListObj> getStoreIdArrList() {
        return storeIdArrList;
    }

    public class StoreListObj {
        @SerializedName("store_id")
        private String storeId;
        @SerializedName("store_name")
        private String storeName;
        @SerializedName("store_address")
        private String storeAddress;
        @SerializedName("store_location")
        private String storeLocation;
        @SerializedName("store_pin_code")
        private String storePincode;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getStoreLocation() {
            return storeLocation;
        }

        public void setStoreLocation(String storeLocation) {
            this.storeLocation = storeLocation;
        }

        public String getStorePincode() {
            return storePincode;
        }

        public void setStorePincode(String storePincode) {
            this.storePincode = storePincode;
        }
    }
}


