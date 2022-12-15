package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnbillprint;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityOrderReturnPrintBillBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.adapter.OrderReturnAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.adapter.PaidListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.OrderReturnModel;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;
import com.apollopharmacy.mpospharmacistTest.utils.EnglishNumberToWords;
import com.apollopharmacy.mpospharmacistTest.utils.ViewAnimationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.inject.Inject;

public class OrderReturnBillPrintActivity extends PDFCreatorActivity implements OrderReturnBillPrintMvpView {
    @Inject
    OrderReturnBillPrintMvpPresenter<OrderReturnBillPrintMvpView> mPresenter;
    private ActivityOrderReturnPrintBillBinding orderReturnPrintBillBinding;
    private ArrayList<SalesLineEntity> orderReturnModelList = null;
    private OrderReturnAdapter orderReturnAdapter;
    private boolean isExpand = false;
    private int rotationAngle = 180;
    private String rating = "10";
    private String comment = "";
    private CalculatePosTransactionRes orderHistoryItem = null;
    private PaidListAdapter payActivityAdapter;
    private ArrayList<OrderReturnModel> arrPayAdapterModel = new ArrayList<>();
    private boolean isCardPayment = false;
    private boolean isCashPayment = false;
    private boolean isSmapay = false;
    private boolean cardWalletTransactionId = false;
    private boolean isHdfcPayment = false;

    private PdfModelResponse pdfModelResponse;
    private String pdfTransactionId;

    public static Intent getStartIntent(Context context, CalculatePosTransactionRes model) {
        Intent intent = new Intent(context, OrderReturnBillPrintActivity.class);
        intent.putExtra("order_history_info", model);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderReturnPrintBillBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_return_print_bill);
        getActivityComponent().inject(OrderReturnBillPrintActivity.this);
        mPresenter.onAttach(OrderReturnBillPrintActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        orderReturnPrintBillBinding.setCallback(mPresenter);
        orderReturnPrintBillBinding.setIsReturn(false);
        orderReturnModelList = new ArrayList<>();
        if (getIntent() != null) {
            orderHistoryItem = (CalculatePosTransactionRes) getIntent().getSerializableExtra("order_history_info");
            if (orderHistoryItem != null) {
                this.pdfTransactionId = orderHistoryItem.getTransactionId();
                mPresenter.downloadPdf(pdfTransactionId);
                orderReturnPrintBillBinding.setItem(orderHistoryItem);
                orderReturnModelList.addAll(orderHistoryItem.getSalesLine());
                orderReturnPrintBillBinding.setProductCount(orderHistoryItem.getSalesLine().size());
                if (orderHistoryItem.getSalesLine().size() > 0) {
                    orderHistoryItem.setPharmaTotalAmount(0);
                    orderHistoryItem.setFmcgTotalAmount(0);
                    orderHistoryItem.setPlTotalAmount(0);
                    orderHistoryItem.setTaxableTotalAmount(0);
                    orderHistoryItem.setOrderTotalAmount(orderHistoryItem.getAmounttoAccount());
                    orderHistoryItem.setOrderSavingsAmount(orderHistoryItem.getDiscAmount() / orderHistoryItem.getGrossAmount() * 100);
                    for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
                        if (orderHistoryItem.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("P")) {
                            orderHistoryItem.setPharmaTotalAmount(orderHistoryItem.getPharmaTotalAmount() + orderHistoryItem.getSalesLine().get(i).getNetAmount());
                            orderHistoryItem.setTaxableTotalAmount(orderHistoryItem.getTaxableTotalAmount() + orderHistoryItem.getSalesLine().get(i).getTaxAmount());
                        } else if (orderHistoryItem.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("F")) {
                            orderHistoryItem.setFmcgTotalAmount(orderHistoryItem.getFmcgTotalAmount() + orderHistoryItem.getSalesLine().get(i).getNetAmount());
                            orderHistoryItem.setTaxableTotalAmount(orderHistoryItem.getTaxableTotalAmount() + orderHistoryItem.getSalesLine().get(i).getTaxAmount());
                        } else if (orderHistoryItem.getSalesLine().get(i).getCategoryCode().equalsIgnoreCase("A")) {
                            orderHistoryItem.setPlTotalAmount(orderHistoryItem.getPlTotalAmount() + orderHistoryItem.getSalesLine().get(i).getNetAmount());
                            orderHistoryItem.setTaxableTotalAmount(orderHistoryItem.getTaxableTotalAmount() + orderHistoryItem.getSalesLine().get(i).getTaxAmount());
                        }
                    }
                    getPaymentTypes();
                }
                mPresenter.trackingWiseReturnAllowed(orderHistoryItem.getCorpCode());
            }

            if (mPresenter.DigitalReceiptRequired()) {
                orderReturnPrintBillBinding.smsSent.setEnabled(true);
                orderReturnPrintBillBinding.smsSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onSalesTrackingApiCall();
                    }
                });
            } else {
                orderReturnPrintBillBinding.smsSent.setEnabled(false);
            }

          /*  orderReturnActiivtyBinding.feedBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderReturnActivity.this);

                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_feedbackform, viewGroup, false);
                    builder.setView(dialogView);
                    TextView receipt_id = (TextView) dialogView.findViewById(R.id.receiptid_text);
                    TextView mobilenumber = (TextView) dialogView.findViewById(R.id.mobilenumber);
                    EditText comment_text=(EditText) dialogView.findViewById(R.id.comment_edit_text);
                    receipt_id.setText(orderHistoryItem.getReciptId());
                    mobilenumber.setText(orderHistoryItem.getMobileNO());
                    TextView cancel_text = (TextView) dialogView.findViewById(R.id.dialogCloseBtn);
                    TextView Sumbmit_text = (TextView) dialogView.findViewById(R.id.submit_button);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    RelativeLayout excelleny_layout = (RelativeLayout) dialogView.findViewById(R.id.excellent_layout);
                    RelativeLayout verygood_layout = (RelativeLayout) dialogView.findViewById(R.id.verygood_layout);
                    RelativeLayout good_layout = (RelativeLayout) dialogView.findViewById(R.id.good_layout);
                    RelativeLayout satisfaction_layout = (RelativeLayout) dialogView.findViewById(R.id.satisfaction_layout);
                    RelativeLayout poor_layout = (RelativeLayout) dialogView.findViewById(R.id.poor_layout);
                     rating="5";
                    excelleny_layout.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                excelleny_layout.setAlpha(1.0f);
                                                                verygood_layout.setAlpha(0.3f);
                                                                good_layout.setAlpha(0.3f);
                                                                satisfaction_layout.setAlpha(0.3f);
                                                                poor_layout.setAlpha(0.3f);
                                                                rating="5";
                                                            }
                                                        }

                    );

                    verygood_layout.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                excelleny_layout.setAlpha(0.3f);
                                                                verygood_layout.setAlpha(1.0f);
                                                                good_layout.setAlpha(0.3f);
                                                                satisfaction_layout.setAlpha(0.3f);
                                                                poor_layout.setAlpha(0.3f);
                                                                rating="4";

                                                            }
                                                        }

                    );
                    good_layout.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               excelleny_layout.setAlpha(0.3f);
                                                               verygood_layout.setAlpha(0.3f);
                                                               good_layout.setAlpha(1.0f);
                                                               satisfaction_layout.setAlpha(0.3f);
                                                               poor_layout.setAlpha(0.3f);
                                                               rating="3";
                                                           }
                                                       }

                    );

                    satisfaction_layout.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           excelleny_layout.setAlpha(0.3f);
                                                           verygood_layout.setAlpha(0.3f);
                                                           good_layout.setAlpha(0.3f);
                                                           satisfaction_layout.setAlpha(1.0f);
                                                           poor_layout.setAlpha(0.3f);
                                                           rating="2";
                                                       }
                                                   }

                    );
                    poor_layout.setOnClickListener(new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View v) {
                                                                   excelleny_layout.setAlpha(0.3f);
                                                                   verygood_layout.setAlpha(0.3f);
                                                                   good_layout.setAlpha(0.3f);
                                                                   satisfaction_layout.setAlpha(0.3f);
                                                                   poor_layout.setAlpha(1.0f);
                                                                   rating="1";
                                                               }
                                                           }

                    );


                    cancel_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    Sumbmit_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Orderid=orderHistoryItem.getStore()+orderHistoryItem.getReciptId();
                            String comment=comment_text.getText().toString();
                            mvpPresenter.feebackapicall(Orderid,rating,comment);
                              alertDialog.dismiss();

                        }
                    });
                }
            });*/

        }


        if (orderHistoryItem.getSalesLine().size() > 0) {
            orderReturnAdapter = new OrderReturnAdapter(this, orderHistoryItem.getSalesLine());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            orderReturnPrintBillBinding.orderreturnrecycle.setLayoutManager(mLayoutManager);
            orderReturnPrintBillBinding.orderreturnrecycle.setAdapter(orderReturnAdapter);
        }

        payActivityAdapter = new

                PaidListAdapter(this, arrPayAdapterModel);

        RecyclerView.LayoutManager mLayoutManagerOne = new LinearLayoutManager(this);
        orderReturnPrintBillBinding.payAmount.setLayoutManager(mLayoutManagerOne);
        orderReturnPrintBillBinding.payAmount.setItemAnimator(new

                DefaultItemAnimator());
        orderReturnPrintBillBinding.payAmount.addItemDecoration(new

                DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        orderReturnPrintBillBinding.payAmount.setAdapter(payActivityAdapter);

        orderReturnPrintBillBinding.detailsLayout.detailsExpanCollapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    isExpand = false;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(orderReturnPrintBillBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.collapse(orderReturnPrintBillBinding.detailsLayout.customerDoctorLayout);
                } else {
                    isExpand = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(orderReturnPrintBillBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.expand(orderReturnPrintBillBinding.detailsLayout.customerDoctorLayout);
                }
            }
        });


    }

    private void getPaymentTypes() {
        arrPayAdapterModel.add(new OrderReturnModel("Total Amount", orderHistoryItem.getGrossAmount() - orderHistoryItem.getDiscAmount()));
        for (int i = 0; i < orderHistoryItem.getTenderLine().size(); i++) {
            OrderReturnModel orderReturnModel = new OrderReturnModel(orderHistoryItem.getTenderLine().get(i).getTenderName(), orderHistoryItem.getTenderLine().get(i).getAmountTendered());
            arrPayAdapterModel.add(orderReturnModel);
            if (orderHistoryItem.getTenderLine().get(i).getTenderId().equalsIgnoreCase("2") || orderHistoryItem.getTenderLine().get(i).getTenderName().equalsIgnoreCase("card")) {
                if (orderHistoryItem.getTenderLine().get(i).getWalletTransactionID().isEmpty()) {
                    cardWalletTransactionId = true;
                } else {
                    cardWalletTransactionId = false;
                }
                isCardPayment = true;
            }

            if (orderHistoryItem.getTenderLine().get(i).getTenderId().equalsIgnoreCase("34") || orderHistoryItem.getTenderLine().get(i).getTenderName().equalsIgnoreCase("HDFC PAYMENT")) {
                isHdfcPayment = true;
            }
            if (orderHistoryItem.getTenderLine().get(i).getTenderId().equalsIgnoreCase("1") || orderHistoryItem.getTenderLine().get(i).getTenderName().equalsIgnoreCase("Cash")) {
                isCashPayment = true;
            }
            if (orderHistoryItem.getTenderLine().get(i).getTenderId().equalsIgnoreCase("5") && orderHistoryItem.getTenderLine().get(i).getTenderName().equalsIgnoreCase("SMS PAY")) {

                isSmapay = true;
            }

        }
        if (orderHistoryItem.getRemainingamount() != 0) {
            arrPayAdapterModel.add(new OrderReturnModel("Pay Back Amount", orderHistoryItem.getRemainingamount()));
        }
    }

    @Override
    public void onClickActionBarBack() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void isCorpAllowedReturn(boolean isAllowed) {

    }

    @Override
    public void setTransactionId(String transactionId) {

    }

    @Override
    public void showInfoPopup(String title, String message, boolean isCancelOrder, boolean isReturnAll, String terminalId) {

    }

    @Override
    public void showCancelOrderSuccess(String title, String message) {

    }

    @Override
    public void partialReturnOrder(String terminalId) {

    }

    @Override
    public void onAlreadyItemReturnedColor() {

    }

    @Override
    public CalculatePosTransactionRes calculations() {
        return null;
    }

    @Override
    public void onSucessPlayList() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void Feedbackfromdialog() {

    }

    @Override
    public void onClickBillReprint() {
        if (isStoragePermissionGranted()) {
            openPdf();
        }
    }

    @Override
    public void onSuccessBillPrintResponse(PdfModelResponse pdfModelResponse) {
        this.pdfModelResponse = pdfModelResponse;

        if (pdfModelResponse != null) {
            if (orderReturnPrintBillBinding.layoutPdfPreview != null) {
                orderReturnPrintBillBinding.layoutPdfPreview.removeAllViews();
            }
            if (isStoragePermissionGranted()) {
                createPDF(pdfTransactionId, orderReturnPrintBillBinding.layoutPdfPreview, pdfModelResponse, new PDFUtil.PDFUtilListener() {
                    @Override
                    public void pdfGenerationSuccess(File savedPDFFile) {
//                    Toast.makeText(OrderReturnActivity.this, "PDF Created", Toast.LENGTH_SHORT).show();
                        hideLoading();
//                    if (isStoragePermissionGranted()) {
//                        openPdf();
//                    }

                    }

                    @Override
                    public void pdfGenerationFailure(Exception exception) {
                        hideLoading();
                        Toast.makeText(OrderReturnBillPrintActivity.this, "PDF NOT Created", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onFailureBillPrintResponse(PdfModelResponse body) {

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
//            openPdf();
            onSuccessBillPrintResponse(pdfModelResponse);
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    private void openPdf() {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), pdfTransactionId.concat(".pdf"));
        if (file.exists()) {
            //Button To start print

            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);

            PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
            String jobName = this.getString(R.string.app_name) + " Document";

            printManager.print(jobName, pda, builder.build());
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
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), pdfTransactionId.concat(".pdf"));

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
                        Toast.makeText(OrderReturnBillPrintActivity.this, "FileInputStream getting null", Toast.LENGTH_SHORT).show();
                    }

                    if (output != null) {
                        output.close();
                    } else {
                        Toast.makeText(OrderReturnBillPrintActivity.this, "FileOutStream getting null", Toast.LENGTH_SHORT).show();
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
            PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(pdfTransactionId + ".pdf").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
            callback.onLayoutFinished(pdi, true);
        }

    };

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
}