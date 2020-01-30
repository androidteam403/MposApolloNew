package com.apollo.pharmacy.ui.searchproduct.model;

public class ProductInfoPojo {

    private String artCode;
    private String desCription;
    private String category;
    private String subCategory;
    private String Manufacture;
    private String generic;
    private String rackId;

    public ProductInfoPojo(String artCode, String desCription, String category, String subCategory,
                           String manufacture, String generic, String rackId) {
        this.artCode = artCode;
        this.desCription = desCription;
        this.category = category;
        this.subCategory = subCategory;
        Manufacture = manufacture;
        this.generic = generic;
        this.rackId = rackId;
    }

    public String getArtCode() {
        return artCode;
    }

    public void setArtCode(String artCode) {
        this.artCode = artCode;
    }

    public String getDesCription() {
        return desCription;
    }

    public void setDesCription(String desCription) {
        this.desCription = desCription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }

    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }
}
