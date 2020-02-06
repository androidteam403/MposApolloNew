package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model;

public class ProductList {
    private String artCode;
    private String productDescription;
    private String productCategory;
    private String subCategory;
    private String productManufacture;
    private String genericName;

    public ProductList(String artCode, String productDescription, String productCategory,
                       String subCategory, String productManufacture, String genericName) {
        this.artCode = artCode;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.subCategory = subCategory;
        this.productManufacture = productManufacture;
        this.genericName = genericName;
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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getProductManufacture() {
        return productManufacture;
    }

    public void setProductManufacture(String productManufacture) {
        this.productManufacture = productManufacture;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }
}
