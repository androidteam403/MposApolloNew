package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class ManualDiscCheckRes {


    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestType")
    private String RequestType;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("PosSalesTransaction")
    private CalculatePosTransactionRes PosSalesTransaction;
    @Expose
    @SerializedName("OPTValidate")
    private boolean OPTValidate;
    @Expose
    @SerializedName("IsOTPRequired")
    private int IsOTPRequired;
    @Expose
    @SerializedName("IsNormalSale")
    private boolean IsNormalSale;
    @Expose
    @SerializedName("IsClearAllDiscount")
    private boolean IsClearAllDiscount;
    @Expose
    @SerializedName("IsAutoDiscount")
    private boolean IsAutoDiscount;
    @Expose
    @SerializedName("ISDiscountCodeRequired")
    private int ISDiscountCodeRequired;
    @Expose
    @SerializedName("HealingCardThresholdAmout")
    private int HealingCardThresholdAmout;
    @Expose
    @SerializedName("EprescriptionMaxDicountValue")
    private int EprescriptionMaxDicountValue;
    @Expose
    @SerializedName("EprescriptionDiscountPer")
    private int EprescriptionDiscountPer;
    @Expose
    @SerializedName("DisplayList")
    private List<DisplayList> DisplayList;
    @Expose
    @SerializedName("DiscList")
    private List<DiscList> DiscList;
    @Expose
    @SerializedName("CreditAmount")
    private int CreditAmount;
    @Expose
    @SerializedName("AvailDiscountList")
    private List<AvailableDiscList> AvailDiscountList;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public String getRequestType() {
        return RequestType;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public CalculatePosTransactionRes getPosSalesTransaction() {
        return PosSalesTransaction;
    }

    public boolean getOPTValidate() {
        return OPTValidate;
    }

    public int getIsOTPRequired() {
        return IsOTPRequired;
    }

    public boolean getIsNormalSale() {
        return IsNormalSale;
    }

    public boolean getIsClearAllDiscount() {
        return IsClearAllDiscount;
    }

    public boolean getIsAutoDiscount() {
        return IsAutoDiscount;
    }

    public int getISDiscountCodeRequired() {
        return ISDiscountCodeRequired;
    }

    public int getHealingCardThresholdAmout() {
        return HealingCardThresholdAmout;
    }

    public int getEprescriptionMaxDicountValue() {
        return EprescriptionMaxDicountValue;
    }

    public int getEprescriptionDiscountPer() {
        return EprescriptionDiscountPer;
    }

    public List<DisplayList> getDisplayList() {
        return DisplayList;
    }

    public List<DiscList> getDiscList() {
        return DiscList;
    }

    public int getCreditAmount() {
        return CreditAmount;
    }

    public List<AvailableDiscList> getAvailDiscountList() {
        return AvailDiscountList;
    }



    public static  class AvailableDiscList {

        @Expose
        @SerializedName("OfferId")
        private String OfferId;
        @Expose
        @SerializedName("IsOTPRequired")
        private int IsOTPRequired;
        @Expose
        @SerializedName("DiscountName")
        private String DiscountName;

        public String getOfferId() {
            return OfferId;
        }

        public int getIsOTPRequired() {
            return IsOTPRequired;
        }

        public String getDiscountName() {
            return DiscountName;
        }
    }

    public static  class DiscList {

        @Expose
        @SerializedName("RetailCategoryId")
        private String RetailCategoryId;
        @Expose
        @SerializedName("DiscountValue")
        private double DiscountValue;
        @Expose
        @SerializedName("CategoryCode")
        private String CategoryCode;

        public String getRetailCategoryId() {
            return RetailCategoryId;
        }

        public double getDiscountValue() {
            return DiscountValue;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }
    }

    public static  class DisplayList {

        @Expose
        @SerializedName("DiscountValue")
        private double DiscountValue;
        @Expose
        @SerializedName("CategoryDescription")
        private String CategoryDescription;
        @Expose
        @SerializedName("CategoryCode")
        private String CategoryCode;

        public double getDiscountValue() {
            return DiscountValue;
        }

        public String getCategoryDescription() {
            return CategoryDescription;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public void setDiscountValue(double discountValue) {
            DiscountValue = discountValue;
        }
    }
}
