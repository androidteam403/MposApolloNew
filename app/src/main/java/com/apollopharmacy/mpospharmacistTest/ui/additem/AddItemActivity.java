package com.apollopharmacy.mpospharmacistTest.ui.additem;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityAddItemBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogNoStockAvailableBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.ExitInfoDialogBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.adapter.ItemTouchHelperCallback;
import com.apollopharmacy.mpospharmacistTest.ui.additem.adapter.MainRecyclerAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CircleMemebershipCashbackPlanResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetPostOnlineOrderApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetSMSPayAPIResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.HdfcLinkGenerateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OmsAddNewItemResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.POSTransactionEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidReq;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentVoidRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PhonepeGenerateQrCodeResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.TenderLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.payadapter.PayActivityAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.additem.payadapter.PayAdapterModel;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.circleplan.CircleMembershipCashbackPlan;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiRequest;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.GetOnlineCorporateListApiResponse;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.customermaster.model.ModelMobileNumVerify;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.OrderSummaryActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.HBPConfigResponse;
import com.apollopharmacy.mpospharmacistTest.ui.presenter.CustDocEditMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.ViewAnimationUtils;
import com.eze.api.EzeAPI;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class AddItemActivity extends BaseActivity implements AddItemMvpView, CustDocEditMvpView {

    @Inject
    AddItemMvpPresenter<AddItemMvpView> mPresenter;
    private ActivityAddItemBinding addItemBinding;
    private MainRecyclerAdapter medicinesDetailAdapter;
    private final int ACTIVITY_ADD_PRODUCT_CODE = 102;
    private final int ACTIVITY_CIRCLE_PLAN_CODE = 107;
    private final int ACTIVITY_EPRESCRIPTIONLIST_CODE = 120;
    private GetCustomerResponse.CustomerEntity customerEntity;
    private DoctorSearchResModel.DropdownValueBean doctorEntity;
    private SalesOriginResModel.DropdownValueBean salesEntity;
    private CorporateModel.DropdownValueBean corporateEntity;
    private PayActivityAdapter payActivityAdapter;
    private ArrayList<PayAdapterModel> arrPayAdapterModel = new ArrayList<>();

    private final int CUSTOMER_SEARCH_ACTIVITY_CODE = 103;
    private final int DOCTOR_SEARCH_ACTIVITY_CODE = 104;
    private final int CORPORATE_SEARCH_ACTIVITY_CODE = 105;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 106;
    private TransactionIDResModel transactionIdModel = null;
    private OrderPriceInfoModel orderPriceInfoModel = new OrderPriceInfoModel();
    private PaymentMethodModel paymentMethodModel = new PaymentMethodModel();
    private final int REQUEST_CODE_INITIALIZE = 10001;
    private final int REQUEST_CODE_PREPARE = 10002;
    private final int REQUEST_CODE_WALLET_TXN = 10003;
    private final int REQUEST_CODE_CHEQUE_TXN = 10004;
    private final int REQUEST_CODE_SALE_TXN = 10006;
    private final int REQUEST_CODE_CASH_BACK_TXN = 10007;
    private final int REQUEST_CODE_CASH_AT_POS_TXN = 10008;
    private final int REQUEST_CODE_CASH_TXN = 10009;
    private final int REQUEST_CODE_UPI = 10018;
    private boolean isExpand = true;
    private int rotationAngle = 180;
    private CorporateModel corporateModel;
    private int flag = 0;
    private PharmacyStaffApiRes pharmacyStaffApiRes;
    double remaindValue = 0.0;
    String saleslistData;
    private String prgData = null;

    boolean iscartitem = false;
    CustomerDataResBean customerDataResBean;
    // boolean isomsorder = false;


    private String selectedPatient;
    private ArrayList<CircleMemebershipCashbackPlanResponse.Category> circlecashbackplan = null;

    private Boolean isCameFromOrderDetailsScreenActivity;

    //changes made by naveen
    private boolean isOnlineOrder;
    private EPrescriptionModelClassResponse ePrescriptionModelClassResponse;
    private List<EPrescriptionMedicineResponse> ePrescriptionMedicineResponseList;
    private String onlineTransactionId;
    private CalculatePosTransactionRes unPostedTransactionResponseBody;
    private boolean isPrescription;
    private boolean isCorporatePayment = false;
    /*public static Intent getStartIntent(Context context,List<CircleMemebershipCashbackPlanResponse.Category> circlecashbackmodel)
    {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("cashback_info",circlecashbackmodel);
        return intent;
    }*/

    public static Intent getStartIntent(Context context, boolean isBack, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("is_back", isBack);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("corporate_model", corporateModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, boolean isBack, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, PharmacyStaffApiRes staffApiRes, String trackingData) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("is_back", isBack);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("corporate_model", corporateModel);
        intent.putExtra("staff_avail_amt", staffApiRes);
        intent.putExtra("prg_track", trackingData);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, boolean isBack, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, PharmacyStaffApiRes staffApiRes, String trackingData, String saleslistData, boolean isUhidSelected, boolean isCorporatePayment, String selectedPatient) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("is_back", isBack);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("corporate_model", corporateModel);
        intent.putExtra("staff_avail_amt", staffApiRes);
        intent.putExtra("prg_track", trackingData);
        intent.putExtra("sales_list_data", saleslistData);
        intent.putExtra("IS_UHID_SELECTED", isUhidSelected);
        intent.putExtra("IS_CORPORATE_PAYMENT", isCorporatePayment);
        intent.putExtra("SELECTED_PATIENT", selectedPatient);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, CalculatePosTransactionRes calculatePosTransactionRes) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("corporate_model", corporateModel);
        intent.putExtra("calculatedPosRes", calculatePosTransactionRes);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, CalculatePosTransactionRes calculatePosTransactionRes, boolean isEPrescriptionScreen, EPrescriptionModelClassResponse ePrescriptionModelClassResponse, List<EPrescriptionMedicineResponse> ePrescriptionMedicineResponseList) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("corporate_model", corporateModel);
        intent.putExtra("calculatedPosRes", calculatePosTransactionRes);
        intent.putExtra("E-PRESCRIPTION_SCREEN", isEPrescriptionScreen);
        intent.putExtra("ePrescription_model_class_response", ePrescriptionModelClassResponse);
        intent.putExtra("ePrescription_medicine_response_list", (Serializable) ePrescriptionMedicineResponseList);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, CalculatePosTransactionRes calculatePosTransactionRes) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("calculatedPosRes", calculatePosTransactionRes);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, boolean isBack, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, PharmacyStaffApiRes staffApiRes, String availableAmt, String prgTrackingData, String saleslistData, boolean isUhidSelected, boolean isCorporatePayment, String selectedPatient) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("is_back", isBack);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("corporate_model", corporateModel);
        intent.putExtra("staff_avail_amt", staffApiRes);
        intent.putExtra("prg_track", prgTrackingData);
        intent.putExtra("sales_list_data", saleslistData);
        intent.putExtra("IS_UHID_SELECTED", isUhidSelected);
        intent.putExtra("IS_CORPORATE_PAYMENT", isCorporatePayment);
        intent.putExtra("SELECTED_PATIENT", selectedPatient);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, ArrayList<SalesLineEntity> salesLineEntities, GetCustomerResponse.CustomerEntity customerEntity, OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem, CustomerDataResBean customerDataResBean, TransactionIDResModel transactionIDResModel, boolean is_omsorder, CorporateModel.DropdownValueBean item, DoctorSearchResModel.DropdownValueBean doctor, boolean isCameFromOrderDetailsScreenActivity) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("sales_list_data", salesLineEntities);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("orderinfo_item", orderinfoitem);
        intent.putExtra("customerbean_info", customerDataResBean);
        intent.putExtra("transaction_id", transactionIDResModel);
        intent.putExtra("is_omsorder", is_omsorder);
        intent.putExtra("corporate_info", item);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("IS_CAME_FROM_ORDER_DETAILS_SCREEN_ACTIVITY", isCameFromOrderDetailsScreenActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, ArrayList<SalesLineEntity> salesLineEntities, GetCustomerResponse.CustomerEntity customerEntity, OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem, CustomerDataResBean customerDataResBean, TransactionIDResModel transactionIDResModel, boolean is_omsorder, CorporateModel.DropdownValueBean item, DoctorSearchResModel.DropdownValueBean doctor) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("sales_list_data", salesLineEntities);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("orderinfo_item", orderinfoitem);
        intent.putExtra("customerbean_info", customerDataResBean);
        intent.putExtra("transaction_id", transactionIDResModel);
        intent.putExtra("is_omsorder", is_omsorder);
        intent.putExtra("corporate_info", item);
        intent.putExtra("doctor_info", doctor);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntents(Context context, ArrayList<SalesLineEntity> salesLineEntities, GetCustomerResponse.CustomerEntity customerEntity, OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem, CustomerDataResBean customerDataResBean, TransactionIDResModel transactionIDResModel, boolean is_online, CorporateModel.DropdownValueBean item, DoctorSearchResModel.DropdownValueBean doctor, EPrescriptionModelClassResponse ePrescriptionModelClassResponse, List<EPrescriptionMedicineResponse> ePrescriptionMedicineResponseList) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("sales_list_data", salesLineEntities);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("orderinfo_item", orderinfoitem);
        intent.putExtra("customerbean_info", customerDataResBean);
        intent.putExtra("transaction_id", transactionIDResModel);
        intent.putExtra("is_online", is_online);
        intent.putExtra("corporate_info", item);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("ePrescription_model_class_response", ePrescriptionModelClassResponse);
        intent.putExtra("ePrescription_medicine_response_list", (Serializable) ePrescriptionMedicineResponseList);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntents(Context context, ArrayList<SalesLineEntity> salesLineEntities, GetCustomerResponse.CustomerEntity customerEntity, OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem, CustomerDataResBean customerDataResBean, boolean is_online, CorporateModel.DropdownValueBean item, DoctorSearchResModel.DropdownValueBean doctor, EPrescriptionModelClassResponse ePrescriptionModelClassResponse, List<EPrescriptionMedicineResponse> ePrescriptionMedicineResponseList, boolean isCameFromEprescriptionMedicineDetailsActivity) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("sales_list_data", salesLineEntities);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("orderinfo_item", orderinfoitem);
        intent.putExtra("customerbean_info", customerDataResBean);
        intent.putExtra("is_online", is_online);
        intent.putExtra("corporate_info", item);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("ePrescription_model_class_response", ePrescriptionModelClassResponse);
        intent.putExtra("ePrescription_medicine_response_list", (Serializable) ePrescriptionMedicineResponseList);
        intent.putExtra("IS_CAME_FROM_EPRESCRIPTION_MEDICINE_DETAILS_ACTIVITY", isCameFromEprescriptionMedicineDetailsActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double diagonalInches = UiUtils.displaymetrics(this);
        Log.i("activityTest Oncreate method-->", "test");
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }

        addItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddItemActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        if (mPresenter.isMposV1Flow()) {
            addItemBinding.detailsLayout.uploadApi.setVisibility(View.VISIBLE);
        } else {
            addItemBinding.detailsLayout.uploadApi.setVisibility(View.GONE);
        }
        addItemBinding.siteName.setText(mPresenter.getStoreName());
        addItemBinding.siteId.setText(mPresenter.getStoreId());
        addItemBinding.terminalId.setText(mPresenter.getTerminalId());
        addItemBinding.setCallback(mPresenter);
        addItemBinding.setCall(this);
        addItemBinding.setClearMvpView(this);
        addItemBinding.setPaymentMode(paymentMethodModel);
        addItemBinding.setGlobalConfiguration(mPresenter.getGlobalConfiguration());
        Constant.getInstance().vendorcredit = false;

        mPresenter.getGlobalConfig();
        mPresenter.getHBPConfig();
        customerDataResBean = new CustomerDataResBean();
        if (getIntent() != null) {
            selectedPatient = (String) getIntent().getStringExtra("SELECTED_PATIENT");
            isCorporatePayment = (Boolean) getIntent().getBooleanExtra("IS_CORPORATE_PAYMENT", false);
            clearallBtnVisibilitybyCorpPayment(isCorporatePayment);
            isPrescription = (Boolean) getIntent().getBooleanExtra("E-PRESCRIPTION_SCREEN", false);
            if (isPrescription) {
                ePrescriptionModelClassResponse = (EPrescriptionModelClassResponse) getIntent().getSerializableExtra("ePrescription_model_class_response");
                ePrescriptionMedicineResponseList = (List<EPrescriptionMedicineResponse>) getIntent().getSerializableExtra("ePrescription_medicine_response_list");
            }
            boolean isUhidSelected = (Boolean) getIntent().getBooleanExtra("IS_UHID_SELECTED", false);
            if (isUhidSelected) {
                addItemBinding.detailsLayout.prgTrackingEdit.setEnabled(false);
            }

            customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
            if (customerEntity != null) {
                addItemBinding.setCustomer(customerEntity);
                if (customerEntity.getCardNo() == null || customerEntity.getCardNo().equalsIgnoreCase("")) {
                    addItemBinding.detailsLayout.prgTrackingEdit.setText("");
                } else {
                    addItemBinding.detailsLayout.prgTrackingEdit.setText(customerEntity.getCardNo());
                    prgData = customerEntity.getCardNo();

                }
                if (customerEntity.getTier() == null || customerEntity.getTier().equalsIgnoreCase("")) {
                    addItemBinding.detailsLayout.tier.setText("--");
                } else {
                    addItemBinding.detailsLayout.tier.setText(customerEntity.getTier());
                }
                if (customerEntity.getAvailablePoints() == null || customerEntity.getAvailablePoints().equalsIgnoreCase("")) {
                    addItemBinding.detailsLayout.availablePoints.setText("--");
                } else {
                    addItemBinding.detailsLayout.availablePoints.setText(customerEntity.getAvailablePoints());
                }
            }

            pharmacyStaffApiRes = (PharmacyStaffApiRes) getIntent().getSerializableExtra("staff_avail_amt");
            if (pharmacyStaffApiRes != null) {
                double availableAmount = Double.parseDouble(pharmacyStaffApiRes.getTotalBalance()) - Double.parseDouble(pharmacyStaffApiRes.getUsedBalance());
                addItemBinding.detailsLayout.availablePoints.setText(String.valueOf(availableAmount));
            }
            if (customerEntity.getCardNo() == null || customerEntity.getCardNo().equalsIgnoreCase("")) {
                prgData = getIntent().getStringExtra("prg_track");
                if (customerEntity.getCardNo() == null || customerEntity.getCardNo().equalsIgnoreCase("")) {
                    addItemBinding.detailsLayout.prgTrackingEdit.setText(prgData);
                }
            }

            doctorEntity = (DoctorSearchResModel.DropdownValueBean) getIntent().getSerializableExtra("doctor_info");
            if (doctorEntity != null) {
                addItemBinding.setDoctor(doctorEntity);
            }
            corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
            if (corporateEntity != null) {
                if (corporateEntity.getPrg_Tracking() != null && !corporateEntity.getPrg_Tracking().isEmpty()) {
                    prgData = corporateEntity.getPrg_Tracking();
                }
                addItemBinding.setCorporate(corporateEntity);
            }

            corporateModel = (CorporateModel) getIntent().getSerializableExtra("corporate_model");
            transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
            if (transactionIdModel != null) {
                addItemBinding.setTransaction(transactionIdModel);
            }
            saleslistData = getIntent().getStringExtra("sales_list_data");


        }
        System.out.println("Producttotal tax-->" + Singletone.getInstance().itemsArrayList.size());
        addItemBinding.setProductCount(Singletone.getInstance().itemsArrayList.size());
        addItemBinding.setItemsCount(Singletone.getInstance().itemsArrayList.size());
        addItemBinding.detailsLayout.detailsExpanCollapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    isExpand = false;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(addItemBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.collapse(addItemBinding.detailsLayout.customerDoctorLayout);
                } else {
                    isExpand = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(addItemBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.expand(addItemBinding.detailsLayout.customerDoctorLayout);
                }
            }
        });

        boolean isAddAllowed = true;
        boolean isDeleteAllowed = true;
        boolean isEditAllowed = true;
//
//        if (getCalculatedPosTransactionRes() != null && getCalculatedPosTransactionRes().getCorpCode() != null) {
//            String corpCode = getCalculatedPosTransactionRes().getCorpCode();
//            GetGlobalConfingRes globalConfingRes = mPresenter.getGlobalConfiguration();
//            GetGlobalConfingRes.OMSVendorWiseConfigration omsVendorWiseConfigration;
//            for (GetGlobalConfingRes.OMSVendorWiseConfigration o : globalConfingRes.getOMSVendorWiseConfigration()) {
//                if (o.getCorpCode().equalsIgnoreCase(corpCode)) {
//                    isAddAllowed = o.getAllowProductAdd();
//                    isDeleteAllowed = o.getAllowVoidProduct();
//                    isEditAllowed = o.getAllowChangeQTY();
//                    break;
//                }
//            }
//        }


        addItemBinding.medicineRecycle.setLayoutManager(new LinearLayoutManager(this));
        medicinesDetailAdapter = new MainRecyclerAdapter(this, Singletone.getInstance().itemsArrayList, isDeleteAllowed, isAddAllowed, isEditAllowed);

        addItemBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);
        ItemTouchHelperExtension.Callback mCallback = new ItemTouchHelperCallback();
        ItemTouchHelperExtension mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView(addItemBinding.medicineRecycle);
        medicinesDetailAdapter.setItemTouchHelperExtension(mItemTouchHelper);
        medicinesDetailAdapter.setAddItemMvpView(this);
        addItemBinding.setProductCount(Singletone.getInstance().itemsArrayList.size());
        addItemBinding.setItemsCount(Singletone.getInstance().itemsArrayList.size());
        payActivityAdapter = new PayActivityAdapter(this, arrPayAdapterModel, this);
        RecyclerView.LayoutManager mLayoutManagerOne = new LinearLayoutManager(this);
        addItemBinding.payAmount.setLayoutManager(mLayoutManagerOne);
        addItemBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        addItemBinding.payAmount.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        addItemBinding.payAmount.setAdapter(payActivityAdapter);


        if (getIntent() != null) {
            calculatePosTransactionRes = (CalculatePosTransactionRes) getIntent().getSerializableExtra("calculatedPosRes");
            if (calculatePosTransactionRes != null) {
                onSuccessCalculatePosTransaction(calculatePosTransactionRes);
            }
        }

        addItemBinding.detailsLayout.prgTrackingEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                addItemBinding.getCorporate().setPrg_Tracking(editable.toString());
                addItemBinding.detailsLayout.prgTrackingEdit.setSelection(addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().length());
            }
        });
        if (addItemBinding.getCorporate() != null) {
            addItemBinding.getCorporate().setPrg_Tracking(prgData);
        }
        addItemBinding.detailsLayout.prgTrackingEdit.setSelection(addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().length());
        mPresenter.checkAllowedPaymentMode(paymentMethodModel);

        if (getIntent() != null && (CustomerDataResBean) getIntent().getSerializableExtra("customerbean_info") != null) {
            boolean is_omsorder = (boolean) getIntent().getBooleanExtra("is_omsorder", false);

            addItemBinding.setIsOMSOrder(is_omsorder);

            boolean isOnline = (boolean) getIntent().getBooleanExtra("is_online", false);
            boolean isCameFromEprescriptionMedicineDetailsActivity = (boolean) getIntent().getBooleanExtra("IS_CAME_FROM_EPRESCRIPTION_MEDICINE_DETAILS_ACTIVITY", false);
            if (isCameFromEprescriptionMedicineDetailsActivity) {
                addItemBinding.detailsLayout.prgTrackingEdit.setEnabled(false);
            }
            //
            this.isOnlineOrder = isOnline;
            if (is_omsorder == true) {
                boolean itemNotFound = true;
                ArrayList<SalesLineEntity> itemsArrayList = (ArrayList<SalesLineEntity>) getIntent().getSerializableExtra("sales_list_data");
                if (itemsArrayList != null) {

                    Constant.getInstance().isomsorder = true;
                    Constant.getInstance().isomsorder_check = true;


                    Singletone.getInstance().itemsArrayList.clear();
                    Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);

                    corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
                    customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
                    OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem = (OMSTransactionHeaderResModel.OMSHeaderObj) getIntent().getSerializableExtra("orderinfo_item");
                    customerDataResBean = (CustomerDataResBean) getIntent().getSerializableExtra("customerbean_info");
                    // Log.d("corpoarte data", corporateEntity.getCode());
                    //if(customerDataResBean != null)
                    //{
                    customerDataResBean.setREFNO(orderinfoitem.getREFNO());
                    //}
                    addItemBinding.setCustomer(customerEntity);
                    addItemBinding.setCorporate(corporateEntity);
                    addItemBinding.detailsLayout.prgTrackingEdit.setText(orderinfoitem.getREFNO());
                    transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
                    if (transactionIdModel != null) {
                        addItemBinding.setTransaction(transactionIdModel);
                    }
                    mPresenter.checkAllowedPaymentMode(paymentMethodModel);
                    mPresenter.checkProductTrackingWise();
                    mPresenter.calculatePosTransaction();
                    medicinesDetailAdapter.notifyDataSetChanged();
                }

            } else if (isOnline) {
                ArrayList<SalesLineEntity> itemsArrayList = (ArrayList<SalesLineEntity>) getIntent().getSerializableExtra("sales_list_data");
                if (itemsArrayList != null) {
                    ePrescriptionModelClassResponse = (EPrescriptionModelClassResponse) getIntent().getSerializableExtra("ePrescription_model_class_response");
                    ePrescriptionMedicineResponseList = (List<EPrescriptionMedicineResponse>) getIntent().getSerializableExtra("ePrescription_medicine_response_list");
//                    Constant.getInstance().isomsorder = true;
//                    Constant.getInstance().isomsorder_check = true;


                    Singletone.getInstance().itemsArrayList.clear();
                    Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);

                    corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
                    customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
                    OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem = (OMSTransactionHeaderResModel.OMSHeaderObj) getIntent().getSerializableExtra("orderinfo_item");
                    customerDataResBean = (CustomerDataResBean) getIntent().getSerializableExtra("customerbean_info");
                    // Log.d("corpoarte data", corporateEntity.getCode());
                    //if(customerDataResBean != null)
                    //{
                    customerDataResBean.setREFNO(orderinfoitem.getREFNO());
                    //}
                    addItemBinding.setCustomer(customerEntity);
                    addItemBinding.setCorporate(corporateEntity);
                    addItemBinding.detailsLayout.prgTrackingEdit.setText(orderinfoitem.getREFNO());
//                    transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
//                    if (transactionIdModel != null) {
//                        addItemBinding.setTransaction(transactionIdModel);
//                    }

                    mPresenter.checkAllowedPaymentMode(paymentMethodModel);
//                    mPresenter.checkProductTrackingWise();
                    showLoading();
                    mPresenter.getUnpostedTransaction();
//                    mPresenter.calculatePosTransaction();

//                    medicinesDetailAdapter.notifyDataSetChanged();
                }

            }
        }

        //mPresenter.calculatePosTransaction();
    }

    @Override
    public void onClickClearAllBtn() {
        if (paymentDoneAmount == 0.0) {
            onClearAll();
        } else {
            partialPaymentDialog("Alert!", "Partial Payment done,Kindly void payment lines");
        }
    }

    @Override
    public String getPrgTracking() {
        return addItemBinding.detailsLayout.prgTrackingEdit.getText().toString();
    }

    @Override
    public void onBackPressed() {
//        if (mPresenter.getGlobalConfiguration().getMPOSVersion().equals("2")) {
//            super.onBackPressed();
//        } else {
        if (addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode()) {
            addItemBinding.setIsPaymentMode(false);
            paymentMethodModel.setGenerateBill(false);
            if (isDonePayment()) {
                addItemBinding.setIsPaymentMode(true);
                paymentMethodModel.setGenerateBill(true);
                alertBackDialog();
            } else if (paymentMethodModel.isBalanceAmount() && paymentMethodModel.getBalanceAmount() < 0) {
                addItemBinding.setIsPaymentMode(true);
                paymentMethodModel.setGenerateBill(true);
                alertBackDialog();
            }
        } else {
            if (paymentDoneAmount == 0.0) {
                alertDialog();
            } else {
                partialPaymentDialog("Alert!", "Partial Payment done,Kindly void payment lines");
            }

        }
        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addItemBinding.imageView.setVisibility(View.GONE);
//        }
    }

    private void alertBackDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setPositiveLabel("Ok");
        dialogView.setSubtitle("Generate Bill Before Exit");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });

        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        dialogView.show();
    }


    @Override
    public void onManualSearchClick() {
        if (customerEntity.getCardName().isEmpty()) {
            ExitInfoDialog dialogView = new ExitInfoDialog(this);
            dialogView.setTitle("");
            dialogView.setPositiveLabel("OK");
            dialogView.setSubtitle("Kindly Select Customer");
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                }
            });
            dialogView.show();
        } else if (doctorEntity.getDisplayText().isEmpty()) {
            ExitInfoDialog dialogView = new ExitInfoDialog(this);
            dialogView.setTitle("");
            dialogView.setPositiveLabel("OK");
            dialogView.setSubtitle("Kindly Select Doctor");
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                }
            });
            dialogView.show();
        } else {
            startActivityForResult(ProductListActivity.getStartIntent(this, getCorporateModule(), getTransactionModule(), "1"), ACTIVITY_ADD_PRODUCT_CODE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

    }


    @Override
    public void Posttratransactionrequest(POSTransactionEntity entity) {
        POSTransactionEntity posTransactionEntity = entity;
        startActivityForResult(CircleMembershipCashbackPlan.getStartIntent(this, getCirclecashbackplan(), orderPriceInfoModel.OrderTotalAmount, customerEntity.getMobileNo(), posTransactionEntity, corporateModel, iscartitem), ACTIVITY_CIRCLE_PLAN_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /*@Override
    public void onEprescriptionButtonevent() {
        startActivityForResult(EprescriptionOrderListActivity.getStartIntent(this, customerEntity, corporateModel), ACTIVITY_EPRESCRIPTIONLIST_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }*/

    @Override
    public void onCircleplanButtonevent() {
        if (Singletone.getInstance().itemsArrayList.size() > 0) {
            iscartitem = true;
            mPresenter.CircleplanCashbackapicall();
        } else {
            iscartitem = false;
            mPresenter.Posttransactionrequest();
        }

    }

    @Override
    public void CirclecashbackplanSuccess(CircleMemebershipCashbackPlanResponse response) {
        if (response != null) {
            circlecashbackplan = response.getCategoryList();
            mPresenter.Posttransactionrequest();
            //startActivityForResult(CircleMembershipCashbackPlan.getStartIntent(this, getCirclecashbackplan(), orderPriceInfoModel.OrderTotalAmount, customerEntity.getMobileNo(), calculatePosTransactionRes, corporateModel), ACTIVITY_CIRCLE_PLAN_CODE);
            // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

    }

    @Override
    public ArrayList<CircleMemebershipCashbackPlanResponse.Category> getCirclecashbackplan() {
        return circlecashbackplan;
    }

    @Override
    public void CirclecashbackplanFailure(CircleMemebershipCashbackPlanResponse response) {
        showMessage(response.getReturnMessage());
    }


    @Override
    public void onVoiceSearchClick() {
        requestAudioPermissions();
    }

    @Override
    public void onBarCodeSearchClick() {
        startActivityForResult(ProductListActivity.getStartIntent(this, getCorporateModule(), getTransactionModule(), "3"), ACTIVITY_ADD_PRODUCT_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onClickActionBarBack() {
        onBackPressed();
    }

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            }
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(ProductListActivity.getStartIntent(this, getCorporateModule(), getTransactionModule(), "2"), ACTIVITY_ADD_PRODUCT_CODE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    private boolean clearItems;

    @Override
    public void onClearAll() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Are you Sure?");
        dialogView.setPositiveLabel("Yes");
        dialogView.setSubtitle("Do you want to clear order Items");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.getInstance().isomsorder = false;
                Constant.getInstance().isomsorder_check = false;

                dialogView.dismiss();
                mPresenter.clearAllVoidTransaction();
                deletePayAdapterData();
                addItemBinding.detailsLayout.corpoEdit.setVisibility(View.VISIBLE);
            }
        });
        dialogView.setNegativeLabel("No");
        dialogView.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.show();

    }

    private void deletePayAdapterData() {
        if (arrPayAdapterModel.size() > 0) {
            arrPayAdapterModel.clear();
            calculatePosTransactionRes.getTenderLine().clear();
            clearItems = true;
            payActivityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccessClearAll() {
        clearOrderData();
        medicinesDetailAdapter.notifyDataSetChanged();
        addItemBinding.setProductCount(Singletone.getInstance().itemsArrayList.size());
        addItemBinding.setItemsCount(Singletone.getInstance().itemsArrayList.size());
    }

    @Override
    public void partialPaymentDialog(String title, String description) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle(title);
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(description);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    @Override
    public void onPayButtonClick() {
        String corpCode = calculatePosTransactionRes.getCorpCode();
        if (corpCode != null && !corpCode.isEmpty()) {
            int flagChronic = 0;
            int flagBabyCare = 0;
            int pos = -1;
            System.out.println("customer name-->" + customerEntity.getCardName());
            System.out.println("customer name-->" + customerEntity.getMobileNo());
            // if ((customerEntity.getMobileNo() != null && customerEntity.getCardName() != null) && (!customerEntity.getMobileNo().equalsIgnoreCase("") && !customerEntity.getCardName().equalsIgnoreCase(""))) {
            for (int i = 0; i < Singletone.getInstance().itemsArrayList.size(); i++) {
                if (Singletone.getInstance().itemsArrayList.get(i).getDiseaseType().equalsIgnoreCase("Chronic") || Singletone.getInstance().itemsArrayList.get(i).getSubCategory().equalsIgnoreCase("BABY CARE")) {
                    flagBabyCare++;
                    if (Singletone.getInstance().itemsArrayList.get(i).getRemainderDays() > 0.1) {
                        flagChronic++;
                        pos = i;
                        break;
                    }
                } else {
                    if (Singletone.getInstance().itemsArrayList.get(i).getRemainderDays() > 0.1) {
                        flagChronic++;
                        pos = i;
                        break;
                    }
                }
            }

            if (flagChronic == 1 || flagBabyCare == 0) {
                if (pos != -1) {
                    remaindValue = Singletone.getInstance().itemsArrayList.get(pos).getRemainderDays();
                }
                if (addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode()) {
                    paymentMethodModel.setGenerateBill(true);
                }
                updatePayedAmount(calculatePosTransactionRes);
                addItemBinding.setIsPaymentMode(true);
                setEnableEditTextChange();
            } else {
                alertQuantityError("Please Enter Remainder Days!!");
            }
      /* }
        else
        {
            Toast.makeText(this,"Please Enter Customer Details",
                    Toast.LENGTH_LONG).show();
        }*/
        } else {
            alertQuantityError("Please select the corporate");
        }
    }

    @Override
    public void onCustomerEditClick() {
        // System.out.println("isomsorder value-->"+Constant.getInstance().isomsorder);
        Log.i("IsOmsOrder-->", String.valueOf(Constant.getInstance().isomsorder));
        if (customerEntity != null && Constant.getInstance().isomsorder == false) {
            startActivityForResult(CustomerDetailsActivity.getStartIntent(this, customerEntity), CUSTOMER_SEARCH_ACTIVITY_CODE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public void onDoctorEditClick() {
        startActivityForResult(DoctorDetailsActivity.getStartIntent(this, doctorEntity, salesEntity), DOCTOR_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCorporateEditClick() {
        if (Constant.getInstance().isomsorder == false) {
            startActivityForResult(CorporateDetailsActivity.getStartIntent(this, corporateEntity, corporateModel), CORPORATE_SEARCH_ACTIVITY_CODE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getCashPaymentAmount() {
        return addItemBinding.cashPaymentAmountEdit.getText().toString();
    }


    private void alertPrescriptionDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setPositiveLabel("Ok");
        dialogView.setSubtitle("Prescription is Mandatory for H1 Scheduled Drug");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }


    @Override
    public String getCardPaymentAmount() {
        return addItemBinding.cardPaymentAmountEditText.getText().toString();
    }

    @Override
    public String getOneApolloPoints() {
        return addItemBinding.getValidatePoints().getAvailablePoints();
    }

    @Override
    public String getOneApolloOtp() {
        return addItemBinding.otpView.getText().toString();
    }

    @Override
    public GetCustomerResponse.CustomerEntity getCustomerModule() {
        return customerEntity;
    }

    @Override
    public DoctorSearchResModel.DropdownValueBean getDoctorModule() {
        return doctorEntity;
    }

    @Override
    public CorporateModel.DropdownValueBean getCorporateModule() {
        return corporateEntity;
    }

    @Override
    public TransactionIDResModel getTransactionModule() {
        return transactionIdModel;
    }

    @Override
    public OrderPriceInfoModel getOrderPriceInfoModel() {
        return orderPriceInfoModel;
    }

    @Override
    public PaymentMethodModel getPaymentMethod() {
        return paymentMethodModel;
    }

    ValidatePointsResModel.OneApolloProcessResultEntity validatePointsResModel;

    @Override
    public ValidatePointsResModel.OneApolloProcessResultEntity getValidateOneApolloPoints() {
        return validatePointsResModel;
    }

    @Override
    public ArrayList<SalesLineEntity> getSelectedProducts() {
        return Singletone.getInstance().itemsArrayList;
    }

    @Override
    public CustomerDataResBean getCustomerDataResbean() {
        return customerDataResBean;
    }

    @Override
    public CalculatePosTransactionRes getCalculatedPosTransactionRes() {
        calculatePosTransactionRes.setTrackingRef(getCorporateModule().getPrg_Tracking());
        String salesCode;
        String codeDes = calculatePosTransactionRes.getDoctorName();
        String[] parts = codeDes.split("-");
        if (parts.length > 1) {
            salesCode = parts[1];
        } else {
            salesCode = codeDes;
        }
        calculatePosTransactionRes.setDoctorName(salesCode);
        calculatePosTransactionRes.setMobileNO(customerEntity.getMobileNo());
        calculatePosTransactionRes.setCustAccount(customerEntity.getCustId());
//        if (isCameFromOrderDetailsScreenActivity) {
//            calculatePosTransactionRes.setIsMPOSBill(2);
//        } else {
        calculatePosTransactionRes.setIsMPOSBill(1);
//        }

        if (selectedPatient != null) {
            if (selectedPatient.contains("-")) {
                String[] selecetdPatientCode = selectedPatient.split("-");
                calculatePosTransactionRes.setPatientID(selecetdPatientCode[0]);
            }
        }
        return calculatePosTransactionRes;
    }

    @Override
    public void setErrorCardPaymentAmountEditText(String message) {
        showMessage(message);
    }

    @Override
    public void setErrorCashPaymentAmountEditText(String message) {
        showMessage(message);
    }

    @Override
    public void setErrorOneApolloOtpEditText(String message) {
        showMessage(message);
    }

    private boolean onCardMode;


    @Override
    public void onClickCardPaymentBtn() {
        onWalletClick = false;
        onCardMode = true;
        onHdfcPayMode = false;
        onCashmode = false;
        onSmspayMode = false;
        onVendorPayMode = false;
        onCodPayMode = false;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(true);
                paymentMethodModel.setHdfcPayMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setSmsPayMode(false);
                paymentMethodModel.setVendorPayMode(false);
                paymentMethodModel.setCodPayMode(false);
                addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(true);
            paymentMethodModel.setHdfcPayMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setSmsPayMode(false);
            paymentMethodModel.setVendorPayMode(false);
            paymentMethodModel.setCodPayMode(false);
            addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
        }

    }

    private boolean onHdfcPayMode;

    @Override
    public void onClickHdfcPayBtn() {
        System.out.println("yes it is coming paysms method." + "--");
        onWalletClick = false;
        onCardMode = false;
        onHdfcPayMode = true;
        onCashmode = false;
        onSmspayMode = false;
        onVendorPayMode = false;
        onCodPayMode = false;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setHdfcPayMode(true);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setSmsPayMode(false);
                paymentMethodModel.setVendorPayMode(false);
                paymentMethodModel.setCodPayMode(false);
                mPresenter.showHdfcPaymentDialog();
                //showsms("PhonePe Transaction", true, walletServiceReq);

                //addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setHdfcPayMode(true);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setSmsPayMode(false);
            paymentMethodModel.setVendorPayMode(false);
            paymentMethodModel.setCodPayMode(false);
            mPresenter.showHdfcPaymentDialog();

            // showsmsPaymentDialog();
            // mPresenter.show
            // addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
        }
    }

    private boolean onSmspayMode;

    //below changes made by gopal on 09-01-2021...
    @Override
    public void onClickSmsPayBtn() {
        System.out.println("yes it is coming paysms method." + "--");
        onWalletClick = false;
        onCardMode = false;
        onHdfcPayMode = false;
        onCashmode = false;
        onSmspayMode = true;
        onVendorPayMode = false;
        onCodPayMode = false;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setHdfcPayMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setSmsPayMode(true);
                paymentMethodModel.setVendorPayMode(false);
                paymentMethodModel.setCodPayMode(false);
                mPresenter.showsmsPaymentDialog();
                //showsms("PhonePe Transaction", true, walletServiceReq);

                //addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setHdfcPayMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setSmsPayMode(true);
            paymentMethodModel.setVendorPayMode(false);
            paymentMethodModel.setCodPayMode(false);
            mPresenter.showsmsPaymentDialog();

            // showsmsPaymentDialog();
            // mPresenter.show
            // addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
        }
    }

    @Override
    public void onSucessOMSOrderValidate(CalculatePosTransactionRes calculatePosTransactionRes) {
        //  addItemBinding.cashPayButtonLayout.setVisibility(View.VISIBLE);
        addItemBinding.CODPayButtonLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void omsremainamount(double remainamount) {
        // orderPriceInfoModel.setOrderTotalAmount(orderRemainingAmount());
        addItemBinding.vendorPayButtonLayout.setVisibility(View.GONE);
        addItemBinding.CODPayButtonLayout.setVisibility(View.VISIBLE);


    }


    private boolean onCodPayMode;

    @Override
    public void onClickCodPayBtn() {
        System.out.println("yes it is coming VendorPay method." + "--");
        onWalletClick = false;
        onCardMode = false;
        onHdfcPayMode = false;
        onCashmode = false;
        onSmspayMode = false;
        onVendorPayMode = false;
        onCodPayMode = true;

        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setHdfcPayMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setSmsPayMode(false);
                paymentMethodModel.setVendorPayMode(false);
                paymentMethodModel.setCodPayMode(true);

                calculatePosTransactionRes.setRequestStatus(0);
                calculatePosTransactionRes.setReturnMessage("");

                if (Constant.getInstance().vendorcredit) {
                    mPresenter.generateTenterLineService(Constant.getInstance().remainamount, null);

                } else {
                    mPresenter.generateTenterLineService(orderPriceInfoModel.getOrderTotalAmount(), null);
                }

                // mPresenter.generateTenterLineService(orderPriceInfoModel.getOrderTotalAmount(), null);


            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setHdfcPayMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setSmsPayMode(false);
            paymentMethodModel.setVendorPayMode(false);
            paymentMethodModel.setCodPayMode(true);

            if (Constant.getInstance().vendorcredit) {
                mPresenter.generateTenterLineService(Constant.getInstance().remainamount, null);

            } else {
                mPresenter.generateTenterLineService(orderPriceInfoModel.getOrderTotalAmount(), null);
            }

            // mPresenter.generateTenterLineService(orderPriceInfoModel.getOrderTotalAmount(), null);

        }
    }

    @Override
    public void generateotpSuccess(ModelMobileNumVerify response, String otp) {
        showOTPPopUpForOMSOrder(otp);
    }

    @Override
    public void showOTPPopUpForOMSOrder(String otp) {
        OTPDialog dialogView = new OTPDialog(this);
        dialogView.setOTP(otp);
        dialogView.setTitle("OTP");
        dialogView.setPositiveLabel("Ok");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.validateOTP()) {
                    dialogView.dismiss();

                    calculatePosTransactionRes.setVendorId(corporateEntity.getCode());
                    mPresenter.validateOmsOrder(orderPriceInfoModel.getOrderTotalAmount(), calculatePosTransactionRes, customerDataResBean);

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


    private boolean onVendorPayMode;

    @Override
    public void onClickVendorPayBtn() {
        System.out.println("yes it is coming VendorPay method." + "--");
        onWalletClick = false;
        onCardMode = false;
        onHdfcPayMode = false;
        onCashmode = false;
        onSmspayMode = false;
        onVendorPayMode = true;
        onVendorPayMode = false;

        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setHdfcPayMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setSmsPayMode(false);
                paymentMethodModel.setVendorPayMode(true);
                paymentMethodModel.setCodPayMode(false);

                if (customerDataResBean.getShippingMethod() != null) {
                    if ((customerDataResBean.getShippingMethod().equalsIgnoreCase("STOREPICKUP") || customerDataResBean.getShippingMethod().equalsIgnoreCase("CURBSIDE")) && customerDataResBean.getPaymentSource().equalsIgnoreCase("PREPAID")) {
                        mPresenter.sendSmsservice(customerDataResBean.getMobileNO());
                    } else {
                        calculatePosTransactionRes.setVendorId(corporateEntity.getCode());
                        mPresenter.validateOmsOrder(orderPriceInfoModel.getOrderTotalAmount(), calculatePosTransactionRes, customerDataResBean);
                    }
                } else {
                    calculatePosTransactionRes.setVendorId(corporateEntity.getCode());
                    mPresenter.validateOmsOrder(orderPriceInfoModel.getOrderTotalAmount(), calculatePosTransactionRes, customerDataResBean);
                }


            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setHdfcPayMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setSmsPayMode(false);
            paymentMethodModel.setVendorPayMode(true);
            paymentMethodModel.setCodPayMode(false);

            if (customerDataResBean.getShippingMethod() != null) {
                if ((customerDataResBean.getShippingMethod().equalsIgnoreCase("STOREPICKUP") || customerDataResBean.getShippingMethod().equalsIgnoreCase("CURBSIDE")) && customerDataResBean.getPaymentSource().equalsIgnoreCase("PREPAID")) {
                    mPresenter.sendSmsservice(customerDataResBean.getMobileNO());
                } else {
                    mPresenter.validateOmsOrder(orderPriceInfoModel.getOrderTotalAmount(), calculatePosTransactionRes, customerDataResBean);

                }
            } else {
                //  calculatePosTransactionRes.setVendorId(corporateEntity.getCode());
                mPresenter.validateOmsOrder(orderPriceInfoModel.getOrderTotalAmount(), calculatePosTransactionRes, customerDataResBean);

            }
        }
    }

    GetSMSPayAPIResponse smspaylinkresponse = null;

    @Override
    public void onSuccessSmsPayTransaction(GetSMSPayAPIResponse res) {
        if (res != null) {
            if (res.getStatus() == true) {
                Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
                if (res.getStatus() == true) {
                    smspaylinkresponse = res;
                }

            } else {
                Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
            }

        }


    }

    @Override
    public void onFailedSmsPayTransaction(GetSMSPayAPIResponse res) {

        showMessage("Failed SmspayTransaction");

    }

    @Override
    public void onSuccessSmsPayValidateTransaction(GetSMSPayAPIResponse res) {
        //  smspaylinkresponse=res;
        if (res.getStatus() == true) {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailedSmsPayValidateTransaction(GetSMSPayAPIResponse res) {

        showMessage("Failed SmspayTransaction");

    }

    @Override
    public void onSuccessPhonepeGenerateQrCode(PhonepeGenerateQrCodeResponse res) {
        if (res.getStatus() == true) {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessSmsPayCancelTransaction(GetSMSPayAPIResponse res) {

        if (res.getStatus() == true) {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public GetSMSPayAPIResponse getGetSMSPayAPIResponse() {
        return smspaylinkresponse;
    }

   /* SmsPaymentDialog smsPaymentDialog;
    public void showsmsPaymentDialog() {
        System.out.println("showsmsPaymentDialog method-->");
        smsPaymentDialog = new SmsPaymentDialog(this);
       // smsPaymentDialog.SmsPaymentDialog();
       // smsPaymentDialog.notify();


    }*/


    private boolean onCashmode;

    @Override
    public void onClickCashPaymentBtn() {
        onWalletClick = false;
        onCardMode = false;
        onCashmode = true;
        onHdfcPayMode = false;
        onSmspayMode = false;
        onVendorPayMode = false;
        onCodPayMode = false;

        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(true);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setHdfcPayMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setPhonePeMode(false);
                paymentMethodModel.setPhonePeQrMode(false);
                paymentMethodModel.setPaytmMode(false);
                paymentMethodModel.setAirtelMode(false);
                paymentMethodModel.setSmsPayMode(false);
                paymentMethodModel.setVendorPayMode(false);
                paymentMethodModel.setCodPayMode(false);

                addItemBinding.cashPaymentAmountEdit.setText(String.format("%.2f", (orderRemainingAmount())));
            }
        } else {
            paymentMethodModel.setCashMode(true);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setHdfcPayMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setPhonePeMode(false);
            paymentMethodModel.setPhonePeQrMode(false);
            paymentMethodModel.setPaytmMode(false);
            paymentMethodModel.setAirtelMode(false);
            paymentMethodModel.setSmsPayMode(false);
            paymentMethodModel.setVendorPayMode(false);
            paymentMethodModel.setCodPayMode(false);
            addItemBinding.cashPaymentAmountEdit.setText(String.format("%.2f", (orderRemainingAmount())));
        }
    }

    private void prescriptionMandatory() {
        for (int i = 0; i < Singletone.getInstance().itemsArrayList.size(); i++) {
            if (Singletone.getInstance().itemsArrayList.get(i).getScheduleCategory().equalsIgnoreCase("H1 Scheduled")) {
                alertPrescriptionDialog();
            }
        }
    }

    @Override
    public void onClickOneApolloBtn() {
        onWalletClick = false;
        onCardMode = false;
        onHdfcPayMode = false;
        onCashmode = false;
        onSmspayMode = false;
        onVendorPayMode = false;
        onCodPayMode = false;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setHdfcPayMode(false);
                paymentMethodModel.setOneApolloMode(true);
                paymentMethodModel.setLoadApolloPoints(true);
                paymentMethodModel.setErrorApolloPoints(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setPhonePeMode(false);
                paymentMethodModel.setPhonePeQrMode(false);
                paymentMethodModel.setPaytmMode(false);
                paymentMethodModel.setAirtelMode(false);
                paymentMethodModel.setSmsPayMode(false);
                paymentMethodModel.setVendorPayMode(false);
                paymentMethodModel.setCodPayMode(false);
                if (customerEntity != null && transactionIdModel != null)
                    mPresenter.validateOneApolloPoints(customerEntity.getMobileNo(), transactionIdModel.getTransactionID());
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setHdfcPayMode(false);
            paymentMethodModel.setOneApolloMode(true);
            paymentMethodModel.setLoadApolloPoints(true);
            paymentMethodModel.setErrorApolloPoints(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setPhonePeMode(false);
            paymentMethodModel.setPhonePeQrMode(false);
            paymentMethodModel.setPaytmMode(false);
            paymentMethodModel.setAirtelMode(false);
            paymentMethodModel.setSmsPayMode(false);
            paymentMethodModel.setVendorPayMode(false);
            paymentMethodModel.setCodPayMode(false);
            if (customerEntity != null && transactionIdModel != null)
                mPresenter.validateOneApolloPoints(customerEntity.getMobileNo(), transactionIdModel.getTransactionID());
        }

    }

    private boolean onWalletClick;

    @Override
    public void onClickWalletPaymentBtn() {
        onWalletClick = true;
        onCardMode = false;
        onCashmode = false;
        onHdfcPayMode = false;
        onSmspayMode = false;
        onVendorPayMode = false;
        onCodPayMode = false;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() || addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setHdfcPayMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(true);
                paymentMethodModel.setPhonePeQrCodeMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setPhonePeMode(false);
                paymentMethodModel.setPhonePeQrMode(false);
                paymentMethodModel.setPaytmMode(false);
                paymentMethodModel.setAirtelMode(false);
                paymentMethodModel.setSmsPayMode(false);
                paymentMethodModel.setVendorPayMode(false);
                paymentMethodModel.setCodPayMode(false);
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setHdfcPayMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(true);
            paymentMethodModel.setPhonePeQrCodeMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setPhonePeMode(false);
            paymentMethodModel.setPhonePeQrMode(false);
            paymentMethodModel.setPaytmMode(false);
            paymentMethodModel.setAirtelMode(false);
            paymentMethodModel.setSmsPayMode(false);
            paymentMethodModel.setVendorPayMode(false);
            paymentMethodModel.setCodPayMode(false);
        }

    }

    @Override
    public void onClickEditItemsList() {
        addItemBinding.setIsPaymentMode(false);
        addItemBinding.cashPaymentAmountEdit.setText("");
        addItemBinding.cardPaymentAmountEditText.setText("");
        addItemBinding.oneApolloAmountEditText.setText("");
        paymentMethodModel.setGenerateBill(false);
    }

    @Override
    public void onFailedGenerateTenderLine(GenerateTenderLineRes body) {
        if (body != null && body.getValidateOMSOrderResult().getReturnMessage() != null && !body.getValidateOMSOrderResult().getReturnMessage().isEmpty()) {
            showMessage(body.getValidateOMSOrderResult().getReturnMessage());
        } else {
            showMessage("Generate TenderLine Failed");
        }
    }

    double paymentDoneAmount = 0.0;
    private SaveRetailsTransactionRes saveRetailsTransactionRes;

    @Override
    public void onSuccessSaveRetailTransaction(SaveRetailsTransactionRes body) {
        this.saveRetailsTransactionRes = body;
        if (!TextUtils.isEmpty(body.getReciptId())) {
            body.setReminderDays(remaindValue);
            paymentMethodModel.setSaveRetailsTransactionRes(body);
            if (isOnlineOrder) {
                mPresenter.getPostOnlineOrderApiCall(ePrescriptionModelClassResponse, ePrescriptionMedicineResponseList, body, customerDataResBean);
            } else {
                onClickGenerateBill();
            }
        } else {
            showMessage(body.getReturnMessage());
        }
        ObjectAnimator anim = ObjectAnimator.ofFloat(addItemBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
        anim.setDuration(500);
        anim.start();
        rotationAngle += 180;
        rotationAngle = rotationAngle % 360;
        ViewAnimationUtils.collapse(addItemBinding.detailsLayout.customerDoctorLayout);
    }

    @Override
    public void onFailedSaveRetailsTransaction(SaveRetailsTransactionRes body) {
        showMessage("Failed SaveRetailTransaction");
    }

    CalculatePosTransactionRes calculatePosTransactionRes;

    @Override
    public void onSuccessCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes) {


        calculatePosTransactionRes = posTransactionRes;

        boolean isAddAllowed = true;
        boolean isDeleteAllowed = true;
        boolean isEditAllowed = true;

        if (getCalculatedPosTransactionRes() != null && getCalculatedPosTransactionRes().getCorpCode() != null) {
            String corpCode = getCalculatedPosTransactionRes().getCorpCode();
            GetGlobalConfingRes globalConfingRes = mPresenter.getGlobalConfiguration();
            for (GetGlobalConfingRes.OMSVendorWiseConfigration o : globalConfingRes.getOMSVendorWiseConfigration()) {
                if (o.getCorpCode().equalsIgnoreCase(corpCode)) {
                    isAddAllowed = o.getAllowProductAdd();
                    isDeleteAllowed = o.getAllowVoidProduct();
                    isEditAllowed = o.getAllowChangeQTY();
                    break;
                }
            }
        }

        medicinesDetailAdapter.setCrudConfiguration(isDeleteAllowed, isAddAllowed, isEditAllowed);

        calculatePosTransactionRes.setRemainingamount(paymentMethodModel.getBalanceAmount());
        orderPriceInfoModel.setOrderSavingsAmount(posTransactionRes.getDiscAmount() / posTransactionRes.getTotalMRP() * 100);
        orderPriceInfoModel.setMrpTotalAmount(posTransactionRes.getTotalMRP());
        orderPriceInfoModel.setTaxableTotalAmount(posTransactionRes.getNetAmount());
        orderPriceInfoModel.setOrderTotalAmount(posTransactionRes.getGrossAmount() - posTransactionRes.getDiscAmount());

        if (calculatePosTransactionRes.getISOMSOrder()) {
            // Creating an object of DecimalFormat class
            DecimalFormat df_obj = new DecimalFormat("#.##");
            String number = df_obj.format(posTransactionRes.getGrossAmount() - posTransactionRes.getDiscAmount());
            double roundedvalue = Double.parseDouble(number);
            // double tempbalanceAmt = orderTotalAmount() - roundedvalue;
            orderPriceInfoModel.setOrderTotalAmount(roundedvalue);
        }

        orderPriceInfoModel.setDiscTotalAmount(posTransactionRes.getDiscAmount());
        orderPriceInfoModel.setRoundedAmount(posTransactionRes.getRoundedAmount());
        orderPriceInfoModel.setOrderSavingsPercentage(posTransactionRes.getDiscAmount() / posTransactionRes.getTotalMRP() * 100);
        orderPriceInfoModel.setTaxAmount(posTransactionRes.getTotalTaxAmount());
        paymentMethodModel.setBalanceAmount(Double.parseDouble(String.format("%.2f", (posTransactionRes.getGrossAmount() - posTransactionRes.getDiscAmount()) - paymentDoneAmount)));
        if (posTransactionRes.getSalesLine().size() > 0) {
            orderPriceInfoModel.setPharmaTotalAmount(0);
            orderPriceInfoModel.setFmcgTotalAmount(0);
            orderPriceInfoModel.setPlTotalAmount(0);
//made changes 07_10_2022 - start
            double netAmt = 0.0;
            double mrpTotal = 0.0;
            double taxAmt = 0.0;
            double savings = 0.0;
            double roundedAmt = 0.0;
//made changes 07_10_2022 - end
            for (int i = 0; i < posTransactionRes.getSalesLine().size(); i++) {
                if (!posTransactionRes.getSalesLine().get(i).getIsVoid()) {
                    if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("P"))
                        orderPriceInfoModel.setPharmaTotalAmount(orderPriceInfoModel.getPharmaTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
                    else if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("F"))
                        orderPriceInfoModel.setFmcgTotalAmount(orderPriceInfoModel.getFmcgTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
                    else if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("A"))
                        orderPriceInfoModel.setPlTotalAmount(orderPriceInfoModel.getPlTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
                    //made changes 07_10_2022 - start
                    netAmt = netAmt + posTransactionRes.getSalesLine().get(i).getNetAmount();
                    mrpTotal = mrpTotal + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax();
                    taxAmt = taxAmt + posTransactionRes.getSalesLine().get(i).getTaxAmount();
                    savings = savings + posTransactionRes.getSalesLine().get(i).getTotalDiscAmount();

//made changes 07_10_2022 - end

                }

            }
            //made changes 07_10_2022 - start
            mrpTotal = mrpTotal + posTransactionRes.getDiscAmount();
            orderPriceInfoModel.setOrderSavingsAmount(savings);
            orderPriceInfoModel.setMrpTotalAmount(mrpTotal);
            orderPriceInfoModel.setTaxableTotalAmount(netAmt);
            orderPriceInfoModel.setOrderTotalAmount(mrpTotal - posTransactionRes.getDiscAmount());
            orderPriceInfoModel.setTaxAmount(taxAmt);


            posTransactionRes.setOrderSavingsAmount(posTransactionRes.getDiscAmount());
            posTransactionRes.setMrpTotalAmount(mrpTotal);
            posTransactionRes.setTaxableTotalAmount(netAmt);
            posTransactionRes.setOrderTotalAmount(mrpTotal - posTransactionRes.getDiscAmount());
            posTransactionRes.setTotalTaxAmount(taxAmt);
            posTransactionRes.setGrossAmount(mrpTotal);
            posTransactionRes.setTotalMRP(mrpTotal);
            calculatePosTransactionRes = posTransactionRes;

//made changes 07_10_2022 - end


            Singletone.getInstance().itemsArrayList.clear();
            Singletone.getInstance().itemsArrayList.addAll(posTransactionRes.getSalesLine());
            medicinesDetailAdapter.notifyDataSetChanged();
        } else {
            Singletone.getInstance().itemsArrayList.clear();
            if (medicinesDetailAdapter != null) {
                medicinesDetailAdapter.notifyDataSetChanged();
            }
        }

        if (getItemsCount() != 0) {
            addItemBinding.setItemsCount(getItemsCount());
            addItemBinding.setProductCount(getItemsCount());
        } else {
            addItemBinding.setItemsCount(getItemsCount());
            addItemBinding.footer.setVisibility(View.GONE);
        }
        addItemBinding.setOrderInfo(orderPriceInfoModel);
        //System.out.println("Omsorder true or false"+Constant.getInstance().isomsorder);
        isExpand = false;
        ObjectAnimator anim = ObjectAnimator.ofFloat(addItemBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
        anim.setDuration(500);
        anim.start();
        rotationAngle += 180;
        rotationAngle = rotationAngle % 360;
        ViewAnimationUtils.collapse(addItemBinding.detailsLayout.customerDoctorLayout);
        updatePayedAmount(calculatePosTransactionRes);

        if (getIntent() != null) {
            isCameFromOrderDetailsScreenActivity = (Boolean) getIntent().getBooleanExtra("IS_CAME_FROM_ORDER_DETAILS_SCREEN_ACTIVITY", false);
            if (isCameFromOrderDetailsScreenActivity) {
                addItemBinding.detailsLayout.prgTrackingEdit.setEnabled(false);
                if (getItemsCount() != 0) {
                    onPayButtonClick();
                } else {
//                    Toast.makeText(this, "No item available", Toast.LENGTH_SHORT).show();
                    showMessagePopup("No item available");
                }
            }
        }
    }

    private void showMessagePopup(String message) {
        Dialog showMessagePopup = new Dialog(this);
        ExitInfoDialogBinding exitInfoDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.exit_info_dialog, null, false);
        showMessagePopup.setCancelable(false);
        showMessagePopup.setContentView(exitInfoDialogBinding.getRoot());
        exitInfoDialogBinding.title.setVisibility(View.GONE);
        exitInfoDialogBinding.subtitle.setText(message);
        exitInfoDialogBinding.dialogButtonNO.setVisibility(View.GONE);
//        exitInfoDialogBinding.sepe
        exitInfoDialogBinding.dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessagePopup.dismiss();
            }
        });

    }

    @Override
    public void onSuccessOneApolloSendOtp(ValidatePointsResModel.OneApolloProcessResultEntity resultEntity) {
        paymentMethodModel.setOTPView(true);
        validatePointsResModel = resultEntity;
    }

    @Override
    public void onSuccessOneApolloOtp(ValidatePointsResModel.OneApolloProcessResultEntity entity) {
        addItemBinding.setValidatePoints(entity);
        paymentMethodModel.setOTPView(false);
        mPresenter.generateTenterLineService(Double.parseDouble(entity.getRedeemPoints()), null);
    }

    @Override
    public void isManualDisc(boolean isManualDisc) {
        paymentMethodModel.setAdditionalDisc(isManualDisc);
    }

    @Override
    public void onItemDeleted(int lineNumber, SalesLineEntity item) {
        GetGlobalConfingRes getGlobalConfingRes = mPresenter.getGlobalConfiguration();
        boolean isoMSOrderDeliveryItem = false;
        for (int n = 0; n < getGlobalConfingRes.getoMSOrderDeliveryItemId().size(); n++) {
            if (getGlobalConfingRes.getoMSOrderDeliveryItemId().get(n).equalsIgnoreCase(item.getItemId())) {
                isoMSOrderDeliveryItem = true;
                break;
            }
        }
        if (isoMSOrderDeliveryItem) {
            showMessagePopup1("Cant void " + item.getItemName());
        } else {
            if (paymentDoneAmount == 0.0) {
                mPresenter.voidProduct(lineNumber);
            } else {
                partialPaymentDialog("Alert!", "Partial Payment done,Kindly void payment lines");
            }
        }
    }

    private void showMessagePopup1(String message) {
        Dialog showMessagePopup = new Dialog(this);
        ExitInfoDialogBinding exitInfoDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.exit_info_dialog, null, false);
        showMessagePopup.setCancelable(false);
        showMessagePopup.setContentView(exitInfoDialogBinding.getRoot());
        if (showMessagePopup.getWindow() != null)
            showMessagePopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitInfoDialogBinding.title.setVisibility(View.VISIBLE);
        exitInfoDialogBinding.title.setText(message);

        exitInfoDialogBinding.subtitle.setVisibility(View.GONE);
        exitInfoDialogBinding.subtitle.setText(message);
        exitInfoDialogBinding.dialogButtonNO.setVisibility(View.GONE);
//        exitInfoDialogBinding.sepe
        exitInfoDialogBinding.dialogButtonOK.setText("OK");
        exitInfoDialogBinding.dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessagePopup.dismiss();
            }
        });
        showMessagePopup.show();

    }

    @Override
    public void onItemAdded(int lineNumber) {
        mPresenter.voidProduct(lineNumber);
    }

    private int getItemsCount() {
        int count = 0;
        for (SalesLineEntity items : Singletone.getInstance().itemsArrayList) {
            if (!items.getIsVoid()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onItemEdit(SalesLineEntity item) {
        if (paymentDoneAmount == 0.0) {
            EditQuantityDialog dialogView = new EditQuantityDialog(this);
            dialogView.setItemData(item);
            dialogView.setTitle("Change Quantity");
            dialogView.setPositiveLabel("Ok");
            dialogView.setSubtitle("Actual Quantity " + item.getQty());
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialogView.validateQuantity()) {
                        medicinesDetailAdapter.notifyDataSetChanged();
                        //  medicinesDetailAdapter.notifyDataSetChanged();
                        dialogView.dismiss();
                        mPresenter.changeQuantity(item, dialogView.getEnteredQuantity());
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
        } else {
            partialPaymentDialog("Alert!", "Partial Payment done,Kindly void payment lines");
        }
    }

    @Override
    public void onClickGenerateBill() {
        Constant.getInstance().isomsorder = false;
        startActivity(OrderSummaryActivity.getStartIntent(this, paymentMethodModel.getSaveRetailsTransactionRes(), corporateEntity, orderPriceInfoModel, paymentMethodModel));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public boolean isDonePayment() {
        return paymentDoneAmount == orderTotalAmount();
    }

    /*@Override
    public boolean isOMSOrder() {
        return isomsorder;
    }*/

    @Override
    public double orderTotalAmount() {
        OrderPriceInfoModel priceInfoModel = addItemBinding.getOrderInfo();
        if (priceInfoModel != null)
            return Double.parseDouble(String.format("%.2f", priceInfoModel.getOrderTotalAmount()));
        else return 0;
    }

    @Override
    public double orderRemainingAmount() {
        return Double.parseDouble(String.format("%.2f", (orderTotalAmount() - paymentDoneAmount)));
    }

    private boolean isGeneratedBill = false;
    private List<TenderLineEntity> tenderLineEntityList = new ArrayList<>();
    private List<TenderLineEntity> tenderAddList = new ArrayList<>();
    private boolean amounttoAdd;
    PayAdapterModel payAdapterModel;

    @Override
    public void updatePayedAmount(CalculatePosTransactionRes transactionRes) {
        if (clearItems) {
            transactionRes.getTenderLine().clear();
            clearItems = false;
        }
        // Constant.getInstance().isomsorder = false;
        List<Boolean> isAmount = new ArrayList<Boolean>();
        List<Integer> crossValue = new ArrayList<Integer>();
        List<Integer> strikeTextData = new ArrayList<>();
        isGeneratedBill = false;
        calculatePosTransactionRes = transactionRes;
        addItemBinding.cashPaymentAmountEdit.setText("");
        addItemBinding.cardPaymentAmountEditText.setText("");
        addItemBinding.oneApolloAmountEditText.setText("");
        addItemBinding.creditPaymentAmountEdit.setText("");
        if (transactionRes != null && transactionRes.getTenderLine() != null && transactionRes.getTenderLine().size() > 0) {
            paymentMethodModel.setPaymentInitiate(true);
            if (arrPayAdapterModel.size() > 0) {
                for (int i = 0; i < arrPayAdapterModel.size(); i++) {
                    isAmount.add(arrPayAdapterModel.get(i).isAmountVoid());
                    crossValue.add(arrPayAdapterModel.get(i).getCrossDis());
                    strikeTextData.add(arrPayAdapterModel.get(i).getStrikeThroughText());
                }
            }
            arrPayAdapterModel.clear();
            paymentDoneAmount = 0.0;
            if (methodCalling) {
                transactionRes.getTenderLine().get(amountPosition).setAmountToBeVoid(amounttoAdd);
                methodCalling = false;
                tenderLineEntityList = transactionRes.getTenderLine();
            } else {
                if (tenderLineEntityList.size() > 0) {
                    for (int i = 0; i < tenderLineEntityList.size(); i++) {
                        transactionRes.getTenderLine().get(i).setAmountToBeVoid(tenderLineEntityList.get(i).isAmountToBeVoid());
                    }
                    tenderLineEntityList = transactionRes.getTenderLine();
                }
            }
            for (TenderLineEntity tenderLineEntity : transactionRes.getTenderLine()) {
                if (!TextUtils.isEmpty(tenderLineEntity.getTenderId())) {
                    if (!tenderLineEntity.isVoid()) {


                        paymentDoneAmount += tenderLineEntity.getAmountTendered();

                        if (calculatePosTransactionRes.getISOMSOrder()) {
                            // Creating an object of DecimalFormat class
                            DecimalFormat df_obj = new DecimalFormat("#.##");
                            String number = df_obj.format(paymentDoneAmount);
                            double roundedvalue = Double.parseDouble(number);
                            double tempbalanceAmt = orderTotalAmount() - roundedvalue;
                            if (tempbalanceAmt == 0) {
                                paymentDoneAmount = roundedvalue;
                            }
                        }


                    }
                    payAdapterModel = new PayAdapterModel(tenderLineEntity.getTenderName(), " " + tenderLineEntity.getAmountTendered(), tenderLineEntity.getAmountTendered());
                    if (tenderLineEntity.getTenderName().equalsIgnoreCase("card")) {
                        if (getItemsCount() > 0) {
                            payAdapterModel.setCrossDis(1);
                        }
                    }
                    if (tenderLineEntity.getTenderName().equalsIgnoreCase("SMS PAY")) {
                        if (getItemsCount() > 0) {
                            payAdapterModel.setCrossDis(1);
                        }
                    }
                    if (tenderLineEntity.getTenderName().equalsIgnoreCase("HDFC PAYMENT")) {
                        if (getItemsCount() > 0) {
                            payAdapterModel.setCrossDis(1);
                        }
                    }
                    if (tenderLineEntity.getTenderName().equalsIgnoreCase("Pay through QR Code") || tenderLineEntity.getTenderName().equalsIgnoreCase("QR Code")) {
                        if (getItemsCount() > 0) {
                            payAdapterModel.setCrossDis(1);
                        }
                    }
                    arrPayAdapterModel.add(payAdapterModel);
                    if (arrPayAdapterModel.size() >= 1) {
                        addItemBinding.detailsLayout.corpoEdit.setVisibility(View.GONE);
                    }
                    if (paymentDoneAmount == orderTotalAmount()) {
                        paymentMethodModel.setPaymentDone(true);
                        paymentMethodModel.setGenerateBill(true);
                        isGeneratedBill = true;
                        paymentMethodModel.setBalanceAmount(Double.parseDouble(String.format("%.2f", (orderTotalAmount() - paymentDoneAmount))));
                        paymentMethodModel.setBalanceAmount(false);
                        for (int i = 0; i < arrPayAdapterModel.size(); i++) {
                            if (getItemsCount() > 0) {
                                arrPayAdapterModel.get(i).setCrossDis(1);
                            }
                        }
                    } else {
                        double balanceAmt = orderTotalAmount() - paymentDoneAmount;
                        if (balanceAmt <= 0) {
                            paymentMethodModel.setPaymentDone(true);
                            paymentMethodModel.setGenerateBill(true);
                            isGeneratedBill = true;
                            paymentMethodModel.setBalanceAmount(Double.parseDouble(String.format("%.2f", (orderTotalAmount() - paymentDoneAmount))));
                            if (balanceAmt == 0) {
                                paymentMethodModel.setBalanceAmount(false);
                            } else {
                                paymentMethodModel.setBalanceAmount(true);
                                for (int i = 0; i < arrPayAdapterModel.size(); i++) {
                                    arrPayAdapterModel.get(i).setCrossDis(1);
                                }
                            }
                        } else {
                            if (tenderLineEntity.isVoid()) {
                                paymentMethodModel.setBalanceAmount(Double.parseDouble(String.format("%.2f", (balanceAmt))));
                                paymentMethodModel.setBalanceAmount(true);
                                paymentMethodModel.setPaymentDone(false);
                                paymentMethodModel.setGenerateBill(false);
                            } else {
                                paymentMethodModel.setBalanceAmount(Double.parseDouble(String.format("%.2f", (orderTotalAmount() - paymentDoneAmount))));
                                paymentMethodModel.setBalanceAmount(true);
                                paymentMethodModel.setPaymentDone(false);
                                paymentMethodModel.setGenerateBill(false);
                            }
                        }
                    }

                } else {
                    showMessage("Tender Id null");
                    break;
                }
            }
            if (arrPayAdapterModel.size() > 0) {
                for (int i = 0; i < arrPayAdapterModel.size(); i++) {
                    if (isAmount.size() - 1 >= i) {
                        arrPayAdapterModel.get(i).setAmountVoid(isAmount.get(i));
                        arrPayAdapterModel.get(i).setStrikeThroughText(strikeTextData.get(i));
                        arrPayAdapterModel.get(i).setCrossDis(crossValue.get(i));
                        if (paymentDoneAmount == orderTotalAmount()) {
                            for (int j = 0; j < arrPayAdapterModel.size(); j++) {
                                if (getItemsCount() > 0) {
                                    arrPayAdapterModel.get(j).setCrossDis(1);
                                }
                            }
                        } else {
                            double balanceAmt = orderTotalAmount() - paymentDoneAmount;
                            if (balanceAmt <= 0) {
                                if (balanceAmt != 0) {
                                    for (int k = 0; k < arrPayAdapterModel.size(); k++) {
                                        arrPayAdapterModel.get(k).setCrossDis(1);
                                    }
                                }
                            }
                        }
                        payActivityAdapter.notifyDataSetChanged();
                    }
                }
            }
            payActivityAdapter.notifyDataSetChanged();
        } else {
            paymentDoneAmount = 0;
            paymentMethodModel.setBalanceAmount(Double.parseDouble(String.format("%.2f", (orderTotalAmount() - paymentDoneAmount))));
            paymentMethodModel.setBalanceAmount(false);
            paymentMethodModel.setPaymentDone(false);
            paymentMethodModel.setGenerateBill(false);
            arrPayAdapterModel.clear();
            payActivityAdapter.notifyDataSetChanged();
        }
        Constant.getInstance().remainamount = 0;
        calculatePosTransactionRes.setRemainingamount(paymentMethodModel.getBalanceAmount());
        Constant.getInstance().remainamount = paymentMethodModel.getBalanceAmount();
        tenderAddList = calculatePosTransactionRes.getTenderLine();
        if (calculatePosTransactionRes.getRemainingamount() < 0) {
            TenderLineEntity tenderLineEntity = new TenderLineEntity();
            for (int i = 0; i < calculatePosTransactionRes.getTenderLine().size(); i++) {
                if (calculatePosTransactionRes.getTenderLine().size() - 1 == i) {
                    tenderLineEntity.setAmountCur(calculatePosTransactionRes.getRemainingamount());
                    tenderLineEntity.setAmountTendered(calculatePosTransactionRes.getRemainingamount());
                    tenderLineEntity.setAmountMst(calculatePosTransactionRes.getRemainingamount());
                    tenderLineEntity.setTenderName(calculatePosTransactionRes.getTenderLine().get(i).getTenderName());
                    tenderLineEntity.setBarCode(calculatePosTransactionRes.getTenderLine().get(i).getBarCode());
                    tenderLineEntity.setExchRate(calculatePosTransactionRes.getTenderLine().get(i).getExchRate());
                    tenderLineEntity.setExchRateMst(calculatePosTransactionRes.getTenderLine().get(i).getExchRateMst());
                    tenderLineEntity.setVoid(calculatePosTransactionRes.getTenderLine().get(i).getIsVoid());
                    tenderLineEntity.setLineNo(calculatePosTransactionRes.getTenderLine().get(i).getLineNo() + 1);
                    tenderLineEntity.setMobileNo(calculatePosTransactionRes.getTenderLine().get(i).getMobileNo());
                    tenderLineEntity.setPreviewText(calculatePosTransactionRes.getTenderLine().get(i).getPreviewText());
                    tenderLineEntity.setRewardsPoint((int) calculatePosTransactionRes.getTenderLine().get(i).getRewardPoints());
                    tenderLineEntity.setTenderId(calculatePosTransactionRes.getTenderLine().get(i).getTenderId());
                    tenderLineEntity.setTenderType(calculatePosTransactionRes.getTenderLine().get(i).getTenderType());
                    tenderLineEntity.setWalletOrderId(calculatePosTransactionRes.getTenderLine().get(i).getWalletOrderId());
                    tenderLineEntity.setWalletTransactionID(calculatePosTransactionRes.getTenderLine().get(i).getWalletTransactionID());
                    tenderLineEntity.setWalletType(calculatePosTransactionRes.getTenderLine().get(i).getWalletType());
                }
            }
            tenderAddList.add(tenderLineEntity);
            calculatePosTransactionRes.setTenderLine(tenderAddList);
        }

    }

    @Override
    public void onSuccessPaymentVoidData(PaymentVoidRes paymentVoidRes) {
        if (paymentVoidRes != null) {
            if (paymentVoidRes.getTenderLine().get(amountPosition).isVoid) {
                arrPayAdapterModel.get(amountPosition).setStrikeThroughText(1);
            } else if (!paymentVoidRes.getTenderLine().get(amountPosition).isVoid) {
                arrPayAdapterModel.get(amountPosition).setStrikeThroughText(0);
            }
            if (calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("PhonePe") || calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("PAYTM") || calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("Airtel") || calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("Pay through QR Code") || calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("QR Code")) {
                arrPayAdapterModel.get(amountPosition).setCrossDis(1);
            }
            List<TenderLineEntity> tenderLineEntities = new ArrayList<>();
            for (int j = 0; j < paymentVoidRes.getTenderLine().size(); j++) {
                TenderLineEntity tenderLineEntity = new TenderLineEntity();
                tenderLineEntity.setAmountCur(paymentVoidRes.getTenderLine().get(j).getAmountCur());
                tenderLineEntity.setAmountMst(paymentVoidRes.getTenderLine().get(j).getAmountMst());
                tenderLineEntity.setAmountTendered(paymentVoidRes.getTenderLine().get(j).getAmountTendered());
                tenderLineEntity.setBarCode(paymentVoidRes.getTenderLine().get(j).getBarCode());
                tenderLineEntity.setExchRate(paymentVoidRes.getTenderLine().get(j).getExchRate());
                tenderLineEntity.setExchRateMst(paymentVoidRes.getTenderLine().get(j).getExchRateMst());
                tenderLineEntity.setVoid(paymentVoidRes.getTenderLine().get(j).getVoid());
                tenderLineEntity.setLineNo(paymentVoidRes.getTenderLine().get(j).getLineNo());
                tenderLineEntity.setMobileNo(paymentVoidRes.getTenderLine().get(j).getMobileNo());
                tenderLineEntity.setPreviewText(paymentVoidRes.getTenderLine().get(j).getPreviewText());
                tenderLineEntity.setRewardsPoint(paymentVoidRes.getTenderLine().get(j).getRewardsPoint());
                tenderLineEntity.setTenderId(paymentVoidRes.getTenderLine().get(j).getTenderId());
                tenderLineEntity.setTenderName(paymentVoidRes.getTenderLine().get(j).getTenderName());
                tenderLineEntity.setTenderType(paymentVoidRes.getTenderLine().get(j).getTenderType());
                tenderLineEntity.setWalletOrderId(paymentVoidRes.getTenderLine().get(j).getWalletOrderId());
                tenderLineEntity.setWalletTransactionID(paymentVoidRes.getTenderLine().get(j).getWalletTransactionID());
                tenderLineEntity.setWalletType(paymentVoidRes.getTenderLine().get(j).getWalletType());
                tenderLineEntities.add(tenderLineEntity);
            }
            calculatePosTransactionRes.setTenderLine(tenderLineEntities);
            updatePayedAmount(calculatePosTransactionRes);
        }
    }

    private int amountPosition;
    private boolean methodCalling;

    @Override
    public void toAddPayedAmount(PayAdapterModel item, int pos) {
        String phonepay = "";
        cardmode = true;
        if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PhonePe") || calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PAYTM") || calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Airtel") || calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Pay through QR Code") || calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("QR Code")) {
            methodCalling = true;
            amounttoAdd = false;
            amountPosition = pos;

            if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PhonePe")) {
                phonepay = "PhonePe";
                wallet.setWalletType(4);
            } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PAYTM")) {
                phonepay = "PAYTM";
                wallet.setWalletType(3);
            } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Airtel")) {
                phonepay = "Airtel";
                wallet.setWalletType(2);
            } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Pay through QR Code")) {
                phonepay = "Pay through QR Code";
                wallet.setWalletType(5);
            } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("QR Code")) {
                phonepay = "QR Code";
                wallet.setWalletType(5);
            }

            wallet.setMobileNo(calculatePosTransactionRes.getTenderLine().get(pos).getMobileNo());
            wallet.setOTP("");
            wallet.setOTPTransactionId("");
            wallet.setRequestURL("");
            wallet.setWalletRequestType(2);
            //Singletone.getInstance().tenderTypeResultEntity
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : mPresenter.getTenderTypeResultEntity().get_TenderType()) {
                if (tenderTypeEntity.getTender().equalsIgnoreCase(phonepay)) {
                    wallet.setWalletURL(tenderTypeEntity.getTenderURL());
                }
            }
            wallet.setWalletAmount(calculatePosTransactionRes.getTenderLine().get(pos).getAmountCur());
            wallet.setRequestURL("");
            wallet.setResponse("");
            wallet.setPOSTransactionID(calculatePosTransactionRes.getTransactionId());
            wallet.setPOSTerminal(calculatePosTransactionRes.getTerminal());
            wallet.setWalletRefundId("");
            wallet.setWalletOrderID(calculatePosTransactionRes.getTenderLine().get(pos).getWalletOrderId());
            wallet.setWalletTransactionID(calculatePosTransactionRes.getTenderLine().get(pos).getWalletTransactionID());
            wallet.setRewardsPoint(0);
            wallet.setUHID("");
            wallet.setRequestStatus(0);
            wallet.setReturnMessage("");
            mPresenter.getPaymentVoidApiCall(calculatePosTransactionRes, wallet, (int) calculatePosTransactionRes.getTenderLine().get(pos).getLineNo());
        } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Cash") || calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Credit")) {
            methodCalling = true;
            amounttoAdd = false;
            amountPosition = pos;
            if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Cash") || calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Credit")) {
                wallet.setWalletType(0);
            }
            wallet.setMobileNo("");
            wallet.setOTP("");
            wallet.setOTPTransactionId("");
            wallet.setRequestURL("");
            wallet.setWalletRequestType(0);
            wallet.setWalletURL("");
            wallet.setWalletAmount(calculatePosTransactionRes.getTenderLine().get(pos).getAmountCur());
            wallet.setRequestURL("");
            wallet.setResponse("");
            wallet.setPOSTransactionID(calculatePosTransactionRes.getTransactionId());
            wallet.setPOSTerminal(calculatePosTransactionRes.getTerminal());
            wallet.setWalletRefundId("");
            wallet.setWalletOrderID("");
            wallet.setWalletTransactionID("");
            wallet.setRewardsPoint(0);
            wallet.setUHID("");
            wallet.setRequestStatus(0);
            wallet.setReturnMessage("");
            mPresenter.getPaymentVoidApiCall(calculatePosTransactionRes, wallet, (int) calculatePosTransactionRes.getTenderLine().get(pos).getLineNo());

        } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("card")) {

        }
    }

    Response<WalletServiceRes> walletServiceResResponseData;

    @Override
    public void getWalletResponseData(Response<WalletServiceRes> walletServiceRes) {
        walletServiceResResponseData = walletServiceRes;
    }

    PaymentVoidReq.Wallet wallet = new PaymentVoidReq.Wallet();

    private boolean cardmode;

    @Override
    public void toRemovePayedAmount(int position, PayActivityAdapter.ViewHolder holder) {
        String phonepay = "";
        cardmode = true;
        if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PhonePe") || calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PAYTM") || calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Airtel") || calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Pay through QR Code") || calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("QR Code")) {
            methodCalling = true;
            amounttoAdd = true;
            amountPosition = position;

            if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PhonePe")) {
                phonepay = "PhonePe";
                wallet.setWalletType(4);
            } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PAYTM")) {
                phonepay = "PAYTM";
                wallet.setWalletType(3);
            } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Airtel")) {
                phonepay = "Airtel";
                wallet.setWalletType(2);
            } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Pay through QR Code")) {
                phonepay = "Pay through QR Code";
                wallet.setWalletType(5);
            } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("QR Code")) {
                phonepay = "QR Code";
                wallet.setWalletType(5);
            }
            wallet.setMobileNo(calculatePosTransactionRes.getTenderLine().get(position).getMobileNo());
            wallet.setOTP("");
            wallet.setOTPTransactionId("");
            wallet.setRequestURL("");
            wallet.setWalletRequestType(2);
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : mPresenter.getTenderTypeResultEntity().get_TenderType()) {
                if (tenderTypeEntity.getTender().equalsIgnoreCase(phonepay)) {
                    wallet.setWalletURL(tenderTypeEntity.getTenderURL());
                }
            }
            wallet.setWalletAmount(calculatePosTransactionRes.getTenderLine().get(position).getAmountCur());
            wallet.setRequestURL("");
            wallet.setResponse("");
            wallet.setPOSTransactionID(calculatePosTransactionRes.getTransactionId());
            wallet.setPOSTerminal(calculatePosTransactionRes.getTerminal());
            wallet.setWalletRefundId("");
            wallet.setWalletOrderID(calculatePosTransactionRes.getTenderLine().get(position).getWalletOrderId());
            wallet.setWalletTransactionID(calculatePosTransactionRes.getTenderLine().get(position).getWalletTransactionID());
            wallet.setRewardsPoint(0);
            wallet.setUHID("");
            wallet.setRequestStatus(0);
            wallet.setReturnMessage("");
            mPresenter.getPaymentVoidApiCall(calculatePosTransactionRes, wallet, (int) calculatePosTransactionRes.getTenderLine().get(position).getLineNo());
        } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Cash") || calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Credit")) {
            methodCalling = true;
            amounttoAdd = true;
            amountPosition = position;
            if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Cash") || calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Credit")) {
                wallet.setWalletType(0);
            }
            wallet.setMobileNo("");
            wallet.setOTP("");
            wallet.setOTPTransactionId("");
            wallet.setRequestURL("");
            wallet.setWalletRequestType(0);
            wallet.setWalletURL("");
            wallet.setWalletAmount(calculatePosTransactionRes.getTenderLine().get(position).getAmountCur());
            wallet.setRequestURL("");
            wallet.setResponse("");
            wallet.setPOSTransactionID(calculatePosTransactionRes.getTransactionId());
            wallet.setPOSTerminal(calculatePosTransactionRes.getTerminal());
            wallet.setWalletRefundId("");
            wallet.setWalletOrderID("");
            wallet.setWalletTransactionID("");
            wallet.setRewardsPoint(0);
            wallet.setUHID("");
            wallet.setRequestStatus(0);
            wallet.setReturnMessage("");
            mPresenter.getPaymentVoidApiCall(calculatePosTransactionRes, wallet, (int) calculatePosTransactionRes.getTenderLine().get(position).getLineNo());

        }
    }

    private ManualDiscDialog manualDiscDialog;

    @Override
    public void openManualDiscDialog(ManualDiscCheckRes body) {
        manualDiscDialog = new ManualDiscDialog(this);
        manualDiscDialog.setCategoryDisplayList(body.getDisplayList());
        manualDiscDialog.setAvailDiscountList(body.getAvailDiscountList());
        manualDiscDialog.setApplyDiscListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.toApplyManualDisc(body, manualDiscDialog.getDisplayListArrayList(), manualDiscDialog.getFixedDiscountCode());
                manualDiscDialog.dismiss();

            }
        });
        manualDiscDialog.setGenerateOTPListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.generateOTP();
            }
        });
        manualDiscDialog.setValidateOTPListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manualDiscDialog.isValidateOTP()) {
                    mPresenter.toApplyManualDisc(body, manualDiscDialog.getDisplayListArrayList(), manualDiscDialog.getFixedDiscountCode());
                    manualDiscDialog.dismiss();
                }
            }
        });
        manualDiscDialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manualDiscDialog.dismiss();
            }
        });
        manualDiscDialog.show();
    }

    @Override
    public void errorMessageDialog(String title, String message) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle(title);
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(message);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    @Override
    public void generateOTPResponseSuccess(String otp) {
        if (manualDiscDialog != null) {
            manualDiscDialog.visibleValidateOtp(otp);
        }
    }

    @Override
    public void clearOTPVIew() {
        addItemBinding.otpView.setText("");
    }

    @Override
    public void showDoctorSelectError() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("");
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle("Kindly Select Doctor");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    @Override
    public void closeOrderSuccess() {
        Constant.getInstance().isomsorder = false;
        clearOrderData();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void showOTPPopUp(double amount, String otp) {
        OTPDialog dialogView = new OTPDialog(this);
        dialogView.setOTP(otp);
        dialogView.setTitle("OTP");
        dialogView.setPositiveLabel("Ok");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.validateOTP()) {
                    dialogView.dismiss();
                    mPresenter.getPharmacyStaffApiDetails(dialogView.getEnteredOTP(), "VALOTP", amount);
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
    public void showCreditPayment(double amount, GetTrackingWiseConfing._TrackingConfigrationEntity entity) {
        CreditPaymentDialog dialogView = new CreditPaymentDialog(this);
        dialogView.setPaymentAmount(amount);
        dialogView.setTitle("Credit Payment");

        System.out.println("getempbilling value-->" + entity.getISEMPBilling());
        if (getCalculatedPosTransactionRes().getTenderLine().size() == 0)
            dialogView.setPositiveLabel("Ok");
        else dialogView.hidePositiveBtn();

        dialogView.setSubtitle("Total amount : " + orderTotalAmount());
        dialogView.setBalanceAmount("Balance Amount : " + Double.parseDouble(String.format("%.2f", (orderRemainingAmount() - amount))));
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.validateQuantity()) {
                    dialogView.dismiss();
                    GetOnlineCorporateListApiRequest request = new GetOnlineCorporateListApiRequest();
                    request.setStoreId(mPresenter.getStoreId());
                    request.setTerminalId("");
                    request.setType("GETSTAFFBILLINGCODE");
                    request.setValue(corporateEntity.getCode());
                    request.setISFMCGReturn(false);
                    request.setISPharmaReturn(false);
                    mPresenter.getOnlineOrderCorporateList(request, amount);
                    /*if (entity.getISEMPBilling() == 1) {
                        mPresenter.getPharmacyStaffApiDetails("", "ENQUIRY", amount);
                    } else if (entity.getISEMPBilling() == 0 && entity.getOTPTenderType().equalsIgnoreCase("2")) {
                        mPresenter.getPharmacyStaffApiDetails("", "ENQUIRY", amount);
                    } else {
                        getPaymentMethod().setCreditMode(true);
                        mPresenter.generateTenterLineService(amount, null);
                    }*/
                }
            }
        });
        dialogView.setNegativeLabel("Exit");
        dialogView.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    @Override
    public void showCouponCodeDialog(double categoryAmount) {
        CreditPaymentDialog dialogView = new CreditPaymentDialog(this);
        dialogView.setTitle("Coupon Discount");
        dialogView.setPositiveLabel("Apply Coupon");
        dialogView.setSubtitle("");
        dialogView.setInputType();
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.validateQuantity()) {
                    dialogView.dismiss();
                    mPresenter.applyCouponCodeApi(dialogView.getEnteredEditText(), categoryAmount);
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
    public void corpPrgTrackingError() {
        isExpand = true;
        ObjectAnimator anim = ObjectAnimator.ofFloat(addItemBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
        anim.setDuration(500);
        anim.start();
        rotationAngle += 180;
        rotationAngle = rotationAngle % 360;
        ViewAnimationUtils.expand(addItemBinding.detailsLayout.customerDoctorLayout);
        partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
    }

    @Override
    public void onUploadApiCall() {
        mPresenter.getUplaodPharmacyStaffApiDetails("ENQUIRY");
    }

    @Override
    public void onFaliureStaffListData(PharmacyStaffApiRes pharmacyStaffApiRes) {
        if (customerEntity != null) {
            String availableData = customerEntity.getAvailablePoints();
            if (customerEntity.getAvailablePoints().equalsIgnoreCase("")) {
                isCorporatePayment = false;
                addItemBinding.detailsLayout.availablePoints.setText("--");
                if (pharmacyStaffApiRes != null && pharmacyStaffApiRes.getMessage() != null) {
                    Toast.makeText(this, pharmacyStaffApiRes.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                isCorporatePayment = true;
                addItemBinding.detailsLayout.availablePoints.setText(availableData);
            }
        } else {
            isCorporatePayment = false;
            addItemBinding.detailsLayout.availablePoints.setText("--");
            if (pharmacyStaffApiRes != null && pharmacyStaffApiRes.getMessage() != null) {
                Toast.makeText(this, pharmacyStaffApiRes.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSucessStaffListData(PharmacyStaffApiRes staffApiRes) {
        double availableAmount = Double.parseDouble(staffApiRes.getTotalBalance()) - Double.parseDouble(staffApiRes.getUsedBalance());
        addItemBinding.detailsLayout.availablePoints.setText(String.valueOf(availableAmount));
        isCorporatePayment = true;
        clearallBtnVisibilitybyCorpPayment(isCorporatePayment);
        if (customerEntity != null) {
            customerEntity.setCardName(staffApiRes.getEmpName());
            customerEntity.setMobileNo(staffApiRes.getRegMobileNo());
            addItemBinding.setCustomer(customerEntity);
            getCalculatedPosTransactionRes().setCustomerName(staffApiRes.getEmpName());
        } else {
            customerEntity = new GetCustomerResponse.CustomerEntity();
            customerEntity.setCardName(staffApiRes.getEmpName());
            customerEntity.setMobileNo(staffApiRes.getRegMobileNo());
            addItemBinding.setCustomer(customerEntity);
            getCalculatedPosTransactionRes().setCustomerName(staffApiRes.getEmpName());
        }
    }

    int itemPosition = 0;
    double remDays = 0.0;

    @Override
    public void onItemClick(int position, double days, SalesLineEntity salesLineEntity) {
        itemPosition = position;
        remDays = days;
        for (int i = 0; i < Singletone.getInstance().itemsArrayList.size(); i++) {
            salesLineEntity.setRemainderDays(remDays);
        }
        medicinesDetailAdapter.notifyItemChanged(position);
    }

    @Override
    public String getSalesOrigin() {
        return saleslistData;
    }

    @Override
    public void onSuccessValidateOneApolloPoints(ValidatePointsResModel body) {
        if (body.getOneApolloProcessResult().getStatus().equalsIgnoreCase("True")) {
            paymentMethodModel.setLoadApolloPoints(false);
            paymentMethodModel.setErrorApolloPoints(false);
            addItemBinding.setValidatePoints(body.getOneApolloProcessResult());
        } else {
            paymentMethodModel.setLoadApolloPoints(false);
            paymentMethodModel.setErrorApolloPoints(true);
        }
    }

    @Override
    public void onFailedValidateOneApolloPoints(ValidatePointsResModel body) {
        paymentMethodModel.setLoadApolloPoints(false);
        paymentMethodModel.setErrorApolloPoints(true);
        showMessage("No Points are available to this number");
    }

    @Override
    public void onSuccessCheckProductTrackingWise(CalculatePosTransactionRes posTransactionRes) {
        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().itemsArrayList.addAll(posTransactionRes.getSalesLine());
        medicinesDetailAdapter.notifyDataSetChanged();
        if (!isOnleneOrder()) mPresenter.calculatePosTransaction();
    }

    private String strTxnId = null, emiID = null;
    String Name, amount, Invoice, LeadsId;
    public static String EMAIL;

    @Override
    public void onSuccessOmsAddNewItem(OmsAddNewItemResponse response, ArrayList<SalesLineEntity> itemsArrayList) {
        // ArrayList<SalesLineEntity> omsitemsArrayList=new ArrayList<>();
        for (SalesLineEntity entity : itemsArrayList) {
            //  if(!response.getItemID().equalsIgnoreCase(entity.getItemId())) {
            entity.setLineDiscPercentage(response.getDiscPer());
            entity.setOmsLineID(response.getOmsLineID());
            entity.setOmsLineRECID(response.getOmsLineRECID());
            //  omsitemsArrayList.add(entity);
            // }
        }

        Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);
        flag = 0;
        medicinesDetailAdapter.notifyDataSetChanged();
        mPresenter.calculatePosTransaction();
    }

    @Override
    public void onFailedOmsAddNewItem(OmsAddNewItemResponse response) {
        showMessage(response.getReturnMessage());
    }

    private String hdfcTransactionMarchantId;

    @Override
    public void onSuccessHdfcPaymentListGenerateApi(HdfcLinkGenerateResponse hdfcLinkGenerateResponse) {
        if (hdfcLinkGenerateResponse.getErrorCode().equals("0")) {
            this.hdfcTransactionMarchantId = hdfcLinkGenerateResponse.getTransactionMerchantID();
            Toast.makeText(this, hdfcLinkGenerateResponse.getSuccessMsg(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, hdfcLinkGenerateResponse.getErrorDesc(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailureHdfcPaymentListGenerateApi(HdfcLinkGenerateResponse hdfcLinkGenerateResponse) {

    }

    @Override
    public String getHdfcTransactionId() {
        return hdfcTransactionMarchantId;
    }

    @Override
    public void getGlobalConfig(GetGlobalConfingRes getGlobalConfingRes) {
        if (getGlobalConfingRes != null && getGlobalConfingRes.isISHBPStore()) {
            paymentMethodModel.setEnableHdfcPayBtn(true);
        } else {
            paymentMethodModel.setEnableHdfcPayBtn(false);
        }

//        if (getGlobalConfingRes != null && getGlobalConfingRes.getMPOSVersion().equals("2")) {
//            addItemBinding.detailsLayout.prgTrackingEdit.setEnabled(false);
//        }
    }

    @Override
    public void getHBPConfig(HBPConfigResponse hbpConfigResponse) {
//        if (hbpConfigResponse.getUHIDBilling())
//            addItemBinding.detailsLayout.prgTrackingEdit.setEnabled(false);
    }

    @Override
    public void showOTPDialog(String otp) {
        OTPDialog dialogView = new OTPDialog(this);
        dialogView.setOnOutSideCancel(false);
        dialogView.setOTP(otp);
        dialogView.setTitle("New customer details found");
        dialogView.setSubTitle("OTP has been sent to " + customerEntity.getMobileNo() + " Please verify");
        dialogView.setPositiveLabel("Ok");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.validateOTP()) {
                    dialogView.dismiss();
                    mPresenter.createNewCustomer();
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
    public void addCustomerFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isOnleneOrder() {
        return isOnlineOrder;
    }

    @Override
    public void onSuccessGetPostOnlineOrderApi(GetPostOnlineOrderApiResponse getPostOnlineOrderApiResponse) {
        if (getPostOnlineOrderApiResponse.getRequestStatus()) {
            onClickGenerateBill();
        } else {
            Toast.makeText(this, getPostOnlineOrderApiResponse.getRequestMessage(), Toast.LENGTH_SHORT).show();
            onClickGenerateBill();
        }

    }

    @Override
    public void onFailedGetPostOnlineOrderApi(GetPostOnlineOrderApiResponse getPostOnlineOrderApiResponse) {

    }

    @Override
    public void noStockAvailableClearAll() {
        if (addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode()) {
            addItemBinding.setIsPaymentMode(false);
            paymentMethodModel.setGenerateBill(false);
            if (isDonePayment()) {
                addItemBinding.setIsPaymentMode(true);
                paymentMethodModel.setGenerateBill(true);
                alertBackDialog();
            } else if (paymentMethodModel.isBalanceAmount() && paymentMethodModel.getBalanceAmount() < 0) {
                addItemBinding.setIsPaymentMode(true);
                paymentMethodModel.setGenerateBill(true);
                alertBackDialog();
            }
        } else {
            if (paymentDoneAmount == 0.0) {
                noStockAlertDialog();
            } else {
                partialPaymentDialog("Alert!", "Partial Payment done,Kindly void payment lines");
            }

        }
        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addItemBinding.imageView.setVisibility(View.GONE);
    }

    @Override
    public String getOnlineTransactionId() {
        return onlineTransactionId;
    }

    @Override
    public CalculatePosTransactionRes getUnPostedTransactionResponseBody() {
        return unPostedTransactionResponseBody;
    }

    @Override
    public Boolean isCameFromOrderDetailsScreenActivity() {
        return isCameFromOrderDetailsScreenActivity;
    }

    @Override
    public void SuccessOnlineorderCorporatelist(GetOnlineCorporateListApiResponse response, double amount) {
        if (response != null
                && response.getDropDownLineList() != null
                && response.getDropDownLineList().size() > 0) {
            mPresenter.getPharmacyStaffApiDetails("", "ENQUIRY", amount);
        } else {
            getPaymentMethod().setCreditMode(true);
            mPresenter.generateTenterLineService(amount, null);
        }
    }

    @Override
    public String getPatientType() {
        return selectedPatient;
    }

    @Override
    public String getTransactionId() {
        return addItemBinding.getTransaction().getTransactionID();
    }

    private void noStockAlertDialog() {
        Dialog dialog = new Dialog(this);
        DialogNoStockAvailableBinding dialogNoStockAvailableBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_no_stock_available, null, false);
        dialog.setContentView(dialogNoStockAvailableBinding.getRoot());
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogNoStockAvailableBinding.dialogButtonOK.setOnClickListener(v -> {
            dialog.dismiss();
            if (calculatePosTransactionRes != null) mPresenter.closeOrderVoidTransaction();
            else closeOrderSuccess();
        });
        dialog.show();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onactivity result additem activity-->", "activity result");
        if (resultCode == RESULT_OK) {
            try {
                switch (requestCode) {
                    case ACTIVITY_ADD_PRODUCT_CODE:
                        if (data != null) {
                            boolean itemNotFound = true;
                            ArrayList<SalesLineEntity> itemsArrayList = (ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item");
                            if (itemsArrayList != null) {
                                if (Constant.getInstance().isomsorder) {
                                    if (itemsArrayList.size() > 0) {
                                        boolean existitem = false;
                                        ArrayList<SalesLineEntity> salesLineEntities = calculatePosTransactionRes.getSalesLine();
                                        for (SalesLineEntity entity : itemsArrayList) {
                                            if (salesLineEntities.size() > 0) {
                                                for (SalesLineEntity entity1 : salesLineEntities) {
                                                    if (entity.getItemId().equalsIgnoreCase(entity1.getItemId())) {
                                                        entity.setOmsLineID(entity1.getOmsLineID());
                                                        entity.setOmsLineRECID(entity1.getOmsLineRECID());
                                                        existitem = true;
                                                    }
                                                }
                                            }
                                        }
                                        if (existitem) {
                                            Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);
                                            flag = 0;
                                            medicinesDetailAdapter.notifyDataSetChanged();
                                            mPresenter.calculatePosTransaction();
                                        } else {
                                            mPresenter.omsaddnewitem(itemsArrayList);
                                        }
                                    } else {
                                        Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);
                                        flag = 0;
                                        medicinesDetailAdapter.notifyDataSetChanged();
                                        mPresenter.calculatePosTransaction();
                                    }

                                } else {
                                    Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);
                                    flag = 0;
                                    medicinesDetailAdapter.notifyDataSetChanged();
                                    mPresenter.calculatePosTransaction();
                                }
                            }
                        }
                        break;
                    case ACTIVITY_CIRCLE_PLAN_CODE:
                        if (data != null) {
                            boolean itemNotFound = true;
                            ArrayList<SalesLineEntity> itemsArrayList = (ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item");
                            if (itemsArrayList != null) {
                                Singletone.getInstance().itemsArrayList.clear();
                                Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);

                                corporateEntity = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                                addItemBinding.setCorporate(corporateEntity);
                                //mPresenter.checkAllowedPaymentMode(paymentMethodModel);
                                // mPresenter.checkProductTrackingWise();
                                mPresenter.getUnpostedTransaction();
                                medicinesDetailAdapter.notifyDataSetChanged();
                                //mPresenter.calculatePosTransaction();

                            }

                        }
                        break;
                    case ACTIVITY_EPRESCRIPTIONLIST_CODE:
                        // System.out.println("Eprescription activity result" + "-->");
                        // Log.d("Eprescription activity result", "-->");
                        if (data != null) {
                            boolean itemNotFound = true;
                            ArrayList<SalesLineEntity> itemsArrayList = (ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item");
                            if (itemsArrayList != null) {

                                Constant.getInstance().isomsorder = true;
                                Constant.getInstance().isomsorder_check = true;

                                Singletone.getInstance().itemsArrayList.clear();
                                Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);

                                corporateEntity = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                                customerEntity = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                                OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem = (OMSTransactionHeaderResModel.OMSHeaderObj) data.getSerializableExtra("fullfillment_id");
                                customerDataResBean = (CustomerDataResBean) data.getSerializableExtra("customer_bean");
                                Log.d("corpoarte data", corporateEntity.getCode());
                                customerDataResBean.setREFNO(orderinfoitem.getREFNO());
                                addItemBinding.setCustomer(customerEntity);
                                addItemBinding.setCorporate(corporateEntity);
                                doctorEntity = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                                // doctorEntity.setCode("R000123");
                                // doctorEntity.setDisplayText(Doctorname);
                                addItemBinding.setDoctor(doctorEntity);
                                calculatePosTransactionRes.setDoctorCode(doctorEntity.getCode());
                                calculatePosTransactionRes.setDoctorName(doctorEntity.getDisplayText());
                                // addItemBinding.detailsLayout.customerDoctorLayout.set
                                addItemBinding.detailsLayout.prgTrackingEdit.setText(orderinfoitem.getREFNO());

                                mPresenter.checkAllowedPaymentMode(paymentMethodModel);
                                mPresenter.checkProductTrackingWise();
                                mPresenter.calculatePosTransaction();
                                medicinesDetailAdapter.notifyDataSetChanged();
                            }
                        }
                        break;

                    case CUSTOMER_SEARCH_ACTIVITY_CODE:
                        customerEntity = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                        if (customerEntity != null) {
                            addItemBinding.setCustomer(customerEntity);
                            if (customerEntity.getCardNo() == null || customerEntity.getCardNo().equalsIgnoreCase("")) {
                                addItemBinding.detailsLayout.prgTrackingEdit.setText("");
                            } else {
                                addItemBinding.detailsLayout.prgTrackingEdit.setText(customerEntity.getCardNo());
                            }
                            if (customerEntity.getTier() == null || customerEntity.getTier().equalsIgnoreCase("")) {
                                addItemBinding.detailsLayout.tier.setText("--");
                            } else {
                                addItemBinding.detailsLayout.tier.setText(customerEntity.getTier());
                            }
                            if (customerEntity.getAvailablePoints() == null || customerEntity.getAvailablePoints().equalsIgnoreCase("")) {
                                addItemBinding.detailsLayout.availablePoints.setText("--");
                            } else {
                                addItemBinding.detailsLayout.availablePoints.setText(customerEntity.getAvailablePoints());
                            }
                            if (calculatePosTransactionRes != null) {
                                calculatePosTransactionRes.setCustomerID(customerEntity.getCustId());
                                calculatePosTransactionRes.setCustAddress(customerEntity.getPostalAddress());
                                calculatePosTransactionRes.setCustomerName(customerEntity.getCardName());
                                calculatePosTransactionRes.setCustomerState(customerEntity.getState());
                                calculatePosTransactionRes.setDOB(customerEntity.getDob());
                            }
                            if (customerEntity.getCorpId() != null) {
                                for (CorporateModel.DropdownValueBean corporateModel : corporateModel.get_DropdownValue())
                                    if (corporateModel.getCode().equalsIgnoreCase(customerEntity.getCorpId())) {
                                        addItemBinding.setCorporate(corporateModel);
                                    }

                            } else {
                                addItemBinding.setCorporate(corporateModel.get_DropdownValue().get(0));
                            }
                        }

                        break;
                    case DOCTOR_SEARCH_ACTIVITY_CODE:
                        doctorEntity = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                        if (doctorEntity != null) {
                            addItemBinding.setDoctor(doctorEntity);
                            if (calculatePosTransactionRes != null) {
                                calculatePosTransactionRes.setDoctorCode(doctorEntity.getCode());
                                calculatePosTransactionRes.setDoctorName(doctorEntity.getDisplayText());
                            }
                        }
                        break;
                    case CORPORATE_SEARCH_ACTIVITY_CODE:
                        corporateEntity = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                        corporateEntity.setPrg_Tracking(addItemBinding.detailsLayout.prgTrackingEdit.getText().toString());
                        addItemBinding.setCorporate(corporateEntity);
                        mPresenter.checkAllowedPaymentMode(paymentMethodModel);
                        mPresenter.checkProductTrackingWise();
                        break;
                    case REQUEST_CODE_INITIALIZE:
                        if (resultCode == RESULT_OK) {
                            doPrepareDeviceEzeTap();
                        } else {
                            Log.i("Ezetap Error", data.toString());
                        }
                        break;
                    case REQUEST_CODE_UPI:
                    case REQUEST_CODE_CASH_TXN:
                    case REQUEST_CODE_CASH_BACK_TXN:
                    case REQUEST_CODE_CASH_AT_POS_TXN:
                    case REQUEST_CODE_WALLET_TXN:
                    case REQUEST_CODE_SALE_TXN:
                        if (resultCode == RESULT_OK) {
                            JSONObject response = new JSONObject(data.getStringExtra("response"));
                            response = response.getJSONObject("result");
                            response = response.getJSONObject("txn");
                            Constant.getInstance().card_transaction_id = response.getString("txnId");
                            Log.i("response-->", Constant.getInstance().card_transaction_id);
                            mPresenter.onSuccessCardPayment(response.getDouble("amountOriginal"));
                        } else if (resultCode == RESULT_CANCELED) {
                            JSONObject response = new JSONObject(data.getStringExtra("response"));
                            response = response.getJSONObject("error");
                            String errorCode = response.getString("code");
                            String errorMessage = response.getString("message");
                        }
                        break;
                    case REQUEST_CODE_PREPARE:
                        if (resultCode == RESULT_OK) {
                            JSONObject response = new JSONObject(data.getStringExtra("response"));
                            response = response.getJSONObject("result");
                            doPaymentEztap();
                        } else if (resultCode == RESULT_CANCELED) {
                            JSONObject response = new JSONObject(data.getStringExtra("response"));
                            response = response.getJSONObject("error");
                            String errorCode = response.getString("code");
                            String errorMessage = response.getString("message");
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onSuccessGetUnPostedPOSTransaction(CalculatePosTransactionRes body) {
        GetCustomerResponse.CustomerEntity entity = new GetCustomerResponse.CustomerEntity();
        entity.setCustId(body.getCustomerID());
        entity.setPostalAddress(body.getCustAddress());
        entity.setState(body.getCustomerState());
        entity.setCardName(body.getCustomerName());
        entity.setMobileNo(body.getMobileNO());
        entity.setSearchId(body.getMobileNO());
        entity.setCardNo(body.getTrackingRef());
        DoctorSearchResModel.DropdownValueBean doctorModule = new DoctorSearchResModel.DropdownValueBean();
        doctorModule.setCode(body.getDoctorCode());
        doctorModule.setDisplayText(body.getDoctorName());
        CorporateModel.DropdownValueBean corporateModule = new CorporateModel.DropdownValueBean();
        if (corporateModel != null) {
            for (CorporateModel.DropdownValueBean valueBean : corporateModel.get_DropdownValue()) {
                if (body.getCorpCode().equalsIgnoreCase(valueBean.getCode())) {
                    corporateModule.setCode(body.getCorpCode());
                    corporateModule.setDescription(valueBean.getDescription());
                    corporateModule.setPayMode(valueBean.getPayMode());
                    break;
                }
            }
        }
        TransactionIDResModel transactionIdModel = new TransactionIDResModel();
        transactionIdModel.setTransactionID(body.getTransactionId());
        addItemBinding.setTransaction(transactionIdModel);
        ArrayList<SalesLineEntity> saleslineentity = new ArrayList<>();
        saleslineentity = body.getSalesLine();
        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().itemsArrayList.addAll(saleslineentity);

        // medicinesDetailAdapter.notifyDataSetChanged();
        this.onlineTransactionId = body.getTransactionId();
        this.unPostedTransactionResponseBody = body;
        mPresenter.calculatePosTransaction();
    }


    private void doPrepareDeviceEzeTap() {
        EzeAPI.prepareDevice(this, REQUEST_CODE_PREPARE);
    }


    private void doPaymentEztap() {
        try {
            JSONObject jsonRequest = new JSONObject();
            JSONObject jsonOptionalParams = new JSONObject();
            JSONObject jsonReferences = new JSONObject();
            JSONObject jsonCustomer = new JSONObject();

            customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
            jsonCustomer.put("name", customerEntity.getLastName());
            jsonCustomer.put("mobileNo", customerEntity.getMobileNo());
            jsonCustomer.put("email", "");

            // calculatePosTransactionRes = (CalculatePosTransactionRes) getIntent().getSerializableExtra("calculatedPosRes");

            jsonReferences.put("reference1", mPresenter.getStoreId() + calculatePosTransactionRes.getTransactionId());
            jsonReferences.put("reference2", Invoice);
            jsonReferences.put("reference3", EMAIL);

            JSONArray array = new JSONArray();
            array.put("addRef_xx1");
            array.put("addRef_xx2");
            jsonReferences.put("additionalReferences", array);

            jsonOptionalParams.put("amountCashback", "");// Cannot
            jsonOptionalParams.put("amountTip", 0.00);
            jsonOptionalParams.put("references", jsonReferences);
            jsonOptionalParams.put("customer", jsonCustomer);

            double serviceFee = -1.0;
            String paymentBy = null;
            jsonOptionalParams.put("serviceFee", serviceFee);
            jsonOptionalParams.put("paymentBy", paymentBy);

            String accountLabel = null;
            jsonOptionalParams.put("payToAccount", accountLabel);

            JSONObject addlData = new JSONObject();
            addlData.put("addl1", "addl1");
            addlData.put("addl2", "addl2");
            addlData.put("addl3", "addl3");
            jsonOptionalParams.put("addlData", addlData);

            JSONObject appData = new JSONObject();
            appData.put("app1", "app1");
            appData.put("app2", "app2");
            appData.put("app3", "app3");
            jsonOptionalParams.put("appData", appData);
            jsonRequest.put("amount", getCardPaymentAmount().trim());
            jsonRequest.put("options", jsonOptionalParams);
            jsonRequest.put("mode", "SALE");//Card payment Mode
            doSaleTxn(jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSaleTxn(JSONObject jsonRequest) {
        EzeAPI.cardTransaction(this, REQUEST_CODE_SALE_TXN, jsonRequest);
    }


    private void alertDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Are you Sure?");
        dialogView.setPositiveLabel("Yes");
        if (Constant.getInstance().isomsorder) {
            dialogView.setSubtitle("Do you want to clear order Items");
        } else {
            dialogView.setSubtitle("Do you want to cancel this order");

        }
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                if (calculatePosTransactionRes != null) mPresenter.closeOrderVoidTransaction();
                else closeOrderSuccess();
            }
        });
        dialogView.setNegativeLabel("No");
        dialogView.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }


    private void clearOrderData() {
        customerEntity = new GetCustomerResponse.CustomerEntity();
        customerEntity.setCardName("");
        customerEntity.setMobileNo("");

        corporateEntity = new CorporateModel.DropdownValueBean();
        corporateEntity.setCode("");
        corporateEntity.setDescription("");

        doctorEntity = new DoctorSearchResModel.DropdownValueBean();
        doctorEntity.setCode("");
        doctorEntity.setDisplayText("");

        addItemBinding.setCustomer(customerEntity);
        addItemBinding.setCorporate(corporateEntity);
        addItemBinding.setDoctor(doctorEntity);

        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().isPlaceNewOrder = true;
        addItemBinding.cardPaymentAmountEditText.setText("");
        addItemBinding.cardPaymentAmountEditText.setText("");
        addItemBinding.oneApolloAmountEditText.setText("");
        paymentMethodModel.setPaymentDone(false);
        paymentMethodModel.setGenerateBill(false);
        isGeneratedBill = false;

        addItemBinding.detailsLayout.availablePoints.setText("--");
        addItemBinding.detailsLayout.prgTrackingEdit.setText("");
        isCorporatePayment = false;
        clearallBtnVisibilitybyCorpPayment(isCorporatePayment);
    }


    private void setEnableEditTextChange() {
        addItemBinding.cardPaymentAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (charSequence.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        addItemBinding.cardPaymentAmountEditText.setText(addItemBinding.cardPaymentAmountEditText.getText().toString().replace(".", ""));
                        addItemBinding.cardPaymentAmountEditText.setSelection(addItemBinding.cardPaymentAmountEditText.getText().length());
                    }
                } else {
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 && String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        addItemBinding.cardPaymentAmountEditText.setText(text.substring(0, text.length() - 1));
                        addItemBinding.cardPaymentAmountEditText.setSelection(addItemBinding.cardPaymentAmountEditText.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCardMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "card")) {
                                addItemBinding.cardPaymentAmountEditText.setText("");
                            }
                        }
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        addItemBinding.cardPaymentAmountEditText.setText(text.substring(0, text.length() - 1));
                        addItemBinding.cardPaymentAmountEditText.setSelection(addItemBinding.cardPaymentAmountEditText.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCardMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "card")) {
                                addItemBinding.cardPaymentAmountEditText.setText("");
                            }
                        }
                    }
                    if (!text.contains(".")) {
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCardMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "card")) {
                                addItemBinding.cardPaymentAmountEditText.setText("");
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty() && editable.toString().length() > 1) {
                    if (!TextUtils.isEmpty(editable) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCardMode()) {
                        if (!mPresenter.validTenderLimit(Double.parseDouble(editable.toString()), "card")) {
                            addItemBinding.cardPaymentAmountEditText.setText("");
                        }
                    }
                }
            }
        });

        addItemBinding.cashPaymentAmountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (charSequence.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        addItemBinding.cashPaymentAmountEdit.setText(addItemBinding.cashPaymentAmountEdit.getText().toString().replace(".", ""));
                        addItemBinding.cashPaymentAmountEdit.setSelection(addItemBinding.cashPaymentAmountEdit.getText().length());
                    }
                } else {
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 && String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        addItemBinding.cashPaymentAmountEdit.setText(text.substring(0, text.length() - 1));
                        addItemBinding.cashPaymentAmountEdit.setSelection(addItemBinding.cashPaymentAmountEdit.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCashMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "Cash")) {
                                addItemBinding.cashPaymentAmountEdit.setText("");
                            }
                        }
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        addItemBinding.cashPaymentAmountEdit.setText(text.substring(0, text.length() - 1));
                        addItemBinding.cashPaymentAmountEdit.setSelection(addItemBinding.cashPaymentAmountEdit.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCashMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "Cash")) {
                                addItemBinding.cashPaymentAmountEdit.setText("");
                            }
                        }
                    }
                    if (!text.contains(".")) {
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCashMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "Cash")) {
                                addItemBinding.cashPaymentAmountEdit.setText("");
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty() && editable.toString().length() > 1) {
                    if (addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCashMode()) {
                        if (!mPresenter.validTenderLimit(Double.parseDouble(editable.toString()), "Cash")) {
                            addItemBinding.cashPaymentAmountEdit.setText("");
                        }
                    }
                }
            }
        });

        addItemBinding.creditPaymentAmountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (charSequence.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        addItemBinding.creditPaymentAmountEdit.setText(addItemBinding.creditPaymentAmountEdit.getText().toString().replace(".", ""));
                        addItemBinding.creditPaymentAmountEdit.setSelection(addItemBinding.creditPaymentAmountEdit.getText().length());
                    } else if (text.indexOf("0") == 0) {
                        addItemBinding.creditPaymentAmountEdit.setText(addItemBinding.creditPaymentAmountEdit.getText().toString().replace("0", ""));
                    }
                } else {
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 && String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        addItemBinding.creditPaymentAmountEdit.setText(text.substring(0, text.length() - 1));
                        addItemBinding.creditPaymentAmountEdit.setSelection(addItemBinding.creditPaymentAmountEdit.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCreditMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "credit")) {
                                addItemBinding.creditPaymentAmountEdit.setText("");
                            }
                        }
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        addItemBinding.creditPaymentAmountEdit.setText(text.substring(0, text.length() - 1));
                        addItemBinding.creditPaymentAmountEdit.setSelection(addItemBinding.creditPaymentAmountEdit.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCreditMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "credit")) {
                                addItemBinding.creditPaymentAmountEdit.setText("");
                            }
                        }
                    }
                    if (!text.contains(".")) {
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCreditMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "credit")) {
                                addItemBinding.creditPaymentAmountEdit.setText("");
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty() && editable.toString().length() > 1) {
                    if (!TextUtils.isEmpty(editable) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isCreditMode()) {
                        if (!mPresenter.validTenderLimit(Double.parseDouble(editable.toString()), "credit")) {
                            addItemBinding.creditPaymentAmountEdit.setText("");
                        }
                    }
                }
            }
        });

        addItemBinding.oneApolloAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (charSequence.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        addItemBinding.oneApolloAmountEditText.setText(addItemBinding.oneApolloAmountEditText.getText().toString().replace(".", ""));
                        addItemBinding.oneApolloAmountEditText.setSelection(addItemBinding.oneApolloAmountEditText.getText().length());
                    } else if (text.indexOf("0") == 0) {
                        addItemBinding.oneApolloAmountEditText.setText(addItemBinding.oneApolloAmountEditText.getText().toString().replace("0", ""));
                    }
                } else {
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 && String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        addItemBinding.oneApolloAmountEditText.setText(text.substring(0, text.length() - 1));
                        addItemBinding.oneApolloAmountEditText.setSelection(addItemBinding.oneApolloAmountEditText.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isOneApolloMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "gift")) {
                                addItemBinding.oneApolloAmountEditText.setText("");
                            }
                        }
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        addItemBinding.oneApolloAmountEditText.setText(text.substring(0, text.length() - 1));
                        addItemBinding.oneApolloAmountEditText.setSelection(addItemBinding.oneApolloAmountEditText.getText().length());
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isOneApolloMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "gift")) {
                                addItemBinding.oneApolloAmountEditText.setText("");
                            }
                        }
                    }
                    if (!text.contains(".")) {
                        if (!TextUtils.isEmpty(text) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isOneApolloMode()) {
                            if (!mPresenter.validTenderLimit(Double.parseDouble(text), "gift")) {
                                addItemBinding.oneApolloAmountEditText.setText("");
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty() && editable.toString().length() > 1) {
                    if (!TextUtils.isEmpty(editable) && addItemBinding.getIsPaymentMode() != null && addItemBinding.getIsPaymentMode() && paymentMethodModel.isOneApolloMode()) {
                        if (!mPresenter.validTenderLimit(Double.parseDouble(editable.toString()), "gift")) {
                            addItemBinding.oneApolloAmountEditText.setText("");
                        }
                    }
                }
            }
        });
    }

    private void alertQuantityError(String message) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("");
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(message);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("activityTest OnResume method-->", "test");

    }

    private void clearallBtnVisibilitybyCorpPayment(boolean isCorporatePayment) {
        if (isCorporatePayment) {
            addItemBinding.clearAllImgTwo.setVisibility(View.VISIBLE);
            addItemBinding.detailsLayout.customereditclick.setEnabled(false);
//            addItemBinding.detailsLayout.doctoreditclick.setEnabled(false);
            addItemBinding.detailsLayout.corpoEdit.setEnabled(false);
            addItemBinding.detailsLayout.prgTrackingEdit.setEnabled(false);
        } else {
            addItemBinding.clearAllImgTwo.setVisibility(View.GONE);
            addItemBinding.detailsLayout.customereditclick.setEnabled(true);
//            addItemBinding.detailsLayout.doctoreditclick.setEnabled(true);
            addItemBinding.detailsLayout.corpoEdit.setEnabled(true);
            addItemBinding.detailsLayout.prgTrackingEdit.setEnabled(true);
        }
    }
}
