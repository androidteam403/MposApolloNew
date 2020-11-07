package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.SalesTrackingDataReq;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.SalesTrackingDataRes;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.TrackingWiseReturnAllowedRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderReturnPresenter<V extends OrederReturnMvpView> extends BasePresenter<V>
        implements OrderReturnMvpPresenter<V> {

    @Inject
    public OrderReturnPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBackPressed() {
        getMvpView().onClickActionBarBack();
    }

    @Override
    public GetGlobalConfingRes getGlobalConfing() {
        return getDataManager().getGlobalJson();
    }

    @Override
    public String terminalId() {
        return getDataManager().getTerminalId();
    }


    @Override
    public void trackingWiseReturnAllowed(String corpId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<TrackingWiseReturnAllowedRes> call = api.TRACKING_WISE_RETURN_ALLOWED_RES_CALL(corpId);
            call.enqueue(new Callback<TrackingWiseReturnAllowedRes>() {
                @Override
                public void onResponse(@NotNull Call<TrackingWiseReturnAllowedRes> call, @NotNull Response<TrackingWiseReturnAllowedRes> response) {
                    // getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        if (response.body().getResultValue().equalsIgnoreCase("True") || response.body().getResultValue().equalsIgnoreCase("true")) {
                            getMvpView().isCorpAllowedReturn(true);
                            getTransactionID();
                        } else {
                            getMvpView().hideLoading();
                            getMvpView().isCorpAllowedReturn(false);
                        }

                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TrackingWiseReturnAllowedRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void getTransactionID() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            TransactionIDReqModel transactionIDModel = new TransactionIDReqModel();
            transactionIDModel.setRequestStatus(0);
            transactionIDModel.setReturnMessage("");
            transactionIDModel.setResultValue("");
            transactionIDModel.setTransactionID("");
            transactionIDModel.setStoreID(getGlobalConfing().getStoreID());
            transactionIDModel.setTerminalID(getDataManager().getTerminalId());
            transactionIDModel.setDataAreaID(getGlobalConfing().getDataAreaID());
            transactionIDModel.setBillingMode(5);
            Call<TransactionIDResModel> call = api.GET_TRANSACTION_ID(transactionIDModel);
            call.enqueue(new Callback<TransactionIDResModel>() {
                @Override
                public void onResponse(@NotNull Call<TransactionIDResModel> call, @NotNull Response<TransactionIDResModel> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().setTransactionId(response.body().getTransactionID());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        } else {
                            getMvpView().showMessage(R.string.some_error);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TransactionIDResModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void cancelDSBilling(CalculatePosTransactionRes posTransactionRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            posTransactionRes.setReturn(true);
            posTransactionRes.setReturnStore(getDataManager().getGlobalJson().getStoreID());
            posTransactionRes.setReturnTerminal(getDataManager().getTerminalId());
            posTransactionRes.setState(getGlobalConfing().getStateCode());
            Call<CalculatePosTransactionRes> call = api.CANCEL_POS_TRANSACTION_RES_CALL(posTransactionRes);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onReturnClick(CalculatePosTransactionRes posTransactionRes) {
        getMvpView().partialReturnOrder(getDataManager().getTerminalId());
    }

    @Override
    public void onCancelCLick(CalculatePosTransactionRes posTransactionRes) {
        //  if (isAllowOrNot(posTransactionRes)) {
        getMvpView().showInfoPopup("Cancel Order", "Do you want to Cancel Order?", true, false, getDataManager().getTerminalId());
//        }else{
//            getMvpView().showCancelOrderSuccess("","Transaction Already Cancelled!!");
//        }
    }

    @Override
    public void onReOrderClick(CalculatePosTransactionRes posTransactionRes) {
        // if (isAllowOrNot(posTransactionRes)) {
        getMvpView().showInfoPopup("Order Return All", "Do you want to Return order?", false, true, getDataManager().getTerminalId());
//        } else {
//            getMvpView().showCancelOrderSuccess("", "Transaction Already Return!!");
//        }
    }

    @Override
    public boolean isAllowOrNot(CalculatePosTransactionRes posTransactionRes) {
        for (SalesLineEntity salesLineEntity : posTransactionRes.getSalesLine()) {
            if (salesLineEntity.getQty() != salesLineEntity.getRemainingQty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onSalesTrackingClick() {
        getMvpView().onSalesTrackingClick();
    }

    @Override
    public void orderReturnAll(CalculatePosTransactionRes posTransactionRes) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            posTransactionRes.setReturn(true);
            posTransactionRes.setReturnStore(getDataManager().getGlobalJson().getStoreID());
            posTransactionRes.setReturnTerminal(getDataManager().getTerminalId());
            posTransactionRes.setState(getGlobalConfing().getStateCode());
            Call<CalculatePosTransactionRes> call = api.RETURN_POS_TRANSACTION_RES_CALL(posTransactionRes);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                        getMvpView().hideLoading();
                        getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
                            getMvpView().onAlreadyItemReturnedColor();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onSalesTrackingApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            SalesTrackingDataReq salesTrackingDataReq = new SalesTrackingDataReq();
            salesTrackingDataReq.setAllowedTenderType("");
            salesTrackingDataReq.setAmounttoAccount((int) getMvpView().calculations().getAmounttoAccount());
            salesTrackingDataReq.setApprovedID("");
            salesTrackingDataReq.setAWBNo("");
            salesTrackingDataReq.setBatchTerminalid(getMvpView().calculations().getBatchTerminalid());
            salesTrackingDataReq.setBillingCity(getMvpView().calculations().getBillingCity());
            salesTrackingDataReq.setBusinessDate(getMvpView().calculations().getBusinessDate());
            salesTrackingDataReq.setCancelReasonCode(getMvpView().calculations().getCancelReasonCode());
            salesTrackingDataReq.setChannel(getMvpView().calculations().getChannel());
            salesTrackingDataReq.setComment(getMvpView().calculations().getComment());
            salesTrackingDataReq.setCorpCode(getMvpView().calculations().getCorpCode());
            salesTrackingDataReq.setCouponCode(getMvpView().calculations().getCouponCode());
            salesTrackingDataReq.setCreatedDateTime("");
            salesTrackingDataReq.setCreatedonPosTerminal(getMvpView().calculations().getCreatedonPosTerminal());
            salesTrackingDataReq.setCurrency(getMvpView().calculations().getCurrency());
            salesTrackingDataReq.setCurrentSalesLine(0);
            salesTrackingDataReq.setCustAccount(getMvpView().calculations().getCustAccount());
            salesTrackingDataReq.setCustAddress(getMvpView().calculations().getCustAddress());
            salesTrackingDataReq.setCustDiscamount((int) getMvpView().calculations().getCustDiscamount());
            salesTrackingDataReq.setCustomerID(getMvpView().calculations().getCustomerID());
            salesTrackingDataReq.setCustomerName(getMvpView().calculations().getCustomerName());
            salesTrackingDataReq.setCustomerState(getMvpView().calculations().getCustomerState());
            salesTrackingDataReq.setDataAreaId(getMvpView().calculations().getDataAreaId());
            salesTrackingDataReq.setDeliveryDate(getMvpView().calculations().getDeliveryDate());
            salesTrackingDataReq.setDiscAmount((int) getMvpView().calculations().getDiscAmount());
            salesTrackingDataReq.setDiscountRef("");
            salesTrackingDataReq.setDOB(getMvpView().calculations().getDOB());
            salesTrackingDataReq.setDoctorCode(getMvpView().calculations().getDoctorCode());
            salesTrackingDataReq.setDoctorName(getMvpView().calculations().getDoctorName());
            salesTrackingDataReq.setDSPCode("");
            salesTrackingDataReq.setEntryStatus((int) getMvpView().calculations().getEntryStatus());
            salesTrackingDataReq.setGender((int) getMvpView().calculations().getGender());
            salesTrackingDataReq.setGrossAmount((int) getMvpView().calculations().getGrossAmount());
            salesTrackingDataReq.setIPNO(getMvpView().calculations().getIPNO());
            salesTrackingDataReq.setIPSerialNO(getMvpView().calculations().getIPSerialNO());
            salesTrackingDataReq.setISAdvancePayment(getMvpView().calculations().getISAdvancePayment());
            salesTrackingDataReq.setISBatchModifiedAllowed(getMvpView().calculations().getISBatchModifiedAllowed());
            salesTrackingDataReq.setISHBPStore(false);
            salesTrackingDataReq.setISHyperDelivered(false);
            salesTrackingDataReq.setISHyperLocalDelivery(false);
            salesTrackingDataReq.setManualBill(getMvpView().calculations().getIsManualBill());
            salesTrackingDataReq.setISOMSOrder(getMvpView().calculations().getISOMSOrder());
            salesTrackingDataReq.setISOMSValidate(false);
            salesTrackingDataReq.setISOnlineOrder(false);
            salesTrackingDataReq.setISPosted(getMvpView().calculations().getISPosted());
            salesTrackingDataReq.setISPrescibeDiscount(getMvpView().calculations().getISPrescibeDiscount());
            salesTrackingDataReq.setISReserved(getMvpView().calculations().getISReserved());
            salesTrackingDataReq.setReturn(getMvpView().calculations().getIsReturn());
            salesTrackingDataReq.setISReturnAllowed(getMvpView().calculations().getISReturnAllowed());
            salesTrackingDataReq.setStockCheck(getMvpView().calculations().getIsStockCheck());
            salesTrackingDataReq.setVoid(getMvpView().calculations().getIsVoid());
            salesTrackingDataReq.setMobileNO(getMvpView().calculations().getMobileNO());
            salesTrackingDataReq.setNetAmount(getMvpView().calculations().getNetAmount());
            salesTrackingDataReq.setNetAmountInclTax((int) getMvpView().calculations().getNetAmountInclTax());
            salesTrackingDataReq.setNumberofItemLines((int) getMvpView().calculations().getNumberofItemLines());
            salesTrackingDataReq.setNumberofItems((int) getMvpView().calculations().getNumberofItems());
            salesTrackingDataReq.setOMSCreditAmount(0);
            salesTrackingDataReq.setOrderPrescriptionURL("");
            salesTrackingDataReq.setOrderSource(getMvpView().calculations().getOrderSource());
            salesTrackingDataReq.setOrderType(getMvpView().calculations().getOrderType());
            salesTrackingDataReq.setPatientID("");
            salesTrackingDataReq.setPaymentSource(getMvpView().calculations().getPaymentSource());
            salesTrackingDataReq.setPincode(getMvpView().calculations().getPincode());
            salesTrackingDataReq.setPosEvent((int) getMvpView().calculations().getPosEvent());
            salesTrackingDataReq.setReciptId(getMvpView().calculations().getReciptId());
            salesTrackingDataReq.setREFNO(getMvpView().calculations().getREFNO());
            salesTrackingDataReq.setRegionCode(getMvpView().calculations().getRegionCode());
            salesTrackingDataReq.setRemainingamount((int) getMvpView().calculations().getRemainingamount());
            salesTrackingDataReq.setReminderDays((int) getMvpView().calculations().getReminderDays());
            salesTrackingDataReq.setRequestStatus((int) getMvpView().calculations().getRequestStatus());
            salesTrackingDataReq.setReturnMessage(getMvpView().calculations().getReturnMessage());
            salesTrackingDataReq.setReturnReceiptId(getMvpView().calculations().getReturnReceiptId());
            salesTrackingDataReq.setReturnStore(getMvpView().calculations().getReturnStore());
            salesTrackingDataReq.setReturnTerminal(getMvpView().calculations().getReturnTerminal());
            salesTrackingDataReq.setReturnTransactionId(getMvpView().calculations().getReturnTransactionId());
            salesTrackingDataReq.setReturnType(getMvpView().calculations().getReturnType());
            salesTrackingDataReq.setRoundedAmount((int) getMvpView().calculations().getRoundedAmount());
            salesTrackingDataReq.setSalesOrigin(getMvpView().calculations().getSalesOrigin());
            salesTrackingDataReq.setSEZ((int) getMvpView().calculations().getSEZ());
            salesTrackingDataReq.setShippingMethod(getMvpView().calculations().getShippingMethod());
            salesTrackingDataReq.setShippingMethodDesc(getMvpView().calculations().getShippingMethod());
            salesTrackingDataReq.setStaff(getMvpView().calculations().getStaff());
            salesTrackingDataReq.setState(getMvpView().calculations().getState());
            salesTrackingDataReq.setStore(getMvpView().calculations().getStore());
            salesTrackingDataReq.setStoreName(getMvpView().calculations().getStoreName());
            salesTrackingDataReq.setTerminal(getMvpView().calculations().getTerminal());
            salesTrackingDataReq.setTimewhenTransClosed((int) getMvpView().calculations().getTimewhenTransClosed());
            salesTrackingDataReq.setTotalDiscAmount((int) getMvpView().calculations().getTotalDiscAmount());
            salesTrackingDataReq.setTotalManualDiscountAmount((int) getMvpView().calculations().getTotalManualDiscountAmount());
            salesTrackingDataReq.setTotalManualDiscountPercentage((int) getMvpView().calculations().getTotalManualDiscountPercentage());
            salesTrackingDataReq.setTotalMRP((int) getMvpView().calculations().getTotalMRP());
            salesTrackingDataReq.setTotalTaxAmount((int) getMvpView().calculations().getTotalTaxAmount());
            salesTrackingDataReq.setTrackingRef(getMvpView().calculations().getTrackingRef());
            salesTrackingDataReq.setTransactionId(getMvpView().calculations().getTransactionId());
            salesTrackingDataReq.setTransDate(getMvpView().calculations().getTransDate());
            salesTrackingDataReq.setTransType((int) getMvpView().calculations().getTransType());
            salesTrackingDataReq.setType((int) getMvpView().calculations().getType());
            salesTrackingDataReq.setVendorCode("");
            salesTrackingDataReq.setVendorId(getMvpView().calculations().getVendorId());
            ArrayList<SalesTrackingDataReq.SalesLine> salesLineArrayList = new ArrayList<>();
            for (int i = 0; i < getMvpView().calculations().getSalesLine().size(); i++) {
                SalesTrackingDataReq.SalesLine salesLine = new SalesTrackingDataReq.SalesLine();
//                salesLine.setAdditionaltax();
//                salesLine.setApplyDiscount();
//                salesLine.setBarcode();
//                salesLine.setBaseAmount();
//                salesLine.setCategory();
//                salesLine.setCategoryCode();
//                salesLine.setCategoryReference();
//                salesLine.setCESSPerc();
//                salesLine.setCESSTaxCode();
            }

            Call<SalesTrackingDataRes> call = api.SALES_TRACKING_DATA_RES_CALL(salesTrackingDataReq);
            call.enqueue(new Callback<SalesTrackingDataRes>() {
                @Override
                public void onResponse(@NotNull Call<SalesTrackingDataRes> call, @NotNull Response<SalesTrackingDataRes> response) {
//                    if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
//                        getMvpView().hideLoading();
//
//                        getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
//                    } else {
//                        getMvpView().hideLoading();
//                        if (response.body() != null) {
//                            getMvpView().showCancelOrderSuccess("", response.body().getReturnMessage());
//                            getMvpView().onAlreadyItemReturnedColor();
//                        }
//                    }
                }

                @Override
                public void onFailure(@NotNull Call<SalesTrackingDataRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }
}
