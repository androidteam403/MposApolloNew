package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityEPrescriptionMedicineDetailsBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.StockNotVailableDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.adapter.EPrescriptionMedicineDetailsAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class EPrescriptionMedicineDetailsActivity extends BaseActivity implements EPrescriptionMedicineDetailsMvpView, AdapterView.OnItemSelectedListener, View.OnClickListener {

    @Inject
    EPrescriptionMedicineDetailsMvpPresenter<EPrescriptionMedicineDetailsMvpView> mPresenter;
    ActivityEPrescriptionMedicineDetailsBinding detailsBinding;
    private OMSTransactionHeaderResModel.OMSHeaderObj orderInfoItem = new OMSTransactionHeaderResModel.OMSHeaderObj();
    EPrescriptionMedicineDetailsAdapter ePrescriptionMedicineDetailsAdapter;
    private EPrescriptionMedicineDetailsMvpView mvpView;
    List<EPrescriptionModelClassResponse> prescriptionLineList;
    private String eprescription_corpcode = "0";
    GetCustomerResponse.CustomerEntity customerEntity = new GetCustomerResponse.CustomerEntity();
    private final int ACTIVITY_EPRESCRIPTIONBILLING_DETAILS_CODE = 122;
    int position;
    boolean isExapanded;
    private CustomerDataResBean customerDataResBean;
    String loinStoreLocation, terminalId;
    private TransactionIDResModel transactionIDResModel;
    private CorporateModel corporateModel;
    String sub1;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList = new ArrayList<>();


    public static Intent getStartActivity(Context context, List<EPrescriptionModelClassResponse> prescriptionLine, int position, String loinStoreLocation, String terminalId) {
        Intent i = new Intent(context, EPrescriptionMedicineDetailsActivity.class);
        i.putExtra("prescriptionLine", (Serializable) prescriptionLine);
        i.putExtra("position", (int) position);
        i.putExtra("loinStoreLocation", loinStoreLocation);
        i.putExtra("terminalId", terminalId);
        return i;
    }


//    public static Intent getStartActivity(Context context) {
//        Intent intent = new Intent(context, EPrescriptionMedicineDetailsActivity.class);
////        intent.putExtra(CommonUtils.SELECTED_ORDERS_LIST, (Serializable) selectedOmsHeaderList);
//        return intent;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_e_prescription_medicine_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setUp();
//        orderid, ordertype, orderdate, orderamount, shippingmethod,paymentsource vendorid, name, address, doc, name, comments


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setUp() {
//        detailsBinding.setCallback((EPrescriptionMedicineDetailsActivity) mPresenter);

        if (getIntent() != null) {
            prescriptionLineList = (List<EPrescriptionModelClassResponse>) getIntent().getSerializableExtra("prescriptionLine");
            loinStoreLocation = (String) getIntent().getStringExtra("loinStoreLocation");
            terminalId = (String) getIntent().getStringExtra("terminalId");

            position = getIntent().getExtras().getInt("position");
        }
        detailsBinding.siteName.setText(loinStoreLocation);
        detailsBinding.siteId.setText(prescriptionLineList.get(0).getShopId());
        detailsBinding.terminalId.setText(terminalId);


        String todaysDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        detailsBinding.prescriptionNo.setText("#" + prescriptionLineList.get(position).getPrescriptionNo());


        mPresenter.fetchLineTransactionList(prescriptionLineList.get(position).getPrescriptionNo());
        mPresenter.getTransactionID();


        detailsBinding.name.setText(prescriptionLineList.get(position).getPatientName());
        detailsBinding.mobilenumber.setText(prescriptionLineList.get(position).getPhoneNo());


        detailsBinding.doctorName.setText(prescriptionLineList.get(position).getDoctorName());
        detailsBinding.vendorId.setText("-");


        if (prescriptionLineList.get(position).getOrderType() != null && !prescriptionLineList.get(position).getOrderType().equals("0") && !prescriptionLineList.get(position).getOrderType().equals("")) {
            detailsBinding.orderType.setText(prescriptionLineList.get(position).getOrderType());
        } else {
            detailsBinding.orderType.setText("-");
        }

        if (prescriptionLineList.get(position).getDownDate() != null && !prescriptionLineList.get(position).getDownDate().equals("0") && !prescriptionLineList.get(position).getDownDate().equals("")) {
            detailsBinding.orderDate.setText(prescriptionLineList.get(position).getDownDate());
        } else {
            detailsBinding.orderDate.setText("-");
        }

        if (prescriptionLineList.get(position).getOrderbillvalue() != null && !prescriptionLineList.get(position).getOrderbillvalue().equals(0.0) && !prescriptionLineList.get(position).getOrderbillvalue().equals("")) {
            detailsBinding.orderAmount.setText(String.valueOf(prescriptionLineList.get(position).getOrderbillvalue()));
        } else {
            detailsBinding.orderAmount.setText("-");
        }

        if (prescriptionLineList.get(position).getShippingmethod() != null && !prescriptionLineList.get(position).getShippingmethod().equals("0") && !prescriptionLineList.get(position).getShippingmethod().equals("")) {
            detailsBinding.shippingMethodType.setText(prescriptionLineList.get(position).getShippingmethod());
        } else {
            detailsBinding.shippingMethodType.setText("-");
        }

        if (prescriptionLineList.get(position).getShippingmethod() != null && !prescriptionLineList.get(position).getShippingmethod().equals("0") && !prescriptionLineList.get(position).getShippingmethod().equals("")) {
            detailsBinding.paymentSource.setText(prescriptionLineList.get(position).getShippingmethod());
        } else {
            detailsBinding.paymentSource.setText("-");
        }

        detailsBinding.customerType.setText("-");


        if (prescriptionLineList.get(position).getCity() != null && !prescriptionLineList.get(position).getCity().equals("0") && !prescriptionLineList.get(position).getCity().equals("")) {
            detailsBinding.city.setText(prescriptionLineList.get(position).getCity());
        } else {
            detailsBinding.city.setText("-");
        }

        if (prescriptionLineList.get(position).getStateCode() != null && !prescriptionLineList.get(position).getStateCode().equals("0") && !prescriptionLineList.get(position).getStateCode().equals("")) {
            detailsBinding.statecode.setText(prescriptionLineList.get(position).getStateCode());
        } else {
            detailsBinding.statecode.setText("-");
        }

        if (prescriptionLineList.get(position).getPinCode() != null && !prescriptionLineList.get(position).getPinCode().equals("0") && !prescriptionLineList.get(position).getPinCode().equals("")) {
            detailsBinding.pincode.setText(prescriptionLineList.get(position).getPinCode());
        } else {
            detailsBinding.pincode.setText("-");
        }

        if (prescriptionLineList.get(position).getComments() != null && prescriptionLineList.get(position).getComments().equals(0) && !prescriptionLineList.get(position).getComments().equals("")) {
            detailsBinding.comments.setText(prescriptionLineList.get(position).getComments());
        } else {
            detailsBinding.comments.setText("-");
        }

        if (prescriptionLineList.get(position).getAddr() != null && !prescriptionLineList.get(position).getAddr().equals("0") && !prescriptionLineList.get(position).getAddr().equals("")) {
            detailsBinding.address.setText(prescriptionLineList.get(position).getAddr());
        } else {
            detailsBinding.address.setText("-");
        }


//        detailsBinding.customerType.setText(prescriptionLineList.get(position).);


//        detailsBinding.backArrowtop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(EPrescriptionMedicineDetailsActivity.this, EPrescriptionActivity.class);
//                startActivity(i);
//            }
//        });

        detailsBinding.backarrowtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        detailsBinding.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detailsBinding.rackChild2Layout.setVisibility(View.VISIBLE);
                detailsBinding.backArrow.setVisibility(View.VISIBLE);
                detailsBinding.rightArrow.setVisibility(View.GONE);


            }
        });

        detailsBinding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detailsBinding.rackChild2Layout.setVisibility(View.GONE);
                detailsBinding.backArrow.setVisibility(View.GONE);
                detailsBinding.rightArrow.setVisibility(View.VISIBLE);


            }
        });

        detailsBinding.continueBillingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sub = "";
                for (EPrescriptionMedicineResponse ePrescriptionMedicineResponse : medicineResponseList) {
                    if (ePrescriptionMedicineResponse.getSubstitute() != null && ePrescriptionMedicineResponse.getSubstitute().getSubstituteArtCode() != null) {
                        if (ePrescriptionMedicineResponse.getSubstitute().getSubstituteArtCode().equalsIgnoreCase("Select")) {
                            sub = sub + ePrescriptionMedicineResponse.getArtCode();
                        }
                    }
                }
                if (sub.isEmpty()) {
                    //             CheckBatchModelRequest customerDataResBean= new CheckBatchModelRequest();
                    customerDataResBean = new CustomerDataResBean();
                    customerDataResBean.setRemainingamount(0);
                    customerDataResBean.setHDOrder(false);
                    customerDataResBean.setTPASeller(false);
                    customerDataResBean.setDonationAmount(0);
                    customerDataResBean.setBulkDiscount(false);
                    customerDataResBean.setReturnRequestId("");
                    customerDataResBean.setOMSJurnalsScreen(false);
                    customerDataResBean.setiSOMSReturn(false);
                    customerDataResBean.setRiderMobile("");
                    customerDataResBean.setRiderCode("");
                    customerDataResBean.setDspName("");
                    customerDataResBean.setRevReturnOtp("");
                    customerDataResBean.setPickupOtp("");
                    customerDataResBean.setFwdReturnOtp("");
                    customerDataResBean.setrTOStatus(false);
                    customerDataResBean.setPickupStatus(false);
                    customerDataResBean.setTier("");
                    customerDataResBean.setCustomerType("");
                    customerDataResBean.setStockStatus("");
                    customerDataResBean.setUHIDBilling(false);
                    customerDataResBean.setHCOfferCode("");
                    customerDataResBean.setDiscountStatus(0);
                    customerDataResBean.setDiscountReferenceID("");
                    customerDataResBean.setISOnlineOrder(true);
                    customerDataResBean.setISCancelled(false);
                    customerDataResBean.setISReserved(false);
                    customerDataResBean.setVendorCode("");
                    customerDataResBean.setISBulkBilling(false);
                    customerDataResBean.setDeliveryDate("");
                    customerDataResBean.setOrderType(prescriptionLineList.get(position).getOrderType());
                    customerDataResBean.setShippingMethod(prescriptionLineList.get(position).getShippingmethod());
                    customerDataResBean.setShippingMethodDesc("");
                    customerDataResBean.setBillingCity("");
                    customerDataResBean.setVendorId(prescriptionLineList.get(position).getCorpCode());
                    customerDataResBean.setPaymentSource("");
                    customerDataResBean.setISPrescibeDiscount(false);
                    customerDataResBean.setCancelReasonCode("");
                    customerDataResBean.setStoreName("");
                    customerDataResBean.setRegionCode("");
                    customerDataResBean.setCustomerID("");
                    customerDataResBean.setCorpCode(prescriptionLineList.get(position).getCorpCode());
                    customerDataResBean.setMobileNO(prescriptionLineList.get(position).getPhoneNo());
                    customerDataResBean.setDOB("");
                    customerDataResBean.setCustomerName(prescriptionLineList.get(position).getPatientName());
                    customerDataResBean.setCustAddress(prescriptionLineList.get(position).getAddr());
                    customerDataResBean.setCustomerState("");
                    customerDataResBean.setGender(0);
                    customerDataResBean.setPincode(prescriptionLineList.get(position).getPinCode());
                    customerDataResBean.setDoctorName(prescriptionLineList.get(position).getDoctorName());
                    customerDataResBean.setDoctorCode(prescriptionLineList.get(position).getDoctorConCode());
                    customerDataResBean.setSalesOrigin("");
                    customerDataResBean.setTrackingRef(prescriptionLineList.get(position).getPrescriptionNo());
                    customerDataResBean.setREFNO(prescriptionLineList.get(position).getPrescriptionNo());
                    customerDataResBean.setIPNO("");
                    customerDataResBean.setIPSerialNO("");
                    customerDataResBean.setReciptId("");
                    customerDataResBean.setBatchTerminalid("");
                    customerDataResBean.setBusinessDate(todaysDate);
                    customerDataResBean.setChannel("");
                    customerDataResBean.setComment("");
                    customerDataResBean.setCreatedonPosTerminal("");
                    customerDataResBean.setCurrency("");
                    customerDataResBean.setCustAccount("");
                    customerDataResBean.setCustDiscamount(0);
                    customerDataResBean.setDiscAmount(0);
                    customerDataResBean.setEntryStatus(0);
                    customerDataResBean.setGrossAmount(0);
                    customerDataResBean.setNetAmount(0);
                    customerDataResBean.setNetAmountInclTax(0);
                    customerDataResBean.setNumberofItemLines(0);
                    customerDataResBean.setNumberofItems(0);
                    customerDataResBean.setRoundedAmount(0);
                    customerDataResBean.setStaff(mPresenter.getLoginUserName());
                    customerDataResBean.setStore(prescriptionLineList.get(0).getShopId());
                    customerDataResBean.setState("");
                    customerDataResBean.setTerminal(terminalId);
                    customerDataResBean.setReturnStore("");
                    customerDataResBean.setReturnTerminal("");
                    customerDataResBean.setReturnTransactionId("");
                    customerDataResBean.setReturnReceiptId("");
                    customerDataResBean.setTimewhenTransClosed(0);
                    customerDataResBean.setTotalDiscAmount(0);
                    customerDataResBean.setTotalManualDiscountAmount(0);
                    customerDataResBean.setTotalManualDiscountPercentage(0);
                    customerDataResBean.setTotalMRP(0);
                    customerDataResBean.setTotalTaxAmount(0);
                    customerDataResBean.setTransactionId("");
                    customerDataResBean.setTransDate("");
                    customerDataResBean.setType(0);
                    customerDataResBean.setDataAreaId("AHEL");
                    customerDataResBean.setVoid(false);
                    customerDataResBean.setReturn(false);
                    customerDataResBean.setISBatchModifiedAllowed(false);
                    customerDataResBean.setISReturnAllowed(false);
                    customerDataResBean.setManualBill(false);
                    customerDataResBean.setReturnType(0);
                    customerDataResBean.setCurrentSalesLine(0);
                    customerDataResBean.setRequestStatus(0);
                    customerDataResBean.setRequestStatus(0);
                    customerDataResBean.setReturnMessage("");
                    customerDataResBean.setPosEvent(0);
                    customerDataResBean.setTransType(0);
                    customerDataResBean.setStockCheck(true);
                    customerDataResBean.setISPosted(false);
                    customerDataResBean.setSEZ(0);
                    customerDataResBean.setISAdvancePayment(false);
                    customerDataResBean.setAmounttoAccount(0);
                    customerDataResBean.setReminderDays(0);
                    customerDataResBean.setISOMSOrder(false);
                    customerDataResBean.setISHBPStore(false);
                    customerDataResBean.setPatientID("");
                    customerDataResBean.setApprovedID("");
                    customerDataResBean.setDiscountRef("");
                    customerDataResBean.setAWBNo("");
                    customerDataResBean.setDSPCode("");
                    customerDataResBean.setISHyperLocalDelivery(false);
                    customerDataResBean.setISHyperDelivered(false);
                    customerDataResBean.setCreatedDateTime(todaysDate);
                    customerDataResBean.setISOMSValidate(false);
                    customerDataResBean.setAllowedTenderType("");
                    customerDataResBean.setShippingCharges(0);
                    customerDataResBean.setAgeGroup("");
                    customerDataResBean.setExpiryDays(30);


                    ArrayList<SalesLineEntity> salesLineEntityList = new ArrayList<>();
                    for (EPrescriptionMedicineResponse ePrescriptionMedicineResponse : medicineResponseList) {
                        SalesLineEntity salesLineEntity = new SalesLineEntity();
                        salesLineEntity.setVariantId("");
                        salesLineEntity.setPriceVariation(false);
                        salesLineEntity.setqCPass(false);
                        salesLineEntity.setqCFail(false);
                        salesLineEntity.setqCStatus(0);
                        salesLineEntity.setqCDate("");
                        salesLineEntity.setqCRemarks("");
                        salesLineEntity.setAlternetItemID("");
                        salesLineEntity.setLineNo(1);
                        salesLineEntity.setItemId(ePrescriptionMedicineResponse.getArtCode());
                        salesLineEntity.setItemName("");
                        salesLineEntity.setCategory("");
                        salesLineEntity.setCategoryCode("");
                        salesLineEntity.setSubCategory("");
                        salesLineEntity.setSubCategoryCode("");
                        salesLineEntity.setScheduleCategory("");
                        salesLineEntity.setScheduleCategoryCode("");
                        salesLineEntity.setManufacturerCode("");
                        salesLineEntity.setManufacturerName("");
//                    salesLineEntity.setExpiry();

                        if (ePrescriptionMedicineResponse.getReqQty() != 0) {
                            salesLineEntity.setQty(Double.parseDouble(String.valueOf(ePrescriptionMedicineResponse.getReqQty())));
                        }

                        salesLineEntity.setStockQty(0);
                        salesLineEntity.setReturnQty(0);
                        salesLineEntity.setRemainingQty(0);
                        salesLineEntity.setMRP(10);
                        salesLineEntity.setTax(0);
                        salesLineEntity.setAdditionaltax(0);
                        salesLineEntity.setBarcode("");
                        salesLineEntity.setComment("");
                        salesLineEntity.setDiscAmount(0);
                        salesLineEntity.setDiscOfferId("");
                        salesLineEntity.setHsncode_In("");
                        salesLineEntity.setInventBatchId("");
                        salesLineEntity.setPreviewText("");
                        salesLineEntity.setLinedscAmount(0);
                        salesLineEntity.setLineManualDiscountAmount(0);
                        salesLineEntity.setLineManualDiscountPercentage(0);
                        salesLineEntity.setNetAmount(0);
                        salesLineEntity.setNetAmountInclTax(0);
                        salesLineEntity.setOriginalPrice(0);
                        salesLineEntity.setPeriodicDiscAmount(0);
                        salesLineEntity.setPrice(10);
                        salesLineEntity.setTaxAmount(0);
                        salesLineEntity.setBaseAmount(0);
                        salesLineEntity.setTotalDiscAmount(0);
                        salesLineEntity.setTotalDiscPct(0);
                        salesLineEntity.setTotalRoundedAmount(0);
                        salesLineEntity.setUnit("");
                        salesLineEntity.setUnitPrice(0);
                        salesLineEntity.setUnitQty(0);
                        salesLineEntity.setVariantId("");
                        salesLineEntity.setTotal(0);
                        salesLineEntity.setISPrescribed(0);
                        salesLineEntity.setRemainderDays(0);
                        salesLineEntity.setVoid(false);
                        salesLineEntity.setPriceOverride(false);
                        salesLineEntity.setChecked(false);
                        salesLineEntity.setRetailCategoryRecID("");
                        salesLineEntity.setRetailSubCategoryRecID("");
                        salesLineEntity.setRetailMainCategoryRecID("");
                        salesLineEntity.setDPCO(false);
                        salesLineEntity.setProductRecID("");
                        salesLineEntity.setModifyBatchId("");
                        salesLineEntity.setDiseaseType("");
                        salesLineEntity.setSubClassification("");
                        salesLineEntity.setOfferQty(0);
                        salesLineEntity.setOfferAmount(0);
                        salesLineEntity.setOfferDiscountType(0);
                        salesLineEntity.setOfferDiscountValue(0);
                        salesLineEntity.setDiscountType("");
                        salesLineEntity.setMixMode(false);
                        salesLineEntity.setMMGroupId("");
                        salesLineEntity.setDiscId("");
                        salesLineEntity.setOfferType(0);
                        salesLineEntity.setLineDiscPercentage(0);
                        salesLineEntity.setApplyDiscount(false);
                        salesLineEntity.setIGSTPerc(0);
                        salesLineEntity.setCESSPerc(0);
                        salesLineEntity.setCGSTPerc(0);
                        salesLineEntity.setSGSTPerc(0);
                        salesLineEntity.setIGSTTaxCode(null);
                        salesLineEntity.setCESSTaxCode(null);
                        salesLineEntity.setCGSTTaxCode(null);
                        salesLineEntity.setSGSTTaxCode(null);
                        salesLineEntity.setDiscountStructureType(0);
                        if (ePrescriptionMedicineResponse.getSubstitute() != null && ePrescriptionMedicineResponse.getSubstitute().getSubstituteArtCode() != null && !ePrescriptionMedicineResponse.getSubstitute().getSubstituteArtCode().isEmpty()) {
                            salesLineEntity.setSubstitudeItemId(ePrescriptionMedicineResponse.getSubstitute().getSubstituteArtCode());
                        } else {
                            salesLineEntity.setSubstitudeItemId("");
                        }

                        salesLineEntity.setCategoryReference("");
                        salesLineEntity.setOrderStatus(0);
                        salesLineEntity.setOmsLineID(0);
                        salesLineEntity.setSubsitute(false);
                        salesLineEntity.setGeneric(false);
                        salesLineEntity.setOmsLineRECID(0);
                        salesLineEntity.setISReserved(false);
                        salesLineEntity.setISStockAvailable(false);
                        salesLineEntity.setPhysicalBatchID(null);
                        salesLineEntity.setPhysicalMRP(0);
                        salesLineEntity.setPhysicalExpiry(null);
                        salesLineEntity.setCancelReasonCode("");

                        salesLineEntityList.add(salesLineEntity);
                    }
                    customerDataResBean.setSalesLine(salesLineEntityList);
                    customerDataResBean.setTenderLine(null);
                    ArrayList<CustomerDataResBean.OrderPrescriptionObj> orderPrescriptionURL = new ArrayList<>();
                    CustomerDataResBean.OrderPrescriptionObj orderPrescriptionObj = new CustomerDataResBean.OrderPrescriptionObj();
                    orderPrescriptionObj.setPERSCRIPTIONURL(prescriptionLineList.get(position).getPrescriptionURL());
                    orderPrescriptionObj.setPRESCRIPTIONNO(prescriptionLineList.get(position).getPrescriptionNo());
                    orderPrescriptionURL.add(orderPrescriptionObj);
                    customerDataResBean.setOrderPrescriptionURL(orderPrescriptionURL);

                    mPresenter.omscheckbatchstocks(customerDataResBean);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select substitute ids for " + sub, Toast.LENGTH_SHORT).show();

                }


            }

        });


    }

    List<EPrescriptionMedicineResponse> medicineResponseList;

    @Override
    public void onSuccessTransactionList(List<EPrescriptionMedicineResponse> medicineResponseList) {
        this.medicineResponseList = medicineResponseList;
//        Toast.makeText(getApplicationContext(), " " + body.size(), Toast.LENGTH_SHORT).show();
        mPresenter.fetchSubstituteList(prescriptionLineList.get(position).getPrescriptionNo());


    }

    ArrayList<String> items;

    @Override
    public void onClickDropDown(ArrayList<String> items) {
//        this.items=items;
//
//        if (items.size() > 0 && items != null) {
//
//
//            Toast.makeText(getApplicationContext(), "Substitute List size: " + items.size(), Toast.LENGTH_SHORT).show();


//       ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, substituteList.getSubstituteList());
//        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(ad);
//            Spinner spinner = findViewById(R.id.coursesspinner);
//
//
//            ArrayAdapter genderSpinnerPojo = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items) {
//                @NonNull
//                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//                    View v = super.getView(position, convertView, parent);
////                    Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
////                    ((TextView) v).setTypeface(externalFont);
//                    return v;
//                }
//
//                public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
//                    View v = super.getDropDownView(position, convertView, parent);
////                    Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf");
////                    ((TextView) v).setTypeface(externalFont);
//                    return v;
//                }
//            };
//            genderSpinnerPojo.setDropDownViewResource(android.R.layout.simple_spinner_item);
//            spinner.setAdapter(genderSpinnerPojo);
//
//
////        ArrayAdapter adapter = new ArrayAdapter(this,
////                android.R.layout.simple_spinner_dropdown_item, substituteList.getSubstituteList()) ;
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spinner.setAdapter(adapter);
////        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
//        }
//


    }

    EPrescriptionSubstituteModelResponse.Substitute selectedItem;
    EPrescriptionSubstituteModelResponse.Substitute substitute1;
    List<EPrescriptionSubstituteModelResponse.Substitute> substituteLists;

    @Override
    public void onSubstituteSelectedItem(EPrescriptionSubstituteModelResponse.Substitute substitute, int position, EPrescriptionSubstituteModelResponse.Substitute selectedItem, List<EPrescriptionSubstituteModelResponse.Substitute> substituteLists) {
        this.selectedItem = selectedItem;
        this.substitute1 = substitute1;
        this.substituteLists = substituteLists;
        if (medicineResponseList != null && medicineResponseList.size() > 0) {
            if (substitute != null) {
                medicineResponseList.get(position).setSubstitute(substitute);
                medicineResponseList.get(position).getSubstitute().setSubstituteArtCode(substitute.getSubstituteArtCode());
//            selectedItem.setSubstituteArtCode(substitute.getSubstituteArtCode());
            }
        }
    }

    EPrescriptionSubstituteModelResponse substituteList;

    @Override
    public void onSuccessSubstituteList(EPrescriptionSubstituteModelResponse substituteLists) {
        this.substituteList = substituteLists;
        if (medicineResponseList != null && medicineResponseList.size() > 0) {
            ePrescriptionMedicineDetailsAdapter = new EPrescriptionMedicineDetailsAdapter(this, this, medicineResponseList, substituteList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EPrescriptionMedicineDetailsActivity.this);
            detailsBinding.productListRecycler.setLayoutManager(mLayoutManager);
            detailsBinding.productListRecycler.setAdapter(ePrescriptionMedicineDetailsAdapter);

        } else {
            Toast.makeText(getApplicationContext(), "No order found", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onFailureSubstituteList(EPrescriptionSubstituteModelResponse substituteList) {
        if (medicineResponseList != null && medicineResponseList.size() > 0) {
            ePrescriptionMedicineDetailsAdapter = new EPrescriptionMedicineDetailsAdapter(this, this, medicineResponseList, substituteList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EPrescriptionMedicineDetailsActivity.this);
            detailsBinding.productListRecycler.setLayoutManager(mLayoutManager);
            detailsBinding.productListRecycler.setAdapter(ePrescriptionMedicineDetailsAdapter);

        } else {
            Toast.makeText(getApplicationContext(), "No order found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showTransactionID(TransactionIDResModel model) {
        this.transactionIDResModel = model;
//        Toast.makeText(getApplicationContext(), "TransactionId Success!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        this.corporateModel = corporateModel;
        corporateList.addAll(corporateModel.get_DropdownValue());
//        Toast.makeText(getApplicationContext(), "CorporateList Success!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CheckBatchStockSuccess(CustomerDataResBean customerDataResBean) {
        if (customerDataResBean != null) {
//            customerDataResBean = customerDataResBean;
//            if (orderInfoItem.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
            mPresenter.onOnlineBillApiCall(customerDataResBean);
//            }
//        else {
//                CheckBatchStockFailure(customerDataResBean);
//
//            }


        }
//        Toast.makeText(getApplicationContext(), "CheckBatchStock Success!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CheckBatchStockFailure(CustomerDataResBean body) {
        this.customerDataResBean = body;
        String message = body.getReturnMessage();
        message = message + "Stock Partial Available \n Do you want Continue this bill";

        StockNotVailableDialog dialogView = new StockNotVailableDialog(this);
        dialogView.setTitle(message);
        dialogView.setPositiveLabel("Proceed");
        dialogView.setNegativeLabel("Cancel");

        dialogView.setPositiveListener(view -> {
            dialogView.dismiss();
            customerDataResBean = body;
            mPresenter.onOnlineBillApiCall(customerDataResBean);
        });

        dialogView.setNegativeListener(v -> dialogView.dismiss());
        dialogView.show();
    }

    @Override
    public void onSuccessOnlineBill(CustomerDataResBean customerDataResBean) {
        CorporateModel.DropdownValueBean item = new CorporateModel.DropdownValueBean();
        CustomerDataResBean customerDataResBean_pass;
        customerDataResBean_pass = customerDataResBean;

        eprescription_corpcode = customerDataResBean.getCorpCode();

        customerEntity.setLastName(customerDataResBean.getCustomerName());
        customerEntity.setMiddleName(customerDataResBean.getCustomerName());
        customerEntity.setMobileNo(customerDataResBean.getMobileNO());
        customerEntity.setCardName(customerDataResBean.getCustomerName());
        customerEntity.setCorpId(customerDataResBean.getCorpCode());
        customerEntity.setCustId(customerDataResBean.getCustomerID());
        customerEntity.setGender(String.valueOf(customerDataResBean.getGender()));

        DoctorSearchResModel.DropdownValueBean doctorentyty = new DoctorSearchResModel.DropdownValueBean();
        doctorentyty.setDisplayText(customerDataResBean.getDoctorName());
        doctorentyty.setCode(prescriptionLineList.get(position).getDoctorConCode());

        ArrayList<SalesLineEntity> saleslineentity = new ArrayList<>();
        for (SalesLineEntity salesLineEntity : customerDataResBean_pass.getSalesLine()) {
            if (salesLineEntity.getTotalTax() == 0) {
                salesLineEntity.setTotalTax(salesLineEntity.getTax());
            }
            saleslineentity.add(salesLineEntity);
        }
        customerDataResBean_pass.setSalesLine(saleslineentity);


        int position = 0;
        int tempposition = 0;
        if (corporateList != null) {
            for (CorporateModel.DropdownValueBean row : corporateList) {
                if (row.getCode().contains(eprescription_corpcode)) {
                    position = tempposition;
                    break;
                }
                tempposition++;
            }
            item = corporateList.get(position);
        }

        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().itemsArrayList.addAll(new ArrayList<>(saleslineentity));
        boolean is_onlineOrder = true;
        orderInfoItem.setREFNO(prescriptionLineList.get(this.position).getPrescriptionNo());
        customerDataResBean_pass.setISOnlineOrder(true);
        startActivityForResult(AddItemActivity.getStartIntents(getContext(), saleslineentity, customerEntity, orderInfoItem, customerDataResBean_pass, transactionIDResModel, is_onlineOrder, item, doctorentyty, prescriptionLineList.get(this.position), medicineResponseList), ACTIVITY_EPRESCRIPTIONBILLING_DETAILS_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void onFailureOnlineBill(CustomerDataResBean customerDataResBean) {
        Toast.makeText(getApplicationContext(), customerDataResBean.getReturnMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReqQtyUpdate(EPrescriptionMedicineResponse medicineResponse) {
        if (medicineResponseList != null && medicineResponseList.size() > 0) {
            for (int i = 0; i < medicineResponseList.size(); i++) {
                if (medicineResponseList.get(i).getArtCode().equalsIgnoreCase(medicineResponse.getArtCode())) {
                    int pos = i;
                    medicineResponseList.get(pos).setReqQty(medicineResponse.getReqQty());
                }
            }
            if (ePrescriptionMedicineDetailsAdapter != null)
                ePrescriptionMedicineDetailsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }
}