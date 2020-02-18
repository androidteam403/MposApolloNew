package com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CampaignDetailsRes {


    @Expose
    @SerializedName("CampDetails")
    private List<CampDetailsEntity> CampDetails;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private boolean status;

    public List<CampDetailsEntity> getCampDetails() {
        return CampDetails;
    }

    public String getMessage() {
        return message;
    }

    public boolean getStatus() {
        return status;
    }

    public static class CampDetailsEntity {
        @Expose
        @SerializedName("store_id")
        private int store_id;
        @Expose
        @SerializedName("end_time")
        private String end_time;
        @Expose
        @SerializedName("start_time")
        private String start_time;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("name")
        private String name;

        public int getStore_id() {
            return store_id;
        }

        public String getEnd_time() {
            return end_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return  name+","+address;
        }
    }
}
