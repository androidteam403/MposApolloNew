package com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCustomerRequest {


    @Expose
    @SerializedName("AXSearchUrl")
    private String AXSearchUrl ;
    @Expose
    @SerializedName("OneApolloSearchUrl")
    private String OneApolloSearchUrl;
    @Expose
    @SerializedName("ClusterCode")
    private String ClusterCode;
    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;
    @Expose
    @SerializedName("Store")
    private String Store;
    @Expose
    @SerializedName("ISOneApollo")
    private boolean ISOneApollo;
    @Expose
    @SerializedName("ISAX")
    private boolean ISAX;
    @Expose
    @SerializedName("SearchType")
    private int SearchType;
    @Expose
    @SerializedName("SearchString")
    private String SearchString;

    @SerializedName("Terminal")
    private String Terminal;

    @SerializedName("CPEnquiry")
    private boolean CPEnquiry;



    public String getTerminal() {
        return Terminal;
    }

    public void setTerminal(String terminal) {
        Terminal = terminal;
    }

    public String getAXSearchUrl() {
        return AXSearchUrl;
    }

    public String getOneApolloSearchUrl() {
        return OneApolloSearchUrl;
    }

    public String getClusterCode() {
        return ClusterCode;
    }

    public String getDataAreaID() {
        return DataAreaID;
    }

    public String getStore() {
        return Store;
    }

    public boolean getISOneApollo() {
        return ISOneApollo;
    }

    public boolean getISAX() {
        return ISAX;
    }
    public boolean getCPEnquiry() {
        return CPEnquiry;
    }

    public int getSearchType() {
        return SearchType;
    }

    public String getSearchString() {
        return SearchString;
    }

    public void setAXSearchUrl(String AXSearchUrl) {
        this.AXSearchUrl = AXSearchUrl;
    }

    public void setOneApolloSearchUrl(String oneApolloSearchUrl) {
        OneApolloSearchUrl = oneApolloSearchUrl;
    }

    public void setClusterCode(String clusterCode) {
        ClusterCode = clusterCode;
    }

    public void setDataAreaID(String dataAreaID) {
        DataAreaID = dataAreaID;
    }

    public void setStore(String store) {
        Store = store;
    }

    public void setISOneApollo(boolean ISOneApollo) {
        this.ISOneApollo = ISOneApollo;
    }

    public void setISAX(boolean ISAX) {
        this.ISAX = ISAX;
    }

    public void setCPEnquiry(boolean CPEnquiry) {
        this.CPEnquiry = CPEnquiry;
    }

    public void setSearchType(int searchType) {
        SearchType = searchType;
    }

    public void setSearchString(String searchString) {
        SearchString = searchString;
    }
}
