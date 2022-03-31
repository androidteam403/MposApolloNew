package com.apollopharmacy.mpospharmacistTest.data.db.model;

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


    private double TotalTax;
    private String SNO;
    private String SGSTTaxCode;
    private double SGSTPerc;
    private int REQQTY;
    private String Q_O_H;
    private double Price;
    private boolean NearByExpiry;
    private double MRP;
    private String ItemID;
    private boolean ISMRPChange;
    private String IGSTTaxCode;
    private double IGSTPerc;
    private String ExpDate;
    private String CGSTTaxCode;
    private double CGSTPerc;
    private String CESSTaxCode;
    private double CESSPerc;
    private String BatchNo;

    public double getTotalTax() {
        return TotalTax;
    }

    public void setTotalTax(double totalTax) {
        TotalTax = totalTax;
    }

    public String getSNO() {
        return SNO;
    }

    public void setSNO(String SNO) {
        this.SNO = SNO;
    }

    public String getSGSTTaxCode() {
        return SGSTTaxCode;
    }

    public void setSGSTTaxCode(String SGSTTaxCode) {
        this.SGSTTaxCode = SGSTTaxCode;
    }

    public double getSGSTPerc() {
        return SGSTPerc;
    }

    public void setSGSTPerc(double SGSTPerc) {
        this.SGSTPerc = SGSTPerc;
    }

    public int getREQQTY() {
        return REQQTY;
    }

    public void setREQQTY(int REQQTY) {
        this.REQQTY = REQQTY;
    }

    public String getQ_O_H() {
        return Q_O_H;
    }

    public void setQ_O_H(String q_O_H) {
        Q_O_H = q_O_H;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public boolean isNearByExpiry() {
        return NearByExpiry;
    }

    public void setNearByExpiry(boolean nearByExpiry) {
        NearByExpiry = nearByExpiry;
    }

    public double getMRP() {
        return MRP;
    }

    public void setMRP(double MRP) {
        this.MRP = MRP;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public boolean isISMRPChange() {
        return ISMRPChange;
    }

    public void setISMRPChange(boolean ISMRPChange) {
        this.ISMRPChange = ISMRPChange;
    }

    public String getIGSTTaxCode() {
        return IGSTTaxCode;
    }

    public void setIGSTTaxCode(String IGSTTaxCode) {
        this.IGSTTaxCode = IGSTTaxCode;
    }

    public double getIGSTPerc() {
        return IGSTPerc;
    }

    public void setIGSTPerc(double IGSTPerc) {
        this.IGSTPerc = IGSTPerc;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }

    public String getCGSTTaxCode() {
        return CGSTTaxCode;
    }

    public void setCGSTTaxCode(String CGSTTaxCode) {
        this.CGSTTaxCode = CGSTTaxCode;
    }

    public double getCGSTPerc() {
        return CGSTPerc;
    }

    public void setCGSTPerc(double CGSTPerc) {
        this.CGSTPerc = CGSTPerc;
    }

    public String getCESSTaxCode() {
        return CESSTaxCode;
    }

    public void setCESSTaxCode(String CESSTaxCode) {
        this.CESSTaxCode = CESSTaxCode;
    }

    public double getCESSPerc() {
        return CESSPerc;
    }

    public void setCESSPerc(double CESSPerc) {
        this.CESSPerc = CESSPerc;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }
}
