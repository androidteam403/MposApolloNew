package com.apollopharmacy.mpospharmacist.ui.additem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.db.model.Cart;
import com.apollopharmacy.mpospharmacist.data.db.model.CartItems;
import com.apollopharmacy.mpospharmacist.data.db.realm.RealmController;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddItemBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.CustomerDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.adapter.MedicinesDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.pay.PayActivity;
import com.apollopharmacy.mpospharmacist.ui.presenter.CustDocEditMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.utils.SwipeController;
import com.apollopharmacy.mpospharmacist.utils.SwipeControllerActions;

import java.util.ArrayList;

import javax.inject.Inject;

public class AddItemActivity extends BaseActivity implements AddItemMvpView, CustDocEditMvpView {

    @Inject
    AddItemMvpPresenter<AddItemMvpView> mPresenter;
    private ActivityAddItemBinding addItemBinding;
    private CustDocEditMvpView custDocEditMvpView;
    private ArrayList<GetItemDetailsRes.Items> medicineDetailsModelsList = new ArrayList<>();
    private MedicinesDetailAdapter medicinesDetailAdapter;
    private int ACTIVITY_ADD_PRODUCT_CODE = 102;
    private GetCustomerResponse.CustomerEntity customerEntity;
    private DoctorSearchResModel.DropdownValueBean doctorEntity;
    private SalesOriginResModel.DropdownValueBean salesEntity;
    private CorporateModel.DropdownValueBean corporateEntity;

    private int CUSTOMER_SEARCH_ACTIVITY_CODE = 103;
    private int DOCTOR_SEARCH_ACTIVITY_CODE = 104;
    private int CORPORATE_SEARCH_ACTIVITY_CODE = 105;
    private TransactionIDResModel transactionIdModel = null;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AddItemActivity.class);
    }

    public static Intent getStartIntent(Context context, GetCustomerResponse.CustomerEntity customerEntity, DoctorSearchResModel.DropdownValueBean doctor, CorporateModel.DropdownValueBean corporate, TransactionIDResModel transactionID) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra("customer_info", customerEntity);
        intent.putExtra("doctor_info", doctor);
        intent.putExtra("corporate_info", corporate);
        intent.putExtra("transaction_id", transactionID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddItemActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        addItemBinding.setCallback(mPresenter);
        addItemBinding.setCall(this);
        if (getIntent() != null) {
            customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
            if (customerEntity != null) {
                addItemBinding.setCustomer(customerEntity);
            }
            doctorEntity = (DoctorSearchResModel.DropdownValueBean) getIntent().getSerializableExtra("doctor_info");
            if (doctorEntity != null) {
                addItemBinding.setDoctor(doctorEntity);
            }
            corporateEntity = (CorporateModel.DropdownValueBean) getIntent().getSerializableExtra("corporate_info");
            transactionIdModel = (TransactionIDResModel) getIntent().getSerializableExtra("transaction_id");
            if (transactionIdModel != null) {
                addItemBinding.setTransaction(transactionIdModel);
            }
        }
        addItemBinding.setProductCount(medicineDetailsModelsList.size());

        Cart savedCart =  RealmController.with(this).getCartTransaction("123456");
        if(savedCart != null && savedCart.getItemsArrayList().size() > 0){
            for(int i=0; i< savedCart.getItemsArrayList().size(); i++){
                CartItems items = savedCart.getItemsArrayList().get(i);
                GetItemDetailsRes.Items cartItems = new GetItemDetailsRes.Items();
                cartItems.setArtCode(items.getArtCode());
                cartItems.setCategory(items.getCategory());
                cartItems.setCategoryCode(items.getCategoryCode());
                cartItems.setDescription(items.getDescription());
                cartItems.setDiseaseType(items.getDiseaseType());
                cartItems.setDPCO(items.isDPCO());
                cartItems.setGenericName(items.getGenericName());
                cartItems.setHsncode_In(items.getHsncode_In());
                cartItems.setManufacture(items.getManufacture());
                cartItems.setManufactureCode(items.getManufactureCode());
                cartItems.setProductRecID(items.getProductRecID());
                cartItems.setRackId(items.getRackId());
                cartItems.setRetailCategoryRecID(items.getRetailCategoryRecID());
                cartItems.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
                cartItems.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
                cartItems.setSch_Catg(items.getSch_Catg());
                cartItems.setSch_Catg_Code(items.getSch_Catg_Code());
                cartItems.setSI_NO(items.getSI_NO());
                cartItems.setSubCategory(items.getSubCategory());
                cartItems.setSubClassification(items.getSubClassification());
                medicineDetailsModelsList.add(cartItems);
            }

        }

        medicinesDetailAdapter = new MedicinesDetailAdapter(this, medicineDetailsModelsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        addItemBinding.medicineRecycle.setLayoutManager(mLayoutManager);
        addItemBinding.medicineRecycle.setAdapter(medicinesDetailAdapter);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //   medicinesDetailAdapter.remove(position);
                medicinesDetailAdapter.notifyItemRemoved(position);
                medicinesDetailAdapter.notifyItemRangeChanged(position, medicinesDetailAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(addItemBinding.medicineRecycle);

        addItemBinding.medicineRecycle.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
        addItemBinding.setProductCount(medicineDetailsModelsList.size());
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onManualSearchClick() {
        startActivityForResult(ProductListActivity.getStartIntent(this), ACTIVITY_ADD_PRODUCT_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onVoiceSearchClick() {
        startActivity(ProductListActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBarCodeSearchClick() {
        startActivity(ProductListActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onClickActionBarBack() {
        onBackPressed();
    }

    @Override
    public void onClearAll() {
        medicineDetailsModelsList.clear();
        medicinesDetailAdapter.notifyDataSetChanged();
        addItemBinding.setProductCount(medicineDetailsModelsList.size());
    }

    @Override
    public void onPayButtonClick() {
        startActivity(PayActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onCustomerEditClick() {
        if (customerEntity != null) {
            startActivityForResult(CustomerDetailsActivity.getStartIntent(this, customerEntity), CUSTOMER_SEARCH_ACTIVITY_CODE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public void onDoctorEditClick() {
        if (doctorEntity != null) {
            startActivityForResult(DoctorDetailsActivity.getStartIntent(this, doctorEntity, salesEntity), DOCTOR_SEARCH_ACTIVITY_CODE);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTIVITY_ADD_PRODUCT_CODE) {
                if (data != null) {
                    GetItemDetailsRes.Items items = (GetItemDetailsRes.Items) data.getSerializableExtra("selected_item");
                    medicineDetailsModelsList.add(items);
                    medicinesDetailAdapter.notifyDataSetChanged();
                    addItemBinding.setProductCount(medicineDetailsModelsList.size());
                    // Insert into cart table
                    if(items != null) {
                      Cart savedCart =  RealmController.with(this).getCartTransaction("123456");
                        CartItems cartItems = new CartItems();
                        cartItems.setArtCode(items.getArtCode());
                        cartItems.setCategory(items.getCategory());
                        cartItems.setCategoryCode(items.getCategoryCode());
                        cartItems.setDescription(items.getDescription());
                        cartItems.setDiseaseType(items.getDiseaseType());
                        cartItems.setDPCO(items.getDPCO());
                        cartItems.setGenericName(items.getGenericName());
                        cartItems.setHsncode_In(items.getHsncode_In());
                        cartItems.setManufacture(items.getManufacture());
                        cartItems.setManufactureCode(items.getManufactureCode());
                        cartItems.setProductRecID(items.getProductRecID());
                        cartItems.setRackId(items.getRackId());
                        cartItems.setRetailCategoryRecID(items.getRetailCategoryRecID());
                        cartItems.setRetailMainCategoryRecID(items.getRetailMainCategoryRecID());
                        cartItems.setRetailSubCategoryRecID(items.getRetailSubCategoryRecID());
                        cartItems.setSch_Catg(items.getSch_Catg());
                        cartItems.setSch_Catg_Code(items.getSch_Catg_Code());
                        cartItems.setSI_NO(items.getSI_NO());
                        cartItems.setSubCategory(items.getSubCategory());
                        cartItems.setSubClassification(items.getSubClassification());
                        if(savedCart != null){
                            Realm realm = RealmController.with(this).getRealm();
                            realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                                @Override
                                public void execute(Realm realm) {
                                    // increment index
                                    Number currentIdNum = realm.where(CartItems.class).max("id");
                                    int nextId;
                                    if (currentIdNum == null) {
                                        nextId = 1;
                                    } else {
                                        nextId = currentIdNum.intValue() + 1;
                                    }
                                    cartItems.setId(nextId);
                                  //  realm.beginTransaction();
                                    savedCart.getItemsArrayList().add(cartItems);
                                    realm.copyToRealmOrUpdate(savedCart);
                                   // realm.commitTransaction();
                                }
                            });

                        }else{
                            RealmList<CartItems> itemsRealmList = new RealmList<>();
                            itemsRealmList.add(cartItems);
                            Cart cart = new Cart();
                            cart.setId(2);
                            cart.setTransactionId("1234567");
                            cart.setItemsArrayList(itemsRealmList);
                            Realm realm = RealmController.with(this).getRealm();

                            realm.beginTransaction();
                            realm.copyToRealm(cart);
                            realm.commitTransaction();
                        }

                    }
                }
            } else if (requestCode == CUSTOMER_SEARCH_ACTIVITY_CODE) {
                customerEntity = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_info");
                if (customerEntity != null) {
                    addItemBinding.setCustomer(customerEntity);
                }
            } else if (requestCode == DOCTOR_SEARCH_ACTIVITY_CODE) {
                doctorEntity = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                if (doctorEntity != null) {
                    addItemBinding.setDoctor(doctorEntity);
                }
//                SalesOriginResModel.DropdownValueBean salesResult = (SalesOriginResModel.DropdownValueBean) data.getSerializableExtra("sales_info");
//                if (salesResult != null) {
//                    searchCutomerDetailsBinding.setSales(salesResult);
//                }
            } else if (requestCode == CORPORATE_SEARCH_ACTIVITY_CODE) {
//                CorporateModel.DropdownValueBean result = (CorporateModel.DropdownValueBean) data.getSerializableExtra("corporate_info");
//                searchCutomerDetailsBinding.setCorporate(result);
            }
        }
    }
}
