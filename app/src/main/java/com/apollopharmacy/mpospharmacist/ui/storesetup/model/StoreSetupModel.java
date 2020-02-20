package com.apollopharmacy.mpospharmacist.ui.storesetup.model;

public class StoreSetupModel {

    private String macId;
    private String deviceType;
    private String deviceName;
    private String storeDate;
    private double storeLatitude;
    private double storeLongitude;

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String imeiNo) {
        this.deviceType = imeiNo;
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

    public double getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(double storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public double getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(double storeLongitude) {
        this.storeLongitude = storeLongitude;
    }
}
