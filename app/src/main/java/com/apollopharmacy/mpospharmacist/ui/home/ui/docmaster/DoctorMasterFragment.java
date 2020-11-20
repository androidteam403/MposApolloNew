package com.apollopharmacy.mpospharmacist.ui.home.ui.docmaster;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentDocMasterBinding;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;


public class DoctorMasterFragment extends BaseFragment implements DoctorMasterMvpView, MainActivity.UserIneractionListener, MainActivity.OnBackPressedListener {
    @Inject
    DoctorMasterMvpPresenter<DoctorMasterMvpView> mPresenter;

    private FragmentDocMasterBinding fragmentDocMasterBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentDocMasterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_master, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DoctorMasterFragment.this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnUserIneractionListener(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnBackPressedListener(this);

        return fragmentDocMasterBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        fragmentDocMasterBinding.setCallback(mPresenter);
        mPresenter.onMposTabApiCall();
        turnOnScreen();
    }


    @Override
    public void onSubmitBtnClick() {
        if (validate()) {
            mPresenter.handleAddDoctorService();
        }
    }

    @Override
    public void onClickBackPressed() {

    }

    @Override
    public void addDoctorSuccess(AddDoctorResModel addDoctorResModel) {
        Toast.makeText(getContext(), "" + addDoctorResModel.getReturnMessage(), Toast.LENGTH_SHORT).show();
        DoctorSearchResModel.DropdownValueBean doctorModel = new DoctorSearchResModel.DropdownValueBean();
        doctorModel.setCode(addDoctorResModel.getDocRegID());
        doctorModel.setDisplayText(addDoctorResModel.getDocName());
        fragmentDocMasterBinding.doctorRegNumber.setText("");
        fragmentDocMasterBinding.doctorName.setText("");
        fragmentDocMasterBinding.speciality.setText("");
        fragmentDocMasterBinding.placeOfPractice.setText("");
        fragmentDocMasterBinding.address.setText("");
        fragmentDocMasterBinding.phoneNumber.setText("");
//        Intent returnIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("doctor_info", doctorModel);
//        bundle.putSerializable("sales_info", salesEntity);
//        returnIntent.putExtras(bundle);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
    }

    @Override
    public void addDoctorFailed(String errMsg) {
        showMessage(errMsg);
    }

    @Override
    public String getDoctorName() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.doctorName.getText())).toString();
    }

    @Override
    public String getDoctorRegNo() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.doctorRegNumber.getText())).toString();
    }

    @Override
    public String getSpeciality() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.speciality.getText())).toString();
    }

    @Override
    public String getPlaceOfPractice() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.placeOfPractice.getText()).toString());
    }

    @Override
    public String getAddress() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.address.getText()).toString());
    }

    @Override
    public String getPhoneNo() {
        return (Objects.requireNonNull(fragmentDocMasterBinding.phoneNumber.getText())).toString();
    }


    private boolean validate() {
        String doctorRegNo = Objects.requireNonNull(fragmentDocMasterBinding.doctorRegNumber.getText()).toString();
        String dctrName = Objects.requireNonNull(fragmentDocMasterBinding.doctorName.getText()).toString();
        String spclty = Objects.requireNonNull(fragmentDocMasterBinding.speciality.getText()).toString();
        String placeofPractice = Objects.requireNonNull(fragmentDocMasterBinding.placeOfPractice.getText()).toString();
        String adrs = Objects.requireNonNull(fragmentDocMasterBinding.address.getText()).toString();
        String pNumber = Objects.requireNonNull(fragmentDocMasterBinding.phoneNumber.getText()).toString();
        if (doctorRegNo.isEmpty()) {
            fragmentDocMasterBinding.doctorRegNumber.setError("Doctor registration number should not be empty");
            fragmentDocMasterBinding.doctorRegNumber.requestFocus();
            return false;
        } else if (dctrName.isEmpty()) {
            fragmentDocMasterBinding.doctorName.setError("Doctor name should not be empty");
            fragmentDocMasterBinding.doctorName.requestFocus();
            return false;
        } else if (!CommonUtils.nameVallidate(dctrName)) {
            fragmentDocMasterBinding.doctorName.setError("Invalid doctor name");
            fragmentDocMasterBinding.doctorName.requestFocus();
            return false;
        } else if (spclty.isEmpty()) {
            fragmentDocMasterBinding.speciality.setError("Speciality should not be empty");
            fragmentDocMasterBinding.speciality.requestFocus();
            return false;
        } else if (placeofPractice.isEmpty()) {
            fragmentDocMasterBinding.placeOfPractice.setError("Place of Practice should not be empty");
            fragmentDocMasterBinding.placeOfPractice.requestFocus();
            return false;
        } else if (adrs.isEmpty()) {
            fragmentDocMasterBinding.address.setError("Address should not be empty");
            fragmentDocMasterBinding.address.requestFocus();
            return false;
        } else if (pNumber.isEmpty()) {
            fragmentDocMasterBinding.phoneNumber.setError("Phone number should not be empty");
            fragmentDocMasterBinding.phoneNumber.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(pNumber)) {
            fragmentDocMasterBinding.phoneNumber.setError("Invalid Phone Number");
            fragmentDocMasterBinding.phoneNumber.requestFocus();
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
        fragmentDocMasterBinding.imageView.setImageBitmap(myBitmap);
        fragmentDocMasterBinding.imageView.setVisibility(View.VISIBLE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        ((MainActivity) getActivity()).closeDrawer();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        fragmentDocMasterBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
                        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    }
                }
                handelPlayList();
                fragmentDocMasterBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        fragmentDocMasterBinding.imageView.setVisibility(View.GONE);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
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
        handler.postDelayed(r, 60 * 1000);
    }

    @Override
    public void userInteraction() {
        stopHandler();
        startHandler();
    }

    public void turnOnScreen() {
        final Window win = getActivity().getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }


    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }

//    @Override
//    public void userBackListenerInteraction() {
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        fragmentDocMasterBinding.imageView.setVisibility(View.VISIBLE);
//        getActivity().finish();
//    }

    @Override
    public void doBack() {
        getActivity().finish();
        stopLooping = true;
    }
}