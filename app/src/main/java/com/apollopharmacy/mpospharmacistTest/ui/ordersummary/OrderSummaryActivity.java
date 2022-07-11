package com.apollopharmacy.mpospharmacistTest.ui.ordersummary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOrderSummaryBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.LayoutpdfBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewMedicineInfoBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewPaymentInfoBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.adapter.PdfAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderSummaryActivity extends BaseActivity implements OrderSummaryMvpView {

    @Inject
    OrderSummaryMvpPresenter<OrderSummaryMvpView> mPresenter;
    ActivityOrderSummaryBinding orderSummaryBinding;
    String dirpath;
    private ArrayList<SaveRetailsTransactionRes.SalesLineEntity> medicineArrList = new ArrayList<>();
    private ArrayList<SaveRetailsTransactionRes.TenderLineEntity> paidAmountArr = new ArrayList<>();
    private CorporateModel.DropdownValueBean corporateEntity;
    ViewPaymentInfoBinding childView;
    SaveRetailsTransactionRes transactionRes;
    PaymentMethodModel paymentMethodModel;
    RelativeLayout relativeLayout;
    Bitmap bitmap;
    String transactionId;

    public static Intent getStartIntent(Context context, SaveRetailsTransactionRes saveRetailsTransactionRes, CorporateModel.DropdownValueBean corporateEntity, OrderPriceInfoModel orderPriceInfoModel, PaymentMethodModel paymentMethodModel) {
        Intent intent = new Intent(context, OrderSummaryActivity.class);
        intent.putExtra("transaction_details", saveRetailsTransactionRes);
        intent.putExtra("corporate_info", corporateEntity);
        intent.putExtra("order_data", orderPriceInfoModel);
        intent.putExtra("payment_mathod_data", paymentMethodModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        orderSummaryBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_summary);

        getActivityComponent().inject(this);
        mPresenter.onAttach(OrderSummaryActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
//        orderSummaryBinding.pflayout.setVisibility(View.GONE);
        orderSummaryBinding.siteName.setText(mPresenter.getStoreName());
        orderSummaryBinding.siteId.setText(mPresenter.getStoreId());
        orderSummaryBinding.terminalId.setText(mPresenter.getTerminalId());
        orderSummaryBinding.setCallback(mPresenter);
        paymentMethodModel = (PaymentMethodModel) getIntent().getSerializableExtra("payment_mathod_data");
        transactionRes = (SaveRetailsTransactionRes) getIntent().getSerializableExtra("transaction_details");

        transactionId = transactionRes.getTransactionId();


        if (transactionRes != null) {
            orderSummaryBinding.setOrderDetails(transactionRes);
        }
        if (getIntent() != null) {
            corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
            if (corporateEntity != null) {
                orderSummaryBinding.setCorporate(corporateEntity);
            }
        }

        mPresenter.downloadPdf(transactionId);


        medicineArrList.addAll(transactionRes.getSalesLine());
        orderSummaryBinding.setItemCount(medicineArrList.size());
        for (int i = 0; i < medicineArrList.size(); i++) {
            ViewMedicineInfoBinding childView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_medicine_info, orderSummaryBinding.medicineListLayout, false);
            childView.setMedicinebean(medicineArrList.get(i));
            orderSummaryBinding.medicineListLayout.addView(childView.getRoot());
        }
        SaveRetailsTransactionRes.TenderLineEntity lineEntity = new SaveRetailsTransactionRes.TenderLineEntity();
        lineEntity.setTenderName("Total Amount");
        lineEntity.setAmountTendered((transactionRes.getGrossAmount() - transactionRes.getDiscAmount()));
        paidAmountArr.add(lineEntity);
        paidAmountArr.addAll(transactionRes.getTenderLine());
        if (transactionRes.getRemainingamount() != 0) {
            SaveRetailsTransactionRes.TenderLineEntity remainAmountLineEntity = new SaveRetailsTransactionRes.TenderLineEntity();
            remainAmountLineEntity.setAmountTendered(paymentMethodModel.getBalanceAmount());
            remainAmountLineEntity.setVoid(transactionRes.getIsVoid());
            remainAmountLineEntity.setTenderName("Pay Back Amount");
            paidAmountArr.add(remainAmountLineEntity);
        }
        for (int i = 0; i < paidAmountArr.size(); i++) {
            if (!paidAmountArr.get(i).getIsVoid() && paidAmountArr.get(i).getAmountTendered() > 0) {
                childView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_payment_info, orderSummaryBinding.paidAmountLayout, false);
                childView.setPaidbean(paidAmountArr.get(i));
                orderSummaryBinding.paidAmountLayout.addView(childView.getRoot());
            } else if (paidAmountArr.get(i).getTenderName().equalsIgnoreCase("Pay Back Amount")) {
                childView = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_payment_info, orderSummaryBinding.paidAmountLayout, false);
                childView.setPaidbean(paidAmountArr.get(i));
                orderSummaryBinding.paidAmountLayout.addView(childView.getRoot());
            }
        }
        if (mPresenter.enablescreens()) {
            mPresenter.onMposTabApiCall();
            turnOnScreen();
        }
    }

    @Override
    public void onBackPressed() {
        onClickNewOrder();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        orderSummaryBinding.imageView.setVisibility(View.GONE);
    }

    @Override
    public void onBackOrderPressed() {
        onClickNewOrder();
    }

    private void onClickNewOrder() {
        Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().isPlaceNewOrder = false;
        Singletone.getInstance().isOrderCompleted = true;
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);


    }

    @Override
    public void onNewPlaceOrderClicked() {
       /* Singletone.getInstance().itemsArrayList.clear();
        Singletone.getInstance().isPlaceNewOrder = true;
        Singletone.getInstance().isOrderCompleted = false;

        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);*/

        Constant.getInstance().ordersource = transactionRes.getOrderSource();
        onClickNewOrder();


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
            if (!onPause) {
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
    }

    public void playListData(String filePath, int pos) {
        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        orderSummaryBinding.imageView.setImageBitmap(myBitmap);
        orderSummaryBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        orderSummaryBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
        idealScreen();
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
                orderSummaryBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        orderSummaryBinding.imageView.setVisibility(View.GONE);
//                        getSupportActionBar().show();
                        stopLooping = true;
                    }
                });

            }
        };
        startHandler();
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 180 * 1000);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    public void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    Dialog pdfDialog;
    LayoutpdfBinding layoutpdfBinding;
    Bitmap bm;

    @Nullable
    @Override
    public Context getContext() {
        return this;
    }

    PdfModelResponse pdfModelResponse;

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessPdfResponse(PdfModelResponse pdfModelResponse) {

        this.pdfModelResponse = pdfModelResponse;

        if (pdfModelResponse != null) {


//        Toast.makeText(getContext(), "Pdf api is successfull", Toast.LENGTH_SHORT ).show();

//        orderSummaryBinding.postesting.setText(pdfModelResponse.getSalesHeader().get(0).getBranch());
            orderSummaryBinding.fssaino.setText(pdfModelResponse.getSalesHeader().get(0).getFssaino());
            orderSummaryBinding.address.setText(pdfModelResponse.getSalesHeader().get(0).getAddress());
            orderSummaryBinding.dLno.setText(pdfModelResponse.getSalesHeader().get(0).getDlno());
            orderSummaryBinding.gstNo.setText(pdfModelResponse.getSalesHeader().get(0).getGstin());
            orderSummaryBinding.phonenumberpdf.setText("PHONE:" + pdfModelResponse.getSalesHeader().get(0).getTelNo());
            orderSummaryBinding.cgstin.setText(pdfModelResponse.getSalesHeader().get(0).getCgstin());


            orderSummaryBinding.customerNamePdf.setText("Name: " + pdfModelResponse.getSalesHeader().get(0).getCustName());
            orderSummaryBinding.custmobileNumberpdf.setText("Mobile No.:" + pdfModelResponse.getSalesHeader().get(0).getCustMobile());
            orderSummaryBinding.billnoReceiptid.setText("Bill No.:" + pdfModelResponse.getSalesHeader().get(0).getReceiptId());
            orderSummaryBinding.corporate6711.setText(pdfModelResponse.getSalesHeader().get(0).getCorporate());
            if(pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")){
                orderSummaryBinding.doctornamepdf.setText("Doctor :" + "--" );
            }else{
                orderSummaryBinding.doctornamepdf.setText("Doctor :" + pdfModelResponse.getSalesHeader().get(0).getDoctorName());
            }

            if (pdfModelResponse.getSalesHeader().get(0).getCgstin() != null) {
                orderSummaryBinding.cgstin.setText(pdfModelResponse.getSalesHeader().get(0).getCgstin());
            } else {
                orderSummaryBinding.cgstin.setText("--");
            }
            orderSummaryBinding.refnoPdf.setText("Ref No: " + pdfModelResponse.getSalesHeader().get(0).getRefNo() + "  TID : " + pdfModelResponse.getSalesHeader().get(0).getTerminalId());
            orderSummaryBinding.billdatepdf.setText("Bill Date: " + pdfModelResponse.getSalesHeader().get(0).getTransDate());


            if (pdfModelResponse != null) {
                PdfAdapter pdfAdapter = new PdfAdapter(pdfModelResponse, getApplicationContext(), this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderSummaryActivity.this);
                orderSummaryBinding.pdfRecyclerView.setLayoutManager(mLayoutManager);
                orderSummaryBinding.pdfRecyclerView.setAdapter(pdfAdapter);
            }


            orderSummaryBinding.taxablevaluepdf.setText("TAXABLE VALUE: " + pdfModelResponse.getSalesLine().get(0).getTaxable());
//        orderSummaryBinding.cgstamtpdf.setText("CGstAMT:" +pdfModelResponse.getSalesLine().get(0).getCgs);
//        orderSummaryBinding.sgstamtpdf.setText();
            orderSummaryBinding.grosspdf.setText("Gross: " + pdfModelResponse.getSalesHeader().get(0).getTotal());
            orderSummaryBinding.disamtpdf.setText("DisAmt :" + pdfModelResponse.getSalesHeader().get(0).getDiscount());
            orderSummaryBinding.dontaionpdf.setText("Donation: " + pdfModelResponse.getSalesHeader().get(0).getDonationAmount());
            orderSummaryBinding.netamtpdf.setText("NetAmt: " + pdfModelResponse.getSalesHeader().get(0).getNetTotal());
            double cgstAmount = 0.0;
            for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
                if (pdfModelResponse.getSalesLine().get(i).getMrp() != null
                        && !pdfModelResponse.getSalesLine().get(i).getMrp().isEmpty()
                        && pdfModelResponse.getSalesLine().get(i).getQty() != null
                        && !pdfModelResponse.getSalesLine().get(i).getQty().isEmpty()
                        && pdfModelResponse.getSalesLine().get(i).getCGSTPer() != null
                        && !pdfModelResponse.getSalesLine().get(i).getCGSTPer().isEmpty()) {
                    cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);

                }
            }
            orderSummaryBinding.cgstamtpdf.setText("CGstAMT : " + String.valueOf(cgstAmount));
            double sgstAmount = 0.0;
            for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
                if (pdfModelResponse.getSalesLine().get(i).getMrp() != null
                        && !pdfModelResponse.getSalesLine().get(i).getMrp().isEmpty()
                        && pdfModelResponse.getSalesLine().get(i).getQty() != null
                        && !pdfModelResponse.getSalesLine().get(i).getQty().isEmpty()
                        && pdfModelResponse.getSalesLine().get(i).getSGSTPer() != null
                        && !pdfModelResponse.getSalesLine().get(i).getSGSTPer().isEmpty()) {
                    sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);

                }
            }
            orderSummaryBinding.sgstamtpdf.setText("SGstAmt: " + String.valueOf(sgstAmount));
            orderSummaryBinding.cinpdf.setText("CIN : U52500TN2016PLC111328");
            orderSummaryBinding.registeredoffcaddress.setText("Registered Office:No.19 Bishop Garden, Raja Annamalaipuram,Chennai-600028");
            orderSummaryBinding.adminoffcpdf.setText("Admin Office : (For all correspondence) Ali Towers,IIIrd Floor,No 55,Greams Road, Chennai-600006.");


        }
    }


    @Override
    public void onDownloadPdfButton() {

        Log.d("size", "" + orderSummaryBinding.pflayout.getWidth() + " " + orderSummaryBinding.pflayout.getWidth());
        bitmap = LoadBitmap(orderSummaryBinding.pflayout, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight());
        createPdf();
    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void createPdf() {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        float hight = displaymetrics.heightPixels;
//        float width = displaymetrics.widthPixels;
//
//        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(2225, 1080, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, 2225, 1080, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, transactionId.concat(".pdf"));
        try {
            document.writeTo(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }////////////////////

        // close the document
        document.close();
        Toast.makeText(this, "successfully pdf created", Toast.LENGTH_SHORT).show();

        openPdf();

    }

    private void openPdf() {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);


//            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(photoURI, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No Application for pdf view", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailurePdfResponse(PdfModelResponse body) {

    }
}
