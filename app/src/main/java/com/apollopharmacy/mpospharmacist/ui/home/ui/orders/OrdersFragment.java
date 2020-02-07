package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;


public class OrdersFragment extends BaseFragment implements OrdersMvpView {

    @Inject
    OrdersMvpPresenter<OrdersMvpView> mPresenter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order, container, false);
        final TextView textView = root.findViewById(R.id.text_share);

        return root;
    }

    @Override
    protected void setUp(View view) {

    }
}