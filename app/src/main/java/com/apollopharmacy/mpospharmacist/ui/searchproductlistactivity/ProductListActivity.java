package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
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
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.scanner.ScannerActivity;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.adapter.ProductListAdapter;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import static com.apollopharmacy.mpospharmacist.root.ApolloMposApp.getContext;

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

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProductListActivity.class);
    }

    public static Intent getStartIntent(Context context, CorporateModel.DropdownValueBean corporateModule, TransactionIDResModel transactionID, String searchType) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("corporate_info", corporateModule);
        intent.putExtra("transaction_id", transactionID);
        intent.putExtra("search_type", searchType);
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
        productListActivityBinding.productRecycler.setItemAnimator(new DefaultItemAnimator());
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
                if (s.length() == 3 && isLoadApi && itemsArrayList.size() == 0) {
                    productListMvpPresenter.getProductDetails();
                } else if (s.length() >= 3 && !isLoadApi) {
                    productListAdapter.getFilter().filter(s);
                } else {
                    if (s.length() <= 2) {
                        updateProductsCount(0);
                        itemsArrayList.clear();
                        productListAdapter.notifyDataSetChanged();
                        isLoadApi = true;
                    }

                }
            }
        });
    }


    @Override
    public void onClickBackBtn() {
        onBackPressed();
    }

    @Override
    public void onClickProductItem(GetItemDetailsRes.Items item) {
        startActivityForResult(BatchInfoActivity.getStartIntent(this, item,productListActivityBinding.getTransaction()), ACTIVITY_RESULT_FOR_BATCH_INFO);
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
        isLoadApi = false;
        updateProductsCount(itemDetailsRes.getItemList().size());
        itemsArrayList.addAll(itemDetailsRes.getItemList());
        productListAdapter.notifyDataSetChanged();
        productListAdapter.getFilter().filter(productListActivityBinding.searchProductEditText.getText().toString());
    }

    @Override
    public void onFailedGetItems(GetItemDetailsRes itemDetailsRes) {

    }

    @Override
    public void updateProductsCount(int count) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_RESULT_FOR_BATCH_INFO) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    GetItemDetailsRes.Items items = (GetItemDetailsRes.Items) data.getSerializableExtra("selected_item");
                    Intent intent = new Intent();
                    intent.putExtra("selected_item", items);
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
}
