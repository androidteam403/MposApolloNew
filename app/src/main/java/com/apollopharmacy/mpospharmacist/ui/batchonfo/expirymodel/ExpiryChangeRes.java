package com.apollopharmacy.mpospharmacist.ui.batchonfo.expirymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

public class ExpiryChangeRes implements Serializable {
    @Expose
    @SerializedName("BatchList")
    private List<ExpiryResBatchList> BatchList;

    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;

    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;

    @Expose
    @SerializedName("Store")
    private String Store;

    @Expose
    @SerializedName("TerminalID")
    private String TerminalID;

    @Expose
    @SerializedName("UserID")
    private String UserID;

    public List<ExpiryResBatchList> getBatchList() {
        return BatchList;
    }

    public void setBatchList(List<ExpiryResBatchList> batchList) {
        BatchList = batchList;
    }

    public String getDataAreaID() {
        return DataAreaID;
    }

    public void setDataAreaID(String dataAreaID) {
        DataAreaID = dataAreaID;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        ReturnMessage = returnMessage;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
    }

    public String getTerminalID() {
        return TerminalID;
    }

    public void setTerminalID(String terminalID) {
        TerminalID = terminalID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public class ExpiryResBatchList extends Observable implements Serializable {
        @Expose
        @SerializedName("BatchNo")
        private String BatchNo;

        @Expose
        @SerializedName("CESSPerc")
        private int CESSPerc;

        @Expose
        @SerializedName("CESSTaxCode")
        private String CESSTaxCode;

        @Expose
        @SerializedName("CGSTPerc")
        private int CGSTPerc;

        @Expose
        @SerializedName("CGSTTaxCode")
        private String CGSTTaxCode;

        @Expose
        @SerializedName("ExpDate")
        private String ExpDate;

        @Expose
        @SerializedName("IGSTPerc")
        private int IGSTPerc;

        @Expose
        @SerializedName("IGSTTaxCode")
        private String IGSTTaxCode;

        @Expose
        @SerializedName("ISMRPChange")
        private boolean ISMRPChange;

        @Expose
        @SerializedName("ItemID")
        private String ItemID;

        @Expose
        @SerializedName("MRP")
        private int MRP;

        @Expose
        @SerializedName("NearByExpiry")
        private boolean NearByExpiry;

        @Expose
        @SerializedName("Price")
        private int Price;

        @Expose
        @SerializedName("Q_O_H")
        private String Q_O_H;

        @Expose
        @SerializedName("REQQTY")
        private int REQQTY;

        @Expose
        @SerializedName("SGSTPerc")
        private int SGSTPerc;

        @Expose
        @SerializedName("SGSTTaxCode")
        private String SGSTTaxCode;

        @Expose
        @SerializedName("SNO")
        private String SNO;

        @Expose
        @SerializedName("TotalTax")
        private int TotalTax;

        public String getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(String batchNo) {
            BatchNo = batchNo;
        }

        public int getCESSPerc() {
            return CESSPerc;
        }

        public void setCESSPerc(int CESSPerc) {
            this.CESSPerc = CESSPerc;
        }

        public String getCESSTaxCode() {
            return CESSTaxCode;
        }

        public void setCESSTaxCode(String CESSTaxCode) {
            this.CESSTaxCode = CESSTaxCode;
        }

        public int getCGSTPerc() {
            return CGSTPerc;
        }

        public void setCGSTPerc(int CGSTPerc) {
            this.CGSTPerc = CGSTPerc;
        }

        public String getCGSTTaxCode() {
            return CGSTTaxCode;
        }

        public void setCGSTTaxCode(String CGSTTaxCode) {
            this.CGSTTaxCode = CGSTTaxCode;
        }

        public String getExpDate() {
            return ExpDate;
        }

        public void setExpDate(String expDate) {
            ExpDate = expDate;
        }

        public int getIGSTPerc() {
            return IGSTPerc;
        }

        public void setIGSTPerc(int IGSTPerc) {
            this.IGSTPerc = IGSTPerc;
        }

        public String getIGSTTaxCode() {
            return IGSTTaxCode;
        }

        public void setIGSTTaxCode(String IGSTTaxCode) {
            this.IGSTTaxCode = IGSTTaxCode;
        }

        public boolean isISMRPChange() {
            return ISMRPChange;
        }

        public void setISMRPChange(boolean ISMRPChange) {
            this.ISMRPChange = ISMRPChange;
        }

        public String getItemID() {
            return ItemID;
        }

        public void setItemID(String itemID) {
            ItemID = itemID;
        }

        public int getMRP() {
            return MRP;
        }

        public void setMRP(int MRP) {
            this.MRP = MRP;
        }

        public boolean isNearByExpiry() {
            return NearByExpiry;
        }

        public void setNearByExpiry(boolean nearByExpiry) {
            NearByExpiry = nearByExpiry;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int price) {
            Price = price;
        }

        public String getQ_O_H() {
            return Q_O_H;
        }

        public void setQ_O_H(String q_O_H) {
            Q_O_H = q_O_H;
        }

        public int getREQQTY() {
            return REQQTY;
        }

        public void setREQQTY(int REQQTY) {
            this.REQQTY = REQQTY;
        }

        public int getSGSTPerc() {
            return SGSTPerc;
        }

        public void setSGSTPerc(int SGSTPerc) {
            this.SGSTPerc = SGSTPerc;
        }

        public String getSGSTTaxCode() {
            return SGSTTaxCode;
        }

        public void setSGSTTaxCode(String SGSTTaxCode) {
            this.SGSTTaxCode = SGSTTaxCode;
        }

        public String getSNO() {
            return SNO;
        }

        public void setSNO(String SNO) {
            this.SNO = SNO;
        }

        public int getTotalTax() {
            return TotalTax;
        }

        public void setTotalTax(int totalTax) {
            TotalTax = totalTax;
        }
    }
}
