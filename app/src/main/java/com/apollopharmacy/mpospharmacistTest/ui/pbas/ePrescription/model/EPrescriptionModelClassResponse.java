package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EPrescriptionModelClassResponse implements Serializable
    {

        @SerializedName("Addr")
        @Expose
        private String addr;
        @SerializedName("Age")
        @Expose
        private String age;
        @SerializedName("AtmNo")
        @Expose
        private String atmNo;
        @SerializedName("COMMENTS")
        @Expose
        private String comments;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("CorpCode")
        @Expose
        private String corpCode;
        @SerializedName("DataSourceType")
        @Expose
        private String dataSourceType;
        @SerializedName("DoctorConCode")
        @Expose
        private String doctorConCode;
        @SerializedName("DoctorContact")
        @Expose
        private String doctorContact;
        @SerializedName("DoctorName")
        @Expose
        private String doctorName;
        @SerializedName("DownDate")
        @Expose
        private String downDate;
        @SerializedName("Flag")
        @Expose
        private String flag;
        @SerializedName("ISAddItem")
        @Expose
        private Boolean iSAddItem;
        @SerializedName("ISDecreaseQty")
        @Expose
        private Boolean iSDecreaseQty;
        @SerializedName("ISPreEnqiryRequired")
        @Expose
        private Boolean iSPreEnqiryRequired;
        @SerializedName("ISRemoveLine")
        @Expose
        private Boolean iSRemoveLine;
        @SerializedName("ISSUBSTITUTE")
        @Expose
        private String issubstitute;
        @SerializedName("IsPrescriptionImage")
        @Expose
        private String isPrescriptionImage;
        @SerializedName("IsSTKFulFillment")
        @Expose
        private Boolean isSTKFulFillment;
        @SerializedName("Name1")
        @Expose
        private String name1;
        @SerializedName("ORDERBILLVALUE")
        @Expose
        private Double orderbillvalue;
        @SerializedName("OnlineOfferID")
        @Expose
        private String onlineOfferID;
        @SerializedName("OrderType")
        @Expose
        private String orderType;
        @SerializedName("PNo")
        @Expose
        private String pNo;
        @SerializedName("PatientName")
        @Expose
        private String patientName;
        @SerializedName("PhoneNo")
        @Expose
        private String phoneNo;
        @SerializedName("PinCode")
        @Expose
        private String pinCode;
        @SerializedName("PresDate")
        @Expose
        private String presDate;
        @SerializedName("PrescriptionLine")
        @Expose
        private List<Object> prescriptionLine;
        @SerializedName("PrescriptionNo")
        @Expose
        private String prescriptionNo;
        @SerializedName("PrescriptionURL")
        @Expose
        private String prescriptionURL;
        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;
        @SerializedName("SHIPPINGMETHOD")
        @Expose
        private String shippingmethod;
        @SerializedName("SearchFilter")
        @Expose
        private String searchFilter;
        @SerializedName("Sex")
        @Expose
        private String sex;
        @SerializedName("ShopId")
        @Expose
        private String shopId;
        @SerializedName("ShopName")
        @Expose
        private String shopName;
        @SerializedName("StateCode")
        @Expose
        private String stateCode;
        @SerializedName("TAT")
        @Expose
        private String tat;
        @SerializedName("TimeLine")
        @Expose
        private String timeLine;
        private final static long serialVersionUID = 4129414992645813773L;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAtmNo() {
            return atmNo;
        }

        public void setAtmNo(String atmNo) {
            this.atmNo = atmNo;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCorpCode() {
            return corpCode;
        }

        public void setCorpCode(String corpCode) {
            this.corpCode = corpCode;
        }

        public String getDataSourceType() {
            return dataSourceType;
        }

        public void setDataSourceType(String dataSourceType) {
            this.dataSourceType = dataSourceType;
        }

        public String getDoctorConCode() {
            return doctorConCode;
        }

        public void setDoctorConCode(String doctorConCode) {
            this.doctorConCode = doctorConCode;
        }

        public String getDoctorContact() {
            return doctorContact;
        }

        public void setDoctorContact(String doctorContact) {
            this.doctorContact = doctorContact;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getDownDate() {
            return downDate;
        }

        public void setDownDate(String downDate) {
            this.downDate = downDate;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public Boolean getISAddItem() {
            return iSAddItem;
        }

        public void setISAddItem(Boolean iSAddItem) {
            this.iSAddItem = iSAddItem;
        }

        public Boolean getISDecreaseQty() {
            return iSDecreaseQty;
        }

        public void setISDecreaseQty(Boolean iSDecreaseQty) {
            this.iSDecreaseQty = iSDecreaseQty;
        }

        public Boolean getISPreEnqiryRequired() {
            return iSPreEnqiryRequired;
        }

        public void setISPreEnqiryRequired(Boolean iSPreEnqiryRequired) {
            this.iSPreEnqiryRequired = iSPreEnqiryRequired;
        }

        public Boolean getISRemoveLine() {
            return iSRemoveLine;
        }

        public void setISRemoveLine(Boolean iSRemoveLine) {
            this.iSRemoveLine = iSRemoveLine;
        }

        public String getIssubstitute() {
            return issubstitute;
        }

        public void setIssubstitute(String issubstitute) {
            this.issubstitute = issubstitute;
        }

        public String getIsPrescriptionImage() {
            return isPrescriptionImage;
        }

        public void setIsPrescriptionImage(String isPrescriptionImage) {
            this.isPrescriptionImage = isPrescriptionImage;
        }

        public Boolean getIsSTKFulFillment() {
            return isSTKFulFillment;
        }

        public void setIsSTKFulFillment(Boolean isSTKFulFillment) {
            this.isSTKFulFillment = isSTKFulFillment;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public Double getOrderbillvalue() {
            return orderbillvalue;
        }

        public void setOrderbillvalue(Double orderbillvalue) {
            this.orderbillvalue = orderbillvalue;
        }

        public String getOnlineOfferID() {
            return onlineOfferID;
        }

        public void setOnlineOfferID(String onlineOfferID) {
            this.onlineOfferID = onlineOfferID;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPNo() {
            return pNo;
        }

        public void setPNo(String pNo) {
            this.pNo = pNo;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getPresDate() {
            return presDate;
        }

        public void setPresDate(String presDate) {
            this.presDate = presDate;
        }

        public List<Object> getPrescriptionLine()   {
            return prescriptionLine;
        }

        public void setPrescriptionLine(List<Object> prescriptionLine) {
            this.prescriptionLine = prescriptionLine;
        }

        public String getPrescriptionNo() {
            return prescriptionNo;
        }

        public void setPrescriptionNo(String prescriptionNo) {
            this.prescriptionNo = prescriptionNo;
        }

        public String getPrescriptionURL() {
            return prescriptionURL;
        }

        public void setPrescriptionURL(String prescriptionURL) {
            this.prescriptionURL = prescriptionURL;
        }

        public Integer getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(Integer requestStatus) {
            this.requestStatus = requestStatus;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public String getShippingmethod() {
            return shippingmethod;
        }

        public void setShippingmethod(String shippingmethod) {
            this.shippingmethod = shippingmethod;
        }

        public String getSearchFilter() {
            return searchFilter;
        }

        public void setSearchFilter(String searchFilter) {
            this.searchFilter = searchFilter;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getTat() {
            return tat;
        }

        public void setTat(String tat) {
            this.tat = tat;
        }

        public String getTimeLine() {
            return timeLine;
        }

        public void setTimeLine(String timeLine) {
            this.timeLine = timeLine;
        }

    }

