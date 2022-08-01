package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityNavigation3PBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FullfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PickerNavigationActivity extends BaseActivity implements PickerNavigationMvpView {

    public static PickerNavigationActivity mInstance;

    @Inject
    PickerNavigationMvpPresenter<PickerNavigationMvpView> mPresenter;
    public ActivityNavigation3PBinding activityNavigation3Binding;
    private AppBarConfiguration mAppBarConfiguration;
    TextView userName;
    TextView userStore;
    public PickerNavigationActivityCallback pickerNavigationActivityCallback;
    NavController navController;
    int itemPos;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList = new ArrayList<>();

    List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    NavOptions navOptions;
    int getPos;

    FullfilmentAdapter fullfilmentAdapter;
    private String fragmentName = null;

    public static Intent getStartIntent(Context mContext, String fragmentName) {
        Intent intent = new Intent(mContext, PickerNavigationActivity.class);
        intent.putExtra("FRAGMENT_NAME", fragmentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNavigation3Binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation3_p);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PickerNavigationActivity.this);
        setUp();


    }

    @Override
    protected void setUp() {
        mInstance = this;
//        activityNavigation3Binding.setCallback(mPresenter);
        setSupportActionBar(activityNavigation3Binding.appBarMain.toolbar);
        if (getIntent() != null) {
            fragmentName = (String) getIntent().getSerializableExtra("FRAGMENT_NAME");
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_picker_vtwo, R.id.nav_packer_vtwo, R.id.nav_biller_vtwo)
                .setDrawerLayout(activityNavigation3Binding.drawerLayout)
                .build();


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(activityNavigation3Binding.navView, navController);

        navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_from_right)
                .setExitAnim(R.anim.slide_to_left)
                .setPopEnterAnim(R.anim.slide_from_right)
                .setPopExitAnim(R.anim.slide_to_left)
                .build();
        if (fragmentName != null) {
            decideFragment(fragmentName);
        }
        activityNavigation3Binding.navView.setNavigationItemSelectedListener(menuItem -> {
            if (mAppBarConfiguration.getDrawerLayout() != null) {
                mAppBarConfiguration.getDrawerLayout().closeDrawers();
            }

            System.out.println("openscren status--->" + menuItem.getItemId());
//                if (menuItem.getItemId() == R.id.nav_dashboard) {
//                    navController.navigate(R.id.nav_dashboard, null, navOptions, null);
//                } else
            if (menuItem.getItemId() == R.id.nav_picker_vtwo) {
//                    getSupportFragmentManager().popBackStack();
                navController.navigate(R.id.nav_picker_vtwo, null, navOptions, null);
            } else if (menuItem.getItemId() == R.id.nav_packer_vtwo) {
//                    getSupportFragmentManager().popBackStack();
                navController.navigate(R.id.nav_packer_vtwo, null, navOptions, null);
            } else if (menuItem.getItemId() == R.id.nav_biller_vtwo) {
//                    getSupportFragmentManager().popBackStack();
                navController.navigate(R.id.nav_biller_vtwo, null, navOptions, null);
            }
            return true;
        });

        activityNavigation3Binding.appBarMain.icFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickerNavigationActivityCallback != null)
                    pickerNavigationActivityCallback.onClickFilters();
            }
        });
        activityNavigation3Binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });
        stockAvailable();
    }

    private void decideFragment(String fragmentName) {
        if (mAppBarConfiguration.getDrawerLayout() != null) {
            mAppBarConfiguration.getDrawerLayout().closeDrawers();
        }

        if (fragmentName.equals("PICKER")) {
            navController.navigate(R.id.nav_picker_vtwo, null, navOptions, null);
        } else if (fragmentName.equals("PACKER")) {
            navController.navigate(R.id.nav_packer_vtwo, null, navOptions, null);
        } else if (fragmentName.equals("BILLER")) {
            navController.navigate(R.id.nav_biller_vtwo, null, navOptions, null);
        }
    }

    public void navigateToOpenOrders() {
        navController.navigate(R.id.nav_picker_vtwo, null, navOptions, null);
    }

    public void setTitle(String tittle) {
        activityNavigation3Binding.appBarMain.title.setText(tittle);
    }

    public void stockAvailable() {
        activityNavigation3Binding.appBarMain.stockAvailableCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (pickerNavigationActivityCallback != null) {
                    pickerNavigationActivityCallback.onClickStockAvailable(b);
                }
            }
        });
    }

    public void setStockAvailableBoxCheck(boolean isCheck) {
        activityNavigation3Binding.appBarMain.stockAvailableCheckbox.setChecked(isCheck);
    }

    public void setStockAvailableVisibilty(boolean isVisible) {
        if (isVisible)
            activityNavigation3Binding.appBarMain.stockAvailableCheckbox.setVisibility(View.VISIBLE);
        else
            activityNavigation3Binding.appBarMain.stockAvailableCheckbox.setVisibility(View.GONE);
    }

    public void setStock(String stock) {
//        RecyclerView recyclerView = findViewById(R.id.fullfilment_recycler);
//
//        activityNavigation3Binding.appBarMain.stock.setText(stock);
//        activityNavigation3Binding.appBarMain.stocknew.setText(stock);
//        activityNavigation3Binding.appBarMain.stock.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                activityNavigation3Binding.appBarMain.stocknew.setVisibility(VISIBLE);
//                activityNavigation3Binding.appBarMain.stock.setVisibility(GONE);
//                pickerNavigationActivityCallback.onItemClick();
//
////                List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
////                for (int i = 0; i < mPresenter.getTotalOmsHeaderList().size(); i++) {
////                    if (mPresenter.getTotalOmsHeaderList().get(i).getStockStatus().equalsIgnoreCase("Stock available")) {
////                        omsHeaderList.add(mPresenter.getTotalOmsHeaderList().get(i));
////                    }
////                }
//////                omsHeaderList = mPresenter.getTotalOmsHeaderList();
//////                activityNavigation3Binding.appBarMain.stock.setTextColor(R.color.black);
////                pickerNavigationActivityCallback.onItemClick();
////                FullfilmentAdapter fullfilmentAdapter = new FullfilmentAdapter(getApplicationContext(), omsHeaderList, PickerNavigationActivity.this, null);
////                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
////                recyclerView.setLayoutManager(mLayoutManager);
////                recyclerView.setAdapter(fullfilmentAdapter);
////                fullfilmentAdapter.notifyDataSetChanged();
////            }
//
//
//            }
//        });
//
//
//        activityNavigation3Binding.appBarMain.stocknew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityNavigation3Binding.appBarMain.stocknew.setVisibility(GONE);
//                activityNavigation3Binding.appBarMain.stock.setVisibility(VISIBLE);
//
//                List<TransactionHeaderResponse.OMSHeader> newomsHeaderList = new ArrayList<>();
////                for (int i = 0; i < mPresenter.getTotalOmsHeaderList().size(); i++) {
////                    if (!mPresenter.getTotalOmsHeaderList().get(i).getOrderPickup()) {
////                        newomsHeaderList.add(mPresenter.getTotalOmsHeaderList().get(i));
////                    }
////                }
////
////                FullfilmentAdapter fullfilmentAdapter = new FullfilmentAdapter(getApplicationContext(), newomsHeaderList, PickerNavigationActivity.this, null);
////                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
////                recyclerView.setLayoutManager(mLayoutManager);
////                recyclerView.setAdapter(fullfilmentAdapter);
////                fullfilmentAdapter.notifyDataSetChanged();
//
//            }
//        });


    }

    public void setWelcome(String welcomeText) {
        activityNavigation3Binding.appBarMain.welcome.setText(welcomeText);
    }

    private void logoutDialog() {
        ExitInfoDialog dialogView = new ExitInfoDialog(PickerNavigationActivity.this);
        dialogView.setTitle("Alert");
        dialogView.setSubtitle("Are you sure want to logout the application ?");
        dialogView.setPositiveLabel("Yes");
        dialogView.setPositiveListener(view -> {
            mPresenter.logoutUser();
        });
        dialogView.setNegativeLabel("No");
        dialogView.setNegativeListener(v -> dialogView.dismiss());

        dialogView.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_p, menu);
        userName = findViewById(R.id.login_user_name);
        userStore = findViewById(R.id.login_user_store);

//        userStore = findViewById(R.id.user_store);

        userName.setText(mPresenter.getLoginUserName());
//        userStore.setText("Terminal ID - ");
        userStore.setText(mPresenter.getLoinStoreLocation());
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void navigateLoginActivity() {
        Intent intent = new Intent(PickerNavigationActivity.this, PharmacistLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }


    @Override
    public void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body) {
//        LinearLayout linearLayout = findViewById(R.id.continue_btn);
//        RecyclerView recyclerView = findViewById(R.id.fullfilment_recycler);
//        if (omsHeaderList.get(getPos).getExpandStatus() == 0) {
//            omsHeaderList.get(getPos).setExpandStatus(1);
//        } else {
//            omsHeaderList.get(getPos).setExpandStatus(0);
//        }
//        fullfilmentAdapter = new FullfilmentAdapter(getApplicationContext(), omsHeaderList, this, body);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(fullfilmentAdapter);
//        recyclerView.scrollToPosition(getPos);
//        linearLayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.continue_select_color));

    }

    @Override
    public void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList) {
//        LinearLayout linearLayout = findViewById(R.id.continue_btn);
//        if (omsHeaderList != null && omsHeaderList.size() > 0) {
//            omsHeaderList.get(getPos).setSelected(!omsHeaderList.get(getPos).isSelected());
//            if (omsHeaderList.get(getPos).isSelected()) {
//
//                omsHeaderList.get(getPos).setGetOMSTransactionResponse(getOMSTransactionResponseList.get(0));
//                selectedOmsHeaderList.add(omsHeaderList.get(getPos));
//            } else {
//                if (selectedOmsHeaderList != null && selectedOmsHeaderList.size() > 0) {
//                    for (int i = 0; i < selectedOmsHeaderList.size(); i++) {
//                        if (selectedOmsHeaderList.get(i).getRefno().equals(omsHeaderList.get(getPos).getRefno())) {
//                            selectedOmsHeaderList.remove(i);
//                            break;
//                        }
//                    }
//                }
//            }
//            if (fullfilmentAdapter != null) {
//                fullfilmentAdapter.notifyItemChanged(itemPos);
//            }
//            linearLayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.continue_select_color));
//        }
    }


    public interface PickerNavigationActivityCallback {
        void onClickFilters();

        void onItemClick();

        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

        void onClickStockAvailable(boolean isStockAvailableChecked);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (pickerNavigationActivityCallback != null)
            pickerNavigationActivityCallback.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PickerNavigationActivity.this, SelectAppFlowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }
}