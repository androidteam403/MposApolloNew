package com.apollo.pharmacy.ui.searchproduct.model;

public class ProductBatchPojo {

    private String batchNo;
    private String expiryDate;
    private String productMrp;
    private String productTax;
    private String reqQty;

    public ProductBatchPojo(String batchNo, String expiryDate, String productMrp, String productTax, String reqQty) {
        this.batchNo = batchNo;
        this.expiryDate = expiryDate;
        this.productMrp = productMrp;
        this.productTax = productTax;
        this.reqQty = reqQty;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProductMrp() {
        return productMrp;
    }

    public void setProductMrp(String productMrp) {
        this.productMrp = productMrp;
    }

    public String getProductTax() {
        return productTax;
    }

    public void setProductTax(String productTax) {
        this.productTax = productTax;
    }

    public String getReqQty() {
        return reqQty;
    }

    public void setReqQty(String reqQty) {
        this.reqQty = reqQty;
    }
}