package com.apollopharmacy.mpospharmacist.ui.additem;

import android.Manifest;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.db.model.Cart;
import com.apollopharmacy.mpospharmacist.data.db.model.CartItems;
import com.apollopharmacy.mpospharmacist.data.db.realm.RealmController;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddItemBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.adapter.MedicinesDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.ordersummary.OrderSummaryActivity;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.pay.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacist.ui.pay.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayActivityAdapter;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayAdapterModel;
import com.apollopharmacy.mpospharmacist.ui.presenter.CustDocEditMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.SwipeController;
import com.apollopharmacy.mpospharmacist.utils.SwipeControllerActions;
import com.eze.api.EzeAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;

public class AddItemActivity extends BaseActivity implements AddItemMvpView, CustDocEditMvpView {

    @Inject
    AddItemMvpPresenter<AddItemMvpView> mPresenter;
    private ActivityAddItemBinding addItemBinding;
    private CustDocEditMvpView custDocEditMvpView;
    private ArrayList<GetItemDetailsRes.Items> medicineDetailsModelsList = new ArrayList<>();
    private MedicinesDetailAdapter medicinesDetailAdapter;
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

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AddItemActivity.class);
    }

    public static Intent getStartIntent(Context context, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
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
        addItemBinding.setCallback(mPresenter);
        addItemBinding.setCall(this);
        addItemBinding.setPaymentMode(paymentMethodModel);
        if (getIntent() != null) {
            customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
            if (customerEntity != null) {
                addItemBinding.setCustomer(customerEntity);
            }
            doctorEntity = (DoctorSearchResModel.DropdownValueBean) getIntent().getSerializableExtra("doctor_info");
            if (doctorEntity != null) {
                addItemBinding.setDoctor(doctorEntity);
            }
            corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
            transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
            if (transactionIdModel != null) {
                addItemBinding.setTransaction(transactionIdModel);
            }
        }
        addItemBinding.setProductCount(medicineDetailsModelsList.size());

        Cart savedCart = RealmController.with(this).getCartTransaction(transactionIdModel.getTransactionID());
        if (savedCart != null && savedCart.getItemsArrayList().size() > 0) {
            for (int i = 0; i < savedCart.getItemsArrayList().size(); i++) {
                CartItems items = savedCart.getItemsArrayList().get(i);
                GetItemDetailsRes.Items cartItems = new GetItemDetailsRes.Items();
                cartItems.setArtCode(items.getArtCode());
                cartItems.setCategory(items.getCategory());
                cartItems.setCategoryCode(items.getCategoryCode());
                cartItems.setDescription(items.getDescription());
                cartItems.setDiseaseType(items.getDiseaseType());
                cartItems.setDPCO(items.isDPCO());
                cartItems.setGenericName(items.getGenericName());
                cartItems.setHsncode_In(items.getHsncode_In());
                cartItems.setManufacture(items.getManufacture());
                cartItems.setManufactureCode(items.getManufactureCode());
                cartItems.setProductRecID(items.getProductRecID());
                cartItems.setRackId(items.getRackId());
                cartItems.setRetailCategoryRecID(items.getRetailCategoryRecID());
                cartItems.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
                cartItems.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
                cartItems.setSch_Catg(items.getSch_Catg());
                cartItems.setSch_Catg_Code(items.getSch_Catg_Code());
                cartItems.setSI_NO(items.getSI_NO());
                cartItems.setSubCategory(items.getSubCategory());
                cartItems.setSubClassification(items.getSubClassification());
                GetBatchInfoRes.BatchListObj batchList = new GetBatchInfoRes.BatchListObj();
                batchList.setTotalTax(items.getTotalTax());
                batchList.setSNO(items.getSNO());
                batchList.setSGSTTaxCode(items.getSGSTTaxCode());
                batchList.setSGSTPerc(items.getSGSTPerc());
                batchList.setREQQTY(items.getREQQTY());
                batchList.setQ_O_H(items.getQ_O_H());
                batchList.setPrice(items.getPrice());
                batchList.setNearByExpiry(items.isNearByExpiry());
                batchList.setMRP(items.getMRP());
                batchList.setItemID(items.getItemID());
                batchList.setISMRPChange(items.isISMRPChange());
                batchList.setIGSTTaxCode(items.getIGSTTaxCode());
                batchList.setIGSTPerc(items.getIGSTPerc());
                batchList.setExpDate(items.getExpDate());
                batchList.setCGSTTaxCode(items.getCGSTTaxCode());
                batchList.setCGSTPerc(items.getCGSTPerc());
                batchList.setCESSPerc(items.getCESSPerc());
                batchList.setCESSTaxCode(items.getCESSTaxCode());
                batchList.setBatchNo(items.getBatchNo());
                cartItems.setBatchListObj(batchList);
                medicineDetailsModelsList.add(cartItems);
            }

        }

        medicinesDetailAdapter = new MedicinesDetailAdapter(this, medicineDetailsModelsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        addItemBinding.medicineRecycle.setLayoutManager(mLayoutManager);
        addItemBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);

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
        addItemBinding.setProductCount(medicineDetailsModelsList.size());

        payActivityAdapter = new PayActivityAdapter(this, arrPayAdapterModel);
        RecyclerView.LayoutManager mLayoutManagerOne = new LinearLayoutManager(this);
        addItemBinding.payAmount.setLayoutManager(mLayoutManagerOne);
        addItemBinding.payAmount.setItemAnimator(new DefaultItemAnimator());
        addItemBinding.payAmount.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        addItemBinding.payAmount.setAdapter(payActivityAdapter);
    }

    @Override
    public void onBackPressed() {
        alertDialog();
    }

    @Override
    public void onManualSearchClick() {
        startActivityForResult(ProductListActivity.getStartIntent(this, getCorporateModule(),getTransactionModule(), "1"), ACTIVITY_ADD_PRODUCT_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onVoiceSearchClick() {
        requestAudioPermissions();
    }

    @Override
    public void onBarCodeSearchClick() {
        startActivityForResult(ProductListActivity.getStartIntent(this, getCorporateModule(),getTransactionModule(), "3"), ACTIVITY_ADD_PRODUCT_CODE);
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
            startActivityForResult(ProductListActivity.getStartIntent(this, getCorporateModule(),getTransactionModule(), "2"), ACTIVITY_ADD_PRODUCT_CODE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public void onClearAll() {
        ExitInfoDialog  dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Are you Sure?");
        dialogView.setPositiveLabel("Yes");
        dialogView.setSubtitle("Do you want to clear order Items");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                medicineDetailsModelsList.clear();
                medicinesDetailAdapter.notifyDataSetChanged();
                addItemBinding.setProductCount(medicineDetailsModelsList.size());
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

    @Override
    public void onPayButtonClick() {
        addItemBinding.setIsPaymentMode(true);
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
    public Context getContext() {
        return this;
    }

    @Override
    public String getCashPaymentAmount() {
        return addItemBinding.cashPaymentAmountEdit.getText().toString();
    }

    @Override
    public String getCardPaymentAmount() {
        return addItemBinding.cardPaymentAmountEditText.getText().toString();
    }

    @Override
    public String getOneApolloPoints() {
        return addItemBinding.oneApolloAmountEditText.getText().toString();
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

    @Override
    public ValidatePointsResModel.OneApolloProcessResultEntity getValidateOneApolloPoints() {
        return addItemBinding.getValidatePoints();
    }

    @Override
    public ArrayList<GetItemDetailsRes.Items> getSelectedProducts() {
        return medicineDetailsModelsList;
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
    public void setErrorOneApolloPointsEditText(String message) {
        showMessage(message);
    }

    @Override
    public void setErrorOneApolloOtpEditText(String message) {
        showMessage(message);
    }

    @Override
    public void onClickCardPaymentBtn() {
        paymentMethodModel.setCashMode(false);
        paymentMethodModel.setCardMode(true);
        paymentMethodModel.setOneApolloMode(false);
    }

    @Override
    public void onClickCashPaymentBtn() {
        paymentMethodModel.setCashMode(true);
        paymentMethodModel.setCardMode(false);
        paymentMethodModel.setOneApolloMode(false);
    }

    @Override
    public void onClickOneApolloBtn() {
        paymentMethodModel.setCashMode(false);
        paymentMethodModel.setCardMode(false);
        paymentMethodModel.setOneApolloMode(true);
        if (customerEntity != null && transactionIdModel != null)
            mPresenter.validateOneApolloPoints(customerEntity.getMobileNo(), transactionIdModel.getTransactionID());
    }

    @Override
    public void onClickEditItemsList() {
        addItemBinding.setIsPaymentMode(false);
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
        showMessage("Success SaveRetailTransaction");
        if(paymentMethodModel.isCashMode()) {
            paymentDoneAmount += Double.parseDouble(getCashPaymentAmount());
            PayAdapterModel payAdapterModel = new PayAdapterModel("CASH PAID",getCashPaymentAmount());
            arrPayAdapterModel.add(payAdapterModel);
        }else if(paymentMethodModel.isCardMode()){
            paymentDoneAmount += Double.parseDouble(getCardPaymentAmount());
            PayAdapterModel payAdapterModel = new PayAdapterModel("CARD PAID",getCardPaymentAmount());
            arrPayAdapterModel.add(payAdapterModel);
        }else if(paymentMethodModel.isOneApolloMode()){
            paymentDoneAmount += Double.parseDouble(getOneApolloPoints());
            PayAdapterModel payAdapterModel = new PayAdapterModel("ONE APOLLO POINTS",getOneApolloPoints());
            arrPayAdapterModel.add(payAdapterModel);
        }
        payActivityAdapter.notifyDataSetChanged();

       OrderPriceInfoModel priceInfoModel =  addItemBinding.getOrderInfo();
        if(priceInfoModel.getOrderTotalAmount() != paymentDoneAmount){
            paymentMethodModel.setBalanceAmount(priceInfoModel.getOrderTotalAmount() - paymentDoneAmount);
            paymentMethodModel.setBalanceAmount(true);
        }else{
            if(!TextUtils.isEmpty(body.getReciptId())) {
                paymentMethodModel.setGenerateBill(true);
                paymentMethodModel.setSaveRetailsTransactionRes(body);
            }
        }
    }

    @Override
    public void onFailedSaveRetailsTransaction(SaveRetailsTransactionRes body) {
        showMessage("Failed SaveRetailTransaction");
    }

    @Override
    public void onSuccessCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes) {
//        txtSaving.Text = Convert.ToDecimal(Math.Round((POSSalesTransaction.DiscAmount / POSSalesTransaction.TotalMRP) * 100, 2)).ToString("#0.00");
//        txtMRP.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.TotalMRP, 2)).ToString("#0.00");
//        txtTaxableAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.NetAmount, 2)).ToString("#0.00");
//        txtNetTotal.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.GrossAmount - POSSalesTransaction.DiscAmount, 2)).ToString("#0.00");
//        txtTaxAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.TotalTaxAmount, 2)).ToString("#0.00");
//        txtDiscAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.DiscAmount, 2)).ToString("#0.00");
//        txtPharmaAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.SalesLine.Where(ObjLine => ObjLine.CategoryCode == "P" && ObjLine.IsVoid == false).Sum(ObjLine => ObjLine.NetAmountInclTax), 2)).ToString("#0.00");
//        txtPLAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.SalesLine.Where(ObjLine => ObjLine.CategoryCode == "A" && ObjLine.IsVoid == false).Sum(ObjLine => ObjLine.NetAmountInclTax), 2)).ToString("#0.00");
//        txtFMCGAmt.Text = Convert.ToDecimal(Math.Round(POSSalesTransaction.SalesLine.Where(ObjLine => ObjLine.CategoryCode == "F" && ObjLine.IsVoid == false).Sum(ObjLine => ObjLine.NetAmountInclTax), 2)).ToString("#0.00");

        orderPriceInfoModel.setOrderSavingsAmount(posTransactionRes.getDiscAmount() / posTransactionRes.getTotalMRP() * 100);
        orderPriceInfoModel.setMrpTotalAmount(posTransactionRes.getTotalMRP());
        orderPriceInfoModel.setTaxableTotalAmount(posTransactionRes.getNetAmount());
        orderPriceInfoModel.setOrderTotalAmount(posTransactionRes.getGrossAmount() - posTransactionRes.getDiscAmount());
        orderPriceInfoModel.setDiscTotalAmount(posTransactionRes.getDiscAmount());
        orderPriceInfoModel.setRoundedAmount(posTransactionRes.getRoundedAmount());
        orderPriceInfoModel.setOrderSavingsPercentage(posTransactionRes.getTotalManualDiscountPercentage());
        if(posTransactionRes.getSalesLine().size() > 0){
            for(int i=0; i< posTransactionRes.getSalesLine().size(); i++){
                if ( posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("P"))
                    orderPriceInfoModel.setPharmaTotalAmount(orderPriceInfoModel.getPharmaTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
                else if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("F"))
                    orderPriceInfoModel.setFmcgTotalAmount(orderPriceInfoModel.getFmcgTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());
                else if (posTransactionRes.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("A"))
                    orderPriceInfoModel.setPlTotalAmount(orderPriceInfoModel.getPlTotalAmount() + posTransactionRes.getSalesLine().get(i).getNetAmountInclTax());

                medicineDetailsModelsList.get(i).getBatchListObj().setCalculatedTotalPrice(new DecimalFormat("##.##").format(posTransactionRes.getSalesLine().get(i).getNetAmountInclTax()));
                if(!TextUtils.isEmpty(posTransactionRes.getSalesLine().get(i).getPreviewText())) {
                    medicineDetailsModelsList.get(i).getBatchListObj().setPreviewText(posTransactionRes.getSalesLine().get(i).getPreviewText());
                }
            }
        }

        addItemBinding.setOrderInfo(orderPriceInfoModel);
    }

    @Override
    public void onFailedCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes) {

    }

    @Override
    public void onSuccessOneApolloSendOtp(ValidatePointsResModel.OneApolloProcessResultEntity resultEntity) {
        paymentMethodModel.setOTPView(true);
        addItemBinding.setValidatePoints(resultEntity);
    }

    @Override
    public void onSuccessOneApolloOtp(ValidatePointsResModel.OneApolloProcessResultEntity entity) {
        addItemBinding.setValidatePoints(entity);
        paymentMethodModel.setOTPView(false);

    }

    @Override
    public void onItemDeleted() {
        mPresenter.calculatePosTransaction();
    }

    @Override
    public void onItemAdded() {
        mPresenter.calculatePosTransaction();
    }

    @Override
    public void onClickGenerateBill() {
        startActivity(OrderSummaryActivity.getStartIntent(this,paymentMethodModel.getSaveRetailsTransactionRes()));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onSuccessValidateOneApolloPoints(ValidatePointsResModel body) {
        addItemBinding.setValidatePoints(body.getOneApolloProcessResult());
    }

    @Override
    public void onFailedValidateOneApolloPoints(ValidatePointsResModel body) {
        showMessage("No Points are available to this number");
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
                            GetItemDetailsRes.Items items = (GetItemDetailsRes.Items) data.getSerializableExtra("selected_item");
                            medicineDetailsModelsList.add(items);
                            medicinesDetailAdapter.notifyDataSetChanged();
                            addItemBinding.setProductCount(medicineDetailsModelsList.size());
                            // Insert into cart table
                            if (items != null) {
                                Cart savedCart = RealmController.with(this).getCartTransaction(transactionIdModel.getTransactionID());
                                CartItems cartItems = new CartItems();
                                cartItems.setArtCode(items.getArtCode());
                                cartItems.setCategory(items.getCategory());
                                cartItems.setCategoryCode(items.getCategoryCode());
                                cartItems.setDescription(items.getDescription());
                                cartItems.setDiseaseType(items.getDiseaseType());
                                cartItems.setDPCO(items.getDPCO());
                                cartItems.setGenericName(items.getGenericName());
                                cartItems.setHsncode_In(items.getHsncode_In());
                                cartItems.setManufacture(items.getManufacture());
                                cartItems.setManufactureCode(items.getManufactureCode());
                                cartItems.setProductRecID(items.getProductRecID());
                                cartItems.setRackId(items.getRackId());
                                cartItems.setRetailCategoryRecID(items.getRetailCategoryRecID());
                                cartItems.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
                                cartItems.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
                                cartItems.setSch_Catg(items.getSch_Catg());
                                cartItems.setSch_Catg_Code(items.getSch_Catg_Code());
                                cartItems.setSI_NO(items.getSI_NO());
                                cartItems.setSubCategory(items.getSubCategory());
                                cartItems.setSubClassification(items.getSubClassification());
                                cartItems.setTotalTax(items.getBatchListObj().getTotalTax());
                                cartItems.setSNO(items.getBatchListObj().getSNO());
                                cartItems.setSGSTTaxCode(items.getBatchListObj().getSGSTTaxCode());
                                cartItems.setSGSTPerc(items.getBatchListObj().getSGSTPerc());
                                cartItems.setREQQTY(items.getBatchListObj().getREQQTY());
                                cartItems.setQ_O_H(items.getBatchListObj().getQ_O_H());
                                cartItems.setPrice(items.getBatchListObj().getPrice());
                                cartItems.setNearByExpiry(items.getBatchListObj().getNearByExpiry());
                                cartItems.setMRP(items.getBatchListObj().getMRP());
                                cartItems.setItemID(items.getBatchListObj().getItemID());
                                cartItems.setISMRPChange(items.getBatchListObj().getISMRPChange());
                                cartItems.setIGSTTaxCode(items.getBatchListObj().getIGSTTaxCode());
                                cartItems.setIGSTPerc(items.getBatchListObj().getIGSTPerc());
                                cartItems.setExpDate(items.getBatchListObj().getExpDate());
                                cartItems.setCGSTTaxCode(items.getBatchListObj().getCGSTTaxCode());
                                cartItems.setCGSTPerc(items.getBatchListObj().getCGSTPerc());
                                cartItems.setCESSPerc(items.getBatchListObj().getCESSPerc());
                                cartItems.setCESSTaxCode(items.getBatchListObj().getCESSTaxCode());
                                cartItems.setBatchNo(items.getBatchListObj().getBatchNo());
                                if (savedCart != null) {
                                    Realm realm = RealmController.with(this).getRealm();
                                    realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                                        @Override
                                        public void execute(Realm realm) {
                                            // increment index
                                            Number currentIdNum = realm.where(CartItems.class).max("id");
                                            int nextId;
                                            if (currentIdNum == null) {
                                                nextId = 1;
                                            } else {
                                                nextId = currentIdNum.intValue() + 1;
                                            }
                                            cartItems.setId(nextId);
                                            //  realm.beginTransaction();
                                            savedCart.getItemsArrayList().add(cartItems);
                                        //    realm.copyToRealmOrUpdate(savedCart);
                                            // realm.commitTransaction();
                                        }
                                    });

                                } else {
                                    RealmList<CartItems> itemsRealmList = new RealmList<>();
                                    itemsRealmList.add(cartItems);
                                    Cart cart = new Cart();
                                    cart.setTransactionId(transactionIdModel.getTransactionID());
                                    cart.setItemsArrayList(itemsRealmList);
                                    Realm realm = RealmController.with(this).getRealm();
                                    realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                                        @Override
                                        public void execute(Realm realm) {
                                            // increment index
                                            Number currentIdNum = realm.where(Cart.class).max("id");
                                            int nextId;
                                            if (currentIdNum == null) {
                                                nextId = 1;
                                            } else {
                                                nextId = currentIdNum.intValue() + 1;
                                            }
                                            cart.setId(nextId);
                                            //  realm.beginTransaction();

                                         //   realm.copyToRealmOrUpdate(cart);
                                            // realm.commitTransaction();
                                        }
                                    });
                                    //realm.beginTransaction();
                                    //realm.copyToRealm(cart);
                                    //realm.commitTransaction();
                                }

                            }

                            mPresenter.calculatePosTransaction();
                        }
                        break;
                    case CUSTOMER_SEARCH_ACTIVITY_CODE:
                        customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
                        if (customerEntity != null) {
                            addItemBinding.setCustomer(customerEntity);
                        }
                        break;
                    case DOCTOR_SEARCH_ACTIVITY_CODE:
                        doctorEntity = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                        if (doctorEntity != null) {
                            addItemBinding.setDoctor(doctorEntity);
                        }
                        break;
                    case CORPORATE_SEARCH_ACTIVITY_CODE:
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
                            strTxnId = response.getString("txnId");
                            emiID = response.getString("emiId");
                            if (strTxnId.equals("") || strTxnId.equals(null)) {
                                Toast.makeText(this, data.getStringExtra("response"), Toast.LENGTH_LONG).show();
                            } else {
                                mPresenter.onSuccessCardPayment(data.getStringExtra("response"));
                            }
//
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



    private void alertDialog(){
        ExitInfoDialog  dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Are you Sure?");
        dialogView.setPositiveLabel("Yes");
        dialogView.setSubtitle("Do you want to cancel this order");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                closeOrder();

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

    private void closeOrder(){
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
