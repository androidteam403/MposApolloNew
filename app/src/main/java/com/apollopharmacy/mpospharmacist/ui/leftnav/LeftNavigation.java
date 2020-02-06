package com.apollopharmacy.mpospharmacist.ui.leftnav;

public class LeftNavigation {

    private String newCashier;
    private String storeCode;
    private String storeName;
    private String storeLocation;
    private String terminalId;

    public LeftNavigation(String newCashier, String storeCode, String storeName, String storeLocation, String terminalId) {
        this.newCashier = newCashier;
        this.storeCode = storeCode;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.terminalId = terminalId;
    }

    public String getNewCashier() {
        return newCashier;
    }

    public void setNewCashier(String newCashier) {
        this.newCashier = newCashier;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
}
