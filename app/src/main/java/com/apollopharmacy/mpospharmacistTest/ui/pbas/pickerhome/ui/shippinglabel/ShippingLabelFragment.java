package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogLabelSizeBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentShippingLabelBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.PickerNavigationActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.adapter.ShippingLabelAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GeneratePdfbyFlidResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.scanner.ScannerActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ShippingLabelFragment extends BaseFragment implements ShippingLabelMvpView, PickerNavigationActivity.PickerNavigationActivityCallback {
    @Inject
    ShippingLabelMvpPresenter<ShippingLabelMvpView> mPresenter;
    private FragmentShippingLabelBinding shippingLabelBinding;
    private ShippingLabelAdapter shippingLabelAdapter;
    private DownloadManager manager;
    private GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse;
    private boolean isScanerBack;
    public static boolean isShippingLabelFragment = false;

    //Pagination
    public static List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseStatic;
    private List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseListTotal;
    private int startIndex = 0;
    private int endIndex = 100;
    int lastIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        shippingLabelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shipping_label, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(ShippingLabelFragment.this);
        return shippingLabelBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        shippingLabelBinding.setCallback(mPresenter);
        PickerNavigationActivity.mInstance.setWelcome("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icFilter.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.icPaperSize.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refresh.setVisibility(View.VISIBLE);
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.unHold.setVisibility(View.GONE);
        PickerNavigationActivity.mInstance.pickerNavigationActivityCallback = this;
        PickerNavigationActivity.mInstance.setTitle("Shipping Label");
        PickerNavigationActivity.mInstance.setStockAvailableVisibilty(false);
        PickerNavigationActivity.mInstance.setWelcome("Total 0 orders");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("");
        PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.refreshPickerPackerBiller.setVisibility(View.GONE);
        mPresenter.getJounalOnlineOrderTransactionApiCall();
        searchByFulfilmentId();
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
        Dialog paperLabelSizeDialog = new Dialog(getContext());
        DialogLabelSizeBinding dialogLabelSizeBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_label_size, null, false);
        paperLabelSizeDialog.setContentView(dialogLabelSizeBinding.getRoot());
        paperLabelSizeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (mPresenter.getPaperLabelSize().equalsIgnoreCase("4X6")) {
            dialogLabelSizeBinding.fourSixRadio.setChecked(true);
        } else if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A4")) {
            dialogLabelSizeBinding.aFourRadio.setChecked(true);
        }
        dialogLabelSizeBinding.labelSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.four_six_radio) {
                mPresenter.setPaperLabelSize("4X6");
                paperLabelSizeDialog.dismiss();
            } else if (checkedId == R.id.a_four_radio) {
                mPresenter.setPaperLabelSize("A4");
                paperLabelSizeDialog.dismiss();
            }
        });
        paperLabelSizeDialog.show();
    }

    @Override
    public void onClickRefresh() {
        mPresenter.getJounalOnlineOrderTransactionApiCall();
    }

    @Override
    public void onClickUnHold() {

    }

    @Override
    public void onClickRefreshPickerPackerBiller() {

    }

    @Override
    public void onSuccessGetJounalOnlineOrderTransactonApi(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList) {
        if (getJounalOnlineOrderTransactionsResponseList != null && getJounalOnlineOrderTransactionsResponseList.size() > 0) {


            getJounalOnlineOrderTransactionsResponseListTotal = getJounalOnlineOrderTransactionsResponseList.stream()
                    .filter(e -> !e.getPickupStatus())
                    .collect(Collectors.toList());
            if (getJounalOnlineOrderTransactionsResponseListTotal != null && getJounalOnlineOrderTransactionsResponseListTotal.size() >= 5000) {
                startIndex = 0;
                endIndex = 5000;
            } else {
                endIndex = getJounalOnlineOrderTransactionsResponseListTotal.size();
            }
            shippingLabelBinding.setIsNaxtPage(endIndex != getJounalOnlineOrderTransactionsResponseListTotal.size());
            shippingLabelBinding.setIsPrevtPage(startIndex != 0);

            List<GetJounalOnlineOrderTransactionsResponse> myLastPosts = getJounalOnlineOrderTransactionsResponseListTotal.subList(startIndex, endIndex);
            onSuccessGetJounalOnlineOrderTransactonApiText(myLastPosts);


//            for (int i = 0; i < getJounalOnlineOrderTransactionsResponseList.size(); i++) {
//                if (getJounalOnlineOrderTransactionsResponseList.get(i).getPickupStatus()) {
//                    getJounalOnlineOrderTransactionsResponseList.remove(i);
//                    i--;
//                }
//                if (i == getJounalOnlineOrderTransactionsResponseList.size() - 1) {
//                    shippingLabelAdapter = new ShippingLabelAdapter(getContext(), getJounalOnlineOrderTransactionsResponseList, this);
//                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                    shippingLabelBinding.shippingListRecycler.setLayoutManager(mLayoutManager1);
//                    shippingLabelBinding.shippingListRecycler.setItemAnimator(new DefaultItemAnimator());
//                    shippingLabelBinding.shippingListRecycler.setAdapter(shippingLabelAdapter);
//                    noShippinfLabelFound(getJounalOnlineOrderTransactionsResponseList.size());
//                    shippingLabelBinding.searchText.requestFocus();
//                }
//            }

        } else {
            noShippinfLabelFound(0);
        }
    }

    public void onSuccessGetJounalOnlineOrderTransactonApiText(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList) {
        if (getJounalOnlineOrderTransactionsResponseList != null && getJounalOnlineOrderTransactionsResponseList.size() > 0) {
//            for (int i = 0; i < getJounalOnlineOrderTransactionsResponseList.size(); i++) {
//                if (getJounalOnlineOrderTransactionsResponseList.get(i).getPickupStatus()) {
//                    getJounalOnlineOrderTransactionsResponseList.remove(i);
//                    i--;
//                }
//                if (i == getJounalOnlineOrderTransactionsResponseList.size() - 1) {
            shippingLabelAdapter = new ShippingLabelAdapter(getContext(), getJounalOnlineOrderTransactionsResponseList, this);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            shippingLabelBinding.shippingListRecycler.setLayoutManager(mLayoutManager1);
            shippingLabelBinding.shippingListRecycler.setItemAnimator(new DefaultItemAnimator());
            shippingLabelBinding.shippingListRecycler.setAdapter(shippingLabelAdapter);
            noShippinfLabelFound(getJounalOnlineOrderTransactionsResponseList.size());
            if (endIndex % 100 == 0) {
                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + (endIndex / 100));
            } else {
                PickerNavigationActivity.mInstance.activityNavigation3Binding.appBarMain.pageNo.setText("Page No." + ((startIndex / 100) + 1));
            }


            shippingLabelBinding.searchText.requestFocus();
//                }
//            }

//            List<GetJounalOnlineOrderTransactionsResponse> listOutput =
//                    getJounalOnlineOrderTransactionsResponseList.stream()
//                            .filter(e -> !e.getPickupStatus())
//                            .collect(Collectors.toList());
//            getJounalOnlineOrderTransactionsResponseList = listOutput;

//            shippingLabelAdapter = new ShippingLabelAdapter(getContext(), getJounalOnlineOrderTransactionsResponseList, this);
//            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//            shippingLabelBinding.shippingListRecycler.setLayoutManager(mLayoutManager1);
//            shippingLabelBinding.shippingListRecycler.setItemAnimator(new DefaultItemAnimator());
//            shippingLabelBinding.shippingListRecycler.setAdapter(shippingLabelAdapter);
//            noShippinfLabelFound(getJounalOnlineOrderTransactionsResponseList.size());
//            shippingLabelBinding.searchText.requestFocus();
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        } else {
            noShippinfLabelFound(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        shippingLabelBinding.searchText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
//        hideKeyboard();
    }

    private void searchByFulfilmentId() {
        shippingLabelBinding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String charString = editable.toString();
                if (charString.isEmpty()) {
//                    omsHeaderListTotal = mPresenter.getGlobalTotalOmsHeaderList();
                    startIndex = 0;
                    onSuccessGetJounalOnlineOrderTransactonApi(getJounalOnlineOrderTransactionsResponseStatic);
                } else {
                    List<GetJounalOnlineOrderTransactionsResponse> omsHeaderListTotalFilterTemp = new ArrayList<>();
                    for (GetJounalOnlineOrderTransactionsResponse row : getJounalOnlineOrderTransactionsResponseStatic) {
                        if (!omsHeaderListTotalFilterTemp.contains(row) && ((row.getRefno().toLowerCase().contains(charString.toLowerCase())) || (row.getBoxId().toLowerCase().contains(charString.toLowerCase())))) {
                            omsHeaderListTotalFilterTemp.add(row);
                        }

                    }
//                    List<TransactionHeaderResponse.OMSHeader> omsHeaderListTotalFilteredTemp = new ArrayList<>();
//                    omsHeaderListTotal = omsHeaderListTotalFilterTemp;
                    startIndex = 0;
                    onSuccessGetJounalOnlineOrderTransactonApi(omsHeaderListTotalFilterTemp);
                }








//                if (editable.length() >= 2) {
//                    shippingLabelBinding.search.setVisibility(View.GONE);
//                    shippingLabelBinding.deleteCancel.setVisibility(View.VISIBLE);
//                    if (shippingLabelAdapter != null) {
//                        shippingLabelAdapter.getFilter().filter(editable);
//                    }
//
//                } else if (shippingLabelBinding.searchText.getText().toString().equals("")) {
//                    if (shippingLabelAdapter != null) {
//                        shippingLabelAdapter.getFilter().filter("");
//                    }
//                    shippingLabelBinding.deleteCancel.setVisibility(View.GONE);
//                    shippingLabelBinding.search.setVisibility(View.VISIBLE);
//                } else {
//                    if (shippingLabelAdapter != null) {
//                        shippingLabelAdapter.getFilter().filter("");
//                    }
//                }
            }
        });
    }

    @Override
    public void onClickPrintLabel(GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse) {
        this.getJounalOnlineOrderTransactionsResponse = getJounalOnlineOrderTransactionsResponse;
        if (isStoragePermissionGranted()) {
            mPresenter.generatePdfbyFlidApiCall(getJounalOnlineOrderTransactionsResponse.getRefno(), mPresenter.getPaperLabelSize());
        }
    }

    @Override
    public void onSuccessGeneratepdfbyFlidApiCall(GeneratePdfbyFlidResponse generatePdfbyFlidResponse) {
        if (generatePdfbyFlidResponse != null && generatePdfbyFlidResponse.getStatus()) {
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File folder = new File(extStorageDirectory, "shipping");
            folder.mkdir();
            File file = new File(folder, this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mPresenter.doDownloadPdf(generatePdfbyFlidResponse.getUrl(), file);
        } else if (generatePdfbyFlidResponse != null && !generatePdfbyFlidResponse.getStatus()) {
            Toast.makeText(getContext(), generatePdfbyFlidResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPdf() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/shipping/" + this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");
        if (file.exists()) {
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            if (mPresenter.getPaperLabelSize().equalsIgnoreCase("4X6")) {
                builder.setMediaSize(PrintAttributes.MediaSize.NA_INDEX_4X6);
            } else if (mPresenter.getPaperLabelSize().equalsIgnoreCase("A4")) {
                builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            }
            builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);
            PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
            String jobName = this.getString(R.string.app_name) + " Document";

            printManager.print(jobName, pda, builder.build());
        }
    }

    @Override
    public void onClickScanCode() {
        isScanerBack = true;
        BillerOrdersActivity.isBillerActivity = true;
        isShippingLabelFragment = true;
        new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
        getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
    }

    @Override
    public void onClickSearchTextClear() {
        shippingLabelBinding.searchText.setText("");
        shippingLabelBinding.deleteCancel.setVisibility(View.GONE);
        shippingLabelBinding.search.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickPrevPage() {
        if (startIndex >= 100) {
            startIndex = startIndex - 100;

            if (lastIndex != 0) {
                endIndex = endIndex - lastIndex;
                lastIndex = 0;
            } else {
                endIndex = endIndex - 100;
            }
            shippingLabelBinding.setIsNaxtPage(endIndex != getJounalOnlineOrderTransactionsResponseListTotal.size());
            shippingLabelBinding.setIsPrevtPage(startIndex != 0);
            List<GetJounalOnlineOrderTransactionsResponse> myLastPosts = getJounalOnlineOrderTransactionsResponseListTotal.subList(startIndex, endIndex);

            onSuccessGetJounalOnlineOrderTransactonApiText(myLastPosts);

        } else {
            Toast.makeText(getContext(), "No Previous orders", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNextPage() {
        if (getJounalOnlineOrderTransactionsResponseListTotal.size() - 1 > endIndex) {
            startIndex = startIndex + 100;
            if (getJounalOnlineOrderTransactionsResponseListTotal != null && getJounalOnlineOrderTransactionsResponseListTotal.size() >= endIndex + 100) {
                endIndex = endIndex + 100;
            } else {
                lastIndex = getJounalOnlineOrderTransactionsResponseListTotal.size() - endIndex;
                endIndex = getJounalOnlineOrderTransactionsResponseListTotal.size();
            }
            shippingLabelBinding.setIsNaxtPage(endIndex != getJounalOnlineOrderTransactionsResponseListTotal.size());
            shippingLabelBinding.setIsPrevtPage(startIndex != 0);
            List<GetJounalOnlineOrderTransactionsResponse> myLastPosts = getJounalOnlineOrderTransactionsResponseListTotal.subList(startIndex, endIndex);
            onSuccessGetJounalOnlineOrderTransactonApiText(myLastPosts);
        } else {
            Toast.makeText(getContext(), "No Next orders", Toast.LENGTH_SHORT).show();
        }
    }

    PrintDocumentAdapter pda = new PrintDocumentAdapter() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
            InputStream input = null;
            OutputStream output = null;
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/shipping/" + ShippingLabelFragment.this.getJounalOnlineOrderTransactionsResponse.getRefno() + ".pdf");

                input = new FileInputStream(file);//"/storage/emulated/0/Documents/my-document-1656940186153.pdf"
                output = new FileOutputStream(destination.getFileDescriptor());
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf)) > 0) {
                    output.write(buf, 0, bytesRead);
                }
            } catch (Exception e) {

            } finally {
                try {
                    if (input != null) {
                        input.close();
                    } else {
                        Toast.makeText(getContext(), "FileInputStream getting null", Toast.LENGTH_SHORT).show();
                    }

                    if (output != null) {
                        output.close();
                    } else {
                        Toast.makeText(getContext(), "FileOutStream getting null", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }
            //int pages = computePageCount(newAttributes);
            PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("file_name.pdf").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
            callback.onLayoutFinished(pdi, true);
        }

    };

    @Override
    public void noShippinfLabelFound(int count) {
        if (count > 0) {
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + getJounalOnlineOrderTransactionsResponseListTotal.size() + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            shippingLabelBinding.noOrderFoundText.setVisibility(View.GONE);
            shippingLabelBinding.shippingListRecycler.setVisibility(View.VISIBLE);
        } else {
            PickerNavigationActivity.mInstance.setWelcome("Total " + count + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + getJounalOnlineOrderTransactionsResponseListTotal.size() + "/" + getJounalOnlineOrderTransactionsResponseStatic.size() + " orders");
//            PickerNavigationActivity.mInstance.setWelcome("Total " + count + " orders");
            shippingLabelBinding.shippingListRecycler.setVisibility(View.GONE);
            shippingLabelBinding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter.generatePdfbyFlidApiCall(getJounalOnlineOrderTransactionsResponse.getRefno(), mPresenter.getPaperLabelSize());
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() != null) {
                Toast.makeText(getContext(), "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                shippingLabelBinding.searchText.setText(Result.getContents());
                BillerOrdersActivity.isBillerActivity = false;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
