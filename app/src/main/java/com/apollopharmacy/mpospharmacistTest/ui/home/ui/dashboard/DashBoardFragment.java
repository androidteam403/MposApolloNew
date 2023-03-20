package com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.pt.MiniLcd;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.apollopharmacy.mpospharmacistTest.BuildConfig;
import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentDashboardBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionslistFragment;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class DashBoardFragment extends BaseFragment implements DashBoardMvpView, MainActivity.UserIneractionListener, MainActivity.OnBackPressedListener {

    @Inject
    DashBoardMvpPresenter<DashBoardMvpView> mPresenter;
    private FragmentDashboardBinding dashboardBinding;
    private int dotscount;
    MiniLcd miniLcd = null;
    boolean open_flg = false;


    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NotNull Network network) {
            if (getBaseActivity() != null) {
                getBaseActivity().runOnUiThread(() -> {
                    dashboardBinding.internet.setTextColor(getResources().getColor(R.color.order_success_color));
                    dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark));
                });
            }
        }

        @Override
        public void onLost(@NotNull Network network) {
            if (getBaseActivity() != null)
                getBaseActivity().runOnUiThread(() -> {
                    dashboardBinding.internet.setTextColor(getResources().getColor(R.color.grey));
                    dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.cross_mark));
                });
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DashBoardFragment.this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnUserIneractionListener(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnBackPressedListener(this);
        return dashboardBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        dashboardBinding.setCallBack(mPresenter);
        DeviceDetailsModel detailsModel = new DeviceDetailsModel();
        detailsModel.setBuildVersion(BuildConfig.VERSION_NAME);
        detailsModel.setDeviceName(android.os.Build.MODEL);
        detailsModel.setDeviceMacId(CommonUtils.getDeviceId(getBaseActivity()));
        dashboardBinding.setDevice(detailsModel);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        dashboardBinding.viewPager.setAdapter(viewPagerAdapter);

        if (isNetworkConnected()) {
            dashboardBinding.internet.setTextColor(getResources().getColor(R.color.order_success_color));
            dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark));
        } else {
            dashboardBinding.internet.setTextColor(getResources().getColor(R.color.grey));
            dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.cross_mark));
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }

        dashboardBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (mPresenter.enablescreens()) {
            mPresenter.onMposTabApiCall();
            turnOnScreen();
        }
    }

    @Override
    public void onClickNewOrderBtn() {
        Navigation.findNavController(dashboardBinding.newOrder).navigate(R.id.nav_billing);
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

    public void playListData(String filePath, int pos) {
        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        dashboardBinding.imageView.setImageBitmap(myBitmap);
        dashboardBinding.imageView.setVisibility(View.VISIBLE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        ((MainActivity) getActivity()).closeDrawer();
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dashboardBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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

    @SuppressLint("ResourceType")
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
                dashboardBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        dashboardBinding.imageView.setVisibility(View.GONE);
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
        handler.postDelayed(r, 180 * 1000);
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


    private List<RowsEntity> rowsPosiFlexEntitiesList;

    @Override
    public void onSucessMposPosiflex() {
        rowsPosiFlexEntitiesList = mPresenter.getPosiflextDataListEntity();
        boolean isLoop = false;
        for (int i = 0; i < rowsPosiFlexEntitiesList.size(); i++) {
            if (rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                    rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                String path = String.valueOf(FileUtil.getMediaFilePath(rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                File file = new File(path);
                if (!file.exists()) {
                    isLoop = true;
                    mPresenter.onDownloadPosiflexCall(rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
                            rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), i);
                    break;
                }
            }
        }
        if (!isLoop) {
            displayPicture();
        }
    }

    public void handelPosiflextPlayListData() {
        if (rowsPosiFlexEntitiesList != null && rowsPosiFlexEntitiesList.size() > 0) {
            boolean isAllFilesExist = false;
            for (int i = 0; i < rowsPosiFlexEntitiesList.size(); i++) {
                if (rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                        rowsPosiFlexEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
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

    @Override
    public void doBack() {
        Dialog dialog = new Dialog(getContext());//R.style.Theme_AppCompat_DayNight_NoActionBar
        DialogCancelBinding dialogCancelBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_cancel, null, false);
        dialogCancelBinding.title.setText("Alert!");
        dialogCancelBinding.dialogMessage.setText("Do you want to exit?");
        dialog.setContentView(dialogCancelBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancelBinding.dialogButtonNO.setOnClickListener(v -> dialog.dismiss());
        dialogCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            getActivity().finish();
            stopLooping = true;
        });
        dialog.show();
    }
}