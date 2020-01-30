package com.apollo.pharmacy.ui.dashboard.model;

public class PharmaPojo {
    private String desc;
    private String artCode;
    private String batchNo;
    private String category;
    private String expiry;

    public PharmaPojo(String desc, String artCode, String batchNo, String category, String expiry) {
        this.desc = desc;
        this.artCode = artCode;
        this.batchNo = batchNo;
        this.category = category;
        this.expiry = expiry;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getArtCode() {
        return artCode;
    }

    public void setArtCode(String artCode) {
        this.artCode = artCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}