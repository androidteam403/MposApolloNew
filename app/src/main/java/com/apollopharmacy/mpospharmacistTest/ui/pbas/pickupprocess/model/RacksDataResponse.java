package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RacksDataResponse implements Serializable {
    @SerializedName("FullfillmentDetails")
    @Expose
    private List<FullfillmentDetail> fullfillmentDetails = null;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 9015040198260701397L;

    public List<FullfillmentDetail> getFullfillmentDetails() {
        return fullfillmentDetails;
    }

    public void setFullfillmentDetails(List<FullfillmentDetail> fullfillmentDetails) {
        this.fullfillmentDetails = fullfillmentDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class FullfillmentDetail implements Serializable {

        @SerializedName("FullfillmentId")
        @Expose
        private String fullfillmentId;
        @SerializedName("TotalItems")
        @Expose
        private String totalItems;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("BoxId")
        @Expose


        private String boxId;
        @SerializedName("Products")
        @Expose
        private List<Product> products = null;
        private final static long serialVersionUID = 6412646009014127107L;

        private int expandStatus = 0;

        private boolean selectedBoxesData;

        public boolean isSelectedBoxesData() {
            return selectedBoxesData;
        }

        public void setSelectedBoxesData(boolean selectedBoxesData) {
            this.selectedBoxesData = selectedBoxesData;
        }

        public int getExpandStatus() {
            return expandStatus;
        }

        public void setExpandStatus(int expandStatus) {
            this.expandStatus = expandStatus;
        }

        public String getFullfillmentId() {
            return fullfillmentId;
        }

        public void setFullfillmentId(String fullfillmentId) {
            this.fullfillmentId = fullfillmentId;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBoxId() {
            return boxId;
        }

        public void setBoxId(String boxId) {
            this.boxId = boxId;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public static class Product implements Serializable {

            @SerializedName("ProductId")
            @Expose
            private String productId;
            @SerializedName("ProductName")
            @Expose
            private String productName;
            @SerializedName("RackId")
            @Expose
            private String rackId;
            @SerializedName("AvailableQuantity")
            @Expose
            private String availableQuantity;
            @SerializedName("RequiredQuantity")
            @Expose
            private String requiredQuantity;
            @SerializedName("CapturedQuantity")
            @Expose
            private String capturedQuantity;
            @SerializedName("BatchId")
            @Expose
            private String batchId;
            @SerializedName("Status")
            @Expose
            private String status;

            public String getItemStatus() {
                return itemStatus;
            }

            public void setItemStatus(String itemStatus) {
                this.itemStatus = itemStatus;
            }

            private String itemStatus = "";
            private final static long serialVersionUID = 6317138212188310262L;

            private int expandStatus = 0;
            private String capturedData;
            private String statusQty;

            private boolean productStatusFillingUpdate;

            private boolean finalStatusUpdate;

            private String packerStatus;

            public String getPackerStatus() {
                return packerStatus;
            }

            public void setPackerStatus(String packerStatus) {
                this.packerStatus = packerStatus;
            }

            public boolean isFinalStatusUpdate() {
                return finalStatusUpdate;
            }

            public void setFinalStatusUpdate(boolean finalStatusUpdate) {
                this.finalStatusUpdate = finalStatusUpdate;
            }

            public boolean isProductStatusFillingUpdate() {
                return productStatusFillingUpdate;
            }

            public void setProductStatusFillingUpdate(boolean productStatusFillingUpdate) {
                this.productStatusFillingUpdate = productStatusFillingUpdate;
            }

            public String getStatusQty() {
                return statusQty;
            }

            public void setStatusQty(String statusQty) {
                this.statusQty = statusQty;
            }

            public String getCapturedData() {
                return capturedData;
            }

            public void setCapturedData(String capturedData) {
                this.capturedData = capturedData;
            }

            public int getExpandStatus() {
                return expandStatus;
            }

            public void setExpandStatus(int expandStatus) {
                this.expandStatus = expandStatus;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getRackId() {
                return rackId;
            }

            public void setRackId(String rackId) {
                this.rackId = rackId;
            }

            public String getAvailableQuantity() {
                return availableQuantity;
            }

            public void setAvailableQuantity(String availableQuantity) {
                this.availableQuantity = availableQuantity;
            }

            public String getRequiredQuantity() {
                return requiredQuantity;
            }

            public void setRequiredQuantity(String requiredQuantity) {
                this.requiredQuantity = requiredQuantity;
            }

            public String getCapturedQuantity() {
                return capturedQuantity;
            }

            public void setCapturedQuantity(String capturedQuantity) {
                this.capturedQuantity = capturedQuantity;
            }

            public String getBatchId() {
                return batchId;
            }

            public void setBatchId(String batchId) {
                this.batchId = batchId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

        }

    }
}
