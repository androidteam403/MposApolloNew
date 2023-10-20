package com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetCustomerResponse {


    @Expose
    @SerializedName("_Customer")
    private List<CustomerEntity> _Customer;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    public List<CustomerEntity> get_Customer() {
        return _Customer;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public void set_Customer(List<CustomerEntity> _Customer) {
        this._Customer = _Customer;
    }

    public void setReturnMessage(String returnMessage) {
        ReturnMessage = returnMessage;
    }

    public void setRequestStatus(int requestStatus) {
        RequestStatus = requestStatus;
    }

    public static class CustomerEntity implements Serializable {
        @Expose
        @SerializedName("Tier")
        private String Tier;
        @Expose
        @SerializedName("TelephoneNo")
        private String TelephoneNo = "";
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("EmpNo")
        private String EmpNo;
        @Expose
        @SerializedName("CustId")
        private String CustId;
        @Expose
        @SerializedName("CustActiveStatus")
        private String CustActiveStatus;
        @Expose
        @SerializedName("CorpId")
        private String CorpId;

        @Expose
        @SerializedName("CardNo")
        private String CardNo = "";
        @Expose
        @SerializedName("CardName")
        private String CardName;

        @Expose
        @SerializedName("CustomerName")
        private String CustomerName;


        @Expose
        @SerializedName("AvailablePoints")
        private String AvailablePoints;

        @Expose
        @SerializedName("CPEnquiry")
        private boolean CPEnquiry;

        public boolean getCPEnquiry() {
            return CPEnquiry;
        }


        public String getTier() {
            return Tier;
        }

        public String getTelephoneNo() {
            return TelephoneNo;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public String getEmpNo() {
            return EmpNo;
        }

        public String getCustId() {
            return CustId;
        }

        public String getCustActiveStatus() {
            return CustActiveStatus;
        }

        public String getCorpId() {
            return CorpId;
        }

        public String getCardNo() {
            return CardNo;
        }

        public String getCardName() {
            return CardName;
        }

        public String getAvailablePoints() {
            return AvailablePoints;
        }

        private String searchId;

        public String getSearchId() {
            return searchId;
        }

        public void setSearchId(String searchId) {
            this.searchId = searchId;
        }

        public void setTier(String tier) {
            Tier = tier;
        }

        public void setTelephoneNo(String telephoneNo) {
            TelephoneNo = telephoneNo;
        }

        public void setMobileNo(String mobileNo) {
            MobileNo = mobileNo;
        }

        public void setEmpNo(String empNo) {
            EmpNo = empNo;
        }

        public void setCustId(String custId) {
            CustId = custId;
        }

        public void setCustActiveStatus(String custActiveStatus) {
            CustActiveStatus = custActiveStatus;
        }

        public void setCorpId(String corpId) {
            CorpId = corpId;
        }

        public void setCardNo(String cardNo) {
            CardNo = cardNo;
        }

        public void setCardName(String cardName) {
            CardName = cardName;
        }

        public void setAvailablePoints(String availablePoints) {
            AvailablePoints = availablePoints;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public void setCPEnquiry(boolean CPEnquiry) {
            this.CPEnquiry = CPEnquiry;
        }

        private String middleName = "";
        private String lastName = "";
        private String age = "";
        private String gender = "";
        private String dob = "";
        private String postalAddress = "";
        private String city = "";
        private String state = "";
        private String district = "";
        private String zipCode = "";
        private String email = "";
        private String anniversary = "";
        private String maritalStatus = "";
        private String numberOfDependents = "";
        private String dateOfRegistration = "";
        private String corporate_id = "";

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getPostalAddress() {
            return postalAddress;
        }

        public void setPostalAddress(String postalAddress) {
            this.postalAddress = postalAddress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAnniversary() {
            return anniversary;
        }

        public void setAnniversary(String anniversary) {
            this.anniversary = anniversary;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getNumberOfDependents() {
            return numberOfDependents;
        }

        public void setNumberOfDependents(String numberOfDependents) {
            this.numberOfDependents = numberOfDependents;
        }

        public String getDateOfRegistration() {
            return dateOfRegistration;
        }

        public void setDateOfRegistration(String dateOfRegistration) {
            this.dateOfRegistration = dateOfRegistration;
        }

        private boolean registeredCustomer;

        public boolean isRegisteredCustomer() {
            return registeredCustomer;
        }

        public void setRegisteredCustomer(boolean registeredCustomer) {
            this.registeredCustomer = registeredCustomer;
        }

        private boolean isExistingCustomerOrNot;

        public boolean isExistingCustomerOrNot() {
            return isExistingCustomerOrNot;
        }

        public void setExistingCustomerOrNot(boolean reqValidateOTP) {
            isExistingCustomerOrNot = reqValidateOTP;
        }

        private boolean isCardPayment;

        public boolean isCardPayment() {
            return isCardPayment;
        }

        public void setCardPayment(boolean cardPayment) {
            isCardPayment = cardPayment;
        }
    }
}
