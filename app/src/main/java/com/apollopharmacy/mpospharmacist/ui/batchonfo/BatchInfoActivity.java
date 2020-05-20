package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityBatchInfoBinding;
import com.apollopharmacy.mpospharmacist.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.adapter.BatchInfoAdapter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.lstener.BatchAdapterListener;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.Singletone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.inject.Inject;

public class BatchInfoActivity extends BaseActivity implements BatchInfoMvpView, BatchAdapterListener {

    @Inject
    BatchInfoMvpPresenter<BatchInfoMvpView> mPresenter;
    ActivityBatchInfoBinding batchInfoBinding;

    BatchInfoAdapter batchInfoAdapter;
    BatchInfoListAdapterBinding batchInfoListAdapterBinding;
    private ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList = new ArrayList<>();
    private ArrayList<GetBatchInfoRes.BatchListObj> selectedBatches = new ArrayList<>();
    private boolean isSelectedBatch = false;
    private SalesLineEntity selectedItem;
    private double selectedBatchQOH;
    private int enteredQuantity, batchQuantity;
    private int manualSelectedPosition = 0;
    private int batchServiceCall = 0;
    private int existingBatchServiceCall = 0;

    private ArrayList<SalesLineEntity> batchInfoProducts = new ArrayList<>();
    private ArrayList<SalesLineEntity> existingProductBatch = new ArrayList<>();

    public static Intent getStartIntent(Context context, SalesLineEntity items, TransactionIDResModel transactionID) {
        Intent intent = new Intent(context, BatchInfoActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("transaction_id", transactionID);
        bundle.putSerializable("selected_item", items);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        batchInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_batch_info);
        getActivityComponent().inject(this);
        mPresenter.onAttach(BatchInfoActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        batchInfoBinding.setCallback(mPresenter);
        TransactionIDResModel transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
        batchInfoBinding.setTransaction(transactionIdModel);
        selectedItem = (SalesLineEntity) getIntent().getSerializableExtra("selected_item");
        batchInfoAdapter = new BatchInfoAdapter(arrBatchList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        batchInfoBinding.batchInfoRecycler.setLayoutManager(mLayoutManager);
        batchInfoBinding.setProduct(selectedItem);
//        ((SimpleItemAnimator) batchInfoBinding.batchInfoRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
//        batchInfoBinding.batchInfoRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
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
                    editable.delete(0, 1);
                }
//                else if(editable.toString().startsWith("0")){
//                    editable.append("1");
//                }
                else if (!TextUtils.isEmpty(editable)) {
                    unSelectAll();
                }
                batchInfoBinding.inputQty.setSelection(editable.length());
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
    }

    private void unSelectAll(){
        for (int i = 0; i < arrBatchList.size(); i++) {
            if (arrBatchList.get(i).isSelected()) {
                arrBatchList.get(i).setSelected(false);
                arrBatchList.get(i).setREQQTY(0);
                batchInfoAdapter.notifyItemChanged(i);
            }
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
            }else{
                checkBatches();
            }
        }else{
            checkBatches();
        }
//        if(TextUtils.isEmpty(batchInfoBinding.inputQty.getText().toString())){
//            if (Singletone.getInstance().itemsArrayList.size() > 0) {
//                if (isProductExitsOrNot()) {
//                 //   productAllReadyExitsDialog(obj.getREQQTY());
//                } else {
//                    if(selectedBatches()){
//                        doneBatchSelect();
//                    }
//                }
//            } else {
//                if(selectedBatches()){
//                    doneBatchSelect();
//                }
//            }
//
////                    if(enteredQuantity <= Double.valueOf(arrBatchList.get(manualSelectedPosition).getQ_O_H()) ) {
////                        if (Singletone.getInstance().itemsArrayList.size() > 0) {
////                            if (isProductExitsOrNot()) {
////                                productAllReadyExitsDialog(enteredQuantity);
////                            } else {
////                                selectDynamicBatches(enteredQuantity);
////                            }
////                        } else {
////                            selectDynamicBatches(enteredQuantity);
////                        }
////                    }else{
////                        alertQuantityError("Qty Can't greater than QOH!!");
////                    }
//        }else{
//            if(Integer.valueOf(getRequiredQuantity()) != 0) {
//                if (Singletone.getInstance().itemsArrayList.size() > 0) {
//                    if (isProductExitsOrNot()) {
//                        productAllReadyExitsDialog(Integer.valueOf(getRequiredQuantity()));
//                    } else {
//                        selectDynamicBatches(Integer.valueOf(getRequiredQuantity()));
//                    }
//                } else {
//                    selectDynamicBatches(Integer.valueOf(getRequiredQuantity()));
//                }
//            }else{
//                alertQuantityError("Please Enter Valid Required Qty!!");
//            }
//        }
    }

    private void checkBatches(){
        if(TextUtils.isEmpty(batchInfoBinding.inputQty.getText().toString())){
            if(selectedBatches()){
                doneBatchSelect();
            }
        }else {
            if(globalBatches()){
                doneBatchSelect();
            }else{
                alertQuantityError("Please Check The QTY & Expiry Date!!");
            }
        }
    }

    private boolean selectedBatches(){
        selectedBatches.clear();
        for(GetBatchInfoRes.BatchListObj obj : arrBatchList){
            if(obj.isSelected()) {
                if (isValidQuantity(obj) && obj.getREQQTY() != 0)
                    selectedBatches.add(obj);
                else {
                    if(obj.getREQQTY() != 0) {
                        alertQuantityError("Qty Can't greater than QOH!!");
                        return false;
                    }
                }
            }
        }
        if(selectedBatches.size() == 0){
            alertQuantityError("Please Enter Valid Required Qty!!");
            return false;
        }
        return true;
    }

    private boolean globalBatches(){
        selectedBatches.clear();
        int totalQuantity = Integer.valueOf(batchInfoBinding.inputQty.getText().toString());
        for(GetBatchInfoRes.BatchListObj obj : arrBatchList){
            if(totalQuantity != 0 && !obj.getNearByExpiry()) {
                if (Double.valueOf(obj.getQ_O_H()) < totalQuantity) {
                    obj.setREQQTY((int)(Double.parseDouble(obj.getQ_O_H())));
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
            Collections.sort(arrBatchList, new Comparator<GetBatchInfoRes.BatchListObj>() {
                @Override
                public int compare(GetBatchInfoRes.BatchListObj o1, GetBatchInfoRes.BatchListObj o2) {
                    boolean b1 = o1.getNearByExpiry();
                    boolean b2 = o2.getNearByExpiry();
                    return Boolean.compare(b1, b2);
                }
            });
            batchInfoAdapter.notifyDataSetChanged();
          //  isSelectedBatch = true;
          //  selectBatch(0);
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
    }

    @Override
    public void onItemClick(int position, int quantity) {
        isSelectedBatch = true;
        manualSelectedPosition = position;
        enteredQuantity = quantity;
      //  selectBatch(quantity);
        for (int i = 0; i < arrBatchList.size(); i++) {
            if (arrBatchList.get(i).isSelected() && arrBatchList.get(i).getREQQTY() == 0 ) {
                arrBatchList.get(i).setSelected(false);
                batchInfoAdapter.notifyItemChanged(i);
            }
        }
        arrBatchList.get(position).setSelected(true);
        batchInfoAdapter.notifyItemChanged(position);
    }

    @Override
    public void onBatchQTYChange(int i, int quantity) {
        isSelectedBatch = true;
        manualSelectedPosition = i;
        enteredQuantity = quantity;
    }

    @Override
    public String getRequiredQuantity() {
        if (!TextUtils.isEmpty(batchInfoBinding.inputQty.getText().toString()))
            return batchInfoBinding.inputQty.getText().toString();
        else
            return "0";
    }

    @Override
    public void checkBatchInventorySuccess() {
//        if(existingProductBatch.size() > 0 && (existingProductBatch.size()-1) != existingBatchServiceCall) {
//            existingProductBatch.remove(existingBatchServiceCall);
//            existingBatchServiceCall++;
//            doneBatchSelect();
//        }else
        if (selectedBatches.size() > 0 && (selectedBatches.size() - 1) != batchServiceCall) {
            batchServiceCall++;
            doneBatchSelect();
        } else {
            for(GetBatchInfoRes.BatchListObj batchListObj : selectedBatches){
                    addBatch(batchListObj);
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selected_item", batchInfoProducts);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void addBatch(GetBatchInfoRes.BatchListObj batchListObj){
        boolean isUpdateExistBatch = false;
        for (int j=0; j<Singletone.getInstance().itemsArrayList.size(); j++ ) {
            SalesLineEntity s = Singletone.getInstance().itemsArrayList.get(j);
            if (selectedItem.getItemId().equalsIgnoreCase(s.getItemId()) && batchListObj.getBatchNo().equalsIgnoreCase(s.getInventBatchId())) {
                if (!s.getIsVoid()) {
                    Singletone.getInstance().itemsArrayList.get(j).setQty((batchListObj.getREQQTY() + s.getQty()));
                    isUpdateExistBatch = true;
                    break;
                }else{
                    Singletone.getInstance().itemsArrayList.get(j).setQty(batchListObj.getREQQTY());
                    Singletone.getInstance().itemsArrayList.get(j).setVoid(false);
                    isUpdateExistBatch = true;
                    break;
                }

            }
        }

        if(!isUpdateExistBatch)
            batchInfoProducts.add(newSalesLineEntity(batchListObj , batchInfoProducts.size() > 0 ? (selectedItem.getLineNo()+batchInfoProducts.size()): selectedItem.getLineNo()));
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

    private void doneBatchSelect() {
//        if(existingProductBatch.size() > 0 ){
//         //   mPresenter.checkBatchInventory(existingProductBatch.get(existingBatchServiceCall));
//        }else
            if (selectedBatches.size() > 0) {
            mPresenter.checkBatchInventory(selectedBatches.get(batchServiceCall));
        }
    }

    private SalesLineEntity newSalesLineEntity(GetBatchInfoRes.BatchListObj batch,int lineNumber){
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

    private void selectDynamicBatches(double totalQuantity) {
        batchQuantity = 0;
       // enteredQuantity = Integer.valueOf(getRequiredQuantity());
        batchInfoProducts.clear();
        existingProductBatch.clear();
        if(isSelectedBatch){
            double qoh = Double.parseDouble(arrBatchList.get(manualSelectedPosition).getQ_O_H());
            boolean isUpdateExistBatch = false;
            for (int j=0; j<Singletone.getInstance().itemsArrayList.size(); j++ ) {
                SalesLineEntity s = Singletone.getInstance().itemsArrayList.get(j);
                if (selectedItem.getItemId().equalsIgnoreCase(s.getItemId()) && arrBatchList.get(manualSelectedPosition).getBatchNo().equalsIgnoreCase(s.getInventBatchId())) {
                    if (!s.getIsVoid()) {
                        //  qoh = Double.parseDouble(s.getStockQty());
                       // existItemQTY = (int)s.getQty();
                        if (qoh <= totalQuantity)
                            Singletone.getInstance().itemsArrayList.get(j).setQty((int) (qoh + s.getQty()));
                        else
                            Singletone.getInstance().itemsArrayList.get(j).setQty((int) (totalQuantity+ s.getQty()) );
                        isUpdateExistBatch = true;
                        break;
                        // it.remove();
                    }else{
                        if (qoh <= totalQuantity)
                            Singletone.getInstance().itemsArrayList.get(j).setQty((int) (qoh ));
                        else
                            Singletone.getInstance().itemsArrayList.get(j).setQty((int) (totalQuantity) );
                        Singletone.getInstance().itemsArrayList.get(j).setVoid(false);
                        isUpdateExistBatch = true;
                        break;
                    }

                }
            }
            if (qoh <= totalQuantity)
                arrBatchList.get(manualSelectedPosition).setEnterReqQuantity((int) qoh);
            else
                arrBatchList.get(manualSelectedPosition).setEnterReqQuantity((int) totalQuantity);

            totalQuantity = totalQuantity - qoh;
            if(!isUpdateExistBatch)
                batchInfoProducts.add(updateBatchDetails(manualSelectedPosition, selectedItem.getLineNo()));
            else
                existingProductBatch.add(updateBatchDetails(manualSelectedPosition, selectedItem.getLineNo()));

        }
        if(totalQuantity > 0) {
            int lineNumber = selectedItem.getLineNo();
            for (int i = 0; i < arrBatchList.size(); i++) {
                if(isSelectedBatch){
                    if(manualSelectedPosition != i){
                        double qoh = Double.parseDouble(arrBatchList.get(i).getQ_O_H());
                        boolean isUpdateExistBatch = false;
                        for (int j=0; j<Singletone.getInstance().itemsArrayList.size(); j++ ) {
                            SalesLineEntity s = Singletone.getInstance().itemsArrayList.get(j);
                            if (selectedItem.getItemId().equalsIgnoreCase(s.getItemId()) && arrBatchList.get(manualSelectedPosition).getBatchNo().equalsIgnoreCase(s.getInventBatchId())) {
                                if (!s.getIsVoid()) {
                                    //  qoh = Double.parseDouble(s.getStockQty());
                                    // existItemQTY = (int)s.getQty();
                                    if (qoh <= totalQuantity)
                                        Singletone.getInstance().itemsArrayList.get(j).setQty((int) (qoh + s.getQty()));
                                    else
                                        Singletone.getInstance().itemsArrayList.get(j).setQty((int) (totalQuantity+ s.getQty()) );
                                    isUpdateExistBatch = true;
                                    break;
                                    // it.remove();
                                }else{
                                    if (qoh <= totalQuantity)
                                        Singletone.getInstance().itemsArrayList.get(j).setQty((int) (qoh ));
                                    else
                                        Singletone.getInstance().itemsArrayList.get(j).setQty((int) (totalQuantity) );
                                    Singletone.getInstance().itemsArrayList.get(j).setVoid(false);
                                    isUpdateExistBatch = true;
                                    break;
                                }

                            }
                        }
                        if (!arrBatchList.get(i).getNearByExpiry()) {
                            if (qoh <= totalQuantity)
                                arrBatchList.get(i).setEnterReqQuantity((int) qoh);
                            else
                                arrBatchList.get(i).setEnterReqQuantity((int) totalQuantity);

                            totalQuantity = totalQuantity - qoh;
                            if(!isUpdateExistBatch) {
                                lineNumber++;
                                batchInfoProducts.add(updateBatchDetails(i, lineNumber));
                            }else{
                                existingProductBatch.add(updateBatchDetails(i, lineNumber));
                            }
                            batchQuantity += arrBatchList.get(i).getEnterReqQuantity();
                        }
                    }
                }else{
                    double qoh = Double.parseDouble(arrBatchList.get(i).getQ_O_H());
                    boolean isUpdateExistBatch = false;
                    for (int j=0; j<Singletone.getInstance().itemsArrayList.size(); j++ ) {
                        SalesLineEntity s = Singletone.getInstance().itemsArrayList.get(j);
                        if (selectedItem.getItemId().equalsIgnoreCase(s.getItemId()) && arrBatchList.get(i).getBatchNo().equalsIgnoreCase(s.getInventBatchId())) {
                            if (!s.getIsVoid()) {
                                //  qoh = Double.parseDouble(s.getStockQty());
                                // existItemQTY = (int)s.getQty();
                                if (qoh <= totalQuantity)
                                    Singletone.getInstance().itemsArrayList.get(j).setQty((int) (qoh + s.getQty()));
                                else
                                    Singletone.getInstance().itemsArrayList.get(j).setQty((int) (totalQuantity+ s.getQty()));
                                isUpdateExistBatch = true;
                                break;
                                // it.remove();
                            }else{
                                if (qoh <= totalQuantity)
                                    Singletone.getInstance().itemsArrayList.get(j).setQty((int) (qoh ));
                                else
                                    Singletone.getInstance().itemsArrayList.get(j).setQty((int) (totalQuantity) );
                                Singletone.getInstance().itemsArrayList.get(j).setVoid(false);
                                isUpdateExistBatch = true;
                                break;
                            }

                        }
                    }
                    if (!arrBatchList.get(i).getNearByExpiry()) {
                        if (qoh <= totalQuantity)
                            arrBatchList.get(i).setEnterReqQuantity((int) qoh);
                        else
                            arrBatchList.get(i).setEnterReqQuantity((int) totalQuantity);

                        totalQuantity = totalQuantity - qoh;
                        if(i!=0) {
                            if(!isUpdateExistBatch) {
                                lineNumber++;
                                batchInfoProducts.add(updateBatchDetails(i, lineNumber));
                            }else{
                                existingProductBatch.add(updateBatchDetails(i, lineNumber));
                            }
                        }else{
                            if(!isUpdateExistBatch) {
                                batchInfoProducts.add(updateBatchDetails(i, selectedItem.getLineNo()));
                            }else{
                                existingProductBatch.add(updateBatchDetails(i, lineNumber));
                            }
                        }
                        batchQuantity += arrBatchList.get(i).getEnterReqQuantity();
                    }
                }
                if(totalQuantity <= 0){
                    break;
                }
            }
        }
        if(totalQuantity > 0){
            alertQuantityError("Please Check The QTY & Expiry Date!!");
        }else {
            doneBatchSelect();
        }
    }

    private SalesLineEntity updateBatchDetails(int i,int lineNumber){
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

    private void alertQuantityError(String message){
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
}
