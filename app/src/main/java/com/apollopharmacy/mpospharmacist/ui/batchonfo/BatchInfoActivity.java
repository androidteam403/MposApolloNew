package com.apollopharmacy.mpospharmacist.ui.batchonfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityBatchInfoBinding;
import com.apollopharmacy.mpospharmacist.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.adapter.BatchInfoAdapter;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.lstener.BatchAdapterListener;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.BatchInfoAdapterPojo;
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
    private boolean isSelectedBatch = false;
    private GetItemDetailsRes.Items selectedItem;
    private double selectedBatchQOH ;

    private ArrayList<GetItemDetailsRes.Items> batchInfoProducts = new ArrayList<>();

    public static Intent getStartIntent(Context context, GetItemDetailsRes.Items items, TransactionIDResModel transactionID) {
        Intent intent = new Intent(context, BatchInfoActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("transaction_id", transactionID);
        bundle.putSerializable("selected_item",items);
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
        selectedItem = (GetItemDetailsRes.Items) getIntent().getSerializableExtra("selected_item");
        batchInfoAdapter = new BatchInfoAdapter( arrBatchList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        batchInfoBinding.batchInfoRecycler.setLayoutManager(mLayoutManager);
        batchInfoBinding.setProduct(selectedItem);
//        ((SimpleItemAnimator) batchInfoBinding.batchInfoRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
//        batchInfoBinding.batchInfoRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        batchInfoBinding.batchInfoRecycler.setAdapter(batchInfoAdapter);
        GetItemDetailsRes.Items selected_item = (GetItemDetailsRes.Items) getIntent().getSerializableExtra("selected_item");
        mPresenter.getBatchDetailsApi(selected_item);

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
                    editable.delete(0,1);
                }else if(!TextUtils.isEmpty(editable)){
                    quantityBaseBatchSelect();
                }

            }
        });
    }

    @Override
    public void onIncrementClick() {
        if(!TextUtils.isEmpty(getRequiredQuantity())) {
            int quantity = Integer.parseInt(getRequiredQuantity());
            quantity++;
            String string = Integer.toString(quantity);
            batchInfoBinding.inputQty.setText(string);
        }else{
            batchInfoBinding.inputQty.setText("1");
        }
    }

    @Override
    public void onDecrementClick() {
        if(!TextUtils.isEmpty(getRequiredQuantity())) {
            int quantity = Integer.parseInt(getRequiredQuantity());
            if (quantity == 1) {
                String string = Integer.toString(quantity);
                batchInfoBinding.inputQty.setText(string);
            } else {
                quantity--;
                String string = Integer.toString(quantity);
                batchInfoBinding.inputQty.setText(string);
            }
        }else{
            batchInfoBinding.inputQty.setText("1");
        }
    }

    @Override
    public void onNavigateNextActivity() {
        if(batchInfoProducts.size()> 0 && !TextUtils.isEmpty(getRequiredQuantity())) {
            addProductBasedBatch();
        }else{
            if (TextUtils.isEmpty(getRequiredQuantity()))
                showMessage("enter Quantity");
            else
                showMessage("select Batch");
        }
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void onSuccessBatchInfo(GetBatchInfoRes body) {
        if(body.getBatchList().size() > 0) {
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
            isSelectedBatch = true;
            selectBatch();
        }else{
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
    public void onItemClick(int position) {
        isSelectedBatch = true;
        manualSelectedPosition = position;
        selectBatch();

    }
private int manualSelectedPosition = 0;
    private void quantityBaseBatchSelect(){
        if(selectedBatchQOH < Double.parseDouble(getRequiredQuantity())) {
            batchInfoProducts.clear();
            double totalQuantity = Double.parseDouble(getRequiredQuantity());
            for (int i = 0; i < arrBatchList.size(); i++) {
                double qoh = Double.parseDouble(arrBatchList.get(i).getQ_O_H());
                if (totalQuantity > 0) {
                    GetItemDetailsRes.Items items = getNewItem();
                    if (qoh <= totalQuantity)
                        arrBatchList.get(i).setEnterReqQuantity((int) qoh);
                    else
                        arrBatchList.get(i).setEnterReqQuantity((int) totalQuantity);


                        totalQuantity = totalQuantity - qoh;
                        items.setBatchListObj(arrBatchList.get(i));
                        batchInfoProducts.add(items);
                        arrBatchList.get(i).setSelected(true);
                        batchInfoAdapter.notifyItemChanged(i);
                } else {
                    arrBatchList.get(i).setSelected(false);
                    batchInfoAdapter.notifyItemChanged(i);
                }
            }
        }else {
            selectBatch();
        }
    }

    @Override
    public String getRequiredQuantity() {
        return batchInfoBinding.inputQty.getText().toString();
    }

    private void addProductBasedBatch(){
        if(Singletone.getInstance().itemsArrayList.size() > 0){
            if(isProductExitsOrNot()){
                productAllReadyExitsDialog();
            }else{
                doneBatchSelect();
            }
        }else{
            doneBatchSelect();
        }
    }

    private void doneBatchSelect(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_item", batchInfoProducts);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
    private boolean isProductExitsOrNot(){
        for(GetItemDetailsRes.Items oldItems  : Singletone.getInstance().itemsArrayList){
            if(selectedItem.getArtCode().equalsIgnoreCase(oldItems.getArtCode())){
                return true;
            }
        }
        return false;
    }

    private void selectBatch(){
        for (int i = 0; i < arrBatchList.size(); i++) {
            if (arrBatchList.get(i).isSelected()) {
                arrBatchList.get(i).setSelected(false);
                batchInfoAdapter.notifyItemChanged(i);
            }
            if (manualSelectedPosition == i) {
                if(Double.parseDouble(getRequiredQuantity()) <= Double.parseDouble(arrBatchList.get(i).getQ_O_H()) ) {
                    arrBatchList.get(i).setSelected(true);
                    batchInfoAdapter.notifyItemChanged(i);
                    selectedBatchQOH = Double.parseDouble(arrBatchList.get(i).getQ_O_H());
                    GetItemDetailsRes.Items items = selectedItem;
                    arrBatchList.get(i).setEnterReqQuantity(Integer.parseInt(getRequiredQuantity()));
                    items.setBatchListObj(arrBatchList.get(i));
                    batchInfoProducts.clear();
                    batchInfoProducts.add(items);
                }else{
                    showMessage("Requested Quantity greater then batch quantity");
                    quantityBaseBatchSelect();
                }
            }
        }

    }

    private void checkBatchQOH(GetBatchInfoRes.BatchListObj selectedBatch){

    }
    private GetItemDetailsRes.Items getNewItem(){
        GetItemDetailsRes.Items  items = new GetItemDetailsRes.Items();
        items.setArtCode(selectedItem.getArtCode());
        items.setCategory(selectedItem.getCategory());
        items.setCategoryCode(selectedItem.getCategoryCode());
        items.setItemDelete(selectedItem.isItemDelete());
        items.setBatchListObj(selectedItem.getBatchListObj());
        items.setDescription(selectedItem.getDescription());
        items.setDiseaseType(selectedItem.getDiseaseType());
        items.setDPCO(selectedItem.getDPCO());
        items.setGenericName(selectedItem.getGenericName());
        items.setHsncode_In(selectedItem.getHsncode_In());
        items.setManufacture(selectedItem.getManufacture());
        items.setManufactureCode(selectedItem.getManufactureCode());
        items.setProductRecID(selectedItem.getProductRecID());
        items.setRackId(selectedItem.getRackId());
        items.setRetailCategoryRecID(selectedItem.getRetailCategoryRecID());
        items.setRetailMainCategoryRecID(selectedItem.getRetailMainCategoryRecID());
        items.setRetailSubCategoryRecID(selectedItem.getRetailSubCategoryRecID());
        items.setSch_Catg(selectedItem.getSch_Catg());
        items.setSch_Catg_Code(selectedItem.getSch_Catg_Code());
        items.setSI_NO(selectedItem.getSI_NO());
        items.setSubCategory(selectedItem.getSubCategory());
        items.setSubClassification(selectedItem.getSubClassification());
        return items;
    }

    private void productAllReadyExitsDialog(){
        ExitInfoDialog dialogView = new ExitInfoDialog(this);
        dialogView.setTitle("Item Already Exist");
        dialogView.setPositiveLabel("Yes");
        dialogView.setSubtitle("Do you want to continue?");
        dialogView.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                updateExistItemBatch();
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

    private void updateExistItemBatch(){
        double totalQuantity = 0.0 ;
        ArrayList<GetItemDetailsRes.Items> existingProducts = Singletone.getInstance().itemsArrayList;
        for(Iterator<GetItemDetailsRes.Items> it = existingProducts.iterator(); it.hasNext();) {
            GetItemDetailsRes.Items s = it.next();
            if(selectedItem.getArtCode().equalsIgnoreCase(s.getArtCode())) {
                totalQuantity += s.getBatchListObj().getEnterReqQuantity();
                it.remove();
            }
        }
//        for(GetItemDetailsRes.Items oldItems  : existingProducts){
//            if(selectedItem.getArtCode().equalsIgnoreCase(oldItems.getArtCode())){
//                totalQuantity += oldItems.getBatchListObj().getEnterReqQuantity();
//                Singletone.getInstance().itemsArrayList.remove(oldItems);
//            }
//        }
        totalQuantity += Double.parseDouble(getRequiredQuantity());
        batchInfoProducts.clear();
        for (int i = 0; i < arrBatchList.size(); i++) {
            double qoh = Double.parseDouble(arrBatchList.get(i).getQ_O_H());
            if (totalQuantity > 0) {
                GetItemDetailsRes.Items items = getNewItem();
                if (qoh <= totalQuantity)
                    arrBatchList.get(i).setEnterReqQuantity((int) qoh);
                else
                    arrBatchList.get(i).setEnterReqQuantity((int) totalQuantity);


                totalQuantity = totalQuantity - qoh;
                items.setBatchListObj(arrBatchList.get(i));
                batchInfoProducts.add(items);
                arrBatchList.get(i).setSelected(true);
                batchInfoAdapter.notifyItemChanged(i);
            } else {
                arrBatchList.get(i).setSelected(false);
                batchInfoAdapter.notifyItemChanged(i);
            }
        }
        doneBatchSelect();
    }
}
