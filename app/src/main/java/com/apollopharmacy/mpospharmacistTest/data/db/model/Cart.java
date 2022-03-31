package com.apollopharmacy.mpospharmacistTest.data.db.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cart extends RealmObject {

    @PrimaryKey
    private int id;

    private String transactionId;
    private RealmList<CartItems> itemsArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public RealmList<CartItems> getItemsArrayList() {
        return itemsArrayList;
    }

    public void setItemsArrayList(RealmList<CartItems> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }
}
