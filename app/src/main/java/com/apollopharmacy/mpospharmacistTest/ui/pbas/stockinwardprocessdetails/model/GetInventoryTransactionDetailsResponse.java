package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


    public class GetInventoryTransactionDetailsResponse implements Serializable
    {

        @SerializedName("InventoryData")
        @Expose
        private List<InventoryDatum> inventoryData;
        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;

        public List<InventoryDatum> getInventoryData() {
            return inventoryData;
        }

        public void setInventoryData(List<InventoryDatum> inventoryData) {
            this.inventoryData = inventoryData;
        }

        public class InventoryDatum implements Serializable
        {

            @SerializedName("DatePhysical")
            @Expose
            private String datePhysical;
            @SerializedName("FromInventSiteId")
            @Expose
            private String fromInventSiteId;
            @SerializedName("InventSiteId")
            @Expose
            private String inventSiteId;
            @SerializedName("InventoryDetails")
            @Expose
            private List<InventoryDetail> inventoryDetails;
            @SerializedName("ReferenceId")
            @Expose
            private String referenceId;
            @SerializedName("TicketId")
            @Expose
            private String ticketId;
            @SerializedName("VendGroup")
            @Expose
            private String vendGroup;

            public String getDatePhysical() {
                return datePhysical;
            }

            public void setDatePhysical(String datePhysical) {
                this.datePhysical = datePhysical;
            }

            public String getFromInventSiteId() {
                return fromInventSiteId;
            }

            public void setFromInventSiteId(String fromInventSiteId) {
                this.fromInventSiteId = fromInventSiteId;
            }

            public String getInventSiteId() {
                return inventSiteId;
            }

            public void setInventSiteId(String inventSiteId) {
                this.inventSiteId = inventSiteId;
            }

            public List<InventoryDetail> getInventoryDetails() {
                return inventoryDetails;
            }

            public void setInventoryDetails(List<InventoryDetail> inventoryDetails) {
                this.inventoryDetails = inventoryDetails;
            }

            public class InventoryDetail implements Serializable
            {

                @SerializedName("InventbatchId")
                @Expose
                private String inventbatchId;
                @SerializedName("ItemId")
                @Expose
                private String itemId;
                @SerializedName("ItemName")
                @Expose
                private String itemName;
                @SerializedName("Mrp")
                @Expose
                private double mrp;
                @SerializedName("Qty")
                @Expose
                private Integer qty;
                @SerializedName("ReferenceId")
                @Expose
                private String referenceId;
                private final static long serialVersionUID = -4447997163708677956L;

                public String getInventbatchId() {
                    return inventbatchId;
                }

                public void setInventbatchId(String inventbatchId) {
                    this.inventbatchId = inventbatchId;
                }

                public String getItemId() {
                    return itemId;
                }

                public void setItemId(String itemId) {
                    this.itemId = itemId;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public double getMrp() {
                    return mrp;
                }

                public void setMrp(Integer mrp) {
                    this.mrp = mrp;
                }

                public Integer getQty() {
                    return qty;
                }

                public void setQty(Integer qty) {
                    this.qty = qty;
                }

                public String getReferenceId() {
                    return referenceId;
                }

                public void setReferenceId(String referenceId) {
                    this.referenceId = referenceId;
                }

                public GetUniversalDropDownBindResponse.DropDownLine dropDownLineList;

                public GetUniversalDropDownBindResponse.DropDownLine getDropDownLineList() {
                    return dropDownLineList;
                }

                public void setDropDownLineList(GetUniversalDropDownBindResponse.DropDownLine dropDownLineList) {
                    this.dropDownLineList = dropDownLineList;
                }
            }

            public String getReferenceId() {
                return referenceId;
            }

            public void setReferenceId(String referenceId) {
                this.referenceId = referenceId;
            }

            public String getTicketId() {
                return ticketId;
            }

            public void setTicketId(String ticketId) {
                this.ticketId = ticketId;
            }

            public String getVendGroup() {
                return vendGroup;
            }

            public void setVendGroup(String vendGroup) {
                this.vendGroup = vendGroup;
            }




        }

        public Integer getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(Integer requestStatus) {
            this.requestStatus = requestStatus;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

    }



