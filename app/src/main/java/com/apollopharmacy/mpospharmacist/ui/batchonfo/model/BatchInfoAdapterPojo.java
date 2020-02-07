package com.apollopharmacy.mpospharmacist.ui.batchonfo.model;

public class BatchInfoAdapterPojo {
    private String batchNo;
    private String expDate;
    private String mrp;
    private String tax;
    private String qoh;
    private String reqQty;

    public BatchInfoAdapterPojo(String batchNo, String expDate, String mrp, String tax, String qoh, String reqQty) {
        this.batchNo = batchNo;
        this.expDate = expDate;
        this.mrp = mrp;
        this.tax = tax;
        this.qoh = qoh;
        this.reqQty = reqQty;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getQoh() {
        return qoh;
    }

    public void setQoh(String qoh) {
        this.qoh = qoh;
    }

    public String getReqQty() {
        return reqQty;
    }

    public void setReqQty(String reqQty) {
        this.reqQty = reqQty;
    }
}
