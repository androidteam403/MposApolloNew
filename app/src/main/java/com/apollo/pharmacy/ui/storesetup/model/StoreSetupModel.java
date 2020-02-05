package com.apollo.pharmacy.ui.storesetup.model;

public class StoreSetupModel {

    private String macId;
    private String imeiNo;
    private String deviceName;
    private String storeDate;
    private String storeLattitude;
    private String storeLongitude;

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(String storeDate) {
        this.storeDate = storeDate;
    }

    public String getStoreLattitude() {
        return storeLattitude;
    }

    public void setStoreLattitude(String storeLattitude) {
        this.storeLattitude = storeLattitude;
    }

    public String getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(String storeLongitude) {
        this.storeLongitude = storeLongitude;
    }
}
