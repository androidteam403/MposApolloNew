package com.apollopharmacy.mpospharmacistTest.ui.ordersummary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.custumpdf.PDFCreatorActivity;
import com.apollopharmacy.mpospharmacistTest.custumpdf.utils.PDFUtil;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.PDFBody;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.PDFFooterView;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.PDFHeaderView;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.PDFTableView;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.basic.PDFHorizontalView;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.basic.PDFImageView;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.basic.PDFLineSeparatorView;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.basic.PDFTextView;
import com.apollopharmacy.mpospharmacistTest.custumpdf.views.basic.PDFVerticalView;
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
import com.apollopharmacy.mpospharmacistTest.utils.EnglishNumberToWords;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderSummaryActivity extends PDFCreatorActivity implements OrderSummaryMvpView {

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
    private boolean isActivityFinished;

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
        isActivityFinished = true;
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
            if (orderSummaryBinding.layoutPdfPreview != null) {
                orderSummaryBinding.layoutPdfPreview.removeAllViews();
            }
            createPDF(transactionId,orderSummaryBinding.layoutPdfPreview,pdfModelResponse, new PDFUtil.PDFUtilListener() {
                @Override
                public void pdfGenerationSuccess(File savedPDFFile) {
                    Toast.makeText(OrderSummaryActivity.this, "PDF Created", Toast.LENGTH_SHORT).show();
                    openPdf();
                }

                @Override
                public void pdfGenerationFailure(Exception exception) {
                    Toast.makeText(OrderSummaryActivity.this, "PDF NOT Created", Toast.LENGTH_SHORT).show();
                }
            });

//        Toast.makeText(getContext(), "Pdf api is successfull", Toast.LENGTH_SHORT ).show();

//        orderSummaryBinding.postesting.setText(pdfModelResponse.getSalesHeader().get(0).getBranch());
            orderSummaryBinding.fssaino.setText(pdfModelResponse.getSalesHeader().get(0).getFssaino());
            orderSummaryBinding.addressOne.setText(pdfModelResponse.getSalesHeader().get(0).getAddressOne());
            orderSummaryBinding.addressTwo.setText(pdfModelResponse.getSalesHeader().get(0).getAddressTwo());
            orderSummaryBinding.dLno.setText(pdfModelResponse.getSalesHeader().get(0).getDlno());
            orderSummaryBinding.gstNo.setText(pdfModelResponse.getSalesHeader().get(0).getGstin());
            orderSummaryBinding.phonenumberpdf.setText("PHONE:" + pdfModelResponse.getSalesHeader().get(0).getTelNo());
            orderSummaryBinding.cgstin.setText(pdfModelResponse.getSalesHeader().get(0).getCgstin());


            orderSummaryBinding.customerNamePdf.setText("Name: " + pdfModelResponse.getSalesHeader().get(0).getCustName());
            orderSummaryBinding.custmobileNumberpdf.setText("Mobile No.:" + pdfModelResponse.getSalesHeader().get(0).getCustMobile());
            orderSummaryBinding.billnoReceiptid.setText("Bill No.:" + pdfModelResponse.getSalesHeader().get(0).getReceiptId());
            orderSummaryBinding.trmnlId.setText("TID : " + pdfModelResponse.getSalesHeader().get(0).getTerminalId());
            orderSummaryBinding.corporate6711.setText(pdfModelResponse.getSalesHeader().get(0).getCorporate());
            if (pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")) {
                orderSummaryBinding.doctornamepdf.setText("Doctor :" + "--");
            } else {
                orderSummaryBinding.doctornamepdf.setText("Doctor :" + pdfModelResponse.getSalesHeader().get(0).getDoctorName());
            }

            if (pdfModelResponse.getSalesHeader().get(0).getCgstin() != null) {
                orderSummaryBinding.cgstin.setText(pdfModelResponse.getSalesHeader().get(0).getCgstin());
            } else {
                orderSummaryBinding.cgstin.setText("--");
            }
            orderSummaryBinding.refnoPdf.setText("Ref No: " + pdfModelResponse.getSalesHeader().get(0).getRefNo());
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

            orderSummaryBinding.costinwords.setText("Rupees " + EnglishNumberToWords.convert(Math.round(Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal()))) + " Only");
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


//            new Handler().postDelayed(() -> {
//                if (!isActivityFinished)
//                    onDownloadPdfButton();
//            }, 2000);
        }
    }


    @Override
    public void onDownloadPdfButton() {
        if (isStoragePermissionGranted()) {
            Log.d("size", "" + orderSummaryBinding.pflayout.getWidth() + " " + orderSummaryBinding.pflayout.getWidth());
            bitmap = LoadBitmap(orderSummaryBinding.pflayout, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight());
            createPdf();
        }
    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void createPdf() {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//          Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int hight = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
//
//        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight(), true);

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
//        Toast.makeText(this, "successfully pdf created", Toast.LENGTH_SHORT).show();
        openPdf();

    }

    private void openPdf() {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
        if (file.exists()) {
            //Button To start print

            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);

            PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
            String jobName = this.getString(R.string.app_name) + " Document";

            printManager.print(jobName, pda, builder.build());

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
////            Uri uri = Uri.fromFile(file);
//            intent.setDataAndType(photoURI, "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//
//            try {
//                startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(this, "No Application for pdf view", Toast.LENGTH_SHORT).show();
//            }
        } else {
//            Toast.makeText(this, "File not exist", Toast.LENGTH_SHORT).show();
        }
    }

    PrintDocumentAdapter pda = new PrintDocumentAdapter() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
            InputStream input = null;
            OutputStream output = null;
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));

                input = new FileInputStream(file);//"/storage/emulated/0/Documents/my-document-1656940186153.pdf"
                output = new FileOutputStream(destination.getFileDescriptor());
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf)) > 0) {
                    output.write(buf, 0, bytesRead);
                }
            } catch (Exception e) {

            } finally {
                try {
                    if (input != null) {
                        input.close();
                    } else {
                        Toast.makeText(OrderSummaryActivity.this, "FileInputStream getting null", Toast.LENGTH_SHORT).show();
                    }

                    if (output != null) {
                        output.close();
                    } else {
                        Toast.makeText(OrderSummaryActivity.this, "FileOutStream getting null", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }
            //int pages = computePageCount(newAttributes);
            PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(transactionId + ".pdf").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
            callback.onLayoutFinished(pdi, true);
        }

    };

    @Override
    public void onFailurePdfResponse(PdfModelResponse body) {

    }

    @Override
    public void onClickBillPrint() {
        if (isStoragePermissionGranted()) {
            Log.d("size", "" + orderSummaryBinding.pflayout.getWidth() + " " + orderSummaryBinding.pflayout.getWidth());
            bitmap = LoadBitmap(orderSummaryBinding.pflayout, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight());
            createPdf();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("size", "" + orderSummaryBinding.pflayout.getWidth() + " " + orderSummaryBinding.pflayout.getWidth());
            bitmap = LoadBitmap(orderSummaryBinding.pflayout, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight());
            createPdf();
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    protected PDFHeaderView getHeaderView(int forPage,PdfModelResponse pdfModelResponse) {
        PDFHeaderView headerView = new PDFHeaderView(getApplicationContext());

        PDFHorizontalView horizontalView = new PDFHorizontalView(getApplicationContext());

        PDFVerticalView verticalView = new PDFVerticalView(getApplicationContext());
        LinearLayout.LayoutParams verticalLayoutParam = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        verticalLayoutParam.setMargins(0,10,0,0);
        PDFTextView pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Apollo Pharmacy-"+pdfModelResponse.getSalesHeader().get(0).getBranch());
        pdfTextView1.setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView.addView(pdfTextView1);
        PDFTextView pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText(pdfModelResponse.getSalesHeader().get(0).getAddressOne()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView.addView(pdfTextView2);
        PDFTextView pdfTextView3 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText(pdfModelResponse.getSalesHeader().get(0).getAddressTwo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView.addView(pdfTextView3);
        PDFTextView pdfTextView4 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("PHONE:" + pdfModelResponse.getSalesHeader().get(0).getTelNo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView.addView(pdfTextView4);
        verticalView.setLayout(verticalLayoutParam);
        verticalView.getView().setGravity(Gravity.CENTER_VERTICAL);
        horizontalView.addView(verticalView);

        PDFVerticalView verticalView2 = new PDFVerticalView(getApplicationContext());
        LinearLayout.LayoutParams headerImageLayoutParam = new LinearLayout.LayoutParams(
                100,
                100,0);
        PDFImageView imageView = new PDFImageView(getApplicationContext());
        imageView.setImageScale(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.apollo_circle_logo);
        headerImageLayoutParam.setMargins(0, 0, 0, 0);
        verticalView2.addView(imageView);
        verticalView2.setLayout(headerImageLayoutParam);
        verticalView2.getView().setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        horizontalView.addView(verticalView2);

        PDFVerticalView verticalView3 = new PDFVerticalView(getApplicationContext());
        verticalLayoutParam.setMargins(0,10,0,0);
        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("FSSAI NO : "+ pdfModelResponse.getSalesHeader().get(0).getFssaino()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView1);
        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("D.L.NO:"+pdfModelResponse.getSalesHeader().get(0).getDlno()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView2);
        pdfTextView3 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("GST NO:"+pdfModelResponse.getSalesHeader().get(0).getGstin()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView3);
        pdfTextView4 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
        if (pdfModelResponse.getSalesHeader().get(0).getCgstin() != null) {
            pdfTextView4.setText("C.GSTIN:"+pdfModelResponse.getSalesHeader().get(0).getCgstin()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        } else {
            pdfTextView4.setText("--").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        }
        verticalView3.addView(pdfTextView4);
        verticalView3.setLayout(verticalLayoutParam);
        verticalView3.getView().setGravity(Gravity.CENTER_VERTICAL);
        horizontalView.addView(verticalView3);
        headerView.addView(horizontalView);

        PDFLineSeparatorView lineSeparatorView1 = new PDFLineSeparatorView(getApplicationContext()).setBackgroundColor(Color.BLACK);
        headerView.addView(lineSeparatorView1);

        return headerView;
    }

    @Override
    protected PDFBody getBodyViews(PdfModelResponse pdfModelResponse) {
        PDFBody pdfBody = new PDFBody();
        PDFHorizontalView horizontalView = new PDFHorizontalView(getApplicationContext());
        PDFVerticalView verticalView1 = new PDFVerticalView(getApplicationContext());
        LinearLayout.LayoutParams verticalLayoutParam1 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        verticalLayoutParam1.setMargins(0,10,0,0);
        PDFTextView pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Name: " + pdfModelResponse.getSalesHeader().get(0).getCustName()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView1.addView(pdfTextView1);
        PDFTextView pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
        if (pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")) {
            pdfTextView2.setText("Doctor :" + "--").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        } else {
            pdfTextView2.setText("Doctor :" + pdfModelResponse.getSalesHeader().get(0).getDoctorName()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        }
        verticalView1.addView(pdfTextView2);
        verticalView1.setLayout(verticalLayoutParam1);
        verticalView1.getView().setGravity(Gravity.CENTER_VERTICAL);
        horizontalView.addView(verticalView1);

        PDFVerticalView verticalView2 = new PDFVerticalView(getApplicationContext());
        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Mobile No.:" + pdfModelResponse.getSalesHeader().get(0).getCustMobile()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView2.addView(pdfTextView1);
        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Ref No: " + pdfModelResponse.getSalesHeader().get(0).getRefNo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView2.addView(pdfTextView2);
        verticalView2.setLayout(verticalLayoutParam1);
        verticalView2.getView().setGravity(Gravity.CENTER_VERTICAL);
        horizontalView.addView(verticalView2);

        PDFVerticalView verticalView3 = new PDFVerticalView(getApplicationContext());
        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Bill No.:" + pdfModelResponse.getSalesHeader().get(0).getReceiptId()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView1);
        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("TID : " + pdfModelResponse.getSalesHeader().get(0).getTerminalId()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView2);
        verticalView3.setLayout(verticalLayoutParam1);
        verticalView3.getView().setGravity(Gravity.CENTER_VERTICAL);
        horizontalView.addView(verticalView3);


        PDFVerticalView verticalView4 = new PDFVerticalView(getApplicationContext());
        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText(pdfModelResponse.getSalesHeader().get(0).getCorporate());
        verticalView4.addView(pdfTextView1);
        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Bill Date: " + pdfModelResponse.getSalesHeader().get(0).getTransDate()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView4.addView(pdfTextView2);

        verticalView4.setLayout(verticalLayoutParam1);
        verticalView4.getView().setGravity(Gravity.CENTER_VERTICAL);
        horizontalView.addView(verticalView4);

        pdfBody.addView(horizontalView);


        PDFLineSeparatorView lineSeparatorView2 = new PDFLineSeparatorView(getApplicationContext()).setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1, 0);
        layoutParams.setMargins(0,5,0,5);
        lineSeparatorView2.setLayout(layoutParams);
        pdfBody.addView(lineSeparatorView2);


        int[] widthPercent = {20, 7, 12, 8,10, 10, 8, 8,10, 7}; // Sum should be equal to 100%
        String[] textInTable = {"Product Name", "SCH", "HSNCODE", "Mfg","BATCH","EXPIRY","Qty","RATE","AMOUNT","GST%"};



        PDFTableView.PDFTableRowView tableHeader = new PDFTableView.PDFTableRowView(getApplicationContext());
        for (String s : textInTable) {
            PDFTextView pdfTextView = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
            pdfTextView.setText(s);
            tableHeader.addToRow(pdfTextView);
        }

        // do not modify table first row
        PDFTableView.PDFTableRowView tableRowView1 = new PDFTableView.PDFTableRowView(getApplicationContext());
        for (String s : textInTable) {
            PDFTextView pdfTextView = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
//            pdfTextView.setText( s);
            tableRowView1.addToRow(pdfTextView);
        }

        PDFTableView tableView = new PDFTableView(getApplicationContext(), tableHeader, tableRowView1);

        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            PdfModelResponse.SalesLine salesLine = pdfModelResponse.getSalesLine().get(i);
            // Create 10 rows
            PDFTableView.PDFTableRowView tableRowView = new PDFTableView.PDFTableRowView(getApplicationContext());
            for (int j = 0; j < textInTable.length; j++) {
                PDFTextView pdfTextView = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
                if(j==0){
                    pdfTextView.setText(salesLine.getItemName()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else if(j==1){
                    pdfTextView.setText(salesLine.getSch()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else if(j==2){
                    pdfTextView.setText(salesLine.getHSNCode()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else if(j==3){
                    pdfTextView.setText(salesLine.getManufacturer()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else if(j==4){
                    pdfTextView.setText(salesLine.getBatchNo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else if(j==5){
                    if (salesLine.getExpDate() != null && salesLine.getExpDate().length() > 5) {
                        pdfTextView.setText(salesLine.getExpDate().substring(5)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    }else {
                        pdfTextView.setText("-").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    }
                }else if(j==6){
                    pdfTextView.setText(salesLine.getQty()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else if(j==7){
                    pdfTextView.setText(salesLine.getMrp()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else if(j==8){
                    pdfTextView.setText(salesLine.getLineTotAmount()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }else {
                    pdfTextView.setText(String.valueOf(Double.parseDouble(salesLine.getSGSTPer()) + Double.parseDouble(salesLine.getCGSTPer()))).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                }
                tableRowView.addToRow(pdfTextView);
            }
            tableView.addRow(tableRowView);
        }
        tableView.setColumnWidth(widthPercent);
        pdfBody.addView(tableView);

        PDFLineSeparatorView lineSeparatorView4 = new PDFLineSeparatorView(getApplicationContext()).setBackgroundColor(Color.BLACK);
        pdfBody.addView(lineSeparatorView4);

        PDFHorizontalView taxbleView = new PDFHorizontalView(getApplicationContext());
        PDFTextView taxableValue = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("TAXABLE VALUE: " + pdfModelResponse.getSalesLine().get(0).getTaxable()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue.setLayout(verticalLayoutParam1);
        taxableValue.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView.addView(taxableValue);
        PDFTextView taxableValue1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
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
        taxableValue1.setText("CGstAMT : " + String.valueOf(cgstAmount)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue1.setLayout(verticalLayoutParam1);
        taxableValue1.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView.addView(taxableValue1);
        PDFTextView taxableValue2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
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
        taxableValue2.setText("SGstAmt: " + String.valueOf(sgstAmount)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue2.setLayout(verticalLayoutParam1);
        taxableValue2.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView.addView(taxableValue2);
        pdfBody.addView(taxbleView);


        PDFHorizontalView taxbleView2 = new PDFHorizontalView(getApplicationContext());
        PDFTextView taxableValue5 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Gross: " + pdfModelResponse.getSalesHeader().get(0).getTotal()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue5.setLayout(verticalLayoutParam1);
        taxableValue5.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue5);
        PDFTextView taxableValue3 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("DisAmt :" + pdfModelResponse.getSalesHeader().get(0).getDiscount()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue3.setLayout(verticalLayoutParam1);
        taxableValue3.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue3);
        PDFTextView taxableValue4 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Donation: " + pdfModelResponse.getSalesHeader().get(0).getDonationAmount()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue4.setLayout(verticalLayoutParam1);
        taxableValue4.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue4);
        PDFTextView taxableValue6 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("NetAmt: " + pdfModelResponse.getSalesHeader().get(0).getNetTotal()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue6.setLayout(verticalLayoutParam1);
        taxableValue6.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue6);
        pdfBody.addView(taxbleView2);

        PDFLineSeparatorView lineSeparatorView5 = new PDFLineSeparatorView(getApplicationContext()).setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1, 0);
        layoutParams5.setMargins(0,5,0,5);
        lineSeparatorView5.setLayout(layoutParams5);
        pdfBody.addView(lineSeparatorView5);


        PDFHorizontalView footerView = new PDFHorizontalView(getApplicationContext());
        PDFTextView cinValue = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("CIN : U52500TN2016PLC111328").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        cinValue.setLayout(verticalLayoutParam1);
        cinValue.getView().setGravity(Gravity.CENTER_VERTICAL);
        footerView.addView(cinValue);
        PDFTextView regsteredOffice = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Registered Office:No.19 Bishop Garden, Raja Annamalaipuram,\nChennai-600028").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        regsteredOffice.setLayout(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,2));
        regsteredOffice.getView().setGravity(Gravity.CENTER_VERTICAL);
        footerView.addView(regsteredOffice);

        pdfBody.addView(footerView);


        PDFHorizontalView adminView = new PDFHorizontalView(getApplicationContext());
        PDFTextView adminOffice = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Admin Office : (For all correspondence) Ali Towers,IIIrd Floor,No 55,Greams Road, Chennai-600006.").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        adminOffice.setLayout(verticalLayoutParam1);
        adminOffice.getView().setGravity(Gravity.CENTER_VERTICAL);
        adminView.addView(adminOffice);
        pdfBody.addView(adminView);

        PDFHorizontalView apolloWishesView = new PDFHorizontalView(getApplicationContext());
        PDFVerticalView verticalFooter = new PDFVerticalView(getApplicationContext());
        PDFTextView wishesTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Rupees " + EnglishNumberToWords.convert(Math.round(Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal()))) + " Only").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalFooter.addView(wishesTextView1);
        PDFTextView wishesTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
        SpannableString word = new SpannableString("Wishes You Speedy Recovery");
        word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wishesTextView2.setText(word).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria).defaultFromStyle(Typeface.BOLD));
        verticalFooter.addView(wishesTextView2);
        verticalFooter.setLayout(verticalLayoutParam1);
        verticalFooter.getView().setGravity(Gravity.CENTER_VERTICAL);
        apolloWishesView.addView(verticalFooter);

        PDFVerticalView apolloWishesView2 = new PDFVerticalView(getApplicationContext());
        LinearLayout.LayoutParams verticalFooter2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        verticalFooter2.setMargins(0,10,0,0);
        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P);
        SpannableString word1 = new SpannableString("APOLLO PHARMACY");
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        word1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        pdfTextView1.setText("for "+word1).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        apolloWishesView2.addView(pdfTextView1);
        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.P)
                .setText("Registered Pharmacist").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        apolloWishesView2.addView(pdfTextView2);
        apolloWishesView2.setLayout(verticalFooter2);
        apolloWishesView2.getView().setGravity(Gravity.CENTER_VERTICAL);
        apolloWishesView.addView(apolloWishesView2);
        pdfBody.addView(apolloWishesView);

        return pdfBody;
    }

    @Override
    protected PDFFooterView getFooterView(int forPage,PdfModelResponse pdfModelResponse) {
        return null;
    }
}
