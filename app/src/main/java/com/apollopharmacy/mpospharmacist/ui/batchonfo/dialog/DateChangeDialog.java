package com.apollopharmacy.mpospharmacist.ui.batchonfo.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.DateChangeDialogBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseDialog;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoMvpView;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

public class DateChangeDialog extends BaseDialog implements DateChangeMvpView {
    @Inject
    DateChangeMvpPresenter<DateChangeMvpView> mPresenter;
    private DateChangeDialogBinding itemExpiredateChangeDialogBinding;
    private GetBatchInfoRes.BatchListObj bachInfoItem;
    private BatchInfoMvpView batchInfoMvpView;

    public static DateChangeDialog newInstance() {
        DateChangeDialog dialog = new DateChangeDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setBachInfoItem(GetBatchInfoRes.BatchListObj bachInfoItem) {
        this.bachInfoItem = bachInfoItem;
    }

    public void setBatchInfoMvpView(BatchInfoMvpView batchInfoMvpView) {
        this.batchInfoMvpView = batchInfoMvpView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemExpiredateChangeDialogBinding = DataBindingUtil.inflate(inflater, R.layout.date_change_dialog, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DateChangeDialog.this);
        return itemExpiredateChangeDialogBinding.getRoot();

    }

    @Override
    protected void setUp(View view) {
        itemExpiredateChangeDialogBinding.setBatchInfo(bachInfoItem);
        itemExpiredateChangeDialogBinding.itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickExpireDate();
            }
        });
        itemExpiredateChangeDialogBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdate();
            }
        });
    }

    @Override
    public void openLoginActivity() {

    }

    public void onClickExpireDate() {

        Calendar c = Calendar.getInstance(Locale.ENGLISH);

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
//            requiredDOBFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
//                itemExpiredateChangeDialogBinding.expireDate.setText(selectedDate);
//            itemExpiredateChangeDialogBinding.expireDate.setText(selectedDate);
            bachInfoItem.setExpDate(selectedDate);
            itemExpiredateChangeDialogBinding.setBatchInfo(bachInfoItem);
        }, mYear, mMonth, mDay);
//        dialog.getDatePicker().setMinDate((long) (System.currentTimeMillis()));// - (1000 * 60 * 60 * 24 * 365.25 * 18)
        dialog.show();
    }

    public void onClickUpdate() {
        batchInfoMvpView.batchItemInfo(bachInfoItem);
        dismiss();
    }

}
