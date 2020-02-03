package com.apollo.pharmacy.ui.adminlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityAdminLoginBinding;
import com.apollo.pharmacy.ui.adminlogin.model.SpinnerIdPojo;
import com.apollo.pharmacy.ui.adminlogin.model.SpinnerPojo;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;

import java.util.ArrayList;

import javax.inject.Inject;

public class AdminLoginActivity extends BaseActivity implements AdminLoginMvpView {

    @Inject
    AdminLoginMvpPresenter<AdminLoginMvpView> mPresenter;
    private ActivityAdminLoginBinding adminLoginBinding;
    String strFont = null;

    private ArrayList<SpinnerPojo> loginTypeArr;
    private ArrayList<SpinnerIdPojo> storeIdTypeArr;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AdminLoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        adminLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_login);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AdminLoginActivity.this);
        setUp();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void setUp() {
        adminLoginBinding.setHandlers(mPresenter);

        ArrayAdapter<SpinnerPojo> adapter = new ArrayAdapter<SpinnerPojo>(this,
                android.R.layout.simple_spinner_dropdown_item, getLoginTypes());

        strFont = this.getString(R.font.roboto_regular);
        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");

        adminLoginBinding.spinner.setTypeface(tt);
        adminLoginBinding.spinner.setAdapter(adapter);

        adminLoginBinding.relative.setOnTouchListener((v, event) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(adminLoginBinding.relative.getWindowToken(), 0);
            return false;
        });
    }

    @Override
    public void onLoginClick() {
        if (validations()) {
            startActivity(DashboardActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public void onSetUpClick() {
        if (validations()) {
            startActivity(DashboardActivity.getStartIntent(this));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public void onNavigateHomeScreen() {

    }

    private boolean validations() {
        String name = adminLoginBinding.userName.getText().toString();
        String password = adminLoginBinding.password.getText().toString();
        if (name.isEmpty()) {
            adminLoginBinding.userName.setError("Name should not empty");
            adminLoginBinding.userName.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            adminLoginBinding.password.setError("Password should not empty");
            adminLoginBinding.password.requestFocus();
            return false;
        }
        return true;
    }

//    private void adminSetup() {
//        Date date = new Date();
//        Realm realm = RealmController.with(this).getRealm();
//        StoreDetails book = new StoreDetails();
//        book.setId(1);
//        book.setStoreId(12345);
//        book.setStoreLat(mylocation.getLatitude());
//        book.setStoreLang(mylocation.getLongitude());
//        book.setRegistrationDate(sdf.format(date));
//        book.setRegisteredBy("Jagadish");
//        book.setUserId("testid");
//        book.setEmail(adminLoginBinding.userName.getText().toString());
//        book.setPhone("9160147044");
//        realm.beginTransaction();
//        realm.copyToRealm(book);
//        realm.commitTransaction();
//        mPresenter.insertAdminLoginDetails();
//    }

    private ArrayList<SpinnerPojo> getLoginTypes() {
        loginTypeArr = new ArrayList<>();
        SpinnerPojo item = new SpinnerPojo();
        item.setLoginType("Admin");
        loginTypeArr.add(item);

        item = new SpinnerPojo();
        item.setLoginType("Pharmacist");
        loginTypeArr.add(item);

        item = new SpinnerPojo();
        item.setLoginType("IT");
        loginTypeArr.add(item);
        return loginTypeArr;
    }

    private ArrayList<SpinnerIdPojo> getStoreIdTypes() {
        storeIdTypeArr = new ArrayList<>();
        SpinnerIdPojo idPojo = new SpinnerIdPojo();
        idPojo.setStoreIdType("Ameerpet");
        idPojo.setStoreId("1234");
        storeIdTypeArr.add(idPojo);

        idPojo = new SpinnerIdPojo();
        idPojo.setStoreIdType("Maadhapur");
        idPojo.setStoreId("5678");
        storeIdTypeArr.add(idPojo);

        idPojo = new SpinnerIdPojo();
        idPojo.setStoreIdType("Miyapur");
        idPojo.setStoreId("9123");
        storeIdTypeArr.add(idPojo);

        return storeIdTypeArr;
    }
}