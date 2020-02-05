package com.apollopharmacy.mpospharmacist.ui.dashboard.tabview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.payment.PaymentFragment;
import com.apollopharmacy.mpospharmacist.ui.dashboard.fragments.sales.SalesFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new SalesFragment();
        } else if (position == 1) {
            fragment = new PaymentFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Sales";
        } else if (position == 1) {
            title = "Payments";
        }
        return title;
    }
}
