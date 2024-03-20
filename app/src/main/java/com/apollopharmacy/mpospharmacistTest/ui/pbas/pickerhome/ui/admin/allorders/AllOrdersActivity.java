package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityAllOrdersBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders.adapter.OrdersAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.notallocatedorders.NotAllocatedOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.orderdetails.OrderDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders.StockAvailableOrdersActivity;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class AllOrdersActivity extends BaseActivity implements AllOrdersMvpView {
    private ActivityAllOrdersBinding allOrdersBinding;
    private OrdersAdapter ordersAdapter;
    List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    @Inject
    AllOrdersMvpPresenter<AllOrdersMvpView> mPresenter;

    public static Intent getStartIntent(Context context, List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList) {
        Intent intent = new Intent(context, AllOrdersActivity.class);
        intent.putExtra("omsHeaderList", (Serializable) omsHeaderList);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_orders);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AllOrdersActivity.this);
        allOrdersBinding.setCallback(mPresenter);
        setUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void setUp() {
        if (getIntent() != null) {
            omsHeaderList = (List<GetOMSTransactionHeaderResponse.OMSHeader>) getIntent().getSerializableExtra("omsHeaderList");
        }
        /*if (omsHeaderList != null && omsHeaderList.size() > 0) {
            allOrdersBinding.allOrdersCount.setText("("+omsHeaderList.size()+")");
            ordersAdapter = new OrdersAdapter(this, this, omsHeaderList);
            allOrdersBinding.ordersRecycler.setAdapter(ordersAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            allOrdersBinding.ordersRecycler.setLayoutManager(layoutManager);
        } else {
            allOrdersBinding.ordersRecycler.setVisibility(View.GONE);
            allOrdersBinding.noOrderFoundText.setVisibility(View.VISIBLE);
            allOrdersBinding.allOrdersCount.setText("(-)");
        }*/

        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            allOrdersBinding.allOrdersCount.setText(String.valueOf(omsHeaderList.size()));
        } else {
            allOrdersBinding.allOrdersCount.setText("-");
        }
        long stockAvailableCount = omsHeaderList.stream()
                .filter(omsHeader -> omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE"))
                .count();
        long partialAvailableCount = omsHeaderList.stream()
                .filter(omsHeader -> omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("PARTIAL AVAILABLE"))
                .count();
        long notAvailableCount = omsHeaderList.stream()
                .filter(omsHeader -> omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("NOT AVAILABLE"))
                .count();
        allOrdersBinding.stockAvailableCount.setText(String.valueOf(stockAvailableCount));
        allOrdersBinding.partiallyAvailableCount.setText(String.valueOf(partialAvailableCount));
        allOrdersBinding.notAvailableCount.setText(String.valueOf(notAvailableCount));
        Map<String, Integer> pieData = new LinkedHashMap<>();
        pieData.put("Stock Available", (int) stockAvailableCount);
        pieData.put("Partially Available", (int) partialAvailableCount);
        pieData.put("Not Available", (int) notAvailableCount);
        pieChartSetUp(pieData);

        allOrdersBinding.stockAvailableLayout.setOnClickListener(view -> {
            List<GetOMSTransactionHeaderResponse.OMSHeader> stockAvailableOrders = omsHeaderList.stream()
                    .filter(omsHeader -> omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE"))
                    .collect(Collectors.toList());
            Intent intent = StockAvailableOrdersActivity.getStartIntent(AllOrdersActivity.this, stockAvailableOrders);
            startActivity(intent);
        });
    }

    private void pieChartSetUp(Map<String, Integer> pieDataSet) {
        ArrayList<PieEntry> records = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : pieDataSet.entrySet()) {
            records.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet dataSet = new PieDataSet(records, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15f);

        int[] colors = {Color.parseColor("#74b99f"), Color.parseColor("#fdb813"), Color.parseColor("#e7698a")};
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
        allOrdersBinding.pieChart.getLegend().setEnabled(true);
        allOrdersBinding.pieChart.getLegend().setWordWrapEnabled(true);
        allOrdersBinding.pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        allOrdersBinding.pieChart.setDrawHoleEnabled(false);
        allOrdersBinding.pieChart.getDescription().setEnabled(false);
        allOrdersBinding.pieChart.setData(pieData);
        allOrdersBinding.pieChart.setDrawEntryLabels(false);
        allOrdersBinding.pieChart.invalidate();
    }

    @Override
    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onClickOrder(int position) {
        if (position == 0) {
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("status", "inprogress");
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(this, NotAllocatedOrdersActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("status", "request");
            startActivity(intent);
        }
    }

    @Override
    public void onSuccessGetOmsTransactionHeader(List<GetOMSTransactionHeaderResponse.OMSHeader> omsHeaderList) {
        if (omsHeaderList != null && omsHeaderList.size() > 0) {
            this.omsHeaderList.clear();
            this.omsHeaderList = omsHeaderList;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOmsTransactionHeader();
    }
}