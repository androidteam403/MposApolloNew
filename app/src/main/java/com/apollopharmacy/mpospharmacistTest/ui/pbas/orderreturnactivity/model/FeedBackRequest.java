package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBackRequest
{
    @SerializedName("OrderId")
    @Expose
    private String orderId;
    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("Comment")
    @Expose
    private String comment;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
