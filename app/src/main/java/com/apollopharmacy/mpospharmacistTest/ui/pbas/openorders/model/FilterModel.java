package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model;

public class FilterModel {
    private String name;
    private boolean isSelected;

    public FilterModel() {

    }

    public FilterModel(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setName(Boolean pickupStatus) {
    }
}
