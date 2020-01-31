package com.apollo.pharmacy.ui.searchproduct.model;

public class ProductInfoPojo {

    private String productSno;
    private String artCode;
    private String productDescription;
    private String prodCategory;
    private String subCategory;
    private String schCatg;
    private String prodManufacture;
    private String rackId;
    private String genericName;

    public ProductInfoPojo(String productSno, String artCode, String productDescription,
                           String prodCategory, String subCategory, String schCatg, String prodManufacture, String rackId, String genericName) {
        this.productSno = productSno;
        this.artCode = artCode;
        this.productDescription = productDescription;
        this.prodCategory = prodCategory;
        this.subCategory = subCategory;
        this.schCatg = schCatg;
        this.prodManufacture = prodManufacture;
        this.rackId = rackId;
        this.genericName = genericName;
    }

    public String getProductSno() {
        return productSno;
    }

    public void setProductSno(String productSno) {
        this.productSno = productSno;
    }

    public String getArtCode() {
        return artCode;
    }

    public void setArtCode(String artCode) {
        this.artCode = artCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSchCatg() {
        return schCatg;
    }

    public void setSchCatg(String schCatg) {
        this.schCatg = schCatg;
    }

    public String getProdManufacture() {
        return prodManufacture;
    }

    public void setProdManufacture(String prodManufacture) {
        this.prodManufacture = prodManufacture;
    }

    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }
}
