package com.apollopharmacy.mpospharmacistTest.ui.ordersummary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.custumpdf.PDFCreatorActivity;
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
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.EnglishNumberToWords;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.qrcode.QRGContents;
import com.apollopharmacy.mpospharmacistTest.utils.qrcode.QRGEncoder;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private int pageBreakCount = 0;
    private int shippingChargePackingCount = 0;
    private boolean duplicateCheckboxChecked = true;
    public static final String cambria = "src/main/res/font/cambriab.ttf";
    private final static int ITEXT_FONT_SIZE_EIGHT = 10;
    private final static int ITEXT_FONT_SIZE_TEN = 12;
    private final static int ITEXT_FONT_SIZE_SIX = 8;

    private final static int ITEXT_FONT_SIZE_SIX_6 = 6;

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
        if (mPresenter.getGlobalConfing().isISHBPStore() && mPresenter.isV1Flow()) {
            orderSummaryBinding.printDuplicateCheckbox.setChecked(true);
        } else {
            orderSummaryBinding.printDuplicateCheckbox.setChecked(false);
        }
        orderSummaryBinding.printDuplicateCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    duplicateCheckboxChecked = true;
                } else {
                    duplicateCheckboxChecked = false;
                }
            }
        });
        if (transactionRes != null) {
            orderSummaryBinding.setOrderDetails(transactionRes);
            mPresenter.setTransactionId(transactionRes.getTransactionId());
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

//        if (pdfModelResponse != null) {
//            if (orderSummaryBinding.layoutPdfPreview != null) {
//                orderSummaryBinding.layoutPdfPreview.removeAllViews();
//            }
//            createPDF(transactionId, orderSummaryBinding.layoutPdfPreview, pdfModelResponse, new PDFUtil.PDFUtilListener() {
//                @Override
//                public void pdfGenerationSuccess(File savedPDFFile) {
//                    Toast.makeText(OrderSummaryActivity.this, "PDF Created", Toast.LENGTH_SHORT).show();
//                    openPdf();
//                }
//
//                @Override
//                public void pdfGenerationFailure(Exception exception) {
//                    Toast.makeText(OrderSummaryActivity.this, "PDF NOT Created", Toast.LENGTH_SHORT).show();
//                }
//            });
//
////        Toast.makeText(getContext(), "Pdf api is successfull", Toast.LENGTH_SHORT ).show();
//
////        orderSummaryBinding.postesting.setText(pdfModelResponse.getSalesHeader().get(0).getBranch());
//            orderSummaryBinding.fssaino.setText(pdfModelResponse.getSalesHeader().get(0).getFssaino());
//            orderSummaryBinding.addressOne.setText(pdfModelResponse.getSalesHeader().get(0).getAddressOne());
//            orderSummaryBinding.addressTwo.setText(pdfModelResponse.getSalesHeader().get(0).getAddressTwo());
//            orderSummaryBinding.dLno.setText(pdfModelResponse.getSalesHeader().get(0).getDlno());
//            orderSummaryBinding.gstNo.setText(pdfModelResponse.getSalesHeader().get(0).getGstin());
//            orderSummaryBinding.phonenumberpdf.setText("PHONE:" + pdfModelResponse.getSalesHeader().get(0).getTelNo());
//            orderSummaryBinding.cgstin.setText(pdfModelResponse.getSalesHeader().get(0).getCgstin());
//
//
//            orderSummaryBinding.customerNamePdf.setText("Name: " + pdfModelResponse.getSalesHeader().get(0).getCustName());
//            orderSummaryBinding.custmobileNumberpdf.setText("Mobile No.:" + pdfModelResponse.getSalesHeader().get(0).getCustMobile());
//            orderSummaryBinding.billnoReceiptid.setText("Bill No.:" + pdfModelResponse.getSalesHeader().get(0).getReceiptId());
//            orderSummaryBinding.trmnlId.setText("TID : " + pdfModelResponse.getSalesHeader().get(0).getTerminalId());
//            orderSummaryBinding.corporate6711.setText(pdfModelResponse.getSalesHeader().get(0).getCorporate());
//            if (pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")) {
//                orderSummaryBinding.doctornamepdf.setText("Doctor :" + "--");
//            } else {
//                orderSummaryBinding.doctornamepdf.setText("Doctor :" + pdfModelResponse.getSalesHeader().get(0).getDoctorName());
//            }
//
//            if (pdfModelResponse.getSalesHeader().get(0).getCgstin() != null) {
//                orderSummaryBinding.cgstin.setText(pdfModelResponse.getSalesHeader().get(0).getCgstin());
//            } else {
//                orderSummaryBinding.cgstin.setText("--");
//            }
//            orderSummaryBinding.refnoPdf.setText("Ref No: " + pdfModelResponse.getSalesHeader().get(0).getRefNo());
//            orderSummaryBinding.billdatepdf.setText("Bill Date: " + pdfModelResponse.getSalesHeader().get(0).getTransDate());
//
//
//            if (pdfModelResponse != null) {
//                PdfAdapter pdfAdapter = new PdfAdapter(pdfModelResponse, getApplicationContext(), this);
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderSummaryActivity.this);
//                orderSummaryBinding.pdfRecyclerView.setLayoutManager(mLayoutManager);
//                orderSummaryBinding.pdfRecyclerView.setAdapter(pdfAdapter);
//            }
//
//
//            orderSummaryBinding.taxablevaluepdf.setText("TAXABLE VALUE: " + pdfModelResponse.getSalesLine().get(0).getTaxable());
////        orderSummaryBinding.cgstamtpdf.setText("CGstAMT:" +pdfModelResponse.getSalesLine().get(0).getCgs);
////        orderSummaryBinding.sgstamtpdf.setText();
//            orderSummaryBinding.grosspdf.setText("Gross: " + pdfModelResponse.getSalesHeader().get(0).getTotal());
//            orderSummaryBinding.disamtpdf.setText("DisAmt :" + pdfModelResponse.getSalesHeader().get(0).getDiscount());
//            orderSummaryBinding.dontaionpdf.setText("Donation: " + pdfModelResponse.getSalesHeader().get(0).getDonationAmount());
//            orderSummaryBinding.netamtpdf.setText("NetAmt: " + pdfModelResponse.getSalesHeader().get(0).getNetTotal());
//
//            orderSummaryBinding.costinwords.setText("Rupees " + EnglishNumberToWords.convert(Math.round(Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal()))) + " Only");
//            double cgstAmount = 0.0;
//            for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
//                if (pdfModelResponse.getSalesLine().get(i).getMrp() != null
//                        && !pdfModelResponse.getSalesLine().get(i).getMrp().isEmpty()
//                        && pdfModelResponse.getSalesLine().get(i).getQty() != null
//                        && !pdfModelResponse.getSalesLine().get(i).getQty().isEmpty()
//                        && pdfModelResponse.getSalesLine().get(i).getCGSTPer() != null
//                        && !pdfModelResponse.getSalesLine().get(i).getCGSTPer().isEmpty()) {
//                    cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);
//
//                }
//            }
//            orderSummaryBinding.cgstamtpdf.setText("CGstAMT : " + String.valueOf(cgstAmount));
//            double sgstAmount = 0.0;
//            for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
//                if (pdfModelResponse.getSalesLine().get(i).getMrp() != null
//                        && !pdfModelResponse.getSalesLine().get(i).getMrp().isEmpty()
//                        && pdfModelResponse.getSalesLine().get(i).getQty() != null
//                        && !pdfModelResponse.getSalesLine().get(i).getQty().isEmpty()
//                        && pdfModelResponse.getSalesLine().get(i).getSGSTPer() != null
//                        && !pdfModelResponse.getSalesLine().get(i).getSGSTPer().isEmpty()) {
//                    sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);
//
//                }
//            }
//            orderSummaryBinding.sgstamtpdf.setText("SGstAmt: " + String.valueOf(sgstAmount));
//            orderSummaryBinding.cinpdf.setText("CIN : U52500TN2016PLC111328");
//            orderSummaryBinding.registeredoffcaddress.setText("Registered Office:No.19 Bishop Garden, Raja Annamalaipuram,Chennai-600028");
//            orderSummaryBinding.adminoffcpdf.setText("Admin Office : (For all correspondence) Ali Towers,IIIrd Floor,No 55,Greams Road, Chennai-600006.");
//
//
////            new Handler().postDelayed(() -> {
////                if (!isActivityFinished)
////                    onDownloadPdfButton();
////            }, 2000);
//        }
    }


    @Override
    public void onDownloadPdfButton() {
        if (isStoragePermissionGranted()) {
            openPdf();
//            Log.d("size", "" + orderSummaryBinding.pflayout.getWidth() + " " + orderSummaryBinding.pflayout.getWidth());
//            bitmap = LoadBitmap(orderSummaryBinding.pflayout, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight());
//            createPdf();
        }
    }

//    private Bitmap LoadBitmap(View v, int width, int height) {
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        v.draw(canvas);
//        return bitmap;
//    }

//    private void createPdf() {
//
//        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        //  Display display = wm.getDefaultDisplay();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
////        float hight = displaymetrics.heightPixels;
////        float width = displaymetrics.widthPixels;
////
////        int convertHighet = (int) hight, convertWidth = (int) width;
//
//        PdfDocument document = new PdfDocument();
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1800, 1080, 1).create();
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//
//        Paint paint = new Paint();
//        canvas.drawPaint(paint);
//
//        bitmap = Bitmap.createScaledBitmap(bitmap, 1800, 1080, true);
//
//        paint.setColor(Color.BLUE);
//        canvas.drawBitmap(bitmap, 0, 0, null);
//        document.finishPage(page);
//
//        // write the document content
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        File file = new File(pdfPath, transactionId.concat(".pdf"));
//        try {
//            document.writeTo(new FileOutputStream(file));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }////////////////////
//
//        // close the document
//        document.close();
////        Toast.makeText(this, "successfully pdf created", Toast.LENGTH_SHORT).show();
//        openPdf();
//
//    }

    private void openPdf() {

//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
        String fileName = transactionId + ".pdf";
        File file = FileUtil.getFilePath(fileName, this, "mposprintbill");

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
//                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
                String fileName = transactionId + ".pdf";
                File file = FileUtil.getFilePath(fileName, OrderSummaryActivity.this, "mposprintbill");

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

        @Override
        public void onFinish() {
            orderSummaryBinding.printBill.setVisibility(View.GONE);
            super.onFinish();
        }
    };

    @Override
    public void onFailurePdfResponse(PdfModelResponse body) {

    }

    @Override
    public void onClickBillPrint() {
        if (pdfModelResponse != null) {
            try {
                createPdf();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        if (isStoragePermissionGranted()) {
////            Log.d("size", "" + orderSummaryBinding.pflayout.getWidth() + " " + orderSummaryBinding.pflayout.getWidth());
////            bitmap = LoadBitmap(orderSummaryBinding.pflayout, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight());
////            createPdf();
//            openPdf();
//        }
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
//            createPdf();
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    protected PDFHeaderView getHeaderView(int forPage, PdfModelResponse pdfModelResponse) {
        PDFHeaderView headerView = new PDFHeaderView(getApplicationContext());

        PDFHorizontalView horizontalView = new PDFHorizontalView(getApplicationContext());

        PDFVerticalView verticalView = new PDFVerticalView(getApplicationContext());
        LinearLayout.LayoutParams verticalLayoutParam = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        verticalLayoutParam.setMargins(0, 10, 0, 0);

        PDFHorizontalView pdfHorizontalView1n = new PDFHorizontalView(getApplicationContext());
        LinearLayout.LayoutParams horizontalLayoutParam1n = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontalLayoutParam1n.setMargins(0, 0, 0, 0);

        PDFTextView pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("Apollo Pharmacy-");
        pdfTextView1.setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        pdfHorizontalView1n.addView(pdfTextView1);

        PDFTextView pdfTextView2n = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText(pdfModelResponse.getSalesHeader().get(0).getBranch());
        pdfTextView2n.setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambriab));
        pdfHorizontalView1n.setLayout(horizontalLayoutParam1n);
        pdfHorizontalView1n.getView().setGravity(Gravity.CENTER_VERTICAL);
        pdfHorizontalView1n.addView(pdfTextView2n);

        verticalView.addView(pdfHorizontalView1n);


        PDFTextView pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText(pdfModelResponse.getSalesHeader().get(0).getAddressOne()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView.addView(pdfTextView2);
        PDFTextView pdfTextView3 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText(pdfModelResponse.getSalesHeader().get(0).getAddressTwo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView.addView(pdfTextView3);
        PDFTextView pdfTextView4 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("PHONE:" + pdfModelResponse.getSalesHeader().get(0).getTelNo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView.addView(pdfTextView4);
        verticalView.setLayout(verticalLayoutParam);
        verticalView.getView().setGravity(Gravity.CENTER_VERTICAL);
        horizontalView.addView(verticalView);

        PDFVerticalView verticalView2 = new PDFVerticalView(getApplicationContext());
        LinearLayout.LayoutParams headerImageLayoutParam = new LinearLayout.LayoutParams(
                100,
                100, 0);
        PDFImageView imageView = new PDFImageView(getApplicationContext());
        imageView.setImageScale(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.apollo_circle_logo);
        headerImageLayoutParam.setMargins(0, 0, 0, 0);
        verticalView2.addView(imageView);
        verticalView2.setLayout(headerImageLayoutParam);
        verticalView2.getView().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        horizontalView.addView(verticalView2);

        PDFVerticalView verticalView3 = new PDFVerticalView(getApplicationContext());
        verticalLayoutParam.setMargins(0, 10, 0, 0);
        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("FSSAI NO : " + pdfModelResponse.getSalesHeader().get(0).getFssaino()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView1);
        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("D.L.NO:" + pdfModelResponse.getSalesHeader().get(0).getDlno()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView2);
        pdfTextView3 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("GST NO:" + pdfModelResponse.getSalesHeader().get(0).getGstin()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalView3.addView(pdfTextView3);
        pdfTextView4 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        if (pdfModelResponse.getSalesHeader().get(0).getCgstin() != null) {
            pdfTextView4.setText("C.GSTIN:" + pdfModelResponse.getSalesHeader().get(0).getCgstin()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
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
        LinearLayout.LayoutParams verticalLayoutParamSample = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        verticalLayoutParamSample.setMargins(0, 10, 0, 0);


        LinearLayout.LayoutParams verticalLayoutParam1 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        verticalLayoutParam1.setMargins(0, 10, 0, 0);


        PDFTextView pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        PDFTextView pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);

        PDFHorizontalView horizontalViewFisrtLine = new PDFHorizontalView(getApplicationContext());
        LinearLayout.LayoutParams horizontalViewFisrtLine1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontalViewFisrtLine1.setMargins(0, 0, 0, 0);


        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView1.setPadding(0, 0, 0, 0);
        pdfTextView1.setText("Name: " + pdfModelResponse.getSalesHeader().get(0).getCustName()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        horizontalViewFisrtLine.addView(pdfTextView1);

        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView2.setPadding(10, 0, 0, 0);
        pdfTextView2.setText("Mobile No.:" + pdfModelResponse.getSalesHeader().get(0).getCustMobile()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        horizontalViewFisrtLine.addView(pdfTextView2);


        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView1.setPadding(10, 0, 0, 0);
        pdfTextView1.setText("Bill No.:" + pdfModelResponse.getSalesHeader().get(0).getReceiptId()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        horizontalViewFisrtLine.addView(pdfTextView1);


        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView1.setPadding(10, 0, 0, 0);
        pdfTextView1.setText(pdfModelResponse.getSalesHeader().get(0).getCorporate()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        horizontalViewFisrtLine.addView(pdfTextView1);


        horizontalViewFisrtLine.setLayout(horizontalViewFisrtLine1);
        verticalView1.addView(horizontalViewFisrtLine);
//        verticalView1.setLayout(verticalLayoutParamSample);
//        horizontalView.addView(verticalView1);
//        pdfBody.addView(horizontalView);


        PDFHorizontalView horizontalViewSecondLine = new PDFHorizontalView(getApplicationContext());
        LinearLayout.LayoutParams horizontalViewSecondLine1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontalViewSecondLine1.setMargins(0, 5, 0, 0);


        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView2.setPadding(0, 0, 0, 0);
        if (pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")) {
            pdfTextView2.setText("Doctor :" + "--").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        } else {
            pdfTextView2.setText("Doctor :" + pdfModelResponse.getSalesHeader().get(0).getDoctorName()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        }
        horizontalViewSecondLine.addView(pdfTextView2);

        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView2.setPadding(40, 0, 0, 0);
        pdfTextView2.setText("Ref No: " + pdfModelResponse.getSalesHeader().get(0).getRefNo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        horizontalViewSecondLine.addView(pdfTextView2);


        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView2.setPadding(10, 0, 0, 0);
        pdfTextView2.setText("TID : " + pdfModelResponse.getSalesHeader().get(0).getTerminalId()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        horizontalViewSecondLine.addView(pdfTextView2);


        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        pdfTextView2.setPadding(15, 0, 0, 0);
        pdfTextView2.setText("Bill Date: " + pdfModelResponse.getSalesHeader().get(0).getTransDate()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        horizontalViewSecondLine.addView(pdfTextView2);


        horizontalViewSecondLine.setLayout(horizontalViewSecondLine1);
        verticalView1.addView(horizontalViewSecondLine);
        verticalView1.setLayout(verticalLayoutParamSample);
        horizontalView.addView(verticalView1);
        pdfBody.addView(horizontalView);

//
//        PDFTextView pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
//                .setText("Name: " + pdfModelResponse.getSalesHeader().get(0).getCustName()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        verticalView1.addView(pdfTextView1);
//        PDFTextView pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
//        if (pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")) {
//            pdfTextView2.setText("Doctor :" + "--").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        } else {
//            pdfTextView2.setText("Doctor :" + pdfModelResponse.getSalesHeader().get(0).getDoctorName()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        }
//        verticalView1.addView(pdfTextView2);
//        verticalView1.setLayout(verticalLayoutParam1);
//        verticalView1.getView().setGravity(Gravity.CENTER_VERTICAL);
//        horizontalView.addView(verticalView1);
//
//        PDFVerticalView verticalView2 = new PDFVerticalView(getApplicationContext());
//        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
//                .setText("Mobile No.:" + pdfModelResponse.getSalesHeader().get(0).getCustMobile()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        verticalView2.addView(pdfTextView1);
//        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
//                .setText("Ref No: " + pdfModelResponse.getSalesHeader().get(0).getRefNo()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        verticalView2.addView(pdfTextView2);
//        verticalView2.setLayout(verticalLayoutParam1);
//        verticalView2.getView().setGravity(Gravity.CENTER_VERTICAL);
//        horizontalView.addView(verticalView2);
//
//        PDFVerticalView verticalView3 = new PDFVerticalView(getApplicationContext());
//        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
//                .setText("Bill No.:" + pdfModelResponse.getSalesHeader().get(0).getReceiptId()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        verticalView3.addView(pdfTextView1);
//        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
//                .setText("TID : " + pdfModelResponse.getSalesHeader().get(0).getTerminalId()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        verticalView3.addView(pdfTextView2);
//        verticalView3.setLayout(verticalLayoutParam1);
//        verticalView3.getView().setGravity(Gravity.CENTER_VERTICAL);
//        horizontalView.addView(verticalView3);
//
//
//        PDFVerticalView verticalView4 = new PDFVerticalView(getApplicationContext());
//        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
//                .setText(pdfModelResponse.getSalesHeader().get(0).getCorporate()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        verticalView4.addView(pdfTextView1);
//        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
//                .setText("Bill Date: " + pdfModelResponse.getSalesHeader().get(0).getTransDate()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        verticalView4.addView(pdfTextView2);
//
//        verticalView4.setLayout(verticalLayoutParam1);
//        verticalView4.getView().setGravity(Gravity.CENTER_VERTICAL);
//        horizontalView.addView(verticalView4);
//
//        pdfBody.addView(horizontalView);


        PDFLineSeparatorView lineSeparatorView2 = new PDFLineSeparatorView(getApplicationContext()).setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1, 0);
        layoutParams.setMargins(0, 5, 0, 5);
        lineSeparatorView2.setLayout(layoutParams);
        pdfBody.addView(lineSeparatorView2);


        int[] widthPercent = {17, 4, 5, 2, 10, 1, 7, 1, 13, 1, 7, 1, 7, 1, 7, 1, 9, 1, 5};
        //  int[] widthPercent = {18, 4, 5, 2, 10, 2, 6, 1, 10, 1, 8, 2, 6, 8, 2, 8, 7}; // Sum should be equal to 100%
        String[] textInTable = {"Product Name", "", "SCH", "", "HSNCODE", "", "Mfg", "", "BATCH", "", "EXPIRY", "", "Qty", "", "RATE", "", "AMOUNT", "", "GST%"};


        PDFTableView.PDFTableRowView tableHeader = new PDFTableView.PDFTableRowView(getApplicationContext());
        for (String s : textInTable) {
            PDFTextView pdfTextView = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
            pdfTextView.setText(s).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
            tableHeader.addToRow(pdfTextView);
        }

        // do not modify table first row
        PDFTableView.PDFTableRowView tableRowView1 = new PDFTableView.PDFTableRowView(getApplicationContext());
//        for (String s : textInTable) {
//            PDFTextView pdfTextView = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
////            pdfTextView.setText( s);
//            tableRowView1.addToRow(pdfTextView);
//        }

        PDFTableView tableView = new PDFTableView(getApplicationContext(), tableHeader, tableRowView1);

        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            PdfModelResponse.SalesLine salesLine = pdfModelResponse.getSalesLine().get(i);
            if (salesLine.getIsShippingCharge() == 0 && salesLine.getIsCircleCharge() == 0) {
                // Create 10 rows
                PDFTableView.PDFTableRowView tableRowView = new PDFTableView.PDFTableRowView(getApplicationContext());
                for (int j = 0; j < textInTable.length; j++) {
                    PDFTextView pdfTextView = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
                    if (j == 0) {

//                        pdfTextView.getView().setSingleLine(true);
//                        pdfTextView.getView().setMaxLines(1);
                        String itemName = salesLine.getItemName().replace(" ", "\u00A0");
                        pdfTextView.setText(itemName).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 1) {
                    } else if (j == 2) {
                        pdfTextView.setText(salesLine.getSch()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 3) {
                    } else if (j == 4) {
                        pdfTextView.setText(salesLine.getHSNCode()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 5) {
                    } else if (j == 6) {
                        pdfTextView.setText(salesLine.getManufacturer()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 7) {
                    } else if (j == 8) {
                        String batchNo = "";
                        if (salesLine.getBatchNo().length() > 12) {
                            batchNo = salesLine.getBatchNo().substring(0, salesLine.getBatchNo().indexOf(11));
                            batchNo = batchNo + "\n" + salesLine.getBatchNo().substring(12);
                        } else {
                            batchNo = salesLine.getBatchNo();
                        }
                        pdfTextView.setText(batchNo).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 9) {
                    } else if (j == 10) {
                        if (salesLine.getExpDate() != null && salesLine.getExpDate().length() > 5) {
                            String expDate[] = salesLine.getExpDate().substring(2, 7).split("-");
                            pdfTextView.setText(expDate[1] + "-" + expDate[0]).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                        } else {
                            pdfTextView.setText("-").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                        }
                    } else if (j == 11) {
                    } else if (j == 12) {
                        pdfTextView.setText(salesLine.getQty()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 13) {
                    } else if (j == 14) {
                        pdfTextView.setText(salesLine.getMrp()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 15) {
                    } else if (j == 16) {
                        pdfTextView.setText(salesLine.getLineTotAmount()).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    } else if (j == 17) {
                    } else {
                        Double gst = Double.parseDouble(salesLine.getSGSTPer()) + Double.parseDouble(salesLine.getCGSTPer());

                        pdfTextView.setText(String.format("%.02f", gst)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                    }
                    tableRowView.addToRow(pdfTextView);
                }
                tableView.addRow(tableRowView);
            }
        }
        tableView.setColumnWidth(widthPercent);
        pdfBody.addView(tableView);

//for e shipping charge and circle member items
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            PdfModelResponse.SalesLine salesLine = pdfModelResponse.getSalesLine().get(i);
            if (salesLine.getIsCircleCharge() == 1 || salesLine.getIsShippingCharge() == 1) {
                Double gst = Double.parseDouble(salesLine.getSGSTPer()) + Double.parseDouble(salesLine.getCGSTPer());
                String itemDetailsInALine = salesLine.getItemName() + ":" + salesLine.getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";

                PDFHorizontalView shippingCharge = new PDFHorizontalView(getApplicationContext());
                PDFTextView shippingChargeText = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                        .setText(itemDetailsInALine).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
                shippingChargeText.setLayout(verticalLayoutParam1);
                shippingChargeText.getView().setGravity(Gravity.CENTER);
                shippingCharge.addView(shippingChargeText);
                pdfBody.addView(shippingCharge);
            }
        }


        PDFLineSeparatorView lineSeparatorView4 = new PDFLineSeparatorView(getApplicationContext()).setBackgroundColor(Color.BLACK);
        lineSeparatorView4.setLayout(layoutParams);
        pdfBody.addView(lineSeparatorView4);

        PDFHorizontalView taxbleView = new PDFHorizontalView(getApplicationContext());
        double taxbleValue = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null
                    && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()) {
                taxbleValue = taxbleValue + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable());

            }
        }
        LinearLayout.LayoutParams verticalLayoutParam8 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        verticalLayoutParam8.setMargins(0, 0, 0, 0);

        PDFTextView taxableValue = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("TAXABLE VALUE: " + String.format("%.02f", taxbleValue)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue.setLayout(verticalLayoutParam8);
        taxableValue.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView.addView(taxableValue);
        PDFTextView taxableValue1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        double cgstAmount = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null
                    && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()

                    && pdfModelResponse.getSalesLine().get(i).getCGSTPer() != null
                    && !pdfModelResponse.getSalesLine().get(i).getCGSTPer().isEmpty()) {
//                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);
                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);

            }
        }


        taxableValue1.setText("CGstAMT : " + String.format("%.02f", cgstAmount)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue1.setLayout(verticalLayoutParam8);
        taxableValue1.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView.addView(taxableValue1);
        PDFTextView taxableValue2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        double sgstAmount = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null
                    && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()
                    && pdfModelResponse.getSalesLine().get(i).getSGSTPer() != null
                    && !pdfModelResponse.getSalesLine().get(i).getSGSTPer().isEmpty()) {
                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);


//                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);

            }
        }
        taxableValue2.setText("SGstAmt: " + String.format("%.02f", sgstAmount)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue2.setLayout(verticalLayoutParam8);
        taxableValue2.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView.addView(taxableValue2);
        pdfBody.addView(taxbleView);


        PDFHorizontalView taxbleView2 = new PDFHorizontalView(getApplicationContext());

        double gross = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getTotal());
        PDFTextView taxableValue5 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("Gross: " + String.format("%.02f", gross)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue5.setLayout(verticalLayoutParam8);
        taxableValue5.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue5);

        double discAmt = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getDiscount());
        PDFTextView taxableValue3 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("DisAmt :" + String.format("%.02f", discAmt)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue3.setLayout(verticalLayoutParam8);
        taxableValue3.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue3);


        double donation = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getDonationAmount());
        PDFTextView taxableValue4 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("Donation: " + String.format("%.02f", donation)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue4.setLayout(verticalLayoutParam8);
        taxableValue4.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue4);

        double netAmout = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal());
        PDFTextView taxableValue6 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("NetAmt: " + String.format("%.02f", netAmout)).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        taxableValue6.setLayout(verticalLayoutParam8);
        taxableValue6.getView().setGravity(Gravity.CENTER_VERTICAL);
        taxbleView2.addView(taxableValue6);
        pdfBody.addView(taxbleView2);

        PDFLineSeparatorView lineSeparatorView5 = new PDFLineSeparatorView(getApplicationContext()).setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1, 0);
        layoutParams5.setMargins(0, 5, 0, 5);
        lineSeparatorView5.setLayout(layoutParams5);
        pdfBody.addView(lineSeparatorView5);


        PDFHorizontalView footerView = new PDFHorizontalView(getApplicationContext());
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams5.setMargins(0, 5, 0, 5);
        PDFTextView cinValue = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("CIN : U52500TN2016PLC111328").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        cinValue.setLayout(verticalLayoutParam1);
//        cinValue.getView().setGravity(Gravity.CENTER_VERTICAL);
        footerView.addView(cinValue);
        PDFTextView regsteredOffice = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        regsteredOffice.setPadding(20, 0, 0, 0);
        regsteredOffice.setText("Registered Office:No.19 Bishop Garden, Raja Annamalaipuram, Chennai-600028").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
//        regsteredOffice.setLayout(new LinearLayout.LayoutParams(
//                0,
//                LinearLayout.LayoutParams.WRAP_CONTENT, 2));
//        regsteredOffice.getView().setGravity(Gravity.CENTER_VERTICAL);
        footerView.addView(regsteredOffice);
        footerView.setLayout(layout);


        pdfBody.addView(footerView);


        PDFHorizontalView adminView = new PDFHorizontalView(getApplicationContext());
        PDFTextView adminOffice = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("Admin Office : (For all correspondence) Ali Towers,IIIrd Floor,No 55,Greams Road, Chennai-600006.").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        adminOffice.setLayout(verticalLayoutParam1);
        adminOffice.getView().setGravity(Gravity.CENTER_VERTICAL);
        adminView.addView(adminOffice);
        pdfBody.addView(adminView);

        PDFHorizontalView apolloWishesView = new PDFHorizontalView(getApplicationContext());
        PDFVerticalView verticalFooter = new PDFVerticalView(getApplicationContext());
        PDFTextView wishesTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("Rupees " + EnglishNumberToWords.convert(Math.round(Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal()))) + " Only").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        verticalFooter.addView(wishesTextView1);
        PDFTextView wishesTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        SpannableString word = new SpannableString("Wishes You Speedy Recovery");
        word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wishesTextView2.setText(word).setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambriab));
        verticalFooter.addView(wishesTextView2);
        verticalFooter.setLayout(verticalLayoutParam1);
        verticalFooter.getView().setGravity(Gravity.CENTER_VERTICAL);
        apolloWishesView.addView(verticalFooter);

        PDFVerticalView apolloWishesView2 = new PDFVerticalView(getApplicationContext());
        LinearLayout.LayoutParams verticalFooter2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        verticalFooter2.setMargins(0, 10, 0, 0);

        PDFHorizontalView forApolloPahramacyHorzontalView = new PDFHorizontalView(getApplicationContext());
        LinearLayout.LayoutParams forApolloPahramacyHorzontalViewMargins = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        forApolloPahramacyHorzontalViewMargins.setMargins(0, 0, 0, 0);


        pdfTextView1 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
        SpannableString word1 = new SpannableString("APOLLO PHARMACY");
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        word1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        pdfTextView1.setText("for ").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        forApolloPahramacyHorzontalView.addView(pdfTextView1);


        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL);
//        SpannableString word2 = new SpannableString("APOLLO PHARMACY");
//        StyleSpan boldSpan1 = new StyleSpan(Typeface.BOLD);
//        word1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        pdfTextView2.setText("APOLLO PHARMACY").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambriab));
        forApolloPahramacyHorzontalView.addView(pdfTextView2);
        apolloWishesView2.addView(forApolloPahramacyHorzontalView);


        pdfTextView2 = new PDFTextView(getApplicationContext(), PDFTextView.PDF_TEXT_SIZE.SMALL)
                .setText("Registered Pharmacist").setTextTypeface(ResourcesCompat.getFont(getContext(), R.font.cambria));
        apolloWishesView2.addView(pdfTextView2);
        apolloWishesView2.setLayout(verticalFooter2);
        apolloWishesView2.getView().setGravity(Gravity.CENTER_VERTICAL);
        apolloWishesView.addView(apolloWishesView2);
        pdfBody.addView(apolloWishesView);

        return pdfBody;
    }

    @Override
    protected PDFFooterView getFooterView(int forPage, PdfModelResponse pdfModelResponse) {
        return null;
    }

    public static Drawable CreateDashedLined() {
        ShapeDrawable sd = new ShapeDrawable(new RectShape());
        Paint fgPaintSel = sd.getPaint();
        fgPaintSel.setColor(Color.BLACK);
        fgPaintSel.setStyle(Paint.Style.STROKE);
        fgPaintSel.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
        return sd;
    }

    public void justify(final TextView textView) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = textView.getText().toString();

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        if (i == lineCount - 1) {
                            builder.append(new SpannableString(lineString));
                            break;
                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void createPdf() throws IOException {
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

//        File file = new File(pdfPath, "pdfdoc.pdf");
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), transactionId.concat(".pdf"));
        String fileName = transactionId + ".pdf";
        FileUtil.createFilePath(fileName, getContext(), "mposprintbill");
        PdfWriter writer = new PdfWriter(FileUtil.getFilePath(fileName, getContext(), "mposprintbill"));

//        OutputStream outputStream = new FileOutputStream(file);
//        PdfWriter writer = new PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new PdfDocument(writer);
        com.itextpdf.layout.Document document = new Document(pdfDocument, PageSize.A4);
        document.setMargins(15, 15, 15, 15);
        pageBreakCount = 0;
        shippingChargePackingCount = 0;
        createPdfPageWise(pdfDocument, document, false);
        document.close();
        if (isStoragePermissionGranted()) {
            openPdf();
        }
    }

    public static final String REGULAR =
            "res/font/gothic_regular.TTF";
    public static final String BOLD =
            "res/font/gothic_bold.TTF";

    private void createPdfPageWise(PdfDocument pdfDocument, Document document, boolean isDuplicate) throws IOException {
        // declaring variables for loading the fonts from asset
        byte[] fontByte, boldByte;
        AssetManager am;
        am = this.getAssets();
//the file name should be same as in your assets folder
//        try (InputStream inStream = am.open("font/cambria.ttf")) {
//            fontByte = IOUtils.toByteArray(inStream);
//        }
        FontProgram fontProgram =
                FontProgramFactory.createFont(REGULAR);
        FontProgram fontProgramBold =
                FontProgramFactory.createFont(BOLD);
//        try (InputStream inStream = am.open("font/cambriab.ttf")) {
//            boldByte = IOUtils.toByteArray(inStream);
//
//        }
//        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
        PdfFont bold = PdfFontFactory.createFont(fontProgramBold, PdfEncodings.WINANSI, true);
//        PdfFont bold = PdfFontFactory.createFont(boldByte, PdfEncodings.WINANSI, true);
//        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
////        PdfFont cam = PdfFontFactory.createFont(font_end, true);
//      PdfFont cam = PdfFontFactory.createFont("src\\main\\res\\font\\cambriab.ttf", true);
        float[] columnWidth1 = {98, 150, 150, 167};//580 - 30
//        float columnWidth1[] = {65, 165, 140, 190};
        Table table1 = new Table(columnWidth1);
        table1.setWidth(UnitValue.createPercentValue(100));
        table1.setFixedLayout();

        //table1.....row1.....
        Drawable apolloLogoDrawable = getDrawable(R.drawable.new_apollo_21);
        Bitmap apolloLogoBitMap = ((BitmapDrawable) apolloLogoDrawable).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        apolloLogoBitMap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitMapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitMapData1);
        Image image1 = new Image(imageData1);
        image1.scaleToFit(65, 65);
        image1.setHeight(65);


        Border border1 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
        table1.setBorder(border1);
        table1.addCell(new Cell(4, 1).add(image1).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Apollo Pharmacy - ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getBranch()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).add(new Paragraph(new Text(pdfModelResponse.getSalesHeader().get(0).getAddressOne()).setFontSize(ITEXT_FONT_SIZE_SIX))).add(new Paragraph(new Text(pdfModelResponse.getSalesHeader().get(0).getAddressTwo()).setFontSize(ITEXT_FONT_SIZE_SIX))).add(new Paragraph(new Text("PHONE: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getTelNo()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("FSSAI NO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getFssaino()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("D.L.NO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getDlno()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("GST NO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getGstin()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER).add(new Paragraph(new Text("C.GSTIN NO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCgstin()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));


        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Registered Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("No.19 Bishop Gerden, Raja Annamalaipuram, Chennai-600028").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("Admin Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("(For all correspondence) All towers, Floor No 55, Greams Road, Chennai-600006").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("CIN : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("U52500TN2016PLC111328").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        String duplicate = isDuplicate ? "Duplicate Copy of Invoice" : "";
        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text(duplicate).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(10)).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text("INVOICE").setFontSize(ITEXT_FONT_SIZE_EIGHT).setFont(bold)).setMarginLeft(10)).setBorder(Border.NO_BORDER));

        float[] columnWidth2 = {170, 120, 120, 170};// 580
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table2 = new Table(columnWidth2);
        Border border2 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
        table2.setBorder(border2);
//        table2.setBorder(new SolidBorder(1));
        table2.addCell(new Cell().add(new Paragraph(new Text("Name : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCustName()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(new Text("Mobile No. : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCustMobile()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(new Text("Bill No. : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getReceiptId()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(new Text("Corp: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getCorporate()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));

        float[] columnWidth3 = {190, 133, 102, 155};//580
//        float columnWidth3[] = {180, 130, 100, 150};
        Table table3 = new Table(columnWidth3);
        Border border3 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
        table3.setBorder(border3);
//        table3.setBorder(new SolidBorder(1));
        if (pdfModelResponse.getSalesHeader().get(0).getDoctorName().equalsIgnoreCase("")) {
            table3.addCell(new Cell().add(new Paragraph(new Text("Doctor : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("--").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        } else {
            table3.addCell(new Cell().add(new Paragraph(new Text("Doctor : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getDoctorName()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        }

        table3.addCell(new Cell().add(new Paragraph(new Text("Ref. NO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getRefNo()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().add(new Paragraph(new Text("TID : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getTerminalId()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().add(new Paragraph(new Text("Bill Date : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(pdfModelResponse.getSalesHeader().get(0).getTransDate()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));

        float[] columnWidth4 = {40, 30, 150, 20, 50, 35, 55, 55, 55, 50, 40};//580
//        float columnWidth4[] = {41, 31, 136, 21, 51, 36, 51, 51, 51, 51, 41};
        Table table4 = new Table(columnWidth4);
        Border border4 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table4.setBorder(border4);
        table4.addCell(new Cell().add(new Paragraph(new Text("Rack").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("QTY").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Product Name").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Sch").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("HSN Code").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Mfg").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Batch").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Expiry").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("MRP").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("Amount").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        table4.addCell(new Cell().add(new Paragraph(new Text("GST%").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
        for (int i = pageBreakCount; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 1 || pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 2) {
                shippingChargePackingCount++;
            } else {
                PdfModelResponse.SalesLine salesLine = pdfModelResponse.getSalesLine().get(i);
                pageBreakCount++;
                if (pdfModelResponse.getSalesLine().get(i).getRackId() != null && pdfModelResponse.getSalesLine().get(i).getRackId().length() > 7) {
                    table4.addCell(new Cell().add(new Paragraph(new Text(pdfModelResponse.getSalesLine().get(i).getRackId().substring(0, 5) + "..").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                } else {
                    table4.addCell(new Cell().add(new Paragraph(new Text(pdfModelResponse.getSalesLine().get(i).getRackId()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                }
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getQty()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                String itemName = salesLine.getItemName().replace(" ", "\u00A0");
                table4.addCell(new Cell().add(new Paragraph(new Text(itemName).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getSch()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getHSNCode()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getManufacturer()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                String batchNo = "";
                if (salesLine.getBatchNo().length() > 12) {
                    batchNo = salesLine.getBatchNo().substring(0, 11);
                    batchNo = batchNo + "\n" + salesLine.getBatchNo().substring(12);
                } else {
                    batchNo = salesLine.getBatchNo();
                }
                table4.addCell(new Cell().add(new Paragraph(new Text(batchNo).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                if (salesLine.getExpDate() != null && salesLine.getExpDate().length() > 5) {
                    String expDate[] = salesLine.getExpDate().substring(2, 7).split("-");
                    table4.addCell(new Cell().add(new Paragraph(new Text(expDate[1] + "-" + expDate[0]).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                } else {
                    table4.addCell(new Cell().add(new Paragraph(new Text("--").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                }
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getMrp()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getLineTotAmount()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                Double gst = Double.parseDouble(salesLine.getSGSTPer()) + Double.parseDouble(salesLine.getCGSTPer());
                table4.addCell(new Cell().add(new Paragraph(new Text(String.format("%.02f", gst)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
                if (pageBreakCount % 5 == 0) {
                    break;
                }
            }
        }
        float[] columnWidthEShippingPacking = {580};//580
        Table tableEShippingPacking = new Table(columnWidthEShippingPacking);
        Border borderEShippingPacking = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        tableEShippingPacking.setBorder(borderEShippingPacking);
        String eShippingPacking = "";
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 1) {
                if (eShippingPacking.isEmpty()) {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                } else {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = eShippingPacking + " | " + pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                }
            } else if (pdfModelResponse.getSalesLine().get(i).getIsShippingCharge() == 2) {
                if (eShippingPacking.isEmpty()) {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                } else {
                    Double gst = Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer()) + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer());
                    eShippingPacking = eShippingPacking + " | " + pdfModelResponse.getSalesLine().get(i).getItemName() + ":" + pdfModelResponse.getSalesLine().get(i).getMrp() + ", SAC:-" + ", GST:" + String.format("%.02f", gst) + "%";
                }
            }
        }
        tableEShippingPacking.addCell(new Cell(1, 1).add(new Paragraph(new Text(eShippingPacking).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setTextAlignment(TextAlignment.CENTER)).setBorder(border4));


        float[] columnWidth5 = {144, 170, 122, 144};//580
//        float columnWidth5[] = {140, 160, 120, 140};
        Table table5 = new Table(columnWidth5);
        Border border5 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table5.setBorder(border5);
//        table5.setBorder(new SolidBorder(1));
        double taxbleValue = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()) {
                taxbleValue = taxbleValue + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable());

            }
        }
        table5.addCell(new Cell().add(new Paragraph(new Text("Taxable Value : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", taxbleValue)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        double cgstAmount = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()

                    && pdfModelResponse.getSalesLine().get(i).getCGSTPer() != null && !pdfModelResponse.getSalesLine().get(i).getCGSTPer().isEmpty()) {
//                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);
                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);

            }
        }
        table5.addCell(new Cell().add(new Paragraph(new Text("CGST Amount: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", cgstAmount)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        double sgstAmount = 0.0;
        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty() && pdfModelResponse.getSalesLine().get(i).getSGSTPer() != null && !pdfModelResponse.getSalesLine().get(i).getSGSTPer().isEmpty()) {
                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);
                //                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);

            }
        }
        table5.addCell(new Cell().add(new Paragraph(new Text("SGST Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", sgstAmount)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        double gross = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getTotal());
        table5.addCell(new Cell().add(new Paragraph(new Text("Gross Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", gross)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));

        float[] columnWidth6 = {144, 170, 122, 144};//580
//        float columnWidth6[] = {140, 160, 120, 140};
        Table table6 = new Table(columnWidth6);
        Border border6 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table6.setBorder(border6);
//        table6.setBorder(new SolidBorder(1));
        table6.addCell(new Cell().add(new Paragraph(new Text("Donation: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("0.00").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
        table6.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER));
        //table6.addCell(new Cell().add(new Paragraph(new Text("*1 HC equal to 1 Rupee").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER));
        table6.addCell(new Cell(1, 2).add(new Paragraph(new Text("* You Saved Rs. " + pdfModelResponse.getSalesHeader().get(0).getDiscount()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(new DashedBorder(1)));
        //table6.addCell(new Cell(1, 2).add(new Paragraph(new Text("* You Saved Rs. " + pdfModelResponse.getSalesHeader().get(0).getDiscount() + "& Earned 50.35 HC's ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(new DashedBorder(1)));


        float[] columnWidth7 = {290, 290};//580
//        float columnWidth7[] = {280, 280};
        Table table7 = new Table(columnWidth7);
        Border border7 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table7.setBorder(border7);
//        table7.setBorder(new SolidBorder(1));
        double netAmout = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal());
        String rupeesInput = "\"Rupees\"";
        String onlyInput = "\"only\"";
        String rupees = rupeesInput.substring(0, rupeesInput.length() - 1);
        String only = onlyInput.substring(1);
//        System.out.println("Input: " + input);
//        System.out.println("Result: " + result);
        table7.addCell(new Cell().add(new Paragraph(new Text((rupees + " " + EnglishNumberToWords.convert(Math.round(Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal()))) + " " + only)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));
        table7.addCell(new Cell().add(new Paragraph(new Text("Paid Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", netAmout)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(150)).setBorder(Border.NO_BORDER));


        float[] columnWidth8 = {167, 123, 123, 167};//580
//        float columnWidth8[] = {160, 120, 120, 160};
        Table table8 = new Table(columnWidth8);
        Border border8 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
        table8.setBorder(border8);
//        table8.setBorder(new SolidBorder(1));

        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//        try {
//            encodeAsBitmap(pdfModelResponse).compress(Bitmap.CompressFormat.PNG, 100, stream2);
        QrCodeGeneration(pdfModelResponse, this).compress(Bitmap.CompressFormat.PNG, 100, stream2);
//        }
//        catch (WriterException e) {
//            e.printStackTrace();
//        }
        byte[] bitMapData2 = stream2.toByteArray();

        ImageData imageData2 = ImageDataFactory.create(bitMapData2);
        Image image2 = new Image(imageData2);
        image2.scaleToFit(70, 70);


        table8.addCell(new Cell().add(new Paragraph(new Text("Wishing You Speedy Recovery").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell(1, 1).add(new Paragraph(new Text("Scan QR Code For\nRefill/Reorder").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell(4, 1).add(image2.setMargins(0, 0, 0, 0).setPadding(0)).setPadding(0).setMargin(0).setBorder(Border.NO_BORDER));
        table8.addCell(new Cell().add(new Paragraph(new Text("For ").setFontSize(8).setFont(font)).add(new Text("APOLLO PHARMACY\n").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("Registered pharmacist").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));

        table8.addCell(new Cell().add(new Paragraph(new Text("\"QR Code was digitally displayed for the Customer at the time of the transaction\"").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setFont(font).setFont(font));


        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell(2, 1).add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(8).setFont(font)).add(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));

        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));
        table8.addCell(new Cell().add(new Paragraph(new Text("").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setFont(font).setFont(font));

        table8.addCell(new Cell(1, 4).add(new Paragraph(new Text("E & O.E Goods Once Sold Cannot Be Taken Back or Exchanges | INSULINS AND VACCINES WILL NOT BE TAKEN BACK | EMERGENCY CALL:1066 | Tollfree No: 1860-500-0101").setFontSize(ITEXT_FONT_SIZE_SIX_6))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER).setFont(font));


        document.add(table1);
        document.add(table2);
        document.add(table3);
        document.add(table4);
        if ((pageBreakCount + shippingChargePackingCount) == pdfModelResponse.getSalesLine().size()) {
            if (!eShippingPacking.isEmpty()) {
                document.add(tableEShippingPacking);
            }
            document.add(table5);
            document.add(table6);
            document.add(table7);
            document.add(table8);
        }

        if ((pageBreakCount + shippingChargePackingCount) != pdfModelResponse.getSalesLine().size()) {
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            createPdfPageWise(pdfDocument, document, isDuplicate);
        } else {
            if (!isDuplicate && duplicateCheckboxChecked) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                pageBreakCount = 0;
                shippingChargePackingCount = 0;
                createPdfPageWise(pdfDocument, document, true);
            }
        }
    }

    private Bitmap QrCodeGeneration(PdfModelResponse pdfModelResponse, Context context) {
        String qrCodeData = "CUSTOMERNAME: " + pdfModelResponse.getSalesHeader().get(0).getCustName() + "\nPHONE: " + pdfModelResponse.getSalesHeader().get(0).getCustMobile() + "\nBILL NO: " + pdfModelResponse.getSalesHeader().get(0).getReceiptId();
        for (PdfModelResponse.SalesLine salesLine : pdfModelResponse.getSalesLine()) {
            qrCodeData = qrCodeData + "\nITEMID: " + "- " + "QTY: " + salesLine.getQty();
        }
        Bitmap bitmap1 = null;
// below line is for getting
        // the windowmanager service.
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        QRGEncoder qrgEncoder = new QRGEncoder((String) qrCodeData, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            if (qrCodeData != null) {
//                 bitmapImg.setImageBitmap(bitmap);
                bitmap1 = bitmap;
            }
        } catch (com.google.zxing.WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }
        return bitmap1;
    }


}
