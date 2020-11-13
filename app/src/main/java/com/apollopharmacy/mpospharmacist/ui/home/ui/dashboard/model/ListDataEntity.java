package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListDataEntity {
    @Expose
    @SerializedName("aggregation")
    private String aggregation;
    @Expose
    @SerializedName("pivotData")
    private String pivotData;
    @Expose
    @SerializedName("zc_extra")
    private String zc_extra;
    @Expose
    @SerializedName("size")
    private int size;
    @Expose
    @SerializedName("rows")
    private List<RowsEntity> rows;
    @Expose
    @SerializedName("page")
    private int page;
    @Expose
    @SerializedName("total")
    private int total;
    @Expose
    @SerializedName("select")
    private boolean select;
    @Expose
    @SerializedName("records")
    private String records;

    public int getSize() {
        return size;
    }

    public List<RowsEntity> getRows() {
        return rows;
    }

    public int getPage() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public boolean getSelect() {
        return select;
    }

    public String getRecords() {
        return records;
    }
}
