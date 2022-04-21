package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityEprescriptionInfoBinding;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemActivity;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.adapter.MedicineInfoRecycleAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.EPrescriptionDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog.StockNotVailableDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineBatchResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineInfoEntity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateRequest;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.BluetoothActivity;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.PhotoPopupWindow;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.ViewAnimationUtils;
import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.google.gson.Gson;
import com.printf.manager.BluetoothManager;
import com.printf.manager.PrintfTSPLManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

//import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;

public class EPrescriptionInfoInfoActivity extends BaseActivity implements EPrescriptionInfoMvpView {

    @Inject
    EPrescriptionInfoMvpPresenter<EPrescriptionInfoMvpView> mPresenter;
    private ActivityEprescriptionInfoBinding activityEPrescriptionInfoBinding;
    private boolean isExpand = true;
    private boolean isMedicineExpand = true;
    private boolean isBarcodeExpand = true;
    private int rotationAngle = 180;
    private int medicineRotationAngle = 180;
    private int barcodeRotationAngle = 180;
    int reserved_qty = 0;
    double totalamount = 0;
    private MedicineInfoRecycleAdapter medicinesDetailAdapter;
    private OMSTransactionHeaderResModel.OMSHeaderObj orderInfoItem;
    private boolean isShowDialog = false;
    ArrayList<SalesLineEntity> salesentity = new ArrayList<>();
    ArrayList<SalesLineEntity> tempsalesentity = new ArrayList<>();
    List<PickPackReservation> globalpickupreservation = new ArrayList<>();
    private String imageUri = "";
    private static final String TAG = EPrescriptionInfoInfoActivity.class.getSimpleName();
    ArrayList<MedicineInfoEntity> itemsArrayList = new ArrayList<>();
    CustomerDataResBean customerDataResBean;
    String eprescription_corpcode = "0";
    boolean pickreserved_status = false;
    private int ACTIVITY_RESULT_FOR_EPRESCRIPTION_BATCH_INFO = 125;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList;
    private GetCustomerResponse.CustomerEntity customerEntity;
    CorporateModel corporateModel;
    TransactionIDResModel transactionIDResModel;
    private final int ACTIVITY_EPRESCRIPTIONBILLING_DETAILS_CODE = 122;
    private ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList = new ArrayList<>();
    private boolean isSelectedBatch = false;
    private int manualSelectedPosition = 0;
    private int enteredQuantity, batchQuantity;
    private int sales_pick_count = 0;
    private int iterator = 0;
    private String globalbatchid = "";
    private ConstraintLayout constraintLayout;
    //Bixolon Bar Code printer functionality...................
    private int mBarcodeSelection = BixolonLabelPrinter.BARCODE_CODE128;
    private int mHri = BixolonLabelPrinter.HRI_BELOW_FONT_SIZE_1;
    public ArrayList<GetBatchInfoRes.BatchListObj> tempselectedBatches = new ArrayList<>();

    public boolean packedclickable = false;
    public boolean packedconformation = false;
    public boolean shippingcharges = false;

    private final int ACTIVITY_BARCODESCANNER_DETAILS_CODE = 151;


    public static Intent getStartIntent(Context context, OMSTransactionHeaderResModel.OMSHeaderObj orderInfoEntity, TransactionIDResModel transactionIDResModel, CorporateModel corporateList) {
        Intent intent = new Intent(context, EPrescriptionInfoInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_item", orderInfoEntity);
        bundle.putSerializable("transactionres_model", transactionIDResModel);
        //intent.putExtra("customer_model", customerEntity);
        intent.putExtra("corporate_model", corporateList);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double diagonalInches = UiUtils.displaymetrics(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activityEPrescriptionInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_eprescription_info);
        getActivityComponent().inject(this);
        mPresenter.onAttach(EPrescriptionInfoInfoActivity.this);
        constraintLayout = findViewById(R.id.constraint_layout);
        setUp();
    }

    @Override
    protected void setUp() {

        if (Constant.getInstance().Orders_type.equalsIgnoreCase("Picking")) {
            activityEPrescriptionInfoBinding.pickupConfirmation.setVisibility(View.GONE);
            activityEPrescriptionInfoBinding.unpickupConformation.setVisibility(View.GONE);

            activityEPrescriptionInfoBinding.packingConfirmation.setVisibility(View.VISIBLE);
            activityEPrescriptionInfoBinding.unpackingConformation.setVisibility(View.VISIBLE);

        } else if (Constant.getInstance().Orders_type.equalsIgnoreCase("Packing")) {
            activityEPrescriptionInfoBinding.pickupConfirmation.setVisibility(View.GONE);
            activityEPrescriptionInfoBinding.unpickupConformation.setVisibility(View.GONE);

            activityEPrescriptionInfoBinding.packingConfirmation.setVisibility(View.GONE);
            activityEPrescriptionInfoBinding.unpackingConformation.setVisibility(View.GONE);
        } else if (Constant.getInstance().Orders_type.equalsIgnoreCase("Invoice")) {

        }

        orderInfoItem = (OMSTransactionHeaderResModel.OMSHeaderObj) getIntent().getSerializableExtra("order_item");
        corporateModel = new CorporateModel();
        //data from add activity......
        corporateModel = (CorporateModel) getIntent().getSerializableExtra("corporate_model");
        transactionIDResModel = (TransactionIDResModel) getIntent().getSerializableExtra("transactionres_model");
        customerEntity = new GetCustomerResponse.CustomerEntity();
        if (corporateModel != null) {
            getCorporateList(corporateModel);
        }


        activityEPrescriptionInfoBinding.setCallback(mPresenter);
        activityEPrescriptionInfoBinding.setOrderItem(orderInfoItem);

        System.out.println("Customer Name-->2" + "");

        itemsArrayList = getMedicineInfo();
        medicinesDetailAdapter = new MedicineInfoRecycleAdapter(this, itemsArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityEPrescriptionInfoBinding.medicineRecycle.setLayoutManager(mLayoutManager);
        activityEPrescriptionInfoBinding.medicineRecycle.setItemAnimator(new DefaultItemAnimator());
        activityEPrescriptionInfoBinding.medicineRecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));
        activityEPrescriptionInfoBinding.medicineRecycle.setItemAnimator(new DefaultItemAnimator());
        medicinesDetailAdapter.setClickListiner(this);
        activityEPrescriptionInfoBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);
        activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());

        mPresenter.fetchOMSCustomerInfo(orderInfoItem.getREFNO());

        activityEPrescriptionInfoBinding.detailsExpanCollapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    isExpand = false;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(activityEPrescriptionInfoBinding.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.collapse(activityEPrescriptionInfoBinding.customerDetailsLayout);
                } else {
                    isExpand = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(activityEPrescriptionInfoBinding.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.expand(activityEPrescriptionInfoBinding.customerDetailsLayout);
                }
            }
        });

       /* activityEPrescriptionInfoBinding.medicineExpanCollapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMedicineExpand) {

                    //activityEPrescriptionInfoBinding.medicineListLayout.setVisibility(View.GONE);
                    isMedicineExpand = false;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(activityEPrescriptionInfoBinding.medicineCollapseIcon, "rotation", medicineRotationAngle, medicineRotationAngle + 180);
                    anim.setDuration(600);
                    anim.start();
                    medicineRotationAngle += 180;
                    medicineRotationAngle = medicineRotationAngle % 360;
                    ViewAnimationUtils.collapse(activityEPrescriptionInfoBinding.medicineListLayout);
                } else {
                    isMedicineExpand = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(activityEPrescriptionInfoBinding.medicineCollapseIcon, "rotation", medicineRotationAngle, medicineRotationAngle + 180);
                    anim.setDuration(300);
                    anim.start();
                    medicineRotationAngle += 180;
                    medicineRotationAngle = medicineRotationAngle % 360;
                    ViewAnimationUtils.expand(activityEPrescriptionInfoBinding.medicineListLayout);
                }
            }
        });*/

        activityEPrescriptionInfoBinding.prescriptionPreView.setOnClickListener(v -> {
            if (!imageUri.isEmpty()) {
                Bitmap bitmap = getBitmapFromURL(imageUri);
                new PhotoPopupWindow(EPrescriptionInfoInfoActivity.this, R.layout.layout_image_fullview, v, imageUri, null);

            } else {
                onError("Prescription is not available");
            }
        });

        activityEPrescriptionInfoBinding.cancelOnlineOrderBtn.setOnClickListener(v -> {

        });


        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Your printer is disconnected. Please connect to Printer by clicking on Reprint Barcode", Toast.LENGTH_LONG).show();
        }
        // generatebarcode(orderInfoItem.getREFNO());

    }

    //generate botmap from prescription url.....
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public void generatebarcode(String refnumber) {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            Toast.makeText(getContext(), "Your printer is disconnected. Please connect to Printer by clicking on Reprint Barcode", Toast.LENGTH_LONG).show();
        } else {
            PrintfTSPLManager instance = PrintfTSPLManager.getInstance(EPrescriptionInfoInfoActivity.this);
            instance.clearCanvas();
            instance.initCanvas(90, 23);
            instance.setDirection(0);
            //打印条形码
            //Print barcode
            instance.printBarCode(20, 10, "128", 130, 2, 2, 0, refnumber);
            instance.beginPrintf(1);
        }
        /*MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(refnumber, BarcodeFormat.CODE_128, 242, 71);
            Bitmap bitmap = Bitmap.createBitmap(242, 71, Bitmap.Config.RGB_565);
            for (int i = 0; i < 242; i++) {
                for (int j = 0; j < 71; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }

            String encodedImage = BitMapToString(bitmap);
            Constant.getInstance().global_barcode_str = encodedImage;
            // activityEPrescriptionInfoBinding.imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }*/

    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    //Update OMS Order
    @Override
    public void UpdateOmsOrder_Pickingconfirmation() {
//        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
//            Toast.makeText(getContext(), "Your printer is disconnected. Please connect to Printer by clicking on Reprint Barcode", Toast.LENGTH_LONG).show();
//        }
//        else
//        {

        if (itemsArrayList != null && itemsArrayList.size() > 0) {
            boolean isAllItemsSelecetd = true;
            for (int i = 0; i < itemsArrayList.size(); i++) {
                if (itemsArrayList.get(i).getReqQty() <= 0) {
                    isAllItemsSelecetd = false;
                }
            }
            if (isAllItemsSelecetd) {
                OMSOrderUpdateRequest request = new OMSOrderUpdateRequest();
                request.setRequestType("1");
                request.setFulfillmentID(orderInfoItem.getREFNO());
                ArrayList<SalesLineEntity> pick_pack_list = new ArrayList<>();
                for (SalesLineEntity item : salesentity) {
                    if (item.getModifyBatchId().length() > 0) {
                        pick_pack_list.add(item);
                    }

                }
                request.setReservedSalesLine(pick_pack_list);
                if (pick_pack_list.size() > 0) {
                    salesentity.clear();
                    salesentity = pick_pack_list;
                    mPresenter.UpdateOmsOrder(request);
                } else {
                    UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Please Reserve the Qty");
                }
//        }
            } else {
                UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Please Select the All Items");
            }
        }
    }

    @Override
    public void UpdateOmsOrder_unPickingconfirmation() {
        if (orderInfoItem.getOrderPacked()) {
            OMSOrderUpdateRequest request = new OMSOrderUpdateRequest();
            request.setRequestType("2");
            request.setFulfillmentID(orderInfoItem.getREFNO());
            ArrayList<SalesLineEntity> pick_pack_list = new ArrayList<>();
            for (SalesLineEntity item : salesentity) {
                if (item.getModifyBatchId().length() > 0) {
                    pick_pack_list.add(item);
                }

            }
            request.setReservedSalesLine(pick_pack_list);
            mPresenter.UpdateOmsOrder(request);
        } else {
            UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "PickUp Not Confimed");

        }

    }

    @Override
    public void UpdateOmsOrder_Packingconfirmation() {
        if (!packedclickable) {
            packedclickable = true;
            OMSOrderUpdateRequest request = new OMSOrderUpdateRequest();
            request.setRequestType("3");
            request.setFulfillmentID(orderInfoItem.getREFNO());
            // request.setTerminalID("005");
            ArrayList<SalesLineEntity> pick_pack_list = new ArrayList<>();
            int lineno = 0;
            System.out.println("Salesentity lines:-->" + new Gson().toJson(salesentity));
            for (SalesLineEntity item : salesentity) {
                if (item.getModifyBatchId().length() > 0) {
                    item.setLineNo(lineno);
                    if (item.getPrice() == 0) {
                        item.setPrice(item.getMRP());
                    }
                    System.out.println("Salesentity lines:-->" + new Gson().toJson(item));
                    pick_pack_list.add(item);
                }
                lineno++;

            }
            request.setReservedSalesLine(pick_pack_list);
            //  mPresenter.UpdateOmsOrder(request);
            if (pick_pack_list.size() > 0) {
                // salesentity.clear();
                //  salesentity = pick_pack_list;
                mPresenter.UpdateOmsOrder(request);
            } else {
                UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Please Reserve the Qty");
            }
        } else {
            UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Already Packed confirmation Completed");

        }

    }

    @Override
    public void UpdateOmsOrder_unPackingconirmation() {
        if (orderInfoItem.getOrderPacked() || packedconformation) {
            OMSOrderUpdateRequest request = new OMSOrderUpdateRequest();
            request.setRequestType("4");
            request.setFulfillmentID(orderInfoItem.getREFNO());
            ArrayList<SalesLineEntity> pick_pack_list = new ArrayList<>();
            for (SalesLineEntity item : salesentity) {
                if (item.getModifyBatchId().length() > 0) {
                    pick_pack_list.add(item);
                }

            }
            request.setReservedSalesLine(pick_pack_list);
            mPresenter.UpdateOmsOrder(request);
        } else {
            UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Picking Not Confimed");
        }

    }


    @Override
    public void barcodereprint() {
        if (!BluetoothManager.getInstance(getContext()).isConnect()) {
            ConnectprinterDialog dialogView = new ConnectprinterDialog(this);
            dialogView.setTitle("Do you want to Re-Connect the Printer");
            dialogView.setPositiveLabel("Ok");
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                    startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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

            //Toast.makeText(getContext(), "Please connect Bluetooth first", Toast.LENGTH_SHORT).show();
            // startActivityForResult(BluetoothActivity.getStartIntent(getContext()), ACTIVITY_BARCODESCANNER_DETAILS_CODE);
            // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            // return;
        } else {
            generatebarcode(orderInfoItem.getREFNO());
        }


        /*String data = orderInfoItem.getREFNO();
        int horizontalPosition = Integer.parseInt("90");
        int verticalPosition = Integer.parseInt("50");
        int narrowBarWidth = Integer.parseInt("2");
        int wideBarWidth = Integer.parseInt("8");
        int height = Integer.parseInt("80");
        int quietZoneWidth = Integer.parseInt("0");
        MainActivity.mBixolonLabelPrinter.beginTransactionPrint();
        MainActivity.mBixolonLabelPrinter.setLength(600, 1, BixolonLabelPrinter.MEDIA_TYPE_GAP, 1);
        MainActivity.mBixolonLabelPrinter.draw1dBarcode(data, horizontalPosition, verticalPosition, mBarcodeSelection,
                narrowBarWidth, wideBarWidth, height, 0, mHri, quietZoneWidth);
        MainActivity.mBixolonLabelPrinter.print(1, 1);
        //MainActivity.mBixolonLabelPrinter.
        MainActivity.mBixolonLabelPrinter.endTransactionPrint();*/

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    public void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }


    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void onSuccessGetOMSTransaction(ArrayList<CustomerDataResBean> response) {
        if (response != null && response.size() > 0) {
            if (response.get(0) != null && response.size() > 0) {
                activityEPrescriptionInfoBinding.setModel(response.get(0));
                customerDataResBean = response.get(0);
                List<PickPackReservation> pickPackReservations = response.get(0).getPickPackReservation();
                if (pickPackReservations != null) {
                    for (PickPackReservation pickPackReservation : pickPackReservations) {
                        pickPackReservation.setisBatchupdated(false);
                        globalpickupreservation.add(pickPackReservation);
                    }
                }

                eprescription_corpcode = response.get(0).getCorpCode();
                salesentity = response.get(0).getSalesLine();
                tempsalesentity = response.get(0).getSalesLine();


                customerEntity.setLastName(response.get(0).getCustomerName());
                customerEntity.setMiddleName(response.get(0).getCustomerName());
                customerEntity.setMobileNo(response.get(0).getMobileNO());
                customerEntity.setCardName(response.get(0).getCustomerName());
                customerEntity.setCorpId(response.get(0).getCorpCode());
                customerEntity.setCustId(response.get(0).getCustomerID());
                customerEntity.setGender(String.valueOf(response.get(0).getGender()));


                if (response.get(0).getSalesLine().size() > 0) {
                    for (SalesLineEntity entity : response.get(0).getSalesLine()) {
                        MedicineInfoEntity medicineInfo = new MedicineInfoEntity();

                        medicineInfo.setCategoryCode(entity.getCategoryCode());
                        medicineInfo.setItemName(entity.getItemName());
                        medicineInfo.setQty(entity.getQty());
                        medicineInfo.setStockQty(entity.getStockQty());
                        medicineInfo.setMRP(entity.getPrice());
                        medicineInfo.setScheduleCategory(entity.getScheduleCategory());
                        medicineInfo.setCategory(entity.getCategory());
                        medicineInfo.setComment("");

                        medicineInfo.setItemId(entity.getItemId());
                        medicineInfo.setSubstitudeItemId("");
                        medicineInfo.setRackId(entity.getRackId());

                        List<PickPackReservation> pickupreservation = new ArrayList<>();
                        pickupreservation = response.get(0).getPickPackReservation();

                        double pickupqty = 0;
                        if (pickupreservation != null) {
                            for (PickPackReservation item : pickupreservation) {

                                if (entity.getItemId().equalsIgnoreCase(item.getPickupItemId())) {
                                    pickreserved_status = true;
                                    Constant.getInstance().isSelectedBatch = true;
                                    Constant.getInstance().selectedItem.setItemId(entity.getItemId());

                                    Constant.getInstance().batchServiceCall = 0;
                                    Constant.getInstance().batchInfoProducts.clear();
                                    Constant.getInstance().arrBatchList.clear();

                                    pickupqty = pickupqty + item.getPickupQty();
                                }

                            }
                        }

                        medicineInfo.setReqQty(pickupqty);
                        System.out.println("Customer Name-->1" + entity.getItemName());
                        itemsArrayList.add(medicineInfo);

                        double total = entity.getPrice() * pickupqty;
                        totalamount = totalamount + total;
                    }
                    String Rupeestring = "\u20B9";
                    byte[] utf8 = null;
                    try {
                        utf8 = Rupeestring.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    try {
                        Rupeestring = new String(utf8, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String total_amt = String.format("%.2f", totalamount);
                    activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);

                }

                activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());
                medicinesDetailAdapter.notifyDataSetChanged();
                if (response.get(0).getOrderPrescriptionURL().size() > 0) {
                    imageUri = response.get(0).getOrderPrescriptionURL().get(0).getPERSCRIPTIONURL();
                }
            }

            if (globalpickupreservation.size() > 0) {
                additemstosalesline(globalpickupreservation);
            } else {
                mPresenter.checkeshopshippingcharges();
            }
        } else {
            showMessage("Order Details Not Available");
        }


    }

    public void updateitemline() {
        if (itemsArrayList != null && itemsArrayList.size() > 0) {
            for (MedicineInfoEntity entity : itemsArrayList) {
                if (entity.getItemId().equalsIgnoreCase("ESH0002")) {
                    double pickupqty = 0;
                    pickupqty = pickupqty + 1.0;
                    entity.setReqQty(pickupqty);
                    System.out.println("Customer Name-->1" + entity.getItemName());
                    // itemsArrayList.add(medicineInfo);
                    double total = entity.getPrice() * pickupqty;
                    totalamount = totalamount + total;
                }

            }

            String Rupeestring = "\u20B9";
            byte[] utf8 = null;
            try {
                utf8 = Rupeestring.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                Rupeestring = new String(utf8, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String total_amt = String.format("%.2f", totalamount);
            activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);


        }

        /*if (salesentity.size() > 0) {
            for (SalesLineEntity entity :salesentity) {
                if(entity.getItemId().equalsIgnoreCase("ESH0002")) {


                    MedicineInfoEntity medicineInfo = new MedicineInfoEntity();

                    medicineInfo.setCategoryCode(entity.getCategoryCode());
                    medicineInfo.setItemName(entity.getItemName());
                    medicineInfo.setQty(entity.getQty());
                    medicineInfo.setStockQty(entity.getStockQty());
                    medicineInfo.setMRP(entity.getPrice());
                    medicineInfo.setScheduleCategory(entity.getScheduleCategory());
                    medicineInfo.setCategory(entity.getCategory());
                    medicineInfo.setComment("");

                    medicineInfo.setItemId(entity.getItemId());
                    medicineInfo.setSubstitudeItemId("");
                    medicineInfo.setRackId(entity.getRackId());
                    double pickupqty = 0;
                    pickupqty = pickupqty + 1.0;
                    medicineInfo.setReqQty(pickupqty);
                    System.out.println("Customer Name-->1" + entity.getItemName());
                    itemsArrayList.add(medicineInfo);
                    double total = entity.getPrice() * pickupqty;
                    totalamount = totalamount + total;
                }
            }
            String Rupeestring = "\u20B9";
            byte[] utf8 = null;
            try {
                utf8 = Rupeestring.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                Rupeestring = new String(utf8, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String total_amt = String.format("%.2f", totalamount);
            activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);
        }*/
        // activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());
        medicinesDetailAdapter.notifyDataSetChanged();
    }

    public void checkeshopshippingcharges() {
        List<PickPackReservation> pickupreservation = new ArrayList<>();
        pickupreservation = customerDataResBean.getPickPackReservation();
        if (salesentity != null && salesentity.size() > 0) {
            boolean itenavailable = false;
            if (pickupreservation != null && pickupreservation.size() > 0) {
                for (PickPackReservation pickPackReservation : pickupreservation) {
                    if (pickPackReservation.getPickupItemId().equalsIgnoreCase("ESH0002")) {
                        itenavailable = true;
                    }
                }
            }
            if (!itenavailable) {
                int count = 0;
                for (SalesLineEntity entity : salesentity) {
                    if (entity.getItemId().equalsIgnoreCase("ESH0002")) {
                        shippingcharges = true;
                        onClickProductItem(count);
                    }
                    count++;
                }


            }
        }
    }


    public void additemstosalesline(List<PickPackReservation> pickPackReservations) {
        for (PickPackReservation pickPackReservation : pickPackReservations) {
            if (!pickPackReservation.getisBatchupdated()) {
                SalesLineEntity entity = new SalesLineEntity();
                entity.setItemId(pickPackReservation.getPickupItemId());
                globalbatchid = pickPackReservation.getPickupInventBatchId();
                for (SalesLineEntity entity1 : salesentity) {
                    if (entity1.getItemId().equalsIgnoreCase(pickPackReservation.getPickupItemId())) {
                        Constant.getInstance().selectedItem = entity1;
                    }
                }
                mPresenter.getBatchDetailsApi_pickpack(entity, pickPackReservation);
                break;
            }

        }
    }

    @Override
    public void onSuccessGetOMSPhysicalBatch(MedicineBatchResBean response) {
        if (response.getLstPhysicalBatch().size() > 0) {

            medicinesDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickOrderStockFulfilment() {

    }

    @Override
    public void onClickCancelOnlineOrder() {
        EPrescriptionDialog mDialog = EPrescriptionDialog.newInstance("OMS Remarks", "Select Remark", "Submit", "Cancel", getCancelReasons());
        mDialog.setEPrescriptionDialogMvpView(this);
        mDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClickChangeSite() {
        EPrescriptionDialog mDialog = EPrescriptionDialog.newInstance("Change Site", "Select Site Name", "Proceed", "Cancel", getChangeSite());
        mDialog.setEPrescriptionDialogMvpView(this);
        mDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClickClose() {

    }

    @Override
    public void CheckBatchStockFailure(CustomerDataResBean response) {
        // showMessage(response.getReturnMessage());
        //  showMessage("Stock Not Available");
        String message = response.getReturnMessage();
        message = message + "Stock Partial Available \n Do you want Continue this bill";

        StockNotVailableDialog dialogView = new StockNotVailableDialog(this);
        dialogView.setTitle(message);
        dialogView.setPositiveLabel("Proceed");
        dialogView.setNegativeLabel("Cancel");

        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                customerDataResBean = response;

                mPresenter.onLoadOmsOrder(customerDataResBean);
            }
        });

        dialogView.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    @Override
    public void CheckBatchStockSuccess(CustomerDataResBean response) {
        if (response != null) {
            customerDataResBean = response;
            if (orderInfoItem.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                mPresenter.onLoadOmsOrder(customerDataResBean);
            } else {
                CheckBatchStockFailure(customerDataResBean);

            }


        }

    }


    @Override
    public void LoadOmsOrderSuccess(CustomerDataResBean response) {
        CorporateModel.DropdownValueBean item = new CorporateModel.DropdownValueBean();
        CustomerDataResBean customerDataResBean_pass;
        customerDataResBean_pass = response;
        eprescription_corpcode = response.getCorpCode();
        customerEntity.setLastName(response.getCustomerName());
        customerEntity.setMiddleName(response.getCustomerName());
        customerEntity.setMobileNo(response.getMobileNO());
        customerEntity.setCardName(response.getCustomerName());
        customerEntity.setCorpId(response.getCorpCode());
        customerEntity.setCustId(response.getCustomerID());
        customerEntity.setGender(String.valueOf(response.getGender()));

        DoctorSearchResModel.DropdownValueBean doctorentyty = new DoctorSearchResModel.DropdownValueBean();

        doctorentyty.setDisplayText(response.getDoctorName());
        doctorentyty.setCode("R00123");
        String Doctor_Name = response.getDoctorName();


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
        boolean is_omsorder = true;


        startActivityForResult(AddItemActivity.getStartIntent(getContext(), saleslineentity, customerEntity, orderInfoItem, customerDataResBean_pass, transactionIDResModel, is_omsorder, item, doctorentyty), ACTIVITY_EPRESCRIPTIONBILLING_DETAILS_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void LoadOmsOrderFailure(CustomerDataResBean response) {
        if (response != null) {
            UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, response.getReturnMessage());

            // mPresenter.onLoadOmsOrder(response);
        }
    }

    //OmsOrderUpdate Api Response................
    @Override
    public void OmsOrderUpdateSuccess(OMSOrderUpdateResponse response) {
        //showMessage(response.getReturnMessage());
        UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, response.getReturnMessage());

        Constant.getInstance().frompickpakconform = true;
        if (Constant.getInstance().Orders_type.equalsIgnoreCase("EPrescription")) {
            barcodereprint();
        }

        // finish();
        // overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

        if (packedclickable) {
            packedconformation = true;
        } else {

            finish();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }


    }


    @Override
    public void OmsOrderUpdateFailure(OMSOrderUpdateResponse response) {
        if (response != null) {
            UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, response.getReturnMessage());
            // mPresenter.onLoadOmsOrder(response);
        }
    }

    @Override
    public void onClickContinueBilling() {
        //  if (orderInfoItem.getOrderPickup() && orderInfoItem.getOrderPacked()) {
        if (orderInfoItem.getOrderPickup() && packedconformation || orderInfoItem.getOrderPickup() && Constant.getInstance().Orders_type.equalsIgnoreCase("Packing")) {
            if (salesentity.size() > 0 && salesentity != null) {
                ArrayList<SalesLineEntity> pick_pack_list = new ArrayList<>();
                ArrayList<SalesLineEntity> pick_pack_list_for_disc = new ArrayList<>();
                for (SalesLineEntity item : salesentity) {
                    if (item.getModifyBatchId().length() > 0) {
                        if (item.getPrice() == 0) {
                            item.setPrice(item.getMRP());
                        }
                        pick_pack_list.add(item);
                    } else {
                        pick_pack_list_for_disc.add(item);
                    }

                }
                if (pick_pack_list.size() > 0) {
                    if (pick_pack_list_for_disc.size() > 0) {
                        for (SalesLineEntity salesLineEntitydec : pick_pack_list_for_disc) {
                            int count = 0;
                            for (SalesLineEntity salesLineEntity : pick_pack_list) {
                                if (salesLineEntitydec.getItemId().equalsIgnoreCase(salesLineEntity.getItemId())) {
                                    pick_pack_list.get(count).setTotalDiscAmount(salesLineEntitydec.getTotalDiscAmount());
                                    pick_pack_list.get(count).setTotalDiscPct(salesLineEntitydec.getTotalDiscPct());
                                    pick_pack_list.get(count).setOmsLineRECID(salesLineEntitydec.getOmsLineRECID());
                                    pick_pack_list.get(count).setOmsLineID(salesLineEntitydec.getOmsLineID());

                                    // salesLineEntity.setTotalDiscAmount(salesLineEntitydec.getTotalDiscAmount());
                                    //  salesLineEntity.setTotalDiscPct(salesLineEntitydec.getTotalDiscPct());

                                }
                                count++;
                            }

                        }
                    }
                    salesentity.clear();
                    salesentity = pick_pack_list;
                    customerDataResBean.setSalesLine(salesentity);
                    if (salesentity.size() > 0) {
                        if (salesentity.size() < 2) {
                            if (salesentity.get(0).getItemId().equalsIgnoreCase("ESH0002")) {
                                UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Please Add New sales Line");
                            } else {
                                mPresenter.onCheckBatchStock(customerDataResBean);
                            }

                        } else {
                            mPresenter.onCheckBatchStock(customerDataResBean);
                        }
                    }
                } else {
                    UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Please Reserve the Qty");
                }
            }
        } else {
            String message = "";
            if (!orderInfoItem.getOrderPickup() && !orderInfoItem.getOrderPacked()) {
                message = "Order Picked&Packed not Completed";
            } else if (orderInfoItem.getOrderPickup() && !orderInfoItem.getOrderPacked()) {
                message = "Order Packed not Completed";
            }
            UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, message);
        }

    }

    public void getCorporateList(CorporateModel corporateModel) {
        if (corporateModel.get_DropdownValue() != null && corporateModel.get_DropdownValue().size() > 0) {
            corporateList = new ArrayList<>();
            corporateList.addAll(corporateModel.get_DropdownValue());

        }
    }


    private ArrayList<SpinnerPojo.MaritalStatus> getCancelReasons() {
        ArrayList<SpinnerPojo.MaritalStatus> arrMaritalSpinner = new ArrayList<>();
        SpinnerPojo.MaritalStatus maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Medicine Out of stock");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Item shortage");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("New Medicine");
        arrMaritalSpinner.add(maritalStatus);
        return arrMaritalSpinner;
    }

    private ArrayList<SpinnerPojo.MaritalStatus> getChangeSite() {
        ArrayList<SpinnerPojo.MaritalStatus> arrMaritalSpinner = new ArrayList<>();
        SpinnerPojo.MaritalStatus maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Nungambakkam");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("TNagar");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Vadapalani");
        arrMaritalSpinner.add(maritalStatus);
        return arrMaritalSpinner;
    }

    private ArrayList<MedicineInfoEntity> getMedicineInfo() {
        ArrayList<MedicineInfoEntity> itemsArrayList = new ArrayList<>();
        ArrayList<MedicineInfoEntity> batchitemsArrayList = new ArrayList<>();
        if (salesentity.size() > 0) {
            for (SalesLineEntity entity : salesentity) {
                MedicineInfoEntity medicineInfo = new MedicineInfoEntity();

                medicineInfo.setCategoryCode(entity.getCategoryCode());
                medicineInfo.setItemName(entity.getItemName());
                medicineInfo.setQty(entity.getQty());
                medicineInfo.setStockQty(entity.getStockQty());
                medicineInfo.setMRP(entity.getPrice());
                medicineInfo.setScheduleCategory(entity.getScheduleCategory());
                medicineInfo.setCategory(entity.getCategory());
                medicineInfo.setComment("");
                medicineInfo.setSubstitudeItemId("");
                medicineInfo.setItemId(entity.getItemId());
                medicineInfo.setRackId(entity.getRackId());

                System.out.println("Customer Name-->1" + entity.getItemName());
                itemsArrayList.add(medicineInfo);

                double total = entity.getPrice() * entity.getQty();
                totalamount = totalamount + total;
            }
            String Rupeestring = "\u20B9";
            byte[] utf8 = null;
            try {
                utf8 = Rupeestring.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                Rupeestring = new String(utf8, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String total_amt = String.format("%.2f", totalamount);
            activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);


        }
        return itemsArrayList;
    }

    @Override
    public void onaddItemevent(int position) {
        mPresenter.onNavigateNextActivity();
        Constant.getInstance().isdoneBatchSelect = true;
        medicinesDetailAdapter.notifyDataSetChanged();
    }

    //BarCode generate functionalities..............
    @Override
    public void onClickProductItem(int position) {

        activityEPrescriptionInfoBinding.medicineRecycle.removeAllViews();
        Constant.getInstance().selected_position = position;

        Constant.getInstance().manualSelectedPosition = 0;
        Constant.getInstance().enteredQuantity = 0;
        Constant.getInstance().isSelectedBatch = false;
        Constant.getInstance().arrBatchList.clear();
        Constant.getInstance().batchServiceCall = 0;
        Constant.getInstance().navigate = false;
        Constant.getInstance().batchInfoProducts.clear();
        Constant.getInstance().isdoneBatchSelect = false;
        // String itemid = itemsArrayList.get(position).getCategoryCode();
        if (salesentity.size() >= position) {
            Constant.getInstance().selectedItem = salesentity.get(position);

            SalesLineEntity salesLineEntitylist = salesentity.get(position);
            SalesLineEntity salesLineEntity = new SalesLineEntity();

            salesLineEntity.setAdditionaltax(0);
            salesLineEntity.setApplyDiscount(false);
            salesLineEntity.setBarcode("");
            salesLineEntity.setCategory(salesLineEntitylist.getCategory());
            salesLineEntity.setCategoryCode(salesLineEntitylist.getCategoryCode());
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
            salesLineEntity.setDiseaseType(salesLineEntitylist.getDiseaseType());
            salesLineEntity.setDPCO(salesLineEntitylist.getDPCO());
            salesLineEntity.setHsncode_In(salesLineEntitylist.getHsncode_In());
            salesLineEntity.setISPrescribed(0);
            salesLineEntity.setISReserved(false);
            salesLineEntity.setISStockAvailable(true);
            salesLineEntity.setItemId(salesLineEntitylist.getItemId());
            salesLineEntity.setItemName(salesLineEntitylist.getItemName());
            salesLineEntity.setLineNo(Singletone.getInstance().itemsArrayList.size() + 1);
            salesLineEntity.setLineDiscPercentage(0);
            salesLineEntity.setLinedscAmount(0);
            salesLineEntity.setLineManualDiscountAmount(0);
            salesLineEntity.setLineManualDiscountPercentage(0);
            salesLineEntity.setManufacturerCode(salesLineEntitylist.getManufacturerCode());
            salesLineEntity.setManufacturerName(salesLineEntitylist.getManufacturerName());
            salesLineEntity.setMixMode(false);
            salesLineEntity.setMMGroupId("0");
            salesLineEntity.setModifyBatchId("");
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
            salesLineEntity.setProductRecID(salesLineEntitylist.getProductRecID());
            salesLineEntity.setRemainderDays(0);
            salesLineEntity.setRemainingQty(0);
            salesLineEntity.setRetailCategoryRecID(salesLineEntitylist.getRetailCategoryRecID());
            salesLineEntity.setRetailMainCategoryRecID(salesLineEntitylist.getRetailMainCategoryRecID());
            salesLineEntity.setRetailSubCategoryRecID(salesLineEntitylist.getRetailSubCategoryRecID());
            salesLineEntity.setReturnQty(0);
            salesLineEntity.setScheduleCategory(salesLineEntitylist.getScheduleCategory());
            salesLineEntity.setScheduleCategoryCode(salesLineEntitylist.getScheduleCategoryCode());
            salesLineEntity.setStockQty(0);
            salesLineEntity.setSubCategory(salesLineEntitylist.getSubCategory());
            salesLineEntity.setSubCategoryCode(salesLineEntitylist.getSubCategoryCode());
            salesLineEntity.setSubClassification(salesLineEntitylist.getSubClassification());
            salesLineEntity.setSubsitute(false);
            salesLineEntity.setSubstitudeItemId("");
            salesLineEntity.setTotalDiscAmount(0);
            salesLineEntity.setTotalDiscPct(0);
            salesLineEntity.setTotalRoundedAmount(0);
            salesLineEntity.setUnit("");
            salesLineEntity.setVariantId("");
            salesLineEntity.setVoid(false);
            salesLineEntity.setMRP(salesLineEntitylist.getMRP());

            mPresenter.getBatchDetailsApi(salesLineEntity);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_RESULT_FOR_EPRESCRIPTION_BATCH_INFO) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    ArrayList<SalesLineEntity> temp_salesentity = new ArrayList<>();

                    ArrayList<SalesLineEntity> med_itemsArrayList = (ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item");
                    if (med_itemsArrayList != null) {
                        for (SalesLineEntity entity : med_itemsArrayList) {
                            temp_salesentity.add(entity);
                            salesentity.add(entity);

                        }
                    }
                    // ArrayList<MedicineInfoEntity> itemsArrayList = new ArrayList<>();
                    for (SalesLineEntity entity : temp_salesentity) {
                        MedicineInfoEntity medicineInfo = new MedicineInfoEntity();

                        medicineInfo.setCategoryCode(entity.getCategoryCode());
                        medicineInfo.setItemName(entity.getItemName());
                        medicineInfo.setQty(entity.getQty());
                        medicineInfo.setStockQty(entity.getStockQty());
                        medicineInfo.setMRP(entity.getPrice());
                        medicineInfo.setScheduleCategory(entity.getScheduleCategory());
                        medicineInfo.setCategory(entity.getCategory());
                        medicineInfo.setComment("");
                        medicineInfo.setSubstitudeItemId("");
                        medicineInfo.setItemId(entity.getItemId());
                        medicineInfo.setRackId(entity.getRackId());

                        System.out.println("Customer Name-->1" + entity.getItemName());
                        double total = entity.getPrice() * entity.getQty();
                        totalamount = totalamount + total;

                        itemsArrayList.add(medicineInfo);

                    }


                    activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());
                    medicinesDetailAdapter.notifyDataSetChanged();

                    String Rupeestring = "\u20B9";
                    byte[] utf8 = null;
                    try {
                        utf8 = Rupeestring.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    try {
                        Rupeestring = new String(utf8, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String total_amt = String.format("%.2f", totalamount);
                    activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);
                }
            }

        }
    }

    @Override
    public void onSuccessBatchInfo(GetBatchInfoRes body, double mrp) {
        if (body.getBatchList().size() > 0) {
            arrBatchList.clear();
            int batch_index = 0;
            Constant.getInstance().Batchinvetoryids.clear();
            for (GetBatchInfoRes.BatchListObj item : body.getBatchList()) {
                if (item.getNearByExpiry() != true) {
                    Constant.getInstance().Batchinvetoryids.add(item.getBatchNo());
                    item.setPhysicalBatchID(item.getBatchNo());
                    for (SalesLineEntity salesLineEntitylist : salesentity) {
                        List<PickPackReservation> pickupreservation = new ArrayList<>();
                        pickupreservation = customerDataResBean.getPickPackReservation();

                        if (pickupreservation == null && batch_index == 0) {
                            if (Double.parseDouble(item.getQ_O_H()) > salesLineEntitylist.getQty()) {
                                if (item.getItemID().equalsIgnoreCase(salesLineEntitylist.getItemId())) {
                                    item.setREQQTY(salesLineEntitylist.getQty());
                                    item.setPhysicalbatchstatus(false);
                                    item.setSelected(false);
                                    item.setVendormrp(mrp);
                                    batch_index++;
                                }
                            } else {
                                if (item.getItemID().equalsIgnoreCase(salesLineEntitylist.getItemId())) {

                                    item.setREQQTY(Double.parseDouble(item.getQ_O_H()));
                                    item.setPhysicalbatchstatus(false);
                                    item.setSelected(false);
                                    item.setVendormrp(mrp);
                                    batch_index++;
                                }
                            }
                        }

                        double pickupqty = 0;
                        if (salesLineEntitylist.getInventBatchId().equalsIgnoreCase(item.getBatchNo()) && salesLineEntitylist.getItemId().equalsIgnoreCase(item.getItemID())) {
                            pickupqty = pickupqty + salesLineEntitylist.getQty();
                            item.setPhysicalbatchstatus(true);
                            item.setSelected(true);
                            item.setREQQTY(pickupqty);
                            item.setVendormrp(mrp);
                            //item.setREQQTY(pickupqty);
                            if (salesLineEntitylist.getModifyBatchId().length() > 0) {
                                item.setPhysicalBatchID(salesLineEntitylist.getModifyBatchId());
                            } else {
                                item.setPhysicalBatchID(item.getBatchNo());
                            }
                        } else if (salesLineEntitylist.getInventBatchId().equalsIgnoreCase(item.getBatchNo()) && !salesLineEntitylist.getInventBatchId().equals("null") && salesLineEntitylist.getItemId().equalsIgnoreCase(item.getItemID())) {
                            pickupqty = pickupqty + salesLineEntitylist.getQty();
                            item.setPhysicalbatchstatus(true);
                            item.setSelected(true);
                            item.setREQQTY(pickupqty);
                            item.setVendormrp(mrp);
                            //item.setREQQTY(pickupqty);
                            if (salesLineEntitylist.getModifyBatchId().length() > 0) {
                                item.setPhysicalBatchID(salesLineEntitylist.getModifyBatchId());
                            } else {
                                item.setPhysicalBatchID(item.getBatchNo());
                            }
                        }
                        // batch_index++;
                    }
                    arrBatchList.add(item);
                }
            }

            List<PickPackReservation> pickupreservation = new ArrayList<>();
            pickupreservation = customerDataResBean.getPickPackReservation();
            if (pickupreservation != null && pickupreservation.size() > 0) {
                for (PickPackReservation pickPackReservation : pickupreservation) {
                    if (Constant.getInstance().Batchinvetoryids != null && Constant.getInstance().Batchinvetoryids.size() > 0) {
                        if (Constant.getInstance().Batchinvetoryids.contains(pickPackReservation.getPickupInventBatchId())) {

                        } else {
                            GetBatchInfoRes.BatchListObj item = new GetBatchInfoRes.BatchListObj();
                            if (pickPackReservation.getPickupItemId().equalsIgnoreCase(Constant.getInstance().selectedItem.getItemId())) {

                                item.setPhysicalbatchstatus(true);
                                item.setSelected(true);
                                item.setREQQTY(pickPackReservation.getPickupQty());
                                item.setPhysicalBatchID(pickPackReservation.getPickupPhysicalInventBatchId());
                                item.setBatchNo(pickPackReservation.getPickupInventBatchId());
                                item.setMRP(pickPackReservation.getPrice());
                                item.setQ_O_H(String.valueOf(pickPackReservation.getPickupQty()));
                                item.setExpDate(pickPackReservation.getExpiry());
                                item.setItemID(pickPackReservation.getPickupItemId());
                                item.setVendormrp(mrp);
                                item.setTotalTax(Double.parseDouble(pickPackReservation.getTaxCode()));

                                arrBatchList.add(item);
                            }

                        }

                    }

                }
            }
            Constant.getInstance().arrBatchList.clear();
            if (arrBatchList != null && arrBatchList.size() > 0) {
                for (int i = 0; i < arrBatchList.size(); i++) {
                    Constant.getInstance().arrBatchList.add(arrBatchList.get(i));
                    if (i >= 14) {
                        break;
                    }
                }
            }
//            Constant.getInstance().arrBatchList = arrBatchList;
            if (shippingcharges) {
                if (Constant.getInstance().arrBatchList != null && Constant.getInstance().arrBatchList.size() > 0) {
                    shippingcharges = false;
                    GetBatchInfoRes.BatchListObj batch = Constant.getInstance().arrBatchList.get(0);
                    batch.setREQQTY(1);
                    SalesLineEntity salesLineEntity = newSalesLineEntity(batch, salesentity.size() + 1);
                    salesentity.add(salesLineEntity);
                    updateitemline();
                }
            }
            medicinesDetailAdapter.notifyDataSetChanged();

            if (arrBatchList.size() == 0) {
                UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Batch Id's Not Found");
            }


        } else {
            arrBatchList.clear();
            List<PickPackReservation> pickupreservation = new ArrayList<>();
            pickupreservation = customerDataResBean.getPickPackReservation();
            if (pickupreservation != null && pickupreservation.size() > 0) {
                for (PickPackReservation pickPackReservation : pickupreservation) {
                    GetBatchInfoRes.BatchListObj item = new GetBatchInfoRes.BatchListObj();
                    if (pickPackReservation.getPickupItemId().equalsIgnoreCase(Constant.getInstance().selectedItem.getItemId())) {

                        item.setPhysicalbatchstatus(true);
                        item.setSelected(true);
                        item.setREQQTY(pickPackReservation.getPickupQty());
                        item.setPhysicalBatchID(pickPackReservation.getPickupPhysicalInventBatchId());
                        item.setBatchNo(pickPackReservation.getPickupInventBatchId());
                        item.setMRP(pickPackReservation.getPrice());
                        item.setQ_O_H(String.valueOf(pickPackReservation.getPickupQty()));
                        item.setExpDate(pickPackReservation.getExpiry());
                        item.setItemID(pickPackReservation.getPickupItemId());
                        item.setTotalTax(Double.parseDouble(pickPackReservation.getTaxCode()));
                        item.setVendormrp(mrp);
                        arrBatchList.add(item);
                    }
                }
                Constant.getInstance().arrBatchList = arrBatchList;
                medicinesDetailAdapter.notifyDataSetChanged();
                if (arrBatchList.size() == 0) {
                    UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Batch Id's Not Found");
                }
            } else {
                UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, "Batch Id's Not Found");
            }
        }
    }


    @Override
    public void onSuccessBatchInfo_pickpack(GetBatchInfoRes body, PickPackReservation
            pickPackReservation, Double mrp) {
        if (body.getBatchList().size() > 0) {
            Constant.getInstance().Batchinvetoryids.clear();
            for (GetBatchInfoRes.BatchListObj item : body.getBatchList()) {
                double pickupqty = 0;
                Constant.getInstance().Batchinvetoryids.add(item.getBatchNo());
                if (item.getNearByExpiry() != true) {
                    item.setPhysicalBatchID(item.getBatchNo());

                    if (pickPackReservation.getPickupInventBatchId().equalsIgnoreCase(item.getBatchNo()) && pickPackReservation.getPickupItemId().equalsIgnoreCase(item.getItemID())) {
                        pickupqty = pickupqty + pickPackReservation.getPickupQty();
                        item.setPhysicalbatchstatus(true);
                        item.setSelected(true);
                        item.setREQQTY(pickupqty);
                        item.setPhysicalBatchID(pickPackReservation.getPickupPhysicalInventBatchId());
                        addsalesline(item, pickPackReservation);

                    } else {

                    }
                }
            }
            if (Constant.getInstance().Batchinvetoryids != null && Constant.getInstance().Batchinvetoryids.size() > 0) {
                if (Constant.getInstance().Batchinvetoryids.contains(pickPackReservation.getPickupInventBatchId())) {

                } else {
                    GetBatchInfoRes.BatchListObj item = new GetBatchInfoRes.BatchListObj();
                    if (pickPackReservation.getPickupItemId().equalsIgnoreCase(Constant.getInstance().selectedItem.getItemId())) {
                        item.setPhysicalbatchstatus(true);
                        item.setSelected(true);
                        item.setREQQTY(pickPackReservation.getPickupQty());
                        item.setPhysicalBatchID(pickPackReservation.getPickupPhysicalInventBatchId());
                        item.setBatchNo(pickPackReservation.getPickupInventBatchId());
                        item.setMRP(pickPackReservation.getPrice());
                        item.setQ_O_H(String.valueOf(pickPackReservation.getPickupQty()));
                        item.setExpDate(pickPackReservation.getExpiry());
                        item.setItemID(pickPackReservation.getPickupItemId());
                        item.setTotalTax(Double.parseDouble(pickPackReservation.getTaxCode()));

                        addsalesline(item, pickPackReservation);
                    }
                }
            }


        } else {
            if (pickPackReservation != null) {
                GetBatchInfoRes.BatchListObj item = new GetBatchInfoRes.BatchListObj();
                if (pickPackReservation.getPickupItemId().equalsIgnoreCase(Constant.getInstance().selectedItem.getItemId())) {
                    double pickupqty = 0;
                    pickupqty = pickupqty + pickPackReservation.getPickupQty();
                    item.setPhysicalbatchstatus(true);
                    item.setSelected(true);
                    item.setREQQTY(pickupqty);
                    item.setPhysicalBatchID(pickPackReservation.getPickupPhysicalInventBatchId());
                    item.setBatchNo(pickPackReservation.getPickupInventBatchId());
                    item.setMRP(pickPackReservation.getPrice());
                    item.setQ_O_H(String.valueOf(pickPackReservation.getPickupQty()));
                    item.setExpDate(pickPackReservation.getExpiry());
                    item.setItemID(pickPackReservation.getPickupItemId());
                    item.setTotalTax(Double.parseDouble(pickPackReservation.getTaxCode()));

                    addsalesline(item, pickPackReservation);
                }
            } else {
                showMessage("Batch Id's Not Found");

            }
            //showMessage("Batch Id's Not Found");
        }
    }

    public void addsalesline(GetBatchInfoRes.BatchListObj item, PickPackReservation
            pickPackReservation) {

        SalesLineEntity salesLineEntity = newSalesLineEntity(item, salesentity.size() + 1);
        salesentity.add(salesLineEntity);
        System.out.println("Customer Name-->0" + new Gson().toJson(salesentity));
        pickPackReservation.setisBatchupdated(true);
        List<PickPackReservation> tempreservation = globalpickupreservation;
        for (PickPackReservation pickPackReservation1 : globalpickupreservation) {
            if (pickPackReservation1.getPickupInventBatchId().equalsIgnoreCase(pickPackReservation.getPickupInventBatchId())) {
                pickPackReservation1.setisBatchupdated(true);
            } else {
            }

        }
        additemstosalesline(globalpickupreservation);


    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {

    }

    @Override
    public void showtoastmessage(String message) {
        UiUtils.showSnackbar(EPrescriptionInfoInfoActivity.this, constraintLayout, message);
        // showMessage(message);
    }

    @Override
    public void onItemClick(int position, int quantity, GetBatchInfoRes.
            BatchListObj batchListObj) {

        Constant.getInstance().manualSelectedPosition = position;
        Constant.getInstance().enteredQuantity = quantity;
        Constant.getInstance().isSelectedBatch = true;
        if (Constant.getInstance().arrBatchList != null && Constant.getInstance().arrBatchList.size() > 0) {
            for (int i = 0; i < Constant.getInstance().arrBatchList.size(); i++) {
                if (!Constant.getInstance().arrBatchList.get(i).getPhysicalbatchstatus()) {
                    Constant.getInstance().arrBatchList.get(i).setREQQTY(0);
                    Constant.getInstance().arrBatchList.get(i).setSelected(i == position);
                }
            }
        }
        medicinesDetailAdapter.notifyItemChanged(Constant.getInstance().selected_position);
    }

    @Override
    public void onUnpickupcheckActivity(GetBatchInfoRes.BatchListObj item) {
        reserved_qty = 0;
        ArrayList<SalesLineEntity> salesLineEntities = salesentity;
        int index = 0;
        for (SalesLineEntity entity : salesLineEntities) {
            if (entity.getItemId().equalsIgnoreCase(item.getItemID()) && entity.getInventBatchId().equalsIgnoreCase(item.getBatchNo())) {
                salesentity.get(index).setModifyBatchId("");
                salesentity.get(index).setInventBatchId("");

            }
            index++;
        }

        SalesLineEntity salesLineEntitylist = salesentity.get(Constant.getInstance().selected_position);
        totalamount = 0;
        double pickupqty = 0;
        for (SalesLineEntity entity : salesentity) {

            //  if (entity.getResqtyflag()) {
            if (salesLineEntitylist.getItemId().equalsIgnoreCase(entity.getItemId())) {
                List<PickPackReservation> pickupreservation = new ArrayList<>();
                pickupreservation = customerDataResBean.getPickPackReservation();
                if (entity.getModifyBatchId().length() > 0) {
                    pickupqty = pickupqty + entity.getQty();
                    double total = entity.getPrice() * entity.getQty();
                    totalamount = totalamount + total;
                }
            }
        }

        for (MedicineInfoEntity entity : itemsArrayList) {
            if (salesLineEntitylist.getItemId().equalsIgnoreCase(entity.getItemId())) {
                int position = itemsArrayList.indexOf(entity);
                itemsArrayList.get(position).setReqQty(pickupqty);
            }
        }


        activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());
        Constant.getInstance().isdoneBatchSelect = false;
        medicinesDetailAdapter.notifyDataSetChanged();
        String Rupeestring = "\u20B9";
        byte[] utf8 = null;
        try {
            utf8 = Rupeestring.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Rupeestring = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String total_amt = String.format("%.2f", totalamount);
        activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);
    }

    public void checkreservedqty(double Reservedqty) {
        if (Constant.getInstance().requestqty > Reservedqty) {
            CheckReservedQtyDialog dialogView = new CheckReservedQtyDialog(this);
            dialogView.setTitle("You have entered less than Request Qty");
            dialogView.setPositiveLabel("Ok");
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (salesentity.size() > 0) {
                        checkBatches();
                    } else {
                        checkBatches();
                    }

                    dialogView.dismiss();
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
            if (salesentity.size() > 0) {
                checkBatches();
            } else {
                checkBatches();
            }
        }
    }

    @Override
    public void addsaleslineautomatically() {
        //  mPresenter.

    }

    @Override
    public void onNavigateNextActivity() {
        double Reservedqty = 0;
        int count = 0;
        if (Constant.getInstance().arrBatchList != null && Constant.getInstance().arrBatchList.size() > 0) {
            for (GetBatchInfoRes.BatchListObj obj : Constant.getInstance().arrBatchList) {
                if (obj.isSelected() && obj.getPhysicalbatchstatus()) {
                    Reservedqty = Reservedqty + obj.getREQQTY();
                }
                count++;
                if (Constant.getInstance().arrBatchList.size() == count) {
                    checkreservedqty(Reservedqty);
                }
            }

        }
        /*if (salesentity.size() > 0) {
            checkBatches();
        } else {
            checkBatches();
        }*/
    }

    private boolean isProductExitsOrNot() {
        SalesLineEntity salesLineEntitylist = salesentity.get(Constant.getInstance().selected_position);
        for (SalesLineEntity oldItems : salesentity) {
            if (salesLineEntitylist.getItemId().equalsIgnoreCase(oldItems.getItemId())) {
                return true;
            }
        }
        return false;
    }

    private void productAllReadyExitsDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Item Already Exist");
        dialogView.setPositiveLabel("Yes");
        dialogView.setSubtitle("Do you want to continue?");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                checkBatches();
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

    private void checkBatches() {
        if (selectedBatches()) {
            doneBatchSelect();
        }

    }

    private void doneBatchSelect() {
        boolean alertForm = false;
        boolean noalertForm = false;

        if (Constant.getInstance().selectedBatches.size() > 0) {
            for (int i = 0; i < Constant.getInstance().selectedBatches.size(); i++) {
                if (Constant.getInstance().selectedBatches.get(i).getREQQTY() > 0) {
                    Constant.getInstance().batchNoTemp = Constant.getInstance().selectedBatches.get(i).getBatchNo();
                    Constant.getInstance().result = Constant.getInstance().batchNoTemp.trim();
                    if (Constant.getInstance().result.equalsIgnoreCase(Constant.getInstance().selectedBatches.get(i).getBatchNo())) {
                        noalertForm = true;
                    } else {
                        alertForm = true;
                        Constant.getInstance().selectedBatches.get(i).setREQQTY(0);
                        Constant.getInstance().arrBatchList.get(i).setREQQTY(0);
                    }
                }
            }
            if (alertForm && noalertForm) {
                mPresenter.checkBatchInventory(Constant.getInstance().selectedBatches.get(Constant.getInstance().batchServiceCall), alertForm);
            } else if (alertForm) {
                Constant.getInstance().navigate = true;
                alertIvalidBatch("Invalid Batch");
            } else if (noalertForm) {
                mPresenter.checkBatchInventory(Constant.getInstance().selectedBatches.get(Constant.getInstance().batchServiceCall), alertForm);
            }
        } else {
            alertQuantityError("Please Enter Valid Required Qty!!");
        }
    }


    @Override
    public void checkBatchInventorySuccess(boolean isAlertDialog) {
        if (isAlertDialog) {
            alertIvalidBatch("Invalid Batch");
        } else {
            int reserved_qty = 0;
            // totalamount = 0;
            if (Constant.getInstance().selectedBatches.size() > 0 && (Constant.getInstance().selectedBatches.size() - 1) != Constant.getInstance().batchServiceCall) {
                Constant.getInstance().batchServiceCall++;
                doneBatchSelect();
            } else {
                //int reservedqty=0;
                for (GetBatchInfoRes.BatchListObj batchListObj : Constant.getInstance().selectedBatches) {
                    if (batchListObj.getREQQTY() > 0) {
                        addBatch(batchListObj);
                        //  reservedqty++;
                    }
                }


                ArrayList<SalesLineEntity> temp_salesentity = new ArrayList<>();
                ArrayList<SalesLineEntity> med_itemsArrayList = Constant.getInstance().batchInfoProducts;
                if (med_itemsArrayList != null) {
                    for (SalesLineEntity entity : med_itemsArrayList) {
                        Log.d("batch id-->", entity.getInventBatchId());
                        // entity.setModifyBatchId();
                        entity.setResqty(true);
                        //entity.setPickupQty(entity.getPickupQty());
                        temp_salesentity.add(entity);
                        salesentity.add(entity);
                        Log.d("batch id-->", String.valueOf(salesentity));

                    }
                }

                double totalamountAddItem = 0.0;
                SalesLineEntity salesLineEntitylist = salesentity.get(Constant.getInstance().selected_position);
                double pickupqty = 0;
                for (SalesLineEntity entity : salesentity) {

                    if (salesLineEntitylist.getItemId().equalsIgnoreCase(entity.getItemId())) {
                        List<PickPackReservation> pickPackReservations = new ArrayList<>();

                        pickPackReservations = customerDataResBean.getPickPackReservation();
                        if (entity.getModifyBatchId().length() > 0) {

                            pickupqty = pickupqty + entity.getQty();
                            double total = entity.getPrice() * entity.getQty();
                            totalamount = totalamount + total;
                            totalamountAddItem = totalamountAddItem + total;
                        }
                    }
                }

                for (MedicineInfoEntity entity : itemsArrayList) {
                    if (salesLineEntitylist.getItemId().equalsIgnoreCase(entity.getItemId())) {
                        int position = itemsArrayList.indexOf(entity);
                        //  itemsArrayList.get(position).setReqQty(reserved_qty);
                        itemsArrayList.get(position).setReqQty(pickupqty);


                    }
                }


                activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());
                Constant.getInstance().isdoneBatchSelect = true;
                medicinesDetailAdapter.notifyDataSetChanged();
                String Rupeestring = "\u20B9";
                byte[] utf8 = null;
                try {
                    utf8 = Rupeestring.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    Rupeestring = new String(utf8, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                String total_amt = String.format("%.2f", totalamount);
                String total_amt = String.format("%.2f", totalamountAddItem);
                activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);
            }
        }
    }

    @Override
    public void checkBatchInventoryFailed(String returnMessage) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("");
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(returnMessage);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                finish();
            }
        });
        dialogView.show();
    }

    private void alertIvalidBatch(String message) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("");
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(message);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.getInstance().navigate) {
                    if (Constant.getInstance().selectedBatches.size() > 0 && (Constant.getInstance().selectedBatches.size() - 1) != Constant.getInstance().batchServiceCall) {
                        Constant.getInstance().batchServiceCall++;
                        doneBatchSelect();
                    } else {
                        for (GetBatchInfoRes.BatchListObj batchListObj : Constant.getInstance().selectedBatches) {
                            if (batchListObj.getREQQTY() > 0) {
                                addBatch(batchListObj);
                            }
                        }

                        salesentity.clear();
                        ArrayList<SalesLineEntity> temp_salesentity = new ArrayList<>();
                        ArrayList<SalesLineEntity> med_itemsArrayList = Constant.getInstance().batchInfoProducts;
                        if (med_itemsArrayList != null) {
                            for (SalesLineEntity entity : med_itemsArrayList) {
                                temp_salesentity.add(entity);
                                salesentity.add(entity);

                            }
                        }

                        for (SalesLineEntity entity : temp_salesentity) {
                            MedicineInfoEntity medicineInfo = new MedicineInfoEntity();

                            medicineInfo.setCategoryCode(entity.getCategoryCode());
                            medicineInfo.setItemName(entity.getItemName());
                            medicineInfo.setQty(entity.getQty());
                            medicineInfo.setStockQty(entity.getStockQty());
                            medicineInfo.setMRP(entity.getPrice());
                            medicineInfo.setScheduleCategory(entity.getScheduleCategory());
                            medicineInfo.setCategory(entity.getCategory());
                            medicineInfo.setComment("");
                            medicineInfo.setSubstitudeItemId("");
                            medicineInfo.setItemId(entity.getItemId());
                            medicineInfo.setRackId(entity.getRackId());

                            System.out.println("Customer Name-->1" + entity.getItemName());
                            itemsArrayList.add(medicineInfo);

                            double total = entity.getPrice() * entity.getQty();
                            totalamount = totalamount + total;

                        }
                        activityEPrescriptionInfoBinding.setItemsCount(itemsArrayList.size());
                        Constant.getInstance().isdoneBatchSelect = true;
                        medicinesDetailAdapter.notifyDataSetChanged();
                        String Rupeestring = "\u20B9";
                        byte[] utf8 = null;
                        try {
                            utf8 = Rupeestring.getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        try {
                            Rupeestring = new String(utf8, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String total_amt = String.format("%.2f", totalamount);
                        activityEPrescriptionInfoBinding.totalAmount.setText("Total Amount: " + Rupeestring + " " + total_amt);
                    }
                }
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }

    private void addBatch(GetBatchInfoRes.BatchListObj batchListObj) {
        boolean isUpdateExistBatch = false;
        for (int j = 0; j < salesentity.size(); j++) {
            SalesLineEntity s = salesentity.get(j);
            if (Constant.getInstance().selectedItem.getItemId().equalsIgnoreCase(s.getItemId()) && batchListObj.getBatchNo().equalsIgnoreCase(s.getInventBatchId())) {
                if (!s.getIsVoid()) {
                    salesentity.get(j).setQty(batchListObj.getREQQTY());
                    salesentity.get(j).setInventBatchId(batchListObj.getBatchNo());
                    salesentity.get(j).setModifyBatchId(batchListObj.getPhysicalBatchID());
                    isUpdateExistBatch = true;
                    break;
                } else {
                    salesentity.get(j).setQty(batchListObj.getREQQTY());
                    salesentity.get(j).setVoid(false);
                    salesentity.get(j).setModifyBatchId(batchListObj.getPhysicalBatchID());
                    isUpdateExistBatch = true;
                    break;
                }

            }
        }

        if (!isUpdateExistBatch)
            Constant.getInstance().batchInfoProducts.add(newSalesLineEntity(batchListObj, Constant.getInstance().batchInfoProducts.size() > 0 ? (Constant.getInstance().selectedItem.getLineNo() + Constant.getInstance().batchInfoProducts.size()) : Constant.getInstance().selectedItem.getLineNo()));
    }

    private boolean selectedBatches() {
        Constant.getInstance().selectedBatches.clear();

        for (GetBatchInfoRes.BatchListObj obj : Constant.getInstance().arrBatchList) {
            if (obj.isSelected()) {
                if (isValidQuantity(obj) && obj.getREQQTY() != 0) {
                    if (obj.getPhysicalbatchstatus()) {
                        Constant.getInstance().selectedBatches.add(obj);
                    } else {
                        Constant.getInstance().pickupstatus = true;
                        alertQuantityError("Check PickUp Status");
                        return false;


                    }
                } else {
                    if (obj.getREQQTY() != 0) {
                        alertQuantityError("Qty Can't greater than QOH!!");
                        return false;
                    }
                }
            } else {
                if (obj.getREQQTY() == 0 && obj.getUpdatezeroqtystatus()) {
                    Constant.getInstance().selectedBatches.add(obj);
                }

            }
        }
        if (Constant.getInstance().selectedBatches.size() == 0 && Constant.getInstance().pickupstatus == false) {

            alertQuantityError("Please Enter Valid Required Qty!!");
            return false;
        }
        return true;
    }

    private boolean isValidQuantity(GetBatchInfoRes.BatchListObj batch) {
        return batch.getREQQTY() <= Double.valueOf(batch.getQ_O_H());
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

    private SalesLineEntity newSalesLineEntity(GetBatchInfoRes.BatchListObj batch,
                                               int lineNumber) {
        SalesLineEntity salesLineEntity = new SalesLineEntity();
        salesLineEntity.setAdditionaltax(0);
        salesLineEntity.setApplyDiscount(false);
        salesLineEntity.setBarcode("");
        salesLineEntity.setCategory(Constant.getInstance().selectedItem.getCategory());
        salesLineEntity.setCategoryCode(Constant.getInstance().selectedItem.getCategoryCode());
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
        salesLineEntity.setDiseaseType(Constant.getInstance().selectedItem.getDiseaseType());
        salesLineEntity.setDPCO(Constant.getInstance().selectedItem.getDPCO());
        salesLineEntity.setHsncode_In(Constant.getInstance().selectedItem.getHsncode_In());
        salesLineEntity.setISPrescribed(0);
        salesLineEntity.setISReserved(false);
        salesLineEntity.setISStockAvailable(true);
        salesLineEntity.setItemId(Constant.getInstance().selectedItem.getItemId());
        salesLineEntity.setItemName(Constant.getInstance().selectedItem.getItemName());
        salesLineEntity.setLineNo(lineNumber);
        salesLineEntity.setLineDiscPercentage(0);
        salesLineEntity.setLinedscAmount(0);
        salesLineEntity.setLineManualDiscountAmount(0);
        salesLineEntity.setLineManualDiscountPercentage(0);
        salesLineEntity.setManufacturerCode(Constant.getInstance().selectedItem.getManufacturerCode());
        salesLineEntity.setManufacturerName(Constant.getInstance().selectedItem.getManufacturerName());
        salesLineEntity.setMixMode(false);
        salesLineEntity.setMMGroupId("0");
        salesLineEntity.setModifyBatchId("");
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
        salesLineEntity.setProductRecID(Constant.getInstance().selectedItem.getProductRecID());
        salesLineEntity.setRemainderDays(0);
        salesLineEntity.setRemainingQty(0);
        salesLineEntity.setRetailCategoryRecID(Constant.getInstance().selectedItem.getRetailCategoryRecID());
        salesLineEntity.setRetailMainCategoryRecID(Constant.getInstance().selectedItem.getRetailMainCategoryRecID());
        salesLineEntity.setRetailSubCategoryRecID(Constant.getInstance().selectedItem.getRetailSubCategoryRecID());
        salesLineEntity.setReturnQty(0);
        salesLineEntity.setScheduleCategory(Constant.getInstance().selectedItem.getScheduleCategory());
        salesLineEntity.setScheduleCategoryCode(Constant.getInstance().selectedItem.getScheduleCategoryCode());
        salesLineEntity.setStockQty(0);
        salesLineEntity.setSubCategory(Constant.getInstance().selectedItem.getSubCategory());
        salesLineEntity.setSubCategoryCode(Constant.getInstance().selectedItem.getSubCategory());
        salesLineEntity.setSubClassification(Constant.getInstance().selectedItem.getSubClassification());
        salesLineEntity.setSubsitute(false);
        salesLineEntity.setSubstitudeItemId("");
        salesLineEntity.setTotalDiscAmount(0);
        salesLineEntity.setTotalDiscPct(0);
        salesLineEntity.setTotalRoundedAmount(0);
        salesLineEntity.setUnit("");
        salesLineEntity.setVariantId("");
        salesLineEntity.setVoid(false);
        salesLineEntity.setBaseAmount(batch.getMRP());
        salesLineEntity.setCGSTPerc(batch.getCGSTPerc());
        salesLineEntity.setCGSTTaxCode(batch.getCGSTTaxCode());
        salesLineEntity.setExpiry(batch.getExpDate());
        salesLineEntity.setIGSTPerc(batch.getIGSTPerc());
        salesLineEntity.setIGSTTaxCode(batch.getIGSTTaxCode());
        salesLineEntity.setInventBatchId(batch.getBatchNo());
        salesLineEntity.setMRP(batch.getMRP());
        salesLineEntity.setNetAmount(batch.getPrice());
        salesLineEntity.setNetAmountInclTax(batch.getMRP());
        salesLineEntity.setOriginalPrice(batch.getMRP());
        salesLineEntity.setPrice(batch.getPrice());
        salesLineEntity.setQty(batch.getREQQTY());
        salesLineEntity.setSGSTPerc(batch.getSGSTPerc());
        salesLineEntity.setSGSTTaxCode(batch.getSGSTTaxCode());
        salesLineEntity.setTax(batch.getTotalTax());
        salesLineEntity.setTaxAmount(batch.getTotalTax());
        salesLineEntity.setTotal(batch.getMRP());
        salesLineEntity.setTotalTax(batch.getTotalTax());
        salesLineEntity.setUnitPrice(batch.getMRP());
        salesLineEntity.setUnitQty(batch.getREQQTY());

        salesLineEntity.setModifyBatchId(batch.getPhysicalBatchID());

       /* List<PickPackReservation> reservationitems = new ArrayList<>();
        if(salesentity.size() >= Constant.getInstance().selected_position) {
            SalesLineEntity salesLineEntitylist = salesentity.get(Constant.getInstance().selected_position);
            PickPackReservation reservarionitem = new PickPackReservation();
            reservarionitem.setPickupQty(batch.getREQQTY());
            reservarionitem.setPickupInventBatchId(batch.getPhysicalBatchID());
            reservarionitem.setPickupItemId(salesLineEntitylist.getItemId());
            reservationitems.add(reservarionitem);
        }*/
        // customerDataResBean.setPickPackReservation(reservationitems);
        return salesLineEntity;
    }


}
