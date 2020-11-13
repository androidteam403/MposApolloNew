package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileEntity {
    @Expose
    @SerializedName("path")
    private String path;
    @Expose
    @SerializedName("contentType")
    private String contentType;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("saved")
    private boolean saved;
    @Expose
    @SerializedName("size")
    private int size;

    public String getPath() {
        return path;
    }

    public String getContentType() {
        return contentType;
    }

    public String getName() {
        return name;
    }

    public boolean getSaved() {
        return saved;
    }

    public int getSize() {
        return size;
    }
}
