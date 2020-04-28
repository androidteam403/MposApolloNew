package com.apollopharmacy.mpospharmacist.ui.home.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivityMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivityMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.MainActivityPresenter;


public class KioskExitDialog extends Dialog {
    private Activity activity;

    private ProgressDialog barProgressDialog = null;
    private KioskExitClickListener adminClickListener;
    private MainActivityMvpPresenter<MainActivityMvpView> activityPresenter;

    public KioskExitDialog(Activity activity,MainActivityMvpPresenter<MainActivityMvpView> activityPresenter) {
        super(activity);
        this.activity = activity;
        this.activityPresenter = activityPresenter;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_kiosk_exit);
        TextView headerView = findViewById(R.id.kiosk_mode_header);

        if(activityPresenter != null)
            if(activityPresenter.isKisokMode())
                headerView.setText("Kiosk Exit Password");
            else
                headerView.setText("Kiosk Enter Password");

        Button button = findViewById(R.id.forgot_details_reset_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adminClickListener!= null) {
                    dismiss();
                    adminClickListener.onClickPositiveBtn();
                }
            }
        });

        ImageView imageView = findViewById(R.id.close_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adminClickListener != null){
                    dismiss();
                }
            }
        });
    }

    public void setPositiveClickListener(KioskExitClickListener  clickListener){
        this.adminClickListener = clickListener;
    }


}
