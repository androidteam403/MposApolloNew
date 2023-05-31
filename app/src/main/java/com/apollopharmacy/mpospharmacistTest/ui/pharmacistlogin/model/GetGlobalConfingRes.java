package com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetGlobalConfingRes {
    //
    @Expose
    @SerializedName("OMSOrderDeliveryItemId")
    private List<String> oMSOrderDeliveryItemId;

    public List<String> getoMSOrderDeliveryItemId() {
        return oMSOrderDeliveryItemId;
    }

    public void setoMSOrderDeliveryItemId(List<String> oMSOrderDeliveryItemId) {
        this.oMSOrderDeliveryItemId = oMSOrderDeliveryItemId;
    }

    @Expose
    @SerializedName("SuspendDeleteDays")
    private int SuspendDeleteDays;
    @Expose
    @SerializedName("StoreName")
    private String StoreName;
    @Expose
    @SerializedName("StoreID")
    private String StoreID;
    @Expose
    @SerializedName("StateCode")
    private String StateCode;
    @Expose
    @SerializedName("STOCKURL")
    private String STOCKURL;
    @Expose
    @SerializedName("SMSAPI")
    private String SMSAPI;
    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("RemoveAddTender")
    private String RemoveAddTender;
    @Expose
    @SerializedName("Region")
    private String Region;
    @Expose
    @SerializedName("PriceChangeURL")
    private String PriceChangeURL;
    @Expose
    @SerializedName("PlutusWIFIUrl")
    private String PlutusWIFIUrl;
    @Expose
    @SerializedName("PasswordExpiry")
    private int PasswordExpiry;
    @Expose
    @SerializedName("OnlineOrderURL")
    private String OnlineOrderURL;
    @Expose
    @SerializedName("OnlineGetItemURL")
    private String OnlineGetItemURL;
    @Expose
    @SerializedName("OnlineGetBatchURL")
    private String OnlineGetBatchURL;
    @Expose
    @SerializedName("OnlineCancelUpdateURL")
    private String OnlineCancelUpdateURL;
    @Expose
    @SerializedName("OneApolloURL")
    private String OneApolloURL;
    @Expose
    @SerializedName("OTPURL")
    private String OTPURL;
    @Expose
    @SerializedName("OPReturnsOTPRequired")
    private boolean OPReturnsOTPRequired;
    @Expose
    @SerializedName("OMSUrl")
    private String OMSUrl;
    @Expose
    @SerializedName("NormalSale")
    private String NormalSale;
    @Expose
    @SerializedName("ManualBillingAllowHours")
    private int ManualBillingAllowHours;
    @Expose
    @SerializedName("LooseDamagSite")
    private String LooseDamagSite;
    @Expose
    @SerializedName("MPOSMaxOrderAllowed")
    private int MPOSMaxOrderAllowed;
    @Expose
    @SerializedName("MPOSVersion")
    private String MPOSVersion;
    @Expose
    @SerializedName("LooseDamagAmount")
    private int LooseDamagAmount;
    @Expose
    @SerializedName("LastBillDate")
    private String LastBillDate;
    @Expose
    @SerializedName("IsSalesTab")
    private boolean IsSalesTab;
    @Expose
    @SerializedName("IsClientSearch")
    private String IsClientSearch;
    @Expose
    @SerializedName("ISRemainder")
    private boolean ISRemainder;
    @Expose
    @SerializedName("ISNoRefReturnAllowed")
    private boolean ISNoRefReturnAllowed;
    @Expose
    @SerializedName("ISMobileNo")
    private boolean ISMobileNo;
    @Expose
    @SerializedName("ISMBQtyRequired")
    private boolean ISMBQtyRequired;
    @Expose
    @SerializedName("ISHDPaymentChange")
    private boolean ISHDPaymentChange;
    @Expose
    @SerializedName("ISHDAddress")
    private boolean ISHDAddress;
    @Expose
    @SerializedName("ISEzetapActive")
    private boolean ISEzetapActive;

    @Expose
    @SerializedName("ISHBPStore")
    private boolean ISHBPStore;
    @Expose
    @SerializedName("ISCardBilling")
    private boolean ISCardBilling;
    @Expose
    @SerializedName("ISBillingPaymentAllowed")
    private boolean ISBillingPaymentAllowed;
    @Expose
    @SerializedName("ISAdvancePaymentAllowed")
    private boolean ISAdvancePaymentAllowed;
    @Expose
    @SerializedName("HomeDeliveryCode")
    private String HomeDeliveryCode;
    @Expose
    @SerializedName("HealingCardURL")
    private String HealingCardURL;
    @Expose
    @SerializedName("HealingCardReloadMinAmount")
    private int HealingCardReloadMinAmount;
    @Expose
    @SerializedName("HealingCardMaxAmount")
    private int HealingCardMaxAmount;
    @Expose
    @SerializedName("HealingCardItemId")
    private String HealingCardItemId;
    @Expose
    @SerializedName("HealingCardCorpId")
    private String HealingCardCorpId;
    @Expose
    @SerializedName("HealingCardActivationMinAmount")
    private int HealingCardActivationMinAmount;
    @Expose
    @SerializedName("FunctionalityProfile")
    private String FunctionalityProfile;
    @Expose
    @SerializedName("EzetapWIFIUrl")
    private String EzetapWIFIUrl;
    @Expose
    @SerializedName("EmployeeSearchURL")
    private String EmployeeSearchURL;
    @Expose
    @SerializedName("DoctorSearchUrl")
    private String DoctorSearchUrl;
    @Expose
    @SerializedName("DefaultCustomerAccount")
    private String DefaultCustomerAccount;

    @Expose
    @SerializedName("DigitalReceiptRequired")
    private boolean DigitalReceiptRequired;

    @Expose
    @SerializedName("DataAreaID")
    private String DataAreaID;
    @Expose
    @SerializedName("DSOTPRequired")
    private boolean DSOTPRequired;
    @Expose
    @SerializedName("DSBillingURL")
    private String DSBillingURL;
    @Expose
    @SerializedName("DCReturnWithoutRefOTPRequired")
    private boolean DCReturnWithoutRefOTPRequired;
    @Expose
    @SerializedName("DCReturnWithRefOTPRequired")
    private boolean DCReturnWithRefOTPRequired;
    @Expose
    @SerializedName("CustomerSearchOneApolloUrl")
    private String CustomerSearchOneApolloUrl;
    @Expose
    @SerializedName("CustomerSearchAXUrl")
    private String CustomerSearchAXUrl;
    @Expose
    @SerializedName("Currency")
    private String Currency;
    @Expose
    @SerializedName("CreditCardIntegration")
    private boolean CreditCardIntegration;
    @Expose
    @SerializedName("ClusterCode")
    private String ClusterCode;
    @Expose
    @SerializedName("Channel")
    private String Channel;
    @Expose
    @SerializedName("BranchTransferOTPRequired")
    private boolean BranchTransferOTPRequired;
    @Expose
    @SerializedName("BackupPath")
    private String BackupPath;
    @Expose
    @SerializedName("AdjustmentIssuesOTPRequired")
    private boolean AdjustmentIssuesOTPRequired;
    @Expose
    @SerializedName("AXServiceUsername")
    private String AXServiceUsername;
    @Expose
    @SerializedName("AXServiceURL")
    private String AXServiceURL;
    @Expose
    @SerializedName("AXServicePassword")
    private String AXServicePassword;
    @Expose
    @SerializedName("AXServiceDomain")
    private String AXServiceDomain;
    @Expose
    @SerializedName("AXInventoryURL")
    private String AXInventoryURL;


    //thease changes made by Gopal on 09-01-2021
    @Expose
    @SerializedName("SMSPayRequestType")
    private String SMSPayRequestType;

    @Expose
    @SerializedName("SMSPayURL")
    private String SMSPayURL;

    @Expose
    @SerializedName("CircleplanCorpCode")
    private String CircleplanCorpCode;

    @Expose
    @SerializedName("UHIDHBPURL")
    private String UHIDHBPURL;

    @Expose
    @SerializedName("SendGlobalMessageURL")
    private String SendGlobalMessageURL;

    public String getSendGlobalMessageURL() {
        return SendGlobalMessageURL;
    }

    public String getCircleplanCorpCode() {
        return CircleplanCorpCode;

    }

    @Expose
    @SerializedName("POSExpiryDays")
    private double POSExpiryDays;

    public double getPOSExpiryDays() {
        return POSExpiryDays;
    }

    @Expose
    @SerializedName("OMSExpiryDays")
    private double OMSExpiryDays;

    public double getOMSExpiryDays() {
        return OMSExpiryDays;
    }

   /* public  Void setCircleplanCorpCode(String CircleplanCorpCode)
    {
        this.CircleplanCorpCode=CircleplanCorpCode;
    }*/

    public boolean DigitalReceiptRequired() {
        return DigitalReceiptRequired;
    }

    public int getSuspendDeleteDays() {
        return SuspendDeleteDays;
    }

    public String getStoreName() {
        return StoreName;
    }

    public String getStoreID() {
        return StoreID;
    }

    public String getStateCode() {
        return StateCode;
    }

    public String getSTOCKURL() {
        return STOCKURL;
    }

    public String getSMSAPI() {
        return SMSAPI;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public String getRemoveAddTender() {
        return RemoveAddTender;
    }

    public String getRegion() {
        return Region;
    }

    public String getPriceChangeURL() {
        return PriceChangeURL;
    }

    public String getPlutusWIFIUrl() {
        return PlutusWIFIUrl;
    }

    public int getPasswordExpiry() {
        return PasswordExpiry;
    }

    public String getOnlineOrderURL() {
        return OnlineOrderURL;
    }

    public String getOnlineGetItemURL() {
        return OnlineGetItemURL;
    }

    public String getOnlineGetBatchURL() {
        return OnlineGetBatchURL;
    }

    public String getOnlineCancelUpdateURL() {
        return OnlineCancelUpdateURL;
    }

    public String getOneApolloURL() {
        return OneApolloURL;
    }

    public String getOTPURL() {
        return OTPURL;
    }

    public boolean isOPReturnsOTPRequired() {
        return OPReturnsOTPRequired;
    }

    public String getOMSUrl() {
        return OMSUrl;
    }

    public String getNormalSale() {
        return NormalSale;
    }

    public int getManualBillingAllowHours() {
        return ManualBillingAllowHours;
    }

    public String getLooseDamagSite() {
        return LooseDamagSite;
    }

    public int getMPOSMaxOrderAllowed() {
        return MPOSMaxOrderAllowed;
    }

    public void setMPOSMaxOrderAllowed(int MPOSMaxOrderAllowed) {
        this.MPOSMaxOrderAllowed = MPOSMaxOrderAllowed;
    }

    public String getMPOSVersion() {
        return MPOSVersion;
    }

    public void setMPOSVersion(String MPOSVersion) {
        this.MPOSVersion = MPOSVersion;
    }

    public int getLooseDamagAmount() {
        return LooseDamagAmount;
    }

    public String getLastBillDate() {
        return LastBillDate;
    }

    public boolean isSalesTab() {
        return IsSalesTab;
    }

    public String getIsClientSearch() {
        return IsClientSearch;
    }

    public boolean isISRemainder() {
        return ISRemainder;
    }

    public boolean isISNoRefReturnAllowed() {
        return ISNoRefReturnAllowed;
    }

    public boolean isISMobileNo() {
        return ISMobileNo;
    }

    public boolean isISMBQtyRequired() {
        return ISMBQtyRequired;
    }

    public boolean isISHDPaymentChange() {
        return ISHDPaymentChange;
    }

    public boolean isISHDAddress() {
        return ISHDAddress;
    }

    public boolean isISEzetapActive() {
        return ISEzetapActive;
    }

    public void setISHBPStore(boolean ISHBPStore) {
        this.ISHBPStore = ISHBPStore;
    }

    public boolean isISHBPStore() {
        return ISHBPStore;
    }

    public boolean isISCardBilling() {
        return ISCardBilling;
    }

    public boolean isISBillingPaymentAllowed() {
        return ISBillingPaymentAllowed;
    }

    public boolean isISAdvancePaymentAllowed() {
        return ISAdvancePaymentAllowed;
    }

    public String getHomeDeliveryCode() {
        return HomeDeliveryCode;
    }

    public String getHealingCardURL() {
        return HealingCardURL;
    }

    public int getHealingCardReloadMinAmount() {
        return HealingCardReloadMinAmount;
    }

    public int getHealingCardMaxAmount() {
        return HealingCardMaxAmount;
    }

    public String getHealingCardItemId() {
        return HealingCardItemId;
    }

    public String getHealingCardCorpId() {
        return HealingCardCorpId;
    }

    public int getHealingCardActivationMinAmount() {
        return HealingCardActivationMinAmount;
    }

    public String getFunctionalityProfile() {
        return FunctionalityProfile;
    }

    public String getEzetapWIFIUrl() {
        return EzetapWIFIUrl;
    }

    public String getEmployeeSearchURL() {
        return EmployeeSearchURL;
    }

    public String getDoctorSearchUrl() {
        return DoctorSearchUrl;
    }

    public String getDefaultCustomerAccount() {
        return DefaultCustomerAccount;
    }

    public String getDataAreaID() {
        return DataAreaID;
    }

    public boolean isDSOTPRequired() {
        return DSOTPRequired;
    }

    public String getDSBillingURL() {
        return DSBillingURL;
    }

    public boolean isDCReturnWithoutRefOTPRequired() {
        return DCReturnWithoutRefOTPRequired;
    }

    public boolean isDCReturnWithRefOTPRequired() {
        return DCReturnWithRefOTPRequired;
    }

    public String getCustomerSearchOneApolloUrl() {
        return CustomerSearchOneApolloUrl;
    }

    public String getCustomerSearchAXUrl() {
        return CustomerSearchAXUrl;
    }

    public String getCurrency() {
        return Currency;
    }

    public boolean isCreditCardIntegration() {
        return CreditCardIntegration;
    }

    public String getClusterCode() {
        return ClusterCode;
    }

    public String getChannel() {
        return Channel;
    }

    public boolean isBranchTransferOTPRequired() {
        return BranchTransferOTPRequired;
    }

    public String getBackupPath() {
        return BackupPath;
    }

    public boolean isAdjustmentIssuesOTPRequired() {
        return AdjustmentIssuesOTPRequired;
    }

    public String getAXServiceUsername() {
        return AXServiceUsername;
    }

    public String getAXServiceURL() {
        return AXServiceURL;
    }

    public String getAXServicePassword() {
        return AXServicePassword;
    }

    public String getAXServiceDomain() {
        return AXServiceDomain;
    }

    public String getAXInventoryURL() {
        return AXInventoryURL;
    }

    //These changes Made by Gopal on 09-01-2021
    public String SMSPayRequestType() {
        return SMSPayRequestType;
    }

    public String SMSPayURL() {
        return SMSPayURL;
    }

    public String getUHIDHBPURL() {
        return UHIDHBPURL;
    }

    @SerializedName("_OMSVendorWiseConfigration")
    @Expose
    private List<OMSVendorWiseConfigration> oMSVendorWiseConfigration = null;

    public List<OMSVendorWiseConfigration> getOMSVendorWiseConfigration() {
        return oMSVendorWiseConfigration;
    }

    public void setOMSVendorWiseConfigration(List<OMSVendorWiseConfigration> oMSVendorWiseConfigration) {
        this.oMSVendorWiseConfigration = oMSVendorWiseConfigration;
    }

    //these changes made by naveen
    public class OMSVendorWiseConfigration {

        @SerializedName("RequestStatus")
        @Expose
        private Integer requestStatus;
        @SerializedName("ReturnMessage")
        @Expose
        private String returnMessage;
        @SerializedName("AllowBulkBilling")
        @Expose
        private Boolean allowBulkBilling;
        @SerializedName("AllowCancellation")
        @Expose
        private Boolean allowCancellation;
        @SerializedName("AllowChangeQTY")
        @Expose
        private Boolean allowChangeQTY;
        @SerializedName("AllowLineCancellation")
        @Expose
        private Boolean allowLineCancellation;
        @SerializedName("AllowMultiBatch")
        @Expose
        private Boolean allowMultiBatch;
        @SerializedName("AllowOMSPriceBilling")
        @Expose
        private Boolean allowOMSPriceBilling;
        @SerializedName("AllowPartialQTY")
        @Expose
        private Boolean allowPartialQTY;
        @SerializedName("AllowProductAdd")
        @Expose
        private Boolean allowProductAdd;
        @SerializedName("AllowSubstitude")
        @Expose
        private Boolean allowSubstitude;
        @SerializedName("AllowVoidProduct")
        @Expose
        private Boolean allowVoidProduct;
        @SerializedName("AllowVoidTender")
        @Expose
        private Boolean allowVoidTender;
        @SerializedName("CorpCode")
        @Expose
        private String corpCode;

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

        public Boolean getAllowBulkBilling() {
            return allowBulkBilling;
        }

        public void setAllowBulkBilling(Boolean allowBulkBilling) {
            this.allowBulkBilling = allowBulkBilling;
        }

        public Boolean getAllowCancellation() {
            return allowCancellation;
        }

        public void setAllowCancellation(Boolean allowCancellation) {
            this.allowCancellation = allowCancellation;
        }

        public Boolean getAllowChangeQTY() {
            return allowChangeQTY;
        }

        public void setAllowChangeQTY(Boolean allowChangeQTY) {
            this.allowChangeQTY = allowChangeQTY;
        }

        public Boolean getAllowLineCancellation() {
            return allowLineCancellation;
        }

        public void setAllowLineCancellation(Boolean allowLineCancellation) {
            this.allowLineCancellation = allowLineCancellation;
        }

        public Boolean getAllowMultiBatch() {
            return allowMultiBatch;
        }

        public void setAllowMultiBatch(Boolean allowMultiBatch) {
            this.allowMultiBatch = allowMultiBatch;
        }

        public Boolean getAllowOMSPriceBilling() {
            return allowOMSPriceBilling;
        }

        public void setAllowOMSPriceBilling(Boolean allowOMSPriceBilling) {
            this.allowOMSPriceBilling = allowOMSPriceBilling;
        }

        public Boolean getAllowPartialQTY() {
            return allowPartialQTY;
        }

        public void setAllowPartialQTY(Boolean allowPartialQTY) {
            this.allowPartialQTY = allowPartialQTY;
        }

        public Boolean getAllowProductAdd() {
            return allowProductAdd;
        }

        public void setAllowProductAdd(Boolean allowProductAdd) {
            this.allowProductAdd = allowProductAdd;
        }

        public Boolean getAllowSubstitude() {
            return allowSubstitude;
        }

        public void setAllowSubstitude(Boolean allowSubstitude) {
            this.allowSubstitude = allowSubstitude;
        }

        public Boolean getAllowVoidProduct() {
            return allowVoidProduct;
        }

        public void setAllowVoidProduct(Boolean allowVoidProduct) {
            this.allowVoidProduct = allowVoidProduct;
        }

        public Boolean getAllowVoidTender() {
            return allowVoidTender;
        }

        public void setAllowVoidTender(Boolean allowVoidTender) {
            this.allowVoidTender = allowVoidTender;
        }

        public String getCorpCode() {
            return corpCode;
        }

        public void setCorpCode(String corpCode) {
            this.corpCode = corpCode;
        }

    }
}
