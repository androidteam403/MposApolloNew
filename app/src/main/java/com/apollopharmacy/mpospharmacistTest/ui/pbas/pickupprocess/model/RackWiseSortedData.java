package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model;

import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.util.List;

public class RackWiseSortedData {

    private String rackId;
    private String rackStatus;
    private List<BoxIdModel> boxIdList;
    private GetOMSTransactionResponse getOMSTransactionResponse;
    private boolean isExpanded;

    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getRackStatus() {
        return rackStatus;
    }

    public void setRackStatus(String rackStatus) {
        this.rackStatus = rackStatus;
    }

    public List<BoxIdModel> getBoxIdList() {
        return boxIdList;
    }

    public void setBoxIdList(List<BoxIdModel> boxIdList) {
        this.boxIdList = boxIdList;
    }

    public GetOMSTransactionResponse getGetOMSTransactionResponse() {
        return getOMSTransactionResponse;
    }

    public void setGetOMSTransactionResponse(GetOMSTransactionResponse getOMSTransactionResponse) {
        this.getOMSTransactionResponse = getOMSTransactionResponse;
    }

    private int expandStatus = 0;

    public int getExpandStatus() {
        return expandStatus;
    }

    public void setExpandStatus(int expandStatus) {
        this.expandStatus = expandStatus;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public static class BoxIdModel {
        private String boxId;
        private String boxIdStatus;
        private String orderItemNo;

        private boolean isSelected;

        public String getBoxId() {
            return boxId;
        }

        public void setBoxId(String boxId) {
            this.boxId = boxId;
        }

        public String getBoxIdStatus() {
            return boxIdStatus;
        }

        public void setBoxIdStatus(String boxIdStatus) {
            this.boxIdStatus = boxIdStatus;
        }

        public String getOrderItemNo() {
            return orderItemNo;
        }

        public void setOrderItemNo(String orderItemNo) {
            this.orderItemNo = orderItemNo;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
