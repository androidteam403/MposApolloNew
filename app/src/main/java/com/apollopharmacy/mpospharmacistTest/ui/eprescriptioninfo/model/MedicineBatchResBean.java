package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MedicineBatchResBean {
    @Expose
    @SerializedName("lstPhysicalBatch")
    private ArrayList<LstPhysicalBatchEntity> lstPhysicalBatch;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    public ArrayList<LstPhysicalBatchEntity> getLstPhysicalBatch() {
        return lstPhysicalBatch;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public static class LstPhysicalBatchEntity {
        @Expose
        @SerializedName("PhysicalItemID")
        private String PhysicalItemID;
        @Expose
        @SerializedName("PhysicalExpDate")
        private String PhysicalExpDate;
        @Expose
        @SerializedName("PhysicalBatchID")
        private String PhysicalBatchID;
        @Expose
        @SerializedName("MRP")
        private int MRP;

        public String getPhysicalItemID() {
            return PhysicalItemID;
        }

        public String getPhysicalExpDate() {
            return PhysicalExpDate;
        }

        public String getPhysicalBatchID() {
            return PhysicalBatchID;
        }

        public int getMRP() {
            return MRP;
        }
    }
}
