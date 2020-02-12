package com.apollopharmacy.mpospharmacist.data.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartItems extends RealmObject {

    @PrimaryKey
    private int id;
    private String SubClassification;
    private String SubCategory;
    private String Sch_Catg_Code;
    private String Sch_Catg;
    private String SI_NO;
    private String RetailSubCategoryRecID;
    private String RetailMainCategoryRecID;
    private String RetailCategoryRecID;
    private String RackId;
    private String ProductRecID;
    private String ManufactureCode;
    private String Manufacture;
    private String Hsncode_In;
    private String GenericName;
    private String DiseaseType;
    private String Description;
    private boolean DPCO;
    private String CategoryCode;
    private String Category;
    private String ArtCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubClassification() {
        return SubClassification;
    }

    public void setSubClassification(String subClassification) {
        SubClassification = subClassification;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public String getSch_Catg_Code() {
        return Sch_Catg_Code;
    }

    public void setSch_Catg_Code(String sch_Catg_Code) {
        Sch_Catg_Code = sch_Catg_Code;
    }

    public String getSch_Catg() {
        return Sch_Catg;
    }

    public void setSch_Catg(String sch_Catg) {
        Sch_Catg = sch_Catg;
    }

    public String getSI_NO() {
        return SI_NO;
    }

    public void setSI_NO(String SI_NO) {
        this.SI_NO = SI_NO;
    }

    public String getRetailSubCategoryRecID() {
        return RetailSubCategoryRecID;
    }

    public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
        RetailSubCategoryRecID = retailSubCategoryRecID;
    }

    public String getRetailMainCategoryRecID() {
        return RetailMainCategoryRecID;
    }

    public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
        RetailMainCategoryRecID = retailMainCategoryRecID;
    }

    public String getRetailCategoryRecID() {
        return RetailCategoryRecID;
    }

    public void setRetailCategoryRecID(String retailCategoryRecID) {
        RetailCategoryRecID = retailCategoryRecID;
    }

    public String getRackId() {
        return RackId;
    }

    public void setRackId(String rackId) {
        RackId = rackId;
    }

    public String getProductRecID() {
        return ProductRecID;
    }

    public void setProductRecID(String productRecID) {
        ProductRecID = productRecID;
    }

    public String getManufactureCode() {
        return ManufactureCode;
    }

    public void setManufactureCode(String manufactureCode) {
        ManufactureCode = manufactureCode;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
    }

    public String getHsncode_In() {
        return Hsncode_In;
    }

    public void setHsncode_In(String hsncode_In) {
        Hsncode_In = hsncode_In;
    }

    public String getGenericName() {
        return GenericName;
    }

    public void setGenericName(String genericName) {
        GenericName = genericName;
    }

    public String getDiseaseType() {
        return DiseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        DiseaseType = diseaseType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isDPCO() {
        return DPCO;
    }

    public void setDPCO(boolean DPCO) {
        this.DPCO = DPCO;
    }

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getArtCode() {
        return ArtCode;
    }

    public void setArtCode(String artCode) {
        ArtCode = artCode;
    }
}
