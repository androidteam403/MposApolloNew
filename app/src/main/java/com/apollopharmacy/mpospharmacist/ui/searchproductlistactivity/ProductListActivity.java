package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ProductListActivityBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.adapter.ProductListAdapter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;
import com.apollopharmacy.mpospharmacist.utils.Singletone;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class ProductListActivity extends BaseActivity implements ProductListMvpView {
    @Inject
    ProductListMvpPresenter<ProductListMvpView> productListMvpPresenter;
    ProductListActivityBinding productListActivityBinding;
    private ProductListAdapter productListAdapter;
    private ArrayList<GetItemDetailsRes.Items> itemsArrayList = new ArrayList<>();
    private boolean isLoadApi = true;
    private int ACTIVITY_RESULT_FOR_BATCH_INFO = 103;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 106;
    private final int REQ_CODE_SPEECH_INPUT = 107;
    private String SEARCH_TYPE = "";
    private boolean isListFiltered = false;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProductListActivity.class);
    }

    public static Intent getStartIntent(Context context, CorporateModel.DropdownValueBean corporateModule, TransactionIDResModel transactionID, String searchType) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("corporate_info", corporateModule);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("search_type", searchType);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productListActivityBinding = DataBindingUtil.setContentView(this, R.layout.product_list_activity);
        getActivityComponent().inject(this);
        productListMvpPresenter.onAttach(ProductListActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        productListActivityBinding.siteName.setText(productListMvpPresenter.getStoreName());
        productListActivityBinding.siteId.setText(productListMvpPresenter.getStoreId());
        productListActivityBinding.terminalId.setText(productListMvpPresenter.getTerminalId());
        productListActivityBinding.pdialog.setVisibility(View.INVISIBLE);
        productListActivityBinding.pdialog.setMax(100);
        productListActivityBinding.pdialog.setProgress(20);
        productListActivityBinding.setCallback(productListMvpPresenter);
        TransactionIDResModel transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
        productListActivityBinding.setTransaction(transactionIdModel);
        if (getIntent() != null) {
            SEARCH_TYPE = (String) getIntent().getSerializableExtra("search_type");
            if (SEARCH_TYPE != null) {
                if (SEARCH_TYPE.equalsIgnoreCase("1")) {
                    //Manual Search
                } else if (SEARCH_TYPE.equalsIgnoreCase("2")) {
                    requestAudioPermissions();
                } else if (SEARCH_TYPE.equalsIgnoreCase("3")) {
                    new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }
        }
        productListAdapter = new ProductListAdapter(this, itemsArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        productListActivityBinding.productRecycler.setLayoutManager(mLayoutManager);
        productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
        productListActivityBinding.productRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        // productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
        productListAdapter.setClickListiner(this);
        productListActivityBinding.productRecycler.setAdapter(productListAdapter);
        productListActivityBinding.searchProductEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        productListActivityBinding.searchProductEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    productListMvpPresenter.getProductDetails();
                    productListActivityBinding.pdialog.setVisibility(View.VISIBLE);
                    isListFiltered = false;
                } else {
                    productListActivityBinding.setProductCount(0);
                    itemsArrayList.clear();
                    productListAdapter.clearDate();
                }
//                else if (s.length() >= 3 && !isLoadApi) {
//                    isListFiltered = true;
//                    productListAdapter.getFilter().filter(s);
//                } else {
//                    productListActivityBinding.setProductCount(0);
//                    itemsArrayList.clear();
//                    productListAdapter.clearDate();
//                    isLoadApi = true;
//                    productListActivityBinding.itemNotFound.setVisibility(View.GONE);
//                }
            }
        });

        productListActivityBinding.productRecycler.setOnTouchListener((v, event) -> {
            CommonUtils.hideKeyboard(ProductListActivity.this);
            return false;
        });

        productListActivityBinding.searchProductEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    productListMvpPresenter.getProductDetails();
                    return true;
                }
                return false;
            }
        });
        productListMvpPresenter.onMposTabApiCall();
        turnOnScreen();
    }


    @Override
    public void onClickBackBtn() {
        onBackPressed();
    }

    @Override
    public void onClickProductItem(GetItemDetailsRes.Items items) {
        CommonUtils.hideKeyboard(ProductListActivity.this);
        SalesLineEntity salesLineEntity = new SalesLineEntity();
        salesLineEntity.setAdditionaltax(0);
        salesLineEntity.setApplyDiscount(false);
        salesLineEntity.setBarcode("");
        salesLineEntity.setCategory(items.getCategory());
        salesLineEntity.setCategoryCode(items.getCategoryCode());
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
        salesLineEntity.setDiseaseType(items.getDiseaseType());
        salesLineEntity.setDPCO(items.getDPCO());
        salesLineEntity.setHsncode_In(items.getHsncode_In());
        salesLineEntity.setISPrescribed(0);
        salesLineEntity.setISReserved(false);
        salesLineEntity.setISStockAvailable(true);
        salesLineEntity.setItemId(items.getArtCode());
        salesLineEntity.setItemName(items.getDescription());
        salesLineEntity.setLineNo(Singletone.getInstance().itemsArrayList.size() + 1);
        salesLineEntity.setLineDiscPercentage(0);
        salesLineEntity.setLinedscAmount(0);
        salesLineEntity.setLineManualDiscountAmount(0);
        salesLineEntity.setLineManualDiscountPercentage(0);
        salesLineEntity.setManufacturerCode(items.getManufactureCode());
        salesLineEntity.setManufacturerName(items.getManufacture());
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
        salesLineEntity.setProductRecID(items.getProductRecID());
        salesLineEntity.setRemainderDays(0);
        salesLineEntity.setRemainingQty(0);
        salesLineEntity.setRetailCategoryRecID(items.getRetailCategoryRecID());
        salesLineEntity.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
        salesLineEntity.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
        salesLineEntity.setReturnQty(0);
        salesLineEntity.setScheduleCategory(items.getSch_Catg());
        salesLineEntity.setScheduleCategoryCode(items.getSch_Catg_Code());
        salesLineEntity.setStockQty(0);
        salesLineEntity.setSubCategory(items.getSubCategory());
        salesLineEntity.setSubCategoryCode(items.getSubCategory());
        salesLineEntity.setSubClassification(items.getSubClassification());
        salesLineEntity.setSubsitute(false);
        salesLineEntity.setSubstitudeItemId("");
        salesLineEntity.setTotalDiscAmount(0);
        salesLineEntity.setTotalDiscPct(0);
        salesLineEntity.setTotalRoundedAmount(0);
        salesLineEntity.setUnit("");
        salesLineEntity.setVariantId("");
        salesLineEntity.setVoid(false);

        startActivityForResult(BatchInfoActivity.getStartIntent(this, salesLineEntity, productListActivityBinding.getTransaction()), ACTIVITY_RESULT_FOR_BATCH_INFO);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public String getSearchProductKey() {
        if (TextUtils.isEmpty(Objects.requireNonNull(productListActivityBinding.searchProductEditText.getText()).toString())) {
            setEmptyErrorOnSearch("Enter product Name");
            return null;
        } else {
            productListActivityBinding.inputLayoutSearch.setError(null);
        }
        return productListActivityBinding.searchProductEditText.getText().toString();
    }

    @Override
    public CorporateModel.DropdownValueBean getCorporateValue() {
        return (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
    }

    @Override
    public void setEmptyErrorOnSearch(String message) {
        productListActivityBinding.inputLayoutSearch.setError(message);
    }

    @Override
    public void onSuccessGetItems(GetItemDetailsRes itemDetailsRes) {
        if (itemDetailsRes.getItemList().size() > 0) {
            itemsArrayList.clear();
            productListActivityBinding.itemNotFound.setVisibility(View.GONE);
            productListActivityBinding.pdialog.setVisibility(View.INVISIBLE);
            updateProductsCount(itemDetailsRes.getItemList().size());
            itemsArrayList.addAll(itemDetailsRes.getItemList());
            productListAdapter.clearDate();
            productListAdapter.add(itemDetailsRes.getItemList());
            //   productListAdapter.notifyDataSetChanged();
            isLoadApi = false;
            //   productListAdapter.getFilter().filter(productListActivityBinding.searchProductEditText.getText().toString());
        } else {
            productListAdapter.clearDate();
            productListActivityBinding.pdialog.setVisibility(View.INVISIBLE);
            productListActivityBinding.itemNotFound.setVisibility(View.GONE);
            updateProductsCount(0);
            isLoadApi = false;
        }
        //   productListAdapter.getFilter().filter(productListActivityBinding.searchProductEditText.getText().toString());
    }

    @Override
    public void onFailedGetItems(GetItemDetailsRes itemDetailsRes) {
        updateProductsCount(0);
    }

    @Override
    public void updateProductsCount(int count) {
        if (count == 0) {
//            if (!isListFiltered) {
//                itemsArrayList.clear();
//                productListAdapter.notifyDataSetChanged();
//            } else {
            // hideKeyboard();
            productListActivityBinding.pdialog.setVisibility(View.INVISIBLE);
            productListActivityBinding.itemNotFound.setVisibility(View.VISIBLE);
            //  }
        } else {
            productListActivityBinding.itemNotFound.setVisibility(View.GONE);
            productListActivityBinding.pdialog.setVisibility(View.INVISIBLE);
        }
        productListActivityBinding.setProductCount(count);
    }

    @Override
    public void onVoiceSearchClick() {
        requestAudioPermissions();
    }

    @Override
    public void onBarCodeClick() {
        new IntentIntegrator(this).setCaptureActivity(ScannerActivity.class).initiateScan();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        productListActivityBinding.imageView.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_RESULT_FOR_BATCH_INFO) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    ArrayList<GetItemDetailsRes.Items> items = (ArrayList<GetItemDetailsRes.Items>) data.getSerializableExtra("selected_item");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selected_item", items);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        } else if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String searchedProductName = result.get(0);
                productListActivityBinding.searchProductEditText.setText(searchedProductName);
                productListMvpPresenter.getProductDetails();
            }
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                } else {
                    String searchedProductName = result.getContents();
                    productListActivityBinding.searchProductEditText.setText(searchedProductName);
                    productListMvpPresenter.getProductDetails();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            //Go ahead with recording audio now
            promptSpeechInput();
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }

    private List<RowsEntity> rowsEntitiesList;

    @Override
    public void onSucessPlayList() {
        rowsEntitiesList = productListMvpPresenter.getDataListEntity();
        for (int i = 0; i < rowsEntitiesList.size(); i++) {
            if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                    rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                File file = new File(path);
                if (!file.exists()) {
                    productListMvpPresenter.onDownloadApiCall(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
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
        productListActivityBinding.imageView.setImageBitmap(myBitmap);
        productListActivityBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        productListActivityBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
                productListActivityBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        productListActivityBinding.imageView.setVisibility(View.GONE);
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
        handler.postDelayed(r, 60 * 1000);
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
