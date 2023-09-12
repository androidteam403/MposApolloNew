package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentAdminBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders.AllOrdersActivity;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class AdminFragment extends BaseFragment implements AdminMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {
    @Inject
    AdminMvpPresenter<AdminMvpView> presenter;

    private FragmentAdminBinding adminBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adminBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false);
        return adminBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        actionbarSetUp();
        pieChartSetUp();
        adminBinding.totalOrdersLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AllOrdersActivity.class);
            startActivity(intent);
        });
    }

    private void pieChartSetUp() {
        ArrayList<PieEntry> records = new ArrayList<>();
        records.add(new PieEntry(25, ""));
        records.add(new PieEntry(30, ""));
        records.add(new PieEntry(15, ""));
        records.add(new PieEntry(10, ""));

        PieDataSet dataSet = new PieDataSet(records, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        int[] colors = {Color.parseColor("#73b99f"), Color.parseColor("#c4a5de"), Color.parseColor("#fdb018"), Color.parseColor("#f58778")};
        dataSet.setColors(colors);

        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int) value);
            }
        });

//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setValueLineColor(Color.TRANSPARENT);
//        dataSet.setValueTextColor(Color.BLACK);
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setSliceSpace(2f);

        PieData pieData = new PieData(dataSet);

        adminBinding.pieChart.getLegend().setEnabled(false);
        adminBinding.pieChart.setDrawHoleEnabled(false);
        adminBinding.pieChart.getDescription().setEnabled(false);

        adminBinding.pieChart.setData(pieData);
        adminBinding.pieChart.invalidate();
    }

    private void actionbarSetUp() {
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.welcome.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Dashboard");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.GONE);
    }

    @Override
    public void onClickFilters() {

    }

    @Override
    public void onItemClick() {

    }

    @Override
    public void onClickStockAvailable(boolean isStockAvailableChecked) {

    }

    @Override
    public void onClicklabelSizeIcon() {

    }

    @Override
    public void onClickRefresh() {

    }

    @Override
    public void onClickUnHold() {

    }

    @Override
    public void onClickRefreshPickerPackerBiller() {

    }
}