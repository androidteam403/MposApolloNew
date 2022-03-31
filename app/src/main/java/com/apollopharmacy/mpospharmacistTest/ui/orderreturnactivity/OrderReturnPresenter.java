package com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity;

import android.util.Pair;
import android.view.View;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListRequest;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ADSPlayListResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.FileEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ListDataEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Media_libraryEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.Playlist_mediaEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.FeedBackRequest;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.FeedBackResponse;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.SalesTrackingDataReq;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.TrackingWiseReturnAllowedRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
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
        getMvpView().showInfoPopup("Cancel Order", "Do you want to Cancel Order?", true, false, getDataManager().getTerminalId());
    }

    @Override
    public void onReOrderClick(CalculatePosTransactionRes posTransactionRes) {
        getMvpView().showInfoPopup("Order Return All", "Do you want to Return order?", false, true, getDataManager().getTerminalId());
    }

    //feed back form dialog.....
    public void onFeedbackformCLick(CalculatePosTransactionRes posTransactionRes) {
       // getMvpView().("Order Return All", "Do you want to Return order?", false, true, getDataManager().getTerminalId());
        getMvpView().Feedbackfromdialog();

    }
    FeedBackDialog feedbackfromDialog;
    String rating="5";
    public void showfeedbackformDialog(String orderid,String mobilenumber,String storeid) {

        feedbackfromDialog = new FeedBackDialog(getMvpView().getContext());
       // feedbackfromDialog.
        feedbackfromDialog.setorderid(orderid);
        feedbackfromDialog.setmobilenumber(mobilenumber);
        rating=feedbackfromDialog.getexcelentviewrating();
        feedbackfromDialog.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackfromDialog.dismiss();
            }
        });
        feedbackfromDialog.setexcellentlayoutLostener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating=feedbackfromDialog.getexcelentviewrating();
               // feedbackfromDialog.dismiss();

            }
        });
        feedbackfromDialog.setverygoodlayoutLostener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating=feedbackfromDialog.getverygoodviewrating();
                // feedbackfromDialog.dismiss();

            }
        });
        feedbackfromDialog.setgoodlayoutLostener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating=feedbackfromDialog.getgoodviewrating();
                // feedbackfromDialog.dismiss();

            }
        });
        feedbackfromDialog.setsatisfactionlayoutLostener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating=feedbackfromDialog.getsatisfactionviewrating();
                // feedbackfromDialog.dismiss();

            }
        });
        feedbackfromDialog.setpoorlayoutLostener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating=feedbackfromDialog.getpoorviewrating();
                // feedbackfromDialog.dismiss();

            }
        });

        feedbackfromDialog.setSubmitbutton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feebackapicall(storeid+feedbackfromDialog.getorderid(),rating,feedbackfromDialog.getcommenttext());
               // feedbackfromDialog.dismiss();
            }
        });
        feedbackfromDialog.show();
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
            salesTrackingDataReq.setIsManualBill(getMvpView().calculations().getIsManualBill());
            salesTrackingDataReq.setISOMSOrder(getMvpView().calculations().getISOMSOrder());
            salesTrackingDataReq.setISOMSValidate(false);
            salesTrackingDataReq.setISOnlineOrder(false);
            salesTrackingDataReq.setISPosted(getMvpView().calculations().getISPosted());
            salesTrackingDataReq.setISPrescibeDiscount(getMvpView().calculations().getISPrescibeDiscount());
            salesTrackingDataReq.setISReserved(getMvpView().calculations().getISReserved());
            salesTrackingDataReq.setIsReturn(getMvpView().calculations().getIsReturn());
            salesTrackingDataReq.setISReturnAllowed(getMvpView().calculations().getISReturnAllowed());
            salesTrackingDataReq.setIsStockCheck(getMvpView().calculations().getIsStockCheck());
            salesTrackingDataReq.setIsVoid(getMvpView().calculations().getIsVoid());
            salesTrackingDataReq.setMobileNO(getMvpView().calculations().getMobileNO());
            salesTrackingDataReq.setNetAmount((float) getMvpView().calculations().getNetAmount());
            salesTrackingDataReq.setNetAmountInclTax((int) getMvpView().calculations().getNetAmountInclTax());
            salesTrackingDataReq.setNumberofItemLines((int) getMvpView().calculations().getNumberofItemLines());
            salesTrackingDataReq.setNumberofItems((int) getMvpView().calculations().getNumberofItems());
            salesTrackingDataReq.setOMSCreditAmount(0);
            salesTrackingDataReq.setOrderPrescriptionURL(null);
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
            salesTrackingDataReq.setReturnType((int) getMvpView().calculations().getReturnType());
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
            salesTrackingDataReq.setTransactionId(getMvpView().calculations().getReturnTransactionId());
            salesTrackingDataReq.setTransDate(getMvpView().calculations().getTransDate());
            salesTrackingDataReq.setTransType((int) getMvpView().calculations().getTransType());
            salesTrackingDataReq.setType((int) getMvpView().calculations().getType());
            salesTrackingDataReq.setVendorCode("");
            salesTrackingDataReq.setVendorId(getMvpView().calculations().getVendorId());
            ArrayList<SalesTrackingDataReq.SalesLine> salesLineArrayList = new ArrayList<>();
            for (int i = 0; i < getMvpView().calculations().getSalesLine().size(); i++) {
                SalesTrackingDataReq.SalesLine salesLine = new SalesTrackingDataReq.SalesLine();
                salesLine.setAdditionaltax((int) getMvpView().calculations().getSalesLine().get(i).getAdditionaltax());
                salesLine.setApplyDiscount(getMvpView().calculations().getSalesLine().get(i).getApplyDiscount());
                salesLine.setBarcode(getMvpView().calculations().getSalesLine().get(i).getBarcode());
                salesLine.setBaseAmount((int) getMvpView().calculations().getSalesLine().get(i).getBaseAmount());
                salesLine.setCategory(getMvpView().calculations().getSalesLine().get(i).getCategory());
                salesLine.setCategoryCode(getMvpView().calculations().getSalesLine().get(i).getCategoryCode());
                salesLine.setCategoryReference(getMvpView().calculations().getSalesLine().get(i).getCategoryReference());
                salesLine.setCESSPerc((float) getMvpView().calculations().getSalesLine().get(i).getCESSPerc());
                salesLine.setCESSTaxCode(getMvpView().calculations().getSalesLine().get(i).getCESSTaxCode());
                salesLine.setCGSTPerc((float) getMvpView().calculations().getSalesLine().get(i).getCGSTPerc());
                salesLine.setCESSTaxCode(getMvpView().calculations().getSalesLine().get(i).getCESSTaxCode());
                salesLine.setComment(getMvpView().calculations().getSalesLine().get(i).getComment());
                salesLine.setDiscAmount((int) getMvpView().calculations().getSalesLine().get(i).getDiscAmount());
                salesLine.setDiscId(getMvpView().calculations().getSalesLine().get(i).getDiscId());
                salesLine.setDiscOfferId(getMvpView().calculations().getSalesLine().get(i).getDiscOfferId());
                salesLine.setDiscountStructureType((int) getMvpView().calculations().getSalesLine().get(i).getDiscountStructureType());
                salesLine.setDiscountType(getMvpView().calculations().getSalesLine().get(i).getDiscountType());
                salesLine.setDiseaseType(getMvpView().calculations().getSalesLine().get(i).getDiseaseType());
                salesLine.setDPCO(getMvpView().calculations().getSalesLine().get(i).getDPCO());
                salesLine.setExpiry(getMvpView().calculations().getSalesLine().get(i).getExpiry());
                salesLine.setHsncodeIn(getMvpView().calculations().getSalesLine().get(i).getHsncode_In());
                salesLine.setIGSTPerc((float) getMvpView().calculations().getSalesLine().get(i).getIGSTPerc());
                salesLine.setIGSTTaxCode(getMvpView().calculations().getSalesLine().get(i).getIGSTTaxCode());
                salesLine.setInventBatchId(getMvpView().calculations().getSalesLine().get(i).getInventBatchId());
                salesLine.setIsChecked(getMvpView().calculations().getSalesLine().get(i).getIsChecked());
                salesLine.setIsGeneric(false);
                salesLine.setISPrescribed((int) getMvpView().calculations().getSalesLine().get(i).getISPrescribed());
                salesLine.setIsPriceOverride(getMvpView().calculations().getSalesLine().get(i).getIsPriceOverride());
                salesLine.setISReserved(getMvpView().calculations().getSalesLine().get(i).getISReserved());
                salesLine.setISStockAvailable(getMvpView().calculations().getSalesLine().get(i).getISStockAvailable());
                salesLine.setIsSubsitute(getMvpView().calculations().getSalesLine().get(i).getIsSubsitute());
                salesLine.setIsVoid(getMvpView().calculations().getSalesLine().get(i).getIsVoid());
                salesLine.setItemId(getMvpView().calculations().getSalesLine().get(i).getItemId());
                salesLine.setItemName(getMvpView().calculations().getSalesLine().get(i).getItemName());
                salesLine.setLineDiscPercentage((int) getMvpView().calculations().getSalesLine().get(i).getLineDiscPercentage());
                salesLine.setLinedscAmount((int) getMvpView().calculations().getSalesLine().get(i).getLinedscAmount());
                salesLine.setLineManualDiscountAmount((int) getMvpView().calculations().getSalesLine().get(i).getLineManualDiscountAmount());
                salesLine.setLineManualDiscountPercentage((int) getMvpView().calculations().getSalesLine().get(i).getLineManualDiscountPercentage());
                salesLine.setLineNo((int) getMvpView().calculations().getSalesLine().get(i).getLineNo());
                salesLine.setManufacturerCode(getMvpView().calculations().getSalesLine().get(i).getManufacturerCode());
                salesLine.setManufacturerName(getMvpView().calculations().getSalesLine().get(i).getManufacturerName());
                salesLine.setMixMode(getMvpView().calculations().getSalesLine().get(i).getMixMode());
                salesLine.setMMGroupId(getMvpView().calculations().getSalesLine().get(i).getMMGroupId());
                salesLine.setModifyBatchId(getMvpView().calculations().getSalesLine().get(i).getModifyBatchId());
                salesLine.setMRP((float) getMvpView().calculations().getSalesLine().get(i).getMRP());
                salesLine.setNetAmount((float) getMvpView().calculations().getSalesLine().get(i).getNetAmount());
                salesLine.setNetAmountInclTax((float) getMvpView().calculations().getSalesLine().get(i).getNetAmountInclTax());
                salesLine.setOfferAmount((int) getMvpView().calculations().getSalesLine().get(i).getOfferAmount());
                salesLine.setOfferDiscountType((int) getMvpView().calculations().getSalesLine().get(i).getOfferDiscountType());
                salesLine.setOfferDiscountValue((int) getMvpView().calculations().getSalesLine().get(i).getOfferDiscountValue());
                salesLine.setOfferQty((int) getMvpView().calculations().getSalesLine().get(i).getOfferQty());
                salesLine.setOfferType((int) getMvpView().calculations().getSalesLine().get(i).getOfferType());
                salesLine.setOmsLineID((int) getMvpView().calculations().getSalesLine().get(i).getOmsLineID());
                salesLine.setOmsLineRECID((int) getMvpView().calculations().getSalesLine().get(i).getOmsLineRECID());
                salesLine.setOrderStatus((int) getMvpView().calculations().getSalesLine().get(i).getOrderStatus());
                salesLine.setOriginalPrice((float) getMvpView().calculations().getSalesLine().get(i).getOriginalPrice());
                salesLine.setPeriodicDiscAmount((int) getMvpView().calculations().getSalesLine().get(i).getPeriodicDiscAmount());
                salesLine.setPreviewText(getMvpView().calculations().getSalesLine().get(i).getPreviewText());
                salesLine.setPrice((float) getMvpView().calculations().getSalesLine().get(i).getPrice());
                salesLine.setProductRecID(getMvpView().calculations().getSalesLine().get(i).getProductRecID());
                salesLine.setQty((int) getMvpView().calculations().getSalesLine().get(i).getQty());
                salesLine.setRemainderDays((int) getMvpView().calculations().getSalesLine().get(i).getRemainderDays());
                salesLine.setRemainingQty((int) getMvpView().calculations().getSalesLine().get(i).getRemainingQty());
                salesLine.setRetailCategoryRecID(getMvpView().calculations().getSalesLine().get(i).getRetailCategoryRecID());
                salesLine.setRetailSubCategoryRecID(getMvpView().calculations().getSalesLine().get(i).getRetailSubCategoryRecID());
                salesLine.setRetailMainCategoryRecID(getMvpView().calculations().getSalesLine().get(i).getRetailMainCategoryRecID());
                salesLine.setReturnQty((int) getMvpView().calculations().getSalesLine().get(i).getReturnQty());
                salesLine.setScheduleCategory(getMvpView().calculations().getSalesLine().get(i).getScheduleCategory());
                salesLine.setScheduleCategoryCode(getMvpView().calculations().getSalesLine().get(i).getScheduleCategoryCode());
                salesLine.setSGSTPerc((float) getMvpView().calculations().getSalesLine().get(i).getSGSTPerc());
                salesLine.setSGSTTaxCode(getMvpView().calculations().getSalesLine().get(i).getSGSTTaxCode());
                salesLine.setStockQty((int) getMvpView().calculations().getSalesLine().get(i).getStockQty());
                salesLine.setSubCategory(getMvpView().calculations().getSalesLine().get(i).getSubCategory());
                salesLine.setSubCategoryCode(getMvpView().calculations().getSalesLine().get(i).getSubCategoryCode());
                salesLine.setSubClassification(getMvpView().calculations().getSalesLine().get(i).getSubClassification());
                salesLine.setSubstitudeItemId(getMvpView().calculations().getSalesLine().get(i).getSubstitudeItemId());
                salesLine.setTax((int) getMvpView().calculations().getSalesLine().get(i).getTax());
                salesLine.setTaxAmount((float) getMvpView().calculations().getSalesLine().get(i).getTaxAmount());
                salesLine.setTotal((int) getMvpView().calculations().getSalesLine().get(i).getTotal());
                salesLine.setTotalDiscAmount((int) getMvpView().calculations().getSalesLine().get(i).getTotalDiscAmount());
                salesLine.setTotalDiscPct((int) getMvpView().calculations().getSalesLine().get(i).getTotalDiscPct());
                salesLine.setTotalRoundedAmount((int) getMvpView().calculations().getSalesLine().get(i).getTotalRoundedAmount());
                salesLine.setTotalTax((int) getMvpView().calculations().getSalesLine().get(i).getTotalTax());
                salesLine.setUnit(getMvpView().calculations().getSalesLine().get(i).getUnit());
                salesLine.setUnitPrice((float) getMvpView().calculations().getSalesLine().get(i).getUnitPrice());
                salesLine.setUnitQty((int) getMvpView().calculations().getSalesLine().get(i).getUnitQty());
                salesLine.setVariantId(getMvpView().calculations().getSalesLine().get(i).getVariantId());
                salesLineArrayList.add(salesLine);
                salesTrackingDataReq.setSalesLine(salesLineArrayList);
            }
            ArrayList<SalesTrackingDataReq.TenderLine> tenderLineArrayList = new ArrayList<>();
            for (int i = 0; i < getMvpView().calculations().getTenderLine().size(); i++) {
                SalesTrackingDataReq.TenderLine tenderLine = new SalesTrackingDataReq.TenderLine();
                tenderLine.setAmountCur((int) getMvpView().calculations().getTenderLine().get(i).getAmountCur());
                tenderLine.setAmountMst((int) getMvpView().calculations().getTenderLine().get(i).getAmountMst());
                tenderLine.setAmountTendered((int) getMvpView().calculations().getTenderLine().get(i).getAmountTendered());
                tenderLine.setBarCode(getMvpView().calculations().getTenderLine().get(i).getBarCode());
                tenderLine.setExchRate((int) getMvpView().calculations().getTenderLine().get(i).getExchRate());
                tenderLine.setExchRateMst((int) getMvpView().calculations().getTenderLine().get(i).getExchRateMst());
                tenderLine.setIsVoid(getMvpView().calculations().getTenderLine().get(i).getIsVoid());
                tenderLine.setLineNo((int) getMvpView().calculations().getTenderLine().get(i).getLineNo());
                tenderLine.setPreviewText(getMvpView().calculations().getTenderLine().get(i).getPreviewText());
                tenderLine.setMobileNo(getMvpView().calculations().getTenderLine().get(i).getMobileNo());
                tenderLine.setRewardsPoint((int) getMvpView().calculations().getTenderLine().get(i).getRewardPoints());
                tenderLine.setTenderId(getMvpView().calculations().getTenderLine().get(i).getTenderId());
                tenderLine.setTenderName(getMvpView().calculations().getTenderLine().get(i).getTenderName());
                tenderLine.setTenderType((int) getMvpView().calculations().getTenderLine().get(i).getTenderType());
                tenderLine.setWalletOrderId(getMvpView().calculations().getTenderLine().get(i).getWalletOrderId());
                tenderLine.setWalletTransactionID(getMvpView().calculations().getTenderLine().get(i).getWalletTransactionID());
                tenderLine.setWalletType((int) getMvpView().calculations().getTenderLine().get(i).getWalletType());
                tenderLineArrayList.add(tenderLine);
                salesTrackingDataReq.setTenderLine(tenderLineArrayList);
            }
            Call<String> call = api.SALES_TRACKING_DATA_RES_CALL(salesTrackingDataReq);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                    getMvpView().hideLoading();
                    getMvpView().showMessage(response.body());
                    System.out.println("sales tracking response-->" + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    getMvpView().hideLoading();
                    getMvpView().showMessage(t.getMessage());
                }
            });

            /* Call<SalesTrackingDataRes> call = api.SALES_TRACKING_DATA_RES_CALL(salesTrackingDataReq);
                call.enqueue(new Callback<SalesTrackingDataRes>() {
                @Override
                public void onResponse(@NotNull Call<SalesTrackingDataRes> call, @NotNull Response<SalesTrackingDataRes> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                        getMvpView().hideLoading();
                        getMvpView().showMessage(response.body().getStatus());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null && !(response.body().getStatus().equalsIgnoreCase("success"))) {
                            getMvpView().showMessage(response.body().getStatus());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SalesTrackingDataRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
//                    handleApiError(t);
                }
            });*/
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

   /* //feed back dialog functionality.....
    FeedbackfromDialog feedbackformDialog;
    public void showfeedbackformDialog()
    {

    }*/

    @Override
    public void feebackapicall(String orderid, String rating, String comment) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceFeedback();
            FeedBackRequest request = new FeedBackRequest();
            request.setOrderId(orderid);
            request.setRating(rating);
            request.setComment(comment);
            Call<FeedBackResponse> call = api.FEEDBACK_Api_RES_CALL(request);
            call.enqueue(new Callback<FeedBackResponse>() {
                @Override
                public void onResponse(@NotNull Call<FeedBackResponse> call, @NotNull Response<FeedBackResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getStatus().equals(true)) {
                        getMvpView().hideLoading();
                        getMvpView().showMessage("Submitted Successfully");
                        feedbackfromDialog.dismiss();

                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null && !(response.body().getStatus().equals(true))) {
                            getMvpView().showMessage(response.body().getMessage());
                        }
                    }
                }
                @Override
                public void onFailure(@NotNull Call<FeedBackResponse> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });


        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onMposTabApiCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceAds();
            ADSPlayListRequest adsPlayListRequest = new ADSPlayListRequest();
            adsPlayListRequest.setScreen_id("MPOS_TAB");
            Call<ADSPlayListResponse> call = api.ADS_PLAY_LIST_RESPONSE_SINGLE(adsPlayListRequest);
            call.enqueue(new Callback<ADSPlayListResponse>() {
                @Override
                public void onResponse(@NotNull Call<ADSPlayListResponse> call, @NotNull Response<ADSPlayListResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getData().getListData().getRows().size() > 0) {
                            getDataManager().setListDataEntity(response.body().getData().getListData());
                            getMvpView().onSucessPlayList();
                            getMvpView().hideLoading();

                        } else {

                            ListDataEntity listDataEntity = new ListDataEntity();
                            ArrayList<RowsEntity> rowsEntitiesList = new ArrayList<>();
                            RowsEntity rowsEntity = new RowsEntity();
                            Playlist_mediaEntity playlist_mediaEntity = new Playlist_mediaEntity();
                            Media_libraryEntity media_libraryEntity = new Media_libraryEntity();
                            ArrayList<FileEntity> fileEntitiesList = new ArrayList<>();
                            FileEntity fileEntity = new FileEntity();
                            fileEntity.setName("mpos_slide_1.jpg");
                            fileEntity.setPath("68B90B3AE0B17AB27D4BB366E50805A2084174193671261F134F22B087221BE64160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
                            fileEntitiesList.add(fileEntity);
                            media_libraryEntity.setFile(fileEntitiesList);
                            playlist_mediaEntity.setMedia_library(media_libraryEntity);
                            rowsEntity.setPlaylist_media(playlist_mediaEntity);
                            rowsEntitiesList.add(rowsEntity);

                            RowsEntity rowsEntity1 = new RowsEntity();
                            Playlist_mediaEntity playlist_mediaEntity1 = new Playlist_mediaEntity();
                            Media_libraryEntity media_libraryEntity1 = new Media_libraryEntity();
                            ArrayList<FileEntity> fileEntitiesList1 = new ArrayList<>();
                            FileEntity fileEntity1 = new FileEntity();
                            fileEntity1.setName("mpos_slide_2.jpg");
                            fileEntity1.setPath("6864B2FA96DB3C2820A9BFA34F0EDF3279AFBA20F9E5EE6F6B54B2BD7D539F604160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
                            fileEntitiesList1.add(fileEntity1);
                            media_libraryEntity1.setFile(fileEntitiesList1);
                            playlist_mediaEntity1.setMedia_library(media_libraryEntity1);
                            rowsEntity1.setPlaylist_media(playlist_mediaEntity1);
                            rowsEntitiesList.add(rowsEntity1);

                            RowsEntity rowsEntity2 = new RowsEntity();
                            Playlist_mediaEntity playlist_mediaEntity2 = new Playlist_mediaEntity();
                            Media_libraryEntity media_libraryEntity2 = new Media_libraryEntity();
                            ArrayList<FileEntity> fileEntitiesList2 = new ArrayList<>();
                            FileEntity fileEntity2 = new FileEntity();
                            fileEntity2.setName("mpos_slide_3.jpg");
                            fileEntity2.setPath("E10F35599A534C1A870654298BDF66B3171ADE8503248AFDE81D19DC013B029C4160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8F74D44EFC9EA3FC8DA2BDB995525098262865F82294905C5C5A8CFD6D40DCD6FC17726E2D538BB53BDD9638C8D1452F9C92E72BE621C97BC437B2B74199C79FD284196CE9CB7DFB050D02FC2329B00D81");
                            fileEntitiesList2.add(fileEntity2);
                            media_libraryEntity2.setFile(fileEntitiesList2);
                            playlist_mediaEntity2.setMedia_library(media_libraryEntity2);
                            rowsEntity2.setPlaylist_media(playlist_mediaEntity2);
                            rowsEntitiesList.add(rowsEntity2);

                            listDataEntity.setRows(rowsEntitiesList);
                            response.body().getData().setListData(listDataEntity);
                            getDataManager().setListDataEntity(response.body().getData().getListData());
                            getMvpView().onSucessPlayList();
                            getMvpView().hideLoading();
                        }
                    } else {
                        getMvpView().hideLoading();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ADSPlayListResponse> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    public List<RowsEntity> getDataListEntity() {
        if (getDataManager().getlistDataEntity().getRows().size() > 0) {
            return getDataManager().getlistDataEntity().getRows();
        } else {
            return null;
        }
    }

    @Override
    public boolean enablescreens() {
        return getDataManager().isOpenScreens();
    }

    @Override
    public boolean DigitalReceiptRequired() {

        return getDataManager().getGlobalJson().DigitalReceiptRequired();
    }

    @Override
    public void onDownloadApiCall(String filePath, String fileName, int pos) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiServiceAds();
            Call<ResponseBody> call = api.doDownloadFile("https://signage.apollopharmacy.app/zc-v3.1-fs-svc/2.0/ads/get/" + filePath);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().hideLoading();
                        createFilePath(response.body(), fileName, true, pos);
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos) {
        try {
            File destinationFile = new File(FileUtil.createMediaFilePath(fileName, getMvpView().getContext()));
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                // Log.d(TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                }
                outputStream.flush();
                // Log.d(TAG, destinationFile.getParent());
//                getMvpView().checkFileAvailability();
                getMvpView().onSucessPlayList();
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                // Log.d(TAG, "Failed to save the file!");
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //  Log.d(TAG, "Failed to save the file!");
        }
    }


    //feedback from functionality

    /*FeedbackfromDialog feedbackfrom;
  public  void   showFeedbackFrom()
  {
      feedbackfrom = new FeedbackfromDialog(getMvpView().getContext());
      feedbackfrom.setCloseListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              smsPaymentDialog.dismiss();
          }
      });
  }*/

}
