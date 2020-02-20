package com.apollopharmacy.mpospharmacist.data.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StoreDetails extends RealmObject {

    @PrimaryKey
    private int id;

    private int storeId;
    private double storeLat;
    private double storeLang;
    private String registrationDate;
    private String registeredBy;
    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public double getStoreLat() {
        return storeLat;
    }

    public void setStoreLat(double storeLatLang) {
        this.storeLat = storeLatLang;
    }

    public double getStoreLang() {
        return storeLang;
    }

    public void setStoreLang(double storeLang) {
        this.storeLang = storeLang;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
