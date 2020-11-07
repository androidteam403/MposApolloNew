package com.apollopharmacy.mpospharmacist.ui.ordersummary;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.ApiClient;
import com.apollopharmacy.mpospharmacist.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.model.PayLoadRequest;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.model.PayLoadRes;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryPresenter<V extends OrderSummaryMvpView> extends BasePresenter<V>
        implements OrderSummaryMvpPresenter<V> {
    private final int REQUEST_CODE_INITIALIZE = 10001;

    @Inject
    public OrderSummaryPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onBackOrderPressed() {
        getMvpView().onBackOrderPressed();
    }

    @Override
    public void onNewPlaceOrderClicked() {
        getMvpView().onNewPlaceOrderClicked();
    }

    @Override
    public String getStoreName() {
        return getDataManager().getGlobalJson().getStoreName();
    }

    @Override
    public String getStoreId() {
        return getDataManager().getStoreId();
    }

    @Override
    public String getTerminalId() {
        return getDataManager().getTerminalId();
    }

    @Override
    public void payLoadDataApi() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            //Creating an object of our api interface
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            PayLoadRequest payLoadRequest = new PayLoadRequest();
            payLoadRequest.setBranchName(getStoreName());
            payLoadRequest.setGSTNo("");
            payLoadRequest.setPhone(getDataManager().getBranchPhoneNumber());
            payLoadRequest.setBillNo(getMvpView().transResData().getReciptId());
            Date now = new Date();
            String format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH).format(now);
            payLoadRequest.setDate(format1);
            payLoadRequest.setCustomerName(getMvpView().transResData().getCustomerName());
            payLoadRequest.setMobileNumber(Long.valueOf(getMvpView().transResData().getMobileNO()));
            payLoadRequest.setNetPayable((int) getMvpView().transResData().getNetAmount());
            ArrayList<PayLoadRequest.Item> itemList = new ArrayList<>();
            for (int i = 0; i < getMvpView().transResData().getSalesLine().size(); i++) {
                PayLoadRequest.Item item = new PayLoadRequest.Item();
                item.setPrice((long) getMvpView().transResData().getSalesLine().get(i).getPrice());
                item.setQTY((long) getMvpView().transResData().getSalesLine().get(i).getQty());
                item.setTax((long) getMvpView().transResData().getSalesLine().get(i).getTaxAmount());
                item.setSkuname(getMvpView().transResData().getSalesLine().get(i).getItemName());
                itemList.add(item);
                payLoadRequest.setItems(itemList);
            }
            int totalCgstPerc = 0;
            int totalIgstPerc = 0;
            int totalSgstPerc = 0;
            PayLoadRequest.Taxes taxes = new PayLoadRequest.Taxes();
            for (int i = 0; i < getMvpView().transResData().getSalesLine().size(); i++) {
                totalCgstPerc += getMvpView().transResData().getSalesLine().get(i).getCGSTPerc();
                totalIgstPerc += getMvpView().transResData().getSalesLine().get(i).getIGSTPerc();
                totalSgstPerc += getMvpView().transResData().getSalesLine().get(i).getSGSTPerc();
            }
            taxes.setCGST(String.valueOf(totalCgstPerc));
            taxes.setIGST(String.valueOf(totalIgstPerc));
            taxes.setSGST(String.valueOf(totalSgstPerc));
            taxes.setTaxes(String.valueOf(getMvpView().transResData().getTotalTaxAmount()));
            payLoadRequest.setTaxes(taxes);
            String token = "Bearer  bl1gsfdr7qhmc53o1jlvt5k1eujvpwwn";
            Call<PayLoadRes> call = api.PAY_LOAD_RES_CALL(token, payLoadRequest);
            Gson gson = new Gson();
            String json = gson.toJson(payLoadRequest);
            System.out.println("void data" + json);
            call.enqueue(new Callback<PayLoadRes>() {
                @Override
                public void onResponse(@NotNull Call<PayLoadRes> call, @NotNull Response<PayLoadRes> response) {
                    //Dismiss Dialog
                    if (response.body() != null) {
                        getMvpView().hideLoading();
                    } else {
                        getMvpView().hideLoading();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PayLoadRes> call, @NotNull Throwable t) {
                    //Dismiss Dialog
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }
    }

}
