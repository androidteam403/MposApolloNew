package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apollopharmacy.mpospharmacist.BuildConfig;
import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentDashboardBinding;
import com.apollopharmacy.mpospharmacist.root.ApolloMposApp;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsActivity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;


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
            getBaseActivity().runOnUiThread(() -> {
                dashboardBinding.internet.setTextColor(getResources().getColor(R.color.order_success_color));
                dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark));
            });

        }

        @Override
        public void onLost(@NotNull Network network) {
            // network unavailable
            getBaseActivity().runOnUiThread(() -> {
                dashboardBinding.internet.setTextColor(getResources().getColor(R.color.grey));
                dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.cross_mark));
            });

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false);
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

        if(isNetworkConnected()){
            dashboardBinding.internet.setTextColor(getResources().getColor(R.color.order_success_color));
            dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark));
        }else{
            dashboardBinding.internet.setTextColor(getResources().getColor(R.color.grey));
            dashboardBinding.internetimagebackground.setImageDrawable(getResources().getDrawable(R.drawable.cross_mark));
        }

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

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
    }







    @Override
    public void onClickNewOrderBtn() {
        Navigation.findNavController(dashboardBinding.newOrder).navigate(R.id.nav_billing);
//        startActivity(SearchCustomerDoctorDetailsActivity.getStartIntent(getBaseActivity()));
//        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}