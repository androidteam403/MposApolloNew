package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PDFShippingLabelResponse implements Serializable
{

    @SerializedName("STATUS")
    @Expose
    private Boolean status;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("URL")
    @Expose
    private Object url;
    @SerializedName("DATA")
    @Expose
    private Data data;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data implements Serializable
    {

        @SerializedName("STORENAME")
        @Expose
        private String storename;
        @SerializedName("gstin")
        @Expose
        private String gstin;
        @SerializedName("STOREADDRESS1")
        @Expose
        private String storeaddress1;
        @SerializedName("STOREADDRESS2")
        @Expose
        private String storeaddress2;
        @SerializedName("STOREADDRESS3")
        @Expose
        private String storeaddress3;
        @SerializedName("CUSTOMERNAME")
        @Expose
        private String customername;
        @SerializedName("PRIMARYCONTACTNO")
        @Expose
        private String primarycontactno;
        @SerializedName("SHIPPINGADDRESS")
        @Expose
        private String shippingaddress;
        @SerializedName("SHIPPINGCITY")
        @Expose
        private String shippingcity;
        @SerializedName("SHIPPINGSTATEID")
        @Expose
        private String shippingstateid;
        @SerializedName("SHIPPINGPINCODE")
        @Expose
        private String shippingpincode;
        @SerializedName("SHIPPINGMETHOD")
        @Expose
        private String shippingmethod;
        @SerializedName("DSPNAME")
        @Expose
        private String dspname;
        @SerializedName("INVOICENO")
        @Expose
        private String invoiceno;
        @SerializedName("INVOICEDATE")
        @Expose
        private String invoicedate;
        @SerializedName("REFVENDORORDERID")
        @Expose
        private String refvendororderid;
        @SerializedName("AWBNO")
        @Expose
        private String awbno;
        @SerializedName("TOTALWEIGHT")
        @Expose
        private String totalweight;
        @SerializedName("INVOICEAMT")
        @Expose
        private String invoiceamt;
        @SerializedName("AMOUNTTOBECOLLECT")
        @Expose
        private String amounttobecollect;
        @SerializedName("HEALTH_WELLNESS")
        @Expose
        private String healthWellness;
        @SerializedName("DISCOUNT")
        @Expose
        private String discount;
        @SerializedName("FULLFILLMENTORDERID")
        @Expose
        private String fullfillmentorderid;
        @SerializedName("SHIPPINGCHARGES")
        @Expose
        private String shippingcharges;
        @SerializedName("QRCODE")
        @Expose
        private String qrcode;
        @SerializedName("PAYMENTMODE")
        @Expose
        private String paymentmode;
        @SerializedName("ORDERID")
        @Expose
        private String orderid;
        @SerializedName("ROUTINGCODE")
        @Expose
        private String routingcode;
        private final static long serialVersionUID = -2681004521757484313L;

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public String getGstin() {
            return gstin;
        }

        public void setGstin(String gstin) {
            this.gstin = gstin;
        }

        public String getStoreaddress1() {
            return storeaddress1;
        }

        public void setStoreaddress1(String storeaddress1) {
            this.storeaddress1 = storeaddress1;
        }

        public String getStoreaddress2() {
            return storeaddress2;
        }

        public void setStoreaddress2(String storeaddress2) {
            this.storeaddress2 = storeaddress2;
        }

        public String getStoreaddress3() {
            return storeaddress3;
        }

        public void setStoreaddress3(String storeaddress3) {
            this.storeaddress3 = storeaddress3;
        }

        public String getCustomername() {
            return customername;
        }

        public void setCustomername(String customername) {
            this.customername = customername;
        }

        public String getPrimarycontactno() {
            return primarycontactno;
        }

        public void setPrimarycontactno(String primarycontactno) {
            this.primarycontactno = primarycontactno;
        }

        public String getShippingaddress() {
            return shippingaddress;
        }

        public void setShippingaddress(String shippingaddress) {
            this.shippingaddress = shippingaddress;
        }

        public String getShippingcity() {
            return shippingcity;
        }

        public void setShippingcity(String shippingcity) {
            this.shippingcity = shippingcity;
        }

        public String getShippingstateid() {
            return shippingstateid;
        }

        public void setShippingstateid(String shippingstateid) {
            this.shippingstateid = shippingstateid;
        }

        public String getShippingpincode() {
            return shippingpincode;
        }

        public void setShippingpincode(String shippingpincode) {
            this.shippingpincode = shippingpincode;
        }

        public String getShippingmethod() {
            return shippingmethod;
        }

        public void setShippingmethod(String shippingmethod) {
            this.shippingmethod = shippingmethod;
        }

        public String getDspname() {
            return dspname;
        }

        public void setDspname(String dspname) {
            this.dspname = dspname;
        }

        public String getInvoiceno() {
            return invoiceno;
        }

        public void setInvoiceno(String invoiceno) {
            this.invoiceno = invoiceno;
        }

        public String getInvoicedate() {
            return invoicedate;
        }

        public void setInvoicedate(String invoicedate) {
            this.invoicedate = invoicedate;
        }

        public String getRefvendororderid() {
            return refvendororderid;
        }

        public void setRefvendororderid(String refvendororderid) {
            this.refvendororderid = refvendororderid;
        }

        public String getAwbno() {
            return awbno;
        }

        public void setAwbno(String awbno) {
            this.awbno = awbno;
        }

        public String getTotalweight() {
            return totalweight;
        }

        public void setTotalweight(String totalweight) {
            this.totalweight = totalweight;
        }

        public String getInvoiceamt() {
            return invoiceamt;
        }

        public void setInvoiceamt(String invoiceamt) {
            this.invoiceamt = invoiceamt;
        }

        public String getAmounttobecollect() {
            return amounttobecollect;
        }

        public void setAmounttobecollect(String amounttobecollect) {
            this.amounttobecollect = amounttobecollect;
        }

        public String getHealthWellness() {
            return healthWellness;
        }

        public void setHealthWellness(String healthWellness) {
            this.healthWellness = healthWellness;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getFullfillmentorderid() {
            return fullfillmentorderid;
        }

        public void setFullfillmentorderid(String fullfillmentorderid) {
            this.fullfillmentorderid = fullfillmentorderid;
        }

        public String getShippingcharges() {
            return shippingcharges;
        }

        public void setShippingcharges(String shippingcharges) {
            this.shippingcharges = shippingcharges;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getPaymentmode() {
            return paymentmode;
        }

        public void setPaymentmode(String paymentmode) {
            this.paymentmode = paymentmode;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getRoutingcode() {
            return routingcode;
        }

        public void setRoutingcode(String routingcode) {
            this.routingcode = routingcode;
        }

    }

}





