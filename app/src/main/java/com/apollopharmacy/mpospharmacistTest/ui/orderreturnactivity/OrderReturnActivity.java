package com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.OrderReturnActiivtyBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.adapter.OrderReturnAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.adapter.PaidListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.OrderReturnModel;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.ViewAnimationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderReturnActivity extends BaseActivity implements OrederReturnMvpView {

    @Inject
    OrderReturnMvpPresenter<OrederReturnMvpView> mvpPresenter;
    OrderReturnActiivtyBinding orderReturnActiivtyBinding;
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


    public static Intent getStartIntent(Context context, CalculatePosTransactionRes model) {
        Intent intent = new Intent(context, OrderReturnActivity.class);
        intent.putExtra("order_history_info", model);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double diagonalInches = UiUtils.displaymetrics(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        orderReturnActiivtyBinding = DataBindingUtil.setContentView(this, R.layout.order_return_actiivty);
        getActivityComponent().inject(this);
        mvpPresenter.onAttach(OrderReturnActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        orderReturnActiivtyBinding.setCallback(mvpPresenter);
        orderReturnActiivtyBinding.setIsReturn(false);
        orderReturnModelList = new ArrayList<>();
        if (getIntent() != null) {
            orderHistoryItem = (CalculatePosTransactionRes) getIntent().getSerializableExtra("order_history_info");
            if (orderHistoryItem != null) {
                orderReturnActiivtyBinding.setItem(orderHistoryItem);
                orderReturnModelList.addAll(orderHistoryItem.getSalesLine());
                orderReturnActiivtyBinding.setProductCount(orderHistoryItem.getSalesLine().size());
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
                mvpPresenter.trackingWiseReturnAllowed(orderHistoryItem.getCorpCode());
            }

            if (mvpPresenter.DigitalReceiptRequired()) {
                orderReturnActiivtyBinding.smsSent.setEnabled(true);
                orderReturnActiivtyBinding.smsSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mvpPresenter.onSalesTrackingApiCall();
                    }
                });
            } else {
                orderReturnActiivtyBinding.smsSent.setEnabled(false);
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


        orderReturnActiivtyBinding.detailsLayout.detailsExpanCollapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    isExpand = false;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(orderReturnActiivtyBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.collapse(orderReturnActiivtyBinding.detailsLayout.customerDoctorLayout);
                } else {
                    isExpand = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(orderReturnActiivtyBinding.detailsLayout.expandCollapseIcon, "rotation", rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle % 360;
                    ViewAnimationUtils.expand(orderReturnActiivtyBinding.detailsLayout.customerDoctorLayout);
                }
            }
        });
        if (orderHistoryItem.getSalesLine().size() > 0) {
            orderReturnAdapter = new OrderReturnAdapter(this, orderHistoryItem.getSalesLine());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            orderReturnActiivtyBinding.orderreturnrecycle.setLayoutManager(mLayoutManager);
            orderReturnActiivtyBinding.orderreturnrecycle.setAdapter(orderReturnAdapter);
        }

        payActivityAdapter = new

                PaidListAdapter(this, arrPayAdapterModel);

        RecyclerView.LayoutManager mLayoutManagerOne = new LinearLayoutManager(this);
        orderReturnActiivtyBinding.payAmount.setLayoutManager(mLayoutManagerOne);
        orderReturnActiivtyBinding.payAmount.setItemAnimator(new

                DefaultItemAnimator());
        orderReturnActiivtyBinding.payAmount.addItemDecoration(new

                DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        orderReturnActiivtyBinding.payAmount.setAdapter(payActivityAdapter);
        if (mvpPresenter.enablescreens()) {
            mvpPresenter.onMposTabApiCall();
            turnOnScreen();
        }

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

        if (isAllowed && orderHistoryItem.getTransType() == 0) {
            orderReturnActiivtyBinding.corpReturnOptionsLayout.setVisibility(View.VISIBLE);
        } else {
            if (mvpPresenter.getGlobalConfing().isISHBPStore()) {
                orderReturnActiivtyBinding.corpReturnOptionsLayout.setVisibility(View.VISIBLE);
            } else {
                orderReturnActiivtyBinding.corpReturnOptionsLayout.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void setTransactionId(String transactionId) {
        orderHistoryItem.setReturnStore(mvpPresenter.getGlobalConfing().getStoreID());
        orderHistoryItem.setReturnTerminal(mvpPresenter.terminalId());
        orderHistoryItem.setReturnTransactionId(orderHistoryItem.getTransactionId());
        orderHistoryItem.setReturnReceiptId(orderHistoryItem.getReciptId());
        orderHistoryItem.setTransactionId(transactionId);
    }

    //feedback form diolg functionality....
    @Override
    public void Feedbackfromdialog() {
        String odrer_id = orderHistoryItem.getReciptId();
        String storeid = orderHistoryItem.getStore();
        String mobilenumber = orderHistoryItem.getMobileNO();
        mvpPresenter.showfeedbackformDialog(odrer_id, mobilenumber, storeid);

    }

    @Override
    public void showInfoPopup(String title, String message, boolean isCancelOrder, boolean isReturnAll, String terminalId) {
        if (orderHistoryItem.getTerminal().equalsIgnoreCase(terminalId)) {
            ExitInfoDialog dialogView = new ExitInfoDialog(this);
            dialogView.setTitle(title);
            dialogView.setPositiveLabel("OK");
            dialogView.setNegativeLabel("Cancel");
            dialogView.setSubtitle(message);
            dialogView.setDialogDismiss();
            dialogView.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                    if (isCancelOrder) {
                        try {
                            if (isCardPayment) {
                                if (mvpPresenter.getGlobalConfing().isISHBPStore() && cardWalletTransactionId) {
                                    orderHistoryItem.setReturnType(0);
                                    orderHistoryItem.setReturn(true);
                                    mvpPresenter.cancelDSBilling(orderHistoryItem);
                                } else {
                                    showCancelOrderSuccess("", "Card Cancellation Not Allowed!!");
                                }
                            } else if (isSmapay) {
                                showCancelOrderSuccess("", "Sms Pay Cancellation Not Allowed!!");
                            } else if (isHdfcPayment) {
                                showCancelOrderSuccess("", "Sms Pay Cancellation Not Allowed!!");
                            } else {
                                if (CommonUtils.checkCancelledDateTime(orderHistoryItem.getBusinessDate()) != 0) {
                                    showCancelOrderSuccess("", "Cancellation Not Allowed!!");
                                } else if (!mvpPresenter.isAllowOrNot(orderHistoryItem)) {
                                    showCancelOrderSuccess("", "Transaction Already Cancelled!!");
                                } else {
                                    orderHistoryItem.setReturnType(0);
                                    orderHistoryItem.setReturn(true);
                                    mvpPresenter.cancelDSBilling(orderHistoryItem);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (isReturnAll) {
                        if (mvpPresenter.isAllowOrNot(orderHistoryItem)) {
                            orderHistoryItem.setReturnType(0);
                            orderHistoryItem.setReturn(true);
                            mvpPresenter.orderReturnAll(orderHistoryItem);
//                            if (isCardPayment && isCashPayment) {
//                                if (mvpPresenter.getGlobalConfing().isISHBPStore() && cardWalletTransactionId) {
//                                    orderHistoryItem.setReturnType(0);
//                                    orderHistoryItem.setReturn(true);
//                                    mvpPresenter.orderReturnAll(orderHistoryItem);
//                                } else {
//                                    showCancelOrderSuccess("", "Return All Not Allowed!!");
//                                }
//                            } else if (isCardPayment){
//                                if (mvpPresenter.getGlobalConfing().isISHBPStore() && cardWalletTransactionId) {
//                                    orderHistoryItem.setReturnType(0);
//                                    orderHistoryItem.setReturn(true);
//                                    mvpPresenter.orderReturnAll(orderHistoryItem);
//                                } else {
//                                    showCancelOrderSuccess("", "Return All Not Allowed!!");
//                                }
//                            }else {
//                                orderHistoryItem.setReturnType(0);
//                                orderHistoryItem.setReturn(true);
//                                mvpPresenter.orderReturnAll(orderHistoryItem);
//                            }
                        } else {
                            showCancelOrderSuccess("", "Transaction Already Return!!");
                        }
                    }
                }
            });
            dialogView.setNegativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                }
            });
            dialogView.show();
            orderReturnActiivtyBinding.setIsReturn(false);
        } else {
            Toast.makeText(this, "Cannot Process order for this Terminal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showCancelOrderSuccess(String title, String message) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle(title);
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(message);
        dialogView.setDialogDismiss();
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                Intent intent = getIntent();
                intent.putExtra("isUpdated", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        dialogView.show();
    }

    @Override
    public void partialReturnOrder(String terminalId) {
        if (orderHistoryItem.getTerminal().equalsIgnoreCase(terminalId)) {
            if (!orderReturnActiivtyBinding.getIsReturn()) {
                for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
                    orderHistoryItem.getSalesLine().get(i).setReturnClick(true);
                    orderReturnAdapter.notifyItemChanged(i);
                }
                orderReturnActiivtyBinding.setIsReturn(true);
            } else {
                if (!isItemChecked()) {
                    ExitInfoDialog dialogView = new ExitInfoDialog(this);
                    dialogView.setTitle("Alert");
                    dialogView.setPositiveLabel("OK");
                    dialogView.setDialogDismiss();
                    dialogView.setSubtitle("Please select return item(s)");
                    dialogView.setPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogView.dismiss();
                        }
                    });
                    if (!dialogView.isDisplay())
                        dialogView.show();

                } else if (!isItemQuantity()) {
                    ExitInfoDialog dialogView = new ExitInfoDialog(this);
                    dialogView.setTitle("Alert");
                    dialogView.setDialogDismiss();
                    dialogView.setPositiveLabel("OK");
                    dialogView.setSubtitle("Return Quantity should be greater than 0 !");
                    dialogView.setPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogView.dismiss();
                        }
                    });
                    if (!dialogView.isDisplay())
                        dialogView.show();
                } else {
                    orderHistoryItem.setReturnType(1);
                    mvpPresenter.orderReturnAll(orderHistoryItem);
                }
            }
        } else {
            Toast.makeText(this, "Cannot Process order for this Terminal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAlreadyItemReturnedColor() {
    }

    @Override
    public CalculatePosTransactionRes calculations() {
        return orderHistoryItem;
    }

    private boolean isItemChecked() {
        boolean isValid = false;
        for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
            if (orderHistoryItem.getSalesLine().get(i).getIsChecked()) {
                return true;
            }
        }
        return isValid;
    }

    private boolean isItemQuantity() {
        boolean isValid = false;
        for (int i = 0; i < orderHistoryItem.getSalesLine().size(); i++) {
            if (orderHistoryItem.getSalesLine().get(i).getIsChecked()) {
                if (orderHistoryItem.getSalesLine().get(i).getReturnQty() != 0) {
                    isValid = true;
                } else {
                    isValid = false;
                    return isValid;
                }
            }
        }
        return isValid;
    }

    @Override
    public void onBackPressed() {
        onClickActionBarBack();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        orderReturnActiivtyBinding.imageView.setVisibility(View.GONE);
    }

    private List<RowsEntity> rowsEntitiesList;

    @Override
    public void onSucessPlayList() {
        rowsEntitiesList = mvpPresenter.getDataListEntity();
        for (int i = 0; i < rowsEntitiesList.size(); i++) {
            if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                    rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                File file = new File(path);
                if (!file.exists()) {
                    mvpPresenter.onDownloadApiCall(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
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
        orderReturnActiivtyBinding.imageView.setImageBitmap(myBitmap);
        orderReturnActiivtyBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        orderReturnActiivtyBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
                orderReturnActiivtyBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        orderReturnActiivtyBinding.imageView.setVisibility(View.GONE);
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


    @Nullable
    @Override
    public Context getContext() {
        return this;
    }
}
