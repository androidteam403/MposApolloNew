package com.apollopharmacy.mpospharmacist.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneApolloSendOtpRes {


    @Expose
    @SerializedName("ReturnMessage")
    private String ReturnMessage;
    @Expose
    @SerializedName("RequestStatus")
    private int RequestStatus;
    @Expose
    @SerializedName("OneApolloProcessResult")
    private OneApolloProcessResultEntity OneApolloProcessResult;

    public void setReturnMessage(String ReturnMessage) {
        this.ReturnMessage = ReturnMessage;
    }

    public void setRequestStatus(int RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public void setOneApolloProcessResult(OneApolloProcessResultEntity OneApolloProcessResult) {
        this.OneApolloProcessResult = OneApolloProcessResult;
    }

    public static class OneApolloProcessResultEntity {
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("RRNO")
        private String RRNO;
        @Expose
        @SerializedName("OTP")
        private String OTP;
        @Expose
        @SerializedName("Message")
        private String Message;
        @Expose
        @SerializedName("Action")
        private String Action;

        public void setStatus(String status) {
            this.status = status;
        }

        public void setRRNO(String RRNO) {
            this.RRNO = RRNO;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public void setAction(String Action) {
            this.Action = Action;
        }
    }
}
