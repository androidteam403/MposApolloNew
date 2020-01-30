package com.apollo.pharmacy.ui.adminlogin.model;

public class SpinnerIdPojo {
    private String storeIdType;
    private String storeId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreIdType() {
        return storeIdType;
    }

    public void setStoreIdType(String storeIdType) {
        this.storeIdType = storeIdType;
    }

    @Override
    public String toString() {
        return storeIdType;
    }
}
