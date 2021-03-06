package com.apollopharmacy.mpospharmacistTest.ui.batchonfo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityBatchInfoBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.adapter.BatchInfoAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.expirymodel.ExpiryChangeRes;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.Singletone;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class BatchInfoActivity extends BaseActivity implements BatchInfoMvpView {

    @Inject
    BatchInfoMvpPresenter<BatchInfoMvpView> mPresenter;
    ActivityBatchInfoBinding batchInfoBinding;

    BatchInfoAdapter batchInfoAdapter;
    private ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList = new ArrayList<>();
    private ArrayList<GetBatchInfoRes.BatchListObj> selectedBatches = new ArrayList<>();
    private boolean isSelectedBatch = false;
    private SalesLineEntity selectedItem;
    private int enteredQuantity, batchQuantity;
    private int manualSelectedPosition = 0;
    private int batchServiceCall = 0;

    private ArrayList<SalesLineEntity> batchInfoProducts = new ArrayList<>();
    private ArrayList<SalesLineEntity> existingProductBatch = new ArrayList<>();

    public static Intent getStartIntent(Context context, SalesLineEntity items, TransactionIDResModel transactionID) {
        Intent intent = new Intent(context, BatchInfoActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("transaction_id", transactionID);
        bundle.putSerializable("selected_item", items);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }


    public static Intent getStartIntent(Context context, SalesLineEntity items, String isEprescriptionactivity, String transaction_id) {
        Intent intent = new Intent(context, BatchInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_item", items);
        bundle.putSerializable("isEprescription_Activity", isEprescriptionactivity);
        bundle.putSerializable("Etransaction_id", transaction_id);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }

        batchInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_batch_info);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BatchInfoActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        batchInfoBinding.siteName.setText(mPresenter.getStoreName());
        batchInfoBinding.siteId.setText(mPresenter.getStoreId());
        batchInfoBinding.terminalId.setText(mPresenter.getTerminalId());
        batchInfoBinding.setCallback(mPresenter);
        if (getIntent() != null) {
            if ((String) getIntent().getSerializableExtra("isEprescription_Activity") != null) {
                selectedItem = (SalesLineEntity) getIntent().getSerializableExtra("selected_item");
                String transactionId = (String) getIntent().getSerializableExtra("Etransaction_id");
                TransactionIDResModel transactionIdModel=new TransactionIDResModel();
                transactionIdModel.setTransactionID(transactionId);
                batchInfoBinding.setTransaction(transactionIdModel);
            } else {
                TransactionIDResModel transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
                selectedItem = (SalesLineEntity) getIntent().getSerializableExtra("selected_item");
                batchInfoBinding.setTransaction(transactionIdModel);
            }

        }


        batchInfoAdapter = new BatchInfoAdapter(arrBatchList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        batchInfoBinding.batchInfoRecycler.setLayoutManager(mLayoutManager);
        batchInfoBinding.setProduct(selectedItem);
        batchInfoBinding.batchInfoRecycler.setAdapter(batchInfoAdapter);
        SalesLineEntity selected_item = (SalesLineEntity) getIntent().getSerializableExtra("selected_item");
        mPresenter.getBatchDetailsApi(selected_item);

        batchInfoBinding.inputQty.setSelection(batchInfoBinding.inputQty.getText().toString().length());

        batchInfoBinding.inputQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 1 && editable.toString().startsWith("0")) {
                } else if (!TextUtils.isEmpty(editable)) {
                }
            }
        });

        batchInfoBinding.inputQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onNavigateNextActivity();
                    return true;
                }
                return false;
            }
        });
        if (mPresenter.enablescreens()) {
            mPresenter.onMposTabApiCall();
            turnOnScreen();
        }
    }

    @Override
    public void onIncrementClick() {
        if (!TextUtils.isEmpty(getRequiredQuantity())) {
            int quantity = Integer.parseInt(getRequiredQuantity());
            quantity++;
            String string = Integer.toString(quantity);
            batchInfoBinding.inputQty.setText(string);
        } else {
            batchInfoBinding.inputQty.setText("0");
        }
    }

    @Override
    public void onDecrementClick() {
        if (!TextUtils.isEmpty(getRequiredQuantity())) {
            int quantity = Integer.parseInt(getRequiredQuantity());
            if (quantity == 0) {
                String string = Integer.toString(quantity);
                batchInfoBinding.inputQty.setText(string);
            } else {
                quantity--;
                String string = Integer.toString(quantity);
                batchInfoBinding.inputQty.setText(string);
            }
        } else {
            batchInfoBinding.inputQty.setText("0");
        }
    }

    @Override
    public void onNavigateNextActivity() {
        if (Singletone.getInstance().itemsArrayList.size() > 0) {
            if (isProductExitsOrNot()) {
                productAllReadyExitsDialog();
            } else {
                checkBatches();
            }
        } else {
            checkBatches();
        }
    }

    private void checkBatches() {
        if (TextUtils.isEmpty(batchInfoBinding.inputQty.getText().toString()) || batchInfoBinding.inputQty.getText().toString().equalsIgnoreCase("0")) {
            if (selectedBatches()) {
                doneBatchSelect();
            }
        } else {
            if (globalBatches()) {
                doneBatchSelect();
            } else {
                alertQuantityError("Please Check The QTY & Expiry Date!!");
            }
        }
    }

    private boolean selectedBatches() {
        selectedBatches.clear();
        for (GetBatchInfoRes.BatchListObj obj : arrBatchList) {
            if (obj.isSelected()) {
                if (isValidQuantity(obj) && obj.getREQQTY() != 0)
                    selectedBatches.add(obj);
                else {
                    if (obj.getREQQTY() != 0) {
                        alertQuantityError("Qty Can't greater than QOH!!");
                        return false;
                    }
                }
            }
        }
        if (selectedBatches.size() == 0) {
            alertQuantityError("Please Enter Valid Required Qty!!");
            return false;
        }
        return true;
    }

    private boolean globalBatches() {
        selectedBatches.clear();
        double totalQuantity = Integer.valueOf(batchInfoBinding.inputQty.getText().toString());
        for (GetBatchInfoRes.BatchListObj obj : arrBatchList) {
            if (totalQuantity != 0 && !obj.getNearByExpiry()) {
                if (Double.valueOf(obj.getQ_O_H()) < totalQuantity) {
                    //obj.setREQQTY(Integer.parseInt(obj.getQ_O_H()));
                    obj.setREQQTY(Double.parseDouble(obj.getQ_O_H()));
                    selectedBatches.add(obj);
                } else {
                    obj.setREQQTY(totalQuantity);
                    selectedBatches.add(obj);
                }
                totalQuantity = (totalQuantity - obj.getREQQTY());
            }
        }
        return totalQuantity <= 0;
    }

    private boolean isValidQuantity(GetBatchInfoRes.BatchListObj batch) {
        return batch.getREQQTY() <= Double.valueOf(batch.getQ_O_H());
    }


    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void onSuccessBatchInfo(GetBatchInfoRes body) {
        if (body.getBatchList().size() > 0) {
            batchInfoBinding.bathNotFoundText.setVisibility(View.GONE);
            arrBatchList.addAll(body.getBatchList());
            grandTotalQoh(arrBatchList);
            Collections.sort(arrBatchList, new Comparator<GetBatchInfoRes.BatchListObj>() {
                @Override
                public int compare(GetBatchInfoRes.BatchListObj o1, GetBatchInfoRes.BatchListObj o2) {
                    boolean b1 = o1.getNearByExpiry();
                    boolean b2 = o2.getNearByExpiry();
                    return Boolean.compare(b1, b2);
                }
            });
            batchInfoAdapter.notifyDataSetChanged();
        } else {
            batchInfoBinding.bathNotFoundText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailedBatchInfo(GetBatchInfoRes body) {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        batchInfoBinding.imageView.setVisibility(View.GONE);
    }

    @Override
    public void batchItemInfo(GetBatchInfoRes.BatchListObj batchListObj) {
        batchInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public List<GetBatchInfoRes.BatchListObj> getbatchInfoRes() {
        return arrBatchList;
    }

    @Override
    public void checkExpiryDateChangeSuccess(ExpiryChangeRes expiryChangeRes) {
        if (expiryChangeRes.getBatchList().size() > 0) {
            batchInfoBinding.bathNotFoundText.setVisibility(View.GONE);
            for (int i = 0; i < arrBatchList.size(); i++) {
                arrBatchList.get(i).setBatchNo(expiryChangeRes.getBatchList().get(i).getBatchNo());
                arrBatchList.get(i).setCESSPerc(expiryChangeRes.getBatchList().get(i).getCESSPerc());
                arrBatchList.get(i).setCESSTaxCode(expiryChangeRes.getBatchList().get(i).getCESSTaxCode());
                arrBatchList.get(i).setCGSTPerc(expiryChangeRes.getBatchList().get(i).getCGSTPerc());
                arrBatchList.get(i).setCGSTTaxCode(expiryChangeRes.getBatchList().get(i).getCGSTTaxCode());
                arrBatchList.get(i).setIGSTPerc(expiryChangeRes.getBatchList().get(i).getIGSTPerc());
                arrBatchList.get(i).setIGSTTaxCode(expiryChangeRes.getBatchList().get(i).getIGSTTaxCode());
                arrBatchList.get(i).setISMRPChange(expiryChangeRes.getBatchList().get(i).isISMRPChange());
                arrBatchList.get(i).setItemID(expiryChangeRes.getBatchList().get(i).getItemID());
                arrBatchList.get(i).setExpDate(expiryChangeRes.getBatchList().get(i).getExpDate());
                arrBatchList.get(i).setMRP(expiryChangeRes.getBatchList().get(i).getMRP());
                arrBatchList.get(i).setTotalTax(expiryChangeRes.getBatchList().get(i).getTotalTax());
                arrBatchList.get(i).setNearByExpiry(expiryChangeRes.getBatchList().get(i).isNearByExpiry());
                arrBatchList.get(i).setPrice(expiryChangeRes.getBatchList().get(i).getPrice());
                arrBatchList.get(i).setQ_O_H(expiryChangeRes.getBatchList().get(i).getQ_O_H());
                arrBatchList.get(i).setREQQTY(expiryChangeRes.getBatchList().get(i).getREQQTY());
                arrBatchList.get(i).setSGSTPerc(expiryChangeRes.getBatchList().get(i).getSGSTPerc());
                arrBatchList.get(i).setSGSTTaxCode(expiryChangeRes.getBatchList().get(i).getSGSTTaxCode());
                arrBatchList.get(i).setSNO(expiryChangeRes.getBatchList().get(i).getSNO());
            }
            grandTotalQoh(arrBatchList);
            Collections.sort(arrBatchList, new Comparator<GetBatchInfoRes.BatchListObj>() {
                @Override
                public int compare(GetBatchInfoRes.BatchListObj o1, GetBatchInfoRes.BatchListObj o2) {
                    boolean b1 = o1.getNearByExpiry();
                    boolean b2 = o2.getNearByExpiry();
                    return Boolean.compare(b1, b2);
                }
            });
            batchInfoAdapter.notifyDataSetChanged();
        } else {
            batchInfoBinding.bathNotFoundText.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onItemClick(int position, int quantity, GetBatchInfoRes.BatchListObj batchListObj) {

        isSelectedBatch = true;
        manualSelectedPosition = position;
        enteredQuantity = quantity;
        for (int i = 0; i < arrBatchList.size(); i++) {
            if (arrBatchList.get(i).isSelected() && arrBatchList.get(i).getREQQTY() == 0) {
                arrBatchList.get(i).setSelected(false);
                batchInfoAdapter.notifyItemChanged(i);
            }
        }
        arrBatchList.get(position).setSelected(true);
        batchInfoAdapter.notifyItemChanged(position);
    }

    public void onClickExpireDate(int position) {

        Calendar c = Calendar.getInstance(Locale.ENGLISH);

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
            arrBatchList.get(position).setExpDate(selectedDate);
            arrBatchList.get(position).setNearByExpiry(false);
            arrBatchList.get(position).setISMRPChange(true);
            batchInfoAdapter.notifyDataSetChanged();
            mPresenter.expiryChangeApi();
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMinDate((long) (System.currentTimeMillis()));// - (1000 * 60 * 60 * 24 * 365.25 * 18)
        dialog.show();
    }

    @Override
    public void onItemExpiryClick(int position, int quantity) {
        onClickExpireDate(position);
    }

    @Override
    public String getRequiredQuantity() {
        if (!TextUtils.isEmpty(batchInfoBinding.inputQty.getText().toString()))
            return batchInfoBinding.inputQty.getText().toString();
        else
            return "0";
    }

    @Override
    public void checkBatchInventorySuccess(boolean isAlertDialog) {
        if (isAlertDialog) {
            alertIvalidBatch("Invalid Batch");
        } else {
            if (selectedBatches.size() > 0 && (selectedBatches.size() - 1) != batchServiceCall) {
                batchServiceCall++;
                doneBatchSelect();
            } else {
                for (GetBatchInfoRes.BatchListObj batchListObj : selectedBatches) {
                    if (batchListObj.getREQQTY() > 0) {
                        addBatch(batchListObj);
                    }
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_item", batchInfoProducts);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private void addBatch(GetBatchInfoRes.BatchListObj batchListObj) {
        boolean isUpdateExistBatch = false;
        for (int j = 0; j < Singletone.getInstance().itemsArrayList.size(); j++) {
            SalesLineEntity s = Singletone.getInstance().itemsArrayList.get(j);
            if (selectedItem.getItemId().equalsIgnoreCase(s.getItemId()) && batchListObj.getBatchNo().equalsIgnoreCase(s.getInventBatchId())) {
                if (!s.getIsVoid()) {
                    Singletone.getInstance().itemsArrayList.get(j).setQty(batchListObj.getREQQTY() + s.getQty());
                    isUpdateExistBatch = true;
                    break;
                } else {
                    Singletone.getInstance().itemsArrayList.get(j).setQty(batchListObj.getREQQTY());
                    Singletone.getInstance().itemsArrayList.get(j).setVoid(false);
                    isUpdateExistBatch = true;
                    break;
                }

            }
        }

        if (!isUpdateExistBatch)
            batchInfoProducts.add(newSalesLineEntity(batchListObj, batchInfoProducts.size() > 0 ? (selectedItem.getLineNo() + batchInfoProducts.size()) : selectedItem.getLineNo()));
    }

    @Override
    public void checkBatchInventoryFailed(String returnMessage) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("");
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(returnMessage);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                finish();
            }
        });
        dialogView.show();
    }

    private String batchNoTemp;
    private String result;
    private boolean navigate = false;


    private void doneBatchSelect() {
        boolean alertForm = false;
        boolean noalertForm = false;

        if (selectedBatches.size() > 0) {
            for (int i = 0; i < selectedBatches.size(); i++) {
                if (selectedBatches.get(i).getREQQTY() > 0) {
                    batchNoTemp = selectedBatches.get(i).getBatchNo();
                    result = batchNoTemp.trim();
                    if (result.equalsIgnoreCase(selectedBatches.get(i).getBatchNo())) {
                        noalertForm = true;
                    } else {
                        alertForm = true;
                        selectedBatches.get(i).setREQQTY(0);
                        arrBatchList.get(i).setREQQTY(0);
                        batchInfoAdapter.notifyDataSetChanged();
                    }
                }
            }
            if (alertForm && noalertForm) {
                mPresenter.checkBatchInventory(selectedBatches.get(batchServiceCall), alertForm);
            } else if (alertForm) {
                navigate = true;
                alertIvalidBatch("Invalid Batch");
            } else if (noalertForm) {
                mPresenter.checkBatchInventory(selectedBatches.get(batchServiceCall), alertForm);
            }
        } else {
            alertQuantityError("Please Enter Valid Required Qty!!");
        }
    }

    private void alertIvalidBatch(String message) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("");
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(message);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!navigate) {
                    if (selectedBatches.size() > 0 && (selectedBatches.size() - 1) != batchServiceCall) {
                        batchServiceCall++;
                        doneBatchSelect();
                    } else {
                        for (GetBatchInfoRes.BatchListObj batchListObj : selectedBatches) {
                            if (batchListObj.getREQQTY() > 0) {
                                addBatch(batchListObj);
                            }
                        }
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("selected_item", batchInfoProducts);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }


    private SalesLineEntity newSalesLineEntity(GetBatchInfoRes.BatchListObj batch, int lineNumber) {
        SalesLineEntity salesLineEntity = new SalesLineEntity();
        salesLineEntity.setAdditionaltax(0);
        salesLineEntity.setApplyDiscount(false);
        salesLineEntity.setBarcode("");
        salesLineEntity.setCategory(selectedItem.getCategory());
        salesLineEntity.setCategoryCode(selectedItem.getCategoryCode());
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
        salesLineEntity.setDiseaseType(selectedItem.getDiseaseType());
        salesLineEntity.setDPCO(selectedItem.getDPCO());
        salesLineEntity.setHsncode_In(selectedItem.getHsncode_In());
        salesLineEntity.setISPrescribed(0);
        salesLineEntity.setISReserved(false);
        salesLineEntity.setISStockAvailable(true);
        salesLineEntity.setItemId(selectedItem.getItemId());
        salesLineEntity.setItemName(selectedItem.getItemName());
        salesLineEntity.setLineNo(lineNumber);
        salesLineEntity.setLineDiscPercentage(0);
        salesLineEntity.setLinedscAmount(0);
        salesLineEntity.setLineManualDiscountAmount(0);
        salesLineEntity.setLineManualDiscountPercentage(0);
        salesLineEntity.setManufacturerCode(selectedItem.getManufacturerCode());
        salesLineEntity.setManufacturerName(selectedItem.getManufacturerName());
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
        salesLineEntity.setProductRecID(selectedItem.getProductRecID());
        salesLineEntity.setRemainderDays(0);
        salesLineEntity.setRemainingQty(0);
        salesLineEntity.setRetailCategoryRecID(selectedItem.getRetailCategoryRecID());
        salesLineEntity.setRetailMainCategoryRecID(selectedItem.getRetailMainCategoryRecID());
        salesLineEntity.setRetailSubCategoryRecID(selectedItem.getRetailSubCategoryRecID());
        salesLineEntity.setReturnQty(0);
        salesLineEntity.setScheduleCategory(selectedItem.getScheduleCategory());
        salesLineEntity.setScheduleCategoryCode(selectedItem.getScheduleCategoryCode());
        salesLineEntity.setStockQty(0);
        salesLineEntity.setSubCategory(selectedItem.getSubCategory());
        salesLineEntity.setSubCategoryCode(selectedItem.getSubCategory());
        salesLineEntity.setSubClassification(selectedItem.getSubClassification());
        salesLineEntity.setSubsitute(false);
        salesLineEntity.setSubstitudeItemId("");
        salesLineEntity.setTotalDiscAmount(0);
        salesLineEntity.setTotalDiscPct(0);
        salesLineEntity.setTotalRoundedAmount(0);
        salesLineEntity.setUnit("");
        salesLineEntity.setVariantId("");
        salesLineEntity.setVoid(false);
        salesLineEntity.setBaseAmount(batch.getMRP());
        salesLineEntity.setCGSTPerc(batch.getCGSTPerc());
        salesLineEntity.setCGSTTaxCode(batch.getCGSTTaxCode());
        salesLineEntity.setExpiry(batch.getExpDate());
        salesLineEntity.setIGSTPerc(batch.getIGSTPerc());
        salesLineEntity.setIGSTTaxCode(batch.getIGSTTaxCode());
        salesLineEntity.setInventBatchId(batch.getBatchNo());
        salesLineEntity.setMRP(batch.getMRP());
        salesLineEntity.setNetAmount(batch.getPrice());
        salesLineEntity.setNetAmountInclTax(batch.getMRP());
        salesLineEntity.setOriginalPrice(batch.getMRP());
        salesLineEntity.setPrice(batch.getPrice());
        salesLineEntity.setQty(batch.getREQQTY());
        salesLineEntity.setSGSTPerc(batch.getSGSTPerc());
        salesLineEntity.setSGSTTaxCode(batch.getSGSTTaxCode());
        salesLineEntity.setTax(batch.getTotalTax());
        salesLineEntity.setTaxAmount(batch.getTotalTax());
        salesLineEntity.setTotal(batch.getMRP());
        salesLineEntity.setTotalTax(batch.getTotalTax());
        salesLineEntity.setUnitPrice(batch.getMRP());
        salesLineEntity.setUnitQty(batch.getREQQTY());
        return salesLineEntity;
    }

    private boolean isProductExitsOrNot() {
        for (SalesLineEntity oldItems : Singletone.getInstance().itemsArrayList) {
            if (selectedItem.getItemId().equalsIgnoreCase(oldItems.getItemId())) {
                return true;
            }
        }
        return false;
    }

    private void productAllReadyExitsDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Item Already Exist");
        dialogView.setPositiveLabel("Yes");
        dialogView.setSubtitle("Do you want to continue?");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                checkBatches();
            }
        });
        dialogView.setNegativeLabel("No");
        dialogView.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }


    public void grandTotalQoh(ArrayList<GetBatchInfoRes.BatchListObj> items) {
        double totalPrice = 0.0;
        for (int i = 0; i < items.size(); i++) {
            totalPrice += Double.valueOf(items.get(i).getQ_O_H());
        }
        batchInfoBinding.qohCoount.setText(String.valueOf(totalPrice));
    }


    private SalesLineEntity updateBatchDetails(int i, int lineNumber) {
        SalesLineEntity salesLineEntity = new SalesLineEntity();
        salesLineEntity.setAdditionaltax(0);
        salesLineEntity.setApplyDiscount(false);
        salesLineEntity.setBarcode("");
        salesLineEntity.setCategory(selectedItem.getCategory());
        salesLineEntity.setCategoryCode(selectedItem.getCategoryCode());
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
        salesLineEntity.setDiseaseType("Acute");
        salesLineEntity.setDPCO(false);
        salesLineEntity.setHsncode_In(selectedItem.getHsncode_In());
        salesLineEntity.setISPrescribed(0);
        salesLineEntity.setISReserved(false);
        salesLineEntity.setISStockAvailable(false);
        salesLineEntity.setItemId(selectedItem.getItemId());
        salesLineEntity.setItemName(selectedItem.getItemName());
        salesLineEntity.setLineNo(lineNumber);
        salesLineEntity.setLineDiscPercentage(0);
        salesLineEntity.setLinedscAmount(0);
        salesLineEntity.setLineManualDiscountAmount(0);
        salesLineEntity.setLineManualDiscountPercentage(0);
        salesLineEntity.setManufacturerCode(selectedItem.getManufacturerCode());
        salesLineEntity.setManufacturerName(selectedItem.getManufacturerName());
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
        salesLineEntity.setProductRecID(selectedItem.getProductRecID());
        salesLineEntity.setRemainderDays(0);
        salesLineEntity.setRemainingQty(0);
        salesLineEntity.setRetailCategoryRecID(selectedItem.getRetailCategoryRecID());
        salesLineEntity.setRetailMainCategoryRecID(selectedItem.getRetailMainCategoryRecID());
        salesLineEntity.setRetailSubCategoryRecID(selectedItem.getRetailSubCategoryRecID());
        salesLineEntity.setReturnQty(0);
        salesLineEntity.setScheduleCategory(selectedItem.getScheduleCategory());
        salesLineEntity.setScheduleCategoryCode(selectedItem.getScheduleCategoryCode());
        salesLineEntity.setStockQty(0);
        salesLineEntity.setSubCategory(selectedItem.getSubCategory());
        salesLineEntity.setSubCategoryCode(selectedItem.getSubCategory());
        salesLineEntity.setSubClassification(selectedItem.getSubClassification());
        salesLineEntity.setSubsitute(false);
        salesLineEntity.setSubstitudeItemId("");
        salesLineEntity.setTotalDiscAmount(0);
        salesLineEntity.setTotalDiscPct(0);
        salesLineEntity.setTotalRoundedAmount(0);
        salesLineEntity.setUnit("");
        salesLineEntity.setVariantId("");
        salesLineEntity.setVoid(false);
        salesLineEntity.setBaseAmount(arrBatchList.get(i).getMRP());
        salesLineEntity.setCGSTPerc(arrBatchList.get(i).getCGSTPerc());
        salesLineEntity.setCGSTTaxCode(arrBatchList.get(i).getCGSTTaxCode());
        salesLineEntity.setExpiry(arrBatchList.get(i).getExpDate());
        salesLineEntity.setIGSTPerc(arrBatchList.get(i).getIGSTPerc());
        salesLineEntity.setIGSTTaxCode(arrBatchList.get(i).getIGSTTaxCode());
        salesLineEntity.setInventBatchId(arrBatchList.get(i).getBatchNo());
        salesLineEntity.setMRP(arrBatchList.get(i).getMRP());
        salesLineEntity.setNetAmount(arrBatchList.get(i).getPrice());
        salesLineEntity.setNetAmountInclTax(arrBatchList.get(i).getMRP());
        salesLineEntity.setOriginalPrice(arrBatchList.get(i).getMRP());
        salesLineEntity.setPrice(arrBatchList.get(i).getPrice());
        salesLineEntity.setQty(arrBatchList.get(i).getEnterReqQuantity());
        salesLineEntity.setSGSTPerc(arrBatchList.get(i).getSGSTPerc());
        salesLineEntity.setSGSTTaxCode(arrBatchList.get(i).getSGSTTaxCode());
        salesLineEntity.setTax(arrBatchList.get(i).getTotalTax());
        salesLineEntity.setTaxAmount(arrBatchList.get(i).getTotalTax());
        salesLineEntity.setTotal(arrBatchList.get(i).getMRP());
        salesLineEntity.setTotalTax(arrBatchList.get(i).getTotalTax());
        salesLineEntity.setUnitPrice(arrBatchList.get(i).getMRP());
        salesLineEntity.setUnitQty(arrBatchList.get(i).getREQQTY());
        return salesLineEntity;
    }

    private void alertQuantityError(String message) {
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("");
        dialogView.setPositiveLabel("OK");
        dialogView.setSubtitle(message);
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                //finish();
            }
        });
        dialogView.show();
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
        batchInfoBinding.imageView.setImageBitmap(myBitmap);
        batchInfoBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        batchInfoBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
                }
                handelPlayList();
                batchInfoBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        batchInfoBinding.imageView.setVisibility(View.GONE);
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
