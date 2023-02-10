package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogLabelSizeBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentShippingLabelBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.adapter.ShippingLabelAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GeneratePdfbyFlidResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.utils.EnglishNumberToWords;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ShippingLabelFragment extends BaseFragment implements ShippingLabelMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {
    @Inject
    ShippingLabelMvpPresenter<ShippingLabelMvpView> mPresenter;
    private FragmentShippingLabelBinding shippingLabelBinding;
    private ShippingLabelAdapter shippingLabelAdapter;
    private DownloadManager manager;
    private GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse;
    private boolean isScanerBack;
    public static boolean isShippingLabelFragment = false;
    private final static int ITEXT_FONT_SIZE_EIGHT = 8;
    private final static int ITEXT_FONT_SIZE_TEN = 10;
    private final static int ITEXT_FONT_SIZE_SIX = 6;

    //Pagination
    public static List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseStatic;
    private List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseListTotal;
    private int startIndex = 0;
    private int endIndex = 100;
    int lastIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        shippingLabelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shipping_label, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ShippingLabelFragment.this);
        return shippingLabelBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        shippingLabelBinding.setCallback(mPresenter);
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Shipping Label");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.GONE);
        mPresenter.getJounalOnlineOrderTransactionApiCall();
        searchByFulfilmentId();
    }

    @Override
    public void onClickFilters() {

    }

    @Override
    public void onItemClick() {

    }

    @Override
    public void onClickStockAvailable(boolean isStockAvailableChecked) {

    }

    @Override
    public void onClicklabelSizeIcon() {
        Dialog paperLabelSizeDialog = new Dialog(getContext());
        DialogLabelSizeBinding dialogLabelSizeBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_label_size, null, false);
        paperLabelSizeDialog.setContentView(dialogLabelSizeBinding.getRoot());
        paperLabelSizeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (mPresenter.getPaperLabelSize().equalsIgnoreCase("4X6")) {
            dialogLabelSizeBinding.fourSixRadio.setChecked(true);
        } else if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A4")) {
            dialogLabelSizeBinding.aFourRadio.setChecked(true);
        }
        dialogLabelSizeBinding.labelSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.four_six_radio) {
                mPresenter.setPaperLabelSize("4X6");
                paperLabelSizeDialog.dismiss();
            } else if (checkedId == R.id.a_four_radio) {
                mPresenter.setPaperLabelSize("A4");
                paperLabelSizeDialog.dismiss();
            }
        });
        paperLabelSizeDialog.show();
    }

    @Override
    public void onClickRefresh() {
        mPresenter.getJounalOnlineOrderTransactionApiCall();
    }

    @Override
    public void onClickUnHold() {

    }

    @Override
    public void onClickRefreshPickerPackerBiller() {

    }

    @Override
    public void onSuccessGetJounalOnlineOrderTransactonApi(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList) {
        if (getJounalOnlineOrderTransactionsResponseList != null && getJounalOnlineOrderTransactionsResponseList.size() > 0) {


            getJounalOnlineOrderTransactionsResponseListTotal = getJounalOnlineOrderTransactionsResponseList.stream()
                    .filter(e -> !e.getPickupStatus())
                    .collect(Collectors.toList());
            if (getJounalOnlineOrderTransactionsResponseListTotal != null && getJounalOnlineOrderTransactionsResponseListTotal.size() >= 5000) {
                startIndex = 0;
                endIndex = 5000;
            } else {
                endIndex = getJounalOnlineOrderTransactionsResponseListTotal.size();
            }
            shippingLabelBinding.setIsNaxtPage(endIndex != getJounalOnlineOrderTransactionsResponseListTotal.size());
            shippingLabelBinding.setIsPrevtPage(startIndex != 0);

            List<GetJounalOnlineOrderTransactionsResponse> myLastPosts = getJounalOnlineOrderTransactionsResponseListTotal.subList(startIndex, endIndex);
            onSuccessGetJounalOnlineOrderTransactonApiText(myLastPosts);


//            for (int i = 0; i < getJounalOnlineOrderTransactionsResponseList.size(); i++) {
//                if (getJounalOnlineOrderTransactionsResponseList.get(i).getPickupStatus()) {
//                    getJounalOnlineOrderTransactionsResponseList.remove(i);
//                    i--;
//                }
//                if (i == getJounalOnlineOrderTransactionsResponseList.size() - 1) {
//                    shippingLabelAdapter = new ShippingLabelAdapter(getContext(), getJounalOnlineOrderTransactionsResponseList, this);
//                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                    shippingLabelBinding.shippingListRecycler.setLayoutManager(mLayoutManager1);
//                    shippingLabelBinding.shippingListRecycler.setItemAnimator(new DefaultItemAnimator());
//                    shippingLabelBinding.shippingListRecycler.setAdapter(shippingLabelAdapter);
//                    noShippinfLabelFound(getJounalOnlineOrderTransactionsResponseList.size());
//                    shippingLabelBinding.searchText.requestFocus();
//                }
//            }

        } else {
            noShippinfLabelFound(0);
        }
    }

    public void onSuccessGetJounalOnlineOrderTransactonApiText(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList) {
        if (getJounalOnlineOrderTransactionsResponseList != null && getJounalOnlineOrderTransactionsResponseList.size() > 0) {
//            for (int i = 0; i < getJounalOnlineOrderTransactionsResponseList.size(); i++) {
//                if (getJounalOnlineOrderTransactionsResponseList.get(i).getPickupStatus()) {
//                    getJounalOnlineOrderTransactionsResponseList.remove(i);
//                    i--;
//                }
//                if (i == getJounalOnlineOrderTransactionsResponseList.size() - 1) {
            shippingLabelAdapter = new ShippingLabelAdapter(getContext(), getJounalOnlineOrderTransactionsResponseList, this);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            shippingLabelBinding.shippingListRecycler.setLayoutManager(mLayoutManager1);
            shippingLabelBinding.shippingListRecycler.setItemAnimator(new DefaultItemAnimator());
            shippingLabelBinding.shippingListRecycler.setAdapter(shippingLabelAdapter);
            noShippinfLabelFound(getJounalOnlineOrderTransactionsResponseList.size());
            if (endIndex % 100 == 0) {
                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + (endIndex / 100));
            } else {
                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + ((startIndex / 100) + 1));
            }


            shippingLabelBinding.searchText.requestFocus();
//                }
//            }

//            List<GetJounalOnlineOrderTransactionsResponse> listOutput =
//                    getJounalOnlineOrderTransactionsResponseList.stream()
//                            .filter(e -> !e.getPickupStatus())
//                            .collect(Collectors.toList());
//            getJounalOnlineOrderTransactionsResponseList = listOutput;

//            shippingLabelAdapter = new ShippingLabelAdapter(getContext(), getJounalOnlineOrderTransactionsResponseList, this);
//            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//            shippingLabelBinding.shippingListRecycler.setLayoutManager(mLayoutManager1);
//            shippingLabelBinding.shippingListRecycler.setItemAnimator(new DefaultItemAnimator());
//            shippingLabelBinding.shippingListRecycler.setAdapter(shippingLabelAdapter);
//            noShippinfLabelFound(getJounalOnlineOrderTransactionsResponseList.size());
//            shippingLabelBinding.searchText.requestFocus();
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        } else {
            noShippinfLabelFound(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        shippingLabelBinding.searchText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
//        hideKeyboard();
    }

    private void searchByFulfilmentId() {
        shippingLabelBinding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String charString = editable.toString();
                if (charString.isEmpty()) {
//                    omsHeaderListTotal = mPresenter.getGlobalTotalOmsHeaderList();
                    startIndex = 0;
                    onSuccessGetJounalOnlineOrderTransactonApi(getJounalOnlineOrderTransactionsResponseStatic);
                } else {
                    List<GetJounalOnlineOrderTransactionsResponse> omsHeaderListTotalFilterTemp = new ArrayList<>();
                    for (GetJounalOnlineOrderTransactionsResponse row : getJounalOnlineOrderTransactionsResponseStatic) {
                        if (!omsHeaderListTotalFilterTemp.contains(row) && ((row.getRefno().toLowerCase().contains(charString.toLowerCase())) || (row.getBoxId().toLowerCase().contains(charString.toLowerCase())))) {
                            omsHeaderListTotalFilterTemp.add(row);
                        }

                    }
//                    List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotalFilteredTemp = new ArrayList<>();
//                    omsHeaderListTotal = omsHeaderListTotalFilterTemp;
                    startIndex = 0;
                    onSuccessGetJounalOnlineOrderTransactonApi(omsHeaderListTotalFilterTemp);
                }








//                if (editable.length() >= 2) {
//                    shippingLabelBinding.search.setVisibility(View.GONE);
//                    shippingLabelBinding.deleteCancel.setVisibility(View.VISIBLE);
//                    if (shippingLabelAdapter != null) {
//                        shippingLabelAdapter.getFilter().filter(editable);
//                    }
//
//                } else if (shippingLabelBinding.searchText.getText().toString().equals("")) {
//                    if (shippingLabelAdapter != null) {
//                        shippingLabelAdapter.getFilter().filter("");
//                    }
//                    shippingLabelBinding.deleteCancel.setVisibility(View.GONE);
//                    shippingLabelBinding.search.setVisibility(View.VISIBLE);
//                } else {
//                    if (shippingLabelAdapter != null) {
//                        shippingLabelAdapter.getFilter().filter("");
//                    }
//                }
            }
        });
    }

    @Override
    public void onClickPrintLabel(GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse) {
        this.getJounalOnlineOrderTransactionsResponse = getJounalOnlineOrderTransactionsResponse;
//        if (isStoragePermissionGranted()) {
//            mPresenter.generatePdfbyFlidApiCall(getJounalOnlineOrderTransactionsResponse.getRefno(), mPresenter.getPaperLabelSize());
//        }
        try {
            createPdf();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            Log.d("size", "" + orderSummaryBinding.pflayout.getWidth() + " " + orderSummaryBinding.pflayout.getWidth());
//            bitmap = LoadBitmap(orderSummaryBinding.pflayout, orderSummaryBinding.pflayout.getWidth(), orderSummaryBinding.pflayout.getHeight());
//            createPdf();
        }

    private void createPdf() throws IOException {
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

//        File file = new File(pdfPath, "pdfdoc.pdf");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "12345".concat(".pdf"));
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new PdfDocument(writer);
        com.itextpdf.layout.Document document = new Document(pdfDocument, PageSize.A5);
        document.setMargins(15, 15, 15, 15);
        createPdfPageWise(pdfDocument, document, false);
        document.close();
        if (isStoragePermissionGranted()) {
            openPdf();
        }
    }
    public static final String REGULAR =
            "res/font/cambria.ttf";
    public static final String BOLD =
            "res/font/cambriab.ttf";
    private void createPdfPageWise(PdfDocument pdfDocument, Document document, boolean isDuplicate) throws IOException {
        // declaring variables for loading the fonts from asset
        byte[] fontByte, boldByte;
        AssetManager am;
        am = this.getActivity().getAssets();
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

        float[] columnWidth1 = {100, 10, 200, 10, 260};//580
//        float columnWidth1[] = {65, 165, 140, 190};
        Table table1 = new Table(columnWidth1);

        //table1.....row1.....
        Drawable apolloLogoDrawable = getActivity().getDrawable(R.drawable.apollo_1525857827435);
        Bitmap apolloLogoBitMap = ((BitmapDrawable) apolloLogoDrawable).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        apolloLogoBitMap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitMapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitMapData1);
        Image image1 = new Image(imageData1);
        image1.scaleToFit(50, 50);
        image1.setHeight(50);


        Border border1 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
//        table1.setBorder(border1);
        table1.addCell(new Cell(4, 1).add(image1).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("APOLLO PHARMACY").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("7-2-1740 ,KSSP\n" +
                "WAREHOUSE\n" +
                "COMPLEX SANATH\n" +
                "NAGAR ,OPP:FI\n" +
                "RE STATION\n" +
                "HYDERABAD,5\n" +
                "00018").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))).setMarginRight(10);
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("INVOICE NUMBER: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("DS0512953").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("INVOICE DATE: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("01-10-22 04:36 PM").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("GST NUMBER: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("36AAPCA5954P1ZS").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
        table1.setMarginBottom(10);

//        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Registered Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("No.19 Bishop Gerden, Raja Annamalaipuram, Chennai-600028").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("Admin Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("(For all correspondence) All towers, Floor No 55, Greams Road, Chennai-600006").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("CIN : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("U52500TN2016PLC111328").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        String duplicate = isDuplicate ? "Duplicate Copy of Invoice" : "";
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text(duplicate).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(10)).setBorder(Border.NO_BORDER));
//
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text("INVOICE").setFontSize(ITEXT_FONT_SIZE_EIGHT).setFont(bold)).setMarginLeft(10)).setBorder(Border.NO_BORDER));

        float[] columnWidth2 = {400, 180};// 580
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table2 = new Table(columnWidth2);
        Border border2 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table2.setBorder(border2);
//        table2.setBorder(new SolidBorder(1));
//        table2.addCell(new Cell().add(new Paragraph(new Text("DELIVER TO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))));
        table2.addCell(new Cell(8, 1).add(new Paragraph(new Text("DELIVER TO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).add(new Paragraph(new Text("Santosh kumar").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text("NEAR AQUA WATER PLANT ADARSH NAGAR, NIRMAL, TS,504106").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text("CONTACT NO: 9550080255").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).setBorder(Border.NO_BORDER));
        table2.setMarginBottom(20);
        table2.setMarginRight(30);
        //        table2.setWidth(510);
        float[] columnWidth3 = {183, 15, 183, 15, 183};//580
//        float columnWidth3[] = {180, 130, 100, 150};
        Table table3 = new Table(columnWidth3);
        Drawable apolloLogoDrawableQr = getActivity().getDrawable(R.drawable.qr_code_shipping);
        Bitmap apolloLogoBitMapQr = ((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
        ByteArrayOutputStream stream1Qr = new ByteArrayOutputStream();
        apolloLogoBitMapQr.compress(Bitmap.CompressFormat.PNG, 100, stream1Qr);
        byte[] bitMapData1Qr = stream1Qr.toByteArray();

        ImageData imageData1Qr = ImageDataFactory.create(bitMapData1Qr);
        Image image1Qr = new Image(imageData1Qr);
        image1Qr.scaleToFit(50, 30);
        image1Qr.setHeight(30);

        table3.addCell(new Cell().add(image1Qr).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().setBorder(Border.NO_BORDER));

        table3.addCell(new Cell().add(new Paragraph(new Text("PREPAID").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
        table3.addCell(new Cell().setBorder(Border.NO_BORDER));

        table3.addCell(new Cell().add(image1Qr).setBorder(Border.NO_BORDER));
        table3.setMarginBottom(5);

        float[] columnWidth4 = {183, 15, 183, 15, 183};//580
//        float columnWidth3[] = {180, 130, 100, 150};
        Table table4 = new Table(columnWidth3);

        table4.addCell(new Cell().add(new Paragraph(new Text("ORDER ID").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));

        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

        table4.addCell(new Cell().add(new Paragraph(new Text("WEIGHT (in GMS):- .00 appr.").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

        table4.addCell(new Cell().add(new Paragraph(new Text("A247-LHUB-DELHIVERY\nRouting code :").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
        table4.setMarginBottom(5);

        float[] columnWidth5 = {580};// 580
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table5 = new Table(columnWidth5);
        Border border5 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table5.setBorder(border5);
//        table2.setBorder(new SolidBorder(1));
//        table2.addCell(new Cell().add(new Paragraph(new Text("DELIVER TO : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))));
        table5.addCell(new Cell(1, 1).add(new Paragraph(new Text("AMOUNT TO BE COLLECTED - Rs .00: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))));
        table5.setMarginBottom(10);
        table5.setMarginRight(50);
        table5.setMarginLeft(50);

        float[] columnWidth6 = {290,290};//580
//        float columnWidth4[] = {41, 31, 136, 21, 51, 36, 51, 51, 51, 51, 41};
        Table table6 = new Table(columnWidth6);
        Border border6 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table6.setBorder(border6);
        table6.addCell(new Cell().add(new Paragraph(new Text("Description").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("TOTAL").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
//        table4.addCell(new Cell().add(new Paragraph(new Text("Product Name").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("Sch").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("HSN Code").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("Mfg").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("Batch").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("Expiry").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("MRP").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("Amount").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//        table4.addCell(new Cell().add(new Paragraph(new Text("GST%").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border4));
//
            table6.addCell(new Cell().add(new Paragraph(new Text("Medicine/Wellness/Hygiene").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
            table6.addCell(new Cell().add(new Paragraph(new Text("302.00").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
//            String itemName = salesLine.getItemName().replace(" ", "\u00A0");
            table6.addCell(new Cell().add(new Paragraph(new Text("Discount").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
            table6.addCell(new Cell().add(new Paragraph(new Text("24.16").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
            table6.addCell(new Cell().add(new Paragraph(new Text("Shipping Charges").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
            table6.addCell(new Cell().add(new Paragraph(new Text("40.00").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Total (Inclusive of all taxes)").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("317.84").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(border6));
        table6.setMarginBottom(40);
        table6.setMarginRight(30);
        table6.setMarginLeft(30);

            //            String batchNo = "";
//            if (salesLine.getBatchNo().length() > 12) {
//                batchNo = salesLine.getBatchNo().substring(0, 11);
//                batchNo = batchNo + "\n" + salesLine.getBatchNo().substring(12);
//            } else {
//                batchNo = salesLine.getBatchNo();
//            }
//            table4.addCell(new Cell().add(new Paragraph(new Text(batchNo).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
//            if (salesLine.getExpDate() != null && salesLine.getExpDate().length() > 5) {
//                String expDate[] = salesLine.getExpDate().substring(2, 7).split("-");
//                table4.addCell(new Cell().add(new Paragraph(new Text(expDate[1] + "-" + expDate[0]).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
//            } else {
//                table4.addCell(new Cell().add(new Paragraph(new Text("--").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
//            }
//            table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getMrp()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
//            table4.addCell(new Cell().add(new Paragraph(new Text(salesLine.getLineTotAmount()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
//            Double gst = Double.parseDouble(salesLine.getSGSTPer()) + Double.parseDouble(salesLine.getCGSTPer());
//            table4.addCell(new Cell().add(new Paragraph(new Text(String.format("%.02f", gst)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(border4));
//
//        float[] columnWidth5 = {144, 170, 122, 144};//580
////        float columnWidth5[] = {140, 160, 120, 140};
//        Table table5 = new Table(columnWidth5);
//        Border border5 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
//        table5.setBorder(border5);
////        table5.setBorder(new SolidBorder(1));
//        double taxbleValue = 0.0;
//        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
//            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()) {
//                taxbleValue = taxbleValue + Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable());
//
//            }
//        }
//        table5.addCell(new Cell().add(new Paragraph(new Text("Taxable Value : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", taxbleValue)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        double cgstAmount = 0.0;
//        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
//            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty()
//
//                    && pdfModelResponse.getSalesLine().get(i).getCGSTPer() != null && !pdfModelResponse.getSalesLine().get(i).getCGSTPer().isEmpty()) {
////                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);
//                cgstAmount = cgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getCGSTPer())) / 100);
//
//            }
//        }
//        table5.addCell(new Cell().add(new Paragraph(new Text("CGST Amount: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", cgstAmount)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        double sgstAmount = 0.0;
//        for (int i = 0; i < pdfModelResponse.getSalesLine().size(); i++) {
//            if (pdfModelResponse.getSalesLine().get(i).getTaxable() != null && !pdfModelResponse.getSalesLine().get(i).getTaxable().isEmpty() && pdfModelResponse.getSalesLine().get(i).getSGSTPer() != null && !pdfModelResponse.getSalesLine().get(i).getSGSTPer().isEmpty()) {
//                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getTaxable()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);
//                //                sgstAmount = sgstAmount + ((Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getMrp()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getQty()) * Double.parseDouble(pdfModelResponse.getSalesLine().get(i).getSGSTPer())) / 100);
//
//            }
//        }
//        table5.addCell(new Cell().add(new Paragraph(new Text("SGST Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", sgstAmount)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        double gross = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getTotal());
//        table5.addCell(new Cell().add(new Paragraph(new Text("Gross Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", gross)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//
//        float[] columnWidth6 = {144, 170, 122, 144};//580
////        float columnWidth6[] = {140, 160, 120, 140};
//        Table table6 = new Table(columnWidth6);
//        Border border6 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
//        table6.setBorder(border6);
////        table6.setBorder(new SolidBorder(1));
//        table6.addCell(new Cell().add(new Paragraph(new Text("Donation: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("0.00").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        table6.addCell(new Cell().add(new Paragraph(new Text("*1 HC equal to 1 Rupee").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER));
//        table6.addCell(new Cell(1, 2).add(new Paragraph(new Text("* You Saved Rs.0.00 & Earned 50.35 HC's").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(new DashedBorder(1)));
//
//
//        float[] columnWidth7 = {290, 290};//580
////        float columnWidth7[] = {280, 280};
//        Table table7 = new Table(columnWidth7);
//        Border border7 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
//        table7.setBorder(border7);
////        table7.setBorder(new SolidBorder(1));
//        double netAmout = Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal());
//        table7.addCell(new Cell().add(new Paragraph(new Text(("Rupees " + EnglishNumberToWords.convert(Math.round(Double.parseDouble(pdfModelResponse.getSalesHeader().get(0).getNetTotal()))) + " Only")).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));
//        table7.addCell(new Cell().add(new Paragraph(new Text("Paid Amount : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text(String.format("%.02f", netAmout)).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(150)).setBorder(Border.NO_BORDER));
//
//
//        float[] columnWidth8 = {167, 123, 123, 167};//580
////        float columnWidth8[] = {160, 120, 120, 160};
//        Table table8 = new Table(columnWidth8);
//        Border border8 = new SolidBorder(new DeviceRgb(192, 192, 192), 1);
//        table8.setBorder(border8);
////        table8.setBorder(new SolidBorder(1));
//
//        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//        try {
//            encodeAsBitmap(pdfModelResponse).compress(Bitmap.CompressFormat.PNG, 100, stream2);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        byte[] bitMapData2 = stream2.toByteArray();
//
//        ImageData imageData2 = ImageDataFactory.create(bitMapData2);
//        Image image2 = new Image(imageData2);
//        image2.scaleToFit(50, 50);
//        image2.setHeight(50);
//
//
//        table8.addCell(new Cell().add(new Paragraph(new Text("Wishing You Speedy Recovery").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));
//        table8.addCell(new Cell().add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));
//        table8.addCell(new Cell(4, 1).add(image2).setBorder(Border.NO_BORDER));
//        table8.addCell(new Cell().add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));
//
//        table8.addCell(new Cell().add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));
//        table8.addCell(new Cell().add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));
//        table8.addCell(new Cell().add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));
//
//        table8.addCell(new Cell().add(new Paragraph(new Text("QR Code was digitally displayed for the").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));
//        table8.addCell(new Cell(2, 1).add(new Paragraph(new Text("Scan QR Code For\nRefill/Reorder").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));
//        table8.addCell(new Cell().add(new Paragraph(new Text("For ").setFontSize(8).setFont(font)).add(new Text("APOLLO PHARMACY").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
//
//        table8.addCell(new Cell().add(new Paragraph(new Text("Customer at the time of the transaction").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));
//        table8.addCell(new Cell().add(new Paragraph(new Text("Registered pharmacist").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER));
//
//        table8.addCell(new Cell(1, 4).add(new Paragraph(new Text("E & O.E Goods Once Sold Cannot Be Taken Back or Exchanges | INSULINS AND VACCINES WILL NOT BE TAKEN BACK | EMERGENCY CALL:1066 | Tollfree No: 1860-500-0101").setFontSize(ITEXT_FONT_SIZE_SIX))).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.CENTER));


        document.add(table1);
       document.add(table2);
       document.add(table3);
       document.add(table4);
       document.add(table5);
       document.add(table6);
//        document.add(table7);
//        document.add(table8);
//        if (pageBreakCount != pdfModelResponse.getSalesLine().size()) {
//            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//            createPdfPageWise(pdfDocument, document, isDuplicate);
//        } else {
//            if (!isDuplicate && duplicateCheckboxChecked) {
//                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//                pageBreakCount = 0;
//                createPdfPageWise(pdfDocument, document, true);
//            }
//        }
    }

    private void openPdf() {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "12345".concat(".pdf"));
        if (file.exists()) {
            //Button To start print

            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);

            PrintManager printManager = (PrintManager) this.getActivity().getSystemService(Context. PRINT_SERVICE);
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

    private Bitmap encodeAsBitmap(PdfModelResponse pdfModelResponse) throws WriterException {

        String str = "CUSTOMERNAME: " + pdfModelResponse.getSalesHeader().get(0).getCustName() + "\nPHONE: " + pdfModelResponse.getSalesHeader().get(0).getCustMobile() + "\nBILL NO: " + pdfModelResponse.getSalesHeader().get(0).getReceiptId();
        for (PdfModelResponse.SalesLine salesLine : pdfModelResponse.getSalesLine()) {
            str = str + "\nITEMID: " + "- " + "QTY: " + salesLine.getQty();
        }
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 90, 90);

        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[y * w + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
    @Override
    public void onSuccessGeneratepdfbyFlidApiCall(GeneratePdfbyFlidResponse generatePdfbyFlidResponse) {
        if (generatePdfbyFlidResponse != null && generatePdfbyFlidResponse.getStatus()) {
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File folder = new File(extStorageDirectory, "shipping");
            folder.mkdir();
            File file = new File(folder, this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
//            mPresenter.doDownloadPdf(generatePdfbyFlidResponse.getUrl(), file);
        } else if (generatePdfbyFlidResponse != null && !generatePdfbyFlidResponse.getStatus()) {
            Toast.makeText(getContext(), generatePdfbyFlidResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPdf() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/shipping/" + this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
        if (file.exists()) {
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            if (mPresenter.getPaperLabelSize().equalsIgnoreCase("4X6")) {
                builder.setMediaSize(PrintAttributes.MediaSize.NA_INDEX_4X6);
            } else if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A4")) {
                builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            }
            builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);
            PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
            String jobName = this.getString(R.string.app_name) + " Document";

            printManager.print(jobName, pda, builder.build());
        }
    }

    @Override
    public void onClickScanCode() {
        isScanerBack = true;
        BillerOrdersActivity.isBillerActivity = true;
        isShippingLabelFragment = true;
        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onClickSearchTextClear() {
        shippingLabelBinding.searchText.setText("");
        shippingLabelBinding.deleteCancel.setVisibility(View.GONE);
        shippingLabelBinding.search.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickPrevPage() {
        if (startIndex >= 100) {
            startIndex = startIndex - 100;

            if (lastIndex != 0) {
                endIndex = endIndex - lastIndex;
                lastIndex = 0;
            } else {
                endIndex = endIndex - 100;
            }
            shippingLabelBinding.setIsNaxtPage(endIndex != getJounalOnlineOrderTransactionsResponseListTotal.size());
            shippingLabelBinding.setIsPrevtPage(startIndex != 0);
            List<GetJounalOnlineOrderTransactionsResponse> myLastPosts = getJounalOnlineOrderTransactionsResponseListTotal.subList(startIndex, endIndex);

            onSuccessGetJounalOnlineOrderTransactonApiText(myLastPosts);

        } else {
            Toast.makeText(getContext(), "No Previous orders", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNextPage() {
        if (getJounalOnlineOrderTransactionsResponseListTotal.size() - 1 > endIndex) {
            startIndex = startIndex + 100;
            if (getJounalOnlineOrderTransactionsResponseListTotal != null && getJounalOnlineOrderTransactionsResponseListTotal.size() >= endIndex + 100) {
                endIndex = endIndex + 100;
            } else {
                lastIndex = getJounalOnlineOrderTransactionsResponseListTotal.size() - endIndex;
                endIndex = getJounalOnlineOrderTransactionsResponseListTotal.size();
            }
            shippingLabelBinding.setIsNaxtPage(endIndex != getJounalOnlineOrderTransactionsResponseListTotal.size());
            shippingLabelBinding.setIsPrevtPage(startIndex != 0);
            List<GetJounalOnlineOrderTransactionsResponse> myLastPosts = getJounalOnlineOrderTransactionsResponseListTotal.subList(startIndex, endIndex);
            onSuccessGetJounalOnlineOrderTransactonApiText(myLastPosts);
        } else {
            Toast.makeText(getContext(), "No Next orders", Toast.LENGTH_SHORT).show();
        }
    }

    PrintDocumentAdapter pda = new PrintDocumentAdapter() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
            InputStream input = null;
            OutputStream output = null;
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/shipping/" + ShippingLabelFragment.this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");

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
                        Toast.makeText(getContext(), "FileInputStream getting null", Toast.LENGTH_SHORT).show();
                    }

                    if (output != null) {
                        output.close();
                    } else {
                        Toast.makeText(getContext(), "FileOutStream getting null", Toast.LENGTH_SHORT).show();
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
            PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("file_name.pdf").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
            callback.onLayoutFinished(pdi, true);
        }

    };

    @Override
    public void noShippinfLabelFound(int count) {
        if (count > 0) {
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + getJounalOnlineOrderTransactionsResponseListTotal.size() + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            shippingLabelBinding.noOrderFoundText.setVisibility(View.GONE);
            shippingLabelBinding.shippingListRecycler.setVisibility(View.VISIBLE);
        } else {
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + getJounalOnlineOrderTransactionsResponseListTotal.size() + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            shippingLabelBinding.shippingListRecycler.setVisibility(View.GONE);
            shippingLabelBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
//            mPresenter.generatePdfbyFlidApiCall(getJounalOnlineOrderTransactionsResponse.getRefno(), mPresenter.getPaperLabelSize());
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() != null) {
                Toast.makeText(getContext(), "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                shippingLabelBinding.searchText.setText(Result.getContents());
                BillerOrdersActivity.isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
