package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CircleMemebershipCashbackPlanResponse {
    @SerializedName("CategoryList")
    @Expose
    private ArrayList<Category> categoryList = null;
    @SerializedName("RequestStatus")
    @Expose
    private Integer requestStatus;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

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


    public  static class Category implements Serializable
    {
        @SerializedName("CATEGORY")
        @Expose
        private String category;
        @SerializedName("HEADER")
        @Expose
        private String header;
        @SerializedName("ITMEVAL")
        @Expose
        private Double itmeval;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public Double getItmeval() {
            return itmeval;
        }

        public void setItmeval(Double itmeval) {
            this.itmeval = itmeval;
        }
    }

}
