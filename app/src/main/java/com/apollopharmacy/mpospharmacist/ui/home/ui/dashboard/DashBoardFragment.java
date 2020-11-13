package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.apollopharmacy.mpospharmacist.BuildConfig;
import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentDashboardBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

public class DashBoardFragment extends BaseFragment implements DashBoardMvpView {

    @Inject
    DashBoardMvpPresenter<DashBoardMvpView> mPresenter;
    private FragmentDashboardBinding dashboardBinding;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NotNull Network network) {
            // network available
            if (getBaseActivity() != null) {
                getBaseActivity().runOnUiThread(() -> {
                    dashboardBinding.internet.setTextColor(getResources().getColor(R.color.order_success_color));
                    dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark));
                });
            }
        }

        @Override
        public void onLost(@NotNull Network network) {
            // network unavailable
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

//        dotscount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotscount];
//        for (int i = 0; i < dotscount; i++) {
//
//            dots[i] = new ImageView(getContext());
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            dashboardBinding.SliderDots.addView(dots[i], params);
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        dashboardBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    //    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }
                // dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        dashboardBinding.ChartProgressBar.setDataList(onChartBar());
//        dashboardBinding.ChartProgressBar.build();
        mPresenter.onPlayListApiCall();
        idealScreen();
    }

    @Override
    public void onClickNewOrderBtn() {
        Navigation.findNavController(dashboardBinding.newOrder).navigate(R.id.nav_billing);
//        startActivity(SearchCustomerDoctorDetailsActivity.getStartIntent(getBaseActivity()));
//        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private List<RowsEntity> rowsEntitiesList;

    @Override
    public void onSucessPlayList() {
        rowsEntitiesList = mPresenter.getDataListEntity();
        for (int i = 0; i < rowsEntitiesList.size(); i++) {
            String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
            File file = new File(path);
            if (!file.exists()) {
                mPresenter.onDownloadApiCall(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
                        path, i);
                break;
            }
        }
    }

    private boolean stopLooping;

    public void handelPlayList() {
        if (!stopLooping) {
            for (int i = 0; i < rowsEntitiesList.size(); i++) {
                if (!rowsEntitiesList.get(i).isPlayed()) {
                    String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                    File file = new File(path);
                    if (file.exists()) {
                        playListData(path, i);
                        break;
                    }
                }
            }
        }
    }

    public void playListData(String filePath, int pos) {
        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        dashboardBinding.imageView.setImageBitmap(myBitmap);
        dashboardBinding.imageView.setVisibility(View.VISIBLE);
        dashboardBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_animation));
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
        }, 2000);
    }

    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }

    private Handler handler;
    private Runnable r;

    public void idealScreen() {
        stopLooping = false;
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                getActivity().getActionBar().hide();
                dashboardBinding.imageView.setVisibility(View.VISIBLE);
//                if (t.getState() == Thread.State.NEW) {
//                    t.start();
//                }
                handelPlayList();
                dashboardBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        dashboardBinding.imageView.setVisibility(View.GONE);
                        stopLooping = true;
                        startHandler();
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
        handler.postDelayed(r, 10 * 1000);
    }
}