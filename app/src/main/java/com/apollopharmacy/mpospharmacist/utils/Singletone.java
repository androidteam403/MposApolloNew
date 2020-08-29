package com.apollopharmacy.mpospharmacist.utils;

import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;

import java.util.ArrayList;

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
