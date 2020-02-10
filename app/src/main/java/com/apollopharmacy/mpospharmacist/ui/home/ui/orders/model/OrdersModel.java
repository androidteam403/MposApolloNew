package com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model;

public class OrdersModel {
    private String orderId;
    private String customerName;
    private String customerNumber;
    private String orderDate;
    private String orderAmount;
    private String orderStatus;

    public OrdersModel(String orderId, String customerName, String customerNumber, String orderDate, String orderAmount, String orderStatus) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
