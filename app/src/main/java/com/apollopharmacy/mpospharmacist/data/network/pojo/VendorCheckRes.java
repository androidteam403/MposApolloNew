package com.apollopharmacy.mpospharmacist.data.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorCheckRes {


    @Expose
    @SerializedName("APIS")
    private List<APISEntity> APIS;
    @Expose
    @SerializedName("BUILDDETAILS")
    private BUILDDETAILSEntity BUILDDETAILS;
    @Expose
    @SerializedName("DeviceDetails")
    private DeviceDetailsEntity DeviceDetails;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private boolean status;

    public List<APISEntity> getAPIS() {
        return APIS;
    }

    public BUILDDETAILSEntity getBUILDDETAILS() {
        return BUILDDETAILS;
    }

    public DeviceDetailsEntity getDeviceDetails() {
        return DeviceDetails;
    }

    public String getMessage() {
        return message;
    }

    public boolean getStatus() {
        return status;
    }

    public static class APISEntity {
        @Expose
        @SerializedName("TOKEN")
        private String TOKEN;
        @Expose
        @SerializedName("NAME")
        private String NAME;
        @Expose
        @SerializedName("URL")
        private String URL;

        public String getTOKEN() {
            return TOKEN;
        }

        public String getNAME() {
            return NAME;
        }

        public String getURL() {
            return URL;
        }
    }

    public static class BUILDDETAILSEntity {
        @Expose
        @SerializedName("VENDORIMAGE")
        private String VENDORIMAGE;
        @Expose
        @SerializedName("APOLLOIMAGE")
        private String APOLLOIMAGE;
        @Expose
        @SerializedName("VENDORTEXT")
        private String VENDORTEXT;
        @Expose
        @SerializedName("APOLLOTEXT")
        private String APOLLOTEXT;
        @Expose
        @SerializedName("BUILDMESSAGE")
        private String BUILDMESSAGE;
        @Expose
        @SerializedName("DOWNLOADURL")
        private String DOWNLOADURL;
        @Expose
        @SerializedName("FORCEDOWNLOAD")
        private boolean FORCEDOWNLOAD;
        @Expose
        @SerializedName("BUILDVERSION")
        private String BUILDVERSION;
        @Expose
        @SerializedName("AVABILITYMESSAGE")
        private String AVABILITYMESSAGE;
        @Expose
        @SerializedName("APPAVALIBALITY")
        private boolean APPAVALIBALITY;

        public String getVENDORIMAGE() {
            return VENDORIMAGE;
        }

        public String getAPOLLOIMAGE() {
            return APOLLOIMAGE;
        }

        public String getVENDORTEXT() {
            return VENDORTEXT;
        }

        public String getAPOLLOTEXT() {
            return APOLLOTEXT;
        }

        public String getBUILDMESSAGE() {
            return BUILDMESSAGE;
        }

        public String getDOWNLOADURL() {
            return DOWNLOADURL;
        }

        public boolean getFORCEDOWNLOAD() {
            return FORCEDOWNLOAD;
        }

        public String getBUILDVERSION() {
            return BUILDVERSION;
        }

        public String getAVABILITYMESSAGE() {
            return AVABILITYMESSAGE;
        }

        public boolean getAPPAVALIBALITY() {
            return APPAVALIBALITY;
        }
    }

    public static class DeviceDetailsEntity {
        @Expose
        @SerializedName("SEQUENCENUMBER")
        private String SEQUENCENUMBER;
        @Expose
        @SerializedName("PRESCRIPTION")
        private boolean PRESCRIPTION;
        @Expose
        @SerializedName("PAYMETTYPE")
        private String PAYMETTYPE;
        @Expose
        @SerializedName("DELIVERYMODE")
        private String DELIVERYMODE;
        @Expose
        @SerializedName("VENDORNAME")
        private String VENDORNAME;

        public String getSEQUENCENUMBER() {
            return SEQUENCENUMBER;
        }

        public boolean getPRESCRIPTION() {
            return PRESCRIPTION;
        }

        public String getPAYMETTYPE() {
            return PAYMETTYPE;
        }

        public String getDELIVERYMODE() {
            return DELIVERYMODE;
        }

        public String getVENDORNAME() {
            return VENDORNAME;
        }
    }
}