package com.apollopharmacy.mpospharmacistTest.ui.circleplan;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentDialogCircleBenefitBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.OTPDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CircleMemebershipCashbackPlanResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsRequest;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.CircleplanDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.model.PlanDetail;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CircleMembershipCashbackPlan extends BaseActivity implements CirclePlanCashbackMvpView {


    @Inject
    CirclePlanPresenter<CirclePlanCashbackMvpView> circleplanMvpPresenter;
    FragmentDialogCircleBenefitBinding fragmentDialogCircleBenefitBinding;
    private String customer_mobilenumber = null;
    ArrayList<CircleMemebershipCashbackPlanResponse.Category> circlecashbackplanmodel;
    CalculatePosTransactionRes calculatePosTransactionRes;
    CircleplanDetailsResponse circleplanDetailsResponse;
    List<PlanDetail> planDetail = null;
    String circleprice = "";
    private final int ACTIVITY_CIRCLE_CODE = 110;
    CorporateModel corporateModel;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList;
    boolean iscartitem;
    POSTransactionEntity posTransactionEntity;




    public static Intent getStartIntent(Context context, ArrayList<CircleMemebershipCashbackPlanResponse.Category> circlecashbackplan, double ordertotalamount, String mobilenumber, POSTransactionEntity entity, CorporateModel corporateModel, boolean iscartitem) {
        Intent intent = new Intent(context, CircleMembershipCashbackPlan.class);
        intent.putExtra("cashback_info", circlecashbackplan);
        intent.putExtra("orer_totalamount", ordertotalamount);
        intent.putExtra("customer_Mobile", mobilenumber);
        intent.putExtra("calculatepos_trasaction_request", entity);
        intent.putExtra("corporate_model", corporateModel);
        intent.putExtra("is_cartitem", iscartitem);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentDialogCircleBenefitBinding = DataBindingUtil.setContentView(this, R.layout.fragment_dialog_circle_benefit);
        getActivityComponent().inject(this);
        fragmentDialogCircleBenefitBinding.setCallback(circleplanMvpPresenter);
        circleplanMvpPresenter.onAttach(CircleMembershipCashbackPlan.this);
        setUp();
    }

    @Override
    protected void setUp() {
       fragmentDialogCircleBenefitBinding.circleBenifits.setVisibility(View.GONE);
        fragmentDialogCircleBenefitBinding.circlePlans.setVisibility(View.GONE);
        iscartitem = (Boolean) getIntent().getSerializableExtra("is_cartitem");
        Log.d("Plan Llist--->", String.valueOf(iscartitem));
        double order_total_amount = (double) getIntent().getSerializableExtra("orer_totalamount");
        customer_mobilenumber = (String) getIntent().getSerializableExtra("customer_Mobile");
        posTransactionEntity = (POSTransactionEntity) getIntent().getSerializableExtra("calculatepos_trasaction_request");

        corporateModel = (CorporateModel) getIntent().getSerializableExtra("corporate_model");
        if (corporateModel != null) {
            getCorporateList(corporateModel);
        }

        if (iscartitem == true) {
            circlecashbackplanmodel = (ArrayList<CircleMemebershipCashbackPlanResponse.Category>) getIntent().getSerializableExtra("cashback_info");
            Log.d("Plan Llist--->", new Gson().toJson(circlecashbackplanmodel));
           fragmentDialogCircleBenefitBinding.circleBenifits.setVisibility(View.VISIBLE);
            fragmentDialogCircleBenefitBinding.circlePlans.setVisibility(View.GONE);
            double oa_total_amount = 0;
            double cp_total_amount = 0;

            fragmentDialogCircleBenefitBinding.oaFmcgText.setVisibility(View.GONE);
            fragmentDialogCircleBenefitBinding.oaPharmaText.setVisibility(View.GONE);
            fragmentDialogCircleBenefitBinding.oaPrivatrlableText.setVisibility(View.GONE);

            fragmentDialogCircleBenefitBinding.cpFmcgText.setVisibility(View.GONE);
            fragmentDialogCircleBenefitBinding.cpPharmaText.setVisibility(View.GONE);
            fragmentDialogCircleBenefitBinding.cpPrivatelableText.setVisibility(View.GONE);

            for (CircleMemebershipCashbackPlanResponse.Category category : circlecashbackplanmodel) {
                if (category.getHeader().equalsIgnoreCase("OA")) {
                    if (category.getCategory().equalsIgnoreCase("FMCG")) {
                        fragmentDialogCircleBenefitBinding.oaFmcgText.setVisibility(View.VISIBLE);
                        fragmentDialogCircleBenefitBinding.oaFmcgText.setText("FMCG  " + String.valueOf(category.getItmeval()));
                        oa_total_amount = oa_total_amount + category.getItmeval();
                    } else if (category.getCategory().equalsIgnoreCase("PHARMA")) {
                        fragmentDialogCircleBenefitBinding.oaPharmaText.setVisibility(View.VISIBLE);
                        fragmentDialogCircleBenefitBinding.oaPharmaText.setText("PHARMA  " + String.valueOf(category.getItmeval()));
                        oa_total_amount = oa_total_amount + category.getItmeval();
                    } else if (category.getCategory().equalsIgnoreCase("PRIVATE LABEL")) {
                        fragmentDialogCircleBenefitBinding.oaPrivatrlableText.setVisibility(View.VISIBLE);
                        fragmentDialogCircleBenefitBinding.oaPrivatrlableText.setText("PRIVATE LABEL  " + String.valueOf(category.getItmeval()));
                        oa_total_amount = oa_total_amount + category.getItmeval();
                    } else {

                    }

                } else if (category.getHeader().equalsIgnoreCase("CP")) {
                    if (category.getCategory().equalsIgnoreCase("FMCG")) {
                        fragmentDialogCircleBenefitBinding.cpFmcgText.setVisibility(View.VISIBLE);
                        fragmentDialogCircleBenefitBinding.cpFmcgText.setText("FMCG  " + String.valueOf(category.getItmeval()));
                        cp_total_amount = cp_total_amount + category.getItmeval();
                    } else if (category.getCategory().equalsIgnoreCase("PHARMA")) {
                        fragmentDialogCircleBenefitBinding.cpPharmaText.setVisibility(View.VISIBLE);
                        fragmentDialogCircleBenefitBinding.cpPharmaText.setText("PHARMA  " + String.valueOf(category.getItmeval()));
                        cp_total_amount = cp_total_amount + category.getItmeval();
                    } else if (category.getCategory().equalsIgnoreCase("PRIVATE LABEL")) {
                        fragmentDialogCircleBenefitBinding.cpPrivatelableText.setVisibility(View.VISIBLE);
                        fragmentDialogCircleBenefitBinding.cpPrivatelableText.setText("PRIVATE LABEL  " + String.valueOf(category.getItmeval()));
                        cp_total_amount = cp_total_amount + category.getItmeval();
                    } else {

                    }
                } else {

                }
            }
            fragmentDialogCircleBenefitBinding.oaTotalAmountText.setText("Total Amount : " + String.valueOf(String.format("%.2f", oa_total_amount)));
            fragmentDialogCircleBenefitBinding.cpTotalamountText.setText("Total Amount : " + String.valueOf(String.format("%.2f", cp_total_amount)));

            double oa_totalsaving_percentage = oa_total_amount / order_total_amount;
            oa_totalsaving_percentage = oa_totalsaving_percentage * 100;
            double cp_totalsaving_percentage = cp_total_amount / order_total_amount;
            cp_totalsaving_percentage = cp_totalsaving_percentage * 100;

            fragmentDialogCircleBenefitBinding.oaTotalsavingsText.setText("Total Saving% : " + String.valueOf(String.format("%.2f", oa_totalsaving_percentage)));
            fragmentDialogCircleBenefitBinding.cpTotalsavingsText.setText("Total Saving% : " + String.valueOf(String.format("%.2f", cp_totalsaving_percentage)));
        } else {
          //  posTransactionEntity=(POSTransactionEntity) getIntent().getSerializableExtra("calculatepos_trasaction_request");
            getcircleplan();
        }


    }


    public void getCorporateList(CorporateModel corporateModel) {
        if (corporateModel.get_DropdownValue() != null && corporateModel.get_DropdownValue().size() > 0) {
            corporateList = new ArrayList<>();
            corporateList.addAll(corporateModel.get_DropdownValue());

        }
    }

    @Override
    public void onClickBackBtn() {
        Log.d("Call back function happening", "call back->0");
        onBackPressed();
    }

    @Override
    public void onCintinueBtn() {
       getcircleplan();
    }

    public void getcircleplan() {
        CircleplanDetailsRequest request = new CircleplanDetailsRequest();
        request.setActionid(0);
        request.setActionType("");
        request.setCardDesc("");
        request.setChannel(0);
        request.setCirclebenifits("");
        request.setCircleid("");
        request.setCircleplan("");
        request.setCircleprice("");
        request.setCorpcode("");
        request.setCounter(0);
        request.setCreatedDateTime("");
        request.setCustomerName("");
        request.setDataAreaID("");
        request.setEnddate("");
        request.setFlag(0);
        request.setItemid("");
        request.setLineNum(0);
        request.setOtherPayment(0);
        request.setPaymentCode(0);
        request.setPaymentType("");
        request.setPhoneNo("");
        request.setPicname("");
        request.setReplicated(0);
        request.setSiteid("");
        request.setStaffId("");
        request.setStartdate("");
        request.setTerminalId("");
        request.setTransactionDate("");
        request.setTransactionID("");
        request.setTransAmt(0);
        request.setUpload("");
        request.setUploaded("");
        request.setUserId("");
        circleplanMvpPresenter.circleplandetailsapicall(request);
    }

    @Override
    public void onSuccessCircleplanDetails(CircleplanDetailsResponse response) {
        if (response != null) {
            circleplanDetailsResponse = response;
            planDetail = circleplanDetailsResponse.getPlanDetails();
            fragmentDialogCircleBenefitBinding.circleBenifits.setVisibility(View.GONE);
            fragmentDialogCircleBenefitBinding.circlePlans.setVisibility(View.VISIBLE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


        }
    }

    @Override
    public void onFailureCircleplanDetails(CircleplanDetailsResponse response) {
        showMessage(response.getReturnMessage());
    }

    boolean validatecheckbox = false;

    @Override
    public void planonecheckboxaction() {
        circleprice = planDetail.get(0).getCircleprice();
        validatecheckbox = true;
        fragmentDialogCircleBenefitBinding.planOneCheckbox.setChecked(true);
        fragmentDialogCircleBenefitBinding.planTwoCheckbox.setChecked(false);
    }

    @Override
    public void plantwocheckboxaction() {
        circleprice = planDetail.get(1).getCircleprice();
        validatecheckbox = true;
        fragmentDialogCircleBenefitBinding.planOneCheckbox.setChecked(false);
        fragmentDialogCircleBenefitBinding.planTwoCheckbox.setChecked(true);
    }

    @Override
    public void ernollbuttonaction() {
        if (validatecheckbox) {
            circleplanMvpPresenter.sendSmsservice(customer_mobilenumber);
        } else {
            showMessage("Please Select Circle Plan First!");
        }
    }

    @Override
    public void generateotpSuccess(ModelMobileNumVerify response, String otp) {
        showOTPPopUp(otp);
    }

    public void showOTPPopUp(String otp) {
        OTPDialog dialogView = new OTPDialog(CircleMembershipCashbackPlan.this);
        dialogView.setOTP(otp);
        dialogView.setTitle("OTP");
        dialogView.setPositiveLabel("Ok");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.validateOTP()) {
                    dialogView.dismiss();
                    circleplanMvpPresenter.oncheckbatchstock(posTransactionEntity, circleprice);

                }
            }
        });
        dialogView.setNegativeLabel("Cancel");
        dialogView.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    @Override
    public void CheckBatchStockSuccess(CalculatePosTransactionRes response) {
        if (response != null) {
            circleplanMvpPresenter.circlrplantransaction(circleprice, response);
        }

    }

    @Override
    public void CheckBatchStockFailure(CalculatePosTransactionRes response) {
        showMessage(response.getReturnMessage());
    }

    @Override
    public void circleplantransactionSuccess(CalculatePosTransactionRes response, String circlecorpcode) {
        int position = 0;
        int tempposition = 0;
        // String circle_code=getDataManager().getGlobalJson().
        for (CorporateModel.DropdownValueBean row : corporateList) {
            if (row.getCode().contains(circlecorpcode)) {
                position = tempposition;
                break;
            }
            tempposition++;

        }

        CorporateModel.DropdownValueBean item = corporateList.get(position);

        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().itemsArrayList.addAll(response.getSalesLine());

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("corporate_info", item);
        bundle.putSerializable("selected_item", response.getSalesLine());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();


    }

    @Override
    public void circleplantransactionFailure(CalculatePosTransactionRes response) {
        showMessage(response.getReturnMessage());

    }

    @Override
    public void generateotpFailed(String errMsg) {
        showMessage(errMsg);
    }

    @Override
    public void ernollexitbuttonaction() {
        Log.d("Call back function happening", "call back");
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onBackPressed() {
        Log.d("Call back function happening", "call back");
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /*@Nullable
    @Override
    public Context getContext() {
        return this;
    }*/

}
