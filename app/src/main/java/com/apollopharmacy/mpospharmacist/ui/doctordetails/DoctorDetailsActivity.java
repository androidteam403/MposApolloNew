package com.apollopharmacy.mpospharmacist.ui.doctordetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityDoctorDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.AddDoctorActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog.AllDoctorsDialog;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DoctorDetailsActivity extends BaseActivity implements DoctorDetailsMvpView {
    @Inject
    DoctorDetailsMvpPresenter<DoctorDetailsMvpView> mPresenter;
    ActivityDoctorDetailsBinding doctorDetailsBinding;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> allDoctorsArrayList;
    private DoctorSearchResModel.DropdownValueBean customDoctorItem = null;
    private DoctorSearchResModel.DropdownValueBean doctorEntity;
    private SalesOriginResModel.DropdownValueBean salesEntity;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> doctorCustomerEntity;
    private int NEW_DOCTOR_SEARCH_ACTIVITY_CODE = 104;
    String strFont = null;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DoctorDetailsActivity.class);
    }

    public static Intent getStartIntent(Context context, DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        Intent intent = new Intent(context, DoctorDetailsActivity.class);
        intent.putExtra("doctor_info", doctorEntity);
        intent.putExtra("sales_info", salesEntity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctorDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DoctorDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        doctorDetailsBinding.siteDataInfo.siteName.setText(mPresenter.getStoreName());
        doctorDetailsBinding.siteDataInfo.siteId.setText(mPresenter.getStoreId());
        doctorDetailsBinding.siteDataInfo.terminalId.setText(mPresenter.getTerminalId());
        doctorDetailsBinding.setCallback(mPresenter);
        allDoctorsArrayList = new ArrayList<>();
        mPresenter.getDoctorsList();
        mPresenter.getAllDoctorsList();
        doctorDetailsBinding.selectDoctor.setFocusableInTouchMode(false);
//        doctorDetailsBinding.selectSalesOrigin.setFocusableInTouchMode(false);
        mPresenter.onMposTabApiCall();
        turnOnScreen();
    }

    @Override
    public void onAddDoctorClick() {
        startActivityForResult(AddDoctorActivity.getStartIntent(this, doctorEntity, salesEntity), NEW_DOCTOR_SEARCH_ACTIVITY_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void getDoctorSearchList(DoctorSearchResModel model) {

        doctorDetailsBinding.selectDoctor.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<DoctorSearchResModel.DropdownValueBean> adapter = new ArrayAdapter<DoctorSearchResModel.DropdownValueBean>(getContext(), android.R.layout.simple_spinner_item, model.get_DropdownValue()) {
            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorDetailsBinding.selectDoctor.setAdapter(adapter);
        doctorDetailsBinding.selectDoctor.setSelection(0);
        doctorDetailsBinding.selectDoctor.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        doctorDetailsBinding.selectDoctor.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        doctorDetailsBinding.selectDoctor.setFocusableInTouchMode(false);
        doctorDetailsBinding.selectDoctor.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
            doctorDetailsBinding.selectDoctor.setError(null);
        });

        if (getIntent() != null) {
            doctorEntity = (DoctorSearchResModel.DropdownValueBean) getIntent().getSerializableExtra("doctor_info");
            if (doctorEntity != null) {
                boolean isItemFound = false;
                for (int i = 0; i < model.get_DropdownValue().size(); i++) {
                    if (model.get_DropdownValue().get(i).getCode().equals(doctorEntity.getCode())) {
                        doctorDetailsBinding.selectDoctor.setSelection(i);
                        isItemFound = true;
                    }
                }
                if (!isItemFound) {
                    doctorDetailsBinding.selectDoctor.setVisibility(View.GONE);
                    doctorDetailsBinding.customDoctorSearchLayout.setVisibility(View.GONE);
                    doctorDetailsBinding.customDoctorLayout.setVisibility(View.VISIBLE);
                    doctorDetailsBinding.corporateNumber.setText(doctorEntity.getDisplayText());
                }
            }
        }
    }

    @Override
    public void getSalesOriginList(SalesOriginResModel model) {
        doctorDetailsBinding.selectDoctor.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SalesOriginResModel.DropdownValueBean> adapter = new ArrayAdapter<SalesOriginResModel.DropdownValueBean>(getContext(), android.R.layout.simple_spinner_item, model.getGetSalesOriginResult().get_DropdownValue()) {
            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        doctorDetailsBinding.selectSalesOrigin.setAdapter(adapter);
//        doctorDetailsBinding.selectSalesOrigin.setSelection(0);
//        doctorDetailsBinding.selectSalesOrigin.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
//        doctorDetailsBinding.selectSalesOrigin.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
//        doctorDetailsBinding.selectSalesOrigin.setFocusableInTouchMode(false);
//        doctorDetailsBinding.selectSalesOrigin.setOnItemClickListener((materialSpinner, view, i, l) -> {
//            materialSpinner.focusSearch(View.FOCUS_DOWN);
//            doctorDetailsBinding.selectSalesOrigin.setError(null);
//        });
//        if (getIntent() != null) {
//            salesEntity = (SalesOriginResModel.DropdownValueBean) getIntent().getSerializableExtra("sales_info");
//            if (salesEntity != null) {
//                for (int i = 0; i < model.getGetSalesOriginResult().get_DropdownValue().size(); i++) {
//                    if (model.getGetSalesOriginResult().get_DropdownValue().get(i).getCode().equals(salesEntity.getCode())) {
//                        doctorDetailsBinding.selectSalesOrigin.setSelection(i);
//                    }
//                }
//            }
//        }
    }

    @Override
    public void getAllDoctorsSearchList(DoctorSearchResModel model) {
        allDoctorsArrayList.clear();
        allDoctorsArrayList.addAll(model.get_DropdownValue());
    }

    @Override
    public void onAllDoctorsClick() {
        if (allDoctorsArrayList.size() > 0) {
            AllDoctorsDialog dialog = AllDoctorsDialog.newInstance();
            dialog.setDoctorDetailsMvpView(this);
            dialog.setDoctorsArray(allDoctorsArrayList);
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    @Override
    public void onSubmitClick() {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        if (customDoctorItem == null) {
            bundle.putSerializable("doctor_info", (Serializable) doctorDetailsBinding.selectDoctor.getSelectedItem());
        } else {
            bundle.putSerializable("doctor_info", customDoctorItem);
        }
//        bundle.putSerializable("sales_info", (Serializable) doctorDetailsBinding.selectSalesOrigin.getSelectedItem());
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    @Override
    public void onSelectDoctor(DoctorSearchResModel.DropdownValueBean dropdownValueBean, ArrayList<DoctorSearchResModel.DropdownValueBean> doctorList) {
        allDoctorsArrayList.clear();
        allDoctorsArrayList = doctorList;
        customDoctorItem = dropdownValueBean;
        doctorDetailsBinding.customDoctorSearchLayout.setVisibility(View.GONE);
        doctorDetailsBinding.selectDoctor.setVisibility(View.GONE);
        doctorDetailsBinding.customDoctorLayout.setVisibility(View.VISIBLE);
        doctorDetailsBinding.corporateNumber.setText(dropdownValueBean.getDisplayText());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllDoctorsList();
        onPause = false;
        idealScreen();
    }

    @Override
    public void onCustomDoctorLayoutClick() {
        AllDoctorsDialog dialog = AllDoctorsDialog.newInstance();
        dialog.setDoctorDetailsMvpView(this);
        dialog.setDoctorsArray(allDoctorsArrayList);
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        doctorDetailsBinding.imageView.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_DOCTOR_SEARCH_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DoctorSearchResModel.DropdownValueBean doctorResult = (DoctorSearchResModel.DropdownValueBean) data.getSerializableExtra("doctor_info");
                SalesOriginResModel.DropdownValueBean salesResult = (SalesOriginResModel.DropdownValueBean) data.getSerializableExtra("sales_info");
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("doctor_info", doctorResult);
                bundle.putSerializable("sales_info", salesResult);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
        doctorDetailsBinding.imageView.setImageBitmap(myBitmap);
        doctorDetailsBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        doctorDetailsBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
                doctorDetailsBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        doctorDetailsBinding.imageView.setVisibility(View.GONE);
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
