package com.apollopharmacy.mpospharmacist.ui.addcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCustomerResModel {
    @Expose
    @SerializedName("ZipCode")
    private String ZipCode;
    @Expose
    @SerializedName("Telephone")
    private String Telephone;
    @Expose
    @SerializedName("StoreId")
    private String StoreId;
    @Expose
    @SerializedName("State")
    private String State;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("RegistrationDate")
    private String RegistrationDate;
    @Expose
    @SerializedName("PostalAddress")
    private String PostalAddress;
    @Expose
    @SerializedName("Mobile")
    private String Mobile;
    @Expose
    @SerializedName("MiddleName")
    private String MiddleName;
    @Expose
    @SerializedName("MaritalStatus")
    private String MaritalStatus;
    @Expose
    @SerializedName("LastName")
    private String LastName;
    @Expose
    @SerializedName("Gender")
    private String Gender;
    @Expose
    @SerializedName("FirstName")
    private String FirstName;
    @Expose
    @SerializedName("Email")
    private String Email;
    @Expose
    @SerializedName("District")
    private String District;
    @Expose
    @SerializedName("DependentsNo")
    private int DependentsNo;
    @Expose
    @SerializedName("DataAreaId")
    private String DataAreaId;
    @Expose
    @SerializedName("DOB")
    private String DOB;
    @Expose
    @SerializedName("DOA")
    private String DOA;
    @Expose
    @SerializedName("CustomerCreationURL")
    private String CustomerCreationURL;
    @Expose
    @SerializedName("CustId")
    private String CustId;
    @Expose
    @SerializedName("CorpId")
    private String CorpId;
    @Expose
    @SerializedName("City")
    private String City;
    @Expose
    @SerializedName("CardNumber")
    private String CardNumber;
    @Expose
    @SerializedName("Age")
    private int Age;
    @Expose
    @SerializedName("AXUserId")
    private String AXUserId;
    @Expose
    @SerializedName("AXPassword")
    private String AXPassword;
    @Expose
    @SerializedName("AXDomain")
    private String AXDomain;

    public String getZipCode() {
        return ZipCode;
    }

    public String getTelephone() {
        return Telephone;
    }

    public String getStoreId() {
        return StoreId;
    }

    public String getState() {
        return State;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

    public String getPostalAddress() {
        return PostalAddress;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public String getLastName() {
        return LastName;
    }

    public String getGender() {
        return Gender;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getEmail() {
        return Email;
    }

    public String getDistrict() {
        return District;
    }

    public int getDependentsNo() {
        return DependentsNo;
    }

    public String getDataAreaId() {
        return DataAreaId;
    }

    public String getDOB() {
        return DOB;
    }

    public String getDOA() {
        return DOA;
    }

    public String getCustomerCreationURL() {
        return CustomerCreationURL;
    }

    public String getCustId() {
        return CustId;
    }

    public String getCorpId() {
        return CorpId;
    }

    public String getCity() {
        return City;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public int getAge() {
        return Age;
    }

    public String getAXUserId() {
        return AXUserId;
    }

    public String getAXPassword() {
        return AXPassword;
    }

    public String getAXDomain() {
        return AXDomain;
    }
}
