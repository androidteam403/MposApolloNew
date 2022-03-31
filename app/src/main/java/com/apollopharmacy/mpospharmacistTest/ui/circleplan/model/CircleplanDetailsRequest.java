package com.apollopharmacy.mpospharmacistTest.ui.circleplan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CircleplanDetailsRequest {
    @SerializedName("CORPCODE")
    @Expose
    private String corpcode;
    @SerializedName("ITEMID")
    @Expose
    private String itemid;
    @SerializedName("PICNAME")
    @Expose
    private String picname;
    @SerializedName("Siteid")
    @Expose
    private String siteid;
    @SerializedName("TransactionID")
    @Expose
    private String transactionID;
    @SerializedName("TransactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("Actionid")
    @Expose
    private Integer actionid;
    @SerializedName("ActionType")
    @Expose
    private String actionType;
    @SerializedName("TransAmt")
    @Expose
    private Integer transAmt;
    @SerializedName("OtherPayment")
    @Expose
    private Integer otherPayment;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("Flag")
    @Expose
    private Integer flag;
    @SerializedName("PaymentType")
    @Expose
    private String paymentType;
    @SerializedName("Card_Desc")
    @Expose
    private String cardDesc;
    @SerializedName("PaymentCode")
    @Expose
    private Integer paymentCode;
    @SerializedName("Upload")
    @Expose
    private String upload;
    @SerializedName("Uploaded")
    @Expose
    private String uploaded;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("Channel")
    @Expose
    private Integer channel;
    @SerializedName("Counter")
    @Expose
    private Integer counter;
    @SerializedName("LineNum")
    @Expose
    private Integer lineNum;
    @SerializedName("Replicated")
    @Expose
    private Integer replicated;
    @SerializedName("StaffId")
    @Expose
    private String staffId;
    @SerializedName("TerminalId")
    @Expose
    private String terminalId;
    @SerializedName("CreatedDateTime")
    @Expose
    private String createdDateTime;
    @SerializedName("DataAreaID")
    @Expose
    private String dataAreaID;
    @SerializedName("CIRCLEID")
    @Expose
    private String circleid;
    @SerializedName("CIRCLEPLAN")
    @Expose
    private String circleplan;
    @SerializedName("CIRCLEPRICE")
    @Expose
    private String circleprice;
    @SerializedName("CIRCLEBENIFITS")
    @Expose
    private String circlebenifits;
    @SerializedName("STARTDATE")
    @Expose
    private String startdate;
    @SerializedName("ENDDATE")
    @Expose
    private String enddate;

    public String getCorpcode() {
        return corpcode;
    }

    public void setCorpcode(String corpcode) {
        this.corpcode = corpcode;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getActionid() {
        return actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Integer getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Integer transAmt) {
        this.transAmt = transAmt;
    }

    public Integer getOtherPayment() {
        return otherPayment;
    }

    public void setOtherPayment(Integer otherPayment) {
        this.otherPayment = otherPayment;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public Integer getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(Integer paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public Integer getReplicated() {
        return replicated;
    }

    public void setReplicated(Integer replicated) {
        this.replicated = replicated;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getDataAreaID() {
        return dataAreaID;
    }

    public void setDataAreaID(String dataAreaID) {
        this.dataAreaID = dataAreaID;
    }

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getCircleplan() {
        return circleplan;
    }

    public void setCircleplan(String circleplan) {
        this.circleplan = circleplan;
    }

    public String getCircleprice() {
        return circleprice;
    }

    public void setCircleprice(String circleprice) {
        this.circleprice = circleprice;
    }

    public String getCirclebenifits() {
        return circlebenifits;
    }

    public void setCirclebenifits(String circlebenifits) {
        this.circlebenifits = circlebenifits;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
