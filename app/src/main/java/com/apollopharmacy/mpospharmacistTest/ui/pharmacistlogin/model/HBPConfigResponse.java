package com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class HBPConfigResponse implements Serializable {


    @Expose
    @SerializedName("UHIDBilling")
    private boolean UHIDBilling;
    @Expose
    @SerializedName("PatientTypeMaster")
    private boolean PatientTypeMaster;
    @Expose
    @SerializedName("PLPromoOffer")
    private boolean PLPromoOffer;
    @Expose
    @SerializedName("PLPromoCorpCode")
    private String PLPromoCorpCode;
    @Expose
    @SerializedName("PLPromoAmount")
    private int PLPromoAmount;
    @Expose
    @SerializedName("ISValidatePassword")
    private boolean ISValidatePassword;
    @Expose
    @SerializedName("ISTokenSystem")
    private boolean ISTokenSystem;
    @Expose
    @SerializedName("ISTerminalCashDrawer")
    private boolean ISTerminalCashDrawer;
    @Expose
    @SerializedName("ISTerminalCardPayment")
    private boolean ISTerminalCardPayment;
    @Expose
    @SerializedName("ISTerminalCardBilling")
    private boolean ISTerminalCardBilling;
    @Expose
    @SerializedName("ISSVPStore")
    private boolean ISSVPStore;
    @Expose
    @SerializedName("ISSOMSCustomerEnable")
    private boolean ISSOMSCustomerEnable;
    @Expose
    @SerializedName("ISPatientRequired")
    private boolean ISPatientRequired;
    @Expose
    @SerializedName("ISNormalSalesTrackingRefAllowed")
    private boolean ISNormalSalesTrackingRefAllowed;
    @Expose
    @SerializedName("ISAutoNewCustRegAllowed")
    private boolean ISAutoNewCustRegAllowed;
    @Expose
    @SerializedName("BillReturnReason")
    private boolean BillReturnReason;
    @Expose
    @SerializedName("BillReturnOTPRequired")
    private boolean BillReturnOTPRequired;
    @Expose
    @SerializedName("BillReturnOTPMobileNo")
    private String BillReturnOTPMobileNo;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;

    public boolean getUHIDBilling() {
        return UHIDBilling;
    }

    public boolean getPatientTypeMaster() {
        return PatientTypeMaster;
    }

    public boolean getPLPromoOffer() {
        return PLPromoOffer;
    }

    public String getPLPromoCorpCode() {
        return PLPromoCorpCode;
    }

    public int getPLPromoAmount() {
        return PLPromoAmount;
    }

    public boolean getISValidatePassword() {
        return ISValidatePassword;
    }

    public boolean getISTokenSystem() {
        return ISTokenSystem;
    }

    public boolean getISTerminalCashDrawer() {
        return ISTerminalCashDrawer;
    }

    public boolean getISTerminalCardPayment() {
        return ISTerminalCardPayment;
    }

    public boolean getISTerminalCardBilling() {
        return ISTerminalCardBilling;
    }

    public boolean getISSVPStore() {
        return ISSVPStore;
    }

    public boolean getISSOMSCustomerEnable() {
        return ISSOMSCustomerEnable;
    }

    public boolean getISPatientRequired() {
        return ISPatientRequired;
    }

    public boolean getISNormalSalesTrackingRefAllowed() {
        return ISNormalSalesTrackingRefAllowed;
    }

    public boolean getISAutoNewCustRegAllowed() {
        return ISAutoNewCustRegAllowed;
    }

    public boolean getBillReturnReason() {
        return BillReturnReason;
    }

    public boolean getBillReturnOTPRequired() {
        return BillReturnOTPRequired;
    }

    public String getBillReturnOTPMobileNo() {
        return BillReturnOTPMobileNo;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

}
