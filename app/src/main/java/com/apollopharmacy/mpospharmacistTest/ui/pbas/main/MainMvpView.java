package com.apollopharmacy.mpospharmacistTest.ui.pbas.main;

import com.apollopharmacy.mpospharmacistTest.data.network.pojo.FeedItem;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

import java.util.List;

/**
 * Created on : Nov 02, 2021
 * Author     : NAVEEN
 */
public interface MainMvpView extends MvpView {
    void updateFeed(List<FeedItem> feedItemList);
}
