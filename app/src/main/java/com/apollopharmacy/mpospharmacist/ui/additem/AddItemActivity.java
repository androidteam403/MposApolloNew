package com.apollopharmacy.mpospharmacist.ui.additem;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddItemBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.adapter.ItemTouchHelperCallback;
import com.apollopharmacy.mpospharmacist.ui.additem.adapter.MainRecyclerAdapter;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentVoidReq;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentVoidRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PharmacyStaffApiRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.TenderLineEntity;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.WalletServiceRes;
import com.apollopharmacy.mpospharmacist.ui.additem.payadapter.PayActivityAdapter;
import com.apollopharmacy.mpospharmacist.ui.additem.payadapter.PayAdapterModel;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.OrderSummaryActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacist.ui.presenter.CustDocEditMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.apollopharmacy.mpospharmacist.utils.ViewAnimationUtils;
import com.eze.api.EzeAPI;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class AddItemActivity extends BaseActivity implements AddItemMvpView, CustDocEditMvpView {

    @Inject
    AddItemMvpPresenter<AddItemMvpView> mPresenter;
    private ActivityAddItemBinding addItemBinding;
    private CustDocEditMvpView custDocEditMvpView;
    //   private ArrayList<GetItemDetailsRes.Items> medicineDetailsModelsList = new ArrayList<>();
    private MainRecyclerAdapter medicinesDetailAdapter;
    private final int ACTIVITY_ADD_PRODUCT_CODE = 102;
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
    private SalesLineEntity salesLineEntity;
    private ArrayList<SalesLineEntity> salesLineEntityArrayList = new ArrayList<>();

    private final int REQUEST_CODE_INITIALIZE = 10001;
    private final int REQUEST_CODE_PREPARE = 10002;
    private final int REQUEST_CODE_WALLET_TXN = 10003;
    private final int REQUEST_CODE_CHEQUE_TXN = 10004;
    private final int REQUEST_CODE_SALE_TXN = 10006;
    private final int REQUEST_CODE_CASH_BACK_TXN = 10007;
    private final int REQUEST_CODE_CASH_AT_POS_TXN = 10008;
    private final int REQUEST_CODE_CASH_TXN = 10009;
    private final int REQUEST_CODE_SEARCH = 10010;
    private final int REQUEST_CODE_VOID = 10011;
    private final int REQUEST_CODE_ATTACH_SIGN = 10012;
    private final int REQUEST_CODE_UPDATE = 10013;
    private final int REQUEST_CODE_CLOSE = 10014;
    private final int REQUEST_CODE_GET_TXN_DETAIL = 10015;
    private final int REQUEST_CODE_GET_INCOMPLETE_TXN = 10016;
    private final int REQUEST_CODE_PAY = 10017;
    private final int REQUEST_CODE_UPI = 10018;
    private final int REQUEST_CODE_REMOTE_PAY = 10019;
    private final int REQUEST_CODE_QR_CODE_PAY = 10020;
    private final int REQUEST_CODE_NORMAL_EMI = 10021;
    private final int REQUEST_CODE_BRAND_EMI = 10022;
    private final int REQUEST_CODE_PRINT_RECEIPT = 10021;
    private final int REQUEST_CODE_PRINT_BITMAP = 10022;
    private boolean isExpand = true;
    private int rotationAngle = 180;
    private CorporateModel corporateModel;
    private int flag = 0;
    private boolean alertBack;
    private PharmacyStaffApiRes pharmacyStaffApiRes;
    private String remainningDays;
    double remaindValue = 0.0;
    SaveRetailsTransactionRes saveRetailsTransactionRes = new SaveRetailsTransactionRes();
    String saleslistData;
    private String prgData = null;


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

    public static Intent getStartIntent(Context context, boolean isBack, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, PharmacyStaffApiRes staffApiRes, String trackingData, String saleslistData) {
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

//    public static Intent getStartIntent(Context context, boolean isBack, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, PharmacyStaffApiRes staffApiRes, String availableAmt, String prgTrackingData) {
//        Intent intent = new Intent(context, AddItemActivity.class);
//        intent.putExtra("is_back", isBack);
//        intent.putExtra("customer_info", customerEntity);
//        intent.putExtra("doctor_info", doctor);
//        intent.putExtra("corporate_info", corporate);
//        intent.putExtra("transaction_id", transactionID);
//        intent.putExtra("corporate_model", corporateModel);
//        intent.putExtra("staff_avail_amt", staffApiRes);
//        intent.putExtra("prg_track", prgTrackingData);
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        return intent;
//    }

    public static Intent getStartIntent(Context context, boolean isBack, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID, CorporateModel corporateModel, PharmacyStaffApiRes staffApiRes, String availableAmt, String prgTrackingData, String saleslistData) {
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
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddItemActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        addItemBinding.siteName.setText(mPresenter.getStoreName());
        addItemBinding.siteId.setText(mPresenter.getStoreId());
        addItemBinding.terminalId.setText(mPresenter.getTerminalId());
        addItemBinding.setCallback(mPresenter);
        addItemBinding.setCall(this);
        addItemBinding.setClearMvpView(this);
        addItemBinding.setPaymentMode(paymentMethodModel);
        if (getIntent() != null) {

            customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
            if (customerEntity != null) {
                addItemBinding.setCustomer(customerEntity);
                if (customerEntity.getCardNo() == null || customerEntity.getCardNo().equalsIgnoreCase("")) {
                    addItemBinding.detailsLayout.prgTrackingEdit.setText("--");
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
                addItemBinding.setCorporate(corporateEntity);
            }

            corporateModel = (CorporateModel) getIntent().getSerializableExtra("corporate_model");
            transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
            if (transactionIdModel != null) {
                addItemBinding.setTransaction(transactionIdModel);
            }
            saleslistData = getIntent().getStringExtra("sales_list_data");
        }

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

//        Cart savedCart = RealmController.with(this).getCartTransaction(transactionIdModel.getTransactionID());
//        if (savedCart != null && savedCart.getItemsArrayList().size() > 0) {
//            for (int i = 0; i < savedCart.getItemsArrayList().size(); i++) {
//                CartItems items = savedCart.getItemsArrayList().get(i);
//                GetItemDetailsRes.Items cartItems = new GetItemDetailsRes.Items();
//                cartItems.setArtCode(items.getArtCode());
//                cartItems.setCategory(items.getCategory());
//                cartItems.setCategoryCode(items.getCategoryCode());
//                cartItems.setDescription(items.getDescription());
//                cartItems.setDiseaseType(items.getDiseaseType());
//                cartItems.setDPCO(items.isDPCO());
//                cartItems.setGenericName(items.getGenericName());
//                cartItems.setHsncode_In(items.getHsncode_In());
//                cartItems.setManufacture(items.getManufacture());
//                cartItems.setManufactureCode(items.getManufactureCode());
//                cartItems.setProductRecID(items.getProductRecID());
//                cartItems.setRackId(items.getRackId());
//                cartItems.setRetailCategoryRecID(items.getRetailCategoryRecID());
//                cartItems.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
//                cartItems.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
//                cartItems.setSch_Catg(items.getSch_Catg());
//                cartItems.setSch_Catg_Code(items.getSch_Catg_Code());
//                cartItems.setSI_NO(items.getSI_NO());
//                cartItems.setSubCategory(items.getSubCategory());
//                cartItems.setSubClassification(items.getSubClassification());
//                GetBatchInfoRes.BatchListObj batchList = new GetBatchInfoRes.BatchListObj();
//                batchList.setTotalTax(items.getTotalTax());
//                batchList.setSNO(items.getSNO());
//                batchList.setSGSTTaxCode(items.getSGSTTaxCode());
//                batchList.setSGSTPerc(items.getSGSTPerc());
//                batchList.setREQQTY(items.getREQQTY());
//                batchList.setQ_O_H(items.getQ_O_H());
//                batchList.setPrice(items.getPrice());
//                batchList.setNearByExpiry(items.isNearByExpiry());
//                batchList.setMRP(items.getMRP());
//                batchList.setItemID(items.getItemID());
//                batchList.setISMRPChange(items.isISMRPChange());
//                batchList.setIGSTTaxCode(items.getIGSTTaxCode());
//                batchList.setIGSTPerc(items.getIGSTPerc());
//                batchList.setExpDate(items.getExpDate());
//                batchList.setCGSTTaxCode(items.getCGSTTaxCode());
//                batchList.setCGSTPerc(items.getCGSTPerc());
//                batchList.setCESSPerc(items.getCESSPerc());
//                batchList.setCESSTaxCode(items.getCESSTaxCode());
//                batchList.setBatchNo(items.getBatchNo());
//                cartItems.setBatchListObj(batchList);
//                Singletone.getInstance().itemsArrayList.add(cartItems);
//            }
//
//        }

        addItemBinding.medicineRecycle.setLayoutManager(new LinearLayoutManager(this));
        medicinesDetailAdapter = new MainRecyclerAdapter(this, Singletone.getInstance().itemsArrayList);
        addItemBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);
        //     addItemBinding.medicineRecycle.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        ItemTouchHelperExtension.Callback mCallback = new ItemTouchHelperCallback();
        ItemTouchHelperExtension mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView(addItemBinding.medicineRecycle);
        medicinesDetailAdapter.setItemTouchHelperExtension(mItemTouchHelper);
        medicinesDetailAdapter.setAddItemMvpView(this);
//        medicinesDetailAdapter = new MedicinesDetailAdapter(this, medicineDetailsModelsList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        addItemBinding.medicineRecycle.setLayoutManager(mLayoutManager);
//        addItemBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);

//        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
//                //   medicinesDetailAdapter.remove(position);
//                medicinesDetailAdapter.notifyItemRemoved(position);
//                medicinesDetailAdapter.notifyItemRangeChanged(position, medicinesDetailAdapter.getItemCount());
//            }
//        });

//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(addItemBinding.medicineRecycle);
//
//        addItemBinding.medicineRecycle.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });
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
//        mPresenter.onMposTabApiCall();
//        turnOnScreen();
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
    public void onBackPressed() {
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addItemBinding.imageView.setVisibility(View.GONE);
        stopLooping = true;
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
        dialogView.show();
    }


    @Override
    public void onManualSearchClick() {
        startActivityForResult(ProductListActivity.getStartIntent(this, getCorporateModule(), getTransactionModule(), "1"), ACTIVITY_ADD_PRODUCT_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
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

//    private boolean remainderData() {
//        for (int i = 0; i < Singletone.getInstance().itemsArrayList.size(); i++) {
//            if (Singletone.getInstance().itemsArrayList.get(i).getDiseaseType().equalsIgnoreCase("")) {
//                if (salesLineEntityArrayList.size() == 0) {
//                    alertQuantityError("Please Enter Valid Required Qty!!");
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    @Override
    public void onPayButtonClick() {
        int flagChronic = 0;
        int flagBabyCare = 0;
        int pos = -1;
        for (int i = 0; i < Singletone.getInstance().itemsArrayList.size(); i++) {
            if (Singletone.getInstance().itemsArrayList.get(i).getDiseaseType().equalsIgnoreCase("Chronic") ||
                    Singletone.getInstance().itemsArrayList.get(i).getSubCategory().equalsIgnoreCase("BABY CARE")) {
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
            // addItemBinding.setIsPaymentMode(true);
            updatePayedAmount(calculatePosTransactionRes);
            addItemBinding.setIsPaymentMode(true);
            setEnableEditTextChange();
        } else {
            alertQuantityError("Please Enter Remainder Days!!");
        }
//        Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
//        addItemBinding.paymentLayout.startAnimation(bottomUp);
//        addItemBinding.paymentLayout.setVisibility(View.VISIBLE);
//        Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
//        addItemBinding.productListLayout.startAnimation(bottomDown);
//        addItemBinding.productListLayout.setVisibility(View.VISIBLE);
//        startActivity(PayActivity.getStartIntent(this,medicineDetailsModelsList,customerEntity,doctorEntity,corporateEntity,transactionIdModel));
//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCustomerEditClick() {
        if (customerEntity != null) {
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
        startActivityForResult(CorporateDetailsActivity.getStartIntent(this, corporateEntity, corporateModel), CORPORATE_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getCashPaymentAmount() {
        return addItemBinding.cashPaymentAmountEdit.getText().toString();
    }


    @Override
    public void getPrescriptionMandatory() {
//        for (int i = 0; i < Singletone.getInstance().itemsArrayList.size(); i++) {
//            if (Singletone.getInstance().itemsArrayList.get(i).getScheduleCategory().equalsIgnoreCase("H Scheduled")) {
//                alertPrescriptionDialog();
//            }
//        }
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
    public String getCreditPaymentAmount() {
        return addItemBinding.creditPaymentAmountEdit.getText().toString();
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
        calculatePosTransactionRes.setCustAccount(customerEntity.getCustId());
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
    public void setErrorCreditPaymentAmountEditText(String message) {
        showMessage(message);
    }

    @Override
    public void setErrorOneApolloPointsEditText(String message) {
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
        onCashmode = false;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() ||
                    addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(true);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setCreditMode(false);
                addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(true);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setCreditMode(false);
            addItemBinding.cardPaymentAmountEditText.setText(String.format("%.2f", (orderRemainingAmount())));
        }

    }

    private boolean onCashmode;

    @Override
    public void onClickCashPaymentBtn() {
        onWalletClick = false;
        onCardMode = false;
        onCashmode = true;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() ||
                    addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(true);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setPhonePeMode(false);
                paymentMethodModel.setPaytmMode(false);
                paymentMethodModel.setAirtelMode(false);
                addItemBinding.cashPaymentAmountEdit.setText(String.format("%.2f", (orderRemainingAmount())));
            }
        } else {
            paymentMethodModel.setCashMode(true);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setPhonePeMode(false);
            paymentMethodModel.setPaytmMode(false);
            paymentMethodModel.setAirtelMode(false);
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
        onCashmode = false;
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() ||
                    addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setOneApolloMode(true);
                paymentMethodModel.setLoadApolloPoints(true);
                paymentMethodModel.setErrorApolloPoints(false);
                paymentMethodModel.setWalletMode(false);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setPhonePeMode(false);
                paymentMethodModel.setPaytmMode(false);
                paymentMethodModel.setAirtelMode(false);
                if (customerEntity != null && transactionIdModel != null)
                    mPresenter.validateOneApolloPoints(customerEntity.getMobileNo(), transactionIdModel.getTransactionID());
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setOneApolloMode(true);
            paymentMethodModel.setLoadApolloPoints(true);
            paymentMethodModel.setErrorApolloPoints(false);
            paymentMethodModel.setWalletMode(false);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setPhonePeMode(false);
            paymentMethodModel.setPaytmMode(false);
            paymentMethodModel.setAirtelMode(false);
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
        if (flag == 0) {
            prescriptionMandatory();
            flag++;
        }
        if (!corporateEntity.getDescription().equalsIgnoreCase("0-NS NORMAL SALES")) {
            if (addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().isEmpty() ||
                    addItemBinding.detailsLayout.prgTrackingEdit.getText().toString().equalsIgnoreCase("--")) {
                partialPaymentDialog("", "Kindly select Partner Prg Tracking !");
            } else {
                paymentMethodModel.setCashMode(false);
                paymentMethodModel.setCardMode(false);
                paymentMethodModel.setOneApolloMode(false);
                paymentMethodModel.setWalletMode(true);
                paymentMethodModel.setCreditMode(false);
                paymentMethodModel.setPhonePeMode(false);
                paymentMethodModel.setPaytmMode(false);
                paymentMethodModel.setAirtelMode(false);
            }
        } else {
            paymentMethodModel.setCashMode(false);
            paymentMethodModel.setCardMode(false);
            paymentMethodModel.setOneApolloMode(false);
            paymentMethodModel.setWalletMode(true);
            paymentMethodModel.setCreditMode(false);
            paymentMethodModel.setPhonePeMode(false);
            paymentMethodModel.setPaytmMode(false);
            paymentMethodModel.setAirtelMode(false);
        }

    }

    @Override
    public void onClickCreditPaymentBtn() {
        onWalletClick = false;
        onCardMode = false;
        onCashmode = false;
        paymentMethodModel.setCashMode(false);
        paymentMethodModel.setCardMode(false);
        paymentMethodModel.setOneApolloMode(false);
        paymentMethodModel.setWalletMode(false);
        paymentMethodModel.setCreditMode(true);
        paymentMethodModel.setPhonePeMode(false);
        paymentMethodModel.setPaytmMode(false);
        paymentMethodModel.setAirtelMode(false);
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
    public void onSuccessGenerateTenderLine(GenerateTenderLineRes body) {
        showMessage("Generate TenderLine Success");
    }

    @Override
    public void onFailedGenerateTenderLine(GenerateTenderLineRes body) {
        showMessage("Generate TenderLine Failed");
    }

    double paymentDoneAmount = 0.0;

    @Override
    public void onSuccessSaveRetailTransaction(SaveRetailsTransactionRes body) {
        if (!TextUtils.isEmpty(body.getReciptId())) {
            body.setReminderDays(remaindValue);
            paymentMethodModel.setSaveRetailsTransactionRes(body);
            onClickGenerateBill();
//            onBillGenerate();
        } else {
            showMessage(body.getReturnMessage());
        }

//        if(paymentMethodModel.isCashMode()) {
//            paymentDoneAmount += Double.parseDouble(getCashPaymentAmount());
//            PayAdapterModel payAdapterModel = new PayAdapterModel("CASH PAID","₹ "+getCashPaymentAmount());
//            arrPayAdapterModel.add(payAdapterModel);
//        }else if(paymentMethodModel.isCardMode()){
//            paymentDoneAmount += Double.parseDouble(getCardPaymentAmount());
//            PayAdapterModel payAdapterModel = new PayAdapterModel("CARD PAID","₹ "+getCardPaymentAmount());
//            arrPayAdapterModel.add(payAdapterModel);
//        }else if(paymentMethodModel.isOneApolloMode()){
//            paymentDoneAmount += Double.parseDouble(getOneApolloPoints());
//            PayAdapterModel payAdapterModel = new PayAdapterModel("ONE APOLLO POINTS","₹ "+getOneApolloPoints());
//            arrPayAdapterModel.add(payAdapterModel);
//        }
//        payActivityAdapter.notifyDataSetChanged();

//       OrderPriceInfoModel priceInfoModel =  addItemBinding.getOrderInfo();
//        if(priceInfoModel.getOrderTotalAmount() >= paymentDoneAmount){
//            paymentMethodModel.setBalanceAmount(priceInfoModel.getOrderTotalAmount() - paymentDoneAmount);
//            paymentMethodModel.setBalanceAmount(true);
//        }else{
//            if(!TextUtils.isEmpty(body.getReciptId())) {
//                paymentMethodModel.setPaymentDone(true);
//                paymentMethodModel.setGenerateBill(true);
//                paymentMethodModel.setSaveRetailsTransactionRes(body);
//            }else{
//                showMessage(body.getReturnMessage());
//            }
//        }
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
//        posTransactionRes.setSalesOrigin(saleslistData);

//        if (parts.length>1){
//             salesCode = parts[1];
//        }else{
//            salesCode=posTransactionRes.getDoctorName();
//        }
        calculatePosTransactionRes = posTransactionRes;
//        txtSaving.Text = Convert.ToDecimal(Math.Round((POSSalesTransaction.DiscAmount / POSSalesTransaction.TotalMRP) * 100, 2)).ToString("#0.00");
//        txtMRP.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.TotalMRP, 2)).ToString("#0.00");
//        txtTaxableAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.NetAmount, 2)).ToString("#0.00");
//        txtNetTotal.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.GrossAmount - POSSalesTransaction.DiscAmount, 2)).ToString("#0.00");
//        txtTaxAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.TotalTaxAmount, 2)).ToString("#0.00");
//        txtDiscAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.DiscAmount, 2)).ToString("#0.00");
//        txtPharmaAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.SalesLine.Where(ObjLine => ObjLine.CategoryCode == "P" && ObjLine.IsVoid == false).Sum(ObjLine => ObjLine.NetAmountInclTax), 2)).ToString("#0.00");
//        txtPLAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.SalesLine.Where(ObjLine => ObjLine.CategoryCode == "A" && ObjLine.IsVoid == false).Sum(ObjLine => ObjLine.NetAmountInclTax), 2)).ToString("#0.00");
//        txtFMCGAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.SalesLine.Where(ObjLine => ObjLine.CategoryCode == "F" && ObjLine.IsVoid == false).Sum(ObjLine => ObjLine.NetAmountInclTax), 2)).ToString("#0.00");
        calculatePosTransactionRes.setRemainingamount(paymentMethodModel.getBalanceAmount());
        orderPriceInfoModel.setOrderSavingsAmount(posTransactionRes.getDiscAmount() / posTransactionRes.getTotalMRP() * 100);
        orderPriceInfoModel.setMrpTotalAmount(posTransactionRes.getTotalMRP());
        orderPriceInfoModel.setTaxableTotalAmount(posTransactionRes.getNetAmount());
        orderPriceInfoModel.setOrderTotalAmount(posTransactionRes.getGrossAmount() - posTransactionRes.getDiscAmount());
        orderPriceInfoModel.setDiscTotalAmount(posTransactionRes.getDiscAmount());
        orderPriceInfoModel.setRoundedAmount(posTransactionRes.getRoundedAmount());
        orderPriceInfoModel.setOrderSavingsPercentage(posTransactionRes.getDiscAmount() / posTransactionRes.getTotalMRP() * 100);
        orderPriceInfoModel.setTaxAmount(posTransactionRes.getTotalTaxAmount());
        paymentMethodModel.setBalanceAmount(Double.parseDouble(String.format("%.2f", (posTransactionRes.getGrossAmount() - posTransactionRes.getDiscAmount()) - paymentDoneAmount)));
        if (posTransactionRes.getSalesLine().size() > 0) {
            orderPriceInfoModel.setPharmaTotalAmount(0);
            orderPriceInfoModel.setFmcgTotalAmount(0);
            orderPriceInfoModel.setPlTotalAmount(0);
            for (int i = 0; i < posTransactionRes.getSalesLine().size(); i++) {
                if (!posTransactionRes.getSalesLine().get(i).getIsVoid()) {
                    if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("P"))
                        orderPriceInfoModel.setPharmaTotalAmount(orderPriceInfoModel.getPharmaTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
                    else if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("F"))
                        orderPriceInfoModel.setFmcgTotalAmount(orderPriceInfoModel.getFmcgTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
                    else if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("A"))
                        orderPriceInfoModel.setPlTotalAmount(orderPriceInfoModel.getPlTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());

//                    Singletone.getInstance().itemsArrayList.get(i).getBatchListObj().setCalculatedTotalPrice(posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
//                    Singletone.getInstance().itemsArrayList.get(i).getBatchListObj().setPreviewText(posTransactionRes.getSalesLine().get(i).getPreviewText());
//                    Singletone.getInstance().itemsArrayList.get(i).setItemDelete(posTransactionRes.getSalesLine().get(i).isVoid());
                }

            }
            Singletone.getInstance().itemsArrayList.clear();
            Singletone.getInstance().itemsArrayList.addAll(posTransactionRes.getSalesLine());
            medicinesDetailAdapter.notifyDataSetChanged();
        }
        if (getItemsCount() != 0) {
            addItemBinding.setItemsCount(getItemsCount());
            addItemBinding.setProductCount(getItemsCount());
        } else {
            addItemBinding.setItemsCount(getItemsCount());
            addItemBinding.footer.setVisibility(View.GONE);
        }
        addItemBinding.setOrderInfo(orderPriceInfoModel);

        isExpand = false;
        ObjectAnimator anim = ObjectAnimator.ofFloat(addItemBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
        anim.setDuration(500);
        anim.start();
        rotationAngle += 180;
        rotationAngle = rotationAngle % 360;
        ViewAnimationUtils.collapse(addItemBinding.detailsLayout.customerDoctorLayout);
        updatePayedAmount(calculatePosTransactionRes);
    }

    @Override
    public void onFailedCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes) {

    }

    @Override
    public void onSuccessOneApolloSendOtp(ValidatePointsResModel.OneApolloProcessResultEntity resultEntity) {
        paymentMethodModel.setOTPView(true);
        validatePointsResModel = resultEntity;
        //   addItemBinding.setValidatePoints(resultEntity);
    }

    @Override
    public void onSuccessOneApolloOtp(ValidatePointsResModel.OneApolloProcessResultEntity entity) {
        addItemBinding.setValidatePoints(entity);
        paymentMethodModel.setOTPView(false);
        mPresenter.generateTenterLineService(Double.parseDouble(entity.getRedeemPoints()), null);
        //  pdatePayedAmount(Double.parseDouble(getOneApolloPoints()), 3);
    }

    @Override
    public void isManualDisc(boolean isManualDisc) {
        paymentMethodModel.setAdditionalDisc(isManualDisc);
    }

    @Override
    public void onItemDeleted(int lineNumber) {
        if (paymentDoneAmount == 0.0) {
            mPresenter.voidProduct(lineNumber);
        } else {
            partialPaymentDialog("Alert!", "Partial Payment done,Kindly void payment lines");
        }
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
                        medicinesDetailAdapter.notifyDataSetChanged();
                        dialogView.dismiss();
                        mPresenter.changeQuantity(item, dialogView.getEnteredQuantity());
                        //mPresenter.calculatePosTransaction();
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
        startActivity(OrderSummaryActivity.getStartIntent(this, paymentMethodModel.getSaveRetailsTransactionRes(), corporateEntity, orderPriceInfoModel, paymentMethodModel));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void onBillGenerate() {
        startActivity(OrderSummaryActivity.getStartIntent(this, paymentMethodModel.getSaveRetailsTransactionRes(), corporateEntity, orderPriceInfoModel, paymentMethodModel));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public boolean isDonePayment() {
        return paymentDoneAmount == orderTotalAmount();
    }

    @Override
    public double orderTotalAmount() {
        OrderPriceInfoModel priceInfoModel = addItemBinding.getOrderInfo();
        if (priceInfoModel != null)
            return Double.parseDouble(String.format("%.2f", priceInfoModel.getOrderTotalAmount()));
        else
            return 0;
    }

    @Override
    public double orderRemainingAmount() {
        return Double.parseDouble(String.format("%.2f", (orderTotalAmount() - paymentDoneAmount)));
    }

    private boolean isGeneratedBill = false;
    private List<TenderLineEntity> tenderLineEntityList = new ArrayList<>();
    private boolean amounttoAdd;
    PayAdapterModel payAdapterModel;

    @Override
    public void updatePayedAmount(CalculatePosTransactionRes transactionRes) {
        if (clearItems) {
            transactionRes.getTenderLine().clear();
            clearItems = false;
        }
        List<Boolean> isAmount = new ArrayList<Boolean>();
        List<Integer> crossValue = new ArrayList<Integer>();
        List<Integer> strikeTextData = new ArrayList<>();
        isGeneratedBill = false;
        calculatePosTransactionRes = transactionRes;
        addItemBinding.cashPaymentAmountEdit.setText("");
        addItemBinding.cardPaymentAmountEditText.setText("");
        addItemBinding.oneApolloAmountEditText.setText("");
        addItemBinding.creditPaymentAmountEdit.setText("");
        if (transactionRes.getTenderLine().size() > 0) {
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
                    }
                    payAdapterModel = new PayAdapterModel(tenderLineEntity.getTenderName(), " " + tenderLineEntity.getAmountTendered(), tenderLineEntity.getAmountTendered());
                    if (tenderLineEntity.getTenderName().equalsIgnoreCase("card")) {
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
//                        if (tenderLineEntity.isVoid()) {
//                            paymentDoneAmount -= tenderLineEntity.getAmountTendered();
//                        }
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
//                            if (tenderLineEntity.isVoid()) {
//                                paymentDoneAmount -= tenderLineEntity.getAmountTendered();
//                            }
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
//                                paymentDoneAmount = paymentDoneAmount - tenderLineEntity.getAmountTendered();
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

        calculatePosTransactionRes.setRemainingamount(paymentMethodModel.getBalanceAmount());
//        if (isGeneratedBill) {
//            mPresenter.onClickGenerateBill();
//        }
    }

    @Override
    public void onSuccessPaymentVoidData(PaymentVoidRes paymentVoidRes) {
        if (paymentVoidRes != null) {
            if (paymentVoidRes.getTenderLine().get(amountPosition).isVoid) {
                arrPayAdapterModel.get(amountPosition).setStrikeThroughText(1);
            } else if (!paymentVoidRes.getTenderLine().get(amountPosition).isVoid) {
                arrPayAdapterModel.get(amountPosition).setStrikeThroughText(0);
            }
            if (calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("PhonePe") ||
                    calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("PAYTM") ||
                    calculatePosTransactionRes.getTenderLine().get(amountPosition).getTenderName().equalsIgnoreCase("Airtel")) {
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
        if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PhonePe") ||
                calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PAYTM") ||
                calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Airtel")) {
            methodCalling = true;
            amounttoAdd = false;
            amountPosition = pos;

//            updatePayedAmount(calculatePosTransactionRes);

            if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PhonePe")) {
                phonepay = "PhonePe";
                wallet.setWalletType(4);
            } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("PAYTM")) {
                phonepay = "PAYTM";
                wallet.setWalletType(3);
            } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Airtel")) {
                phonepay = "Airtel";
                wallet.setWalletType(2);
            }
            wallet.setMobileNo(calculatePosTransactionRes.getTenderLine().get(pos).getMobileNo());
            wallet.setOTP("");
            wallet.setOTPTransactionId("");
            wallet.setRequestURL("");
            wallet.setWalletRequestType(2);
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()) {
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
        } else if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Cash") ||
                calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Credit")) {
            methodCalling = true;
            amounttoAdd = false;
            amountPosition = pos;
//            updatePayedAmount(calculatePosTransactionRes);
            if (calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Cash") ||
                    calculatePosTransactionRes.getTenderLine().get(pos).getTenderName().equalsIgnoreCase("Credit")) {
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
        if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PhonePe") ||
                calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PAYTM") ||
                calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Airtel")) {
            methodCalling = true;
            amounttoAdd = true;
            amountPosition = position;

//            updatePayedAmount(calculatePosTransactionRes);

            if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PhonePe")) {
                phonepay = "PhonePe";
                wallet.setWalletType(4);
            } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("PAYTM")) {
                phonepay = "PAYTM";
                wallet.setWalletType(3);
            } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Airtel")) {
                phonepay = "Airtel";
                wallet.setWalletType(2);
            }
            wallet.setMobileNo(calculatePosTransactionRes.getTenderLine().get(position).getMobileNo());
            wallet.setOTP("");
            wallet.setOTPTransactionId("");
            wallet.setRequestURL("");
            wallet.setWalletRequestType(2);
            for (GetTenderTypeRes._TenderTypeEntity tenderTypeEntity : Singletone.getInstance().tenderTypeResultEntity.get_TenderType()) {
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
        } else if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Cash") ||
                calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Credit")) {
            methodCalling = true;
            amounttoAdd = true;
            amountPosition = position;
//            updatePayedAmount(calculatePosTransactionRes);
            if (calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Cash") ||
                    calculatePosTransactionRes.getTenderLine().get(position).getTenderName().equalsIgnoreCase("Credit")) {
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
//        paymentDoneAmount -= amount;
//        if (paymentDoneAmount == 0.0) {
//            paymentMethodModel.setBalanceAmount(orderTotalAmount() - paymentDoneAmount);
//            paymentMethodModel.setBalanceAmount(false);
//
//        }
//        paymentMethodModel.setPaymentDone(false);
//        paymentMethodModel.setGenerateBill(false);
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
//        dialogView.setNegativeLabel("No");
//        dialogView.setNegativeListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogView.dismiss();
//            }
//        });
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
//                    getPaymentMethod().setCreditMode(true);
//                    mPresenter.generateTenterLineService(orderRemainingAmount(),null);
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
        if (getCalculatedPosTransactionRes().getTenderLine().size() == 0)
            dialogView.setPositiveLabel("Ok");
        else
            dialogView.hidePositiveBtn();

        dialogView.setSubtitle("Total amount : " + orderTotalAmount());
        dialogView.setBalanceAmount("Balance Amount : " + Double.parseDouble(String.format("%.2f", (orderRemainingAmount() - amount))));
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.validateQuantity()) {
                    dialogView.dismiss();
                    if (entity.getISEMPBilling() == 1) {
                        mPresenter.getPharmacyStaffApiDetails("", "ENQUIRY", amount);
                    } else {
                        getPaymentMethod().setCreditMode(true);
                        mPresenter.generateTenterLineService(amount, null);
                    }
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
    public void onFaliureStaffListData() {
        if (customerEntity != null) {
            String availableData = customerEntity.getAvailablePoints();
            if (customerEntity.getAvailablePoints().equalsIgnoreCase("")) {
                addItemBinding.detailsLayout.availablePoints.setText("--");
            } else {
                addItemBinding.detailsLayout.availablePoints.setText(availableData);
            }
        } else {
            addItemBinding.detailsLayout.availablePoints.setText("--");
        }
    }

    @Override
    public void onSucessStaffListData(PharmacyStaffApiRes staffApiRes) {
        double availableAmount = Double.parseDouble(staffApiRes.getTotalBalance()) - Double.parseDouble(staffApiRes.getUsedBalance());
        addItemBinding.detailsLayout.availablePoints.setText(String.valueOf(availableAmount));
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
    public void onErrorshowOfRDays() {

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
    public ValidatePointsResModel.OneApolloProcessResultEntity onApolloPointsAvailablePoints() {
        return addItemBinding.getValidatePoints();
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
        mPresenter.calculatePosTransaction();
    }

    /**
     * unique ID for a transaction in EzeTap EMI Id associated with the
     * transaction
     */
    private String strTxnId = null, emiID = null;
    String Name, amount, Invoice, LeadsId;
    public static String EMAIL;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                switch (requestCode) {
                    case ACTIVITY_ADD_PRODUCT_CODE:
                        if (data != null) {
                            boolean itemNotFound = true;
                            ArrayList<SalesLineEntity> itemsArrayList = (ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item");
                            if (itemsArrayList != null) {
                                //  Singletone.getInstance().itemsArrayList.clear();
                                Singletone.getInstance().itemsArrayList.addAll(itemsArrayList);
                                flag = 0;
                                medicinesDetailAdapter.notifyDataSetChanged();
                                mPresenter.calculatePosTransaction();
//                                for(GetItemDetailsRes.Items items : itemsArrayList){
//                                    if(items != null && medicineDetailsModelsList.size() > 0){
//                                        for(int i=0; i< medicineDetailsModelsList.size(); i++){
//                                            GetItemDetailsRes.Items items1 = medicineDetailsModelsList.get(i);
//                                            if(items.getArtCode().equalsIgnoreCase(items1.getArtCode()) ){
//                                                double qoh = Double.parseDouble(items1.getBatchListObj().getQ_O_H());
//                                                int previewsCount = items1.getBatchListObj().getEnterReqQuantity();
//                                                double quantity = items1.getBatchListObj().getEnterReqQuantity() + items.getBatchListObj().getEnterReqQuantity();
//                                                if (qoh <= quantity) {
//                                                    items1.getBatchListObj().setEnterReqQuantity((int) qoh);
//                                                }else {
//                                                    items1.getBatchListObj().setEnterReqQuantity((int) quantity);
//                                                }
//                                                int afterCount = items1.getBatchListObj().getEnterReqQuantity();
//                                                if(previewsCount == afterCount){
//
//                                                }
//                                               // medicineDetailsModelsList.get(i).getBatchListObj().setEnterReqQuantity(items1.getBatchListObj().getEnterReqQuantity()+items.getBatchListObj().getEnterReqQuantity());
//
//                                            }
//                                        }
//                                    }
//                                    medicineDetailsModelsList.add(items);
//                                }
                            }


                            //  addItemBinding.setProductCount(Singletone.getInstance().itemsArrayList.size());
//                            // Insert into cart table
//                            if (items != null) {
//                                Cart savedCart = RealmController.with(this).getCartTransaction(transactionIdModel.getTransactionID());
//                                CartItems cartItems = new CartItems();
//                                cartItems.setArtCode(items.getArtCode());
//                                cartItems.setCategory(items.getCategory());
//                                cartItems.setCategoryCode(items.getCategoryCode());
//                                cartItems.setDescription(items.getDescription());
//                                cartItems.setDiseaseType(items.getDiseaseType());
//                                cartItems.setDPCO(items.getDPCO());
//                                cartItems.setGenericName(items.getGenericName());
//                                cartItems.setHsncode_In(items.getHsncode_In());
//                                cartItems.setManufacture(items.getManufacture());
//                                cartItems.setManufactureCode(items.getManufactureCode());
//                                cartItems.setProductRecID(items.getProductRecID());
//                                cartItems.setRackId(items.getRackId());
//                                cartItems.setRetailCategoryRecID(items.getRetailCategoryRecID());
//                                cartItems.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
//                                cartItems.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
//                                cartItems.setSch_Catg(items.getSch_Catg());
//                                cartItems.setSch_Catg_Code(items.getSch_Catg_Code());
//                                cartItems.setSI_NO(items.getSI_NO());
//                                cartItems.setSubCategory(items.getSubCategory());
//                                cartItems.setSubClassification(items.getSubClassification());
//                                cartItems.setTotalTax(items.getBatchListObj().getTotalTax());
//                                cartItems.setSNO(items.getBatchListObj().getSNO());
//                                cartItems.setSGSTTaxCode(items.getBatchListObj().getSGSTTaxCode());
//                                cartItems.setSGSTPerc(items.getBatchListObj().getSGSTPerc());
//                                cartItems.setREQQTY(items.getBatchListObj().getREQQTY());
//                                cartItems.setQ_O_H(items.getBatchListObj().getQ_O_H());
//                                cartItems.setPrice(items.getBatchListObj().getPrice());
//                                cartItems.setNearByExpiry(items.getBatchListObj().getNearByExpiry());
//                                cartItems.setMRP(items.getBatchListObj().getMRP());
//                                cartItems.setItemID(items.getBatchListObj().getItemID());
//                                cartItems.setISMRPChange(items.getBatchListObj().getISMRPChange());
//                                cartItems.setIGSTTaxCode(items.getBatchListObj().getIGSTTaxCode());
//                                cartItems.setIGSTPerc(items.getBatchListObj().getIGSTPerc());
//                                cartItems.setExpDate(items.getBatchListObj().getExpDate());
//                                cartItems.setCGSTTaxCode(items.getBatchListObj().getCGSTTaxCode());
//                                cartItems.setCGSTPerc(items.getBatchListObj().getCGSTPerc());
//                                cartItems.setCESSPerc(items.getBatchListObj().getCESSPerc());
//                                cartItems.setCESSTaxCode(items.getBatchListObj().getCESSTaxCode());
//                                cartItems.setBatchNo(items.getBatchListObj().getBatchNo());
//                                if (savedCart != null) {
//                                    Realm realm = RealmController.with(this).getRealm();
//                                    realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
//                                        @Override
//                                        public void execute(Realm realm) {
//                                            // increment index
//                                            Number currentIdNum = realm.where(CartItems.class).max("id");
//                                            int nextId;
//                                            if (currentIdNum == null) {
//                                                nextId = 1;
//                                            } else {
//                                                nextId = currentIdNum.intValue() + 1;
//                                            }
//                                            cartItems.setId(nextId);
//                                            //  realm.beginTransaction();
//                                            savedCart.getItemsArrayList().add(cartItems);
//                                        //    realm.copyToRealmOrUpdate(savedCart);
//                                            // realm.commitTransaction();
//                                        }
//                                    });
//
//                                } else {
//                                    RealmList<CartItems> itemsRealmList = new RealmList<>();
//                                    itemsRealmList.add(cartItems);
//                                    Cart cart = new Cart();
//                                    cart.setTransactionId(transactionIdModel.getTransactionID());
//                                    cart.setItemsArrayList(itemsRealmList);
//                                    Realm realm = RealmController.with(this).getRealm();
//                                    realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
//                                        @Override
//                                        public void execute(Realm realm) {
//                                            // increment index
//                                            Number currentIdNum = realm.where(Cart.class).max("id");
//                                            int nextId;
//                                            if (currentIdNum == null) {
//                                                nextId = 1;
//                                            } else {
//                                                nextId = currentIdNum.intValue() + 1;
//                                            }
//                                            cart.setId(nextId);
//                                            //  realm.beginTransaction();
//
//                                         //   realm.copyToRealmOrUpdate(cart);
//                                            // realm.commitTransaction();
//                                        }
//                                    });
//                                    //realm.beginTransaction();
//                                    //realm.copyToRealm(cart);
//                                    //realm.commitTransaction();
//                                }
//
//                            }


                        }
                        break;
                    case CUSTOMER_SEARCH_ACTIVITY_CODE:
                        customerEntity = (GetCustomerResponse.CustomerEntity) data.getSerializableExtra("customer_info");
                        if (customerEntity != null) {
                            addItemBinding.setCustomer(customerEntity);

                            if (customerEntity.getCardNo() == null || customerEntity.getCardNo().equalsIgnoreCase("")) {
                                addItemBinding.detailsLayout.prgTrackingEdit.setText("--");
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
                                //  calculatePosTransactionRes.setGender(customerEntity.getGender().equalsIgnoreCase("Male") ? 0 : 1);
                                calculatePosTransactionRes.setDOB(customerEntity.getDob());
                            }
                            if (customerEntity.getCorpId() != null) {
                                for (CorporateModel.DropdownValueBean corporateModel : corporateModel.get_DropdownValue())
                                    if (corporateModel.getCode().equalsIgnoreCase(customerEntity.getCorpId())) {
                                        addItemBinding.setCorporate(corporateModel);
                                    }

                            } else {
                                addItemBinding.setCorporate(corporateModel.get_DropdownValue().get(0));
//                                addItemBinding.detailsLayout.prgTrackingEdit.setText("--");
//                                addItemBinding.detailsLayout.tier.setText("--");
//                                addItemBinding.detailsLayout.availablePoints.setText("--");
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
                        addItemBinding.setCorporate(corporateEntity);
                        mPresenter.checkAllowedPaymentMode(paymentMethodModel);
                        mPresenter.checkProductTrackingWise();
                        break;
                    case REQUEST_CODE_INITIALIZE:
                        if (resultCode == RESULT_OK) {
                            doPrepareDeviceEzeTap();
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


    /**
     * optional mechanism to prepare a device for card transactions
     */
    private void doPrepareDeviceEzeTap() {
        EzeAPI.prepareDevice(this, REQUEST_CODE_PREPARE);
    }


    private void doPaymentEztap() {
        try {
            JSONObject jsonRequest = new JSONObject();
            JSONObject jsonOptionalParams = new JSONObject();
            JSONObject jsonReferences = new JSONObject();
            JSONObject jsonCustomer = new JSONObject();
            // Building Customer Object
            jsonCustomer.put("name", "jagadeesh");
            jsonCustomer.put("mobileNo", "9160147044");
            jsonCustomer.put("email", "jagadish.v@thresholdsoft.com");

            // Building References Object
            jsonReferences.put("reference1", "urefu7869");
            jsonReferences.put("reference2", Invoice);
            jsonReferences.put("reference3", EMAIL);

            // Passing Additional References
            JSONArray array = new JSONArray();
            array.put("addRef_xx1");
            array.put("addRef_xx2");
            jsonReferences.put("additionalReferences", array);

            // Building Optional params Object
            jsonOptionalParams.put("amountCashback", "");// Cannot
            // have
            // amount cashback in SALE transaction.
            jsonOptionalParams.put("amountTip", 0.00);
            jsonOptionalParams.put("references", jsonReferences);
            jsonOptionalParams.put("customer", jsonCustomer);

            // Service Fee
            double serviceFee = -1.0;
            String paymentBy = null;
//            if (serviceFeeEditText.getText().toString().length() > 0) {
//                serviceFee = Double.parseDouble(serviceFeeEditText.getText().toString());
//            }
//            if (paymentByEditText.getText().toString().length() > 0) {
//                paymentBy = paymentByEditText.getText().toString();
//            }
            jsonOptionalParams.put("serviceFee", serviceFee);
            jsonOptionalParams.put("paymentBy", paymentBy);

            // Pay to Account
            String accountLabel = null;
//            if (accountLabelEditTet.getText().toString().length() > 0) {
//                accountLabel = accountLabelEditTet.getText().toString();
//            }
            jsonOptionalParams.put("payToAccount", accountLabel);

//            if(REQUEST_CODE == REQUEST_CODE_BRAND_EMI || REQUEST_CODE == REQUEST_CODE_PAY)
//            {
//                JSONObject brandDetails = new JSONObject();
//                brandDetails.put("SKUCode", productCodeEditText.getText().toString().trim());
//                brandDetails.put("brand", productBrandEditText.getText().toString().trim());
//                brandDetails.put("serial", productSerialEditText.getText().toString().trim());
//                jsonOptionalParams.put("productDetails", brandDetails);
//            }

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

            // Building final request object
            jsonRequest.put("amount", getCardPaymentAmount().trim());
            jsonRequest.put("options", jsonOptionalParams);

//            InputMethodManager imm = (InputMethodManager) Online_Payment.this
//                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(emailIdEditText.getWindowToken(), 0);

            jsonRequest.put("mode", "SALE");//Card payment Mode
            doSaleTxn(jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Take credit card transactions for Visa, Mastercard and Rupay. Debit card
     * transactions for Indian banks. Ability to perform EMI option.
     */
    private void doSaleTxn(JSONObject jsonRequest) {
        /******************************************
         {
         "amount": "123",
         "options": {
         "amountCashback": 0,
         "amountTip": 0,
         "references": {
         "reference1": "1234",
         "additionalReferences": [
         "addRef_xx1",
         "addRef_xx2"
         ]
         },
         "customer": {
         "name": "xyz",
         "mobileNo": "1234567890",
         "email": "abc@xyz.com"
         }
         },
         "mode": "SALE"
         }
         ******************************************/
        EzeAPI.cardTransaction(this, REQUEST_CODE_SALE_TXN, jsonRequest);
    }


    private void alertDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Are you Sure?");
        dialogView.setPositiveLabel("Yes");
        dialogView.setSubtitle("Do you want to cancel this order");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                if (calculatePosTransactionRes != null)
                    mPresenter.closeOrderVoidTransaction();
                else
                    closeOrderSuccess();
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
        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().isPlaceNewOrder = true;
        addItemBinding.cardPaymentAmountEditText.setText("");
        addItemBinding.cardPaymentAmountEditText.setText("");
        addItemBinding.oneApolloAmountEditText.setText("");
        paymentMethodModel.setPaymentDone(false);
        paymentMethodModel.setGenerateBill(false);
        isGeneratedBill = false;
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
//                    else if (text.indexOf("0") == 0) {
//                        addItemBinding.cardPaymentAmountEditText.setText(addItemBinding.cardPaymentAmountEditText.getText().toString().replace("0", ""));
//                    }
                } else {
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
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
//                    else if (text.indexOf("0") == 0) {
//                        addItemBinding.cashPaymentAmountEdit.setText(addItemBinding.cashPaymentAmountEdit.getText().toString().replace("0", ""));
//                    }
                } else {
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
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
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
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
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
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
                //finish();
            }
        });
        dialogView.show();
    }

    private List<RowsEntity> rowsEntitiesList;

    @Override
    public void onSucessPlayList() {
        rowsEntitiesList = mPresenter.getDataListEntity();
        for (int i = 0; i < rowsEntitiesList.size(); i++) {
            if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                    rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                File file = new File(path);
                if (!file.exists()) {
                    mPresenter.onDownloadApiCall(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
                            rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), i);
                    break;
                }
            }
        }
    }

    private boolean stopLooping;

    public void handelPlayList() {
        if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
            if (!onPause)
                if (!stopLooping) {
                    boolean isAllFilesExist = false;
                    for (int i = 0; i < rowsEntitiesList.size(); i++) {
                        if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                                rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                            if (!rowsEntitiesList.get(i).isPlayed()) {
                                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                                File file = new File(path);
                                if (file.exists()) {
                                    playListData(path, i);
                                    isAllFilesExist = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!isAllFilesExist) {
                        for (int i = 0; i < rowsEntitiesList.size(); i++) {
                            rowsEntitiesList.get(i).setPlayed(false);
                        }
                        handelPlayList();
                    }
                }
        }

    }

    public void playListData(String filePath, int pos) {
        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        addItemBinding.imageView.setImageBitmap(myBitmap);
        addItemBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        addItemBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsEntitiesList.get(pos).setPlayed(true);
                if (pos == rowsEntitiesList.size() - 1) {
                    for (RowsEntity rowsEntity : rowsEntitiesList) {
                        rowsEntity.setPlayed(false);
                    }
                }
                handelPlayList();
            }
        }, 5000);
    }

    private boolean onPause;

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        onPause = false;
//        idealScreen();
    }


    private Handler handler;
    private Runnable r;

    public void idealScreen() {
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                if (onPause) {
                    stopLooping = true;
                } else {
                    stopLooping = false;
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    getSupportActionBar().hide();
                }
                handelPlayList();
                addItemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        addItemBinding.imageView.setVisibility(View.GONE);
//                        getSupportActionBar().show();
                        stopLooping = true;
                    }
                });
            }
        };
//        startHandler();
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 180 * 1000);
    }


//    @Override
//    public void onUserInteraction() {
//        super.onUserInteraction();
//        stopHandler();
//        startHandler();
//    }

    public void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

}
