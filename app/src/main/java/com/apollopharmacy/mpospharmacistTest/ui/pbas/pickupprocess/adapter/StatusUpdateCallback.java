package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import java.util.concurrent.atomic.AtomicInteger;

public interface StatusUpdateCallback {
    void onClickUpdate(int orderAdapterPos, int newSelectedOrderAdapterPos, String status);
}
