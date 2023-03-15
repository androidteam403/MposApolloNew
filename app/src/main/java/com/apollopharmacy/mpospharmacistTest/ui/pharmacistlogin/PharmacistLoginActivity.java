package com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.pt.MiniLcd;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityPharmacistLoginBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogDecideVersionFlowBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow.SelectAppFlowActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.CampaignDetailsRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UpdatePatchRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UpdatePatchResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class PharmacistLoginActivity extends BaseActivity implements PharmacistLoginMvpView {
    @Inject
    PharmacistLoginMvpPresenter<PharmacistLoginMvpView> mPresenter;
    private ActivityPharmacistLoginBinding pharmacistLoginBinding;
    MiniLcd miniLcd = null;
    boolean open_flg = false;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PharmacistLoginActivity.class);
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
        pharmacistLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_pharmacist_login);
        getActivityComponent().inject(this);
        mPresenter.onAttach(PharmacistLoginActivity.this);
        setUp();
    }


    @Override
    protected void setUp() {
        //this is not required for Uncomment
//        mPresenter.onMposTabApiCall();
        turnOnScreen();
        pharmacistLoginBinding.setCallback(mPresenter);
        pharmacistLoginBinding.password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pharmacistLoginBinding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        pharmacistLoginBinding.selectUser.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        pharmacistLoginBinding.selectCampaign.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        pharmacistLoginBinding.selectUser.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        pharmacistLoginBinding.selectCampaign.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        mPresenter.getUserId();
//        idealScreen();
        if (!mPresenter.firstTimeFalse()) {
            if (mPresenter.enablescreens()) {
                mPresenter.onMposPosiflexApiCall();

                miniLcd = new MiniLcd();
                if (miniLcd.open() == 0) {
                    open_flg = true;
                    //startTask();
                    show_result(0);
                }
                openMiniLcd();
                mPresenter.secondTimeTrue();
            }
        }
    }

    private boolean validations() {
        String password = Objects.requireNonNull(pharmacistLoginBinding.password.getText()).toString();
        // String usetid =  (String)pharmacistLoginBinding.selectUser.getSelectedItem();
        if (pharmacistLoginBinding.selectUser.getSelectedItem() == null) {
            pharmacistLoginBinding.selectUser.setError("Please Select User");
            pharmacistLoginBinding.selectUser.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;
        } else if (password.isEmpty()) {
            pharmacistLoginBinding.password.setError("Password should not empty");
            pharmacistLoginBinding.password.requestFocus();
            return false;
        } else if (pharmacistLoginBinding.selectUser.getSelectedItem().toString().equalsIgnoreCase("Select User")) {
            pharmacistLoginBinding.selectUser.setError("Please Select User");
            pharmacistLoginBinding.selectUser.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void onClickLogin() {
        if (validations()) {
            int radioButtonID = pharmacistLoginBinding.radioGroup.getCheckedRadioButtonId();
            View radioButton = pharmacistLoginBinding.radioGroup.findViewById(radioButtonID);
            int idx = pharmacistLoginBinding.radioGroup.indexOfChild(radioButton);
            if (idx == 0) {
                mPresenter.userLoginInStoreApi();
            } else {
                mPresenter.userLoginCampaignApi();
            }
        }

      /*startActivity(MainActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();*/
    }

    @Override
    public void onClickInstore() {
        pharmacistLoginBinding.selectCampaign.setVisibility(View.GONE);
        pharmacistLoginBinding.relativeInfo.setVisibility(View.GONE);
    }

    @Override
    public void onClickCampaignClose() {
        pharmacistLoginBinding.relativeInfo.setVisibility(View.GONE);
        pharmacistLoginBinding.selectCampaign.setVisibility(View.VISIBLE);
    }

    @Override
    public void getUserIds(UserModel body) {
        ArrayAdapter<UserModel._DropdownValueBean> adapter = new ArrayAdapter<UserModel._DropdownValueBean>(this, android.R.layout.simple_spinner_dropdown_item, body.getGetLoginUserResult().get_DropdownValue()) {

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

        pharmacistLoginBinding.selectUser.setAdapter(adapter);
        pharmacistLoginBinding.selectUser.setOnItemClickListener((materialSpinner, view, i, l) -> {
            materialSpinner.focusSearch(View.FOCUS_DOWN);
            pharmacistLoginBinding.selectUser.setError(null);
        });
    }

    @Override
    public void setCampaignDetails(CampaignDetailsRes campaignDetails) {
        ArrayAdapter<CampaignDetailsRes.CampDetailsEntity> adapter1 = new ArrayAdapter<CampaignDetailsRes.CampDetailsEntity>(this, android.R.layout.simple_spinner_dropdown_item, campaignDetails.getCampDetails()) {

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
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pharmacistLoginBinding.selectCampaign.setAdapter(adapter1);

        pharmacistLoginBinding.selectCampaign.setOnItemClickListener((materialSpinner, view, i, l) -> {
            pharmacistLoginBinding.relativeInfo.setVisibility(View.VISIBLE);
            pharmacistLoginBinding.selectCampaign.setVisibility(View.GONE);
            pharmacistLoginBinding.setCampaign((CampaignDetailsRes.CampDetailsEntity) materialSpinner.getSelectedItem());
            materialSpinner.focusSearch(View.FOCUS_DOWN);
        });
        pharmacistLoginBinding.selectCampaign.setVisibility(View.VISIBLE);
    }

    @Override
    public void userLoginSuccess() {
        mPresenter.updatePatchApiCAll();
    }

    @Override
    public void userLoginFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserId() {
        return pharmacistLoginBinding.selectUser.getSelectedItem() != null ? pharmacistLoginBinding.selectUser.getSelectedItem().toString() : null;
    }


    @Override
    public String getUserPassword() {
        return Objects.requireNonNull(pharmacistLoginBinding.password.getText()).toString();
    }

    public void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }


    @Nullable
    @Override
    public Context getContext() {
        return this;
    }


    private List<RowsEntity> rowsPosiFlexEntitiesList;


    @Override
    public void onSucessMposPosiflex() {
        rowsPosiFlexEntitiesList = mPresenter.getPosiflextDataListEntity();
        boolean isLoop = false;
        for (int i = 0; i < rowsPosiFlexEntitiesList.size(); i++) {
            if (rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null || rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                String path = String.valueOf(FileUtil.getMediaFilePath(rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                File file = new File(path);
                if (!file.exists()) {
                    isLoop = true;
                    mPresenter.onDownloadPosiflexCall(rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(), rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), i);
                    break;
                }
            }
        }
        if (!isLoop) {
            displayPicture();
        }
    }

    @Override
    public void onSuccessUpdatePatchApiCAll(UpdatePatchResponse updatePatchResponse) {
        //        if (mPresenter.getGlobalConfigurationObj() != null && mPresenter.getGlobalConfigurationObj().getMPOSVersion() != null && mPresenter.getGlobalConfigurationObj().getMPOSVersion().equals("1")) {
//        startActivity(MainActivity.getStartIntent(PharmacistLoginActivity.this));
//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//        finish();
//        } else if (mPresenter.getGlobalConfigurationObj() != null && mPresenter.getGlobalConfigurationObj().getMPOSVersion() != null && mPresenter.getGlobalConfigurationObj().getMPOSVersion().equals("2")) {
//        startActivity(SelectAppFlowActivity.getStartActivity(PharmacistLoginActivity.this));
//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//        finish();
//        } else {
//            Toast.makeText(this, "MPOS version other than verion 1 & 2", Toast.LENGTH_SHORT).show();
//        }
//        BottomSheetDialog decideVersionFlowDialog = new BottomSheetDialog(this);
//        DialogDecideVersionFlowBinding dialogDecideVersionFlowBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_decide_version_flow, null, false);
//        decideVersionFlowDialog.setContentView(dialogDecideVersionFlowBinding.getRoot());
//        decideVersionFlowDialog.setCancelable(false);
//        dialogDecideVersionFlowBinding.mposOneUserFlow.setOnClickListener(v -> {
//            startActivity(MainActivity.getStartIntent(PharmacistLoginActivity.this));
//            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            decideVersionFlowDialog.dismiss();
//            finish();
//        });
//        dialogDecideVersionFlowBinding.mposMultipleUserFlow.setOnClickListener(v -> {
            startActivity(SelectAppFlowActivity.getStartActivity(PharmacistLoginActivity.this));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            decideVersionFlowDialog.dismiss();
            finish();
//        });
//        decideVersionFlowDialog.show();
    }

    public void handelPosiflextPlayListData() {
        if (rowsPosiFlexEntitiesList != null && rowsPosiFlexEntitiesList.size() > 0) {
            boolean isAllFilesExist = false;
            for (int i = 0; i < rowsPosiFlexEntitiesList.size(); i++) {
                if (rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null || rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                    if (!rowsPosiFlexEntitiesList.get(i).isPosiflex()) {
                        String path = String.valueOf(FileUtil.getMediaFilePath(rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                        File file = new File(path);
                        if (file.exists()) {
                            playPosiflexListData(path, i);
                            isAllFilesExist = true;
                            break;
                        }
                    }
                }
            }
            if (!isAllFilesExist) {
                for (int i = 0; i < rowsPosiFlexEntitiesList.size(); i++) {
                    rowsPosiFlexEntitiesList.get(i).setPosiflex(false);
                }
                handelPosiflextPlayListData();
            }
        }
    }

    public void playPosiflexListData(String filePath, int pos) {
        int ret;
        ret = miniLcd.displayPictureByAbsolutePath(0, 0, filePath);
        show_result(ret);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsPosiFlexEntitiesList.get(pos).setPosiflex(true);
                if (pos == rowsPosiFlexEntitiesList.size() - 1) {
                    for (RowsEntity rowsEntity : rowsPosiFlexEntitiesList) {
                        rowsEntity.setPosiflex(false);
                    }
                }
                handelPosiflextPlayListData();
            }
        }, 5000);
    }

    private void show_result(int ret) {
        // TODO Auto-generated method stub
        switch (ret) {
            case 0:
                show(getContext(), "success ");
                break;
            case -1:
                show(getContext(), "fail");
                break;
            case -2:
                show(getContext(), "time out");
                break;
            case -3:
                show(getContext(), "in parameters error");
                break;
            default:
                show(getContext(), "fail");
                break;
        }
    }

    public void show(Context context, String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void openMiniLcd() {
        Log.i("guanjie", "openMiniLcd");
        int ret = miniLcd.open();
        if (ret != 0) {
            show_result(ret);
            return;
        }
        //startTask();
        show_result(ret);
        Log.i("guanjie", "ret:" + ret);
    }


    public void displayPicture() {

        Log.i("guanjie", "displayPicture");
        if (!open_flg) {
            open_flg = true;
            openMiniLcd();
            show(getContext(), "Please open MiniLCD");
            return;
        }
        handelPosiflextPlayListData();
    }
}

