package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public static class Items implements Serializable {

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
    }
}
