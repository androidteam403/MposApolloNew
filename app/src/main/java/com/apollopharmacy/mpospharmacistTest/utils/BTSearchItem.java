package com.apollopharmacy.mpospharmacistTest.utils;

import java.io.Serializable;

public class BTSearchItem implements Serializable
{
    private String name = "";
    private String address = "";
    private Boolean isPaired;

    public Boolean getPaired() {
        return isPaired;
    }

    public void setPaired(Boolean paired) {
        isPaired = paired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
