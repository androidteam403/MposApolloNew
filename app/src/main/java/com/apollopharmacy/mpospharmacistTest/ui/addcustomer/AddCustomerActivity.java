package com.apollopharmacy.mpospharmacistTest.ui.addcustomer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityAddCustomerBinding;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.AddCustomerResModel;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
import com.apollopharmacy.mpospharmacistTest.utils.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class AddCustomerActivity extends BaseActivity implements AddCustomerMvpView {

    @Inject
    AddCustomerMvpPresenter<AddCustomerMvpView> mPresenter;
    private ActivityAddCustomerBinding addCustomerBinding;

    private String requiredDOBFormat = "";
    private GetCustomerResponse.CustomerEntity userInputNumber;

    public static Intent getStartIntent(Context context, boolean isEdit, GetCustomerResponse.CustomerEntity inputNumber) {
        Intent intent = new Intent(context, AddCustomerActivity.class);
        intent.putExtra("is_edit_mode", isEdit);
        intent.putExtra("customer_number", inputNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        addCustomerBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AddCustomerActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        addCustomerBinding.setCallback(mPresenter);
        addCustomerBinding.siteData.siteName.setText(mPresenter.getStoreName());
        addCustomerBinding.siteData.siteId.setText(mPresenter.getStoreId());
        addCustomerBinding.siteData.terminalId.setText(mPresenter.getTerminalId());
        if (getIntent() != null) {
            userInputNumber = (GetCustomerResponse.CustomerEntity) getIntent().getSerializableExtra("customer_number");
            addCustomerBinding.setCustomerDetails(userInputNumber);

        }
        addCustomerBinding.gender.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        addCustomerBinding.gender.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        ArrayAdapter<SpinnerPojo> genderSpinnerPojo = new ArrayAdapter<SpinnerPojo>(getContext(), android.R.layout.simple_spinner_item, getGender()) {
            @NotNull
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        genderSpinnerPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCustomerBinding.gender.setAdapter(genderSpinnerPojo);

       // addCustomerBinding.maritalStatusSpinner.getEditText().setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
      // addCustomerBinding.maritalStatusSpinner.setTypeface(Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf"));
        /*ArrayAdapter<SpinnerPojo.MaritalStatus> maritalStatusPojo = new ArrayAdapter<SpinnerPojo.MaritalStatus>(getApplicationContext(), android.R.layout.simple_spinner_item, getMarital()) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        maritalStatusPojo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCustomerBinding.maritalStatusSpinner.setAdapter(maritalStatusPojo);
        addCustomerBinding.maritalStatusSpinner.setFocusableInTouchMode(false);*/

        if (mPresenter.enablescreens()) {
            mPresenter.onMposTabApiCall();
            turnOnScreen();
        }


    }
   /* @Override
    public boolean isOpenScreens() {
        return getDataManager().isOpenScreens();
    }*/

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        addCustomerBinding.imageView.setVisibility(View.GONE);
    }

    @Override
    public void onDateClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(addCustomerBinding.dateOfBirth.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addCustomerBinding.dateOfBirth.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            requiredDOBFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(c.getTime());
            addCustomerBinding.dateOfBirth.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 365.25 * 18)));
        dialog.show();
    }

    @Override
    public void onSubmitClick() {
        if (validate()) {
            boolean isEditMode = getIntent().getBooleanExtra("is_edit_mode", false);
            if (isEditMode)
                submitEditMode();
            else
                mPresenter.handleCustomerAddService();
        }
    }

    private void submitEditMode() {
        GetCustomerResponse.CustomerEntity customerEntity = new GetCustomerResponse.CustomerEntity();
        customerEntity.setSearchId(userInputNumber.getSearchId());
        customerEntity.setCustId(userInputNumber.getCustId());
        customerEntity.setCardName(getFirstName());
        //customerEntity.setMiddleName(getMiddleName());
      //  customerEntity.setLastName(getLastName());
          customerEntity.setMiddleName("");
          customerEntity.setLastName("");
        customerEntity.setCardNo(getCardNumber());
       // customerEntity.setEmail(getEmail());
        customerEntity.setEmail("");

        customerEntity.setMobileNo(getMobile());
      //  customerEntity.setTelephoneNo(getTelephone());
        customerEntity.setTelephoneNo("");
        //customerEntity.setAge(addCustomerBinding.age.getText().toString());
        customerEntity.setAge("");
        customerEntity.setGender(getGenderOption());
        //customerEntity.setMaritalStatus(getMaritalStatus());
        customerEntity.setMaritalStatus("");
        customerEntity.setDob(getDOB());
       // customerEntity.setDistrict(getDistrictOption());
         customerEntity.setDistrict("");
       // customerEntity.setCity(getCityOption());
        customerEntity.setCity("");
        customerEntity.setState(getStateOption());
        customerEntity.setZipCode(getZipCode());
        customerEntity.setPostalAddress(getPostalAddress());
       // customerEntity.setAnniversary(getAnniversary());
        customerEntity.setAnniversary("");
       // customerEntity.setNumberOfDependents(addCustomerBinding.numberOfDependents.getText().toString());
         customerEntity.setNumberOfDependents("");

        customerEntity.setDateOfRegistration(getDateOfReg());
        customerEntity.setCorpId(userInputNumber.getCorpId());

        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer_info", customerEntity);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

   /* @Override
    public void onAnniversaryClick() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int mYear;
        int mMonth;
        int mDay;
        if (Objects.requireNonNull(addCustomerBinding.anniversary.getText()).toString().isEmpty()) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String selectedBirthDate = addCustomerBinding.anniversary.getText().toString();
            String[] expDate = selectedBirthDate.split("-");
            mYear = Integer.parseInt(expDate[2]);
            mMonth = Integer.parseInt(expDate[1]) - 1;
            mDay = Integer.parseInt(expDate[0]);
        }
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            String selectedDate;
            c.set(year, monthOfYear, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());
            addCustomerBinding.anniversary.setText(selectedDate);
        }, mYear, mMonth, mDay);
        dialog.show();
    }*/

    @Override
    public void onClickBackPressed() {
        onBackPressed();
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

        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer_info", customerEntity);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

        Toast.makeText(this, addCustomerResModel.getReturnMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCustomerFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getFirstName() {
        return (Objects.requireNonNull(addCustomerBinding.firstName.getText())).toString();
    }

    /*@Override
    public String getMiddleName() {
        return (Objects.requireNonNull(addCustomerBinding.middleName.getText())).toString();
    }

    @Override
    public String getLastName() {
        return (Objects.requireNonNull(addCustomerBinding.lastName.getText())).toString();
    }

    @Override
    public int getAge() {
        if (Objects.requireNonNull(addCustomerBinding.age.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(addCustomerBinding.age.getText().toString());
        }
    }
*/
    @Override
    public String getGenderOption() {
        if (addCustomerBinding.gender.getSelectedItem() == null) {
            return "";
        } else {
            return addCustomerBinding.gender.getSelectedItem().toString();
        }
    }

    @Override
    public String getMobile() {
        return (Objects.requireNonNull(addCustomerBinding.mobile.getText())).toString();
    }

  /*  @Override
    public String getAnniversary() {
        return (Objects.requireNonNull(addCustomerBinding.anniversary.getText())).toString();
    }*/

   /* @Override
    public String getMaritalStatus() {
        if (addCustomerBinding.maritalStatusSpinner.getSelectedItem() == null) {
            return "";
        } else {
            return addCustomerBinding.maritalStatusSpinner.getSelectedItem().toString();
        }
    }*/

   /* @Override
    public int getNumberOfDependants() {
        if (Objects.requireNonNull(addCustomerBinding.numberOfDependents.getText()).toString().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(addCustomerBinding.numberOfDependents.getText().toString());
        }
    }*/

    @Override
    public String getCardNumber() {
        return (Objects.requireNonNull(addCustomerBinding.cardNumber.getText())).toString();
    }

    @Override
    public String getDateOfReg() {
        return Objects.requireNonNull(addCustomerBinding.dateOfRegistration.getText()).toString();
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
        return Objects.requireNonNull(addCustomerBinding.postalAddress.getText()).toString();
    }

    /*@Override
    public String getCityOption() {
        return Objects.requireNonNull(addCustomerBinding.cityEdittext.getText()).toString();
    }*/

    @Override
    public String getStateOption() {
        return Objects.requireNonNull(addCustomerBinding.stateEdittext.getText()).toString();
    }

   /* @Override
    public String getDistrictOption() {
        return Objects.requireNonNull(addCustomerBinding.districtEditText.getText()).toString();
    }
*/
    @Override
    public String getZipCode() {
        return Objects.requireNonNull(addCustomerBinding.zipCode.getText()).toString();
    }

   /* @Override
    public String getEmail() {
        return Objects.requireNonNull(addCustomerBinding.email.getText()).toString();
    }*/

   /* @Override
    public String getTelephone() {
        return Objects.requireNonNull(addCustomerBinding.telephone.getText()).toString();
    }*/

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
        String firstName = Objects.requireNonNull(addCustomerBinding.firstName.getText()).toString();
        String mobile = Objects.requireNonNull(addCustomerBinding.mobile.getText()).toString();
        String cardNumber = Objects.requireNonNull(addCustomerBinding.cardNumber.getText()).toString();
      //  String email = Objects.requireNonNull(addCustomerBinding.email.getText()).toString();
        String zipCode = Objects.requireNonNull(addCustomerBinding.zipCode.getText()).toString();

        if (firstName.isEmpty()) {
            addCustomerBinding.firstName.setError("First Name should not be empty");
            addCustomerBinding.firstName.requestFocus();
            return false;
        } else if (cardNumber.isEmpty()) {
            addCustomerBinding.cardNumber.setError("Card Number should not be empty");
            addCustomerBinding.cardNumber.requestFocus();
            return false;
        } else if (cardNumber.length() < 10) {
            addCustomerBinding.cardNumber.setError("Card Number Minimum 10 characters");
            addCustomerBinding.cardNumber.requestFocus();
            return false;
        } /*else if (!email.isEmpty() && !CommonUtils.isValidEmail(email)) {
            addCustomerBinding.email.setError("Enter Valid Email");
            addCustomerBinding.email.requestFocus();
            return false;
        } */else if (mobile.isEmpty()) {
            addCustomerBinding.mobile.setError("Mobile Number should not be empty");
            addCustomerBinding.mobile.requestFocus();
            return false;
        } else if (!CommonUtils.mobileValidate(mobile)) {
            addCustomerBinding.mobile.setError("Invalid Mobile Number");
            addCustomerBinding.mobile.requestFocus();
            return false;
        } else if (!zipCode.isEmpty() && zipCode.length() < 6) {
            addCustomerBinding.zipCode.setError("Enter Valid ZipCode");
            addCustomerBinding.zipCode.requestFocus();
            return false;
        }

        return true;
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
        addCustomerBinding.imageView.setImageBitmap(myBitmap);
        addCustomerBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        addCustomerBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                handelPlayList();
                addCustomerBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        addCustomerBinding.imageView.setVisibility(View.GONE);
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
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    public void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }


    @Nullable
    @Override
    public Context getContext() {
        return this;
    }
}
