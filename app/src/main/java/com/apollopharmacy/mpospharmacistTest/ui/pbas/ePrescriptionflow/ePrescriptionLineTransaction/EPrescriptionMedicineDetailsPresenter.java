package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDReqModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EPrescriptionMedicineDetailsPresenter<V extends EPrescriptionMedicineDetailsMvpView> extends BasePresenter<V>
        implements EPrescriptionMedicineDetailsMvpPresenter<V> {

    @Inject
    public EPrescriptionMedicineDetailsPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void fetchLineTransactionList(String prescriptionNo) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().hideKeyboard();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            EPrescriptionMedicineRequest reqModel = new EPrescriptionMedicineRequest();
            reqModel.setPosExpiry(30);
            reqModel.setStoreId("");
            reqModel.setTrasactionId("");
            reqModel.setCancelReasion("");
            reqModel.setPrescriptionNo(prescriptionNo);
            reqModel.setStatusId("");
            reqModel.setDataSourceType("");
            reqModel.setOperationType("");
            Call<List<EPrescriptionMedicineResponse>> call = apiInterface.GET_ONLINEORDER_LINE_TRANSACTION(reqModel);

            call.enqueue(new Callback<List<EPrescriptionMedicineResponse>>() {
                @Override
                public void onResponse(Call<List<EPrescriptionMedicineResponse>> call, Response<List<EPrescriptionMedicineResponse>> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            getMvpView().onSuccessTransactionList(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<EPrescriptionMedicineResponse>> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        }
    }

    @Override
    public void fetchSubstituteList(String prescriptionNo) {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<EPrescriptionSubstituteModelResponse> call = apiInterface.GET_SUBSTITUTE_DETAILS(prescriptionNo);
            call.enqueue(new Callback<EPrescriptionSubstituteModelResponse>() {
                @Override
                public void onResponse(Call<EPrescriptionSubstituteModelResponse> call, Response<EPrescriptionSubstituteModelResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getSubstituteList() != null && !response.body().getRequestStatus().equals(1) && !response.body().getReturnMessage().equals("No Subsitude Available!!")) {
                            getMvpView().onSuccessSubstituteList(response.body());
                        } else {
                            getMvpView().onFailureSubstituteList(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<EPrescriptionSubstituteModelResponse> call, Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });

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
            transactionIDModel.setStoreID(getDataManager().getStoreId());
            transactionIDModel.setTerminalID(getDataManager().getTerminalId());
            transactionIDModel.setDataAreaID(getDataManager().getDataAreaId());
            transactionIDModel.setBillingMode(5);
            Call<TransactionIDResModel> call = api.GET_TRANSACTION_ID(transactionIDModel);
            call.enqueue(new Callback<TransactionIDResModel>() {
                @Override
                public void onResponse(@NotNull Call<TransactionIDResModel> call, @NotNull Response<TransactionIDResModel> response) {
                    if (response.isSuccessful()) {
                        getMvpView().showTransactionID(response.body());
                        getCorporateList();
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
    public void getCorporateList() {
        if (getMvpView().isNetworkConnected()) {
            //getMvpView().showLoading();
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CorporateModel> call = api.getCorporateList(getDataManager().getStoreId(), getDataManager().getDataAreaId(), new JsonObject());
            call.enqueue(new Callback<CorporateModel>() {
                @Override
                public void onResponse(@NotNull Call<CorporateModel> call, @NotNull Response<CorporateModel> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().getCorporateList(response.body());
                    } else {
                        getMvpView().hideLoading();
                        if (response.body() != null) {
                            getMvpView().showMessage(response.body().getReturnMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CorporateModel> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }


    @Override
    public void omscheckbatchstocks(CustomerDataResBean customerDataResBean) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CustomerDataResBean> call = Objects.requireNonNull(api).omscheckbatchstocks(customerDataResBean);
            call.enqueue(new Callback<CustomerDataResBean>() {
                @Override
                public void onResponse(@NotNull Call<CustomerDataResBean> call, @NotNull Response<CustomerDataResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0 || response.body().getRequestStatus() == 2) {
                            getMvpView().CheckBatchStockSuccess(response.body());
                        } else {
                            getMvpView().CheckBatchStockFailure(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CustomerDataResBean> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onOnlineBillApiCall(CustomerDataResBean customerDataResBean) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            customerDataResBean.setTerminal(getDataManager().getTerminalId());
            customerDataResBean.setCreatedonPosTerminal(getDataManager().getTerminalId());

            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CustomerDataResBean> call = api.ONLINE_BILL_APICALL(customerDataResBean);
            call.enqueue(new Callback<CustomerDataResBean>() {
                @Override
                public void onResponse(@NotNull Call<CustomerDataResBean> call, @NotNull Response<CustomerDataResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessOnlineBill(response.body());
                        } else {
                            getMvpView().onFailureOnlineBill(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CustomerDataResBean> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");

        }
    }

//    @Override
//    public void checkBatchStock(CustomerDataResBean customerDataResBean) {
//
//        if (getMvpView().isNetworkConnected()) {
//            getMvpView().showLoading();
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            Call<CustomerDataResBean> call = apiInterface.omscheckbatchstock(customerDataResBean);
//            call.enqueue(new Callback<CustomerDataResBean>() {
//                @Override
//                public void onResponse(Call<CustomerDataResBean> call, Response<CustomerDataResBean> response) {
//                    if (response.isSuccessful()) {
//                        if (response.body() != null && response.body().getRequestStatus() == 0) {
//                            getMvpView().CheckBatchStockSuccess(response.body());
//                        } else {
//                            getMvpView().CheckBatchStockFailure(response.body());
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<CustomerDataResBean> call, Throwable t) {
//
//                }
//            });
//        } else {
//            getMvpView().onError("Internet Connection Not Available");
//
//        }
//
//    }

    @Override
    public String getLoginUserName() {
        return getDataManager().getUserName();
    }
}

