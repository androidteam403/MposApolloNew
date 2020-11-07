package com.apollopharmacy.mpospharmacist.ui.ordersummary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PayLoadRequest {

    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @SerializedName("GSTNo")
    @Expose
    private String gSTNo;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("BillNo")
    @Expose
    private String billNo;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("MobileNumber")
    @Expose
    private Long mobileNumber;
    @SerializedName("NetPayable")
    @Expose
    private int netPayable;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("Taxes")
    @Expose
    private Taxes taxes;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getGSTNo() {
        return gSTNo;
    }

    public void setGSTNo(String gSTNo) {
        this.gSTNo = gSTNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getNetPayable() {
        return netPayable;
    }

    public void setNetPayable(int netPayable) {
        this.netPayable = netPayable;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Taxes getTaxes() {
        return taxes;
    }

    public void setTaxes(Taxes taxes) {
        this.taxes = taxes;
    }

    public static class Item {

        @SerializedName("skuname")
        @Expose
        private String skuname;
        @SerializedName("QTY")
        @Expose
        private Long qTY;
        @SerializedName("price")
        @Expose
        private Long price;
        @SerializedName("tax")
        @Expose
        private Long tax;

        public String getSkuname() {
            return skuname;
        }

        public void setSkuname(String skuname) {
            this.skuname = skuname;
        }

        public Long getQTY() {
            return qTY;
        }

        public void setQTY(Long qTY) {
            this.qTY = qTY;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public Long getTax() {
            return tax;
        }

        public void setTax(Long tax) {
            this.tax = tax;
        }

    }

    public static class Taxes {

        @SerializedName("SGST")
        @Expose
        private String sGST;
        @SerializedName("CGST")
        @Expose
        private String cGST;
        @SerializedName("IGST")
        @Expose
        private String iGST;
        @SerializedName("Taxes")
        @Expose
        private String taxes;

        public String getSGST() {
            return sGST;
        }

        public void setSGST(String sGST) {
            this.sGST = sGST;
        }

        public String getCGST() {
            return cGST;
        }

        public void setCGST(String cGST) {
            this.cGST = cGST;
        }

        public String getIGST() {
            return iGST;
        }

        public void setIGST(String iGST) {
            this.iGST = iGST;
        }

        public String getTaxes() {
            return taxes;
        }

        public void setTaxes(String taxes) {
            this.taxes = taxes;
        }

    }
}
