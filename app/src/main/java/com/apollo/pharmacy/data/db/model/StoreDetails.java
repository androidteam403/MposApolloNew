package com.apollo.pharmacy.data.db.model;

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
    private String email;
    private String phone;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
