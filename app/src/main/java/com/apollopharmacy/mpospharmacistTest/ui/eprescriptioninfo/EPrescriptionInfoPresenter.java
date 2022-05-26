package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.CheckBatchInventoryRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoReq;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataReqBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchReqBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EPrescriptionInfoPresenter<V extends EPrescriptionInfoMvpView> extends BasePresenter<V>
        implements EPrescriptionInfoMvpPresenter<V> {
    @Inject
    EPrescriptionInfoPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void fetchOMSCustomerInfo(String refNumber) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            CustomerDataReqBean reqModel = new CustomerDataReqBean();
            reqModel.setTransactionID(refNumber);
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            Call<ArrayList<CustomerDataResBean>> call = api.GET_OMS_TRANSACTION(reqModel);
            call.enqueue(new Callback<ArrayList<CustomerDataResBean>>() {
                @Override
                public void onResponse(@NotNull Call<ArrayList<CustomerDataResBean>> call, @NotNull Response<ArrayList<CustomerDataResBean>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        fetchOMSMedicineInfo(refNumber);
                        //System.out.println("Customer Name-->0"+response.body().get(0).getCustomerName());
                        getMvpView().onSuccessGetOMSTransaction(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<CustomerDataResBean>> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    System.out.println("Customer Name-->4" + "Syntax Error");

                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void fetchOMSMedicineInfo(String refNumber) {
        if (getMvpView().isNetworkConnected()) {
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            MedicineBatchReqBean reqModel = new MedicineBatchReqBean();
            reqModel.setTransactionID(refNumber);
            reqModel.setRefID("");
            reqModel.setExpiryDays(90);
            reqModel.setStoreID(getDataManager().getStoreId());
            reqModel.setTerminalID(getDataManager().getTerminalId());
            reqModel.setDataAreaID(getDataManager().getDataAreaId());
            Call<MedicineBatchResBean> call = api.GET_OMS_PHYSICAL_BATCH(reqModel);
            call.enqueue(new Callback<MedicineBatchResBean>() {
                @Override
                public void onResponse(@NotNull Call<MedicineBatchResBean> call, @NotNull Response<MedicineBatchResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        getMvpView().onSuccessGetOMSPhysicalBatch(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MedicineBatchResBean> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    // handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void onClickOrderStockFulfilment() {
        getMvpView().onClickOrderStockFulfilment();
    }

    @Override
    public void onClickCancelOnlineOrder() {
        getMvpView().onClickCancelOnlineOrder();
    }

    @Override
    public void onClickUpdateOMSOrder_pickingconfirmation() {
        getMvpView().UpdateOmsOrder_Pickingconfirmation();
    }

    @Override
    public void onClickUpdateOMSOrder_unpickingconfirmation() {
        getMvpView().UpdateOmsOrder_unPickingconfirmation();
    }

    @Override
    public void onClickUpdateOMSOrder_packingconfirmation() {
        getMvpView().UpdateOmsOrder_Packingconfirmation();
    }

    @Override
    public void onClickUpdateOMSOrder_unpackingconfirmation() {
        getMvpView().UpdateOmsOrder_unPackingconirmation();
    }

    @Override
    public void onclickbarcodereprint() {
        getMvpView().barcodereprint();
    }


    @Override
    public void onClickChangeSite() {
        getMvpView().onClickChangeSite();
    }


    @Override
    public void onClickClose() {
        getMvpView().onClickClose();
    }

    @Override
    public void onClickContinueBilling() {
        getMvpView().onClickContinueBilling();
    }


    @Override
    public void onCheckBatchStock(CustomerDataResBean customerDataResBean) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CustomerDataResBean> call = api.omscheckbatchstock(customerDataResBean);
            call.enqueue(new Callback<CustomerDataResBean>() {
                @Override
                public void onResponse(@NotNull Call<CustomerDataResBean> call, @NotNull Response<CustomerDataResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
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
    public void onLoadOmsOrder(CustomerDataResBean customerDataResBean) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            customerDataResBean.setTerminal(getDataManager().getTerminalId());
            customerDataResBean.setCreatedonPosTerminal(getDataManager().getTerminalId());

            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CustomerDataResBean> call = api.LOAD_OMS_ORDER(customerDataResBean);
            call.enqueue(new Callback<CustomerDataResBean>() {
                @Override
                public void onResponse(@NotNull Call<CustomerDataResBean> call, @NotNull Response<CustomerDataResBean> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().LoadOmsOrderSuccess(response.body());
                        } else {
                            getMvpView().LoadOmsOrderFailure(response.body());
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

    //Update Oms Order.................................
    @Override
    public void UpdateOmsOrder(OMSOrderUpdateRequest omsOrderUpdateRequest) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            omsOrderUpdateRequest.setTerminalID(getDataManager().getTerminalId());
            //ApiInterface api = ApiClient.getApiService(Constant.UPDATEOMSORDER);
            // text.replace("/"","");
            String check_epos = getDataManager().getEposURL();
            String replace_url = getDataManager().getEposURL();
            if (check_epos.contains("EPOS/")) {
                replace_url = check_epos.replace("EPOS/", "");

            }
            if (check_epos.contains("9880")) {
                replace_url = check_epos.replace("9880", "9887");

            }
            // ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            ApiInterface api = ApiClient.getApiService(replace_url);

            // ApiInterface api = ApiClient.getApiService3();
            Call<OMSOrderUpdateResponse> call = api.UPDATE_OMS_ORDER(omsOrderUpdateRequest);
            call.enqueue(new Callback<OMSOrderUpdateResponse>() {
                @Override
                public void onResponse(@NotNull Call<OMSOrderUpdateResponse> call, @NotNull Response<OMSOrderUpdateResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().OmsOrderUpdateSuccess(response.body());
                        } else {
                            getMvpView().OmsOrderUpdateFailure(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<OMSOrderUpdateResponse> call, @NotNull Throwable t) {
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
    public void getBatchDetailsApi(SalesLineEntity selected_item, boolean isEshopChecking) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            GetBatchInfoReq batchInfoReq = new GetBatchInfoReq();
            batchInfoReq.setArticleCode(selected_item.getItemId());
            batchInfoReq.setCustomerState("");
            batchInfoReq.setDataAreaId(getDataManager().getDataAreaId());
            batchInfoReq.setSearchType(1);
            batchInfoReq.setSEZ(0);
            batchInfoReq.setStoreId(getDataManager().getStoreId());
            batchInfoReq.setStoreState(getDataManager().getGlobalJson().getStateCode());
            batchInfoReq.setTerminalId(getDataManager().getTerminalId());
            batchInfoReq.setExpiryDays(getDataManager().getGlobalJson().getOMSExpiryDays());
            Call<GetBatchInfoRes> call = api.GET_BATCH_INFO_RES_CALL(batchInfoReq);
            call.enqueue(new Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBatchInfoRes> call, @NotNull Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0) {
                            if (isEshopChecking) {
                                getMvpView().onSuccessBatchInfoEshopChecking(response.body(), selected_item.getMRP());
                            } else {
                                getMvpView().onSuccessBatchInfo(response.body(), selected_item.getMRP());
                            }
                        } else {
                            getMvpView().onFailedBatchInfo(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetBatchInfoRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

    @Override
    public void checkeshopshippingcharges() {
        getMvpView().checkeshopshippingcharges();
    }

    @Override
    public void getBatchDetailsApi_pickpack(SalesLineEntity selected_item, PickPackReservation pickPackReservation) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            GetBatchInfoReq batchInfoReq = new GetBatchInfoReq();
            batchInfoReq.setArticleCode(selected_item.getItemId());
            batchInfoReq.setCustomerState("");
            batchInfoReq.setDataAreaId(getDataManager().getDataAreaId());
            batchInfoReq.setSearchType(1);
            batchInfoReq.setSEZ(0);
            batchInfoReq.setStoreId(getDataManager().getStoreId());
            batchInfoReq.setStoreState(getDataManager().getGlobalJson().getStateCode());
            batchInfoReq.setTerminalId(getDataManager().getTerminalId());
            batchInfoReq.setExpiryDays(getDataManager().getGlobalJson().getOMSExpiryDays());
            Call<GetBatchInfoRes> call = api.GET_BATCH_INFO_RES_CALL(batchInfoReq);
            call.enqueue(new Callback<GetBatchInfoRes>() {
                @Override
                public void onResponse(@NotNull Call<GetBatchInfoRes> call, @NotNull Response<GetBatchInfoRes> response) {
                    if (response.isSuccessful()) {
                        //Dismiss Dialog
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null && response.body().getRequestStatus() == 0)
                            getMvpView().onSuccessBatchInfo_pickpack(response.body(), pickPackReservation, selected_item.getMRP());
                        else
                            getMvpView().onFailedBatchInfo(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetBatchInfoRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }



  /*  @Override
    public void getBatchDetails_multiplearticles(ArrayList<SalesLineEntity> salesLineEntities) {
        List<Observable<GetBatchInfoRes>> observableList = new ArrayList<>();
        getMvpView().showLoading();
        ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());


        observableList.addAll(setupProductNetworkMiancategory(api, salesLineEntities));
        Observable<HashMap<String, GetBatchInfoRes>> combined = Observable.zip(observableList, new Function<Object[], HashMap<String, GetBatchInfoRes>>() {
            @Override
            public HashMap<String, GetBatchInfoRes> apply(@NotNull Object[] objects) throws Exception {


                return null;
            }
        });

        combined.subscribe(new Subscriber<HashMap<String, GetBatchInfoRes>>()
                           {

                               @Override
                               public void onSubscribe(Subscription subscription) {

                               }

                               @Override
                               public void onNext(HashMap<String, GetBatchInfoRes> stringGetBatchInfoResHashMap) {

                               }

                               @Override
                               public void onError(Throwable throwable) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
        );

    }

    public List<Observable<GetBatchInfoRes>> setupProductNetworkMiancategory(ApiInterface restConnection, ArrayList<SalesLineEntity> entities) {
        List<Observable<GetBatchInfoRes>> list = new ArrayList<>();

        for (SalesLineEntity s : entities) {

            GetBatchInfoReq batchInfoReq = new GetBatchInfoReq();
            batchInfoReq.setArticleCode(s.getItemId());
            batchInfoReq.setCustomerState("");
            batchInfoReq.setDataAreaId(getDataManager().getDataAreaId());
            batchInfoReq.setSearchType(1);
            batchInfoReq.setSEZ(0);
            batchInfoReq.setStoreId(getDataManager().getStoreId());
            batchInfoReq.setStoreState(getDataManager().getGlobalJson().getStateCode());
            batchInfoReq.setTerminalId(getDataManager().getTerminalId());
            list.add(restConnection.GET_BATCH_INFO_RES_CALL(batchInfoReq)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread()));


         *//*   Observable.merge(btcObservable, ethObservable)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResults, this::handleError);*//*
        }

        return list;
    }
*/



    /*  public void getProductList(String mainCategory, Context context, MyOffersListener myOffersListener, int categoryId) {
        if (null != mainCategory && mainCategory.length() > 0) {
            List<Observable<GetProductListResponse>> observableList;
            showDialog(context, context.getResources().getString(R.string.label_please_wait));
            ApiInterface restConnection = ApiClient.createService(ApiInterface.class, Constants.Get_Special_Offers_Products);
            observableList = setupProductNetwork(restConnection, mainCategory, context, categoryId);
            Observable<HashMap<String, GetProductListResponse>> combined = Observable.zip(observableList, new FuncN<HashMap<String, GetProductListResponse>>() {
                @Override
                public HashMap<String, GetProductListResponse> call(Object... args) {
                    HashMap<String, GetProductListResponse> data = new HashMap<>();
                    List<String> categList = getSpecialOffersCategoryList();
                    data = prepareToListView(categList, args);
                    return data;
                }
            });

            combined.subscribe(new Subscriber<HashMap<String, GetProductListResponse>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    dismissDialog();
                    myOffersListener.onFailure(e.getMessage());
                    e.printStackTrace();
                }

                @Override
                public void onNext(HashMap<String, GetProductListResponse> o) {
                    dismissDialog();
                    myOffersListener.onSuccessProductList(o);
                }
            });
        }
    } */


    @Override
    public void checkBatchInventory(GetBatchInfoRes.BatchListObj items, boolean isAlertDialog) {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            CheckBatchInventoryReq inventoryReq = new CheckBatchInventoryReq();
            inventoryReq.setDataAreaID(getDataManager().getDataAreaId());
            inventoryReq.setInventBatchID(items.getBatchNo());
            inventoryReq.setItemID(items.getItemID());
            inventoryReq.setRequestStatus(0);
            inventoryReq.setReturnMessage("");
            inventoryReq.setStock(items.getREQQTY());
            inventoryReq.setStoreID(getDataManager().getStoreId());
            inventoryReq.setTerminalID(getDataManager().getTerminalId());

            Call<CheckBatchInventoryRes> call = api.CHECK_BATCH_INVENTORY_RES_CALL(inventoryReq);
            call.enqueue(new Callback<CheckBatchInventoryRes>() {
                @Override
                public void onResponse(@NotNull Call<CheckBatchInventoryRes> call, @NotNull Response<CheckBatchInventoryRes> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful() && response.body() != null)

                            getMvpView().checkBatchInventorySuccess(isAlertDialog);
                        else
                            getMvpView().checkBatchInventoryFailed(response.body() != null ? response.body().getReturnMessage() : "Stock not Available!");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CheckBatchInventoryRes> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("InternetConnection Not Available");
        }
    }

    @Override
    public void onNavigateNextActivity() {
        getMvpView().onNavigateNextActivity();
    }


}
