package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentStockInwardProcessBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess.adapter.StockInwardProcessAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.StockInwardProcessDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class StockInwardProcessFragment extends BaseFragment implements StockInwardProcessMvpView {

    @Inject
    StockInwardProcessMvpPresenter<StockInwardProcessMvpView> mPresenter;
    private String fromDate = CommonUtils.getBeforeSevenDaysDate();
    private String toDate = CommonUtils.getCurrentDate("yyyy-MM-dd");
    private FragmentStockInwardProcessBinding stockInwardProcessBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stockInwardProcessBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_inward_process, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(StockInwardProcessFragment.this);
        return stockInwardProcessBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        stockInwardProcessBinding.setCallback(mPresenter);
        String strDate = fromDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String dateNewFormat = new SimpleDateFormat("dd-MMMM-yyyy").format(date);

        String strDate2 = toDate;
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            date2 = dateFormat2.parse(strDate2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String dateNewFormatToDate = new SimpleDateFormat("dd-MMMM-yyyy").format(date2);
//        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat outputFormat = new SimpleDateFormat("dd-MMMM-yyyy");
//        String inputDateStr=fromDate;
//        Date date = null;
//        try {
//            date = inputFormat.parse(inputDateStr);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        String fromDateStr = outputFormat.format(date);
        mPresenter.getInventoryTransactionDetails(dateNewFormat, dateNewFormatToDate);
        fromToDateConfig();
        actionbarConfiguration();
        setAdapter();
    }

    private void fromToDateConfig() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date fromDates = formatter.parse(fromDate);
            long fromDateMills = fromDates.getTime();
            stockInwardProcessBinding.fromDate.setText(CommonUtils.getDateFormatForStockInwardProcessFromTo(fromDateMills));

            Date toDates = formatter.parse(toDate);
            long toDateMills = toDates.getTime();
            stockInwardProcessBinding.toDate.setText(CommonUtils.getDateFormatForStockInwardProcessFromTo(toDateMills));

        } catch (Exception e) {
            System.out.println("onViewCreated:::::::::::::::::" + e.getMessage());
        }
    }

    private void setAdapter() {
//        StockInwardProcessAdapter stockInwardProcessAdapter = new StockInwardProcessAdapter(getContext(), this, getInventoryTransactionDetailsResponse.getInventoryData());
//        LinearLayoutManager mLinearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        stockInwardProcessBinding.stockInwardProcessRecycler.setLayoutManager(mLinearManager);
//        stockInwardProcessBinding.stockInwardProcessRecycler.setAdapter(stockInwardProcessAdapter);
    }

    private void actionbarConfiguration() {
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.setTitle("Stock inward process");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.GONE);
    }

    @Override
    public void onClickFromDate() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        //Objects.requireNonNull(summaryBinding.fromDate).getText().toString().isEmpty()
        if (fromDate.isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = fromDate;
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[0]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[2]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime());
//            requiredDOBFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
//            try {

            if (CommonUtils.isFromDateBeforeToDate(selectedDate, toDate)) {
                stockInwardProcessBinding.fromDate.setText(CommonUtils.getDateFormatForStockInwardProcessFromTo(c.getTimeInMillis()));
                this.fromDate = CommonUtils.getSqlDateFormat(c);
//                    stockInwardProcessBinding.dateComparisonErrorText.setVisibility(View.GONE);
//                    onClickOk();
            } else {
                Toast.makeText(getContext(), "Invalid date selection", Toast.LENGTH_SHORT).show();
//                    summaryBinding.dateComparisonErrorText.setVisibility(View.VISIBLE);
            }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis()));// - (1000 * 60 * 60 * 24 * 365.25 * 18)
        dialog.show();
    }

    @Override
    public void onClickToDate() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        //Objects.requireNonNull(summaryBinding.toDate).getText().toString().isEmpty()
        if (toDate.isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = toDate;
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[0]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[2]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime());
//            requiredDOBFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
//            try {

            if (CommonUtils.isFromDateBeforeToDate(fromDate, selectedDate)) {
                stockInwardProcessBinding.toDate.setText(CommonUtils.getDateFormatForStockInwardProcessFromTo(c.getTimeInMillis()));
                this.toDate = CommonUtils.getSqlDateFormat(c);
//                    stockInwardProcessBinding.dateComparisonErrorText.setVisibility(View.GONE);
//                    onClickOk();
            } else {
                Toast.makeText(getContext(), "Invalid date selection", Toast.LENGTH_SHORT).show();

//                    stockInwardProcessBinding.dateComparisonErrorText.setVisibility(View.VISIBLE);
            }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis()));// - (1000 * 60 * 60 * 24 * 365.25 * 18)
        dialog.show();
    }

    @Override
    public void onClickItem() {
        startActivity(StockInwardProcessDetailsActivity.getStartIntent(getContext()));
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }


    @Override
    public void onSuccessgetInventoryTransactionDetails(GetInventoryTransactionDetailsResponse getInventoryTransactionDetailsResponse) {
        if (getInventoryTransactionDetailsResponse != null && getInventoryTransactionDetailsResponse.getInventoryData() != null && getInventoryTransactionDetailsResponse.getInventoryData().size() > 0) {
            stockInwardProcessBinding.stockInwardProcessRecycler.setVisibility(View.VISIBLE);
            stockInwardProcessBinding.noRecordFound.setVisibility(View.GONE);
            StockInwardProcessAdapter stockInwardProcessAdapter = new StockInwardProcessAdapter(getContext(), this, getInventoryTransactionDetailsResponse.getInventoryData());
            LinearLayoutManager mLinearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            stockInwardProcessBinding.stockInwardProcessRecycler.setLayoutManager(mLinearManager);
            stockInwardProcessBinding.stockInwardProcessRecycler.setAdapter(stockInwardProcessAdapter);
        } else {
            stockInwardProcessBinding.stockInwardProcessRecycler.setVisibility(View.GONE);
            stockInwardProcessBinding.noRecordFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickShow() {
        String strDate = stockInwardProcessBinding.fromDate.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String dateNewFormat = new SimpleDateFormat("dd-MMMM-yyyy").format(date);

        String strDate2 = stockInwardProcessBinding.toDate.getText().toString();
        DateFormat dateFormat2 = new SimpleDateFormat("MMMM dd, yyyy");
        Date date2 = null;
        try {
            date2 = dateFormat2.parse(strDate2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String dateNewFormatToDate = new SimpleDateFormat("dd-MMMM-yyyy").format(date2);
//        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat outputFormat = new SimpleDateFormat("dd-MMMM-yyyy");
//        String inputDateStr=fromDate;
//        Date date = null;
//        try {
//            date = inputFormat.parse(inputDateStr);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        String fromDateStr = outputFormat.format(date);
        mPresenter.getInventoryTransactionDetails(dateNewFormat, dateNewFormatToDate);
    }
}
