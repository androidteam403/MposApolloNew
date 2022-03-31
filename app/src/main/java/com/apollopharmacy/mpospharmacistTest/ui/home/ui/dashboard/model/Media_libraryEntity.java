package com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Media_libraryEntity {
    @Expose
    @SerializedName("file")
    private List<FileEntity> file;
    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("media_type")
    private Media_typeEntity media_type;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("uid")
    private String uid;

    public List<FileEntity> getFile() {
        return file;
    }

    public boolean getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Media_typeEntity getMedia_type() {
        return media_type;
    }

    public String getDescription() {
        return description;
    }

    public String getUid() {
        return uid;
    }

    public void setFile(List<FileEntity> file) {
        this.file = file;
    }
}
