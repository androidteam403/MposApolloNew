package com.apollopharmacy.mpospharmacist.ui.corporatedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityCorporateDetailsBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.adapter.CorporateDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CorporateDetailsActivity extends BaseActivity implements CorporateDetailsMvpView {
    @Inject
    CorporateDetailsMvpPresenter<CorporateDetailsMvpView> mPresenter;
    ActivityCorporateDetailsBinding corporateDetailsBinding;

    private CorporateDetailAdapter corporateDetailAdapter;
    private ArrayList<CorporateModel.DropdownValueBean> corporateList;
    private ArrayList<CorporateModel.DropdownValueBean> tempCorporateList = new ArrayList<>();

    public static Intent getStartIntent(Context context, CorporateModel corporateModel) {
        Intent intent = new Intent(context, CorporateDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("corporate_list", corporateModel);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public static Intent getStartIntent(Context context, CorporateModel.DropdownValueBean corporateEntity, CorporateModel corporateModel) {
        Intent intent = new Intent(context, CorporateDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("corporate_list", corporateModel);
        bundle.putSerializable("corporate_info", corporateEntity);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        corporateDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_corporate_details);
        getActivityComponent().inject(this);
        mPresenter.onAttach(CorporateDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        corporateDetailsBinding.siteDataInfo.siteName.setText(mPresenter.getStoreName());
        corporateDetailsBinding.siteDataInfo.siteId.setText(mPresenter.getStoreId());
        corporateDetailsBinding.siteDataInfo.terminalId.setText(mPresenter.getTerminalId());
        corporateDetailsBinding.setCallback(mPresenter);
        CorporateModel corporateModel = (CorporateModel) getIntent().getSerializableExtra("corporate_list");
        if (corporateModel != null) {
            getCorporateList(corporateModel);
        }
        corporateDetailsBinding.corporateNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (corporateDetailAdapter != null) {
                    corporateDetailAdapter.getFilter().filter(s);
                }
            }
        });

        corporateDetailsBinding.corporateParentView.setOnTouchListener((v, event) -> {
            CommonUtils.hideKeyboard(CorporateDetailsActivity.this);
            return false;
        });
        mPresenter.onMposTabApiCall();
        turnOnScreen();
    }

    @Override
    public void onClickBackPressed() {
        onBackPressed();
    }

    @Override
    public void getCorporateList(CorporateModel corporateModel) {
        if (corporateModel.get_DropdownValue() != null && corporateModel.get_DropdownValue().size() > 0) {
            corporateList = new ArrayList<>();
            tempCorporateList.clear();
            corporateList.addAll(corporateModel.get_DropdownValue());
            tempCorporateList.addAll(corporateModel.get_DropdownValue());
            corporateDetailAdapter = new CorporateDetailAdapter(this, corporateList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            corporateDetailsBinding.corporateRecyclerView.setLayoutManager(mLayoutManager);
            corporateDetailAdapter.setClickListener(this);
            corporateDetailsBinding.corporateRecyclerView.setAdapter(corporateDetailAdapter);
        }
    }

    @Override
    public void showNotFoundCorporate() {
        corporateDetailsBinding.corporateRecyclerView.setVisibility(View.GONE);
        corporateDetailsBinding.noCorporateFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickCorporateItem(CorporateModel.DropdownValueBean item) {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("corporate_info", item);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        corporateDetailsBinding.imageView.setVisibility(View.GONE);
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
        corporateDetailsBinding.imageView.setImageBitmap(myBitmap);
        corporateDetailsBinding.imageView.setVisibility(View.VISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        corporateDetailsBinding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
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
//                    getSupportActionBar().hide();
                }
                handelPlayList();
                corporateDetailsBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        corporateDetailsBinding.imageView.setVisibility(View.GONE);
//                        getSupportActionBar().show();
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
