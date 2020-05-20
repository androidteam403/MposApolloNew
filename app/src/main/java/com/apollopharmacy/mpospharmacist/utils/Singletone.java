package com.apollopharmacy.mpospharmacist.utils;

import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.AllowedPaymentModeRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import java.util.ArrayList;
import java.util.List;

public class Singletone {
    private static final Singletone ourInstance = new Singletone();

    public static Singletone getInstance() {
        return ourInstance;
    }

    private Singletone() {
    }

    public ArrayList<SalesLineEntity> itemsArrayList = new ArrayList<>();
    public GetTenderTypeRes.GetTenderTypeResultEntity tenderTypeResultEntity;
    public boolean isPlaceNewOrder = false;
    public boolean isOrderCompleted = false;
    public boolean isManualBilling = false;
    public boolean isReturn = false;
}
