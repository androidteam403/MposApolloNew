package com.apollopharmacy.mpospharmacist.ui.dashboard.model;

public class PharmaPojo {
    private String dashboardSno;
    private String artCode;
    private String dashboardDescription;
    private String dashboardQty;
    private String dashboardCat;
    private String dashboardManuf;
    private String batchNo;
    private String expiryDate;
    private String mrpRemi;
    private String dashboardTax;
    private String taxValue;
    private String dashboardTotal;

    public PharmaPojo(String dashboardSno, String artCode, String dashboardDescription, String dashboardQty, String dashboardCat, String dashboardManuf,
                      String batchNo, String expiryDate, String mrpRemi, String dashboardTax, String taxValue, String dashboardTotal) {
        this.dashboardSno = dashboardSno;
        this.artCode = artCode;
        this.dashboardDescription = dashboardDescription;
        this.dashboardQty = dashboardQty;
        this.dashboardCat = dashboardCat;
        this.dashboardManuf = dashboardManuf;
        this.batchNo = batchNo;
        this.expiryDate = expiryDate;
        this.mrpRemi = mrpRemi;
        this.dashboardTax = dashboardTax;
        this.taxValue = taxValue;
        this.dashboardTotal = dashboardTotal;
    }

    public String getDashboardSno() {
        return dashboardSno;
    }

    public void setDashboardSno(String dashboardSno) {
        this.dashboardSno = dashboardSno;
    }

    public String getArtCode() {
        return artCode;
    }

    public void setArtCode(String artCode) {
        this.artCode = artCode;
    }

    public String getDashboardDescription() {
        return dashboardDescription;
    }

    public void setDashboardDescription(String dashboardDescription) {
        this.dashboardDescription = dashboardDescription;
    }

    public String getDashboardQty() {
        return dashboardQty;
    }

    public void setDashboardQty(String dashboardQty) {
        this.dashboardQty = dashboardQty;
    }

    public String getDashboardCat() {
        return dashboardCat;
    }

    public void setDashboardCat(String dashboardCat) {
        this.dashboardCat = dashboardCat;
    }

    public String getDashboardManuf() {
        return dashboardManuf;
    }

    public void setDashboardManuf(String dashboardManuf) {
        this.dashboardManuf = dashboardManuf;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMrpRemi() {
        return mrpRemi;
    }

    public void setMrpRemi(String mrpRemi) {
        this.mrpRemi = mrpRemi;
    }

    public String getDashboardTax() {
        return dashboardTax;
    }

    public void setDashboardTax(String dashboardTax) {
        this.dashboardTax = dashboardTax;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    public String getDashboardTotal() {
        return dashboardTotal;
    }

    public void setDashboardTotal(String dashboardTotal) {
        this.dashboardTotal = dashboardTotal;
    }
}