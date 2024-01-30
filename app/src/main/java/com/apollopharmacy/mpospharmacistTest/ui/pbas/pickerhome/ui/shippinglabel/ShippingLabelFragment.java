package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
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
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.adapter.ShippingLabelAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GeneratePdfbyFlidResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.qrcode.QRGContents;
import com.apollopharmacy.mpospharmacistTest.utils.qrcode.QRGEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.barcodes.qrcode.WriterException;
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
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
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
import java.util.Hashtable;
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

    //Pagination
    public static List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseStatic;
    private List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseListTotal;
    private int startIndex = 0;
    private int endIndex = 100;
    int lastIndex = 0;

    private final static int ITEXT_FONT_SIZE_EIGHT = 8;
    private final static int ITEXT_FONT_SIZE_TEN = 12;
    private final static int ITEXT_FONT_SIZE_SIX = 10;

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
        } else if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A5")) {
            dialogLabelSizeBinding.aFiveRadio.setChecked(true);
        }
        dialogLabelSizeBinding.labelSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.four_six_radio) {
                mPresenter.setPaperLabelSize("4X6");
                paperLabelSizeDialog.dismiss();
            } else if (checkedId == R.id.a_four_radio) {
                mPresenter.setPaperLabelSize("A4");
                paperLabelSizeDialog.dismiss();
            } else if (checkedId == R.id.a_five_radio) {
                mPresenter.setPaperLabelSize("A5");
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

    @SuppressLint("NewApi")
    @Override
    public void onSuccessGetJounalOnlineOrderTransactonApi(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList) {
        if (getJounalOnlineOrderTransactionsResponseList != null && getJounalOnlineOrderTransactionsResponseList.size() > 0) {


            getJounalOnlineOrderTransactionsResponseListTotal = getJounalOnlineOrderTransactionsResponseList.stream().filter(e -> !e.getPickupStatus()).collect(Collectors.toList());
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

        mPresenter.pdfApiCall_(getJounalOnlineOrderTransactionsResponse.getRefno());
//       mPresenter.pdfApiCall();
//        if (isStoragePermissionGranted()) {
//            mPresenter.generatePdfbyFlidApiCall(getJounalOnlineOrderTransactionsResponse.getRefno(), mPresenter.getPaperLabelSize());
//        }
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
            mPresenter.doDownloadPdf(generatePdfbyFlidResponse.getUrl(), file);
        } else if (generatePdfbyFlidResponse != null && !generatePdfbyFlidResponse.getStatus()) {
            Toast.makeText(getContext(), generatePdfbyFlidResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPdf() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/shipping/" + this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
        if (file.exists()) {
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
//            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
            builder.setMinMargins(PrintAttributes.Margins.NO_MARGINS);
            if (mPresenter.getPaperLabelSize().equalsIgnoreCase("4X6")) {
                builder.setMediaSize(PrintAttributes.MediaSize.NA_INDEX_4X6);
            } else {
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
//        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        Intent intent = new Intent(getActivity(), ScannerActivity.class);
        intent.putExtra("isShippingLabelFragment", isShippingLabelFragment);
        startActivityForResult(intent, 888);
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

    PDFShippingLabelResponse pdfShippingLabelResponse;

    @Override
    public void onSuccessPdfApiCall(PDFShippingLabelResponse pdfShippingLabelResponse) {
//        pdfShippingLabelResponse.getData().setDspname("A247-LHUB-LHUB");
        if (pdfShippingLabelResponse != null && pdfShippingLabelResponse.getData() != null) {
            this.pdfShippingLabelResponse = pdfShippingLabelResponse;

//            pdfShippingLabelResponse.getData().setPaymentmode("CASHONDELIVERY");
            if (pdfShippingLabelResponse.getData().getDspname().equalsIgnoreCase("A247-LHUB-LHUB")) {
                Toast.makeText(getActivity(), "DSP name is LHUB-LHUB and has not yet been changed; Kindly try again later", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A5")) {
//                        createPdfA5();
                    } else if (mPresenter.getPaperLabelSize().equalsIgnoreCase("4X6")) {
                        createPdfA5();
                    } else {
                        createPdf();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

//            try {
//                if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A5")) {
//                    createPdfA5();
//                } else {
//                    createPdf();
//                }

        } else {
            Toast.makeText(getActivity(), "" + pdfShippingLabelResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void createPdf() throws IOException {
        if (isStoragePermissionGranted()) {

//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

//        File file = new File(pdfPath, "pdfdoc.pdf");
////        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "12345".concat(".pdf"));
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//
//        File file = new File(pdfPath, getJounalOnlineOrderTransactionsResponse.getRefno()+".pdf");

            //from
//            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//            File folder = new File(extStorageDirectory, "shipping");
//            folder.mkdir();
////        if(folder.exists()){
////            folder.delete();
////        }
//            File file = new File(folder, this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
//
//            try {
//                file.createNewFile();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            //to
            String fileName = this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf";
            FileUtil.createFilePath(fileName, getContext(), "shipping");
            PdfWriter writer = new PdfWriter(FileUtil.getFilePath(fileName, getContext(), "shipping"));

//        OutputStream outputStream = new FileOutputStream(file);


//            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);
            document.setMargins(15, 15, 15, 15);
            createPdfPageWise(pdfDocument, document, false);
            document.close();
            if (isStoragePermissionGranted()) {
                openPdf();
            }
        }
    }

    private void createPdfA5() throws IOException {
        if (isStoragePermissionGranted()) {

//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

//        File file = new File(pdfPath, "pdfdoc.pdf");
////        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "12345".concat(".pdf"));
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//
//        File file = new File(pdfPath, getJounalOnlineOrderTransactionsResponse.getRefno()+".pdf");

            //from
//            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//            File folder = new File(extStorageDirectory, "shipping");
//            folder.mkdir();
////        if(folder.exists()){
////            folder.delete();
////        }
//            File file = new File(folder, this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
//
//            try {
//                file.createNewFile();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            //to
            String fileName = this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf";
            FileUtil.createFilePath(fileName, getContext(), "shipping");
            PdfWriter writer = new PdfWriter(FileUtil.getFilePath(fileName, getContext(), "shipping"));

//        OutputStream outputStream = new FileOutputStream(file);


//            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            PageSize fourBySix = new PageSize(288, 432);
            Document document = new Document(pdfDocument, fourBySix);
            document.setMargins(15, 15, 15, 15);
            createPdfPageWiseA5(pdfDocument, document, false);
            document.close();
            if (isStoragePermissionGranted()) {
                openPdf();
            }
        }
    }

    private void openPdf() {

//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "12345".concat(".pdf"));
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//
//        File file = new File(pdfPath, getJounalOnlineOrderTransactionsResponse.getRefno()+".pdf");
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/shipping/" + this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
        String fileName = getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf";
        File file = FileUtil.getFilePath(fileName, getContext(), "shipping");
        if (file.exists()) {
            //Button To start print

            PrintAttributes.Builder builder = new PrintAttributes.Builder();
//            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
            if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A5")) {
                builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
            } else if (mPresenter.getPaperLabelSize().equalsIgnoreCase("4X6")) {
                builder.setMediaSize(PrintAttributes.MediaSize.NA_INDEX_4X6);
            } else {
                builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            }
//            else{
//                builder.setMediaSize(PrintAttributes.MediaSize.NA_INDEX_4X6);
//            }
            builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);
            builder.setMinMargins(PrintAttributes.Margins.NO_MARGINS);

            PrintManager printManager = (PrintManager) getContext().getSystemService(Context.PRINT_SERVICE);
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
            try {
                createPdfA5();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            Toast.makeText(this, "File not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void createPdfPageWise(PdfDocument pdfDocument, Document document, boolean isDuplicate) throws IOException {
        // declaring variables for loading the fonts from asset
        byte[] fontByte, boldByte;
        AssetManager am;
        am = this.getActivity().getAssets();
//the file name should be same as in your assets folder
//        try (InputStream inStream = am.open("font/cambria.ttf")) {
//            fontByte = IOUtils.toByteArray(inStream);
//        }
        FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
        FontProgram fontProgramBold = FontProgramFactory.createFont(BOLD);
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
        Table table1 = new Table(columnWidth1);

        //table1.....row1.....
        Drawable apolloLogoDrawable = getActivity().getDrawable(R.drawable.apollo_1525857827435);
        Bitmap apolloLogoBitMap = ((BitmapDrawable) apolloLogoDrawable).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        apolloLogoBitMap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitMapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitMapData1);
        Image image1 = new Image(imageData1);
        image1.scaleToFit(80, 80);
        image1.setHeight(80);


        Border border1 = new SolidBorder(new DeviceRgb(199, 199, 199), 1);
//        table1.setBorder(border1);
        table1.addCell(new Cell(4, 1).add(image1).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("APOLLO PHARMACY").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getStoreaddress1() + pdfShippingLabelResponse.getData().getStoreaddress2() + pdfShippingLabelResponse.getData().getStoreaddress3()).setFontSize(ITEXT_FONT_SIZE_SIX))).setPadding(10).setBorder(new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F)));
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("INVOICE NUMBER: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).add(new Text(pdfShippingLabelResponse.getData().getInvoiceno()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("INVOICE DATE: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).add(new Text(pdfShippingLabelResponse.getData().getInvoicedate()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("GST NUMBER: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).add(new Text(pdfShippingLabelResponse.getData().getGstin()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))).setPadding(10));
        table1.setMarginBottom(10);

//        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Registered Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("No.19 Bishop Gerden, Raja Annamalaipuram, Chennai-600028").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("Admin Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("(For all correspondence) All towers, Floor No 55, Greams Road, Chennai-600006").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("CIN : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("U52500TN2016PLC111328").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        String duplicate = isDuplicate ? "Duplicate Copy of Invoice" : "";
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text(duplicate).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(10)).setBorder(Border.NO_BORDER));
//
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text("INVOICE").setFontSize(ITEXT_FONT_SIZE_EIGHT).setFont(bold)).setMarginLeft(10)).setBorder(Border.NO_BORDER));

        float[] columnWidth2 = {430, 150};// 580
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table2 = new Table(columnWidth2);
//        Border border2 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        table2.setBorder(border2);
        table2.addCell(new Cell(8, 1).add(new Paragraph(new Text("DELIVER TO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getCustomername()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getShippingaddress() + "" + pdfShippingLabelResponse.getData().getShippingcity() + "" + pdfShippingLabelResponse.getData().getShippingstateid() + "" + pdfShippingLabelResponse.getData().getShippingpincode()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text("CONTACT NO:" + pdfShippingLabelResponse.getData().getPrimarycontactno()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)));
        table2.addCell(new Cell(8, 1).add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));

        //        table2.setWidth(510);
        float[] columnWidth3 = {183, 15, 183, 15, 184};//580
        Table table3 = new Table(columnWidth3);
//        Drawable apolloLogoDrawableQr = getActivity().getDrawable(R.drawable.qr_code_shipping);
//
//
//        Bitmap apolloLogoBitMapQr = null;
//        try {
//            apolloLogoBitMapQr = encodeAsBitmapBarcode("1234567890");
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            e.printStackTrace();
//        }
//
//        //((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
//        ByteArrayOutputStream stream1Qr = new ByteArrayOutputStream();
//        apolloLogoBitMapQr.compress(Bitmap.CompressFormat.PNG, 100, stream1Qr);
//        byte[] bitMapData1Qr = stream1Qr.toByteArray();
//
//        ImageData imageData1Qr = ImageDataFactory.create(bitMapData1Qr);
//        Image image1Qr = new Image(imageData1Qr);
//        image1Qr.setWidth(130);
//        image1Qr.setHeight(300);
//        image1Qr.scaleToFit(130, 30);
//        image1Qr.setHeight(30);

        Bitmap apolloLogoBitMapQrA4 = null;
//        try {
//            apolloLogoBitMapQr = encodeAsBitmapBarcode("1234567890");
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            e.printStackTrace();
//        }
        apolloLogoBitMapQrA4 = generateBarcode(pdfShippingLabelResponse.getData().getAwbno());
        //((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
        ByteArrayOutputStream stream1QrA4 = new ByteArrayOutputStream();
        apolloLogoBitMapQrA4.compress(Bitmap.CompressFormat.PNG, 100, stream1QrA4);
        byte[] bitMapData1QrA42 = stream1QrA4.toByteArray();

        ImageData imageData1QrA4 = ImageDataFactory.create(bitMapData1QrA42);
        Image image1QrA42 = new Image(imageData1QrA4);
        image1QrA42.setWidth(130);
        image1QrA42.setHeight(300);
        image1QrA42.scaleToFit(130, 30);
        image1QrA42.setHeight(30);

        table3.addCell(new Cell().add(image1QrA42).setBorder(Border.NO_BORDER).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getAwbno()).setFontSize(10).setFont(font)).setMarginLeft(10)).setPaddingBottom(0f));


//        table3.addCell(new Cell().add(image1Qr).setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().setBorder(Border.NO_BORDER));

        table3.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getPaymentmode()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
        table3.addCell(new Cell().setBorder(Border.NO_BORDER));
        Bitmap apolloLogoBitMapQrA4S = null;
//        try {
//            apolloLogoBitMapQr = encodeAsBitmapBarcode("1234567890");
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            e.printStackTrace();
//        }
        apolloLogoBitMapQrA4S = generateBarcode(pdfShippingLabelResponse.getData().getAwbno());
        //((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
        ByteArrayOutputStream stream1QrA4S = new ByteArrayOutputStream();
        apolloLogoBitMapQrA4S.compress(Bitmap.CompressFormat.PNG, 100, stream1QrA4S);
        byte[] bitMapData1QrA42S = stream1QrA4S.toByteArray();

        ImageData imageData1QrA4S = ImageDataFactory.create(bitMapData1QrA42S);
        Image image1QrA42S = new Image(imageData1QrA4S);
        image1QrA42S.setWidth(130);
        image1QrA42S.setHeight(300);
        image1QrA42S.scaleToFit(130, 30);
        image1QrA42S.setHeight(30);

        table3.addCell(new Cell().add(image1QrA42S).setBorder(Border.NO_BORDER).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getOrderid()).setFontSize(10).setFont(font)).setMarginLeft(20)).setPaddingBottom(0f));

//        table3.addCell(new Cell().add(image1Qr).setBorder(Border.NO_BORDER));
        table3.setMarginBottom(5);

        float[] columnWidth4 = {183, 15, 183, 15, 183};//580
        Table table4 = new Table(columnWidth4);

        table4.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getOrderid()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));

        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

        table4.addCell(new Cell().add(new Paragraph(new Text("WEIGHT (in GMS):- " + pdfShippingLabelResponse.getData().getTotalweight() + "appr.").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

        table4.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getDspname() + "\n" + "Routing code :" + pdfShippingLabelResponse.getData().getRoutingcode()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
        table4.setMarginBottom(5);

        float[] columnWidth5 = {580};// 580
        Table table5 = new Table(columnWidth5);
        Border border5 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table5.setBorder(border5);
        table5.addCell(new Cell(1, 1).add(new Paragraph(new Text("AMOUNT TO BE COLLECTED - Rs ." + pdfShippingLabelResponse.getData().getAmounttobecollect()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER));
        table5.setMarginBottom(10);
        table5.setMarginRight(50);
        table5.setMarginLeft(50);

        float[] columnWidth6 = {290, 290};//580
        Table table6 = new Table(columnWidth6);
        Border border6 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table6.setBorder(border6);
        table6.addCell(new Cell().add(new Paragraph(new Text("Description").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("TOTAL").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));

        table6.addCell(new Cell().add(new Paragraph(new Text("Medicine/Wellness/Hygiene").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getHealthWellness()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Discount").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getDiscount()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Shipping Charges").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getShippingcharges()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Total (Inclusive of all taxes)").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getInvoiceamt()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.setMarginBottom(40);
        table6.setMarginRight(30);
        table6.setMarginLeft(30);

        float[] columnWidth7 = {290, 290};//580
        Table table7 = new Table(columnWidth7);
        table7.addCell(new Cell().add(new Paragraph(new Text("if undelivered,please return it to Apollo Pharmacy,").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
        table7.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getStoreaddress1() + pdfShippingLabelResponse.getData().getStoreaddress2() + pdfShippingLabelResponse.getData().getStoreaddress3()).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));


        float[] columnWidth8 = {580};//580
        Table table8 = new Table(columnWidth8);
        table8.addCell(new Cell().add(new Paragraph(new Text("Notice - Please do not accept this packet if it is tampered or damaged").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));


        document.add(table1);
        document.add(table2);
        document.add(new Paragraph(""));
        document.add(table3);
        document.add(table4);
        document.add(table5);
        document.add(table6);
        document.add(table7);
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(table8);
    }

    public static final String REGULAR = "res/font/roboto_regular.ttf";
    public static final String BOLD = "res/font/roboto_bold.ttf";

    private void createPdfPageWiseA5(PdfDocument pdfDocument, Document document, boolean isDuplicate) throws IOException {
        // declaring variables for loading the fonts from asset
        byte[] fontByte, boldByte;
        AssetManager am;
        am = this.getActivity().getAssets();
//the file name should be same as in your assets folder
//        try (InputStream inStream = am.open("font/cambria.ttf")) {
//            fontByte = IOUtils.toByteArray(inStream);
//        }
        FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
        FontProgram fontProgramBold = FontProgramFactory.createFont(BOLD);
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

        float[] columnWidth1 = {40, 5, 110, 5, 128};//288
        Table table1 = new Table(columnWidth1);

        //table1.....row1.....
        Drawable apolloLogoDrawable = getActivity().getDrawable(R.drawable.apollo_1525857827435);
        Bitmap apolloLogoBitMap = ((BitmapDrawable) apolloLogoDrawable).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        apolloLogoBitMap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitMapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitMapData1);
        Image image1 = new Image(imageData1);
        image1.scaleToFit(40, 40);
        image1.setHeight(40);


        Border border1 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table1.setBorderLeft(border1);
        table1.setBorderRight(border1);
        table1.setBorderTop(border1);
//        table1.setMarginLeft(5);
//        table1.setMarginRight(5);
        table1.setPadding(0f);

        table1.addCell(new Cell(1, 1).add(image1).setBorder(Border.NO_BORDER).setMarginLeft(10));
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getDspname()).setFontSize(8).setFont(bold).setBorder(Border.NO_BORDER)).setBorder(Border.NO_BORDER).setPadding(0)).add(new Paragraph(new Text("Routing code  : ").setFontSize(7).setFont(bold)).add(new Text(pdfShippingLabelResponse.getData().getRoutingcode()).setFontSize(7).setFont(font))).setBorder(Border.NO_BORDER).setPadding(0));
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getInvoicedate()).setFontSize(8).setFont(font))).setBorder(Border.NO_BORDER));
//        table1.setMarginBottom(10);

//        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Registered Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("No.19 Bishop Gerden, Raja Annamalaipuram, Chennai-600028").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("Admin Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("(For all correspondence) All towers, Floor No 55, Greams Road, Chennai-600006").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("CIN : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("U52500TN2016PLC111328").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        String duplicate = isDuplicate ? "Duplicate Copy of Invoice" : "";
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text(duplicate).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(10)).setBorder(Border.NO_BORDER));
//
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text("INVOICE").setFontSize(ITEXT_FONT_SIZE_EIGHT).setFont(bold)).setMarginLeft(10)).setBorder(Border.NO_BORDER));
//        float[] columnWidthnew = {580};//580
//        Table tableNew = new Table(columnWidthnew);
//        Border border2New = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
////        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        tableNew.setBorderLeft(border2New);
//        tableNew.setBorderRight(border2New);
////        table2.setBorderBottom(border2);
////        tableNew.setMarginLeft(100);
////        tableNew.setMarginRight(100);
//        tableNew.setBorder(Border.NO_BORDER);
//        tableNew.setMarginRight(15);
//        tableNew.setMarginLeft(15);
//        tableNew.addCell(new Cell(1, 1).add(new Paragraph(new Text("Routing code  : ").setFontSize(12).setFont(bold)).add(new Text("ROMS18874").setFontSize(12).setFont(font)).setMarginRight(50).setMarginLeft(50)).setBorder(Border.NO_BORDER));

        float[] columnWidth2 = {250, 38};//288
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table2 = new Table(columnWidth2);
        Border border2 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table2.setBorderLeft(border2);
        table2.setBorderRight(border2);
        table2.setBorderBottom(border2);
//        table2.setMarginLeft(5);
//        table2.setMarginRight(5);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);

        Bitmap apolloLogoBitMapQr = null;
//        try {
//            apolloLogoBitMapQr = encodeAsBitmapBarcode("1234567890");
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            e.printStackTrace();
//        }

        apolloLogoBitMapQr = generateBarcodeWidthIncreased(pdfShippingLabelResponse.getData().getAwbno());
        //((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
        ByteArrayOutputStream stream1Qr = new ByteArrayOutputStream();
        apolloLogoBitMapQr.compress(Bitmap.CompressFormat.PNG, 100, stream1Qr);
        byte[] bitMapData1Qr = stream1Qr.toByteArray();

        ImageData imageData1Qr = ImageDataFactory.create(bitMapData1Qr);
        Image image1Qr = new Image(imageData1Qr);
        image1Qr.setWidth(250);
        image1Qr.setHeight(25);
        image1Qr.scaleToFit(250, 30);
        image1Qr.setHeight(25);
//        table2.setHeight(60);

        table2.addCell(new Cell().add(image1Qr).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getAwbno()).setHorizontalAlignment(HorizontalAlignment.CENTER.CENTER).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setFont(font))).setHorizontalAlignment(HorizontalAlignment.CENTER.CENTER).setTextAlignment(TextAlignment.CENTER).setPaddingBottom(0f));

//
//        table2.addCell(new Cell(8, 1).add(new Paragraph(new Text("DELIVER TO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).add(new Paragraph(new Text("Santosh kumar").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text("NEAR AQUA WATER PLANT ADARSH NAGAR, NIRMAL, TS,504106").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text("CONTACT NO: 9550080255").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)));
//        table2.addCell(new Cell(8, 1).add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));

        //        table2.setWidth(510);
//        float[] columnWidth3 = {160, 128};//288
        float[] columnWidth3 = {100, 188};//288

        Table table3 = new Table(columnWidth3);
        Border border3Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table3.setBorderLeft(border3Black);
        table3.setBorderRight(border3Black);
        table3.setBorderTop(border3Black);
//        table3.setMarginLeft(5);
//        table3.setMarginRight(5);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);
        table3.addCell(new Cell(1, 1)
                .add(new Paragraph(new Text("Shipping Address: ")
                        .setFontSize(8).setFont(bold))
                        .setMarginLeft(5)).setBorder(Border.NO_BORDER).setMarginLeft(5));
//        if (pdfShippingLabelResponse.getData().getPaymentmode().equalsIgnoreCase("cashondelivery")){
//            table3.addCell(new Cell(1, 1).add(new Paragraph(new Text("COD" + ": " + " ").setFontSize(8).setFont(bold)).add(new Text("" + pdfShippingLabelResponse.getData().getInvoiceamt()).setFontSize(7).setFont(font)).setMarginLeft(2)).setBorder(border3Black));
//
//        }else {
        table3.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getPaymentmode() + ": " + " ").setFontSize(8).setFont(bold)).add(new Text("" + pdfShippingLabelResponse.getData().getInvoiceamt()).setFontSize(7).setFont(font)).setMarginLeft(2)).setBorder(border3Black));

//        }
        Table tableAddress;
//      pdfShippingLabelResponse.getData().setQrcode("12345678920");
        if (pdfShippingLabelResponse.getData().getQrcode() != null && !pdfShippingLabelResponse.getData().getQrcode().equals("")) {
            float[] columnWidthAddress = {180, 108};//288
            tableAddress = new Table(columnWidthAddress);
            Border border2Address = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
            tableAddress.setBorderLeft(border2Address);
            tableAddress.setBorderRight(border2Address);
//        table2.setBorderBottom(border2);
//        tableNew.setMarginLeft(100);
//        tableNew.setMarginRight(100);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//        try {
//            encodeAsBitmap(pdfModelResponse).compress(Bitmap.CompressFormat.PNG, 100, stream2);
            QrCodeGeneration(pdfShippingLabelResponse.getData().getQrcode(), getActivity()).compress(Bitmap.CompressFormat.PNG, 100, stream2);
//        }
//        catch (WriterException e) {
//            e.printStackTrace();
//        }
            byte[] bitMapData2 = stream2.toByteArray();

            ImageData imageData2 = ImageDataFactory.create(bitMapData2);
            Image image2 = new Image(imageData2);
            image2.scaleToFit(50, 50);

//.add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getCustomername()).setFont(font).setFontSize(7)).setMarginLeft(5))
            tableAddress.setBorder(Border.NO_BORDER);
//            tableAddress.setMarginRight(5);
//            tableAddress.setMarginLeft(5);
            tableAddress.addCell(new Cell(3, 1)
                    .add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getCustomername() + "\n" + pdfShippingLabelResponse.getData().getShippingaddress() + ", " + pdfShippingLabelResponse.getData().getShippingcity() + ", " + pdfShippingLabelResponse.getData().getShippingstateid() + ", " + pdfShippingLabelResponse.getData().getShippingpincode()).setFont(font).setFontSize(8)).setMarginLeft(5).setFixedLeading(10)).setBorder(Border.NO_BORDER).setMarginLeft(5));
            tableAddress.addCell(new Cell(4, 1).add(image2).setMarginLeft(15).add(new Paragraph(new Text("Scan to pay via UPI").setFontSize(7).setFont(font))).setBorder(Border.NO_BORDER));
        } else {
            float[] columnWidthAddress = {288};//288
            tableAddress = new Table(columnWidthAddress);
            Border border2Address = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
            tableAddress.setBorderLeft(border2Address);
            tableAddress.setBorderRight(border2Address);
//        table2.setBorderBottom(border2);
//        tableNew.setMarginLeft(100);
//        tableNew.setMarginRight(100);
            tableAddress.setBorder(Border.NO_BORDER);
//            tableAddress.setMarginRight(5);
//            tableAddress.setMarginLeft(5);
            tableAddress.addCell(new Cell(3, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getCustomername() + "\n" + pdfShippingLabelResponse.getData().getShippingaddress() + ", " + pdfShippingLabelResponse.getData().getShippingcity() + ", " + pdfShippingLabelResponse.getData().getShippingstateid() + ", " + pdfShippingLabelResponse.getData().getShippingpincode()).setFontSize(7.5f).setFont(font)).setFixedLeading(10).setMarginLeft(5)).setBorder(Border.NO_BORDER).setMarginLeft(5));
//.add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getCustomername()).setFont(font).setFontSize(8)).setMarginLeft(5))
        }

        float[] columnWidthContactNo = {160, 128};//288
        Table tableContactNo = new Table(columnWidthContactNo);
        Border border3BlackContact = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        tableContactNo.setBorderLeft(border3BlackContact);
        tableContactNo.setBorderRight(border3BlackContact);
        tableContactNo.setBorderBottom(border3BlackContact);
//        tableContactNo.setMarginLeft(5);
//        tableContactNo.setMarginRight(5);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);
        tableContactNo.addCell(new Cell(1, 1).add(new Paragraph(new Text("Contact No: " + pdfShippingLabelResponse.getData().getPrimarycontactno()).setFontSize(7.5f).setFont(font)).setMarginLeft(5)).setBorder(Border.NO_BORDER));
        tableContactNo.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getFullfillmentorderid()).setFont(font).setFontSize(7.5f)).setMarginLeft(5)).setBorder(Border.NO_BORDER));

        float[] columnWidth4 = {288};//288
        Table table4 = new Table(columnWidth4);
        Border border4Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table4.setBorder(border4Black);
//        table4.setMarginLeft(5);
//        table4.setMarginRight(5);
        table4.setMarginTop(0);
        table4.setMarginBottom(0);
        table4.addCell(new Cell(2, 1).setMaxHeight(70).add(new Paragraph(new Text("\nSeller Address: " + "\n").setFontSize(8).setFont(bold)).setMarginLeft(5).add(new Text(pdfShippingLabelResponse.getData().getStoreaddress1() + pdfShippingLabelResponse.getData().getStoreaddress2() + pdfShippingLabelResponse.getData().getStoreaddress3()).setFont(font).setFontSize(7.5f)).setFixedLeading(10)).setPadding(0f).setMargin(0f));
        table4.setPadding(0f);

        //        table4.addCell(new Cell().add(new Paragraph(new Text("ORDER ID").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));

//        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

//        table4.addCell(new Cell().add(new Paragraph(new Text("WEIGHT (in GMS):- .00 appr.").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
//        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

//        table4.addCell(new Cell().add(new Paragraph(new Text("A247-LHUB-DELHIVERY\nRouting code :").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
//        table4.setMarginBottom(5);

//        float[] columnWidth5 = {580};// 580
//        Table table5 = new Table(columnWidth5);
//        Border border5 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        table5.setBorder(border5);
//        table5.addCell(new Cell(1, 1).add(new Paragraph(new Text("AMOUNT TO BE COLLECTED - Rs .00: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER));
//        table5.setMarginBottom(10);
//        table5.setMarginRight(50);
//        table5.setMarginLeft(50);

        float[] columnWidth6 = {144, 144};//580
        Table table6 = new Table(columnWidth6);
        Border border6 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table6.setBorder(border6);
//        table6.setMarginLeft(5);
//        table6.setMarginRight(5);
        table6.addCell(new Cell().add(new Paragraph(new Text("Product").setFontSize(7).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Price").setFontSize(7).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));

        table6.addCell(new Cell().add(new Paragraph(new Text("Medicine/Wellness/Hygiene").setFontSize(7).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getHealthWellness()).setFontSize(7).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Total (Inclusive all taxes)").setFontSize(7).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getInvoiceamt()).setFontSize(7).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("Shipping Charges").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("40.00").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("Total (Inclusive of all taxes)").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("317.84").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.setMarginBottom(40);
//        table6.setMarginRight(30);
//        table6.setMarginLeft(30);

        float[] columnWidth7 = {250, 38};// 288
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table7 = new Table(columnWidth7);
        Border border7 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        Bitmap apolloLogoBitMapQr2 = null;
//        try {
//            apolloLogoBitMapQr2 = encodeAsBitmapBarcode("12345678AB");
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            e.printStackTrace();
//        }
        apolloLogoBitMapQr2 = generateBarcode(pdfShippingLabelResponse.getData().getOrderid());
        //((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
        ByteArrayOutputStream stream1Qr2 = new ByteArrayOutputStream();
        apolloLogoBitMapQr2.compress(Bitmap.CompressFormat.PNG, 100, stream1Qr2);
        byte[] bitMapData1Qr2 = stream1Qr2.toByteArray();

        ImageData imageData1Qr2 = ImageDataFactory.create(bitMapData1Qr2);
        Image image1Qr2 = new Image(imageData1Qr2);
        image1Qr2.setWidth(230);
        image1Qr2.setHeight(200);
        image1Qr2.scaleToFit(100, 20);
        image1Qr2.setHeight(20);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table7.setBorder(border7);
//        table7.setMarginLeft(5);
//        table7.setMarginRight(5);
//        table7.setHeight(60);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);
        table7.addCell(new Cell().add(image1Qr2).setMarginLeft(10).setBorder(Border.NO_BORDER).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getOrderid()).setFontSize(7).setFont(font)).setMarginLeft(80)).setPaddingBottom(0f));


//        float[] columnWidth7 = {290, 290};//580
//        Table table7 = new Table(columnWidth7);
//        table7.addCell(new Cell().add(new Paragraph(new Text("if undelivered,please return it to Apollo Pharmacy,").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
//        table7.addCell(new Cell().add(new Paragraph(new Text("7-2-1740 ,KSSP WAREHOUSE COMPLEX, SANATH NAGAR ,OPP:FIRE STATION, HYDERABAD,500018").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));


        float[] columnWidth8 = {288};//288
        Table table8 = new Table(columnWidth8);
        Border border8 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table8.setBorder(border8);
//        table8.setMarginLeft(5);
//        table8.setMarginRight(5);
        table8.addCell(new Cell().add(new Paragraph(new Text("If Undelivered, please return it to the above seller address\n(APOLLO PHARMACY)").setFontSize(7).setFont(font)).setFixedLeading(10)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));


        document.add(table1);
//        document.add(tableNew);
        document.add(table2);
//        document.add(new Paragraph(""));
        document.add(table3);
        document.add(tableAddress);
        document.add(tableContactNo);
        document.add(table4);
//        document.add(table5);
        document.add(table6);
        document.add(table7);
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
        document.add(table8);
    }

    private Bitmap QrCodeGeneration(String qrCode, Context context) {
//        String qrCodeData = "CUSTOMERNAME: " + pdfModelResponse.getSalesHeader().get(0).getCustName() + "\nPHONE: " + pdfModelResponse.getSalesHeader().get(0).getCustMobile() + "\nBILL NO: " + pdfModelResponse.getSalesHeader().get(0).getReceiptId();
//        for (PdfModelResponse.SalesLine salesLine : pdfModelResponse.getSalesLine()) {
        String qrCodeData = qrCode;
//        }
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

    PrintDocumentAdapter pda = new PrintDocumentAdapter() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
            InputStream input = null;
            OutputStream output = null;
            try {
//                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/shipping/" + ShippingLabelFragment.this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
                String fileName = getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf";
                File file = FileUtil.getFilePath(fileName, getContext(), "shipping");

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
            String fileName = getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf";
            PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(fileName).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
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
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
            mPresenter.generatePdfbyFlidApiCall(getJounalOnlineOrderTransactionsResponse.getRefno(), mPresenter.getPaperLabelSize());
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String result = data.getStringExtra("result");
            if (result != null) {
                Toast.makeText(getContext(), "Scanned -> " + result, Toast.LENGTH_SHORT).show();
                shippingLabelBinding.searchText.setText(result);
                BillerOrdersActivity.isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private Bitmap encodeAsBitmapBarcode(String pdfModelResponse) throws WriterException {
        String str = pdfModelResponse;
//        String str = "CUSTOMERNAME: " + pdfModelResponse.getSalesHeader().get(0).getCustName()
//                + "\nPHONE: " + pdfModelResponse.getSalesHeader().get(0).getCustMobile()
//                + "\nBILL NO: " + pdfModelResponse.getSalesHeader().get(0).getReceiptId();
//        for (PdfModelResponse.SalesLine salesLine : pdfModelResponse.getSalesLine()) {
//            str = str + "\nITEMID: " + "- " + "QTY: " + salesLine.getQty();
//        }
        CodaBarWriter codaBarWriter = new CodaBarWriter();
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = codaBarWriter.encode(str, BarcodeFormat.CODABAR, 150, 40);
        } catch (com.google.zxing.WriterException e) {
            e.printStackTrace();
        }

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

    private Bitmap generateBarcode(String productId) {
        try {

            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new Code128Writer();
            BitMatrix byteMatrix = codeWriter.encode(productId, BarcodeFormat.CODE_128, 400, 200, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
//            bitmapImg.setImageBitmap(bitmap);
            return bitmap;
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

    }
    private Bitmap generateBarcodeWidthIncreased(String productId) {
        try {

            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new Code128Writer();
            BitMatrix byteMatrix = codeWriter.encode(productId, BarcodeFormat.CODE_128, 450, 250, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
//            bitmapImg.setImageBitmap(bitmap);
            return bitmap;
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

    }
    private void createPdffoursix() throws IOException {
        if (isStoragePermissionGranted()) {

//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

//        File file = new File(pdfPath, "pdfdoc.pdf");
////        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "12345".concat(".pdf"));
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//
//        File file = new File(pdfPath, getJounalOnlineOrderTransactionsResponse.getRefno()+".pdf");

            //from
//            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//            File folder = new File(extStorageDirectory, "shipping");
//            folder.mkdir();
////        if(folder.exists()){
////            folder.delete();
////        }
//            File file = new File(folder, this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
//
//            try {
//                file.createNewFile();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            //to
            String fileName = this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf";
            FileUtil.createFilePath(fileName, getContext(), "shipping");
            PdfWriter writer = new PdfWriter(FileUtil.getFilePath(fileName, getContext(), "shipping"));

//        OutputStream outputStream = new FileOutputStream(file);


//            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A5);
            document.setMargins(15, 15, 15, 15);
            createPdfPageWiseFourSix(pdfDocument, document, false);
            document.close();
            if (isStoragePermissionGranted()) {
                openPdf();
            }
        }
    }

    private void createPdfPageWiseFourSix(PdfDocument pdfDocument, Document document, boolean isDuplicate) throws IOException {
        // declaring variables for loading the fonts from asset
        byte[] fontByte, boldByte;
        AssetManager am;
        am = this.getActivity().getAssets();
//the file name should be same as in your assets folder
//        try (InputStream inStream = am.open("font/cambria.ttf")) {
//            fontByte = IOUtils.toByteArray(inStream);
//        }
        FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
        FontProgram fontProgramBold = FontProgramFactory.createFont(BOLD);
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

        float[] columnWidth1 = {60, 5, 275, 5, 235};//580
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
        image1.setHeight(40);


        Border border1 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table1.setBorderLeft(border1);
        table1.setBorderRight(border1);
        table1.setBorderTop(border1);
        table1.setMarginLeft(15);
        table1.setMarginRight(15);
        table1.setPadding(0f);

        table1.addCell(new Cell(1, 1).add(image1).setBorder(Border.NO_BORDER).setMarginLeft(10));
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getDspname()).setFontSize(11).setFont(bold).setBorder(Border.NO_BORDER)).setBorder(Border.NO_BORDER).setPadding(0)).add(new Paragraph(new Text("Routing code  : ").setFontSize(11).setFont(bold)).add(new Text(pdfShippingLabelResponse.getData().getRoutingcode()).setFontSize(11).setFont(font))).setBorder(Border.NO_BORDER).setPadding(0));
        table1.addCell(new Cell().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getInvoicedate()).setFontSize(11).setFont(font))).setBorder(Border.NO_BORDER));
//        table1.setMarginBottom(10);

//        table1.addCell(new Cell(4, 1).add(new Paragraph(new Text("Registered Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("No.19 Bishop Gerden, Raja Annamalaipuram, Chennai-600028").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("Admin Office: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("(For all correspondence) All towers, Floor No 55, Greams Road, Chennai-600006").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).add(new Paragraph(new Text("CIN : ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).add(new Text("U52500TN2016PLC111328").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font))).setBorder(Border.NO_BORDER));
//        String duplicate = isDuplicate ? "Duplicate Copy of Invoice" : "";
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text(duplicate).setFontSize(ITEXT_FONT_SIZE_SIX).setFont(font)).setMarginLeft(10)).setBorder(Border.NO_BORDER));
//
//        table1.addCell(new Cell(1, 2).add(new Paragraph(new Text("INVOICE").setFontSize(ITEXT_FONT_SIZE_EIGHT).setFont(bold)).setMarginLeft(10)).setBorder(Border.NO_BORDER));
//        float[] columnWidthnew = {580};//580
//        Table tableNew = new Table(columnWidthnew);
//        Border border2New = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
////        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        tableNew.setBorderLeft(border2New);
//        tableNew.setBorderRight(border2New);
////        table2.setBorderBottom(border2);
////        tableNew.setMarginLeft(100);
////        tableNew.setMarginRight(100);
//        tableNew.setBorder(Border.NO_BORDER);
//        tableNew.setMarginRight(15);
//        tableNew.setMarginLeft(15);
//        tableNew.addCell(new Cell(1, 1).add(new Paragraph(new Text("Routing code  : ").setFontSize(12).setFont(bold)).add(new Text("ROMS18874").setFontSize(12).setFont(font)).setMarginRight(50).setMarginLeft(50)).setBorder(Border.NO_BORDER));

        float[] columnWidth2 = {400, 180};// 580
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table2 = new Table(columnWidth2);
        Border border2 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table2.setBorderLeft(border2);
        table2.setBorderRight(border2);
        table2.setBorderBottom(border2);
        table2.setMarginLeft(15);
        table2.setMarginRight(15);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);

        Bitmap apolloLogoBitMapQr = null;
//        try {
//            apolloLogoBitMapQr = encodeAsBitmapBarcode("1234567890");
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            e.printStackTrace();
//        }
        apolloLogoBitMapQr = generateBarcode(pdfShippingLabelResponse.getData().getAwbno());
        //((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
        ByteArrayOutputStream stream1Qr = new ByteArrayOutputStream();
        apolloLogoBitMapQr.compress(Bitmap.CompressFormat.PNG, 100, stream1Qr);
        byte[] bitMapData1Qr = stream1Qr.toByteArray();

        ImageData imageData1Qr = ImageDataFactory.create(bitMapData1Qr);
        Image image1Qr = new Image(imageData1Qr);
        image1Qr.setWidth(350);
        image1Qr.setHeight(300);
        image1Qr.scaleToFit(100, 30);
        image1Qr.setHeight(30);
        table2.setHeight(60);

        table2.addCell(new Cell().add(image1Qr).setBorder(Border.NO_BORDER).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getAwbno()).setFontSize(10).setFont(font)).setMarginLeft(120)).setPaddingBottom(0f));

//
//        table2.addCell(new Cell(8, 1).add(new Paragraph(new Text("DELIVER TO: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).add(new Paragraph(new Text("Santosh kumar").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text("NEAR AQUA WATER PLANT ADARSH NAGAR, NIRMAL, TS,504106").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)).add(new Paragraph(new Text("CONTACT NO: 9550080255").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold)).setMarginLeft(30)));
//        table2.addCell(new Cell(8, 1).add(new Paragraph(new Text(""))).setBorder(Border.NO_BORDER));

        //        table2.setWidth(510);
        float[] columnWidth3 = {270, 310};//580
        Table table3 = new Table(columnWidth3);
        Border border3Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table3.setBorderLeft(border3Black);
        table3.setBorderRight(border3Black);
        table3.setBorderTop(border3Black);
        table3.setMarginLeft(15);
        table3.setMarginRight(15);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);
        table3.addCell(new Cell(1, 1).add(new Paragraph(new Text("Shipping Address: ").setFontSize(11).setFont(bold)).setMarginLeft(5)).setBorder(Border.NO_BORDER).setMarginLeft(5));
        table3.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getPaymentmode() + ": " + " ").setFontSize(10).setFont(bold)).add(new Text("" + pdfShippingLabelResponse.getData().getInvoiceamt()).setFontSize(11).setFont(font)).setMarginLeft(2)).setBorder(border3Black));
        Table tableAddress;
//        pdfShippingLabelResponse.getData().setQrcode("12345678920");
        if (pdfShippingLabelResponse.getData().getQrcode() != null && !pdfShippingLabelResponse.getData().getQrcode().equals("")) {
            float[] columnWidthAddress = {460, 120};//580
            tableAddress = new Table(columnWidthAddress);
            Border border2Address = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
            tableAddress.setBorderLeft(border2Address);
            tableAddress.setBorderRight(border2Address);
//        table2.setBorderBottom(border2);
//        tableNew.setMarginLeft(100);
//        tableNew.setMarginRight(100);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//        try {
//            encodeAsBitmap(pdfModelResponse).compress(Bitmap.CompressFormat.PNG, 100, stream2);
            QrCodeGeneration(pdfShippingLabelResponse.getData().getQrcode(), getActivity()).compress(Bitmap.CompressFormat.PNG, 100, stream2);
//        }
//        catch (WriterException e) {
//            e.printStackTrace();
//        }
            byte[] bitMapData2 = stream2.toByteArray();

            ImageData imageData2 = ImageDataFactory.create(bitMapData2);
            Image image2 = new Image(imageData2);
            image2.scaleToFit(80, 80);


            tableAddress.setBorder(Border.NO_BORDER);
            tableAddress.setMarginRight(15);
            tableAddress.setMarginLeft(15);
            tableAddress.addCell(new Cell(3, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getCustomername() + "\n" + pdfShippingLabelResponse.getData().getShippingaddress() + "" + pdfShippingLabelResponse.getData().getShippingcity() + "" + pdfShippingLabelResponse.getData().getShippingstateid() + "" + pdfShippingLabelResponse.getData().getShippingpincode()).setFont(font).setFontSize(11)).setMarginLeft(5)).setBorder(Border.NO_BORDER).setMarginLeft(5));
            tableAddress.addCell(new Cell(4, 1).add(image2).add(new Paragraph(new Text("Scan to pay via UPI").setFontSize(9.5f).setFont(font))).setBorder(Border.NO_BORDER));
        } else {
            float[] columnWidthAddress = {580};//580
            tableAddress = new Table(columnWidthAddress);
            Border border2Address = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
            tableAddress.setBorderLeft(border2Address);
            tableAddress.setBorderRight(border2Address);
//        table2.setBorderBottom(border2);
//        tableNew.setMarginLeft(100);
//        tableNew.setMarginRight(100);
            tableAddress.setBorder(Border.NO_BORDER);
            tableAddress.setMarginRight(15);
            tableAddress.setMarginLeft(15);
            tableAddress.addCell(new Cell(3, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getCustomername()).setFont(font).setFontSize(11)).setMarginLeft(5)).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getShippingaddress() + "" + pdfShippingLabelResponse.getData().getShippingcity() + "" + pdfShippingLabelResponse.getData().getShippingstateid() + "" + pdfShippingLabelResponse.getData().getShippingpincode()).setFontSize(11).setFont(font)).setMarginLeft(5)).setBorder(Border.NO_BORDER).setMarginLeft(5));

        }

        float[] columnWidthContactNo = {350, 230};//580
        Table tableContactNo = new Table(columnWidthContactNo);
        Border border3BlackContact = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        tableContactNo.setBorderLeft(border3BlackContact);
        tableContactNo.setBorderRight(border3BlackContact);
        tableContactNo.setBorderBottom(border3BlackContact);
        tableContactNo.setMarginLeft(15);
        tableContactNo.setMarginRight(15);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);
        tableContactNo.addCell(new Cell(1, 1).add(new Paragraph(new Text("Contact No: " + pdfShippingLabelResponse.getData().getPrimarycontactno()).setFontSize(11).setFont(font)).setMarginLeft(5)).setBorder(Border.NO_BORDER));
        tableContactNo.addCell(new Cell(1, 1).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getFullfillmentorderid()).setFont(font).setFontSize(11)).setMarginLeft(5)).setBorder(Border.NO_BORDER));

        float[] columnWidth4 = {580};//580
        Table table4 = new Table(columnWidth4);
        Border border4Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table4.setBorder(border4Black);
        table4.setMarginLeft(15);
        table4.setMarginRight(15);
        table4.setMarginTop(0);
        table4.setMarginBottom(0);
        table4.addCell(new Cell(2, 1).add(new Paragraph(new Text("Seller Address: " + "\n").setFontSize(11).setFont(bold)).setMarginLeft(5).add(new Text(pdfShippingLabelResponse.getData().getStoreaddress1() + pdfShippingLabelResponse.getData().getStoreaddress2() + pdfShippingLabelResponse.getData().getStoreaddress3()).setFont(font).setFontSize(10))).setPadding(0f).setMargin(0f));
        table4.setPadding(0f);

        //        table4.addCell(new Cell().add(new Paragraph(new Text("ORDER ID").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));

//        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

//        table4.addCell(new Cell().add(new Paragraph(new Text("WEIGHT (in GMS):- .00 appr.").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
//        table4.addCell(new Cell().setBorder(Border.NO_BORDER));

//        table4.addCell(new Cell().add(new Paragraph(new Text("A247-LHUB-DELHIVERY\nRouting code :").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder((new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F))));
//        table4.setMarginBottom(5);

//        float[] columnWidth5 = {580};// 580
//        Table table5 = new Table(columnWidth5);
//        Border border5 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
//        table5.setBorder(border5);
//        table5.addCell(new Cell(1, 1).add(new Paragraph(new Text("AMOUNT TO BE COLLECTED - Rs .00: ").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER));
//        table5.setMarginBottom(10);
//        table5.setMarginRight(50);
//        table5.setMarginLeft(50);

        float[] columnWidth6 = {290, 290};//580
        Table table6 = new Table(columnWidth6);
        Border border6 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table6.setBorder(border6);
        table6.setMarginLeft(15);
        table6.setMarginRight(15);
        table6.addCell(new Cell().add(new Paragraph(new Text("Product").setFontSize(11).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Price").setFontSize(11).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));

        table6.addCell(new Cell().add(new Paragraph(new Text("Medicine/Wellness/Hygiene").setFontSize(11).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getHealthWellness()).setFontSize(11).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text("Total (Inclusive all taxes)").setFontSize(11).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
        table6.addCell(new Cell().add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getInvoiceamt()).setFontSize(11).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("Shipping Charges").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("40.00").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("Total (Inclusive of all taxes)").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.addCell(new Cell().add(new Paragraph(new Text("317.84").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setTextAlignment(TextAlignment.CENTER).setBorder(border6));
//        table6.setMarginBottom(40);
//        table6.setMarginRight(30);
//        table6.setMarginLeft(30);

        float[] columnWidth7 = {400, 180};// 580
//        float columnWidth2[] = {150, 130, 115, 165};
        Table table7 = new Table(columnWidth7);
        Border border7 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        Bitmap apolloLogoBitMapQr2 = null;
//        try {
//            apolloLogoBitMapQr2 = encodeAsBitmapBarcode("12345678AB");
//        } catch (com.itextpdf.barcodes.qrcode.WriterException e) {
//            e.printStackTrace();
//        }
        apolloLogoBitMapQr2 = generateBarcode(pdfShippingLabelResponse.getData().getOrderid());
        //((BitmapDrawable) apolloLogoDrawableQr).getBitmap();
        ByteArrayOutputStream stream1Qr2 = new ByteArrayOutputStream();
        apolloLogoBitMapQr2.compress(Bitmap.CompressFormat.PNG, 100, stream1Qr2);
        byte[] bitMapData1Qr2 = stream1Qr2.toByteArray();

        ImageData imageData1Qr2 = ImageDataFactory.create(bitMapData1Qr2);
        Image image1Qr2 = new Image(imageData1Qr2);
        image1Qr2.setWidth(350);
        image1Qr2.setHeight(300);
        image1Qr2.scaleToFit(100, 30);
        image1Qr2.setHeight(30);
//        Border border2Black = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table7.setBorder(border7);
        table7.setMarginLeft(15);
        table7.setMarginRight(15);
        table7.setHeight(60);
//        table2.setMarginRight(35);
//        table2.setMarginLeft(35);
        table7.addCell(new Cell().add(image1Qr2).setMarginLeft(10).setBorder(Border.NO_BORDER).add(new Paragraph(new Text(pdfShippingLabelResponse.getData().getOrderid()).setFontSize(10).setFont(font)).setMarginLeft(120)).setPaddingBottom(0f));


//        float[] columnWidth7 = {290, 290};//580
//        Table table7 = new Table(columnWidth7);
//        table7.addCell(new Cell().add(new Paragraph(new Text("if undelivered,please return it to Apollo Pharmacy,").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));
//        table7.addCell(new Cell().add(new Paragraph(new Text("7-2-1740 ,KSSP WAREHOUSE COMPLEX, SANATH NAGAR ,OPP:FIRE STATION, HYDERABAD,500018").setFontSize(ITEXT_FONT_SIZE_SIX).setFont(bold))).setBorder(Border.NO_BORDER));


        float[] columnWidth8 = {580};//580
        Table table8 = new Table(columnWidth8);
        Border border8 = new SolidBorder(new DeviceRgb(0, 0, 0), 0.7F);
        table8.setBorder(border8);
        table8.setMarginLeft(15);
        table8.setMarginRight(15);
        table8.addCell(new Cell().add(new Paragraph(new Text("If Undelivered, please return it to the above seller address\n(APOLLO PHARMACY)").setFontSize(11).setFont(font))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));


        document.add(table1);
//        document.add(tableNew);
        document.add(table2);
//        document.add(new Paragraph(""));
        document.add(table3);
        document.add(tableAddress);
        document.add(tableContactNo);
        document.add(table4);
//        document.add(table5);
        document.add(table6);
        document.add(table7);
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
//        document.add(new Paragraph(""));
        document.add(table8);
    }

}
