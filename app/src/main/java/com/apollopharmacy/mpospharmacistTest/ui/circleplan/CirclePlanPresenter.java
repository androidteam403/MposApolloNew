package com.apollopharmacy.mpospharmacistTest.ui.circleplan;

import android.util.Log;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiClient;
import com.apollopharmacy.mpospharmacistTest.data.network.ApiInterface;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CirclePlanPresenter<V extends CirclePlanCashbackMvpView> extends BasePresenter<V>
        implements CirclePlanMvpPresenter<V> {
    @Inject
    public CirclePlanPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickBackPress() {
        getMvpView().onClickBackBtn();
    }

    @Override
    public void onContinueButtonpressed() {
        getMvpView().onCintinueBtn();
    }




    @Override
    public void circleplandetailsapicall(CircleplanDetailsRequest request) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CircleplanDetailsResponse> call = api.circlePlandetails(request);
            call.enqueue(new Callback<CircleplanDetailsResponse>() {
                @Override
                public void onResponse(@NotNull Call<CircleplanDetailsResponse> call, @NotNull Response<CircleplanDetailsResponse> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            getMvpView().onSuccessCircleplanDetails(response.body());
                        } else {
                            getMvpView().onFailureCircleplanDetails(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CircleplanDetailsResponse> call, @NotNull Throwable t) {
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
    public void planonecheckboxpressed() {
        getMvpView().planonecheckboxaction();
    }

    @Override
    public void plantwocheckboxpressed() {
        getMvpView().plantwocheckboxaction();
    }

    @Override
    public void clickonernollbuttonpressed() {
        getMvpView().ernollbuttonaction();
    }

    @Override
    public void clickonernollexitbuttonpressed() {
        getMvpView().ernollexitbuttonaction();
    }


    //set Sales line item to Sales line for check stock....
    @Override
    public void oncheckbatchstock(POSTransactionEntity posTransactionEntity, String circleprice) {

        List<SalesLineEntity> itemsArrayList = posTransactionEntity.getSalesLine();
        SalesLineEntity salesLineEntity = new SalesLineEntity();
        salesLineEntity.setAdditionaltax(0);
        salesLineEntity.setApplyDiscount(false);
        salesLineEntity.setBarcode("");
        salesLineEntity.setCategory("PHARMA");
        salesLineEntity.setCategoryCode("P");
        salesLineEntity.setCategoryReference("");
        salesLineEntity.setCESSPerc(0);
        salesLineEntity.setCESSTaxCode("");
        salesLineEntity.setChecked(false);
        salesLineEntity.setComment("");
        salesLineEntity.setDiscAmount(0);
        salesLineEntity.setDiscId("");
        salesLineEntity.setDiscOfferId("");
        salesLineEntity.setDiscountStructureType(0);
        salesLineEntity.setDiscountType("");
        salesLineEntity.setDiseaseType("");
        salesLineEntity.setDPCO(false);
        salesLineEntity.setHsncode_In("");
        salesLineEntity.setISPrescribed(0);
        salesLineEntity.setISReserved(false);
        salesLineEntity.setISStockAvailable(true);
        salesLineEntity.setItemId("CIR0033");
        salesLineEntity.setItemName("CIRCLE MEMBERSHIP");
        salesLineEntity.setLineNo(Singletone.getInstance().itemsArrayList.size() + 1);
        salesLineEntity.setLineDiscPercentage(0);
        salesLineEntity.setLinedscAmount(0);
        salesLineEntity.setLineManualDiscountAmount(0);
        salesLineEntity.setLineManualDiscountPercentage(0);
        salesLineEntity.setManufacturerCode("");
        salesLineEntity.setManufacturerName("");
        salesLineEntity.setMixMode(false);
        salesLineEntity.setMMGroupId("0");
        salesLineEntity.setModifyBatchId("");
        salesLineEntity.setMRP(Double.parseDouble(circleprice));
        salesLineEntity.setOfferAmount(0);
        salesLineEntity.setOfferDiscountType(0);
        salesLineEntity.setOfferDiscountValue(0);
        salesLineEntity.setOfferQty(0);
        salesLineEntity.setOfferType(0);
        salesLineEntity.setOmsLineID(0);
        salesLineEntity.setOmsLineRECID(0);
        salesLineEntity.setOrderStatus(0);
        salesLineEntity.setPeriodicDiscAmount(0);
        salesLineEntity.setPreviewText("");
        salesLineEntity.setPriceOverride(false);
        salesLineEntity.setProductRecID("");
        salesLineEntity.setQty(1);
        salesLineEntity.setRemainderDays(0);
        salesLineEntity.setRemainingQty(0);
        salesLineEntity.setRetailCategoryRecID("");
        salesLineEntity.setRetailMainCategoryRecID("");
        salesLineEntity.setRetailSubCategoryRecID("");
        salesLineEntity.setReturnQty(0);
        salesLineEntity.setScheduleCategory("");
        salesLineEntity.setScheduleCategoryCode("");
        salesLineEntity.setStockQty(0);
        salesLineEntity.setSubCategory("");
        salesLineEntity.setSubCategoryCode("");
        salesLineEntity.setSubClassification("");
        salesLineEntity.setSubsitute(false);
        salesLineEntity.setSubstitudeItemId("");
        salesLineEntity.setTotalDiscAmount(0);
        salesLineEntity.setTotalDiscPct(0);
        salesLineEntity.setTotalRoundedAmount(0);
        salesLineEntity.setPrice(salesLineEntity.getMRP());
        salesLineEntity.setUnitPrice(salesLineEntity.getMRP());
        salesLineEntity.setUnitQty(salesLineEntity.getQty());
        double total =salesLineEntity.getQty() * salesLineEntity.getMRP();
        salesLineEntity.setNetAmount(total);
        salesLineEntity.setTotal(total);
        salesLineEntity.setBaseAmount(total);
        salesLineEntity.setNetAmountInclTax(total);
        salesLineEntity.setOriginalPrice(salesLineEntity.getMRP());
        salesLineEntity.setUnit("");
        salesLineEntity.setVariantId("");
        salesLineEntity.setReturnClick(false);
        salesLineEntity.setSelectedReturnItem(false);
        salesLineEntity.setSubCategory("PHARMA");
        salesLineEntity.setSubstitudeItemId("PHARMA");
        salesLineEntity.setVoid(false);
        itemsArrayList.add(salesLineEntity);
        posTransactionEntity.setSalesLine(itemsArrayList);
        //Singletone.getInstance().itemsArrayList.add(itemsArrayList);
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.checkbatchstock(posTransactionEntity);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
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
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
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
    public void circlrplantransaction(String price,CalculatePosTransactionRes calculatePosTransactionRes)
    {
        if (getMvpView().isNetworkConnected()) {

            getMvpView().showLoading();
            int linenumber=Singletone.getInstance().itemsArrayList.size()+1;
            if(linenumber > 2)
            {
                linenumber=2;
            }

            ApiInterface api = ApiClient.getApiService(getDataManager().getEposURL());
            Call<CalculatePosTransactionRes> call = api.circleplantransaction(linenumber,price,"CIR0033",calculatePosTransactionRes);
            call.enqueue(new Callback<CalculatePosTransactionRes>() {
                @Override
                public void onResponse(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Response<CalculatePosTransactionRes> response) {
                    getMvpView().hideLoading();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 0) {
                            String criclecode=getDataManager().getGlobalJson().getCircleplanCorpCode();
                            getMvpView().circleplantransactionSuccess(response.body(),criclecode);
                        } else {
                            getMvpView().circleplantransactionFailure(response.body());

                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalculatePosTransactionRes> call, @NotNull Throwable t) {
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
    public void sendSmsservice(String mobilenumber) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            String URl = getDataManager().getGlobalJson().getSMSAPI();

            //randaom Otp Number---->
            SecureRandom random = new SecureRandom();
            int num = random.nextInt(100000);
            String formatted = String.format("%05d", num);
            // System.out.println(formatted);
            String message = "Your OTP is " + formatted + ". To proceed with the transaction, please visit www.oneapollo.com and consent to our privacy policies and terms and conditions.";

            String[] separated = URl.split("SendSmsFortemplete?");
            String url = separated[0];
            // url=url+"SendSmsFortemplete?"+"to="+mobilenumber+"&message="+message;
            Log.d("Mobile number-->", mobilenumber);
            Log.d("Mobile number-->", url);

            ApiInterface api = ApiClient.getApiServiceOTp(url);
            Call<ModelMobileNumVerify> call = api.verifyMobileNumber(mobilenumber, message);
            call.enqueue(new Callback<ModelMobileNumVerify>() {
                @Override
                public void onResponse(@NotNull Call<ModelMobileNumVerify> call, @NotNull Response<ModelMobileNumVerify> response) {
                    if (response.isSuccessful()) {
                        getMvpView().hideLoading();
                        assert response.body() != null;
                        getMvpView().generateotpSuccess(response.body(), formatted);
                     

                    }
                }

                @Override
                public void onFailure(@NotNull Call<ModelMobileNumVerify> call, @NotNull Throwable t) {
                    getMvpView().hideLoading();
                    handleApiError(t);
                }
            });
        } else {
            getMvpView().onError("Internet Connection Not Available");
        }

    }
}
