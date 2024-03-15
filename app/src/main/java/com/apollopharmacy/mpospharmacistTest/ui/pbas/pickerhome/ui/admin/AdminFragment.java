package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentAdminBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders.AllOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;

import java.util.List;

import javax.inject.Inject;

public class AdminFragment extends BaseFragment implements AdminMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {
    @Inject
    AdminMvpPresenter<AdminMvpView> mPresenter;

    private FragmentAdminBinding adminBinding;
    List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adminBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AdminFragment.this);
        return adminBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        adminBinding.setCallback(mPresenter);
        mPresenter.getOmsTransactionHeader();
        actionbarSetUp();
    }

    /*private void pieChartSetUp(Map<String, Integer> pieDataSet) {
        ArrayList<PieEntry> records = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : pieDataSet.entrySet()) {
            records.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet dataSet = new PieDataSet(records, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

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
        dataSet.setSliceSpace(2f);

        PieData pieData = new PieData(dataSet);

        adminBinding.pieChart.getLegend().setEnabled(true);
        adminBinding.pieChart.getLegend().setWordWrapEnabled(true);
        adminBinding.pieChart.setDrawHoleEnabled(false);
        adminBinding.pieChart.getDescription().setEnabled(false);

        adminBinding.pieChart.setData(pieData);
        adminBinding.pieChart.setDrawEntryLabels(false);
        adminBinding.pieChart.invalidate();
    }*/

    private void actionbarSetUp() {
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.welcome.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.GONE);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccessGetOmsTransactionHeader(List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList) {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            this.omsHeaderList = omsHeaderList;
            long stockAvailable = omsHeaderList.stream().filter(omsHeader -> omsHeader.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE") && omsHeader.getPickPackUser().isEmpty()).count();
            long partialAvailable = omsHeaderList.stream().filter(omsHeader -> omsHeader.getStockStatus().equalsIgnoreCase("PARTIAL AVAILABLE") && omsHeader.getPickPackUser().isEmpty()).count();
            long pickerCount = omsHeaderList.stream().filter(omsHeader -> omsHeader.getPickPackStatus() == 0).count();
            long checkerCount = omsHeaderList.stream().filter(omsHeader -> omsHeader.getPickPackStatus() == 5).count();
            adminBinding.allOrders.setText(String.valueOf(omsHeaderList.size()));
            adminBinding.stockAvailable.setText(String.valueOf(stockAvailable));
            adminBinding.partiallyAvailable.setText(String.valueOf(partialAvailable));
            adminBinding.pickerIssues.setText(String.valueOf(pickerCount));
            adminBinding.checker.setText(String.valueOf(checkerCount));
            /*Map<String, Integer> pieData = new LinkedHashMap<>();
            pieData.put("Stock Available", (int) stockAvailable);
            pieData.put("Stock Partially Available", (int) partialAvailable);
            pieData.put("Picker Issues", 15);
            pieData.put("Checker", 10);
            pieChartSetUp(pieData);*/
        }
    }

    @Override
    public void onClickAllOrders() {
        Intent intent = AllOrdersActivity.getStartIntent(requireContext(), omsHeaderList);
        startActivity(intent);
    }
}