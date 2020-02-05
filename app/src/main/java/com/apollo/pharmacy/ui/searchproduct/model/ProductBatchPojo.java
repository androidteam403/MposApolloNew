package com.apollo.pharmacy.ui.searchproduct.model;

public class ProductBatchPojo {

   private String prodSno;
   private String prodBatchNo;
   private String expDate;
   private String prodMrp;
   private String prodTax;
   private String prodQoh;
   private String prodReqQty;

    public ProductBatchPojo(String prodSno, String prodBatchNo, String expDate, String prodMrp, String prodTax, String prodQoh, String prodReqQty) {
        this.prodSno = prodSno;
        this.prodBatchNo = prodBatchNo;
        this.expDate = expDate;
        this.prodMrp = prodMrp;
        this.prodTax = prodTax;
        this.prodQoh = prodQoh;
        this.prodReqQty = prodReqQty;
    }

    public String getProdSno() {
        return prodSno;
    }

    public void setProdSno(String prodSno) {
        this.prodSno = prodSno;
    }

    public String getProdBatchNo() {
        return prodBatchNo;
    }

    public void setProdBatchNo(String prodBatchNo) {
        this.prodBatchNo = prodBatchNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getProdMrp() {
        return prodMrp;
    }

    public void setProdMrp(String prodMrp) {
        this.prodMrp = prodMrp;
    }

    public String getProdTax() {
        return prodTax;
    }

    public void setProdTax(String prodTax) {
        this.prodTax = prodTax;
    }

    public String getProdQoh() {
        return prodQoh;
    }

    public void setProdQoh(String prodQoh) {
        this.prodQoh = prodQoh;
    }

    public String getProdReqQty() {
        return prodReqQty;
    }

    public void setProdReqQty(String prodReqQty) {
        this.prodReqQty = prodReqQty;
    }
}