package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogEprescriptionCancelBinding;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoMvpView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

public class EPrescriptionDialog extends BaseDialog implements EPrescriptionDialogMvpView {

    private EPrescriptionInfoMvpView ePrescriptionInfoMvpView;
    private DialogEprescriptionCancelBinding eprescriptionCancelBinding;
    @Inject
    EPrescriptionDialogMvpPresenter<EPrescriptionDialogMvpView> mPresenter;
    private String headerTitle, spinnerHint, okBtnTitle, cancelBtnTitle;
    private ArrayList<SpinnerPojo.MaritalStatus> reasonList;

    public EPrescriptionDialog(String title, String spinnerHint, String okBtnTitle, String cancelBtnTitle, ArrayList<SpinnerPojo.MaritalStatus> reasonList) {
        super();
        this.headerTitle = title;
        this.spinnerHint = spinnerHint;
        this.okBtnTitle = okBtnTitle;
        this.cancelBtnTitle = cancelBtnTitle;
        this.reasonList = reasonList;
    }

    public static EPrescriptionDialog newInstance(String title, String spinnerHint, String okBtnTitle, String cancelBtnTitle, ArrayList<SpinnerPojo.MaritalStatus> reasonList) {
        EPrescriptionDialog dialog = new EPrescriptionDialog(title,spinnerHint,  okBtnTitle, cancelBtnTitle, reasonList);
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setEPrescriptionDialogMvpView(EPrescriptionInfoMvpView dialogMvpView) {
        this.ePrescriptionInfoMvpView = dialogMvpView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eprescriptionCancelBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_eprescription_cancel, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(EPrescriptionDialog.this);
        return eprescriptionCancelBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        eprescriptionCancelBinding.title.setText(headerTitle);
        eprescriptionCancelBinding.dialogButtonOK.setText(okBtnTitle);
        eprescriptionCancelBinding.dialogButtonNO.setText(cancelBtnTitle);
        eprescriptionCancelBinding.cancelReasonSpinner.setHint(spinnerHint);
        eprescriptionCancelBinding.cancelReasonSpinner.getEditText().setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/roboto_regular.ttf"));
        eprescriptionCancelBinding.cancelReasonSpinner.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SpinnerPojo.MaritalStatus> maritalStatusPojo = new ArrayAdapter<SpinnerPojo.MaritalStatus>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, reasonList) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        maritalStatusPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eprescriptionCancelBinding.cancelReasonSpinner.setAdapter(maritalStatusPojo);
        eprescriptionCancelBinding.cancelReasonSpinner.setFocusableInTouchMode(false);

        eprescriptionCancelBinding.dialogButtonNO.setOnClickListener(v -> {
            dismissDialog();
        });

        eprescriptionCancelBinding.dialogButtonOK.setOnClickListener(v -> {
            dismissDialog();
        });
    }

    @Override
    public void openLoginActivity() {

    }

    @Override
    public void dismissDialog() {
        dismissDialog("");
    }
}
