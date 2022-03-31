package com.apollopharmacy.mpospharmacistTest.ui.additem.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PickPackReservation
{

    @SerializedName("PickupFullfillmentId")
    @Expose
    private String pickupFullfillmentId;
    @SerializedName("PickupInventBatchId")
    @Expose
    private String pickupInventBatchId;
    @SerializedName("PickupItemId")
    @Expose
    private String pickupItemId;
    @SerializedName("PickupPhysicalInventBatchId")
    @Expose
    private String pickupPhysicalInventBatchId;
    @SerializedName("PickupQty")
    @Expose
    private double pickupQty;

    private boolean isBatchupdated;

    @SerializedName("Price")
    @Expose
    private double Price;

    @SerializedName("Expiry")
    @Expose
    private String Expiry;

    @SerializedName("TaxCode")
    @Expose
    private String TaxCode;

    public  double getPrice()
    {
        return Price;
    }

    public void  setPrice(double price)
    {
        this.Price=price;
    }

    public String getExpiry()
    {
        return  Expiry;
    }

    public  void setExpiry(String expiry)
    {
        this.Expiry=expiry;
    }

    public  String getTaxCode()
    {
        return TaxCode;
    }

    public  void setTaxCode(String taxCode)
    {
        this.TaxCode=taxCode;
    }

    public boolean getisBatchupdated() {
        return isBatchupdated;
    }

    public void setisBatchupdated(boolean isBatchupdated1) {
        this.isBatchupdated = isBatchupdated1;
    }

    public String getPickupFullfillmentId() {
        return pickupFullfillmentId;
    }

    public void setPickupFullfillmentId(String pickupFullfillmentId) {
        this.pickupFullfillmentId = pickupFullfillmentId;
    }

    public String getPickupInventBatchId() {
        return pickupInventBatchId;
    }

    public void setPickupInventBatchId(String pickupInventBatchId) {
        this.pickupInventBatchId = pickupInventBatchId;
    }

    public String getPickupItemId() {
        return pickupItemId;
    }

    public void setPickupItemId(String pickupItemId) {
        this.pickupItemId = pickupItemId;
    }

    public String getPickupPhysicalInventBatchId() {
        return pickupPhysicalInventBatchId;
    }

    public void setPickupPhysicalInventBatchId(String pickupPhysicalInventBatchId) {
        this.pickupPhysicalInventBatchId = pickupPhysicalInventBatchId;
    }

    public double getPickupQty() {
        return pickupQty;
    }

    public void setPickupQty(double pickupQty) {
        this.pickupQty = pickupQty;
    }
}
