package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentDashboardPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class DashboardFragment extends BaseFragment implements DashboardMvpView, OnChartValueSelectedListener {
    @Inject
    DashboardMvpPresenter<DashboardMvpView> mPresenter;
    private FragmentDashboardPBinding fragmentDashboardBinding;
   List<TransactionHeaderResponse.OMSHeader> racksDataResponse;

    public static Intent getStartActivity(Context context) {
        return new Intent(context, DashboardFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_p, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DashboardFragment.this);
        return fragmentDashboardBinding.getRoot();

    }

    @Override
    protected void setUp(View view) {
        fragmentDashboardBinding.setCallback(mPresenter);
        setGraphData();
        setData();
//        Glide.with(this)
//                .load("https://apis.v35.dev.zeroco.de/zc-v3.1-fs-svc/2.0/apollo_rider/get/41B8F83052E720DA0FC28401C9BFAA90396DCB4FD14F508D641DBC42F5808C634160E6E9BDFF4D97E46A107F1185330BE9BE56FEC6E2C512EC7E08CAAA498D8FA633B599A9A34C9C97BCF338231C7AA91F16F94D257D61803FBC97DE5FEEACF62933C5F49DFFBE9EBADD5C68A6A9245EE277F7369BEBB4A75B56F81CDA296FE0F45824C81F0E7A9C29BA1E691D49C48BCB3E2586250A732BC0C95D8C9A1E1154C38FC1DFED04C09C36722BD70B9D0E10952C6B12C3EABEF551397B781F83118196C4F5899C1A7EBB728DE8B78537C55B735B4BEAE021E0391CB1ACE72296B00A8869B3AA7F4BF1674AC2BF9952BF39A67ABCA6DC6BF69C69CCC9C5766F79B2F9")
//                .circleCrop()
//                .into(fragmentDashboardBinding.pickerImg);
    }

    public void setGraphData() {
//        fragmentDashboardBinding.barChart.setOnChartValueSelectedListener(this);
//        fragmentDashboardBinding.barChart.setDrawBarShadow(false);
//        fragmentDashboardBinding.barChart.setDrawValueAboveBar(false);
//        fragmentDashboardBinding.barChart.getDescription().setEnabled(false);
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        fragmentDashboardBinding.barChart.setMaxVisibleValueCount(50);
//        // scaling can now only be done on x- and y-axis separately
//        fragmentDashboardBinding.barChart.setPinchZoom(false);
//        fragmentDashboardBinding.barChart.setScaleEnabled(false);
//        fragmentDashboardBinding.barChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("1");
        xVals.add("2");
        xVals.add("3");
        xVals.add("4");
        xVals.add("5");
        xVals.add("6");

//        XAxis xAxis = fragmentDashboardBinding.barChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//        xAxis.setLabelCount(7);
        IAxisValueFormatter xAxisFormatter = new IndexAxisValueFormatter(xVals);
//        xAxis.setValueFormatter(xAxisFormatter);
//        fragmentDashboardBinding.barChart.getAxisRight().setEnabled(false);
//        YAxis leftAxis = fragmentDashboardBinding.barChart.getAxisLeft();
//        leftAxis.setLabelCount(5, false);
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(30f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

//        Legend l = fragmentDashboardBinding.barChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.NONE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(7f);


//        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
//        mv.setChartView(dashboardBinding.barChart); // For bounds control
//        fragmentDashboardBinding.barChart.getLegend().setEnabled(false);   // Hide the legend
//        dashboardBinding.barChart.setMarker(mv); // Set the marker to the chart
    }

    private void setData() {
//        fragmentDashboardBinding.barChart.invalidate();
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
//        yVals1.add(new BarEntry(0, (int) monVal));

        yVals1.add(new BarEntry(0, new float[]{10, 10, 10}, ""));
        yVals1.add(new BarEntry(1, new float[]{15, 5, 5}, ""));
        yVals1.add(new BarEntry(2, new float[]{15, 10, 5}, ""));
        yVals1.add(new BarEntry(3, new float[]{5, 10, 5}, ""));
        yVals1.add(new BarEntry(4, new float[]{15, 5, 5}, ""));
        yVals1.add(new BarEntry(5, new float[]{5, 10, 5}, ""));

        BarDataSet set1 = new BarDataSet(yVals1, "");
//        set1.setDrawIcons(false);
        set1.setColors(getColors());
//        set1.setColors(deliveredOrdersColour);
//        set1.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
//        data.setValueTextSize(8f);
//             data.setValueTypeface(mTfLight);
        data.setBarWidth(0.5f);
        data.setValueFormatter(new StackedValueFormatter(false, "", 1));
        data.setValueTextColor(Color.WHITE);


//        fragmentDashboardBinding.barChart.setData(data);
//        fragmentDashboardBinding.barChart.animateY(2000);

//        for (IBarDataSet iSet : dataSets) {
//            BarDataSet set = (BarDataSet) iSet;
//            set.setDrawValues(!set.isDrawValuesEnabled());
//        }

        for (IBarDataSet set : dataSets)
            set.setDrawValues(!set.isDrawValuesEnabled());
        //    }
//        dashboardBinding.barChart.getLegend().setEnabled(false);
    }

    private int[] getColors() {
        int[] colors = {Color.rgb(0, 194, 195), Color.parseColor("#fdb813"), Color.rgb(255, 1, 1)};
        System.arraycopy(colors, 0, colors, 0, 2);
        return colors;
    }

    /**
     * Called when a value has been selected inside the chart.
     *
     * @param e The selected Entry
     * @param h The corresponding highlight object that contains information
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClickOpenOrders() {
        startActivity(OpenOrdersActivity.getStartActivity(getContext()));
    }

    @Override
    public void onClickFromDate() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
//        if (fragmentDashboardBinding.fromDate.getText().toString().trim().isEmpty()) {
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//        } else {
//            String selectedBirthDate = fragmentDashboardBinding.fromDate.getText().toString().trim();
//            String[] expDate = selectedBirthDate.split("-");
//            mYear = Integer.parseInt(expDate[0]);
//            mMonth = Integer.parseInt(expDate[1]) - 1;
//            mDay = Integer.parseInt(expDate[2]);
//        }
//        final DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
//            String selectedDate;
//            c.set(year, monthOfYear, dayOfMonth);
//            selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime());
//            fragmentDashboardBinding.fromDate.setText(selectedDate);
//        }, mYear, mMonth, mDay);
//        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis()));// - (1000 * 60 * 60 * 24 * 365.25 * 18)
//        dialog.show();
    }

    @Override
    public void onClickToDate() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
//        if (fragmentDashboardBinding.toDate.getText().toString().trim().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
//        } else {
//            String selectedBirthDate = fragmentDashboardBinding.toDate.getText().toString().trim();
//            String[] expDate = selectedBirthDate.split("-");
//            mYear = Integer.parseInt(expDate[0]);
//            mMonth = Integer.parseInt(expDate[1]) - 1;
//            mDay = Integer.parseInt(expDate[2]);
//        }
        final DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime());
//            fragmentDashboardBinding.toDate.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis()));// - (1000 * 60 * 60 * 24 * 365.25 * 18)
        dialog.show();
    }

    @Override
    public void onClickToday() {
//        allUnselectTodayWeeklyMonthlyYearly();
//        fragmentDashboardBinding.today.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_green_bg));
//        fragmentDashboardBinding.today.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClickWeekly() {
//        allUnselectTodayWeeklyMonthlyYearly();
//        fragmentDashboardBinding.weekly.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_green_bg));
//        fragmentDashboardBinding.weekly.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onClickMonthly() {
//        allUnselectTodayWeeklyMonthlyYearly();
//        fragmentDashboardBinding.monthly.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_green_bg));
//        fragmentDashboardBinding.monthly.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClickYearly() {
//        allUnselectTodayWeeklyMonthlyYearly();
//        fragmentDashboardBinding.yearly.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_green_bg));
//        fragmentDashboardBinding.yearly.setTextColor(getResources().getColor(R.color.white));
    }

    private void allUnselectTodayWeeklyMonthlyYearly() {
//        fragmentDashboardBinding.today.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_bg));
//        fragmentDashboardBinding.weekly.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_bg));
//        fragmentDashboardBinding.monthly.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_bg));
//        fragmentDashboardBinding.yearly.setBackground(getResources().getDrawable(R.drawable.dashboard_today_text_bg));
//
//        fragmentDashboardBinding.today.setTextColor(getResources().getColor(R.color.text_color_grey));
//        fragmentDashboardBinding.weekly.setTextColor(getResources().getColor(R.color.text_color_grey));
//        fragmentDashboardBinding.monthly.setTextColor(getResources().getColor(R.color.text_color_grey));
//        fragmentDashboardBinding.yearly.setTextColor(getResources().getColor(R.color.text_color_grey));
    }

//    @Override
//    public void onBackPressed() {
//
//    }
}




