package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EPrescriptionModelClassRequest implements Serializable
{

    @SerializedName("PosExpiry")
    @Expose
    private Integer posExpiry;
    @SerializedName("StoreId")
    @Expose
    private String storeId;
    @SerializedName("TrasactionId")
    @Expose
    private String trasactionId;
    @SerializedName("CancelReasion")
    @Expose
    private String cancelReasion;
    @SerializedName("PrescriptionNo")
    @Expose
    private String prescriptionNo;
    @SerializedName("StatusId")
    @Expose
    private String statusId;
    @SerializedName("DataSourceType")
    @Expose
    private String dataSourceType;
    @SerializedName("OperationType")
    @Expose
    private String operationType;
    private final static long serialVersionUID = -1898725952715965007L;

    public Integer getPosExpiry() {
        return posExpiry;
    }

    public void setPosExpiry(Integer posExpiry) {
        this.posExpiry = posExpiry;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTrasactionId() {
        return trasactionId;
    }

    public void setTrasactionId(String trasactionId) {
        this.trasactionId = trasactionId;
    }

    public String getCancelReasion() {
        return cancelReasion;
    }

    public void setCancelReasion(String cancelReasion) {
        this.cancelReasion = cancelReasion;
    }

    public String getPrescriptionNo() {
        return prescriptionNo;
    }

    public void setPrescriptionNo(String prescriptionNo) {
        this.prescriptionNo = prescriptionNo;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

}
