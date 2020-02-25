package com.apollopharmacy.mpospharmacist.ui.addcustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCustomerReqModel {
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("CustomerCreationURL")
    private String CustomerCreationURL;
    @Expose
    @SerializedName("AXPassword")
    private String AXPassword;
    @Expose
    @SerializedName("AXUserId")
    private String AXUserId;
    @Expose
    @SerializedName("AXDomain")
    private String AXDomain;
    @Expose
    @SerializedName("StoreId")
    private String StoreId;
    @Expose
    @SerializedName("CustId")
    private String CustId;
    @Expose
    @SerializedName("CorpId")
    private String CorpId;
    @Expose
    @SerializedName("RegistrationDate")
    private String RegistrationDate;
    @Expose
    @SerializedName("CardNumber")
    private String CardNumber;
    @Expose
    @SerializedName("DependentsNo")
    private int DependentsNo;
    @Expose
    @SerializedName("MaritalStatus")
    private String MaritalStatus;
    @Expose
    @SerializedName("Gender")
    private String Gender;
    @Expose
    @SerializedName("Age")
    private int Age;
    @Expose
    @SerializedName("DOA")
    private String DOA;
    @Expose
    @SerializedName("DOB")
    private String DOB;
    @Expose
    @SerializedName("Mobile")
    private String Mobile;
    @Expose
    @SerializedName("DataAreaId")
    private String DataAreaId;
    @Expose
    @SerializedName("Telephone")
    private String Telephone;
    @Expose
    @SerializedName("Email")
    private String Email;
    @Expose
    @SerializedName("ZipCode")
    private String ZipCode;
    @Expose
    @SerializedName("District")
    private String District;
    @Expose
    @SerializedName("State")
    private String State;
    @Expose
    @SerializedName("City")
    private String City;
    @Expose
    @SerializedName("PostalAddress")
    private String PostalAddress;
    @Expose
    @SerializedName("LastName")
    private String LastName;
    @Expose
    @SerializedName("MiddleName")
    private String MiddleName;
    @Expose
    @SerializedName("FirstName")
    private String FirstName;

    public void setReturnMessage(String ReturnMessage) {
        this.ReturnMessage = ReturnMessage;
    }

    public void setRequestStatus(int RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public void setCustomerCreationURL(String CustomerCreationURL) {
        this.CustomerCreationURL = CustomerCreationURL;
    }

    public void setAXPassword(String AXPassword) {
        this.AXPassword = AXPassword;
    }

    public void setAXUserId(String AXUserId) {
        this.AXUserId = AXUserId;
    }

    public void setAXDomain(String AXDomain) {
        this.AXDomain = AXDomain;
    }

    public void setStoreId(String StoreId) {
        this.StoreId = StoreId;
    }

    public void setCustId(String CustId) {
        this.CustId = CustId;
    }

    public void setCorpId(String CorpId) {
        this.CorpId = CorpId;
    }

    public void setRegistrationDate(String RegistrationDate) {
        this.RegistrationDate = RegistrationDate;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    public void setDependentsNo(int DependentsNo) {
        this.DependentsNo = DependentsNo;
    }

    public void setMaritalStatus(String MaritalStatus) {
        this.MaritalStatus = MaritalStatus;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public void setDOA(String DOA) {
        this.DOA = DOA;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public void setDataAreaId(String DataAreaId) {
        this.DataAreaId = DataAreaId;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public void setState(String State) {
        this.State = State;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setPostalAddress(String PostalAddress) {
        this.PostalAddress = PostalAddress;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setMiddleName(String MiddleName) {
        this.MiddleName = MiddleName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
}
