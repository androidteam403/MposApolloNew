package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentEprescriptionlistBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoInfoActivity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.EprescriptionOrderListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter.CategorytypeFilter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter.CustomertypeFilter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter.OrderListRecycleAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter.OrderSourcetypeFilter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter.OrdertypeFiter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter.PaymenttypeFilter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.CategorytypeModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.CustomerTypeModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.Ordersourcetypemodel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OrdertypeModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.PaymenttypeModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.BottomSheetFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.FiltersReq;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.CustomTypeface;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.google.zxing.integration.android.IntentIntegrator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import javax.inject.Inject;

public class EprescriptionslistFragment extends BaseFragment implements EprescriptionsListMvpView, MainActivity.UserIneractionListener, MainActivity.OnBackPressedListener {
    @Inject
    EprescriptionsListMvpPresenter<EprescriptionsListMvpView> mPresenter;
    private FragmentEprescriptionlistBinding fragmentEprescriptionlistBinding;
    private ArrayList<CalculatePosTransactionRes> ordersModelArrayList = new ArrayList<>();
    private CalculatePosTransactionRes transactionRes;
    private FiltersReq filtersReq = new FiltersReq();
    private BottomSheetFragment bottomSheetFragment;
    public static final int REQUEST_CODE = 1;
    private OrderListRecycleAdapter orderListRecycleAdapter;
    private ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> ordersArrayList;
    private ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> tempordersArrayList = new ArrayList<>();

    private ArrayList<CorporateModel.DropdownValueBean> corporateList;
    private GetCustomerResponse.CustomerEntity customerEntity;
    CorporateModel corporateModel;
    TransactionIDResModel transactionIDResModel;

    private final int ACTIVITY_EPRESCRIPTIONLIST_DETAILS_CODE = 121;
    private final int ACTIVITY_BARCODESCANNER_DETAILS_CODE = 151;

    //private final int ACTIVITY_BARCODESCANNER_DETAILS_CODE = 121;

    private ConstraintLayout constraintLayout;

    boolean stockavailability_flag = false;
    boolean prority_flag = false;
    boolean pickinglist_flag = false;
    boolean packinglist_flag = false;
    // FiltersModel filters=new FiltersModel();
    ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> globaltemporderlist = new ArrayList<>();

    CustomertypeFilter customertypeFilter;
    CategorytypeFilter categorytypeFilter;
    OrderSourcetypeFilter orderSourcetypeFilter;
    OrdertypeFiter ordertypeFiter;
    PaymenttypeFilter paymenttypeFilter;
    EprescriptionsListMvpView eprescriptionsListMvpView;

    boolean filterslayoutvisibility = false;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, EprescriptionOrderListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, GetCustomerResponse.CustomerEntity customerEntity, CorporateModel corporateModel) {
        Intent intent = new Intent(context, EprescriptionOrderListActivity.class);
        intent.putExtra("customer_model", customerEntity);
        intent.putExtra("corporate_model", corporateModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }


    /* @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_for_fragment_name);
     }
 */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentEprescriptionlistBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_eprescriptionlist, container, false);

        getActivityComponent().inject(this);
        mPresenter.onAttach(EprescriptionslistFragment.this);

        /* Bundle bundle = this.getArguments();
        String barcodeStr = bundle.getString("BarcodeStr");
        if(barcodeStr.length() > 0)
        {
            fragmentEprescriptionlistBinding.searchOrderNumberEdit.setText(barcodeStr);
        }*/

        ((MainActivity) Objects.requireNonNull(getActivity())).setOnUserIneractionListener(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnBackPressedListener(this);

        return fragmentEprescriptionlistBinding.getRoot();

    }

    @Override
    protected void setUp(View view) {
        fragmentEprescriptionlistBinding.setCallbacks(mPresenter);
        fragmentEprescriptionlistBinding.setNoDataFound(false);
        setHasOptionsMenu(true);
        Constant.getInstance().frompickpakconform = false;
        Constant.getInstance().filtersModel.setStockstatus(false);
        Constant.getInstance().isomsorder_check = false;

        //constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);
        constraintLayout = (ConstraintLayout) getView().findViewById(R.id.constraint_layout);


        // constraintLayout = findViewById(R.id.constraint_layout);
        fragmentEprescriptionlistBinding.searchOrderNumberEdit.setText("");
        Constant.getInstance().BarcodeStr = "";

        ordersArrayList = new ArrayList<>();
        corporateList = new ArrayList<CorporateModel.DropdownValueBean>();
        corporateModel = new CorporateModel();
        mPresenter.getTransactionID();

        // corporateModel = (CorporateModel) getContext().getSerializableExtra("corporate_model");
        // customerEntity=(GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_model");

        orderListRecycleAdapter = new OrderListRecycleAdapter(getContext(), ordersArrayList, mPresenter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        fragmentEprescriptionlistBinding.orderRecycle.setLayoutManager(mLayoutManager);
        fragmentEprescriptionlistBinding.orderRecycle.setItemAnimator(new DefaultItemAnimator());
        fragmentEprescriptionlistBinding.orderRecycle.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));
        fragmentEprescriptionlistBinding.orderRecycle.setItemAnimator(new DefaultItemAnimator());
        fragmentEprescriptionlistBinding.orderRecycle.setAdapter(orderListRecycleAdapter);

        // mPresenter.fetchOMSOrderList();

        fragmentEprescriptionlistBinding.searchOrderNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    orderListRecycleAdapter.getFilter().filter(editable);
                } else {
                    orderListRecycleAdapter.getFilter().filter("");
                    Collections.sort(ordersArrayList, new sortItems());
                    orderListRecycleAdapter.notifyDataSetChanged();
                }
            }
        });


        filter_flags();

    }

    @Override
    public void clickOnApplyCommonfilter() {
        multplefilter_function();
        clickOnCancelCommonfilter();
    }

    @Override
    public void clickOnCancelCommonfilter() {
        isFilterScreen = false;
        filterslayoutvisibility = false;
        TranslateAnimation animate;
        if (fragmentEprescriptionlistBinding.filterslayout.getHeight() == 0) {
            fragmentEprescriptionlistBinding.parentLayout.getHeight(); // parent layout
            animate = new TranslateAnimation(0,
                    -1000, 0, 0);
        } else {
            animate = new TranslateAnimation(0, -1000, 0, 0); // View for animation
        }
        animate.setDuration(500);
        animate.setFillAfter(true);
        fragmentEprescriptionlistBinding.filterslayout.startAnimation(animate);
        fragmentEprescriptionlistBinding.filtersscrollview.setVisibility(View.GONE);
        fragmentEprescriptionlistBinding.filterslayout.setVisibility(View.GONE);

        // orderListRecycleAdapter.notifyDataSetChanged();
        if (ordersArrayList.size() == 0) {
            fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.VISIBLE);
        }


    }

    public void multplefilter_function() {
        //temporderlist=ordersArrayList;
        ordersArrayList.clear();
        tempordersArrayList.clear();
        int tempindex = 0;
        List<String> tempcustomertypefullfillmentid = new ArrayList<>();
        List<String> tempcategorytypefullfillmentid = new ArrayList<>();
        List<String> tempordersourcetypefullfillmentid = new ArrayList<>();
        List<String> tempordertypefullfillmentid = new ArrayList<>();
        List<String> temppaymenttypefullfillmentid = new ArrayList<>();
        List<String> commonfullfillmentid = new ArrayList<>();
        List<List<String>> mixedfullfillmentid = new ArrayList<List<String>>();

        //customer type filter functionality............................
        if (Constant.getInstance().Customertypearraylist.size() > 0) {
            if (Constant.getInstance().Customertypearraylist.contains("All")) {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                    if (Constant.getInstance().filtersModel.getStockstatus()) {
                        if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                            tempcustomertypefullfillmentid.add(obj.getREFNO());
                        }
                    } else {
                        tempcustomertypefullfillmentid.add(obj.getREFNO());
                    }
                }
            } else {
                for (String customertype : Constant.getInstance().Customertypearraylist) {
                    for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                        if (obj.getCustomerType().equalsIgnoreCase(customertype)) {
                            if (Constant.getInstance().filtersModel.getStockstatus()) {
                                if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                                    tempcustomertypefullfillmentid.add(obj.getREFNO());
                                }
                            } else {
                                tempcustomertypefullfillmentid.add(obj.getREFNO());
                            }
                        }

                    }

                }
            }
            if (tempcustomertypefullfillmentid.size() > 0) {
                mixedfullfillmentid.add(tempcustomertypefullfillmentid);
            }
            // Collections.sort(ordersArrayList, new sortItems());
            //  orderListRecycleAdapter.notifyDataSetChanged();
        }

        //category type filter functionality..................
        if (Constant.getInstance().Categorytypearraylist.size() > 0) {
            if (Constant.getInstance().Categorytypearraylist.contains("All")) {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                    if (Constant.getInstance().filtersModel.getStockstatus()) {
                        if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                            tempcategorytypefullfillmentid.add(obj.getREFNO());
                        }
                    } else {
                        tempcategorytypefullfillmentid.add(obj.getREFNO());
                    }
                }
            } else {
                for (String customertype : Constant.getInstance().Categorytypearraylist) {
                    for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                        if (obj.getCategoryType().equalsIgnoreCase(customertype)) {
                            if (Constant.getInstance().filtersModel.getStockstatus()) {
                                if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                                    tempcategorytypefullfillmentid.add(obj.getREFNO());
                                }
                            } else {
                                tempcategorytypefullfillmentid.add(obj.getREFNO());
                            }
                        }

                    }

                }
            }
            if (tempcategorytypefullfillmentid.size() > 0) {
                mixedfullfillmentid.add(tempcategorytypefullfillmentid);
            }
            //Collections.sort(ordersArrayList, new sortItems());
            // orderListRecycleAdapter.notifyDataSetChanged();
        }
        //order source type filter functionality....................
        if (Constant.getInstance().Ordersorcetypearraylist.size() > 0) {
            if (Constant.getInstance().Ordersorcetypearraylist.contains("All")) {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                    if (Constant.getInstance().filtersModel.getStockstatus()) {
                        if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                            tempordersourcetypefullfillmentid.add(obj.getREFNO());
                        }
                    } else {
                        tempordersourcetypefullfillmentid.add(obj.getREFNO());
                    }
                }
            } else {
                for (String customertype : Constant.getInstance().Ordersorcetypearraylist) {
                    for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                        if (obj.getOrderSource().equalsIgnoreCase(customertype)) {
                            if (Constant.getInstance().filtersModel.getStockstatus()) {
                                if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                                    tempordersourcetypefullfillmentid.add(obj.getREFNO());
                                }
                            } else {
                                tempordersourcetypefullfillmentid.add(obj.getREFNO());
                            }
                        }

                    }

                }
            }
            if (tempordersourcetypefullfillmentid.size() > 0) {
                mixedfullfillmentid.add(tempordersourcetypefullfillmentid);
            }
            //  Collections.sort(ordersArrayList, new sortItems());
            // orderListRecycleAdapter.notifyDataSetChanged();
        }

        //order type filter functionality.....................
        if (Constant.getInstance().Ordertypearraylist.size() > 0) {
            if (Constant.getInstance().Ordertypearraylist.contains("All")) {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                    if (Constant.getInstance().filtersModel.getStockstatus()) {
                        if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                            tempordertypefullfillmentid.add(obj.getREFNO());
                        }
                    } else {
                        tempordertypefullfillmentid.add(obj.getREFNO());
                    }
                }
            } else {
                for (String customertype : Constant.getInstance().Ordertypearraylist) {
                    for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                        if (obj.getOrderType().equalsIgnoreCase(customertype)) {
                            if (Constant.getInstance().filtersModel.getStockstatus()) {
                                if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                                    tempordertypefullfillmentid.add(obj.getREFNO());

                                }
                            } else {
                                tempordertypefullfillmentid.add(obj.getREFNO());

                            }
                        }

                    }

                }
            }

            if (tempordertypefullfillmentid.size() > 0) {
                mixedfullfillmentid.add(tempordertypefullfillmentid);
            }
            // Collections.sort(ordersArrayList, new sortItems());
            // orderListRecycleAdapter.notifyDataSetChanged();
        }

        //payment type filter functionality............................
        if (Constant.getInstance().paymenttypearraylist.size() > 0) {
            if (Constant.getInstance().paymenttypearraylist.contains("All")) {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                    if (Constant.getInstance().filtersModel.getStockstatus()) {
                        if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                            temppaymenttypefullfillmentid.add(obj.getREFNO());
                        }
                    } else {
                        temppaymenttypefullfillmentid.add(obj.getREFNO());
                    }
                }
            } else {
                for (String customertype : Constant.getInstance().paymenttypearraylist) {
                    for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                        if (obj.getPaymentSource().equalsIgnoreCase(customertype)) {
                            if (Constant.getInstance().filtersModel.getStockstatus()) {
                                if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                                    temppaymenttypefullfillmentid.add(obj.getREFNO());
                                }
                            } else {
                                temppaymenttypefullfillmentid.add(obj.getREFNO());
                            }
                        }

                    }

                }
            }
            if (temppaymenttypefullfillmentid.size() > 0) {
                mixedfullfillmentid.add(temppaymenttypefullfillmentid);
            }
            //   Collections.sort(ordersArrayList, new sortItems());
            //  orderListRecycleAdapter.notifyDataSetChanged();
        }

        if (Constant.getInstance().Customertypearraylist.size() == 0 && Constant.getInstance().Categorytypearraylist.size() == 0 && Constant.getInstance().Ordersorcetypearraylist.size() == 0 && Constant.getInstance().Ordertypearraylist.size() == 0 && Constant.getInstance().paymenttypearraylist.size() == 0) {
            for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                if (Constant.getInstance().filtersModel.getStockstatus()) {
                    if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                        ordersArrayList.add(obj);
                    }
                } else {
                    ordersArrayList.add(obj);
                }
            }
        } else {
            if (mixedfullfillmentid.size() > 0) {
                if (mixedfullfillmentid.get(0).size() > 0) {
                    commonfullfillmentid.addAll(mixedfullfillmentid.get(0));
                    for (ListIterator<List<String>> iter = mixedfullfillmentid.listIterator(0); iter.hasNext(); ) {
                        commonfullfillmentid.retainAll(iter.next());
                    }
                } else if (mixedfullfillmentid.get(1).size() > 0) {
                    commonfullfillmentid.addAll(mixedfullfillmentid.get(1));
                    for (ListIterator<List<String>> iter = mixedfullfillmentid.listIterator(1); iter.hasNext(); ) {
                        commonfullfillmentid.retainAll(iter.next());
                    }

                } else if (mixedfullfillmentid.get(2).size() > 0) {
                    commonfullfillmentid.addAll(mixedfullfillmentid.get(2));
                    for (ListIterator<List<String>> iter = mixedfullfillmentid.listIterator(2); iter.hasNext(); ) {
                        commonfullfillmentid.retainAll(iter.next());
                    }
                } else if (mixedfullfillmentid.get(3).size() > 0) {
                    commonfullfillmentid.addAll(mixedfullfillmentid.get(3));
                    for (ListIterator<List<String>> iter = mixedfullfillmentid.listIterator(3); iter.hasNext(); ) {
                        commonfullfillmentid.retainAll(iter.next());
                    }
                } else {
                    commonfullfillmentid.addAll(mixedfullfillmentid.get(4));
                    for (ListIterator<List<String>> iter = mixedfullfillmentid.listIterator(4); iter.hasNext(); ) {
                        commonfullfillmentid.retainAll(iter.next());
                    }
                }


                if (commonfullfillmentid.size() > 0) {
                    for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                        if (commonfullfillmentid.contains(obj.getREFNO())) {
                            ordersArrayList.add(obj);
                        }
                    }

                }
            } else {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                    if (Constant.getInstance().filtersModel.getStockstatus()) {
                        if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                            ordersArrayList.add(obj);
                        }
                    } else {
                        ordersArrayList.add(obj);
                    }
                }

            }


        }
        Collections.sort(ordersArrayList, new sortItems());
        orderListRecycleAdapter.notifyDataSetChanged();

        if (fragmentEprescriptionlistBinding.searchOrderNumberEdit.getText().length() > 0) {
            if (ordersArrayList.size() > 0 && mPresenter != null) {
                String fullfillmentid = fragmentEprescriptionlistBinding.searchOrderNumberEdit.getText().toString();
                orderListRecycleAdapter.getFilter().filter(fullfillmentid);
                // orderListRecycleAdapter.notifyDataSetChanged();
            }
        }

        if (ordersArrayList.size() > 0) {
            fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.GONE);
        } else {
            fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.VISIBLE);
        }

    }

    public void filter_flags() {
        fragmentEprescriptionlistBinding.stockavailabilitybutton.setOnClickListener(v -> {
            if (stockavailability_flag == false) {
                fragmentEprescriptionlistBinding.stockavailabilitybutton.setBackgroundResource(R.drawable.icon_points_allow);
                stockavailability_flag = true;
                Constant.getInstance().filtersModel.setStockstatus(true);
                multplefilter_function();

            } else {
                fragmentEprescriptionlistBinding.stockavailabilitybutton.setBackgroundResource(R.drawable.icon_unchecked_checkbox);
                stockavailability_flag = false;
                Constant.getInstance().filtersModel.setStockstatus(false);
                multplefilter_function();
            }

        });


        if (mPresenter.enablescreens()) {
            mPresenter.onMposTabApiCall();
            turnOnScreen();
        }
    }


    @Override
    public void updateProductsCount(int count) {
        if (count == 0) {
            fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.VISIBLE);
        } else {
            fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.GONE);
        }
    }


    public void stockavailability_filter() {
    }


    //scanner functionality........
    @Override
    public void onBarCodeClick() {
        Constant.getInstance().BarcodeStr = "";
        // startActivityForResult(ScannerActivity.getStartIntent(getContext()),ACTIVITY_BARCODESCANNER_DETAILS_CODE);
        //startActivityForResult(EPrescriptionInfoInfoActivity.getStartIntent(getContext(), item,transactionIDResModel,corporateModel),ACTIVITY_EPRESCRIPTIONLIST_DETAILS_CODE);
        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void showTransactionID(TransactionIDResModel model) {
        //  fragmentBillingBinding.setTransaction(model);
        // transactionIdItem = model;
        transactionIDResModel = model;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        //   inflater.inflate( menu);
        Typeface externalFont = Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem, externalFont);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi, externalFont);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void applyFontToMenuItem(MenuItem mi, Typeface font) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypeface("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onOrderItemClick(int position, OMSTransactionHeaderResModel.OMSHeaderObj item) {
        if (!filterslayoutvisibility) {
            if (item.getStockStatus().equalsIgnoreCase("NOT AVAILABLE")) {
                showSnackBar("Stock Not Available");
                //  UiUtils.showSnackbar(m, constraintLayout, "Stock Not Available");

            } else {

                // stockavailability_flag = false;
                prority_flag = false;
                pickinglist_flag = false;
                packinglist_flag = false;

                //  Constant.getInstance().Customertypearraylist.clear();

                Constant.getInstance().BarcodeStr = "";
                fragmentEprescriptionlistBinding.searchOrderNumberEdit.setText("");


                startActivityForResult(EPrescriptionInfoInfoActivity.getStartIntent(getContext(), item, transactionIDResModel, corporateModel), ACTIVITY_EPRESCRIPTIONLIST_DETAILS_CODE);
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 16908332) {
            doBack();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void noDataFound() {
        hideKeyboard();
        // ordersModelArrayList.clear();
        //ordersAdapter.notifyDataSetChanged();
        // fragmentEprescriptionlistBinding.setCount(ordersModelArrayList.size());
        fragmentEprescriptionlistBinding.setNoDataFound(true);
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
        fragmentEprescriptionlistBinding.imageView.setImageBitmap(myBitmap);
        fragmentEprescriptionlistBinding.imageView.setVisibility(View.VISIBLE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        ((MainActivity) getActivity()).closeDrawer();
        // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentEprescriptionlistBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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

        //  Constant.getInstance().Customertypearraylist.clear();

        stockavailability_flag = false;
        prority_flag = false;
        pickinglist_flag = false;
        packinglist_flag = false;
        //filter_flags();

        /*if (Constant.getInstance().frompickpakconform) {
            fragmentEprescriptionlistBinding.searchOrderNumberEdit.setText("");
        }*/

        fragmentEprescriptionlistBinding.stockavailabilitybutton.setBackgroundResource(R.drawable.icon_unchecked_checkbox);

        mPresenter.fetchOMSOrderList();

        if (ordersArrayList.size() > 0) {
            fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.GONE);
        } else {
            fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.VISIBLE);
        }

        Log.i("barcode value-->", Constant.getInstance().BarcodeStr);
        if (Constant.getInstance().BarcodeStr.length() > 0) {
            fragmentEprescriptionlistBinding.searchOrderNumberEdit.setText(Constant.getInstance().BarcodeStr);
        }

        if (ordersArrayList.size() > 0) {
            orderListRecycleAdapter.notifyDataSetChanged();
        }


        //  multplefilter_function();
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
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
                        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    }
                }
                handelPlayList();
                fragmentEprescriptionlistBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        fragmentEprescriptionlistBinding.imageView.setVisibility(View.GONE);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
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
    public void userInteraction() {
        stopHandler();
        startHandler();
    }

    public void turnOnScreen() {
        final Window win = getActivity().getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    public void customertypefilers() {


        //Customer type functionality...............
        ArrayList<CustomerTypeModel> customertypearray = new ArrayList<>();
        ArrayList<String> customertype = new ArrayList<>();
        for (OMSTransactionHeaderResModel.OMSHeaderObj item : globaltemporderlist) {
            if (item.getCustomerType().length() > 0) {
                customertype.add(item.getCustomerType());
            }

        }
        ArrayList<String> newcustomertypeList = new ArrayList<String>(new HashSet<String>(customertype));
        if (newcustomertypeList.size() > 0) {
            CustomerTypeModel customerTypeModel1 = new CustomerTypeModel();
            customerTypeModel1.setCustomertype("All");
            customerTypeModel1.setCheckstatus(false);
            customertypearray.add(customerTypeModel1);
            for (String customertype1 : newcustomertypeList) {
                CustomerTypeModel customerTypeModel = new CustomerTypeModel();
                customerTypeModel.setCustomertype(customertype1);
                customerTypeModel.setCheckstatus(false);
                customertypearray.add(customerTypeModel);
            }
            if (Constant.getInstance().Customertypearraylist != null && Constant.getInstance().Customertypearraylist.size() > 0) {
                for (String tempcustomertype : Constant.getInstance().Customertypearraylist) {
                    if (!customertype.contains(tempcustomertype)) {
                        //  Constant.getInstance().Customertypearraylist.remove(tempcustomertype);
                       /* CustomerTypeModel customerTypeModel = new CustomerTypeModel();
                        customerTypeModel.setCustomertype(tempcustomertype);
                        customerTypeModel.setCheckstatus(true);
                        customertypearray.add(customerTypeModel);*/
                    }

                }

            }

        }

        customertypeFilter = new CustomertypeFilter(getContext(), customertypearray, mPresenter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        fragmentEprescriptionlistBinding.customertypeFiltersview.setLayoutManager(mLayoutManager);
        customertypeFilter.onClickListener(eprescriptionsListMvpView);
        // fragmentEprescriptionlistBinding.customertypeFiltersview.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        fragmentEprescriptionlistBinding.customertypeFiltersview.setAdapter(customertypeFilter);

        //category type functionality..........
        ArrayList<CategorytypeModel> categorytpearray = new ArrayList<>();
        ArrayList<String> categorytype = new ArrayList<>();
        for (OMSTransactionHeaderResModel.OMSHeaderObj item : globaltemporderlist) {
            if (item.getCategoryType().length() > 0) {
                categorytype.add(item.getCategoryType());
            }

        }
        ArrayList<String> newcustomertypeList1 = new ArrayList<String>(new HashSet<String>(categorytype));
        if (newcustomertypeList1.size() > 0) {
            CategorytypeModel categorytypeModel1 = new CategorytypeModel();
            categorytypeModel1.setCategorytype("All");
            categorytypeModel1.setCheckstatus(false);
            categorytpearray.add(categorytypeModel1);
            for (String categorytypestr : newcustomertypeList1) {
                CategorytypeModel categorytypeModel = new CategorytypeModel();
                categorytypeModel.setCategorytype(categorytypestr);
                categorytypeModel.setCheckstatus(false);
                categorytpearray.add(categorytypeModel);
            }

            if (Constant.getInstance().Categorytypearraylist != null && Constant.getInstance().Categorytypearraylist.size() > 0) {
                for (String tempcategorytype : Constant.getInstance().Categorytypearraylist) {
                    if (!categorytype.contains(tempcategorytype)) {
                        // Constant.getInstance().Categorytypearraylist.remove(tempcategorytype);
                       /* CategorytypeModel categorytypeModel = new CategorytypeModel();
                        categorytypeModel.setCategorytype(tempcategorytype);
                        categorytypeModel.setCheckstatus(true);
                        categorytpearray.add(categorytypeModel);*/
                    }

                }

            }
        }

        categorytypeFilter = new CategorytypeFilter(getContext(), categorytpearray, mPresenter);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext());
        fragmentEprescriptionlistBinding.ordercategoryFiltersview.setLayoutManager(mLayoutManager1);
        customertypeFilter.onClickListener(eprescriptionsListMvpView);
        fragmentEprescriptionlistBinding.ordercategoryFiltersview.setAdapter(categorytypeFilter);

        //order Source type filter functionality..........................
        ArrayList<Ordersourcetypemodel> ordersourcetypearray = new ArrayList<>();
        ArrayList<String> ordersourcetype = new ArrayList<>();
        for (OMSTransactionHeaderResModel.OMSHeaderObj item : globaltemporderlist) {
            if (item.getOrderSource().length() > 0) {
                ordersourcetype.add(item.getOrderSource());
            }

        }
        ArrayList<String> newcustomertypeList2 = new ArrayList<String>(new HashSet<String>(ordersourcetype));
        if (newcustomertypeList2.size() > 0) {
            Ordersourcetypemodel ordersourcetypemodel1 = new Ordersourcetypemodel();
            ordersourcetypemodel1.setOrdersourcetype("All");
            ordersourcetypemodel1.setCheckstatus(false);
            ordersourcetypearray.add(ordersourcetypemodel1);
            for (String categorytypestr : newcustomertypeList2) {
                Ordersourcetypemodel ordersourcetypemodel = new Ordersourcetypemodel();
                ordersourcetypemodel.setOrdersourcetype(categorytypestr);
                ordersourcetypemodel.setCheckstatus(false);
                ordersourcetypearray.add(ordersourcetypemodel);
            }

            if (Constant.getInstance().Ordersorcetypearraylist != null && Constant.getInstance().Ordersorcetypearraylist.size() > 0) {
                for (String tempordersourcetype : Constant.getInstance().Ordersorcetypearraylist) {
                    if (!ordersourcetype.contains(tempordersourcetype)) {
                        // Constant.getInstance().Ordersorcetypearraylist.remove(tempordersourcetype);
                        /*Ordersourcetypemodel ordersourcetypemodel = new Ordersourcetypemodel();
                        ordersourcetypemodel.setOrdersourcetype(tempordersourcetype);
                        ordersourcetypemodel.setCheckstatus(true);
                        ordersourcetypearray.add(ordersourcetypemodel);*/
                    }

                }

            }
        }

        orderSourcetypeFilter = new OrderSourcetypeFilter(getContext(), ordersourcetypearray, mPresenter);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext());
        fragmentEprescriptionlistBinding.ordersourceFiltersview.setLayoutManager(mLayoutManager2);
        orderSourcetypeFilter.onClickListener(eprescriptionsListMvpView);
        fragmentEprescriptionlistBinding.ordersourceFiltersview.setAdapter(orderSourcetypeFilter);

        //order type filter functionality...............
        ArrayList<OrdertypeModel> ordertypearray = new ArrayList<>();
        ArrayList<String> ordertype = new ArrayList<>();
        for (OMSTransactionHeaderResModel.OMSHeaderObj item : globaltemporderlist) {
            if (item.getOrderType().length() > 0) {
                ordertype.add(item.getOrderType());
            }

        }
        ArrayList<String> newcustomertypeList3 = new ArrayList<String>(new HashSet<String>(ordertype));
        if (newcustomertypeList3.size() > 0) {
            OrdertypeModel ordertypeModel1 = new OrdertypeModel();
            ordertypeModel1.setOrdertype("All");
            ordertypeModel1.setCheckstatus(false);
            ordertypearray.add(ordertypeModel1);
            for (String categorytypestr : newcustomertypeList3) {
                OrdertypeModel ordertypeModel = new OrdertypeModel();
                ordertypeModel.setOrdertype(categorytypestr);
                ordertypeModel.setCheckstatus(false);
                ordertypearray.add(ordertypeModel);
            }

            if (Constant.getInstance().Ordertypearraylist != null && Constant.getInstance().Ordertypearraylist.size() > 0) {
                for (String tempordertype : Constant.getInstance().Ordertypearraylist) {
                    if (!ordertype.contains(tempordertype)) {
                        // Constant.getInstance().Ordertypearraylist.remove(tempordertype);
                       /* OrdertypeModel ordertypeModel = new OrdertypeModel();
                        ordertypeModel.setOrdertype(tempordertype);
                        ordertypeModel.setCheckstatus(true);
                        ordertypearray.add(ordertypeModel);*/
                    }

                }

            }
        }
        ordertypeFiter = new OrdertypeFiter(getContext(), ordertypearray, mPresenter);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getContext());
        fragmentEprescriptionlistBinding.ordertypeFiltersview.setLayoutManager(mLayoutManager3);
        ordertypeFiter.onClickListener(eprescriptionsListMvpView);
        fragmentEprescriptionlistBinding.ordertypeFiltersview.setAdapter(ordertypeFiter);

        //payment type filter functionality...................
        ArrayList<PaymenttypeModel> paymenttypearray = new ArrayList<>();
        ArrayList<String> paymenttypetype = new ArrayList<>();
        for (OMSTransactionHeaderResModel.OMSHeaderObj item : globaltemporderlist) {
            if (item.getPaymentSource().length() > 0) {
                paymenttypetype.add(item.getPaymentSource());
            }

        }
        ArrayList<String> newcustomertypeList4 = new ArrayList<String>(new HashSet<String>(paymenttypetype));
        if (newcustomertypeList4.size() > 0) {
            PaymenttypeModel paymenttypeModel1 = new PaymenttypeModel();
            paymenttypeModel1.setPaymenttype("All");
            paymenttypeModel1.setCheckstatus(false);
            paymenttypearray.add(paymenttypeModel1);
            for (String categorytypestr : newcustomertypeList4) {
                PaymenttypeModel paymenttypeModel = new PaymenttypeModel();
                paymenttypeModel.setPaymenttype(categorytypestr);
                paymenttypeModel.setCheckstatus(false);
                paymenttypearray.add(paymenttypeModel);
            }


            if (Constant.getInstance().paymenttypearraylist != null && Constant.getInstance().paymenttypearraylist.size() > 0) {
                for (String temppaymenttype : Constant.getInstance().paymenttypearraylist) {
                    if (!paymenttypetype.contains(temppaymenttype)) {
                        //  Constant.getInstance().paymenttypearraylist.remove(temppaymenttype);
                       /* PaymenttypeModel paymenttypeModel = new PaymenttypeModel();
                        paymenttypeModel.setPaymenttype(temppaymenttype);
                        paymenttypeModel.setCheckstatus(true);
                        paymenttypearray.add(paymenttypeModel);*/
                    }

                }

            }
        }
        paymenttypeFilter = new PaymenttypeFilter(getContext(), paymenttypearray, mPresenter);
        RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(getContext());
        fragmentEprescriptionlistBinding.paymenttypeFiltersview.setLayoutManager(mLayoutManager4);
        paymenttypeFilter.onClickListener(eprescriptionsListMvpView);
        fragmentEprescriptionlistBinding.paymenttypeFiltersview.setAdapter(paymenttypeFilter);

        multplefilter_function();
    }

    CustomertypeFilterDialog customertypeFilterDialog;
    boolean isFilterScreen = false;

    @Override
    public void customertypefilter() {
        isFilterScreen = true;
        filterslayoutvisibility = true;
        TranslateAnimation animate;
        if (fragmentEprescriptionlistBinding.filterslayout.getHeight() == 0) {
            fragmentEprescriptionlistBinding.parentLayout.getHeight(); // parent layout
            animate = new TranslateAnimation(-400,
                    0, 0, 0);
        } else {
            animate = new TranslateAnimation(-400, 0, 0, 0); // View for animation
        }
        animate.setDuration(500);
        animate.setFillAfter(true);
        fragmentEprescriptionlistBinding.filterslayout.startAnimation(animate);
        fragmentEprescriptionlistBinding.filtersscrollview.setVisibility(View.VISIBLE);
        fragmentEprescriptionlistBinding.filterslayout.setVisibility(View.VISIBLE);

        fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.GONE);

        //Using code...............................
      /*  customertypeFilterDialog = new CustomertypeFilterDialog(getContext());

        customertypeFilterDialog.setcustomertypeListArray(globaltemporderlist);

        customertypeFilterDialog.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customertypeFilterDialog.dismiss();
            }
        });

        customertypeFilterDialog.setApplyListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersArrayList.clear();
                if (Constant.getInstance().Customertypearraylist.size() > 0) {
                    if (Constant.getInstance().Customertypearraylist.contains("All")) {
                        for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                            if (Constant.getInstance().filtersModel.getStockstatus()) {
                                if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                                    ordersArrayList.add(obj);
                                }
                            } else {
                                ordersArrayList.add(obj);
                            }
                        }
                    } else {

                        for (String customertype : Constant.getInstance().Customertypearraylist) {
                            for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                                if (obj.getCustomerType().equalsIgnoreCase(customertype)) {
                                    if (Constant.getInstance().filtersModel.getStockstatus()) {
                                        if (obj.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
                                            ordersArrayList.add(obj);
                                        }
                                    } else {
                                        ordersArrayList.add(obj);
                                    }
                                }

                            }

                        }
                    }


                    Collections.sort(ordersArrayList, new sortItems());
                    orderListRecycleAdapter.notifyDataSetChanged();
                } else {
                    for (OMSTransactionHeaderResModel.OMSHeaderObj obj : globaltemporderlist) {
                        ordersArrayList.add(obj);
                    }
                    Collections.sort(ordersArrayList, new sortItems());
                    orderListRecycleAdapter.notifyDataSetChanged();
                }
                customertypeFilterDialog.dismiss();

                if (fragmentEprescriptionlistBinding.searchOrderNumberEdit.getText().length() > 0) {
                    orderListRecycleAdapter.getFilter().filter(fragmentEprescriptionlistBinding.searchOrderNumberEdit.getText().toString());
                }
            }
        });

        customertypeFilterDialog.show();*/

    }

    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void doBack() {
        if (isFilterScreen) {
            isFilterScreen = false;
            filterslayoutvisibility = false;
            TranslateAnimation animate;
            if (fragmentEprescriptionlistBinding.filterslayout.getHeight() == 0) {
                fragmentEprescriptionlistBinding.parentLayout.getHeight(); // parent layout
                animate = new TranslateAnimation(0,
                        -1000, 0, 0);
            } else {
                animate = new TranslateAnimation(0, -1000, 0, 0); // View for animation
            }
            animate.setDuration(500);
            animate.setFillAfter(true);
            fragmentEprescriptionlistBinding.filterslayout.startAnimation(animate);
            fragmentEprescriptionlistBinding.filtersscrollview.setVisibility(View.GONE);
            fragmentEprescriptionlistBinding.filterslayout.setVisibility(View.GONE);

            // orderListRecycleAdapter.notifyDataSetChanged();
            if (ordersArrayList.size() == 0) {
                fragmentEprescriptionlistBinding.orderNotFound.setVisibility(View.VISIBLE);
            }
        } else {
            //  Constant.getInstance().Customertypearraylist.clear();
            Constant.getInstance().filtersModel.setStockstatus(false);
            // getActivity().finish();
            stopLooping = true;

            Navigation.findNavController(fragmentEprescriptionlistBinding.stockvailabilitylabel).navigate(R.id.nav_dash_board);
        }
    }


    @Override
    public void onSuccessGetOMSTransactionList(OMSTransactionHeaderResModel response) {
        if (response.getOMSHeaderArr().size() > 0) {

            ordersArrayList.clear();
            globaltemporderlist.clear();
            // ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> temparray=response.getOMSHeaderArr();
            // ordersArrayList.addAll(response.getOMSHeaderArr());
            // orderListRecycleAdapter.notifyDataSetChanged();
            // Constant.getInstance().global_ordersArrayList = response.getOMSHeaderArr();
            ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> temporderlist = new ArrayList<>();
            if (Constant.getInstance().Orders_type.equalsIgnoreCase("Picking")) {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : response.getOMSHeaderArr()) {
                    /*if (obj.getOrderPickup()) {
                        temporderlist.add(obj);

                    }*/
                    if (!obj.getOrderPacked() && obj.getOrderPickup()) {
                        temporderlist.add(obj);
                    }
                }

                ordersArrayList.addAll(temporderlist);
                globaltemporderlist.addAll(temporderlist);
                // ordersArrayList.addAll(response.getOMSHeaderArr());
                Collections.sort(ordersArrayList, new sortItems());
                orderListRecycleAdapter.notifyDataSetChanged();
            } else if (Constant.getInstance().Orders_type.equalsIgnoreCase("Packing")) {
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : response.getOMSHeaderArr()) {
                    if (obj.getOrderPacked() && obj.getOrderPickup()) {
                        temporderlist.add(obj);

                    }
                }

                ordersArrayList.addAll(temporderlist);
                // ordersArrayList.addAll(response.getOMSHeaderArr());
                globaltemporderlist.addAll(temporderlist);
                Collections.sort(ordersArrayList, new sortItems());
                orderListRecycleAdapter.notifyDataSetChanged();

            } else if (Constant.getInstance().Orders_type.equalsIgnoreCase("EPrescription")) {
                int index = 0;
                for (OMSTransactionHeaderResModel.OMSHeaderObj obj : response.getOMSHeaderArr()) {
                    if (!obj.getOrderPickup() && !obj.getOrderPacked()) {
                        temporderlist.add(obj);

                    }
                }
                // ordersArrayList.addAll(response.getOMSHeaderArr());
                ordersArrayList.addAll(temporderlist);
                Collections.sort(ordersArrayList, new sortItems());
                globaltemporderlist.addAll(temporderlist);
                orderListRecycleAdapter.notifyDataSetChanged();

                if (Constant.getInstance().BarcodeStr.length() > 0) {
                    fragmentEprescriptionlistBinding.searchOrderNumberEdit.setText(Constant.getInstance().BarcodeStr);
                }


                Constant.getInstance().frompickpakconform = false;
                //fragmentEprescriptionlistBinding.searchOrderNumberEdit.setText("");

            }

            if (Constant.getInstance().filtersModel.getStockstatus()) {
                fragmentEprescriptionlistBinding.stockavailabilitybutton.setBackgroundResource(R.drawable.icon_points_allow);
                stockavailability_flag = true;
            }
            // if(Constant.getInstance().Customertypearraylist.size() > 0)
            //  {
            // mPresenter.clickOnCustomertypeFilter();
            customertypefilers();
            //  multplefilter_function();
            // }
            // Constant.getInstance().globamultplefilter_functionl_ordersArrayList.clear();
            //  Constant.getInstance().global_ordersArrayList=ordersArrayList;

        }
    }

    class sortItems implements Comparator<OMSTransactionHeaderResModel.OMSHeaderObj> {

        // Method of this class
        // @Override
        public int compare(OMSTransactionHeaderResModel.OMSHeaderObj a, OMSTransactionHeaderResModel.OMSHeaderObj b) {

            // Returning the value after comparing the objects
            // this will sort the data in Ascending order
            // return a.getCreatedDateTime().compareTo(b.getCreatedDateTime());
            //this will sort the data in Desceinding order
            return b.getCreatedDateTime().compareTo(a.getCreatedDateTime());
        }
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        this.corporateModel = corporateModel;
        corporateList.addAll(corporateModel.get_DropdownValue());
        //  mPresenter.fetchOMSOrderList();
        // fragmentBillingBinding.setCorporate(corporateModel.get_DropdownValue().get(0));
    }


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        Log.i("Activity result--->", String.valueOf(data));
       /* if (requestCode == ACTIVITY_EPRESCRIPTIONLIST_DETAILS_CODE) {
            if (resultCode == RESULT_OK) {
                if(data != null)
                {
                    System.out.println("Activity result--> eorderliat"+(ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item"));
                    ArrayList <SalesLineEntity> salesentity=new ArrayList<>();
                    CorporateModel.DropdownValueBean item = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
                    customerEntity=(GetCustomerResponse.CustomerEntity)data.getSerializableExtra("customer_info");
                    salesentity = (ArrayList<SalesLineEntity>) data.getSerializableExtra("selected_item");
                    OMSTransactionHeaderResModel.OMSHeaderObj orderinfoitem=(OMSTransactionHeaderResModel.OMSHeaderObj)data.getSerializableExtra("fullfillment_id");
                    CustomerDataResBean customerDataResBean=(CustomerDataResBean)data.getSerializableExtra("customer_bean");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("corporate_info", item);
                    bundle.putSerializable("selected_item", salesentity);
                    bundle.putSerializable("customer_info", customerEntity);
                    bundle.putSerializable("fullfillment_id", orderinfoitem);
                    bundle.putSerializable("customer_bean", customerDataResBean);
                    intent.putExtras(bundle);
                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                }

            }
        }
    }*/


}
