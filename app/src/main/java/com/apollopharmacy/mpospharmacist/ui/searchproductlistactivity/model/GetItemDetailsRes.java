package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model;

import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class GetItemDetailsRes {


    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("ItemList")
    private ArrayList<Items> ItemList;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public ArrayList<Items> getItemList() {
        return ItemList;
    }

    public static class Items extends BaseObservable implements Serializable {

        @Expose
        @SerializedName("SubClassification")
        private String SubClassification;
        @Expose
        @SerializedName("SubCategory")
        private String SubCategory;
        @Expose
        @SerializedName("Sch_Catg_Code")
        private String Sch_Catg_Code;
        @Expose
        @SerializedName("Sch_Catg")
        private String Sch_Catg;
        @Expose
        @SerializedName("SI_NO")
        private String SI_NO;
        @Expose
        @SerializedName("RetailSubCategoryRecID")
        private String RetailSubCategoryRecID;
        @Expose
        @SerializedName("RetailMainCategoryRecID")
        private String RetailMainCategoryRecID;
        @Expose
        @SerializedName("RetailCategoryRecID")
        private String RetailCategoryRecID;
        @Expose
        @SerializedName("RackId")
        private String RackId;
        @Expose
        @SerializedName("ProductRecID")
        private String ProductRecID;
        @Expose
        @SerializedName("ManufactureCode")
        private String ManufactureCode;
        @Expose
        @SerializedName("Manufacture")
        private String Manufacture;
        @Expose
        @SerializedName("Hsncode_In")
        private String Hsncode_In;
        @Expose
        @SerializedName("GenericName")
        private String GenericName;
        @Expose
        @SerializedName("DiseaseType")
        private String DiseaseType;
        @Expose
        @SerializedName("Description")
        private String Description;
        @Expose
        @SerializedName("DPCO")
        private boolean DPCO;
        @Expose
        @SerializedName("CategoryCode")
        private String CategoryCode;
        @Expose
        @SerializedName("Category")
        private String Category;
        @Expose
        @SerializedName("ArtCode")
        private String ArtCode;

        public String getSubClassification() {
            return SubClassification;
        }

        public String getSubCategory() {
            return SubCategory;
        }

        public String getSch_Catg_Code() {
            return Sch_Catg_Code;
        }

        public String getSch_Catg() {
            return Sch_Catg;
        }

        public String getSI_NO() {
            return SI_NO;
        }

        public String getRetailSubCategoryRecID() {
            return RetailSubCategoryRecID;
        }

        public String getRetailMainCategoryRecID() {
            return RetailMainCategoryRecID;
        }

        public String getRetailCategoryRecID() {
            return RetailCategoryRecID;
        }

        public String getRackId() {
            return RackId;
        }

        public String getProductRecID() {
            return ProductRecID;
        }

        public String getManufactureCode() {
            return ManufactureCode;
        }

        public String getManufacture() {
            return Manufacture;
        }

        public String getHsncode_In() {
            return Hsncode_In;
        }

        public String getGenericName() {
            return GenericName;
        }

        public String getDiseaseType() {
            return DiseaseType;
        }

        public String getDescription() {
            return Description;
        }

        public boolean getDPCO() {
            return DPCO;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public String getCategory() {
            return Category;
        }

        public String getArtCode() {
            return ArtCode;
        }

        public void setSubClassification(String subClassification) {
            SubClassification = subClassification;
        }

        public void setSubCategory(String subCategory) {
            SubCategory = subCategory;
        }

        public void setSch_Catg_Code(String sch_Catg_Code) {
            Sch_Catg_Code = sch_Catg_Code;
        }

        public void setSch_Catg(String sch_Catg) {
            Sch_Catg = sch_Catg;
        }

        public void setSI_NO(String SI_NO) {
            this.SI_NO = SI_NO;
        }

        public void setRetailSubCategoryRecID(String retailSubCategoryRecID) {
            RetailSubCategoryRecID = retailSubCategoryRecID;
        }

        public void setRetailMainCategoryRecID(String retailMainCategoryRecID) {
            RetailMainCategoryRecID = retailMainCategoryRecID;
        }

        public void setRetailCategoryRecID(String retailCategoryRecID) {
            RetailCategoryRecID = retailCategoryRecID;
        }

        public void setRackId(String rackId) {
            RackId = rackId;
        }

        public void setProductRecID(String productRecID) {
            ProductRecID = productRecID;
        }

        public void setManufactureCode(String manufactureCode) {
            ManufactureCode = manufactureCode;
        }

        public void setManufacture(String manufacture) {
            Manufacture = manufacture;
        }

        public void setHsncode_In(String hsncode_In) {
            Hsncode_In = hsncode_In;
        }

        public void setGenericName(String genericName) {
            GenericName = genericName;
        }

        public void setDiseaseType(String diseaseType) {
            DiseaseType = diseaseType;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public void setDPCO(boolean DPCO) {
            this.DPCO = DPCO;
        }

        public void setCategoryCode(String categoryCode) {
            CategoryCode = categoryCode;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public void setArtCode(String artCode) {
            ArtCode = artCode;
        }

        private GetBatchInfoRes.BatchListObj batchListObj;

        public GetBatchInfoRes.BatchListObj getBatchListObj() {
            return batchListObj;
        }

        public void setBatchListObj(GetBatchInfoRes.BatchListObj batchListObj) {
            this.batchListObj = batchListObj;
        }


        private boolean itemDelete;

        @Bindable
        public boolean isItemDelete() {
            return itemDelete;
        }

        public void setItemDelete(boolean itemDelete) {
            this.itemDelete = itemDelete;
            notifyPropertyChanged(BR.itemDelete);
        }
    }
}
