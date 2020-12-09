package com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentCustMasterBinding;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacist.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;


public class CustomerMasterFragment extends BaseFragment implements CustomerMasterMvpView, MainActivity.UserIneractionListener, MainActivity.OnBackPressedListener {

    @Inject
    CustomerMasterPresenter<CustomerMasterMvpView> mPresenter;
    private FragmentCustMasterBinding fragmentCustMasterBinding;
    private String requiredDOBFormat = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCustMasterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cust_master, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(CustomerMasterFragment.this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnUserIneractionListener(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnBackPressedListener(this);

        return fragmentCustMasterBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        fragmentCustMasterBinding.setCallback(mPresenter);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fragmentCustMasterBinding.dateOfRegistration.setText(df.format(c));

        genderSpinner();
        maritalStatusSpinner();
        mPresenter.onMposTabApiCall();
        turnOnScreen();
    }

    @Override
    public void onDateClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(fragmentCustMasterBinding.dateOfBirth.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = fragmentCustMasterBinding.dateOfBirth.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getBaseActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            requiredDOBFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
            fragmentCustMasterBinding.dateOfBirth.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 365.25 * 18)));
        dialog.show();
    }

    @Override
    public void onSubmitClick() {
        if (validate()) {
            mPresenter.handleCustomerAddService();
        }
    }

    @Override
    public void onAnniversaryClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(fragmentCustMasterBinding.anniversary.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = fragmentCustMasterBinding.anniversary.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getBaseActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            fragmentCustMasterBinding.anniversary.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onRegistrationClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(fragmentCustMasterBinding.dateOfRegistration.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = fragmentCustMasterBinding.dateOfRegistration.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(getBaseActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            fragmentCustMasterBinding.dateOfRegistration.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void onClickBackPressed() {

    }

    @Override
    public void addCustomerSuccess(AddCustomerResModel addCustomerResModel) {
        GetCustomerResponse.CustomerEntity customerEntity = new GetCustomerResponse.CustomerEntity();
        customerEntity.setSearchId(addCustomerResModel.getCustId());
        customerEntity.setCardName(addCustomerResModel.getFirstName());
        customerEntity.setCardNo(addCustomerResModel.getCardNumber());
        customerEntity.setCorpId(addCustomerResModel.getCorpId());
        customerEntity.setMobileNo(addCustomerResModel.getMobile());
        customerEntity.setTelephoneNo(addCustomerResModel.getTelephone());

//        Intent returnIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("customer_info", customerEntity);
//        returnIntent.putExtras(bundle);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
        showMessage(addCustomerResModel.getReturnMessage());
        fragmentCustMasterBinding.firstName.setText("");
        fragmentCustMasterBinding.middleName.setText("");
        fragmentCustMasterBinding.lastName.setText("");
        fragmentCustMasterBinding.cardNumber.setText("");
        fragmentCustMasterBinding.mobile.setText("");
        fragmentCustMasterBinding.telephone.setText("");
        fragmentCustMasterBinding.email.setText("");
        fragmentCustMasterBinding.age.setText("");
        fragmentCustMasterBinding.dateOfBirth.setText("");
        fragmentCustMasterBinding.districtEditText.setText("");
        fragmentCustMasterBinding.stateEdittext.setText("");
        fragmentCustMasterBinding.cityEdittext.setText("");
        fragmentCustMasterBinding.zipCode.setText("");
        fragmentCustMasterBinding.postalAddress.setText("");
        fragmentCustMasterBinding.anniversary.setText("");
        fragmentCustMasterBinding.numberOfDependents.setText("");

        genderSpinner();
        maritalStatusSpinner();

    }

    @Override
    public void addCustomerFailed(String errMsg) {
        showMessage(errMsg);
    }

    @Override
    public String getFirstName() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.firstName.getText())).toString();
    }

    @Override
    public String getMiddleName() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.middleName.getText())).toString();
    }

    @Override
    public String getLastName() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.lastName.getText())).toString();
    }

    @Override
    public int getAge() {
        if (Objects.requireNonNull(fragmentCustMasterBinding.age.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(fragmentCustMasterBinding.age.getText().toString());
        }
    }

    @Override
    public String getGenderOption() {
        if (fragmentCustMasterBinding.gender.getSelectedItem() == null) {
            return "";
        } else {
            return fragmentCustMasterBinding.gender.getSelectedItem().toString();
        }
    }

    @Override
    public String getMobile() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.mobile.getText())).toString();
    }

    @Override
    public String getAnniversary() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.anniversary.getText())).toString();
    }

    @Override
    public String getMaritalStatus() {
        if (fragmentCustMasterBinding.maritalStatusSpinner.getSelectedItem() == null) {
            return "";
        } else {
            return fragmentCustMasterBinding.maritalStatusSpinner.getSelectedItem().toString();
        }
    }

    @Override
    public int getNumberOfDependants() {
        if (Objects.requireNonNull(fragmentCustMasterBinding.numberOfDependents.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(fragmentCustMasterBinding.numberOfDependents.getText().toString());
        }
    }

    @Override
    public String getCardNumber() {
        return (Objects.requireNonNull(fragmentCustMasterBinding.cardNumber.getText())).toString();
    }

    @Override
    public String getDateOfReg() {
        return Objects.requireNonNull(fragmentCustMasterBinding.dateOfRegistration.getText()).toString();
    }

    @Override
    public String getDOB() {
        if (TextUtils.isEmpty(requiredDOBFormat)) {
            return CommonUtils.getCurrentDate("dd-MMM-yyyy");
        } else {
            return requiredDOBFormat;
        }
    }

    @Override
    public String getPostalAddress() {
        return Objects.requireNonNull(fragmentCustMasterBinding.postalAddress.getText()).toString();
    }

    @Override
    public String getCityOption() {
        return Objects.requireNonNull(fragmentCustMasterBinding.cityEdittext.getText()).toString();
    }

    @Override
    public String getStateOption() {
        return Objects.requireNonNull(fragmentCustMasterBinding.stateEdittext.getText()).toString();
    }

    @Override
    public String getDistrictOption() {
        return Objects.requireNonNull(fragmentCustMasterBinding.districtEditText.getText()).toString();
    }

    @Override
    public String getZipCode() {
        return Objects.requireNonNull(fragmentCustMasterBinding.zipCode.getText()).toString();
    }

    @Override
    public String getEmail() {
        return Objects.requireNonNull(fragmentCustMasterBinding.email.getText()).toString();
    }

    @Override
    public String getTelephone() {
        return Objects.requireNonNull(fragmentCustMasterBinding.telephone.getText()).toString();
    }

    private ArrayList<SpinnerPojo> getGender() {
        ArrayList<SpinnerPojo> arrGenderSpinner = new ArrayList<>();
        SpinnerPojo genderSpinnerPojo = new SpinnerPojo();
        genderSpinnerPojo.setGender("Male");
        arrGenderSpinner.add(genderSpinnerPojo);
        genderSpinnerPojo = new SpinnerPojo();
        genderSpinnerPojo.setGender("Female");
        arrGenderSpinner.add(genderSpinnerPojo);
        return arrGenderSpinner;
    }

    private ArrayList<SpinnerPojo.MaritalStatus> getMarital() {
        ArrayList<SpinnerPojo.MaritalStatus> arrMaritalSpinner = new ArrayList<>();
        SpinnerPojo.MaritalStatus maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Married");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("UnMarried");
        arrMaritalSpinner.add(maritalStatus);
        maritalStatus = new SpinnerPojo.MaritalStatus();
        maritalStatus.setMaritalStatus("Single");
        arrMaritalSpinner.add(maritalStatus);
        return arrMaritalSpinner;
    }


    private boolean validate() {
        String firstName = Objects.requireNonNull(fragmentCustMasterBinding.firstName.getText()).toString();
        String mobile = Objects.requireNonNull(fragmentCustMasterBinding.mobile.getText()).toString();
        String cardNumber = Objects.requireNonNull(fragmentCustMasterBinding.cardNumber.getText()).toString();
        String email = Objects.requireNonNull(fragmentCustMasterBinding.email.getText()).toString();
        String zipCode = Objects.requireNonNull(fragmentCustMasterBinding.zipCode.getText()).toString();

        if (firstName.isEmpty()) {
            fragmentCustMasterBinding.firstName.setError("First Name should not be empty");
            fragmentCustMasterBinding.firstName.requestFocus();
            return false;
        } else if (cardNumber.isEmpty()) {
            fragmentCustMasterBinding.cardNumber.setError("Card Number should not be empty");
            fragmentCustMasterBinding.cardNumber.requestFocus();
            return false;
        } else if (cardNumber.length() < 10) {
            fragmentCustMasterBinding.cardNumber.setError("Card Number Minimum 10 characters");
            fragmentCustMasterBinding.cardNumber.requestFocus();
            return false;
        } else if (!email.isEmpty() && !CommonUtils.isValidEmail(email)) {
            fragmentCustMasterBinding.email.setError("Enter Valid Email");
            fragmentCustMasterBinding.email.requestFocus();
            return false;
        } else if (mobile.isEmpty()) {
            fragmentCustMasterBinding.mobile.setError("Mobile Number should not be empty");
            fragmentCustMasterBinding.mobile.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(mobile)) {
            fragmentCustMasterBinding.mobile.setError("Invalid Mobile Number");
            fragmentCustMasterBinding.mobile.requestFocus();
            return false;
        } else if (!zipCode.isEmpty() && zipCode.length() < 6) {
            fragmentCustMasterBinding.zipCode.setError("Enter Valid ZipCode");
            fragmentCustMasterBinding.zipCode.requestFocus();
            return false;
        }
        return true;
    }

    private void genderSpinner() {
        fragmentCustMasterBinding.gender.setSelection(0);
        fragmentCustMasterBinding.gender.getEditText().setTypeface(Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf"));
        fragmentCustMasterBinding.gender.setTypeface(Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SpinnerPojo> genderSpinnerPojo = new ArrayAdapter<SpinnerPojo>(getBaseActivity(), android.R.layout.simple_spinner_item, getGender()) {
            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        genderSpinnerPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentCustMasterBinding.gender.setAdapter(genderSpinnerPojo);
    }

    private void maritalStatusSpinner() {
        fragmentCustMasterBinding.maritalStatusSpinner.setSelection(0);
        fragmentCustMasterBinding.maritalStatusSpinner.getEditText().setTypeface(Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf"));
        fragmentCustMasterBinding.maritalStatusSpinner.setTypeface(Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SpinnerPojo.MaritalStatus> maritalStatusPojo = new ArrayAdapter<SpinnerPojo.MaritalStatus>(getBaseActivity(), android.R.layout.simple_spinner_item, getMarital()) {

            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getBaseActivity().getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        maritalStatusPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentCustMasterBinding.maritalStatusSpinner.setAdapter(maritalStatusPojo);
        fragmentCustMasterBinding.maritalStatusSpinner.setFocusableInTouchMode(false);
    }

    private List<RowsEntity> rowsEntitiesList;

    @Override
    public void onSucessPlayList() {
        rowsEntitiesList = mPresenter.getDataListEntity();
        for (int i = 0; i < rowsEntitiesList.size(); i++) {
            if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                    rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                File file = new File(path);
                if (!file.exists()) {
                    mPresenter.onDownloadApiCall(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath(),
                            rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), i);
                    break;
                }
            }
        }
    }

    private boolean stopLooping;

    public void handelPlayList() {
        if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
            if (!onPause) {
                if (!stopLooping) {
                    boolean isAllFilesExist = false;
                    for (int i = 0; i < rowsEntitiesList.size(); i++) {
                        if (rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getPath() != null ||
                                rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName() != null) {
                            if (!rowsEntitiesList.get(i).isPlayed()) {
                                String path = String.valueOf(FileUtil.getMediaFilePath(rowsEntitiesList.get(i).getPlaylist_media().getMedia_library().getFile().get(0).getName(), getContext()));
                                File file = new File(path);
                                if (file.exists()) {
                                    playListData(path, i);
                                    isAllFilesExist = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!isAllFilesExist) {
                        for (int i = 0; i < rowsEntitiesList.size(); i++) {
                            rowsEntitiesList.get(i).setPlayed(false);
                        }
                        handelPlayList();
                    }
                }
            }
        }
    }

    public void playListData(String filePath, int pos) {
        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        fragmentCustMasterBinding.imageView.setImageBitmap(myBitmap);
        fragmentCustMasterBinding.imageView.setVisibility(View.VISIBLE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        fragmentCustMasterBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsEntitiesList.get(pos).setPlayed(true);
                if (pos == rowsEntitiesList.size() - 1) {
                    for (RowsEntity rowsEntity : rowsEntitiesList) {
                        rowsEntity.setPlayed(false);
                    }
                }
                handelPlayList();
            }
        }, 5000);
    }

    private boolean onPause;

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        onPause = false;
        idealScreen();
    }


    private Handler handler;
    private Runnable r;

    public void idealScreen() {
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                if (onPause) {
                    stopLooping = true;
                } else {
                    stopLooping = false;
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    if (rowsEntitiesList != null && rowsEntitiesList.size() > 0) {
                        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    }
                }
                handelPlayList();
                fragmentCustMasterBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        fragmentCustMasterBinding.imageView.setVisibility(View.GONE);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                        stopLooping = true;
                    }
                });

            }
        };
        startHandler();
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 180 * 1000);
    }

    @Override
    public void userInteraction() {
        stopHandler();
        startHandler();
    }

    public void turnOnScreen() {
        final Window win = getActivity().getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }


    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }

//    @Override
//    public void userBackListenerInteraction() {
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        fragmentCustMasterBinding.imageView.setVisibility(View.VISIBLE);
//        getActivity().finish();
//    }

    @Override
    public void doBack() {
        getActivity().finish();
        stopLooping = true;
    }
}