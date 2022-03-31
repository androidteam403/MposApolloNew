package com.apollopharmacy.mpospharmacistTest.ui.adddoctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityAddDoctorBinding;
import com.apollopharmacy.mpospharmacistTest.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.SalesOriginResModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class AddDoctorActivity extends BaseActivity implements AddDoctorMvpView {
    @Inject
    AddDoctorMvpPresenter<AddDoctorMvpView> mPresenter;
    private ActivityAddDoctorBinding addDoctorBinding;
    private DoctorSearchResModel.DropdownValueBean doctorEntity;
    private SalesOriginResModel.DropdownValueBean salesEntity;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AddDoctorActivity.class);
    }

    public static Intent getStartIntent(Context context, DoctorSearchResModel.DropdownValueBean doctorEntity, SalesOriginResModel.DropdownValueBean salesEntity) {
        Intent intent = new Intent(context, AddDoctorActivity.class);
        intent.putExtra("doctor_info", doctorEntity);
        intent.putExtra("sales_info", salesEntity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        addDoctorBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_doctor);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddDoctorActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        addDoctorBinding.setCallback(mPresenter);
        if (getIntent() != null) {
            doctorEntity = (DoctorSearchResModel.DropdownValueBean) getIntent().getSerializableExtra("doctor_info");
        }
        if (getIntent() != null) {
            salesEntity = (SalesOriginResModel.DropdownValueBean) getIntent().getSerializableExtra("sales_info");
        }

        if (mPresenter.enablescreens()) {
            mPresenter.onMposTabApiCall();
            turnOnScreen();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        addDoctorBinding.imageView.setVisibility(View.GONE);
    }

    @Override
    public void onSubmitBtnClick() {
        if (validate()) {
            mPresenter.handleAddDoctorService();
        }
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void addDoctorSuccess(AddDoctorResModel addDoctorResModel) {
        DoctorSearchResModel.DropdownValueBean doctorModel = new DoctorSearchResModel.DropdownValueBean();
        doctorModel.setCode(addDoctorResModel.getDocRegID());
        doctorModel.setDisplayText(addDoctorResModel.getDocName());
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("doctor_info", doctorModel);
        bundle.putSerializable("sales_info", salesEntity);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void addDoctorFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getDoctorName() {
        return (Objects.requireNonNull(addDoctorBinding.doctorName.getText())).toString();
    }

    @Override
    public String getDoctorRegNo() {
        return (Objects.requireNonNull(addDoctorBinding.doctorRegNumber.getText())).toString();
    }

    @Override
    public String getSpeciality() {
        return (Objects.requireNonNull(addDoctorBinding.speciality.getText())).toString();
    }

    @Override
    public String getPlaceOfPractice() {
        return (Objects.requireNonNull(addDoctorBinding.placeOfPractice.getText()).toString());
    }

    @Override
    public String getAddress() {
        return (Objects.requireNonNull(addDoctorBinding.address.getText()).toString());
    }

    @Override
    public String getPhoneNo() {
        return (Objects.requireNonNull(addDoctorBinding.phoneNumber.getText())).toString();
    }


    private boolean validate() {
        String doctorRegNo = Objects.requireNonNull(addDoctorBinding.doctorRegNumber.getText()).toString();
        String dctrName = Objects.requireNonNull(addDoctorBinding.doctorName.getText()).toString();
        String spclty = Objects.requireNonNull(addDoctorBinding.speciality.getText()).toString();
        String placeofPractice = Objects.requireNonNull(addDoctorBinding.placeOfPractice.getText()).toString();
        String adrs = Objects.requireNonNull(addDoctorBinding.address.getText()).toString();
        String pNumber = Objects.requireNonNull(addDoctorBinding.phoneNumber.getText()).toString();
        if (doctorRegNo.isEmpty()) {
            addDoctorBinding.doctorRegNumber.setError("Doctor registration number should not be empty");
            addDoctorBinding.doctorRegNumber.requestFocus();
            return false;
        } else if (dctrName.isEmpty()) {
            addDoctorBinding.doctorName.setError("Doctor name should not be empty");
            addDoctorBinding.doctorName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(dctrName)) {
            addDoctorBinding.doctorName.setError("Invalid doctor name");
            addDoctorBinding.doctorName.requestFocus();
            return false;
        } else if (spclty.isEmpty()) {
            addDoctorBinding.speciality.setError("Speciality should not be empty");
            addDoctorBinding.speciality.requestFocus();
            return false;
        } else if (placeofPractice.isEmpty()) {
            addDoctorBinding.placeOfPractice.setError("Place of Practice should not be empty");
            addDoctorBinding.placeOfPractice.requestFocus();
            return false;
        } else if (adrs.isEmpty()) {
            addDoctorBinding.address.setError("Address should not be empty");
            addDoctorBinding.address.requestFocus();
            return false;
        } else if (pNumber.isEmpty()) {
            addDoctorBinding.phoneNumber.setError("Phone number should not be empty");
            addDoctorBinding.phoneNumber.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(pNumber)) {
            addDoctorBinding.phoneNumber.setError("Invalid Phone Number");
            addDoctorBinding.phoneNumber.requestFocus();
            return false;
        }
        return true;
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
        addDoctorBinding.imageView.setImageBitmap(myBitmap);
        addDoctorBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        addDoctorBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
                    if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
                        getSupportActionBar().hide();
                    }
                }
                handelPlayList();
                addDoctorBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        addDoctorBinding.imageView.setVisibility(View.GONE);
                        getSupportActionBar().show();
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
